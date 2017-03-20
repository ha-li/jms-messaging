package com.gecko.message.complex;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Created by hlieu on 03/1/17.
 */
public interface SessionProvider {
   Session session (Connection connection) throws JMSException;
}
