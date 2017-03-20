package com.gecko.message.jms.producer;

import com.gecko.message.jms.SessionProvider;

import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by hlieu on 03/19/17.
 */
public interface SecureProducer {
   boolean sendMessage (Session session, String destination, String message);
}
