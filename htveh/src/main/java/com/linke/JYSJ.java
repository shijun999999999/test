/**
 * JYSJ.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linke;

import java.net.URL;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface JYSJ extends Service {
	public String getJYSJSoapAddress();

	public JYSJSoap getJYSJSoap() throws ServiceException;

	public JYSJSoap getJYSJSoap(URL portAddress) throws ServiceException;
}
