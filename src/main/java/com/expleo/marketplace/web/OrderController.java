package com.expleo.marketplace.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.expleo.marketplace.exception.RecordNotFoundException;
import com.expleo.marketplace.model.Matches;
import com.expleo.marketplace.model.OrderEntity;
import com.expleo.marketplace.service.OrderService;

@Controller
@RequestMapping("/")
public class OrderController {

	@Autowired
	OrderService service;

	@GetMapping
	public String getAllOrders(Model model) {
		List<OrderEntity> list = service.getAllOrders();

		model.addAttribute("orders", list);
		return "list-orders";
	}

	@RequestMapping(path = { "/add" })
	public String add(Model model) throws RecordNotFoundException {
		model.addAttribute("order", new OrderEntity());
		return "market-place";
	}

	@PostMapping(path = "/createOrder")
	public String createOrUpdateOrder(OrderEntity order) {
		service.createOrUpdateOrder(order);
		return "redirect:/";
	}

	@RequestMapping(path = { "/match" })
	public String matchOrders(Model model) throws RecordNotFoundException {
		List<Matches> list = service.matchAllOrders();

		model.addAttribute("matches", list);
		return "list-matches";
	}
}
