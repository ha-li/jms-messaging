package com.gecko.message.example.simple;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

/**
 * Created by hlieu on 03/3/17.
 */
public class DurableTopicSubscriber {

   public static void main (String[] args) throws JMSException, InterruptedException {

      // a durable subscriber to a topic and
      // transportConnector configured according to nioConnection string
      // you should see a jms message created in "Queue.simple"
      // in the admin console

      String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(nioConnection);
      Connection connection = connectionFactory.createConnection ("admin", "admin");

      // this is how the client id gets set, is required for a durable consumer
      connection.setClientID ("123");
      connection.start ();

      Session session = connection.createSession(false, TopicSession.AUTO_ACKNOWLEDGE);
      Topic destination = session.createTopic("Topic.simple");
      TopicSubscriber consumer = session.createDurableSubscriber (destination, "durableSub");

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
