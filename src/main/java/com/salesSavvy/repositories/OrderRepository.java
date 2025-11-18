package com.salesSavvy.repositories;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salesSavvy.entities.Orders;
import com.salesSavvy.entities.Users;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String>{

	List<Orders> findByUser(Users user);
	List<Orders> findByUserAndStatus(Users user, String status);

}
