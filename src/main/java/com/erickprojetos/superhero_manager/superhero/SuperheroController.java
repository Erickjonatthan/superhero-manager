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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.ServletRequest;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Tag(name = "Super Hero Controller", description = "Gerenciamento de Super Heróis")
public class SuperHeroController {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @GetMapping("/superheroes")
    @Operation(summary = "Listar todos os Super Heróis", description = "Retorna uma lista de todos os super heróis.")
    public ResponseEntity<List<SuperHero>> getAllSuperheroes() {
        List<SuperHero> superheroes = this.superHeroRepository.findAll();
        return ResponseEntity.ok(superheroes);
    }

    @GetMapping("/superheroes/filter")
    @Operation(summary = "Filtrar Super Heróis por superpoderes", description = "Retorna uma lista de super heróis filtrados por superpoderes.")
    public ResponseEntity<List<SuperHero>> getSuperheroesBysuperpowers(ServletRequest request) {
        Object attribute = request.getAttribute("filteredSuperheroes");
    
        if (attribute instanceof List<?>) {
            List<?> list = (List<?>) attribute;
            
            List<SuperHero> filteredSuperheroes = list.stream()
                .filter(SuperHero.class::isInstance)  
                .map(SuperHero.class::cast)          
                .toList();
    
            if (!filteredSuperheroes.isEmpty()) {
                return ResponseEntity.ok(filteredSuperheroes);
            }
        }
    
        return ResponseEntity.notFound().build();
    }
    

    @PostMapping("/superheroes")
    @Operation(summary = "Criar um novo Super Herói", description = "Cria um novo super herói e retorna os detalhes do super herói criado.")
    @Transactional
    public ResponseEntity<SuperHero> createSuperHero(@RequestBody SuperHero superHero) {
        SuperHero savedSuperHero = this.superHeroRepository.save(superHero);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSuperHero);
    }

    @PutMapping("/superheroes/{id}")
    @Operation(summary = "Atualizar Super Herói pelo ID", description = "Atualiza os detalhes de um super herói específico pelo seu ID.")
    @Transactional
    public ResponseEntity<SuperHero> updateSuperHero(@PathVariable UUID id, @RequestBody SuperHero payload) {
        Optional<SuperHero> superHero = this.superHeroRepository.findById(id);
        if (superHero.isPresent()) {
            
            SuperHero superHeroToUpdate = superHero.get();

            superHeroToUpdate.update(payload);

            this.superHeroRepository.save(superHeroToUpdate);

            return ResponseEntity.ok(superHeroToUpdate);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/superheroes/{id}")
    @Operation(summary = "Deletar Super Herói pelo ID", description = "Deleta um super herói específico pelo seu ID.")
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