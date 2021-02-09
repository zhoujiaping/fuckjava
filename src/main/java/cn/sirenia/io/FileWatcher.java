package cn.sirenia.io;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author zjp
 */
public class FileWatcher {
	private String dir;
	private List<Kind<Path>> kindList = new ArrayList<>();
	private boolean enabled = true;
	public FileWatcher withDir(String dir){
		this.dir = dir;
		return this;
	}
	/**
	 * 参考StandardWatchEventKinds
	 * @param kind 监听类型
	 * @return FileWatcher
	 */
	public FileWatcher withKind(Kind<Path> kind){
		kindList.add(kind);
		return this;
	}
	public void stop(){
		enabled = false;
	}
	/**
	 * 参考WatchEvent
	 * @param consumer 监听到事件的回调
	 */
	public void watch(Consumer<WatchEvent<?>> consumer){
		try{
			watchInternal(consumer);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public void watchEntryModify(Consumer<WatchEvent<?>> consumer){
		withKind(StandardWatchEventKinds.ENTRY_MODIFY);
		watch(consumer);
	}
	private void watchInternal(Consumer<WatchEvent<?>> consumer) throws IOException, InterruptedException{
		WatchService watcher = FileSystems.getDefault().newWatchService();
		Path path = FileSystems.getDefault().getPath(dir);
		path.register(watcher, kindList.toArray(new Kind[0]));
		while (enabled) {
			WatchKey key = watcher.take();
            for (WatchEvent<?> event : key.pollEvents()) {
				consumer.accept(event);
            }
            key.reset();  
        }  
	}
	public static void main(String[] args){
		String dir = "f:/";
		new FileWatcher().withDir(dir).withKind(StandardWatchEventKinds.ENTRY_MODIFY)
		.watch(event->{
			//获取目录下新增的文件名
            String fileName = event.context().toString();
            //检查文件名是否符合要求
			String ignoredFileName = "text.txt";
            if(ignoredFileName.equals(fileName)){
                String filePath = PathUtil.concat(dir, fileName);
                System.out.println(FileUtil.readText(filePath));
            }
		});
	}
}
