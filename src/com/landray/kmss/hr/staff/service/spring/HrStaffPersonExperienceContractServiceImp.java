package com.landray.kmss.hr.staff.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.hr.staff.forms.HrStaffImportForm;
import com.landray.kmss.hr.staff.forms.HrStaffPersonExperienceContractForm;
import com.landray.kmss.hr.staff.model.HrStaffAlertWarningContract;
import com.landray.kmss.hr.staff.model.HrStaffContractType;
import com.landray.kmss.hr.staff.model.HrStaffImport;
import com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract;
import com.landray.kmss.hr.staff.model.HrStaffPersonInfo;
import com.landray.kmss.hr.staff.service.IHrStaffContractTypeService;
import com.landray.kmss.hr.staff.service.IHrStaffPersonExperienceContractService;
import com.landray.kmss.hr.staff.util.ErrorMsgBean;
import com.landray.kmss.hr.staff.util.HrStaffAuthorityUtil;
import com.landray.kmss.hr.staff.util.HrStaffDateUtil;
import com.landray.kmss.hr.staff.util.HrStaffImportUtil;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.IUserAddOper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.ListSortUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.sunbor.web.tag.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 合同信息
 * 
 * @author 潘永辉 2016-12-30
 * 
 */
public class HrStaffPersonExperienceContractServiceImp extends HrStaffPersonExperienceBaseServiceImp
		implements IHrStaffPersonExperienceContractService {
	private ThreadPoolTaskExecutor taskExecutor;

	public void setTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	@Override
	public String[] getImportFields() {
		// 合同名称、合同类型、签订标识、开始时间、到期时间、合同状态、备注
		return new String[] { "fdName", "fdContType", "fdSignType", "fdContStatus", "fdHandleDate", "fdBeginDate",
				"fdEndDate", "fdMemo" };
	}

	/**
	 * 导入数据
	 * 
	 * @param importForm
	 * @param isRollBack 发生异常时数据是否需要回滚（通常有唯一性时，是不需要回滚，即同一员工的数据在表中只会有一条记录，导入时可以做新增或更新操作）
	 * @return
	 * @throws Exception
	 */
	@Override
	public KmssMessage saveImportData(HrStaffImportForm importForm, boolean isRollBack) throws Exception {
		Workbook wb = null;
		Sheet sheet = null;
		InputStream inputStream = null;
		KmssMessage message = null;
		List<ErrorMsgBean> errorList = Collections.synchronizedList(new ArrayList<ErrorMsgBean>());
		try {
			inputStream = importForm.getFile().getInputStream();
			// 抽象类创建Workbook，适合excel 2003和2007以上
			wb = WorkbookFactory.create(inputStream);
			sheet = wb.getSheetAt(1);
			// 一份标准的模板，最少包含2行，第一行是字段名称，导入的数据应该是从第二行开始
			int rowNum = sheet.getLastRowNum();
			if (rowNum < 1) {
				throw new RuntimeException(ResourceUtil.getString("hrStaff.import.empty", "hr-staff"));
			}
			// 检查文件是否是下载的模板文件
			if (!checkFile(sheet)) {
				throw new RuntimeException(ResourceUtil.getString("hrStaff.import.errFile", "hr-staff"));
			}

			Map<String, SysDictCommonProperty> map = SysDataDict.getInstance().getModel(getModelName()).getPropertyMap();
			String[] headers = getImportFields();
			int breakFlag = headers.length - 1;
			if (getModelName().equals(HrStaffPersonExperienceContract.class.getName())) {
				breakFlag += 1;
			}


			List<HrStaffImport> saveList = Collections.synchronizedList(new ArrayList<HrStaffImport>());
			List<HrStaffImport> updateList = Collections.synchronizedList(new ArrayList<HrStaffImport>());
			List<Map<String, Object>> fileList = Collections.synchronizedList(new ArrayList<Map<String, Object>>());
			Set<HrStaffPersonInfo> targets = Collections.synchronizedSet(new HashSet<HrStaffPersonInfo>());
            Map<String, Integer> contractMap = new HashMap<String, Integer>();
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setSelectBlock("hrStaffContractType");
			hqlInfo.setFromBlock("HrStaffContractType hrStaffContractType");
			List<HrStaffContractType> fdContTypeList = this.findValue(hqlInfo);
            //循环处理
            int max = sheet.getLastRowNum();
            for (int j = 1; j <= max; j++) {
                hrStaffPersonExperienceContractImport(sheet, j, map, headers, getModelName(), breakFlag, saveList,
                        updateList, errorList, targets, fdContTypeList, fileList, isRollBack, contractMap);
            }

			if (errorList.size() > 0) {
				ListSortUtil.sort(errorList, "num", false);
			}

			if (isRollBack && errorList.size() > 0) {
				StringBuilder errorMsg = new StringBuilder();
				for (ErrorMsgBean errorMap : errorList) {
					errorMsg.append(errorMap.getMsg()).append("<br/>");
				}
				// 如果有一条数据校验失败，则需要数据回滚。
				throw new RuntimeException(errorMsg.toString());
			}
			int rowCount = 0;
			if (!CollectionUtils.isEmpty(saveList)) {
				for (HrStaffImport saveBean : saveList) {
					saveData(saveBean, true);
				}
				rowCount += saveList.size();
			}
			if (!CollectionUtils.isEmpty(updateList)) {
				for (HrStaffImport updateBean : updateList) {
					saveData(updateBean, false);
				}
				rowCount += updateList.size();
			}
			if (!CollectionUtils.isEmpty(fileList)) {
				//附件操作
				for (Map<String, Object> info : fileList) {
					String attStrs = info.get("attStrs") != null ? info.get("attStrs").toString() : null;
					if (attStrs == null) {
						continue;
					}
					HrStaffImport baseModel = info.get("model") == null ? null : (HrStaffImport) info.get("model");
					String attName = attStrs.substring(
							attStrs.lastIndexOf(System.getProperty("file.separator")) + 1,
							attStrs.length());
					File attFile = new File(attStrs);
					FileInputStream fileInputStream = new FileInputStream(attFile);
					getSysAttMainService().addAttachment(baseModel,
							IHrStaffPersonExperienceContractService.FD_ATT_KEY, fileInputStream,
							attFile.getName(), "byte", Double.valueOf(fileInputStream.available()),
							attStrs);
					IOUtils.closeQuietly(fileInputStream);
				}
			}
			if (errorList.size() > 0) {
				StringBuilder errorMsg = new StringBuilder();
				errorMsg.append(ResourceUtil.getString("hrStaff.import.portion.failed", "hr-staff", null, rowCount));
				for (ErrorMsgBean errorMap : errorList) {
					errorMsg.append(errorMap.getMsg()).append("<br/>");
				}
				message = new KmssMessage(errorMsg.toString());
				message.setMessageType(KmssMessage.MESSAGE_ERROR);
				// 记录导入日志
				buildLog(targets, rowCount);
			} else {
				message = new KmssMessage(ResourceUtil.getString("hrStaff.import.success", "hr-staff", null, rowCount));
				message.setMessageType(KmssMessage.MESSAGE_COMMON);
				// 记录导入日志
				buildLog(targets, rowCount);
			}
		} catch (Exception e) {
			if (isRollBack && errorList.size() > 0) {
				throw new RuntimeException(e.getMessage());
			}else {
				throw new RuntimeException(ResourceUtil.getString("hrStaff.import.error", "hr-staff"));
			}
		} finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(inputStream);
		}

		return message;
	}

    /**
     * 合同导入，由多线程改成单线程  参考原来的HrStaffPersonExperienceContractImportThread进行移植
     *
     * @param
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/11/12 10:45 上午
     */
    private void hrStaffPersonExperienceContractImport(Sheet sheet, int i, Map<String, SysDictCommonProperty> map, String[] headers,
                                                       String modelName, int breakFlag, List<HrStaffImport> saveList, List<HrStaffImport> updateList,
                                                       List<ErrorMsgBean> errorList, Set<HrStaffPersonInfo> targets, List<HrStaffContractType> fdContTypeList,
                                                       List<Map<String, Object>> fileList, boolean isRollBack, Map<String, Integer> contractMap) {
        KmssMessages messages = new KmssMessages();
        int skipColumn = 3;
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
                    personInfo = getHrStaffPersonInfoService().findPersonInfoByLoginNameAndNo((String) value, fdNo);
                } else if (j == 2 && personInfo == null) { // 第二列为工号，如果通过登录账号没有找到人员，则需要再通过工号继续查找
                    value = ImportUtil.getCellValue(row.getCell(j));
                    if (StringUtil.isNull((String) value)) {
                        continue;
                    }
                    hasLoginNameOrStaffNo = true;
                    personInfo = getHrStaffPersonInfoService().findPersonInfoByStaffNo((String) value);
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
                            if (!isRollBack) {
                                HrStaffImport importModel = this.getByPersonInfo(personInfo.getFdId());
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
                        if ("fdEndDate".equals(fdField)) {
                            value = ImportUtil.getCellValue(row.getCell(j));
                            if ("长期有效".equals(value)){
                                value = Boolean.TRUE;
                                fdField = "fdIsLongtermContract";
                            }
                        }
                        SysDictCommonProperty property = map.get(fdField);
                        if (!"fdIsLongtermContract".equals(fdField)) {
                            value = ImportUtil.getCellValue(row.getCell(j), property, null);
                        }
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
                                value = "1";
                            } else if ("正常".equals(value)) {
                                value = "1";
                            } else if ("已解除".equals(value)) {
                                value = "3";
                            } else if ("已到期".equals(value)) {
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
                                                        new Object[]{attDisables, attStrs[q]})));
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
                                                        new Object[]{attDisables, attStrs[q]})));
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
                                Map<String, Object> info = new HashMap<String, Object>();
                                info.put("attStrs", attStrs[k]);
                                info.put("model", baseModel);
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
            // 合同重复，就不进行导入
            if(personInfo != null){
                List<HrStaffPersonExperienceContract> list = findByContract(baseModel, personInfo);
                if (list != null && !list.isEmpty()) {
                    messages.addError(new KmssMessage(ResourceUtil.getString("hr-staff:hrStaff.import.error.contract.repeat")));
                } else {
                    HrStaffPersonExperienceContract experienceContract = (HrStaffPersonExperienceContract) baseModel;
                    if (StringUtil.isNotNull(experienceContract.getFdContType())
                            && StringUtil.isNotNull(experienceContract.getFdName()) && experienceContract.getFdBeginDate() != null
                            && (Boolean.TRUE.equals(experienceContract.getFdIsLongtermContract()) || experienceContract.getFdEndDate() != null)) {
                        StringBuilder key = new StringBuilder();
                        key.append(personInfo.getFdLoginName()).append("_")
                           .append(personInfo.getFdStaffNo()).append("_")
                           .append(experienceContract.getFdContType()).append("_")
                           .append(experienceContract.getFdName()).append("_")
                           .append(DateUtil.convertDateToString(experienceContract.getFdBeginDate(), DateUtil.PATTERN_DATE)).append("_");
                        if (Boolean.TRUE.equals(experienceContract.getFdIsLongtermContract())) {
                            key.append("true");
                        } else {
                            key.append(DateUtil.convertDateToString(experienceContract.getFdEndDate(), DateUtil.PATTERN_DATE));
                        }
                        Integer haveData = contractMap.get(key.toString());
                        if (haveData == null) {
                            contractMap.put(key.toString(), i);
                        } else {
                            if (haveData != i) {
                                // 不是同一行，存在重复，提示错误
                                messages.addError(new KmssMessage(ResourceUtil.getString("hrStaff.import.error.contract.repeat1", "hr-staff", null, haveData)));
                            }
                        }
                    }
                }
            }
            // 如果有错误，就不进行导入
            if (!messages.hasError()) {
                if (isNew) {
                    saveList.add(baseModel);
                } else {
                    updateList.add(baseModel);
                }
                targets.add(personInfo);
            } else {
                StringBuilder errorMsg = new StringBuilder();
                errorMsg.append(ResourceUtil.getString("hrStaff.import.error.num", "hr-staff", null, i));
                // 解析错误信息
                for (KmssMessage message : messages.getMessages()) {
                    errorMsg.append(message.getMessageKey());
                }
                ErrorMsgBean errorBean = new ErrorMsgBean();
                errorBean.setNum(i);
                errorBean.setMsg(errorMsg.toString());
                errorList.add(errorBean);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            ErrorMsgBean errorBean = new ErrorMsgBean();
            errorBean.setNum(i);
            errorBean.setMsg(e.getMessage());
            errorList.add(errorBean);
        }
    }

    /**
     * 查询合同
     * @param baseModel
     * @param personInfo
     * @return
     * @throws Exception
     */
    private List<HrStaffPersonExperienceContract> findByContract(HrStaffImport baseModel, HrStaffPersonInfo personInfo) throws Exception {
        HrStaffPersonExperienceContract experienceContract = (HrStaffPersonExperienceContract) baseModel;
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("(hrStaffPersonExperienceContract.fdPersonInfo.fdLoginName = :fdLoginName or hrStaffPersonExperienceContract.fdPersonInfo.fdStaffNo = :fdStaffNo)"
                + " and hrStaffPersonExperienceContract.fdContType = :fdContType and hrStaffPersonExperienceContract.fdName = :fdName"
                + " and hrStaffPersonExperienceContract.fdBeginDate = :fdBeginDate and (hrStaffPersonExperienceContract.fdEndDate = :fdEndDate"
                + " or hrStaffPersonExperienceContract.fdIsLongtermContract = :fdIsLongtermContract)" );
        hqlInfo.setParameter("fdLoginName", personInfo.getFdLoginName());
        hqlInfo.setParameter("fdStaffNo", personInfo.getFdStaffNo());
        hqlInfo.setParameter("fdContType", experienceContract.getFdContType());
        hqlInfo.setParameter("fdName", experienceContract.getFdName());
        hqlInfo.setParameter("fdBeginDate", experienceContract.getFdBeginDate());
        hqlInfo.setParameter("fdEndDate", experienceContract.getFdEndDate());
        hqlInfo.setParameter("fdIsLongtermContract", experienceContract.getFdIsLongtermContract());
        return findList(hqlInfo);
    }

	/**
	 * 查询合同列表 根据配置的提醒类型
	 * @param searchDateType
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<HrStaffPersonExperienceContract> findByContract(String searchDateType) throws Exception {
		HQLInfo hqlInfo = getPersonContractHql(searchDateType,null);
		if(hqlInfo ==null){
			return null;
		}
		List<HrStaffPersonExperienceContract> list = findList(hqlInfo);
		if (list != null && !list.isEmpty()) {
			return list;
		} else {
			return null;
		}
	}
	/**
	 * 根据周期查询对应的合同列表、分页查询
	 * @param searchDateType 周期类型
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page findByContractPage(String searchDateType, Date beginDate, int rowSize, int pageNo) throws Exception {
		HQLInfo hqlInfo =getPersonContractHql(searchDateType,beginDate);
		if(hqlInfo ==null){
			return null;
		}
		hqlInfo.setPageNo(pageNo);
		hqlInfo.setRowSize(rowSize);
		hqlInfo.setOrderBy("hrStaffPersonExperienceContract.fdEndDate");
		StringBuffer whereBlock =new StringBuffer(hqlInfo.getWhereBlock());
		HrStaffAlertWarningContract warningBirthday=new HrStaffAlertWarningContract();
		if("true".equals(warningBirthday.getCerifyAuthorization())){
			whereBlock = HrStaffAuthorityUtil.builtWhereBlock(whereBlock, "hrStaffPersonExperienceContract", hqlInfo);
			hqlInfo.setWhereBlock(whereBlock.toString());
		}else{
			hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
		}

		return findPage(hqlInfo);
	}

	/**
	 * 根据配置的提醒类型，转换成日期的查询 HQLInfo
	 * @param searchDateType
	 * @param beginDate
	 * @return
	 */
	private HQLInfo getPersonContractHql(String searchDateType, Date beginDate){
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setJoinBlock(", HrStaffPersonInfo hrStaffPersonInfo");
		hqlInfo.setWhereBlock(
//				" hrStaffPersonInfo.fdId = hrStaffPersonExperienceContract.fdPersonInfo.fdId and hrStaffPersonExperienceContract.fdEndDate >= :fdEndDateQ and hrStaffPersonExperienceContract.fdEndDate <= :fdEndDateH and hrStaffPersonInfo.fdStatus in ( :trial,:official,:temporary,:trialDelay ) and (hrStaffPersonExperienceContract.fdContStatus is null or hrStaffPersonExperienceContract.fdContStatus = :fdContStatus) and hrStaffPersonExperienceContract.fdIsLongtermContract = false");
	" hrStaffPersonInfo.fdId = hrStaffPersonExperienceContract.fdPersonInfo.fdId and hrStaffPersonExperienceContract.fdEndDate >= :fdEndDateQ and hrStaffPersonExperienceContract.fdEndDate <= :fdEndDateH  and (hrStaffPersonExperienceContract.fdContStatus is null or hrStaffPersonExperienceContract.fdContStatus = :fdContStatus) and hrStaffPersonExperienceContract.fdIsLongtermContract = false");
	if ("week".equals(searchDateType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate==null?HrStaffDateUtil.getTimesWeekmorning():DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getTimesWeeknight());
		} else if ("month".equals(searchDateType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate==null?HrStaffDateUtil.getTimesMonthmorning():DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getTimesMonthnight());

		} else if ("twoMonth".equals(searchDateType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate==null?HrStaffDateUtil.getTimesMonthmorning():DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getTimeLastMonthLast());
		} else if ("quarter".equals(searchDateType)) {
			hqlInfo.setParameter("fdEndDateQ", beginDate==null?HrStaffDateUtil.getFirstDayOfQuarter():DateUtil.removeTime(beginDate).getTime());
			hqlInfo.setParameter("fdEndDateH", HrStaffDateUtil.getLastDayOfQuarter());
		}else{
			return null;
		}
		hqlInfo.setParameter("trial", "trial");
		hqlInfo.setParameter("official", "official");
		hqlInfo.setParameter("temporary", "temporary");
		hqlInfo.setParameter("trialDelay", "trialDelay");
		hqlInfo.setParameter("fdContStatus", "1");
		return hqlInfo;
	}

	@Override
	public String getTypeString() {
		return "合同信息";
	}

	@Override
	public HSSFWorkbook buildTempletWorkBook() throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 定义必填字体效果
		HSSFFont font1 = wb.createFont();
		font1.setBold(true); // 字体增粗

		HSSFFont font2 = wb.createFont();
		font2.setBold(true); // 字体增粗
		font2.setColor(org.apache.poi.ss.usermodel.IndexedColors.RED.index); // 字体颜色
		HSSFCellStyle style = null;
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		List<String> itemNodes = getItemNode();
		// 注意事项
		if (itemNodes != null && !itemNodes.isEmpty()) {
			HSSFSheet sheet2 = wb.createSheet(ResourceUtil.getString("hr-staff:hrStaff.import.sheet2.title"));
			sheet2.setColumnWidth(0, 35 * 80); // 第一列宽度
			sheet2.setColumnWidth(1, 35 * 500); // 第二列宽度
			HSSFRow row2 = null;
			HSSFCell cell2 = null;
			style = getStyle(wb);
			style.setFont(font1);

			row2 = sheet2.createRow((int) 0);
			row2.setHeight((short) (20 * 20));
			cell2 = row2.createCell(0);
			cell2.setCellValue(ResourceUtil.getString("hr-staff:hrStaff.import.sheet2.serial"));
			cell2.setCellStyle(style);

			cell2 = row2.createCell(1);
			cell2.setCellValue(ResourceUtil.getString("hr-staff:hrStaff.import.sheet2.item"));
			cell2.setCellStyle(style);

			// 单元格样式
			HSSFCellStyle style2_1 = wb.createCellStyle();
			style2_1.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平居中
			style2_1.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			HSSFCellStyle style2_2 = wb.createCellStyle();
			style2_2.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
			for (int i = 0; i < itemNodes.size(); i++) {
				row2 = sheet2.createRow((int) (i + 1));
				row2.setHeight((short) (20 * 20));

				cell2 = row2.createCell(0);
				cell2.setCellValue(String.valueOf(i + 1));
				cell2.setCellStyle(style2_1);

				cell2 = row2.createCell(1);
				cell2.setCellValue(itemNodes.get(i));
				cell2.setCellStyle(style2_2);
			}
		}
		HSSFSheet sheet = wb.createSheet(ResourceUtil.getString("hr-staff:hrStaff.import.sheet1.title"));
		sheet.setDefaultColumnWidth(25); // 设置宽度
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
		HSSFRow row = sheet.createRow((int) 0);
		row.setHeight((short) (20 * 20));
		// 第四步，创建单元格
		HSSFCell cell = null;

		// 备注
		HSSFPatriarch patr = sheet.createDrawingPatriarch();

		// 需要跳过前面几个列
		int skipColumn = 3;

		/********** 设置头部内容 **********/
		// 第零列为：组织架构人员编号（非必填）
		cell = row.createCell(0);
		cell.setCellValue(ResourceUtil.getString("hr-staff:hrStaffPersonInfo.template.fdNo"));
		style = getStyle(wb);
		style.setFont(font1);
		cell.setCellStyle(style);
		// 第一列为：登录账号
		cell = row.createCell(1);
		cell.setCellValue(ResourceUtil.getString("hr-staff:hrStaffPersonInfo.fdLoginName"));
		style = getStyle(wb);
		style.setFont(font2);
		cell.setCellStyle(style);
		cell.setCellComment(buildComment(patr, ResourceUtil.getString("hr-staff:hrStaff.import.templet.selectOne")));
		// 第二列为：工号
		cell = row.createCell(2);
		cell.setCellValue(ResourceUtil.getString("hr-staff:hrStaffPersonInfo.fdStaffNo"));
		style = getStyle(wb);
		style.setFont(font2);
		cell.setCellStyle(style);
		cell.setCellComment(buildComment(patr, ResourceUtil.getString("hr-staff:hrStaff.import.templet.selectOne")));
		cell.setCellType(org.apache.poi.ss.usermodel.CellType.STRING);

		Map<String, SysDictCommonProperty> map = SysDataDict.getInstance().getModel(getModelName()).getPropertyMap();
		String[] importFields = getImportFields();
		String val = null;
		StringBuffer comments = null;
		for (int i = 0; i < importFields.length; i++) {
			cell = row.createCell(i + skipColumn);
			val = importFields[i];
			SysDictCommonProperty property = map.get(val);
			style = getStyle(wb);

			comments = new StringBuffer();
			if (property.isNotNull()) {
				// 必填字段，字体设置为红色
				style.setFont(font2);
				comments.append(ResourceUtil.getString("hr-staff:hrStaff.import.templet.required"));
			} else {
				style.setFont(font1);
			}
			String[] fieldComments = getFieldComments();
			// 如果有描述，就增加
			if (fieldComments != null && fieldComments.length > i) {
				if (StringUtil.isNotNull(comments.toString()) && StringUtil.isNotNull(fieldComments[i])) {
					comments.append(", ");
				}
				comments.append(fieldComments[i]);
			}

			if (StringUtil.isNotNull(comments.toString())) {
				cell.setCellComment(buildComment(patr, comments.toString()));
			}

			cell.setCellValue(ResourceUtil.getString(property.getMessageKey()));
			cell.setCellStyle(style);
		}

		// 合同附件
		cell = row.createCell(importFields.length + skipColumn);
		cell.setCellValue(ResourceUtil.getString("hr-staff:hrStaffPersonExperience.contract.autoHashMap"));
		style = getStyle(wb);
		style.setFont(font1);
		cell.setCellStyle(style);
		cell.setCellComment(buildComment(patr,
				ResourceUtil.getString("hr-staff:hrStaffPersonExperience.contract.autoHashMap.import")));

		return wb;
	}

	/**
	 * 获取带背景和居中的样式
	 * 
	 * @param wb
	 * @return
	 */
	private static HSSFCellStyle getStyle(HSSFWorkbook wb) {
		// 单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER); // 水平布局：居中
		style.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER); // 垂直居中
		// 背景色
		style.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.index);

		return style;
	}

	/**
	 * 构建批注
	 * 
	 * @param patr
	 * @param value
	 * @return
	 */
	private static HSSFComment buildComment(HSSFPatriarch patr, String value) {
		// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
		HSSFClientAnchor anrhor = null;
		if (value.length() < 50) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 2, 4);
		} else if (value.length() > 150) {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3, 7);
		} else {
			anrhor = new HSSFClientAnchor(0, 0, 0, 0, (short) 1, 1, (short) 3, 5);
		}
		HSSFComment comment = patr.createComment(anrhor);
		comment.setString(new HSSFRichTextString(value));
		return comment;
	}

	@Override
	public void updateStatus() throws Exception {
		String hql = "update com.landray.kmss.hr.staff.model.HrStaffPersonExperienceContract contract set contract.fdContStatus=:fdContStatus where contract.fdEndDate<:currDate and contract.fdIsLongtermContract = :longtermContract";
		Session session = getBaseDao().getHibernateSession();
		Query query = session.createQuery(hql);
		query.setParameter("fdContStatus", "2");
		query.setParameter("currDate", DateUtil.getDate(0));
        query.setParameter("longtermContract", Boolean.FALSE);
		query.executeUpdate();
	}

	@Override
	public void saveRenewContract(String oldContractId, IExtendForm form, RequestContext requestContext)
			throws Exception {
		// 将旧合同置为已到期
		if (StringUtil.isNotNull(oldContractId)) {
			HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract) this
					.findByPrimaryKey(oldContractId);
			contract.setFdContStatus("2");
			this.update(contract);
		}
		// 添加新合同
		this.add(form, requestContext);

	}

	@Override
	public HrStaffPersonExperienceContract findContractByPersonId(String personId) throws Exception {
		List<HrStaffPersonExperienceContract> list = findContractListByPersonId(personId);
		return (null != list && list.size() > 0) ? list.get(0) : null;
	}

	@Override
	public List<HrStaffPersonExperienceContract> findContractListByPersonId(String personId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("hrStaffPersonExperienceContract.fdPersonInfo.fdId =:fdId");
		hqlInfo.setOrderBy("hrStaffPersonExperienceContract.fdCreateTime desc");
		hqlInfo.setParameter("fdId", personId);
		List<HrStaffPersonExperienceContract> list = this.findList(hqlInfo);
		return list;
	}

    @Override
    public List<HrStaffPersonExperienceContract> findByContract(HrStaffPersonInfo hrStaffPersonInfo, RequestContext request) throws Exception {
        String fdId = request.getParameter("fdId");
        String contType = request.getParameter("fdContType");
        String name = request.getParameter("fdName");
        String beginDate = request.getParameter("fdBeginDate");
        String endDate = request.getParameter("fdEndDate");
        String longtermContract = request.getParameter("fdIsLongtermContract");
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("(hrStaffPersonExperienceContract.fdPersonInfo.fdLoginName = :fdLoginName or hrStaffPersonExperienceContract.fdPersonInfo.fdStaffNo = :fdStaffNo)"
                + " and hrStaffPersonExperienceContract.fdName = :fdName");
        // 获取合同类型
        if (StringUtil.isNotNull(contType)) {
            IHrStaffContractTypeService hrStaffContractTypeService = (IHrStaffContractTypeService) SpringBeanUtil.getBean("hrStaffContractTypeService");
            HrStaffContractType hrStaffContractType = (HrStaffContractType) hrStaffContractTypeService.findByPrimaryKey(contType, null, true);
            if (hrStaffContractType != null) {
                contType = hrStaffContractType.getFdName();
            }
            hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and hrStaffPersonExperienceContract.fdContType = :fdContType");
            hqlInfo.setParameter("fdContType", contType);
        }
        // 长期有效 或 结束时间匹配是否唯一
        if(Boolean.parseBoolean(longtermContract)){
            hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and hrStaffPersonExperienceContract.fdIsLongtermContract = :fdIsLongtermContract");
            hqlInfo.setParameter("fdIsLongtermContract", Boolean.parseBoolean(longtermContract));
        } else {
            hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and hrStaffPersonExperienceContract.fdEndDate = :fdEndDate");
            hqlInfo.setParameter("fdEndDate", DateUtil.convertStringToDate(endDate));
        }
        //开始时间为空获取上份开始时间
        if(StringUtil.isNull(beginDate)){
            HrStaffPersonExperienceContract contract = findContractByPersonId(hrStaffPersonInfo.getFdId());
            if(contract != null){
                hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and hrStaffPersonExperienceContract.fdBeginDate = :fdBeginDate");
                hqlInfo.setParameter("fdBeginDate", contract.getFdBeginDate());
            }
        }else {
            hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and hrStaffPersonExperienceContract.fdBeginDate = :fdBeginDate");
            hqlInfo.setParameter("fdBeginDate", DateUtil.convertStringToDate(beginDate));
        }
        if (StringUtil.isNotNull(fdId)) {
            hqlInfo.setWhereBlock(hqlInfo.getWhereBlock() + " and hrStaffPersonExperienceContract.fdId != :fdId");
            hqlInfo.setParameter("fdId", fdId);
        }
        hqlInfo.setParameter("fdLoginName", hrStaffPersonInfo.getFdLoginName());
        hqlInfo.setParameter("fdStaffNo", hrStaffPersonInfo.getFdStaffNo());
        hqlInfo.setParameter("fdName", name);
        return findList(hqlInfo);
    }

    @Override
	public List<String> getItemNode() {
		List<String> itemNode = super.getItemNode();
        itemNode.add(ResourceUtil.getString("hr-staff:hrStaffPersonExperienceContract.itemNode.3"));
		itemNode.add(ResourceUtil.getString("hr-staff:hrStaffPersonExperienceContract.itemNode.4"));
		itemNode.add(ResourceUtil.getString("hr-staff:hrStaffPersonExperienceContract.itemNode.5"));
		itemNode.add(ResourceUtil.getString("hr-staff:hrStaffPersonExperienceContract.itemNode.6"));
		return itemNode;
	}

	@Override
	public IBaseModel convertFormToModel(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		HrStaffPersonExperienceContract contract = (HrStaffPersonExperienceContract) super.convertFormToModel(form,
				model, requestContext);
		HrStaffContractType contType = contract.getFdStaffContType();
		if (contType != null){
            contract.setFdContType(contType.getFdName());
        }
        HrStaffPersonExperienceContractForm contractForm = (HrStaffPersonExperienceContractForm) form;
        if (StringUtil.isNull(contractForm.getFdIsLongtermContract())){
            contract.setFdIsLongtermContract(Boolean.FALSE);
        }
		return contract;
	}
}
