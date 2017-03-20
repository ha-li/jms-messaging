package com.gecko.message.complex.producer;

import com.gecko.message.complex.AbstractSessionProvider;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by hlieu on 02/24/17.
 */
public class PlainTextProducer extends AbstractSessionProvider implements JmsProducer {

   private Connection connection;
   public PlainTextProducer (Connection connection) {
      this.connection = connection;
   }

   public void sendMessage (String destinationName, String message)throws JMSException {
      Session session = super.session(this.connection);
      Destination destination = session.createQueue (destinationName);
      //MessageProducer producer = null;

      if (session != null) {
         MessageProducer producer = session.createProducer (destination);
         TextMessage msg = session.createTextMessage(message);
         producer.send (msg);

         producer.close();
      }

      session.close ();
   }
}
