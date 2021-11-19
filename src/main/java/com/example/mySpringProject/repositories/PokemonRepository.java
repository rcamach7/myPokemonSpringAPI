package com.example.mySpringProject.repositories;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.mySpringProject.entities.Pokemon;



public interface PokemonRepository extends CrudRepository<Pokemon, Integer> {
	List<Pokemon> findByType(String type);
	List<Pokemon> findByGender(String gender);
	List<Pokemon> findByName(String name);
	List<Pokemon> findByTypeAndGender(String type, String gender);
	
	
	
	
	
}
// * Also, rename package and remove 'example' to create a cleanly named project.