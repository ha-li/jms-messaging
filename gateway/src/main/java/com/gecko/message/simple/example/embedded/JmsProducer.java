package com.gecko.message.simple.example.embedded;


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
public class JmsProducer {

   public static void main (String[] args) throws JMSException {

      // this producer connects with the embedded active mq broker via
      // transportConnector configured according to nioConnection string

      String producerBrokerUrl = InMemoryRepository.getBrokerUrl("vm_producer");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(producerBrokerUrl);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.simple");
      MessageProducer producer = session.createProducer (destination);
      TextMessage msg = session.createTextMessage("A simple message for an embedded broker");

      System.out.println ("Sending a message to an embedded broker");
      producer.send (msg);
      producer.close ();
      session.close ();
      connection.close ();
   }
}