package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.eop.basedata.service.IEopBasedataSwitchService;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;


/**
 * 判断是否启用银企直联
 * @author
 *
 */
public class EopBasedataFsscCheckUseBankTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8846905641537390142L;
	
	private String fdBank;
	
	public String getFdBank() {
		return fdBank;
	}

	public void setFdBank(String fdBank) {
		this.fdBank = fdBank;
	}

	private IEopBasedataSwitchService  eopBasedataSwitchService;
	
	public IEopBasedataSwitchService getIEopBasedataSwitchService(){
		if(null==eopBasedataSwitchService){
			eopBasedataSwitchService = (IEopBasedataSwitchService)SpringBeanUtil.getBean("eopBasedataSwitchService");
		}
		return eopBasedataSwitchService;
	}

	@Override
    public int doStartTag()throws JspException{
	
		try {
			JSONObject obj =  getIEopBasedataSwitchService().bankOpenOrClose();
			if(StringUtils.isNotEmpty(fdBank)&&fdBank.contains(",")) {
				String[] fdBanks=fdBank.split(",");
				for(String fdBank_:fdBanks) {
					if(checkFbBank(fdBank_,obj)==1) {
						return 1;
					}
				}
				return 0;
			}else {
				return checkFbBank(fdBank,obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}


	public int checkFbBank(String fdBank,JSONObject obj ) {
		if("BOC".equals(fdBank)){
			if(obj.optString("result").indexOf("BOC")>-1&&null!=SysConfigs.getInstance().getModule("/fssc/boc/")){
				return 1;
			}else{
				return 0;
			}
		}else if("CMB".equals(fdBank)){
			if(obj.optString("result").indexOf("CMB")>-1&&null!=SysConfigs.getInstance().getModule("/fssc/cmb/")){
				return 1;
			}else{
				return 0;
			}
		}else if("CBS".equals(fdBank)){

			if(obj.optString("result").indexOf("CBS")>-1&&null!=SysConfigs.getInstance().getModule("/fssc/cbs/")){
				return 1;
			}else{
				return 0;
			}
		}else if("Yonfin".equals(fdBank)) {
			if(obj.optString("result").indexOf("Yonfin")>-1&&null!=SysConfigs.getInstance().getModule("/fssc/yonfin/")){
				return 1;
			}else{
				return 0;
			}
		}else if("CMInt".equals(fdBank)) {
			if(obj.optString("result").indexOf("CMInt")>-1&&null!=SysConfigs.getInstance().getModule("/fssc/cmbint/")){
				return 1;
			}else{
				return 0;
			}
		}else if("BANK".equals(fdBank)){
			if(StringUtil.isNull(obj.optString("result"))){
				return 0;
			}else{
				return 1;
			}
		}else{
			if(null!=SysConfigs.getInstance().getModule("/fssc/cmb/") || null!=SysConfigs.getInstance().getModule("/fssc/boc/")||null!=SysConfigs.getInstance().getModule("/fssc/cbs/")||null!=SysConfigs.getInstance().getModule("/fssc/yonfin/")
					||null!=SysConfigs.getInstance().getModule("/fssc/cmbint/")){
				return 1;
			}else{
				return 0;
			}
		}

	}
}
