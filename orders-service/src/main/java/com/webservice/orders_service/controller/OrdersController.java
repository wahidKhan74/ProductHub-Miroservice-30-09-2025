package com.webservice.orders_service.controller;

import com.webservice.orders_service.entity.Order;
import com.webservice.orders_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {

  @Autowired
  private OrderService orderService;

  // GET all orders : Fetch all orders data
  @GetMapping()
  public List<Order> getOrders() {
    return orderService.getAll();
  }

  // Get one order by id
  @GetMapping("/{id}")
  public Order getOrder(@PathVariable String id) {
    return orderService.getById(id);
  }

  // Add new order
  @PostMapping()
  public Order addOrder(@RequestBody Order order) {
    return orderService.save(order);
  }

  // Update a order
  @PutMapping("/{id}")
  public Order updateOrder(@PathVariable String id, @RequestBody Order order) {
    order.setId(id);
    return orderService.save(order);
  }

  // Delete a order
  @DeleteMapping("/{id}")
  public void deleteOrder(@PathVariable String id) {
    orderService.delete(id);
  }

}
