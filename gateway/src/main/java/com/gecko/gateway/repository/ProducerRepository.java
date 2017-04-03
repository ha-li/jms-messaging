package com.gecko.gateway.repository;

import com.gecko.gateway.producer.AbstractProducer;
import com.gecko.gateway.producer.Producer;
import com.gecko.message.repository.InMemoryRepository;
import com.thoughtworks.xstream.InitializationException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 03/28/17.
 */
public class ProducerRepository {

   private static Map<String, Producer> producerMap = new HashMap<String, Producer> ();

   private static Map<String, ProducerRecord> producerRepository = new HashMap<String, ProducerRecord> ();

   private static Map<String, ConnectionFactory> connectionMap = new HashMap<String, ConnectionFactory> ();

   private static String ENVIRONMENT = "dev";

   static {
      // save the connection factory, this could be put in the db
      // and handle like the producers, ie a connection db
      //String nioConnection = InMemoryRepository.getBrokerUrl("nio");
      ConnectionFactory connectionFactory = null;
      try {
         connectionFactory = ConnectionFactoryRepository.findConnectionFactory ("connection-factory");
      } catch (Exception e) {
         e.printStackTrace ();
         throw new InitializationException ("Exception while creating connection factory", e);
      }
      //ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin", nioConnection);
      //connectionMap.put ("connectionKey", connectionFactory);

      PooledConnectionFactory pooledFactory = new PooledConnectionFactory((ActiveMQConnectionFactory)connectionFactory);
      connectionMap.put("pooledConnectionKey", pooledFactory);

      // fake a database, repository is the db
      // TODO: replace with hibernate
      producerRepository.put (
              "default_producer",
              new ProducerRecord( "default_producer",
                                  "fictional-client-id",
                                  "Gecko.global.{env}.test.Queue",
                                  "org.apache.activemq.command.ActiveMQQueue",
                                  "com.gecko.gateway.producer.DefaultProducer",
                                  "pooledConnectionKey") );
   }

   public static AbstractProducer findProducer(String accessKey) throws Exception {
      // look key up in the database
      ProducerRecord producerRecord = producerRepository.get(accessKey);
      AbstractProducer producer = null;

      try {

         Constructor<?> constructor = Class.forName (producerRecord.getProducerClass ()).getConstructor (ConnectionFactory.class, Destination.class, String.class);
         Destination destination = createDestination (producerRecord.getDestination (), producerRecord.getDestinationClass () );
         ConnectionFactory factory = connectionMap.get(producerRecord.getConnectionFactoryKey ());
         producer = (AbstractProducer) constructor.newInstance ( factory, destination, producerRecord.getClientId () );
         producerMap.put (accessKey, producer);

      } catch (Exception e) {
         e.printStackTrace ();
         throw e;
      }

      return producer;
   }

   private static Destination createDestination(String destination, String destinationClass) throws Exception {
      String destinationStr = destination.replace("{env}", ENVIRONMENT);
      Constructor constructor = Class.forName(destinationClass).getConstructor(String.class);
      return (Destination) constructor.newInstance(destinationStr);
   }
}
