package com.threads.concurrency.Controllers;

import com.threads.concurrency.Entities.*;
import com.threads.concurrency.Exceptions.ChefNotFoundException;
import com.threads.concurrency.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;  // Correct import
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chefs")
public class ChefController {

    @Autowired
    private ChefService chefService;

    @PostMapping
    public ResponseEntity<String> createChef(@RequestBody Chef chef) {
        Chef savedChef = chefService.saveChef(chef);
        return new ResponseEntity<>("Chef created with ID: " + savedChef.getId() + " - Multithreading done",
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<Chef>> getAllChefs(Pageable pageable) {
        Page<Chef> chefs = chefService.getAllChefs(pageable);
        return new ResponseEntity<>(chefs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getChefById(@PathVariable Long id) {
        Chef chef = chefService.getChefById(id);
        if (chef == null) {
            throw new ChefNotFoundException("Chef not found with ID: " + id);
        }
        return new ResponseEntity<>("Chef found: " + chef.getName() + " - Multithreading done", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteChef(@PathVariable Long id) {
        chefService.deleteChefById(id);
        return new ResponseEntity<>("Chef deleted - Multithreading done", HttpStatus.OK);
    }
}
