package com.gecko.message.examples.peer;

import com.gecko.message.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/3/17.
 */
public class PeerJmsConsumer {

   public static void main (String[] args) throws JMSException, InterruptedException {

      // an example of a network of embedded brokers
      // this consumer's embedded broker will receive
      // messages from the producers embedded brokers

      // String brokerUrl = InMemoryRepository.getBrokerUrl("multicast");

      String brokerUrl = InMemoryRepository.getBrokerUrl("peer");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.simple");
      MessageConsumer consumer = session.createConsumer (destination);

      System.out.println ("Waiting for messages");
      while (true) {
         Message msg = consumer.receive ();
         TextMessage txtMsg = (TextMessage) msg;
         String content = txtMsg.getText ();
         System.out.println (content);
         Thread.sleep (1);
      }
   }
}
