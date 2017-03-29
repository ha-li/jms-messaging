package com.gecko.message.springified.repository;

import com.gecko.message.springified.producer.AbstractProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Assert;
import org.junit.Test;

import javax.jms.Destination;

/**
 * Unit Testing of producer lookup.
 * Created by hlieu on 03/28/17.
 */
public class ProducerRepositoryTest {

   @Test
   public void testProducerRepository_getProducer() throws Exception {
      AbstractProducer defaultProducer = ProducerRepository.findProducer("default_producer");
      Assert.assertNotNull(defaultProducer);
      Destination destination = defaultProducer.getDestination ();
      String application = defaultProducer.getApplicationId ();
      Assert.assertEquals(((ActiveMQQueue)destination).getPhysicalName (), "Gecko.global.dev.test.Queue");
      Assert.assertEquals(application, "fictional-client-id");
   }

   @Test(expected = NullPointerException.class)
   public void testProducerRepository_exception () throws Exception {
      AbstractProducer producer = ProducerRepository.findProducer ("does-not-exist");
   }
}