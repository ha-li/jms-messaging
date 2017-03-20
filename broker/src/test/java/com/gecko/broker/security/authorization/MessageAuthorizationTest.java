package com.gecko.broker.security.authorization;

import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.Message;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by hlieu on 03/19/17.
 */
public class MessageAuthorizationTest {

   private MessageAuthorization resourceUnderTest = new MessageAuthorization();

   @Test
   public void testMessageAuthorization () throws IOException {

      ConnectionContext context = new ConnectionContext ();
      Message message = new MockMessage ();
      String uuid = UUID.randomUUID ().toString();
      message.setProperty("TransactionId", uuid);
      boolean consumable = resourceUnderTest.isAllowedToConsume (context, message);
      Assert.assertTrue (consumable);
      Assert.assertEquals (message.getProperty("TransactionId"), uuid);
   }

   @Test
   public void testMessageAuthorization_fail () throws IOException {
      ConnectionContext context = new ConnectionContext ();
      Message message = new MockMessage ();
      boolean consumable = resourceUnderTest.isAllowedToConsume (context, message);
      Assert.assertFalse (consumable);
   }

   @Test
   public void testMessageAuthorization_null_fail () throws IOException {
      ConnectionContext context = new ConnectionContext ();
      Message message = null;
      boolean consumable = resourceUnderTest.isAllowedToConsume (context, message);
      Assert.assertFalse (consumable);
   }
}
