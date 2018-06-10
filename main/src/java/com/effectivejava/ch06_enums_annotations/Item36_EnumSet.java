package com.effectivejava.ch06_enums_annotations;

import java.util.EnumSet;
import java.util.Set;

import static com.effectivejava.ch06_enums_annotations.Item36_EnumSet.EnumSetText.Style.BOLD;
import static com.effectivejava.ch06_enums_annotations.Item36_EnumSet.EnumSetText.Style.ITALIC;

public class Item36_EnumSet {

    public static void main(String[] args) {
        BitFieldText text = new BitFieldText();
        text.applyStyles(BitFieldText.STYLE_BOLD | BitFieldText.STYLE_ITALIC);

        EnumSetText enumSetText = new EnumSetText();
        enumSetText.applyStyles(EnumSet.of(BOLD, ITALIC));
    }

    // Bit field enumeration constants - OBSOLETE!
    public static class BitFieldText {
        public static final int STYLE_BOLD = 1 << 0; // 1
        public static final int STYLE_ITALIC = 1 << 1;  // 2
        public static final int STYLE_UNDERLINE = 1 << 2;  // 4
        public static final int STYLE_STRIKETHROUGH = 1 << 3;  // 8

        // Parameter is bitwise OR of zero or more STYLE_ constants
        public void applyStyles(int styles) {
            System.out.print(styles);
        }
    }


    // EnumSet - a modern replacement for bit fields
    public static class EnumSetText {

        public enum Style { BOLD, ITALIC, UNDERLINE, STRIKETHROUGH }

        // Any Set could be passed in, but EnumSet is clearly best
        public void applyStyles(Set<Style> styles) {
            System.out.print(styles);
        }
    }


}
