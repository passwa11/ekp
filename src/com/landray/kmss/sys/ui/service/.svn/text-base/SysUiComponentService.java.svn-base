package com.landray.kmss.sys.ui.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.ui.forms.SysUiComponentForm;
import com.landray.kmss.sys.ui.util.IniUtil;
import com.landray.kmss.sys.ui.util.ResourceCacheListener;
import com.landray.kmss.sys.ui.util.ZipUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.web.upload.FormFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.springframework.transaction.TransactionStatus;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysUiComponentService implements IXMLDataBean {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());

    public void upload(SysUiComponentForm xform, RequestContext requestInfo)
            throws Exception {
        HttpServletRequest request = requestInfo.getRequest();
        // 检查部件包，并获取部件包信息
        JSONObject jsonInfo = null;
        FormFile file = xform.getFile();
        if (file != null) {
            jsonInfo = this.checkExtend(xform.getFile());
        } else if (StringUtil.isNotNull(xform.getFilePath())) {
            String fileName = xform.getFileName();
            String filePath = xform.getFilePath();
            InputStream is = null;
            try {
                is = new FileInputStream(filePath);
                jsonInfo = this.checkExtend(fileName, is);
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        // 部件ID
        String extendId = jsonInfo.getString("extendId");
        // 部件名称
        String extendName = jsonInfo.getString("extendName");
        // 部件包临时存放目录文件夹名称
        String folderName = jsonInfo.getString("folderName");
        // 根据部件包ID判断部件包在系统中是否已经存在
        boolean isExists = this.isExistsExtend(extendId);
        if (isExists) {
            String nameText = " " + extendId + "（" + extendName + "）";
            String errorMessage = ResourceUtil.getString(
                    "ui.help.luiext.component.exist.replace", "sys-ui", null,
                    nameText);
            request.setAttribute("errorMessage", errorMessage);
            request.setAttribute("extendId", extendId);
            request.setAttribute("folderName", folderName);
        } else {
            // 保存部件包
            this.saveExtend(extendId, folderName);
            request.setAttribute("successMessage", ResourceUtil.getString("ui.help.luiext.upload.success", "sys-ui"));
        }
        request.setAttribute("componentIsExits", isExists);
        if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
            UserOperHelper.setModelNameAndModelDesc(null, ResourceUtil.getString("sys-admin:home.nav.sysAdmin") + "("
                    + ResourceUtil.getString("sys-ui:ui.extend.upload") + ")");
        }
    }

    /**
     * 检测扩展部件包 （检查的主要内容：是否为空、文件格式、component.ini文件是否存在、ui.ini文件中是否有定义部件包ID）
     *
     * @param input
     * @return 返回JSONObject对象，包含部件包ID和临时文件夹名称
     * 例:{"extendId":"sky_blue","folderName":"16a43fc687dae13080c8a1244178efe8"}
     * @throws Exception
     */
    public JSONObject checkExtend(FormFile input) throws Exception {
        return checkExtend(input.getFileName(), input.getInputStream());
    }

    private JSONObject checkExtend(String fileName, InputStream is) throws Exception {
        JSONObject resultObj = new JSONObject();
        String ext = FilenameUtils.getExtension(fileName);
        if (StringUtil.isNull(ext)) {
            throw new Exception(ResourceUtil.getString("ui.help.luiext.selfile", "sys-ui"));
        }
        // 上传文件不是zip格式压缩包时提示“文件类型错误，只能是zip格式”
        if (!"zip".equals(ext)) {
            throw new Exception(ResourceUtil.getString("ui.help.luiext.upload.fileType", "sys-ui"));
        }
        // 获取临时目录，并在目录下新建一个zip文件
        String folderPath = System.getProperty("java.io.tmpdir");
        if (!folderPath.endsWith("/") && !folderPath.endsWith("\\")) {
            folderPath += "/";
        }
        // 获取部件包临时存放目录文件夹名称
        String folderName = IDGenerator.generateID();
        folderPath += folderName;
        File zipFile = new File(folderPath + ".zip");
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(zipFile);
            IOUtils.copy(is, output);
            output.close();
            ZipUtil.unZip(zipFile, folderPath);
            // 检查component.ini文件是否存在，如果不存在则提示“component.ini文件不存在”
            File iniFile = new File(folderPath + "/component.ini");
            if (!iniFile.exists()) {
                throw new Exception(ResourceUtil.getString(
                        "component.help.luiext.upload.file.notExists",
                        "sys-ui"));
            }
            // 检查ui.ini文件中是否有定义id，如果未定义则提示“component.ini文件里面没有id的定义”
            Map<String, String> map = IniUtil.loadIniFile(iniFile);
            String extendId = map.get("id"); // 部件包ID
            String extendName = map.get("name"); // 部件包名称
            if (StringUtil.isNull(extendId)) {
                throw new Exception(ResourceUtil.getString(
                        "component.help.luiext.upload.id.notExists", "sys-ui"));
            }
            resultObj.put("extendId", extendId);
            resultObj.put("extendName", extendName);
            resultObj.put("folderName", folderName);
            if (map.containsKey("thumb")) {
                resultObj.put("thumbnail", map.get("thumb"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                zipFile.delete();
            } catch (Exception e2) {
            }
            try {
                output.close();
            } catch (Exception e2) {
            }
        }
        return resultObj;
    }

    /**
     * 保存扩展部件包
     *
     * @param extendId   部件ID
     * @param folderName 部件包临时存放目录文件夹名称
     * @throws Exception
     */
    public void saveExtend(String extendId, String folderName) throws Exception {
        String folderPath = System.getProperty("java.io.tmpdir");
        if (!folderPath.endsWith("/") && !folderPath.endsWith("\\")) {
            folderPath += "/";
        }
        folderPath += folderName;
        // 将临时目录下的部件包内容复制到/sys/portal/template
        File extendFolder = new File(ConfigLocationsUtil.getWebContentPath() + XmlReaderContext.SYSPORTALUI + extendId);
        File resourcePath = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/ui_component/" + extendId);
        File sourcePath = new File(folderPath);
        FileUtils.copyDirectory(sourcePath, resourcePath); // 拷贝一份解压后的包到附件目录
        FileUtils.copyDirectory(sourcePath, extendFolder);
        // 在集群时上传至统一存储oss ，并且把文件zip包存放在本地资源文件夹 ui_component 目录中，为后面从集群环境中做比对提供依据
        SysFileLocationUtil.getFileService().writeOFolder(folderPath, "/ui_component/", extendId + ".zip", "/ui_component/", null);
        // 更新集群缓存信息
        ResourceCacheListener.updateResourceCache();
    }

    /**
     * 替换部件包（替换的逻辑是先根据部件包ID进行删除，然后保存新上传的部件包）
     *
     * @param request
     */
    public boolean replaceExtend(HttpServletRequest request) throws Exception {
        String extendId = request.getParameter("extendId");
        String folderName = request.getParameter("folderName");
        boolean bool = this.deleteExtendDirectory(extendId, null); // 根据部件包ID删除部件包文件目录
        if (bool) {
            saveExtend(extendId, folderName); // 保存扩展部件包
        }
        return bool;
    }

    /**
     * 根据部件包ID删除部件包文件目录
     *
     * @param extendId 部件包ID
     */
    public boolean deleteExtendDirectory(String extendId, String uiType) throws Exception {
        boolean bool = false; // 是否删除成功
        if (StringUtil.isNotNull(extendId)) {
            File extendFolder = new File(PluginConfigLocationsUtil.getWebContentPath() + XmlReaderContext.SYSPORTALUI + extendId);
            if (extendFolder.exists()) {
                // 删除 附件目录的部件包
                File file = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/ui_component/" + extendId);
                FileUtils.deleteQuietly(file);
                //删除/sys/portal/template/ui_component/下的部件文件
                FileUtils.deleteDirectory(extendFolder);
                // 删除统一存储oss上的文件和本地文件zip包
                SysFileLocationUtil.getFileService().deleteFile("/ui_component/" + extendId + ".zip");
                if(StringUtil.isNotNull(uiType)){
                    Map<String,String> map = new HashMap<>();
                    map.put("uiType", uiType);
                    map.put("operate", "delete");
                    map.put("extendId", extendId);
                    ResourceCacheListener.updateResourceCache(map);
                }else{
                    // 更新集群缓存信息
                    ResourceCacheListener.updateResourceCache();
                }
                bool = true;
            }
        }
        return bool;
    }

    /**
     * 根据部件包ID判断部件包是否已经存在
     *
     * @param extendId 部件包ID
     * @return
     */
    private boolean isExistsExtend(String extendId) {
        boolean bool = false;
        // 检查扩展包是否已经存在于系统中
        File extendFolder = new File(
                PluginConfigLocationsUtil.getWebContentPath() +
                        XmlReaderContext.SYSPORTALUI + extendId);
        if (extendFolder.exists()) {
            bool = true;
        }
        return bool;
    }

    @Override
    public List getDataList(RequestContext requestInfo) throws Exception {
        TransactionStatus status = null;
        try {
            status = TransactionUtils.beginNewTransaction();
            String fileName = requestInfo.getParameter("fileName");
            String filePath = requestInfo.getParameter("filePath");
            if (logger.isInfoEnabled()) {
                logger.info("正在处理部件包：" + filePath);
            }
            SysUiComponentForm mainForm = new SysUiComponentForm();
            mainForm.setFilePath(filePath);
            mainForm.setFileName(fileName);
            upload(mainForm, requestInfo);
            TransactionUtils.commit(status);
        } catch (Exception e) {
            TransactionUtils.rollback(status);
            logger.error("模板解析失败：", e);
            return faile(StringUtil.getStackTrace(e));
        }
        HttpServletRequest request = requestInfo.getRequest();
        Boolean componentIsExits = (Boolean) request
                .getAttribute("componentIsExits");
        String errorMessage = (String) request.getAttribute("errorMessage");
        String successMessage = (String) request.getAttribute("successMessage");
        String extendId = (String) request.getAttribute("extendId");
        String folderName = (String) request.getAttribute("folderName");

        JSONObject obj = new JSONObject();
        obj.put("componentIsExits", componentIsExits);
        obj.put("errorMessage", errorMessage);
        obj.put("successMessage", successMessage);
        obj.put("folderName", folderName);
        obj.put("extendId", extendId);
        return success(obj);
    }

    public List success(JSONObject obj) {
        JSONArray rtnData = new JSONArray();
        if (obj == null) {
            obj = new JSONObject();
        }
        obj.put("status", "2");
        rtnData.add(obj);
        return rtnData;
    }

    public List faile(String msg) {
        JSONArray rtnData = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("status", "3");
        obj.put("msg", msg);
        rtnData.add(obj);
        return rtnData;
    }
}
