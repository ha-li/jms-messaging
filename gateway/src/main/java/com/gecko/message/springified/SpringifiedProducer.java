package com.gecko.message.springified;

import com.gecko.message.springified.service.MessageSenderService;
import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.JMSException;

/**
 * Created by hlieu on 03/3/17.
 */
public class SpringifiedProducer {

   public static void main (String[] args) throws JMSException {

      String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", nioConnection);

      MessageSenderService service = new MessageSenderService (connectionFactory);

      for (int i = 0; i < 10; i++) {
         System.out.println ("Sending a message with transaction id");
         service.sendMessage ("Queue.request", "A message to send");
      }
   }
}
