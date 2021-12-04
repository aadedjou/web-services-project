/**
 * Converter.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.uge.ifservice.converter;

public interface Converter extends java.rmi.Remote {
    public float convert(java.lang.String currentCurrency, java.lang.String targetCurrency, float amount) throws java.rmi.RemoteException;
}
