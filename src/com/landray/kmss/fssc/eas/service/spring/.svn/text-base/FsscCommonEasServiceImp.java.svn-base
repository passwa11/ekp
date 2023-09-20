package com.landray.kmss.fssc.eas.service.spring;

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
import com.landray.kmss.fssc.common.interfaces.IFsscCommonEasService;
import com.landray.kmss.fssc.eas.constant.FsscEasConstant;
import com.landray.kmss.fssc.eas.util.FsscEasUtil;
import com.landray.kmss.fssc.eas.webService.WSGLWebServiceFacadeSrvProxyServiceLocator;
import com.landray.kmss.fssc.eas.webService.WSWSVoucher;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.axis.client.Stub;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FsscCommonEasServiceImp extends Stub implements IFsscCommonEasService {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FsscCommonEasServiceImp.class);

	private IEopBasedataCompanyService eopBasedataCompanyService;
	
	public IEopBasedataCompanyService getEopBasedataCompanyService() {
		if (eopBasedataCompanyService == null) {
			eopBasedataCompanyService = (IEopBasedataCompanyService) SpringBeanUtil.getBean("eopBasedataCompanyService");
        }
		return eopBasedataCompanyService;
	}
	
	/**
	 * 登录
	 * @return Map<String, String>
	 *     		result:success 成功，failure，失败
	 *     		fdSessionId: sessionId
	 *     		message：失败信息
	 * @throws Exception
	 */
	@Override
    public Map<String, String> login(String fdCompanyId) throws Exception {
		Map<String, String> rtnMap = new HashMap<String, String>();
    	try {
    		//设置传参必填字段
    		EopBasedataCompany company = (EopBasedataCompany) getEopBasedataCompanyService().findByPrimaryKey(fdCompanyId);
			if(null != company){
				if(StringUtil.isNull(company.getFdEUserName()) || StringUtil.isNull(company.getFdEPassWord()) || StringUtil.isNull(company.getFdESlnName())
						|| StringUtil.isNull(company.getFdEDcName()) || StringUtil.isNull(company.getFdELanguage()) 
						|| StringUtil.isNull(company.getFdEDbType()) || StringUtil.isNull(company.getFdELoginWsdlUrl())){
					KmssMessage msg = new KmssMessage("fssc-eas:message.setParameterError");
					throw new KmssRuntimeException(msg);
				}
			}
			String fdUserName = company.getFdEUserName();
			String fdPassword = company.getFdEPassWord();
			String fdSlnName = company.getFdESlnName();
			String fdDcName = company.getFdEDcName();
			String fdLanguage = company.getFdELanguage();
			String fdDbType = company.getFdEDbType();
			String fdLoginWsdlUrl = company.getFdELoginWsdlUrl();
			//拼接请求字符串
			String loginRequestStr = FsscEasConstant.loginRequestStr
					.replace("%fdUserName%", fdUserName)
					.replace("%fdPassword%", fdPassword)
					.replace("%fdSlnName%", fdSlnName)
					.replace("%fdDcName%", fdDcName)
					.replace("%fdLanguage%", fdLanguage)
					.replace("%fdDbType%", fdDbType);
			logger.error("------eas报文打印-----："+loginRequestStr);
			String rtnStr = doPostSoap(fdLoginWsdlUrl, loginRequestStr,"");
			String fdSessionId = null;
			if(rtnStr.indexOf("</sessionId>") > -1){
				String[] temp1 = rtnStr.split("</sessionId>");
				String[] temp2 = temp1[0].split(">");
				fdSessionId = temp2[temp2.length-1];
			}
			if(StringUtil.isNotNull(fdSessionId)){//登录成功
				rtnMap.put("result", "success");
				rtnMap.put("fdSessionId", fdSessionId);
			}else{
				rtnMap.put("result", "failure");
				rtnMap.put("message", ResourceUtil.getString("message.login.error", "fssc-eas"));
				return rtnMap;
			}
		} catch (Exception e) {
			e.printStackTrace();
			rtnMap.put("result", "failure");
			rtnMap.put("message", e.getMessage());
		}
    	
    	return rtnMap;
	}

	public static String doPostSoap(String postUrl, String soapXml, String soapAction) throws Exception{
		String retStr = "";
		// 创建HttpClientBuilder
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		// HttpClient
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httpPost = new HttpPost(postUrl);
		/*
		 * // 设置请求和传输超时时间 RequestConfig requestConfig = RequestConfig.custom()
		 * .setSocketTimeout(socketTimeout)
		 * .setConnectTimeout(connectTimeout).build();
		 * httpPost.setConfig(requestConfig);
		 */
			try {
				httpPost.setHeader("Content-Type",
						"text/xml;charset=UTF-8;action=\"" + soapAction + "\"");
			StringEntity data = new StringEntity(soapXml,
					Charset.forName("UTF-8"));
			httpPost.setEntity(data);
			CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				// 打印响应内容
				retStr = EntityUtils.toString(httpEntity, "UTF-8");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		// 释放资源
		try {
			closeableHttpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retStr;
	}

	/**
	 * 凭证写入eas
	 * @param map{
	 *             fdCompanyCode: 记账公司编码
	 *             fdVoucherDate: 凭证日期 yyyy-MM-dd
	 *             fdBookkeepingDate: 记账日期 yyyy-MM-dd
	 *             docNumber: 费控凭证号
	 *             fdBaseCurrencySymbol: 币种符号
	 *             fdBaseVoucherTypeCode: 凭证字
	 *             fdNumber: Integer单据数
	 *             fdEImportVoucherWsdlUrl: 传输凭证wsdl路径
	 *             detailMapList: {
	 *             		fdVoucherText: 摘要
	 *             		fdBaseAccountsCode: 会计科目编号
	 *             		fdType: 类型
	 *             		fdMoney: Double凭证金额
	 *             		fsscBaseCostCenter: FsscBaseCostCenter 核算项目之部门
	 *             		fsscBaseErpPerson: FsscBaseErpPerson erp个人
	 *             		fsscBaseSupplier: FsscBaseSupplier 供应商
	 *             		fsscBaseCustomer: FsscBaseCustomer 客户
	 *             		fsscBaseCashFlow: FsscBaseCashFlow 现金流量项目
	 *             		fsscBaseProject: FsscBaseProject 核算项目
	 *           		fsscBasePayBank: FsscBasePayBank 付款银行
	 *           		fdDept: 部门
	 *           		fdContractCode: 合同编号
	 *           		fdContractName: 合同名称
	 *             }
	 * }
	 *
	 * @return Map<String, String>
	 *     		result:success 成功，failure，失败
	 *     		fdEasNo:fdEasNo
	 *     		message：失败信息
	 * @throws Exception
	 */
	@Override
    public Map<String, String> importVoucher(Map<String, Object> map) throws Exception{
		WSGLWebServiceFacadeSrvProxyServiceLocator locator = new WSGLWebServiceFacadeSrvProxyServiceLocator();
		java.net.URL endpoint = new java.net.URL(map.get("fdEImportVoucherWsdlUrl")+"");
		WSWSVoucher[] voucherCols = findJournalByFdId(map);
		// int isSubmit, int isVerify, int isCashflow
		String[] returnVal = locator.getWSGLWebServiceFacade(endpoint).importVoucher(voucherCols, 0, 0, 0);
		// 0000||转账凭证||2017||9||成功保存||0366
		// 1002||null||0||0||凭证P201709004凭证分录1没有找到科目:660201
		String returnString = returnVal[0];
		returnVal = returnString.split("\\|\\|");
		Map<String, String> rtnMap = new HashMap<String, String>();
		if ("0000".equals(returnVal[0])) {
			rtnMap.put("result", "success");
			rtnMap.put("message", returnVal[4]);
			rtnMap.put("fdEasNo", returnVal[5]);
		} else {
			rtnMap.put("result", "failure");
			rtnMap.put("message", ResourceUtil.getString("message.import.error", "fssc-eas").replace("%errorCode%", String.valueOf(returnVal[0])).replace("%text%", returnVal[4]));
			rtnMap.put("fdEasNo", "");
		}
		return rtnMap;
	}

	/**
	 * @description 拼装需要传送给EAS的凭证主体
	 *
	 * @param map{
	 *             fdCompanyCode: 记账公司编码
	 *             fdVoucherDate: 凭证日期 yyyy-MM-dd
	 *             fdBookkeepingDate: 记账日期 yyyy-MM-dd
	 *             docNumber: 费控凭证号
	 *             fdBaseCurrencySymbol: 币种符号
	 *             fdBaseVoucherTypeCode: 凭证字
	 *             fdNumber: Integer单据数
	 *             detailMapList: {
	 *             		fdVoucherText: 摘要
	 *             		fdBaseAccountsCode: 会计科目编号
	 *             		fdType: 类型
	 *             		fdMoney: Double凭证金额
	 *             		fsscBaseCostCenter: FsscBaseCostCenter 核算项目之部门
	 *             		fsscBaseErpPerson: FsscBaseErpPerson erp个人
	 *             		fsscBaseSupplier: FsscBaseSupplier 供应商
	 *             		fsscBaseCustomer: FsscBaseCustomer 客户
	 *             		fsscBaseCashFlow: FsscBaseCashFlow 现金流量项目
	 *             		fsscBaseProject: FsscBaseProject 核算项目
	 *           		fsscBasePayBank: FsscBasePayBank 付款银行
	 *           		fdDept: 部门
	 *           		fdContractCode: 合同编号
	 *           		fdContractName: 合同名称
	 *             }
	 * }
	 *
	 * @return @throws Exception @exception
	 */
	public WSWSVoucher[] findJournalByFdId(Map<String, Object> map)
			throws Exception {
		String fdCompanyCode = map.get("fdCompanyCode")+"";// 记账公司编码
		String fdVoucherDate = map.get("fdVoucherDate")+"";// 凭证日期 yyyy-MM-dd
		//String fdBookkeepingDate = map.get("fdBookkeepingDate")+"";// 记账日期 yyyy-MM-dd
		String docNumber = map.get("docNumber")+"";// 费控凭证号
		String fdBaseCurrencySymbol = map.get("fdBaseCurrencySymbol")+"";// 币种符号
		String fdBaseVoucherTypeCode = map.get("fdBaseVoucherTypeCode")+"";// 凭证字
		int fdNumber = (Integer) map.get("fdNumber");// 单据数

		WSWSVoucher[] wswsVoucherList = null;
		int len = 0;// 用来作为数组的下标??
		Calendar c = Calendar.getInstance();
		int periodYear;
		int periodNumber;
		if (StringUtil.isNull(fdVoucherDate)) {
			fdVoucherDate = DateUtil.convertDateToString(
					new Date(), "yyyy-MM-dd");// 凭证日期
			periodYear = c.get(Calendar.YEAR);
			periodNumber = c.get(Calendar.MONTH) + 1;
		} else {
			periodYear = Integer.parseInt(fdVoucherDate.substring(0, 4));
			periodNumber = Integer.parseInt(fdVoucherDate.substring(5, 7));
		}
		// FsBaseFlows fdCashFlowPrj1 = new
		String fdCashFlowPrjName = null;
		int oppAccountSeq = 0;
		double cashflowAmountOriginal = 0;
		double cashflowAmountLocal = 0;
		double cashflowAmountRpt = 0;
		List<WSWSVoucher> list = new ArrayList<WSWSVoucher>();
		List<WSWSVoucher> list1 = new ArrayList<WSWSVoucher>();
		int index = 1;
		List<Map<String, Object>> detailMapList = (List<Map<String, Object>>) map.get("detailMapList");// map
		for (int j = 0; j < detailMapList.size(); j++) {
			Map<String, Object> detailMap = detailMapList.get(j);
			String fdVoucherText = detailMap.get("fdVoucherText")+"";// 摘要
			String fdBaseAccountsCode = detailMap.get("fdBaseAccountsCode")+"";// 会计科目编号
			String fdType = detailMap.get("fdType")+"";// 类型
			double fdMoney = (Double) detailMap.get("fdMoney");// 凭证金额
			EopBasedataCostCenter eopBasedataCostCenter = (EopBasedataCostCenter) detailMap.get("eopBasedataCostCenter");//成本中心
			EopBasedataProject eopBasedataProject = (EopBasedataProject) detailMap.get("eopBasedataProject");//核算项目
			EopBasedataCustomer eopBasedataCustomer = (EopBasedataCustomer) detailMap.get("eopBasedataCustomer");//客户
			EopBasedataCashFlow eopBasedataCashFlow = (EopBasedataCashFlow) detailMap.get("eopBasedataCashFlow");//现金流量项目
			EopBasedataErpPerson eopBasedataErpPerson = (EopBasedataErpPerson) detailMap.get("eopBasedataErpPerson");//个人
			EopBasedataPayBank eopBasedataPayBank = (EopBasedataPayBank) detailMap.get("eopBasedataPayBank");//银行
			EopBasedataSupplier eopBasedataSupplier = (EopBasedataSupplier) detailMap.get("eopBasedataSupplier");//供应商
			SysOrgElement fdDept = (SysOrgElement) detailMap.get("fdDept");//部门
			String fdContractCode = detailMap.get("fdContractCode")+"";//合同编号
			String fdContractName = detailMap.get("fdContractName")+"";//合同名称
			int entrySeq = j + 1;
			WSWSVoucher wswsVoucher = new WSWSVoucher();
			// 币种编码 默认是人民币
			String currencyNumber = "";
			if (fdBaseCurrencySymbol.indexOf("CNY") >= 0) {
				currencyNumber = "BB01";
			} else if (fdBaseCurrencySymbol.indexOf("HK") >= 0) {
				currencyNumber = "BB03";
			} else if (fdBaseCurrencySymbol.indexOf("US") >= 0) {
				currencyNumber = "BB02";
			} else if (fdBaseCurrencySymbol.indexOf("CHF") >= 0) {
				currencyNumber = "BB04";
			} else if (fdBaseCurrencySymbol.indexOf("ESP") >= 0) {
				currencyNumber = "BB05";
			} else if (fdBaseCurrencySymbol.indexOf("GBP") >= 0) {
				currencyNumber = "BB06";
			} else if (fdBaseCurrencySymbol.indexOf("EUR") >= 0) {
				currencyNumber = "BB07";
			} else if (fdBaseCurrencySymbol.indexOf("LAK") >= 0) {
				currencyNumber = "BB08";
			} else {
				currencyNumber = fdBaseCurrencySymbol;
			}
			wswsVoucher.setCurrencyNumber(currencyNumber);// 币种编码 "BB01"
			wswsVoucher.setCompanyNumber(fdCompanyCode);// 记账公司(供应??编码 010000
			wswsVoucher.setBookedDate(fdVoucherDate);// 记账日期
			// "2012-05-01"
			// "2013-09-21"
			wswsVoucher.setBizDate(fdVoucherDate);// 业务日期bizDate "2012-05-01"
			// 2013-09-21
			wswsVoucher.setPeriodYear(periodYear);// 会计期间-年periodYear 2012 2013
			wswsVoucher.setPeriodNumber(periodNumber);// 会计期间-编码periodNumber 5 9
			wswsVoucher.setVoucherNumber(docNumber);// 凭证号
			wswsVoucher.setVoucherType(fdBaseVoucherTypeCode);// 凭证??"转账凭证"
			wswsVoucher.setDescription(docNumber);// 参考信??
			wswsVoucher.setEntrySeq(entrySeq);// 分录号
			wswsVoucher.setAttaches(fdNumber);// 单据数
			// fdEasCreator制单??
			SysOrgElement user = UserUtil.getKMSSUser().getPerson();
			wswsVoucher.setCreator(user.getFdName());// 制单人
			// "万绍??
			wswsVoucher.setVoucherAbstract(fdVoucherText);// 分录摘要
			wswsVoucher.setAccountNumber(fdBaseAccountsCode == null ? "1001.01" : fdBaseAccountsCode);// 会计科目编码
			// "1001.01"
			int entryDC = Integer.parseInt(fdType);// 借：1;贷：2
			wswsVoucher.setEntryDC(entryDC);// 方向
			if (entryDC == 1) {
				wswsVoucher.setDebitAmount(fdMoney);// 分录行借方金额
			} else if (entryDC == 2) {
				wswsVoucher.setCreditAmount(fdMoney);// 分录行贷方金??
			}
			wswsVoucher.setOriginalAmount(fdMoney);// 分录行原币金??

			/************************************************
			 * 辅助项目，EAS中接受辅助项是AsstActType1.....AsstActType8,
			 * 顺序读取，如果中间断了一个，后面的辅助项目就不会读取。因此在拼装的时
			 * 将所有的辅助项目放入listJour集合中，在下面的listJour.size来判断
			 * 会计分录有几个辅助项目，一个辅助项目，二个辅助项目.
			 ************************************************/
			LinkedList<String> listJour = new LinkedList<String>();
			Map<String, String> eMap = FsscEasUtil.getParamValue(null);	//获取eas会计科目映射
			// 核算项目之部门
			if (eopBasedataCostCenter != null) {
				listJour.add(eMap.get("fdDetail.0.fdName"));// 成本中心
				listJour.add(eopBasedataCostCenter.getFdCode().trim());
				listJour.add(eopBasedataCostCenter.getFdName().trim());
			}
			// erp个人
			if (eopBasedataErpPerson != null) {
				listJour.add(eMap.get("fdDetail.4.fdName"));// 个人
				listJour.add(eopBasedataErpPerson.getFdCode());
				listJour.add(eopBasedataErpPerson.getFdName());
			}

			// 供应商
			if (eopBasedataSupplier != null) {// 供应商
				listJour.add(eMap.get("fdDetail.8.fdName"));// 供应商
				listJour.add(eopBasedataSupplier.getFdCode());
				listJour.add(eopBasedataSupplier.getFdName());
			}

			// 客户
			if (eopBasedataCustomer != null) {// 客户
				listJour.add(eMap.get("fdDetail.2.fdName"));
				listJour.add(eopBasedataCustomer.getFdCode());
				listJour.add(eopBasedataCustomer.getFdName());
			}

			// 现金流量项目
			if (eopBasedataCashFlow != null) {
				fdCashFlowPrjName = eopBasedataCashFlow.getFdName();
				oppAccountSeq = entrySeq;
				cashflowAmountOriginal = fdMoney;
				cashflowAmountLocal = fdMoney;
				cashflowAmountRpt = fdMoney;

				if (StringUtil.isNotNull(fdCashFlowPrjName)) {
					WSWSVoucher wswsVoucherC = new WSWSVoucher();
					wswsVoucherC.setEntrySeq(index);
					index++;
					wswsVoucherC.setItemFlag(1);// 现金流量标记
					wswsVoucherC.setPrimaryItem(FsscEasConstant.EASCashFlow_PrimaryItem);//收到的税费返还
					wswsVoucherC.setOppAccountSeq(oppAccountSeq);
					wswsVoucherC
							.setCashflowAmountOriginal(-cashflowAmountOriginal);
					wswsVoucherC.setCashflowAmountLocal(-cashflowAmountLocal);
					wswsVoucherC.setCashflowAmountRpt(-cashflowAmountRpt);
					list1.add(wswsVoucherC);
					fdCashFlowPrjName = null;
					oppAccountSeq = 0;
					cashflowAmountOriginal = 0;
					cashflowAmountLocal = 0;
					cashflowAmountRpt = 0;
				}

				listJour.add(eMap.get("fdDetail.3.fdName"));// 现金流量项目
				listJour.add(eopBasedataCashFlow.getFdCode());
				listJour.add(eopBasedataCashFlow.getFdName());
			}
			// 核算项目
			if (eopBasedataProject != null) {
				listJour.add(eMap.get("fdDetail.1.fdName"));// "项目"
				listJour.add(eopBasedataProject.getFdCode());
				listJour.add(eopBasedataProject.getFdName());
			}
			// 银行账户
			if (eopBasedataPayBank != null) {
				listJour.add(eMap.get("fdDetail.7.fdName"));// "银行账户"
				listJour.add(eopBasedataPayBank.getFdCode()==null?"":eopBasedataPayBank.getFdCode());
				listJour.add(eopBasedataPayBank.getFdAccountName());
			}
			// 合同
			if (StringUtil.isNotNull(fdContractCode) && StringUtil.isNotNull(fdContractName)) {
				listJour.add(eMap.get("fdDetail.9.fdName"));// "合同"
				listJour.add(fdContractCode);
				listJour.add(fdContractName);
			}
			// 部门
			if (fdDept != null) {
				listJour.add(eMap.get("fdDetail.10.fdName"));// "部门"
				listJour.add(fdDept.getFdNo());
				listJour.add(fdDept.getFdName());
			}
			int listJourSize = listJour.size();

			/************************************************
			 * 1个辅助项??
			 ************************************************/
			if (listJourSize == 3) {
				wswsVoucher.setAsstActType1(listJour.get(0));
				wswsVoucher.setAsstActNumber1(listJour.get(1));
				wswsVoucher.setAsstActName1(listJour.get(2));
			}
			/************************************************
			 * 2个辅助项??
			 ************************************************/
			if (listJourSize == 6) {
				wswsVoucher.setAsstActType1(listJour.get(0));
				wswsVoucher.setAsstActNumber1(listJour.get(1));
				wswsVoucher.setAsstActName1(listJour.get(2));

				wswsVoucher.setAsstActType2(listJour.get(3));
				wswsVoucher.setAsstActNumber2(listJour.get(4));
				wswsVoucher.setAsstActName2(listJour.get(5));
			}
			/************************************************
			 * 3个辅助项??
			 ************************************************/
			if (listJourSize == 9) {
				wswsVoucher.setAsstActType1(listJour.get(0));
				wswsVoucher.setAsstActNumber1(listJour.get(1));
				wswsVoucher.setAsstActName1(listJour.get(2));

				wswsVoucher.setAsstActType2(listJour.get(3));
				wswsVoucher.setAsstActNumber2(listJour.get(4));
				wswsVoucher.setAsstActName2(listJour.get(5));

				wswsVoucher.setAsstActType3(listJour.get(6));
				wswsVoucher.setAsstActNumber3(listJour.get(7));
				wswsVoucher.setAsstActName3(listJour.get(8));
			}
			/************************************************
			 * 4个辅助项??
			 ************************************************/
			if (listJourSize == 12) {
				wswsVoucher.setAsstActType1(listJour.get(0));
				wswsVoucher.setAsstActNumber1(listJour.get(1));
				wswsVoucher.setAsstActName1(listJour.get(2));

				wswsVoucher.setAsstActType2(listJour.get(3));
				wswsVoucher.setAsstActNumber2(listJour.get(4));
				wswsVoucher.setAsstActName2(listJour.get(5));

				wswsVoucher.setAsstActType3(listJour.get(6));
				wswsVoucher.setAsstActNumber3(listJour.get(7));
				wswsVoucher.setAsstActName3(listJour.get(8));

				wswsVoucher.setAsstActType4(listJour.get(9));
				wswsVoucher.setAsstActNumber4(listJour.get(10));
				wswsVoucher.setAsstActName4(listJour.get(11));
			}
			/************************************************
			 * 5个辅助项??
			 ************************************************/
			if (listJourSize == 15) {
				wswsVoucher.setAsstActType1(listJour.get(0));
				wswsVoucher.setAsstActNumber1(listJour.get(1));
				wswsVoucher.setAsstActName1(listJour.get(2));

				wswsVoucher.setAsstActType2(listJour.get(3));
				wswsVoucher.setAsstActNumber2(listJour.get(4));
				wswsVoucher.setAsstActName2(listJour.get(5));

				wswsVoucher.setAsstActType3(listJour.get(6));
				wswsVoucher.setAsstActNumber3(listJour.get(7));
				wswsVoucher.setAsstActName3(listJour.get(8));

				wswsVoucher.setAsstActType4(listJour.get(9));
				wswsVoucher.setAsstActNumber4(listJour.get(10));
				wswsVoucher.setAsstActName4(listJour.get(11));

				wswsVoucher.setAsstActType5(listJour.get(12));
				wswsVoucher.setAsstActNumber5(listJour.get(13));
				wswsVoucher.setAsstActName5(listJour.get(14));
			}
			// *****************辅助项目end************************
			wswsVoucher.setItemFlag(0);// 现金流量标记
			list.add(wswsVoucher);
			logger.error("-----打印Eas报文参数-----："+wswsVoucher.toString());
		}
		if (list.size() > 0) {
			int size = list1.size() + list.size();
			wswsVoucherList = new WSWSVoucher[size];
			for (WSWSVoucher wswsVoucher2 : list) {
				wswsVoucherList[len++] = wswsVoucher2;
			}
			for (WSWSVoucher wswsVoucher2 : list1) {
				wswsVoucherList[len++] = wswsVoucher2;
			}
		}
		return wswsVoucherList;
	}
}
