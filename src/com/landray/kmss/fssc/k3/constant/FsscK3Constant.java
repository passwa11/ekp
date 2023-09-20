package com.landray.kmss.fssc.k3.constant;

public interface FsscK3Constant {
	
	/**
	 * 凭证类型 1 借
	 */
	public static final String FD_TYPE_1 = "1";
	public static final String FD_PK_LOAN = "40"; // 默认借方PK码
	
	/**
	 * 凭证类型 2 贷
	 */
	public static final String FD_TYPE_2 = "2";
	public static final String FD_PK_LEND = "50"; // 默认贷方PK码
	
	/**
	 * Requestmethod
	 */
	public static final String POST = "POST";
	/**
	 * Content
	 */
	public static final String PROPERTY1 = "Content-type";
	/**
	 * RequestProperty
	 */
	public static final String PROPERTY2 = "text/xml;charset=UTF-8";

	/**
	 * 凭证查询接口XML
	 * @param %fdKId% 账套ID
	 * @param %docNumber% 费控凭证号
	 */
	public static final String K3_VOUCHER_QUERY = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:vouc=\"http://www.kingdee.com/VoucherData\">"
			+ "<soapenv:Header/>"
			+ " <soapenv:Body>"
			+ "<vouc:Query>"
			+ "<vouc:iAisID>iAisID_iAisID</vouc:iAisID>"
			+	"<vouc:strUser>strUser_strUser</vouc:strUser>"
			+ " <vouc:strPassword>strPassword_strPassword</vouc:strPassword><vouc:iPerCount>iPerCount_iPerCount</vouc:iPerCount>"
			+"<vouc:strFilter>FSerialNum='FSerialNum_FSerialNum'</vouc:strFilter>"
			+"</vouc:Query></soapenv:Body></soapenv:Envelope>";
	
	/**
	 * K3凭证接口 头部
	 */
	public static final String K3_VOUCHER_TITLE =" <?xml version=\"1.0\" encoding=\"utf-16\"?>"
		+ "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
		+ "<soap:Body>"
		+ "	<Update xmlns=\"http://www.kingdee.com/VoucherData\">"
		+ "		<iAisID>iAisID_iAisID</iAisID>"
		+ "		<strUser>strUser_strUser</strUser>"
		+ "		<strPassword>strPassword_strPassword</strPassword>"
		+ "		<Data> " 
		+ "      <Voucher>";

	/**
	 * K3凭证接口 尾部
	 */
	public static final String K3_VOUCHER_END="</Voucher>"
		+ "</Data>"
		+ "<iBillClassTypeID>1013306</iBillClassTypeID>"
		+ "</Update> "
		+ "</soap:Body>"
		+ "</soap:Envelope>";
}
