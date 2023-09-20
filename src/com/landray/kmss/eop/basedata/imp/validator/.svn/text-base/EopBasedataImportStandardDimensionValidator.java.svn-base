package com.landray.kmss.eop.basedata.imp.validator;

import java.util.List;

import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.interfaces.IEopBasedataImportValidator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class EopBasedataImportStandardDimensionValidator extends IEopBasedataImportValidator{

	@Override
	public Boolean validate(EopBasedataImportContext ctx, EopBasedataImportColumn col, List<Object> data) throws Exception {
		//如果该字段不是必填且值为空，则不校验
		if(StringUtil.isNull((String) data.get(col.getFdColumn()))){
			return true;
		}
		String value = (String) data.get(col.getFdColumn());
		String city = ResourceUtil.getString("enums.standard_dimension.6","eop-basedata");
		String area = ResourceUtil.getString("enums.standard_dimension.2","eop-basedata");
		if(value.contains(city)&&value.contains(area)) {
			return false;
		}
		return super.validate(ctx, col, data);
	}

}
