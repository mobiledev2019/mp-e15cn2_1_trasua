package com.chaudq.milktea.controller;

import com.chaudq.milktea.model.Topping;
import com.chaudq.milktea.service.ToppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topping")
public class ToppingController {
    @Autowired
    private ToppingService toppingService;

    @GetMapping("/all")
    public List<Topping> getAllTopping() {
        return toppingService.getAll();
    }

    @PostMapping("/get")
    public Topping getById(@RequestBody String id) {
        return toppingService.getById(id);

    }
}
