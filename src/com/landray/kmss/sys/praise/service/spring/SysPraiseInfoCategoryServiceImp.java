package com.landray.kmss.sys.praise.service.spring;

import java.util.Date;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.praise.model.SysPraiseInfoCategory;
import com.landray.kmss.sys.praise.service.ISysPraiseInfoCategoryService;
import com.landray.kmss.sys.simplecategory.service.SysSimpleCategoryServiceImp;
import com.landray.kmss.util.UserUtil;

public class SysPraiseInfoCategoryServiceImp extends SysSimpleCategoryServiceImp
		implements ISysPraiseInfoCategoryService {

	@Override
    public String add(IBaseModel modelObj) throws Exception {
		SysPraiseInfoCategory sysPraiseInfoCategory = (SysPraiseInfoCategory) modelObj;
		if(sysPraiseInfoCategory.getDocCreator()==null){
			sysPraiseInfoCategory.setDocCreator(UserUtil.getUser());
		}
		if(sysPraiseInfoCategory.getDocCreateTime()==null){
			sysPraiseInfoCategory.setDocCreateTime(new Date());
		}
		return super.add(sysPraiseInfoCategory);
	}
}
