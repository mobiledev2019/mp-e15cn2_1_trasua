package com.chaudq.milktea.service.impl;


import com.chaudq.milktea.db.StoreDatabase;
import com.chaudq.milktea.model.Store;
import com.chaudq.milktea.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired private StoreDatabase storeDatabase;
    @Override
    public List<Store> getAllStore() {
        return storeDatabase.getAllStore();
    }
}
