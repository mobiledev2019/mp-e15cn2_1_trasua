package com.chaudq.milktea.service;

import com.chaudq.milktea.model.Tea;

import java.util.List;

public interface TeaService {
    public List<Tea> getAllTea();
    public Tea getById(String id);

}
