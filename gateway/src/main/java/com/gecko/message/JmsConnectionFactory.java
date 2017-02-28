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

   public static Connection createConnection (String brokerUrl, String connectUser, String connectPasswd) throws JMSException{
      return (new ActiveMQConnectionFactory (brokerUrl)).createConnection(connectUser, connectPasswd);
   }
}
