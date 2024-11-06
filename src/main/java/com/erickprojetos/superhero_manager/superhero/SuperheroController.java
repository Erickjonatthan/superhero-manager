package com.erickprojetos.superhero_manager.superhero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

import java.util.List;

@RestController
public class SuperheroController {

    @Autowired
    private SuperheroRepository superheroRepository;

    @GetMapping("/superheroes")
    public List<Superhero> getAllSuperheroes() {
        return superheroRepository.findAll();
    }

    @PostMapping("/superheroes")
    @Transactional
    public Superhero createSuperHero(@RequestBody Superhero superhero) {
        return superheroRepository.save(superhero);
    }
}