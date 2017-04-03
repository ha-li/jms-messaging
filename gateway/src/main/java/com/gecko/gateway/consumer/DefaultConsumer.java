package com.gecko.gateway.consumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 04/1/17.
 */
public class DefaultConsumer implements MessageListener {

   public void onMessage(Message message) {
      try {
         if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            String transactionId = msg.getStringProperty ("TransactionId");
            System.out.println ("transactionId: " + transactionId);
            System.out.println ("message: " + msg.getText ());
         } else {
            System.out.println ("DefaultConsumer cannot handle this non-text messages");
         }
      } catch (JMSException jme) {
         jme.printStackTrace ();
      }
   }
}
