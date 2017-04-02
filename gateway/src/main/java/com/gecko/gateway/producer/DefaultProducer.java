package com.gecko.gateway.producer;

import com.gecko.gateway.service.MessageService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

/**
 * Created by hlieu on 03/28/17.
 */
public class DefaultProducer extends AbstractProducer {
   private JmsTemplate jmsTemplate;

   public DefaultProducer (ConnectionFactory connectionFactory, Destination pDestination, String id) {
      super(pDestination, id);
      jmsTemplate = new JmsTemplate(connectionFactory);
   }

   @Override
   public void send (final String message) {
      jmsTemplate.send (getDestination (), new MessageCreator () {
         public Message createMessage(Session session) throws JMSException {
            String uuid = UUID.randomUUID ().toString ();
            return MessageService.createMessage(session, uuid, message);
         }
      });
   }

}
