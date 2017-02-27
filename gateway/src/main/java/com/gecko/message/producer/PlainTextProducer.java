package com.gecko.message.producer;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 02/24/17.
 */
public class PlainTextProducer {

   public static void main (String[] args) throws JMSException {
      //String  jmsBrokerUrl = "tcp://127.0.0.1:61616";
      String jmsBrokerUrl = InMemoryRepository.getProducerBrokerUrl ();
      ConnectionFactory connectionFactory = new ActiveMQConnectionFactory (jmsBrokerUrl);
      String connectUser = "admin";
      String connectPwd = "admin";
      Connection connection = null;
      Session session = null;

      String destinationName = "simple.test.queue";
      MessageProducer producer = null;

      try {
         connection = connectionFactory.createConnection (connectUser, connectPwd);
         session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue(destinationName);
         producer = session.createProducer (destination);

         TextMessage message = session.createTextMessage ("Hello messaging world!");
         producer.send(message);
         System.out.println ("Message sent");
      } catch (JMSException jme) {
         jme.printStackTrace ();
      } catch (Exception e) {
         e.printStackTrace ();
      } finally {
         if(producer != null) {
            producer.close();
         }
         if(session != null) {
            //session.commit();
            session.close();
         }
         if (connection != null) {
            connection.close ();
         }
      }
   }
}
