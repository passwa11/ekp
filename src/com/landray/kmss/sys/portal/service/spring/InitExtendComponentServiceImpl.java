package com.landray.kmss.sys.portal.service.spring;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.util.ResourceUtil;

/**
 * 监听系统启动完成事件
 *
 * @author 苏琦
 *
 */

public class InitExtendComponentServiceImpl implements InitializingBean {

    private static final Logger logger = LoggerFactory
            .getLogger(InitExtendComponentServiceImpl.class);


    @Override
    public void afterPropertiesSet() throws Exception {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("IniExtendComponentServiceImpl begin");
                try {
                    //目标路径
                    String desPath = ConfigLocationsUtil.getWebContentPath()
                            + XmlReaderContext.SYSPORTALUI;
                    //附件部件路径
                    String resourePath = ResourceUtil.KMSS_RESOURCE_PATH
                            + "/ui_component/";
                    File desFile = new File(desPath);
                    File resourceFile = new File(resourePath);
                    logger.info("目标路径:"+desPath);
                    logger.info("附件路径:"+resourePath);
                    if (!desFile.exists()) {
                        logger.info("目标路径不存在!");
                        // 创建目录
                        desFile.mkdir();
                        logger.info("创建目录完成！");
                        // 拷贝附件目录的部件包到sys/portal/template/ui_component/目录下
                        if (resourceFile.exists() && resourceFile.listFiles() != null && resourceFile.listFiles().length>0) {
                            logger.info("开始拷贝...");
                            FileUtils.copyDirectory(resourceFile, desFile);
                            logger.info("拷贝成功！");
                        }
                    }else{
                        logger.info("目标路径已存在！");
                        if(desFile.listFiles().length==0){
                            logger.info("目标路径目录为空");
                            // 拷贝附件目录的部件包到sys/portal/template/ui_component/目录下
                            if (resourceFile.exists() && resourceFile.listFiles().length>0) {
                                logger.info("附件目录不为空！");
                                logger.info("开始拷贝...");
                                FileUtils.copyDirectory(resourceFile, desFile);
                                logger.info("拷贝成功！");
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.info("IniExtendComponentServiceImpl  =============拷贝文件失败=========", e);
                }
                logger.info("IniExtendComponentServiceImpl end");
            }
        },"IniExtendComponentServiceImpl");
        t.start();
    }
}
