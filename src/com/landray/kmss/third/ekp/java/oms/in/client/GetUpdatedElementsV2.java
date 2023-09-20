
package com.landray.kmss.third.ekp.java.oms.in.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUpdatedElementsV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUpdatedElementsV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroGetOrgInfoContextV2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUpdatedElementsV2", propOrder = {
    "arg0"
})
public class GetUpdatedElementsV2 {

    protected SysSynchroGetOrgInfoContextV2 arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link SysSynchroGetOrgInfoContextV2 }
     *     
     */
    public SysSynchroGetOrgInfoContextV2 getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SysSynchroGetOrgInfoContextV2 }
     *     
     */
    public void setArg0(SysSynchroGetOrgInfoContextV2 value) {
        this.arg0 = value;
    }

}
