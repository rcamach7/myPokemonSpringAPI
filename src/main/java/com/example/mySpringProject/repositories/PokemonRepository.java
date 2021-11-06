package com.example.mySpringProject.repositories;
import org.springframework.data.repository.CrudRepository;

import com.example.mySpringProject.entities.Pokemon;


public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
	
}
// * Also, rename package and remove 'example' to create a cleanly named project.