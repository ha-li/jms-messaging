package com.gecko.gateway.repository;

/**
 * Created by hlieu on 04/2/17.
 */
public class ConnectionFactoryRecord {
   private String connectionKey;
   private String connectionClass;
   private String user;
   private String password;

   public ConnectionFactoryRecord (String connectionKey,
                                   String connectionClass,
                                   String user,
                                   String password) {
      this.connectionKey = connectionKey;
      this.connectionClass = connectionClass;
      this.user = user;
      this.password = password;
   }

   public String getConnectionKey () {
      return connectionKey;
   }

   public void setConnectionKey (String connectionKey) {
      this.connectionKey = connectionKey;
   }

   public String getConnectionClass () {
      return connectionClass;
   }

   public void setConnectionClass (String connectionClass) {
      this.connectionClass = connectionClass;
   }

   public String getUser () {
      return user;
   }

   public void setUser (String user) {
      this.user = user;
   }

   public String getPassword () {
      return password;
   }

   public void setPassword (String password) {
      this.password = password;
   }
}
