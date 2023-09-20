package com.landray.kmss.eop.basedata.actions;

import com.alibaba.fastjson.JSONArray;
import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.forms.KmssFormFile;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.eop.basedata.forms.EopBasedataMaterialForm;
import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.eop.basedata.model.EopBasedataMateUnit;
import com.landray.kmss.eop.basedata.model.EopBasedataMaterial;
import com.landray.kmss.eop.basedata.model.ImportMaterialBean;
import com.landray.kmss.eop.basedata.service.IEopBasedataMateCateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataMateUnitService;
import com.landray.kmss.eop.basedata.service.IEopBasedataMaterialService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.authorization.constant.ISysAuthConstant;
import com.landray.kmss.sys.authorization.interfaces.SysAuthAreaUtils;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.*;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author wangwh
 * @description:物料action
 * @date 2021/5/7
 */
public class EopBasedataMaterialAction extends ExtendAction {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    private IEopBasedataMaterialService eopBasedataMaterialService;
    
    private IEopBasedataMateCateService eopBasedataMateCateService;

    private IEopBasedataMateUnitService eopBasedataMateUnitService;

    @Override
    public IEopBasedataMaterialService getServiceImp(HttpServletRequest request) {
        if (eopBasedataMaterialService == null) {
            eopBasedataMaterialService = (IEopBasedataMaterialService) getBean("eopBasedataMaterialService");
        }
        return eopBasedataMaterialService;
    }

	public IEopBasedataMateCateService getEopBasedataMateCateService(HttpServletRequest request) {
		if (eopBasedataMateCateService == null) {
			eopBasedataMateCateService = (IEopBasedataMateCateService) getBean("eopBasedataMateCateService");
		}
		return eopBasedataMateCateService;
	}

	public IEopBasedataMateUnitService getEopBasedataMateUnitService(HttpServletRequest request) {
		if (eopBasedataMateUnitService == null) {
			eopBasedataMateUnitService = (IEopBasedataMateUnitService) getBean("eopBasedataMateUnitService");
		}
		return eopBasedataMateUnitService;
	}
    
