package com.grocerymanagement.servlet;

import com.grocerymanagement.config.FileInitializationUtil;
import com.grocerymanagement.dao.OrderDAO;
import com.grocerymanagement.dao.PaymentDAO;
import com.grocerymanagement.model.Order;
import com.grocerymanagement.model.Payment;
import com.grocerymanagement.model.User;
import com.grocerymanagement.dto.PaymentDetails;
import com.grocerymanagement.model.Cart;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import com.grocerymanagement.dao.CartDAO;

@WebServlet("/payment/*")
public class PaymentServlet extends HttpServlet {
    private PaymentDAO paymentDAO;
    private OrderDAO orderDAO;
    private CartDAO cartDAO;

    @Override
    public void init() throws ServletException {
        FileInitializationUtil fileInitUtil = new FileInitializationUtil(getServletContext());
        orderDAO = new OrderDAO(fileInitUtil);
        paymentDAO = new PaymentDAO(fileInitUtil, orderDAO);
        cartDAO = new CartDAO(fileInitUtil);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo == null) {
            pathInfo = "/checkout";
        }

        switch(pathInfo) {
            case "/checkout":
                showCheckoutPage(request, response);
                break;
            case "/success":
                showPaymentSuccess(request, response);
                break;
            case "/saved-cards":
                showSavedCards(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/cart/view");
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        if(pathInfo == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        switch(pathInfo) {
            case "/process":
                processPayment(request, response);
                break;
            case "/save-card":
                savePaymentCard(request, response);
                break;
            case "/delete-card":
                deletePaymentCard(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }
    }





}
