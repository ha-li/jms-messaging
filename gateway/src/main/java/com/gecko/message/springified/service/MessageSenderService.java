package com.gecko.message.springified.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

/**
 * A simple example leveraging the spring objects jmsTemplate
 * to do the heavy lifting for me.
 *
 * Created by hlieu on 03/26/17.
 */
public class MessageSenderService {
   private JmsTemplate jmsTemplate;

   public MessageSenderService (ConnectionFactory connFactory) {
      jmsTemplate = new JmsTemplate(connFactory);
   }

   public void sendMessage (String destinationId, final String message) {

      jmsTemplate.send( destinationId, new MessageCreator () {
         public Message createMessage (Session session) throws JMSException {
            String uid = UUID.randomUUID ().toString();
            return MessageService.createMessage (session, uid, message);
         }
      });
   }
}
