package com.example.mySpringProject.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.mySpringProject.entities.Pokemon;
import com.example.mySpringProject.repositories.PokemonRepository;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PokemonController {
	private final PokemonRepository pokemonRepository;
	
	public PokemonController(final PokemonRepository pokemonRepository) {
		this.pokemonRepository = pokemonRepository;
	}
	
	@GetMapping()
	public ModelAndView index() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@GetMapping("/pokemon")
	public Iterable<Pokemon> getAllPokemon() {
		return this.pokemonRepository.findAll();
	}
	
	
	@GetMapping("/pokemon/{id}")
	public Optional<Pokemon> getPokemonById(@PathVariable Integer id) {
		return this.pokemonRepository.findById(id);
	}
	
	@PostMapping("/pokemon")
	public Pokemon createNewPokemon(@RequestBody Pokemon pokemon) {
		Pokemon newPokemon = this.pokemonRepository.save(pokemon);
		return newPokemon;
		// curl POST http://localhost:8080/pokemon -H "Content-Type: application/json" -d'{"name":"Tito","type":"God"}'
	}
	
	@PutMapping("/pokemon/{id}")
	public Pokemon updatePokemon(@PathVariable("id") Integer id, @RequestBody Pokemon pokemon) {
		// First check to see if this pokemon exists in our database
		Optional<Pokemon> pokemonToUpdateOptional = this.pokemonRepository.findById(id);
		if (!pokemonToUpdateOptional.isPresent()) {
			return null;
		}
		// After we verified, we pull any data from the @RequestBody that's valid / provided,
		// and pass along those values to our pokemonToUpdate holder
		Pokemon pokemonToUpdate = pokemonToUpdateOptional.get();
		if (pokemon.getName() != null) {
			pokemonToUpdate.setName(pokemon.getName());
		}
		if (pokemon.getType() != null) {
			pokemonToUpdate.setType(pokemon.getType());
		}
		// After all fields are now up to date on our holder, save it to our database, pull it out and 
		// send it back to the method (client). 
		Pokemon updatedPokemon = this.pokemonRepository.save(pokemonToUpdate);
		return updatedPokemon;
		// curl -X PUT http://localhost/pokemon/3 -H "Content-Type: application/json" -d'{"type":"water"}'
	}
	
	@DeleteMapping("/pokemon/{id}")
	public Pokemon deletePokemon(@PathVariable("id") Integer id) {
		// Check first if this pokemon ID exists
		Optional<Pokemon> pokemonToDeleteOptional = this.pokemonRepository.findById(id);
		if (!pokemonToDeleteOptional.isPresent()) {
			return null;
		}
		// Hold the pokemon to you can return the pokemon you deleted
		Pokemon pokemonToDelete = pokemonToDeleteOptional.get();
		this.pokemonRepository.delete(pokemonToDelete);
		return pokemonToDelete;
		// curl -X DELETE http://localhost:8080/pokemon/4
	}
	
	@GetMapping("/pokemon/search")
	public List<Pokemon> searchPokemon( 
			@RequestParam(name = "type", required = false) String type,
			@RequestParam(name = "gender", required = false) String gender,
			@RequestParam(name = "name", required = false) String name) {
		if (type != null && gender != null) {
			return this.pokemonRepository.findByTypeAndGender(type, gender);
		} else if (type != null) {
			return this.pokemonRepository.findByType(type);
		} else if  (gender != null) {
			return this.pokemonRepository.findByGender(gender);
		} else if (name != null) {
			return this.pokemonRepository.findByName(name);
		} else {
			return new ArrayList<>();
		}
		// curl 'http://localhost:8080/pokemon/search?type=Fire&gender=Male'
	}

}