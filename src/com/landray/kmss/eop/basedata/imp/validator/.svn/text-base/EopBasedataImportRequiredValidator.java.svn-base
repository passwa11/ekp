package com.landray.kmss.eop.basedata.imp.validator;

import java.util.List;

import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.interfaces.IEopBasedataImportValidator;
import com.landray.kmss.util.StringUtil;

public class EopBasedataImportRequiredValidator extends IEopBasedataImportValidator{

	@Override
	public Boolean validate(EopBasedataImportContext ctx, EopBasedataImportColumn col, List<Object> data) throws Exception {
		String value = (String) data.get(col.getFdColumn());
		return StringUtil.isNotNull(value);
	}
	
}
