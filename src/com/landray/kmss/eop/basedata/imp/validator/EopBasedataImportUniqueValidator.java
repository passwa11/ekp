package com.landray.kmss.eop.basedata.imp.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportColumn;
import com.landray.kmss.eop.basedata.imp.context.EopBasedataImportContext;
import com.landray.kmss.eop.basedata.imp.interfaces.IEopBasedataImportValidator;
import com.landray.kmss.util.StringUtil;

public class EopBasedataImportUniqueValidator extends IEopBasedataImportValidator{

	@Override
	public Boolean validate(EopBasedataImportContext ctx, EopBasedataImportColumn col, List<Object> data) throws Exception {
		String type = col.getFdType();
		//如果该字段不是必填且值为空，则不校验
		if(StringUtil.isNull((String) data.get(col.getFdColumn()))){
			return true;
		}
		//如果是对象类型字段，校验是否已存在
		if(EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_LIST.equals(type)||EopBasedataImportColumn.FSSC_BASE_IMPORT_COLUMN_TYPE_ENUMS.equals(type)){
			String value = (String) data.get(col.getFdColumn());
			Map<String,String> map = new HashMap<String,String>();
			String[] vals = value.split(";");
			for(String val:vals){
				if(map.containsKey(val)){
					return false;
				}
				map.put(val, val);
			}
		}
		return super.validate(ctx, col, data);
	}

}
