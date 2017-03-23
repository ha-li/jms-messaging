package com.gecko.message.replier;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/22/17.
 */
public class ReplyingConsumer  {

   public static void replyTo (Session session, Message msg) throws JMSException {
      TextMessage txtMsg = (TextMessage) msg;
      String correlationId = txtMsg.getJMSCorrelationID ();
      Destination destination = txtMsg.getJMSReplyTo ();
      System.out.println (txtMsg.getText ());
      Destination replyDestination = session.createQueue(destination.toString ());

      MessageProducer replier = session.createProducer (replyDestination);
      TextMessage txtMessage = session.createTextMessage ( "Got your message");
      txtMessage.setJMSCorrelationID (correlationId);
      txtMessage.setStringProperty ("TransactionId", "iajsdf");
      replier.send (txtMessage);
   }

   public static void main (String[] args) throws JMSException {
      Connection connection = null;
      Session session = null;
      try {
         String brokerUrl = InMemoryRepository.getBrokerUrl ("tcp");
         ConnectionFactory factory = new ActiveMQConnectionFactory (brokerUrl);

         connection = factory.createConnection ("admin", "admin");
         session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

         Destination destination = session.createQueue ("Queue.request");
         MessageConsumer consumer = session.createConsumer (destination);

         connection.start ();

         System.out.println ("listening for requests");
         for (int i = 0; i < 1; i++) {
            Message message = consumer.receive ();
            TextMessage txtMsg = (TextMessage) message;
            String txt = txtMsg.getText ();
            System.out.println (txt);
            replyTo (session, message);
         }
      } finally {
         session.close ();
         connection.close ();
      }
   }
}
