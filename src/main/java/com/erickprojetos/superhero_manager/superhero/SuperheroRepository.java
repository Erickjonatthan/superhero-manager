package com.erickprojetos.superhero_manager.superhero;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperHeroRepository extends JpaRepository<SuperHero, UUID> {
    @Query("SELECT s FROM SuperHero s JOIN s.superpowers sp WHERE LOWER(sp) LIKE LOWER(CONCAT('%', :superpowers, '%'))")
    List<SuperHero> findBySuperpowers(@Param("superpowers") String superpowers);
}
