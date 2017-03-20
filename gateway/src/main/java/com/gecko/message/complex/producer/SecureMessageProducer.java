package com.gecko.message.complex.producer;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import java.util.UUID;

/**
 *
 *
 * Created by hlieu on 03/19/17.
 */
public abstract class SecureMessageProducer implements SecureProducer {
   public boolean sendMessage (Session session, Destination destination, Message message) {
      try {
         MessageProducer producer = session.createProducer (destination);
         message.setStringProperty ("TransactionId", UUID.randomUUID ().toString ());
         producer.send (message);
         return true;
      } catch (Throwable t) {
         throw new RuntimeException ("Failed to send message");
      }
   }
}
