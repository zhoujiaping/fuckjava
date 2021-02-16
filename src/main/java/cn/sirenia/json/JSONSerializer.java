package cn.sirenia.json;

import cn.sirenia.lang.ValueHolder;
import cn.sirenia.reflect.ReflectUtil;

import java.lang.reflect.Array;
import java.util.*;

/**
 * todo
 * 1:支持循环引用
 * 2:支持缩进
 * 3:支持过滤器
 * 4:支持自定义日期格式
 * 5:支持配置null值序列化策略
 * */
public class JSONSerializer {
    static Map<Character,String> specials = new HashMap<>();
    static{
        specials.put('\t',"\\t");
        specials.put('\r',"\\r");
        specials.put('\n',"\\n");
        specials.put('\b',"\\b");
        specials.put('\f',"\\f");
        specials.put('"',"\\\"");
        specials.put('\\',"\\\\");
        //any else?
    }
    public String stringify(Object obj) {
        if (obj == null) {
            return "null";
        }
        StringBuilder json = new StringBuilder();
        Class clazz = obj.getClass();
        if (clazz.isArray()) {
            json.append("[");
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                Object item = Array.get(obj, 0);
                json.append(stringify(item));
            }
            json.append("]");
        } else if (Collection.class.isAssignableFrom(clazz)) {
            json.append("[");
            Collection collection = (Collection) obj;
            for (Object item : collection) {
                json.append(stringify(item));
            }
            json.append("]");
        } else if (Map.class.isAssignableFrom(clazz)) {
            json.append("{");
            Map map = (Map) obj;
            Iterator<Map.Entry> iter = map.entrySet().iterator();
            boolean isTail = false;
            while (iter.hasNext()) {
                Map.Entry entry = iter.next();
                if (isTail) {
                    json.append(",");
                } else {
                    isTail = true;
                }
                json.append("\"").append(entry.getKey().toString())
                        .append("\":")
                        .append(stringify(entry.getValue()));
            }
            json.append("}");
        } else if (clazz.isPrimitive() || Number.class.isAssignableFrom(clazz)) {
            json.append(obj);
        } else if (clazz.isEnum()) {
            Enum e = (Enum) obj;
            return e.name();
        } else if (CharSequence.class.isAssignableFrom(clazz)) {
            json.append("\"");
            //字符和字符串需要对特殊字符转义
            escapedAppend((CharSequence)obj,json);
            json.append("\"");
        } else {
            json.append("{");
            ValueHolder<Boolean> isTail = new ValueHolder<>(false);
            ReflectUtil.eachMemberFields(obj, f -> {
                try {
                    if (isTail.value) {
                        json.append(",");
                    } else {
                        isTail.value = true;
                    }
                    json.append("\"").append(f.getName()).append("\":")
                            .append(stringify(f.get(obj)));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            json.append("}");
        }
        return json.toString();
    }

    private void escapedAppend(CharSequence value,StringBuilder json) {
        int len = value.length();
        char ch;
        String escaped;
        for(int i=0;i<len;i++){
            ch = value.charAt(i);
            escaped = specials.get(ch);
            if(escaped==null){
                json.append(ch);
            }else{
                json.append(escaped);
            }
        }
    }

    static class Animal {
        String name;
        int age;

    }

    static class Cat extends Animal {
        String color;
        String[] label;
    }

    public static void main(String[] args) {
        JSONSerializer jsonSerializer = new JSONSerializer();
        Cat cat = new Cat();
        cat.name = "tomcat";
        cat.color = "red\"\t\\";
        cat.age = 1;
        cat.label = new String[]{"beautiful"};
        String res = jsonSerializer.stringify(cat);
        System.out.println(res);

    }
}
