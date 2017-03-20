package com.gecko.message.simple.example.multicast;

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
public class MulticastJmsConsumer {

   public static void main (String[] args) throws JMSException, InterruptedException {

      // run a active mq broker up using the multicast protocol
      // this consumer will auto discovery the broker,
      // or it can use the tcp protocol, either one will work

      // String brokerUrl = InMemoryRepository.getBrokerUrl("multicast");

      String brokerUrl = InMemoryRepository.getBrokerUrl("tcp");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerUrl);
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
