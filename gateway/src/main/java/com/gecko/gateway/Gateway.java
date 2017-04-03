package com.gecko.gateway;

import com.gecko.gateway.producer.AbstractProducer;
import com.gecko.gateway.producer.Producer;
import com.gecko.gateway.repository.ConnectionFactoryRecord;
import com.gecko.gateway.repository.ConnectionFactoryRepository;
import com.gecko.gateway.repository.ProducerRecord;
import com.gecko.gateway.repository.ProducerRepository;
import com.gecko.message.repository.InMemoryRepository;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
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
   private Map<String, Producer> producerMap = new HashMap<String, Producer> ();
   private static String ENVIRONMENT = "dev";

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

   private static Destination createDestination(String destination, String destinationClass) throws Exception {
      String destinationStr = destination.replace("{env}", ENVIRONMENT);
      Constructor constructor = Class.forName(destinationClass).getConstructor(String.class);
      return (Destination) constructor.newInstance(destinationStr);
   }

   public void registerProducer (ProducerRecord producerRecord) {
      try {
         Constructor<?> constructor = Class.forName (producerRecord.getProducerClass ()).getConstructor (ConnectionFactory.class, Destination.class, String.class);
         Destination destination = createDestination (producerRecord.getDestination (), producerRecord.getDestinationClass () );
         ConnectionFactory factory = connectionMap.get(producerRecord.getConnectionFactoryKey ());
         Producer producer = (AbstractProducer) constructor.newInstance (factory, destination, producerRecord.getClientId () );
         producerMap.put (producerRecord.getAccessKey (), producer);

      } catch (Exception e) {
         e.printStackTrace ();
      }
   }

   public void registerProducers () {
      Collection<ProducerRecord> allProducers = ProducerRepository.allProducers ();
      for (ProducerRecord producer : allProducers) {
         try {
            registerProducer(producer);
         } catch (Exception e) {
            e.printStackTrace ();
         }
      }
   }

   public Producer findProducer (String accessKey) {
      Producer producer = producerMap.get(accessKey);
      if (null == producer) {
         ProducerRecord record = ProducerRepository.findProducer (accessKey);
         if (record != null) {
            registerProducer (record);
            producer = producerMap.get (accessKey);
         }
      }
      return producer;
   }
}
