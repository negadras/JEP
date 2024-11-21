package com.jep;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.SequencedMap;
import java.util.SequencedSet;

/**
 * Demonstrates the use of Sequenced Collections as introduced in JEP 431.
 *
 * <p>JEP 431 introduces new interfaces — {@code SequencedCollection}, {@code SequencedSet},
 * and {@code SequencedMap} — to provide a consistent encounter order for collections.
 * These interfaces support operations for accessing, modifying, and iterating elements
 * in both forward and reverse order. They allow operations such as retrieving the first
 * and last elements, adding elements at both ends, and reversing the collection view.</p>
 *
 * <p>This example showcases:
 * <ul>
 *     <li>Using {@code LinkedHashSet} as a {@code SequencedSet} to manage ordered elements.</li>
 *     <li>Using {@code LinkedHashMap} as a {@code SequencedMap} to handle ordered key-value pairs.</li>
 *     <li>Operations to access the first and last elements, reverse the collection order, and move elements
 *         to specific positions.</li>
 * </ul>
 * </p>
 *
 * <p>"Life can only be understood backwards; but it must be lived forwards." — Kierkegaard</p>
 *
 * @since Java 21
 */
public class JEP431 {

    public static void main( String[] args ) {

        // Example with SequencedSet using LinkedHashSet
        SequencedSet<String> orderedSet = new LinkedHashSet<>();
        orderedSet.addFirst("one");
        orderedSet.addLast("two");
        orderedSet.addLast("three");

        System.out.println("Original set: " + orderedSet);
        System.out.println("First element: " + orderedSet.getFirst());
        System.out.println("Last element: " + orderedSet.getLast());

        // Reverse the order of the set
        System.out.println("Reversed set: " + orderedSet.reversed());

        // Move an existing element to the first position
        orderedSet.addFirst("two");
        System.out.println("Set with 'two' moved to the first position: " + orderedSet);

        // Example with SequencedSet using LinkedHashMap
        SequencedMap<String, Integer> orderedMap = new LinkedHashMap<>();

        orderedMap.putFirst("one", 1);
        orderedMap.putLast("two", 2);
        orderedMap.putLast("three", 3);

        System.out.println("Original map: " + orderedMap);
        System.out.println("First entry: " + orderedMap.firstEntry());
        System.out.println("Last entry: " + orderedMap.lastEntry());

        // Reverse the order of the map
        System.out.println("Reversed map: " + orderedMap.reversed());

        // Move an existing entry to the first position
        orderedMap.putFirst("two", 2);
        System.out.println("Map with 'two' moved to the first position: " + orderedMap);
    }
}
