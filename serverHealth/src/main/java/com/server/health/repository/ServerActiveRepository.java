package com.server.health.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.health.model.ServerActive;

@Repository
public interface ServerActiveRepository extends MongoRepository<ServerActive, String> {
	
	List<ServerActive> findAllByOrderByOnlineSince(); 
	Optional<ServerActive> findByUniqueServerHash(String uniqueServerHash);
}
