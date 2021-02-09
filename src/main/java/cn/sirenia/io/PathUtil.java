package cn.sirenia.io;

public class PathUtil {
    public static String concat(String path0,String... paths){
        StringBuilder path = new StringBuilder(path0);
        if(paths!=null){
            for(int i=0;i<paths.length-1;i++){
                path.append(paths[i]).append("/");
            }
            path.append(paths[paths.length-1]);
        }
        return path.toString().replaceAll("[/\\\\]+", "/");
    }
}
