
package com.landray.kmss.third.ekp.java.oms.in.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getElementsBaseInfoV2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getElementsBaseInfoV2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://out.webservice.organization.sys.kmss.landray.com/}sysSynchroGetOrgBaseInfoContextV2" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getElementsBaseInfoV2", propOrder = {
    "arg0"
})
public class GetElementsBaseInfoV2 {

    protected SysSynchroGetOrgBaseInfoContextV2 arg0;

    /**
     * Gets the value of the arg0 property.
     * 
     * @return
     *     possible object is
     *     {@link SysSynchroGetOrgBaseInfoContextV2 }
     *     
     */
    public SysSynchroGetOrgBaseInfoContextV2 getArg0() {
        return arg0;
    }

    /**
     * Sets the value of the arg0 property.
     * 
     * @param value
     *     allowed object is
     *     {@link SysSynchroGetOrgBaseInfoContextV2 }
     *     
     */
    public void setArg0(SysSynchroGetOrgBaseInfoContextV2 value) {
        this.arg0 = value;
    }

}
