package com.gecko.message.examples.simple;

import com.gecko.message.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * Created by hlieu on 03/3/17.
 */
public class SimpleProducer {

   public static void main (String[] args) throws JMSException {

      // run this with a non-embedded active mq broker up and
      // transportConnector configured according to nioConnection string
      // you should see a message created in "Queue.simple"
      // in the admin console

      String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(nioConnection);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.simple");
      MessageProducer producer = session.createProducer (destination);

      for (int i = 0; i < 50; i++) {

         TextMessage msg = session.createTextMessage ("A simple message " + i);
         msg.setStringProperty ("TransactionId", UUID.randomUUID ().toString () );
         System.out.println ("Sending a message with transaction id");

         producer.send (msg);
      }
      producer.close ();
      session.close ();
      connection.close ();
   }
}
