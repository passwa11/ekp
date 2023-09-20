package com.landray.kmss.eop.basedata.vo;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * 收/付款Vo
 */
public class EopBaseDataCollectionOrPaymentVo {

    private String docNumber; //合同编号
    private String fdPeriod;  //期次
    private String fdName; //收/付款申请人
    private String sign; //收/付款标识
    private String register; //收款登记
    private String fdSttType; //结算方式
    private Double fdOpsAmt; //收/付款金额
    private String invoiceVoucher; //收款凭证
    private String fdDocLink; //单据链接
    private String InvoiceNumber; //单据编号
    private Date fdOpsDate; //收/付款时间


    public String getFdName() {
        return fdName;
    }

    public void setFdName(String fdName) {
        this.fdName = fdName;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getFdPeriod() {
        return fdPeriod;
    }

    public void setFdPeriod(String fdPeriod) {
        this.fdPeriod = fdPeriod;
    }

    public String getFdSttType() {
        return fdSttType;
    }

    public void setFdSttType(String fdSttType) {
        this.fdSttType = fdSttType;
    }

    public Double getFdOpsAmt() {
        return fdOpsAmt;
    }

    public void setFdOpsAmt(Double fdOpsAmt) {
        this.fdOpsAmt = fdOpsAmt;
    }

    public String getFdDocLink() {
        return fdDocLink;
    }

    public void setFdDocLink(String fdDocLink) {
        this.fdDocLink = fdDocLink;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getInvoiceVoucher() {
        return invoiceVoucher;
    }

    public void setInvoiceVoucher(String invoiceVoucher) {
        this.invoiceVoucher = invoiceVoucher;
    }

    public Date getFdOpsDate() {
        return fdOpsDate;
    }

    public void setFdOpsDate(Date fdOpsDate) {
        this.fdOpsDate = fdOpsDate;
    }
}
