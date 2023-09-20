package com.landray.kmss.km.archives.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseCreateInfoModel;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IDocSubjectModel;
import com.landray.kmss.common.module.core.enhance.IBeanEnhance;
import com.landray.kmss.common.module.core.proxy.IDynamicProxy;
import com.landray.kmss.common.module.core.register.loader.ModuleDictUtil;
import com.landray.kmss.common.module.util.ModuleCenter;
import com.landray.kmss.common.service.BaseCoreInnerServiceImp;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.archives.interfaces.IFileAddDataService;
import com.landray.kmss.km.archives.model.KmArchivesCategory;
import com.landray.kmss.km.archives.model.KmArchivesFileTemplate;
import com.landray.kmss.km.archives.model.KmArchivesMain;
import com.landray.kmss.km.archives.service.IKmArchivesFileTemplateService;
import com.landray.kmss.km.archives.service.IKmArchivesMainService;
import com.landray.kmss.km.archives.service.IKmArchivesSignService;
import com.landray.kmss.km.archives.util.KmArchivesUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.SysAttViewerUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictListProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.filestore.model.SysFileConvertClient;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.service.ISysFileConvertClientService;
import com.landray.kmss.sys.filestore.service.ISysFileConvertQueueService;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.dict.SysDictExtendModel;
import com.landray.kmss.sys.metadata.dict.SysDictExtendProperty;
import com.landray.kmss.sys.metadata.interfaces.IExtendDataModel;
import com.landray.kmss.sys.organization.interfaces.ISysOrgCoreService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.model.SysOrgPost;
import com.landray.kmss.sys.property.model.SysPropertyReference;
import com.landray.kmss.sys.property.util.SysPropertyUtil;
import com.landray.kmss.sys.xform.base.model.SysFormTemplate;
import com.landray.kmss.sys.xform.base.service.ISysFormTemplateService;
import com.landray.kmss.sys.xform.service.DictLoadService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.HtmlToMht;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ObjectXML;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 档案信息
 *
 * @version 1.0
 */
