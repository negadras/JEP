package com.jep;

import com.jep.ExhaustiveSwitchPatterns.Transaction;

import static com.jep.ExhaustiveSwitchPatterns.calculateBalanceImpact;
import static com.jep.ExhaustiveSwitchPatterns.processTransaction;

/**
 * Demonstrates the use of Record Patterns as introduced in JEP 440.
 *
 * <p>JEP 440 finalizes record patterns, enabling pattern matching for records to
 * decompose record components directly. This enhancement provides a declarative
 * and concise way of navigating and processing data within records.</p>
 *
 * <p>This class includes examples to illustrate:</p>
 * <ul>
 *   <li>Basic pattern matching with records</li>
 *   <li>Nested record patterns for handling complex structures</li>
 *   <li>Exhaustive switches with record patterns</li>
 * </ul>
 *
 * @since Java 21
 */
public class JEP440 {

    public static void main( String[] args ) {
        System.out.println("Record Patterns Example");

        // Example 1: Pattern matching with records
        patternMatchingWithRecords();

        // Example 2: Nested record patterns
        nestedRecordPatterns();

        // Example 3: Exhaustive switches with record patterns
        exhaustiveSwitchPatterns();
    }

    private static void patternMatchingWithRecords() {
        record Point(int x, int y) { }

        Object object = new Point(10, 20);

        if (object instanceof Point point) {
            System.out.println("Point x: " + point.x() + ", y: " + point.y());
        }
    }

    private static void nestedRecordPatterns() {
        record Coordinate(double latitude, double longitude) { }
        record Address(String city, String country, Coordinate coordinates) { }
        record Person(String name, Address address) { }

        Person person = new Person("Max",
                new Address("Payne", "Kitchen", new Coordinate(37.7749, 122.4194))
        );

        if (person instanceof Person(String name, Address(String city, String country,
                                                          Coordinate(double lat, double lon)))) {
            System.out.println("Person: " + name);
            System.out.println("Address: " + city + ", " + country);
            System.out.println("Coordinates: " + lat + ", " + lon);
        }
    }

    private static void exhaustiveSwitchPatterns() {
        Transaction payment = new ExhaustiveSwitchPatterns.PaymentTransaction(
                new ExhaustiveSwitchPatterns.AccountInfo("ACC123", "USD"),
                99.99,
                "MERCHANT456"
        );

        Transaction refund = new ExhaustiveSwitchPatterns.RefundTransaction(
                new ExhaustiveSwitchPatterns.AccountInfo("ACC123", "USD"),
                25.00,
                "TXN789",
                "Item returned - wrong size"
        );

        Transaction transfer = new ExhaustiveSwitchPatterns.TransferTransaction(
                new ExhaustiveSwitchPatterns.AccountInfo("ACC123", "USD"),
                new ExhaustiveSwitchPatterns.AccountInfo("ACC456", "USD"),
                500.00,
                "Monthly rent payment"
        );

        // Process each transaction type
        System.out.println("\n" + processTransaction(payment));
        System.out.println("\n" + processTransaction(refund));
        System.out.println("\n" + processTransaction(transfer));

        // Calculate balance impact for each transaction
        System.out.println("\nBalance impact for payment:");
        System.out.printf("Payment impact: %.2f%n", calculateBalanceImpact(payment));
        System.out.printf("Refund impact: %.2f%n", calculateBalanceImpact(refund));
        System.out.printf("Transfer impact: %.2f%n", calculateBalanceImpact(transfer));
    }
}
