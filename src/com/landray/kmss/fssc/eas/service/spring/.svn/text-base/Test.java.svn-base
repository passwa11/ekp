package com.landray.kmss.fssc.eas.service.spring;
import java.io.IOException;
import java.nio.charset.Charset;

import com.landray.kmss.util.StringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
public class Test {



    public static void main(String[] arg) {

        // soap测试
        String postUrl1 = "http://192.168.1.208:6888/ormrpc/services/EASLogin";
        // 采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务
        String orderSoapXml1 = "<soapenv:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:log='http://login.webservice.bos.kingdee.com'>"
                + "   <soapenv:Header/>                                                                                                                                                                                                               "
                + "   <soapenv:Body>                                                                                                                                                                                                                  "
                + "      <log:login soapenv:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/'>                                                                                                                                                "
                + "         <userName xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>acz</userName>                                                                                                 "
                + "         <password xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>123</password>                                                                                                 "
                + "         <slnName xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>eas</slnName>                                                                                                   "
                + "         <dcName xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>d02</dcName>                                                                                                     "
                + "         <language xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>l2</language>                                                                                                  "
                + "         <dbType xsi:type='xsd:int' xs:type='type:int' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>0</dbType>                                                                                                             "
                + "      </log:login>                                                                                                                                                                                                                 "
                + "   </soapenv:Body>                                                                                                                                                                                                                 "
                + "</soapenv:Envelope>                                                                                                                                                                                                                ";
        String soap1_11 = doPostSoap1_1(postUrl1, orderSoapXml1,
                "",null);

        String fdSessionId = null;
        if(soap1_11.indexOf("</sessionId>") > -1){
            String[] temp1 = soap1_11.split("</sessionId>");
            String[] temp2 = temp1[0].split(">");
            fdSessionId = temp2[temp2.length-1];
        }


        // soap测试
        String postUrl = "http://192.168.1.208:6888/ormrpc/services/WSGLWebServiceFacade";
        // 采用SOAP1.1调用服务端，这种方式能调用服务端为soap1.1和soap1.2的服务
        String requestXml = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.app.gl.fi.eas.kingdee.com\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\"> "+
                "   <soapenv:Header/>                                                                                                                                                                                                                                                                                        "+
                "   <soapenv:Body>                                                                                                                                                                                                                                                                                           "+
                "      <web:importVoucher soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">                                                                                                                                                                                                               "+
                "         <col xsi:type=\"wsg:ArrayOf_tns3_WSWSVoucher\"                                                                                                                                                                                                                                                     "+
                "         soapenc:arrayType=\"urn:WSWSVoucher[]\"                                                                                                                                                                                                                                                            "+
                "         xmlns:wsg=\"http://192.168.1.208:6888/ormrpc/services/WSGLWebServiceFacade\"                                                                                                                                                                                                                       "+
                "         xmlns:urn=\"urn:client.wsvoucher\">                                                                                                                                                                                                                                                                "+
                "               {'accountNumber':'1002','assistBizDate':'','assistEndDate':'','asstActName1':'','asstActName2':'','asstActName3':'','asstActName4':'','asstActName5':'','asstActName6':'','asstActName7':'','asstActName8':'','asstActNumber1':'','asstActNumber2':'','asstActNumber3':'','asstActNumber4':'','asstActNumber5':'','asstActNumber6':'','asstActNumber7':'','asstActNumber8':'','asstActType1':'','asstActType2':'','asstActType3':'','asstActType4':'','asstActType5':'','asstActType6':'','asstActType7':'','asstActType8':'','asstSeq':0,'attaches':1,'auditor':'','bizDate':'2019-01-21','bizNumber':'','bookedDate':'2019-01-25','cashAsstActName1':'','cashAsstActName2':'','cashAsstActName3':'','cashAsstActName4':'','cashAsstActName5':'','cashAsstActName6':'','cashAsstActName7':'','cashAsstActName8':'','cashAsstActNumber1':'','cashAsstActNumber2':'','cashAsstActNumber3':'','cashAsstActNumber4':'','cashAsstActNumber5':'','cashAsstActNumber6':'','cashAsstActNumber7':'','cashAsstActNumber8':'','cashAsstActType1':'','cashAsstActType2':'','cashAsstActType3':'','cashAsstActType4':'','cashAsstActType5':'','cashAsstActType6':'','cashAsstActType7':'','cashAsstActType8':'','cashflowAmountLocal':0,'cashflowAmountOriginal':0,'cashflowAmountRpt':0,'companyNumber':'010000','creator':'管理员','creditAmount':0,'currencyNumber':'CNY','cussent':0,'customerNumber':'','debitAmount':12,'description':'201901031','entryDC':1,'entrySeq':1,'icketNumber':'','invoiceNumber':'','isCheck':false,'itemFlag':0,'localRate':0,'measurement':'','oppAccountSeq':0,'originalAmount':12,'periodNumber':1,'periodYear':2019,'poster':'','price':0,'primaryCoef':0,'primaryItem':'','qty':0,'settlementNumber':'','settlementType':'','supplyCoef':0,'supplyItem':'','tempNumber':'','type':'','voucherAbstract':'银行存款','voucherNumber':'201901031','voucherType':'记'}" +
                "         ,{'accountNumber':'6602','assistBizDate':'','assistEndDate':'','asstActName1':'','asstActName2':'','asstActName3':'','asstActName4':'','asstActName5':'','asstActName6':'','asstActName7':'','asstActName8':'','asstActNumber1':'','asstActNumber2':'','asstActNumber3':'','asstActNumber4':'','asstActNumber5':'','asstActNumber6':'','asstActNumber7':'','asstActNumber8':'','asstActType1':'','asstActType2':'','asstActType3':'','asstActType4':'','asstActType5':'','asstActType6':'','asstActType7':'','asstActType8':'','asstSeq':0,'attaches':1,'auditor':'','bizDate':'2019-01-21','bizNumber':'','bookedDate':'2019-01-25','cashAsstActName1':'','cashAsstActName2':'','cashAsstActName3':'','cashAsstActName4':'','cashAsstActName5':'','cashAsstActName6':'','cashAsstActName7':'','cashAsstActName8':'','cashAsstActNumber1':'','cashAsstActNumber2':'','cashAsstActNumber3':'','cashAsstActNumber4':'','cashAsstActNumber5':'','cashAsstActNumber6':'','cashAsstActNumber7':'','cashAsstActNumber8':'','cashAsstActType1':'','cashAsstActType2':'','cashAsstActType3':'','cashAsstActType4':'','cashAsstActType5':'','cashAsstActType6':'','cashAsstActType7':'','cashAsstActType8':'','cashflowAmountLocal':0,'cashflowAmountOriginal':0,'cashflowAmountRpt':0,'companyNumber':'010000','creator':'管理员','creditAmount':12,'currencyNumber':'CNY','cussent':0,'customerNumber':'','debitAmount':0,'description':'201901031','entryDC':2,'entrySeq':2,'icketNumber':'','invoiceNumber':'','isCheck':false,'itemFlag':0,'localRate':0,'measurement':'','oppAccountSeq':0,'originalAmount':12,'periodNumber':1,'periodYear':2019,'poster':'','price':0,'primaryCoef':0,'primaryItem':'','qty':0,'settlementNumber':'','settlementType':'','supplyCoef':0,'supplyItem':'','tempNumber':'','type':'','voucherAbstract':'管理费用','voucherNumber':'201901031','voucherType':'记'}" +
                "					<cashAsstActType5></cashAsstActType5>                                                                                                                                                                                                                                                              "+
                "		 </col>                                                                                                                                                                                                                                                                                                  "+
                "         <isSubmit xsi:type=\"xsd:int\">0</isSubmit>                                                                                                                                                                                                                                                        "+
                "         <isVerify xsi:type=\"xsd:int\">0</isVerify>                                                                                                                                                                                                                                                        "+
                "         <isCashflow xsi:type=\"xsd:int\">0</isCashflow>                                                                                                                                                                                                                                                    "+
                "      </web:importVoucher>                                                                                                                                                                                                                                                                                  "+
                "   </soapenv:Body>                                                                                                                                                                                                                                                                                          "+
                "</soapenv:Envelope>                                                                                                                                                                                                                                                                                         ";

        String soap1_1 = doPostSoap1_1(postUrl, requestXml,
                "", fdSessionId);
        System.out.println(soap1_1);
        System.out.println("end");

    }

    public static String doPostSoap1_1(String postUrl, String soapXml,
                                       String soapAction, String fdSessionId) {

        System.out.println("fdSessionId:"+fdSessionId);
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        // 设置请求和传输超时时间
        /*
         * RequestConfig requestConfig = RequestConfig.custom()
         * .setSocketTimeout(socketTimeout)
         * .setConnectTimeout(connectTimeout).build();
         * httpPost.setConfig(requestConfig);
         */
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", soapAction);
            httpPost.setHeader("Host", "192.168.1.208:6888");
            if(StringUtil.isNotNull(fdSessionId)){
                httpPost.setHeader("SessionId", fdSessionId);
            }
            StringEntity data = new StringEntity(soapXml,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
            }

        } catch (Exception e) {
        } finally {
            // 释放资源
            try {
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retStr;
    }


}
