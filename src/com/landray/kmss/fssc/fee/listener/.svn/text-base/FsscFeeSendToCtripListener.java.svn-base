package com.landray.kmss.fssc.fee.listener;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.eop.basedata.model.EopBasedataExpenseItem;
import com.landray.kmss.eop.basedata.model.EopBasedataPassport;
import com.landray.kmss.eop.basedata.service.IEopBasedataExpenseItemService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPassportService;
import com.landray.kmss.eop.basedata.util.EopBasedataFsscUtil;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonCtripService;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.fee.constant.FsscFeeConstant;
import com.landray.kmss.fssc.fee.model.FsscFeeMain;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpm.engine.manager.event.EventExecutionContext;
import com.landray.kmss.sys.lbpm.engine.manager.event.IEventListener;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.sso.client.oracle.StringUtil;

import corp.openapicalls.contract.Authentification;
import corp.openapicalls.contract.setapproval.request.CurrencyType;
import corp.openapicalls.contract.setapproval.request.ExtendField;
import corp.openapicalls.contract.setapproval.request.FlightEndorsementDetail;
import corp.openapicalls.contract.setapproval.request.FlightWayType;
import corp.openapicalls.contract.setapproval.request.HotelEndorsementDetail;
import corp.openapicalls.contract.setapproval.request.HotelProductType;
import corp.openapicalls.contract.setapproval.request.PassengerDetail;
import corp.openapicalls.contract.setapproval.request.ProductType;
import corp.openapicalls.contract.setapproval.request.SeatClassType;
import corp.openapicalls.contract.setapproval.request.SetApprovalRequest;
import corp.openapicalls.contract.setapproval.request.SetApprovalServiceRequest;
import corp.openapicalls.contract.setapproval.response.SetApprovalResponse;
import corp.openapicalls.contract.ticket.TicketResponse;
import corp.openapicalls.service.setapproval.SetApprovalService;
import corp.openapicalls.service.ticket.CorpTicketService;

/**
 * 发送信息给予携程 提前审批接口
 * 
 */
public class FsscFeeSendToCtripListener implements IEventListener {

	private final Logger log = org.slf4j.LoggerFactory.getLogger(FsscFeeSendToCtripListener.class);

	private IFsscCommonCtripService fsscCommonCtripService;

	private ISysOrgCoreService sysOrgCoreService;

	private IEopBasedataPassportService eopBasedataPassportService;

	private IEopBasedataExpenseItemService eopBasedataExpenseItemService;

	@Override
    public void handleEvent(EventExecutionContext context, String parameter)
			throws Exception {
		IBaseModel baseModel = context.getMainModel();
		if (null == baseModel) {
			log.info("未找到相应的实例！");
			return;
		}
		if(!FsscCommonUtil.checkHasModule("/fssc/ctrip/")) {//不存在携程模块
			return;
		}
		if (baseModel instanceof FsscFeeMain) {
			FsscFeeMain fsscFeeMain = (FsscFeeMain) baseModel;
			log.info("开始传递数据到携程----------------->" + fsscFeeMain.getDocNumber());
			String fdTemplateId = fsscFeeMain.getDocTemplate().getFdId();
			List<SetApprovalResponse> setApprovalResponseL = new ArrayList<SetApprovalResponse>();
			//获取提前审批映射
			Map<String, Object> tempMap = getFsscCommonCtripService().getMapping(fdTemplateId);
			if("failure".equals(tempMap.get("result")+"")){
				throw new Exception(tempMap.get("message")+"");
			}
			if (null != getFsscCommonCtripService()) {
				setApprovalResponseL = setApproval((Map<String, String>) tempMap.get("mappingMap"), fsscFeeMain);
			}
			log.info("结束传递数据到携程----------------->" + setApprovalResponseL);

		}
	}

