package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.model.EopBasedataSupType;
import com.landray.kmss.eop.basedata.model.EopBasedataSupplier;
import com.landray.kmss.eop.basedata.service.IEopBasedataSupTypeService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

/**
 * @author wangwh
 * @description:供应商类别业务类实现类
 * @date 2021/5/7
 */
public class EopBasedataSupTypeServiceImp extends ExtendDataServiceImp implements IEopBasedataSupTypeService,IXMLDataBean {

	private ISysNotifyMainCoreService sysNotifyMainCoreService;

	private ISysNumberFlowService sysNumberFlowService;

	public ISysNumberFlowService getSysNumberFlowService() {
		if (sysNumberFlowService == null) {
			sysNumberFlowService = (ISysNumberFlowService) SpringBeanUtil.getBean("sysNumberFlowService");
		}
		return sysNumberFlowService;
	}

	@Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context)
			throws Exception {
		model = super.convertBizFormToModel(form, model, context);
		if (model instanceof EopBasedataSupType) {
			EopBasedataSupType eopBasedataSupType = (EopBasedataSupType) model;
			eopBasedataSupType.setDocAlterTime(new Date());
			eopBasedataSupType.setDocAlteror(UserUtil.getUser());
		}
		return model;
	}

	@Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
		EopBasedataSupType eopBasedataSupType = new EopBasedataSupType();
		eopBasedataSupType.setDocCreateTime(new Date());
		eopBasedataSupType.setDocAlterTime(new Date());
		eopBasedataSupType.setFdStatus(Integer.valueOf("0"));
		eopBasedataSupType.setDocCreator(UserUtil.getUser());
		eopBasedataSupType.setDocAlteror(UserUtil.getUser());
		EopBasedataUtil.initModelFromRequest(eopBasedataSupType, requestContext);
		return eopBasedataSupType;
	}

	@Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext)
			throws Exception {
		EopBasedataSupType eopBasedataSupType = (EopBasedataSupType) model;
	}

	@Override
    public List<EopBasedataSupType> findByFdParent(EopBasedataSupType fdParent) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataSupType.fdParent.fdId=:fdId");
		hqlInfo.setParameter("fdId", fdParent.getFdId());
		return this.findList(hqlInfo);
	}

	public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
		if (sysNotifyMainCoreService == null) {
			sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
		}
		return sysNotifyMainCoreService;
	}

	private boolean checkCodeOrNameExist(String fdCode, String fdName) throws Exception {
		// 验证code与name是否存在
		boolean flag = false;

		HQLInfo hqlInfo = new HQLInfo();
		String whereString = "eopBasedataSupType.fdName=:fdName";
		hqlInfo.setParameter("fdName", fdName);
		if (StringUtil.isNotNull(fdCode)) {
			whereString += " or eopBasedataSupType.fdCode = :fdCode";
			hqlInfo.setParameter("fdCode", fdCode);
		}
		hqlInfo.setWhereBlock(whereString);
		List<EopBasedataSupType> results = findList(hqlInfo);
		if (CollectionUtils.isNotEmpty(results)) {
			flag = true;
		}
		return flag;
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		// 校验项目名称唯一性
		String fdName = requestInfo.getRequest().getParameter("fdName");
		String fdCode = requestInfo.getRequest().getParameter("fdCode");
		List rtnList = new ArrayList();
		boolean isExist = checkCodeOrNameExist(fdCode, fdName);
		rtnList.add(isExist);
		return rtnList;
	}

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		EopBasedataSupType eopBasedataSupType = EopBasedataSupType.class.cast(modelObj);
		String fdCode = getSysNumberFlowService().generateFlowNumber(eopBasedataSupType);
		eopBasedataSupType.setFdCode(fdCode);
		return super.add(modelObj);
	}

	@Override
	public boolean checkUnique(String fdId, String fdName) throws Exception {
		boolean flag = false;
		HQLInfo hqlInfo = new HQLInfo();
		String whereBlock = "eopBasedataSupType.fdName=:fdName";
		hqlInfo.setParameter("fdName", fdName);
		if (StringUtil.isNotNull(fdId)) {
			whereBlock += " and eopBasedataSupType.fdId!=:fdId";
			hqlInfo.setParameter("fdId", fdId);
		}
		hqlInfo.setWhereBlock(whereBlock);
		List<EopBasedataSupplier> list = this.findList(hqlInfo);
		if (org.springframework.util.CollectionUtils.isEmpty(list)) {
			flag = true;
		}
		return flag;
	}
}
