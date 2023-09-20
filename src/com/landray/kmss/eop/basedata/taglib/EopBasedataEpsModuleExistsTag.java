package com.landray.kmss.eop.basedata.taglib;

import com.landray.kmss.sys.config.design.SysConfigs;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
/**
 * 判断是否存在对应模块,注意模块全部存在才显示
 * @author
 *
 */
public class EopBasedataEpsModuleExistsTag extends TagSupport {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int doStartTag()throws JspException {
        try {
            int size = 0;
            String[] pathes = path.split(";");
            for(String module:pathes){
                if(null!= SysConfigs.getInstance().getModule(module)){
                    ++size;
                }
            }
            //模块全部存在才返回1
            if(size == pathes.length){
                return 1;
            }else{
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
