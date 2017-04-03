package com.gecko.gateway.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 04/2/17.
 */
public class ConnectionFactoryRepository {
   private static Map<String, ConnectionFactoryRecord> connectionRepository = new HashMap<String, ConnectionFactoryRecord> ();

   static {
      connectionRepository.put (
              "connection-factory",
              new ConnectionFactoryRecord (
                 "connection-factory",
                 "org.apache.activemq.ActiveMQConnectionFactory",
                 "admin",
                 "admin"
              ) );
   }

   public static ConnectionFactoryRecord get (String accessKey) {
      return connectionRepository.get(accessKey);
   }

   public static Collection<ConnectionFactoryRecord> allConnectionFactories () {
      return connectionRepository.values ();
   }
}
