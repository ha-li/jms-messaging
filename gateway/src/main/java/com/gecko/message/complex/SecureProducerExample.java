package com.gecko.message.complex;

import com.gecko.message.complex.producer.SecureProducer;
import com.gecko.message.complex.producer.SecureTextMessageProducer;
import com.gecko.message.complex.provider.JmsConnectionFactory;
import com.gecko.repository.Destinations;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by hlieu on 03/19/17.
 */
public class SecureProducerExample {

   /**
    * The main that sends a message with the appropriate authorization token
    * 'TransactionId'
    * @param args
    * @throws JMSException
    */
   public static void main (String[] args) throws JMSException {
      Connection connection = null;

      connection = JmsConnectionFactory.createConnection ();

      // SecureTextMessageProducer will add a 'TransactionId' property
      // which is required to be consumed in this broker
      SecureProducer producer = new SecureTextMessageProducer ();
      producer.sendMessage(
              connection.createSession (false, Session.AUTO_ACKNOWLEDGE),
              Destinations.defaultDestination (),
              "Kicking Ass in an Ass kicking World Implemented");
      connection.stop ();
      connection.close();

   }
}