public class KmArchivesFileTemplateServiceImp extends BaseCoreInnerServiceImp
        implements IKmArchivesFileTemplateService {

    private IFileAddDataService service;

    private ISysAttMainCoreInnerService sysAttMainService;

    private ISysFormTemplateService sysFormTemplateService;

    private DictLoadService sysFormDictLoadService;


    // 组织架构
    private ISysOrgCoreService sysOrgCoreService;

    public void setSysOrgCoreService(ISysOrgCoreService sysOrgCoreService) {
        this.sysOrgCoreService = sysOrgCoreService;
    }


    private Logger logger = org.slf4j.LoggerFactory.getLogger(KmArchivesFileTemplateServiceImp.class);

    protected IFileAddDataService getService() {
        if (service == null) {
            Object obj = SpringBeanUtil
                    .getBean("kmArchivesMainService");
            if (obj != null) {
                service = (IFileAddDataService) obj;
            }
        }
        return service;
    }

    public DictLoadService getDictLoadService() {
        if (sysFormDictLoadService == null) {
            sysFormDictLoadService = (DictLoadService) SpringBeanUtil
                    .getBean("sysFormDictLoadService");
        }
        return sysFormDictLoadService;
    }

    protected ISysFileConvertQueueService convertQueueService;

    public ISysFileConvertQueueService getConvertQueueService() {
        if (convertQueueService == null) {
            convertQueueService = (ISysFileConvertQueueService) SpringBeanUtil.getBean("sysFileConvertQueueService");
        }
        return convertQueueService;
    }

    public void setSysAttMainService(
            ISysAttMainCoreInnerService sysAttMainService) {
        this.sysAttMainService = sysAttMainService;
    }

    public void setSysFormTemplateService(
            ISysFormTemplateService sysFormTemplateService) {
        this.sysFormTemplateService = sysFormTemplateService;
    }

    private IKmArchivesSignService kmArchivesSignService;

    public IKmArchivesSignService getArchivesSignService() {
        if (kmArchivesSignService == null) {
            kmArchivesSignService = (IKmArchivesSignService) SpringBeanUtil.getBean("kmArchivesSignService");
        }
        return kmArchivesSignService;
    }

    @Override
    public void addFileArchives(IBaseModel mainModel) throws Exception {
        if (getService() != null) {
            getService().addFileModel(mainModel);
        }
    }

    @Override
    public void setFileField(KmArchivesMain kmArchivesMain,
                             KmArchivesFileTemplate fileTemplate, IBaseModel mainModel)
            throws Exception {
        String fdModelId = mainModel.getFdId();
        String fdModelName = mainModel.getClass().getName();
        kmArchivesMain.setFdModelId(fdModelId);
        if (fdModelName.indexOf("$") > -1) {
            kmArchivesMain.setFdModelName(fdModelName.substring(0, fdModelName.indexOf("$")));
        } else {
            kmArchivesMain.setFdModelName(fdModelName);
        }

        kmArchivesMain.setDocTemplate(fileTemplate.getCategory());
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
        List<SysFileConvertClient> findClients = sysFileConvertClientService.findList(hqlInfo);
        int toPdfAlive = findClients.size() > 0 ? 1 : 0;
        kmArchivesMain.setFdPdfAlive(toPdfAlive);
        // 归档人身份
        if ("org".equals(fileTemplate.getSelectFilePersonType())) {
            kmArchivesMain.setDocCreator(fileTemplate.getFdFilePerson());
        } else {
            List<SysOrgElement> listArgs = KmArchivesUtil.getFormulaValue(
                    mainModel,
                    fileTemplate.getFdFilePersonFormula());
            if (listArgs != null && listArgs.size() > 0) {
                SysOrgElement person = listArgs.get(0);
                if (person instanceof SysOrgPerson) {
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

    @Override
    public void setFileField(KmArchivesMain kmArchivesMain,
                             KmArchivesFileTemplate fileTemplate, IBaseModel mainModel,
                             Map<String, IBaseModel> modelMap) throws Exception {
        String fdModelId = mainModel.getFdId();
        String fdModelName = mainModel.getClass().getName();
        kmArchivesMain.setFdModelId(fdModelId);
        if (fdModelName.indexOf("$") > -1) {
            kmArchivesMain.setFdModelName(fdModelName.substring(0, fdModelName.indexOf("$")));
        } else {
            kmArchivesMain.setFdModelName(fdModelName);
        }

        kmArchivesMain.setDocTemplate(fileTemplate.getCategory());
        // 预归档
        Boolean fdPreFile = fileTemplate.getFdPreFile() != null && fileTemplate.getFdPreFile() ? true : false;
        if (fdPreFile) {
            kmArchivesMain.setDocStatus(SysDocConstant.DOC_STATUS_DRAFT);
        } else {
            kmArchivesMain.setDocStatus(SysDocConstant.DOC_STATUS_EXAMINE);
        }
        // 归档人身份
        if ("org".equals(fileTemplate.getSelectFilePersonType())) {
            if (fileTemplate.getFdFilePerson() != null) {
                kmArchivesMain.setDocCreator(fileTemplate.getFdFilePerson());
            }
        } else {
            List<SysOrgElement> listArgs = KmArchivesUtil.getFormulaValue(
                    mainModel,
                    fileTemplate.getFdFilePersonFormula());
            if (listArgs != null && listArgs.size() > 0) {
                SysOrgElement person = listArgs.get(0);
                if (person instanceof SysOrgPerson) {
                    kmArchivesMain.setDocCreator((SysOrgPerson) person);
                } else {
                    if (UserUtil.getAnonymousUser().getUserId().equals(UserUtil.getUser().getFdId())) {
                        //匿名用户已经传入
                    } else {
                        kmArchivesMain.setDocCreator(UserUtil.getUser());
                    }

                }
            }
        }

        // 档案名称
        setField(fileTemplate.getDocSubjectMapping(), modelMap, kmArchivesMain,
                "docSubject");
        if (StringUtil.isNull(kmArchivesMain.getDocSubject())) {
            kmArchivesMain.setDocSubject(getModelSubject(mainModel));
        }
        // 所属卷库
        setField(fileTemplate.getFdLibraryMapping(), modelMap, kmArchivesMain,
                "fdLibrary");
        // 组卷年度
        setField(fileTemplate.getFdVolumeYearMapping(), modelMap,
                kmArchivesMain, "fdVolumeYear");
        // 保管期限
        setField(fileTemplate.getFdPeriodMapping(), modelMap, kmArchivesMain,
                "fdPeriod");
        // 保管单位
        setField(fileTemplate.getFdUnitMapping(), modelMap, kmArchivesMain,
                "fdUnit");
        // 保管员
        setField(fileTemplate.getFdKeeperMapping(), modelMap, kmArchivesMain,
                "fdStorekeeper");
        // 档案有效期
        setField(fileTemplate.getFdValidityDateMapping(), modelMap,
                kmArchivesMain, "fdValidityDate");
        // 密级程度
        setField(fileTemplate.getFdDenseLevelMapping(), modelMap,
                kmArchivesMain, "fdDenseLevel");
        // 归档日期
        setField(fileTemplate.getFdFileDateMapping(), modelMap, kmArchivesMain,
                "fdFileDate");
        //
        setFileExtendData(kmArchivesMain, fileTemplate, modelMap);
    }

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
						/*
						ISysMetadataParser sysMetadataParser = (ISysMetadataParser) SpringBeanUtil
								.getBean("sysMetadataParser");
						SysDictModel dictModel = sysMetadataParser.getDictModel(mainModel);
						SysDictExtendModel dictExtendModel = (SysDictExtendModel) dictModel;
						Map<String, SysDictCommonProperty> extmap = dictExtendModel.getPropertyMap();
						SysDictCommonProperty sysDictCommonProperty = extmap.get(fieldName);
						String fdType = sysDictCommonProperty.getType();
						if ("com.landray.kmss.sys.organization.model.SysOrgPerson".equals(fdType)) {
							HashMap objMap = (HashMap) obj;
							ISysOrgPersonService sysOrgPersonService = (ISysOrgPersonService) SpringBeanUtil
									.getBean("sysOrgPersonService");
							SysOrgPerson person = (SysOrgPerson) sysOrgPersonService
									.findByPrimaryKey((String) objMap.get("id"));
							return person;
						}
						if ("com.landray.kmss.sys.organization.model.SysOrgElement".equals(fdType)) {
							HashMap objMap = (HashMap) obj;
							ISysOrgCoreService sysOrgCoreService = (ISysOrgCoreService) SpringBeanUtil
									.getBean("sysOrgCoreService");
							SysOrgElement sysOrgElement = sysOrgCoreService.findByPrimaryKey((String) objMap.get("id"));
							return sysOrgElement;
						}
						*/
                    }
                }
            }
        }
        return obj;
    }

    /**
     * 获取模型中的属性值，有特殊处理
     *
     * @param mainModel
     * @param fieldName
     * @return
     * @throws Exception
     */
    private Object getExtendBusiValue(IBaseModel mainModel, String fieldName) throws Exception {
        Object obj = null;
        if (StringUtil.isNotNull(fieldName)) {
            String className = mainModel.getClass().getName();
            if (logger.isDebugEnabled()) {
                logger.debug("获取模型中的属性值,className=" + className + ",fieldName=" + fieldName);
            }
            String modelName = "";
            if (className.indexOf("$") > -1) {
                modelName = className.substring(0, className.indexOf("$"));
            } else {
                modelName = className;
            }

            SysDictModel dictModel = SysDataDict.getInstance().getModel(modelName);
            SysDictCommonProperty property = dictModel.getPropertyMap().get(fieldName);
            IBaseService baseService = (IBaseService) SpringBeanUtil.getBean(dictModel.getServiceBean());
            RequestContext requestContext = new RequestContext();
            IExtendForm form = null;
            form = baseService.convertModelToForm(form, mainModel, requestContext);
            // 主文档的属性
            if (PropertyUtils.isReadable(mainModel, fieldName)) {
                // 主文档数据
                try {
                    String type = property.getType();
                    if (type.startsWith("com.landray.kmss")) {
                        //对象类型
                        obj = PropertyUtils.getSimpleProperty(form, fieldName + "Name");
                    } else {
                        if ("fdStanding".equals(fieldName) || "fdSettype".equals(fieldName)) {
                            //根据枚举值获取
                            Object fieldValue = PropertyUtils.getSimpleProperty(mainModel, fieldName);
                            if (fieldValue != null && fieldValue instanceof String) {
                                String enumValue = fieldValue.toString();
                                if (StringUtil.isNotNull(enumValue)) {
                                    if ("fdStanding".equals(fieldName)) {
                                        obj = ResourceUtil.getString("enums.standing." + enumValue, "km-agreement");
                                    } else if ("fdSettype".equals(fieldName)) {
                                        obj = ResourceUtil.getString("enums.settlement_type." + enumValue, "km-agreement");
                                    }
                                }
                            }
                        } else if (property.isEnum()) {
                            //处理枚举
                            String enumType = property.getEnumType();
                            //枚举值获取
                            Object fieldValue = PropertyUtils.getSimpleProperty(mainModel, fieldName);
                            if (fieldValue != null && fieldValue instanceof String) {
                                String enumValue = fieldValue.toString();
                                obj = EnumerationTypeUtil.getColumnEnumsLabel(enumType, enumValue);
                            }
                        } else {
                            obj = PropertyUtils.getProperty(mainModel, fieldName);
                        }
                    }
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

    // 给字段赋值
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

    // 给字段赋值（多model）
    private void setField(String fieldName, Map<String, IBaseModel> modelMap,
                          KmArchivesMain kmArchivesMain, String setFieldName)
            throws Exception {
        if (StringUtil.isNotNull(fieldName)) {
            String[] fieldNameArray = fieldName.split(":");
            if (fieldNameArray.length != 2) {
                return;
            }
            String field = fieldNameArray[1];
            IBaseModel mainModel = modelMap.get(fieldNameArray[0]);
            if (mainModel != null) {
                Object obj = getValue(mainModel, field);
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
                                + "，from：" + field + "，to："
                                + setFieldName + "，value：" + obj);
                    }
                }
            }
        }
    }

    // 获得主文档的标题
    private String getModelSubject(IBaseModel mainModel) {
        String subject = null;
        // try {
        // subject = (String) PropertyUtils.getProperty(mainModel,
        // "docSubject");
        // } catch (Exception e) {
        // try {
        // subject = (String) PropertyUtils.getProperty(mainModel,
        // "fdName");
        // } catch (Exception e2) {
        // logger.error("未找到文档标题");
        // }
        // }
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

    // 扩展属性
    private void setFileExtendData(KmArchivesMain kmArchivesMain,
                                   KmArchivesFileTemplate fileTemplate, IBaseModel mainModel)
            throws Exception {
        KmArchivesCategory category = fileTemplate.getCategory();
        if (category != null
                && category.getSysPropertyTemplate() != null
                && category.getSysPropertyTemplate()
                .getFdReferences() != null) {
            List<SysPropertyReference> references = category
                    .getSysPropertyTemplate()
                    .getFdReferences();
            Map<String, Object> modelData = kmArchivesMain.getExtendDataModelInfo().getModelData();
            for (SysPropertyReference reference : references) {
                String key = reference.getFdDefine().getFdStructureName();
                modelData.put(key, getValue(mainModel,
                        KmArchivesUtil.getFieldName(fileTemplate, key)));
            }
            kmArchivesMain.setExtendFilePath(
                    SysPropertyUtil.getExtendFilePath(category));
            kmArchivesMain.setExtendDataXML(
                    ObjectXML.objectXmlEncoder(modelData));
        }

    }

    // 扩展属性（多model）
    private void setFileExtendData(KmArchivesMain kmArchivesMain,
                                   KmArchivesFileTemplate fileTemplate,
                                   Map<String, IBaseModel> modelMap) throws Exception {
        KmArchivesCategory category = fileTemplate.getCategory();
        if (category != null && category.getSysPropertyTemplate() != null
                && category.getSysPropertyTemplate()
                .getFdReferences() != null) {
            List<SysPropertyReference> references = category
                    .getSysPropertyTemplate().getFdReferences();
            Map<String, Object> modelData = kmArchivesMain.getExtendDataModelInfo().getModelData();
            for (SysPropertyReference reference : references) {
                String key = reference.getFdDefine().getFdStructureName();
                String fieldName = KmArchivesUtil.getFieldName(fileTemplate,
                        key);
                Object object = null;
                if (StringUtil.isNotNull(fieldName)) {
                    String[] fieldNameArray = fieldName.split(":");
                    if (fieldNameArray.length == 2) {
                        String field = fieldNameArray[1];
                        if (modelMap.containsKey(fieldNameArray[0])) {
                            IBaseModel mainModel = modelMap.get(fieldNameArray[0]);
                            object = getExtendBusiValue(mainModel, field);
                        }
                    }
                }
                modelData.put(key, object);
            }
            kmArchivesMain.setExtendFilePath(
                    SysPropertyUtil.getExtendFilePath(category));
            kmArchivesMain.setExtendDataXML(
                    ObjectXML.objectXmlEncoder(modelData));
        }
    }

    @Override
    public void setFilePrintPage(KmArchivesMain kmArchivesMain,
                                 HttpServletRequest request, String url, String fileName)
            throws Exception {
        setFilePrintPageZoom(kmArchivesMain, request, url, fileName, "0.7");
    }

    @Override
    public void setFilePrintPageZoom(KmArchivesMain kmArchivesMain,
                                     Map<String, String> params, String url, String fileName, String zoom)
            throws Exception {
        HtmlToMht htmlToMht = new HtmlToMht(false);
        String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
        url = serverUrl + url;
        String page = htmlToMht.getClientPage(params, url);

        page = new KmArchivesHtmlHandler().handlingHTMLAgainZoom(page, zoom);
        if (StringUtil.isNotNull(page)) {
            byte[] bytes = page.getBytes("UTF-8");
            // 主文档归档页面当做临时附件
            addToPDFSourceAtt(kmArchivesMain, fileName, bytes, serverUrl, null);
        }
    }

    @Override
    public void setFilePrintPageZoom(KmArchivesMain kmArchivesMain,
                                     HttpServletRequest request, String url, String fileName, String zoom)
            throws Exception {
        Map<String,String> params = new HashMap<>();
        params.put("cookie", request.getHeader("Cookie"));
        params.put("serverName", request.getServerName());
        params.put("serverPort", request.getServerPort()+"");
        params.put("servletPath", request.getServletPath());
        params.put("SESSIONID", request.getRequestedSessionId());
        params.put("lang", ResourceUtil.getLocaleStringByUser(request));
        params.put("contextPath", request.getContextPath());

        setFilePrintPageZoom(kmArchivesMain, params, url, fileName, zoom);
    }

    @Override
    public void setFileAttachement(KmArchivesMain kmArchivesMain,
                                   IBaseModel mainModel) throws Exception {
        List<?> attList = sysAttMainService.findAttListByModel(
                ModelUtil.getModelClassName(mainModel),
                mainModel.getFdId());

        if (attList != null && attList.size() > 0) {
            for (int j = 0; j < attList.size(); j++) {
                SysAttMain attMain = (SysAttMain) attList.get(j);
                if (!isNotNeedWhenFile(attMain)) {
                    // 主文档附件当做归档临时附件
                    addToPDFSourceAtt(kmArchivesMain, attMain);
                }
            }
        }
    }

    @Override
    public void setFileAttachement(KmArchivesMain kmArchivesMain,
                                   Map<String, IBaseModel> modelMap) throws Exception {
        List<?> attList = new ArrayList<>();
        for (IBaseModel mainModel : modelMap.values()) {
            if (mainModel != null) {
                List<?> list = sysAttMainService.findAttListByModel(
                        ModelUtil.getModelClassName(mainModel),
                        mainModel.getFdId());
                SysDictModel dict = SysDataDict.getInstance()
                        .getModel(ModelUtil.getModelClassName(mainModel));
                String messageKey = ResourceUtil
                        .getString(dict.getMessageKey());
                if (!ArrayUtil.isEmpty(list)) {
                    List<String> tmpFileKeyList = new ArrayList<String>();
                    //不需要进行档案处理的文件，主要是针对重复文件
                    List<SysAttMain> removeList = new ArrayList<SysAttMain>();
                    for (int i = 0; i < list.size(); i++) {
                        SysAttMain attMain = (SysAttMain) list.get(i);
                        String versionName = "";
                        if ("com.landray.kmss.km.agreement.model.KmAgreementApply".equals(attMain.getFdModelName())) {
                            if ("processDoc".equals(attMain.getFdKey()) && attMain.getFdFileName().indexOf("_V") < 0) {
                                versionName = ResourceUtil.getString("file.version.prefix", "km-archives") + " - ";
                            }
                        } else if ("com.landray.kmss.km.agreement.model.KmAgreementApplyMakeup".equals(attMain.getFdModelName())) {
                            if (!"attFianlContract".equals(attMain.getFdKey())) {
                                removeList.add(attMain);
                            } else {
                                messageKey = ResourceUtil
                                        .getString("km-agreement:table.kmAgreementReview");
                            }
                        }
                        String[] fileName = attMain.getFdFileName()
                                .split(" - ");
                        if (fileName.length > 1) {
                            attMain.setFdFileName(
                                    messageKey + " - "
                                            + versionName + fileName[fileName.length - 1]);
                        } else {
                            attMain.setFdFileName(
                                    messageKey + " - "
                                            + versionName + attMain.getFdFileName());
                        }
                        String fileExt = FilenameUtils.getExtension(attMain.getFdFileName());
                        if ("xls".equalsIgnoreCase(fileExt) || "xlsx".equalsIgnoreCase(fileExt)) {
                            String key = attMain.getFdFileId() + "*_*" + attMain.getFdFileName();
                            if (!tmpFileKeyList.contains(key)) {
                                tmpFileKeyList.add(key);
                            } else {
                                removeList.add(attMain);
                            }
                        }
                    }
                    if (!ArrayUtil.isEmpty(removeList)) {
                        list.removeAll(removeList);
                    }

                    ArrayUtil.concatTwoList(list, attList);
                }
            }
        }

        if (attList != null && attList.size() > 0) {
            for (int j = 0; j < attList.size(); j++) {
                SysAttMain attMain = (SysAttMain) attList.get(j);
                if (!isNotNeedWhenFile(attMain)) {
                    // 主文档附件当做归档临时附件
                    addToPDFSourceAtt(kmArchivesMain, attMain);
                }
            }
        }
    }

    /**
     * 判断是否是不需要归档的附件，一般是一些流程中的签批图片等
     *
     * @param attMain
     * @return
     */
    private boolean isNotNeedWhenFile(SysAttMain attMain) {
        /**
         * sp语音，sg批注，qz签章，hw手写，不需要归档过去
         */
        String[] noFileKeys = new String[]{"_sp", "_sg", "_qz", "_hw", "historyVersionAttachment", "_overdue"};
        String fdKey = attMain.getFdKey();
        if (StringUtil.isNotNull(fdKey)) {
            for (String key : noFileKeys) {
                if (fdKey.endsWith(key) || "baseInfoAtt_html".equals(fdKey) || "historyVersionAttachment".equals(fdKey) || "attProve_asposezip".equals(fdKey) || "WPSCenterSelfAttachment".equals(fdKey)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 添加到待转pdf的临时附件区域，若为无需转PDF的格式，则跳过转换直接放到展示附件列表 123
     *
     * @param kmArchivesMain
     * @param attMain
     * @throws Exception
     */
    @Override
    public void addToPDFSourceAtt(KmArchivesMain kmArchivesMain, SysAttMain attMain) throws Exception {
        SysAttMain newAtt = KmArchivesUtil.clone(attMain);
        newAtt.setFdAttType("byte");
        newAtt.setFdModelId(kmArchivesMain.getFdId());
        int saveOldFile = kmArchivesMain.getFdSaveOldFile();
        int toPdfAlive = kmArchivesMain.getFdPdfAlive();
        if (toPdfAlive != 1) {
            //pdf转换服务没有开启,默认保存旧文件
            saveOldFile = 1;
        }
        newAtt.setFdKey(saveOldFile != 1 ? IKmArchivesMainService.FD_TEMP_ATT_KEY : IKmArchivesMainService.FD_ATT_KEY);
        newAtt.setFdModelName(KmArchivesMain.class.getName());
        sysAttMainService.update(newAtt);
        addToPDFSourceAtt(kmArchivesMain, attMain.getFdFileName(), null, null, newAtt);
    }

    /**
     * 添加到待转pdf的临时附件区域，若为无需转PDF的格式，则跳过转换直接放到展示附件列表
     *
     * @param kmArchivesMain
     * @param fileName
     * @param bytes
     * @param serverUrl      服务器地址，HTML中CSS/IMG的根路径地址，HTML转PDF前检查是否能连通，若无法连通则替换为aspose服务器的dns配置地址
     * @throws Exception
     */
    private void addToPDFSourceAtt(KmArchivesMain kmArchivesMain, String fileName, byte[] bytes, String serverUrl,
                                   SysAttMain attMain) throws Exception {
        //XXX 需转换PDF格式列表由后台配置
        final String convertTypes = "html;ppt;pptx;xls;xlsx;doc;docx;rtf;txt;wps;dps;et";
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        int saveOldFile = kmArchivesMain.getFdSaveOldFile();
        int toPdfAlive = kmArchivesMain.getFdPdfAlive();
        if (toPdfAlive != 1) {
            //pdf转换服务没有开启,默认保存旧文件
            saveOldFile = 1;
        }
        if (convertTypes.toLowerCase().contains(extName.toLowerCase())) {
            // 需转换PDF格式
            if (attMain != null) { // 优化逻辑，大附件归档时，附件采用拷贝方式
                if (saveOldFile != 1) {
                    // 加入PDF转换队列
                    SysFileConvertQueue queue = getConvertQueueService().addQueueToPDF(attMain.getFdFileId(),
                            attMain.getFdFileName(), attMain.getFdModelName(), attMain.getFdModelId(),
                            StringUtils.EMPTY, attMain.getFdId(), serverUrl);
                    if (queue != null) {
                        if (queue.getFdConvertStatus() == 4) {
                            addPDFAtt("Aspose", "toPDF", attMain, Arrays.asList(attMain.getFdModelId()));
                        } else {
                            logger.debug("已加入队列，或转换队列中已存在该文件，等待转换完成消息");
                        }
                    }
                }
            } else { // 原逻辑，用于保存二进制流
                String downloadUrl = sysAttMainService.addAttachment(kmArchivesMain,
                        saveOldFile != 1 ? IKmArchivesMainService.FD_TEMP_ATT_KEY : IKmArchivesMainService.FD_ATT_KEY,
                        bytes, fileName, "byte", true);
                if (StringUtil.isNotNull(downloadUrl) && saveOldFile != 1) {
                    String urlSub = "fdId=";
                    int index = downloadUrl.indexOf(urlSub) + urlSub.length();
                    if (index != -1) {
                        String attId = downloadUrl.substring(index, index + 32);
                        SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(attId);
                        // 加入PDF转换队列
                        SysFileConvertQueue queue = getConvertQueueService().addQueueToPDF(sysAttMain.getFdFileId(),
                                sysAttMain.getFdFileName(), sysAttMain.getFdModelName(), sysAttMain.getFdModelId(),
                                StringUtils.EMPTY, sysAttMain.getFdId(), serverUrl);
                        if (queue != null) {
                            if (queue.getFdConvertStatus() == 4) {
                                addPDFAtt("Aspose", "toPDF", sysAttMain, Arrays.asList(sysAttMain.getFdModelId()));
                            } else {
                                logger.debug("已加入队列，或转换队列中已存在该文件，等待转换完成消息");
                            }
                        }
                    }
                }
            }
        } else {
            //无需转换PDF格式
            if (bytes != null) {
                sysAttMainService.addAttachment(kmArchivesMain, IKmArchivesMainService.FD_ATT_KEY, bytes, fileName,
                        "byte", true);
            }
        }
    }

    private void addPDFAtt(String converterType, String converterKey, SysAttMain sysAttMain, List<String> modelIds)
            throws Exception {
        if (sysAttMain == null) {
            return;
        }
        //校验转换后的PDF是否存在
        String pdfFileName = converterKey + "-" + converterType + "_pdf";
        String pdfFilePath = SysAttViewerUtil.getConvertFilePath(sysAttMain, pdfFileName);
        String filePath = SysAttViewerUtil.getAttFilePath(sysAttMain);
        if (pdfFilePath == null || pdfFilePath.equals(filePath)) {
            //使点击归档按钮操作失败
            logger.error(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
            throw new FileNotFoundException(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
        }
        //读取转换PDF后的文件流
        InputStream inputStream = SysAttViewerUtil.getConvertFileInputStream(sysAttMain, pdfFileName);
        String fileName = sysAttMain.getFdFileName();

        try {
            //PDF作为新附件添加到归档文件中
            if (fileName.contains(".")) {
                String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if ("html".equals(extName)) {
                    fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
                } else {
                    //保留原格式信息
                    fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + extName + ".pdf";
                }
            }
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            KmArchivesMain model = new KmArchivesMain();
            for (String id : modelIds) {
                model.setFdId(id);
                sysAttMainService.addAttachment(model, IKmArchivesMainService.FD_ATT_KEY, byteArray, fileName, "byte");
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public Map<String, String> getOptions(String modelName, String type,
                                          String templateService, String templateId)
            throws Exception {
        //通用方法有时候不适用业务模块的配置需求，所以单独处理
        if (modelName.indexOf("com.landray.kmss.km.agreement.model") > -1) {
            return getBusiModelOptions(modelName, type, templateService, templateId);
        }
        Map<String, String> map = new HashMap<String, String>();
        SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
        List<SysDictCommonProperty> props = dict.getPropertyList();
        if (StringUtil.isNotNull(type)) {
            String[] types = type.split("\\|");
            List<String> typeList = Arrays.asList(types);
            for (SysDictCommonProperty sysDictCommonProperty : props) {
                if (typeList.contains(sysDictCommonProperty.getType())) {
                    String title = ResourceUtil
                            .getString(sysDictCommonProperty.getMessageKey());
                    if (StringUtil.isNotNull(title)) {
                        map.put(sysDictCommonProperty.getName(), title);
                    }
                }
            }
            SysDictExtendModel sysDictExtendModel = getExtendModel(
                    templateService, templateId);
            if (sysDictExtendModel != null) {
                List<SysDictCommonProperty> properties = sysDictExtendModel.getPropertyList();
                FormulaParser formulaParser = FormulaParser.getInstance(new KmArchivesMain());
                for (SysDictCommonProperty sysDictCommonProperty : properties) {
                    if (sysDictCommonProperty instanceof SysDictExtendProperty) {
                        SysDictExtendProperty extendProp = (SysDictExtendProperty) sysDictCommonProperty;
                        if (StringUtil.isNotNull(extendProp.getLabel())) {
                            // 保管员，可以设置为人员和岗位，但是对于岗位的类型一般使用SysOrgElement，所以这里需要特殊处理
                            // 如果自定义字段是组织类型，需要特殊处理
                            if ("com.landray.kmss.sys.organization.model.SysOrgElement".equals(extendProp.getType())) {
                                // 说明前端需要选择的可包括人员和岗位地址框
                                if (!"com.landray.kmss.sys.organization.model.SysOrgPerson|com.landray.kmss.sys.organization.model.SysOrgPost".equals(type)) {
                                    continue;
                                }
                                Object value = formulaParser.parseValueScript(extendProp.getDefaultValue());
                                SysOrgElement element = null;
                                if (value instanceof SysOrgElement) {
                                    // 说明有设置初始值
                                    element = sysOrgCoreService.format((SysOrgElement) value);
                                    if (element instanceof SysOrgPost || element instanceof SysOrgPerson) {
                                        map.put(extendProp.getName(), extendProp.getLabel());
                                    }
                                } else if (value == null) {
                                    // 说明初始值为无并且选择框是可以选择人员或者岗位的地址框
                                    map.put(extendProp.getName(), extendProp.getLabel());
                                }
                            } else if (typeList.contains(extendProp.getType())) {
                                map.put(extendProp.getName(), extendProp.getLabel());
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 业务模块自定义属性要求，比如设置地址本，也可以过滤出人员
     *
     * @param modelName
     * @param type
     * @param templateService
     * @param templateId
     * @return
     * @throws Exception
     */
    private Map<String, String> getBusiModelOptions(String modelName, String type,
                                                    String templateService, String templateId) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        //目前只获取相应模型字典表的属性，扩展属性暂时不取
        SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
        List<SysDictCommonProperty> props = dict.getPropertyList();
        if (StringUtil.isNotNull(type)) {
            String[] types = type.split("\\|");
            for (SysDictCommonProperty sysDictCommonProperty : props) {
                if (sysDictCommonProperty instanceof SysDictListProperty) {
                    //过滤字典表中propertyType为list的值
                    continue;
                }

                //属性类型
                String propType = sysDictCommonProperty.getType();

                if (propType == null) {
                    propType = "";
                }

                if (propType.indexOf("com.landray.kmss") > -1) {
                    propType = "String";
                }

                if (Arrays.asList(types)
                        .contains(propType)) {
                    String title = ResourceUtil
                            .getString(sysDictCommonProperty.getMessageKey());
                    if (StringUtil.isNotNull(title)) {
                        map.put(sysDictCommonProperty.getName(), title);
                    }
                }
            }
            SysDictExtendModel sysDictExtendModel = getExtendModel(
                    templateService, templateId);
            if (sysDictExtendModel != null) {
                List<SysDictCommonProperty> properties = sysDictExtendModel
                        .getPropertyList();
                for (SysDictCommonProperty sysDictCommonProperty : properties) {
                    if (sysDictCommonProperty instanceof SysDictExtendProperty) {
                        SysDictExtendProperty extendProp = (SysDictExtendProperty) sysDictCommonProperty;
                        if (Arrays.asList(types)
                                .contains(extendProp.getType())) {
                            if (StringUtil.isNotNull(extendProp.getLabel())) {
                                map.put(extendProp.getName(),
                                        extendProp.getLabel());
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

    private SysDictExtendModel getExtendModel(String templateService,
                                              String templateId) throws Exception {
        if (StringUtil.isNull(templateService)
                || StringUtil.isNull(templateId)) {
            return null;
        }
        IBaseService service = (IBaseService) SpringBeanUtil
                .getBean(templateService);
        IBaseModel model = service.findByPrimaryKey(templateId);
        if (model == null) {
            return null;
        }
        List list = sysFormTemplateService.getCoreModels(model, null);
        if (!ArrayUtil.isEmpty(list)) {
            SysFormTemplate sysFormTemplate = (SysFormTemplate) list.get(0);
            return getDictLoadService()
                    .loadDictByFileName(sysFormTemplate.getFdFormFileName());
            // final String s = DateUtil.convertDateToString(
            // sysFormTemplate.getFdAlterTime(),
            // "yyyyMMddHHmmss");
            // String fileName = sysFormTemplate.getFdFormFileName();
            // String fullPath = ConfigLocationsUtil.getWebContentPath() + "/"
            // + fileName + "_" + s;
            // File desFile = new File(fullPath + ".xml");
            // if (desFile.exists()) {
            // BeanReader xmlReader = new BeanReader();
            // xmlReader.registerBeanClass(SysDictExtendModel.class);
            // SysDictExtendModel sysDictExtendModel = (SysDictExtendModel)
            // xmlReader
            // .parse(desFile);
            // return sysDictExtendModel;
            // }
        }
        return null;
    }

    @Override
    public KmArchivesFileTemplate getFileTemplate(IBaseModel categoryMain,
                                                  String key)
            throws Exception {
        List list = getCoreModels(categoryMain, key);
        if (list != null && list.size() > 0) {
            KmArchivesFileTemplate template = (KmArchivesFileTemplate) list
                    .get(0);
            if (template.getCategory() != null) {
                return template;
            }
        }
        return null;
    }

    @Override
    public void setFilePrintArchivesPage(KmArchivesMain kmArchivesMain, String url, String fileName) throws Exception {
        setFilePrintArchivesPageZoom(kmArchivesMain, url, fileName, "0.7");
    }

    @Override
    public void setFilePrintArchivesPageZoom(KmArchivesMain kmArchivesMain, String url, String fileName, String zoom) throws Exception {
        HtmlToMht htmlToMht = new HtmlToMht(false);
        String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
        url = serverUrl + url;
        String page = htmlToMht.getClientPageArchives(url);

        page = new KmArchivesHtmlHandler().handlingHTMLAgainZoom(page, zoom);

        if (StringUtil.isNotNull(page)) {
            byte[] bytes = page.getBytes("UTF-8");
            // 主文档归档页面当做临时附件
            addToPDFSourceAtt(kmArchivesMain, fileName, bytes, serverUrl, null);
        }
    }

    @Override
    public void updateFileField(KmArchivesMain kmArchivesMain,
                                KmArchivesFileTemplate fileTemplate, IBaseModel mainModel,
                                Map<String, IBaseModel> modelMap) throws Exception {
        if (fileTemplate != null) {
            // 归档人身份
            if ("org".equals(fileTemplate.getSelectFilePersonType())) {
                if (fileTemplate.getFdFilePerson() != null) {
                    kmArchivesMain.setDocCreator(fileTemplate.getFdFilePerson());
                }

            } else {
                List<SysOrgElement> listArgs = KmArchivesUtil.getFormulaValue(
                        mainModel,
                        fileTemplate.getFdFilePersonFormula());
                if (listArgs != null && listArgs.size() > 0) {
                    SysOrgElement person = listArgs.get(0);
                    if (person instanceof SysOrgPerson) {
                        kmArchivesMain.setDocCreator((SysOrgPerson) person);
                    } else {
                        if (UserUtil.getAnonymousUser().getUserId().equals(UserUtil.getUser().getFdId())) {
                            //匿名用户已经传入
                        } else {
                            kmArchivesMain.setDocCreator(UserUtil.getUser());
                        }
                    }
                }
            }

            if (StringUtil.isNull(kmArchivesMain.getFdLibrary())) {
                // 所属卷库
                setField(fileTemplate.getFdLibraryMapping(), modelMap, kmArchivesMain,
                        "fdLibrary");
            }
            if (StringUtil.isNull(kmArchivesMain.getFdVolumeYear())) {
                // 组卷年度
                setField(fileTemplate.getFdVolumeYearMapping(), modelMap, kmArchivesMain,
                        "fdVolumeYear");
            }
            if (StringUtil.isNull(kmArchivesMain.getFdPeriod())) {
                // 保管期限
                setField(fileTemplate.getFdPeriodMapping(), modelMap, kmArchivesMain,
                        "fdPeriod");
            }
            if (StringUtil.isNull(kmArchivesMain.getFdUnit())) {
                // 保管单位
                setField(fileTemplate.getFdUnitMapping(), modelMap, kmArchivesMain,
                        "fdUnit");
            }
            if (kmArchivesMain.getFdStorekeeper() == null) {
                // 保管员
                setField(fileTemplate.getFdKeeperMapping(), modelMap, kmArchivesMain,
                        "fdStorekeeper");
            }
            if (kmArchivesMain.getFdValidityDate() == null) {
                // 档案有效期
                setField(fileTemplate.getFdValidityDateMapping(), modelMap, kmArchivesMain,
                        "fdValidityDate");
            }
            if (StringUtil.isNull(kmArchivesMain.getFdDenseLevel())) {
                // 密级程度
                setField(fileTemplate.getFdDenseLevelMapping(), modelMap, kmArchivesMain,
                        "fdDenseLevel");
            }
            if (kmArchivesMain.getFdFileDate() == null) {
                // 归档日期
                setField(fileTemplate.getFdFileDateMapping(), modelMap, kmArchivesMain,
                        "fdFileDate");
            }
            // 自定义属性
            setFileExtendData(kmArchivesMain, fileTemplate, modelMap);
        }
    }

    @Override
    public void updateFilePrintPage(KmArchivesMain kmArchivesMain,
                                    HttpServletRequest request, String url, String fileName)
            throws Exception {
        updateFilePrintPageZoom(kmArchivesMain, request, url, fileName, "0.7");
    }

    private void updateFilePrintPageZoom(KmArchivesMain kmArchivesMain,
                                         HttpServletRequest request, String url, String fileName, String zoom)
            throws Exception {
        HtmlToMht htmlToMht = new HtmlToMht(false);
        String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
        url = serverUrl + url;
        String page = htmlToMht.getClientPage(request, url);

        page = new KmArchivesHtmlHandler().handlingHTMLAgainZoom(page, zoom);
        if (StringUtil.isNotNull(page)) {
            byte[] bytes = page.getBytes("UTF-8");
            // 主文档归档页面当做临时附件
            updateToPDFSourceAtt(kmArchivesMain, fileName, bytes, serverUrl);
        }
    }


    /**
     * 档案更新使用
     * 添加到待转pdf的临时附件区域，若为无需转PDF的格式，则跳过转换直接放到展示附件列表
     *
     * @param kmArchivesMain
     * @param fileName
     * @param bytes
     * @param serverUrl      服务器地址，HTML中CSS/IMG的根路径地址，HTML转PDF前检查是否能连通，若无法连通则替换为aspose服务器的dns配置地址
     * @throws Exception
     */
    private void updateToPDFSourceAtt(KmArchivesMain kmArchivesMain, String fileName, byte[] bytes, String serverUrl)
            throws Exception {
        //XXX 需转换PDF格式列表由后台配置
        final String convertTypes = "html;ppt;pptx;xls;xlsx;doc;docx;rtf;txt;wps;dps;et";
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        int saveOldFile = kmArchivesMain.getFdSaveOldFile();
        int toPdfAlive = kmArchivesMain.getFdPdfAlive();
        if (toPdfAlive != 1) {
            //pdf转换服务没有开启,默认保存旧文件
            saveOldFile = 1;
        }
        String fdKey = saveOldFile != 1 ? IKmArchivesMainService.FD_TEMP_ATT_KEY : IKmArchivesMainService.FD_ATT_KEY;
        if (convertTypes.toLowerCase().contains(extName.toLowerCase())) {
            //需转换PDF格式
            //如果已经存在档案附件信息，则进行更新
            HQLInfo hqlInfo = new HQLInfo();
            StringBuilder sql = new StringBuilder();
            sql.append(" sysAttMain.fdModelId = :fdModelId");
            sql.append(" and sysAttMain.fdModelName = :fdModelName");
            sql.append(" and sysAttMain.fdKey in (:fdKeyList)");
            sql.append(" and sysAttMain.fdFileName = :fdFileName");
            hqlInfo.setWhereBlock(sql.toString());
            hqlInfo.setOrderBy("docCreateTime desc");
            hqlInfo.setParameter("fdModelId", kmArchivesMain.getFdId());
            hqlInfo.setParameter("fdModelName", KmArchivesMain.class.getName());
            List<String> fdKeyList = new ArrayList<String>();
            fdKeyList.add(IKmArchivesMainService.FD_ATT_KEY);
            fdKeyList.add(IKmArchivesMainService.FD_TEMP_ATT_KEY);
            hqlInfo.setParameter("fdKeyList", fdKeyList);
            hqlInfo.setParameter("fdFileName", fileName);
            List<SysAttMain> atts = sysAttMainService.findList(hqlInfo);
            if (atts != null && !atts.isEmpty()) {
                if (atts.size() == 1) {
                    SysAttMain attMain = atts.get(0);
                    attMain.setFdSize(Double.valueOf(bytes.length));
                    attMain.setInputStream(new ByteArrayInputStream(bytes));
                    attMain.setFdKey(fdKey);
                    sysAttMainService.update(attMain);
                } else {
                    //档案附件存在多条相同文件名的数据，则忽略
                    return;
                }
            } else {
                String downloadUrl = sysAttMainService.addAttachment(kmArchivesMain, fdKey,
                        bytes, fileName, "byte", true);
                if (StringUtil.isNotNull(downloadUrl) && saveOldFile != 1) {
                    String urlSub = "fdId=";
                    int index = downloadUrl.indexOf(urlSub) + urlSub.length();
                    if (index != -1) {
                        String attId = downloadUrl.substring(index, index + 32);
                        SysAttMain sysAttMain = (SysAttMain) sysAttMainService.findByPrimaryKey(attId);
                        //加入PDF转换队列
                        SysFileConvertQueue queue = getConvertQueueService().addQueueToPDF(sysAttMain.getFdFileId(),
                                sysAttMain.getFdFileName(), sysAttMain.getFdModelName(), sysAttMain.getFdModelId(),
                                StringUtils.EMPTY, sysAttMain.getFdId(), serverUrl);
                        if (queue != null) {
                            if (queue.getFdConvertStatus() == 4) {
                                updatePDFAtt("Aspose", "toPDF", sysAttMain, Arrays.asList(sysAttMain.getFdModelId()));
                            } else {
                                logger.debug("已加入队列，或转换队列中已存在该文件，等待转换完成消息");
                            }
                        }
                    }
                }
            }
        } else {
            //无需转换PDF格式
            //如果已经存在档案附件信息，则进行更新
            HQLInfo hqlInfo = new HQLInfo();
            StringBuilder sql = new StringBuilder();
            sql.append(" sysAttMain.fdModelId = :fdModelId");
            sql.append(" and sysAttMain.fdModelName = :fdModelName");
            sql.append(" and sysAttMain.fdKey in (:fdKeyList)");
            sql.append(" and sysAttMain.fdFileName = :fdFileName");
            hqlInfo.setWhereBlock(sql.toString());
            hqlInfo.setOrderBy("docCreateTime desc");
            hqlInfo.setParameter("fdModelId", kmArchivesMain.getFdId());
            hqlInfo.setParameter("fdModelName", KmArchivesMain.class.getName());
            List<String> fdKeyList = new ArrayList<String>();
            fdKeyList.add(IKmArchivesMainService.FD_ATT_KEY);
            hqlInfo.setParameter("fdKeyList", fdKeyList);
            hqlInfo.setParameter("fdFileName", fileName);
            List<SysAttMain> atts = sysAttMainService.findList(hqlInfo);
            if (atts != null && !atts.isEmpty()) {
                if (atts.size() == 1) {
                    SysAttMain attMain = atts.get(0);
                    attMain.setFdSize(Double.valueOf(bytes.length));
                    attMain.setInputStream(new ByteArrayInputStream(bytes));
                    attMain.setFdKey(IKmArchivesMainService.FD_ATT_KEY);
                    sysAttMainService.update(attMain);
                } else {
                    //档案附件存在多条相同文件名的数据，则忽略
                    return;
                }
            } else {
                sysAttMainService.addAttachment(kmArchivesMain, IKmArchivesMainService.FD_ATT_KEY, bytes, fileName, "byte", true);
            }
        }
    }

    /**
     * 将转换后的pdf文件添加到档案信息
     *
     * @param converterType
     * @param converterKey
     * @param sysAttMain
     * @param modelIds
     * @throws Exception
     */
    private void updatePDFAtt(String converterType, String converterKey, SysAttMain sysAttMain, List<String> modelIds)
            throws Exception {
        if (sysAttMain == null) {
            return;
        }
        //校验转换后的PDF是否存在
        String pdfFileName = converterKey + "-" + converterType + "_pdf";
        String pdfFilePath = SysAttViewerUtil.getConvertFilePath(sysAttMain, pdfFileName);
        String filePath = SysAttViewerUtil.getAttFilePath(sysAttMain);
        if (pdfFilePath == null || pdfFilePath.equals(filePath)) {
            //使点击归档按钮操作失败
            logger.error(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
            throw new FileNotFoundException(filePath + "转换后的PDF文件[" + pdfFileName + "]未找到");
        }
        //读取转换PDF后的文件流
        InputStream inputStream = SysAttViewerUtil.getConvertFileInputStream(sysAttMain, pdfFileName);
        String fileName = sysAttMain.getFdFileName();

        try {
            //PDF作为新附件添加到归档文件中
            if (fileName.contains(".")) {
                String extName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                if ("html".equals(extName)) {
                    fileName = fileName.substring(0, fileName.lastIndexOf(".")) + ".pdf";
                } else {
                    //保留原格式信息
                    fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + extName + ".pdf";
                }
            }
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            KmArchivesMain model = new KmArchivesMain();
            for (String id : modelIds) {
                model.setFdId(id);
                sysAttMainService.addAttachment(model, IKmArchivesMainService.FD_ATT_KEY, byteArray, fileName, "byte");
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @Override
    public void updateFileAttachement(KmArchivesMain kmArchivesMain,
                                      Map<String, IBaseModel> modelMap) throws Exception {
        List<?> attList = new ArrayList<>();
        for (IBaseModel mainModel : modelMap.values()) {
            if (mainModel != null) {
                List<?> list = sysAttMainService.findAttListByModel(
                        ModelUtil.getModelClassName(mainModel),
                        mainModel.getFdId());
                SysDictModel dict = SysDataDict.getInstance()
                        .getModel(ModelUtil.getModelClassName(mainModel));
                String messageKey = ResourceUtil
                        .getString(dict.getMessageKey());
                if (!ArrayUtil.isEmpty(list)) {
                    List<String> tmpFileKeyList = new ArrayList<String>();
                    //不需要进行档案处理的文件，主要是针对重复文件
                    List<SysAttMain> removeList = new ArrayList<SysAttMain>();
                    for (int i = 0; i < list.size(); i++) {
                        SysAttMain attMain = (SysAttMain) list.get(i);
                        String versionName = "";
                        if ("com.landray.kmss.km.agreement.model.KmAgreementApply".equals(attMain.getFdModelName())) {
                            if ("processDoc".equals(attMain.getFdKey()) && attMain.getFdFileName().indexOf("_V") < 0) {
                                versionName = ResourceUtil.getString("file.version.prefix", "km-archives") + " - ";
                            }
                        } else if ("com.landray.kmss.km.agreement.model.KmAgreementApplyMakeup".equals(attMain.getFdModelName())) {
                            if (!"attFianlContract".equals(attMain.getFdKey())) {
                                removeList.add(attMain);
                            } else {
                                messageKey = ResourceUtil
                                        .getString("km-agreement:table.kmAgreementReview");
                            }
                        }
                        String[] fileName = attMain.getFdFileName()
                                .split(" - ");
                        if (fileName.length > 1) {
                            attMain.setFdFileName(
                                    messageKey + " - "
                                            + versionName + fileName[fileName.length - 1]);
                        } else {
                            attMain.setFdFileName(
                                    messageKey + " - "
                                            + versionName + attMain.getFdFileName());
                        }
                        String fileExt = FilenameUtils.getExtension(attMain.getFdFileName());
                        if ("xls".equalsIgnoreCase(fileExt) || "xlsx".equalsIgnoreCase(fileExt)) {
                            String key = attMain.getFdFileId() + "*_*" + attMain.getFdFileName();
                            if (!tmpFileKeyList.contains(key)) {
                                tmpFileKeyList.add(key);
                            } else {
                                removeList.add(attMain);
                            }
                        }
                    }
                    if (!ArrayUtil.isEmpty(removeList)) {
                        list.removeAll(removeList);
                    }

                    ArrayUtil.concatTwoList(list, attList);
                }
            }
        }

        if (attList != null && attList.size() > 0) {
            for (int j = 0; j < attList.size(); j++) {
                SysAttMain attMain = (SysAttMain) attList.get(j);
                if (!isNotNeedWhenFile(attMain)) {
                    // 主文档附件当做归档临时附件
                    updateToPDFSourceAtt(kmArchivesMain, attMain);
                }
            }
        }

    }

    /**
     * 添加到待转pdf的临时附件区域，若为无需转PDF的格式，则跳过转换直接放到展示附件列表
     *
     * @param kmArchivesMain
     * @param attMain
     * @throws Exception
     */
    private void updateToPDFSourceAtt(KmArchivesMain kmArchivesMain, SysAttMain attMain) throws Exception {
        InputStream is = null;
        try {
            is = sysAttMainService.getInputStream(attMain);
            updateToPDFSourceAtt(kmArchivesMain, attMain.getFdFileName(), IOUtils.toByteArray(is), null);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    @Override
    public void updateFileArchives(KmArchivesMain mainModel) throws Exception {
        IKmArchivesMainService archiveMainService = (IKmArchivesMainService) SpringBeanUtil
                .getBean("kmArchivesMainService");
        archiveMainService.updateArchivesMain(mainModel);
    }

    @Override
    public void updateFilePrintArchivesPage(KmArchivesMain kmArchivesMain,
                                            String url, String fileName) throws Exception {
        updateFilePrintArchivesPageZoom(kmArchivesMain, url, fileName, "0.7");
    }

    /**
     * 设置自动归档的预览页面
     *
     * @param kmArchivesMain
     * @param url
     * @param fileName
     * @param zoom
     * @throws Exception
     */
    private void updateFilePrintArchivesPageZoom(KmArchivesMain kmArchivesMain, String url, String fileName, String zoom) throws Exception {
        HtmlToMht htmlToMht = new HtmlToMht(false);
        String serverUrl = ResourceUtil.getKmssConfigString("kmss.innerUrlPrefix");
        url = serverUrl + url;
        String page = htmlToMht.getClientPageArchives(url);

        page = new KmArchivesHtmlHandler().handlingHTMLAgainZoom(page, zoom);

        if (StringUtil.isNotNull(page)) {
            byte[] bytes = page.getBytes("UTF-8");
            // 主文档归档页面当做临时附件
            updateToPDFSourceAtt(kmArchivesMain, fileName, bytes, serverUrl);
        }
    }

    /**
     * 手动归档单个文档
     *
     * @param request          请求的Request
     * @param mainModel        主文档model
     * @param templateModel    主文档模板model
     * @param fileTemplate     KmArchivesFileTemplate
     * @param filePrintPageUrl 归档页面的链接，用于生成html
     */
    @Override
    public void addFileMainDoc(HttpServletRequest request, IBaseModel mainModel, IBaseModel templateModel,
                               IBeanEnhance<KmArchivesFileTemplate> fileTemplate, String filePrintPageUrl) throws Exception {
        if (KmArchivesUtil.isStartFile(ModuleDictUtil.getModuleId(mainModel))) {
            KmArchivesFileTemplate fTemplate = this.getFileTemplate(templateModel, null);
            // 有归档模板
            if (fTemplate != null) {
                addArchives(request, mainModel, fTemplate, filePrintPageUrl, getDocSubject(mainModel), getFilingFlagProperty(mainModel));
            } else if (null != request && "1".equals(request.getParameter("userSetting"))) { // 支持用户级配置
                addArchives(request, mainModel, fileTemplate.get(KmArchivesFileTemplate.class), filePrintPageUrl, getDocSubject(mainModel), getFilingFlagProperty(mainModel));
            }
        }
    }

    private void addArchives(HttpServletRequest request, IBaseModel mainModel, KmArchivesFileTemplate fileTemplate,
                             String filePrintPageUrl, String fileNamePrefix, String filingFlagPropertyName) throws Exception {
        KmArchivesMain kmArchivesMain = new KmArchivesMain();
        this.setFileField(kmArchivesMain, fileTemplate, mainModel);
        //保管年限格式控制
        if (kmArchivesMain.getFdPeriod() != null && !kmArchivesMain.getFdPeriod().matches("[0-9]+")) {
            kmArchivesMain.setFdPeriod(null);
        }
        String fileName = fileNamePrefix + ".html";
        this.setFilePrintPage(kmArchivesMain, request, filePrintPageUrl, fileName);

        // 找到与主文档绑定的所有附件
        this.setFileAttachement(kmArchivesMain, mainModel);
        this.addFileArchives(kmArchivesMain);
        if (UserOperHelper.allowLogOper("fileDoc", "*")) {
            UserOperContentHelper.putAdd(kmArchivesMain).putSimple("docTemplate", fileTemplate);
        }

        PropertyUtils.setProperty(mainModel, filingFlagPropertyName, true);

        if (UserOperHelper.allowLogOper("fileDoc", "*") || UserOperHelper.allowLogOper("fileDocAll", "*")) {
            UserOperContentHelper.putUpdate(mainModel);
        }
        IDynamicProxy service = ModuleCenter.findServiceByModel(mainModel);
        if (service != null) {
            service.invoke("update", mainModel);
        }
    }

    /**
     * 流程结束自动归档
     *
     * @param mainModel        主文档model
     * @param templateModel    主文档模板model
     * @param filePrintPageUrl 归档页面的链接，用于生成html
     */
    @Override
    public void addAutoFileMainDoc(IBaseModel mainModel, IBaseModel templateModel, String filePrintPageUrl) throws Exception {
        if (KmArchivesUtil.isStartFile(ModuleDictUtil.getModuleId(mainModel))) {
            KmArchivesFileTemplate fTemplate = this.getFileTemplate(templateModel, null);
            if (fTemplate == null) {
                //控制台打印不使用messageKey
                logger.warn("归档失败，模板Model(" + templateModel.getClass().getName() + ")无归档模板");
                return;
            }
            addSignArchives(mainModel, fTemplate, filePrintPageUrl, getDocCreator(mainModel), getDocSubject(mainModel), getFilingFlagProperty(mainModel));
        }
    }

    /**
     * 获取归档标记字段，如果主文档中不存在则抛出异常
     *
     * @param mainModel 主文档
     */
    private String getFilingFlagProperty(IBaseModel mainModel) {
        String filingFlagPropertyName = "fdIsFiling";
        if (!checkFiledExist(mainModel, filingFlagPropertyName)) {
            //开发阶段的异常不使用messageKey
            throw new IllegalArgumentException("归档失败，主文档无归档字段" + filingFlagPropertyName);
        }
        return filingFlagPropertyName;
    }

    /**
     * 判断对象中是否存在字段
     *
     * @param object    对象
     * @param fieldName 字段名
     */
    private boolean checkFiledExist(Object object, String fieldName) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        Stream<Field> stream = Arrays.stream(declaredFields);
        boolean exist = stream.noneMatch(field -> fieldName.equals(field.getName()));
        stream.close();
        return exist;
    }

    /**
     * 获取标题字段，如果主文档中不存在则抛出异常
     *
     * @param mainModel 主文档
     */
    private String getDocSubject(IBaseModel mainModel) {
        if (!(mainModel instanceof IDocSubjectModel)) {
            //开发阶段的异常不使用messageKey
            throw new IllegalArgumentException("归档失败，主文档应继承IDocSubjectModel接口");
        }
        return ((IDocSubjectModel) mainModel).getDocSubject();
    }

    /**
     * 获取创建者字段，如果主文档中不存在则抛出异常
     *
     * @param mainModel 主文档
     */
    private SysOrgPerson getDocCreator(IBaseModel mainModel) {
        if (!(mainModel instanceof IBaseCreateInfoModel)) {
            //开发阶段的异常不使用messageKey
            throw new IllegalArgumentException("归档失败，主文档应继承IBaseCreateInfoModel接口");
        }
        return ((IBaseCreateInfoModel) mainModel).getDocCreator();
    }

    private void addSignArchives(IBaseModel mainModel, KmArchivesFileTemplate fileTemplate, String filePrintPageUrl,
                                 SysOrgPerson docCreator, String fileNamePrefix, String filingFlagPropertyName) {
        KmArchivesMain kmArchivesMain = new KmArchivesMain();
        try {
            this.setFileField(kmArchivesMain, fileTemplate, mainModel);
            if (kmArchivesMain.getDocCreator() == null || kmArchivesMain.getDocCreator().isAnonymous()) {
                kmArchivesMain.setDocCreator(docCreator);
            }
            //流程自动归档
            String fileName = fileNamePrefix + ".html";
            this.setFilePrintArchivesPage(kmArchivesMain, filePrintPageUrl, fileName);

            // 找到与主文档绑定的所有附件
            this.setFileAttachement(kmArchivesMain, mainModel);
            this.addFileArchives(kmArchivesMain);
            getArchivesSignService().deleteSign(mainModel.getFdId());

            if (UserOperHelper.allowLogOper("fileDoc", "*")) {
                UserOperContentHelper.putAdd(kmArchivesMain).putSimple("docTemplate", fileTemplate);
            }

            PropertyUtils.setProperty(mainModel, filingFlagPropertyName, true);
            if (UserOperHelper.allowLogOper("fileDoc", "*") || UserOperHelper.allowLogOper("fileDocAll", "*")) {
                UserOperContentHelper.putUpdate(mainModel);
            }
            IDynamicProxy service = ModuleCenter.findServiceByModel(mainModel);
            if (service != null) {
                service.invoke("update", mainModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
