package com.nayan.aaho;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface UserRepository extends  MongoRepository<User, String>{

	public List<User> findByFirstName(String firstName);
    public List<User> findByLastName(String lastName);
	public User findOneByEmailAndPassword(String email, String pwd);
    
}
