/**
 * WSContext.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package client;

public class WSContext  implements java.io.Serializable {
    private int dbType;

    private java.lang.String dcName;

    private java.lang.String password;

    private java.lang.String sessionId;

    private java.lang.String slnName;

    private java.lang.String userName;

    public WSContext() {
    }

    public WSContext(
           int dbType,
           java.lang.String dcName,
           java.lang.String password,
           java.lang.String sessionId,
           java.lang.String slnName,
           java.lang.String userName) {
           this.dbType = dbType;
           this.dcName = dcName;
           this.password = password;
           this.sessionId = sessionId;
           this.slnName = slnName;
           this.userName = userName;
    }


    /**
     * Gets the dbType value for this WSContext.
     * 
     * @return dbType
     */
    public int getDbType() {
        return dbType;
    }


    /**
     * Sets the dbType value for this WSContext.
     * 
     * @param dbType
     */
    public void setDbType(int dbType) {
        this.dbType = dbType;
    }


    /**
     * Gets the dcName value for this WSContext.
     * 
     * @return dcName
     */
    public java.lang.String getDcName() {
        return dcName;
    }


    /**
     * Sets the dcName value for this WSContext.
     * 
     * @param dcName
     */
    public void setDcName(java.lang.String dcName) {
        this.dcName = dcName;
    }


    /**
     * Gets the password value for this WSContext.
     * 
     * @return password
     */
    public java.lang.String getPassword() {
        return password;
    }


    /**
     * Sets the password value for this WSContext.
     * 
     * @param password
     */
    public void setPassword(java.lang.String password) {
        this.password = password;
    }


    /**
     * Gets the sessionId value for this WSContext.
     * 
     * @return sessionId
     */
    public java.lang.String getSessionId() {
        return sessionId;
    }


    /**
     * Sets the sessionId value for this WSContext.
     * 
     * @param sessionId
     */
    public void setSessionId(java.lang.String sessionId) {
        this.sessionId = sessionId;
    }


    /**
     * Gets the slnName value for this WSContext.
     * 
     * @return slnName
     */
    public java.lang.String getSlnName() {
        return slnName;
    }


    /**
     * Sets the slnName value for this WSContext.
     * 
     * @param slnName
     */
    public void setSlnName(java.lang.String slnName) {
        this.slnName = slnName;
    }


    /**
     * Gets the userName value for this WSContext.
     * 
     * @return userName
     */
    public java.lang.String getUserName() {
        return userName;
    }


    /**
     * Sets the userName value for this WSContext.
     * 
     * @param userName
     */
    public void setUserName(java.lang.String userName) {
        this.userName = userName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSContext)) return false;
        WSContext other = (WSContext) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.dbType == other.getDbType() &&
            ((this.dcName==null && other.getDcName()==null) || 
             (this.dcName!=null &&
              this.dcName.equals(other.getDcName()))) &&
            ((this.password==null && other.getPassword()==null) || 
             (this.password!=null &&
              this.password.equals(other.getPassword()))) &&
            ((this.sessionId==null && other.getSessionId()==null) || 
             (this.sessionId!=null &&
              this.sessionId.equals(other.getSessionId()))) &&
            ((this.slnName==null && other.getSlnName()==null) || 
             (this.slnName!=null &&
              this.slnName.equals(other.getSlnName()))) &&
            ((this.userName==null && other.getUserName()==null) || 
             (this.userName!=null &&
              this.userName.equals(other.getUserName())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getDbType();
        if (getDcName() != null) {
            _hashCode += getDcName().hashCode();
        }
        if (getPassword() != null) {
            _hashCode += getPassword().hashCode();
        }
        if (getSessionId() != null) {
            _hashCode += getSessionId().hashCode();
        }
        if (getSlnName() != null) {
            _hashCode += getSlnName().hashCode();
        }
        if (getUserName() != null) {
            _hashCode += getUserName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSContext.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:client", "WSContext"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dbType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dbType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dcName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dcName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("password");
        elemField.setXmlName(new javax.xml.namespace.QName("", "password"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sessionId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sessionId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("slnName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "slnName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("userName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
