package com.dpkbinwal.rest.webservices.restfulwebservices.user;
//work as instance or insesrtion
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	//jpa/hibernate > database
	//userdaoservice >static list
	
	private static int userCount=0;
	
	private static List<User> users=new ArrayList<>();
	
	static {
		users.add(new User(++userCount,"Adam",LocalDate.now().minusYears(30)));
		users.add(new User(++userCount,"Ram",LocalDate.now().minusYears(15)));
		users.add(new User(++userCount,"Shyam",LocalDate.now().minusYears(20)));
	}
	
	
	public List<User> findAll(){
		return users;
	}
	
	
	public User save(User user){
		user.setId(++userCount);
		users.add(user);
		return user;
	}
	
	public User findOne (int id){
		Predicate<? super User> predicate= user->user.getId().equals(id);
//		return users.stream().filter(predicate).findFirst().get();
		return users.stream().filter(predicate).findFirst().orElse(null);//exception handling
	}
	
	
	public void deleteUser(int id){
		Predicate<? super User> predicate= user->user.getId().equals(id);
		users.removeIf(predicate);
	}
	
}
