package com.chaudq.milktea.service;

import com.chaudq.milktea.model.Topping;

import java.util.List;

public interface ToppingService {
    List<Topping> getAll();
    Topping getById(String id);
}
