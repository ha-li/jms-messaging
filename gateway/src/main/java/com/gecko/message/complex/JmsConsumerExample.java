package com.gecko.message.complex;

import com.gecko.message.complex.provider.JmsConnectionFactory;
import com.gecko.message.complex.listener.JmsConsumerRegister;
import com.gecko.message.repository.Destinations;

import javax.jms.Connection;
import javax.jms.JMSException;

/**
 * Created by hlieu on 03/1/17.
 */
public class JmsConsumerExample {

   public static void main (String[] args) throws JMSException {
      Connection connection = null;

      JmsConsumerRegister consumer = null;
      try {
         connection = JmsConnectionFactory.createConnection ();
         // need to start the connection ! very important
         connection.start();

         consumer = new JmsConsumerRegister (connection);
         consumer.registerConsumer (Destinations.defaultDestination ());

         System.out.println ("Waiting for messages...");

         while (true) {

         }

      } catch (JMSException jme) {
         jme.printStackTrace ();
      } catch (Exception e) {
         e.printStackTrace ();
      } finally {
         if (consumer != null) {
            consumer.destroy ();
         }

         if (connection != null) {
            connection.close();
         }
      }
   }
}
