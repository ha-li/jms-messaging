package com.gecko.message.example.simple;

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
public class SimpleJmsProducerEx {

   public static void main (String[] args) throws JMSException {

      // run this with a non-embedded active mq broker up and
      // transportConnector configured according to nioConnection string
      // you should see a jms message created in "Queue.simple"
      // in the admin console

      String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(nioConnection);
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