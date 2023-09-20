package com.landray.kmss.km.archives.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.event.Event_CommonDelete;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.constant.SysNotifyConstant;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.km.archives.forms.KmArchivesMainForm;
import com.landray.kmss.km.archives.interfaces.IFileAddDataService;
import com.landray.kmss.km.archives.model.ErrorRowInfo;
import com.landray.kmss.km.archives.model.ExcelImportResult;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.model.KmArchivesConfig;
import com.landray.kmss.km.archives.model.KmArchivesDense;
import com.landray.kmss.km.archives.model.KmArchivesFileConfig;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.km.archives.model.KmArchivesLibrary;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.model.KmArchivesPeriod;
import com.landray.kmss.km.archives.model.KmArchivesUnit;
import com.landray.kmss.km.archives.service.IKmArchivesBorrowService;
import com.landray.kmss.km.archives.service.IKmArchivesCategoryService;
import com.landray.kmss.km.archives.service.IKmArchivesDenseService;
import com.landray.kmss.km.archives.service.IKmArchivesDetailsService;
import com.landray.kmss.km.archives.service.IKmArchivesFileTemplateService;
import com.landray.kmss.km.archives.service.IKmArchivesLibraryService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesPeriodService;
import com.landray.kmss.km.archives.service.IKmArchivesUnitService;
import com.landray.kmss.km.archives.util.KmArchivesConstant;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.archives.config.SysArchivesConfig;
import com.landray.kmss.sys.archives.interfaces.IArchivesDispDataService;
import com.landray.kmss.sys.archives.model.SysArchivesFileTemplate;
import com.landray.kmss.sys.archives.model.SysArchivesParamModel;
import com.landray.kmss.sys.archives.service.ISysArchivesFileTemplateService;
import com.landray.kmss.sys.archives.service.ISysArchivesSignService;
import com.landray.kmss.sys.archives.util.SysArchivesUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.authentication.background.IBackgroundAuthService;
import com.landray.kmss.sys.authorization.model.SysAuthArea;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.metadata.interfaces.ISysMetadataParser;
import com.landray.kmss.sys.metadata.service.ISysMetadataService;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.notify.interfaces.NotifyContext;
import com.landray.kmss.sys.notify.util.SysNotifyConfigUtil;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.property.model.SysPropertyDefine;
import com.landray.kmss.sys.property.model.SysPropertyReference;
import com.landray.kmss.sys.property.util.SysPropertySetUtil;
import com.landray.kmss.sys.property.util.SysPropertyUtil;
import com.landray.kmss.sys.recycle.util.SysRecycleUtil;
import com.landray.kmss.sys.right.forms.DocAuthForm;
import com.landray.kmss.sys.workflow.engine.WorkflowEngineContext;
import com.landray.kmss.sys.workflow.interfaces.ISysWfProcessSubService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectXML;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.Runner;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.excel.Column;
import com.landray.kmss.util.excel.Sheet;
import com.landray.kmss.util.excel.WorkBook;
import com.sunbor.web.tag.Page;
import edu.emory.mathcs.backport.java.util.Arrays;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StopWatch;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KmArchivesMainServiceImp extends ExtendDataServiceImp
		implements IKmArchivesMainService, IFileAddDataService,
		ApplicationContextAware, IArchivesDispDataService {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(KmArchivesMainServiceImp.class);

    private ISysNumberFlowService sysNumberFlowService;
	// 分类
	private IKmArchivesCategoryService kmArchivesCategoryService;
	public void setKmArchivesCategoryService(
			IKmArchivesCategoryService kmArchivesCategoryService) {
		this.kmArchivesCategoryService = kmArchivesCategoryService;
	}

	// 密级程度
	private IKmArchivesDenseService kmArchivesDenseService;
	public void setKmArchivesDenseService(
			IKmArchivesDenseService kmArchivesDenseService) {
		this.kmArchivesDenseService = kmArchivesDenseService;
	}

	// 所属卷库
	private IKmArchivesLibraryService kmArchivesLibraryService;
	public void setKmArchivesLibraryService(
			IKmArchivesLibraryService kmArchivesLibraryService) {
		this.kmArchivesLibraryService = kmArchivesLibraryService;
	}

	// 保管期限
	private IKmArchivesPeriodService kmArchivesPeriodService;
	public void setKmArchivesPeriodService(
			IKmArchivesPeriodService kmArchivesPeriodService) {
		this.kmArchivesPeriodService = kmArchivesPeriodService;
	}

	// 保管单位
	private IKmArchivesUnitService kmArchivesUnitService;
	public void setKmArchivesUnitService(
			IKmArchivesUnitService kmArchivesUnitService) {
		this.kmArchivesUnitService = kmArchivesUnitService;
	}

	// 组织架构
	private ISysOrgCoreService sysOrgCoreService;
	public void setSysOrgCoreService(
			ISysOrgCoreService sysOrgCoreService) {
		this.sysOrgCoreService = sysOrgCoreService;
	}

	// 附件
	private ISysAttMainCoreInnerService sysAttMainService;
	public void setSysAttMainService(
			ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	// 流程
	private ISysWfProcessSubService sysWfProcessSubService;
	public void setSysWfProcessSubService(
			ISysWfProcessSubService sysWfProcessSubService) {
		this.sysWfProcessSubService = sysWfProcessSubService;
	}

	// 提醒
	private ISysNotifyMainCoreService sysNotifyMainCoreService;
	public void setSysNotifyMainCoreService(
			ISysNotifyMainCoreService sysNotifyMainCoreService) {
		this.sysNotifyMainCoreService = sysNotifyMainCoreService;
	}

	// 借阅
	private IKmArchivesBorrowService kmArchivesBorrowService;
	public void setKmArchivesBorrowService(
			IKmArchivesBorrowService kmArchivesBorrowService) {
		this.kmArchivesBorrowService = kmArchivesBorrowService;
	}

	// 借阅详细
	private IKmArchivesDetailsService kmArchivesDetailsService;
	public void setKmArchivesDetailsService(
			IKmArchivesDetailsService kmArchivesDetailsService) {
		this.kmArchivesDetailsService = kmArchivesDetailsService;
	}

	public IKmArchivesDetailsService getkmArchivesDetailsService() {
		return (IKmArchivesDetailsService) SpringBeanUtil
				.getBean("kmArchivesDetailsService");
	}
	
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public IKmArchivesMainService getKmArchivesMainService() {
		return (IKmArchivesMainService) SpringBeanUtil
				.getBean("kmArchivesMainService");
	}

	public IKmArchivesBorrowService getKmArchivesBorrowService() {
		return (IKmArchivesBorrowService) SpringBeanUtil
				.getBean("kmArchivesBorrowService");
	}

    @Override
	public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof KmArchivesMain) {
            KmArchivesMain kmArchivesMain = (KmArchivesMain) model;
            KmArchivesMainForm kmArchivesMainForm = (KmArchivesMainForm) form;
            if (kmArchivesMain.getDocStatus() == null || kmArchivesMain.getDocStatus().startsWith("1")) {
                if (kmArchivesMainForm.getDocStatus() != null && (kmArchivesMainForm.getDocStatus().startsWith("1") || kmArchivesMainForm.getDocStatus().startsWith("2"))) {
                    kmArchivesMain.setDocStatus(kmArchivesMainForm.getDocStatus());
                }
            }
            if (kmArchivesMain.getDocNumber() == null && (kmArchivesMain.getDocStatus().startsWith("2") || kmArchivesMain.getDocStatus().startsWith("3"))) {
                kmArchivesMain.setDocNumber(sysNumberFlowService.generateFlowNumber(kmArchivesMain));
            }
        }
        return model;
    }

    @Override
	public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        KmArchivesMain kmArchivesMain = new KmArchivesMain();
        kmArchivesMain.setDocCreateTime(new Date());
        kmArchivesMain.setFdDestroyed(Boolean.valueOf("0"));
        kmArchivesMain.setDocCreator(UserUtil.getUser());
        KmArchivesUtil.initModelFromRequest(kmArchivesMain, requestContext);
		String fdCategoryId = requestContext.getParameter("i.docTemplate");
		// #59912 新版本并未指定分类ID，需要从老版本中取
		String originId = requestContext.getParameter("originId");
		if (StringUtil.isNull(fdCategoryId) && StringUtil.isNull(originId)) {
			return null;
		}
		KmArchivesCategory category = null;
		if (StringUtil.isNotNull(fdCategoryId)) {
			category = (KmArchivesCategory) this.kmArchivesCategoryService
					.findByPrimaryKey(fdCategoryId);
		} else if (StringUtil.isNotNull(originId)) {
			KmArchivesMain mainModel = (KmArchivesMain) findByPrimaryKey(
					originId);
			category = mainModel.getDocTemplate();
		}
		kmArchivesMain
				.setExtendFilePath(SysPropertyUtil.getExtendFilePath(category));
        return kmArchivesMain;
    }

    @Override
	public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        KmArchivesMain kmArchivesMain = (KmArchivesMain) model;
        if (kmArchivesMain.getDocTemplate() != null) {
            dispatchCoreService.initFormSetting(form, "kmArchivesMain", kmArchivesMain.getDocTemplate(), "kmArchivesMain", requestContext);
        }
    }

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		KmArchivesMain mainModel = (KmArchivesMain) modelObj;
		mainModel.setFdDestroyed(false);
		return super.add(modelObj);
	}

	@Override
	public void update(IBaseModel modelObj) throws Exception {
		KmArchivesMain mainModel = (KmArchivesMain) modelObj;
		if (!"10".equals(mainModel.getDocStatus()) && mainModel.getFdIsPreFile() != null
				&& mainModel.getFdIsPreFile()) {
			mainModel.setFdIsPreFile(false);
		}
		super.add(modelObj);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		KmArchivesMain mainModel = (KmArchivesMain) modelObj;
		if (mainModel.getDocStatus().charAt(0) >= '3') {
			applicationContext.publishEvent(new Event_CommonDelete(mainModel));
		}
		// 未部署软删除时直接删除档案有关的借阅申请
		if (!SysRecycleUtil
				.isEnableSoftDelete(
						"com.landray.kmss.km.archives.model.KmArchivesMain")) {
			this.kmArchivesBorrowService
					.deleteByArchivesId(mainModel.getFdId());
		}
		super.delete(modelObj);
	}

	@Override
	public void deleteHard(IBaseModel modelObj) throws Exception {
		this.kmArchivesBorrowService.deleteByArchivesId(modelObj.getFdId());
		super.deleteHard(modelObj);
	}

    @Override
	public List<KmArchivesMain> findByDocTemplate(KmArchivesCategory docTemplate) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("kmArchivesMain.docTemplate.fdId=:fdId");
        hqlInfo.setParameter("fdId", docTemplate.getFdId());
        return this.findList(hqlInfo);
    }

    public void setSysNumberFlowService(ISysNumberFlowService sysNumberFlowService) {
        this.sysNumberFlowService = sysNumberFlowService;
    }

	@Override
	public WorkBook buildTemplateWorkbook(HttpServletRequest request)
			throws Exception {
		String[] baseColumns = new String[] {
				getStr("kmArchivesMain.docSubject"), // 档案名称 0
				getStr("kmArchivesMain.docTemplate"), // 所属分类 1
				getStr("kmArchivesMain.fdLibrary"), // 所属卷库 2
				getStr("kmArchivesMain.fdVolumeYear"), // 组卷年度 3
				getStr("kmArchivesMain.fdPeriod"), // 保管期限 4
				getStr("kmArchivesMain.fdUnit"), // 保管单位 5
				getStr("kmArchivesMain.fdStorekeeper"), // 保管员 6
				getStr("kmArchivesMain.fdValidityDate"), // 档案有效期 7
				getStr("kmArchivesMain.fdDenseLevel"), // 密级程度 8
				getStr("kmArchivesMain.fdFileDate"), // 归档日期 9
				getStr("kmArchivesMain.attachement") // 附件 10
		};
		// 必填的列的下标
		Integer[] notNullArr = new Integer[] { 0, 1, 6, 9, 10 };
		List notNullList = Arrays.asList(notNullArr);
		String filename = getStr("kmArchivesMain.import.templateFile");
		WorkBook wb = new WorkBook();
		wb.setLocale(request.getLocale());
		Sheet sheet = new Sheet();
		sheet.setTitle(filename);
		for (int i = 0; i < baseColumns.length; i++) {
			Column col = new Column();
			col.setTitle(baseColumns[i]);
			if (notNullList.contains(i)) {
                col.setRedFont(true);
            }
			sheet.addColumn(col);
		}
		// 备注
		Column col = new Column();
		col.setTitle(getStr("kmArchivesMain.fdRemarks"));
		sheet.addColumn(col);
		// 扩展属性
		String docTemplate = request.getParameter("docTemplate");
		KmArchivesCategory category = (KmArchivesCategory) this.kmArchivesCategoryService
				.findByPrimaryKey(docTemplate);
		if (category != null && category.getSysPropertyTemplate() != null
				&& category.getSysPropertyTemplate()
						.getFdReferences() != null) {
			List<SysPropertyReference> references = category
					.getSysPropertyTemplate()
					.getFdReferences();
			for (SysPropertyReference reference : references) {
				Column column = new Column();
				column.setTitle(reference.getFdDisplayName());
				if (reference.getFdIsNotNull()) {
                    column.setRedFont(true);
                }
				sheet.addColumn(column);
			}
		}
		// 扩展属性结束
		List contentList = new ArrayList();
		// 样例数据
		Object[] objs = new Object[sheet.getColumnList().size()];
		objs[0] = "档案1";
		String cateName = category.getFdName();
		while (category.getFdParent() != null) {
			category = (KmArchivesCategory) category.getFdParent();
			cateName = category.getFdName() + "/" + cateName;
		}
		objs[1] = cateName;
		objs[2] = "卷库1";
		objs[3] = "2018";
		objs[4] = "一年";
		objs[5] = "蓝凌机构";
		objs[6] = "张三";
		objs[7] = "2018-05-05";
		objs[8] = "绝密";
		objs[9] = "2018-04-12";
		objs[10] = "C:/Users/Administrator/Desktop/test.doc";
		contentList.add(objs);
		sheet.setContentList(contentList);
		wb.addSheet(sheet);
		wb.setFilename(filename);
		return wb;
	}

	@Override
	public ExcelImportResult addImportData(InputStream inputStream,
			String docTemplate,
			Locale locale) throws Exception {
		Workbook wb = null;
		org.apache.poi.ss.usermodel.Sheet sheet = null;
		ExcelImportResult result = new ExcelImportResult();
		int columnSize = 12;
		int successCount = 0, failCount = 0;
		KmArchivesCategory category = (KmArchivesCategory) this.kmArchivesCategoryService
				.findByPrimaryKey(docTemplate);
		if (category != null && category.getSysPropertyTemplate() != null
				&& category.getSysPropertyTemplate()
						.getFdReferences() != null) {
			columnSize += category.getSysPropertyTemplate()
					.getFdReferences().size();
		}
		try {
			wb = WorkbookFactory.create(inputStream); // 抽象类创建Workbook 适合excel
			// 2003和2007以上
			sheet = wb.getSheetAt(0);
		} catch (IOException e) {
			result.getOtherErrors().add(e.getMessage());
		}
		try{
			// 数据必须大于columnSize-1列，且不能少于2行
			if (sheet.getLastRowNum() < 1
					|| sheet.getRow(0).getLastCellNum() < columnSize - 1) {
				result.getOtherErrors().add(
						getStr("kmArchivesMain.import.template.fileError"));
			} else {
				for (int i = 1; i <= sheet.getLastRowNum(); i++) {
					Row row = sheet.getRow(i);
					int rowIndex = i + 1;
					result.setRowIndex(rowIndex);
					ErrorRowInfo errorRow = null;
					// 行不为空
					if (row == null) {
						continue;
					}
					// 每列都是空的行跳过
					int j = 0;
					for (; j < columnSize; j++) {
                        if (StringUtil.isNotNull(
                                KmArchivesUtil.getCellValue(row.getCell(j)))) {
                            break;
                        }
                    }
					if (j == columnSize) {
                        continue;
                    }
					try {
						// 解决内部调用importRowData，事务不生效（导入多行数据，其中一条数据导入异常不影响其余数据导入）
						errorRow = getKmArchivesMainService().importRowData(
								category, row,
								result, locale);
					} catch (Exception e) {
						result.getOtherErrors().add(e.getMessage());
					}
					// 有错误
					if (errorRow != null && errorRow.isError()) {
						// 当前行的内容
						List<String> contents = new ArrayList<String>();
						for (int k = 0; k < columnSize; k++) {
							String value = KmArchivesUtil
									.getCellValue(row.getCell(k));
							contents.add(value);
						}
						errorRow.setContents(contents);
						result.getErrorRows().add(errorRow);
						failCount++;
					} else {
						if (errorRow == null) {
							failCount++;
						} else {
							if (!errorRow.hasOtherError()) {
								successCount++;
							} else {
								failCount++;
							}
						}
					}
				}
				if (result.isError()) { // 有错误
					List<String> titles = new ArrayList<String>();
					titles.add(getStr("kmArchivesMain.lineNumber")); // 行号
					titles.add(getStr("kmArchivesMain.docSubject"));
					titles.add(getStr("kmArchivesMain.docTemplate"));
					titles.add(getStr("kmArchivesMain.fdLibrary"));
					titles.add(getStr("kmArchivesMain.fdVolumeYear"));
					titles.add(getStr("kmArchivesMain.fdPeriod"));
					titles.add(getStr("kmArchivesMain.fdUnit"));
					titles.add(getStr("kmArchivesMain.fdStorekeeper"));
					titles.add(getStr("kmArchivesMain.fdValidityDate"));
					titles.add(getStr("kmArchivesMain.fdDenseLevel"));
					titles.add(getStr("kmArchivesMain.fdFileDate"));
					titles.add(getStr("kmArchivesMain.attachement"));
					titles.add(getStr("kmArchivesMain.fdRemarks"));
					// 扩展属性
					if (category != null
							&& category.getSysPropertyTemplate() != null
							&& category.getSysPropertyTemplate()
							.getFdReferences() != null) {
						List<SysPropertyReference> references = category
								.getSysPropertyTemplate()
								.getFdReferences();
						for (int k = 0; k < references.size(); k++) {
							titles.add(references.get(k).getFdDisplayName());
						}
					}
					// 扩展属性结束
					titles.add(getStr("kmArchivesMain.errorDetails")); // 错误详情
					result.setTitles(titles);
					String importMsg = ResourceUtil.getString(
							"kmArchivesMain.import.format.msg", "km-archives",
							locale, new Object[] { successCount, failCount });
					result.setImportMsg(importMsg);
				} else { // 无错误
					String importMsg = ResourceUtil.getString(
							"kmArchivesMain.import.format.msg.succ", "km-archives",
							locale, new Object[] { successCount });
					result.setImportMsg(importMsg);
				}
			}
			return result;
		} finally {
			if (wb != null){
				wb.close();
			}
		}
	}

	@Override
	public ErrorRowInfo importRowData(KmArchivesCategory category, Row row,
									  ExcelImportResult result,
									  Locale locale) throws Exception {
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		ErrorRowInfo errorRow = new ErrorRowInfo();
		int rowIndex = result.getRowIndex();
		// 档案名称
		String nameValue = KmArchivesUtil.getCellValue(row.getCell(0));
		if (StringUtil.isNotNull(nameValue)) {
			kmArchivesMain.setDocSubject(nameValue);
		} else {
			errorRow.addError(rowIndex, 0, ResourceUtil.getString(
					"kmArchivesMain.import.notnull", "km-archives",
					locale, getStr("kmArchivesMain.docSubject")));
		}
		// 所属分类
		String categoryValue = KmArchivesUtil.getCellValue(row.getCell(1));
		boolean checkCateFlag = true;
		if (StringUtil.isNotNull(categoryValue)) {
			String[] categories = categoryValue.split("/");
			String parentCateId = null;
			for (int k = 0; k < categories.length; k++) {
				HQLInfo hqlInfo = new HQLInfo();
				String cateWhere = "kmArchivesCategory.fdName = :fdName";
				hqlInfo.setParameter("fdName", categories[k]);
				if (StringUtil.isNotNull(parentCateId)) {
					cateWhere += " and kmArchivesCategory.hbmParent.fdId = :fdParentId";
					hqlInfo.setParameter("fdParentId", parentCateId);
				} else {
					cateWhere += " and kmArchivesCategory.hbmParent is null";
				}
				hqlInfo.setWhereBlock(cateWhere);

//				List<KmArchivesCategory> list = kmArchivesCategoryService
//						.findList(hqlInfo);
				Object one = kmArchivesCategoryService.findFirstOne(hqlInfo);
//				if (list == null || list.size() == 0) {
				if(one == null){
					errorRow.addError(rowIndex, 1,
							ResourceUtil.getString(
									"kmArchivesMain.import.notfind",
									"km-archives",
									locale,
									getStr("kmArchivesMain.docTemplate")));
					checkCateFlag = false;
				} else {
					if (k == categories.length - 1) {
						kmArchivesMain.setDocTemplate((KmArchivesCategory) one);
//						kmArchivesMain.setDocTemplate(list.get(0));
					} else {
//						parentCateId = list.get(0).getFdId();
						parentCateId = ((KmArchivesCategory)one).getFdId();
					}
				}
			}
		} else {
			errorRow.addError(rowIndex, 1, ResourceUtil.getString(
					"kmArchivesMain.import.notnull", "km-archives",
					locale, getStr("kmArchivesMain.docTemplate")));
			checkCateFlag = false;
		}
		// 判断填写分类与所选分类是否一致
		if(checkCateFlag && !category.getFdId().equals(kmArchivesMain.getDocTemplate().getFdId())){
			errorRow.addError(rowIndex, 1, ResourceUtil.getString(
					"kmArchivesMain.import.cateDiff", "km-archives"));
		}
		// 所属卷库
		String libraryValue = KmArchivesUtil
				.getCellValue(row.getCell(2));
		if (StringUtil.isNotNull(libraryValue)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmArchivesLibrary.fdName = :fdName");
			hqlInfo.setParameter("fdName", libraryValue);
			List<KmArchivesLibrary> list = kmArchivesLibraryService
					.findList(hqlInfo);
			if (list == null || list.size() == 0) {
				errorRow.addError(rowIndex, 2, ResourceUtil.getString(
						"kmArchivesMain.import.notfind", "km-archives",
						locale, getStr("kmArchivesMain.fdLibrary")));
			} else {
				kmArchivesMain.setFdLibrary(libraryValue);
			}
		}
		// 组卷年度
		String volumeYearValue = KmArchivesUtil
				.getCellValue(row.getCell(3));
		if (StringUtil.isNotNull(volumeYearValue)) {
			try {
				int temp = Integer.parseInt(volumeYearValue);
				kmArchivesMain.setFdVolumeYear(volumeYearValue);
			} catch (NumberFormatException e) {
				errorRow.addError(rowIndex, 3,
						ResourceUtil.getString(
								"kmArchivesMain.import.mustNumber",
								"km-archives",
								locale,
								getStr("kmArchivesMain.fdVolumeYear")));
			}
		}
		// 保管期限
		String periodValue = KmArchivesUtil
				.getCellValue(row.getCell(4));
		if (StringUtil.isNotNull(periodValue)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmArchivesPeriod.fdName = :fdName");
			hqlInfo.setParameter("fdName", periodValue);
//			List<KmArchivesPeriod> list = kmArchivesPeriodService
//					.findList(hqlInfo);
			Object one = kmArchivesPeriodService.findFirstOne(hqlInfo);
			if(one == null){
//			if (list == null || list.size() == 0) {
				errorRow.addError(rowIndex, 4, ResourceUtil.getString(
						"kmArchivesMain.import.notfind", "km-archives",
						locale, getStr("kmArchivesMain.fdPeriod")));
			} else {
				kmArchivesMain
						.setFdPeriod(((KmArchivesPeriod)one).getFdSaveLife().toString());
			}
		}

		// 保管单位
		String unitValue = KmArchivesUtil.getCellValue(row.getCell(5));
		if (StringUtil.isNotNull(unitValue)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmArchivesUnit.fdName = :fdName");
			hqlInfo.setParameter("fdName", unitValue);
			List<KmArchivesUnit> list = kmArchivesUnitService
					.findList(hqlInfo);
			if (list == null || list.size() == 0) {
				errorRow.addError(rowIndex, 5, ResourceUtil.getString(
						"kmArchivesMain.import.notfind", "km-archives",
						locale, getStr("kmArchivesMain.fdUnit")));
			} else {
				kmArchivesMain.setFdUnit(unitValue);
			}
		}

		// 保管员
		String keeperValue = KmArchivesUtil
				.getCellValue(row.getCell(6));
		if (StringUtil.isNotNull(keeperValue)) {
			List<SysOrgElement> list = sysOrgCoreService.findByName(keeperValue, SysOrgConstant.ORG_TYPE_POSTORPERSON);
			if (list == null || list.size() == 0) {
				errorRow.addError(rowIndex, 6,
						ResourceUtil.getString(
								"kmArchivesMain.import.notfind",
								"km-archives",
								locale,
								getStr("kmArchivesMain.fdStorekeeper")));
			} else {
				kmArchivesMain.setFdStorekeeper(list.get(0));
			}
		} else {
			errorRow.addError(rowIndex, 6, ResourceUtil.getString(
					"kmArchivesMain.import.notnull", "km-archives",
					locale, getStr("kmArchivesMain.fdStorekeeper")));
		}
		// 档案有效期
		String validityValue = KmArchivesUtil
				.getCellValue(row.getCell(7));
		if (StringUtil.isNotNull(validityValue)) {
			try {
				Date date = DateUtil.convertStringToDate(
						validityValue, DateUtil.PATTERN_DATE);
				kmArchivesMain.setFdValidityDate(date);
			} catch (Exception e) {
				errorRow.addError(rowIndex, 7,
						ResourceUtil.getString(
								"kmArchivesMain.import.mustDate",
								"km-archives",
								locale,
								getStr("kmArchivesMain.fdValidityDate")));
			}
		}
		// 密级程度
		String denseValue = KmArchivesUtil.getCellValue(row.getCell(8));
		if (StringUtil.isNotNull(denseValue)) {
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo.setWhereBlock(
					"kmArchivesDense.fdName = :fdName");
			hqlInfo.setParameter("fdName", denseValue);
//			List<KmArchivesDense> list = kmArchivesDenseService
//					.findList(hqlInfo);
			Object one = kmArchivesDenseService.findFirstOne(hqlInfo);
			if(one == null){
//			if (list == null || list.size() == 0) {
				errorRow.addError(rowIndex, 8, ResourceUtil.getString(
						"kmArchivesMain.import.notfind", "km-archives",
						locale, getStr("kmArchivesMain.fdDenseLevel")));
			} else {
				kmArchivesMain.setFdDenseLevel(denseValue);
				kmArchivesMain.setFdDense((KmArchivesDense) one);
			}
		}
		// 归档日期
		String fileDateValue = KmArchivesUtil
				.getCellValue(row.getCell(9));
		if (StringUtil.isNotNull(fileDateValue)) {
			try {
				Date date = DateUtil.convertStringToDate(
						fileDateValue, DateUtil.PATTERN_DATE);
				kmArchivesMain.setFdFileDate(date);
			} catch (Exception e) {
				errorRow.addError(rowIndex, 9,
						ResourceUtil.getString(
								"kmArchivesMain.import.mustDate",
								"km-archives",
								locale,
								getStr("kmArchivesMain.fdFileDate")));
			}
		} else {
			errorRow.addError(rowIndex, 9, ResourceUtil.getString(
					"kmArchivesMain.import.notnull", "km-archives",
					locale, getStr("kmArchivesMain.fdFileDate")));
		}
		// 附件路径
		String attPathValue = KmArchivesUtil
				.getCellValue(row.getCell(10));
		if (StringUtil.isNotNull(attPathValue)) {
			setAttachment(errorRow, rowIndex, attPathValue,
					kmArchivesMain);
		} else {
			errorRow.addError(rowIndex, 10, ResourceUtil.getString(
					"kmArchivesMain.import.notnull", "km-archives",
					locale, getStr("kmArchivesMain.attachement")));
		}
		// 备注
		String remarkValue = KmArchivesUtil
				.getCellValue(row.getCell(11));
		if (remarkValue != null) {
            kmArchivesMain.setFdRemarks(remarkValue);
        }
		// 扩展属性
		if (category != null
				&& category.getSysPropertyTemplate() != null
				&& category.getSysPropertyTemplate()
						.getFdReferences() != null) {
			List<SysPropertyReference> references = category
					.getSysPropertyTemplate()
					.getFdReferences();
			Map<String, Object> modelData = new HashMap<String, Object>();
			for (int k = 12; k < references.size() + 12; k++) {
				String propValue = KmArchivesUtil
						.getCellValue(row.getCell(k));
				SysPropertyReference reference = references.get(k - 12);
				if (StringUtil.isNotNull(propValue)) {
					if (checkPropertyValue(reference.getFdDefine(),
							propValue)) {
						Map map = SysPropertySetUtil.setValue(
								category.getSysPropertyTemplate(),
								reference.getFdDisplayName(), propValue);
						if (map != null) {
                            modelData.putAll(map);
                        }
					} else {
						errorRow.addError(rowIndex, k,
								ResourceUtil.getString(
										"kmArchivesMain.import.prop.notInc",
										"km-archives", locale,
										reference.getFdDisplayName()));
					}
				} else {
					if (reference.getFdIsNotNull()) {
						errorRow.addError(rowIndex, k,
								ResourceUtil.getString(
										"kmArchivesMain.import.notnull",
										"km-archives", locale,
										reference.getFdDisplayName()));
					}
				}
			}
			kmArchivesMain.setExtendFilePath(
					SysPropertyUtil.getExtendFilePath(category));
			kmArchivesMain.setExtendDataXML(
					ObjectXML.objectXmlEncoder(modelData));
		}
		// 扩展属性 结束
		if (!errorRow.isError()) {
			try {
				setModelValue(kmArchivesMain);
				add(kmArchivesMain);
				kmArchivesMain.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
				kmArchivesMain.getSysWfBusinessModel()
						.setCanStartProcess("true");
				WorkflowEngineContext subContext = sysWfProcessSubService
						.init(kmArchivesMain, "kmArchivesMain",
								kmArchivesMain.getDocTemplate(),
								"kmArchivesMain");
				sysWfProcessSubService.doAction(subContext,
						kmArchivesMain);

			} catch (Exception e) {
				errorRow.setOtherError(true);
				result.getOtherErrors().add(e.getMessage());
			}
		}
		return errorRow;
	}

	/**
	 * 校验属性（单选，多选，下拉列表）的值正确性
	 * 
	 * @param define
	 * @param propValue
	 * @return
	 */
	private boolean checkPropertyValue(SysPropertyDefine define,
			String propValue) {
		Map map = define.getFdParamMap();
		if ("radio".equals(define.getFdDisplayType())
				|| "select".equals(define.getFdDisplayType())) {
			Map<String, String> options = SysPropertyUtil
					.getOptionMap(map.get("fd_options")
							.toString());
			if (!options.containsValue(propValue)) {
				return false;
			}
		} else if ("checkbox".equals(define.getFdDisplayType())) {
			Map<String, String> options = SysPropertyUtil
					.getOptionMap(map.get("fd_options")
							.toString());
			String[] values = propValue.split("[;；,，]");
			for (String value : values) {
				if (!options.containsValue(value)) {
					return false;
				}
			}
		}
		return true;
	}

	private void setModelValue(KmArchivesMain kmArchivesMain) throws Exception {
		if (kmArchivesMain.getDocCreateTime() == null) {
			kmArchivesMain.setDocCreateTime(new Date());
		}
		if (kmArchivesMain.getDocCreator() == null) {
			kmArchivesMain.setDocCreator(UserUtil.getUser());
		}
		if (kmArchivesMain.getFdFileDate() == null) {
			kmArchivesMain.setFdFileDate(new Date());
		}

		// 设置编号
		kmArchivesMain.setDocNumber(
				sysNumberFlowService.generateFlowNumber(kmArchivesMain));
		// 设置删除标志为“未删除”
		kmArchivesMain.setDocDeleteFlag(0);
		recalculateEditorField(kmArchivesMain, kmArchivesMain.getDocTemplate());
	}

	private void recalculateEditorField(KmArchivesMain mainModel,
			KmArchivesCategory category) {
		mainModel.setAuthRBPFlag(category.getAuthRBPFlag());
		if (category.getAuthTmpReaders() != null) {
            mainModel.getAuthReaders().addAll(new ArrayList(category.getAuthTmpReaders()));
        }

		// 分类的可维护者默认不继承到档案的可编辑者
		// mainModel.setAuthEditorFlag(category.getAuthEditorFlag());
		// if (category.getAuthAllEditors() != null) {
		// mainModel.getAuthAllEditors().addAll(new
		// ArrayList(category.getAuthAllEditors()));
		// }
		// if (category.getAuthEditors() != null) {
		// mainModel.getAuthEditors().addAll(new
		// ArrayList(category.getAuthEditors()));
		// }
		// if (category.getAuthOtherEditors() != null)
		// mainModel.getAuthOtherEditors().addAll(new
		// ArrayList(category.getAuthOtherEditors()));
	}

	@Override
	public String replaceCharacter(String str) throws Exception {
		str = str.replaceAll("\\\\n|\\\\r|\\\\r\\\\n", "<br>") // 将内容中的换行符替换，避免前台JSON解析出错
				.replaceAll("'", "‘") // 将内容中的'替换成‘
				.replaceAll("\\\\", "\\\\\\\\"); // 将\替换成\\
		return str;
	}

	private String getStr(String key) {
		return ResourceUtil.getString(key, "km-archives");
	}

	/**
	 * 
	 * 设置附件
	 * 
	 */
	private void setAttachment(ErrorRowInfo errorRow, int rowIndex, String path,
			KmArchivesMain mainModel) throws Exception {
		String[] attStrs = path.split("[;；]");
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
			if(StringUtil.isNotNull(ResourceUtil.getKmssConfigString("sys.att.fileLimitType"))){
				fileLimitType = ResourceUtil.getKmssConfigString("sys.att.fileLimitType");
			
				if(ResourceUtil.getKmssConfigString("sys.att.disabledFileType")!=null) {
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
					if("1".equals(fileLimitType)){
						Boolean isPass = true;
						for(String f : files){
							if(_fileType.equals(f)){
								isPass = false;
								break;
							}
						}
						if(!isPass){
							errorRow.addError(rowIndex, 10,
									ResourceUtil.getString(
											"kmArchivesMain.import.attachement.typeNotAllow",
											"km-archives",
											null,
											new Object[] { attDisables,
													attStrs[q] }));
							return;
						}
					}else if("2".equals(fileLimitType)){
						
						Boolean isPass = false;
						for(String f : files){
							if(_fileType.equals(f)){
								isPass = true;
								break;
							}
						}
						if(!isPass){
							errorRow.addError(rowIndex, 10,
									ResourceUtil.getString(
											"kmArchivesMain.import.attachement.typeOnlyAllow",
											"km-archives",
											null,
											new Object[] { attDisables,
													attStrs[q] }));
							return;
						}
					}
				}
			}
			if (fileNames.contains(attFile.getName())) {
				errorRow.addError(rowIndex, 10,
						ResourceUtil.getString(
								"kmArchivesMain.import.attachement.nameNotAllow",
								"km-archives",
								null, attStrs[q]));
				return;
			} else {
				fileNames.add(attFile.getName());
			}
			if (attFile == null || !attFile.exists()) {
				retunStr = retunStr + attStrs[q] + ";";
			}
		}
		if (retunStr.length() > 0) {
			errorRow.addError(rowIndex, 10,
					ResourceUtil.getString(
							"kmArchivesMain.import.attachement.fileNotExist",
							"km-archives",
							null, retunStr));
			return;
		}
		for (int k = 0; k < attStrs.length; k++) {
			if (StringUtil.isNull(attStrs[k])) {
                continue;
            }
			File attFile = new File(attStrs[k]);
			if (attFile != null) {
				FileInputStream fileInputStream = null;
				try {
					fileInputStream = new FileInputStream(attFile);
					sysAttMainService.addAttachment(mainModel,
							IKmArchivesMainService.FD_ATT_KEY,
							fileInputStream, attFile.getName(), "byte",
							Double.valueOf(fileInputStream.available()),
							attStrs[k]);
				} finally {
					IOUtils.closeQuietly(fileInputStream);
				}
			}
		}
	}

	@Override
	public void sendExpireWarn() throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		try {
			KmArchivesConfig config = new KmArchivesConfig();
			int fdSoonExpireDate = 0; // 即将过期提醒天数
			if (StringUtil.isNotNull(config.getFdSoonExpireDate())) {
				fdSoonExpireDate = Integer
						.parseInt(config.getFdSoonExpireDate());
			}
			if (fdSoonExpireDate > 0) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DATE, fdSoonExpireDate);
				String whereBlock = " (kmArchivesMain.fdValidityDate is not null) and (kmArchivesMain.fdValidityDate > :minValidityDate and kmArchivesMain.fdValidityDate < :maxValidityDate) and (kmArchivesMain.docStatus like :docStatus)";
				hqlInfo.setParameter("minValidityDate", new Date());
				hqlInfo.setParameter("maxValidityDate", cal.getTime());
				hqlInfo.setParameter("docStatus", "3" + "%");
				hqlInfo.setWhereBlock(whereBlock);
				// 需要提醒的档案
				List<KmArchivesMain> list = findList(hqlInfo);
				NotifyContext notifyContext = null;
				if (list != null && list.size() > 0) {
					for (KmArchivesMain kmArchivesMain : list) {
						// 给保管员发送通知
						List listNotify = new ArrayList();
						notifyContext = sysNotifyMainCoreService.getContext(
								"km-archives:kmArchivesMain.expireNotify");
						listNotify.add(kmArchivesMain.getFdStorekeeper());
						notifyContext.setNotifyTarget(listNotify);
						notifyContext.setFlag(
										SysNotifyConstant.NOTIFY_TODOTYPE_ONCE);
						// 通知方式
						String notifyType = config.getFdDefaultRemind();
						if (StringUtil.isNull(notifyType)) {
							notifyType = SysNotifyConfigUtil
									.getNotifyDefaultValue();
						}
						notifyContext.setNotifyType(notifyType);
						sysNotifyMainCoreService.send(kmArchivesMain,
								notifyContext,
								getReplaceMap(kmArchivesMain));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private HashMap getReplaceMap(KmArchivesMain kmArchivesMain) {
		HashMap replaceMap = new HashMap();
		replaceMap.put(
				"km-archives:kmArchivesMain.docSubject",
				kmArchivesMain.getDocSubject());
		return replaceMap;
	}

	@Override
	public void addFileModel(IBaseModel model) throws Exception {
		if (model instanceof KmArchivesMain) {
			KmArchivesMain mainModel = (KmArchivesMain) model;
			if (mainModel.getDocTemplate() == null) {
                return;
            }
			setModelValue(mainModel);
			// 设置文档状态
			if (StringUtil.isNull(mainModel.getDocStatus())) {
				mainModel.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
			}
			if (SysDocConstant.DOC_STATUS_DRAFT
					.equals(mainModel.getDocStatus())) { // 归档过来默认为草稿状态
				mainModel.setFdIsPreFile(true);
				mainModel.getSysWfBusinessModel().setCanStartProcess("false"); // 归档过来默认不启动流程，放到预归档库
			} else {
				mainModel.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
				mainModel.setFdIsPreFile(false);
				mainModel.getSysWfBusinessModel().setCanStartProcess("true"); // 归档过来默认不启动流程，放到预归档库
			}
			// add(mainModel);
			IBackgroundAuthService backgroundAuthService = (IBackgroundAuthService)SpringBeanUtil.getBean("backgroundAuthService");

			//做归档缺省值填充
			setArchPerson(mainModel);
			// 切换归档人用户，使得启动流程的创建者与归档人保持一致
			backgroundAuthService.switchUserById(mainModel.getDocCreator().getFdId(),new Runner(){
				@Override
				public Object run(Object mainModel) throws Exception {
					KmArchivesMain model = (KmArchivesMain)mainModel;
					saveFileModel(model);
					if(SysDocConstant.DOC_STATUS_EXAMINE.equals(model.getDocStatus())){
						model.getSysWfBusinessModel().setCanStartProcess("true");
						WorkflowEngineContext subContext = sysWfProcessSubService.init(model, "kmArchivesMain",
								model.getDocTemplate(), "kmArchivesMain");
						sysWfProcessSubService.doAction(subContext, model);
					}
					return null;
				}
			},mainModel);
		}
	}

	/**
	 * 做归档缺省值填充
	 * 手动归档：如果人员无效：取当前用户
	 * 自动归档：如果人员无效：取管理员
	 * @param mainModel
	 * @throws Exception
	 */
	private void setArchPerson(KmArchivesMain mainModel) throws Exception {
		SysOrgPerson docCreator=mainModel.getDocCreator();
		if(docCreator==null||!docCreator.getFdIsAvailable()){
			//如果是匿名用户则说明是自动归档则取管理员身份
			if(UserUtil.getUser().isAnonymous()){
				docCreator = sysOrgCoreService.findByLoginName(ResourceUtil.getKmssConfigString("kmss.admin.loginName"));
			}else{
				docCreator = UserUtil.getUser();
			}
		}
		mainModel.setDocCreator(docCreator);
	}

	/**
	 * 保存文档
	 * 
	 * @param mainModel
	 * @throws Exception
	 */
	private void saveFileModel(KmArchivesMain mainModel) throws Exception {
		ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil.getBean("sysMetadataParser");
		ISysFormTemplateService sysFormTemplateService = (ISysFormTemplateService) SpringBeanUtil
				.getBean("sysFormTemplateService");
		HQLInfo hqlCommon = new HQLInfo();
		hqlCommon.setWhereBlock("fdModelId=:fdModelId and fdModelName=:fdModelName and fdParentForm is null");
		hqlCommon.setParameter("fdModelId", mainModel.getDocTemplate().getFdId());
		hqlCommon.setParameter("fdModelName", KmArchivesCategory.class.getName());

		List<SysFormTemplate> sysFormTemplateValues = sysFormTemplateService.findValue(hqlCommon);
		if (!CollectionUtils.isEmpty(sysFormTemplateValues)) {
			SysFormTemplate sysFormTemplate = sysFormTemplateValues.get(0);
			// 通用表单流程数据
			if (sysFormTemplate.getFdCommonTemplate() != null) {
				mainModel.setExtendFilePath(sysFormTemplateValues.get(0).getFdCommonTemplate().getFdFormFileName());
			} else {
				mainModel.setExtendFilePath(sysFormTemplateValues.get(0).getFdFormFileName());
			}
		}

		SysDictModel dict = sysMetadataParser.getDictModel(mainModel);
		// 初始化表单流程数据
		RequestContext requestContext = getContext(mainModel, dict);
		// 启动流程
		IExtendForm form = initFormSetting(null, requestContext);
		// 归档自定义属性赋值
		Map<String, Object> formData = ((KmArchivesMainForm) form).getExtendDataFormInfo().getFormData();
		formData.putAll(mainModel.getExtendDataModelInfo().getModelData());
		// 保存
		add(form, requestContext);
	}

	/**
	 * 初始化主文档及流程表单数据
	 */
	private RequestContext getContext(KmArchivesMain mainModel, SysDictModel dict) throws Exception {
		RequestContext requestContext = new RequestContext();
		if (mainModel.getDocTemplate() != null) {
			requestContext.setParameter("docTemplateId", mainModel.getDocTemplate().getFdId());
			requestContext.setParameter("i.docTemplate", mainModel.getDocTemplate().getFdId());
		}
		requestContext.setParameter("fdId", mainModel.getFdId());

		Map<String, Object> values = new HashMap<String, Object>();
		requestContext.setAttribute(ISysMetadataService.INIT_MODELDATA_KEY, values);
		// 默认为草稿状态
		if (StringUtil.isNull(mainModel.getDocStatus())) {
			mainModel.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
		}

		values.put("fdLibrary", mainModel.getFdLibrary());
		values.put("fdVolumeYear", mainModel.getFdVolumeYear());
		values.put("fdPeriod", mainModel.getFdPeriod());
		values.put("fdUnit", mainModel.getFdUnit());
		values.put("fdStorekeeper", mainModel.getFdStorekeeper());
		values.put("fdValidityDate", mainModel.getFdValidityDate());
		values.put("fdDenseLevel", mainModel.getFdDenseLevel());

		values.put("docStatus", mainModel.getDocStatus());
		values.put("docSubject", mainModel.getDocSubject());
		values.put("fdModelId", mainModel.getFdModelId());
		values.put("fdModelName", mainModel.getFdModelName());
		values.put("docNumber", mainModel.getDocNumber());
		values.put("fdFileDate", mainModel.getFdFileDate());
		values.put("fdIsPreFile", mainModel.getFdIsPreFile());
		if (mainModel.getDocCreator() != null) {
			values.put("docCreator", mainModel.getDocCreator());
		} else {
			values.put("docCreator", UserUtil.getUser());
		}

		SysAuthArea sysAuthArea = mainModel.getAuthArea();
		if (sysAuthArea != null && sysAuthArea.getFdName() != null) {
			values.put("authArea", sysAuthArea);
		}
		values.put("fdPrintState", mainModel.getFdPrintState());

		return requestContext;
	}

	@Override
	public boolean validateArchives(String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setSelectBlock("kmArchivesDetails.fdId");
		hqlInfo.setWhereBlock(
				"kmArchivesDetails.fdArchives.fdId = :fdId and kmArchivesDetails.fdStatus = :fdStatus");
		hqlInfo.setParameter("fdId", fdId);
		hqlInfo.setParameter("fdStatus",
				KmArchivesConstant.BORROW_STATUS_LOANING);
		List list = this.kmArchivesDetailsService.findValue(hqlInfo);
		return ArrayUtil.isEmpty(list);
	}

	@Override
	public String getCount(String type, Boolean isDraft) throws Exception {
		String count = "0";
		HQLInfo hql = new HQLInfo();
		if ("draft".equals(type)) {
			String whereBlock = null; // " kmArchivesMain.docCreator.fdId=:createorId";
			if (isDraft) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" kmArchivesMain.docStatus=:docStatus and kmArchivesMain.fdDestroyed=:fdDestroyed ");
				// 查询只显示最新的版本
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						" kmArchivesMain.docIsNewVersion=:isNewVersion and (kmArchivesMain.fdIsPreFile !=:fdIsPreFile or kmArchivesMain.fdIsPreFile is null) ");
				hql.setParameter("docStatus", "10");
				hql.setParameter("fdDestroyed", false);
				hql.setParameter("isNewVersion", true);
				hql.setParameter("fdIsPreFile", true);
			}
			// hql.setParameter("createorId", UserUtil.getUser().getFdId());
			hql.setWhereBlock(whereBlock);
		} else if ("approved".equals(type)) {
			LbpmUtil.buildLimitBlockForMyApproved("kmArchivesMain", hql,
					UserUtil.getUser().getFdId());
			hql.setAuthCheckType(null);
		} else {
			hql.setWhereBlock(" 1=1 ");
		}
		Page page = this.findPage(hql);
		count = Integer.toString(page.getTotalrows());
		return count;
	}

	@Override
	public JSONArray getArchivesMobileIndex() throws Exception {
		JSONArray ja = new JSONArray();
		JSONObject json = null;
		JSONObject json1 = null;
		HQLInfo hql = null;
		List list = null;

		json = new JSONObject();// 待我审的档案
		json1 = new JSONObject();// 待审档案
		json.put("name", "approvalMain");
		json.put("count", "0");
		json1.put("name", "approvalMainRemind");
		json1.put("count", "0");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		String whereBlock = hql.getWhereBlock();
		whereBlock = "kmArchivesMain.fdDestroyed =  '0' and kmArchivesMain.docIsNewVersion = '1'";
		hql.setWhereBlock(whereBlock);
		SysFlowUtil.buildLimitBlockForMyApproval("kmArchivesMain", hql);
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		list = getKmArchivesMainService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
			json1.put("count", list.get(0).toString());
		}
		ja.add(json);
		ja.add(json1);

		json = new JSONObject();// 我已审的档案
		json.put("name", "approvedMain");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		whereBlock = "kmArchivesMain.fdDestroyed =  '0' and kmArchivesMain.docIsNewVersion = '1'";
		hql.setWhereBlock(whereBlock);
		SysFlowUtil.buildLimitBlockForMyApproved("kmArchivesMain", hql);
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		list = getKmArchivesMainService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
		}
		ja.add(json);

		json = new JSONObject();// 待我审的借阅
		json1 = new JSONObject();// 待审借阅
		json.put("name", "approvalBorrow");
		json.put("count", "0");
		json1.put("name", "approvalBorrowRemind");
		json1.put("count", "0");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		SysFlowUtil.buildLimitBlockForMyApproval("kmArchivesBorrow",
				hql);
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		list = getKmArchivesBorrowService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
			json1.put("count", list.get(0).toString());
		}
		ja.add(json);
		ja.add(json1);

		json = new JSONObject();// 我已审的借阅
		json.put("name", "approvedBorrow");
		json.put("count", "0");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		SysFlowUtil.buildLimitBlockForMyApproved("kmArchivesBorrow", hql);
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);
		list = getKmArchivesBorrowService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
		}
		ja.add(json);

		json = new JSONObject();// 档案借阅
		json.put("name", "myMainRemind");
		json.put("count", "0");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		whereBlock = "kmArchivesDetails.fdBorrower = :fdBorrower and kmArchivesDetails.fdStatus!=0";
		hql.setParameter("fdBorrower", UserUtil.getKMSSUser().getPerson());
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		hql.setWhereBlock(whereBlock);
		list = getkmArchivesDetailsService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
		}
		ja.add(json);

		json = new JSONObject();// 我的借阅
		json.put("name", "myBorrow");
		json.put("count", "0");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		whereBlock = "kmArchivesBorrow.fdBorrower.fdId=:createorId";
		whereBlock = StringUtil.linkString(whereBlock, " and ",
				"(kmArchivesBorrow.docStatus = '30' or kmArchivesBorrow.docStatus = '20')");
		hql.setParameter("createorId", UserUtil.getUser().getFdId());
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		hql.setWhereBlock(whereBlock);
		list = getKmArchivesBorrowService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
		}
		ja.add(json);

		json = new JSONObject();// 档案库
		json.put("name", "main");
		json.put("count", "0");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		whereBlock = "kmArchivesMain.fdDestroyed =  '0' and kmArchivesMain.docIsNewVersion = '1'";
		hql.setWhereBlock(whereBlock);
		list = getKmArchivesMainService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
		}
		ja.add(json);

		json = new JSONObject();// 我的录入
		json.put("name", "myMain");
		json.put("count", "0");
		hql = new HQLInfo();
		hql.setGettingCount(true);
		whereBlock = "kmArchivesMain.docCreator.fdId=:createorId and kmArchivesMain.fdDestroyed =  '0' and kmArchivesMain.docIsNewVersion = '1'";
		hql.setParameter("createorId", UserUtil.getUser().getFdId());
		hql.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.DEFAULT);
		hql.setWhereBlock(whereBlock);
		list = getKmArchivesMainService().findValue(hql);
		if (list.size() > 0) {
			json.put("count", list.get(0).toString());
		}
		ja.add(json);

		return ja;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateDocRight(KmArchivesMainForm mainForm, String[] fdIds,
			String oprType) throws Exception {
		for (int i = 0; i < fdIds.length; i++) {
			KmArchivesMain model = (KmArchivesMain) findByPrimaryKey(fdIds[i]);
			List<String> beforeReaders = new ArrayList<>();
			List<String> afterReaders = new ArrayList<>();
			String[] nowAuthIds = mainForm.getAuthFileReaderIds().split("[;；]");
			List<SysOrgElement> readers = model.getAuthFileReaders();
			if (!ArrayUtil.isEmpty(readers)) {
				for (int j = 0; j < readers.size(); j++) {
					beforeReaders.add(readers.get(j).getFdId());
					afterReaders.add(readers.get(j).getFdId());
				}
			}
			if (DocAuthForm.OPR_ADD.equals(oprType)) {
				if (beforeReaders.size() > 0) {
					for (int j = 0; j < nowAuthIds.length; j++) {
						if (!afterReaders.contains(nowAuthIds[j])) {
							afterReaders.add(nowAuthIds[j]);
						}
					}
				} else {
					afterReaders.addAll(Arrays.asList(nowAuthIds));
				}
			} else if (DocAuthForm.OPR_RESET.equals(oprType)) {
				afterReaders = new ArrayList<>();
				afterReaders.addAll(Arrays.asList(nowAuthIds));
			} else if (DocAuthForm.OPR_DELETE.equals(oprType)) {
				if (beforeReaders.size() > 0) {
					for (int j = 0; j < nowAuthIds.length; j++) {
						if (afterReaders.contains(nowAuthIds[j])) {
							afterReaders.remove(nowAuthIds[j]);
						}
					}
				}
			}
			if (!ArrayUtil.isListSame(beforeReaders, afterReaders)) {
				List<SysOrgElement> elements = sysOrgCoreService
						.findByPrimaryKeys(ArrayUtil
								.toStringArray(afterReaders.toArray()));
				model.setAuthFileReaders(elements);
				update(model);
			}
		}
	}

	@Override
	public void updatePreFiles(String[] fdIds) throws Exception {
		for (int i = 0; i < fdIds.length; i++) {
			updatePreFile(fdIds[i]);
		}
	}

	@Override
	public void updatePreFile(String fdId) throws Exception {
		KmArchivesMain kmArchivesMain = (KmArchivesMain) this.findByPrimaryKey(fdId);
		if (kmArchivesMain != null && SysDocConstant.DOC_STATUS_DRAFT.equals(kmArchivesMain.getDocStatus())
				&& StringUtil.isNotNull(kmArchivesMain.getFdModelId())
				&& StringUtil.isNotNull(kmArchivesMain.getFdModelName())) {
			updatePreFile(kmArchivesMain);
		}
	}

	public void updatePreFile(KmArchivesMain kmArchivesMain) throws Exception {
		kmArchivesMain.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		kmArchivesMain.setFdIsPreFile(Boolean.FALSE);
		sysWfProcessSubService.init(kmArchivesMain, "kmArchivesMain", kmArchivesMain.getDocTemplate(),
				"kmArchivesMain");
		sysWfProcessSubService.doAction(null, kmArchivesMain);
	}

	@Override
	public void updateChangeCates(String[] fdIds, String cateGoryId) throws Exception {
		for (int i = 0; i < fdIds.length; i++) {
			updateChangeCate(fdIds[i], cateGoryId);
		}
	}

	@Override
	public void updateChangeCate(String fdId, String cateGoryId) throws Exception {
		KmArchivesCategory kmArchivesCategory = (KmArchivesCategory) kmArchivesCategoryService
				.findByPrimaryKey(cateGoryId);
		KmArchivesMain kmArchivesMain = (KmArchivesMain) this.findByPrimaryKey(fdId);
		if (kmArchivesMain != null && kmArchivesCategory != null) {
			kmArchivesMain.setDocTemplate(kmArchivesCategory);
			kmArchivesMain.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
			kmArchivesMain.getSysWfBusinessModel().setCanStartProcess("false");
			recalculateEditorField(kmArchivesMain, kmArchivesMain.getDocTemplate());
			this.update(kmArchivesMain);
		}
	}
	
    /**
     * 获取(我已审)档案审批统计数字
     * @param startTime  统计范围起始时间（可为空）
     * @param endTime 统计范围截至时间（可为空） 
     * @return
     * @throws Exception
     */
	@Override
	public Long getApprovedStatisticalCount(Date startTime, Date endTime) throws Exception {
		Long count = 0L;
		try {
			HQLInfo hql = new HQLInfo();
			hql.setWhereBlock(" 1=1 ");
			hql.setGettingCount(true);
			SysFlowUtil.buildLimitBlockForMyApproved("kmArchivesMain", hql);
			hql.setCheckParam(SysAuthConstant.CheckType.AllCheck, SysAuthConstant.AllCheck.NO);
			StringBuffer whereBlock = new StringBuffer();
			if(startTime!=null || endTime!=null){
				whereBlock.append(" and myWorkitem.fdFinishDate is not null ");
				if(startTime!=null){
					whereBlock.append(" and myWorkitem.fdFinishDate >= :startTime ");
					hql.setParameter("startTime", startTime);
				}
				if(endTime!=null){
					whereBlock.append(" and myWorkitem.fdFinishDate <= :endTime ");
					hql.setParameter("endTime", endTime);
				}
			}
			hql.setWhereBlock(hql.getWhereBlock()+whereBlock.toString());			
	
			List<Long> list = this.getBaseDao().findValue(hql);
			if (list.size() > 0) {
				count = list.get(0);
			} 

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public void updateArchivesMain(KmArchivesMain model) throws Exception {
		super.update(model);
		
	}

	/*---------------新归档机制  - -----------    start ------------------------------*/
	public IKmArchivesFileTemplateService kmArchivesFileTemplateService;
	public IKmArchivesFileTemplateService getKmArchivesFileTemplateService() {
		if (kmArchivesFileTemplateService == null) {
			kmArchivesFileTemplateService = (IKmArchivesFileTemplateService) SpringBeanUtil.getBean("kmArchivesFileTemplateService");
		}
		return kmArchivesFileTemplateService;
	}

	private ISysArchivesSignService sysArchivesSignService;
	public ISysArchivesSignService getSysArchivesSignService() {
		if (sysArchivesSignService == null) {
			sysArchivesSignService = (ISysArchivesSignService) SpringBeanUtil.getBean("sysArchivesSignService");
		}
		return sysArchivesSignService;
	}

	private ISysArchivesFileTemplateService sysArchivesFileTemplateService;
	public ISysArchivesFileTemplateService getSysArchivesFileTemplateService() {
		if (sysArchivesFileTemplateService == null) {
			sysArchivesFileTemplateService = (ISysArchivesFileTemplateService) SpringBeanUtil.getBean(
					"sysArchivesFileTemplateService");
		}
		return sysArchivesFileTemplateService;
	}

	/**
	 * 手动归档
	 * @param request
	 * @param mainModel
	 * @param paramModel
	 * @param fileTemplate
	 * @throws Exception
	 */
	@Override
	public void addArchFileModel(HttpServletRequest request, IBaseModel mainModel, SysArchivesParamModel paramModel, SysArchivesFileTemplate fileTemplate) throws Exception {
		addArchFileModel(request, mainModel, paramModel, fileTemplate, false);
	}

	/**
	 * 自动归档
	 * @param request
	 * @param mainModel
	 * @param paramModel
	 * @param fileTemplate
	 * @param sign
	 * @throws Exception
	 */
	@Override
	public void addArchAutoFileModel(HttpServletRequest request, IBaseModel mainModel, SysArchivesParamModel paramModel, SysArchivesFileTemplate fileTemplate, String sign) throws Exception {
		addArchFileModel(request, mainModel, paramModel, fileTemplate, true);
	}

	private void addArchFileModel(HttpServletRequest request, IBaseModel mainModel, SysArchivesParamModel paramModel, SysArchivesFileTemplate fileTemplate, boolean isAuto) throws Exception {
		// 秒表计时器
		StopWatch stopWatch = null;
		// 是否打印归档详情耗时信息
		boolean isDebugger = false;
		if (logger.isInfoEnabled()) {
			isDebugger = true;
			stopWatch = new StopWatch(mainModel.getClass().getName() + "|" + mainModel.getFdId());
		}
		KmArchivesMain kmArchivesMain = new KmArchivesMain();
		// 原文档异步打印
		kmArchivesMain.setFdPrintState("0");
		if (isDebugger) {
			stopWatch.start("设置基本信息");
		}
		setFileField(kmArchivesMain, fileTemplate, mainModel);
//		if (isDebugger) {
//			stopWatch.stop();
//			stopWatch.start("下载原文档HTML页面（异步下载）");
//		}
//		if (isAuto) {
//			getKmArchivesFileTemplateService().setFilePrintArchivesPage(kmArchivesMain, paramModel.getUrl(), paramModel.getFileName());
//		} else {
//			getKmArchivesFileTemplateService().setFilePrintPage(kmArchivesMain, request, paramModel.getUrl(), paramModel.getFileName());
//		}

		if (isDebugger) {
			stopWatch.stop();
			stopWatch.start("添加原文档主附件");
		}
		getKmArchivesFileTemplateService().setFileAttachement(kmArchivesMain, mainModel);
		if (isDebugger) {
			stopWatch.stop();
			stopWatch.start("添加原文档其他附件");
		}
		addOtherAtt(kmArchivesMain, paramModel);
		if (isDebugger) {
			stopWatch.stop();
			stopWatch.start("创建新归档文档");
		}
		addFileModel(kmArchivesMain);
		if (isAuto) {
			getSysArchivesSignService().deleteSign(mainModel.getFdId());
		}

		// 异步下载原文档HTML
		Map<String, String> params = new HashMap<>();
		// request信息
		params.put("cookie", request.getHeader("Cookie"));
		params.put("serverName", request.getServerName());
		params.put("serverPort", request.getServerPort() + "");
		params.put("servletPath", request.getServletPath());
		params.put("SESSIONID", request.getRequestedSessionId());
		params.put("lang", ResourceUtil.getLocaleStringByUser(request));
		params.put("contextPath", request.getContextPath());
		// 其它信息
		params.put("mainModelId", kmArchivesMain.getFdId());
		params.put("modelUrl", paramModel.getUrl());
		params.put("fileName", paramModel.getFileName());
		params.put("isAuto", isAuto + "");
		// 异步下载文档
		setFilePrintPage(params);

		if (isDebugger) {
			stopWatch.stop();
			// 日志中打印秒表计时信息
			logger.info("详细耗时情况：{}", stopWatch.prettyPrint());
		}
	}

	// 开启一个线程来处理
	private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

	/**
	 * 异步下载文档
	 *
	 * @param params
	 */
	private void setFilePrintPage(Map<String, String> params) {
		if (logger.isInfoEnabled()) {
			logger.info("启动异步线程下载原文档HTML");
		}
		fixedThreadPool.execute(new Runnable() {
			@Override
			public void run() {
				TransactionStatus status = null;
				KmArchivesMain kmArchivesMain = null;
				IKmArchivesMainService kmArchivesMainService = (IKmArchivesMainService) SpringBeanUtil.getBean("kmArchivesMainService");
				try {
					long start = System.currentTimeMillis();
					if (logger.isInfoEnabled()) {
						logger.info("开启异步线程下载文档内容");
					}
					status = TransactionUtils.beginNewTransaction();
					IKmArchivesFileTemplateService kmArchivesFileTemplateService = (IKmArchivesFileTemplateService) SpringBeanUtil.getBean("kmArchivesFileTemplateService");
					String mainModelId = params.get("mainModelId");
					String modelUrl = params.get("modelUrl");
					String fileName = params.get("fileName");
					kmArchivesMain = (KmArchivesMain) kmArchivesMainService.findByPrimaryKey(mainModelId);
					if ("true".equals(params.get("isAuto"))) {
						kmArchivesFileTemplateService.setFilePrintArchivesPage(kmArchivesMain, modelUrl, fileName);
					} else {
						kmArchivesFileTemplateService.setFilePrintPageZoom(kmArchivesMain, params, modelUrl, fileName, "0.7");
					}
					if (logger.isInfoEnabled()) {
						logger.info("下载文档内容完成，耗时：" + ((System.currentTimeMillis() - start) / 1000.0) + "秒");
					}
					// 下载成功
					kmArchivesMain.setFdPrintState("1");
				} catch (Exception e) {
					logger.error("下载文档失败：", e);
					if (kmArchivesMain != null) {
						// 下载失败
						kmArchivesMain.setFdPrintState("2");
					}
				} finally {
					if (kmArchivesMain != null) {
						try {
							kmArchivesMainService.getBaseDao().update(kmArchivesMain);
						} catch (Exception e) {
							logger.error("更新档案失败：", e);
						}
					}
					TransactionUtils.commit(status);
				}
			}
		});
	}

	/**
	 * 添加其他附件
	 * @param kmArchivesMain
	 * @param paramModel
	 */
	public void addOtherAtt(KmArchivesMain kmArchivesMain,SysArchivesParamModel paramModel)throws Exception{
		if(!ArrayUtil.isEmpty(paramModel.getExtendAttList())){
			for(Object attMain:paramModel.getExtendAttList()){
				if(attMain instanceof  SysAttMain){
					getKmArchivesFileTemplateService().addToPDFSourceAtt(kmArchivesMain,(SysAttMain)attMain);
				}
			}
		}
	}
	@Override
	public IBaseModel findArchivesInfoByModel(String fdModelId, String fdModelName) throws Exception {
		HQLInfo info = new HQLInfo();
		info.setWhereBlock("fdModelId=:fdModelId and fdModelName=:fdModelName");
		info.setParameter("fdModelId", fdModelId);
		info.setParameter("fdModelName", fdModelName);
		List<KmArchivesMain> eopList = (List<KmArchivesMain>)findList(info);
		if(!ArrayUtil.isEmpty(eopList)){
			return eopList.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void tranDataTask() throws Exception {
		//第一步、迁移配置文件
		KmArchivesFileConfig kmConfig = new KmArchivesFileConfig();
		SysArchivesConfig sysConfig = new SysArchivesConfig();
		if(StringUtil.isNotNull(kmConfig.getFdStartFile())&&ModelUtil.isExisted("com.landray.kmss.sys.archives.config.SysArchivesConfig")&&StringUtil.isNull(sysConfig.getSysArchEnabled())){
			String fdModelName = "com.landray.kmss.sys.archives.config.SysArchivesConfig";
			String sysArchEnabled=kmConfig.getFdStartFile();//归档开关
			String kmOrEop="km_arch";//数据归口
			String fdFileModels=kmConfig.getFdFileModels();//模块开关
			String hql1 = "INSERT INTO sys_app_config (fd_id,fd_key,fd_field,fd_value) VALUES ('"+ IDGenerator.generateID()+"', '"+fdModelName+"','sysArchEnabled','"+sysArchEnabled+"')";
			String hql2 = "INSERT INTO sys_app_config (fd_id,fd_key,fd_field,fd_value) VALUES ('"+IDGenerator.generateID()+"', '"+fdModelName+"','kmOrEop','"+kmOrEop+"')";
			String hql3 = "INSERT INTO sys_app_config (fd_id,fd_key,fd_field,fd_value) VALUES ('"+IDGenerator.generateID()+"', '"+fdModelName+"','fdFileModels','"+fdFileModels+"')";
			DataSource dataSource = (DataSource) SpringBeanUtil.getBean("dataSource");
			Connection conn = null;
			PreparedStatement ps1 = null;
			PreparedStatement ps2 = null;
			PreparedStatement ps3 = null;
			try {
				conn = dataSource.getConnection();
				ps1 = conn.prepareStatement(hql1);
				ps2 = conn.prepareStatement(hql2);
				ps3 = conn.prepareStatement(hql3);
				ps1.execute();
				ps2.execute();
				ps3.execute();
			} catch (Exception e) {
				logger.error("归档机制迁移任务出错:", e);
			} finally {
				JdbcUtils.closeStatement(ps1);
				JdbcUtils.closeStatement(ps2);
				JdbcUtils.closeStatement(ps3);
				JdbcUtils.closeConnection(conn);
			}
		}

		//第二步、迁移机制模板数据
		TransactionStatus status = null;
		// 记录开始时间
		long start = System.currentTimeMillis();
		// 每个事务一批处理的记录数
		int batch = 50;
		int count = 0;
		int totalCount = 0;
		int hulueCount = 0;
		List<KmArchivesFileTemplate> kmTempList = getKmArchivesFileTemplateService().findList(new HQLInfo());
		if(!ArrayUtil.isEmpty(kmTempList)) {
			Iterator<KmArchivesFileTemplate> iterator = kmTempList.iterator();
			while (iterator.hasNext()) {
				// 显示开启事务
				if (status == null) {
					status = TransactionUtils.beginNewTransaction();
				}
				try{
					KmArchivesFileTemplate kmTemp = iterator.next();
					String fdModelId = kmTemp.getFdModelId();
					String fdModelName = kmTemp.getFdModelName();
					SysArchivesFileTemplate sysTemp = getSysArchivesFileTemplateService().findTempArchivesInfo(fdModelId, fdModelName);
					if (sysTemp == null) {
						//为空、则说明sys机制未保存数据、将km的数据迁移到sys
						sysTemp = new SysArchivesFileTemplate();
						sysTemp.setFdId(IDGenerator.generateID());
						sysTemp.setFdModelId(fdModelId);
						sysTemp.setFdModelName(fdModelName);
						sysTemp.setFdKey(kmTemp.getFdKey());
						sysTemp.setFdTmpXml(kmTemp.getFdTmpXml());
						sysTemp.setDocCreateTime(kmTemp.getDocCreateTime());
						sysTemp.setDocCreator(kmTemp.getDocCreator());
						sysTemp.setCategoryId(kmTemp.getCategory() == null ? null : kmTemp.getCategory().getFdId());
						sysTemp.setCategoryName(kmTemp.getCategory() == null ? null : kmTemp.getCategory().getFdName());
						sysTemp.setSelectFilePersonType(kmTemp.getSelectFilePersonType());
						sysTemp.setFdFilePerson(kmTemp.getFdFilePerson());
						sysTemp.setFdFilePersonFormula(kmTemp.getFdFilePersonFormula());
						sysTemp.setFdFilePersonFormulaName(kmTemp.getFdFilePersonFormulaName());
						sysTemp.setFdSaveApproval(kmTemp.getFdSaveApproval());
						sysTemp.setFdPreFile(kmTemp.getFdPreFile());
						sysTemp.setFdSaveOldFile(kmTemp.getFdSaveOldFile());
						sysTemp.setDocSubjectMapping(kmTemp.getDocSubjectMapping());
						sysTemp.setFdLibraryMapping(kmTemp.getFdLibraryMapping());
						sysTemp.setFdPeriodMapping(kmTemp.getFdPeriodMapping());
						sysTemp.setFdVolumeYearMapping(kmTemp.getFdVolumeYearMapping());
						sysTemp.setFdUnitMapping(kmTemp.getFdUnitMapping());
						sysTemp.setFdKeeperMapping(kmTemp.getFdKeeperMapping());
						sysTemp.setFdValidityDateMapping(kmTemp.getFdValidityDateMapping());
						sysTemp.setFdDenseLevelMapping(kmTemp.getFdDenseLevelMapping());
						sysTemp.setFdFileDateMapping(kmTemp.getFdFileDateMapping());
						//保存数据
						getSysArchivesFileTemplateService().add(sysTemp);
						count++;
						totalCount++;
					} else {
						//不为空则说明已经保存了数据、不迁移
						hulueCount++;
						continue;
					}
					if (count >= batch || !iterator.hasNext()) {
						getBaseDao().flushHibernateSession();
						getBaseDao().clearHibernateSession();
						TransactionUtils.getTransactionManager().commit(status);
						status = null;
						count = 0;
					}
				} catch (Exception e) {
					if (status != null) {
						TransactionUtils.getTransactionManager().rollback(status);
						status = null;
						count = 0;
					}
					logger.error("迁移归档机制数据时出错:"+e);
			}

			}
		}
		long end = System.currentTimeMillis();
		logger.info("本次迁移归档机制数据--成功:"+totalCount+"条 ; 忽略:"+hulueCount+"条 ; 共耗时:"+(end - start) / 1000+"秒");
	}

	/**
	 * 设置基本信息
	 * @param kmArchivesMain 档案管理
	 * @param fileTemplate 档案机制模板
	 * @param mainModel 业务model
	 * @throws Exception
	 */
	public void setFileField(KmArchivesMain kmArchivesMain,SysArchivesFileTemplate fileTemplate, IBaseModel mainModel)
			throws Exception {
		String fdModelId = mainModel.getFdId();
		String fdModelName = mainModel.getClass().getName();
		kmArchivesMain.setFdModelId(fdModelId);
		if (fdModelName.indexOf("$") > -1) {
			kmArchivesMain.setFdModelName(fdModelName.substring(0, fdModelName.indexOf("$")));
		} else {
			kmArchivesMain.setFdModelName(fdModelName);
		}

		kmArchivesMain.setDocTemplate((KmArchivesCategory)kmArchivesCategoryService.findByPrimaryKey(fileTemplate.getCategoryId()));
		// 归档是否保存旧文件
		int saveOldFile = fileTemplate.getFdSaveOldFile() != null
				&& fileTemplate.getFdSaveOldFile() ? 1 : 0;
		// 预归档
		Boolean fdPreFile = fileTemplate.getFdPreFile() != null && fileTemplate.getFdPreFile() ? true : false;
		if (fdPreFile) {
			kmArchivesMain.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
		} else {
			kmArchivesMain.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
		}

		kmArchivesMain.setFdSaveOldFile(saveOldFile);
		ISysFileConvertClientService sysFileConvertClientService = (ISysFileConvertClientService) SpringBeanUtil
				.getBean("sysFileConvertClientService");
		sysFileConvertClientService.refreshClients(false);
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("converterFullKey = :converterFullKey and avail = :avail ");
		hqlInfo.setParameter("converterFullKey", "toPDF-Aspose");
		hqlInfo.setParameter("avail", Boolean.TRUE);
		List<SysFileConvertClient> findClients=sysFileConvertClientService.findList(hqlInfo);
		int toPdfAlive = findClients.size()>0?1:0;
		kmArchivesMain.setFdPdfAlive(toPdfAlive);
		// 归档人身份
		if ("org".equals(fileTemplate.getSelectFilePersonType())){
			kmArchivesMain.setDocCreator(fileTemplate.getFdFilePerson());
		}else {
			List<SysOrgElement> listArgs = KmArchivesUtil.getFormulaValue(
					mainModel,
					fileTemplate.getFdFilePersonFormula());
			if (listArgs != null && listArgs.size() > 0) {
				SysOrgElement person = listArgs.get(0);
				if (person.getFdOrgType() == SysOrgElement.ORG_TYPE_PERSON) {
					kmArchivesMain.setDocCreator((SysOrgPerson) person);
				} else {
					kmArchivesMain.setDocCreator(UserUtil.getUser());
				}
			}
		}

		// 档案名称
		setField(fileTemplate.getDocSubjectMapping(), mainModel, kmArchivesMain,
				"docSubject");
		if (StringUtil.isNull(kmArchivesMain.getDocSubject())) {
			kmArchivesMain.setDocSubject(getModelSubject(mainModel));
		}
		// 所属卷库
		setField(fileTemplate.getFdLibraryMapping(), mainModel, kmArchivesMain,
				"fdLibrary");
		// 组卷年度
		setField(fileTemplate.getFdVolumeYearMapping(), mainModel,
				kmArchivesMain, "fdVolumeYear");
		// 保管期限
		setField(fileTemplate.getFdPeriodMapping(), mainModel, kmArchivesMain,
				"fdPeriod");
		// 保管单位
		setField(fileTemplate.getFdUnitMapping(), mainModel, kmArchivesMain,
				"fdUnit");
		// 保管员
		setField(fileTemplate.getFdKeeperMapping(), mainModel, kmArchivesMain,
				"fdStorekeeper");
		// 档案有效期
		setField(fileTemplate.getFdValidityDateMapping(), mainModel,
				kmArchivesMain, "fdValidityDate");
		// 密级程度
		setField(fileTemplate.getFdDenseLevelMapping(), mainModel, kmArchivesMain, "fdDenseLevel");
		// 归档日期
		setField(fileTemplate.getFdFileDateMapping(), mainModel, kmArchivesMain,
				"fdFileDate");
		//
		setFileExtendData(kmArchivesMain, fileTemplate, mainModel);
	}

	/**
	 * 给字段赋值
	 * @param fieldName
	 * @param mainModel
	 * @param kmArchivesMain
	 * @param setFieldName
	 * @throws Exception
	 */
	private void setField(String fieldName, IBaseModel mainModel,
						  KmArchivesMain kmArchivesMain, String setFieldName)
			throws Exception {
		Object obj = getValue(mainModel, fieldName);
		// 对表单中配置的保管员做特殊处理
		if ("fdStorekeeper".equals(setFieldName) && obj != null
				&& obj instanceof HashMap) {
			HashMap map = (HashMap) obj;
			if (map.get("id") != null) {
				String personId = (String) map.get("id");
				obj = sysOrgCoreService.findByPrimaryKey(personId);
			}
		}
		if (obj != null) {
			try {
				PropertyUtils.setProperty(kmArchivesMain,
						setFieldName,
						obj);
			} catch (Exception e) {
				logger.warn("归档出错，属性赋值错误！ 域模型："
						+ mainModel.getClass().getName()
						+ "，from：" + fieldName + "，to："
						+ setFieldName + "，value：" + obj);
			}
		}
	}

	/**
	 * 获得主文档的标题
	 * @param mainModel
	 * @return
	 */
	private String getModelSubject(IBaseModel mainModel) {
		String subject = null;
		String modelName = ModelUtil.getModelClassName(mainModel);
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		String fieldName = dict.getDisplayProperty();
		try {
			subject = (String) PropertyUtils.getProperty(mainModel, fieldName);
		} catch (Exception e) {
			logger.error("归档出错：未找到文档标题");
		}
		return subject;
	}

	/**
	 * 获取域模型字段值
	 * @param mainModel
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	private Object getValue(IBaseModel mainModel, String fieldName) throws Exception {
		Object obj = null;
		if (StringUtil.isNotNull(fieldName)) {
			// 主文档的属性
			if (PropertyUtils.isReadable(mainModel, fieldName)) {
				// 主文档数据
				try {
					obj = PropertyUtils.getProperty(mainModel, fieldName);
				} catch (Exception e) {
					logger.warn("归档出错，获取属性值错误！ 域模型："
							+ mainModel.getClass().getName() + "，属性名："
							+ fieldName);
				}
			} else {
				if (mainModel instanceof IExtendDataModel) {
					IExtendDataModel dataModel = (IExtendDataModel) mainModel;
					List list = null;
					try {
						list = ObjectXML.objectXMLDecoderByString(
								dataModel.getExtendDataXML());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (!ArrayUtil.isEmpty(list)) {
						Map<String, Object> map = (Map<String, Object>) list.get(0);
						obj = map.get(fieldName);
					}
				}
			}
		}
		return obj;
	}

	/**
	 * 设置扩展属性
	 * @param kmArchivesMain
	 * @param fileTemplate
	 * @param mainModel
	 * @throws Exception
	 */
	private void setFileExtendData(KmArchivesMain kmArchivesMain,
								   SysArchivesFileTemplate fileTemplate, IBaseModel mainModel)
			throws Exception {
		KmArchivesCategory category = (KmArchivesCategory)kmArchivesCategoryService.findByPrimaryKey(fileTemplate.getCategoryId());
		if (category != null
				&& category.getSysPropertyTemplate() != null
				&& category.getSysPropertyTemplate()
				.getFdReferences() != null) {
			List<SysPropertyReference> references = category
					.getSysPropertyTemplate()
					.getFdReferences();
			Map<String, Object> modelData = new HashMap<String, Object>();
			for (SysPropertyReference reference : references) {
				String key = reference.getFdDefine().getFdStructureName();
				modelData.put(key, getValue(mainModel,
						SysArchivesUtil.getFieldName(fileTemplate, key)));
			}
			kmArchivesMain.setExtendFilePath(
					SysPropertyUtil.getExtendFilePath(category));
			kmArchivesMain.setExtendDataXML(
					ObjectXML.objectXmlEncoder(modelData));
		}

	}
	/*---------------新归档机制  - -----------    end ------------------------------*/
}

