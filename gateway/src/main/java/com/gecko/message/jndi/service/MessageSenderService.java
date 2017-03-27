package com.gecko.message.jndi.service;

import org.apache.camel.component.jms.JmsMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 03/26/17.
 */
public class MessageSenderService {
   private JmsTemplate jmsTemplate;

   public MessageSenderService (ConnectionFactory connFactory, Destination defaultDestination) {
      jmsTemplate = new JmsTemplate();
      jmsTemplate.setDefaultDestination (defaultDestination);

   }

   public void sendMessage (final TextMessage message) {

      jmsTemplate.send( new MessageCreator () {
         public Message createMessage (Session session) throws JMSException {
            return message;
         }
      });
   }
}
