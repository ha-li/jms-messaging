package com.gecko.message.producer;

import com.gecko.message.JmsConnectionFactory;

import javax.jms.Connection;
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
      Connection connection = null;
      Session session = null;

      String destinationName = "simple.test.queue";
      MessageProducer producer = null;

      try {
         connection = JmsConnectionFactory.createConnection ();
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
