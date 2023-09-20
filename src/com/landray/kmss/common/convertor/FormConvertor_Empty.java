package com.landray.kmss.common.convertor;

import org.apache.commons.lang.StringUtils;

/**
 * Form的空转换器，常用于属性不执行转换
 * 
 * @author 缪贵荣
 * 
 */
public class FormConvertor_Empty implements IFormToModelConvertor {

	public static final IFormToModelConvertor INSTANCE = new FormConvertor_Empty();

	@Override
    public String getTPropertyName() {
		return StringUtils.EMPTY;
	}

	@Override
    public void excute(ConvertorContext ctx) throws Exception {
		// do nothing
	}

	@Override
    @SuppressWarnings("unchecked")
	public void examine(ExamineContext context, Class fromClass, Class toClass) {
		// do nothing
	}
}
