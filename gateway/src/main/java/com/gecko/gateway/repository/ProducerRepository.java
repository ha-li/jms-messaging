package com.gecko.gateway.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hlieu on 03/28/17.
 */
public class ProducerRepository {

   private static Map<String, ProducerRecord> producerRepository = new HashMap<String, ProducerRecord> ();

   static {

      // fake a database, repository is the db
      // TODO: replace with actual db
      producerRepository.put (
          "default_producer",
          new ProducerRecord( "default_producer",
                              "fictional-client-id",
                              "Gecko.global.{env}.test.Queue",
                              "org.apache.activemq.command.ActiveMQQueue",
                              "com.gecko.gateway.producer.DefaultProducer",
                              "connection-factory") );
   }

   public static Collection<ProducerRecord> allProducers () {
      return producerRepository.values ();
   }

   public static ProducerRecord findProducer (String accessKey) {
      return producerRepository.get(accessKey);
   }
}