	/**
	 * 提前审批落地
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List<SetApprovalResponse> setApproval(Map<String, String> map, FsscFeeMain fsscFeeMain)
			throws Exception {
		List<SetApprovalResponse> list = new ArrayList<SetApprovalResponse>();
		FormulaParser parser = FormulaParser.getInstance(fsscFeeMain);

		String fdCompanyId = (String) parser.parseValueScript(map.get("fdCompanyId"),"String");


		Map<String, String> tempMap1 = new HashMap<String, String>();
		tempMap1.put("fdPropertyName", "fdAppKey");
		tempMap1.put("fdCompanyId", fdCompanyId);
		//获取提前审批映射
		Map<String, String> tempMap2 = getFsscCommonCtripService().getAppMessageProperty(tempMap1);
		if("failure".equals(tempMap2.get("result"))){
			throw new Exception(tempMap2.get("message"));
		}
		String fdAppKey = tempMap2.get("fdValue");

		tempMap1 = new HashMap<String, String>();
		tempMap1.put("fdPropertyName", "fdAppSecurity");
		tempMap1.put("fdCompanyId", fdCompanyId);
		//获取提前审批映射
		Map<String, String> tempMap3 = getFsscCommonCtripService().getAppMessageProperty(tempMap1);
		if("failure".equals(tempMap3.get("result"))){
			throw new Exception(tempMap3.get("message"));
		}
		String fdAppSecurity = tempMap2.get("fdValue");

		//获取对接标识
		Map<String, String> tempMap4 = getFsscCommonCtripService().getDockingMark(fdCompanyId);
		if("failure".equals(tempMap4.get("result"))){
			throw new Exception(tempMap4.get("message"));
		}
		String fdDockingMark = tempMap4.get("fdDockingMark");

		String fdApprovalNumber = (String) parser.parseValueScript(map.get("fdApprovalNumber"), "String");// 审批单号
		ArrayList<String> fdDetailNo = (ArrayList<String>) parser
				.parseValueScript(map.get("fdDetailNo"),"String[]");// 明细序号
		ArrayList<String> fdBaseExpenseItemId = (ArrayList<String>) parser
				.parseValueScript(map.get("fdBaseExpenseItemId"),"String[]");// 费用类型id
		ArrayList<String> fdSingleDayStandard = (ArrayList<String>) parser
				.parseValueScript(map.get("fdSingleDayStandard"),"String[]");// 单日标准金额
		ArrayList<String> fdAirLine = (ArrayList<String>) parser
				.parseValueScript(map.get("fdAirLine"),"String");// 承运航空公司
		ArrayList<String> fdCurrency = (ArrayList<String>) parser
				.parseValueScript(map.get("fdCurrency"),"String");// 币种
		ArrayList<String> fdDiscount = (ArrayList<String>) parser
				.parseValueScript(map.get("fdDiscount"),"String[]");// 折扣
		ArrayList<String> fdSeatClass = (ArrayList<String>) parser
				.parseValueScript(map.get("fdSeatClass"), "String[]");// 舱等
		ArrayList<Integer> fdFlightWay = (ArrayList<Integer>) parser
				.parseValueScript(map.get("fdFlightWay"), "Integer[]");// 航程类型
		ArrayList<Date> fdDepartBeginDate = (ArrayList<Date>) parser
				.parseValueScript(map.get("fdDepartBeginDate"),"Date[]");// 出发日期的范围的起始日期
		int fdDepartDateNo = (int) parser.parseValueScript(map.get("fdDepartDateNo"),"Integer");// 出发时间时间差
		ArrayList<String> fdFromCities = (ArrayList<String>) parser
				.parseValueScript(map.get("fdFromCities"),"String[]");// 出发城市
		ArrayList<String> fdPassengerList = (ArrayList<String>) parser
				.parseValueScript(map.get("fdPassengerList"), "String[]");// 出行人列表
		ArrayList<String> fdPrice = (ArrayList<String>) parser
				.parseValueScript(map.get("fdPrice"), "String[]");// 价格
		ArrayList<Integer> fdProductType = (ArrayList<Integer>) parser
				.parseValueScript(map.get("fdProductType"),"Integer[]");// 预定类型
		ArrayList<Date> fdReturnBeginDate = (ArrayList<Date>) parser
				.parseValueScript(map.get("fdReturnBeginDate"),"Date[]");// 返程日期范围起始日期
		int fdReturnDateNo = (int) parser.parseValueScript(map.get("fdReturnDateNo"),"Integer");// 返程日期时间差

		ArrayList<String> fdToCities = (ArrayList<String>) parser
				.parseValueScript(map.get("fdToCities"),"String[]");// 到达城市（中文）
		ArrayList<String> fdCostCenter1 = (ArrayList<String>) parser
				.parseValueScript(map.get("fdCostCenter1"),"String[]");// 成本中心1
		String fdCostCenter2 = (String) parser.parseValueScript(map.get("fdCostCenter2"),"String");// 成本中心2
		String fdCostCenter3 = (String) parser.parseValueScript(map.get("fdCostCenter3"),"String");// 成本中心3
		String fdDefineFlag1 = (String) parser.parseValueScript(map.get("fdDefineFlag1"),"String");// 自定义字段1
		ArrayList<String> fdDefineFlag2 = (ArrayList<String>) parser
				.parseValueScript(map.get("fdDefineFlag2"),"String[]");// 自定义字段2
		if (fdDetailNo.size() > 0) {
			for (int i = 0; i < fdDetailNo.size(); i++) {
					System.out.println("第" + (i+1) + "次开始传递数据到携程");

					if(StringUtil.isNull(fdBaseExpenseItemId.get(i))){
						log.info("审批单号" + fdApprovalNumber + "_" + fdDetailNo.get(i)+"不满足推送携程的条件");
						continue;
					}
					// 费用类型
					EopBasedataExpenseItem fsBaseExpenseItem = (EopBasedataExpenseItem) eopBasedataExpenseItemService.findByPrimaryKey(fdBaseExpenseItemId.get(i));
					if (fsBaseExpenseItem == null || StringUtil.isNull(fsBaseExpenseItem.getFdTripType())) {
						log.info("审批单号" + fdApprovalNumber + "_" + fdDetailNo.get(i)+"不满足推送携程的条件");
						continue;
					}

					// 请求契约
					TicketResponse ticketResponse = CorpTicketService.getOrderAuditTicket(fdAppKey, fdAppSecurity,"1.0");

					if (ticketResponse != null && ticketResponse.getStatus() != null
							&& ticketResponse.getStatus().getSuccess())
					{

						SetApprovalService setapprovalService = new SetApprovalService();

						SetApprovalServiceRequest setApprovalServiceRequest = new SetApprovalServiceRequest();

						SetApprovalRequest setApprovalRequest = new SetApprovalRequest();

						Authentification authInfo = new Authentification(fdAppKey,
								ticketResponse.getTicket());// 接入账号、ticket

						setApprovalRequest.setAuth(authInfo);
						//序号必须为一位或两位数
						String fdDetailNoStr = fdDetailNo.get(i);
						if(fdDetailNoStr.length() != 1 && fdDetailNoStr.length() != 2){
							return null;
						}
						if(fdDetailNoStr.length() == 1){
							fdDetailNoStr = "0"+fdDetailNoStr;
						}
						setApprovalRequest.setApprovalNumber(fdApprovalNumber+"_"+fdDetailNoStr);// OA审批单号
																		// CC201707250002
						setApprovalRequest.setStatus(1); // 审批状态 1有效 0无效


						SysOrgPerson element = null;// 出行人
						if (null != fdPassengerList && fdPassengerList.size() > 0
								&& StringUtil.isNotNull(fdPassengerList.get(i))) {
							element = (SysOrgPerson) sysOrgCoreService.findByPrimaryKey(fdPassengerList.get(i),
									SysOrgPerson.class, true);// 实际使用人
						}

						FormulaParser parserPerson = FormulaParser.getInstance(element);
						setApprovalRequest.setEmployeeID((String) parserPerson.parseValueScript(fdDockingMark));// 对接标识：例：可能是员工编号

						ArrayList<ExtendField> extendFieldList = new ArrayList<ExtendField>(); // 设置成本中心
						// **********************************扩展字段列表start*********************************//
						/**
						 * 成本中心等扩展字段列表，非必填，如无该需求则不需要传
						 *
						 * 字段 类型 描述 默认值 可为空 备注 FieldName String 扩展字段字段名 无 Y
						 *
						 * 扩展字段字段名
						 *
						 * 1.不区分大小写
						 *
						 * 2.多个相同字段名只取第一个值保存到数据库
						 *
						 * 3.目前支持的扩展字段名如下表（扩展字段名列表）
						 *
						 * 4.如果字段名不在下表范围内，返回字段名错误信息
						 *
						 * 5.如果FieldName为空，则忽略该条记录 FieldValue String 字段值 无 N 字段值
						 * FieldType String 扩展字段数据类型 无 Y 扩展字段数据类型，目前只支持String，不区分大小写
						 *
						 * 扩展字段名列表 字段 类型 描述 默认值 可为空 备注
						 *
						 * CostCenter1 String(100) 成本中心1 无 Y
						 *
						 * CostCenter2 String(100) 成本中心2 无 Y
						 *
						 * CostCenter3 String(100) 成本中心3 无 Y
						 *
						 * DefineFlag1 String(100) 自定义字段1 无 Y
						 *
						 * DefineFlag2 String(100) 自定义字段2 无 Y
						 */
						if(null!=fdCostCenter1&&fdCostCenter1.size()>0){
							ExtendField extendField = new ExtendField();
							extendField.setFieldName("CostCenter1");
							extendField.setFieldValue(fdCostCenter1.get(i));
							extendFieldList.add(extendField);
						}
						if(StringUtil.isNotNull(fdCostCenter2)){
							ExtendField extendField = new ExtendField();
							extendField.setFieldName("CostCenter2");
							extendField.setFieldValue(fdCostCenter2);
							extendFieldList.add(extendField);
						}
						if (StringUtil.isNotNull(fdCostCenter3)) {
							ExtendField extendField = new ExtendField();
							extendField.setFieldName("CostCenter3");
							extendField.setFieldValue(fdCostCenter3);
							extendFieldList.add(extendField);
						}
						if (StringUtil.isNotNull(fdDefineFlag1)) {
							ExtendField extendField = new ExtendField();
							extendField.setFieldName("DefineFlag1");
							extendField.setFieldValue(fdDefineFlag1);
							extendFieldList.add(extendField);
						}
						if (null != fdDefineFlag2 && fdDefineFlag2.size() > 0) {
							ExtendField extendField = new ExtendField();
							extendField.setFieldName("DefineFlag2");
							extendField.setFieldValue(fdDefineFlag2.get(i));
							extendFieldList.add(extendField);
						}
						setApprovalRequest.setExtendFieldList(extendFieldList);
						// **********************************扩展字段列表end*********************************//



