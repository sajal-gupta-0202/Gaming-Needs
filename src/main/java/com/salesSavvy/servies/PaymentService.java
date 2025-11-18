package com.salesSavvy.servies;

import com.razorpay.Order;
import com.razorpay.RazorpayException;

public interface PaymentService {

	 Order createRzpOrder(int amountPaise) throws RazorpayException;
	 boolean verifySignature(String orderId, String paymentId, String signature);
	 String getKeyId();

}
