package com.jep;

/**
 * Demonstrates exhaustive switches with record patterns using a practical example
 * of processing different types of financial transactions.
 */
public class ExhaustiveSwitchPatterns {

    // JEP 409: Sealed Classes [https://openjdk.org/jeps/409]
    sealed interface Transaction permits 
        PaymentTransaction, 
        RefundTransaction, 
        TransferTransaction {}

    // Record for storing account information
    record AccountInfo(String accountId, String currency) {}

    // Different transaction types as records
    record PaymentTransaction(AccountInfo account, double amount, String merchantId) implements Transaction {}

    record RefundTransaction(AccountInfo account, double amount, String originalTransactionId, String reason
    ) implements Transaction {}

    record TransferTransaction(AccountInfo sourceAccount, AccountInfo destinationAccount,
                               double amount, String description
    ) implements Transaction {}

    /**
     * Processes a transaction and returns a detailed description of the action taken
     */
    public static String processTransaction(Transaction transaction) {
        // Using an exhaustive switch with record patterns to handle all transaction types
        return switch (transaction) {
            case PaymentTransaction(
                AccountInfo(String accId, String currency),
                double amount,
                String merchantId
            ) -> {
                String formattedAmount = String.format("%.2f %s", amount, currency);
                yield String.format(
                    "PAYMENT: Account %s paid %s to merchant %s",
                    accId,
                    formattedAmount,
                    merchantId
                );
            }
            
            case RefundTransaction(AccountInfo(String accId, String currency), double amount,
                                   String transId, String reason) -> {
                String formattedAmount = String.format("%.2f %s", amount, currency);
                yield String.format(
                    "REFUND: Account %s received refund of %s for transaction %s\nReason: %s",
                    accId,
                    formattedAmount,
                    transId,
                    reason
                );
            }
            
            case TransferTransaction(AccountInfo(String srcAccId, String srcCurrency),
                                     AccountInfo(String destAccId, String destCurrency),
                                     double amount, String description) -> {
                String formattedAmount = String.format("%.2f %s", amount, srcCurrency);
                yield String.format(
                    "TRANSFER: %s transferred from account %s to account %s\nPurpose: %s",
                    formattedAmount,
                    srcAccId,
                    destAccId,
                    description
                );
            }
        };
    }

    /**
     * Calculates the impact on account balance based on transaction type
     */
    public static double calculateBalanceImpact(Transaction transaction) {
        return switch (transaction) {
            case PaymentTransaction(AccountInfo acc, double amount, var merchantId) -> 
                -amount; // Payments decrease balance
            
            case RefundTransaction(AccountInfo acc, double amount, var transId, var reason) -> 
                amount;  // Refunds increase balance
            
            case TransferTransaction(
                AccountInfo source,
                AccountInfo destination,
                double amount,
                var description
            ) -> 
                -amount; // Transfers decrease balance
        };
    }
}