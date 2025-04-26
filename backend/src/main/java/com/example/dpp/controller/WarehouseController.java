package com.example.dpp.controller;

import com.example.dpp.model.warehouses.Warehouse;
import com.example.dpp.repository.WarehouseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@CrossOrigin(origins = "http://localhost:3000")
public class WarehouseController {
    public static final ResponseStatusException WAREHOUSE_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "Warehouse not found");
    private final WarehouseRepository repository;

    public WarehouseController(WarehouseRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public List<Warehouse> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Warehouse findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(()-> WAREHOUSE_NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void create(@RequestBody Warehouse warehouse) {
        repository.save(warehouse);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Warehouse warehouse, @PathVariable Integer id) {
        if(!repository.existsById(id)){
            throw WAREHOUSE_NOT_FOUND;
        }

        repository.deleteById(id);
        repository.save(warehouse);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
