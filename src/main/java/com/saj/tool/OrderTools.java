package com.saj.tool;

import com.saj.model.Order;
import com.saj.service.OrderService;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderTools {

    private final OrderService orderService;

    public OrderTools(OrderService orderService) {
        this.orderService = orderService;
    }

    @Tool("Get the current status and delivery information for an order")
    public Order getOrderStatus(String orderId) {
        System.out.println("TOOL CALLED -> getOrderStatus(" + orderId + ")");
        return orderService.getOrderStatus(orderId);
    }

    @Tool("Cancel an order")
    public Order cancelOrder(String orderId) {
        System.out.println("TOOL CALLED -> cancelOrder(" + orderId + ")");
        return orderService.cancelOrder(orderId);
    }

    @Tool("Track the current status and location of an order")
    public Order trackOrder(String orderId) {
        System.out.println("TOOL CALLED -> trackOrder(" + orderId + ")");
        return orderService.trackOrder(orderId);
    }

    @Tool("Get the estimated delivery date for an order")
    public LocalDate getDeliveryEstimate(String orderId) {
        System.out.println("TOOL CALLED -> getDeliveryEstimate(" + orderId + ")");
        return orderService.getDeliveryEstimate(orderId);
    }

    @Tool("List all orders belonging to a customer")
    public List<Order> listOrdersByCustomer(String customerName) {
        System.out.println("TOOL CALLED -> listOrdersByCustomer(" + customerName + ")");
        return orderService.listOrdersByCustomer(customerName);
    }

    @Tool("Get the payment status for an order")
    public String getPaymentStatus(String orderId) {
        System.out.println("TOOL CALLED -> getPaymentStatus(" + orderId + ")");
        return orderService.getPaymentStatus(orderId);
    }

    @Tool("Request a refund for an eligible active order")
    public Order requestRefund(String orderId) {
        System.out.println("TOOL CALLED -> requestRefund(" + orderId + ")");
        return orderService.requestRefund(orderId);
    }

    @Tool("Update the expected delivery date for an eligible order")
    public Order updateExpectedDelivery(String orderId, LocalDate expectedDelivery) {
        System.out.println("TOOL CALLED -> updateExpectedDelivery("
                + orderId + ", " + expectedDelivery + ")");
        return orderService.updateExpectedDelivery(orderId, expectedDelivery);
    }
}
