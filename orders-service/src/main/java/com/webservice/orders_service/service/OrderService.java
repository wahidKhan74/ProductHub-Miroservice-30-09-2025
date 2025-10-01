package com.webservice.orders_service.service;

import com.webservice.orders_service.entity.Order;
import com.webservice.orders_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

  @Autowired
  private OrderRepository orderRepository;

  public List<Order> getAll() {
    return orderRepository.findAll();
  }

  public Order save(Order order) {
    return  orderRepository.save(order);
  }

  public void delete(String id) {
    orderRepository.deleteById(id);
  }

  public Order getById(String id) {
    return orderRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Order not found: " + id));
  }
}
