/**
 * IKmReviewWebserviceServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.third.ding.webclient;

import com.landray.kmss.third.ding.model.DingConfig;

public class IKmReviewWebserviceServiceServiceLocator extends org.apache.axis.client.Service implements com.landray.kmss.third.ding.webclient.IKmReviewWebserviceServiceService {

    public IKmReviewWebserviceServiceServiceLocator() {
    }


    public IKmReviewWebserviceServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public IKmReviewWebserviceServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    
    // Use to get a proxy class for IKmReviewWebserviceServicePort
//    private java.lang.String IKmReviewWebserviceServicePort_address = "http://localhost/ding/sys/webservice/kmReviewWebserviceService";
    private java.lang.String IKmReviewWebserviceServicePort_address = DingConfig.newInstance().getDingDomain()+"/sys/webservice/kmReviewWebserviceService";

    @Override
    public java.lang.String getIKmReviewWebserviceServicePortAddress() {
        return IKmReviewWebserviceServicePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String IKmReviewWebserviceServicePortWSDDServiceName = "IKmReviewWebserviceServicePort";

    public java.lang.String getIKmReviewWebserviceServicePortWSDDServiceName() {
        return IKmReviewWebserviceServicePortWSDDServiceName;
    }

    public void setIKmReviewWebserviceServicePortWSDDServiceName(java.lang.String name) {
        IKmReviewWebserviceServicePortWSDDServiceName = name;
    }

    @Override
    public com.landray.kmss.third.ding.webclient.IKmReviewWebserviceService getIKmReviewWebserviceServicePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(IKmReviewWebserviceServicePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getIKmReviewWebserviceServicePort(endpoint);
    }

    @Override
    public com.landray.kmss.third.ding.webclient.IKmReviewWebserviceService getIKmReviewWebserviceServicePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.landray.kmss.third.ding.webclient.IKmReviewWebserviceServiceServiceSoapBindingStub _stub = new com.landray.kmss.third.ding.webclient.IKmReviewWebserviceServiceServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getIKmReviewWebserviceServicePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setIKmReviewWebserviceServicePortEndpointAddress(java.lang.String address) {
        IKmReviewWebserviceServicePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.landray.kmss.third.ding.webclient.IKmReviewWebserviceService.class.isAssignableFrom(serviceEndpointInterface)) {
                com.landray.kmss.third.ding.webclient.IKmReviewWebserviceServiceServiceSoapBindingStub _stub = new com.landray.kmss.third.ding.webclient.IKmReviewWebserviceServiceServiceSoapBindingStub(new java.net.URL(IKmReviewWebserviceServicePort_address), this);
                _stub.setPortName(getIKmReviewWebserviceServicePortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
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
        java.lang.String inputPortName = portName.getLocalPart();
        if ("IKmReviewWebserviceServicePort".equals(inputPortName)) {
            return getIKmReviewWebserviceServicePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://webservice.review.km.kmss.landray.com/", "IKmReviewWebserviceServiceService");
    }

    private java.util.HashSet ports = null;

    @Override
    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://webservice.review.km.kmss.landray.com/", "IKmReviewWebserviceServicePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("IKmReviewWebserviceServicePort".equals(portName)) {
            setIKmReviewWebserviceServicePortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
