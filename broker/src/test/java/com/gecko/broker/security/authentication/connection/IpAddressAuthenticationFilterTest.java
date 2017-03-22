package com.gecko.broker.security.authentication.connection;

import com.gecko.broker.security.authentication.MockBroker;
import com.gecko.broker.security.authentication.MockConnection;
import com.gecko.broker.security.authentication.MockConnectionContext;
import com.gecko.broker.security.authentication.connection.IpAddressAuthenticationFilter;
import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.command.ConnectionInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hlieu on 03/21/17.
 */
public class IpAddressAuthenticationFilterTest {

   private IpAddressAuthenticationFilter resourceUnderTest; // = new IpAddressAuthenticationFilter (null, null);

   @Before
   public void instantiateBrokerFilter () {
      List<String> allowedIpAddress = new ArrayList<String> ();
      allowedIpAddress.add( "127.0.0.1");
      Broker mockBroker = new MockBroker ();
      resourceUnderTest = new IpAddressAuthenticationFilter (mockBroker, allowedIpAddress);
   }

   @Test(expected = SecurityException.class)
   public void testLocalHost () throws Exception {
      ConnectionContext context = new MockConnectionContext ();
      MockConnection connection = new MockConnection ();
      connection.setRemoteAddress ("127.0.0.1:234");
      context.setConnection(connection);
      ConnectionInfo info = new ConnectionInfo ();
      resourceUnderTest.addConnection (context, info);
   }

   @Test
   public void testLocalHost_with_Protocol () throws Exception {
      ConnectionContext context = new MockConnectionContext ();
      MockConnection connection = new MockConnection ();
      connection.setRemoteAddress ("tcp://127.0.0.1:123");
      context.setConnection(connection);
      ConnectionInfo info = new ConnectionInfo ();
      resourceUnderTest.addConnection (context, info);
   }

}