package com.landray.kmss.sys.ui.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.attachment.model.SysAttBase;
import com.landray.kmss.sys.cluster.interfaces.Event_ClusterReady;
import com.landray.kmss.sys.cluster.interfaces.MessageCenter;
import com.landray.kmss.sys.cluster.interfaces.message.IMessage;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageQueue;
import com.landray.kmss.sys.cluster.interfaces.message.IMessageReceiver;
import com.landray.kmss.sys.cluster.interfaces.message.SimpleMessage;
import com.landray.kmss.sys.cluster.interfaces.message.UniqueMessageQueue;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.xml.DesignConfigLoader;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.filestore.location.interfaces.ISysFileLocationProxyService;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.util.DbUtils;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.filter.ResourceCacheFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author linxiuxian
 */
public class ResourceCacheListener
        implements ApplicationListener, IMessageReceiver, Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8037899126362783379L;

    protected IMessageQueue messageQueue = new UniqueMessageQueue();
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ResourceCacheListener.class);

    @Override
    public IMessageQueue getMessageQueue() {
        return messageQueue;
    }

    @Override
    public void receiveMessage(IMessage message) throws Exception {
        if (!(message instanceof SimpleMessage)) {
            return;
        }
        SimpleMessage msg = (SimpleMessage) message;
        if ("resourceCacheType".equals(msg.getType())) {

            //如果是json新消息，仅仅只是删除部件才会发出的信息，则直接处理，业务不继续往下操作了
            if(msg.getBody().indexOf("operate") > -1){
                deleteExtendUiCache(msg.getBody());
                return;
            }
            String cache = msg.getBody();
            String locaCache = ResourceCacheFilter.mobileCache;
            //说明是旧消息，并且把当前系统最后缓存时间发送出去，让其他服务器进行更新缓存
            if (Long.valueOf(locaCache).compareTo(Long.valueOf(cache)) > 0) {
                // 群集消息
                SimpleMessage newMessage = new SimpleMessage("resourceCacheType", locaCache);
                MessageCenter.getInstance().sendToOther(newMessage);
            } else {
                ResourceCacheFilter.cache = cache;
                ResourceCacheFilter.mobileCache = cache;

                refreshUiCache();
            }
        }
    }

    /**
     * 删除缓存部件，包括缓存和WebPath中的文件
     * @param msgBody
     * @return: void
     * @author: wangjf
     * @time: 2022/7/28 11:07 上午
     */
    private void deleteExtendUiCache(String msgBody){

        try {
            JSONObject msgObject = JSON.parseObject(msgBody);
            if("theme".equals(msgObject.getString("uiType"))){
                SysUiPluginUtil.deleteUiExtend(msgObject.getString("extendId"), msgObject.getString("uiType"));
            }else{
                SysUiPluginUtil.deleteUiExtend(msgObject.getString("extendId").replace("-","."), msgObject.getString("uiType"));
                File file = new File(PluginConfigLocationsUtil.getWebContentPath()+ XmlReaderContext.SYSPORTALUI + File.separator + msgObject.getString("extendId"));
                if(file.exists()){
                    FileUtils.deleteQuietly(file);
                }
            }

        }catch (Exception e){
            logger.error("删除缓存部件错误",e);
        }
    }


    public void refreshUiCache() {
        String key = SysFileLocationUtil.getOtherFileKey();
        switch (key) {
            case SysAttBase.ATTACHMENT_LOCATION_SERVER:
                // 刷新之前需要先拷贝资源附件目录中的组件到web的sys/portal/template/ui_component/目录下，否则就会出现空刷新
                copyExtendUi2WebPath();
                DesignConfigLoader.getXmlReaderContext().refresh();
                break;
            case SysAttBase.ALiYun_SERVER:
                try {
                    loadUiExtend();
                    //获取统一存储上的门户部件
                    loadUiComponentExtend();
                    DesignConfigLoader.getXmlReaderContext().refresh();
                } catch (Exception e) {
                    logger.error("从阿里云OSS更新扩展主题异常", e);
                }
                break;
            case SysAttBase.F4OSS_SERVER:
                break;
            default:
                break;
        }
    }

    /**
     * 从资源目录拷贝拓展部件至webPath
     * @param
     * @return: void
     * @author: wangjf
     * @time: 2022/7/25 4:56 下午
     */
   private void copyExtendUi2WebPath(){
       //目标路径
       String targetPath = ConfigLocationsUtil.getWebContentPath() + XmlReaderContext.SYSPORTALUI;
       File targetFile = new File(targetPath);
       //资源文件路径
       String resourcePath = ResourceUtil.KMSS_RESOURCE_PATH + "/ui_component/";
       File resourceFile = new File(resourcePath);
       if(!resourceFile.exists()){
           return;
       }
       try {
           if (!targetFile.exists()) {
               // 创建目录
               targetFile.mkdir();
               // 拷贝附件目录的部件包到sys/portal/template/ui_component/目录下  全量拷贝
               if (resourceFile.listFiles() != null && resourceFile.listFiles().length > 0) {
                   FileUtils.copyDirectory(resourceFile, targetFile);
               }
           }else{
               if(resourceFile.listFiles() == null || resourceFile.listFiles().length == 0){
                   return;
               }
               //以附件目录中的为主
               for (File file : resourceFile.listFiles()) {
                   if(file.isHidden() || file.isFile()){
                       continue;
                   }
                   File targetTemp = new File(targetPath + file.getName());
                   //先删除再拷贝
                   FileUtils.deleteQuietly(targetTemp);
                   FileUtils.copyDirectory(file, targetTemp);

               }
           }
       }catch (IOException e){
           logger.error("扩展部件附件拷贝异常", e);
       }
   }

    /**
     * 遍历本地所有扩展主题zip，和OSS的zip比较大小，不一样则删除本地zip，从OSS下载
     *
     * @throws Exception
     */
    private void loadUiExtend() throws Exception {
        File uiFolder = new File(ResourceUtil.KMSS_RESOURCE_PATH + "/" + XmlReaderContext.UIEXT);
        if (!uiFolder.exists()) {
            uiFolder.mkdirs();
        }
        ISysFileLocationProxyService fileService = SysFileLocationUtil.getFileService();
        boolean localEmpty = false;
        boolean serverEmpty = false;
        //本地路径下所有文件名
        List<String> localFiles = getLocalFiles(uiFolder);
        //云路径下所有文件名
        List<String> serverFiles = getServerFiles(fileService, "/" + XmlReaderContext.UIEXT + "/");
        if (CollectionUtils.isEmpty(localFiles)) {
            localEmpty = true;
        }
        if (CollectionUtils.isEmpty(serverFiles)) {
            serverEmpty = true;
        }
        if (localEmpty && serverEmpty) {
            //do nothing
        } else if (localEmpty && !serverEmpty) {
            for (String sf : serverFiles) {
                downloadFile(fileService, sf, uiFolder);
            }
        } else if (!localEmpty && serverEmpty) {
            List<File> exFiles = new ArrayList<File>();
            exFiles.add(new File(uiFolder, "login"));
            FileUtil.deleteFiles(uiFolder, exFiles);
        } else if (!localEmpty && !serverEmpty) {
            for (String lf : localFiles) {
                if (serverFiles.contains(lf)) {
                    updateFile(fileService, lf, uiFolder);
                } else {
                    String path = getUiFolderPath(uiFolder) + lf.replace(".zip", "");
                    File extFolder = new File(path);
                    File file = new File(getUiFolderPath(uiFolder) + lf);
                    FileUtils.deleteQuietly(extFolder);
                    FileUtils.deleteQuietly(file);
                }
            }
            for (String sf : serverFiles) {
                if (localFiles.contains(sf)) {
                    updateFile(fileService, sf, uiFolder);
                } else {
                    downloadFile(fileService, sf, uiFolder);
                }
            }
        }
    }

    private String getUiFolderPath(File uiFolder) throws IOException {
        return uiFolder.getCanonicalPath().replaceAll("\\\\", "/") + "/";
    }

    private void downloadFile(ISysFileLocationProxyService fileService, String fileName, File uiFolder) throws Exception {
        File zipFile = fileService.downloadFile(XmlReaderContext.UIEXT, fileName, XmlReaderContext.UIEXT);
        String path = getUiFolderPath(uiFolder) + fileName.replace(".zip", "");
        ZipUtil.unpack(zipFile, new File(path));
    }

    private void updateFile(ISysFileLocationProxyService fileService, String fileName, File uiFolder) throws Exception {
        String path = getUiFolderPath(uiFolder) + fileName.replace(".zip", "");
        File extFolder = new File(path);
        File file = new File(getUiFolderPath(uiFolder) + fileName);
        boolean same = fileService.compareFile(XmlReaderContext.UIEXT, XmlReaderContext.UIEXT, fileName);
        if (!same) {
            FileUtils.deleteQuietly(extFolder);
            FileUtils.deleteQuietly(file);
            File zipFile = fileService.downloadFile(XmlReaderContext.UIEXT, fileName, XmlReaderContext.UIEXT);
            ZipUtil.unpack(zipFile, extFolder);
        } else {
            if (!extFolder.exists()) {
                ZipUtil.unpack(file, extFolder);
            }
        }
    }

    /**
     * 获取服务器资源文件
     *
     * @param fileService
     * @param uiFolder
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/12/2 11:32 上午
     */
    private List<String> getServerFiles(ISysFileLocationProxyService fileService, String uiFolder) throws Exception {
        List<String> serverFiles = fileService.listFileNames(uiFolder);
        if (CollectionUtils.isEmpty(serverFiles)) {
            return Collections.EMPTY_LIST;
        }
        List<String> list = new ArrayList<>(serverFiles.size());
        for (String sf : serverFiles) {
            if (!sf.endsWith(".zip")) {
                continue;
            }
            list.add(sf.replace(uiFolder, ""));
        }
        return list;
    }

    private List<String> getLocalFiles(File uiFolder) {
        File[] uiext = uiFolder.listFiles();
        if (uiext == null || uiext.length == 0) {
            return Collections.EMPTY_LIST;
        }
        List<String> list = new ArrayList<>(uiext.length);
        for (File f : uiext) {
            if (StringUtils.equals("login", f.getName())) {
                //排除登录页文件
                continue;
            } else if (!f.getName().endsWith(".zip")) {
                continue;
            }
            list.add(f.getName());
        }
        return list;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {
            if (event instanceof Event_ClusterReady) {// 集群服务器启动完毕
                long lastModified = DbUtils.getDbTimeMillis();
                String cache = String.valueOf(lastModified);
                ResourceCacheFilter.cache = cache;
                ResourceCacheFilter.mobileCache = cache;

                // 群集消息
                SimpleMessage message = new SimpleMessage("resourceCacheType",
                        cache);
                MessageCenter.getInstance().sendToAll(message);
            }
        } catch (Exception e) {
            logger.error("系统启动发送资源缓存cacheID集群消息失败:" + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    /**
     * 加载服务器和本地门户部件，如果本地与oss服务器大小不一致则采取oss上的包
     *
     * @param
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/12/2 11:28 上午
     */
    private void loadUiComponentExtend() throws Exception {
        String localUiExtendFolderPath = File.separator + "ui_component" + File.separator;
        // 请注意OSS上面必须通过/来写路径，不允许出现windows中的\\
        String serverUiExtendFolderPath = "/ui_component/";
        String localResourcePath = ResourceUtil.KMSS_RESOURCE_PATH + localUiExtendFolderPath;
        // new File(PluginConfigLocationsUtil.getWebContentPath()) 之所以这么写是因为在windows系统中 通过PluginConfigLocationsUtil.getWebContentPath()获取出来的路径存在/e:/aaa/bb
        String localWebContentPath = new File(PluginConfigLocationsUtil.getWebContentPath()) + File.separator + "sys" + File.separator + "portal" + File.separator + "template" + localUiExtendFolderPath;
        File uiFolder = new File(localResourcePath);

        if (!uiFolder.exists()) {
            uiFolder.mkdirs();
        }
        ISysFileLocationProxyService fileService = SysFileLocationUtil.getFileService();
        boolean localEmpty = false;
        boolean serverEmpty = false;
        //本地路径下所有文件名
        List<String> localFiles = getLocalFiles(uiFolder);
        //云路径下所有文件名
        List<String> serverFiles = getServerFiles(fileService, serverUiExtendFolderPath);
        if (CollectionUtils.isEmpty(localFiles)) {
            localEmpty = true;
        }
        if (CollectionUtils.isEmpty(serverFiles)) {
            serverEmpty = true;
        }
        if (localEmpty && !serverEmpty) {
            // 本地不存在，oss服务器上存在
            for (String serverFile : serverFiles) {
                //下载统一存储oss资源，并解压
                downloadUiComponent(fileService, serverUiExtendFolderPath, localUiExtendFolderPath, serverFile, localResourcePath, localWebContentPath);
            }
        } else if (!localEmpty && serverEmpty) {
            // 本地存在，服务器不存在，需要删除资源目录、zip包和webContent中的数据
            for (String localFile : localFiles) {
                //删除本地文件
                deleteFile(localResourcePath, localWebContentPath, localFile);
            }
        } else if (!localEmpty && !serverEmpty) {
            //oss服务器和本地都存在，则先删除本地多余的数据，存在相同名字的则需要对比大小，如果与服务器大小不一致则以服务器的数据为准
            for (String localFile : localFiles) {
                if (serverFiles.contains(localFile)) {
                    boolean result = fileService.compareFile(localUiExtendFolderPath, serverUiExtendFolderPath, localFile);
                    //如果不相等
                    if (!result) {
                        //先删除本地文件
                        deleteFile(localResourcePath, localWebContentPath, localFile);
                        //下载统一存储oss资源，并解压
                        downloadUiComponent(fileService, serverUiExtendFolderPath, localUiExtendFolderPath, localFile, localResourcePath, localWebContentPath);
                    }
                } else {
                    //删除本地文件
                    deleteFile(localResourcePath, localWebContentPath, localFile);
                }
            }
            for (String serverFile : serverFiles) {
                if (!localFiles.contains(serverFile)) {
                    //下载统一存储oss资源，并解压
                    downloadUiComponent(fileService, serverUiExtendFolderPath, localUiExtendFolderPath, serverFile, localResourcePath, localWebContentPath);
                }
            }
        }
    }

    /**
     * 下载新文件
     *
     * @param fileService
     * @param serverUiExtendFolderPath
     * @param localUiExtendFolderPath
     * @param serverFile
     * @param localResourcePath
     * @param localWebContentPath
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/12/2 3:05 下午
     */
    private void downloadUiComponent(ISysFileLocationProxyService fileService, String serverUiExtendFolderPath, String localUiExtendFolderPath, String serverFile,
                                     String localResourcePath, String localWebContentPath) throws Exception {
        // 从服务器获取下载并且解压至本地资源目录和webContent目录
        File zipFile = fileService.downloadFile(serverUiExtendFolderPath, serverFile, localUiExtendFolderPath);
        String path = localResourcePath + serverFile.replace(".zip", "");
        String webContentPath = localWebContentPath + serverFile.replace(".zip", "");
        ZipUtil.unpack(zipFile, new File(path));
        //解压到webcontent中
        ZipUtil.unpack(zipFile, new File(webContentPath));
    }

    /**
     * 删除本地文件
     *
     * @param localResourcePath
     * @param localWebContentPath
     * @param localZipFile
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/12/2 2:52 下午
     */
    private void deleteFile(String localResourcePath, String localWebContentPath, String localZipFile) {
        //删除zip包
        FileUtils.deleteQuietly(new File(localResourcePath + localZipFile));
        //删除资源目录中的包
        FileUtils.deleteQuietly(new File(localResourcePath + localZipFile.replace(".zip", "")));
        //删除webContent下的包
        FileUtils.deleteQuietly(new File(localWebContentPath + localZipFile.replace(".zip", "")));
    }

    /**
     * 刷新资源缓存
     * @param
     * @return: void
     */
    public static void updateResourceCache() {
        try {
            long lastModified = DbUtils.getDbTimeMillis();
            String cache = String.valueOf(lastModified);
            ResourceCacheFilter.cache = cache;
            ResourceCacheFilter.mobileCache = cache;

            SimpleMessage message = new SimpleMessage("resourceCacheType", cache);
            // 群集消息
            MessageCenter.getInstance().sendToAll(message);
        }catch (Exception e) {
            logger.error("更新系统资源缓存cacheID失败:" + e.getMessage(), e);
        }
    }

    /**
     * 刷新资源缓存
     * @param bodyMap 消息内容题，在接收消息的地方如果该值不为空则需要进行判断
     * @return: void
     * @author: wangjf
     * @time: 2022/7/28 10:36 上午
     */
    public static void updateResourceCache(Map<String,String> bodyMap){

        try {
            String bodyString = JSON.toJSONString(bodyMap);
            SimpleMessage message = new SimpleMessage("resourceCacheType", bodyString);
            // 群集消息
            MessageCenter.getInstance().sendToAll(message);
        } catch (Exception e) {
            logger.error("更新系统资源缓存cacheID失败:" + e.getMessage(), e);
        }
    }
}
