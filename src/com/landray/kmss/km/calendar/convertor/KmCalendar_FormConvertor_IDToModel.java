package com.landray.kmss.km.calendar.convertor;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.convertor.FormConvertor_IDToModel;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;

/**
 * 将ID转换为域模型,暂时只用于处理人员转换（即要求modelClass为SysOrgPerson或者SysOrgElement）
 */
public class KmCalendar_FormConvertor_IDToModel extends FormConvertor_IDToModel {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmCalendar_FormConvertor_IDToModel.class);

	public KmCalendar_FormConvertor_IDToModel(String tPropertyName,
			Class modelClass) {
		super(tPropertyName, modelClass);
	}

	@Override
	public void excute(ConvertorContext ctx) throws Exception {
		RequestContext requestContext = ctx.getRequestContext();
		String s_fromApp = requestContext.getParameter("s_fromApp");
		Boolean mayOrgPerson = getModelClass() == SysOrgPerson.class
				|| getModelClass() == SysOrgElement.class;
		if (StringUtil.isNotNull(s_fromApp) && mayOrgPerson) {
			String id;
			try {
				id = (String) PropertyUtils.getProperty(ctx.getSObject(),
						ctx.getSPropertyName());
			} catch (NestedNullException e) {
				id = null;
				if (logger.isDebugEnabled()) {
                    logger.debug("获取属性值" + ctx.getSPropertyName()
                            + "时中间出现null值");
                }
			}
			if (id == null) {
                return;
            }
			if (id.trim().length() == 0) {
				BeanUtils.copyProperty(ctx.getTObject(), getTPropertyName(),
						null);
			} else {
				IBaseModel model = findModelByKeyWord(ctx, id, "fdLoginName");
				if (model != null) {
					BeanUtils.copyProperty(ctx.getTObject(),
							getTPropertyName(), model);
				} else {
					BeanUtils.copyProperty(
							ctx.getTObject(),
							getTPropertyName(),
							ctx.getBaseService().findByPrimaryKey(id,
									getModelClass(), false));
				}
			}
			return;
		}
		super.excute(ctx);
	}
	
	private IBaseModel findModelByKeyWord(ConvertorContext ctx, String id,
			String keyword) throws Exception {
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setModelName(SysOrgPerson.class.getName());
		// 后续做成拓展点,根据实际字段转成model
		hqlInfo.setWhereBlock("sysOrgPerson." + keyword + " = :" + keyword);
		hqlInfo.setParameter(keyword, id);
		List<SysOrgPerson> ps = (List<SysOrgPerson>) ctx.getBaseService()
				.findList(hqlInfo);
		if (ps.size() > 0) {
			return ps.get(0);
		} else {
			IBaseModel person = ctx.getBaseService().findByPrimaryKey(id,
					SysOrgElement.class, false);
			return person;
		}
	}

}
