package com.landray.kmss.km.smissive.service.spring;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.km.smissive.model.KmSmissiveNumber;
import com.landray.kmss.km.smissive.service.IKmSmissiveNumberService;
import com.landray.kmss.sys.number.model.SysNumberMainFlow;
import com.landray.kmss.sys.number.service.ISysNumberMainFlowService;
import com.landray.kmss.sys.number.util.NumberContentElement;
import com.landray.kmss.sys.number.util.NumberTypeConst;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class KmSmissiveNumberServiceImp extends BaseServiceImp implements
		IKmSmissiveNumberService {
	
	@Override
    public synchronized String getTempNumFromDb(String fdNumberId) throws Exception {
		String docNum = "";
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"kmSmissiveNumber.fdNumberId = :fdNumberId ");
		hqlInfo.setParameter("fdNumberId", fdNumberId);
		List l = this.findList(hqlInfo);
		if (l.size() > 0) {
			KmSmissiveNumber kmSmissiveNumber = (KmSmissiveNumber) l.get(0);
			docNum = kmSmissiveNumber.getFdNumberValue();
		}
		return docNum;
	}

	@Override
    public void deleteTempNumFromDb(String fdNumberId, String docBufferNum) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "kmSmissiveNumber.fdNumberId = :fdNumberId";
		if(StringUtil.isNotNull(docBufferNum)){
			whereBlock = StringUtil.linkString(whereBlock, " and ", "kmSmissiveNumber.fdNumberValue=:fdNumberValue");
			hqlInfo.setParameter("fdNumberValue", docBufferNum);
		}
		hqlInfo.setWhereBlock(whereBlock);
		hqlInfo.setParameter("fdNumberId", fdNumberId);
		
		List<KmSmissiveNumber> l = this.findList(hqlInfo);
		if (l.size() > 0) {
			for (KmSmissiveNumber kmSmissiveNumber : l) {
				this.delete(kmSmissiveNumber);
			}
		}
	}

	public NumberContentElement[] getElements(String ele) {
		String key = "";
		String value = "";
		NumberContentElement nce[] = null;

		String[] elementList = ele.split(";");
		if (elementList.length > 0) {
			nce = new NumberContentElement[elementList.length];
			int i = 0;
			for (String string : elementList) {
				nce[i] = new NumberContentElement();

				String[] childEles = string.split(",");
				if (childEles.length > 0) {
					for (String string2 : childEles) {
						String[] suns = string2.split("@@@");
						if (suns.length == 2) {
							key = suns[0];
							value = suns[1];
							if ("type".equals(key)) {
								nce[i].setType(value);
							} else if ("value".equals(key)) {
								nce[i].setValue(value);
							} else if ("len".equals(key)) {
								nce[i].setLen(value);
							} else if ("period".equals(key)) {
								nce[i].setPeriod(value);
							} else if ("start".equals(key)) {
								nce[i].setStart(value);
							} else if ("step".equals(key)) {
								nce[i].setStep(value);
							} else if ("id".equals(key)) {
								nce[i].setId(value);
							}
						}
					}
				}
				i++;
			}
		}
		return nce;
	}

	@Override
	public void deleteExpiredNum(SysQuartzJobContext context) throws Exception {
		List<KmSmissiveNumber> list = this.findList("1=1", "docCreateTime");
		for (KmSmissiveNumber kmSmissiveNumber : list) {
			boolean deleteFlag = false;
			String fdNumberId = kmSmissiveNumber.getFdNumberId();
			String fdNumberVal = kmSmissiveNumber.getFdNumberValue();
			if (StringUtil.isNotNull(fdNumberId) && StringUtil.isNotNull(fdNumberVal)) {
				String fdFlowId = fdNumberVal.substring(fdNumberVal.lastIndexOf("#") + 1, fdNumberVal.length());
				ISysNumberMainFlowService sysNumberMainFlowService = (ISysNumberMainFlowService) SpringBeanUtil
						.getBean("sysNumberMainFlowService");
				SysNumberMainFlow sysNumberMainFlow = (SysNumberMainFlow) sysNumberMainFlowService
						.findByPrimaryKey(fdFlowId, SysNumberMainFlow.class, true);
				if (sysNumberMainFlow != null) {
					String strContentSimple = sysNumberMainFlow.getFdContentSimple();
					NumberContentElement[] nce = getElements(strContentSimple);
					for (int i = 0; i < nce.length; i++) {
						NumberContentElement ele = nce[i];
						if (NumberTypeConst.NUMBER_TYPE_FLOW.equals(ele.getType())) {
							int period = Integer.parseInt(ele.getPeriod());
							// 缓存数据创建时间
							Date createDateTime = kmSmissiveNumber.getDocCreateTime();
							Calendar createCal = Calendar.getInstance();
							createCal.setTime(createDateTime);
							createCal.add(Calendar.DATE, 0);
							createCal.set(Calendar.HOUR_OF_DAY, 0);
							createCal.set(Calendar.MINUTE, 0);
							createCal.set(Calendar.SECOND, 0);
							createCal.set(Calendar.MILLISECOND, 0);
							Date createDate = createCal.getTime();
							// 上一次获取该编号规则编号时间
							Date beforeGetDate = sysNumberMainFlow.getFdBeforeGetDate();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(beforeGetDate);
							// 现在时间
							Calendar calendarNow = Calendar.getInstance();
							switch (period) {
							case 1:
								long btime = beforeGetDate.getTime();
								long ctime = createDate.getTime();
								long ntime = DateUtil.getDate(0).getTime();
								if (btime != ntime || ctime != ntime) {
									deleteFlag = true;
								}
								break;
							case 2:
								if (calendar.get(Calendar.MONTH) != calendarNow.get(Calendar.MONTH)
										|| createCal.get(Calendar.MONTH) != calendarNow.get(Calendar.MONTH)) {
									deleteFlag = true;
								}
								break;
							case 3:
								if (calendar.get(Calendar.YEAR) != calendarNow.get(Calendar.YEAR)
										|| createCal.get(Calendar.YEAR) != calendarNow.get(Calendar.YEAR)) {
									deleteFlag = true;
								}
								break;
							}
						}
					}
				} else {
					deleteFlag = true;
				}
			} else {
				deleteFlag = true;
			}
			if (deleteFlag) {
				this.delete(kmSmissiveNumber);
			}
		}
	}

}
