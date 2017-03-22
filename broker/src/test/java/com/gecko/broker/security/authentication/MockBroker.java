package com.gecko.broker.security.authentication;

import org.apache.activemq.broker.Broker;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.EmptyBroker;

/**
 * Created by hlieu on 03/21/17.
 */
public class MockBroker extends EmptyBroker {

   @Override
   public ConnectionContext getAdminConnectionContext () {
      return new ConnectionContext ();
   }
}
