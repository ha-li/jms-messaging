package com.gecko.message.complex.producer;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by hlieu on 03/19/17.
 */
public class SecureTextMessageProducer extends SecureMessageProducer {
   public boolean sendMessage (Session session, String destination, String message) {
      try {
         Destination queue = session.createQueue (destination);
         Message msg = session.createTextMessage(message);
         super.sendMessage (session, queue, msg);
         return true;
      } catch (Throwable t) {
         throw new RuntimeException ("Failed to send message");
      }
   }
}
