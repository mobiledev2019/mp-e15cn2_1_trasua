package com.chaudq.milktea.service;

import com.chaudq.milktea.model.Order1;
import com.chaudq.milktea.model.Statusorder;

import java.util.List;

public interface OrderService {
    boolean addOrder(Order1 order);
    List<Statusorder> getNotDoneOrderById(String userId);
    List<Statusorder> getDoneOrderById(String userId);
}
