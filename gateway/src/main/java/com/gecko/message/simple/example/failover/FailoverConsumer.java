package com.gecko.message.simple.example.failover;

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
public class FailoverConsumer {

   public static void main (String[] args) throws JMSException, InterruptedException {

      // An example of a failover url configuration.
      // 2 brokers are configured and when one dies, the client automatically
      // switches over to the other broker and the client needs to do nothing.

      String nioConnection = InMemoryRepository.getBrokerUrl("failover");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(nioConnection);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.failover");
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
