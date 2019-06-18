package com.chaudq.milktea.service.impl;

import com.chaudq.milktea.db.TeaDatabase;
import com.chaudq.milktea.model.Tea;
import com.chaudq.milktea.service.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeaServiceImpl implements TeaService {
    @Autowired private TeaDatabase teaDatabase;
    @Override
    public List<Tea> getAllTea() {
        return teaDatabase.getAllTea();
    }

    @Override
    public Tea getById(String id) {
        return teaDatabase.getById(id);
    }
}
