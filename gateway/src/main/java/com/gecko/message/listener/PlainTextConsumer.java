package com.gecko.message.listener;

import com.gecko.message.JmsConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 02/24/17.
 */
public class PlainTextConsumer implements MessageListener {

   public void onMessage (Message message) {
      TextMessage textMessage = (TextMessage) message;
      try {
         String content = textMessage.getText ();
         System.out.println ("The message is : " + content);
      } catch (JMSException jme) {
         jme.printStackTrace ();;
      }
   }

   public static void main (String[] args) throws JMSException {
      Connection connection = null;
      Session session = null;

      String destinationName = "simple.test.queue";
      MessageConsumer consumer;
      try {
         connection = JmsConnectionFactory.createConnection ();
         session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
         Destination destination = session.createQueue (destinationName);

         // need to start the connection ! very important
         connection.start();
         consumer = session.createConsumer (destination);//  (new PlainTextConsumer () );
         consumer.setMessageListener (new PlainTextConsumer ());

         System.out.println ("Waiting for messages...");
         while (true) {
            /* Message msg = consumer.receive ();

            if( msg instanceof TextMessage ) {
               TextMessage txt = (TextMessage) msg;
               String content = txt.getText ();
               System.out.println (content);
               //break;
            } else {
               Thread.sleep(10);
            } */
         }
      } catch (JMSException jme) {
         jme.printStackTrace ();
      } catch (Exception e) {
         e.printStackTrace ();
      } finally {
         if (session != null) {
            session.close();
         }
         if (connection != null) {
            connection.close();
         }
      }
   }
}
