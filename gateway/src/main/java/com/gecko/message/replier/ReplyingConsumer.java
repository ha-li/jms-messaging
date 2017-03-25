package com.gecko.message.replier;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by hlieu on 03/22/17.
 */
public class ReplyingConsumer  {

   private Connection connection = null;
   private Session session = null;
   private Destination destination = null;

   public void start () throws JMSException {
      try {
         String brokerUrl = InMemoryRepository.getBrokerUrl ("tcp");
         ConnectionFactory factory = new ActiveMQConnectionFactory (brokerUrl);

         connection = factory.createConnection ("admin", "admin");
         session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);

         destination = session.createQueue ("Queue.request");

         MessageConsumer consumer = session.createConsumer (destination);

         connection.start ();
         consumer.setMessageListener (new ReplySender (session));
      } catch (Exception e) {
         e.printStackTrace ();
      }
   }


   public void stop () throws JMSException{
      session.close ();
      connection.close ();
   }


   public static void main (String[] args) throws Exception {
      ReplyingConsumer replier = new ReplyingConsumer ();
      replier.start ();

      Thread.sleep (10000);

      replier.stop ();
   }
}
