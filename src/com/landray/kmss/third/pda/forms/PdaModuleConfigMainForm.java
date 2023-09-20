package com.landray.kmss.third.pda.forms;

import javax.servlet.http.HttpServletRequest;

import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.convertor.FormConvertor_FormListToModelList;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.BaseAuthForm;
import com.landray.kmss.third.pda.constant.PdaModuleConfigConstant;
import com.landray.kmss.third.pda.model.PdaModuleCate;
import com.landray.kmss.third.pda.model.PdaModuleConfigMain;
import com.landray.kmss.util.AutoArrayList;

/**
 * 模块配置表 Form
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleConfigMainForm extends BaseAuthForm {

	/**
	 * 模块中文名
	 */
	protected String fdName = null;

	/**
	 * @return 模块中文名
	 */
	public String getFdName() {
		return fdName;
	}

	/**
	 * @param fdName
	 *            模块中文名
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
	}
	/*
	 * 模块分类
	 */
	private String fdModuleCateId = null;

	public String getFdModuleCateId() {
		return fdModuleCateId;
	}

	public void setFdModuleCateId(String fdModuleCateId) {
		this.fdModuleCateId = fdModuleCateId;
	}

	public String getFdModuleCateName() {
		return fdModuleCateName;
	}

	public void setFdModuleCateName(String fdModuleCateName) {
		this.fdModuleCateName = fdModuleCateName;
	}
	private String fdModuleCateName = null;

	/**
	 * 排序号
	 */
	protected String fdOrder = null;

	/**
	 * @return 排序号
	 */
	public String getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(String fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 模块说明
	 */
	protected String fdDescription;

	public String getFdDescription() {
		return fdDescription;
	}

	public void setFdDescription(String fdDescription) {
		this.fdDescription = fdDescription;
	}

	/**
	 * 创建时间
	 */
	protected String fdCreateTime = null;

	/**
	 * @return 创建时间
	 */
	public String getFdCreateTime() {
		return fdCreateTime;
	}

	/**
	 * @param fdCreateTime
	 *            创建时间
	 */
	public void setFdCreateTime(String fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 修改时间
	 */
	protected String docAlterTime = null;

	/**
	 * @return 修改时间
	 */
	public String getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            修改时间
	 */
	public void setDocAlterTime(String docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 模块url前缀
	 */
	protected String fdUrlPrefix = null;

	public String getFdUrlPrefix() {
		return fdUrlPrefix;
	}

	public void setFdUrlPrefix(String fdUrlPrefix) {
		this.fdUrlPrefix = fdUrlPrefix;
	}
	
	protected String fdCountUrl = null;
	
	public String getFdCountUrl() {
		return fdCountUrl;
	}

	public void setFdCountUrl(String fdCountUrl) {
		this.fdCountUrl = fdCountUrl;
	}

	/**
	 * 图标
	 */
	protected String fdIconUrl = null;

	/**
	 * @return 图标
	 */
	public String getFdIconUrl() {
		return fdIconUrl;
	}

	/**
	 * @param fdIconUrl
	 *            图标
	 */
	public void setFdIconUrl(String fdIconUrl) {
		this.fdIconUrl = fdIconUrl;
	}

	/**
	 * 状态
	 */
	protected String fdStatus = null;

	/**
	 * @return 状态
	 */
	public String getFdStatus() {
		return fdStatus;
	}

	/**
	 * @param fdStatus
	 *            状态
	 */
	public void setFdStatus(String fdStatus) {
		this.fdStatus = fdStatus;
	}

	/**
	 * 创建人的ID
	 */
	protected String docCreatorId = null;

	/**
	 * @return 创建人的ID
	 */
	public String getDocCreatorId() {
		return docCreatorId;
	}

	/**
	 * @param docCreatorId
	 *            创建人的ID
	 */
	public void setDocCreatorId(String docCreatorId) {
		this.docCreatorId = docCreatorId;
	}

	/**
	 * 创建人的名称
	 */
	protected String docCreatorName = null;

	/**
	 * @return 创建人的名称
	 */
	public String getDocCreatorName() {
		return docCreatorName;
	}

	/**
	 * @param docCreatorName
	 *            创建人的名称
	 */
	public void setDocCreatorName(String docCreatorName) {
		this.docCreatorName = docCreatorName;
	}

	/**
	 * 修改人的ID
	 */
	protected String docAlterorId = null;

	/**
	 * @return 修改人的ID
	 */
	public String getDocAlterorId() {
		return docAlterorId;
	}

	/**
	 * @param docAlterorId
	 *            修改人的ID
	 */
	public void setDocAlterorId(String docAlterorId) {
		this.docAlterorId = docAlterorId;
	}

	/**
	 * 修改人的名称
	 */
	protected String docAlterorName = null;

	/**
	 * @return 修改人的名称
	 */
	public String getDocAlterorName() {
		return docAlterorName;
	}

	/**
	 * @param docAlterorName
	 *            修改人的名称
	 */
	public void setDocAlterorName(String docAlterorName) {
		this.docAlterorName = docAlterorName;
	}

	/**
	 * 子菜单类型:module;listTab;list;doc;app
	 */
	private String fdSubMenuType = null;

	public String getFdSubMenuType() {
		return fdSubMenuType;
	}

	public void setFdSubMenuType(String fdSubMenuType) {
		this.fdSubMenuType = fdSubMenuType;
	}

	/**
	 * 子菜单文档时文档链接
	 */
	private String fdSubDocLink = null;

	public String getFdSubDocLink() {
		return fdSubDocLink;
	}

	public void setFdSubDocLink(String fdSubDocLink) {
		this.fdSubDocLink = fdSubDocLink;
	}

	/**
	 * ekp集成模块配置链接
	 */
	private String fdEkpModuleUrl;
	
	
	public String getFdEkpModuleUrl() {
		return fdEkpModuleUrl;
	}

	public void setFdEkpModuleUrl(String fdEkpModuleUrl) {
		this.fdEkpModuleUrl = fdEkpModuleUrl;
	}
	
	/**
	 * 链接类型 0：内部链接 1：外部链接 （缺省为0：内部链接）
	 */
	private String fdLinkerType = "0";

	public String getFdLinkerType() {
		return fdLinkerType;
	}

	public void setFdLinkerType(String fdLinkerType) {
		this.fdLinkerType = fdLinkerType;
	}

	/**
	 * 子菜单为第三方应用时，应用类型，应用标识，应用下载地址
	 */
	private String fdAppType;

	private String fdUrlScheme;

	private String fdUrlDownLoad;

	public String getFdAppType() {
		return fdAppType;
	}

	public void setFdAppType(String fdAppType) {
		this.fdAppType = fdAppType;
	}

	public String getFdUrlScheme() {
		return fdUrlScheme;
	}

	public void setFdUrlScheme(String fdUrlScheme) {
		this.fdUrlScheme = fdUrlScheme;
	}

	public String getFdUrlDownLoad() {
		return fdUrlDownLoad;
	}

	public void setFdUrlDownLoad(String fdUrlDownLoad) {
		this.fdUrlDownLoad = fdUrlDownLoad;
	}

	/**
	 * 子菜单为模块时模块列表
	 */
	protected String fdSubModuleIds = null;

	public String getFdSubModuleIds() {
		return fdSubModuleIds;
	}

	public void setFdSubModuleIds(String fdSubModuleIds) {
		this.fdSubModuleIds = fdSubModuleIds;
	}

	protected String fdSubModuleNames = null;

	public String getFdSubModuleNames() {
		return fdSubModuleNames;
	}

	public void setFdSubModuleNames(String fdSubModuleNames) {
		this.fdSubModuleNames = fdSubModuleNames;
	}

	public static void setToModelPropertyMap(
			FormToModelPropertyMap toModelPropertyMap) {
		PdaModuleConfigMainForm.toModelPropertyMap = toModelPropertyMap;
	}

	/**
	 * 列表页签配置信息列表
	 */
	private AutoArrayList fdLabelList = new AutoArrayList(
			PdaModuleLabelListForm.class);

	public AutoArrayList getFdLabelList() {
		return fdLabelList;
	}

	public void setFdLabelList(AutoArrayList fdLabelList) {
		this.fdLabelList = fdLabelList;
	}

	/*
	 * 展示页面配置信息选项
	 */
	private AutoArrayList fdViewItems = new AutoArrayList(
			PdaModuleConfigViewForm.class);

	public AutoArrayList getFdViewItems() {
		return fdViewItems;
	}

	public void setFdViewItems(AutoArrayList fdViewItems) {
		this.fdViewItems = fdViewItems;
	}

	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdName = null;
		fdModuleCateId = null;
		fdModuleCateName = null;
		fdOrder = null;
		fdCreateTime = null;
		docAlterTime = null;
		fdUrlPrefix = null;
		fdCountUrl = null;
		fdIconUrl = null;
		fdStatus = "1";
		docCreatorId = null;
		docCreatorName = null;
		docAlterorId = null;
		docAlterorName = null;
		fdSubMenuType = PdaModuleConfigConstant.PDA_MENUS_LISTTAB;
		fdAppType = PdaModuleConfigConstant.PDA_APP_TYPE_APPLE;
		fdLabelList = new AutoArrayList(PdaModuleLabelListForm.class);
		fdViewItems = new AutoArrayList(PdaModuleConfigViewForm.class);
		super.reset(mapping, request);
	}

	@Override
    public Class getModelClass() {
		return PdaModuleConfigMain.class;
	}

	private static FormToModelPropertyMap toModelPropertyMap;

	@Override
    public FormToModelPropertyMap getToModelPropertyMap() {
		if (toModelPropertyMap == null) {
			toModelPropertyMap = new FormToModelPropertyMap();
			toModelPropertyMap.putAll(super.getToModelPropertyMap());
			toModelPropertyMap.put("docCreatorId", new FormConvertor_IDToModel(
					"docCreator", SysOrgElement.class));
			toModelPropertyMap.put("docAlterorId", new FormConvertor_IDToModel(
					"docAlteror", SysOrgElement.class));
			toModelPropertyMap.put("fdModuleCateId",
					new FormConvertor_IDToModel("fdModuleCate",
							PdaModuleCate.class));
			toModelPropertyMap.put("fdLabelList",
					new FormConvertor_FormListToModelList("fdLabelList",
							"pdaModuleLabelList"));
			toModelPropertyMap.put("fdViewItems",
					new FormConvertor_FormListToModelList("fdViewItems",
							"pdaModuleConfigView"));
			toModelPropertyMap.put("fdSubModuleIds",
					new FormConvertor_IDsToModelList("fdSubModuleList",
							PdaModuleConfigMain.class));
		}
		return toModelPropertyMap;
	}
}
