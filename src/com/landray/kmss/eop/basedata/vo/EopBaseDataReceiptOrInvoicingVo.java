package com.landray.kmss.eop.basedata.vo;

import java.util.Date;

/**
 * 收/开票Vo
 */
public class EopBaseDataReceiptOrInvoicingVo {

    private String docNumber; // 合同编号

    private String fdPeriod; // 期次

    private String drawer; // 开票人

    private Date invoicingTime; // 开票时间

    private  Double  invoicingAmount; // 开票金额

    private String invoiceNumber; // 发票号

    private String invoiceCode; // 发票代码

    private String invoicingVoucher; // 开票凭证

    private String documentLink; // 单据链接

    private String  documentNumber; // 单据编号;

    private String sign; //开/收款标识

    private String fdSttType; //结算方式


    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getFdPeriod() {
        return fdPeriod;
    }

    public void setFdPeriod(String  fdPeriod) {
        this.fdPeriod = fdPeriod;
    }

    public String getDrawer() {
        return drawer;
    }

    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }

    public Date getInvoicingTime() {
        return invoicingTime;
    }

    public void setInvoicingTime(Date invoicingTime) {
        this.invoicingTime = invoicingTime;
    }

    public Double getInvoicingAmount() {
        return invoicingAmount;
    }

    public void setInvoicingAmount(Double invoicingAmount) {
        this.invoicingAmount = invoicingAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoicingVoucher() {
        return invoicingVoucher;
    }

    public void setInvoicingVoucher(String invoicingVoucher) {
        this.invoicingVoucher = invoicingVoucher;
    }

    public String getDocumentLink() {
        return documentLink;
    }

    public void setDocumentLink(String documentLink) {
        this.documentLink = documentLink;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFdSttType() {
        return fdSttType;
    }

    public void setFdSttType(String fdSttType) {
        this.fdSttType = fdSttType;
    }
}
