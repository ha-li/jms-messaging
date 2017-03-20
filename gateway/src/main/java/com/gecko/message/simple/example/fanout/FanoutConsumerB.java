package com.gecko.message.simple.example.fanout;

import com.gecko.repository.InMemoryRepository;
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
public class FanoutConsumerB {

   public static void main (String[] args) throws JMSException, InterruptedException {

      // an example of a consumer listening on a topic that a publisher
      // has fanout published a message to.

      String brokerUrl = "tcp://localhost:61617";
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createTopic("Queue.simple");
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
