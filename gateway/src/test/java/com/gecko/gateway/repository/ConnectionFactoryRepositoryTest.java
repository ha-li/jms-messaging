package com.gecko.gateway.repository;

import org.junit.Assert;
import org.junit.Test;

import javax.jms.ConnectionFactory;
import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by hlieu on 04/2/17.
 */
public class ConnectionFactoryRepositoryTest {

   @Test
   public void test () throws Exception {
      ConnectionFactoryRecord record = new ConnectionFactoryRecord(
         "connection-factory",
         "org.apache.activemq.ActiveMQConnectionFactory",
         "admin",
         "admin"
      );
      ConnectionFactoryRepository.registerConnectionFactory (record);
   }

   @Test
   public void test_registerConnections () throws Exception {
      ConnectionFactoryRepository.registerConnectionFactories ();
      ConnectionFactory factory = ConnectionFactoryRepository.findConnectionFactory ("connection-factory");
      Assert.assertNotNull (factory);
   }

   @Test
   public void test_findCOnnectionFactory () throws Exception {
      // reflect to get the connectionMap
      Field map = ConnectionFactoryRepository.class.getDeclaredField("connectionMap");
      map.setAccessible (Boolean.TRUE);
      // set it to new hash map ... basically delete any existing connectionfactories
      map.set(null, new HashMap<String, ConnectionFactory> ());

      // force execution of 1st if block
      ConnectionFactory factory = ConnectionFactoryRepository.findConnectionFactory ("connection-factory");
      Assert.assertNotNull (factory);
   }

}