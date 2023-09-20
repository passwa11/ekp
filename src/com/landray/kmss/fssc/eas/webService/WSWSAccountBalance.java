/**
 * WSWSAccountBalance.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.fssc.eas.webService;

import com.landray.kmss.fssc.common.util.FsscNumberUtil;

public class WSWSAccountBalance  extends WSBean implements java.io.Serializable {
    private double debitFor;

    private double yearDebitFor;

    private double debitQty;

    private double yearDebitLocal;

    private double creditRpt;

    private double creditLocal;

    private String orgUnitNumber;

    private double yearDebitRpt;

    private double beginBalanceLocal;

    private int periodYear;

    private double creditQty;

    private double monthPnLRpt;

    private double yearPnLLocal;

    private int periodNumber;

    private double yearDebitQty;

    private double yearCreditLocal;

    private double endBalanceFor;

    private double debitLocal;

    private double yearCreditRpt;

    private String currencyNumber;

    private double yearPnLRpt;

    private int balType;

    private String accountNumber;

    private double beginBalanceFor;

    private double creditFor;

    private double monthPnLFor;

    private double endQty;

    private double monthPnLLocal;

    private double yearCreditQty;

    private double yearCreditFor;

    private double endBalanceRpt;

    private double endBalanceLocal;

    private double debitRpt;

    private double beginQty;

    private double beginBalanceRpt;

    private double yearPnLFor;

    public WSWSAccountBalance() {
    }

    public WSWSAccountBalance(
           double debitFor,
           double yearDebitFor,
           double debitQty,
           double yearDebitLocal,
           double creditRpt,
           double creditLocal,
           String orgUnitNumber,
           double yearDebitRpt,
           double beginBalanceLocal,
           int periodYear,
           double creditQty,
           double monthPnLRpt,
           double yearPnLLocal,
           int periodNumber,
           double yearDebitQty,
           double yearCreditLocal,
           double endBalanceFor,
           double debitLocal,
           double yearCreditRpt,
           String currencyNumber,
           double yearPnLRpt,
           int balType,
           String accountNumber,
           double beginBalanceFor,
           double creditFor,
           double monthPnLFor,
           double endQty,
           double monthPnLLocal,
           double yearCreditQty,
           double yearCreditFor,
           double endBalanceRpt,
           double endBalanceLocal,
           double debitRpt,
           double beginQty,
           double beginBalanceRpt,
           double yearPnLFor) {
        this.debitFor = debitFor;
        this.yearDebitFor = yearDebitFor;
        this.debitQty = debitQty;
        this.yearDebitLocal = yearDebitLocal;
        this.creditRpt = creditRpt;
        this.creditLocal = creditLocal;
        this.orgUnitNumber = orgUnitNumber;
        this.yearDebitRpt = yearDebitRpt;
        this.beginBalanceLocal = beginBalanceLocal;
        this.periodYear = periodYear;
        this.creditQty = creditQty;
        this.monthPnLRpt = monthPnLRpt;
        this.yearPnLLocal = yearPnLLocal;
        this.periodNumber = periodNumber;
        this.yearDebitQty = yearDebitQty;
        this.yearCreditLocal = yearCreditLocal;
        this.endBalanceFor = endBalanceFor;
        this.debitLocal = debitLocal;
        this.yearCreditRpt = yearCreditRpt;
        this.currencyNumber = currencyNumber;
        this.yearPnLRpt = yearPnLRpt;
        this.balType = balType;
        this.accountNumber = accountNumber;
        this.beginBalanceFor = beginBalanceFor;
        this.creditFor = creditFor;
        this.monthPnLFor = monthPnLFor;
        this.endQty = endQty;
        this.monthPnLLocal = monthPnLLocal;
        this.yearCreditQty = yearCreditQty;
        this.yearCreditFor = yearCreditFor;
        this.endBalanceRpt = endBalanceRpt;
        this.endBalanceLocal = endBalanceLocal;
        this.debitRpt = debitRpt;
        this.beginQty = beginQty;
        this.beginBalanceRpt = beginBalanceRpt;
        this.yearPnLFor = yearPnLFor;
    }


    /**
     * Gets the debitFor value for this WSWSAccountBalance.
     *
     * @return debitFor
     */
    public double getDebitFor() {
        return debitFor;
    }


    /**
     * Sets the debitFor value for this WSWSAccountBalance.
     *
     * @param debitFor
     */
    public void setDebitFor(double debitFor) {
        this.debitFor = debitFor;
    }


    /**
     * Gets the yearDebitFor value for this WSWSAccountBalance.
     *
     * @return yearDebitFor
     */
    public double getYearDebitFor() {
        return yearDebitFor;
    }


    /**
     * Sets the yearDebitFor value for this WSWSAccountBalance.
     *
     * @param yearDebitFor
     */
    public void setYearDebitFor(double yearDebitFor) {
        this.yearDebitFor = yearDebitFor;
    }


    /**
     * Gets the debitQty value for this WSWSAccountBalance.
     *
     * @return debitQty
     */
    public double getDebitQty() {
        return debitQty;
    }


    /**
     * Sets the debitQty value for this WSWSAccountBalance.
     *
     * @param debitQty
     */
    public void setDebitQty(double debitQty) {
        this.debitQty = debitQty;
    }


    /**
     * Gets the yearDebitLocal value for this WSWSAccountBalance.
     *
     * @return yearDebitLocal
     */
    public double getYearDebitLocal() {
        return yearDebitLocal;
    }


    /**
     * Sets the yearDebitLocal value for this WSWSAccountBalance.
     *
     * @param yearDebitLocal
     */
    public void setYearDebitLocal(double yearDebitLocal) {
        this.yearDebitLocal = yearDebitLocal;
    }


    /**
     * Gets the creditRpt value for this WSWSAccountBalance.
     *
     * @return creditRpt
     */
    public double getCreditRpt() {
        return creditRpt;
    }


    /**
     * Sets the creditRpt value for this WSWSAccountBalance.
     *
     * @param creditRpt
     */
    public void setCreditRpt(double creditRpt) {
        this.creditRpt = creditRpt;
    }


    /**
     * Gets the creditLocal value for this WSWSAccountBalance.
     *
     * @return creditLocal
     */
    public double getCreditLocal() {
        return creditLocal;
    }


    /**
     * Sets the creditLocal value for this WSWSAccountBalance.
     *
     * @param creditLocal
     */
    public void setCreditLocal(double creditLocal) {
        this.creditLocal = creditLocal;
    }


    /**
     * Gets the orgUnitNumber value for this WSWSAccountBalance.
     *
     * @return orgUnitNumber
     */
    public String getOrgUnitNumber() {
        return orgUnitNumber;
    }


    /**
     * Sets the orgUnitNumber value for this WSWSAccountBalance.
     *
     * @param orgUnitNumber
     */
    public void setOrgUnitNumber(String orgUnitNumber) {
        this.orgUnitNumber = orgUnitNumber;
    }


    /**
     * Gets the yearDebitRpt value for this WSWSAccountBalance.
     *
     * @return yearDebitRpt
     */
    public double getYearDebitRpt() {
        return yearDebitRpt;
    }


    /**
     * Sets the yearDebitRpt value for this WSWSAccountBalance.
     *
     * @param yearDebitRpt
     */
    public void setYearDebitRpt(double yearDebitRpt) {
        this.yearDebitRpt = yearDebitRpt;
    }


    /**
     * Gets the beginBalanceLocal value for this WSWSAccountBalance.
     *
     * @return beginBalanceLocal
     */
    public double getBeginBalanceLocal() {
        return beginBalanceLocal;
    }


    /**
     * Sets the beginBalanceLocal value for this WSWSAccountBalance.
     *
     * @param beginBalanceLocal
     */
    public void setBeginBalanceLocal(double beginBalanceLocal) {
        this.beginBalanceLocal = beginBalanceLocal;
    }


    /**
     * Gets the periodYear value for this WSWSAccountBalance.
     *
     * @return periodYear
     */
    public int getPeriodYear() {
        return periodYear;
    }


    /**
     * Sets the periodYear value for this WSWSAccountBalance.
     *
     * @param periodYear
     */
    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }


    /**
     * Gets the creditQty value for this WSWSAccountBalance.
     *
     * @return creditQty
     */
    public double getCreditQty() {
        return creditQty;
    }


    /**
     * Sets the creditQty value for this WSWSAccountBalance.
     *
     * @param creditQty
     */
    public void setCreditQty(double creditQty) {
        this.creditQty = creditQty;
    }


    /**
     * Gets the monthPnLRpt value for this WSWSAccountBalance.
     *
     * @return monthPnLRpt
     */
    public double getMonthPnLRpt() {
        return monthPnLRpt;
    }


    /**
     * Sets the monthPnLRpt value for this WSWSAccountBalance.
     *
     * @param monthPnLRpt
     */
    public void setMonthPnLRpt(double monthPnLRpt) {
        this.monthPnLRpt = monthPnLRpt;
    }


    /**
     * Gets the yearPnLLocal value for this WSWSAccountBalance.
     *
     * @return yearPnLLocal
     */
    public double getYearPnLLocal() {
        return yearPnLLocal;
    }


    /**
     * Sets the yearPnLLocal value for this WSWSAccountBalance.
     *
     * @param yearPnLLocal
     */
    public void setYearPnLLocal(double yearPnLLocal) {
        this.yearPnLLocal = yearPnLLocal;
    }


    /**
     * Gets the periodNumber value for this WSWSAccountBalance.
     *
     * @return periodNumber
     */
    public int getPeriodNumber() {
        return periodNumber;
    }


    /**
     * Sets the periodNumber value for this WSWSAccountBalance.
     *
     * @param periodNumber
     */
    public void setPeriodNumber(int periodNumber) {
        this.periodNumber = periodNumber;
    }


    /**
     * Gets the yearDebitQty value for this WSWSAccountBalance.
     *
     * @return yearDebitQty
     */
    public double getYearDebitQty() {
        return yearDebitQty;
    }


    /**
     * Sets the yearDebitQty value for this WSWSAccountBalance.
     *
     * @param yearDebitQty
     */
    public void setYearDebitQty(double yearDebitQty) {
        this.yearDebitQty = yearDebitQty;
    }


    /**
     * Gets the yearCreditLocal value for this WSWSAccountBalance.
     *
     * @return yearCreditLocal
     */
    public double getYearCreditLocal() {
        return yearCreditLocal;
    }


    /**
     * Sets the yearCreditLocal value for this WSWSAccountBalance.
     *
     * @param yearCreditLocal
     */
    public void setYearCreditLocal(double yearCreditLocal) {
        this.yearCreditLocal = yearCreditLocal;
    }


    /**
     * Gets the endBalanceFor value for this WSWSAccountBalance.
     *
     * @return endBalanceFor
     */
    public double getEndBalanceFor() {
        return endBalanceFor;
    }


    /**
     * Sets the endBalanceFor value for this WSWSAccountBalance.
     *
     * @param endBalanceFor
     */
    public void setEndBalanceFor(double endBalanceFor) {
        this.endBalanceFor = endBalanceFor;
    }


    /**
     * Gets the debitLocal value for this WSWSAccountBalance.
     *
     * @return debitLocal
     */
    public double getDebitLocal() {
        return debitLocal;
    }


    /**
     * Sets the debitLocal value for this WSWSAccountBalance.
     *
     * @param debitLocal
     */
    public void setDebitLocal(double debitLocal) {
        this.debitLocal = debitLocal;
    }


    /**
     * Gets the yearCreditRpt value for this WSWSAccountBalance.
     *
     * @return yearCreditRpt
     */
    public double getYearCreditRpt() {
        return yearCreditRpt;
    }


    /**
     * Sets the yearCreditRpt value for this WSWSAccountBalance.
     *
     * @param yearCreditRpt
     */
    public void setYearCreditRpt(double yearCreditRpt) {
        this.yearCreditRpt = yearCreditRpt;
    }


    /**
     * Gets the currencyNumber value for this WSWSAccountBalance.
     *
     * @return currencyNumber
     */
    public String getCurrencyNumber() {
        return currencyNumber;
    }


    /**
     * Sets the currencyNumber value for this WSWSAccountBalance.
     *
     * @param currencyNumber
     */
    public void setCurrencyNumber(String currencyNumber) {
        this.currencyNumber = currencyNumber;
    }


    /**
     * Gets the yearPnLRpt value for this WSWSAccountBalance.
     *
     * @return yearPnLRpt
     */
    public double getYearPnLRpt() {
        return yearPnLRpt;
    }


    /**
     * Sets the yearPnLRpt value for this WSWSAccountBalance.
     *
     * @param yearPnLRpt
     */
    public void setYearPnLRpt(double yearPnLRpt) {
        this.yearPnLRpt = yearPnLRpt;
    }


    /**
     * Gets the balType value for this WSWSAccountBalance.
     *
     * @return balType
     */
    public int getBalType() {
        return balType;
    }


    /**
     * Sets the balType value for this WSWSAccountBalance.
     *
     * @param balType
     */
    public void setBalType(int balType) {
        this.balType = balType;
    }


    /**
     * Gets the accountNumber value for this WSWSAccountBalance.
     *
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this WSWSAccountBalance.
     *
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the beginBalanceFor value for this WSWSAccountBalance.
     *
     * @return beginBalanceFor
     */
    public double getBeginBalanceFor() {
        return beginBalanceFor;
    }


    /**
     * Sets the beginBalanceFor value for this WSWSAccountBalance.
     *
     * @param beginBalanceFor
     */
    public void setBeginBalanceFor(double beginBalanceFor) {
        this.beginBalanceFor = beginBalanceFor;
    }


    /**
     * Gets the creditFor value for this WSWSAccountBalance.
     *
     * @return creditFor
     */
    public double getCreditFor() {
        return creditFor;
    }


    /**
     * Sets the creditFor value for this WSWSAccountBalance.
     *
     * @param creditFor
     */
    public void setCreditFor(double creditFor) {
        this.creditFor = creditFor;
    }


    /**
     * Gets the monthPnLFor value for this WSWSAccountBalance.
     *
     * @return monthPnLFor
     */
    public double getMonthPnLFor() {
        return monthPnLFor;
    }


    /**
     * Sets the monthPnLFor value for this WSWSAccountBalance.
     *
     * @param monthPnLFor
     */
    public void setMonthPnLFor(double monthPnLFor) {
        this.monthPnLFor = monthPnLFor;
    }


    /**
     * Gets the endQty value for this WSWSAccountBalance.
     *
     * @return endQty
     */
    public double getEndQty() {
        return endQty;
    }


    /**
     * Sets the endQty value for this WSWSAccountBalance.
     *
     * @param endQty
     */
    public void setEndQty(double endQty) {
        this.endQty = endQty;
    }


    /**
     * Gets the monthPnLLocal value for this WSWSAccountBalance.
     *
     * @return monthPnLLocal
     */
    public double getMonthPnLLocal() {
        return monthPnLLocal;
    }


    /**
     * Sets the monthPnLLocal value for this WSWSAccountBalance.
     *
     * @param monthPnLLocal
     */
    public void setMonthPnLLocal(double monthPnLLocal) {
        this.monthPnLLocal = monthPnLLocal;
    }


    /**
     * Gets the yearCreditQty value for this WSWSAccountBalance.
     *
     * @return yearCreditQty
     */
    public double getYearCreditQty() {
        return yearCreditQty;
    }


    /**
     * Sets the yearCreditQty value for this WSWSAccountBalance.
     *
     * @param yearCreditQty
     */
    public void setYearCreditQty(double yearCreditQty) {
        this.yearCreditQty = yearCreditQty;
    }


    /**
     * Gets the yearCreditFor value for this WSWSAccountBalance.
     *
     * @return yearCreditFor
     */
    public double getYearCreditFor() {
        return yearCreditFor;
    }


    /**
     * Sets the yearCreditFor value for this WSWSAccountBalance.
     *
     * @param yearCreditFor
     */
    public void setYearCreditFor(double yearCreditFor) {
        this.yearCreditFor = yearCreditFor;
    }


    /**
     * Gets the endBalanceRpt value for this WSWSAccountBalance.
     *
     * @return endBalanceRpt
     */
    public double getEndBalanceRpt() {
        return endBalanceRpt;
    }


    /**
     * Sets the endBalanceRpt value for this WSWSAccountBalance.
     *
     * @param endBalanceRpt
     */
    public void setEndBalanceRpt(double endBalanceRpt) {
        this.endBalanceRpt = endBalanceRpt;
    }


    /**
     * Gets the endBalanceLocal value for this WSWSAccountBalance.
     *
     * @return endBalanceLocal
     */
    public double getEndBalanceLocal() {
        return endBalanceLocal;
    }


    /**
     * Sets the endBalanceLocal value for this WSWSAccountBalance.
     *
     * @param endBalanceLocal
     */
    public void setEndBalanceLocal(double endBalanceLocal) {
        this.endBalanceLocal = endBalanceLocal;
    }


    /**
     * Gets the debitRpt value for this WSWSAccountBalance.
     *
     * @return debitRpt
     */
    public double getDebitRpt() {
        return debitRpt;
    }


    /**
     * Sets the debitRpt value for this WSWSAccountBalance.
     *
     * @param debitRpt
     */
    public void setDebitRpt(double debitRpt) {
        this.debitRpt = debitRpt;
    }


    /**
     * Gets the beginQty value for this WSWSAccountBalance.
     *
     * @return beginQty
     */
    public double getBeginQty() {
        return beginQty;
    }


    /**
     * Sets the beginQty value for this WSWSAccountBalance.
     *
     * @param beginQty
     */
    public void setBeginQty(double beginQty) {
        this.beginQty = beginQty;
    }


    /**
     * Gets the beginBalanceRpt value for this WSWSAccountBalance.
     *
     * @return beginBalanceRpt
     */
    public double getBeginBalanceRpt() {
        return beginBalanceRpt;
    }


    /**
     * Sets the beginBalanceRpt value for this WSWSAccountBalance.
     *
     * @param beginBalanceRpt
     */
    public void setBeginBalanceRpt(double beginBalanceRpt) {
        this.beginBalanceRpt = beginBalanceRpt;
    }


    /**
     * Gets the yearPnLFor value for this WSWSAccountBalance.
     *
     * @return yearPnLFor
     */
    public double getYearPnLFor() {
        return yearPnLFor;
    }


    /**
     * Sets the yearPnLFor value for this WSWSAccountBalance.
     *
     * @param yearPnLFor
     */
    public void setYearPnLFor(double yearPnLFor) {
        this.yearPnLFor = yearPnLFor;
    }

    private Object __equalsCalc = null;
    @Override
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof WSWSAccountBalance)) {
            return false;
        }
        WSWSAccountBalance other = (WSWSAccountBalance) obj;
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) &&
            FsscNumberUtil.isEqual(this.debitFor , other.getDebitFor()) &&
            FsscNumberUtil.isEqual(this.yearDebitFor , other.getYearDebitFor()) &&
            FsscNumberUtil.isEqual(this.debitQty , other.getDebitQty()) &&
            FsscNumberUtil.isEqual(this.yearDebitLocal , other.getYearDebitLocal()) &&
            FsscNumberUtil.isEqual(this.creditRpt , other.getCreditRpt()) &&
            FsscNumberUtil.isEqual(this.creditLocal , other.getCreditLocal()) &&
            ((this.orgUnitNumber==null && other.getOrgUnitNumber()==null) ||
             (this.orgUnitNumber!=null &&
              this.orgUnitNumber.equals(other.getOrgUnitNumber()))) &&
            FsscNumberUtil.isEqual(this.yearDebitRpt , other.getYearDebitRpt()) &&
            FsscNumberUtil.isEqual(this.beginBalanceLocal , other.getBeginBalanceLocal()) &&
            FsscNumberUtil.isEqual(this.periodYear , other.getPeriodYear()) &&
            FsscNumberUtil.isEqual(this.creditQty , other.getCreditQty()) &&
            FsscNumberUtil.isEqual(this.monthPnLRpt , other.getMonthPnLRpt()) &&
            FsscNumberUtil.isEqual(this.yearPnLLocal , other.getYearPnLLocal()) &&
            FsscNumberUtil.isEqual(this.periodNumber , other.getPeriodNumber()) &&
            FsscNumberUtil.isEqual(this.yearDebitQty , other.getYearDebitQty()) &&
            FsscNumberUtil.isEqual(this.yearCreditLocal , other.getYearCreditLocal()) &&
            FsscNumberUtil.isEqual(this.endBalanceFor , other.getEndBalanceFor()) &&
            FsscNumberUtil.isEqual(this.debitLocal , other.getDebitLocal()) &&
            FsscNumberUtil.isEqual(this.yearCreditRpt , other.getYearCreditRpt()) &&
            ((this.currencyNumber==null && other.getCurrencyNumber()==null) ||
             (this.currencyNumber!=null &&
              this.currencyNumber.equals(other.getCurrencyNumber()))) &&
            FsscNumberUtil.isEqual(this.yearPnLRpt , other.getYearPnLRpt()) &&
            FsscNumberUtil.isEqual(this.balType , other.getBalType()) &&
            ((this.accountNumber==null && other.getAccountNumber()==null) ||
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            FsscNumberUtil.isEqual(this.beginBalanceFor , other.getBeginBalanceFor()) &&
            FsscNumberUtil.isEqual(this.creditFor , other.getCreditFor()) &&
            FsscNumberUtil.isEqual(this.monthPnLFor , other.getMonthPnLFor()) &&
            FsscNumberUtil.isEqual(this.endQty , other.getEndQty()) &&
            FsscNumberUtil.isEqual(this.monthPnLLocal , other.getMonthPnLLocal()) &&
            FsscNumberUtil.isEqual(this.yearCreditQty , other.getYearCreditQty()) &&
            FsscNumberUtil.isEqual(this.yearCreditFor , other.getYearCreditFor()) &&
            FsscNumberUtil.isEqual(this.endBalanceRpt , other.getEndBalanceRpt()) &&
            FsscNumberUtil.isEqual(this.endBalanceLocal , other.getEndBalanceLocal()) &&
            FsscNumberUtil.isEqual(this.debitRpt , other.getDebitRpt()) &&
            FsscNumberUtil.isEqual(this.beginQty , other.getBeginQty()) &&
            FsscNumberUtil.isEqual(this.beginBalanceRpt , other.getBeginBalanceRpt()) &&
            FsscNumberUtil.isEqual(this.yearPnLFor , other.getYearPnLFor());
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    @Override
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        _hashCode += new Double(getDebitFor()).hashCode();
        _hashCode += new Double(getYearDebitFor()).hashCode();
        _hashCode += new Double(getDebitQty()).hashCode();
        _hashCode += new Double(getYearDebitLocal()).hashCode();
        _hashCode += new Double(getCreditRpt()).hashCode();
        _hashCode += new Double(getCreditLocal()).hashCode();
        if (getOrgUnitNumber() != null) {
            _hashCode += getOrgUnitNumber().hashCode();
        }
        _hashCode += new Double(getYearDebitRpt()).hashCode();
        _hashCode += new Double(getBeginBalanceLocal()).hashCode();
        _hashCode += getPeriodYear();
        _hashCode += new Double(getCreditQty()).hashCode();
        _hashCode += new Double(getMonthPnLRpt()).hashCode();
        _hashCode += new Double(getYearPnLLocal()).hashCode();
        _hashCode += getPeriodNumber();
        _hashCode += new Double(getYearDebitQty()).hashCode();
        _hashCode += new Double(getYearCreditLocal()).hashCode();
        _hashCode += new Double(getEndBalanceFor()).hashCode();
        _hashCode += new Double(getDebitLocal()).hashCode();
        _hashCode += new Double(getYearCreditRpt()).hashCode();
        if (getCurrencyNumber() != null) {
            _hashCode += getCurrencyNumber().hashCode();
        }
        _hashCode += new Double(getYearPnLRpt()).hashCode();
        _hashCode += getBalType();
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        _hashCode += new Double(getBeginBalanceFor()).hashCode();
        _hashCode += new Double(getCreditFor()).hashCode();
        _hashCode += new Double(getMonthPnLFor()).hashCode();
        _hashCode += new Double(getEndQty()).hashCode();
        _hashCode += new Double(getMonthPnLLocal()).hashCode();
        _hashCode += new Double(getYearCreditQty()).hashCode();
        _hashCode += new Double(getYearCreditFor()).hashCode();
        _hashCode += new Double(getEndBalanceRpt()).hashCode();
        _hashCode += new Double(getEndBalanceLocal()).hashCode();
        _hashCode += new Double(getDebitRpt()).hashCode();
        _hashCode += new Double(getBeginQty()).hashCode();
        _hashCode += new Double(getBeginBalanceRpt()).hashCode();
        _hashCode += new Double(getYearPnLFor()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSWSAccountBalance.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://app.gl.fi.eas.kingdee.com", "WSWSAccountBalance"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debitFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "debitFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearDebitFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearDebitFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debitQty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "debitQty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearDebitLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearDebitLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orgUnitNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orgUnitNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearDebitRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearDebitRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginBalanceLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginBalanceLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodYear");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodYear"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditQty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditQty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthPnLRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monthPnLRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearPnLLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearPnLLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearDebitQty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearDebitQty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearCreditLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearCreditLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endBalanceFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endBalanceFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debitLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "debitLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearCreditRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearCreditRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currencyNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearPnLRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearPnLRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("balType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "balType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginBalanceFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginBalanceFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthPnLFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monthPnLFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endQty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endQty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthPnLLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "monthPnLLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearCreditQty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearCreditQty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearCreditFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearCreditFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endBalanceRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endBalanceRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endBalanceLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endBalanceLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debitRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "debitRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginQty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginQty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("beginBalanceRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "beginBalanceRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearPnLFor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "yearPnLFor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
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
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
