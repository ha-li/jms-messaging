package com.gecko.message.replier;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * Created by hlieu on 03/22/17.
 */
public class MessageSender {

   public static void getReplies (Session session, Destination destination) throws JMSException {
      MessageConsumer consumer = session.createConsumer (destination);

      for (int i = 0; i < 1; i++) {
         Message reply = consumer.receive ();
         TextMessage txtMessage = (TextMessage) reply;
         System.out.println (txtMessage.getText ());
      }
   }

   public static void main (String[] args) throws JMSException {
      String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(nioConnection);
      Connection connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.request");
      Destination replyToDestination = session.createTemporaryQueue ();
      MessageProducer producer = session.createProducer (destination);

      for (int i = 0; i < 1; i++) {

         TextMessage msg = session.createTextMessage ("Can you reply to me regarding message " + i);
         String uuid = UUID.randomUUID ().toString ();
         msg.setStringProperty ("TransactionId", uuid );
         msg.setJMSCorrelationID (uuid);
         msg.setJMSReplyTo (replyToDestination);
         System.out.println ("Sending a message with transaction id " + i);

         producer.send (msg);
      }

      getReplies (session, replyToDestination);
      producer.close ();
      session.close ();
      connection.close ();

   }
}
