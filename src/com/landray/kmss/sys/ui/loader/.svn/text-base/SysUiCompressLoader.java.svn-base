package com.landray.kmss.sys.ui.loader;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.framework.loader.AbstractFrameworkLoader;
import com.landray.kmss.framework.plugin.core.factory.IListablePluginFactory;
import com.landray.kmss.framework.spring.core.IConfigLocationAware;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.ui.compressor.PcCompressService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.UnicodeReader;

/**
 *  PC端静态资源压缩，通过plugin.xml注册，在系统启动时执行。
 * @desc Create with IntelliJ IDEA 
 * @author lr-linyuchao
 * @date 2021-8-16
 */
public class SysUiCompressLoader extends AbstractFrameworkLoader {

    private static final Logger logger = LoggerFactory.getLogger(SysUiCompressLoader.class);
    //文件类型
    private List<String> types = Arrays.asList("js", "css");

    private final boolean isDebugEnabled = logger.isDebugEnabled();

    private final boolean isInfoEnabled = logger.isInfoEnabled();
    @Override
    public void doLoad(ServletContext servletContext, IListablePluginFactory iListablePluginFactory, String[] strings, Map<IConfigLocationAware.FrameworkName, String[]> map) {
        long st = System.currentTimeMillis();
        try {
            if(isInfoEnabled){
                logger.info("开始备份原版js以及生成压缩版js");
            }
            //读取admin.do中是否开启压缩静态资源的选项
            String compress = ResourceUtil
                    .getKmssConfigString("sys.ui.pc.compress.task.enabled");
			if ("true".equals(compress)) {
                PcCompressService.synchCompressAll();
                if(isDebugEnabled){
                    logger.debug("压缩pc端js结束 in "+(System.currentTimeMillis()-st));
                }
            } else {
                final String pathname = PluginConfigLocationsUtil.getWebContentPath();
                Thread t = new Thread("JS-compress-disposable-thread"){
                    @Override
                    public void run() {
                        long st = System.currentTimeMillis();
                        //删除逻辑，参照移动端删除逻辑，大致为:遍历目录文件，按文件格式以及后缀约定命名（*_src_.js,*_min_.js）过滤，进行删除.
                        remove(new File(pathname));
                        if(isDebugEnabled){
                            logger.debug("移除压缩pc端js in "+(System.currentTimeMillis()-st));
                        }
                    }
                };
                t.start();
            }

        } catch (Exception e) {
            if(isInfoEnabled){
                logger.info("压缩pc端js出错", e);
            }
        } finally {

        }
    }

    /**
     * 移除压缩开关生成的文件。
     * 先是写回原文件的内容，再删除生成的副本文件。
     * @param f
     */
    private void remove(File f) {
        File[] fs = f.listFiles();
        for (File file : fs) {
            if (file.isDirectory()) {
                remove(file);
            } else if (canRemove(file)) {
                file.delete();
                if(isDebugEnabled){
                    logger.debug("成功删除" + file.getName());
                }
            }
        }
    }

    /**
     * 判断是否能删除
     * @param delFile 待删除文件
     * @return
     */
    private boolean canRemove(File delFile) {
        String name = delFile.getName();
        for (String type : types) {
            if (name.lastIndexOf("_min_." + type) > -1 && name.lastIndexOf("._min_." + type) == -1) {
                //非移动端的压缩文件可直接删除 注：移动端压缩文件后缀为 ._min_.js
                return true;
            }
            if (name.lastIndexOf("_src_." + type) > -1) {
                //需要把文件内容写回去后方可删除
                return resetSrcFile(delFile, type);
            }
        }
        return false;
    }

    /**
     *  恢复压缩前源文件内容
     * @param delFile
     * @param extension
     * @return
     */
    private boolean resetSrcFile(File delFile, String extension){
        boolean result = false;
        Throwable t = null;
        FileInputStream fis = null;
        try {
            //完整路径
            String srcPath = delFile.getAbsolutePath();
            //源文件路径
            String tarPath = StringUtils.removeEnd(srcPath, "_src_." + extension) + "." + extension;
            File tarFile = new File(tarPath);
            if(tarFile.exists()){
                fis = new FileInputStream(delFile);
                StringBuilder sb = new StringBuilder(1000);
                sb.append(IOUtils.toString(new UnicodeReader(fis, "utf-8")));
                //以文本形式读取文件
                String txt = sb.toString();
                //写回原内容
                FileUtils.writeStringToFile(tarFile, txt, "utf-8");
            }
            result = true;
        } catch (Exception e) {
            t = e;
            logger.error("写回文件源过程出错", delFile.getAbsolutePath() ,e);
        } finally {
            IOUtils.closeQuietly(fis);
            if(t != null){
                result = false;
            }
        }
        return result;
    }

    @Override
    protected String getFileName() {
        return null;
    }

    @Override
    protected String getExtendFileName(String s) {
        return null;
    }

}
