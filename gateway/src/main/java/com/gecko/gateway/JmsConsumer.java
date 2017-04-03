package com.gecko.gateway;

import com.gecko.gateway.consumer.DefaultConsumer;
import com.gecko.message.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import java.io.IOException;

/**
 * Created by hlieu on 03/3/17.
 */
public class JmsConsumer {

   public static void main (String[] args) throws JMSException, InterruptedException, IOException {

      String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", nioConnection);
      PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory (connectionFactory);

      DefaultMessageListenerContainer messageListenerContainer = new DefaultMessageListenerContainer ();
      messageListenerContainer.setConnectionFactory (pooledConnectionFactory);

      Destination destination = new ActiveMQQueue ("Gecko.global.dev.test.Queue");
      messageListenerContainer.setDestination (destination);

      MessageListener listener = new DefaultConsumer ();
      messageListenerContainer.setMessageListener(listener);
      messageListenerContainer.initialize ();
      messageListenerContainer.start();
      System.out.println ("Waiting for messages");

      System.in.read();

      messageListenerContainer.stop();
      System.exit(1);
   }
}
