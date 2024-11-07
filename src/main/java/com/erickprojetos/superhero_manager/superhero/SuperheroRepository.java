package com.erickprojetos.superhero_manager.superhero;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, UUID> {
}
