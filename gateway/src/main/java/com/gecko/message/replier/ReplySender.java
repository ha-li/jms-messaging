package com.gecko.message.replier;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.UUID;

/**
 * Created by hlieu on 03/24/17.
 */
public class ReplySender implements MessageListener {

   private Session session = null;

   public ReplySender (Session session) {
      this.session = session;
   }
   public void onMessage (Message message) {
      try {
         TextMessage request = (TextMessage) message;
         String msg = request.getText ();

         System.out.println ("Got a request: " + msg );


         String responseText = "Replying to request: " + msg;
         TextMessage response = session.createTextMessage (responseText);
         String uuid = UUID.randomUUID ().toString ();
         response.setStringProperty ("TransactionId", uuid );

         String correlationId = request.getJMSCorrelationID ();
         response.setJMSCorrelationID (correlationId);

         MessageProducer producer = session.createProducer (null);
         producer.setDeliveryMode (DeliveryMode.NON_PERSISTENT);
         Destination tempDestination = request.getJMSReplyTo ();
         //session.createTemporaryQueue ();
         producer.send (request.getJMSReplyTo (), response);
      } catch (Exception e) {
         e.printStackTrace ();
      }
   }
}
