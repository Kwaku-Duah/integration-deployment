package com.threads.concurrency.Services;

import com.threads.concurrency.Entities.Chef;
import com.threads.concurrency.Repositories.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ChefService {

    @Autowired
    private ChefRepository chefRepository;

    // Save and cache chef in Caffeine and Redis
    @CachePut(value = "chefs", key = "#chef.id")
    public Chef saveChef(Chef chef) {
        return chefRepository.save(chef);
    }

    // Cache all chefs in Caffeine
    @Cacheable(value = "chefs")
    public Page<Chef> getAllChefs(Pageable pageable) {
        return chefRepository.findAll(pageable);
    }



    // Cacheable method to retrieve chef by ID
    @Cacheable(value = "chef", key = "#id")
    public Chef getChefById(Long id) {
        return chefRepository.findById(id).orElse(null);
    }


    // Cache eviction on chef deletion
    @CacheEvict(value = "chefs", allEntries = true)
    public void deleteChefById(Long id) {
        chefRepository.deleteById(id);
    }
}
