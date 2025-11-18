package com.salesSavvy.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.razorpay.Order;
import com.razorpay.RazorpayException;
import com.salesSavvy.DTO.PaymentRequest;
import com.salesSavvy.DTO.PaymentVerifyRequest;
import com.salesSavvy.entities.Orders;
import com.salesSavvy.entities.Users;
import com.salesSavvy.repositories.OrderRepository;
import com.salesSavvy.servies.CartService;
import com.salesSavvy.servies.PaymentService;
import com.salesSavvy.servies.UsersService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired private PaymentService payService;
    @Autowired private UsersService usersService;
    @Autowired private CartService cartService;
    @Autowired private OrderRepository orderRepo;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PaymentRequest req) {
        try {
            Users user = usersService.getUser(req.getUsername());
            if (user == null) return ResponseEntity.badRequest().body("User not found");

            Order rzpOrder = payService.createRzpOrder(req.getAmount());

            Orders order = new Orders();
            order.setId(rzpOrder.get("id"));
            order.setAmount(req.getAmount());
            order.setCurrency("INR");
            order.setStatus("CREATED");
            order.setUser(user);
            order.setItems(cartService.cloneItems(req.getUsername()));
            orderRepo.save(order);

            return ResponseEntity.ok(Map.of(
                "key", payService.getKeyId(),
                "orderId", rzpOrder.get("id"),
                "amount", req.getAmount()
            ));
        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating Razorpay order");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody PaymentVerifyRequest req) {
        try {
            boolean verified = payService.verifySignature(req.getOrderId(), req.getPaymentId(), req.getSignature());
            if (!verified) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature mismatch");
            }

            Orders order = orderRepo.findById(req.getOrderId()).orElse(null);
            if (order == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");

            order.setStatus("PAID");
            order.setPaymentId(req.getPaymentId());
            orderRepo.save(order);

            cartService.clearCart(req.getUsername());

            return ResponseEntity.ok(Map.of("orderId", order.getId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Payment verification failed");
        }
    }

    @GetMapping("/order/{orderId}")
    public Optional<Object> getOrder(@PathVariable String orderId) {
        return orderRepo.findById(orderId)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/order/summary/{orderId}")
    public ResponseEntity<?> getOrderSummary(@PathVariable String orderId) {
        Orders order = orderRepo.findById(orderId).orElse(null);
        if (order == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");

        double totalRupees = order.getAmount() / 100;

        return ResponseEntity.ok(Map.of(
            "orderId", order.getId(),
            "status", order.getStatus(),
            "total", totalRupees,
            "items", order.getItems().stream().map(ci -> Map.of(
                "name", ci.getProduct().getName(),
                "qty", ci.getQuantity(),
                "price", ci.getProduct().getPrice()
            )).toList()
        ));
    }
}