						// **********************************设置机票和酒店住宿信息start******************************//
						ArrayList<FlightEndorsementDetail> flightEndorsementDetails = new ArrayList<FlightEndorsementDetail>();// 设置航班信息
						FlightEndorsementDetail flightEndorsementDetail = new FlightEndorsementDetail();
						ArrayList<HotelEndorsementDetail> hotelEndorsementDetails = new ArrayList<HotelEndorsementDetail>();// 设置酒店住宿信息
						HotelEndorsementDetail hotelEndorsementDetail = new HotelEndorsementDetail();
						if (null != fdFlightWay && fdFlightWay.size() > 0) {
							int fdFlightWayValue = fdFlightWay.get(i);
							if (fdFlightWayValue == 1) {
								flightEndorsementDetail
								.setFlightWay(FlightWayType.SingleTrip);// 设置单程1
							} else {
								flightEndorsementDetail
										.setFlightWay(FlightWayType.RoundTrip);// 设置往返
							}
						}
						// 开始时间区间
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						try {
							if (null != fdDepartBeginDate
									&& fdDepartBeginDate.size() > 0) {
								Date departBeginDate = fdDepartBeginDate.get(i);
								String dateValue = DateUtil.convertDateToString(departBeginDate, DateUtil.PATTERN_DATE);
								flightEndorsementDetail.setDepartBeginDate(sdf.parse(dateValue)); // 若json格式调用
								flightEndorsementDetail.setDepartEndDate(sdf.parse(EopBasedataFsscUtil.getAfterDate(sdf.parse(dateValue), fdDepartDateNo))); // "2017-08-05"

								hotelEndorsementDetail.setCheckInBeginDate(EopBasedataFsscUtil.getBeforeDate(sdf.parse(dateValue), fdDepartDateNo));
								hotelEndorsementDetail.setCheckInEndDate(sdf.parse(EopBasedataFsscUtil.getAfterDate(sdf.parse(dateValue), fdReturnDateNo)));
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
						// 返程日期范围起始日期
						try {
							if (null != fdReturnBeginDate
									&& fdReturnBeginDate.size() > 0) {
								Date departBeginDate = fdReturnBeginDate.get(i);
								String dateValue = DateUtil.convertDateToString(departBeginDate, DateUtil.PATTERN_DATE);
								flightEndorsementDetail.setReturnBeginDate(sdf.parse(dateValue)); // 若jason格式调用
								flightEndorsementDetail.setReturnEndDate(sdf.parse(EopBasedataFsscUtil.getAfterDate(sdf.parse(dateValue), fdReturnDateNo))); // "2017-08-05"

								hotelEndorsementDetail.setCheckOutBeginDate(EopBasedataFsscUtil.getBeforeDate(sdf.parse(dateValue), fdDepartDateNo - 1));
								hotelEndorsementDetail.setCheckOutEndDate(sdf.parse(EopBasedataFsscUtil.getAfterDate(sdf.parse(dateValue), fdReturnDateNo)));
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
						// 出发城市
						ArrayList<String> fromcities = new ArrayList<String>();
						if (null != fdFromCities && fdFromCities.size() > 0) {
							String fromcity = fdFromCities.get(i);
							String[] fromcitys = fromcity.split("\\(");
							fromcity = fromcitys[0];
							fromcities.add(fromcity);
						}
						// 到达城市
						ArrayList<String> tocities = new ArrayList<String>();
						if (null != fdToCities && fdToCities.size() > 0) {
							String toCity = fdToCities.get(i);
							toCity = toCity.split("\\(")[0];
							tocities.add(toCity);
						}
						flightEndorsementDetail.setFromCities(fromcities);
						flightEndorsementDetail.setToCities(tocities);
						hotelEndorsementDetail.setToCities(tocities);
						// 出行人数
						flightEndorsementDetail.setTravelerCount(1);
						// 航空类型
						if (null != fdProductType && fdProductType.size() > 0) {
							int productType = fdProductType.get(i);
							if (1 == productType) {
								flightEndorsementDetail.setProductType(ProductType.DomesticFlight);// 国内航空
								hotelEndorsementDetail.setProductType(HotelProductType.Domestic);// 国内酒店
							} else if (2 == productType) {
								flightEndorsementDetail.setProductType(ProductType.InternationalFlight);// 国际航空
								hotelEndorsementDetail.setProductType(HotelProductType.International);// 海外酒店
							}
						}

						// 最大价格（申请金额）
						if (null != fdPrice && fdPrice.size() > 0) {
							String fdMoney = fdPrice.get(i);
							if (StringUtil.isNotNull(fdMoney) && !"NaN".equals(fdMoney)) {
								hotelEndorsementDetail.setMaxPrice(fdMoney);// 酒店申请金额
							}
						}
						// 差标金额（单日标准）
						if (null != fdSingleDayStandard && fdSingleDayStandard.size() > 0) {
							String averagePriceStr = fdSingleDayStandard.get(i);// 单日标准金额
							if (StringUtil.isNotNull(averagePriceStr)
									&& StringUtil.isNotNull(hotelEndorsementDetail.getMaxPrice())
									&& !"NaN".equals(averagePriceStr)) {
								Double averagePriceDou = FsscNumberUtil.getDivide(
										Double.parseDouble(hotelEndorsementDetail.getMaxPrice()),
										Double.parseDouble(averagePriceStr), 2);
								BigDecimal bd1 = new BigDecimal(averagePriceDou);
								hotelEndorsementDetail.setAveragePrice(bd1.intValue());// 差标金额
								BigDecimal bd2 = new BigDecimal(Double.parseDouble(averagePriceStr));
								hotelEndorsementDetail.setTotalRoomNightCount(bd2.intValue());// 总间夜数
							}
						}

						// 单价
						if (null != fdPrice && fdPrice.size() > 0) {
							flightEndorsementDetail.setPrice(Float.parseFloat(fdPrice.get(i)));// price
						}

						// 承运航空公司
						if (null != fdAirLine && fdAirLine.size() > 0) {
							flightEndorsementDetail
									.setAirline(fdAirLine.get(i));// price
						}
						// 币种
						// UnKnow Int 未知 不管控（0）
						// RMB Int 人民币 人民币（1）
						// CNY Int 人民币 币种的默认值（2）
						if (null != fdCurrency && fdCurrency.size() > 0) {
							CurrencyType currency = CurrencyType.forValue(Integer.getInteger(fdCurrency.get(i)));
							flightEndorsementDetail.setCurrency(currency);// price
							hotelEndorsementDetail.setCurrency(currency);// price
						}
						// 折扣
						if (null != fdDiscount && fdDiscount.size() > 0) {
							flightEndorsementDetail.setDiscount(Float.parseFloat(fdDiscount.get(i)));// price
						}
						// 舱等
						// 字段 类型 描述 备注
						// UnKnow Int 未知 未知（0）
						// SaloonCabin Int 头等舱 头等舱（1）
						// BusinessClass Int 公务舱 公务舱（2）
						// SuperTouristClass Int 超级经济舱 超级经济舱（4）
						// TouristClass Int 经济舱 经济舱（3）
						if (null != fdSeatClass && fdSeatClass.size() > 0) {
							SeatClassType seatClassType = SeatClassType.forValue(Integer.getInteger(fdSeatClass.get(i)));
							flightEndorsementDetail.setSeatClass(seatClassType);// 舱等
						}

						// **********************************设置出行人列表start*************************************//
						ArrayList<PassengerDetail> passengerDetails = new ArrayList<PassengerDetail>();
						PassengerDetail passengerDetail = new PassengerDetail();
						if (element != null) {
							// 中文名
							String fdName = element.getFdName();
							if (StringUtil.isNotNull(fdName)) {
								for (int j = 0; j < 10; j++) {
									fdName = fdName.replaceAll(j + "", "");
								}
							}
							passengerDetail.setName(fdName);// 姓名
							passengerDetails.add(passengerDetail);
							EopBasedataPassport eopBasedataPassport = eopBasedataPassportService.getEopBasedataPassport(element.getFdId());
							if (eopBasedataPassport != null) {// 护照名
								passengerDetail = new PassengerDetail();
								passengerDetail.setName(eopBasedataPassport.getFdName());// 护照名
								passengerDetails.add(passengerDetail);
							}
						}
						// **********************************设置出行人列表end*************************************//
						flightEndorsementDetail.setPassengerList(passengerDetails);
						hotelEndorsementDetail.setPassengerList(passengerDetails);

						flightEndorsementDetails.add(flightEndorsementDetail);//机票
						hotelEndorsementDetails.add(hotelEndorsementDetail);// 酒店住宿

						if(FsscFeeConstant.FSSC_TRIP_YPE_1.equals(fsBaseExpenseItem.getFdTripType())){//机票
							setApprovalRequest.setFlightEndorsementDetails(flightEndorsementDetails);//机票
						} else if(FsscFeeConstant.FSSC_TRIP_YPE_2.equals(fsBaseExpenseItem.getFdTripType())) {// 酒店住宿
							setApprovalRequest.setHotelEndorsementDetails(hotelEndorsementDetails);// 酒店住宿
						}
						// **********************************设置机票和酒店住宿信息end***************************************//

						setApprovalServiceRequest.setRequest(setApprovalRequest);
						SetApprovalResponse setApprovalResponse = setapprovalService.SetApproval(setApprovalServiceRequest);

						if (setApprovalResponse != null
								&& setApprovalResponse.getStatus() != null)
						{
							System.out.printf("service result:%s",
									com.alibaba.fastjson.JSONObject
											.toJSONString(setApprovalResponse
													.getStatus()));

						}
						list.add(setApprovalResponse);
				}
			}

		}

		return list;

	}

	public IFsscCommonCtripService getFsscCommonCtripService() {
		if(fsscCommonCtripService == null){
			fsscCommonCtripService = (IFsscCommonCtripService) SpringBeanUtil.getBean("fsscCommonCtripService");
		}
		return fsscCommonCtripService;
	}

	public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	public void setEopBasedataPassportService(IEopBasedataPassportService eopBasedataPassportService) {
		this.eopBasedataPassportService = eopBasedataPassportService;
	}

	public void setEopBasedataExpenseItemService(IEopBasedataExpenseItemService eopBasedataExpenseItemService) {
		this.eopBasedataExpenseItemService = eopBasedataExpenseItemService;
	}
}
