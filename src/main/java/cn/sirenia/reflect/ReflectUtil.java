package cn.sirenia.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ReflectUtil {
    public static void eachDeclaredMemberFields(Object obj, Consumer<Field> consumer){
        Field[] fs = obj.getClass().getDeclaredFields();
        for(int i=0;i<fs.length;i++){
            if(!fs[i].isSynthetic() && !Modifier.isStatic(fs[i].getModifiers())){
                boolean accessible = fs[i].isAccessible();
                if(!accessible){
                    fs[i].setAccessible(true);
                }
                consumer.accept(fs[i]);
                //并发情况有问题，所以不还原accessible
                //fs[i].setAccessible(accessible);
            }
        }
    }
    public static Field getFieldByFieldName(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }
    /**
     * 获取obj对象fieldName的属性值
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueByFieldName(Object obj, String fieldName)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值
     *
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValueByFieldName(Object obj, String fieldName, Object value)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    // 处理有多个插件时的情况
    public static Object unwrapProxys(Object target) throws Exception {
        boolean isProxy = false;
        do {
            Class<?> targetClazz = target.getClass();
            isProxy = targetClazz.getName().startsWith("com.sun.proxy.$Proxy");
            if (isProxy) {
                target = getValueByFieldName(target, "h");
                target = getValueByFieldName(target, "target");
            }
        } while (isProxy);
        return target;
    }
    public static Set<Class<?>> getAllInterfaces(Class<?> type){
        Set<Class<?>> interfaces = new HashSet<>();
        while(type!=null){
            for(Class<?> i : type.getInterfaces()){
                interfaces.add(i);
            }
            type = type.getSuperclass();
        }
        return interfaces;
    }
}
