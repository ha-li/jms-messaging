package com.gecko.message.springified.repository;

import com.gecko.message.springified.producer.AbstractProducer;
import com.gecko.message.springified.producer.Producer;

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

   private static String ENVIRONMENT = "dev";

   static {
      // fake a database, repository is the db
      // TODO: replace with hibernate
      producerRepository.put (
              "default_producer",
              new ProducerRecord( "default_producer",
                                  "fictional-client-id",
                                  "Gecko.global.{env}.test.Queue",
                                  "org.apache.activemq.command.ActiveMQQueue",
                                  "com.gecko.message.springified.producer.DefaultProducer",
                                  "connection") );
   }

   public static AbstractProducer findProducer(String accessKey) throws Exception {
      // look key up in the database
      ProducerRecord producerRecord = producerRepository.get(accessKey);
      AbstractProducer producer = null;

      try {

         Constructor<?> constructor = Class.forName (producerRecord.getProducerClass ()).getConstructor (Destination.class, String.class);
         Destination destination = createDestination (producerRecord.getDestination (), producerRecord.getDestinationClass () );
         producer = (AbstractProducer) constructor.newInstance ( destination, producerRecord.getClientId () );
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
