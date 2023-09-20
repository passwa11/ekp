package com.landray.kmss.sys.time.service.spring;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.time.forms.SysTimeWorkForm;
import com.landray.kmss.sys.time.model.SysTimeCommonTime;
import com.landray.kmss.sys.time.service.ISysTimeCommonTimeService;
import com.landray.kmss.sys.time.service.ISysTimeWorkService;
import com.landray.kmss.sys.time.util.SysTimeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 创建日期 2008-一月-16
 * 
 * @author 易荣烽 班次设置业务接口实现
 */
public class SysTimeWorkServiceImp extends BaseServiceImp implements
		ISysTimeWorkService {

	@Override
	public String add(IBaseModel modelObj) throws Exception {
		SysTimeUtil.updateSignTimesCatch();
		return super.add(modelObj);
	}

	@Override
	public void delete(IBaseModel modelObj) throws Exception {
		SysTimeUtil.updateSignTimesCatch();
		super.delete(modelObj);
	}

	@Override
	public void update(IExtendForm form, RequestContext requestContext)
			throws Exception {
		String modelName = form.getModelClass().getName();
		modelName = StringUtil.isNotNull(modelName) ? modelName
				: getModelName();
		UserOperHelper.logUpdate(modelName);
		SysTimeWorkForm workForm = (SysTimeWorkForm) form;
		if ("1".equals(workForm.getTimeType())) {
			ISysTimeCommonTimeService commonTimeService = (ISysTimeCommonTimeService) SpringBeanUtil
					.getBean("sysTimeCommonTimeService");
			SysTimeCommonTime commonTimes = (SysTimeCommonTime) commonTimeService
					.findByPrimaryKey(workForm.getSysTimeCommonId());
			workForm.setFdTimeWorkColor(commonTimes.getFdWorkTimeColor());
		}
		IBaseModel model = convertFormToModel(form, null, requestContext);
		update(model);
	}
}
