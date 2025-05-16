package com.grocerymanagement.dao;

import java.util.Optional;

public class PaymentDAO {
    private String paymentFilePath;
    private String savedCardsFilePath;
    private OrderDAO orderDAO;

    public PaymentDAO(FileInitializationUtil fileInitUtil, OrderDAO orderDAO) {
        this.paymentFilePath = fileInitUtil.getDataFilePath("payments.txt");
        this.savedCardsFilePath = fileInitUtil.getDataFilePath("saved_cards.txt");
        this.orderDAO = orderDAO;
    }

    public boolean createPayment(Payment payment) {
        if (payment == null || payment.getOrder() == null) {
            return false;
        }

        FileHandlerUtil.writeToFile(paymentFilePath, payment.toFileString(), true);
        return true;
    }

    public Optional<Payment> getPaymentByOrderId(String orderId) {
        return FileHandlerUtil.readFromFile(paymentFilePath).stream()
                .filter(line -> line.split("\\|")[1].equals(orderId))
                .map(line -> {
                    Optional<Order> orderOptional = orderDAO.getOrderById(orderId);
                    return orderOptional.map(order -> Payment.fromFileString(line, order));
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
}
