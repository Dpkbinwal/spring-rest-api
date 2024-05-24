package com.dpkbinwal.rest.webservices.restfulwebservices.user;
import java.net.URI;
//call kr do 
import java.util.List;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class UserResource {
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service=service;
	}
	
	//get/users
	@GetMapping("/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
//	//get/users 		normal method used in (10video)
//	@GetMapping("/users/{id}")
//	public User retrieveOneUser(@PathVariable int id){
//		User user = service.findOne(id);
//		if(user==null) throw new UserNotFoundException("id:"+id);
//		return user;
//	}
	
	//get/users			haetoas method
		@GetMapping("/users/{id}")
		
		public EntityModel<User> retrieveOneUser(@PathVariable int id){
			User user = service.findOne(id);
			
			if(user==null) throw new UserNotFoundException("id:"+id);
			EntityModel<User> entityModel=EntityModel.of(user);
			
			WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUsers());
			entityModel.add(link.withRel("all-users"));
			return entityModel;
		}
	
	
	
	//post/users
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
//		return ResponseEntity.created(null).build();
		
		// /users/4 -> /user/{id} , user.getID	
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedUser.getId())
				.toUri() ;
		
		//location -/users/4
		return ResponseEntity.created(location).build();
	}
	
	
	//delete/users
		@DeleteMapping("/users/{id}")
		public void deleteOneUser(@PathVariable int id){
			service.deleteUser(id);
			
		}
		
}
