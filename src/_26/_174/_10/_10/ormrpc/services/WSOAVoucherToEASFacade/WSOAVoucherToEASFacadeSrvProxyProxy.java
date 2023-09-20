package _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade;

public class WSOAVoucherToEASFacadeSrvProxyProxy implements _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxy {
  private String _endpoint = null;
  private _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxy wSOAVoucherToEASFacadeSrvProxy = null;
  
  public WSOAVoucherToEASFacadeSrvProxyProxy() {
    _initWSOAVoucherToEASFacadeSrvProxyProxy();
  }
  
  public WSOAVoucherToEASFacadeSrvProxyProxy(String endpoint) {
    _endpoint = endpoint;
    _initWSOAVoucherToEASFacadeSrvProxyProxy();
  }
  
  private void _initWSOAVoucherToEASFacadeSrvProxyProxy() {
    try {
      wSOAVoucherToEASFacadeSrvProxy = (new _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxyServiceLocator()).getWSOAVoucherToEASFacade();
      if (wSOAVoucherToEASFacadeSrvProxy != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)wSOAVoucherToEASFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)wSOAVoucherToEASFacadeSrvProxy)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (wSOAVoucherToEASFacadeSrvProxy != null)
      ((javax.xml.rpc.Stub)wSOAVoucherToEASFacadeSrvProxy)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public _26._174._10._10.ormrpc.services.WSOAVoucherToEASFacade.WSOAVoucherToEASFacadeSrvProxy getWSOAVoucherToEASFacadeSrvProxy() {
    if (wSOAVoucherToEASFacadeSrvProxy == null)
      _initWSOAVoucherToEASFacadeSrvProxyProxy();
    return wSOAVoucherToEASFacadeSrvProxy;
  }
  
  public java.lang.String voucherSync(java.lang.String data) throws java.rmi.RemoteException, oavouchertoeasfacade.client.WSInvokeException{
    if (wSOAVoucherToEASFacadeSrvProxy == null)
      _initWSOAVoucherToEASFacadeSrvProxyProxy();
    return wSOAVoucherToEASFacadeSrvProxy.voucherSync(data);
  }
  
  
}