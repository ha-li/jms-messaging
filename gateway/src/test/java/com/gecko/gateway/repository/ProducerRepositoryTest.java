package com.gecko.gateway.repository;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Unit Testing of producer lookup.
 * Created by hlieu on 03/28/17.
 */
public class ProducerRepositoryTest {

   @Test
   public void test_getProducer() throws Exception {
      ProducerRecord defaultProducer = ProducerRepository.findProducer("default_producer");
      Assert.assertNotNull(defaultProducer);
      Assert.assertEquals ("default_producer", defaultProducer.getAccessKey ());
      Assert.assertEquals ("fictional-client-id", defaultProducer.getClientId ());
      Assert.assertEquals ("Gecko.global.{env}.test.Queue", defaultProducer.getDestination ());
      Assert.assertEquals ("org.apache.activemq.command.ActiveMQQueue", defaultProducer.getDestinationClass ());
      Assert.assertEquals ("com.gecko.gateway.producer.DefaultProducer", defaultProducer.getProducerClass ());
      Assert.assertEquals ("connection-factory", defaultProducer.getConnectionFactoryKey ());
   }

   @Test
   public void test_getNullProducer () throws Exception {
      ProducerRecord producer = ProducerRepository.findProducer ("does-not-exist");
      Assert.assertNull (producer);
   }

   @Test
   public void test_allProducers () {
      Collection<ProducerRecord> all = ProducerRepository.allProducers ();
      Assert.assertEquals (1, all.size());
   }
}