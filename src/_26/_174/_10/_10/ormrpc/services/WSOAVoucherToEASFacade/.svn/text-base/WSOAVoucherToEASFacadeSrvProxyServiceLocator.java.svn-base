/**
 * WSOAVoucherToEASFacadeSrvProxyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade;

public class WSOAVoucherToEASFacadeSrvProxyServiceLocator extends org.apache.axis.client.Service implements _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxyService {

    public WSOAVoucherToEASFacadeSrvProxyServiceLocator() {
    }


    public WSOAVoucherToEASFacadeSrvProxyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WSOAVoucherToEASFacadeSrvProxyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WSOAVoucherToEASFacade
    private java.lang.String WSOAVoucherToEASFacade_address = "http://10.10.174.200:56898/ormrpc/services/WSOAVoucherToEASFacade";

    public java.lang.String getWSOAVoucherToEASFacadeAddress() {
        return WSOAVoucherToEASFacade_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WSOAVoucherToEASFacadeWSDDServiceName = "WSOAVoucherToEASFacade";

    public java.lang.String getWSOAVoucherToEASFacadeWSDDServiceName() {
        return WSOAVoucherToEASFacadeWSDDServiceName;
    }

    public void setWSOAVoucherToEASFacadeWSDDServiceName(java.lang.String name) {
        WSOAVoucherToEASFacadeWSDDServiceName = name;
    }

    public _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxy getWSOAVoucherToEASFacade() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WSOAVoucherToEASFacade_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWSOAVoucherToEASFacade(endpoint);
    }

    public _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxy getWSOAVoucherToEASFacade(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSoapBindingStub _stub = new _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSoapBindingStub(portAddress, this);
            _stub.setPortName(getWSOAVoucherToEASFacadeWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWSOAVoucherToEASFacadeEndpointAddress(java.lang.String address) {
        WSOAVoucherToEASFacade_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (_26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxy.class.isAssignableFrom(serviceEndpointInterface)) {
                _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSoapBindingStub _stub = new _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSoapBindingStub(new java.net.URL(WSOAVoucherToEASFacade_address), this);
                _stub.setPortName(getWSOAVoucherToEASFacadeWSDDServiceName());
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
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("WSOAVoucherToEASFacade".equals(inputPortName)) {
            return getWSOAVoucherToEASFacade();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://10.10.174.200:56898/ormrpc/services/WSOAVoucherToEASFacade", "WSOAVoucherToEASFacadeSrvProxyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://10.10.174.200:56898/ormrpc/services/WSOAVoucherToEASFacade", "WSOAVoucherToEASFacade"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WSOAVoucherToEASFacade".equals(portName)) {
            setWSOAVoucherToEASFacadeEndpointAddress(address);
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
