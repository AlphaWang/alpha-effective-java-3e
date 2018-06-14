package com.effectivejava.ch06_enums_annotations;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

public class Item37_EnumMap {

    static class Plant {
        enum LifeCycle {
            ANNUAL, PERENNIAL, BIENNIAL
        }

        final String name;
        final LifeCycle lifeCycle;

        Plant(String name, LifeCycle lifeCycle) {
            this.name = name;
            this.lifeCycle = lifeCycle;
        }

        @Override public String toString() {
            return name;
        }
    }

    /**
     * 1. Using ordinal() to index into an array - DON'T DO THIS!
     * 
     * Problems:
     *
     * 1) Class cast: arrays are not compatible with generics.
     * 2) when you access an array that is indexed by an enum’s ordinal, 
     *    it is your responsibility to use the correct int value.
     */
    private static void ordinalIndexing(List<Plant> garden) {
        Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            plantsByLifeCycle[i] = new HashSet<>();
        }

        for (Plant p : garden) {
            plantsByLifeCycle[p.lifeCycle.ordinal()].add(p);
        }

        // ANNUAL: [Grass]
        // PERENNIAL: [Tree]
        // BIENNIAL: []
        for (int i = 0; i < plantsByLifeCycle.length; i++) {
            System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
        }
    }

    /**
     * 2. Using an EnumMap to associate data with an enum
     */
    private static void enumMap(List<Plant> garden) {
        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);
        for (Plant.LifeCycle lc : Plant.LifeCycle.values()) {
            plantsByLifeCycle.put(lc, new HashSet<>());
        }

        for (Plant p : garden) {
            plantsByLifeCycle.get(p.lifeCycle).add(p);
        }

        // {ANNUAL=[Grass], PERENNIAL=[Tree], BIENNIAL=[]}
        System.out.println(plantsByLifeCycle);
    }

    /**
     * 3. Naive stream-based approach - unlikely to produce an EnumMap!
     */
    private static void streamMap(List<Plant> garden) {
        // The EnumMap version always makes a nested map for each plant lifecycle,
        // while the stream-based versions only make a nested map if the garden contains one or more plants with that lifecycle.

        // {ANNUAL=[Grass], PERENNIAL=[Tree]}
        System.out.println(garden.stream().collect(Collectors.groupingBy(p -> p.lifeCycle)));
    }

    /**
     *  4. Using a stream and an EnumMap to associate data with an enum
     */
    private static void streamEnumMap(List<Plant> garden) {
        // {ANNUAL=[Grass], PERENNIAL=[Tree]}
        System.out.println(garden.stream().collect(Collectors.groupingBy(
            p -> p.lifeCycle,
            () -> new EnumMap<>(Plant.LifeCycle.class),
            toSet())));
    }

    public static void main(String[] args) {
        List<Plant> garden = new ArrayList<>();
        garden.add(new Plant("Tree", Plant.LifeCycle.PERENNIAL));
        garden.add(new Plant("Grass", Plant.LifeCycle.ANNUAL));

        ordinalIndexing(garden);
        enumMap(garden);
        streamMap(garden);
        streamEnumMap(garden);

    }
}
