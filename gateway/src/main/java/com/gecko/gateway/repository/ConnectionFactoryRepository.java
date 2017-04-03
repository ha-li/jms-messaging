package com.gecko.gateway.repository;

import com.gecko.message.repository.InMemoryRepository;

import javax.jms.ConnectionFactory;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 04/2/17.
 */
public class ConnectionFactoryRepository {
   private static Map<String, ConnectionFactoryRecord> connectionRepository = new HashMap<String, ConnectionFactoryRecord> ();
   private static Map<String, ConnectionFactory> connectionMap = new HashMap<String, ConnectionFactory> ();
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

   public static ConnectionFactory findConnectionFactory (String connectionKey) throws Exception {
      ConnectionFactory factory = connectionMap.get (connectionKey);
      if (factory == null) {
         ConnectionFactoryRecord factoryRecord = connectionRepository.get(connectionKey);
         if (factoryRecord != null) {
            registerConnectionFactory (factoryRecord);
            factory = connectionMap.get (connectionKey);
         }
      }
      return factory;
   }

   public static void registerConnectionFactory (ConnectionFactoryRecord factoryRecord) throws Exception {
      String nioConnection = InMemoryRepository.getBrokerUrl ("nio");

      Constructor<?> constructor = Class.forName (factoryRecord.getConnectionClass ()).getConstructor (String.class, String.class, String.class);
      ConnectionFactory factory = (ConnectionFactory) constructor.newInstance (factoryRecord.getUser (), factoryRecord.getPassword (), nioConnection);
      connectionMap.put (factoryRecord.getConnectionKey (), factory);
   }

   public static void registerConnectionFactories () {
      Collection<ConnectionFactoryRecord> connections = connectionRepository.values ();
      for (ConnectionFactoryRecord record : connections) {
         try {
            registerConnectionFactory (record);
         } catch (Exception e) {
            e.printStackTrace ();
         }
      }
   }
}
