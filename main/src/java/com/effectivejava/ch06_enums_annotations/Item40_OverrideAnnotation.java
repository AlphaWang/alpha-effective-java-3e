package com.effectivejava.ch06_enums_annotations;

import java.util.HashSet;
import java.util.Set;

public class Item40_OverrideAnnotation {

    // Can you spot the bug?
    public static class Bigram {
        
        private final char first;
        private final char second;
        
        public Bigram(char first, char second) {
            this.first  = first;
            this.second = second;
        }
        
        public boolean equals(Bigram b) {
            return b.first == first && b.second == second;
        }
        
        public int hashCode() {
            return 31 * first + second;
        }
        
        public static void main(String[] args) {
            Set<Bigram> s = new HashSet<>();
            for (int i = 0; i < 10; i++) {
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    s.add(new Bigram(ch, ch));
                }
            }
            
            //260, NOT 26 
            System.out.println(s.size());
        }
    }
}
