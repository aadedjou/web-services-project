/**
 * Bank.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.uge.ifservice.bank;

public interface Bank extends java.rmi.Remote {
    public void credit(java.lang.String clientName, double amount) throws java.rmi.RemoteException;
    public void debit(java.lang.String clientName, double amount) throws java.rmi.RemoteException;
    public boolean clientCanBuy(java.lang.String clientName, double amount) throws java.rmi.RemoteException;
    public java.lang.String getClientAccountInformation(java.lang.String clientName) throws java.rmi.RemoteException;
}
