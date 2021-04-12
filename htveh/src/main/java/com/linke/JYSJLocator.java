/**
 * JYSJLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.linke;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;
import org.apache.axis.client.Stub;

@SuppressWarnings({ "rawtypes" })
public class JYSJLocator extends Service implements JYSJ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JYSJLocator() {
	}

	public JYSJLocator(EngineConfiguration config) {
		super(config);
	}

	public JYSJLocator(String wsdlLoc, QName sName) throws ServiceException {
		super(wsdlLoc, sName);
	}

	// Use to get a proxy class for JYSJSoap
	// private String JYSJSoap_address = "https://172.168.1.4/sjjh2021test/jysj.asmx";
	private String JYSJSoap_address = "http://localhost:9999/sjjh2021test/jysj.asmx";

	public String getJYSJSoapAddress() {
		return JYSJSoap_address;
	}

	// The WSDD service name defaults to the port name.
	private String JYSJSoapWSDDServiceName = "JYSJSoap";

	public String getJYSJSoapWSDDServiceName() {
		return JYSJSoapWSDDServiceName;
	}

	public void setJYSJSoapWSDDServiceName(String name) {
		JYSJSoapWSDDServiceName = name;
	}

	public JYSJSoap getJYSJSoap() throws ServiceException {
		URL endpoint;
		try {
			endpoint = new URL(JYSJSoap_address);
		} catch (MalformedURLException e) {
			throw new ServiceException(e);
		}
		return getJYSJSoap(endpoint);
	}

	public JYSJSoap getJYSJSoap(URL portAddress) throws ServiceException {
		try {
			JYSJSoapStub _stub = new JYSJSoapStub(portAddress, this);
			_stub.setPortName(getJYSJSoapWSDDServiceName());
			return _stub;
		} catch (AxisFault e) {
			return null;
		}
	}

	public void setJYSJSoapEndpointAddress(String address) {
		JYSJSoap_address = address;
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
		try {
			if (JYSJSoap.class.isAssignableFrom(serviceEndpointInterface)) {
				JYSJSoapStub _stub = new JYSJSoapStub(new URL(JYSJSoap_address), this);
				_stub.setPortName(getJYSJSoapWSDDServiceName());
				return _stub;
			}
		} catch (Throwable t) {
			throw new ServiceException(t);
		}
		throw new ServiceException("There is no stub implementation for the interface:  "
				+ (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("JYSJSoap".equals(inputPortName)) {
			return getJYSJSoap();
		} else {
			Remote _stub = getPort(serviceEndpointInterface);
			((Stub) _stub).setPortName(portName);
			return _stub;
		}
	}

	public QName getServiceName() {
		return new QName("http://linke.com/", "JYSJ");
	}

	private HashSet<QName> ports = null;

	public Iterator getPorts() {
		if (ports == null) {
			ports = new HashSet<QName>();
			ports.add(new QName("http://linke.com/", "JYSJSoap"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName, String address) throws ServiceException {

		if ("JYSJSoap".equals(portName)) {
			setJYSJSoapEndpointAddress(address);
		} else { // Unknown Port Name
			throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(QName portName, String address) throws ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}

}