    @Override
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, EopBasedataMaterial.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        EopBasedataUtil.buildHqlInfoDate(hqlInfo, request, EopBasedataMaterial.class);
        EopBasedataUtil.buildHqlInfoModel(hqlInfo, request);
    }

    @Override
    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EopBasedataMaterialForm eopBasedataMaterialForm = (EopBasedataMaterialForm) super.createNewForm(mapping, form, request, response);
        ((IEopBasedataMaterialService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return eopBasedataMaterialForm;
    }

	/**
	 * 重写系统方法,价格保留两位小数
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@Override
	protected void loadActionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		form.reset(mapping, request);
		IExtendForm rtnForm = null;
		String id = request.getParameter("fdId");
		if (!StringUtil.isNull(id)) {
			IBaseModel model = getServiceImp(request).findByPrimaryKey(id,
					null, true);
			if (model != null) {
				rtnForm = getServiceImp(request).convertModelToForm(
						(IExtendForm) form, model, new RequestContext(request));
				UserOperHelper.logFind(model);
			}
		}
		if (rtnForm == null) {
			throw new NoRecordException();
		}
		DecimalFormat format = new DecimalFormat("0.00");
		EopBasedataMaterialForm myForm = (EopBasedataMaterialForm) form;
		if(StringUtil.isNotNull(myForm.getFdPrice())){
			myForm.setFdPrice(format.format(new BigDecimal(myForm.getFdPrice())));
		}
		request.setAttribute(getFormName(rtnForm, request), rtnForm);
	}

	/**
	 * 打开导入页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importDoc(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return getActionForward("importDoc", mapping, form, request, response);
	}

	/**
	 * excel导入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveExcel(ActionMapping mapping, ActionForm form,
								   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-saveExcel", true, getClass());
		KmssMessages messages = new KmssMessages();
		try{
			EopBasedataMaterialForm eopBasedataMaterialForm = (EopBasedataMaterialForm) form;
			FormFile file = eopBasedataMaterialForm.getFile();
			if(file.getFileSize() == 0){
				request.setAttribute("msg", ResourceUtil.getString("eop-basedata:noFile"));
				return getActionForward("returnInfo", mapping, form, request, response);
			}else{
				POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
				HSSFWorkbook wb = new HSSFWorkbook(fs);
				HSSFSheet sheet = wb.getSheetAt(0);
				int rowNum = sheet.getLastRowNum();
				if (rowNum < 1) {
					request.setAttribute("msg",ResourceUtil.getString("eop-basedata:noData"));
					return getActionForward("returnInfo", mapping, form, request, response);
				}
				List<ImportMaterialBean> importList = new ArrayList<>();
				for (int i = 1; i <= rowNum ; i++) {
					ImportMaterialBean bean = new ImportMaterialBean();
					EopBasedataMaterial material = new EopBasedataMaterial();
					HSSFRow row = sheet.getRow(i);
					//必填
					HSSFCell nameCell = row.getCell(0);
					HSSFCell specsCell = row.getCell(1);
					HSSFCell typeCell = row.getCell(2);
					HSSFCell unitCell = row.getCell(3);
					//非必填
					HSSFCell priceCell = row.getCell(4);
					HSSFCell erpCodeCell = row.getCell(5);
					HSSFCell descCell = row.getCell(6);
					HSSFCell attachmentCell = row.getCell(7);

					if(checkCellNull(nameCell) || checkCellNull(specsCell)
					|| checkCellNull(typeCell) || checkCellNull(unitCell)){
						request.setAttribute("msg",ResourceUtil.getString("eop-basedata:dataMustBeNotNull"));
						return getActionForward("returnInfo", mapping, form, request, response);
					}else{
						material.setFdName(changeToString(nameCell));
						material.setFdSpecs(changeToString(specsCell));

						List<EopBasedataMateCate> typeList = getEopBasedataMateCateService(request).findByName(changeToString(typeCell));
						if(CollectionUtils.isEmpty(typeList)){
							request.setAttribute("msg",ResourceUtil.getString("eop-basedata:material.type.is.not.exist"));
							return getActionForward("returnInfo", mapping, form, request, response);
						}
						material.setFdType(typeList.get(0));
						List<EopBasedataMateUnit> unitList = getEopBasedataMateUnitService(request).findByName(changeToString(unitCell));
						if(CollectionUtils.isEmpty(unitList)){
							request.setAttribute("msg",ResourceUtil.getString("eop-basedata:material.unit.is.not.exist"));
							return getActionForward("returnInfo", mapping, form, request, response);
						}
						material.setFdUnit(unitList.get(0));
					}
					if(!checkCellNull(priceCell)){
						material.setFdPrice(Double.valueOf(changeToString(priceCell)));
					}
					if(!checkCellNull(erpCodeCell)){
						material.setFdErpCode(changeToString(erpCodeCell));
					}
					if(!checkCellNull(descCell)){
						material.setFdRemarks(changeToString(descCell));
					}
					String path = null;
					if(!checkCellNull(attachmentCell)){
						path = changeToString(attachmentCell);
					}
					List<EopBasedataMaterial> existMaterialList = getServiceImp(request).findByNameOrErpCode(material);
					if(!CollectionUtils.isEmpty(existMaterialList)){
						request.setAttribute("msg",ResourceUtil.getString("eop-basedata:material.name.or.erpCode.exist"));
						return getActionForward("returnInfo", mapping, form, request, response);
					}
					material.setFdStatus(0);
					bean.setMaterial(material);
					bean.setPath(path);
					importList.add(bean);
				}
				getServiceImp(request).addImportMaterial(importList);
			}
			UserOperHelper.setOperSuccess(true);
		}catch (Exception e){
			messages.addError(e);
			UserOperHelper.setOperSuccess(false);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(0)
				.save(request);
		TimeCounter.logCurrentTime("Action-saveExcel", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		}
		return getActionForward("success", mapping, form, request, response);
	}

	/**
	 * excel值转换
	 * @param cell
	 * @return
	 * @throws Exception
	 */
	private String changeToString(HSSFCell cell) throws Exception {
		String returnv = null;
		if (cell == null) {
			return returnv;
		}
		switch (cell.getCellType()) {
			case NUMERIC: {
				DecimalFormat df = new DecimalFormat("0.00");
				returnv = df.format(cell.getNumericCellValue());
				break;
			}
			case STRING:
				returnv = cell.getRichStringCellValue().getString();
				break;
			default:
				break;
		}
		return returnv;
	}

	/**
	 * 验证excel的cell是否为空
	 * @param cell
	 * @return
	 */
	private boolean checkCellNull(HSSFCell cell){
		boolean flag = false;
		if(cell == null || "".equals(cell.toString()) || cell.getCellType() == org.apache.poi.ss.usermodel.CellType.BLANK){
			flag = true;
		}
		return flag;
	}

	/**
	 * 在list列表中批量删除选定的多条记录。<br>
	 * 表单中，复选框的域名必须为“List_Selected”，其值为记录id。<br>
	 * 该操作一般以HTTP的POST方式触发。
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 执行成功，返回success页面，否则返回failure页面
	 * @throws Exception
	 */
	@Override
    public ActionForward deleteall(ActionMapping mapping, ActionForm form,
                                   HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-deleteall", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"POST".equals(request.getMethod())) {
				throw new UnexpectedRequestException();
			}
			String[] ids = request.getParameterValues("List_Selected");

			//判断批量物料是否存在于采购需求中，能否被删除
			//为true则查询有值，不可删
			boolean isAllowDelete = getServiceImp(request).isDeleteByRequire(ids);
			if(!isAllowDelete) {
				throw new KmssRuntimeException(new KmssMessage("eop-basedata:related.materials.can.not.delete"));
			}

			if (ISysAuthConstant.IS_AREA_ENABLED) {
				String queryString = "method=delete&fdId=${id}";
				String fdModelName = request.getParameter("fdModelName");
				if(StringUtil.isNotNull(fdModelName))
				{
					queryString += "&fdModelName=" + fdModelName;
				}
				String[] authIds = SysAuthAreaUtils.removeNoAuthIds(ids,
						request, queryString);
				int noAuthIdNum = ids.length - authIds.length;
				if (noAuthIdNum > 0) {
					messages.addMsg(new KmssMessage(
							"sys-authorization:area.batch.operation.info",
							noAuthIdNum));
				}

				if (!ArrayUtils.isEmpty(authIds)) {
					getServiceImp(request).delete(authIds);
				}
			} else if (ids != null) {
				getServiceImp(request).delete(ids);
			}

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException || e instanceof ConstraintViolationException) {
				messages.addError(new KmssMessage("eop-basedata:error.delete.1"),e);
			}else {
				messages.addError(e);
			}
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		TimeCounter.logCurrentTime("Action-deleteall", false, getClass());
		if (messages.hasError()) {
			return getActionForward("failure", mapping, form, request, response);
		} else {
			return getActionForward("success", mapping, form, request, response);
		}
	}

	/**
	 * 导入需求台账信息
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importMaterial(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		TimeCounter.logCurrentTime("Action-importMaterial", true, getClass());
		KmssMessages messages = new KmssMessages();
		com.alibaba.fastjson.JSONObject rtnData = new com.alibaba.fastjson.JSONObject();
		try {
			MultipartHttpServletRequest fileRequest = (MultipartHttpServletRequest) request;
			String repeatOpt = request.getParameter("repeatOpt");
			KmssFormFile excelFile = (KmssFormFile) fileRequest.getFile("dataFile");
			handleExcelFile(excelFile, repeatOpt, rtnData, request);
		} catch (Exception e) {
			messages.addError(e);
			log.error("error", e);
			rtnData.put("error", e.getMessage());
		}
		TimeCounter.logCurrentTime("Action-importMaterial", false, getClass());
		if (messages.hasError()) {
			rtnData.put("success", false);
		} else {
			rtnData.put("success", true);
		}
		request.setAttribute("lui-source", rtnData);
		return mapping.findForward("lui-source");
	}

	/**
	 * 处理excel文件
	 *
	 * @param materialFile
	 * @param repeatOpt
	 * @param rtnData
	 * @param request
	 * @throws Exception
	 */
	private void handleExcelFile(KmssFormFile materialFile, String repeatOpt, com.alibaba.fastjson.JSONObject rtnData,
								 HttpServletRequest request) throws Exception {
		Workbook workbook = WorkbookFactory.create(materialFile.getInputStream());
		JSONArray result = new JSONArray();
		rtnData.put("result", result);
		com.alibaba.fastjson.JSONObject info = new com.alibaba.fastjson.JSONObject();
		info.put("label", ResourceUtil.getString("table.import.material", "eop-basedata"));
		info.put("success", true);
		info.put("repeatOpt", repeatOpt);
		result.add(info);
		handleMaterial(workbook.getSheetAt(0), info, request);
		workbook.close();
	}

	/**
	 * 新增物料
	 *
	 * @param sheet
	 * @param info
	 * @param request
	 * @throws Exception
	 */
	private void handleMaterial(Sheet sheet, com.alibaba.fastjson.JSONObject info, HttpServletRequest request) throws Exception {
		Date nowDate = new Date();
		try {
			confirmCols(sheet, 8);
		} catch (Exception e) {
			info.put("error", e.getMessage());
			info.put("success", false);
			info.put("add", 0);
			info.put("update", 0);
			info.put("errorN", 0);
			info.put("ignore", 0);
			throw e;
		}
		int addCount = 0;
		int updateCount = 0;
		int errorCount = 0;
		int ignoreCount = 0;
		JSONArray detail = new JSONArray();
		info.put("detail", detail);
		com.alibaba.fastjson.JSONObject item = null;

		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			try {
				item = new com.alibaba.fastjson.JSONObject();

				Row row = sheet.getRow(i);
				// 必填
				ImportMaterialBean bean = new ImportMaterialBean();
				EopBasedataMaterial eopBasedataMaterial = new EopBasedataMaterial();
				String label = ResourceUtil.getString("eop-basedata:msg.sheet.row.error");
				label = label.replace("%row%",String.valueOf(i));
				item.put("label", label);

				String materialName = getCellValue(row.getCell(0), String.class, 0);
				assertNull(StringUtil.isNull(materialName), "msg.material.materialName.empty");
				String materialSpecs = getCellValue(row.getCell(1), String.class, 1);
				assertNull(StringUtil.isNull(materialSpecs), "msg.material.materialSpecs.empty");
				String materialType = getCellValue(row.getCell(2), String.class, 2);
				assertNull(StringUtil.isNull(materialType), "msg.material.materialType.empty");
				String materialUnit = getCellValue(row.getCell(3), String.class, 3);
				assertNull(StringUtil.isNull(materialUnit), "msg.material.materialUnit.empty");
				// 非必填
				Double materialPrice = getCellValue(row.getCell(4), Double.class, 4);
				String ERPCode = getCellValue(row.getCell(5), String.class, 5);
				String materialDesc = getCellValue(row.getCell(6), String.class, 6);
				String materialAtt = getCellValue(row.getCell(7), String.class, 7);

				List<EopBasedataMateCate> typeList = getEopBasedataMateCateService(request).findByName(materialType);
				if (CollectionUtils.isEmpty(typeList)) {
					throw new Exception(materialType + ResourceUtil.getString("material.type.is.not.exist", "eop-basedata"));
				}

				List<EopBasedataMateUnit> unitList = getEopBasedataMateUnitService(request).findByName(materialUnit);
				if (CollectionUtils.isEmpty(unitList)) {
					throw new Exception(materialUnit + ResourceUtil.getString("material.unit.is.not.exist", "eop-basedata"));
				}

				List<EopBasedataMaterial> existMaterialList = getServiceImp(request).findByNameOrErpCode(eopBasedataMaterial);
				if (!CollectionUtils.isEmpty(existMaterialList)) {
					throw new Exception(ResourceUtil.getString("material.name.or.erpCode.exist", "eop-basedata"));
				}

				eopBasedataMaterial.setFdName(materialName);
				eopBasedataMaterial.setFdSpecs(materialSpecs);
				eopBasedataMaterial.setFdType(typeList.get(0));
				eopBasedataMaterial.setFdUnit(unitList.get(0));
				eopBasedataMaterial.setFdPrice(materialPrice);
				eopBasedataMaterial.setFdErpCode(ERPCode);
				eopBasedataMaterial.setFdRemarks(materialDesc);
				eopBasedataMaterial.setFdStatus(0);
				bean.setMaterial(eopBasedataMaterial);
				bean.setPath(materialAtt);
				getServiceImp(request).addImportMaterial(bean);
				addCount++;
				item.put("opt", "add");
				item.put("success", true);
			} catch (Exception e) {
				log.error("material_error", e);
				String errorInfo = e.getMessage();
				item.put("error", errorInfo);
				item.put("success", false);
				info.put("success", false);
				errorCount++;
			} finally {
				detail.add(item);
			}
		}
		info.put("add", addCount);
		info.put("update", updateCount);
		info.put("ignore", ignoreCount);
		info.put("errorN", errorCount);
	}

	/**
	 * 判断文件类型，根据文件列数
	 *
	 * @param sheet
	 * @param colNum
	 * @throws Exception
	 */
	private void confirmCols(Sheet sheet, int colNum) throws Exception {
		Row firstRow = sheet.getRow(0);
		if (firstRow.getLastCellNum() != colNum) {
			throw new Exception(ResourceUtil.getString("template.error", "eop-basedata"));
		}
	}

	/**
	 * 获取excel列值
	 *
	 * @param cell
	 * @param rtnType
	 * @param <T>
	 * @return
	 */
	private <T extends Object> T getCellValue(Cell cell, Class<T> rtnType, Integer column) throws Exception {
		T rtn = null;
		if (cell != null) {
			if (Integer.class.equals(rtnType)) {
				try {
					rtn = (T) Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue());
				} catch (Exception e) {
					try {
						rtn = (T) Integer.valueOf(cell.getStringCellValue());
					} catch (Exception e1) {
						buildException(column);
					}
				}
			} else if (Double.class.equals(rtnType)) {
				try {
					rtn = (T) Double.valueOf(cell.getNumericCellValue());
				} catch (Exception e) {
					try {
						rtn = (T) Double.valueOf(cell.getStringCellValue());
					} catch (Exception e1) {
						buildException(column);
					}
				}
			} else if (String.class.equals(rtnType)) {
				try {
					rtn = (T) cell.getStringCellValue();
				} catch (Exception e) {
					try {
						rtn = (T) new DecimalFormat("#").format(Double.valueOf(cell.getNumericCellValue()));
					} catch (Exception e1) {
						buildException(column);
					}
				}
			} else if (Date.class.equals(rtnType)) {
				try {
					rtn = (T) cell.getDateCellValue();
				} catch (Exception e) {
					try {
						rtn = (T) cell.getDateCellValue();
					} catch (Exception e1) {
						buildException(column);
					}
				}

			}
		}
		return rtn;
	}

	/**
	 * 判断是否为空
	 *
	 * @param isnull
	 * @param msgKey
	 * @throws Exception
	 */
	private void assertNull(boolean isnull, String msgKey) throws Exception {
		if (isnull) {
			throw new Exception(ResourceUtil.getString(msgKey, "eop-basedata"));
		}
	}

	/**
	 * 组建excel导入错误
	 *
	 * @param column
	 */
	public void buildException(Integer column) throws Exception {
		String msgKey = null;
		switch (column) {
			case 0:
				msgKey = "msg.material.materialName.error";
				break;
			case 1:
				msgKey = "msg.material.materialSpecs.error";
				break;
			case 2:
				msgKey = "msg.material.materialType.error";
				break;
			case 3:
				msgKey = "msg.material.materialUnit.error";
				break;
			case 4:
				msgKey = "msg.material.price.error";
				break;
			case 5:
				msgKey = "msg.material.ERPCode.error";
				break;
			case 6:
				msgKey = "msg.material.desc.error";
				break;
			case 7:
				msgKey = "msg.material.attachment.error";
				break;
			default:
		}
		throw new Exception(ResourceUtil.getString(msgKey, "eop-basedata"));
	}
	@Override
    public ActionForward update(ActionMapping mapping, ActionForm form,
                                HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EopBasedataMaterialForm eopBasedataMaterialForm = (EopBasedataMaterialForm)form;
		getServiceImp(request).updatePre(eopBasedataMaterialForm);
		return super.update(mapping, form, request, response);
	}
}
