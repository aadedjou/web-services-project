package fr.uge.ifservice.bank;

public class BankProxy implements fr.uge.ifservice.bank.Bank {
  private String _endpoint = null;
  private fr.uge.ifservice.bank.Bank bank = null;
  
  public BankProxy() {
    _initBankProxy();
  }
  
  public BankProxy(String endpoint) {
    _endpoint = endpoint;
    _initBankProxy();
  }
  
  private void _initBankProxy() {
    try {
      bank = (new fr.uge.ifservice.bank.BankServiceLocator()).getBank();
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
  
  public fr.uge.ifservice.bank.Bank getBank() {
    if (bank == null)
      _initBankProxy();
    return bank;
  }
  
  public java.lang.String createAccount(java.lang.String pseudo, double cash) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.createAccount(pseudo, cash);
  }
  
  public void credit(java.lang.String clientName, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.credit(clientName, amount);
  }
  
  public boolean clientCanBuy(java.lang.String clientName, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.clientCanBuy(clientName, amount);
  }
  
  public void debit(java.lang.String clientName, double amount) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    bank.debit(clientName, amount);
  }
  
  public java.lang.String getClientAccountInformation(java.lang.String clientName) throws java.rmi.RemoteException{
    if (bank == null)
      _initBankProxy();
    return bank.getClientAccountInformation(clientName);
  }
  
  
}