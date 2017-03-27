package com.gecko.broker.security.authentication;

import org.apache.activemq.broker.Connection;
import org.apache.activemq.broker.Connector;
import org.apache.activemq.broker.region.ConnectionStatistics;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ConnectionControl;
import org.apache.activemq.command.Response;

import java.io.IOException;

/**
 * Created by hlieu on 03/21/17.
 */
public class MockConnection implements Connection {

   private String remoteAddress;
   public String getRemoteAddress () {
      return remoteAddress;
   }

   public void setRemoteAddress (String remoteAddress) {
      this.remoteAddress = remoteAddress;
   }

   public void start (){}

   public void stop () {}

   public Connector getConnector () {
      throw new RuntimeException ("Not Implemented Yet");
   }

   public void dispatchSync (Command message) {
      throw new RuntimeException ("Not Implemented Yet");
   }

   public void dispatchAsync (Command command) {
      throw new RuntimeException ("Not Implemented Yet");
   }

   public Response service (Command command) {
      throw new RuntimeException ("Not Implemented Yet");
   }

   public void serviceException (Throwable error) {
      throw new RuntimeException ("Not Implemented Yet");
   }

   public boolean isSlow () {
      return true;
   }

   public boolean isBlocked () { return false; }

   public boolean isConnected () { return true; }

   public boolean isActive () { return true; }

   public int getDispatchQueueSize () { return 0; }

   public ConnectionStatistics getStatistics () {
      return null;
   }

   public boolean isManageable () { return true; }

   public void serviceExceptionAsync (IOException e) {

   }

   public String getConnectionId () {
      return "mockId";
   }

   public boolean isNetworkConnection () {
      return true;
   }

   public boolean isFaultTolerantConnection () {
      return true;
   }

   public void updateClient (ConnectionControl control) {

   }

   public int getActiveTransactionCount () {
      return 1;
   }

   public Long getOldestActiveTransactionDuration () {
      return 1000L;
   }
}
