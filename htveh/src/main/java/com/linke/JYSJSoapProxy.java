package com.linke;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

public class JYSJSoapProxy implements JYSJSoap {
	private String _endpoint = null;
	private JYSJSoap jYSJSoap = null;

	public JYSJSoapProxy() {
		_initJYSJSoapProxy();
	}

	public JYSJSoapProxy(String endpoint) {
		_endpoint = endpoint;
		_initJYSJSoapProxy();
	}

	private void _initJYSJSoapProxy() {
		try {
			jYSJSoap = (new JYSJLocator()).getJYSJSoap();
			if (jYSJSoap != null) {
				if (_endpoint != null)
					((Stub) jYSJSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
				else
					_endpoint = (String) ((Stub) jYSJSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
			}

		} catch (ServiceException serviceException) {
		}
	}

	public String getEndpoint() {
		return _endpoint;
	}

	public void setEndpoint(String endpoint) {
		_endpoint = endpoint;
		if (jYSJSoap != null)
			((Stub) jYSJSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);

	}

	public JYSJSoap getJYSJSoap() {
		if (jYSJSoap == null)
			_initJYSJSoapProxy();
		return jYSJSoap;
	}

	public String hello() throws RemoteException {
		if (jYSJSoap == null)
			_initJYSJSoapProxy();
		return jYSJSoap.hello();
	}

	public String queryObjectOut(String jkid, String queryXmlDoc) throws RemoteException {
		if (jYSJSoap == null)
			_initJYSJSoapProxy();
		return jYSJSoap.queryObjectOut(jkid, queryXmlDoc);
	}

	public String writeObjectOut(String jkid, String writeXmlDoc, String MD5, String MAC) throws RemoteException {
		if (jYSJSoap == null)
			_initJYSJSoapProxy();
		return jYSJSoap.writeObjectOut(jkid, writeXmlDoc, MD5, MAC);
	}

}