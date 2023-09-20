/**
 * WSWSVoucher.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.landray.kmss.fssc.eas.webService;

import com.landray.kmss.fssc.common.util.FsscNumberUtil;

public class WSWSVoucher  extends WSBean implements java.io.Serializable {
    private String cashAsstActType5;

    private int oppAccountSeq;

    private String cashAsstActType4;

    private String cashAsstActType3;

    private double cashflowAmountOriginal;

    private String cashAsstActType2;

    private String bizDate;

    private String cashAsstActType1;

    private double cashflowAmountLocal;

    private String bizNumber;

    private String companyNumber;

    private String voucherType;

    private int attaches;

    private String tempNumber;

    private String cashAsstActType8;

    private String cashAsstActType6;

    private String cashAsstActType7;

    private int cussent;

    private int entrySeq;

    private String currencyNumber;

    private double supplyCoef;

    private String asstActType5;

    private int asstSeq;

    private String asstActType6;

    private String asstActType7;

    private String asstActType8;

    private String asstActType1;

    private String bookedDate;

    private String asstActType2;

    private String asstActType3;

    private String asstActType4;

    private String assistBizDate;

    private String creator;

    private String supplyItem;

    private String icketNumber;

    private double cashflowAmountRpt;

    private int itemFlag;

    private int entryDC;

    private String cashAsstActNumber2;

    private String cashAsstActNumber1;

    private String voucherNumber;

    private String cashAsstActNumber4;

    private String cashAsstActNumber3;

    private double creditAmount;

    private String poster;

    private String cashAsstActName5;

    private String asstActNumber1;

    private String cashAsstActName6;

    private String asstActNumber2;

    private String cashAsstActName3;

    private String cashAsstActName4;

    private String asstActNumber3;

    private String cashAsstActName1;

    private String asstActNumber4;

    private double price;

    private int periodYear;

    private String asstActNumber5;

    private String cashAsstActName2;

    private String asstActNumber6;

    private String asstActNumber7;

    private String measurement;

    private double localRate;

    private String asstActNumber8;

    private int periodNumber;

    private String invoiceNumber;

    private double qty;

    private String settlementType;

    private String primaryItem;

    private String assistEndDate;

    private double primaryCoef;

    private String voucherAbstract;

    private String settlementNumber;

    private double debitAmount;

    private String description;

    private String accountNumber;

    private double originalAmount;

    private String asstActName1;

    private String type;

    private boolean isCheck;

    private String cashAsstActName8;

    private String cashAsstActName7;

    private String cashAsstActNumber7;

    private String customerNumber;

    private String asstActName7;

    private String cashAsstActNumber8;

    private String asstActName6;

    private String cashAsstActNumber5;

    private String cashAsstActNumber6;

    private String asstActName8;

    private String auditor;

    private String asstActName3;

    private String asstActName2;

    private String asstActName5;

    private String asstActName4;

    public WSWSVoucher() {
    }

    public WSWSVoucher(
           String cashAsstActType5,
           int oppAccountSeq,
           String cashAsstActType4,
           String cashAsstActType3,
           double cashflowAmountOriginal,
           String cashAsstActType2,
           String bizDate,
           String cashAsstActType1,
           double cashflowAmountLocal,
           String bizNumber,
           String companyNumber,
           String voucherType,
           int attaches,
           String tempNumber,
           String cashAsstActType8,
           String cashAsstActType6,
           String cashAsstActType7,
           int cussent,
           int entrySeq,
           String currencyNumber,
           double supplyCoef,
           String asstActType5,
           int asstSeq,
           String asstActType6,
           String asstActType7,
           String asstActType8,
           String asstActType1,
           String bookedDate,
           String asstActType2,
           String asstActType3,
           String asstActType4,
           String assistBizDate,
           String creator,
           String supplyItem,
           String icketNumber,
           double cashflowAmountRpt,
           int itemFlag,
           int entryDC,
           String cashAsstActNumber2,
           String cashAsstActNumber1,
           String voucherNumber,
           String cashAsstActNumber4,
           String cashAsstActNumber3,
           double creditAmount,
           String poster,
           String cashAsstActName5,
           String asstActNumber1,
           String cashAsstActName6,
           String asstActNumber2,
           String cashAsstActName3,
           String cashAsstActName4,
           String asstActNumber3,
           String cashAsstActName1,
           String asstActNumber4,
           double price,
           int periodYear,
           String asstActNumber5,
           String cashAsstActName2,
           String asstActNumber6,
           String asstActNumber7,
           String measurement,
           double localRate,
           String asstActNumber8,
           int periodNumber,
           String invoiceNumber,
           double qty,
           String settlementType,
           String primaryItem,
           String assistEndDate,
           double primaryCoef,
           String voucherAbstract,
           String settlementNumber,
           double debitAmount,
           String description,
           String accountNumber,
           double originalAmount,
           String asstActName1,
           String type,
           boolean isCheck,
           String cashAsstActName8,
           String cashAsstActName7,
           String cashAsstActNumber7,
           String customerNumber,
           String asstActName7,
           String cashAsstActNumber8,
           String asstActName6,
           String cashAsstActNumber5,
           String cashAsstActNumber6,
           String asstActName8,
           String auditor,
           String asstActName3,
           String asstActName2,
           String asstActName5,
           String asstActName4) {
        this.cashAsstActType5 = cashAsstActType5;
        this.oppAccountSeq = oppAccountSeq;
        this.cashAsstActType4 = cashAsstActType4;
        this.cashAsstActType3 = cashAsstActType3;
        this.cashflowAmountOriginal = cashflowAmountOriginal;
        this.cashAsstActType2 = cashAsstActType2;
        this.bizDate = bizDate;
        this.cashAsstActType1 = cashAsstActType1;
        this.cashflowAmountLocal = cashflowAmountLocal;
        this.bizNumber = bizNumber;
        this.companyNumber = companyNumber;
        this.voucherType = voucherType;
        this.attaches = attaches;
        this.tempNumber = tempNumber;
        this.cashAsstActType8 = cashAsstActType8;
        this.cashAsstActType6 = cashAsstActType6;
        this.cashAsstActType7 = cashAsstActType7;
        this.cussent = cussent;
        this.entrySeq = entrySeq;
        this.currencyNumber = currencyNumber;
        this.supplyCoef = supplyCoef;
        this.asstActType5 = asstActType5;
        this.asstSeq = asstSeq;
        this.asstActType6 = asstActType6;
        this.asstActType7 = asstActType7;
        this.asstActType8 = asstActType8;
        this.asstActType1 = asstActType1;
        this.bookedDate = bookedDate;
        this.asstActType2 = asstActType2;
        this.asstActType3 = asstActType3;
        this.asstActType4 = asstActType4;
        this.assistBizDate = assistBizDate;
        this.creator = creator;
        this.supplyItem = supplyItem;
        this.icketNumber = icketNumber;
        this.cashflowAmountRpt = cashflowAmountRpt;
        this.itemFlag = itemFlag;
        this.entryDC = entryDC;
        this.cashAsstActNumber2 = cashAsstActNumber2;
        this.cashAsstActNumber1 = cashAsstActNumber1;
        this.voucherNumber = voucherNumber;
        this.cashAsstActNumber4 = cashAsstActNumber4;
        this.cashAsstActNumber3 = cashAsstActNumber3;
        this.creditAmount = creditAmount;
        this.poster = poster;
        this.cashAsstActName5 = cashAsstActName5;
        this.asstActNumber1 = asstActNumber1;
        this.cashAsstActName6 = cashAsstActName6;
        this.asstActNumber2 = asstActNumber2;
        this.cashAsstActName3 = cashAsstActName3;
        this.cashAsstActName4 = cashAsstActName4;
        this.asstActNumber3 = asstActNumber3;
        this.cashAsstActName1 = cashAsstActName1;
        this.asstActNumber4 = asstActNumber4;
        this.price = price;
        this.periodYear = periodYear;
        this.asstActNumber5 = asstActNumber5;
        this.cashAsstActName2 = cashAsstActName2;
        this.asstActNumber6 = asstActNumber6;
        this.asstActNumber7 = asstActNumber7;
        this.measurement = measurement;
        this.localRate = localRate;
        this.asstActNumber8 = asstActNumber8;
        this.periodNumber = periodNumber;
        this.invoiceNumber = invoiceNumber;
        this.qty = qty;
        this.settlementType = settlementType;
        this.primaryItem = primaryItem;
        this.assistEndDate = assistEndDate;
        this.primaryCoef = primaryCoef;
        this.voucherAbstract = voucherAbstract;
        this.settlementNumber = settlementNumber;
        this.debitAmount = debitAmount;
        this.description = description;
        this.accountNumber = accountNumber;
        this.originalAmount = originalAmount;
        this.asstActName1 = asstActName1;
        this.type = type;
        this.isCheck = isCheck;
        this.cashAsstActName8 = cashAsstActName8;
        this.cashAsstActName7 = cashAsstActName7;
        this.cashAsstActNumber7 = cashAsstActNumber7;
        this.customerNumber = customerNumber;
        this.asstActName7 = asstActName7;
        this.cashAsstActNumber8 = cashAsstActNumber8;
        this.asstActName6 = asstActName6;
        this.cashAsstActNumber5 = cashAsstActNumber5;
        this.cashAsstActNumber6 = cashAsstActNumber6;
        this.asstActName8 = asstActName8;
        this.auditor = auditor;
        this.asstActName3 = asstActName3;
        this.asstActName2 = asstActName2;
        this.asstActName5 = asstActName5;
        this.asstActName4 = asstActName4;
    }


    /**
     * Gets the cashAsstActType5 value for this WSWSVoucher.
     *
     * @return cashAsstActType5
     */
    public String getCashAsstActType5() {
        return cashAsstActType5;
    }


    /**
     * Sets the cashAsstActType5 value for this WSWSVoucher.
     *
     * @param cashAsstActType5
     */
    public void setCashAsstActType5(String cashAsstActType5) {
        this.cashAsstActType5 = cashAsstActType5;
    }


    /**
     * Gets the oppAccountSeq value for this WSWSVoucher.
     *
     * @return oppAccountSeq
     */
    public int getOppAccountSeq() {
        return oppAccountSeq;
    }


    /**
     * Sets the oppAccountSeq value for this WSWSVoucher.
     *
     * @param oppAccountSeq
     */
    public void setOppAccountSeq(int oppAccountSeq) {
        this.oppAccountSeq = oppAccountSeq;
    }


    /**
     * Gets the cashAsstActType4 value for this WSWSVoucher.
     *
     * @return cashAsstActType4
     */
    public String getCashAsstActType4() {
        return cashAsstActType4;
    }


    /**
     * Sets the cashAsstActType4 value for this WSWSVoucher.
     *
     * @param cashAsstActType4
     */
    public void setCashAsstActType4(String cashAsstActType4) {
        this.cashAsstActType4 = cashAsstActType4;
    }


    /**
     * Gets the cashAsstActType3 value for this WSWSVoucher.
     *
     * @return cashAsstActType3
     */
    public String getCashAsstActType3() {
        return cashAsstActType3;
    }


    /**
     * Sets the cashAsstActType3 value for this WSWSVoucher.
     *
     * @param cashAsstActType3
     */
    public void setCashAsstActType3(String cashAsstActType3) {
        this.cashAsstActType3 = cashAsstActType3;
    }


    /**
     * Gets the cashflowAmountOriginal value for this WSWSVoucher.
     *
     * @return cashflowAmountOriginal
     */
    public double getCashflowAmountOriginal() {
        return cashflowAmountOriginal;
    }


    /**
     * Sets the cashflowAmountOriginal value for this WSWSVoucher.
     *
     * @param cashflowAmountOriginal
     */
    public void setCashflowAmountOriginal(double cashflowAmountOriginal) {
        this.cashflowAmountOriginal = cashflowAmountOriginal;
    }


    /**
     * Gets the cashAsstActType2 value for this WSWSVoucher.
     *
     * @return cashAsstActType2
     */
    public String getCashAsstActType2() {
        return cashAsstActType2;
    }


    /**
     * Sets the cashAsstActType2 value for this WSWSVoucher.
     *
     * @param cashAsstActType2
     */
    public void setCashAsstActType2(String cashAsstActType2) {
        this.cashAsstActType2 = cashAsstActType2;
    }


    /**
     * Gets the bizDate value for this WSWSVoucher.
     *
     * @return bizDate
     */
    public String getBizDate() {
        return bizDate;
    }


    /**
     * Sets the bizDate value for this WSWSVoucher.
     *
     * @param bizDate
     */
    public void setBizDate(String bizDate) {
        this.bizDate = bizDate;
    }


    /**
     * Gets the cashAsstActType1 value for this WSWSVoucher.
     *
     * @return cashAsstActType1
     */
    public String getCashAsstActType1() {
        return cashAsstActType1;
    }


    /**
     * Sets the cashAsstActType1 value for this WSWSVoucher.
     *
     * @param cashAsstActType1
     */
    public void setCashAsstActType1(String cashAsstActType1) {
        this.cashAsstActType1 = cashAsstActType1;
    }


    /**
     * Gets the cashflowAmountLocal value for this WSWSVoucher.
     *
     * @return cashflowAmountLocal
     */
    public double getCashflowAmountLocal() {
        return cashflowAmountLocal;
    }


    /**
     * Sets the cashflowAmountLocal value for this WSWSVoucher.
     *
     * @param cashflowAmountLocal
     */
    public void setCashflowAmountLocal(double cashflowAmountLocal) {
        this.cashflowAmountLocal = cashflowAmountLocal;
    }


    /**
     * Gets the bizNumber value for this WSWSVoucher.
     *
     * @return bizNumber
     */
    public String getBizNumber() {
        return bizNumber;
    }


    /**
     * Sets the bizNumber value for this WSWSVoucher.
     *
     * @param bizNumber
     */
    public void setBizNumber(String bizNumber) {
        this.bizNumber = bizNumber;
    }


    /**
     * Gets the companyNumber value for this WSWSVoucher.
     *
     * @return companyNumber
     */
    public String getCompanyNumber() {
        return companyNumber;
    }


    /**
     * Sets the companyNumber value for this WSWSVoucher.
     *
     * @param companyNumber
     */
    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }


    /**
     * Gets the voucherType value for this WSWSVoucher.
     *
     * @return voucherType
     */
    public String getVoucherType() {
        return voucherType;
    }


    /**
     * Sets the voucherType value for this WSWSVoucher.
     *
     * @param voucherType
     */
    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }


    /**
     * Gets the attaches value for this WSWSVoucher.
     *
     * @return attaches
     */
    public int getAttaches() {
        return attaches;
    }


    /**
     * Sets the attaches value for this WSWSVoucher.
     *
     * @param attaches
     */
    public void setAttaches(int attaches) {
        this.attaches = attaches;
    }


    /**
     * Gets the tempNumber value for this WSWSVoucher.
     *
     * @return tempNumber
     */
    public String getTempNumber() {
        return tempNumber;
    }


    /**
     * Sets the tempNumber value for this WSWSVoucher.
     *
     * @param tempNumber
     */
    public void setTempNumber(String tempNumber) {
        this.tempNumber = tempNumber;
    }


    /**
     * Gets the cashAsstActType8 value for this WSWSVoucher.
     *
     * @return cashAsstActType8
     */
    public String getCashAsstActType8() {
        return cashAsstActType8;
    }


    /**
     * Sets the cashAsstActType8 value for this WSWSVoucher.
     *
     * @param cashAsstActType8
     */
    public void setCashAsstActType8(String cashAsstActType8) {
        this.cashAsstActType8 = cashAsstActType8;
    }


    /**
     * Gets the cashAsstActType6 value for this WSWSVoucher.
     *
     * @return cashAsstActType6
     */
    public String getCashAsstActType6() {
        return cashAsstActType6;
    }


    /**
     * Sets the cashAsstActType6 value for this WSWSVoucher.
     *
     * @param cashAsstActType6
     */
    public void setCashAsstActType6(String cashAsstActType6) {
        this.cashAsstActType6 = cashAsstActType6;
    }


    /**
     * Gets the cashAsstActType7 value for this WSWSVoucher.
     *
     * @return cashAsstActType7
     */
    public String getCashAsstActType7() {
        return cashAsstActType7;
    }


    /**
     * Sets the cashAsstActType7 value for this WSWSVoucher.
     *
     * @param cashAsstActType7
     */
    public void setCashAsstActType7(String cashAsstActType7) {
        this.cashAsstActType7 = cashAsstActType7;
    }


    /**
     * Gets the cussent value for this WSWSVoucher.
     *
     * @return cussent
     */
    public int getCussent() {
        return cussent;
    }


    /**
     * Sets the cussent value for this WSWSVoucher.
     *
     * @param cussent
     */
    public void setCussent(int cussent) {
        this.cussent = cussent;
    }


    /**
     * Gets the entrySeq value for this WSWSVoucher.
     *
     * @return entrySeq
     */
    public int getEntrySeq() {
        return entrySeq;
    }


    /**
     * Sets the entrySeq value for this WSWSVoucher.
     *
     * @param entrySeq
     */
    public void setEntrySeq(int entrySeq) {
        this.entrySeq = entrySeq;
    }


    /**
     * Gets the currencyNumber value for this WSWSVoucher.
     *
     * @return currencyNumber
     */
    public String getCurrencyNumber() {
        return currencyNumber;
    }


    /**
     * Sets the currencyNumber value for this WSWSVoucher.
     *
     * @param currencyNumber
     */
    public void setCurrencyNumber(String currencyNumber) {
        this.currencyNumber = currencyNumber;
    }


    /**
     * Gets the supplyCoef value for this WSWSVoucher.
     *
     * @return supplyCoef
     */
    public double getSupplyCoef() {
        return supplyCoef;
    }


    /**
     * Sets the supplyCoef value for this WSWSVoucher.
     *
     * @param supplyCoef
     */
    public void setSupplyCoef(double supplyCoef) {
        this.supplyCoef = supplyCoef;
    }


    /**
     * Gets the asstActType5 value for this WSWSVoucher.
     *
     * @return asstActType5
     */
    public String getAsstActType5() {
        return asstActType5;
    }


    /**
     * Sets the asstActType5 value for this WSWSVoucher.
     *
     * @param asstActType5
     */
    public void setAsstActType5(String asstActType5) {
        this.asstActType5 = asstActType5;
    }


    /**
     * Gets the asstSeq value for this WSWSVoucher.
     *
     * @return asstSeq
     */
    public int getAsstSeq() {
        return asstSeq;
    }


    /**
     * Sets the asstSeq value for this WSWSVoucher.
     *
     * @param asstSeq
     */
    public void setAsstSeq(int asstSeq) {
        this.asstSeq = asstSeq;
    }


    /**
     * Gets the asstActType6 value for this WSWSVoucher.
     *
     * @return asstActType6
     */
    public String getAsstActType6() {
        return asstActType6;
    }


    /**
     * Sets the asstActType6 value for this WSWSVoucher.
     *
     * @param asstActType6
     */
    public void setAsstActType6(String asstActType6) {
        this.asstActType6 = asstActType6;
    }


    /**
     * Gets the asstActType7 value for this WSWSVoucher.
     *
     * @return asstActType7
     */
    public String getAsstActType7() {
        return asstActType7;
    }


    /**
     * Sets the asstActType7 value for this WSWSVoucher.
     *
     * @param asstActType7
     */
    public void setAsstActType7(String asstActType7) {
        this.asstActType7 = asstActType7;
    }


    /**
     * Gets the asstActType8 value for this WSWSVoucher.
     *
     * @return asstActType8
     */
    public String getAsstActType8() {
        return asstActType8;
    }


    /**
     * Sets the asstActType8 value for this WSWSVoucher.
     *
     * @param asstActType8
     */
    public void setAsstActType8(String asstActType8) {
        this.asstActType8 = asstActType8;
    }


    /**
     * Gets the asstActType1 value for this WSWSVoucher.
     *
     * @return asstActType1
     */
    public String getAsstActType1() {
        return asstActType1;
    }


    /**
     * Sets the asstActType1 value for this WSWSVoucher.
     *
     * @param asstActType1
     */
    public void setAsstActType1(String asstActType1) {
        this.asstActType1 = asstActType1;
    }


    /**
     * Gets the bookedDate value for this WSWSVoucher.
     *
     * @return bookedDate
     */
    public String getBookedDate() {
        return bookedDate;
    }


    /**
     * Sets the bookedDate value for this WSWSVoucher.
     *
     * @param bookedDate
     */
    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }


    /**
     * Gets the asstActType2 value for this WSWSVoucher.
     *
     * @return asstActType2
     */
    public String getAsstActType2() {
        return asstActType2;
    }


    /**
     * Sets the asstActType2 value for this WSWSVoucher.
     *
     * @param asstActType2
     */
    public void setAsstActType2(String asstActType2) {
        this.asstActType2 = asstActType2;
    }


    /**
     * Gets the asstActType3 value for this WSWSVoucher.
     *
     * @return asstActType3
     */
    public String getAsstActType3() {
        return asstActType3;
    }


    /**
     * Sets the asstActType3 value for this WSWSVoucher.
     *
     * @param asstActType3
     */
    public void setAsstActType3(String asstActType3) {
        this.asstActType3 = asstActType3;
    }


    /**
     * Gets the asstActType4 value for this WSWSVoucher.
     *
     * @return asstActType4
     */
    public String getAsstActType4() {
        return asstActType4;
    }


    /**
     * Sets the asstActType4 value for this WSWSVoucher.
     *
     * @param asstActType4
     */
    public void setAsstActType4(String asstActType4) {
        this.asstActType4 = asstActType4;
    }


    /**
     * Gets the assistBizDate value for this WSWSVoucher.
     *
     * @return assistBizDate
     */
    public String getAssistBizDate() {
        return assistBizDate;
    }


    /**
     * Sets the assistBizDate value for this WSWSVoucher.
     *
     * @param assistBizDate
     */
    public void setAssistBizDate(String assistBizDate) {
        this.assistBizDate = assistBizDate;
    }


    /**
     * Gets the creator value for this WSWSVoucher.
     *
     * @return creator
     */
    public String getCreator() {
        return creator;
    }


    /**
     * Sets the creator value for this WSWSVoucher.
     *
     * @param creator
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }


    /**
     * Gets the supplyItem value for this WSWSVoucher.
     *
     * @return supplyItem
     */
    public String getSupplyItem() {
        return supplyItem;
    }


    /**
     * Sets the supplyItem value for this WSWSVoucher.
     *
     * @param supplyItem
     */
    public void setSupplyItem(String supplyItem) {
        this.supplyItem = supplyItem;
    }


    /**
     * Gets the icketNumber value for this WSWSVoucher.
     *
     * @return icketNumber
     */
    public String getIcketNumber() {
        return icketNumber;
    }


    /**
     * Sets the icketNumber value for this WSWSVoucher.
     *
     * @param icketNumber
     */
    public void setIcketNumber(String icketNumber) {
        this.icketNumber = icketNumber;
    }


    /**
     * Gets the cashflowAmountRpt value for this WSWSVoucher.
     *
     * @return cashflowAmountRpt
     */
    public double getCashflowAmountRpt() {
        return cashflowAmountRpt;
    }


    /**
     * Sets the cashflowAmountRpt value for this WSWSVoucher.
     *
     * @param cashflowAmountRpt
     */
    public void setCashflowAmountRpt(double cashflowAmountRpt) {
        this.cashflowAmountRpt = cashflowAmountRpt;
    }


    /**
     * Gets the itemFlag value for this WSWSVoucher.
     *
     * @return itemFlag
     */
    public int getItemFlag() {
        return itemFlag;
    }


    /**
     * Sets the itemFlag value for this WSWSVoucher.
     *
     * @param itemFlag
     */
    public void setItemFlag(int itemFlag) {
        this.itemFlag = itemFlag;
    }


    /**
     * Gets the entryDC value for this WSWSVoucher.
     *
     * @return entryDC
     */
    public int getEntryDC() {
        return entryDC;
    }


    /**
     * Sets the entryDC value for this WSWSVoucher.
     *
     * @param entryDC
     */
    public void setEntryDC(int entryDC) {
        this.entryDC = entryDC;
    }


    /**
     * Gets the cashAsstActNumber2 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber2
     */
    public String getCashAsstActNumber2() {
        return cashAsstActNumber2;
    }


    /**
     * Sets the cashAsstActNumber2 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber2
     */
    public void setCashAsstActNumber2(String cashAsstActNumber2) {
        this.cashAsstActNumber2 = cashAsstActNumber2;
    }


    /**
     * Gets the cashAsstActNumber1 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber1
     */
    public String getCashAsstActNumber1() {
        return cashAsstActNumber1;
    }


    /**
     * Sets the cashAsstActNumber1 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber1
     */
    public void setCashAsstActNumber1(String cashAsstActNumber1) {
        this.cashAsstActNumber1 = cashAsstActNumber1;
    }


    /**
     * Gets the voucherNumber value for this WSWSVoucher.
     *
     * @return voucherNumber
     */
    public String getVoucherNumber() {
        return voucherNumber;
    }


    /**
     * Sets the voucherNumber value for this WSWSVoucher.
     *
     * @param voucherNumber
     */
    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }


    /**
     * Gets the cashAsstActNumber4 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber4
     */
    public String getCashAsstActNumber4() {
        return cashAsstActNumber4;
    }


    /**
     * Sets the cashAsstActNumber4 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber4
     */
    public void setCashAsstActNumber4(String cashAsstActNumber4) {
        this.cashAsstActNumber4 = cashAsstActNumber4;
    }


    /**
     * Gets the cashAsstActNumber3 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber3
     */
    public String getCashAsstActNumber3() {
        return cashAsstActNumber3;
    }


    /**
     * Sets the cashAsstActNumber3 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber3
     */
    public void setCashAsstActNumber3(String cashAsstActNumber3) {
        this.cashAsstActNumber3 = cashAsstActNumber3;
    }


    /**
     * Gets the creditAmount value for this WSWSVoucher.
     *
     * @return creditAmount
     */
    public double getCreditAmount() {
        return creditAmount;
    }


    /**
     * Sets the creditAmount value for this WSWSVoucher.
     *
     * @param creditAmount
     */
    public void setCreditAmount(double creditAmount) {
        this.creditAmount = creditAmount;
    }


    /**
     * Gets the poster value for this WSWSVoucher.
     *
     * @return poster
     */
    public String getPoster() {
        return poster;
    }


    /**
     * Sets the poster value for this WSWSVoucher.
     *
     * @param poster
     */
    public void setPoster(String poster) {
        this.poster = poster;
    }


    /**
     * Gets the cashAsstActName5 value for this WSWSVoucher.
     *
     * @return cashAsstActName5
     */
    public String getCashAsstActName5() {
        return cashAsstActName5;
    }


    /**
     * Sets the cashAsstActName5 value for this WSWSVoucher.
     *
     * @param cashAsstActName5
     */
    public void setCashAsstActName5(String cashAsstActName5) {
        this.cashAsstActName5 = cashAsstActName5;
    }


    /**
     * Gets the asstActNumber1 value for this WSWSVoucher.
     *
     * @return asstActNumber1
     */
    public String getAsstActNumber1() {
        return asstActNumber1;
    }


    /**
     * Sets the asstActNumber1 value for this WSWSVoucher.
     *
     * @param asstActNumber1
     */
    public void setAsstActNumber1(String asstActNumber1) {
        this.asstActNumber1 = asstActNumber1;
    }


    /**
     * Gets the cashAsstActName6 value for this WSWSVoucher.
     *
     * @return cashAsstActName6
     */
    public String getCashAsstActName6() {
        return cashAsstActName6;
    }


    /**
     * Sets the cashAsstActName6 value for this WSWSVoucher.
     *
     * @param cashAsstActName6
     */
    public void setCashAsstActName6(String cashAsstActName6) {
        this.cashAsstActName6 = cashAsstActName6;
    }


    /**
     * Gets the asstActNumber2 value for this WSWSVoucher.
     *
     * @return asstActNumber2
     */
    public String getAsstActNumber2() {
        return asstActNumber2;
    }


    /**
     * Sets the asstActNumber2 value for this WSWSVoucher.
     *
     * @param asstActNumber2
     */
    public void setAsstActNumber2(String asstActNumber2) {
        this.asstActNumber2 = asstActNumber2;
    }


    /**
     * Gets the cashAsstActName3 value for this WSWSVoucher.
     *
     * @return cashAsstActName3
     */
    public String getCashAsstActName3() {
        return cashAsstActName3;
    }


    /**
     * Sets the cashAsstActName3 value for this WSWSVoucher.
     *
     * @param cashAsstActName3
     */
    public void setCashAsstActName3(String cashAsstActName3) {
        this.cashAsstActName3 = cashAsstActName3;
    }


    /**
     * Gets the cashAsstActName4 value for this WSWSVoucher.
     *
     * @return cashAsstActName4
     */
    public String getCashAsstActName4() {
        return cashAsstActName4;
    }


    /**
     * Sets the cashAsstActName4 value for this WSWSVoucher.
     *
     * @param cashAsstActName4
     */
    public void setCashAsstActName4(String cashAsstActName4) {
        this.cashAsstActName4 = cashAsstActName4;
    }


    /**
     * Gets the asstActNumber3 value for this WSWSVoucher.
     *
     * @return asstActNumber3
     */
    public String getAsstActNumber3() {
        return asstActNumber3;
    }


    /**
     * Sets the asstActNumber3 value for this WSWSVoucher.
     *
     * @param asstActNumber3
     */
    public void setAsstActNumber3(String asstActNumber3) {
        this.asstActNumber3 = asstActNumber3;
    }


    /**
     * Gets the cashAsstActName1 value for this WSWSVoucher.
     *
     * @return cashAsstActName1
     */
    public String getCashAsstActName1() {
        return cashAsstActName1;
    }


    /**
     * Sets the cashAsstActName1 value for this WSWSVoucher.
     *
     * @param cashAsstActName1
     */
    public void setCashAsstActName1(String cashAsstActName1) {
        this.cashAsstActName1 = cashAsstActName1;
    }


    /**
     * Gets the asstActNumber4 value for this WSWSVoucher.
     *
     * @return asstActNumber4
     */
    public String getAsstActNumber4() {
        return asstActNumber4;
    }


    /**
     * Sets the asstActNumber4 value for this WSWSVoucher.
     *
     * @param asstActNumber4
     */
    public void setAsstActNumber4(String asstActNumber4) {
        this.asstActNumber4 = asstActNumber4;
    }


    /**
     * Gets the price value for this WSWSVoucher.
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }


    /**
     * Sets the price value for this WSWSVoucher.
     *
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }


    /**
     * Gets the periodYear value for this WSWSVoucher.
     *
     * @return periodYear
     */
    public int getPeriodYear() {
        return periodYear;
    }


    /**
     * Sets the periodYear value for this WSWSVoucher.
     *
     * @param periodYear
     */
    public void setPeriodYear(int periodYear) {
        this.periodYear = periodYear;
    }


    /**
     * Gets the asstActNumber5 value for this WSWSVoucher.
     *
     * @return asstActNumber5
     */
    public String getAsstActNumber5() {
        return asstActNumber5;
    }


    /**
     * Sets the asstActNumber5 value for this WSWSVoucher.
     *
     * @param asstActNumber5
     */
    public void setAsstActNumber5(String asstActNumber5) {
        this.asstActNumber5 = asstActNumber5;
    }


    /**
     * Gets the cashAsstActName2 value for this WSWSVoucher.
     *
     * @return cashAsstActName2
     */
    public String getCashAsstActName2() {
        return cashAsstActName2;
    }


    /**
     * Sets the cashAsstActName2 value for this WSWSVoucher.
     *
     * @param cashAsstActName2
     */
    public void setCashAsstActName2(String cashAsstActName2) {
        this.cashAsstActName2 = cashAsstActName2;
    }


    /**
     * Gets the asstActNumber6 value for this WSWSVoucher.
     *
     * @return asstActNumber6
     */
    public String getAsstActNumber6() {
        return asstActNumber6;
    }


    /**
     * Sets the asstActNumber6 value for this WSWSVoucher.
     *
     * @param asstActNumber6
     */
    public void setAsstActNumber6(String asstActNumber6) {
        this.asstActNumber6 = asstActNumber6;
    }


    /**
     * Gets the asstActNumber7 value for this WSWSVoucher.
     *
     * @return asstActNumber7
     */
    public String getAsstActNumber7() {
        return asstActNumber7;
    }


    /**
     * Sets the asstActNumber7 value for this WSWSVoucher.
     *
     * @param asstActNumber7
     */
    public void setAsstActNumber7(String asstActNumber7) {
        this.asstActNumber7 = asstActNumber7;
    }


    /**
     * Gets the measurement value for this WSWSVoucher.
     *
     * @return measurement
     */
    public String getMeasurement() {
        return measurement;
    }


    /**
     * Sets the measurement value for this WSWSVoucher.
     *
     * @param measurement
     */
    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }


    /**
     * Gets the localRate value for this WSWSVoucher.
     *
     * @return localRate
     */
    public double getLocalRate() {
        return localRate;
    }


    /**
     * Sets the localRate value for this WSWSVoucher.
     *
     * @param localRate
     */
    public void setLocalRate(double localRate) {
        this.localRate = localRate;
    }


    /**
     * Gets the asstActNumber8 value for this WSWSVoucher.
     *
     * @return asstActNumber8
     */
    public String getAsstActNumber8() {
        return asstActNumber8;
    }


    /**
     * Sets the asstActNumber8 value for this WSWSVoucher.
     *
     * @param asstActNumber8
     */
    public void setAsstActNumber8(String asstActNumber8) {
        this.asstActNumber8 = asstActNumber8;
    }


    /**
     * Gets the periodNumber value for this WSWSVoucher.
     *
     * @return periodNumber
     */
    public int getPeriodNumber() {
        return periodNumber;
    }


    /**
     * Sets the periodNumber value for this WSWSVoucher.
     *
     * @param periodNumber
     */
    public void setPeriodNumber(int periodNumber) {
        this.periodNumber = periodNumber;
    }


    /**
     * Gets the invoiceNumber value for this WSWSVoucher.
     *
     * @return invoiceNumber
     */
    public String getInvoiceNumber() {
        return invoiceNumber;
    }


    /**
     * Sets the invoiceNumber value for this WSWSVoucher.
     *
     * @param invoiceNumber
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }


    /**
     * Gets the qty value for this WSWSVoucher.
     *
     * @return qty
     */
    public double getQty() {
        return qty;
    }


    /**
     * Sets the qty value for this WSWSVoucher.
     *
     * @param qty
     */
    public void setQty(double qty) {
        this.qty = qty;
    }


    /**
     * Gets the settlementType value for this WSWSVoucher.
     *
     * @return settlementType
     */
    public String getSettlementType() {
        return settlementType;
    }


    /**
     * Sets the settlementType value for this WSWSVoucher.
     *
     * @param settlementType
     */
    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
    }


    /**
     * Gets the primaryItem value for this WSWSVoucher.
     *
     * @return primaryItem
     */
    public String getPrimaryItem() {
        return primaryItem;
    }


    /**
     * Sets the primaryItem value for this WSWSVoucher.
     *
     * @param primaryItem
     */
    public void setPrimaryItem(String primaryItem) {
        this.primaryItem = primaryItem;
    }


    /**
     * Gets the assistEndDate value for this WSWSVoucher.
     *
     * @return assistEndDate
     */
    public String getAssistEndDate() {
        return assistEndDate;
    }


    /**
     * Sets the assistEndDate value for this WSWSVoucher.
     *
     * @param assistEndDate
     */
    public void setAssistEndDate(String assistEndDate) {
        this.assistEndDate = assistEndDate;
    }


    /**
     * Gets the primaryCoef value for this WSWSVoucher.
     *
     * @return primaryCoef
     */
    public double getPrimaryCoef() {
        return primaryCoef;
    }


    /**
     * Sets the primaryCoef value for this WSWSVoucher.
     *
     * @param primaryCoef
     */
    public void setPrimaryCoef(double primaryCoef) {
        this.primaryCoef = primaryCoef;
    }


    /**
     * Gets the voucherAbstract value for this WSWSVoucher.
     *
     * @return voucherAbstract
     */
    public String getVoucherAbstract() {
        return voucherAbstract;
    }


    /**
     * Sets the voucherAbstract value for this WSWSVoucher.
     *
     * @param voucherAbstract
     */
    public void setVoucherAbstract(String voucherAbstract) {
        this.voucherAbstract = voucherAbstract;
    }


    /**
     * Gets the settlementNumber value for this WSWSVoucher.
     *
     * @return settlementNumber
     */
    public String getSettlementNumber() {
        return settlementNumber;
    }


    /**
     * Sets the settlementNumber value for this WSWSVoucher.
     *
     * @param settlementNumber
     */
    public void setSettlementNumber(String settlementNumber) {
        this.settlementNumber = settlementNumber;
    }


    /**
     * Gets the debitAmount value for this WSWSVoucher.
     *
     * @return debitAmount
     */
    public double getDebitAmount() {
        return debitAmount;
    }


    /**
     * Sets the debitAmount value for this WSWSVoucher.
     *
     * @param debitAmount
     */
    public void setDebitAmount(double debitAmount) {
        this.debitAmount = debitAmount;
    }


    /**
     * Gets the description value for this WSWSVoucher.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }


    /**
     * Sets the description value for this WSWSVoucher.
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * Gets the accountNumber value for this WSWSVoucher.
     *
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }


    /**
     * Sets the accountNumber value for this WSWSVoucher.
     *
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    /**
     * Gets the originalAmount value for this WSWSVoucher.
     *
     * @return originalAmount
     */
    public double getOriginalAmount() {
        return originalAmount;
    }


    /**
     * Sets the originalAmount value for this WSWSVoucher.
     *
     * @param originalAmount
     */
    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }


    /**
     * Gets the asstActName1 value for this WSWSVoucher.
     *
     * @return asstActName1
     */
    public String getAsstActName1() {
        return asstActName1;
    }


    /**
     * Sets the asstActName1 value for this WSWSVoucher.
     *
     * @param asstActName1
     */
    public void setAsstActName1(String asstActName1) {
        this.asstActName1 = asstActName1;
    }


    /**
     * Gets the type value for this WSWSVoucher.
     *
     * @return type
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the type value for this WSWSVoucher.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }


    /**
     * Gets the isCheck value for this WSWSVoucher.
     *
     * @return isCheck
     */
    public boolean isIsCheck() {
        return isCheck;
    }


    /**
     * Sets the isCheck value for this WSWSVoucher.
     *
     * @param isCheck
     */
    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }


    /**
     * Gets the cashAsstActName8 value for this WSWSVoucher.
     *
     * @return cashAsstActName8
     */
    public String getCashAsstActName8() {
        return cashAsstActName8;
    }


    /**
     * Sets the cashAsstActName8 value for this WSWSVoucher.
     *
     * @param cashAsstActName8
     */
    public void setCashAsstActName8(String cashAsstActName8) {
        this.cashAsstActName8 = cashAsstActName8;
    }


    /**
     * Gets the cashAsstActName7 value for this WSWSVoucher.
     *
     * @return cashAsstActName7
     */
    public String getCashAsstActName7() {
        return cashAsstActName7;
    }


    /**
     * Sets the cashAsstActName7 value for this WSWSVoucher.
     *
     * @param cashAsstActName7
     */
    public void setCashAsstActName7(String cashAsstActName7) {
        this.cashAsstActName7 = cashAsstActName7;
    }


    /**
     * Gets the cashAsstActNumber7 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber7
     */
    public String getCashAsstActNumber7() {
        return cashAsstActNumber7;
    }


    /**
     * Sets the cashAsstActNumber7 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber7
     */
    public void setCashAsstActNumber7(String cashAsstActNumber7) {
        this.cashAsstActNumber7 = cashAsstActNumber7;
    }


    /**
     * Gets the customerNumber value for this WSWSVoucher.
     *
     * @return customerNumber
     */
    public String getCustomerNumber() {
        return customerNumber;
    }


    /**
     * Sets the customerNumber value for this WSWSVoucher.
     *
     * @param customerNumber
     */
    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }


    /**
     * Gets the asstActName7 value for this WSWSVoucher.
     *
     * @return asstActName7
     */
    public String getAsstActName7() {
        return asstActName7;
    }


    /**
     * Sets the asstActName7 value for this WSWSVoucher.
     *
     * @param asstActName7
     */
    public void setAsstActName7(String asstActName7) {
        this.asstActName7 = asstActName7;
    }


    /**
     * Gets the cashAsstActNumber8 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber8
     */
    public String getCashAsstActNumber8() {
        return cashAsstActNumber8;
    }


    /**
     * Sets the cashAsstActNumber8 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber8
     */
    public void setCashAsstActNumber8(String cashAsstActNumber8) {
        this.cashAsstActNumber8 = cashAsstActNumber8;
    }


    /**
     * Gets the asstActName6 value for this WSWSVoucher.
     *
     * @return asstActName6
     */
    public String getAsstActName6() {
        return asstActName6;
    }


    /**
     * Sets the asstActName6 value for this WSWSVoucher.
     *
     * @param asstActName6
     */
    public void setAsstActName6(String asstActName6) {
        this.asstActName6 = asstActName6;
    }


    /**
     * Gets the cashAsstActNumber5 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber5
     */
    public String getCashAsstActNumber5() {
        return cashAsstActNumber5;
    }


    /**
     * Sets the cashAsstActNumber5 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber5
     */
    public void setCashAsstActNumber5(String cashAsstActNumber5) {
        this.cashAsstActNumber5 = cashAsstActNumber5;
    }


    /**
     * Gets the cashAsstActNumber6 value for this WSWSVoucher.
     *
     * @return cashAsstActNumber6
     */
    public String getCashAsstActNumber6() {
        return cashAsstActNumber6;
    }


    /**
     * Sets the cashAsstActNumber6 value for this WSWSVoucher.
     *
     * @param cashAsstActNumber6
     */
    public void setCashAsstActNumber6(String cashAsstActNumber6) {
        this.cashAsstActNumber6 = cashAsstActNumber6;
    }


    /**
     * Gets the asstActName8 value for this WSWSVoucher.
     *
     * @return asstActName8
     */
    public String getAsstActName8() {
        return asstActName8;
    }


    /**
     * Sets the asstActName8 value for this WSWSVoucher.
     *
     * @param asstActName8
     */
    public void setAsstActName8(String asstActName8) {
        this.asstActName8 = asstActName8;
    }


    /**
     * Gets the auditor value for this WSWSVoucher.
     *
     * @return auditor
     */
    public String getAuditor() {
        return auditor;
    }


    /**
     * Sets the auditor value for this WSWSVoucher.
     *
     * @param auditor
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }


    /**
     * Gets the asstActName3 value for this WSWSVoucher.
     *
     * @return asstActName3
     */
    public String getAsstActName3() {
        return asstActName3;
    }


    /**
     * Sets the asstActName3 value for this WSWSVoucher.
     *
     * @param asstActName3
     */
    public void setAsstActName3(String asstActName3) {
        this.asstActName3 = asstActName3;
    }


    /**
     * Gets the asstActName2 value for this WSWSVoucher.
     *
     * @return asstActName2
     */
    public String getAsstActName2() {
        return asstActName2;
    }


    /**
     * Sets the asstActName2 value for this WSWSVoucher.
     *
     * @param asstActName2
     */
    public void setAsstActName2(String asstActName2) {
        this.asstActName2 = asstActName2;
    }


    /**
     * Gets the asstActName5 value for this WSWSVoucher.
     *
     * @return asstActName5
     */
    public String getAsstActName5() {
        return asstActName5;
    }


    /**
     * Sets the asstActName5 value for this WSWSVoucher.
     *
     * @param asstActName5
     */
    public void setAsstActName5(String asstActName5) {
        this.asstActName5 = asstActName5;
    }


    /**
     * Gets the asstActName4 value for this WSWSVoucher.
     *
     * @return asstActName4
     */
    public String getAsstActName4() {
        return asstActName4;
    }


    /**
     * Sets the asstActName4 value for this WSWSVoucher.
     *
     * @param asstActName4
     */
    public void setAsstActName4(String asstActName4) {
        this.asstActName4 = asstActName4;
    }

    private Object __equalsCalc = null;
    @Override
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof WSWSVoucher)) {
            return false;
        }
        WSWSVoucher other = (WSWSVoucher) obj;
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
            ((this.cashAsstActType5==null && other.getCashAsstActType5()==null) ||
             (this.cashAsstActType5!=null &&
              this.cashAsstActType5.equals(other.getCashAsstActType5()))) &&
            FsscNumberUtil.isEqual(this.oppAccountSeq , other.getOppAccountSeq()) &&
            ((this.cashAsstActType4==null && other.getCashAsstActType4()==null) ||
             (this.cashAsstActType4!=null &&
              this.cashAsstActType4.equals(other.getCashAsstActType4()))) &&
            ((this.cashAsstActType3==null && other.getCashAsstActType3()==null) ||
             (this.cashAsstActType3!=null &&
              this.cashAsstActType3.equals(other.getCashAsstActType3()))) &&
            FsscNumberUtil.isEqual(this.cashflowAmountOriginal , other.getCashflowAmountOriginal()) &&
            ((this.cashAsstActType2==null && other.getCashAsstActType2()==null) ||
             (this.cashAsstActType2!=null &&
              this.cashAsstActType2.equals(other.getCashAsstActType2()))) &&
            ((this.bizDate==null && other.getBizDate()==null) ||
             (this.bizDate!=null &&
              this.bizDate.equals(other.getBizDate()))) &&
            ((this.cashAsstActType1==null && other.getCashAsstActType1()==null) ||
             (this.cashAsstActType1!=null &&
              this.cashAsstActType1.equals(other.getCashAsstActType1()))) &&
            FsscNumberUtil.isEqual(this.cashflowAmountLocal , other.getCashflowAmountLocal()) &&
            ((this.bizNumber==null && other.getBizNumber()==null) ||
             (this.bizNumber!=null &&
              this.bizNumber.equals(other.getBizNumber()))) &&
            ((this.companyNumber==null && other.getCompanyNumber()==null) ||
             (this.companyNumber!=null &&
              this.companyNumber.equals(other.getCompanyNumber()))) &&
            ((this.voucherType==null && other.getVoucherType()==null) ||
             (this.voucherType!=null &&
              this.voucherType.equals(other.getVoucherType()))) &&
            FsscNumberUtil.isEqual(this.attaches , other.getAttaches()) &&
            ((this.tempNumber==null && other.getTempNumber()==null) ||
             (this.tempNumber!=null &&
              this.tempNumber.equals(other.getTempNumber()))) &&
            ((this.cashAsstActType8==null && other.getCashAsstActType8()==null) ||
             (this.cashAsstActType8!=null &&
              this.cashAsstActType8.equals(other.getCashAsstActType8()))) &&
            ((this.cashAsstActType6==null && other.getCashAsstActType6()==null) ||
             (this.cashAsstActType6!=null &&
              this.cashAsstActType6.equals(other.getCashAsstActType6()))) &&
            ((this.cashAsstActType7==null && other.getCashAsstActType7()==null) ||
             (this.cashAsstActType7!=null &&
              this.cashAsstActType7.equals(other.getCashAsstActType7()))) &&
            FsscNumberUtil.isEqual(this.cussent , other.getCussent()) &&
            FsscNumberUtil.isEqual(this.entrySeq , other.getEntrySeq()) &&
            ((this.currencyNumber==null && other.getCurrencyNumber()==null) ||
             (this.currencyNumber!=null &&
              this.currencyNumber.equals(other.getCurrencyNumber()))) &&
            FsscNumberUtil.isEqual(this.supplyCoef , other.getSupplyCoef()) &&
            ((this.asstActType5==null && other.getAsstActType5()==null) ||
             (this.asstActType5!=null &&
              this.asstActType5.equals(other.getAsstActType5()))) &&
            FsscNumberUtil.isEqual(this.asstSeq , other.getAsstSeq()) &&
            ((this.asstActType6==null && other.getAsstActType6()==null) ||
             (this.asstActType6!=null &&
              this.asstActType6.equals(other.getAsstActType6()))) &&
            ((this.asstActType7==null && other.getAsstActType7()==null) ||
             (this.asstActType7!=null &&
              this.asstActType7.equals(other.getAsstActType7()))) &&
            ((this.asstActType8==null && other.getAsstActType8()==null) ||
             (this.asstActType8!=null &&
              this.asstActType8.equals(other.getAsstActType8()))) &&
            ((this.asstActType1==null && other.getAsstActType1()==null) ||
             (this.asstActType1!=null &&
              this.asstActType1.equals(other.getAsstActType1()))) &&
            ((this.bookedDate==null && other.getBookedDate()==null) ||
             (this.bookedDate!=null &&
              this.bookedDate.equals(other.getBookedDate()))) &&
            ((this.asstActType2==null && other.getAsstActType2()==null) ||
             (this.asstActType2!=null &&
              this.asstActType2.equals(other.getAsstActType2()))) &&
            ((this.asstActType3==null && other.getAsstActType3()==null) ||
             (this.asstActType3!=null &&
              this.asstActType3.equals(other.getAsstActType3()))) &&
            ((this.asstActType4==null && other.getAsstActType4()==null) ||
             (this.asstActType4!=null &&
              this.asstActType4.equals(other.getAsstActType4()))) &&
            ((this.assistBizDate==null && other.getAssistBizDate()==null) ||
             (this.assistBizDate!=null &&
              this.assistBizDate.equals(other.getAssistBizDate()))) &&
            ((this.creator==null && other.getCreator()==null) ||
             (this.creator!=null &&
              this.creator.equals(other.getCreator()))) &&
            ((this.supplyItem==null && other.getSupplyItem()==null) ||
             (this.supplyItem!=null &&
              this.supplyItem.equals(other.getSupplyItem()))) &&
            ((this.icketNumber==null && other.getIcketNumber()==null) ||
             (this.icketNumber!=null &&
              this.icketNumber.equals(other.getIcketNumber()))) &&
            FsscNumberUtil.isEqual(this.cashflowAmountRpt , other.getCashflowAmountRpt()) &&
            FsscNumberUtil.isEqual(this.itemFlag , other.getItemFlag()) &&
            FsscNumberUtil.isEqual(this.entryDC , other.getEntryDC()) &&
            ((this.cashAsstActNumber2==null && other.getCashAsstActNumber2()==null) ||
             (this.cashAsstActNumber2!=null &&
              this.cashAsstActNumber2.equals(other.getCashAsstActNumber2()))) &&
            ((this.cashAsstActNumber1==null && other.getCashAsstActNumber1()==null) ||
             (this.cashAsstActNumber1!=null &&
              this.cashAsstActNumber1.equals(other.getCashAsstActNumber1()))) &&
            ((this.voucherNumber==null && other.getVoucherNumber()==null) ||
             (this.voucherNumber!=null &&
              this.voucherNumber.equals(other.getVoucherNumber()))) &&
            ((this.cashAsstActNumber4==null && other.getCashAsstActNumber4()==null) ||
             (this.cashAsstActNumber4!=null &&
              this.cashAsstActNumber4.equals(other.getCashAsstActNumber4()))) &&
            ((this.cashAsstActNumber3==null && other.getCashAsstActNumber3()==null) ||
             (this.cashAsstActNumber3!=null &&
              this.cashAsstActNumber3.equals(other.getCashAsstActNumber3()))) &&
            FsscNumberUtil.isEqual(this.creditAmount , other.getCreditAmount()) &&
            ((this.poster==null && other.getPoster()==null) ||
             (this.poster!=null &&
              this.poster.equals(other.getPoster()))) &&
            ((this.cashAsstActName5==null && other.getCashAsstActName5()==null) ||
             (this.cashAsstActName5!=null &&
              this.cashAsstActName5.equals(other.getCashAsstActName5()))) &&
            ((this.asstActNumber1==null && other.getAsstActNumber1()==null) ||
             (this.asstActNumber1!=null &&
              this.asstActNumber1.equals(other.getAsstActNumber1()))) &&
            ((this.cashAsstActName6==null && other.getCashAsstActName6()==null) ||
             (this.cashAsstActName6!=null &&
              this.cashAsstActName6.equals(other.getCashAsstActName6()))) &&
            ((this.asstActNumber2==null && other.getAsstActNumber2()==null) ||
             (this.asstActNumber2!=null &&
              this.asstActNumber2.equals(other.getAsstActNumber2()))) &&
            ((this.cashAsstActName3==null && other.getCashAsstActName3()==null) ||
             (this.cashAsstActName3!=null &&
              this.cashAsstActName3.equals(other.getCashAsstActName3()))) &&
            ((this.cashAsstActName4==null && other.getCashAsstActName4()==null) ||
             (this.cashAsstActName4!=null &&
              this.cashAsstActName4.equals(other.getCashAsstActName4()))) &&
            ((this.asstActNumber3==null && other.getAsstActNumber3()==null) ||
             (this.asstActNumber3!=null &&
              this.asstActNumber3.equals(other.getAsstActNumber3()))) &&
            ((this.cashAsstActName1==null && other.getCashAsstActName1()==null) ||
             (this.cashAsstActName1!=null &&
              this.cashAsstActName1.equals(other.getCashAsstActName1()))) &&
            ((this.asstActNumber4==null && other.getAsstActNumber4()==null) ||
             (this.asstActNumber4!=null &&
              this.asstActNumber4.equals(other.getAsstActNumber4()))) &&
            FsscNumberUtil.isEqual(this.price , other.getPrice()) &&
            FsscNumberUtil.isEqual(this.periodYear , other.getPeriodYear()) &&
            ((this.asstActNumber5==null && other.getAsstActNumber5()==null) ||
             (this.asstActNumber5!=null &&
              this.asstActNumber5.equals(other.getAsstActNumber5()))) &&
            ((this.cashAsstActName2==null && other.getCashAsstActName2()==null) ||
             (this.cashAsstActName2!=null &&
              this.cashAsstActName2.equals(other.getCashAsstActName2()))) &&
            ((this.asstActNumber6==null && other.getAsstActNumber6()==null) ||
             (this.asstActNumber6!=null &&
              this.asstActNumber6.equals(other.getAsstActNumber6()))) &&
            ((this.asstActNumber7==null && other.getAsstActNumber7()==null) ||
             (this.asstActNumber7!=null &&
              this.asstActNumber7.equals(other.getAsstActNumber7()))) &&
            ((this.measurement==null && other.getMeasurement()==null) ||
             (this.measurement!=null &&
              this.measurement.equals(other.getMeasurement()))) &&
            FsscNumberUtil.isEqual(this.localRate , other.getLocalRate()) &&
            ((this.asstActNumber8==null && other.getAsstActNumber8()==null) ||
             (this.asstActNumber8!=null &&
              this.asstActNumber8.equals(other.getAsstActNumber8()))) &&
            FsscNumberUtil.isEqual(this.periodNumber , other.getPeriodNumber()) &&
            ((this.invoiceNumber==null && other.getInvoiceNumber()==null) ||
             (this.invoiceNumber!=null &&
              this.invoiceNumber.equals(other.getInvoiceNumber()))) &&
            FsscNumberUtil.isEqual(this.qty , other.getQty()) &&
            ((this.settlementType==null && other.getSettlementType()==null) ||
             (this.settlementType!=null &&
              this.settlementType.equals(other.getSettlementType()))) &&
            ((this.primaryItem==null && other.getPrimaryItem()==null) ||
             (this.primaryItem!=null &&
              this.primaryItem.equals(other.getPrimaryItem()))) &&
            ((this.assistEndDate==null && other.getAssistEndDate()==null) ||
             (this.assistEndDate!=null &&
              this.assistEndDate.equals(other.getAssistEndDate()))) &&
            FsscNumberUtil.isEqual(this.primaryCoef , other.getPrimaryCoef()) &&
            ((this.voucherAbstract==null && other.getVoucherAbstract()==null) ||
             (this.voucherAbstract!=null &&
              this.voucherAbstract.equals(other.getVoucherAbstract()))) &&
            ((this.settlementNumber==null && other.getSettlementNumber()==null) ||
             (this.settlementNumber!=null &&
              this.settlementNumber.equals(other.getSettlementNumber()))) &&
            FsscNumberUtil.isEqual(this.debitAmount , other.getDebitAmount()) &&
            ((this.description==null && other.getDescription()==null) ||
             (this.description!=null &&
              this.description.equals(other.getDescription()))) &&
            ((this.accountNumber==null && other.getAccountNumber()==null) ||
             (this.accountNumber!=null &&
              this.accountNumber.equals(other.getAccountNumber()))) &&
            FsscNumberUtil.isEqual(this.originalAmount , other.getOriginalAmount()) &&
            ((this.asstActName1==null && other.getAsstActName1()==null) ||
             (this.asstActName1!=null &&
              this.asstActName1.equals(other.getAsstActName1()))) &&
            ((this.type==null && other.getType()==null) ||
             (this.type!=null &&
              this.type.equals(other.getType()))) &&
            Boolean.valueOf(this.isCheck).equals(Boolean.valueOf(other.isIsCheck())) &&
            ((this.cashAsstActName8==null && other.getCashAsstActName8()==null) ||
             (this.cashAsstActName8!=null &&
              this.cashAsstActName8.equals(other.getCashAsstActName8()))) &&
            ((this.cashAsstActName7==null && other.getCashAsstActName7()==null) ||
             (this.cashAsstActName7!=null &&
              this.cashAsstActName7.equals(other.getCashAsstActName7()))) &&
            ((this.cashAsstActNumber7==null && other.getCashAsstActNumber7()==null) ||
             (this.cashAsstActNumber7!=null &&
              this.cashAsstActNumber7.equals(other.getCashAsstActNumber7()))) &&
            ((this.customerNumber==null && other.getCustomerNumber()==null) ||
             (this.customerNumber!=null &&
              this.customerNumber.equals(other.getCustomerNumber()))) &&
            ((this.asstActName7==null && other.getAsstActName7()==null) ||
             (this.asstActName7!=null &&
              this.asstActName7.equals(other.getAsstActName7()))) &&
            ((this.cashAsstActNumber8==null && other.getCashAsstActNumber8()==null) ||
             (this.cashAsstActNumber8!=null &&
              this.cashAsstActNumber8.equals(other.getCashAsstActNumber8()))) &&
            ((this.asstActName6==null && other.getAsstActName6()==null) ||
             (this.asstActName6!=null &&
              this.asstActName6.equals(other.getAsstActName6()))) &&
            ((this.cashAsstActNumber5==null && other.getCashAsstActNumber5()==null) ||
             (this.cashAsstActNumber5!=null &&
              this.cashAsstActNumber5.equals(other.getCashAsstActNumber5()))) &&
            ((this.cashAsstActNumber6==null && other.getCashAsstActNumber6()==null) ||
             (this.cashAsstActNumber6!=null &&
              this.cashAsstActNumber6.equals(other.getCashAsstActNumber6()))) &&
            ((this.asstActName8==null && other.getAsstActName8()==null) ||
             (this.asstActName8!=null &&
              this.asstActName8.equals(other.getAsstActName8()))) &&
            ((this.auditor==null && other.getAuditor()==null) ||
             (this.auditor!=null &&
              this.auditor.equals(other.getAuditor()))) &&
            ((this.asstActName3==null && other.getAsstActName3()==null) ||
             (this.asstActName3!=null &&
              this.asstActName3.equals(other.getAsstActName3()))) &&
            ((this.asstActName2==null && other.getAsstActName2()==null) ||
             (this.asstActName2!=null &&
              this.asstActName2.equals(other.getAsstActName2()))) &&
            ((this.asstActName5==null && other.getAsstActName5()==null) ||
             (this.asstActName5!=null &&
              this.asstActName5.equals(other.getAsstActName5()))) &&
            ((this.asstActName4==null && other.getAsstActName4()==null) ||
             (this.asstActName4!=null &&
              this.asstActName4.equals(other.getAsstActName4())));
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
        if (getCashAsstActType5() != null) {
            _hashCode += getCashAsstActType5().hashCode();
        }
        _hashCode += getOppAccountSeq();
        if (getCashAsstActType4() != null) {
            _hashCode += getCashAsstActType4().hashCode();
        }
        if (getCashAsstActType3() != null) {
            _hashCode += getCashAsstActType3().hashCode();
        }
        _hashCode += new Double(getCashflowAmountOriginal()).hashCode();
        if (getCashAsstActType2() != null) {
            _hashCode += getCashAsstActType2().hashCode();
        }
        if (getBizDate() != null) {
            _hashCode += getBizDate().hashCode();
        }
        if (getCashAsstActType1() != null) {
            _hashCode += getCashAsstActType1().hashCode();
        }
        _hashCode += new Double(getCashflowAmountLocal()).hashCode();
        if (getBizNumber() != null) {
            _hashCode += getBizNumber().hashCode();
        }
        if (getCompanyNumber() != null) {
            _hashCode += getCompanyNumber().hashCode();
        }
        if (getVoucherType() != null) {
            _hashCode += getVoucherType().hashCode();
        }
        _hashCode += getAttaches();
        if (getTempNumber() != null) {
            _hashCode += getTempNumber().hashCode();
        }
        if (getCashAsstActType8() != null) {
            _hashCode += getCashAsstActType8().hashCode();
        }
        if (getCashAsstActType6() != null) {
            _hashCode += getCashAsstActType6().hashCode();
        }
        if (getCashAsstActType7() != null) {
            _hashCode += getCashAsstActType7().hashCode();
        }
        _hashCode += getCussent();
        _hashCode += getEntrySeq();
        if (getCurrencyNumber() != null) {
            _hashCode += getCurrencyNumber().hashCode();
        }
        _hashCode += new Double(getSupplyCoef()).hashCode();
        if (getAsstActType5() != null) {
            _hashCode += getAsstActType5().hashCode();
        }
        _hashCode += getAsstSeq();
        if (getAsstActType6() != null) {
            _hashCode += getAsstActType6().hashCode();
        }
        if (getAsstActType7() != null) {
            _hashCode += getAsstActType7().hashCode();
        }
        if (getAsstActType8() != null) {
            _hashCode += getAsstActType8().hashCode();
        }
        if (getAsstActType1() != null) {
            _hashCode += getAsstActType1().hashCode();
        }
        if (getBookedDate() != null) {
            _hashCode += getBookedDate().hashCode();
        }
        if (getAsstActType2() != null) {
            _hashCode += getAsstActType2().hashCode();
        }
        if (getAsstActType3() != null) {
            _hashCode += getAsstActType3().hashCode();
        }
        if (getAsstActType4() != null) {
            _hashCode += getAsstActType4().hashCode();
        }
        if (getAssistBizDate() != null) {
            _hashCode += getAssistBizDate().hashCode();
        }
        if (getCreator() != null) {
            _hashCode += getCreator().hashCode();
        }
        if (getSupplyItem() != null) {
            _hashCode += getSupplyItem().hashCode();
        }
        if (getIcketNumber() != null) {
            _hashCode += getIcketNumber().hashCode();
        }
        _hashCode += new Double(getCashflowAmountRpt()).hashCode();
        _hashCode += getItemFlag();
        _hashCode += getEntryDC();
        if (getCashAsstActNumber2() != null) {
            _hashCode += getCashAsstActNumber2().hashCode();
        }
        if (getCashAsstActNumber1() != null) {
            _hashCode += getCashAsstActNumber1().hashCode();
        }
        if (getVoucherNumber() != null) {
            _hashCode += getVoucherNumber().hashCode();
        }
        if (getCashAsstActNumber4() != null) {
            _hashCode += getCashAsstActNumber4().hashCode();
        }
        if (getCashAsstActNumber3() != null) {
            _hashCode += getCashAsstActNumber3().hashCode();
        }
        _hashCode += new Double(getCreditAmount()).hashCode();
        if (getPoster() != null) {
            _hashCode += getPoster().hashCode();
        }
        if (getCashAsstActName5() != null) {
            _hashCode += getCashAsstActName5().hashCode();
        }
        if (getAsstActNumber1() != null) {
            _hashCode += getAsstActNumber1().hashCode();
        }
        if (getCashAsstActName6() != null) {
            _hashCode += getCashAsstActName6().hashCode();
        }
        if (getAsstActNumber2() != null) {
            _hashCode += getAsstActNumber2().hashCode();
        }
        if (getCashAsstActName3() != null) {
            _hashCode += getCashAsstActName3().hashCode();
        }
        if (getCashAsstActName4() != null) {
            _hashCode += getCashAsstActName4().hashCode();
        }
        if (getAsstActNumber3() != null) {
            _hashCode += getAsstActNumber3().hashCode();
        }
        if (getCashAsstActName1() != null) {
            _hashCode += getCashAsstActName1().hashCode();
        }
        if (getAsstActNumber4() != null) {
            _hashCode += getAsstActNumber4().hashCode();
        }
        _hashCode += new Double(getPrice()).hashCode();
        _hashCode += getPeriodYear();
        if (getAsstActNumber5() != null) {
            _hashCode += getAsstActNumber5().hashCode();
        }
        if (getCashAsstActName2() != null) {
            _hashCode += getCashAsstActName2().hashCode();
        }
        if (getAsstActNumber6() != null) {
            _hashCode += getAsstActNumber6().hashCode();
        }
        if (getAsstActNumber7() != null) {
            _hashCode += getAsstActNumber7().hashCode();
        }
        if (getMeasurement() != null) {
            _hashCode += getMeasurement().hashCode();
        }
        _hashCode += new Double(getLocalRate()).hashCode();
        if (getAsstActNumber8() != null) {
            _hashCode += getAsstActNumber8().hashCode();
        }
        _hashCode += getPeriodNumber();
        if (getInvoiceNumber() != null) {
            _hashCode += getInvoiceNumber().hashCode();
        }
        _hashCode += new Double(getQty()).hashCode();
        if (getSettlementType() != null) {
            _hashCode += getSettlementType().hashCode();
        }
        if (getPrimaryItem() != null) {
            _hashCode += getPrimaryItem().hashCode();
        }
        if (getAssistEndDate() != null) {
            _hashCode += getAssistEndDate().hashCode();
        }
        _hashCode += new Double(getPrimaryCoef()).hashCode();
        if (getVoucherAbstract() != null) {
            _hashCode += getVoucherAbstract().hashCode();
        }
        if (getSettlementNumber() != null) {
            _hashCode += getSettlementNumber().hashCode();
        }
        _hashCode += new Double(getDebitAmount()).hashCode();
        if (getDescription() != null) {
            _hashCode += getDescription().hashCode();
        }
        if (getAccountNumber() != null) {
            _hashCode += getAccountNumber().hashCode();
        }
        _hashCode += new Double(getOriginalAmount()).hashCode();
        if (getAsstActName1() != null) {
            _hashCode += getAsstActName1().hashCode();
        }
        if (getType() != null) {
            _hashCode += getType().hashCode();
        }
        _hashCode += (isIsCheck() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getCashAsstActName8() != null) {
            _hashCode += getCashAsstActName8().hashCode();
        }
        if (getCashAsstActName7() != null) {
            _hashCode += getCashAsstActName7().hashCode();
        }
        if (getCashAsstActNumber7() != null) {
            _hashCode += getCashAsstActNumber7().hashCode();
        }
        if (getCustomerNumber() != null) {
            _hashCode += getCustomerNumber().hashCode();
        }
        if (getAsstActName7() != null) {
            _hashCode += getAsstActName7().hashCode();
        }
        if (getCashAsstActNumber8() != null) {
            _hashCode += getCashAsstActNumber8().hashCode();
        }
        if (getAsstActName6() != null) {
            _hashCode += getAsstActName6().hashCode();
        }
        if (getCashAsstActNumber5() != null) {
            _hashCode += getCashAsstActNumber5().hashCode();
        }
        if (getCashAsstActNumber6() != null) {
            _hashCode += getCashAsstActNumber6().hashCode();
        }
        if (getAsstActName8() != null) {
            _hashCode += getAsstActName8().hashCode();
        }
        if (getAuditor() != null) {
            _hashCode += getAuditor().hashCode();
        }
        if (getAsstActName3() != null) {
            _hashCode += getAsstActName3().hashCode();
        }
        if (getAsstActName2() != null) {
            _hashCode += getAsstActName2().hashCode();
        }
        if (getAsstActName5() != null) {
            _hashCode += getAsstActName5().hashCode();
        }
        if (getAsstActName4() != null) {
            _hashCode += getAsstActName4().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSWSVoucher.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn:client.wsvoucher", "WSWSVoucher"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("oppAccountSeq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oppAccountSeq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashflowAmountOriginal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashflowAmountOriginal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bizDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bizDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashflowAmountLocal");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashflowAmountLocal"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bizNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bizNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("companyNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "companyNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voucherType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "voucherType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attaches");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attaches"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tempNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tempNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActType7");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActType7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cussent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cussent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entrySeq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entrySeq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currencyNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "currencyNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supplyCoef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supplyCoef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstSeq");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstSeq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType7");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bookedDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "bookedDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActType4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActType4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assistBizDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assistBizDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creator");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creator"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supplyItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supplyItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("icketNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "icketNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashflowAmountRpt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashflowAmountRpt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("itemFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "itemFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryDC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "entryDC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voucherNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "voucherNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("poster");
        elemField.setXmlName(new javax.xml.namespace.QName("", "poster"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActNumber1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActNumber2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActNumber3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActNumber4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber4"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("", "price"));
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
        elemField.setFieldName("asstActNumber5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActNumber6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActNumber7");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("measurement");
        elemField.setXmlName(new javax.xml.namespace.QName("", "measurement"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("localRate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "localRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActNumber8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActNumber8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("invoiceNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "invoiceNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qty");
        elemField.setXmlName(new javax.xml.namespace.QName("", "qty"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settlementType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settlementType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryItem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryItem"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("assistEndDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "assistEndDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primaryCoef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primaryCoef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("voucherAbstract");
        elemField.setXmlName(new javax.xml.namespace.QName("", "voucherAbstract"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("settlementNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "settlementNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("debitAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "debitAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("description");
        elemField.setXmlName(new javax.xml.namespace.QName("", "description"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "accountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("originalAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "originalAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("type");
        elemField.setXmlName(new javax.xml.namespace.QName("", "type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isCheck");
        elemField.setXmlName(new javax.xml.namespace.QName("", "isCheck"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActName7");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActName7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber7");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "customerNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName7");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName7"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cashAsstActNumber6");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cashAsstActNumber6"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName8");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName8"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("auditor");
        elemField.setXmlName(new javax.xml.namespace.QName("", "auditor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName3");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName3"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName5");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName5"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("asstActName4");
        elemField.setXmlName(new javax.xml.namespace.QName("", "asstActName4"));
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

	@Override
	public String toString() {
		return "WSWSVoucher [cashAsstActType5=" + cashAsstActType5 + ", oppAccountSeq=" + oppAccountSeq
				+ ", cashAsstActType4=" + cashAsstActType4 + ", cashAsstActType3=" + cashAsstActType3
				+ ", cashflowAmountOriginal=" + cashflowAmountOriginal + ", cashAsstActType2=" + cashAsstActType2
				+ ", bizDate=" + bizDate + ", cashAsstActType1=" + cashAsstActType1 + ", cashflowAmountLocal="
				+ cashflowAmountLocal + ", bizNumber=" + bizNumber + ", companyNumber=" + companyNumber
				+ ", voucherType=" + voucherType + ", attaches=" + attaches + ", tempNumber=" + tempNumber
				+ ", cashAsstActType8=" + cashAsstActType8 + ", cashAsstActType6=" + cashAsstActType6
				+ ", cashAsstActType7=" + cashAsstActType7 + ", cussent=" + cussent + ", entrySeq=" + entrySeq
				+ ", currencyNumber=" + currencyNumber + ", supplyCoef=" + supplyCoef + ", asstActType5=" + asstActType5
				+ ", asstSeq=" + asstSeq + ", asstActType6=" + asstActType6 + ", asstActType7=" + asstActType7
				+ ", asstActType8=" + asstActType8 + ", asstActType1=" + asstActType1 + ", bookedDate=" + bookedDate
				+ ", asstActType2=" + asstActType2 + ", asstActType3=" + asstActType3 + ", asstActType4=" + asstActType4
				+ ", assistBizDate=" + assistBizDate + ", creator=" + creator + ", supplyItem=" + supplyItem
				+ ", icketNumber=" + icketNumber + ", cashflowAmountRpt=" + cashflowAmountRpt + ", itemFlag=" + itemFlag
				+ ", entryDC=" + entryDC + ", cashAsstActNumber2=" + cashAsstActNumber2 + ", cashAsstActNumber1="
				+ cashAsstActNumber1 + ", voucherNumber=" + voucherNumber + ", cashAsstActNumber4=" + cashAsstActNumber4
				+ ", cashAsstActNumber3=" + cashAsstActNumber3 + ", creditAmount=" + creditAmount + ", poster=" + poster
				+ ", cashAsstActName5=" + cashAsstActName5 + ", asstActNumber1=" + asstActNumber1
				+ ", cashAsstActName6=" + cashAsstActName6 + ", asstActNumber2=" + asstActNumber2
				+ ", cashAsstActName3=" + cashAsstActName3 + ", cashAsstActName4=" + cashAsstActName4
				+ ", asstActNumber3=" + asstActNumber3 + ", cashAsstActName1=" + cashAsstActName1 + ", asstActNumber4="
				+ asstActNumber4 + ", price=" + price + ", periodYear=" + periodYear + ", asstActNumber5="
				+ asstActNumber5 + ", cashAsstActName2=" + cashAsstActName2 + ", asstActNumber6=" + asstActNumber6
				+ ", asstActNumber7=" + asstActNumber7 + ", measurement=" + measurement + ", localRate=" + localRate
				+ ", asstActNumber8=" + asstActNumber8 + ", periodNumber=" + periodNumber + ", invoiceNumber="
				+ invoiceNumber + ", qty=" + qty + ", settlementType=" + settlementType + ", primaryItem=" + primaryItem
				+ ", assistEndDate=" + assistEndDate + ", primaryCoef=" + primaryCoef + ", voucherAbstract="
				+ voucherAbstract + ", settlementNumber=" + settlementNumber + ", debitAmount=" + debitAmount
				+ ", description=" + description + ", accountNumber=" + accountNumber + ", originalAmount="
				+ originalAmount + ", asstActName1=" + asstActName1 + ", type=" + type + ", isCheck=" + isCheck
				+ ", cashAsstActName8=" + cashAsstActName8 + ", cashAsstActName7=" + cashAsstActName7
				+ ", cashAsstActNumber7=" + cashAsstActNumber7 + ", customerNumber=" + customerNumber
				+ ", asstActName7=" + asstActName7 + ", cashAsstActNumber8=" + cashAsstActNumber8 + ", asstActName6="
				+ asstActName6 + ", cashAsstActNumber5=" + cashAsstActNumber5 + ", cashAsstActNumber6="
				+ cashAsstActNumber6 + ", asstActName8=" + asstActName8 + ", auditor=" + auditor + ", asstActName3="
				+ asstActName3 + ", asstActName2=" + asstActName2 + ", asstActName5=" + asstActName5 + ", asstActName4="
				+ asstActName4 + "]";
	}
}
