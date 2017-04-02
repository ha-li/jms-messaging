package com.gecko.message.examples.simple;

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
public class SimpleConsumer {

   public static void main (String[] args) throws JMSException, InterruptedException {

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
      MessageConsumer consumer = session.createConsumer (destination);

      System.out.println ("Waiting for messages");
      while (true) {
         Message msg = consumer.receive ();
         TextMessage txtMsg = (TextMessage) msg;
         String content = txtMsg.getText ();
         System.out.println (content);
         Thread.sleep (10);
      }
   }
}
