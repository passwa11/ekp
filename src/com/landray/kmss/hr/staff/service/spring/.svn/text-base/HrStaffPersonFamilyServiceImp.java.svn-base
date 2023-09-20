package com.landray.kmss.hr.staff.service.spring;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffImportForm;
import com.landray.kmss.hr.staff.model.HrStaffPersonFamily;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonFamilyService;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 家庭信息
 * 
 * 
 */
public class HrStaffPersonFamilyServiceImp extends HrStaffImportServiceImp
		implements IHrStaffPersonFamilyService {

	@Override
	public String[] getImportFields() {
		return new String[] { "fdName", "fdRelated", "fdCompany",
				"fdOccupation", "fdConnect", "fdMemo" };
	}

	private HrStaffPersonFamily setSaveMode(HrStaffPersonInfo person,
			String fdName)
			throws Exception {
		String fdId = person.getFdId();
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock(
				"fdPersonInfo.fdId = :personInfoId and fdName=:fdName");
		hqlInfo.setParameter("personInfoId", fdId);
		hqlInfo.setParameter("fdName", fdName);
		List<HrStaffPersonFamily> list = findList(hqlInfo);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public KmssMessage saveImportData(HrStaffImportForm importForm,
			boolean isRollBack)
			throws Exception {
		Workbook wb = null;
		Sheet sheet = null;
		InputStream inputStream = null;
		int count = 0;
		KmssMessages messages = null;
		StringBuffer errorMsg = new StringBuffer();
		try {
			inputStream = importForm.getFile().getInputStream();
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			sheet = wb.getSheetAt(0);

			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.empty", "hr-staff"));
			}
			short lastCellNum = sheet.getRow(0).getLastCellNum();
			if (lastCellNum != 9) {
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.errFile", "hr-staff"));
			}

			// 从第二行开始取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				HrStaffPersonFamily family = new HrStaffPersonFamily();
				messages = new KmssMessages();
				Row row = sheet.getRow(i);
				if (row == null) { // 跳过空行
					continue;
				}
				// 获取列数
				int cellNum = row.getLastCellNum();
				HrStaffPersonInfo personInfo = null;
				// 是否是新建
				boolean isNew = true;
				// 是否有账号或工号
				boolean hasLoginNameOrStaffNo = false;
				for (int j = 1; j < cellNum; j++) {
					String value = ImportUtil.getCellValue(row.getCell(j));
					// if (StringUtil.isNull(value) && j > 1) {
					// messages.addError(new KmssMessage(ResourceUtil.getString(
					// "hrStaff.import.error.notNull", "hr-staff", null,
					// ResourceUtil.getString(
					// "calendar.msg.timeFormat.error"))));
					// }
					if (j == 1) {
						if (StringUtil.isNull((String) value)) {
                            continue;
                        }
						hasLoginNameOrStaffNo = true;
						personInfo = getHrStaffPersonInfoService()
								.findPersonInfoByLoginName((String) value);
						BeanUtils.setProperty(family, "fdPersonInfo",
								personInfo);
					}
					if (j == 2 && personInfo == null) {
						if (StringUtil.isNull((String) value)) {

							if (personInfo == null) {
								messages.addError(new KmssMessage(ResourceUtil
										.getString(
												"hr-staff:hrStaffPerson.import.noperson")));
								break;
							}
						}
						hasLoginNameOrStaffNo = true;
						personInfo = getHrStaffPersonInfoService()
								.findPersonInfoByStaffNo((String) value);
						if (personInfo == null) {
							if (personInfo == null) {
								messages.addError(new KmssMessage(ResourceUtil
										.getString(
												"hr-staff:hrStaffPerson.import.noperson")));
								break;
							}
						}
						BeanUtils.setProperty(family, "fdPersonInfo",
								personInfo);
					}

					if (!hasLoginNameOrStaffNo) {
						messages.addError(new KmssMessage(ResourceUtil
								.getString(
										"hr-staff:hrStaffTrackRecord.error.person")));
					} else {
						try {

							if (j > 2) {
								String[] fields = getImportFields();
								//#107273 数组下标越界引起的staff人事档案导入数据报错问题
								if (j - 3 < fields.length && "fdName".equals(fields[j - 3])) {
									HrStaffPersonFamily res = setSaveMode(personInfo,
											value);
									if (res != null) {
										family = res;
										isNew = false;
									}
								}

								if (value != null) {
									//#107273 数组下标越界引起的staff人事档案导入数据报错问题
									String name = j - 3 < fields.length ? fields[j - 3] : "";
									BeanUtils.setProperty(family, name,
											value);
								}
							}


						} catch (Exception e) {

						}
					}

				}
				// 如果有错误，就不进行导入
				if (!messages.hasError()) {
					super.saveData(family, isNew);
					count++;
				} else {
					errorMsg.append(ResourceUtil.getString(
							"hrStaff.import.error.num", "hr-staff", null, i));
					// 解析错误信息
					for (KmssMessage message : messages.getMessages()) {
						errorMsg.append(message.getMessageKey());
					}
					errorMsg.append("<br>");
				}
			}

			KmssMessage message = null;
			if (errorMsg.length() > 0) {
				errorMsg.insert(0, ResourceUtil.getString(
						"hrStaff.import.portion.failed", "hr-staff", null, count)
						+ "<br>");
				message = new KmssMessage(errorMsg.toString());
				message.setMessageType(KmssMessage.MESSAGE_ERROR);
			} else {
				message = new KmssMessage(ResourceUtil.getString(
						"hrStaff.import.success", "hr-staff", null, count));
				message.setMessageType(KmssMessage.MESSAGE_COMMON);
			}
			return message;
		} catch (Exception e) {
			throw new RuntimeException(ResourceUtil.getString(
					"hrStaff.import.error", "hr-staff"));
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}

	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model,
			RequestContext requestContext) throws Exception {
		HrStaffPersonFamily family = (HrStaffPersonFamily) super.convertFormToModel(
				form, model, requestContext);
		if (family.getFdCreator() == null) {
			family.setFdCreator(UserUtil.getUser());
		}
		if (family.getFdCreateTime() == null) {
			family.setFdCreateTime(new Date());
		}
		return family;
	}
}
