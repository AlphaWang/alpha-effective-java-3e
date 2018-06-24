package com.effectivejava.ch08_method;

import java.util.*;

/**
 * Author Mr.Pro
 * Date   2018/6/24 = 下午10:44
 */
public class CollectionClassfier {

    public static String classify(Set<?> s){ return "set"; }
    public static String classify(List<?> l){ return "list"; }
    public static String classify(Collection<?> c){ return "unknown"; }

    public static void main(String[] args){
        Collection<?>[] collections = {new HashSet<String>(), new ArrayList<String>(), new HashMap<String, String>().values()};
        for (Collection<?> c : collections){
            System.out.println(classify(c));
        }
    }
}
