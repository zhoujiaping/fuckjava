package cn.sirenia.io;

import cn.sirenia.exception.ExceptionUtil;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class FileUtil {
    public static String readText(String filename){
        return readText(filename,"utf-8");
    }
    public static String readText(String filename,String charset){
        try {
            FileChannel channel = new FileInputStream(filename).getChannel();
            ByteBuffer bf = ByteBuffer.allocate((int) channel.size());
            channel.read(bf);
            return new String(bf.array(), charset);
        }catch (IOException e){
            throw ExceptionUtil.wrapRuntimeException(e);
        }
    }
    public static void eachLine(String filename, Consumer<String> consumer){
        withReader(filename,reader->{
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    consumer.accept(line);
                }
            }catch (IOException e){
                throw ExceptionUtil.wrapRuntimeException(e);
            }
        });
    }
    public static void withReader(String filename,String charset,Consumer<BufferedReader> consumer){
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename),charset))) {
            consumer.accept(br);
        }catch (IOException e){
            throw ExceptionUtil.wrapRuntimeException(e);
        }
    }
    public static void withReader(String filename,Consumer<BufferedReader> consumer){
        withReader(filename,"utf-8",consumer);
    }
    public static void withInputStream(String filename, Consumer<BufferedInputStream> consumer){
        try(BufferedInputStream bi = new BufferedInputStream(new FileInputStream(filename))){
            consumer.accept(bi);
        }catch (IOException e){
            throw ExceptionUtil.wrapRuntimeException(e);
        }
    }
    //write
    public static void copy(InputStream in,File out){
        try {
            copy(in, new FileOutputStream(out));
        }catch (Exception e){
            throw ExceptionUtil.wrapRuntimeException(e);
        }
    }
    public static void copy(InputStream in,OutputStream out){
        try(BufferedInputStream bis = buffered(in);
        BufferedOutputStream bos = buffered(out);){
            byte[] buf = new byte[1024*64];
            int len;
            while((len = bis.read(buf))>0){
                bos.write(buf,0,len);
            }
        } catch (IOException e) {
            ExceptionUtil.wrapRuntimeException(e);
        }
    }
    public static BufferedInputStream buffered(InputStream in){
        BufferedInputStream bis;
        if(in instanceof BufferedInputStream){
            bis = (BufferedInputStream) in;
        }else{
            bis = new BufferedInputStream(in);
        }
        return bis;
    }
    public static BufferedOutputStream buffered(OutputStream out){
        BufferedOutputStream bos;
        if(out instanceof BufferedOutputStream){
            bos = (BufferedOutputStream) out;
        }else{
            bos = new BufferedOutputStream(out);
        }
        return bos;
    }
    /**比如有时候我们并不需要保存该文件，仅仅需要下载到缓存目录，读取处理之后可以清理掉它*/
    public static <R> R withTempFile(InputStream in, Function<File,R> func){
        String tmpDir = System.getProperty("java.io.tmpdir");
        String uuid = UUID.randomUUID().toString();
        File file = new File(tmpDir,uuid);
        try {
            if (!file.getParentFile().exists()) {
                if (file.getParentFile().mkdirs()) {
                    throw new RuntimeException("创建目录失败");
                }
            }
            copy(in, file);
            return func.apply(file);
        }finally {
            file.delete();
        }
    }
    public boolean createRecursion(File file){
        File p = file.getParentFile();
        try {
            if(!p.exists()){
                if(!p.mkdirs()){
                    return false;
                }
            }
            return file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 递归删除该文件及其子文件
     */
    public boolean deleteRecursion(File file){
        return deleteRecursion(file,true);
    }
    public boolean deleteRecursion(File file,boolean deleteThisFile){
        Map<String,Boolean> map = new HashMap<>();
        walkDfs(file,f->map.put(f.getAbsolutePath(), f.delete()),deleteThisFile);
        return map.isEmpty();
    }
    /**
     * 深度优先遍历
     * */
    public void walkDfs(File file,Consumer<File> consumer) {
        walkDfs(file,consumer,true);
    }
    /**
     * 广度优先遍历
     * */
    public void walkBfs(File file,Consumer<File> consumer) {
        walkBfs(file,consumer,true);
    }
    public void walkBfs(File file,Consumer<File> consumer, boolean walkThisFile) {
        if(walkThisFile){
            consumer.accept(file);
        }
        walkBfsInternal(file,consumer);
    }
    /**
     * 深度优先遍历
     * */
    public void walkDfs(File file,Consumer<File> consumer,boolean handleThisFile) {
        walkDfsInternal(file,consumer);
        if(handleThisFile){
            consumer.accept(file);
        }
    }
    private void walkBfsInternal(File file,Consumer<File> consumer) {
        File[] files = file.listFiles();
        for(int i=0;i<files.length;i++){
            consumer.accept(files[i]);
            if(files[i].isDirectory()){
                walkBfsInternal(files[i],consumer);
            }
        }
    }
    private void walkDfsInternal(File file,Consumer<File> consumer) {
        File[] files = file.listFiles();
        for(int i=0;i<files.length;i++){
            if(files[i].isDirectory()){
                walkDfsInternal(files[i],consumer);
            }
            consumer.accept(files[i]);
        }
    }
}
