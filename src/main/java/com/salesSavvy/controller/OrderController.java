package com.salesSavvy.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.salesSavvy.entities.Orders;
import com.salesSavvy.entities.Users;
import com.salesSavvy.repositories.OrderRepository;
import com.salesSavvy.repositories.UsersRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrderController {



    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private UsersRepository userRepo;

    
    

    @GetMapping("/getorders/{username}")
    public ResponseEntity<?> getFilteredOrdersByUser(@PathVariable String username) {
    	username = username.trim();
        Users user = userRepo.findByUsername(username);
        if (user == null) return ResponseEntity.badRequest().body("User not found");

        List<Orders> orders = orderRepo.findByUser(user).stream()
                .filter(order -> !"CREATED".equalsIgnoreCase(order.getStatus()))
                .sorted((a, b) -> b.getOrderTime().compareTo(a.getOrderTime()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(orders);
    }

    @PutMapping("/request-return/{orderId}")
    public ResponseEntity<String> requestReturn(@PathVariable String orderId) {
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return ResponseEntity.badRequest().body("Order not found");

        if (!"DELIVERED".equalsIgnoreCase(order.getStatus())) {
            return ResponseEntity.status(400).body("Order not delivered yet");
        }

        order.setStatus("RETURN_REQUESTED");
        orderRepo.save(order);

        return ResponseEntity.ok("Return requested successfully");
    }


    
    @GetMapping("/admin/orders")
    public List<Orders> getAllOrdersForAdmin() {
        return orderRepo.findAll().stream()
                .filter(order -> !"CREATED".equalsIgnoreCase(order.getStatus()))
                .sorted((a, b) -> b.getOrderTime().compareTo(a.getOrderTime()))
                .toList();
    }
    


    @PutMapping("/admin/{orderId}/status")
    public String updateOrderStatus(@PathVariable String orderId, @RequestBody String newStatus) {
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return "Order not found";

        order.setStatus(newStatus.replace("\"", "")); 

        if ("DELIVERED".equalsIgnoreCase(order.getStatus())) {
            order.setDeliveryTime(LocalDateTime.now());
        }

        orderRepo.save(order);
        return "Order status updated";
    }
}
