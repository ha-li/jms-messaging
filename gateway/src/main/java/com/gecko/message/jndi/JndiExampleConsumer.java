package com.gecko.message.jndi;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
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
public class JndiExampleConsumer {
   public static void main (String[] args) throws Exception {
      Context ctx = new InitialContext ();
      ConnectionFactory factory = (ConnectionFactory) ctx.lookup ("connectionFactory");
      Connection connection = factory.createConnection ("admin", "admin");
      connection.start();
      Session session = connection.createSession (false, Session.AUTO_ACKNOWLEDGE);
      Queue qDest = (Queue)ctx.lookup ("requestQueue");
      MessageConsumer consumer = session.createConsumer (qDest);

      System.out.println ("Waiting for messages");

      while (true) {
         Message msg = consumer.receive ();
         TextMessage txtMsg = (TextMessage) msg;
         String content = txtMsg.getText ();
         System.out.println (content);
         Thread.sleep (10);
      }
   }
}
