/**
 * WSGLWebServiceFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.fssc.eas.webService;

import java.util.HashMap;
import java.util.Map;

import com.landray.kmss.fssc.eas.util.FsscEasUtil;
import com.landray.kmss.sys.authentication.util.StringUtil;

public class WSGLWebServiceFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements WSGLWebServiceFacadeSrvProxyService {

	public static Map<String,String> map =new HashMap<String,String>(); 
	 
    public WSGLWebServiceFacadeSrvProxyServiceLocator() {
    	try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public void init() throws Exception{
		map=FsscEasUtil.getSwitchValue(null);
    }

    public WSGLWebServiceFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSGLWebServiceFacadeSrvProxyServiceLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSGLWebServiceFacade
    private String WSGLWebServiceFacade_address = map.get("fdImportVoucherWsdlUrl");

    @Override
    public String getWSGLWebServiceFacadeAddress() {
        return WSGLWebServiceFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private String WSGLWebServiceFacadeWSDDServiceName = "WSGLWebServiceFacade";

    public String getWSGLWebServiceFacadeWSDDServiceName() {
        return WSGLWebServiceFacadeWSDDServiceName;
    }

    public void setWSGLWebServiceFacadeWSDDServiceName(String name) {
        WSGLWebServiceFacadeWSDDServiceName = name;
    }

    @Override
    public WSGLWebServiceFacadeSrvProxy getWSGLWebServiceFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
        	if(StringUtil.isNull(WSGLWebServiceFacade_address)) {
        		try {
					init();
				} catch (Exception e) {
					e.printStackTrace();
				}
        		WSGLWebServiceFacade_address=map.get("fdImportVoucherWsdlUrl");
        	}
            endpoint = new java.net.URL(WSGLWebServiceFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSGLWebServiceFacade(endpoint);
    }

    @Override
    public WSGLWebServiceFacadeSrvProxy getWSGLWebServiceFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            WSGLWebServiceFacadeSoapBindingStub _stub = new WSGLWebServiceFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSGLWebServiceFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSGLWebServiceFacadeEndpointAddress(String address) {
        WSGLWebServiceFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (WSGLWebServiceFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                WSGLWebServiceFacadeSoapBindingStub _stub = new WSGLWebServiceFacadeSoapBindingStub(new java.net.URL(WSGLWebServiceFacade_address), this);
                _stub.setPortName(getWSGLWebServiceFacadeWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("WSGLWebServiceFacade".equals(inputPortName)) {
            return getWSGLWebServiceFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName(map.get("fdImportVoucherWsdlUrl"), "WSGLWebServiceFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName(map.get("fdImportVoucherWsdlUrl"), "WSGLWebServiceFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("WSGLWebServiceFacade".equals(portName)) {
            setWSGLWebServiceFacadeEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
