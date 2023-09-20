package com.landray.kmss.hr.staff.service.spring;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import com.landray.kmss.sys.attachment.util.SysAttConstant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.ClassUtils;

import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.model.HrStaffImport;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffPersonInfoService;
import com.landray.kmss.hr.staff.util.ErrorMsgBean;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserAddOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class HrStaffPersonExperienceContractImportThread implements Runnable {
	private CountDownLatch countDownLatch; 
	private Sheet sheet ;
	private int i;
	Map<String, SysDictCommonProperty> map;
	String[] headers;
	String modelName; 
	
	IHrStaffPersonInfoService hrStaffPersonInfoService;
	HrStaffPersonExperienceBaseServiceImp service;
	int breakFlag;
	int skipColumn=3;
	
	List<HrStaffImport> saveList;
	List<HrStaffImport> updateList;
	List<ErrorMsgBean> errorList;
	Set<HrStaffPersonInfo> targets; 
	List<HrStaffContractType> fdContTypeList ;
	List<Map<String,Object>> fileList;
	
	 boolean isRollBack;
	public HrStaffPersonExperienceContractImportThread(CountDownLatch countDownLatch, Sheet sheet, int i,
			Map<String, SysDictCommonProperty> map, String[] headers, String modelName,  
			IHrStaffPersonInfoService hrStaffPersonInfoService, HrStaffPersonExperienceBaseServiceImp service,
			int breakFlag, List<HrStaffImport> saveList, List<HrStaffImport> updateList,
			List<ErrorMsgBean> errorList, Set<HrStaffPersonInfo> targets,
			List<HrStaffContractType> fdContTypeList,List<Map<String,Object>> fileList,boolean isRollBack) {
		super();
		this.countDownLatch = countDownLatch;
		this.sheet = sheet;
		this.i = i;
		this.map = map;
		this.headers = headers;
		this.modelName = modelName; 
		this.hrStaffPersonInfoService = hrStaffPersonInfoService;
		this.service = service;
		this.breakFlag = breakFlag; 
		this.saveList = saveList;
		this.updateList = updateList;
		this.errorList = errorList;
		this.targets = targets; 
		this.fdContTypeList=fdContTypeList;
		this.fileList=fileList;
		this.isRollBack=isRollBack;
	}


	@Override
	public void run() {
		KmssMessages messages = new KmssMessages();
		try { 
			Row row = sheet.getRow(i); 
			if (row == null) {  
				return;
			} 
			// 非空判断
			HrStaffImportUtil.validateNotNullProperties(map, headers, row, messages);

			HrStaffImport baseModel = (HrStaffImport) com.landray.kmss.util.ClassUtils.forName(modelName).newInstance();
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
					personInfo = hrStaffPersonInfoService.findPersonInfoByLoginNameAndNo((String) value, fdNo);
				} else if (j == 2 && personInfo == null) { // 第二列为工号，如果通过登录账号没有找到人员，则需要再通过工号继续查找
					value = ImportUtil.getCellValue(row.getCell(j));
					if (StringUtil.isNull((String) value)) {
						continue;
					}
					hasLoginNameOrStaffNo = true;
					personInfo = hrStaffPersonInfoService.findPersonInfoByStaffNo((String) value);
				}

				if (j == skipColumn) {
					// 因为登录账号和工号是至少需要一个，如果2个都为空，则不允许导入
					if (!hasLoginNameOrStaffNo) {
						messages.addError(
								new KmssMessage(ResourceUtil.getString("hr-staff:hrStaff.import.templet.selectOne")));
					} else {
						if (personInfo == null) {
							String loginName = ImportUtil.getCellValue(row.getCell(1));
							String _loginName = null;
							if (StringUtil.isNotNull(loginName)) {
								_loginName = ResourceUtil.getString("hr-staff:hrStaff.import.error.loginName", null,
										null, loginName);
							}
							String staffNo = ImportUtil.getCellValue(row.getCell(2));
							String _staffNo = null;
							if (StringUtil.isNotNull(staffNo)) {
								_staffNo = ResourceUtil.getString("hr-staff:hrStaff.import.error.staffNo", null, null,
										staffNo);
							}
							String msg = StringUtil.linkString(_loginName,
									ResourceUtil.getString("hr-staff:hrStaff.import.error.and"), _staffNo);
							messages.addError(new KmssMessage(ResourceUtil
									.getString("hr-staff:hrStaff.import.error.personInfo.notFind", null, null, msg)));
							break;
						} else {
							BeanUtils.setProperty(baseModel, "fdPersonInfo", personInfo); 
							if(!isRollBack) {
								HrStaffImport importModel = service.getByPersonInfo(personInfo.getFdId());
								if (importModel != null) {
									isNew = false;
									baseModel = importModel;
								} 
							}
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
						value = ImportUtil.getCellValue(row.getCell(j), property, null);
						if ("fdSignType".equals(fdField)) {
							if (value == null) {
                                continue;
                            }
							if ("首次签订".equals(value)) {
                                value = "1";
                            }
							if ("续签".equals(value)) {
                                value = "2";
                            }
							if (!"1".equals(value) && !"2".equals(value)) {
								messages.addError(new KmssMessage(
										ResourceUtil.getString("hr-staff:hrStaffPersonExperienceContract.itemNode.5")));

								continue;
							}
						}
						if ("fdContType".equals(fdField)) {

							if (value == null) {
                                continue;
                            }
							
							boolean flge = false;
							for (HrStaffContractType ct : fdContTypeList) {
								if (ct.getFdName().equals(value)) {
									flge = true;
									BeanUtils.setProperty(baseModel, "fdStaffContType", ct);
									break;
								}

							}
							if (!flge) {
								messages.addError(new KmssMessage(
										ResourceUtil.getString("hr-staff:hrStaffPersonExperienceContract.itemNode.4")));
								continue;
							}

						}
						if ("fdContStatus".equals(fdField)) {
							//导入默认就是正常状态
							if (value == null) {
								value ="1";
							}
							else if ("正常".equals(value)) {
								value = "1";
							}
							else if ("已解除".equals(value)) {
								value = "3";
							}
							else if ("已到期".equals(value)) {
								value = "2";
							}
							if (!"1".equals(value) && !"2".equals(value) && !"3".equals(value)) {

								messages.addError(new KmssMessage(
										ResourceUtil.getString("hr-staff:hrStaffPersonExperienceContract.itemNode.6")));
								continue;
							}
						}

						if (HrStaffImportUtil.checkValue(property, value, messages)) {
							BeanUtils.setProperty(baseModel, fdField, value);
						}
						// 添加日志信息
						if (UserOperHelper.allowLogOper("fileUpload", modelName)) {
							addOper = UserOperContentHelper.putAdd(baseModel, fdField);
						}

					}
					if (cellIndex == headers.length) {
						String attPath = ImportUtil.getCellValue(row.getCell(j));
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
								if (StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))) {
									fileLimitType = ResourceUtil.getKmssConfigString("sys.att.fileLimitType");

									if (ResourceUtil.getKmssConfigString("sys.att.disabledFileType") != null) {
                                        attDisables = ResourceUtil.getKmssConfigString("sys.att.disabledFileType");
                                    }
								}

								if (StringUtil.isNotNull(attStrs[q])) {
									String _fileType = null;
									if (attStrs[q].indexOf(".") > -1) {
										_fileType = attStrs[q].substring(attStrs[q].lastIndexOf("."));
									}
									if (StringUtil.isNotNull(_fileType)) {
										_fileType = _fileType.toLowerCase();
										String[] files = attDisables.split("[;；]");
										if ("1".equals(fileLimitType)) {
											Boolean isPass = true;
											for (String f : files) {
												if (_fileType.equals(f)) {
													isPass = false;
													break;
												}
											}
											if (!isPass) {
												messages.addError(new KmssMessage(ResourceUtil.getString(
														"hrStaff.import.attachement.typeNotAllow", "hr-staff", null,
														new Object[] { attDisables, attStrs[q] })));
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
												messages.addError(new KmssMessage(ResourceUtil.getString(
														"hrStaff.import.attachement.typeOnlyAllow", "hr-staff", null,
														new Object[] { attDisables, attStrs[q] })));
												break;
											}
										}
									}
								}
								if (fileNames.contains(attFile.getName())) {
									messages.addError(new KmssMessage(ResourceUtil.getString(
											"hrStaff.import.attachement.nameNotAllow", "hr-staff", null, attStrs[q])));
									break;
								} else {
									fileNames.add(attFile.getName());
								}
								if (attFile == null || !attFile.exists()) {
									retunStr = retunStr + attStrs[q] + ";";
								}
							}
							if (retunStr.length() > 0) {
								messages.addError(new KmssMessage(ResourceUtil.getString(
										"hrStaff.import.attachement.fileNotExist", "hr-staff", null, retunStr)));
								break;
							}
							//封装数据到外部操作数据库
							for (int k = 0; k < attStrs.length; k++) {
								if (StringUtil.isNull(attStrs[k])) {
                                    continue;
                                }
								Map<String,Object> info=new HashMap<String, Object>();
								info.put("attStrs",attStrs[k]);
								info.put("model",baseModel);
								fileList.add(info); 
								
							}
						}
					}
				}
			}
			// 添加日志信息
			if (UserOperHelper.allowLogOper("fileUpload", modelName)) {
				addOper.putSimple("loginName", ImportUtil.getCellValue(row.getCell(0))).putSimple("staffNo",
						ImportUtil.getCellValue(row.getCell(1)));
			}
			// 如果有错误，就不进行导入
			if (!messages.hasError()) {
				if(isNew) {
					saveList.add(baseModel);
				}else {
					updateList.add(baseModel);
				} 
				targets.add(personInfo);
			} else {
				StringBuilder errorMsg=new StringBuilder(); 
				errorMsg.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i)); 
				// 解析错误信息
				for (KmssMessage message : messages.getMessages()) {
					errorMsg.append(message.getMessageKey()); 
				}
				ErrorMsgBean errorBean=new ErrorMsgBean();
				errorBean.setNum(i);
				errorBean.setMsg(errorMsg.toString()); 
				errorList.add(errorBean); 
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ErrorMsgBean errorBean=new ErrorMsgBean();
			errorBean.setNum(i);
			errorBean.setMsg(e.getMessage()); 
			errorList.add(errorBean);  
		}finally {
			countDownLatch.countDown();
		}
	}
	 

}
