/*******************************************************************************
* Copyright Regione Piemonte - 2023
* SPDX-License-Identifier: EUPL-1.2
******************************************************************************/


package it.csi.dma.dmairidecache;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.dma.dmairidecache package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IdentificaUserPasswordPinResponse_QNAME = new QName("http://dmairidecache.dma.csi.it", "identificaUserPasswordPinResponse");
    private final static QName _IdentificaUserPasswordResponse_QNAME = new QName("http://dmairidecache.dma.csi.it", "identificaUserPasswordResponse");
    private final static QName _IdentificaUserPasswordPin_QNAME = new QName("http://dmairidecache.dma.csi.it", "identificaUserPasswordPin");
    private final static QName _IdentificaUserPassword_QNAME = new QName("http://dmairidecache.dma.csi.it", "identificaUserPassword");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.dma.dmairidecache
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordRicevuta }
     * 
     */
    public IdentificaUserPasswordRicevuta createIdentificaUserPasswordRicevuta() {
        return new IdentificaUserPasswordRicevuta();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPinRicevuta }
     * 
     */
    public IdentificaUserPasswordPinRicevuta createIdentificaUserPasswordPinRicevuta() {
        return new IdentificaUserPasswordPinRicevuta();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordResponse }
     * 
     */
    public IdentificaUserPasswordResponse createIdentificaUserPasswordResponse() {
        return new IdentificaUserPasswordResponse();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPinResponse }
     * 
     */
    public IdentificaUserPasswordPinResponse createIdentificaUserPasswordPinResponse() {
        return new IdentificaUserPasswordPinResponse();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPin }
     * 
     */
    public IdentificaUserPasswordPin createIdentificaUserPasswordPin() {
        return new IdentificaUserPasswordPin();
    }

    /**
     * Create an instance of {@link IdentificaUserPassword }
     * 
     */
    public IdentificaUserPassword createIdentificaUserPassword() {
        return new IdentificaUserPassword();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordRicevuta.IdentitaIride }
     * 
     */
    public IdentificaUserPasswordRicevuta.IdentitaIride createIdentificaUserPasswordRicevutaIdentitaIride() {
        return new IdentificaUserPasswordRicevuta.IdentitaIride();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordRichiesta }
     * 
     */
    public IdentificaUserPasswordRichiesta createIdentificaUserPasswordRichiesta() {
        return new IdentificaUserPasswordRichiesta();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPinRichiesta }
     * 
     */
    public IdentificaUserPasswordPinRichiesta createIdentificaUserPasswordPinRichiesta() {
        return new IdentificaUserPasswordPinRichiesta();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPinRicevuta.IdentitaIride }
     * 
     */
    public IdentificaUserPasswordPinRicevuta.IdentitaIride createIdentificaUserPasswordPinRicevutaIdentitaIride() {
        return new IdentificaUserPasswordPinRicevuta.IdentitaIride();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordResponse.IdentitaIride }
     * 
     */
    public IdentificaUserPasswordResponse.IdentitaIride createIdentificaUserPasswordResponseIdentitaIride() {
        return new IdentificaUserPasswordResponse.IdentitaIride();
    }

    /**
     * Create an instance of {@link IdentificaUserPasswordPinResponse.IdentitaIride }
     * 
     */
    public IdentificaUserPasswordPinResponse.IdentitaIride createIdentificaUserPasswordPinResponseIdentitaIride() {
        return new IdentificaUserPasswordPinResponse.IdentitaIride();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificaUserPasswordPinResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmairidecache.dma.csi.it", name = "identificaUserPasswordPinResponse")
    public JAXBElement<IdentificaUserPasswordPinResponse> createIdentificaUserPasswordPinResponse(IdentificaUserPasswordPinResponse value) {
        return new JAXBElement<IdentificaUserPasswordPinResponse>(_IdentificaUserPasswordPinResponse_QNAME, IdentificaUserPasswordPinResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificaUserPasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmairidecache.dma.csi.it", name = "identificaUserPasswordResponse")
    public JAXBElement<IdentificaUserPasswordResponse> createIdentificaUserPasswordResponse(IdentificaUserPasswordResponse value) {
        return new JAXBElement<IdentificaUserPasswordResponse>(_IdentificaUserPasswordResponse_QNAME, IdentificaUserPasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificaUserPasswordPin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmairidecache.dma.csi.it", name = "identificaUserPasswordPin")
    public JAXBElement<IdentificaUserPasswordPin> createIdentificaUserPasswordPin(IdentificaUserPasswordPin value) {
        return new JAXBElement<IdentificaUserPasswordPin>(_IdentificaUserPasswordPin_QNAME, IdentificaUserPasswordPin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentificaUserPassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://dmairidecache.dma.csi.it", name = "identificaUserPassword")
    public JAXBElement<IdentificaUserPassword> createIdentificaUserPassword(IdentificaUserPassword value) {
        return new JAXBElement<IdentificaUserPassword>(_IdentificaUserPassword_QNAME, IdentificaUserPassword.class, null, value);
    }

}
