
package com.landray.kmss.third.ekp.java.oms.in.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUpdatedElementsByTokenResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUpdatedElementsByTokenResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroOrgTokenResult" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUpdatedElementsByTokenResponse", propOrder = {
    "_return"
})
public class GetUpdatedElementsByTokenResponse {

    @XmlElement(name = "return")
    protected SysSynchroOrgTokenResult _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link SysSynchroOrgTokenResult }
     *     
     */
    public SysSynchroOrgTokenResult getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link SysSynchroOrgTokenResult }
     *     
     */
    public void setReturn(SysSynchroOrgTokenResult value) {
        this._return = value;
    }

}
