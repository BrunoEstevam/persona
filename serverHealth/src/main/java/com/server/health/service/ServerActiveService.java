package com.server.health.service;

import static com.server.health.config.Constantes.CHARACTER_UNIQUESERVERHASH_QUEUE_NAME;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.health.config.Constantes;
import com.server.health.model.ServerActive;
import com.server.health.repository.ServerActiveRepository;
import com.server.health.service.impl.IServerActiveService;

@Service
public class ServerActiveService implements IServerActiveService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ServerActiveRepository repository;

	@Autowired
	private ServerHistoryService historyService;

	@Override
	@RabbitListener(queues = CHARACTER_UNIQUESERVERHASH_QUEUE_NAME)
	public void save(String uniqueServerHash) {
		Optional<ServerActive> optional = repository.findByUniqueServerHash(uniqueServerHash);

		if (optional.isEmpty()) {

			List<ServerActive> entities = repository.findAllByOrderByOnlineSince();

			if (entities.size() == 10) {
				ServerActive entity = entities.get(0);

				rabbitTemplate.convertAndSend(Constantes.CHARACTER_INACTIVE_QUEUE_NAME, entity.getUniqueServerHash());

				repository.delete(entity);

				historyService.save(entity.getUniqueServerHash());
			}

			repository.save(
					ServerActive.builder().onlineSince(LocalDateTime.now()).uniqueServerHash(uniqueServerHash).build());
		}
	}

}
