package cn.sirenia.lang;

import java.util.LinkedHashMap;
import java.util.Map;

class EnumUtil {
    /**
     * 返回枚举名称到枚举实例的映射
     * */
    public static <E extends Enum<E>> Map<String, E> getEnumMap(Class<E> enumClass) {
        Map<String, E> map = new LinkedHashMap<>();
        for (E e: enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

}