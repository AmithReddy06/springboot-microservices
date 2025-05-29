package com.bookstore.order_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookstore.order_service.dto.Book;
import com.bookstore.order_service.dto.OrderWithDetails;
import com.bookstore.order_service.dto.User;
import com.bookstore.order_service.model.Order;
import com.bookstore.order_service.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate restTemplate;


    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    // for interservice communication:

    private final String USER_SERVICE_URL = "http://user-service:8081/users/";
    private final String BOOK_SERVICE_URL = "http://book-service:8082/books/";

    public OrderWithDetails getOrderDetails(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) return null;

        User user = restTemplate.getForObject(USER_SERVICE_URL + order.getUserId(), User.class);
        Book book = restTemplate.getForObject(BOOK_SERVICE_URL + order.getBookId(), Book.class);

        return new OrderWithDetails(order, user, book);

}

}