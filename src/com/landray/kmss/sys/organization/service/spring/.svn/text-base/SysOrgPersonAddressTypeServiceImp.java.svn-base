package com.landray.kmss.sys.organization.service.spring;

import java.util.List;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.organization.forms.SysOrgPersonAddressTypeForm;
import com.landray.kmss.sys.organization.service.ISysOrgPersonAddressTypeService;
import com.landray.kmss.util.UserUtil;

/**
 * 创建日期 2008-十二月-17
 * 
 * @author 陈亮 个人地址本分类业务接口实现
 */
public class SysOrgPersonAddressTypeServiceImp extends BaseServiceImp implements
		ISysOrgPersonAddressTypeService {

	@Override
	public void updateAddress(List<SysOrgPersonAddressTypeForm> list,
							  RequestContext requestContext) throws Exception {
		List exist = findList("docCreator.fdId='"
				+ UserUtil.getUser().getFdId() + "'", "fdOrder");
		if (exist != null && !exist.isEmpty()) {
			for (int i = 0; i < exist.size(); i++) {
				delete((IBaseModel) exist.get(i));
			}
		}
		if (list != null && !list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IBaseModel model = convertFormToModel(list.get(i), null,
						requestContext);
				if (model == null) {
                    throw new NoRecordException();
                }
				update(model);
			}
		}
	}
}
