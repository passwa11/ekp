package com.landray.kmss.eop.basedata.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.landray.kmss.sys.config.design.SysConfigs;



/**
 * 判断是否存在对应模块
 * @author 
 *
 */
public class EopBasedataFsscModuleExistsTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8846905641537390142L;
	
	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
    public int doStartTag()throws JspException{
	
		try {
			String[] pathes = path.split(";");
			for(String module:pathes){
				if(null!=SysConfigs.getInstance().getModule(module)){
					return 1;
				}
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
