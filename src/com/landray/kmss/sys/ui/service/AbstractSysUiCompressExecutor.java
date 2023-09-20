package com.landray.kmss.sys.ui.service;

import com.landray.kmss.sys.ui.util.PcJsOptimizeUtil;
import com.landray.kmss.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ISysUiCompressExecutor的抽象实现<br/>
 * 用于接口实现类继承，提取公共逻辑。
 */
public abstract class AbstractSysUiCompressExecutor implements ISysUiCompressExecutor, ApplicationListener<ContextRefreshedEvent> {
    private static String contextPath;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String execute(String fileKey) {
        Object obj = getFileListMapping().get(fileKey);
        String src = "";
        if(obj instanceof String[]){
            //seajs形式
            String[] fileList = (String[]) obj;
            src = PcJsOptimizeUtil.getScriptSrc(getRelatePath() + fileKey + FIX_JS, Arrays.asList(fileList), true, false);
        } else if (obj instanceof Map) {
            //commonjs形式
            Map comJsMap = (Map<String, List<String>>) obj;
            src = PcJsOptimizeUtil.getCommonJsScriptSrc(getRelatePath() + fileKey + FIX_JS, comJsMap, Boolean.TRUE, Boolean.FALSE);
        }
        return src;
    }

    /**
     * 文件压缩错误时标准格式日志打印
     * @param fileName
     * @param t
     */
    protected void logFileCompressError(String fileName, Throwable t) {
        logger.error("合并压缩JS出错，miniPath:{}{}{},{}",COM_COMPRESS_SOURCE_PATH, getRelatePath() + fileName, t);
    }

    /**
     * 获取项目根路径
     * @return
     */
    protected String getContextPath(){
        if (contextPath == null) {
            try {
                contextPath = ((WebApplicationContext) SpringBeanUtil.getApplicationContext()).getServletContext().getContextPath();
            } catch (Exception e) {
                logger.error("获取contextPath失败，使用“”作为contextPath");
                contextPath = "";
            }
        }
        return contextPath;
    }

}
