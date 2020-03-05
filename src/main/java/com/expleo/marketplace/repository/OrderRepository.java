package com.expleo.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expleo.marketplace.model.OrderEntity;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long>{

}
