package com.landray.kmss.eop.basedata.imp.validator;

import java.util.List;

import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.interfaces.IEopBasedataImportValidator;
import com.landray.kmss.util.StringUtil;

public class EopBasedataImportRadioValidator extends IEopBasedataImportValidator{

	@Override
	public Boolean validate(EopBasedataImportContext ctx, EopBasedataImportColumn col, List<Object> data) throws Exception {
		String type = col.getFdType();
		//如果该字段不是必填且值为空，则不校验
		if(StringUtil.isNull((String) data.get(col.getFdColumn()))){
			return true;
		}
		if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_RADIO.equals(type)){
			String value = (String) data.get(col.getFdColumn());
			return value.split(";").length==1;
		}
		return super.validate(ctx, col, data);
	}

}
