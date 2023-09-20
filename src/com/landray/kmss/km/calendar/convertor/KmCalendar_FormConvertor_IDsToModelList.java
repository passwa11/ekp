package com.landray.kmss.km.calendar.convertor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.convertor.FormConvertor_IDsToModelList;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.StringUtil;

/**
 * 将多个ID组成的字符串转换为有序的域模型列表，常用于用户列表
 */
public class KmCalendar_FormConvertor_IDsToModelList extends
		FormConvertor_IDsToModelList {

	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KmCalendar_FormConvertor_IDsToModelList.class);

	public KmCalendar_FormConvertor_IDsToModelList(String tPropertyName,
			Class modelClass) {
		super(tPropertyName, modelClass);
	}

	@Override
	public void excute(ConvertorContext ctx) throws Exception {
		RequestContext requestContext = ctx.getRequestContext();
		String s_fromApp = requestContext.getParameter("s_fromApp");
		if (StringUtil.isNotNull(s_fromApp)) {
			String ids;
			try {
				ids = (String) PropertyUtils.getProperty(ctx.getSObject(),
						ctx.getSPropertyName());
			} catch (NestedNullException e) {
				if (logger.isDebugEnabled()) {
                    logger.debug("获取属性" + ctx.getSPropertyName()
                            + "的值时中间出现null值，不转换该属性");
                }
				return;
			}
			if (ids == null) {
				if (logger.isDebugEnabled()) {
                    logger.debug("属性" + ctx.getSPropertyName()
                            + "的值为null，不转换该属性");
                }
				return;
			}
			List tList = (List) PropertyUtils.getProperty(ctx.getTObject(),
					getTPropertyName());
			if (tList == null) {
                tList = new ArrayList();
            } else {
                tList.clear();
            }
			if (!"".equals(ids.trim())) {
				String[] idArr = ids.split("\\s*[" + getSplitStr() + "]\\s*");
				for (int i = 0; i < idArr.length; i++) {
					// 后续改成拓展点,根据s_fromApp找不同字段查找
					IBaseModel tModel = findModelByKeyWord(ctx, idArr[i],
							"fdLoginName");
					if (tModel != null) {
                        tList.add(tModel);
                    }
				}
			}
			BeanUtils.copyProperty(ctx.getTObject(), getTPropertyName(), tList);
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
