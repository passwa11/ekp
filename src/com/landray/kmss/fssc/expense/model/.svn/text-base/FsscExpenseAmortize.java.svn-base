package com.landray.kmss.fssc.expense.model;

import com.landray.kmss.common.convertor.ModelToFormPropertyMap;
import com.landray.kmss.common.model.BaseModel;
import com.landray.kmss.fssc.expense.forms.FsscExpenseAmortizeForm;

/**
 *  摊销明细
 * @author wangjinman
 *
 */
public class FsscExpenseAmortize  extends BaseModel{
	private static ModelToFormPropertyMap toFormPropertyMap;
	/**
	 * 摊销比例
	 */
	private Double fdPercent;
	/**
	 * 受益期
	 */
	private String fdMonth;
	/**
	 * 摊销金额
	 */
	private Double fdMoney;
	
	@Override
	public Class<FsscExpenseAmortizeForm> getFormClass() {
		return FsscExpenseAmortizeForm.class;
	}
	@Override
    public ModelToFormPropertyMap getToFormPropertyMap() {
        if (toFormPropertyMap == null) {
            toFormPropertyMap = new ModelToFormPropertyMap();
            toFormPropertyMap.putAll(super.getToFormPropertyMap());
        }
        return toFormPropertyMap;
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
	public Double getFdMoney() {
		return fdMoney;
	}
	/**
	 * 摊销金额
	 */
	public void setFdMoney(Double fdMoney) {
		this.fdMoney = fdMoney;
	}
	public Double getFdPercent() {
		return fdPercent;
	}
	public void setFdPercent(Double fdPercent) {
		this.fdPercent = fdPercent;
	}

}
