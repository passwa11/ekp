package com.landray.kmss.fssc.k3.service.spring;


import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.eop.basedata.model.EopBasedataCashFlow;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.eop.basedata.model.EopBasedataCostCenter;
import com.landray.kmss.eop.basedata.model.EopBasedataCustomer;
import com.landray.kmss.eop.basedata.model.EopBasedataErpPerson;
import com.landray.kmss.eop.basedata.model.EopBasedataPayBank;
import com.landray.kmss.eop.basedata.model.EopBasedataProject;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonK3Service;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.k3.constant.FsscK3Constant;
import com.landray.kmss.fssc.k3.util.FsscK3Util;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FsscCommonK3ServiceImp extends ExtendDataServiceImp implements IFsscCommonK3Service {
	
	private static Log logger = LogFactory.getLog(FsscCommonK3ServiceImp.class);

	private IEopBasedataCompanyService eopBasedataCompanyService;
	
	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if (eopBasedataCompanyService == null) {
			eopBasedataCompanyService = (IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
        }
		return eopBasedataCompanyService;
	}
	/**
	 * 判断该凭证是否存在
	 * @param map{
	 *           fdKId:账套ID
	 *		     docNumber:费控凭证号
	 * }
	 *
	 * @return Map<String, String>
	 *     		result:success 成功，failure，失败
	 *     		fdIsExist: true存在 false不存在
	 *     		message：失败信息
	 * @throws Exception
	 */
	@Override
    public Map<String, String> isExist(Map<String, Object> map) throws Exception {
		Map<String, String> rtnMap = new ConcurrentHashMap();
    	try {
			//设置传参必填字段
			String[] validateProperty = {"fdKId","docNumber"};
			for(String property : validateProperty){
				Object val= map.containsKey(property)?map.get(property):null;
				if(val==null){
					KmssMessage msg = new KmssMessage("fssc-k3:message.setParameterError");
					throw new KmssRuntimeException(msg);
				}
			}
			String fdKId = map.get("fdKId")+"";
			String docNumber = map.get("docNumber")+"";
			String companyId = map.get("companyId").toString();

			logger.info("凭证获取开始:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			System.out.println("凭证获取开始:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			List list = new ArrayList();
			try {
				if(StringUtil.isNull(companyId)) {
					return null;
				}
				
				//获取公司信息
				EopBasedataCompany company = (EopBasedataCompany) getEopBasedataCompanyService().findByPrimaryKey(companyId);
				if(StringUtil.isNull(company.getFdKUserName()) || StringUtil.isNull(company.getFdKPassWord())) {
					return null;
				}
				
				HttpURLConnection con = initDataSet(company);
				
				// 发送Request消息
				OutputStream out = con.getOutputStream();
				//请求xml，判断当前凭证费控编号在K3中是否已经存在
				String requestXml = FsscK3Constant.K3_VOUCHER_QUERY.replace("iAisID_iAisID", fdKId).replace("strUser_strUser", company.getFdKUserName())
						.replace("strPassword_strPassword", company.getFdKPassWord())
						.replace("iPerCount_iPerCount", "1")
						.replace("FReference_FReference", docNumber);
				out.write(requestXml.getBytes());

				// 获取Response消息
				InputStream in = con.getInputStream();
				byte[] b = new byte[1024];
		        int len = 0;
		        String s = "";
		        while((len = in.read(b)) != -1){
		            String ss = new String(b,0,len,"UTF-8");
		            s += ss;
		        }
				String responseXml = s.toString();
				list = readStringXmlOut(responseXml);
				rtnMap.put("result", "success");
			} catch (Exception e) {
				e.printStackTrace();
				rtnMap.put("result", "failure");
			}

			logger.info("凭证获取结束:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			System.out.println("凭证获取结束:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			rtnMap.put("fdIsExist", !ArrayUtil.isEmpty(list)+"");//true存在 false不存在
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("result", "failure");
			rtnMap.put("message", e.getMessage());
		}
    	return rtnMap;
	}
	
	/**
	 * 凭证写入k3
	 *
	 * @throws Exception
	 */
	@Override
    public List synVoucher(Map<String, Object> map) throws Exception {
		logger.info("凭证写入开始:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("凭证写入开始:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		List list = new ArrayList();
		try {
			String companyId = map.get("companyId").toString();
			if(StringUtil.isNull(companyId)) {
				return null;
			}
			EopBasedataCompany company = (EopBasedataCompany) getEopBasedataCompanyService().findByPrimaryKey(companyId);
			if(StringUtil.isNull(company.getFdKUserName()) || StringUtil.isNull(company.getFdKPassWord())) {
				return null;
			}
			
			HttpURLConnection con = initDataSet(company);
			// 发送Request消息
			OutputStream out = con.getOutputStream();
			// 封装xml
			String requestXml = VoucherXmlBuilder(map,company).trim();
			logger.error("-----K3报文XML-----："+requestXml);
			out.write(requestXml.getBytes());
			InputStream in = con.getInputStream();
			byte[] b = new byte[1024];
	        int len = 0;
	        String s = "";
	        while((len = in.read(b)) != -1){
	            String ss = new String(b,0,len,"UTF-8");
	            s += ss;
	        }
			String responseXml = s.toString();
			Document document = DocumentHelper.parseText(responseXml); // 将字符串转为XML
			Element rootElt = document.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("UpdateResponse"); // 获取根节点下的子节点code
			Element root = document.getRootElement();
			Iterator it = root.elementIterator();
			while (it.hasNext()) {
				Element element = (Element) it.next();
				Iterator QueryResponse = element.elementIterator("UpdateResponse");
				while (QueryResponse.hasNext()) {
					Element recordEle = (Element) QueryResponse.next();
					String isSucceed = recordEle.elementTextTrim("UpdateResult");
					String strError = recordEle.elementTextTrim("strError"); // 描述
					Map map1 = new ConcurrentHashMap();
					map1.put("succeed", ("true".equals(isSucceed)?"0":""));
					map1.put("u8voucher_id", "");
					map1.put("dsc", StringUtil.isNotNull(strError)?strError:"");
					list.add(map1);
				}
			}
			in.close();
			out.close();
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("凭证写入结束:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println("凭证写入结束:" + DateUtil.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		return list;
	}
	
	/**
	 * 封装凭证xml
	 * @param map{
	 *           fdUeai:系统号
	 *		     docNumber:费控凭证号
	 *		     fdVoucherType:凭证类别
	 *		     fdAccountingYear:会计年度
	 *		     fdPeriod:期间
	 *		     fdNumber:单据数
	 *		     fdVoucherDate:凭证日期（yyyy-MM-dd）
	 *		     docCreatorName:制单人名称
	 *		     fdCashierName:出纳人名称
	 *		     fdBaseCurrencyCode:币种编号
	 *		     detailMap:Map<String, Object>{
	 *		     	fdBaseAccountsCode:科目编码
	 *		     	fdVoucherText:凭证摘要
	 *		     	fdType:借贷 1借 2贷
	 *		     	fdMoney:金额
	 *		     	fdAccountProperty:核算属性
	 *		     	fsscBaseCostCenter: FsscBaseCostCenter 成本中心
	 *				fsscBaseProject: FsscBaseProject 核算项目
	 *				fsscBaseCustomer: FsscBaseCustomer 客户
	 *				fsscBaseCashFlow: FsscBaseCashFlow 现金流量项目
	 *             	fsscBaseErpPerson: FsscBaseErpPerson 个人
	 *              fsscBasePayBank: FsscBasePayBank 银行
	 *             	fsscBaseSupplier: FsscBaseSupplier 供应商
	 *              fdDept: 部门
	 *             	fdContractCode: 合同编号
	 *             	fdContractName: 合同名称
	 *		     }
	 * }
	 *
	 * @throws Exception
	 */
	public String VoucherXmlBuilder(Map<String, Object> map,EopBasedataCompany company) throws Exception {
		String fdKId = map.get("fdKId")+"";//账套ID
		String docNumber = map.get("docNumber")+"";//费控凭证号
		String fdVoucherType = map.get("fdVoucherType")+"";//凭证类别
		String fdAccountingYear = map.get("fdAccountingYear")+"";//会计年度
		String fdPeriod = map.get("fdPeriod")+"";//期间
		String fdNumber = map.get("fdNumber")+"";//单据数
		String fdVoucherDate = map.get("fdVoucherDate")+"";//凭证日期（yyyy-MM-dd）
		String fdVoucherMainText = map.get("fdVoucherText")+"";//凭证抬头文本
		String docCreatorName = map.get("docCreatorName")+"";//制单人名称
		String fdCashierName = map.get("fdCashierName")+"";//出纳人名称
		String requestXml = FsscK3Constant.K3_VOUCHER_TITLE;
		
		if(StringUtil.isNull(company.getFdKUserName()) || StringUtil.isNull(company.getFdKPassWord())) {
			return null;
		}
		
		requestXml = requestXml.replace("iAisID_iAisID", fdKId).replace("strUser_strUser", company.getFdKUserName()).replace("strPassword_strPassword", company.getFdKPassWord());
		requestXml +="<FGroup>" +"<![CDATA[" + fdVoucherType + "]]>"+ "</FGroup>";	//凭证类别
		requestXml +="<FTransDate>" +fdVoucherDate+ "</FTransDate>";	//凭证日期
		requestXml +="<FDate>" +fdVoucherDate+ "</FDate>";//凭证录入日期
		requestXml +="<FExplanation>"+"<![CDATA[" + fdVoucherMainText + "]]>"+"</FExplanation>";//凭证抬头文本
		requestXml +="<FAttachments>" +fdNumber+ "</FAttachments>";	//单据数
		requestXml +="<FCashier>NONE</FCashier>";
		requestXml +="<FHandler/>";
		requestXml +="<FNumber>0</FNumber>";	//凭证号
		if (fdPeriod.length() == 1) {
			fdPeriod = "0" + fdPeriod;
		}
		requestXml +="<FPeriod>" +fdPeriod+ "</FPeriod>";//期间
		requestXml +="<FPoster>NONE</FPoster>";	//是否过账
		requestXml+="<FPreparer>"+"<![CDATA[" + docCreatorName + "]]>"+"</FPreparer>";	//制单人名称
		requestXml+="<FCashier>"+"<![CDATA[" + fdCashierName + "]]>"+"</FCashier>";	//出纳人名称
		requestXml+="<FReference>"  +docNumber+  "</FReference>";
		requestXml+="<FYear>" +fdAccountingYear+ "</FYear>";	//会计年度
		
		// 凭证明细
		List<Map<String, Object>> detailMapList = (List<Map<String, Object>>) map.get("detailMapList");
		requestXml+="<FSerialNum>"  +detailMapList.size()+  "</FSerialNum>";
		for (int i = 0; i < detailMapList.size(); i++) {
			Map<String, Object> detailMap = detailMapList.get(i);
			String fdBaseAccountsCode = detailMap.get("fdBaseAccountsCode")+"";//科目编码
			String fdBaseAccountsName = detailMap.get("fdBaseAccountsName")+"";//科目名称
			String fdVoucherText = detailMap.get("fdVoucherText")+"";//凭证摘要
			String fdType = detailMap.get("fdType")+"";//借贷
			String fdMoney = detailMap.get("fdMoney")+"";//金额
			String fdAccountProperty = detailMap.get("fdAccountProperty")+"";//核算属性
			EopBasedataCostCenter eopBasedataCostCenter = (EopBasedataCostCenter) detailMap.get("eopBasedataCostCenter");//核算项目之部门
			EopBasedataErpPerson eopBasedataErpPerson = (EopBasedataErpPerson) detailMap.get("eopBasedataErpPerson");//erp个人
			EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) detailMap.get("eopBasedataSupplier");//供应商
			EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) detailMap.get("eopBasedataCustomer");//客户
			EopBasedataCashFlow eopBasedataCashFlow = (EopBasedataCashFlow) detailMap.get("eopBasedataCashFlow");//现金流量项目
			EopBasedataProject eopBasedataProject = (EopBasedataProject) detailMap.get("eopBasedataProject");//核算项目
			EopBasedataPayBank eopBasedataPayBank = (EopBasedataPayBank) detailMap.get("eopBasedataPayBank");//银行
			SysOrgElement fdDept = (SysOrgElement) detailMap.get("fdDept");//部门
			String fdContractCode = detailMap.get("fdContractCode")+"";//合同编号
			String fdContractName = detailMap.get("fdContractName")+"";//合同名称
			
			requestXml+="<Entries>";
			requestXml +="<FEntryID>"+i+"</FEntryID>";	//分录号
			if (FsscK3Constant.FD_TYPE_1.equals(fdType)) {
				requestXml+="<FDC>1</FDC>";	//余额方向
			} else if (FsscK3Constant.FD_TYPE_2.equals(fdType)) {
				requestXml+="<FDC>0</FDC>";	//余额方向
			}
			requestXml+="<FMeasureUnitUUID/>";
			requestXml+="<FTransNo/>";	//项目任务内码
			requestXml+="<FMeasureUnit/>";	//单位内码
			requestXml+="<FAccountNumber>" +fdBaseAccountsCode+ "</FAccountNumber>";	//科目代码
			requestXml+="<FAccountName>" +fdBaseAccountsName+ "</FAccountName>";//科目名称
			
			if (fdVoucherText.length() > 60) {
				fdVoucherText = fdVoucherText.substring(0, 59);
			}
			requestXml+="<FExplanation><![CDATA[" + fdVoucherText + "]]></FExplanation>";	//摘要
			// 结算方式,在科目是银行或往来时，可以填写此项
			requestXml +="<FSettleNo/>";//结算号
			requestXml +="<FSettleTypeName/>";//结算方式
			requestXml+="<FCurrencyName><![CDATA[人民币]]></FCurrencyName>";//币别
			requestXml +="<FCurrencyNumber>RMB</FCurrencyNumber>";	//币种编号
			// 单价,在科目有数量核算时，填写此项
			requestXml +="<FUnitPrice>0</FUnitPrice>";	//单价
			// 汇率1 主辅币核算时使用 折辅汇率 原币*汇率1=辅币 NC用户使用
			requestXml +="<FExchangeRate>1</FExchangeRate>";	//汇率
			// 借方数量, 在科目有数量核算时，填写此项
			requestXml +="<FQuantity>0</FQuantity>";	//数量
			// 区分借贷方金额
			Double money = FsscNumberUtil.doubleToUp(fdMoney);
			// 借方
			requestXml +="<FAmount>"+money.toString()+"</FAmount>";	//本位币金额
			requestXml +="<FAmountFor>"+money.toString()+"</FAmountFor>";	//原币金额
			Map<String, String> kMap = FsscK3Util.getSwitchValue(null);	//获取k3配置信息
			//部门*科目有部门核算时不能为空
			if(null!= eopBasedataCostCenter){
				requestXml+="<DetailEntries>";
				requestXml+="<FDetailName><![CDATA[" +eopBasedataCostCenter.getFdName()+ "]]></FDetailName>";
				requestXml+="<FDetailNumber>" +eopBasedataCostCenter.getFdCode()+ "</FDetailNumber>";
				requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.0.fdName")+ "]]></FTypeName>";
				requestXml+="<FTypeNumber>" +kMap.get("fdDetail.0.fdCode")+ "</FTypeNumber>";
				requestXml+="</DetailEntries>";
			}
			// 项目 *科目有项目核算时不能为空
            if(null!= eopBasedataProject){
				requestXml+="<DetailEntries>";
				requestXml+="<FDetailName><![CDATA[" +eopBasedataProject.getFdName()+ "]]></FDetailName>";
	            requestXml+="<FDetailNumber>" +eopBasedataProject.getFdCode()+ "</FDetailNumber>";
	            requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.1.fdName")+ "]]></FTypeName>";
	            requestXml+="<FTypeNumber>" +kMap.get("fdDetail.1.fdCode")+ "</FTypeNumber>";
	            requestXml+="</DetailEntries>";
            }
            // 客户 *科目有客户核算时不能为空
 			if(null!= eopBasedataCustomer){
 				requestXml+="<DetailEntries>";
 				requestXml+="<FDetailName><![CDATA["+eopBasedataCustomer.getFdName()+"]]></FDetailName>";
 				requestXml+="<FDetailNumber>" +eopBasedataCustomer.getFdCode()+ "</FDetailNumber>";
 				requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.2.fdName")+ "]]></FTypeName>";
 				requestXml+="<FTypeNumber>" +kMap.get("fdDetail.2.fdCode")+ "</FTypeNumber>";
 				requestXml+="</DetailEntries>";
 			}
 			// 现金流量项目 *科目有现金流量项目核算时不能为空
			if(null!= eopBasedataCashFlow){
				requestXml+="<DetailEntries>";
				requestXml+="<FDetailName><![CDATA[" +eopBasedataCashFlow.getFdName()+ "]]></FDetailName>";
	            requestXml+="<FDetailNumber>" +eopBasedataCashFlow.getFdCode()+ "</FDetailNumber>";
	            requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.3.fdName")+ "]]></FTypeName>";
	            requestXml+="<FTypeNumber>" +kMap.get("fdDetail.3.fdCode")+ "</FTypeNumber>";
	            requestXml+="</DetailEntries>";
			}
			// 人员 *科目有人员核算时不能为空
			if(null!= eopBasedataErpPerson){
				requestXml+="<DetailEntries>";
				requestXml+="<FDetailName><![CDATA[" +eopBasedataErpPerson.getFdName()+ "]]></FDetailName>";
	            requestXml+="<FDetailNumber>" +eopBasedataErpPerson.getFdCode()+ "</FDetailNumber>";
	            requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.4.fdName")+ "]]></FTypeName>";
	            requestXml+="<FTypeNumber>" +kMap.get("fdDetail.4.fdCode")+ "</FTypeNumber>";
	            requestXml+="</DetailEntries>";
			}
			// 银行 *科目有银行核算时不能为空
			if(null!= eopBasedataPayBank){
				requestXml+="<DetailEntries>";
				requestXml+="<FDetailName><![CDATA[" +eopBasedataPayBank.getFdAccountName()+ "]]></FDetailName>";
				requestXml+="<FDetailNumber>" +eopBasedataPayBank.getFdCode()+ "</FDetailNumber>";
				requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.7.fdName")+ "]]></FTypeName>";
				requestXml+="<FTypeNumber>" +kMap.get("fdDetail.7.fdCode")+ "</FTypeNumber>";
				requestXml+="</DetailEntries>";
			}
			// 供应商 *科目有供应商核算时不能为空
			if(null!= eopBasedataSupplier){
				requestXml+="<DetailEntries>";
				requestXml+="<FDetailName><![CDATA[" +eopBasedataSupplier.getFdName()+ "]]></FDetailName>";
				requestXml+="<FDetailNumber>" +eopBasedataSupplier.getFdCode()+ "</FDetailNumber>";
				requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.8.fdName")+ "]]></FTypeName>";
				requestXml+="<FTypeNumber>" +kMap.get("fdDetail.8.fdCode")+ "</FTypeNumber>";
				requestXml+="</DetailEntries>";
			}
            // 合同 *科目有合同核算时不能为空
            if(StringUtil.isNotNull(fdContractName) && StringUtil.isNotNull(fdContractCode)){
				requestXml+="<DetailEntries>";
				requestXml+="<FDetailName><![CDATA[" +fdContractName+ "]]></FDetailName>";
	            requestXml+="<FDetailNumber>" +fdContractCode+ "</FDetailNumber>";
	            requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.9.fdName")+ "]]></FTypeName>";
	            requestXml+="<FTypeNumber>" +kMap.get("fdDetail.9.fdCode")+ "</FTypeNumber>";
	            requestXml+="</DetailEntries>";
            }
            // 部门 *科目有部门核算时不能为空
 			if(null!= fdDept){
 				requestXml+="<DetailEntries>";
 				requestXml+="<FDetailName><![CDATA[" +fdDept.getFdName()+ "]]></FDetailName>";
 				requestXml+="<FDetailNumber>" +fdDept.getFdNo()+ "</FDetailNumber>";
 				requestXml+="<FTypeName><![CDATA[" +kMap.get("fdDetail.10.fdName")+ "]]></FTypeName>";
 				requestXml+="<FTypeNumber>" +kMap.get("fdDetail.10.fdCode")+ "</FTypeNumber>";
 				requestXml+="</DetailEntries>";
 			}
			// 明细
			requestXml+="</Entries>";
		}
		// 凭证尾部
		requestXml +=FsscK3Constant.K3_VOUCHER_END;
		return requestXml;
	}
	
	/**
	 * k3同步接口获取
	 * @return
	 * @throws Exception
	 */
	public HttpURLConnection initDataSet(EopBasedataCompany company) throws Exception {
		if(StringUtil.isNull(company.getFdKUrl())) {
			return null;
		}
		URL url = new URL(company.getFdKUrl());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod(FsscK3Constant.POST);
		con.setRequestProperty(FsscK3Constant.PROPERTY1, FsscK3Constant.PROPERTY2);
		return con;
	}
	
	/**
	 *
	 * xml字符串转换成list<map>
	 *
	 * @param xml
	 * @return Map
	 */
	public static List readStringXmlOut(String xml) {
		List list = new ArrayList();
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
			Element rootElt = doc.getRootElement(); // 获取根节点
			Iterator iter = rootElt.elementIterator("voucher"); // 获取根节点下的子节点code
			// 遍历code节点
			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				Iterator iter1 = recordEle.elementIterator("voucher_head");
				while (iter1.hasNext()) {
					Map map = new ConcurrentHashMap();
					Element record = (Element) iter1.next();
					String memo1 = record.elementTextTrim("memo1"); // 凭证号
					map.put("memo1", StringUtil.isNotNull(memo1)?memo1:"");
					list.add(map);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
