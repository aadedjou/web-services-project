package fr.uge.ifservice.converter;

public class ConverterProxy implements fr.uge.ifservice.converter.Converter {
  private String _endpoint = null;
  private fr.uge.ifservice.converter.Converter converter = null;
  
  public ConverterProxy() {
    _initConverterProxy();
  }
  
  public ConverterProxy(String endpoint) {
    _endpoint = endpoint;
    _initConverterProxy();
  }
  
  private void _initConverterProxy() {
    try {
      converter = (new fr.uge.ifservice.converter.ConverterServiceLocator()).getConverter();
      if (converter != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)converter)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)converter)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (converter != null)
      ((javax.xml.rpc.Stub)converter)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public fr.uge.ifservice.converter.Converter getConverter() {
    if (converter == null)
      _initConverterProxy();
    return converter;
  }
  
  public float convert(java.lang.String currentCurrency, java.lang.String targetCurrency, float amount) throws java.rmi.RemoteException{
    if (converter == null)
      _initConverterProxy();
    return converter.convert(currentCurrency, targetCurrency, amount);
  }
  
  
}