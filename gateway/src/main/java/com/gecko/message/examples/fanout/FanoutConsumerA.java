package com.gecko.message.examples.fanout;

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
public class FanoutConsumerA {

   public static void main (String[] args) throws JMSException, InterruptedException {

      // an example of a fanout consumer listening on a topic
      // the producer will publish to a fanout protocol, so both
      // brokers will get the message, so both consumers will get the same message

      String brokerUrl = InMemoryRepository.getBrokerUrl("tcp");
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
