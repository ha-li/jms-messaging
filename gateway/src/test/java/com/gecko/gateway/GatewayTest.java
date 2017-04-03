package com.gecko.gateway;

import com.gecko.gateway.producer.Producer;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.jms.ConnectionFactory;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by hlieu on 04/2/17.
 */
public class GatewayTest {

   @BeforeClass
   public static void setUp () {
      Gateway.getInstance();
      Gateway.getInstance().registerConnectionFactories ();
   }

   @Test
   public void test_getInstance () {
      Gateway gateway = Gateway.getInstance ();
      Assert.assertNotNull(gateway);
   }

   @Test
   public void test_registerConnectionFactories () throws Exception {

      ConnectionFactory factory = Gateway.getInstance ().findConnectionFactory ("connection-factory");
      Assert.assertNotNull (factory);
   }

   @Test
   public void test_findConnectionFactory () throws Exception {
      // reflect to get reset the connection map
      Field field = Gateway.class.getDeclaredField ("connectionMap");
      field.setAccessible (Boolean.TRUE);
      field.set(Gateway.getInstance(), new HashMap<String, ConnectionFactory> ());

      ConnectionFactory factory = Gateway.getInstance ().findConnectionFactory ("connection-factory");
      Assert.assertTrue (factory instanceof PooledConnectionFactory);
   }

   @Test
   public void test_registerProducers () {
      Gateway.getInstance ().registerProducers ();
      Producer producer = Gateway.getInstance ().findProducer ("default_producer");
      Assert.assertNotNull(producer);
   }

   @Test
   public void test_findProducer () throws Exception {
      Field field = Gateway.class.getDeclaredField ("producerMap");
      field.setAccessible (Boolean.TRUE);
      field.set(Gateway.getInstance(), new HashMap<String, Producer> ());

      Producer producer = Gateway.getInstance ().findProducer ("default_producer");
      Assert.assertNotNull (producer);
   }
}