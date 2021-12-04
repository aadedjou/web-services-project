package fr.uge.ifservice.bank;

public class BankProxy implements Bank {
  private String _endpoint = null;
  private Bank bank = null;
  
  public BankProxy() {
    _initBankProxy();
  }
  
  public BankProxy(String endpoint) {
    _endpoint = endpoint;
    _initBankProxy();
  }
  
  private void _initBankProxy() {
    try {
      bank = (new BankServiceLocator()).getBank();
      if (bank != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bank)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bank != null)
      ((javax.xml.rpc.Stub)bank)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public Bank getBank() {
    if (bank == null)
      _initBankProxy();
    return bank;
  }
  
  public void credit(java.lang.String clientName, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.credit(clientName, amount);
  }
  
  public void debit(java.lang.String clientName, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.debit(clientName, amount);
  }
  
  public boolean clientCanBuy(java.lang.String clientName, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.clientCanBuy(clientName, amount);
  }
  
  public java.lang.String getClientAccountInformation(java.lang.String clientName) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getClientAccountInformation(clientName);
  }
  
  
}