package com.gecko.gateway;

import com.gecko.gateway.repository.ConnectionFactoryRecord;
import com.gecko.gateway.repository.ConnectionFactoryRepository;
import com.gecko.message.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.ConnectionFactory;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 04/2/17.
 */
public class Gateway {

   /* the static instance that is the gateway to the jms broker */
   private static Gateway instance;
   private Map<String, ConnectionFactory> connectionMap = new HashMap<String, ConnectionFactory> ();

   static {
      instance = new Gateway ();
   }

   public static Gateway getInstance () {
      if (instance == null) {
         instance = new Gateway ();
      }
      return instance;
   }

   public void start () {
      registerConnectionFactories ();
      registerProducers ();
   }


   public ConnectionFactory findConnectionFactory (String connectionKey) throws Exception {
      ConnectionFactory factory = connectionMap.get (connectionKey);
      if (factory == null) {
         ConnectionFactoryRecord factoryRecord = ConnectionFactoryRepository.get(connectionKey);
         if (factoryRecord != null) {
            registerConnectionFactory (factoryRecord);
            factory = connectionMap.get (connectionKey);
         }
      }
      return factory;
   }

   public void registerConnectionFactory (ConnectionFactoryRecord factoryRecord) throws Exception {
      String nioConnection = InMemoryRepository.getBrokerUrl ("nio");

      Constructor<?> constructor = Class.forName (factoryRecord.getConnectionClass ()).getConstructor (String.class, String.class, String.class);
      ConnectionFactory factory = (ConnectionFactory) constructor.newInstance (factoryRecord.getUser (), factoryRecord.getPassword (), nioConnection);
      PooledConnectionFactory pooledFactory = new PooledConnectionFactory ((ActiveMQConnectionFactory) factory);
      connectionMap.put (factoryRecord.getConnectionKey (), pooledFactory);
   }

   public void registerConnectionFactories () {
      Collection<ConnectionFactoryRecord> allFactories = ConnectionFactoryRepository.allConnectionFactories();
      for (ConnectionFactoryRecord record : allFactories) {
         try {
            registerConnectionFactory (record);
         } catch (Exception e) {
            e.printStackTrace ();
         }
      }
   }



   public void registerProducers () {

   }


}
