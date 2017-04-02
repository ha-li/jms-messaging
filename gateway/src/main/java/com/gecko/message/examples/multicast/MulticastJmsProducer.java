package com.gecko.message.examples.multicast;

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
public class MulticastJmsProducer {

   public static void main (String[] args) throws JMSException {

      // run a broker using an autodiscover multicast protocol, this producer
      // will be able to find it and publish to it

      String multicast = InMemoryRepository.getBrokerUrl("multicast");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(multicast);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.simple");
      MessageProducer producer = session.createProducer (destination);
      TextMessage msg = session.createTextMessage("A simple message");

      System.out.println ("Sending a message");
      producer.send (msg);
      producer.close ();
      session.close ();
      connection.close ();
   }
}
