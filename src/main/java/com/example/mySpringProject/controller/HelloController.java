package com.example.mySpringProject.controller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
 
@RestController()
public class HelloController {
	PokemonCollection pokeDex = new PokemonCollection();
	
	// Default page into our server application and return the index.html file. 
	@GetMapping()
	public ModelAndView index() {
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("index");
	    return modelAndView;
	}
	
	// This will print out all captured pokemon (always starts with the 3 starters)
	@GetMapping("/allpokemons")
	public String allPokeon() {
		return pokeDex.printAll();
	}
	
	// This will check to see if you have a specific pokemon by name. 
	@GetMapping("/check/{name}")
	public String checkIfCaptured(@PathVariable String name) {
		if (pokeDex.contains(name)) {
			return "You have captured this pokemon";
		} else {
			return "You have not captured this pokemon";
		}
	}
	
	// This will let you delete a pokemon by giving just a name.
	@GetMapping("delete/{name}")
	public String deletePokemon(@PathVariable String name) {
		name = name.toLowerCase();
		String copy = name;
		boolean hasNumbers =  copy.replaceAll("[^0-9]", "").isEmpty();
		if (pokeDex.contains(name) && hasNumbers) {
			pokeDex.deletePokemon(name);
			return "Pokemon " + name + " has been released";
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You either don't have this pokemon or the name contains digits");
		}
	}
	
	// This will let you add a captured pokemon with TWO Peremeters
	@GetMapping("/add/{name}+{type}")
	public String addPokemon(@PathVariable String name, @PathVariable String type) {
		if (!pokeDex.contains(name)) {
			pokeDex.addPokemon(name, type);
			return "added";
		} else {
			return "aleady captured";
		}
	}
	
	// This will work using curl from the command line since we can specify the type of request we want
	// example : curl -d "name:gyardos&type=water" -X POST http://localhost:8080
	// Recall we can have two requests that hit the same endpoint as long as the TYPE OF REQUEST is different. 
	@PostMapping()
	public String addViaCurl(@RequestParam String name, @RequestParam String type) {
		if (!pokeDex.contains(name)) {
			pokeDex.addPokemon(name, type);
			return "added";
		} else {
			return "aleady captured";
		}
	}
	
	// Delete pokemon given a name via curl
	@DeleteMapping()
	public String deleteViaCurl(@RequestParam String name) {
		name = name.toLowerCase();
		String copy = name;
		boolean hasNumbers =  copy.replaceAll("[^0-9]", "").isEmpty();
		if (pokeDex.contains(name) && hasNumbers) {
			pokeDex.deletePokemon(name);
			return "Pokemon " + name + " has been released";
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You either don't have this pokemon or the name contains digits");
		}
	}
	
	// Will change pokemon type given a name.
	@PutMapping() 
	public String editTypeViaCurl(@RequestParam String name, String typeChange) {
		if (pokeDex.contains(name)) {
			pokeDex.changeType(name, typeChange);
			return "Pokemon type has been changed";
		} else {
			return "No pokemon with that name has been captured yet.";
		}
	}
}