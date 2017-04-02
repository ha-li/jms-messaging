package com.gecko.gateway.repository;

/**
 * Created by hlieu on 03/28/17.
 */
public class ProducerRecord {
   private String accessKey;
   private Integer version;
   private String clientId;
   private String destination;
   private String destinationClass;
   private String producerClass;
   private String connectionFactoryKey;

   public ProducerRecord (String access, String clientId,
          String destination, String destinationClass,
          String producerClass, String connectionKey) {

      this.accessKey = access;
      this.version = 0;
      this.clientId = clientId;
      this.destination = destination;
      this.destinationClass = destinationClass;
      this.producerClass = producerClass;
      this.connectionFactoryKey = connectionKey;
   }

   public String getAccessKey () {
      return accessKey;
   }

   public void setAccessKey (String accessKey) {
      this.accessKey = accessKey;
   }

   public Integer getVersion () {
      return version;
   }

   public void setVersion (Integer version) {
      this.version = version;
   }

   public String getClientId () {
      return clientId;
   }

   public void setClientId (String clientId) {
      this.clientId = clientId;
   }

   public String getDestination () {
      return destination;
   }

   public void setDestination (String destination) {
      this.destination = destination;
   }

   public String getDestinationClass () {
      return destinationClass;
   }

   public void setDestinationClass (String destinationClass) {
      this.destinationClass = destinationClass;
   }

   public String getProducerClass () {
      return producerClass;
   }

   public void setProducerClass (String producerClass) {
      this.producerClass = producerClass;
   }

   public String getConnectionFactoryKey () {
      return connectionFactoryKey;
   }

   public void setConnectionFactoryKey (String connectionFactoryKey) {
      this.connectionFactoryKey = connectionFactoryKey;
   }
}
