package com.landray.kmss.hr.staff.service.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.hr.staff.forms.HrStaffImportForm;
import com.landray.kmss.hr.staff.model.HrStaffAccumulationFund;
import com.landray.kmss.hr.staff.model.HrStaffImport;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfoSettingNew;
import com.landray.kmss.hr.staff.model.HrStaffSecurity;
import com.landray.kmss.hr.staff.service.IHrStaffAccumulationFundService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.service.IHrStaffSecurityService;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserAddOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class HrStaffAccumulationFundServiceImp extends HrStaffImportServiceImp
		implements IHrStaffAccumulationFundService {

	@Override
	public String[] getImportFields() {
		// TODO Auto-generated method stub
		return new String[] {
				"fdDeliveryDate","fdIndividualProvidentFundAccount","fdIdCard","fdProvidentFundInsuranceCompany","fdPaymentBase","fdPersonalDelivery","fdUnitDelivery","fdDeliveryAmount","fdRemark","fdPlaceOfInsurancePayment",
				
				
				};
	}

	@SuppressWarnings("unchecked")
	@Override
	public String add(IBaseModel modelObj) throws Exception {
		// 检查该员工是否已经有数据
		HrStaffAccumulationFund model = (HrStaffAccumulationFund) modelObj;
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("fdId = :personInfoId");
		hqlInfo.setParameter("personInfoId", model.getFdId());
		List<HrStaffAccumulationFund> list = findPage(hqlInfo).getList();
		if (!list.isEmpty()) {
			throw new KmssException(new KmssMessage(ResourceUtil
					.getString("hr-staff:hrStaffAccumulationFund.exist")));
		}

		return super.add(modelObj);
	}
	@Override
	public KmssMessage saveImportData(HrStaffImportForm importForm,
									  boolean isRollBack) throws Exception {
		Workbook wb = null;
		Sheet sheet = null;
		HrStaffImport baseModel = null;
		int count = 0;
		KmssMessages messages = null;
		StringBuffer errorMsg = new StringBuffer();
		InputStream inputStream = null;
		Set<HrStaffPersonInfo> targets = new HashSet<HrStaffPersonInfo>();
		try {
			inputStream = importForm.getFile().getInputStream();
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			sheet = wb.getSheetAt(0);

			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				errorMsg.append(ResourceUtil.getString(
						"hrStaff.import.empty", "hr-staff"));
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.empty", "hr-staff"));
			}

			// 检查文件是否是下载的模板文件
			if (!checkFile(sheet)) {
				errorMsg.append(ResourceUtil.getString(
						"hrStaff.import.errFile", "hr-staff"));
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.errFile", "hr-staff"));
			}

			Map<String, SysDictCommonProperty> map = SysDataDict.getInstance()
					.getModel(getModelName()).getPropertyMap();
			String[] headers = getImportFields();
			int breakFlag = headers.length - 1;
			if (getModelName().equals(HrStaffPersonExperienceContract.class.getName())) {
				breakFlag += 1;
			}
			// 需要跳过前面几个列
			int skipColumn = 3;

			List<String> checkFileds = getCheckFields();
			// 从第二行开始取数据
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				messages = new KmssMessages();
				Row row = sheet.getRow(i);
				if (row == null) { // 跳过空行
					continue;
				}

				// 非空判断
				HrStaffImportUtil.validateNotNullProperties(map, headers, row,
						messages);

				baseModel = (HrStaffImport) com.landray.kmss.util.ClassUtils.forName(getModelName())
						.newInstance();
				// 获取列数
				int cellNum = row.getLastCellNum();
				// 员工信息
				HrStaffPersonInfo personInfo = null;
				// 是否是新建
				boolean isNew = true;
				// 是否有账号或工号
				boolean hasLoginNameOrStaffNo = false;

				IUserAddOper addOper = null;
				String fdNo = null;
				for (int j = 0; j < cellNum; j++) {
					Object value = null;
					if (j == 0) {
						fdNo = ImportUtil.getCellValue(row.getCell(j));
					}
					if (j == 1) { // 第一列为登录账号，需要校验此账号是否存在
						value = ImportUtil.getCellValue(row.getCell(j));
						if (StringUtil.isNull((String) value)) {
                            continue;
                        }
						hasLoginNameOrStaffNo = true;
						personInfo = getHrStaffPersonInfoService()
								.findPersonInfoByLoginNameAndNo((String) value,
										fdNo);
					} else if (j == 2 && personInfo == null) { // 第二列为工号，如果通过登录账号没有找到人员，则需要再通过工号继续查找
						value = ImportUtil.getCellValue(row.getCell(j));
						if (StringUtil.isNull((String) value)) {
                            continue;
                        }
						hasLoginNameOrStaffNo = true;
						personInfo = getHrStaffPersonInfoService()
								.findPersonInfoByStaffNo((String) value);
					}

					if (j == skipColumn) { // 因为登录账号和工号是至少需要一个，如果2个都为空，则不允许导入
						if (!hasLoginNameOrStaffNo) {
							messages.addError(new KmssMessage(ResourceUtil
									.getString(
											"hr-staff:hrStaff.import.templet.selectOne")));
						} else {
							if (personInfo == null) {
								String loginName = ImportUtil
										.getCellValue(row.getCell(1));
								String _loginName = null;
								if (StringUtil.isNotNull(loginName)) {
									_loginName = ResourceUtil.getString(
											"hr-staff:hrStaff.import.error.loginName",
											null, null, loginName);
								}
								String staffNo = ImportUtil
										.getCellValue(row.getCell(2));
								String _staffNo = null;
								if (StringUtil.isNotNull(staffNo)) {
									_staffNo = ResourceUtil.getString(
											"hr-staff:hrStaff.import.error.staffNo",
											null, null, staffNo);
								}
								String msg = StringUtil.linkString(_loginName,
										ResourceUtil
												.getString(
														"hr-staff:hrStaff.import.error.and"),
										_staffNo);
								messages.addError(
										new KmssMessage(ResourceUtil.getString(
												"hr-staff:hrStaff.import.error.personInfo.notFind",
												null, null, msg)));
								break;
							} else {
								BeanUtils.setProperty(baseModel, "fdPersonInfo",
										personInfo);
//								if (!isRollBack) {
//									HrStaffImport importModel = getByPersonInfo(
//											personInfo
//													.getFdId());
//									if (importModel != null) {
//										isNew = false;
//										baseModel = importModel;
//									}
//								}
							}
						}
					}

					if (j > 2) { // 第三列以后才是导入数据部分
						int cellIndex = j - skipColumn;
						if (cellIndex > breakFlag) {
							break;
						}
						if (cellIndex <= headers.length - 1) {
							String fdField = headers[cellIndex];
							SysDictCommonProperty property = map.get(fdField);
							value = ImportUtil.getCellValue(row.getCell(j),
									property,
									null);
							if (HrStaffImportUtil.checkValue(property, value, messages)) {
								ConvertUtils.register(new DateConverter(null), Date.class);
								BeanUtils.setProperty(baseModel, fdField, value);
							}
							if (!ArrayUtil.isEmpty(checkFileds) && checkFileds.contains(fdField) && value != null) {
								//如果子类中存在需要验证的字段，则判断值是否在配置中
								List<HrStaffPersonInfoSettingNew> settingValues = getHrConfigList(fdField);
								if (settingValues != null) {
									boolean flge = false;
									for (HrStaffPersonInfoSettingNew hrStaffPersonInfoSettingNew : settingValues) {
										if (value.equals(hrStaffPersonInfoSettingNew.getFdName())) {
											flge = true;
										}
									}
									if (!flge) {
										String fdFieldLabel = ResourceUtil.getString(property.getMessageKey());
										//如果值不在系统配置中，则提示错误
										messages.addError(new KmssMessage(fdFieldLabel + ResourceUtil.getString("hr-staff:hrEntry.import.sheet2.item.node6")));
										continue;
									}
								}
							}
							// 添加日志信息
							if (UserOperHelper.allowLogOper("fileUpload",
									getModelName())) {
								addOper = UserOperContentHelper.putAdd(baseModel,
										fdField);
							}
						}
						if (cellIndex == headers.length) {
							String attPath = ImportUtil
									.getCellValue(row.getCell(j));
							if (StringUtil.isNotNull(attPath)) {
								String[] attStrs = attPath.split("[;；]");
								List<String> fileNames = new ArrayList<String>();
								String retunStr = "";
								for (int q = 0; q < attStrs.length; q++) {
									if (StringUtil.isNull(attStrs[q])) {
                                        continue;
                                    }
									File attFile = new File(attStrs[q]);
									String fileLimitType = "1";

									String attDisables = SysAttConstant.DISABLED_FILE_TYPE;

									// 限制上传的附件类型
									if (StringUtil.isNotNull(
											ResourceUtil.getKmssConfigString(
													"sys.att.fileLimitType"))) {
										fileLimitType = ResourceUtil
												.getKmssConfigString(
														"sys.att.fileLimitType");

										if (ResourceUtil.getKmssConfigString(
												"sys.att.disabledFileType") != null) {
                                            attDisables = ResourceUtil
                                                    .getKmssConfigString(
                                                            "sys.att.disabledFileType");
                                        }
									}

									if (StringUtil.isNotNull(attStrs[q])) {
										String _fileType = null;
										if (attStrs[q].indexOf(".") > -1) {
											_fileType = attStrs[q].substring(
													attStrs[q].lastIndexOf("."));
										}
										if (StringUtil.isNotNull(_fileType)) {
											_fileType = _fileType.toLowerCase();
											String[] files = attDisables
													.split("[;；]");
											if ("1".equals(fileLimitType)) {
												Boolean isPass = true;
												for (String f : files) {
													if (_fileType.equals(f)) {
														isPass = false;
														break;
													}
												}
												if (!isPass) {
													messages.addError(
															new KmssMessage(
																	ResourceUtil
																			.getString(
																					"hrStaff.import.attachement.typeNotAllow",
																					"hr-staff",
																					null,
																					new Object[]{
																							attDisables,
																							attStrs[q]})));
													break;
												}
											} else if ("2".equals(fileLimitType)) {

												Boolean isPass = false;
												for (String f : files) {
													if (_fileType.equals(f)) {
														isPass = true;
														break;
													}
												}
												if (!isPass) {
													messages.addError(
															new KmssMessage(
																	ResourceUtil
																			.getString(
																					"hrStaff.import.attachement.typeOnlyAllow",
																					"hr-staff",
																					null,
																					new Object[]{
																							attDisables,
																							attStrs[q]})));
													break;
												}
											}
										}
									}
									if (fileNames.contains(attFile.getName())) {
										messages.addError(new KmssMessage(
												ResourceUtil.getString(
														"hrStaff.import.attachement.nameNotAllow",
														"hr-staff", null,
														attStrs[q])));
										break;
									} else {
										fileNames.add(attFile.getName());
									}
									if (attFile == null || !attFile.exists()) {
										retunStr = retunStr + attStrs[q] + ";";
									}
								}
								if (retunStr.length() > 0) {
									messages.addError(
											new KmssMessage(ResourceUtil.getString(
													"hrStaff.import.attachement.fileNotExist",
													"hr-staff", null, retunStr)));
									break;
								}
								for (int k = 0; k < attStrs.length; k++) {
									if (StringUtil.isNull(attStrs[k])) {
                                        continue;
                                    }
									String attName = attStrs[k]
											.substring(
													attStrs[k].lastIndexOf(
															System.getProperty(
																	"file.separator"))
															+ 1,
													attStrs[k].length());
									File attFile = new File(attStrs[k]);
									if (attFile != null) {
										FileInputStream fileInputStream = new FileInputStream(
												attFile);
										getSysAttMainService().addAttachment(
												baseModel,
												IHrStaffPersonExperienceContractService.FD_ATT_KEY,
												fileInputStream, attFile.getName(),
												"byte",
												Double.valueOf(fileInputStream
														.available()),
												attStrs[k]);
										IOUtils.closeQuietly(fileInputStream);
									}
								}
							}
						}
					}
				}
				// 添加日志信息
				if (UserOperHelper.allowLogOper("fileUpload", getModelName())) {
					addOper.putSimple("loginName",
							ImportUtil.getCellValue(row.getCell(0)))
							.putSimple("staffNo",
									ImportUtil.getCellValue(row.getCell(1)));
				}
				// 如果有错误，就不进行导入
				if (!messages.hasError()) {
					saveData(baseModel, isNew);
					count++;
					targets.add(personInfo);
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
				if (isRollBack) {
					// 如果有一条数据校验失败，则需要数据回滚。
					throw new RuntimeException(errorMsg.toString());
				} else {
					errorMsg.insert(0, ResourceUtil.getString(
							"hrStaff.import.portion.failed", "hr-staff", null,
							count)
							+ "<br>");
					message = new KmssMessage(errorMsg.toString());
					message.setMessageType(KmssMessage.MESSAGE_ERROR);
					// 记录导入日志
					buildLog(targets, count);
				}
			} else {
				message = new KmssMessage(ResourceUtil.getString(
						"hrStaff.import.success", "hr-staff", null, count));
				message.setMessageType(KmssMessage.MESSAGE_COMMON);
				// 记录导入日志
				buildLog(targets, count);
			}

			return message;
		} catch (Exception e) {
			e.printStackTrace();
			if (errorMsg.length() > 0) {
				throw new RuntimeException(errorMsg.toString());
			}else{
				throw new RuntimeException(ResourceUtil.getString(
						"hrStaff.import.error", "hr-staff"));
			}
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}

	}
	
}
