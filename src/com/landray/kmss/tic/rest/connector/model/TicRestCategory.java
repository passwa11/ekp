package com.landray.kmss.tic.rest.connector.model;

import com.landray.kmss.tic.core.common.model.TicCoreBusiCate;
import com.landray.kmss.tic.rest.connector.forms.TicRestCategoryForm;

/**
 * TicRest服务分类
 * 
 */
@SuppressWarnings("serial")
public class TicRestCategory extends TicCoreBusiCate {
	@Override
    public Class<TicRestCategoryForm> getFormClass() {
		return TicRestCategoryForm.class;
	}
}
