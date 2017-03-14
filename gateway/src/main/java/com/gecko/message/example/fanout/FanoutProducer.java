package com.gecko.message.example.fanout;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/3/17.
 */
public class FanoutProducer {

   public static void main (String[] args) throws Exception {

      // an example of a fanout producer publishing to topics on 2 brokers,
      // because topics are used, both brokers will get the message. if it
      // were queues, the message would only be published to one of the brokers

      String fanout = InMemoryRepository.getBrokerUrl("fanout");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(fanout);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createTopic("Queue.simple");
      MessageProducer producer = session.createProducer (destination);

      System.out.println ("Sending a message");

      for (int i = 0; i < 1000; i++) {
         System.out.println ("Sending message " + i);
         TextMessage msg = session.createTextMessage("A simple message " + i);

         producer.send (msg);
      }
      producer.close ();
      session.close ();
      connection.close ();
      Thread.sleep(10000);
      System.exit(1);
   }
}
