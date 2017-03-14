package com.gecko.message.example.peer;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/3/17.
 */
public class PeerJmsProducer {

   public static void main (String[] args) throws Exception {

      // an example of a network of embedded brokers. this publisher
      // will publish to its embedded broker, which will forward the
      // message to it's peer (the consumers embedded broker)

      String peer = InMemoryRepository.getBrokerUrl("peer");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(peer);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.simple");
      MessageProducer producer = session.createProducer (destination);

      System.out.println ("Sending a message");

      for (int i = 0; i < 1000; i++) {
         System.out.println ("Sending message " + i);
         TextMessage msg = session.createTextMessage("A simple message " + i);

         producer.send (msg);
         // producer.close ();
      }
      producer.close ();
      session.close ();
      connection.close ();
      Thread.sleep(10000);
      System.exit(1);
   }
}
