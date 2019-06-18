package com.chaudq.milktea.controller;

import com.chaudq.milktea.model.Store;
import com.chaudq.milktea.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/all")
    public List<Store> getAllStore() {
       return storeService.getAllStore();

    }
}
