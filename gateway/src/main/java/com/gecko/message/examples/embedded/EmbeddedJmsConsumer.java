package com.gecko.message.examples.embedded;

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
public class EmbeddedJmsConsumer {

   public static void main (String[] args) throws JMSException, InterruptedException {

      // consumer with an embedded active mq broker.
      // producer can use transportConnector configured according to nioConnection string
      // since this is an embedded broker, the admin console will not reflect this broker
      // to verify this is an embedded broker, do not start up activemq

      String embeddedBroker = InMemoryRepository.getBrokerUrl("vm");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(embeddedBroker);
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
