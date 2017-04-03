package com.gecko.gateway.repository;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

/**
 * Created by hlieu on 04/2/17.
 */
public class ConnectionFactoryRepositoryTest {


   @Test
   public void test_get () {
      ConnectionFactoryRecord record = ConnectionFactoryRepository.get ("connection-factory");
      Assert.assertNotNull(record);
      Assert.assertEquals ("admin", record.getUser());
      Assert.assertEquals ("admin", record.getPassword());
      Assert.assertEquals ("org.apache.activemq.ActiveMQConnectionFactory", record.getConnectionClass ());
      Assert.assertEquals ("connection-factory", record.getConnectionKey ());
   }

   @Test
   public void test_allConnections () {
      Collection<ConnectionFactoryRecord> records = ConnectionFactoryRepository.allConnectionFactories ();
      Assert.assertEquals(1, records.size ());
   }
}