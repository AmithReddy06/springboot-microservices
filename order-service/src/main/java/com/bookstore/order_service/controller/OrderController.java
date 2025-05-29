package com.bookstore.order_service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bookstore.order_service.dto.Book;
import com.bookstore.order_service.dto.OrderWithDetails;
import com.bookstore.order_service.dto.User;
import com.bookstore.order_service.model.Order;
import com.bookstore.order_service.repository.OrderRepository;
import com.bookstore.order_service.service.OrderService;


@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;


    @PostMapping
    public Order placeOrder(@RequestBody Order order) {
        return orderService.placeOrder(order);
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }



    @GetMapping("/{id}/details")
    public ResponseEntity<OrderWithDetails> getOrderDetails(@PathVariable Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOpt.get();

        User user = restTemplate.getForObject(
            "http://user-service:8081/users/" + order.getUserId(), User.class);

        Book book = restTemplate.getForObject(
            "http://book-service:8082/books/" + order.getBookId(), Book.class);

        OrderWithDetails full = new OrderWithDetails(order, user, book);
        return ResponseEntity.ok(full);

    }

}
