package com.chaudq.milktea.controller;

import com.chaudq.milktea.model.Tea;
import com.chaudq.milktea.service.TeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tea")
public class TeaController {
    @Autowired
    private TeaService teaService;

    @GetMapping("/all")
    public List<Tea> getAllTea() {
        return teaService.getAllTea();
    }

    @PostMapping("/get")
    public Tea getById(@RequestBody String id) {
        return teaService.getById(id);
    }
}
