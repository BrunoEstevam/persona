package com.server.health.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MqConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;

    // Queues: filas de espera que tem o objetivo de organizar as mensagens para efetuar a entrega para o destinatário correto.
        // durable - a fila sobreviverá a uma reinicialica��o do broker
        // exclusive - usado por apenas uma conexão e a fila será excluída quando essa conexão for encerrada
        // autoDelete - a fila que teve pelo menos um consumidor é excluída quando o último consumidor cancela a inscrição
    private Queue queue(String queueName){
        return new Queue(queueName, true, false, false);
    }

    //  Exchanges: locais onde as mensagens são postadas.
    private DirectExchange directExchange(){
        return new DirectExchange(Constantes.EXCHANGE_NAME);
    }

    //  Bindings: conjunto de regras com objetivo de classificar as mensagens em filas de espera.
    private Binding binding(Queue queue, DirectExchange directExchange){
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void createMq(){
        Queue queueUniquehash = queue(Constantes.CHARACTER_UNIQUESERVERHASH_QUEUE_NAME);
        Queue queueInactive = queue(Constantes.CHARACTER_INACTIVE_QUEUE_NAME);
        
        DirectExchange directExchange = directExchange();
        Binding bindingUniquehash = binding(queueUniquehash, directExchange);
        Binding bindingInactive = binding(queueInactive, directExchange);

        amqpAdmin.declareQueue(queueUniquehash);
        amqpAdmin.declareQueue(queueInactive);
        amqpAdmin.declareExchange(directExchange);
        amqpAdmin.declareBinding(bindingUniquehash);
        amqpAdmin.declareBinding(bindingInactive);
    }

}
