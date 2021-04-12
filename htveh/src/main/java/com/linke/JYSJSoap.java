/**
 * JYSJSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linke;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface JYSJSoap extends Remote {
    public String hello() throws RemoteException;
    public String queryObjectOut(String jkid, String queryXmlDoc) throws RemoteException;
    public String writeObjectOut(String jkid, String writeXmlDoc, String MD5, String MAC) throws RemoteException;
}
