package com.gecko.message.complex.producer;

import javax.jms.Session;

/**
 * Created by hlieu on 03/19/17.
 */
public interface SecureProducer {
   boolean sendMessage (Session session, String destination, String message);
}
