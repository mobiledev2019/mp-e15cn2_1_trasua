package com.chaudq.milktea.service.impl;

import com.chaudq.milktea.db.ToppingDatabase;
import com.chaudq.milktea.model.Topping;
import com.chaudq.milktea.service.ToppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToppingServiceImpl implements ToppingService {
    @Autowired
    private ToppingDatabase toppingDatabase;
    @Override
    public List<Topping> getAll() {
        return toppingDatabase.getAllTopping();
    }

    @Override
    public Topping getById(String id) {
        return toppingDatabase.getById(id);
    }
}
