package com.server.health.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.server.health.model.ServerHistory;

@Repository
public interface ServerHistoryRepository extends MongoRepository<ServerHistory, String> {
	
	Optional<ServerHistory> findByUniqueServerHash(String uniqueServerHash);
}
