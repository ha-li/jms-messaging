package com.gecko.gateway.repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by hlieu on 04/2/17.
 */
public class ConnectionFactoryRecordTest {

   private String connectionKey = "fake.connection.key";
   private String connectionClass = "com.gecko.fake.connection.class";
   private String user = "fake.user";
   private String password = "fake.password";

   private ConnectionFactoryRecord getDefault () {

      ConnectionFactoryRecord record = new ConnectionFactoryRecord
              (connectionKey, connectionClass,
               user, password);
      return record;
   }

   @Test
   public void test () {

      ConnectionFactoryRecord record = getDefault ();

      Assert.assertEquals (connectionKey, record.getConnectionKey ());
      Assert.assertEquals (connectionClass, record.getConnectionClass ());
      Assert.assertEquals (user, record.getUser ());
      Assert.assertEquals (password, record.getPassword ());
   }

   @Test
   public void test_setter () {

      ConnectionFactoryRecord record = getDefault ();
      record.setConnectionKey ( "differentKey" );
      record.setConnectionClass ("com.gecko.fake.class");
      record.setUser ("anotherUser");
      record.setPassword ("anotherPassword");

      Assert.assertEquals ("differentKey", record.getConnectionKey ());
      Assert.assertEquals ("com.gecko.fake.class", record.getConnectionClass ());
      Assert.assertEquals ("anotherUser", record.getUser ());
      Assert.assertEquals ("anotherPassword", record.getPassword ());

   }
}