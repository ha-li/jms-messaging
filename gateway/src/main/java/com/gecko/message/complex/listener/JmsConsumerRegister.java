package com.gecko.message.complex.listener;

import com.gecko.message.complex.provider.AbstractSessionProvider;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;

/**
 * Created by hlieu on 02/24/17.
 */
public class JmsConsumerRegister extends AbstractSessionProvider {

   private Connection connection;
   private Session session;

   public JmsConsumerRegister (Connection connection) {
      this.connection = connection;
   }

   public void registerConsumer (String destinationName) throws JMSException {

      this.session = session (connection);

      Destination destination = session.createQueue (destinationName);
      MessageConsumer consumer = session.createConsumer (destination);
      consumer.setMessageListener (new JmsConsumer ());
   }

   public void destroy () throws JMSException {
      System.out.println ("closing the session.");
      session.close ();
   }
}
