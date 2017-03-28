package com.gecko.message.jndi;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.UUID;

/**
 * A simple example of jndi and activemq using the spi provided by activemq.
 * See resources/jndi.properties for the settings
 *
 * Created by hlieu on 03/26/17.
 */
public class JndiExampleProducer {
   public static void main (String[] args) throws Exception {
      Context ctx = new InitialContext ();
      ConnectionFactory factory = (ConnectionFactory) ctx.lookup ("connectionFactory");
      Queue qDest = (Queue)ctx.lookup ("requestQueue");

      Connection connection = factory.createConnection ("admin", "admin");
      connection.start();
      Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
      MessageProducer producer = session.createProducer (qDest);

      TextMessage msg = session.createTextMessage("A jndi example");

      String uuid = UUID.randomUUID ().toString();
      msg.setStringProperty ("TransactionId", uuid);

      System.out.println ("Sending a message");
      //service.sendMessage (msg);

      producer.send(msg);
      producer.close();
      session.close();
      connection.close();
      System.out.println ("bye");

   }
}
