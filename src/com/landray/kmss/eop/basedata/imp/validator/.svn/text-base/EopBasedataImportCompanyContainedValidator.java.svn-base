package com.landray.kmss.eop.basedata.imp.validator;

import java.util.List;
import java.util.Map;

import org.hibernate.query.Query;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportReference;
import com.landray.kmss.eop.basedata.imp.interfaces.IEopBasedataImportValidator;
import com.landray.kmss.eop.basedata.service.IEopBasedataCompanyService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.StringUtil;

import edu.emory.mathcs.backport.java.util.Arrays;
/**
 * 导入数据中包含公司列但未填写公司数据时，校验是否所有公司都含有当前数据
 * @author wangjinman
 *
 */
public class EopBasedataImportCompanyContainedValidator extends IEopBasedataImportValidator{
	private IEopBasedataCompanyService eopBasedataCompanyService;
	public void setEopBasedataCompanyService(IEopBasedataCompanyService eopBasedataCompanyService) {
		this.eopBasedataCompanyService = eopBasedataCompanyService;
	}
	@Override
	public Boolean validate(EopBasedataImportContext ctx, EopBasedataImportColumn col, List<Object> data) throws Exception {
		String value = (String) data.get(col.getFdColumn());
		EopBasedataImportReference rel = col.getFdRel();
		Boolean required = false;
		//如果当前字段不关联公司，不作校验
		for(Map<String,Object> map:rel.getFdFields()){
			if("fdCompany".equals(map.get("rel-column"))&&"ref".equals(map.get("type"))){
				required = true;
			}
		}
		if(!required){
			return true;
		}
		required = false;
		//如果当前字段不是必填，不作校验
		List<EopBasedataValidateContext> validators = col.getFdValidators();
		for(EopBasedataValidateContext val:validators){
			if("required".equals(val.getFdName())){
				required = true;
			}
		}
		if(!required){
			return true;
		}
		if(ctx.getColumnByProperty("fdCompany")==null||StringUtil.isNull(value)){
			return true;
		}
		String companyCode = (String) data.get(ctx.getColumnByProperty("fdCompany").getFdColumn());
		List<String> values = Arrays.asList(value.split(";"));
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setWhereBlock("eopBasedataCompany.fdIsAvailable=:fdIsAvailable and fdCode in(:code)");
		hqlInfo.setSelectBlock("eopBasedataCompany.fdId");
		hqlInfo.setParameter("fdIsAvailable",true);
		hqlInfo.setParameter("code",Arrays.asList(companyCode.split(";")));
		List<String> list = eopBasedataCompanyService.findList(hqlInfo);
		if(ArrayUtil.isEmpty(list)){
			return false;
		}
		String fdIsAvailable = "fdIsAvailable";
		Object setValue = true;
		//是否有效，会计科目和预算科目需要特殊处理
		String fdModelName = col.getFdProperty().getType();
		SysDictModel dict = SysDataDict.getInstance().getModel(fdModelName);
		if(dict.getPropertyMap().containsKey("fdStatus")){
			fdIsAvailable = "fdStatus";
			setValue = "0";
		}
		String hql = "select count(fdId) from "+col.getFdProperty().getType()+" model";
		hql+=" where model."+fdIsAvailable+"=:fdIsAvailable and model.";
		hql+=col.getFdRel().getFdKey()+" in(:value) and model.fdCompany.fdId in(:ids)";
		Query query = eopBasedataCompanyService.getBaseDao().getHibernateSession().createQuery(hql);
		query.setParameterList("value", values);
		query.setParameterList("ids", list);
		query.setParameter("fdIsAvailable", setValue);
		Number cnt = (Number) query.uniqueResult();
		cnt = cnt==null?0:cnt;
		return cnt.intValue()==values.size()*list.size();
	}

}
