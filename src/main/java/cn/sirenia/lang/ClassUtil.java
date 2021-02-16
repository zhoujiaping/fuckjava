package cn.sirenia.lang;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClassUtil {
	private static final Map<String,Class<?>> typeMap = new HashMap<>();
	private static final Set<Class> wrapperClassMap = new HashSet<>();
	static{
		typeMap.put("long", long.class);
		typeMap.put("int", int.class);
		typeMap.put("short", short.class);
		typeMap.put("byte", byte.class);
		typeMap.put("boolean", boolean.class);
		typeMap.put("float", float.class);
		typeMap.put("double", double.class);
		typeMap.put("char", char.class);

		wrapperClassMap.add(Short.class);
		wrapperClassMap.add(Integer.class);
		wrapperClassMap.add(Character.class);
		wrapperClassMap.add(Long.class);
		wrapperClassMap.add(Float.class);
		wrapperClassMap.add(Double.class);
		wrapperClassMap.add(Boolean.class);
	}
	public static Class<?> forName(String name) throws ClassNotFoundException{
		Class<?> c = typeMap.get(name);
		if(c!=null){
			return c;
		}
		return Class.forName(name);
	}
	public static boolean isWrapperClass(Class clazz){
		return wrapperClassMap.contains(clazz);
	}
}
