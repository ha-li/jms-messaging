package com.gecko.message.example.failover;

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
public class FailoverProducer {

   public static void main (String[] args) throws JMSException {

      // An example of a failover url configuration.
      // 2 brokers are configured and when one dies, the client automatically
      // switches over to the other broker and the client needs to do nothing.

      String nioConnection = InMemoryRepository.getBrokerUrl("failover");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(nioConnection);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.failover");
      MessageProducer producer = session.createProducer (destination);
      TextMessage msg = session.createTextMessage("A simple message");

      System.out.println ("Sending a message");
      producer.send (msg);
      producer.close ();
      session.close ();
      connection.close ();
   }
}
