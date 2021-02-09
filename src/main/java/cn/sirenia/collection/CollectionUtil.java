package cn.sirenia.collection;

import java.util.*;

public class CollectionUtil {
    /**
     * java泛型不支持协变和逆变
     * 比如
     * List<Cat> cats = ...
     * List<Animal> animals = cats//编译不通过
     * */
    public static <S extends T,T> List<T> covariantToList(Collection<S> source){
        if(source==null){
            return null;
        }
        List<T> target = new ArrayList<>(source.size());
        for(S item: source){
            target.add(item);
        }
        return target;
    }
    public static <S extends T,T> Set<T> covariantToSet(Collection<S> source){
        if(source==null){
            return null;
        }
        Set<T> target = new HashSet<>(source.size());
        for(S item: source){
            target.add(item);
        }
        return target;
    }
}
