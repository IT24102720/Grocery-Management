package com.grocerymanagement.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionDAO {

    private String transactionFilePath;

    public TransactionDAO(FileInitializationUtil fileInitUtil) {
        this.transactionFilePath = fileInitUtil.getDataFilePath("transactions.txt");
    }

    public boolean createTransaction(Transaction transaction) {
        if (!validateTransaction(transaction)) {
            return false;
        }

        FileHandlerUtil.writeToFile(transactionFilePath, transaction.toFileString(), true);
        return true;
    }

    public Optional<Transaction> getTransactionById(String transactionId) {
        return FileHandlerUtil.readFromFile(transactionFilePath).stream()
                .map(Transaction::fromFileString)
                .filter(transaction -> transaction.getTransactionId().equals(transactionId))
                .findFirst();
    }

    public Optional<Transaction> getTransactionByOrderId(String orderId) {
        return FileHandlerUtil.readFromFile(transactionFilePath).stream()
                .map(Transaction::fromFileString)
                .filter(transaction -> transaction.getOrderId().equals(orderId))
                .findFirst();
    }

    public List<Transaction> getTransactionsByStatus(Transaction.TransactionStatus status) {
        return FileHandlerUtil.readFromFile(transactionFilePath).stream()
                .map(Transaction::fromFileString)
                .filter(transaction -> transaction.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Transaction> getAllTransactions() {
        return FileHandlerUtil.readFromFile(transactionFilePath).stream()
                .map(Transaction::fromFileString)
                .collect(Collectors.toList());

}
