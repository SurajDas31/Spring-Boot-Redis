package com.virus.controller;

import com.virus.dao.ProductDAO;
import com.virus.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class _Controller {

    private ProductDAO productDAO;

    public _Controller(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody Product product){
        productDAO.save(product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/all")
    public ResponseEntity getAll(){
        return ResponseEntity.ok(productDAO.getAll());
    }
}
