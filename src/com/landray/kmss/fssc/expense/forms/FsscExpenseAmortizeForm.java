package com.landray.kmss.fssc.expense.forms;

import javax.servlet.http.HttpServletRequest;


import com.landray.kmss.common.convertor.FormToModelPropertyMap;
import com.landray.kmss.common.forms.ExtendForm;
import com.landray.kmss.fssc.expense.model.FsscExpenseAmortize;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionMapping;

public class FsscExpenseAmortizeForm extends ExtendForm{
	
	private static FormToModelPropertyMap toModelPropertyMap;
	private String fdPercent;
	/**
	 * 受益期
	 */
	private String fdMonth;
	/**
	 * 摊销金额
	 */
	private String fdMoney;
	
	@Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
		fdMonth = null;
        fdMoney = null;
        fdPercent = null;
        super.reset(mapping, request);
    }

    @Override
    public Class<FsscExpenseAmortize> getModelClass() {
        return FsscExpenseAmortize.class;
    }

    @Override
    public FormToModelPropertyMap getToModelPropertyMap() {
        if (toModelPropertyMap == null) {
            toModelPropertyMap = new FormToModelPropertyMap();
            toModelPropertyMap.putAll(super.getToModelPropertyMap());
        }
        return toModelPropertyMap;
    }

    /**
	 * 受益期
	 */
	public String getFdMonth() {
		return fdMonth;
	}

	/**
	 * 受益期
	 */
	public void setFdMonth(String fdMonth) {
		this.fdMonth = fdMonth;
	}

	/**
	 * 摊销金额
	 */
	public String getFdMoney() {
		if(StringUtil.isNull(this.fdMoney)){
			return this.fdMoney;
		}
		return fdMoney;
	}

	/**
	 * 摊销金额
	 */
	public void setFdMoney(String fdMoney) {
		this.fdMoney = fdMoney;
	}

	public String getFdPercent() {
		return fdPercent;
	}

	public void setFdPercent(String fdPercent) {
		this.fdPercent = fdPercent;
	}
    
}
