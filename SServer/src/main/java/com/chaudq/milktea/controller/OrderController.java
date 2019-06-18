package com.chaudq.milktea.controller;

import com.chaudq.milktea.model.Order1;
import com.chaudq.milktea.model.Statusorder;
import com.chaudq.milktea.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/add")
    public boolean addOrder(@RequestBody Order1 order) {
        return orderService.addOrder(order);
    }
    @PostMapping("/getNotDone")
    public List<Statusorder> getNotDoneOrderById(@RequestBody String userId){
        return orderService.getNotDoneOrderById(userId);
    }
    @PostMapping("/getDone")
    public List<Statusorder> getDoneOrderById(@RequestBody String userId){
        return orderService.getDoneOrderById(userId);
    }


}
