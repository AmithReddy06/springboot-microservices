package com.bookstore.order_service.dto;


import com.bookstore.order_service.model.Order;


public class OrderWithDetails {
    private Order order;
    private User user;
    private Book book;

    public OrderWithDetails(Order order, User user, Book book) {
        this.order = order;
        this.user = user;
        this.book = book;
    }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
