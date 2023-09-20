package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.forms.EopBasedataMaterialForm;
import com.landray.kmss.eop.basedata.model.EopBasedataMateCate;
import com.landray.kmss.eop.basedata.model.EopBasedataMateUnit;
import com.landray.kmss.eop.basedata.model.EopBasedataMaterial;
import com.landray.kmss.eop.basedata.model.ImportMaterialBean;
import com.landray.kmss.eop.basedata.service.IEopBasedataMaterialChangeService;
import com.landray.kmss.eop.basedata.service.IEopBasedataMaterialCheckService;
import com.landray.kmss.eop.basedata.service.IEopBasedataMaterialService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.framework.plugin.core.config.IExtension;
import com.landray.kmss.framework.plugin.core.config.IExtensionPoint;
import com.landray.kmss.framework.service.plugin.Plugin;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.number.interfaces.ISysNumberFlowService;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @author wangwh
 * @description:物料业务类实现类
 * @date 2021/5/7
 */
public class EopBasedataMaterialServiceImp extends ExtendDataServiceImp implements IEopBasedataMaterialService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;
    
    private ISysNumberFlowService sysNumberFlowService;

    private ISysAttMainCoreInnerService sysAttMainService;

    @Override
    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof EopBasedataMaterial) {
            EopBasedataMaterial eopBasedataMaterial = (EopBasedataMaterial) model;
        }
        return model;
    }

    @Override
    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        EopBasedataMaterial eopBasedataMaterial = new EopBasedataMaterial();
        eopBasedataMaterial.setDocCreateTime(new Date());
        eopBasedataMaterial.setFdStatus(Integer.valueOf("0"));
        eopBasedataMaterial.setDocCreator(UserUtil.getUser());
        EopBasedataUtil.initModelFromRequest(eopBasedataMaterial, requestContext);
        return eopBasedataMaterial;
    }

    @Override
    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        EopBasedataMaterial eopBasedataMaterial = (EopBasedataMaterial) model;
    }

    @Override
    public List<EopBasedataMaterial> findByFdUnit(EopBasedataMateUnit fdUnit) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMaterial.fdUnit.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdUnit.getFdId());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataMaterial> findByFdType(EopBasedataMateCate fdType) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMaterial.fdType.fdId=:fdId");
        hqlInfo.setParameter("fdId", fdType.getFdId());
        return this.findList(hqlInfo);
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    public ISysNumberFlowService getSysNumberFlowService() {
        if (sysNumberFlowService == null) {
        	sysNumberFlowService = (ISysNumberFlowService) SpringBeanUtil.getBean("sysNumberFlowService");
        }
        return sysNumberFlowService;
    }

    public ISysAttMainCoreInnerService getSysAttMainCoreInnerService() {
        if (sysAttMainService == null) {
            sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
        }
        return sysAttMainService;
    }
    
    @Override
    public String add(IBaseModel modelObj) throws Exception {
    	EopBasedataMaterial material = (EopBasedataMaterial) modelObj;
        String newNo = getSysNumberFlowService().generateFlowNumber(material);
        if(checkCodeExist(newNo)){
            throw new KmssRuntimeException(new KmssMessage("eop-basedata:materialCodeIsNotUnique"));
        }
        if(StringUtil.isNotNull(material.getFdErpCode())){
            if(checkErpCodeExist(material.getFdErpCode())){
                throw new KmssRuntimeException(new KmssMessage("eop-basedata:materialErpCodeIsNotUnique"));
            }
        }
        if(checkNameAndSpecsExist(material.getFdName(),material.getFdSpecs())){
            throw new KmssRuntimeException(new KmssMessage("eop-basedata:materialIsExist"));
        }
        material.setFdCode(newNo);
    	return super.add(material);
    }
    
    @Override
    public String findMaterialPrice(EopBasedataMaterial material) throws Exception {
        IBaseService clogService = (IBaseService) SpringBeanUtil.getBean("epsCommonPriceLibMainService");
        Method method = ReflectionUtils.findMethod(clogService.getClass(), "findMaterialPrice", String.class);
        return (String) method.invoke(clogService, material.getFdCode());
    }

    @Override
    public void addImportMaterial(List<ImportMaterialBean> list) throws Exception {
        for (int i = 0; i < list.size(); i++) {
            ImportMaterialBean bean = list.get(i);
            this.add(bean.getMaterial());
            this.setAttachment(bean.getPath(),bean.getMaterial());
        }
    }

    @Override
    public List<EopBasedataMaterial> findByNameOrErpCode(EopBasedataMaterial material) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = "eopBasedataMaterial.fdName=:fdName";
        if(StringUtil.isNotNull(material.getFdErpCode())){
            whereBlock += " or eopBasedataMaterial.fdErpCode=:fdErpCode";
            hqlInfo.setParameter("fdErpCode",material.getFdErpCode());
        }
        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("fdName",material.getFdName());
        return this.findList(hqlInfo);
    }

    @Override
    public List<EopBasedataMaterial> findByCodeOrErpCode(String code, String erpCode) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        StringBuilder whereBlock = new StringBuilder("eopBasedataMaterial.fdStatus=:fdStatus ");
        hqlInfo.setParameter("fdStatus",0);
        if(StringUtil.isNotNull(code)){
        	whereBlock.append( " and eopBasedataMaterial.fdCode=:fdCode ");
            hqlInfo.setParameter("fdCode",code);
        } 
        if(StringUtil.isNotNull(erpCode)){
        	whereBlock.append( " and eopBasedataMaterial.fdErpCode=:fdErpCode  ");
            hqlInfo.setParameter("fdErpCode",erpCode);
        }
        hqlInfo.setWhereBlock(whereBlock.toString());
        return this.findList(hqlInfo);
    }

    /**
     *
     * 设置附件
     *
     */
    private String setAttachment(String path, EopBasedataMaterial mainModel)
            throws Exception {
        String retunStr = "";
        if (StringUtil.isNull(path)) {
            return retunStr;
        }
        String[] attStrs = path.split("[;；]");
        for (int i = 0; i < attStrs.length; i++) {
            if (StringUtil.isNull(attStrs[i])) {
                continue;
            }
            File attFile = new File(attStrs[i]);
            if (attFile == null || !attFile.exists()) {
                if(attStrs[i].indexOf("\\")>=-1){ //含有符号“\”
                    attStrs[i] = attStrs[i].replace("\\", "\\\\");
                }
                retunStr = retunStr + attStrs[i] + ";";
            }
        }
        if (retunStr.length() > 0) {
            return "导入失败，" + retunStr + " 附件不存在 。请检查附件路径是否正确。";
        }

        for (int k = 0; k < attStrs.length; k++) {
            if (StringUtil.isNull(attStrs[k])) {
                continue;
            }
            String attName = attStrs[k].substring(attStrs[k].lastIndexOf(System
                    .getProperty("file.separator")) + 1, attStrs[k].length());
            File attFile = new File(attStrs[k]);
            if (attFile != null) {
                // String fileType = FilenameUtils.getExtension(attName);
                FileInputStream fileInputStream = new FileInputStream(attFile);
                getSysAttMainCoreInnerService().addAttachment(mainModel, "attOther",
                        fileInputStream, attName, "byte", Double
                                .valueOf(fileInputStream.available()),
                        attStrs[k]);
                IOUtils.closeQuietly(fileInputStream,null);
            }
        }
        return retunStr;
    }

    private boolean checkCodeExist(String fdCode) throws Exception {
        boolean flag = false;
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMaterial.fdCode=:fdCode");
        hqlInfo.setParameter("fdCode", fdCode);
        List<EopBasedataMaterial> list = this.findList(hqlInfo);
        if(!CollectionUtils.isEmpty(list)){
            flag = true;
        }
        return flag;
    }

    private boolean checkErpCodeExist(String erpCode) throws Exception{
        boolean flag = false;
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMaterial.fdErpCode=:fdErpCode");
        hqlInfo.setParameter("fdErpCode", erpCode);
        List<EopBasedataMaterial> list = this.findList(hqlInfo);
        if(!CollectionUtils.isEmpty(list)){
            flag = true;
        }
        return flag;
    }

    private boolean checkNameAndSpecsExist(String fdName, String fdSpecs) throws Exception{
        boolean flag = false;
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("eopBasedataMaterial.fdName=:fdName and eopBasedataMaterial.fdSpecs=:fdSpecs");
        hqlInfo.setParameter("fdName", fdName);
        hqlInfo.setParameter("fdSpecs", fdSpecs);
        List<EopBasedataMaterial> list = this.findList(hqlInfo);
        if(!CollectionUtils.isEmpty(list)){
            flag = true;
        }
        return flag;
    }

    /**
     * 判断批量物料是否存在于采购需求中，能否被删除
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @Override
    public boolean isDeleteByRequire(String[] ids) throws Exception {
        //通过id集合获取物料实体集合
        List<EopBasedataMaterial> list = null;
        if(null !=ids && ids.length>0) {
            list = this.findByPrimaryKeys(ids);
        }
        //通过拓展点在采购需求模块中进行判断
        IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.material.info.extend");
        IExtension[] extensions = point.getExtensions();
        boolean canDelete = true;
        if (extensions.length > 0) {
            for (IExtension extension : extensions) {
                if ("setting".equals(extension.getAttribute("name"))) {
                    String serviceName = Plugin.getParamValue(extension, "modelName");
                    //false为查询有值，不可删
                    canDelete = ((IEopBasedataMaterialCheckService) SpringBeanUtil.getBean(serviceName))
                            .checkMaterialCanDelete(list);
                }
            }
        }

        return canDelete;
    }

    @Override
    public void addImportMaterial(ImportMaterialBean importMaterialBean) throws Exception {
        this.add(importMaterialBean.getMaterial());
        this.setAttachment(importMaterialBean.getPath(), importMaterialBean.getMaterial());
	}
	@Override
	public void updatePre(EopBasedataMaterialForm eopBasedataMaterialForm) throws Exception {
		List<EopBasedataMaterial> list = new ArrayList<EopBasedataMaterial>();
		EopBasedataMaterial eopBasedataMaterial = (EopBasedataMaterial)convertFormToModel(eopBasedataMaterialForm, null, new RequestContext());
		list.add(eopBasedataMaterial);
		IExtensionPoint point = Plugin.getExtensionPoint("com.landray.kmss.eop.material.change.extend");
		IExtension[] extensions = point.getExtensions();
		if (extensions.length > 0) {
			for (IExtension extension : extensions) {
				if ("change".equals(extension.getAttribute("name"))) {
					String serviceName = Plugin.getParamValue(extension, "modelName");
					((IEopBasedataMaterialChangeService) SpringBeanUtil.getBean(serviceName)).updateMaterialInfo(list);
    }
			}
		}		
	}
}
