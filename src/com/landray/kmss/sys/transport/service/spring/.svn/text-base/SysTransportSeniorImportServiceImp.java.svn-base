package com.landray.kmss.sys.transport.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.dict.SysDictSimpleProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendElementProperty;
import com.landray.kmss.sys.metadata.dict.SysDictExtendSimpleProperty;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataForm;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.transport.service.ISysTransportImportPropertyParse;
import com.landray.kmss.sys.transport.service.ISysTransportSeniorImportService;
import com.landray.kmss.sys.transport.service.parse.SysTransportImportAddressParse;
import com.landray.kmss.sys.transport.service.parse.SysTransportImportDateParse;
import com.landray.kmss.sys.transport.service.parse.SysTransportImportXformEnumParse;
import com.landray.kmss.sys.xform.base.service.controls.seniorDetailsTable.form.SysFormDetailsTableMainForm;
import com.landray.kmss.sys.xform.base.service.controls.seniorDetailsTable.model.SysFormDetailsTableMain;
import com.landray.kmss.sys.xform.base.service.controls.seniorDetailsTable.spring.SysFormDetailsTableMainServiceImp;
import com.landray.kmss.util.*;
import com.landray.kmss.web.upload.FormFile;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class SysTransportSeniorImportServiceImp extends SysTransportImportServiceImp implements ISysTransportSeniorImportService {
	private ImportContext context;
	private boolean notFoundRes = false;
	protected ApplicationContext applicationContext;

	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

	private final Map renderDataCache =  new HashMap<String,List>();

	private final Map renderPageNumCache =  new HashMap<String,Integer>();

	public SysFormDetailsTableMainServiceImp getSysFormDetailsTableMainService() {
		return (SysFormDetailsTableMainServiceImp) SpringBeanUtil.getBean("sysFormDetailsTableMainServiceTarget");
	}
	/*
	 * 高级明细表内容校验
	 */
	@Override
	public void detailTableValidate(FormFile file, HttpServletRequest request, HttpServletResponse response,
									Locale locale) throws Exception {
		// 处理file
		InputStream input = file.getInputStream();
		String errorResult = "";
		KmssMessages messages = new KmssMessages();
		Workbook wb = null;
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		try {
			wb = WorkbookFactory.create(input); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);
		} catch (IOException e) {
			if (e.getMessage().startsWith("Invalid header signature")) {
				KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file");
				messages.addError(message);
			}
		} catch (OfficeXmlFileException e) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.version.error");
			messages.addError(message);
		}finally {
			IOUtils.closeQuietly(wb);
			IOUtils.closeQuietly(input);
		}
		String isXform = request.getParameter("isXform");
		// 是否需要校验数据的类型，用于导出的Excel导入到明细表，导出的Excel目前没有接口设置单元格的数据类型，导致数字类型和日期类型导入不成功
		String validateType = request.getParameter("validateType");
		boolean validateDataType = true;
		if (StringUtil.isNotNull(validateType) && "false".equalsIgnoreCase(validateType)) {
			validateDataType = false;
		}
		// 业务模块的 modelId
		String modelId = request.getParameter("modelId");
		// 表单的 Id
		String formId = request.getParameter("formId");
		// 高级明细表的id
		String detailId = request.getParameter("detailId");
		// 导入模式
		String importType = request.getParameter("importType");
		// 模板配置的导入模式：关联与非关联导入
		String excelImportType = request.getParameter("excelImportType");

		String maxLimitedNum = request.getParameter("maxLimitedNum");
		String maxRenderNum = request.getParameter("maxRenderNum");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		ImportInDetailsContext detailsContext = new ImportInDetailsContext();
		List propertyList = new ArrayList();
		// 获取明细表各字段属性
		if (StringUtil.isNotNull(isXform) && "true".equalsIgnoreCase(isXform)) {
			// 表单明细表输入字段的数据字典属性
			propertyList = getExtendProperty(detailsContext, request);
		} else {
			// 获取普通模块明细表输入字段的数据字典属性,返回的都是简单类型和对象类型
			propertyList = detailTableGetProperty(request, detailsContext);// 根据页面的字段从数据字典取得对应的属性
		}

		// 标题行校验
		messages.concat(validateTitleRow(propertyList, sheet, request));
		if (messages.hasError()) {
			errorResult = saveExcelError(messages, request);
			response.getWriter()
					.write("<script>parent.showResultTr();parent.changeImportStatus(\"uploadFailure\");</script>");
		} else {
			// 校验内容 start
			// 遍历导入的excel，校验内容
			List excelContentList = new ArrayList();
			List renderDataList = new ArrayList();
			List renderContentList = new ArrayList();
			// 用于存数据库
			KmssMessages contentMessage = this.validateCellByDictProerty(excelContentList, propertyList, renderContentList,sheet,
					detailsContext, validateDataType, request);
			if (excelContentList != null) {
				if (excelContentList.isEmpty()) {
					KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.notNull");
					messages.addError(message);
					errorResult = saveExcelError(messages, request);
					response.getWriter().write(
							"<script>parent.showResultTr();parent.changeImportStatus(\"uploadFailure\");</script>");
				} else {
					if (contentMessage.hasError()) {
						response.getWriter()
								.write("<script>parent.changeImportStatus(\"uploadFailure\");parent.showResultTr();parent.setImportDataAndXform("
										+ JSONArray.fromObject(excelContentList)
										+ ");parent.showContinueImportData();</script>");
					} else {
						Boolean isSuccess = false;
						RequestContext requestContext = new RequestContext(request);

						// 如果配置了关联导入则进行缓存渲染
						if("relevanceImport".equals(excelImportType)){
							// 缓存数据key随机生成，避免重复
							String keyCache = IDGenerator.generateID();
							renderDataCache.put(keyCache,renderContentList);
							renderPageNumCache.put(keyCache,1);
							request.getSession().setAttribute(keyCache,"importing");

							// 如果导入模式是更新，则先清空所有数据再进行保存导入的数据
							if("update".equals(importType)){
								this.getSysFormDetailsTableMainService().deleteAll(formId,detailId,modelId);
								this.addImportData(request,excelContentList);
								renderDataList = this.getExcelRenderPageList(keyCache,maxRenderNum);
							}else if("append".equals(importType)){
								// 导入模式是追加，则先保存数据再添加到缓存进行分页渲染
								this.addImportData(request,excelContentList);
								renderDataList = this.getExcelRenderPageList(keyCache,maxRenderNum);
							}
							// 从java返回list数据到js，一般需要转换成json，不然就是转换成string
							response.getWriter()
									.write("<script>parent.changeImportStatus(\"uploading\");top.DocList_seniorImportData(\""
											+ "TABLE_DL_" + detailId + "\","
											+ "\""+detailId + "\","
											+ JSONArray.fromObject(renderDataList) + "," + isXform + ","
											+ maxRenderNum + ","+ "\""+keyCache + "\""+");</script>");
						}else if("unRelevanceImport".equals(excelImportType)){// 默认配置非关联导入则不需要渲染，直接刷新请求第一页数据
							// 如果导入模式是更新，则先清空所有数据再进行保存导入的数据
							if("update".equals(importType)){
								this.getSysFormDetailsTableMainService().deleteAll(formId,detailId,modelId);
								this.addImportData(request,excelContentList);
							}else if("append".equals(importType)){
								// 导入模式是追加
								this.addImportData(request,excelContentList);
							}
							response.getWriter()
										.write("<script>parent.changeImportStatus(\"uploading\");top.DocList_seniorImportData(\""
												+ "TABLE_DL_" + detailId + "\","
												+ "\""+detailId + "\","
												+ JSONArray.fromObject(renderDataList) + "," + isXform + ","
												+ maxRenderNum + ","+ null + ");</script>");
						}


					}
				}
			} else {
				response.getWriter().write("<script>parent.changeImportStatus(\"uploadFailure\");</script>");
			}
			if (contentMessage.hasError()) {
				errorResult = saveExcelError(contentMessage, request);
			}
			// end
		}
		response.getWriter().write("<script>parent.callback(\'" + errorResult + "\');</script>");

	}
	/*
	 * 获取渲染的分页条目数
	 */
	@Override
	public List getExcelRenderPageList(String key, String maxRenderNum){
		// 存放分页后数据
		List renderDataPageList = new ArrayList();
		Map map = new HashMap<String,Object>();
		int maxSize = Integer.parseInt(maxRenderNum);
		// 缓存中存在此key值则从缓存中取相应页码数据
		if(renderDataCache.containsKey(key) && renderPageNumCache.containsKey(key)){
			Integer renderPageNum =  (Integer)renderPageNumCache.get(key);
			if(renderPageNum!=null && renderPageNum>0){
				List renderDataList = (List) renderDataCache.get(key);
				// 总页数
				int pageSize = renderDataList.size()/ maxSize;
				if(renderDataList.size()<=maxSize){
					pageSize = 1;
				}else if((renderDataList.size())%maxSize>0){
					pageSize+=1;
				}

				map.put("renderPageNum",renderPageNum);
				map.put("pageSize",pageSize);
				map.put("totalCount",renderDataList.size());
				map.put("maxSize",maxSize);
				renderDataPageList.addAll(getPageResult(map,renderDataList));

				// 重新更新缓存中的页码
				renderPageNumCache.put(key,renderPageNum+1);
			}
		}
		return renderDataPageList;
	}
	protected List getPageResult(Map<String,Object> map,List pageList){
		List renderDataPageList = new ArrayList();
		Integer renderPageNum = (Integer)map.get("renderPageNum");
		Integer pageSize = (Integer)map.get("pageSize");
		Integer totalCount = (Integer)map.get("totalCount");
		Integer maxSize = (Integer)map.get("maxSize");
		if(renderPageNum<=pageSize){
			if((renderPageNum-1)*maxSize<=totalCount && (renderPageNum*maxSize)<=totalCount){
				renderDataPageList = pageList.subList((renderPageNum-1)*maxSize,renderPageNum*maxSize);
			}else{
				int fromIndex = (renderPageNum-1)*maxSize;
				int toIndex = fromIndex+totalCount-((renderPageNum-1)*maxSize);
				renderDataPageList = pageList.subList(fromIndex,toIndex);
			}

		}
		return renderDataPageList;
	}
	/*
	 * 清除缓存数据
	 */
	@Override
	public Boolean clearRenderCache(String key){
		// 缓存中存在此key值则从缓存中取相应页码数据
		if(renderDataCache.containsKey(key) && renderPageNumCache.containsKey(key)){
			renderDataCache.remove(key);
			renderPageNumCache.remove(key);
			return true;
		}
		return true;
	}
	/*
	 * 将导入的数据转换成model对象
	 */
	public IExtendDataModel coverListToModel(Object excelContent, String modelName, String modelId, String filePath,String fdKey, String isXform, String detailId, String originDetailId,String formId) throws Exception {
		// 判断是否是表单
		if (StringUtil.isNotNull(isXform) && "true".equalsIgnoreCase(isXform)){
			// 解析成表单的form对象
			SysFormDetailsTableMain detailsTableMain = new SysFormDetailsTableMain();
			Map map = (Map)excelContent;
			List array = new ArrayList<>();
			array.add(map);
			String fdId = (String) map.get("fdId");
			String detailXml = ObjectXML.objectXmlEncoder(excelContent);
			detailsTableMain.setFdOriginControlId(originDetailId);
			detailsTableMain.setFdModelName(modelName);
			detailsTableMain.setFdFormId(formId);
			detailsTableMain.setFdKey(fdKey);
			detailsTableMain.setFdControlId(detailId);
			detailsTableMain.setFdOrder(null);
			detailsTableMain.setFdModelId(modelId);
			detailsTableMain.setExtendDataXML(detailXml);
			detailsTableMain.setFdCreateTime(new Date());
			detailsTableMain.setFdCreator(UserUtil.getUser());
			detailsTableMain.setExtendFilePath(filePath);
			detailsTableMain.setFdId(fdId);
			detailsTableMain.getExtendDataModelInfo().getModelData().put(detailId,array);
			return detailsTableMain;
		}else{
			// 其他业务模块根据modelName进行解析
		}

		return null;
	}
	/*
	 * 将导入的数据转换成model对象
	 */
	public IExtendDataForm coverListToForm(Object excelContent, String modelName, String modelId, String filePath, String fdKey, String isXform, String detailId, String originDetailId, String formId) throws Exception {
		// 判断是否是表单
		if (StringUtil.isNotNull(isXform) && "true".equalsIgnoreCase(isXform)){
			// 解析成表单的form对象
			SysFormDetailsTableMainForm detailsTableMainForm = new SysFormDetailsTableMainForm();
			Map map = (Map)excelContent;
			List array = new ArrayList<>();
			array.add(map);
			String fdId = (String) map.get("fdId");
			String detailXml = ObjectXML.objectXmlEncoder(excelContent);
			detailsTableMainForm.setFdOriginControlId(originDetailId);
			detailsTableMainForm.setFdModelName(modelName);
			detailsTableMainForm.setFdFormId(formId);
			detailsTableMainForm.setFdKey(fdKey);
			detailsTableMainForm.setFdControlId(detailId);
			detailsTableMainForm.setFdOrder(null);
			detailsTableMainForm.setFdModelId(modelId);
			detailsTableMainForm.setFdId(fdId);
			detailsTableMainForm.setIsImportAdd("true");
			detailsTableMainForm.getExtendDataFormInfo().getFormData().put(detailId,array);
			detailsTableMainForm.getExtendDataFormInfo().setExtendFilePath(filePath);
			return detailsTableMainForm;
		}else{
			// 其他业务模块根据modelName进行解析
		}

		return null;
	}
	 public void addImportData(HttpServletRequest request,List excelContentList) throws Exception{
		 // filepath
		 String filepath = request.getParameter("modelName");
		 // 业务模块的 modelId
		 String modelId = request.getParameter("modelId");
		 // 表单的 Id
		 String formId = request.getParameter("formId");
		 // 业务模块的modelname
		 String mainModelName = request.getParameter("mainModelName");
		 // 业务模块的 fdKey
		 String fdKey = request.getParameter("fdKey");
		 // 高级明细表的id
		 String detailId = request.getParameter("detailId");
		 // 高级明细表唯一标识Id
		 String originDetailId = request.getParameter("originDetailId");
		 String isXform = request.getParameter("isXform");
		 RequestContext requestContext = new RequestContext(request);
		 // 先循环解析控件的list数据
		 for(Object excelContent : excelContentList){
			 IExtendDataForm extendDataForm = coverListToForm(excelContent,mainModelName,modelId,filepath,fdKey,isXform,detailId,originDetailId,formId);
			 JSONObject isSuccess = this.getSysFormDetailsTableMainService().saveOrUpdate(extendDataForm,requestContext);
		 }
	 }

	/**
	 * 高级明细表更新导入的数据
	 *
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateImportData(IExtendForm form, RequestContext requestContext) throws Exception{
		// 先更新数据
		getSysFormDetailsTableMainService().saveOrUpdate(form,requestContext);
	}

	/**
	 *
	 * 通过数据字典校验excel表的单元格内容
	 *
	 * @param excelContentList
	 * @param propertyList
	 * @param sheet
	 * @param validateType
	 * @param request
	 * @param
	 * @param
	 * @return
	 * @throws Exception
	 */
	public KmssMessages validateCellByDictProerty(List excelContentList, List<SysDictCommonProperty> propertyList,
												  List renderContentList,
												  org.apache.poi.ss.usermodel.Sheet sheet, ImportInDetailsContext detailsContext, boolean validateType,
												  HttpServletRequest request) throws Exception {
		KmssMessages contentMessage = new KmssMessages();
		Locale locale = ResourceUtil.getLocaleByUser();
		Map dictComMapToDictSim = detailsContext.getDictComMapToDictSim();
		Map idToType = detailsContext.getIdToType();
		Row titleRow = sheet.getRow(1);// 需用标题行来验证，以免内容行最后几列没有，那就匹配不正确了
		String fileName = request.getParameter("modelName");
		String maxLimitedNum = request.getParameter("maxLimitedNum");
		// 根据列名匹配数据字典
		Map labelToPropertyMap = getLabelToPropertyMap(propertyList, titleRow, fileName);
		// 当没有一列匹配上的时候，返回
		if (labelToPropertyMap.size() <= 0) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.notMatchColum");
			contentMessage.addError(message);
			return contentMessage;
		}
		int lastRowNum = sheet.getLastRowNum();
		// 单次导入可最多只能导入1000条，通过前端传入
		int num = 201;
		if (StringUtil.isNotNull(maxLimitedNum)) {
			num = Integer.valueOf(maxLimitedNum) + 1;
		}
		if (lastRowNum > num) {
			lastRowNum = num;
		}
		// 不支持导入的表单控件
		String[] unsuportedControlArray = { "xform_chinavalue", "xform_calculate", "xform_relation_radio",
				"xform_relation_checkbox", "xform_relation_select", "xform_relation_choose" };
		List unsuportedControls = ArrayUtil.convertArrayToList(unsuportedControlArray);
		// 前两条是提示行和标题行
		for (int i = 2; i <= lastRowNum; i++) {
			Map<String, String> temp = new HashMap<String, String>();
			Map ExcelMap = new HashMap<String,Object>();
			// map的putAll只能进行基本数据的深层复制，对于引用类型的不管用，由于这里是基本数据类型，故不做严格的深层复制
			temp.putAll(detailsContext.getDetailTableMap());
			temp.put("fdCompanyId", request.getParameter("fdCompanyId"));
			temp.put("fdTemplateId", request.getParameter("templateId"));
			// 导入数据是新建所以需要生成一个行的记录fdId
			String fdId = IDGenerator.generateID();
			temp.put("fdId",fdId);
			Row row = sheet.getRow(i);
			if (isEmptyRow(row)) {
				continue;
			}
			for (short j = 0; j < titleRow.getLastCellNum(); j++) {
				// 标题列名
				String titleName = ImportUtil.getCellValue(titleRow.getCell(j));
				if (!labelToPropertyMap.containsKey(titleName)) {
					continue;
				}
				// 根据标题名查找对应的数据字典
				SysDictCommonProperty property = (SysDictCommonProperty) labelToPropertyMap.get(titleName);
				String propertyName = detailsContext.getNamePropertyToNameForm().get(property);
				// 判断列是否支持导入
				if (idToType.containsKey(propertyName)) {
					if (unsuportedControls.indexOf(idToType.get(propertyName)) > -1) {
						if (i == 2) {
							KmssMessage message = new KmssMessage(
									"sys-transport:sysTransport.import.dataError.unsuportedImport",
									SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale));
							contentMessage.addError(message);
						}
						continue;
					}
				}

				Cell cell = row.getCell(j);
				String cellString = ImportUtil.getCellValue(cell);
				// 校验能否为空，为true则不能为空
				if (property.isNotNull()) {
					// 如果为空
					if (cell == null || cell.getCellType() == org.apache.poi.ss.usermodel.CellType.BLANK							|| StringUtil.isNull(cellString)) {
						KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.dataError.null", i + 1,
								SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale));
						contentMessage.addError(message);
						temp.put(propertyName, "");
						continue;
					}
				}
				// 如果单元格为空，则不处理，必填处理在上面
				if (StringUtil.isNull(cellString)) {
					continue;
				}
				// 由于需要传递的参数过多，直接放到一个map里面，方面后续扩展
				ImportInDetailsCellContext detailsCellContext = new ImportInDetailsCellContext();
				detailsCellContext.setDetailsContext(detailsContext);
				detailsCellContext.setProperty(property);
				detailsCellContext.setPropertyName(propertyName);
				detailsCellContext.setCell(cell);
				detailsCellContext.setContentMessage(contentMessage);
				detailsCellContext.setIndex(i);
				detailsCellContext.setTemp(temp);
				// 如果该简单属性是从对象属性中细分而来的,需要再取一个id，因为页面的数据存储的是id
				// 在表单有xformflag标签的之后，由于获取到的字段名不再含有name和id了，所以此处做个兼容
				if (dictComMapToDictSim.containsKey(property) || property instanceof SysDictExtendElementProperty) {
					SysDictCommonProperty modelPro = (SysDictCommonProperty) dictComMapToDictSim.get(property);
					if (modelPro == null) {
						SysDictModel dictModel = SysDataDict.getInstance().getModel(property.getType());
						Map propertyMap = dictModel.getPropertyMap();
						// 由于其他控件写法不规范，很多fdName都写成name了，这里只能先这样写死了
						modelPro = (SysDictCommonProperty) propertyMap.get("fdName");
					}
					detailsCellContext.setModelPro(modelPro);
					// 地址本
					if (property.getType().indexOf("com.landray.kmss.sys.organization") > -1) {
						ISysTransportImportPropertyParse propertyParse = new SysTransportImportAddressParse();
						if (!propertyParse.parse(detailsCellContext)) {
							String propertyId = detailsCellContext.getDetailsContext()
									.getNameToIdMap()
									.get(propertyName);
							if (StringUtil.isNull(propertyId)) {
								propertyId = propertyName + ".id";
								propertyName += ".name";
							}
							String propertyValueId = detailsCellContext.getTemp().get(propertyId);
							String propertyValueName = detailsCellContext.getTemp().get(propertyName);
							Map map = new HashMap<String,Object>();
							map.put("id",propertyValueId);
							map.put("name",propertyValueName);
							ExcelMap.put(property.getName(),map);
							continue;
						}
					}

				} else {
					String[] expControls = {"costCenter","expenseItem","project","vehicle","currency","wbs","innerOrder","area","dayCount","accountMoney"};
					if(Arrays.asList(expControls).contains(idToType.get(property.getName()))) {
						String name = (String) idToType.get(property.getName());
						name = name.substring(0,1).toUpperCase()+name.substring(1);
						ISysTransportImportPropertyParse propertyParse = (ISysTransportImportPropertyParse) Class.forName("com.landray.kmss.fssc.fee.xform.impt.detail.FsscFee"+name+"DetailImportParse").newInstance();
						propertyParse.parse(detailsCellContext);
						continue;
					}
					// 校验是否只能是数字类型而且也不能是枚举型，如果是
					if (validateType && property.isNumber() && !property.isEnum()) {
						// 如果不是数字类型
						if (cell != null && !isNumeric(cellString)) {
							KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.dataError.notNum",
									i + 1, SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale));
							contentMessage.addError(message);
							temp.put(propertyName, "");
							continue;
						}
					}
					int length = 0;
					// 不是所有字段都需要校验长度
					boolean isValidateLength = false;
					if (property instanceof SysDictSimpleProperty) {
						length = ((SysDictSimpleProperty) property).getLength();
						isValidateLength = true;
					} else if (property instanceof SysDictExtendSimpleProperty) {
						length = ((SysDictExtendSimpleProperty) property).getLength();
						isValidateLength = true;
					}

					// 校验是否是日期类型，如果是，日期类型不用校验长度
					if (validateType && property.isDateTime()) {
						// 如果单元不为空
						if (cell != null && (cell.getCellType() != org.apache.poi.ss.usermodel.CellType.BLANK)) {
							ISysTransportImportPropertyParse propertyParse = new SysTransportImportDateParse();
							if (!propertyParse.parse(detailsCellContext)) {
								continue;
							}
							cellString = detailsCellContext.getCellString();
						}
					}

					/*if (validateType && isValidateLength && StringUtil.isNotNull(cellString) && (length != 0)
							&& cellString.getBytes().length > length) {
						KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.dataError.tooLong",
								i + 1, SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale),
								length);
						contentMessage.addError(message);
						temp.put(propertyName, "");
						continue;
					}*/
					// 校验是否是枚举类型，如果是
					if (property.isEnum()) {

					}

					// 表单明细表某些特殊属性判断
					if (property instanceof SysDictExtendSimpleProperty && StringUtil.isNotNull(cellString)) {
						SysDictExtendSimpleProperty extendProperty = (SysDictExtendSimpleProperty) property;
						// 判断是否是数字类型，而且不是枚举类型，有小数位限制
						if (extendProperty.isNumber()
								&& !extendProperty.isEnum()) {
							int scale = 0;
							if (extendProperty.getScale() != -1) {
								scale = extendProperty.getScale();
							}
							//公式类型,并且有小数位限制，则进行自动转换
							/*if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA &&
									scale > 0){
								String templateDecimal = "#################################";
								templateDecimal = "####################." + templateDecimal.substring(0,
										scale);
								cellString = NumberUtil.roundDecimal(cellString, templateDecimal);
							}*/

							if (scale > 0){
								String templateDecimal = "00000000000000000000";
								templateDecimal = "###################0." + templateDecimal.substring(0,
										scale);
								cellString = NumberUtil.roundDecimalPattern(cellString, templateDecimal);
							}

							if (!validateScale(cellString, scale)) {
								KmssMessage message = new KmssMessage(
										"sys-transport:sysTransport.import.dataError.tooLongScale", i + 1,
										SysTransportTableUtil.getSysSimpleOrExtendPropertyMessage(property, locale),
										scale);
								contentMessage.addError(message);
								temp.put(propertyName, "");
								continue;
							}
						}
						// 判断是否是枚举型
						if (extendProperty.isEnum()) {
							ISysTransportImportPropertyParse propertyParse = new SysTransportImportXformEnumParse();
							if (!propertyParse.parse(detailsCellContext)) {
								continue;
							}
						}
					}
				}
				temp.put(propertyName, cellString);
			}
			if (!temp.isEmpty()) {
				ExcelMap.putAll(temp);
				excelContentList.add(ExcelMap);
				renderContentList.add(temp);
			}

		}

		return contentMessage;
	}
	/**
	 * 对excel的标题行进行校验
	 *
	 * @param
	 *
	 * @param
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	@Override
	public KmssMessages validateTitleRow(List propertyList, org.apache.poi.ss.usermodel.Sheet sheet,
										 HttpServletRequest request) throws IOException, Exception {
		KmssMessages messages = new KmssMessages();
		// 判断提示行，如果提示行不正确，则提示有问题
		if (sheet.getLastRowNum() <= 0) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.file.notNull");
			messages.addError(message);
			return messages;
		}
		Row tipRow = sheet.getRow(0);
		String tipString = ImportUtil.getCellValue(tipRow.getCell(0));
		String tip = ResourceUtil.getString("sys-transport:sysTransport.seniorExport.tip", request.getLocale());
		if (!tip.equalsIgnoreCase(tipString)) {
			KmssMessage message = new KmssMessage("sys-transport:sysTransport.import.fileNotTemplate");
			messages.addError(message);
		}
		return messages;
	}





}
