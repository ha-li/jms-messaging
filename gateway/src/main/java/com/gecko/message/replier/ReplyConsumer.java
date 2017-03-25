package com.gecko.message.replier;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/24/17.
 */
public class ReplyConsumer implements MessageListener {

   public void onMessage (Message message) {
      try {
         TextMessage msg = (TextMessage) message;
         System.out.println ("Reply received " + msg.getText());
      } catch (JMSException e) {
         e.printStackTrace ();
      }
   }
}
