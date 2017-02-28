package com.gecko.message;

import com.gecko.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * Created by hlieu on 02/27/17.
 */
public class JmsConnectionFactory {
   private static ConnectionFactory factory;
   private static String brokerUrl;
   private static String connectUser = "admin";
   private static String connectPwd = "admin";
   static {
      brokerUrl = InMemoryRepository.getBrokerUrl ();
      factory = new ActiveMQConnectionFactory (brokerUrl);
   }

   public static Connection getConnection () throws JMSException {
      if (factory == null) {
         brokerUrl = InMemoryRepository.getBrokerUrl ();
         factory = new ActiveMQConnectionFactory (brokerUrl);
      }

      return factory.createConnection (connectUser, connectPwd);
   }

   /* public Session getSession (boolean inTransaction, int type) throws JMSException {
      return getConnection ().createSession(inTransaction, type);
   }

   public Session getSession () throws JMSException {
      return getSession(false, Session.AUTO_ACKNOWLEDGE);
   } */
}
