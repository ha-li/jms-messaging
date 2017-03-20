package com.gecko.message.complex.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/1/17.
 */
public class JmsConsumer implements MessageListener {

   public void onMessage (Message message) {
      TextMessage textMessage = (TextMessage) message;
      try {
         String content = textMessage.getText ();
         System.out.println ("The message is : " + content);
      } catch (JMSException jme) {
         jme.printStackTrace ();;
      }
   }
}
