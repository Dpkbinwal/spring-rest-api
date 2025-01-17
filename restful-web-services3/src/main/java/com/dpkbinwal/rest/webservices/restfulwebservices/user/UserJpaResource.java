package com.dpkbinwal.rest.webservices.restfulwebservices.user;
import java.net.URI;
//call kr do 
import java.util.List;

import org.apache.el.stream.Optional;
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

import com.dpkbinwal.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.dpkbinwal.rest.webservices.restfulwebservices.jpa.UserRepository;

import jakarta.validation.Valid;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
public class UserJpaResource {
	
	private UserRepository repository;
	
	private PostRepository postRepository;
	
	public UserJpaResource( UserRepository repository, PostRepository postRepository) {
		super();
		this.repository=repository;
		this.postRepository=postRepository;
		
	}
	
	//get/users
	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers(){
		return repository.findAll();
	}
	
		@GetMapping("/jpa/users/{id}")
		public EntityModel<User> retrieveOneUser(@PathVariable int id){
			java.util.Optional<User> user = repository.findById(id);
			
			if(user.isEmpty()) throw new UserNotFoundException("id:"+id);
			EntityModel<User> entityModel=EntityModel.of(user.get());
			
			WebMvcLinkBuilder link= linkTo(methodOn(this.getClass()).retrieveAllUsers());
			entityModel.add(link.withRel("all-users"));
			return entityModel;
		}
	
	
	
	//post/users
	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
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
		@DeleteMapping("/jpa/users/{id}")
		public void deleteOneUser(@PathVariable int id){
			repository.deleteById(id);
			
		}
		
		
		@GetMapping("/jpa/users/{id}/posts")
		public List<Post> retrievePostForUser(@PathVariable int id){
			java.util.Optional<User> user= repository.findById(id);
			
			if(user.isEmpty())
				throw new UserNotFoundException("id:" + id);
			
			return user.get().getPosts();
			
		}
		
		@PostMapping("/jpa/users/{id}/posts")
		public ResponseEntity<Object> createPostForUser(@PathVariable int id,@Valid @RequestBody Post post){
			java.util.Optional<User> user= repository.findById(id);
			
			if(user.isEmpty())
				throw new UserNotFoundException("id:" + id);
			
			post.setUser(user.get());
			Post savedPost= postRepository.save(post);
			
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}")
					.buildAndExpand(savedPost.getId())
					.toUri() ;
			
			//location -/users/4
			return ResponseEntity.created(location).build();
			
		}
		
}
