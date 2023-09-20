package com.landray.kmss.third.pda.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToFormList;
import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.right.interfaces.BaseAuthModel;
import com.landray.kmss.third.pda.forms.PdaModuleConfigMainForm;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.UserUtil;

/**
 * 模块配置表
 * 
 * @author zhuangwl
 * @version 1.0 2011-03-03
 */
public class PdaModuleConfigMain extends BaseAuthModel {

	/**
	 * 模块中文名
	 */
	protected String fdName;

	/**
	 * @return 模块中文名
	 */
	public String getFdName() {
		return SysLangUtil.getPropValue(this, "fdName", this.fdName);
	}

	/**
	 * @param fdName
	 *            模块中文名
	 */
	public void setFdName(String fdName) {
		this.fdName = fdName;
		SysLangUtil.setPropValue(this, "fdName", fdName);
	}

	/**
	 * 排序号
	 */
	protected Integer fdOrder;
	/*
	 * 所属分类
	 */
	protected PdaModuleCate fdModuleCate;

	public PdaModuleCate getFdModuleCate() {
		return fdModuleCate;
	}

	public void setFdModuleCate(PdaModuleCate fdModuleCate) {
		this.fdModuleCate = fdModuleCate;
	}

	/**
	 * @return 排序号
	 */
	public Integer getFdOrder() {
		return fdOrder;
	}

	/**
	 * @param fdOrder
	 *            排序号
	 */
	public void setFdOrder(Integer fdOrder) {
		this.fdOrder = fdOrder;
	}

	/**
	 * 设置toFormMap
	 * 
	 * @param toFormPropertyMap
	 */
	public static void setToFormPropertyMap(
			ModelToFormPropertyMap toFormPropertyMap) {
		PdaModuleConfigMain.toFormPropertyMap = toFormPropertyMap;
	}

	/**
	 * 创建时间
	 */
	protected Date fdCreateTime;

	/**
	 * @return 创建时间
	 */
	public Date getFdCreateTime() {
		return fdCreateTime;
	}

	/**
	 * @param fdCreateTime
	 *            创建时间
	 */
	public void setFdCreateTime(Date fdCreateTime) {
		this.fdCreateTime = fdCreateTime;
	}

	/**
	 * 修改时间
	 */
	protected Date docAlterTime;

	/**
	 * @return 修改时间
	 */
	public Date getDocAlterTime() {
		return docAlterTime;
	}

	/**
	 * @param docAlterTime
	 *            修改时间
	 */
	public void setDocAlterTime(Date docAlterTime) {
		this.docAlterTime = docAlterTime;
	}

	/**
	 * 模块url前缀
	 */
	protected String fdUrlPrefix;

	public String getFdUrlPrefix() {
		return fdUrlPrefix;
	}

	public void setFdUrlPrefix(String fdUrlPrefix) {
		this.fdUrlPrefix = fdUrlPrefix;
	}
	
	/**
	 *  数据总数URL 
	 */
	private String fdCountUrl;
	
	public String getFdCountUrl() {
		return fdCountUrl;
	}

	public void setFdCountUrl(String fdCountUrl) {
		this.fdCountUrl = fdCountUrl;
	}

	/**
	 * 图标
	 */
	protected String fdIconUrl;

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
	 * 状态
	 */
	protected String fdStatus;

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
	 * 修改人
	 */
	protected SysOrgElement docAlteror;

	/**
	 * @return 修改人
	 */
	public SysOrgElement getDocAlteror() {
		return docAlteror;
	}

	/**
	 * @param docAlteror
	 *            修改人
	 */
	public void setDocAlteror(SysOrgElement docAlteror) {
		this.docAlteror = docAlteror;
	}

	@Override
	public Class getFormClass() {
		return PdaModuleConfigMainForm.class;
	}

	/**
	 * 子菜单类型
	 */
	private String fdSubMenuType;

	public String getFdSubMenuType() {
		return fdSubMenuType;
	}

	public void setFdSubMenuType(String fdSubMenuType) {
		this.fdSubMenuType = fdSubMenuType;
	}

	/**
	 * 子菜单文档时文档链接
	 */
	private String fdSubDocLink;

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
	protected List<PdaModuleConfigMain> fdSubModuleList = new ArrayList<PdaModuleConfigMain>();

