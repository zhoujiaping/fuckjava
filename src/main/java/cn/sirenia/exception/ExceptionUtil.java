package cn.sirenia.exception;

public class ExceptionUtil {
    public static RuntimeException wrapRuntimeException(Throwable e){
        if(isRuntimeException(e)){
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }
    /**
     * 从异常栈中找到第一个不为RuntimeException的异常（精确匹配），如果找不到，就取最上层的一个异常
     * * */
    public static Throwable unwrapRuntimeException(Throwable e){
        Throwable cause = e;
        Throwable nextCause = e.getCause();
        while(cause!=null){
            if(isExactlyRuntimeException(cause)){
                if(nextCause==null){
                    return cause;
                }else{
                    cause = nextCause;
                    nextCause = nextCause.getCause();
                }
            }else{
                return cause;
            }
        }
        return e;//that's impossible
    }
    public static boolean isRuntimeException(Throwable e){
        return e instanceof RuntimeException;
    }
    public static boolean isExactlyRuntimeException(Throwable e){
        return e.getClass().equals(RuntimeException.class);
    }
    /***/
    public static String findLocation(Throwable e) {
        StackTraceElement[] traces = e.getStackTrace();
        StackTraceElement trace = traces[0];
        String className = trace.getClassName();
        String methodName = trace.getMethodName();
        String fileName = trace.getFileName();
        int lineNumber = trace.getLineNumber();
        return "at " + className + "." + methodName + "(" + fileName + ":" + lineNumber + ")";
    }
}
