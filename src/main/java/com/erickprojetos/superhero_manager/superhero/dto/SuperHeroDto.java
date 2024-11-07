package com.erickprojetos.superhero_manager.superhero.dto;

import java.util.UUID;

import com.erickprojetos.superhero_manager.superhero.SuperHero;

public record SuperHeroDto(UUID id, String name, String superpower, String weakness, String description, Boolean isHuman, String imageUrl) {
    public SuperHeroDto(SuperHero superHero) {
        this(superHero.getId(), superHero.getName(), superHero.getSuperpower(), superHero.getWeakness(), superHero.getDescription(), superHero.getIsHuman(), superHero.getImageUrl());
    }
}
