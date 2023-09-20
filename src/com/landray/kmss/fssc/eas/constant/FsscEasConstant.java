package com.landray.kmss.fssc.eas.constant;

public interface FsscEasConstant {

	public static String XDFFKJOURNAL_LOAN = "贷";// 贷方
	public static String XDFFKJOURNAL_BORROW = "借";// 借方
	// 凭证状态
	public static String XDFFKJOURNAL_WAIT = "待发送";
	public static String XDFFKJOURNAL_NOCREATE = "未生成";
	public static String XDFFKJOURNAL_SENDSUCCESS = "成功";
	public static String XDFFKJOURNAL_SENDFAILD = "失败";

	public static String EASCostCenter = "成本中心";// 部门
	public static String EASClient = "客户";// 客户
	public static String EASSupplier = "供应商";// 供应商
	public static String EASCashFlow = "现金流量项目";// 现金流量项目
	public static String EASCashFlow_PrimaryItem = "收到的税费返还";// 收到的税费返还
	public static String EASProject = "项目";// 项目
	public static String EASBlank = "银行账户";// 银行账户
	public static String EASContract = "合同";// 合同
	public static String EASErpperson = "职员";// 职员
	public static String EASProduct = "商品";// 商品
	public static String EASFeiyong = "费用类型";// 费用类型
	public static String EASYinhang = "银行账户";// 银行账户
	public static String EASZijin = "资金类别";// 资金类别
	public static String EASPdc = "PDC";// pdc
	public static String FDERPPERSON = "职员";// 职员
	public static String FDCASHFLOWPRJ = "现金流量项目";// 现金流量
	public static String FDACCOUNT = "会计科目";// 会计科目
	public static String FDPROJECT = "核算项目";// 核算项目

	/**
	 * 登录请求str
	 * @param %fdUserName% 用户名
	 * @param %fdPassword% 密码
	 * @param %fdSlnName% 解决方案
	 * @param %fdDcName% 数据中心
	 * @param %fdLanguage% 语音
	 * @param %fdDbType% 数据库类型
	 */
	public static final String loginRequestStr = "<soapenv:Envelope xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:log='http://login.webservice.bos.kingdee.com'>"
			+ "   <soapenv:Header/>                                                                                                                                                                                                               "
			+ "   <soapenv:Body>                                                                                                                                                                                                                  "
			+ "      <log:login soapenv:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/'>                                                                                                                                                "
			+ "         <userName xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>%fdUserName%</userName>                                                                                        "
			+ "         <password xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>%fdPassword%</password>                                                                                        "
			+ "         <slnName xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>%fdSlnName%</slnName>                                                                                           "
			+ "         <dcName xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>%fdDcName%</dcName>                                                                                              "
			+ "         <language xsi:type='xsd:string' xs:type='type:string' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>%fdLanguage%</language>                                                                                        "
			+ "         <dbType xsi:type='xsd:int' xs:type='type:int' xmlns:xs='http://www.w3.org/2000/XMLSchema-instance'>%fdDbType%</dbType>                                                                                                    "
			+ "      </log:login>                                                                                                                                                                                                                 "
			+ "   </soapenv:Body>                                                                                                                                                                                                                 "
			+ "</soapenv:Envelope>                                                                                                                                                                                                                ";
}
