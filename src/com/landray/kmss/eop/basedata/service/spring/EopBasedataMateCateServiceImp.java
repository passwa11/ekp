package com.landray.kmss.eop.basedata.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.forms.EopBasedataMateCateForm;
import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.eop.basedata.model.EopBasedataMaterial;
import com.landray.kmss.eop.basedata.service.IEopBasedataMateCateService;
import com.landray.kmss.eop.basedata.service.IEopBasedataMaterialChangeService;
import com.landray.kmss.eop.basedata.service.IEopBasedataMaterialService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.ftsearch.apache.commons.collections4.CollectionUtils;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangwh
 * @description:物料类别业务类实现类
 * @date 2021/5/7
 */
public class EopBasedataMateCateServiceImp extends ExtendDataServiceImp implements IEopBasedataMateCateService, IXMLDataBean {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;
	private IEopBasedataMaterialService eopBasedataMaterialService;
	public IEopBasedataMaterialService getEopBasedataMaterialService() {
        if (eopBasedataMaterialService == null) {
        	eopBasedataMaterialService = (IEopBasedataMaterialService) SpringBeanUtil.getBean("eopBasedataMaterialService");
        }
        return eopBasedataMaterialService;
    }

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataMateCate) {
            EopBasedataMateCate eopBasedataMateCate = (EopBasedataMateCate) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataMateCate eopBasedataMateCate = new EopBasedataMateCate();
        eopBasedataMateCate.setFdStatus(Integer.valueOf("0"));
        eopBasedataMateCate.setDocCreateTime(new Date());
        eopBasedataMateCate.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataMateCate, requestContext);
        return eopBasedataMateCate;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataMateCate eopBasedataMateCate = (EopBasedataMateCate) model;
    }

    @Override
    public List<EopBasedataMateCate> findByFdParent(EopBasedataMateCate fdParent) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMateCate.fdParent.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdParent.getFdId());
        return this.findList(hqlInfo);
    }

    /**
     * 根据名称查找物料类别
     * @param fdName
     * @return
     * @throws Exception
     */
    @Override
    public List<EopBasedataMateCate> findByName(String fdName) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMateCate.fdName=:fdName");
        hqlInfo.setParameter("fdName", fdName);
        return this.findList(hqlInfo);
    }
    
    @Override
	public List<EopBasedataMateCate> findByNameIgnoreSelf(String fdName, String fdId) throws Exception {
    	HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMateCate.fdName=:fdName and eopBasedataMateCate.fdId!=:fdId");
        hqlInfo.setParameter("fdName", fdName);
        hqlInfo.setParameter("fdId", fdId);
        return this.findList(hqlInfo);
	}

	@Override
	public List<EopBasedataMateCate> findByCodeIgnoreSelf(String fdCode, String fdId) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMateCate.fdCode=:fdCode and eopBasedataMateCate.fdId!=:fdId");
        hqlInfo.setParameter("fdCode", fdCode);
        hqlInfo.setParameter("fdId", fdId);
        return this.findList(hqlInfo);
	}

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }

    /**
     * 校验编码与名称唯一性
     * @param requestInfo
     *            数据请求信息
     * @return
     * @throws Exception
     */
    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        String fdName = requestInfo.getRequest().getParameter("fdName");
        String fdCode = requestInfo.getRequest().getParameter("fdCode");
        List rtnList = new ArrayList();
        boolean isExist = checkCodeOrNameExist(fdCode,fdName);
        rtnList.add(isExist);
        return rtnList;
    }

    private boolean checkCodeOrNameExist(String fdCode, String fdName) throws Exception {
        //验证code与name是否存在
        boolean flag = false;

        HQLInfo hqlInfo = new HQLInfo();
        String whereString = "eopBasedataMateCate.fdName=:fdName";
        hqlInfo.setParameter("fdName", fdName);
        if (StringUtil.isNotNull(fdCode)) {
            whereString += " or eopBasedataMateCate.fdCode = :fdCode";
            hqlInfo.setParameter("fdCode", fdCode);
        }
        hqlInfo.setWhereBlock(whereString);
        List<EopBasedataMateCate> results = findList(hqlInfo);
        if(CollectionUtils.isNotEmpty(results)) {
            flag = true;
        }
        return flag;
    }
    
    @Override
   	public EopBasedataMateCate getMateCateByCode(String fdTypeCode) throws Exception {
   		HQLInfo hqlInfo=new HQLInfo();
   		hqlInfo.setWhereBlock("eopBasedataMateCate.fdCode=:fdTypeCode and eopBasedataMateCate.fdStatus=:fdStatus");
   		hqlInfo.setParameter("fdTypeCode", fdTypeCode);
   		hqlInfo.setParameter("fdStatus", 0);
   		List<EopBasedataMateCate> typeList=this.findList(hqlInfo);
   		return ArrayUtil.isEmpty(typeList)?null:typeList.get(0);
   	}

    @Override
    public void updatePre(EopBasedataMateCateForm eopBasedataMateCateForm) throws Exception {
        //通过物料类别查到对应物料明细，直接调用物料明细动态拓展接口
        EopBasedataMateCate fdType = (EopBasedataMateCate)convertFormToModel(eopBasedataMateCateForm, null, new RequestContext());
        List<EopBasedataMaterial> eopBasedataMaterialList = getEopBasedataMaterialService().findByFdType(fdType);

        //通过拓展点在采购各模块中实现物料修改
        IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.material.change.extend");
        IExtension[] extensions = point.getExtensions();
        if (extensions.length > 0) {
            for (IExtension extension : extensions) {
                if ("change".equals(extension.getAttribute("name"))) {
                    String serviceName = Plugin.getParamValue(extension, "modelName");
                    //动态拓展调用实现类
                    ((IEopBasedataMaterialChangeService) SpringBeanUtil.getBean(serviceName)).updateMaterialInfo(eopBasedataMaterialList);
                }
            }
        }
    }

	
}
