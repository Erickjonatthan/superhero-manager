package com.erickprojetos.superhero_manager.superhero.filter;

import com.erickprojetos.superhero_manager.superhero.SuperHero;
import com.erickprojetos.superhero_manager.superhero.SuperHeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.List;

@WebFilter(urlPatterns = "/superheroes/filter")
@Component
public class SuperHeroFilter implements Filter {

    @Autowired
    private SuperHeroRepository superHeroRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String superpowers = request.getParameter("superpowers");
        if (superpowers != null && !superpowers.isEmpty()) {
            List<SuperHero> superheroes = superHeroRepository.findBySuperpowers(superpowers.toLowerCase());
            request.setAttribute("filteredSuperheroes", superheroes);
        }
        chain.doFilter(request, response);
    }
}