package com.gecko.gateway;

/**
 * Created by hlieu on 04/2/17.
 */
public class Gateway {

   /* the static instance that is the gateway to the jms broker */
   private static Gateway instance;

   static {
      instance = new Gateway ();
   }

   public static Gateway getInstance () {
      if (instance == null) {
         instance = new Gateway ();
      }
      return instance;
   }

   public void registerConnectionFactory () {

   }
}