	public List<PdaModuleConfigMain> getFdSubModuleList() {
		return fdSubModuleList;
	}

	public void setFdSubModuleList(List<PdaModuleConfigMain> fdSubModuleList) {
		this.fdSubModuleList = fdSubModuleList;
	}

	/**
	 * 子菜单为列表页签时,页签列表
	 */
	protected List<PdaModuleLabelList> fdLabelList = new ArrayList<PdaModuleLabelList>();

	public List<PdaModuleLabelList> getFdLabelList() {
		return fdLabelList;
	}

	public void setFdLabelList(List<PdaModuleLabelList> fdLabelList) {
		this.fdLabelList = fdLabelList;
	}

	/**
	 * 设置文档类型列表,在该模块子菜单类型为模块时无需设置.
	 */
	protected List<PdaModuleConfigView> fdViewItems = new ArrayList<PdaModuleConfigView>();

	public List<PdaModuleConfigView> getFdViewItems() {
		return fdViewItems;
	}

	public void setFdViewItems(List<PdaModuleConfigView> fdViewItems) {
		this.fdViewItems = fdViewItems;
	}
	
	@Override
	public Boolean getAuthReaderFlag() {
		if (ArrayUtil.isEmpty(getAuthReaders())) {
            authReaderFlag = new Boolean(true);
        } else {
            authReaderFlag = new Boolean(false);
        }
		return authReaderFlag;
	}
	
	@Override
	protected void recalculateReaderField() {
		super.recalculateReaderField();
		// 重新计算可阅读者
		if (authAllReaders == null) {
            authAllReaders = new ArrayList();
        } else {
            authAllReaders.clear();
        }

		if (getAuthReaderFlag().booleanValue()) {
			// 创建人属于外部组织，处理为空则所有人可访问的逻辑
			SysOrgElement creator = getDocCreator();
			if (creator == null) {
				creator = UserUtil.getUser();
			}
			if (BooleanUtils.isTrue(creator.getFdIsExternal())) {
				SysOrgElement parent = creator.getFdParent();
				// 增加所属组织（外部人员的可访问者为空时，仅限于该组织及子组织可访问）
				if (parent != null) {
					authAllReaders.add(parent);
				}
			} else {
				// everyone
				authAllReaders.add(UserUtil.getEveryoneUser());
			}
			// 因为外部人员默认不能查看阅读权限为空的文档，所以这里还需要加入外部维护者
			if (CollectionUtils.isNotEmpty(authAllEditors)) {
				List outter = new ArrayList();
				for (Object obj : authAllEditors) {
					SysOrgElement elem = (SysOrgElement) obj;
					if (BooleanUtils.isTrue(elem.getFdIsExternal())) {
						outter.add(elem);
					}
				}
				ArrayUtil.concatTwoList(outter, authAllReaders);
			}
			return;
		}

		authAllReaders.add(getDocCreator());

		List tmpList = getAuthOtherReaders();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		}
		tmpList = getAuthReaders();
		if (tmpList != null) {
			ArrayUtil.concatTwoList(tmpList, authAllReaders);
		}
		ArrayUtil.concatTwoList(authAllEditors, authAllReaders);
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
	public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("docCreator.fdId", "docCreatorId");
			toFormPropertyMap.put("docCreator.fdName", "docCreatorName");
			toFormPropertyMap.put("fdModuleCate.fdId", "fdModuleCateId");
			toFormPropertyMap.put("fdModuleCate.fdName", "fdModuleCateName");
			toFormPropertyMap.put("docAlteror.fdId", "docAlterorId");
			toFormPropertyMap.put("docAlteror.fdName", "docAlterorName");
			toFormPropertyMap.put("fdLabelList",
					new ModelConvertor_ModelListToFormList("fdLabelList"));
			toFormPropertyMap.put("fdViewItems",
					new ModelConvertor_ModelListToFormList("fdViewItems"));
			toFormPropertyMap.put("fdSubModuleList",
					new ModelConvertor_ModelListToString(
							"fdSubModuleIds:fdSubModuleNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
	public String getDocSubject() {
		// TODO 自动生成的方法存根
		return null;
	}
}
