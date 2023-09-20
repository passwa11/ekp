package com.landray.kmss.km.imeeting.model;

import java.util.ArrayList;
import java.util.List;

import com.landray.kmss.common.convertor.ModelConvertor_ModelListToString;
import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.km.imeeting.forms.KmImeetingResCategoryForm;
import com.landray.kmss.sys.simplecategory.model.SysSimpleCategoryAuthTmpModel;
import com.landray.kmss.util.ArrayUtil;

/**
 * 会议室分类
 */
public class KmImeetingResCategory extends SysSimpleCategoryAuthTmpModel {

	private static final long serialVersionUID = 8643964322857906101L;

	@Override
    public Class getFormClass() {
		return KmImeetingResCategoryForm.class;
	}

	private static ModelToFormPropertyMap toFormPropertyMap;

	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
		if (toFormPropertyMap == null) {
			toFormPropertyMap = new ModelToFormPropertyMap();
			toFormPropertyMap.putAll(super.getToFormPropertyMap());
			toFormPropertyMap.put("defReaders",
					new ModelConvertor_ModelListToString(
							"defReaderIds:defReaderNames", "fdId:fdName"));
		}
		return toFormPropertyMap;
	}

	@Override
    public void recalculateFields() {
		authAllReaders.clear();
		super.recalculateFields();
		ArrayUtil.concatTwoList(defReaders, authAllReaders);
	}

	/**
	 * 默认访问者
	 */
	protected List defReaders = new ArrayList();

	public List getDefReaders() {
		return defReaders;
	}

	public void setDefReaders(List defReaders) {
		this.defReaders = defReaders;
	}

}
