package com.saj.service;

import com.saj.model.Order;
import com.saj.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getOrderStatus(String orderId) {
        return findOrder(orderId);
    }

    public Order trackOrder(String orderId) {
        Order order = findOrder(orderId);
        if ("CANCELLED".equalsIgnoreCase(order.getStatus())) {
            throw new RuntimeException("Cannot track a cancelled order.");
        }
        return order;
    }

    public LocalDate getDeliveryEstimate(String orderId) {
        return findOrder(orderId).getExpectedDelivery();
    }

    public List<Order> listOrdersByCustomer(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name is required.");
        }
        return orderRepository.findByCustomerNameIgnoreCase(customerName.trim());
    }

    public String getPaymentStatus(String orderId) {
        return findOrder(orderId).getPaymentStatus();
    }

    public Order requestRefund(String orderId) {
        Order order = findOrder(orderId);
        String status = order.getStatus();
        String paymentStatus = order.getPaymentStatus();

        if ("CANCELLED".equalsIgnoreCase(status)
                || "DELIVERED".equalsIgnoreCase(status)) {
            throw new RuntimeException("This order is not eligible for a refund.");
        }
        if (paymentStatus == null
                || "REFUND_REQUESTED".equalsIgnoreCase(paymentStatus)
                || "REFUND_COMPLETED".equalsIgnoreCase(paymentStatus)) {
            throw new RuntimeException("A refund cannot be requested for this order.");
        }

        order.setPaymentStatus("REFUND_REQUESTED");
        return orderRepository.save(order);
    }

    public Order updateExpectedDelivery(String orderId, LocalDate expectedDelivery) {
        if (expectedDelivery == null) {
            throw new IllegalArgumentException("Expected delivery date is required.");
        }

        Order order = findOrder(orderId);
        String status = order.getStatus();
        if ("CANCELLED".equalsIgnoreCase(status)
                || "DELIVERED".equalsIgnoreCase(status)
                || "SHIPPED".equalsIgnoreCase(status)) {
            throw new RuntimeException("Cannot update delivery for an order with status: " + status);
        }

        order.setExpectedDelivery(expectedDelivery);
        return orderRepository.save(order);
    }

    public Order cancelOrder(String orderId) {
        Order order = findOrder(orderId);
        String status = order.getStatus();
        if ("SHIPPED".equalsIgnoreCase(status)
                || "DELIVERED".equalsIgnoreCase(status)
                || "CANCELLED".equalsIgnoreCase(status)) {
            throw new RuntimeException("Cannot cancel an order with status: " + status);
        }
        order.setStatus("CANCELLED");
        order.setExpectedDelivery(null);
        return orderRepository.save(order);
    }

    private Order findOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("Order ID is required.");
        }
        return orderRepository.findByOrderId(orderId.trim())
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
    }
}