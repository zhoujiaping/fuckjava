package cn.sirenia.lang;

import java.lang.reflect.Modifier;
import java.util.Collection;

public class TypeUtil {
    public static boolean isArray(Class<?> clazz){
        return clazz.isArray();
    }
    public static boolean isCollection(Class<?> clazz){
        return Collection.class.isAssignableFrom(clazz);
    }
    public static boolean isPrimitive(Class<?> clazz){
    	return clazz.isPrimitive();
    }
    public static boolean isAbstract(Class<?> clazz){
    	return Modifier.isAbstract(clazz.getModifiers());
    }
}
