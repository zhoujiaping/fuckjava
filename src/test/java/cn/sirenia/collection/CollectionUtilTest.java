package cn.sirenia.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtilTest{
    private static class Cat extends Animal{

    }
    private static class Animal{

    }
    @Test
    public void test() {
        List<Cat> cats = new ArrayList<>();
        // List<Animal> animals = cats;//compile error
        List<Animal> animals = CollectionUtil.covariantToList(cats);
        System.out.println(animals);
    }
}
