
package com.landray.kmss.third.ekp.java.notify.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;

/**
 * <p>Java class for notifyTodoRemoveContext complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="notifyTodoRemoveContext">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="appName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modelId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="modelName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="optType" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="param1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="param2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="targets" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="others" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notifyTodoRemoveContext", propOrder = {
    "appName",
    "key",
    "modelId",
    "modelName",
    "optType",
    "param1",
    "param2",
    "type",
		"targets",
		"others"
})
public class NotifyTodoRemoveContext {
	
	public static final int OPT_NOTIFY_TODO=1;
	
	public static final int OPT_NOTIFY_TODO_PERSON=2;

    protected String appName;
    protected String key;
    protected String modelId;
    protected String modelName;
    protected int optType;
    protected String param1;
    protected String param2;
    protected Integer type;
    protected String targets;
	protected String others;
	 

	/**
	 * Gets the value of the appName property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
    public String getAppName() {
        return appName;
    }

    /**
     * Sets the value of the appName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppName(String value) {
        this.appName = value;
    }

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the modelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelId() {
        return modelId;
    }

    /**
     * Sets the value of the modelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelId(String value) {
        this.modelId = value;
    }

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the optType property.
     * 
     */
    public int getOptType() {
        return optType;
    }

    /**
     * Sets the value of the optType property.
     * 
     */
    public void setOptType(int value) {
        this.optType = value;
    }

    /**
     * Gets the value of the param1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParam1() {
        return param1;
    }

    /**
     * Sets the value of the param1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParam1(String value) {
        this.param1 = value;
    }

    /**
     * Gets the value of the param2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParam2() {
        return param2;
    }

    /**
     * Sets the value of the param2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParam2(String value) {
        this.param2 = value;
    }
    
    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Gets the value of the targets property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargets() {
        return targets;
    }

    /**
     * Sets the value of the targets property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargets(String value) {
        this.targets = value;
    }

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}
	
	
	public JSONObject toJson(int opt) {
		JSONObject json = new JSONObject();
		json.accumulate("appName", this.getAppName());
		json.accumulate("key", this.getKey());
		json.accumulate("modelId", this.getModelId());
		json.accumulate("modelName", this.getModelName());
		json.accumulate("optType", this.getOptType());
		json.accumulate("param1", this.getParam1());
		json.accumulate("param2", this.getParam2());
		json.accumulate("others", this.getOthers());
		json.accumulate("targets", this.getTargets());
		json.accumulate("type", this.getType()); 
		json.accumulate("method", opt == 2 ? "setTodoDone" : "deleteTodo");
		return json;
	}

	public String generateMD5(int opt) {
		return MD5Util.getMD5String(StringUtil.getString(this.getAppName()) +
				StringUtil.getString(this.getKey()) +
						StringUtil.getString(this.getModelId()) +
						StringUtil.getString(this.getModelName()) +
				this.getOptType() +
						StringUtil.getString(this.getParam1()) +
						StringUtil.getString(this.getParam2()) +
						StringUtil.getString(this.getTargets()) +
				this.getType() + (opt == 2 ? "setTodoDone" : "deleteTodo"));
	}

	public String generateMD5(int opt, String notifyId) {
		return MD5Util.getMD5String(StringUtil.getString(notifyId) +
				StringUtil.getString(this.getAppName()) +
				StringUtil.getString(this.getKey()) +
				StringUtil.getString(this.getModelId()) +
				StringUtil.getString(this.getModelName()) +
				this.getOptType() +
				StringUtil.getString(this.getParam1()) +
				StringUtil.getString(this.getParam2()) +
				StringUtil.getString(this.getTargets()) +
				this.getType() + (opt == 2 ? "setTodoDone" : "deleteTodo"));
	}
}
