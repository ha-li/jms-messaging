package com.gecko.message.replier;

import com.gecko.message.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * Created by hlieu on 03/22/17.
 */
public class MessageSender {
   private Session session = null;
   private Destination replyToDestination;
   private MessageProducer producer;
   private Connection connection = null;

   public void start () throws JMSException {
      String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(nioConnection);
      connection = connectionFactory.createConnection ("admin", "admin");
      connection.start ();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      Destination destination = session.createQueue("Queue.request");
      producer = session.createProducer (destination);

      replyToDestination = session.createQueue ("Queue.reply");

      MessageConsumer consumer = session.createConsumer(replyToDestination);
      consumer.setMessageListener (new ReplyConsumer ());
   }

   public void sendRequests () throws JMSException {
      for (int i = 0; i < 1; i++) {

         TextMessage msg = session.createTextMessage ("Can you reply to me regarding message " + i);
         String uuid = UUID.randomUUID ().toString ();
         msg.setStringProperty ("TransactionId", uuid );
         msg.setJMSCorrelationID (uuid);
         msg.setJMSReplyTo (replyToDestination);
         System.out.println ("Sending a message with transaction id " + i);

         producer.send (msg);
      }

   }

   public void close () throws JMSException{
      producer.close ();
      session.close ();
      connection.close ();
   }

   public static void main (String[] args) throws Exception {

      MessageSender sender = new MessageSender();
      sender.start ();
      sender.sendRequests ();
      Thread.sleep (30000);
      sender.close ();
   }
}
