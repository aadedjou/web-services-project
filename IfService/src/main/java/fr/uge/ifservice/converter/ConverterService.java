/**
 * ConverterService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package fr.uge.ifservice.converter;

public interface ConverterService extends javax.xml.rpc.Service {
    public java.lang.String getConverterAddress();

    public fr.uge.ifservice.converter.Converter getConverter() throws javax.xml.rpc.ServiceException;

    public fr.uge.ifservice.converter.Converter getConverter(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
