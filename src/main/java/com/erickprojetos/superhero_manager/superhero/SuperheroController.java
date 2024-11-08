package com.erickprojetos.superhero_manager.superhero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class SuperHeroController {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @GetMapping("/superheroes")
    public ResponseEntity<List<SuperHero>> getAllSuperheroes() {
        List<SuperHero> superheroes = this.superHeroRepository.findAll();
        return ResponseEntity.ok(superheroes);
    }

    @PostMapping("/superheroes")
    @Transactional
    public ResponseEntity<SuperHero> createSuperHero(@RequestBody SuperHero superHero) {
        SuperHero savedSuperHero = this.superHeroRepository.save(superHero);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSuperHero);
    }

    @PutMapping("/superheroes/{id}")
    @Transactional
    public ResponseEntity<SuperHero> updateSuperHero(@PathVariable UUID id, @RequestBody SuperHero payload) {
        Optional<SuperHero> superHero = this.superHeroRepository.findById(id);
        if (superHero.isPresent()) {
            SuperHero superHeroToUpdate = superHero.get();

            superHeroToUpdate.setName(payload.getName());
            superHeroToUpdate.setSuperpower(payload.getSuperpower());
            superHeroToUpdate.setWeakness(payload.getWeakness());
            superHeroToUpdate.setDescription(payload.getDescription());
            superHeroToUpdate.setIsHuman(payload.getIsHuman());
            superHeroToUpdate.setImageUrl(payload.getImageUrl());

            this.superHeroRepository.save(superHeroToUpdate);

            return ResponseEntity.ok(superHeroToUpdate);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/superheroes/{id}")
    @Transactional
    public ResponseEntity<SuperHero> deleteSuperHero(@PathVariable UUID id) {
        Optional<SuperHero> superHero = this.superHeroRepository.findById(id);
        if (superHero.isPresent()) {
            this.superHeroRepository.delete(superHero.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

}