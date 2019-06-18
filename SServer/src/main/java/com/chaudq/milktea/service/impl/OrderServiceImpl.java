package com.chaudq.milktea.service.impl;

import com.chaudq.milktea.db.OrderDatabase;
import com.chaudq.milktea.model.Order1;
import com.chaudq.milktea.model.Statusorder;
import com.chaudq.milktea.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDatabase store;
    @Override
    public boolean addOrder(Order1 order) {
        return store.addOrder(order);
    }

    @Override
    public List<Statusorder> getNotDoneOrderById(String userId) {
        return store.getNotDoneOrderByUserId(userId);
    }

    @Override
    public List<Statusorder> getDoneOrderById(String userId) {
        return store.getDoneOrderByUserId(userId);
    }
}
