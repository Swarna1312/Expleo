package com.expleo.marketplace.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expleo.marketplace.model.Matches;
import com.expleo.marketplace.model.OrderEntity;
import com.expleo.marketplace.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository repository;

	public List<OrderEntity> getAllOrders() {
		List<OrderEntity> result = (List<OrderEntity>) repository.findAll();

		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<OrderEntity>();
		}
	}

	public OrderEntity createOrUpdateOrder(OrderEntity entity) {
		if (entity.getId() == null) {
			entity = repository.save(entity);

			return entity;
		} else {
			Optional<OrderEntity> order = repository.findById(entity.getId());

			if (order.isPresent()) {
				OrderEntity newEntity = order.get();
				newEntity.setOrdererName(entity.getOrdererName());
				newEntity.setOrderType(entity.getOrderType());
				newEntity.setOrderPrice(entity.getOrderPrice());
				newEntity = repository.save(newEntity);

				return newEntity;
			} else {
				entity = repository.save(entity);

				return entity;
			}
		}
	}

	public List<Matches> matchAllOrders() {
		List<Matches> result = findMatches();
		if (result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Matches>();
		}
	}

	private List<Matches> findMatches() {
		List<OrderEntity> orders = repository.findAll();
		Comparator<OrderEntity> priceComparator = (p1, p2) -> p1.getOrderPrice().compareTo(p2.getOrderPrice());
		List<OrderEntity> buyOrders = orders.stream().filter(o -> o.getOrderType().equals("Buy"))
				.sorted(priceComparator.reversed()).collect(Collectors.toList());
		List<OrderEntity> sellOrders = orders.stream().filter(o -> o.getOrderType().equals("Sell"))
				.sorted(priceComparator).collect(Collectors.toList());
		List<Matches> result = new ArrayList<Matches>();
		List<OrderEntity> unmatchedOrders = new ArrayList<OrderEntity>();
		Matches match = null;
		for (OrderEntity buyOrder : buyOrders) {
			OrderEntity bestMatch = new OrderEntity();
			if (sellOrders.size() > 1) {
				bestMatch = sellOrders.stream().filter(so -> so.getOrderPrice() <= buyOrder.getOrderPrice())
						.reduce((first, second) -> second).get();
			} else if (sellOrders.size() == 1) {
				if (sellOrders.get(0).getOrderPrice() <= buyOrder.getOrderPrice()) {
					bestMatch = sellOrders.get(0);
				} else {
					unmatchedOrders.add(sellOrders.get(0));
					sellOrders.clear();
					unmatchedOrders.add(buyOrder);
					continue;
				}
			} else {
				unmatchedOrders.add(buyOrder);
				continue;
			}
			match = new Matches();
			match.setBuyerName(buyOrder.getOrdererName());
			match.setBuyerPrice(buyOrder.getOrderPrice());
			match.setSellerName(bestMatch.getOrdererName());
			match.setSellerPrice(bestMatch.getOrderPrice());
			sellOrders.remove(bestMatch);
			result.add(match);
		}
		if(sellOrders != null && !sellOrders.isEmpty()) {
			unmatchedOrders.addAll(sellOrders);
		}
		if(unmatchedOrders != null && !unmatchedOrders.isEmpty()) {
			addUnmatchedOrdersToList(unmatchedOrders, result);
		}
		return result;
	}
	
	public void addUnmatchedOrdersToList(List<OrderEntity> unmatchedOrders, List<Matches> result) {
		Matches match = null;
		for(OrderEntity entity : unmatchedOrders) {
			match = new Matches();
			if(entity.getOrderType().equalsIgnoreCase("Buy")) {
				match.setBuyerName(entity.getOrdererName());
				match.setBuyerPrice(entity.getOrderPrice());
				match.setSellerName("No matching seller");
				match.setSellerPrice(0);
			} else {
				match.setBuyerName("No matching buyer");
				match.setBuyerPrice(0);
				match.setSellerName(entity.getOrdererName());
				match.setSellerPrice(entity.getOrderPrice());
			}
			result.add(match);
		}
	
	}
}
