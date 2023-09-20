package com.landray.kmss.sys.portal.service.spring;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.framework.util.PluginConfigLocationsUtil;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.model.SysAttRtfData;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.config.xml.XmlReaderContext;
import com.landray.kmss.sys.filestore.dao.ISysAttUploadDao;
import com.landray.kmss.sys.filestore.location.util.SysFileLocationUtil;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.sys.portal.dto.SysAttFileDTO;
import com.landray.kmss.sys.portal.dto.SysAttMainDTO;
import com.landray.kmss.sys.portal.dto.SysAttRtfDataDTO;
import com.landray.kmss.sys.portal.dto.SysFileStoreDTO;
import com.landray.kmss.sys.portal.dto.SysPortalHtmlDTO;
import com.landray.kmss.sys.portal.dto.SysPortalLinkDTO;
import com.landray.kmss.sys.portal.dto.SysPortalLinkDetailDTO;
import com.landray.kmss.sys.portal.dto.SysPortalMainDTO;
import com.landray.kmss.sys.portal.dto.SysPortalMainPageDTO;
import com.landray.kmss.sys.portal.dto.SysPortalMaterialMainDTO;
import com.landray.kmss.sys.portal.dto.SysPortalNavDTO;
import com.landray.kmss.sys.portal.dto.SysPortalPageDTO;
import com.landray.kmss.sys.portal.dto.SysPortalPageDetailDTO;
import com.landray.kmss.sys.portal.dto.SysPortalTopicDTO;
import com.landray.kmss.sys.portal.dto.SysPortalTreeDTO;
import com.landray.kmss.sys.portal.forms.SysPortalPackageForm;
import com.landray.kmss.sys.portal.model.SysPortalHtml;
import com.landray.kmss.sys.portal.model.SysPortalLink;
import com.landray.kmss.sys.portal.model.SysPortalLinkDetail;
import com.landray.kmss.sys.portal.model.SysPortalMain;
import com.landray.kmss.sys.portal.model.SysPortalMainPage;
import com.landray.kmss.sys.portal.model.SysPortalMaterialMain;
import com.landray.kmss.sys.portal.model.SysPortalNav;
import com.landray.kmss.sys.portal.model.SysPortalPage;
import com.landray.kmss.sys.portal.model.SysPortalPageDetail;
import com.landray.kmss.sys.portal.model.SysPortalTopic;
import com.landray.kmss.sys.portal.model.SysPortalTree;
import com.landray.kmss.sys.portal.service.ISysPortalHtmlService;
import com.landray.kmss.sys.portal.service.ISysPortalLinkService;
import com.landray.kmss.sys.portal.service.ISysPortalMainService;
import com.landray.kmss.sys.portal.service.ISysPortalMaterialMainService;
import com.landray.kmss.sys.portal.service.ISysPortalNavService;
import com.landray.kmss.sys.portal.service.ISysPortalPageService;
import com.landray.kmss.sys.portal.service.ISysPortalService;
import com.landray.kmss.sys.portal.service.ISysPortalTopicService;
import com.landray.kmss.sys.portal.service.ISysPortalTreeService;
import com.landray.kmss.sys.portal.util.PortalConstant;
import com.landray.kmss.sys.portal.util.PortalUtil;
import com.landray.kmss.sys.portal.util.ZipUtil;
import com.landray.kmss.sys.portal.xml.model.SysPortalFooter;
import com.landray.kmss.sys.portal.xml.model.SysPortalHeader;
import com.landray.kmss.sys.ui.plugin.SysUiPluginUtil;
import com.landray.kmss.sys.ui.util.ResourceCacheListener;
import com.landray.kmss.sys.ui.xml.model.SysUiFormat;
import com.landray.kmss.sys.ui.xml.model.SysUiLayout;
import com.landray.kmss.sys.ui.xml.model.SysUiPortlet;
import com.landray.kmss.sys.ui.xml.model.SysUiRender;
import com.landray.kmss.sys.ui.xml.model.SysUiTemplate;
import com.landray.kmss.util.FileMimeTypeUtil;
import com.landray.kmss.util.FileUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.TransactionUtils;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.util.XMLUtil;
import com.landray.kmss.web.upload.FormFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description: 门户导入导出
 * @author: wangjf
 * @time: 2021/6/14 5:01 下午
 * @version: 1.0
 */

public class SysPortalServiceImp implements ISysPortalService {
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(SysPortalServiceImp.class);

    private final static String XML_ROOT_START = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\t\t\t\t\t<root xmlns=\"http://www.landray.com.cn/schema/lui\" xmlns:degign=\"http://www.landray.com.cn/schema/design\" xmlns:portal=\"http://www.landray.com.cn/schema/portal\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"\n" +
            "\t\t\t\t\t\thttp://www.landray.com.cn/schema/lui\n" +
            "\t\t\t\t\t\t../../../sys/ui/lui.xsd\n" +
            "\t\t\t\t\t\thttp://www.landray.com.cn/schema/design\n" +
            "\t\t\t\t\t\t../../../design-xml.xsd\n" +
            "\t\t\t\t\t\thttp://www.landray.com.cn/schema/portal\n" +
            "\t\t\t\t\t\t../../../sys/portal/portal.xsd\">";
    private final static String XML_ROOT_END = "</root>";
    private final static String FILE_ENCODING = "UTF-8";

    //存放自定义组件的路径
    private final static String SYS_PORTAL_UI = File.separator + "sys" + File.separator + "portal" + File.separator + "template" + File.separator + "ui_component" + File.separator;

    /**
     * 自定义标签
     */
    private final static String HTML_UI_SOURCE = "ui:source";


    private ISysPortalMainService getSysPortalMainService() {
        ISysPortalMainService sysPortalMainService = (ISysPortalMainService) SpringBeanUtil.getBean("sysPortalMainService");
        return sysPortalMainService;
    }

    private ISysPortalPageService getSysPortalPageService() {
        ISysPortalPageService sysPortalPageService = (ISysPortalPageService) SpringBeanUtil.getBean("sysPortalPageService");
        return sysPortalPageService;
    }

    private ISysPortalHtmlService getSysPortalHtmlService() {
        ISysPortalHtmlService sysPortalHtmlService = (ISysPortalHtmlService) SpringBeanUtil.getBean("sysPortalHtmlService");
        return sysPortalHtmlService;
    }

    private ISysPortalLinkService getSysPortalLinkService() {
        ISysPortalLinkService sysPortalLinkService = (ISysPortalLinkService) SpringBeanUtil.getBean("sysPortalLinkService");
        return sysPortalLinkService;
    }

    private ISysPortalNavService getSysPortalNavService() {
        ISysPortalNavService sysPortalNavService = (ISysPortalNavService) SpringBeanUtil.getBean("sysPortalNavService");
        return sysPortalNavService;
    }

    private ISysAttMainCoreInnerService getSysAttMainService() {
        ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
        return sysAttMainService;
    }

    private ISysAttUploadService getSysAttUploadService() {
        ISysAttUploadService sysAttUploadService = (ISysAttUploadService) SpringBeanUtil.getBean("sysAttUploadService");
        return sysAttUploadService;
    }

    private ISysAttUploadDao getSysAttUploadDao() {
        ISysAttUploadDao sysAttUploadDao = (ISysAttUploadDao) SpringBeanUtil.getBean("sysAttUploadDao");
        return sysAttUploadDao;
    }

    private ISysPortalTreeService getSysPortalTreeService() {
        ISysPortalTreeService sysPortalTreeService = (ISysPortalTreeService) SpringBeanUtil.getBean("sysPortalTreeService");
        return sysPortalTreeService;
    }

    private ISysPortalTopicService getSysPortalTopicService() {
        ISysPortalTopicService sysPortalTopicService = (ISysPortalTopicService) SpringBeanUtil.getBean("sysPortalTopicService");
        return sysPortalTopicService;
    }

    private ISysPortalMaterialMainService getSysPortalMaterialMainService() {
        ISysPortalMaterialMainService sysPortalMaterialMainService = (ISysPortalMaterialMainService) SpringBeanUtil.getBean("sysPortalMaterialMainService");
        return sysPortalMaterialMainService;
    }

    /**
     * 导出SysPortalMain
     *
     * @param rootPath
     * @param sysPortalMain
     * @param fileStoreDTOList
     * @param requestContext
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/15 9:34 上午
     */
    private String exportSysPortalMain(String rootPath, SysPortalMain sysPortalMain, List<SysFileStoreDTO> fileStoreDTOList, RequestContext requestContext) throws Exception {
        String filePath = rootPath + PortalConstant.SYS_PORTAL_MAIN_EXPORT_PREFIX + sysPortalMain.getFdId();
        //导出sysPortalMain
        SysPortalMainDTO sysPortalMainDTO = new SysPortalMainDTO();
        BeanUtils.copyProperties(sysPortalMain, sysPortalMainDTO);
        sysPortalMainDTO.setContextPath(requestContext.getContextPath());
        String sysPortalMainStr = JSONObject.toJSONString(sysPortalMainDTO);
        //如果存在logo则导出
        if (StringUtil.isNotNull(sysPortalMain.getFdLogo())) {
            exportSysPortalLogo(rootPath, sysPortalMain.getFdLogo(), fileStoreDTOList);
        }
        FileUtil.createFile(filePath, sysPortalMainStr, FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出SysPortalMainPage
     *
     * @param rootPath
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/15 9:39 上午
     */
    private String exportSysPortalMainPage(String rootPath, String mainId, List<SysPortalMainPage> list) throws Exception {
        String filePath = rootPath + PortalConstant.SYS_PORTAL_MAIN_PAGE_EXPORT_PREFIX + mainId;
        //导出sysPortalMainPage
        List<SysPortalMainPageDTO> sysPortalMainPageDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SysPortalMainPage sysPortalMainPage : list) {
                SysPortalMainPageDTO sysPortalMainPageDTO = new SysPortalMainPageDTO();
                BeanUtils.copyProperties(sysPortalMainPage, sysPortalMainPageDTO);
                sysPortalMainPageDTO.setFdMainId(sysPortalMainPage.getSysPortalMain().getFdId());
                sysPortalMainPageDTO.setFdPortalPageId(sysPortalMainPage.getSysPortalPage().getFdId());
                sysPortalMainPageDTOList.add(sysPortalMainPageDTO);
            }
        }
        String sysPortalMainPageDTOListStr = JSONArray.toJSONString(sysPortalMainPageDTOList);
        FileUtil.createFile(filePath, sysPortalMainPageDTOListStr, FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出页面
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/15 10:05 上午
     */
    private String exportSysPortalPage(String rootPath, String mainId, List<SysPortalPage> list) throws Exception {

        String filePath = rootPath + PortalConstant.SYS_PORTAL_PAGE_EXPORT_PREFIX + mainId;
        //导出
        List<SysPortalPageDTO> sysPortalPageDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SysPortalPage sysPortalPage : list) {
                SysPortalPageDTO sysPortalPageDTO = new SysPortalPageDTO();
                BeanUtils.copyProperties(sysPortalPage, sysPortalPageDTO);
                sysPortalPageDTOList.add(sysPortalPageDTO);
            }
        }
        String sysPortalPageDTOListStr = JSONArray.toJSONString(sysPortalPageDTOList);
        FileUtil.createFile(filePath, sysPortalPageDTOListStr, FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出页面详情
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @param fileStoreDTOList
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/17 9:52 上午
     */
    private String exportSysPortalPageDetail(String rootPath, String mainId, List<SysPortalPageDetail> list, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        String filePath = rootPath + PortalConstant.SYS_PORTAL_PAGE_DETAIL_EXPORT_PREFIX + mainId;
        //导出
        List<SysPortalPageDetailDTO> sysPortalPageDetailDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (SysPortalPageDetail sysPortalPageDetail : list) {
                SysPortalPageDetailDTO sysPortalPageDetailDTO = new SysPortalPageDetailDTO();
                BeanUtils.copyProperties(sysPortalPageDetail, sysPortalPageDetailDTO);
                sysPortalPageDetailDTO.setFdPageId(sysPortalPageDetail.getSysPortalPage().getFdId());
                sysPortalPageDetailDTOList.add(sysPortalPageDetailDTO);
                //把logo文件拷贝出来
                if (StringUtil.isNotNull(sysPortalPageDetail.getFdLogo())) {
                    exportSysPortalLogo(rootPath, sysPortalPageDetail.getFdLogo(), fileStoreDTOList);
                }
            }
        }
        String sysPortalPageDetailDTOListStr = JSONArray.toJSONString(sysPortalPageDetailDTOList);
        FileUtil.createFile(filePath, sysPortalPageDetailDTOListStr, FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出logo图片
     *
     * @param rootPath
     * @param logoDbPath
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/24 5:09 下午
     */
    private void exportSysPortalLogo(String rootPath, String logoDbPath, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        String attFileSourcePath = ResourceUtil.KMSS_RESOURCE_PATH + logoDbPath;
        String[] split = logoDbPath.split("\\/");
        String logoPath = rootPath + File.separator + "logo" + File.separator;
        if (!FileUtil.getFile(logoPath).exists()) {
            FileUtil.createDir(logoPath, true);
        }
        String packagePath = logoPath + split[split.length - 1];
        //如果资源文件在资源目录中存在，并且在目标目录中不存在则拷贝
        if (FileUtil.getFile(attFileSourcePath).exists() && !FileUtil.getFile(packagePath).exists()) {
            FileUtil.copy(attFileSourcePath, packagePath);
            //保存存储位置
            String targetPath = logoDbPath.replace(split[split.length - 1], "");
            //包的内部路径
            SysFileStoreDTO sysFileStoreDTO = new SysFileStoreDTO();
            sysFileStoreDTO.setFdId(split[split.length - 1]);
            sysFileStoreDTO.setPackagePath("logo");
            sysFileStoreDTO.setTargetPath(targetPath);
            fileStoreDTOList.add(sysFileStoreDTO);
        }


    }

    /**
     * 根据标签获取该标签上的fdId
     *
     * @param tagName
     * @param refName
     * @param list
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/6/15 4:32 下午
     */
    private List<String> getTagVarFdId(String tagName, String refName, List<SysPortalPageDetail> list) {
        return getTagVarFdId(tagName, refName, "var-fdId", list);
    }

    /**
     * 处理某些标签不一样
     *
     * @param tagName
     * @param refName
     * @param valueName
     * @param list
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/7/2 10:03 下午
     */
    private List<String> getTagVarFdId(String tagName, String refName, String valueName, List<SysPortalPageDetail> list) {
        List<String> portalHtmlIdsList = new ArrayList<>();
        for (SysPortalPageDetail sysPortalPageDetail : list) {
            if (StringUtil.isNotNull(sysPortalPageDetail.getFdJsp())) {

                Document document = Jsoup.parseBodyFragment(sysPortalPageDetail.getFdJsp());
                Elements uiSourceTags = document.getElementsByTag(tagName);
                for (Element element : uiSourceTags) {
                    if (refName.equals(element.attr("ref")) && StringUtil.isNotNull(element.attr(valueName))) {
                        if (element.attr(valueName).indexOf(",") > -1) {
                            String[] ids = element.attr(valueName).split(",");
                            for (String id : ids) {
                                if (StringUtil.isNotNull(id)) {
                                    portalHtmlIdsList.add(id);
                                }
                            }
                        } else {
                            portalHtmlIdsList.add(element.attr(valueName));
                        }
                    }
                }

            }
        }
        return portalHtmlIdsList;
    }

    /**
     * 根据标签获取标签上面的值
     *
     * @param attr
     * @param list
     * @param tagNames 可变参数
     * @description:
     * @return: java.util.Set<java.lang.String>
     * @author: wangjf
     * @time: 2021/6/28 6:14 下午
     */
    private Set<String> getTagRef(String attr, List<SysPortalPageDetail> list, String... tagNames) {
        Set<String> tagRefValueSet = new HashSet<>();
        for (SysPortalPageDetail sysPortalPageDetail : list) {
            if (StringUtil.isNotNull(sysPortalPageDetail.getFdJsp())) {

                Document document = Jsoup.parseBodyFragment(sysPortalPageDetail.getFdJsp());
                for (String tagName : tagNames) {
                    Elements tagNameTags = document.getElementsByTag(tagName);
                    for (Element element : tagNameTags) {
                        if (StringUtil.isNotNull(element.attr(attr))) {
                            tagRefValueSet.add(element.attr(attr));
                        }
                    }
                }

            }
        }
        return tagRefValueSet;
    }

    /**
     * 导出自定义HTML
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/15 1:58 下午
     */
    private String exportSysPortalHtml(String rootPath, String mainId, RequestContext requestContext, List<SysPortalPageDetail> list) throws Exception {

        List<SysPortalHtmlDTO> sysPortalHtmlDTList = new ArrayList<>();

        List<String> tagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.html.source", list);
        List<String> anonymTagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.html.anonym.source", list);
        if (!CollectionUtils.isEmpty(anonymTagVarFdIds)) {
            tagVarFdIds.addAll(anonymTagVarFdIds);
        }
        if (CollectionUtils.isEmpty(tagVarFdIds)) {
            return null;
        }

        List<SysPortalHtml> dbList = new ArrayList<>();
        for (String id : tagVarFdIds) {
            SysPortalHtml sysPortalHtml = (SysPortalHtml) getSysPortalHtmlService().findByPrimaryKey(id, SysPortalHtml.class, true);
            if (sysPortalHtml != null) {
                dbList.add(sysPortalHtml);
            }
        }
        if (CollectionUtils.isEmpty(dbList)) {
            return null;
        }

        for (SysPortalHtml sysPortalHtml : dbList) {

            SysPortalHtmlDTO sysPortalHtmlDTO = new SysPortalHtmlDTO();
            BeanUtils.copyProperties(sysPortalHtml, sysPortalHtmlDTO);
            sysPortalHtmlDTO.setContextPath(requestContext.getContextPath());
            sysPortalHtmlDTList.add(sysPortalHtmlDTO);
        }
        String filePath = rootPath + PortalConstant.SYS_PORTAL_HTML_EXPORT_PREFIX + mainId;
        //判断是否已经存在数据，如果存在则需要先把文件数据读取出来，并叠加在一起
        if (FileUtil.getFile(filePath).exists()) {
            String fileContext = FileUtil.getFileString(filePath, FILE_ENCODING);
            if (StringUtil.isNotNull(fileContext)) {
                List<SysPortalHtmlDTO> fileSysPortalHtmlDTOList = JSONArray.parseArray(fileContext, SysPortalHtmlDTO.class);
                sysPortalHtmlDTList.addAll(fileSysPortalHtmlDTOList);
                FileUtil.deleteFile(filePath);
            }
        }
        if (CollectionUtils.isEmpty(sysPortalHtmlDTList)) {
            return null;
        }
        //过滤重复数据
        List<SysPortalHtmlDTO> collect = sysPortalHtmlDTList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        FileUtil.createFile(filePath, JSONArray.toJSONString(collect), FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出门户快捷方式
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/16 9:43 上午
     */
    private List<String> exportSysPortalLink(String rootPath, String mainId, String refName, List<SysPortalPageDetail> list, List<SysFileStoreDTO> fileStoreDTOList, List<KeyValue> headerVarsSourceList) throws Exception {
        // 导出link信息
        List<SysPortalLinkDTO> sysPortalLinkDTOList = new ArrayList<>();
        //查找标签并获取标签值
        List<String> tagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, refName, list);
        //把页眉上面的ID和页面的ID合并
        if (!CollectionUtils.isEmpty(headerVarsSourceList)) {
            List<String> collect = headerVarsSourceList.stream().map(item -> item.getValue()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                tagVarFdIds.addAll(collect);
            }
        }
        if (CollectionUtils.isEmpty(tagVarFdIds)) {
            return null;
        }

        List<SysPortalLink> sysPortalLinkList = new ArrayList<>();
        for (String id : tagVarFdIds) {
            SysPortalLink sysPortalLink = (SysPortalLink) getSysPortalLinkService().findByPrimaryKey(id, SysPortalLink.class, true);
            if (sysPortalLink != null) {
                sysPortalLinkList.add(sysPortalLink);
            }
        }
        //如果数据库都不存在这些数据则不执行下面的操作
        if (CollectionUtils.isEmpty(sysPortalLinkList)) {
            return null;
        }
        //存储detail信息
        List<SysPortalLinkDetailDTO> sysPortalLinkDetailDTOList = new ArrayList<>();
        for (SysPortalLink sysPortalLink : sysPortalLinkList) {

            SysPortalLinkDTO sysPortalLinkDTO = new SysPortalLinkDTO();
            BeanUtils.copyProperties(sysPortalLink, sysPortalLinkDTO);
            sysPortalLinkDTOList.add(sysPortalLinkDTO);

            if (!CollectionUtils.isEmpty(sysPortalLink.getFdLinks())) {
                //把详情存储，当做详情导出使用
                for (SysPortalLinkDetail sysPortalLinkDetail : sysPortalLink.getFdLinks()) {
                    SysPortalLinkDetailDTO sysPortalLinkDTOTemp = new SysPortalLinkDetailDTO();
                    BeanUtils.copyProperties(sysPortalLinkDetail, sysPortalLinkDTOTemp);
                    sysPortalLinkDTOTemp.setSysPortalLinkId(sysPortalLink.getFdId());
                    sysPortalLinkDetailDTOList.add(sysPortalLinkDTOTemp);
                }
            }
        }
        List<String> fileList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(sysPortalLinkDTOList)) {
            String filePath = rootPath + PortalConstant.SYS_PORTAL_LINK_EXPORT_PREFIX + mainId;
            //判断是否已经存在数据，如果存在则需要先把文件数据读取出来，并叠加在一起
            if (FileUtil.getFile(filePath).exists()) {
                String fileContext = FileUtil.getFileString(filePath, FILE_ENCODING);
                if (StringUtil.isNotNull(fileContext)) {
                    List<SysPortalLinkDTO> fileSysPortalLinkDTOList = JSONArray.parseArray(fileContext, SysPortalLinkDTO.class);
                    sysPortalLinkDTOList.addAll(fileSysPortalLinkDTOList);
                    FileUtil.deleteFile(filePath);
                }
            }
            //过滤重复数据
            List<SysPortalLinkDTO> collect = sysPortalLinkDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
            String sysPortalLinkDTOListStr = JSONArray.toJSONString(collect);
            FileUtil.createFile(filePath, sysPortalLinkDTOListStr, FILE_ENCODING);
            fileList.add(filePath);
        }

        if (!CollectionUtils.isEmpty(sysPortalLinkDetailDTOList)) {

            List<String> collectImg = sysPortalLinkDetailDTOList.stream().filter(item -> StringUtil.isNotNull(item.getFdImg())).map(item -> item.getFdImg()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collectImg)) {
                List<String> attMainUrlList = splitAttMainUrl(collectImg);
                //导出自定义素材库中的图片信息
                exportAttMainMaterial(rootPath, mainId, attMainUrlList, fileStoreDTOList);
            }
            //导出linkDetail
            String detailFilePath = rootPath + PortalConstant.SYS_PORTAL_LINK_DETAIL_EXPORT_PREFIX + mainId;
            //判断是否已经存在数据，如果存在则需要先把文件数据读取出来，并叠加在一起
            if (FileUtil.getFile(detailFilePath).exists()) {
                String fileContext = FileUtil.getFileString(detailFilePath, FILE_ENCODING);
                if (StringUtil.isNotNull(fileContext)) {
                    List<SysPortalLinkDetailDTO> fileSysPortalLinkDetailDTOList = JSONArray.parseArray(fileContext, SysPortalLinkDetailDTO.class);
                    sysPortalLinkDetailDTOList.addAll(fileSysPortalLinkDetailDTOList);
                    FileUtil.deleteFile(detailFilePath);
                }
            }
            List<SysPortalLinkDetailDTO> collect = sysPortalLinkDetailDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
            String sysPortalLinkDetailDTOListStr = JSONArray.toJSONString(collect);
            FileUtil.createFile(detailFilePath, sysPortalLinkDetailDTOListStr, FILE_ENCODING);
            fileList.add(detailFilePath);
        }
        return fileList;
    }

    /**
     * 导出快捷方式
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/6/16 11:03 上午
     */
    private List<String> exportSysPortalShortcut(String rootPath, String mainId, List<SysPortalPageDetail> list, List<SysFileStoreDTO> fileStoreDTOList, List<KeyValue> headerVarsSourceList) throws Exception {
        List<String> filePathList = new ArrayList<>();
        List<String> shortcutList = exportSysPortalLink(rootPath, mainId, "sys.portal.shortcut.source", list, fileStoreDTOList, headerVarsSourceList);
        if (!CollectionUtils.isEmpty(shortcutList)) {
            filePathList.addAll(shortcutList);
        }
        List<String> anonymList = exportSysPortalLink(rootPath, mainId, "sys.portal.shortcut.anonym.source", list, fileStoreDTOList, null);
        if (!CollectionUtils.isEmpty(anonymList)) {
            filePathList.addAll(anonymList);
        }
        return filePathList;
    }

    /**
     * @param rootPath
     * @param mainId
     * @param list
     * @description: sys.portal.linking.source
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/6/16 11:05 上午
     */
    private List<String> exportSysPortalLinking(String rootPath, String mainId, List<SysPortalPageDetail> list, List<SysFileStoreDTO> fileStoreDTOList, List<KeyValue> headerVarsSourceList) throws Exception {

        List<String> filePathList = new ArrayList<>();
        List<String> linkingList = exportSysPortalLink(rootPath, mainId, "sys.portal.linking.source", list, fileStoreDTOList, headerVarsSourceList);
        if (!CollectionUtils.isEmpty(linkingList)) {
            filePathList.addAll(linkingList);
        }
        List<String> anonymList = exportSysPortalLink(rootPath, mainId, "sys.portal.linking.anonym.source", list, fileStoreDTOList, null);
        if (!CollectionUtils.isEmpty(anonymList)) {
            filePathList.addAll(anonymList);
        }
        return filePathList;
    }

    /**
     * @param rootPath
     * @param mainId
     * @param list
     * @description: 系统导航 导出标签
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/16 10:47 上午
     */
    private String exportSysPortalSysNavTag(String rootPath, String mainId, List<SysPortalPageDetail> list, List<SysFileStoreDTO> fileStoreDTOList, List<KeyValue> headerVarsSourceList) throws Exception {
        // 导出导航信息
        List<SysPortalNavDTO> sysPortalNavDTOList = new ArrayList<>();
        //查找标签并获取标签值
        List<String> tagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.sysnav.source", list);
        List<String> anonymTagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.sysnav.anonym.source", list);
        if (!CollectionUtils.isEmpty(anonymTagVarFdIds)) {
            tagVarFdIds.addAll(anonymTagVarFdIds);
        }
        //把页眉上面的ID和页面的ID合并
        if (!CollectionUtils.isEmpty(headerVarsSourceList)) {
            List<String> collect = headerVarsSourceList.stream().map(item -> item.getValue()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                tagVarFdIds.addAll(collect);
            }
        }

        if (CollectionUtils.isEmpty(tagVarFdIds)) {
            return null;
        }
        List<SysPortalNav> sysPortalLinkList = new ArrayList<>();
        for (String id : tagVarFdIds) {
            SysPortalNav sysPortalNav = (SysPortalNav) getSysPortalNavService().findByPrimaryKey(id, SysPortalNav.class, true);
            if (sysPortalNav != null) {
                sysPortalLinkList.add(sysPortalNav);
            }
        }
        if (CollectionUtils.isEmpty(sysPortalLinkList)) {
            return null;
        }
        for (SysPortalNav sysPortalNav : sysPortalLinkList) {

            SysPortalNavDTO sysPortalNavDTO = new SysPortalNavDTO();
            BeanUtils.copyProperties(sysPortalNav, sysPortalNavDTO);
            sysPortalNavDTOList.add(sysPortalNavDTO);
        }

        if (CollectionUtils.isEmpty(sysPortalNavDTOList)) {
            return null;
        }

        //导出素材库图片
        List<String> collect = sysPortalNavDTOList.stream().filter(item -> StringUtil.isNotNull(item.getFdContent())).map(item -> item.getFdContent()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            exportSysPortalSysNavImg(rootPath, mainId, fileStoreDTOList, collect);
        }

        String filePath = rootPath + PortalConstant.SYS_PORTAL_NAV_EXPORT_PREFIX + mainId;
        //如果文件存在读取文件内容，并删除文件
        if (FileUtil.getFile(filePath).exists()) {
            sysPortalNavDTOList.addAll(JSONArray.parseArray(FileUtil.getFileString(filePath, FILE_ENCODING), SysPortalNavDTO.class));
            FileUtil.deleteFile(filePath);
        }
        //过滤重复数据
        List<SysPortalNavDTO> exportCollect = sysPortalNavDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        String sysPortalNavDTOListStr = JSONArray.toJSONString(exportCollect);
        FileUtil.createFile(filePath, sysPortalNavDTOListStr, FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出导航素材图片
     *
     * @param rootPath
     * @param mainId
     * @param fileStoreDTOList
     * @param contentList      内容列表
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/8/10 4:32 下午
     */
    private void exportSysPortalSysNavImg(String rootPath, String mainId, List<SysFileStoreDTO> fileStoreDTOList, List<String> contentList) throws Exception {
        if (CollectionUtils.isEmpty(contentList)) {
            return;
        }
        List<String> imgUrlList = new ArrayList<>();
        for (String fdContent : contentList) {
            JSONArray fdContentJSONArray = JSONArray.parseArray(fdContent);
            for (int i = 0; i < fdContentJSONArray.size(); i++) {
                JSONObject jsonObject = fdContentJSONArray.getJSONObject(i);
                if (StringUtil.isNotNull(jsonObject.getString("img"))) {
                    imgUrlList.add(jsonObject.getString("img"));
                }
                if (StringUtil.isNotNull(jsonObject.getString("children"))) {
                    JSONArray childrenJSONArray = JSONArray.parseArray(jsonObject.getString("children"));
                    if (childrenJSONArray.size() > 0) {
                        for (int m = 0; m < childrenJSONArray.size(); m++) {
                            JSONObject subJSONObject = childrenJSONArray.getJSONObject(m);
                            if (StringUtil.isNotNull(subJSONObject.getString("img"))) {
                                imgUrlList.add(subJSONObject.getString("img"));
                            }
                        }
                    }
                }
            }
        }
        List<String> collectUrlList = imgUrlList.stream().filter(item -> StringUtil.isNotNull(item)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collectUrlList)) {
            List<String> attMainUrlList = splitAttMainUrl(collectUrlList);
            //导出自定义素材库中的图片信息
            exportAttMainMaterial(rootPath, mainId, attMainUrlList, fileStoreDTOList);
        }
    }

    /**
     * 导出页面子菜单信息
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/7/6 1:15 下午
     */
    private String exportSysPortalSysNav(String rootPath, String mainId, List<SysPortalPage> list, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        // 导出导航信息
        List<SysPortalNavDTO> sysPortalNavDTOList = new ArrayList<>();
        List<SysPortalNav> sysPortalLinkList = new ArrayList<>();

        List<String> pageIdList = list.stream().map(item -> item.getFdId()).collect(Collectors.toList());
        HQLInfo hqlInfo = new HQLInfo();
        String where = "sysPortalNav.fdPageId in (:fdPageIds)";
        hqlInfo.setWhereBlock(where);
        hqlInfo.setParameter("fdPageIds", pageIdList);
        List<SysPortalNav> sysPortalNavList = getSysPortalNavService().findList(hqlInfo);
        if (CollectionUtils.isEmpty(sysPortalNavList)) {
            return null;
        }
        sysPortalLinkList.addAll(sysPortalNavList);
        for (SysPortalNav sysPortalNav : sysPortalLinkList) {

            SysPortalNavDTO sysPortalNavDTO = new SysPortalNavDTO();
            BeanUtils.copyProperties(sysPortalNav, sysPortalNavDTO);
            sysPortalNavDTOList.add(sysPortalNavDTO);
        }

        if (CollectionUtils.isEmpty(sysPortalNavDTOList)) {
            return null;
        }
        //导出素材库图片
        List<String> collect = sysPortalNavDTOList.stream().filter(item -> StringUtil.isNotNull(item.getFdContent())).map(item -> item.getFdContent()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collect)) {
            exportSysPortalSysNavImg(rootPath, mainId, fileStoreDTOList, collect);

        }

        String filePath = rootPath + PortalConstant.SYS_PORTAL_NAV_EXPORT_PREFIX + mainId;
        //如果文件存在读取文件内容，并删除文件
        if (FileUtil.getFile(filePath).exists()) {
            sysPortalNavDTOList.addAll(JSONArray.parseArray(FileUtil.getFileString(filePath, FILE_ENCODING), SysPortalNavDTO.class));
            FileUtil.deleteFile(filePath);
        }
        //过滤重复数据
        List<SysPortalNavDTO> exportCollect = sysPortalNavDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        String sysPortalNavDTOListStr = JSONArray.toJSONString(exportCollect);
        FileUtil.createFile(filePath, sysPortalNavDTOListStr, FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出SysPortalTree
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/19 9:50 上午
     */
    private String exportSysPortalTree(String rootPath, String mainId, List<SysPortalPageDetail> list, String refName, List<KeyValue> headerVarsSourceList) throws Exception {
        // 导出导航信息
        List<SysPortalTreeDTO> sysPortalTreeDTOList = new ArrayList<>();
        //查找标签并获取标签值
        List<String> tagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, refName, list);
        //把页眉上面的ID和页面的ID合并
        if (!CollectionUtils.isEmpty(headerVarsSourceList)) {
            List<String> collect = headerVarsSourceList.stream().map(item -> item.getValue()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                tagVarFdIds.addAll(collect);
            }
        }

        if (CollectionUtils.isEmpty(tagVarFdIds)) {
            return null;
        }
        List<SysPortalTree> sysPortalTreeList = new ArrayList<>();
        for (String id : tagVarFdIds) {
            SysPortalTree sysPortalTree = (SysPortalTree) getSysPortalTreeService().findByPrimaryKey(id, SysPortalTree.class, true);
            if (sysPortalTree != null) {
                sysPortalTreeList.add(sysPortalTree);
            }
        }
        if (CollectionUtils.isEmpty(sysPortalTreeList)) {
            return null;
        }

        for (SysPortalTree sysPortalTree : sysPortalTreeList) {
            SysPortalTreeDTO sysPortalTreeDTO = new SysPortalTreeDTO();
            BeanUtils.copyProperties(sysPortalTree, sysPortalTreeDTO);
            sysPortalTreeDTOList.add(sysPortalTreeDTO);
        }
        if (CollectionUtils.isEmpty(sysPortalTreeDTOList)) {
            return null;
        }
        String filePath = rootPath + PortalConstant.SYS_PORTAL_TREE_EXPORT_PREFIX + mainId;
        //如果文件存在读取文件内容，并删除文件
        if (FileUtil.getFile(filePath).exists()) {
            sysPortalTreeDTOList.addAll(JSONArray.parseArray(FileUtil.getFileString(filePath, FILE_ENCODING), SysPortalTreeDTO.class));
            FileUtil.deleteFile(filePath);
        }
        //过滤重复数据
        List<SysPortalTreeDTO> collect = sysPortalTreeDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        FileUtil.createFile(filePath, JSONArray.toJSONString(collect), FILE_ENCODING);
        return filePath;
    }

    /**
     * 导出SysPortalTree2
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/19 10:07 上午
     */
    private List<String> exportSysPortalTree2(String rootPath, String mainId, List<SysPortalPageDetail> list, List<KeyValue> headerVarsSourceList) throws Exception {

        List<String> filePathList = new ArrayList<>();
        String treeMenu2Path = exportSysPortalTree(rootPath, mainId, list, "sys.portal.treeMenu2.source", headerVarsSourceList);
        if (StringUtil.isNotNull(treeMenu2Path)) {
            filePathList.add(treeMenu2Path);
        }
        String anonymPath = exportSysPortalTree(rootPath, mainId, list, "sys.portal.treeMenu2.anonym.source", null);
        if (StringUtil.isNotNull(anonymPath)) {
            filePathList.add(anonymPath);
        }
        return filePathList;
    }

    /**
     * 导出SysPortalTree3
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/19 10:07 上午
     */
    private List<String> exportSysPortalTree3(String rootPath, String mainId, List<SysPortalPageDetail> list, List<KeyValue> headerVarsSourceList) throws Exception {

        List<String> filePathList = new ArrayList<>();
        String treeMenu3Path = exportSysPortalTree(rootPath, mainId, list, "sys.portal.treeMenu3.source", headerVarsSourceList);
        if (StringUtil.isNotNull(treeMenu3Path)) {
            filePathList.add(treeMenu3Path);
        }
        String anonymPath = exportSysPortalTree(rootPath, mainId, list, "sys.portal.treeMenu3.anonym.source", null);
        if (StringUtil.isNotNull(anonymPath)) {
            filePathList.add(anonymPath);
        }
        return filePathList;
    }

    /**
     * 获取导出自定义HTML中的附件fckeditor中的图片
     *
     * @param list
     * @description:
     * @return: List<SysAttRtfData>
     * @author: wangjf
     * @time: 2021/6/15 4:38 下午
     */
    private List<SysAttRtfData> getAttRtfData(List<SysPortalPageDetail> list) throws Exception {

        List<String> tagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.html.source", list);

        if (CollectionUtils.isEmpty(tagVarFdIds)) {
            return null;
        }
        List<SysPortalHtml> byPrimaryKeys = new ArrayList<>();
        for (String id : tagVarFdIds) {
            SysPortalHtml sysPortalHtml = (SysPortalHtml) getSysPortalHtmlService().findByPrimaryKey(id, SysPortalHtml.class, true);
            if (sysPortalHtml != null) {
                byPrimaryKeys.add(sysPortalHtml);
            }
        }
        if (CollectionUtils.isEmpty(byPrimaryKeys)) {
            return null;
        }
        List<String> attIdList = new ArrayList<>();
        for (SysPortalHtml sysPortalHtml : byPrimaryKeys) {
            if (StringUtil.isNotNull(sysPortalHtml.getFdContent())) {
                Document document = Jsoup.parseBodyFragment(sysPortalHtml.getFdContent());
                //后续还需要把其他类型的资源拉出来
                Elements imgSourceTags = document.getElementsByTag("img");
                for (Element element : imgSourceTags) {
                    //判断是否属于本系统内部的资源 ，通过/resource/fckeditor来进行判断
                    if (StringUtil.isNotNull(element.attr("src")) && element.attr("src").indexOf("/resource/fckeditor") > -1) {
                        //通过?进行分割，把?号后面的数据分离出来
                        String[] srcs = element.attr("src").split("\\?");
                        if (srcs.length > 1 && StringUtil.isNotNull(srcs[1])) {
                            //通过&把数据分离出来
                            String[] split = srcs[1].split("&");
                            for (String str : split) {
                                //通过fdId=取出数据
                                if (str.startsWith("fdId=")) {
                                    String[] split1 = str.split("=");
                                    if (split1.length > 1) {
                                        attIdList.add(split1[1]);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(attIdList)) {
            return null;
        }
        List<SysAttRtfData> attFileList = new ArrayList<>();
        for (String id : attIdList) {
            SysAttRtfData sysAttRtfData = (SysAttRtfData) getSysAttMainService().getSysAttMainDao().findByPrimaryKey(id, SysAttRtfData.class, true);
            if (sysAttRtfData != null) {
                attFileList.add(sysAttRtfData);
            }
        }
        return attFileList;
    }

    /**
     * @param list
     * @return java.util.List<java.lang.String>
     * @author wangjf
     * @Description 获取自定义页面中的素材库
     * @Date 2021/9/4 21:28
     */

    private List<String> getHtmlAttMainUrl(List<SysPortalPageDetail> list) throws Exception {

        List<String> tagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.html.source", list);

        if (CollectionUtils.isEmpty(tagVarFdIds)) {
            return null;
        }
        List<SysPortalHtml> byPrimaryKeys = new ArrayList<>();
        for (String id : tagVarFdIds) {
            SysPortalHtml sysPortalHtml = (SysPortalHtml) getSysPortalHtmlService().findByPrimaryKey(id, SysPortalHtml.class, true);
            if (sysPortalHtml != null) {
                byPrimaryKeys.add(sysPortalHtml);
            }
        }
        if (CollectionUtils.isEmpty(byPrimaryKeys)) {
            return null;
        }
        List<String> attUrlList = new ArrayList<>();
        for (SysPortalHtml sysPortalHtml : byPrimaryKeys) {
            if (StringUtil.isNotNull(sysPortalHtml.getFdContent())) {
                Document document = Jsoup.parseBodyFragment(sysPortalHtml.getFdContent());
                //后续还需要把其他类型的资源拉出来
                Elements imgSourceTags = document.getElementsByTag("img");
                for (Element element : imgSourceTags) {
                    //判断是否属于本系统附件的资源 ，通过/sys/attachment/sys_att_main来进行判断
                    if (StringUtil.isNotNull(element.attr("src")) && element.attr("src").indexOf("/sys/attachment/sys_att_main") > -1) {
                        attUrlList.add(element.attr("src"));
                    }
                }
            }
        }
        return attUrlList;
    }

    /**
     * 导出附件存储表和attFile
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/15 5:38 下午
     */
    private List<String> exportSysAttFile(String rootPath, String mainId, List<SysPortalPageDetail> list, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        //导出
        List<SysAttRtfData> attRtfDataList = getAttRtfData(list);
        if (CollectionUtils.isEmpty(attRtfDataList)) {
            return null;
        }
        List<String> filePathList = new ArrayList<>();
        //导出SysAttRtfData数据
        List<SysAttRtfDataDTO> sysAttRtfDataDTOList = new ArrayList<>();
        List<SysAttFile> sysAttFileList = new ArrayList<>();
        for (SysAttRtfData sysAttRtfData : attRtfDataList) {
            SysAttRtfDataDTO sysAttRtfDataDTO = new SysAttRtfDataDTO();
            BeanUtils.copyProperties(sysAttRtfData, sysAttRtfDataDTO);
            sysAttRtfDataDTOList.add(sysAttRtfDataDTO);
            SysAttFile sysAttFile = getSysAttUploadService().getFileById(sysAttRtfData.getFdFileId());
            if (sysAttFile != null) {
                sysAttFileList.add(sysAttFile);
            }
        }
        String attRtfDataFilePath = rootPath + PortalConstant.SYS_ATT_RTF_DATA_EXPORT_PREFIX + mainId;

        //如果文件存在读取文件内容，并删除文件
        if (FileUtil.getFile(attRtfDataFilePath).exists()) {
            sysAttRtfDataDTOList.addAll(JSONArray.parseArray(FileUtil.getFileString(attRtfDataFilePath, FILE_ENCODING), SysAttRtfDataDTO.class));
            FileUtil.deleteFile(attRtfDataFilePath);
        }
        //过滤重复数据
        List<SysAttRtfDataDTO> rtfCollect = sysAttRtfDataDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        FileUtil.createFile(attRtfDataFilePath, JSONArray.toJSONString(rtfCollect), FILE_ENCODING);
        filePathList.add(attRtfDataFilePath);

        //导出attfile
        if (!CollectionUtils.isEmpty(sysAttFileList)) {
            List<SysAttFileDTO> sysAttFileDTOList = new ArrayList<>();
            String targetResourcePath = rootPath + File.separator + "download" + File.separator;
            FileUtil.createDir(targetResourcePath, true);
            for (SysAttFile sysAttFile : sysAttFileList) {
                SysAttFileDTO sysAttFileDTO = new SysAttFileDTO();
                BeanUtils.copyProperties(sysAttFile, sysAttFileDTO);
                sysAttFileDTOList.add(sysAttFileDTO);
                //资源文件的拷贝
                String attFileSourcePath = ResourceUtil.KMSS_RESOURCE_PATH + sysAttFile.getFdFilePath();
                String targetPath = targetResourcePath + sysAttFile.getFdId();
                if (FileUtil.getFile(attFileSourcePath).exists()) {
                    FileUtil.copy(attFileSourcePath, targetPath);
                    //记录文件的拷贝信息
                    //文件路径需要去除文件本身
                    String fileTargetPath = sysAttFile.getFdFilePath().replace(sysAttFile.getFdId(), "");
                    SysFileStoreDTO sysFileStoreDTO = new SysFileStoreDTO();
                    sysFileStoreDTO.setFdId(sysAttFile.getFdId());
                    sysFileStoreDTO.setPackagePath("download");
                    sysFileStoreDTO.setTargetPath(fileTargetPath);
                    fileStoreDTOList.add(sysFileStoreDTO);

                }
            }

            //判断数据文件是否存在，如果存在则读取并且加入本次数据一起写入数据库
            String attFilePath = rootPath + PortalConstant.SYS_ATT_FILE_EXPORT_PREFIX + mainId;
            if (FileUtil.getFile(attFilePath).exists()) {
                List<SysAttFileDTO> existsDataList = JSONArray.parseArray(FileUtil.getFileString(attFilePath, FILE_ENCODING), SysAttFileDTO.class);
                if (!CollectionUtils.isEmpty(existsDataList)) {
                    sysAttFileDTOList.addAll(existsDataList);
                    FileUtil.deleteFile(attFilePath);
                }
            }
            //过滤重复数据
            List<SysAttFileDTO> attFileCollect = sysAttFileDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
            String sysAttFileDTOListStr = JSONArray.toJSONString(attFileCollect);
            FileUtil.createFile(attFilePath, sysAttFileDTOListStr, FILE_ENCODING);
            filePathList.add(attFilePath);
        }
        return filePathList;
    }

    /**
     * 导出SysPortalTopic
     *
     * @param rootPath
     * @param mainId
     * @param list
     * @param fileStoreDTOList
     * @param headerVarsSourceList 页眉自定义的应用
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/19 10:31 上午
     */
    private String exportSysPortalTopic(String rootPath, String mainId, List<SysPortalPageDetail> list, List<SysFileStoreDTO> fileStoreDTOList, List<KeyValue> headerVarsSourceList) throws Exception {
        List<SysPortalTopicDTO> sysPortalTopicDTOList = new ArrayList<>();
        //查找标签并获取标签值
        List<String> tagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.topic.source", "var-fdids", list);
        List<String> anonymTagVarFdIds = getTagVarFdId(HTML_UI_SOURCE, "sys.portal.topic.anonym.source", "var-fdids", list);
        if (!CollectionUtils.isEmpty(anonymTagVarFdIds)) {
            tagVarFdIds.addAll(anonymTagVarFdIds);
        }

        //把页眉上面的ID和页面的ID合并
        if (!CollectionUtils.isEmpty(headerVarsSourceList)) {
            List<String> collect = headerVarsSourceList.stream().map(item -> item.getValue()).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                tagVarFdIds.addAll(collect);
            }
        }

        if (CollectionUtils.isEmpty(tagVarFdIds)) {
            return null;
        }
        List<SysPortalTopic> sysPortalTopicList = new ArrayList<>();
        for (String id : tagVarFdIds) {
            SysPortalTopic sysPortalTopic = (SysPortalTopic) getSysPortalTopicService().findByPrimaryKey(id, SysPortalTopic.class, true);
            if (sysPortalTopic != null) {
                sysPortalTopicList.add(sysPortalTopic);
            }
        }
        //素材库图片
        List<String> imgUrlList = new ArrayList<>();
        List<String> sysPortalTopicIdsList = new ArrayList<>();
        for (SysPortalTopic sysPortalTopic : sysPortalTopicList) {
            SysPortalTopicDTO sysPortalTopicDTO = new SysPortalTopicDTO();
            BeanUtils.copyProperties(sysPortalTopic, sysPortalTopicDTO);
            sysPortalTopicDTOList.add(sysPortalTopicDTO);
            sysPortalTopicIdsList.add(sysPortalTopic.getFdId());
            if (StringUtil.isNotNull(sysPortalTopic.getFdImg())) {
                imgUrlList.add(sysPortalTopic.getFdImg());
            }
        }

        if (CollectionUtils.isEmpty(sysPortalTopicDTOList)) {
            return null;
        }

        if (!CollectionUtils.isEmpty(imgUrlList)) {
            List<String> collectUrlList = imgUrlList.stream().filter(item -> StringUtil.isNotNull(item)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collectUrlList)) {
                List<String> attMainUrlList = splitAttMainUrl(collectUrlList);
                //导出自定义素材库中的图片信息
                exportAttMainMaterial(rootPath, mainId, attMainUrlList, fileStoreDTOList);
            }
        }

        String filePath = rootPath + PortalConstant.SYS_PORTAL_TOPIC_EXPORT_PREFIX + mainId;
        if (FileUtil.getFile(filePath).exists()) {
            List<SysPortalTopicDTO> existsDataList = JSONArray.parseArray(FileUtil.getFileString(filePath, FILE_ENCODING), SysPortalTopicDTO.class);
            if (!CollectionUtils.isEmpty(existsDataList)) {
                sysPortalTopicDTOList.addAll(existsDataList);
                FileUtil.deleteFile(filePath);
            }
        }
        //过滤重复数据
        List<SysPortalTopicDTO> collect = sysPortalTopicDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        String sysPortalTopicDTOListStr = JSONArray.toJSONString(collect);
        FileUtil.createFile(filePath, sysPortalTopicDTOListStr, FILE_ENCODING);
        //导出附件
        exportAttMain(rootPath, mainId, sysPortalTopicIdsList, "sysPortalTopic_fdKey", fileStoreDTOList);
        return filePath;
    }

    /**
     * 导出附件
     *
     * @param rootPath
     * @param idList
     * @param fdKey
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/2 5:36 下午
     */
    private void exportAttMain(String rootPath, String mainId, List<String> idList, String fdKey, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        // 导出附件信息
        List<SysAttMainDTO> sysAttMainDTOList = new ArrayList<>();
        List<String> attFileIdList = new ArrayList<>();
        HQLInfo hqlInfo = new HQLInfo();
        String where = "sysAttMain.fdKey=:fdKey and sysAttMain.fdModelId in (:fdModelIds)";
        hqlInfo.setWhereBlock(where);
        hqlInfo.setParameter("fdKey", fdKey);
        hqlInfo.setParameter("fdModelIds", idList);
        List<SysAttMain> sysAttMainList = getSysAttMainService().findList(hqlInfo);
        if (CollectionUtils.isEmpty(sysAttMainList)) {
            return;
        }
        for (SysAttMain sysAttMain : sysAttMainList) {
            SysAttMainDTO sysAttMainDTO = new SysAttMainDTO();
            BeanUtils.copyProperties(sysAttMain, sysAttMainDTO);
            sysAttMainDTOList.add(sysAttMainDTO);
            attFileIdList.add(sysAttMain.getFdFileId());
        }
        if (CollectionUtils.isEmpty(sysAttMainDTOList)) {
            return;
        }

        String filePath = rootPath + PortalConstant.SYS_ATT_MAIN_EXPORT_PREFIX + mainId;
        if (FileUtil.getFile(filePath).exists()) {
            List<SysAttMainDTO> sysAttMainDTOS = JSONArray.parseArray(FileUtil.getFileString(filePath, FILE_ENCODING), SysAttMainDTO.class);
            if (!CollectionUtils.isEmpty(sysAttMainDTOS)) {
                sysAttMainDTOList.addAll(sysAttMainDTOS);
                FileUtil.deleteFile(filePath);
            }
        }
        List<SysAttMainDTO> collect = sysAttMainDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        FileUtil.createFile(filePath, JSONArray.toJSONString(collect), FILE_ENCODING);
        //导出文件
        exportAttFile(rootPath, mainId, attFileIdList, fileStoreDTOList);
    }


    /**
     * 导出具体附件
     *
     * @param rootPath
     * @param mainId
     * @param idList
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/2 6:11 下午
     */
    private void exportAttFile(String rootPath, String mainId, List<String> idList, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {

        if (CollectionUtils.isEmpty(idList)) {
            return;
        }
        List<SysAttFileDTO> sysAttFileDTOList = new ArrayList<>();
        for (String id : idList) {
            SysAttFile sysAttFile = getSysAttUploadService().getFileById(id);
            if (sysAttFile != null) {
                SysAttFileDTO sysAttFileDTO = new SysAttFileDTO();
                BeanUtils.copyProperties(sysAttFile, sysAttFileDTO);
                sysAttFileDTOList.add(sysAttFileDTO);
            }
        }

        if (CollectionUtils.isEmpty(sysAttFileDTOList)) {
            return;
        }

        String targetResourcePath = rootPath + File.separator + "download" + File.separator;
        FileUtil.createDir(targetResourcePath, true);
        for (SysAttFileDTO sysAttFileDTO : sysAttFileDTOList) {
            //资源文件的拷贝
            String attFileSourcePath = ResourceUtil.KMSS_RESOURCE_PATH + sysAttFileDTO.getFdFilePath();
            String targetPath = targetResourcePath + sysAttFileDTO.getFdId();
            if (FileUtil.getFile(attFileSourcePath).exists()) {
                FileUtil.copy(attFileSourcePath, targetPath);
                //记录文件的拷贝信息
                //文件路径需要去除文件本身
                String fileTargetPath = sysAttFileDTO.getFdFilePath().replace(sysAttFileDTO.getFdId(), "");
                SysFileStoreDTO sysFileStoreDTO = new SysFileStoreDTO();
                sysFileStoreDTO.setFdId(sysAttFileDTO.getFdId());
                sysFileStoreDTO.setPackagePath("download");
                sysFileStoreDTO.setTargetPath(fileTargetPath);
                fileStoreDTOList.add(sysFileStoreDTO);

            }
        }
        //判断数据文件是否存在，如果存在则读取并且加入本次数据一起写入数据库
        String attFilePath = rootPath + PortalConstant.SYS_ATT_FILE_EXPORT_PREFIX + mainId;
        if (FileUtil.getFile(attFilePath).exists()) {
            List<SysAttFileDTO> existsDataList = JSONArray.parseArray(FileUtil.getFileString(attFilePath, FILE_ENCODING), SysAttFileDTO.class);
            if (!CollectionUtils.isEmpty(existsDataList)) {
                sysAttFileDTOList.addAll(existsDataList);
                FileUtil.deleteFile(attFilePath);
            }
        }
        String sysAttFileDTOListStr = JSONArray.toJSONString(sysAttFileDTOList);
        FileUtil.createFile(attFilePath, sysAttFileDTOListStr, FILE_ENCODING);
    }


    /**
     * 导出主题包
     *
     * @param rootPath
     * @param themeSet
     * @param fileStoreDTOList
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/21 2:55 下午
     */
    public void exportSysPortalTheme(String rootPath, Set<String> themeSet, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {

        if (CollectionUtils.isEmpty(themeSet)) {
            return;
        }
        final String fileSuffix = ".zip";
        //创建包存放的位置
        final String filePath = rootPath + File.separator + "themeZip" + File.separator;
        //主题在系统中存放的位置
        final String themeLocalPath = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + "ui-ext" + File.separator;
        FileUtil.createDir(filePath, true);
        themeSet.stream().forEach(item -> {
            String themePath = themeLocalPath + item + File.separator;
            try {
                // 如果在主题文件夹中未找到主题该主题，说是系统主题，可以不需要导出
                if (FileUtil.getFile(themePath).exists()) {
                    String themeZipPath = themeLocalPath + item + fileSuffix;
                    SysFileStoreDTO sysFileStoreDTO = new SysFileStoreDTO();
                    sysFileStoreDTO.setZipPackage(true);
                    sysFileStoreDTO.setFdId(item + fileSuffix);
                    sysFileStoreDTO.setPackagePath("themeZip");
                    sysFileStoreDTO.setTargetPath("ui-ext");

                    ZipUtil.zip(themeZipPath, themePath, false);
                    FileUtil.copy(themeZipPath, filePath + item + fileSuffix);
                    FileUtil.deleteFile(themeZipPath);
                    fileStoreDTOList.add(sysFileStoreDTO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 直接压在zip包，并拷贝即可
     *
     * @param rootPath
     * @param uiId
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/28 10:43 下午
     */
    private void exportSysExportZip(String rootPath, String uiId, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        final String fileSuffix = ".zip";
        uiId = uiId.replaceAll("\\.", "-");
        //创建包存放的位置
        String filePath = rootPath + File.separator + "extendZip" + File.separator;
        if (!FileUtil.getFile(filePath).exists()) {
            FileUtil.createDir(filePath, true);
        }
        //主题在系统中存放的位置
        String extendLocalPath = new File(getWebContentPath() + SYS_PORTAL_UI + File.separator + uiId).toString() + File.separator;
        String tempZipPath = getWebContentPath() + SYS_PORTAL_UI + File.separator + IDGenerator.generateID();
        if (FileUtil.getFile(extendLocalPath).exists()) {
            SysFileStoreDTO sysFileStoreDTO = new SysFileStoreDTO();
            sysFileStoreDTO.setZipPackage(true);
            sysFileStoreDTO.setFdId(uiId + fileSuffix);
            sysFileStoreDTO.setPackagePath("extendZip");
            sysFileStoreDTO.setTargetPath("ui_component");
            sysFileStoreDTO.setHandleStatus("extend");

            String zipName = tempZipPath + uiId;
            ZipUtil.zip(zipName, extendLocalPath, false);
            FileUtil.copy(zipName, filePath + uiId + fileSuffix);
            FileUtil.deleteFile(zipName);
            fileStoreDTOList.add(sysFileStoreDTO);
        }

    }

    /**
     * 页眉导出
     *
     * @param rootPath
     * @param exportSet
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/30 10:41 上午
     */
    private void exportSysExtendHeader(String rootPath, Set<String> exportSet, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(exportSet)) {
            return;
        }
        List<Object> exportList = filterSysComponent(exportSet, "header");
        if (CollectionUtils.isEmpty(exportList)) {
            return;
        }
        for (int i = 0; i < exportList.size(); i++) {

            SysPortalHeader sysPortalHeader = (SysPortalHeader) exportList.get(i);
            String idDir = sysPortalHeader.getFdId().replaceAll("\\.", "-");
            if (sysPortalHeader.getXmlPath().indexOf(idDir) > -1) {
                //直接拷贝
                exportSysExportZip(rootPath, sysPortalHeader.getFdId(), fileStoreDTOList);
            } else {
                //需要分包处理
                Map<String, String> map = new HashMap<>();
                map.put("type", "header");
                map.put("fdId", sysPortalHeader.getFdId());
                map.put("fdName", sysPortalHeader.getFdName());
                map.put("thumbPath", sysPortalHeader.getThumbPath());
                map.put("xmlPath", sysPortalHeader.getXmlPath());
                map.put("path", sysPortalHeader.getPath());
                exportExtendSeparatorUi(rootPath, map, fileStoreDTOList);
            }
        }
    }

    /**
     * 导出footer 页脚
     *
     * @param rootPath
     * @param exportSet
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/30 10:38 上午
     */
    private void exportSysExtendFooter(String rootPath, Set<String> exportSet, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(exportSet)) {
            return;
        }
        List<Object> exportList = filterSysComponent(exportSet, "footer");
        if (CollectionUtils.isEmpty(exportList)) {
            return;
        }
        for (int i = 0; i < exportList.size(); i++) {

            SysPortalFooter sysPortalFooter = (SysPortalFooter) exportList.get(i);
            String idDir = sysPortalFooter.getFdId().replaceAll("\\.", "-");
            if (sysPortalFooter.getXmlPath().indexOf(idDir) > -1) {
                //直接拷贝
                exportSysExportZip(rootPath, sysPortalFooter.getFdId(), fileStoreDTOList);
            } else {
                //需要分包处理
                Map<String, String> map = new HashMap<>();
                map.put("type", "footer");
                map.put("fdId", sysPortalFooter.getFdId());
                map.put("fdName", sysPortalFooter.getFdName());
                map.put("thumbPath", sysPortalFooter.getThumbPath());
                map.put("xmlPath", sysPortalFooter.getXmlPath());
                map.put("path", sysPortalFooter.getPath());
                exportExtendSeparatorUi(rootPath, map, fileStoreDTOList);
            }
        }
    }

    /**
     * 导出拓展模板
     *
     * @param rootPath
     * @param exportSet
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/28 10:24 下午
     */
    private void exportSysExtendTemplate(String rootPath, Set<String> exportSet, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(exportSet)) {
            return;
        }
        List<Object> exportList = filterSysComponent(exportSet, "template");
        if (CollectionUtils.isEmpty(exportList)) {
            return;
        }
        for (int i = 0; i < exportList.size(); i++) {

            SysUiTemplate sysUiTemplate = (SysUiTemplate) exportList.get(i);
            String idDir = sysUiTemplate.getFdId().replaceAll("\\.", "-");
            if (sysUiTemplate.getXmlPath().indexOf(idDir) > -1) {
                //直接拷贝
                exportSysExportZip(rootPath, sysUiTemplate.getFdId(), fileStoreDTOList);
            } else {
                //需要分包处理
                Map<String, String> map = new HashMap<>();
                map.put("type", "template");
                map.put("fdId", sysUiTemplate.getFdId());
                map.put("fdName", sysUiTemplate.getFdName());
                map.put("thumbPath", sysUiTemplate.getThumbPath());
                map.put("xmlPath", sysUiTemplate.getXmlPath());
                map.put("path", sysUiTemplate.getPath());
                exportExtendSeparatorUi(rootPath, map, fileStoreDTOList);
            }
        }
    }

    /**
     * 导出面板
     *
     * @param rootPath
     * @param exportSet
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/29 3:58 下午
     */
    private void exportSysExtendPanel(String rootPath, Set<String> exportSet, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(exportSet)) {
            return;
        }
        List<Object> exportList = filterSysComponent(exportSet, "layout");
        if (CollectionUtils.isEmpty(exportList)) {
            return;
        }
        for (int i = 0; i < exportList.size(); i++) {

            SysUiLayout sysUiLayout = (SysUiLayout) exportList.get(i);
            String idDir = sysUiLayout.getFdId().replaceAll("\\.", "-");
            if (sysUiLayout.getXmlPath().indexOf(idDir) > -1) {
                //直接拷贝
                exportSysExportZip(rootPath, sysUiLayout.getFdId(), fileStoreDTOList);
            } else {
                //需要分包处理
                Map<String, String> map = new HashMap<>();
                map.put("type", "panel");
                map.put("fdId", sysUiLayout.getFdId());
                map.put("fdName", sysUiLayout.getFdName());
                map.put("thumbPath", sysUiLayout.getThumbPath());
                map.put("xmlPath", sysUiLayout.getXmlPath());
                map.put("path", sysUiLayout.getPath());
                exportExtendSeparatorUi(rootPath, map, fileStoreDTOList);
            }
        }
    }

    /**
     * 导出render
     *
     * @param rootPath
     * @param exportSet
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/29 3:59 下午
     */
    private void exportSysExtendRender(String rootPath, Set<String> exportSet, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(exportSet)) {
            return;
        }
        List<Object> exportList = filterSysComponent(exportSet, "render");
        if (CollectionUtils.isEmpty(exportList)) {
            return;
        }
        for (int i = 0; i < exportList.size(); i++) {

            SysUiRender sysUiLayout = (SysUiRender) exportList.get(i);
            String idDir = sysUiLayout.getFdId().replaceAll("\\.", "-");
            if (sysUiLayout.getXmlPath().indexOf(idDir) > -1) {
                //直接拷贝
                exportSysExportZip(rootPath, sysUiLayout.getFdId(), fileStoreDTOList);
            } else {
                //需要分包处理
                Map<String, String> map = new HashMap<>();
                map.put("type", "render");
                map.put("fdId", sysUiLayout.getFdId());
                map.put("fdName", sysUiLayout.getFdName());
                map.put("thumbPath", sysUiLayout.getThumbPath());
                map.put("xmlPath", sysUiLayout.getXmlPath());
                map.put("path", sysUiLayout.getPath());
                exportExtendSeparatorUi(rootPath, map, fileStoreDTOList);
            }
        }
    }

    /**
     * 修改jsp中的路径，采用递归
     *
     * @param jspDir
     * @param oldId
     * @param newId
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/1 12:32 下午
     */
    private void updateJsp(File jspDir, String oldId, String newId) throws IOException {
        //1、获取file下的所有jsp文件
        File[] allFiles = jspDir.listFiles();
        if (allFiles != null) {
            for (int i = 0; i < allFiles.length; i++) {
                File file = allFiles[i];
                if (file.isDirectory()) {
                    updateJsp(file, oldId, newId);
                } else {
                    if (file.getName().endsWith(".jsp")) {
                        loadJspFile(file, oldId, newId);
                    }
                }
            }
        }
    }

    /**
     * 进行内容替换
     *
     * @param file
     * @param oldId
     * @param newId
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/1 12:33 下午
     */
    private void loadJspFile(File file, String oldId, String newId) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        try {
            fileReader = new FileReader(file);
            br = new BufferedReader(fileReader);
            //按行读取
            String line = null;
            StringBuilder jsp = new StringBuilder();
            while ((line = br.readLine()) != null) {
                line = line.replaceAll(oldId, newId);
                jsp.append(line);
                jsp.append(System.lineSeparator());
            }
            fileWriter = new FileWriter(file);
            bw = new BufferedWriter(fileWriter);
            bw.write(jsp.toString());
            bw.flush();
        } catch (Exception e) {
            logger.error("jsp文件读写操作失败！", e);
        } finally {
            IOUtils.closeQuietly(fileWriter,null);
            IOUtils.closeQuietly(fileReader,null);
            IOUtils.closeQuietly(br,null);
            IOUtils.closeQuietly(bw,null);
        }
    }

    /**
     * 进行分包处理，并导出
     *
     * @param rootPath
     * @param map
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/29 1:51 下午
     */
    private void exportExtendSeparatorUi(String rootPath, Map<String, String> map, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        //1、在rootPath下面创建包
        //2、创建component.ini，缩略图对象路径可能需要变化
        //3、创建design-xml文件夹并且把源xml文件提取对应的xml内容并创建xml文件，注意路径文件
        //4、拷贝资源文件并打包
        String idDir = map.get("fdId").replaceAll("\\.", "-");
        String folderPath = rootPath + idDir + File.separator;
        FileUtil.createDirs(folderPath, true);

        String designXmlFolderPath = folderPath + File.separator + "design-xml" + File.separator;
        FileUtil.createDirs(designXmlFolderPath, true);

        String xmlFile = map.get("xmlPath");
        String[] split = xmlFile.split("\\/");
        String sourceDesignXmlFolderPath = getWebContentPath() + SYS_PORTAL_UI + split[0] + File.separator;
        String localResourcePath = SYS_PORTAL_UI + split[0];

        //写ini文件
        writeIniFile(folderPath, idDir, map.get("fdName"), map.get("thumbPath").replaceAll(localResourcePath, ""));

        //获取xml文件内容
        //获取组件包的名称
        String sourceDesignXmlFilePath = getWebContentPath() + SYS_PORTAL_UI + xmlFile;
        String templateXmlContent = "";
        //panel做特殊处理
        if ("panel".equals(map.get("type"))) {
            templateXmlContent = getXmlContent(sourceDesignXmlFilePath, "layout", map.get("fdId"));
        } else if ("header".equals(map.get("type"))) {
            templateXmlContent = getXmlContent(sourceDesignXmlFilePath, "portal:header", map.get("fdId"));
        } else if ("footer".equals(map.get("type"))) {
            templateXmlContent = getXmlContent(sourceDesignXmlFilePath, "portal:footer", map.get("fdId"));
        } else {
            templateXmlContent = getXmlContent(sourceDesignXmlFilePath, map.get("type"), map.get("fdId"));
        }
        if (StringUtil.isNull(templateXmlContent)) {
            //XML文件都无法处理，后续处理也没必要了
            return;
        }
        String designXmlFile = designXmlFolderPath + map.get("type") + ".xml";
        writeXmlContent(designXmlFile, templateXmlContent, idDir, localResourcePath);
        //如果类型未render则需要处理portId和format
        if ("render".equals(map.get("type"))) {
            writePortalAndFormat(folderPath, sourceDesignXmlFolderPath, templateXmlContent, idDir, localResourcePath);
        }
        final String VAR_DIR = "jsp" + File.separator + "lux-ui-exts" + File.separator + "varkind" + File.separator;
        //拷贝资源
        String sourceVarDir = sourceDesignXmlFolderPath + VAR_DIR;
        String targetVarDir = folderPath + VAR_DIR;
        if (FileUtil.getFile(sourceVarDir).exists()) {
            FileUtil.createDirs(targetVarDir, true);
            FileUtils.copyDirectory(new File(sourceVarDir), new File(targetVarDir));
        }

        String targetExtendPath = folderPath + map.get("path");
        if (!FileUtil.getFile(targetExtendPath).exists()) {
            FileUtil.createDirs(targetExtendPath, true);
        }
        String sourceExtendPath = sourceDesignXmlFolderPath + map.get("path");
        FileUtils.copyDirectory(new File(sourceExtendPath), new File(targetExtendPath));

        //内容替换 老的路径替换成新分包的路径 只替换JSP
        updateJsp(new File(folderPath), localResourcePath, SYS_PORTAL_UI + idDir);

        //打包
        String zipPath = rootPath + File.separator + "extendZip" + File.separator;

        if (!FileUtil.getFile(zipPath).exists()) {
            FileUtil.createDirs(zipPath, true);
        }
        String zipName = zipPath + idDir + ".zip";
        ZipUtil.zip(zipName, folderPath, true);

        //导出设置
        SysFileStoreDTO sysFileStoreDTO = new SysFileStoreDTO();
        sysFileStoreDTO.setZipPackage(true);
        sysFileStoreDTO.setFdId(idDir + ".zip");
        sysFileStoreDTO.setPackagePath("extendZip");
        sysFileStoreDTO.setTargetPath("ui_component");
        sysFileStoreDTO.setHandleStatus("extend");
        fileStoreDTOList.add(sysFileStoreDTO);

    }


    /**
     * 提取portal和format信息
     *
     * @param folderPath
     * @param webRootPath
     * @param content
     * @param idDir
     * @param sourceDesignXmlFolderPath
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/29 3:57 下午
     */
    private void writePortalAndFormat(String folderPath, String webRootPath, String content, String idDir, String sourceDesignXmlFolderPath) throws Exception {
        String sourceXmlContent = XML_ROOT_START + content + XML_ROOT_END;
        Document document = Jsoup.parse(sourceXmlContent);
        Elements elements = document.getElementsByTag("render");
        Element element = elements.get(0);
        String portletId = element.attr("lux-portletId");
        String formatId = element.attr("format");
        //非系统的portalId  需要处理
        if (!portletId.startsWith("sys")) {
            SysUiPortlet portletById = SysUiPluginUtil.getPortletById(portletId);
            if (portletById != null) {
                String portletXmlPath = getWebContentPath() + SYS_PORTAL_UI + portletById.getXmlPath();
                String portletXml = getXmlContent(portletXmlPath, "portlet", portletId);
                if (StringUtil.isNotNull(portletXml)) {
                    String writePortletXmlPath = folderPath + "design-xml" + File.separator + "portlet.xml";
                    writeXmlContent(writePortletXmlPath, portletXml, idDir, sourceDesignXmlFolderPath);
                    //拷贝资源
                    Document portletDocument = Jsoup.parseBodyFragment(portletXml);
                    Elements portletElements = portletDocument.getElementsByTag("portlet");
                    Element portletElement = portletElements.get(0);
                    String path = portletElement.attr("path");
                    //存在资源则拷贝
                    if (StringUtil.isNotNull(path)) {
                        int index = path.indexOf(sourceDesignXmlFolderPath);
                        if (index > -1) {
                            String resourcePath = getWebContentPath() + path.substring(index, path.length());
                            String targetPath = folderPath + path.substring(index, path.length()).replaceAll(sourceDesignXmlFolderPath, "");
                            FileUtils.copyDirectory(new File(resourcePath), new File(targetPath));
                        }
                    }
                }
            }
        }
        if (!formatId.startsWith("sys")) {
            SysUiFormat formatById = SysUiPluginUtil.getFormatById(formatId);
            if (formatById != null) {
                String formatXmlPath = getWebContentPath() + SYS_PORTAL_UI + formatById.getXmlPath();
                String formatXml = getXmlContent(formatXmlPath, "format", formatId);
                if (StringUtil.isNotNull(formatXml)) {
                    String writePortletXmlPath = folderPath + "design-xml" + File.separator + "format.xml";
                    writeXmlContent(writePortletXmlPath, formatXml, null, null);
                }
            }
        }
    }

    /**
     * 写ini文件
     *
     * @param folderPath
     * @param idDir
     * @param name
     * @param thumb
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/29 1:28 下午
     */
    private void writeIniFile(String folderPath, String idDir, String name, String thumb) throws Exception {
        StringBuilder iniContent = new StringBuilder();
        iniContent.append("id=").append(idDir).append("\r\n");
        iniContent.append("name=").append(name).append("\r\n");
        iniContent.append("thumb=").append(thumb).append("\r\n");
        String iniFile = folderPath + File.separator + "component.ini";
        FileUtil.createFile(iniFile, iniContent.toString(), FILE_ENCODING);
    }


    /**
     * 写xml内容
     *
     * @param targetPath
     * @param content
     * @param idDir
     * @param sourceDesignXmlFolderPath
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/29 10:39 上午
     */
    private void writeXmlContent(String targetPath, String content, String idDir, String sourceDesignXmlFolderPath) throws Exception {
        if (StringUtil.isNotNull(idDir) && StringUtil.isNotNull(sourceDesignXmlFolderPath)) {
            content = content.replaceAll(sourceDesignXmlFolderPath, SYS_PORTAL_UI + idDir);
        }
        StringBuilder xmlContent = new StringBuilder();
        xmlContent.append(XML_ROOT_START).append("\r\n");
        xmlContent.append(content).append("\r\n");
        xmlContent.append(XML_ROOT_END);
        FileUtil.createFile(targetPath, xmlContent.toString(), FILE_ENCODING);
    }

    /**
     * 获取xml里面的内容
     *
     * @param xmlPath
     * @param tagName
     * @param id
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/6/29 10:32 上午
     */
    private String getXmlContent(String xmlPath, String tagName, String id) throws Exception {

        //如果不是文件则打印错误
        if (FileUtil.getFile(xmlPath).isFile()) {
            String fileString = FileUtil.getFileString(xmlPath, FILE_ENCODING);
            if (StringUtil.isNotNull(fileString)) {
                org.dom4j.Document document = XMLUtil.readXML(fileString);
                List<org.dom4j.Element> elements = document.getRootElement().elements();
                for (org.dom4j.Element element : elements) {
                    if (element.attribute("id") != null && id.equals(element.attribute("id").getValue())) {
                        String result = element.asXML();
                        return result.replaceAll("xmlns=\"http://www.landray.com.cn/schema/lui\"", "");
                    }
                }
            }
        } else {
            logger.error("无法读取文件={},tagName={},id={}", xmlPath, tagName, id);
        }
        return null;
    }

    /**
     * 过滤系统模板
     *
     * @param exportSet
     * @param type
     * @description:
     * @return: java.util.List<java.lang.Object>
     * @author: wangjf
     * @time: 2021/6/28 10:22 下午
     */
    private List<Object> filterSysComponent(Set<String> exportSet, String type) {
        //采用guava的工具类
        List<Object> resultList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(exportSet)) {
            return resultList;
        }
        exportSet.stream().forEach(item -> {
            if ("template".equals(type)) {
                SysUiTemplate templateById = SysUiPluginUtil.getTemplateById(item);
                if (templateById != null && StringUtil.isNotNull(templateById.getPath())) {
                    resultList.add(templateById);
                }
            } else if ("render".equals(type)) {
                SysUiRender renderById = SysUiPluginUtil.getRenderById(item);
                if (renderById != null && StringUtil.isNotNull(renderById.getPath())) {
                    resultList.add(renderById);
                }
            } else if ("layout".equals(type)) {
                SysUiLayout layoutById = SysUiPluginUtil.getLayoutById(item);
                if (layoutById != null && StringUtil.isNotNull(layoutById.getPath())) {
                    resultList.add(layoutById);
                }
            } else if ("footer".equals(type)) {
                SysPortalFooter sysPortalFooter = PortalUtil.getPortalFooters().get(item);
                if (sysPortalFooter != null && StringUtil.isNotNull(sysPortalFooter.getPath())) {
                    resultList.add(sysPortalFooter);
                }
            } else if ("header".equals(type)) {
                SysPortalHeader sysPortalHeader = PortalUtil.getPortalHeaders().get(item);
                if (sysPortalHeader != null && StringUtil.isNotNull(sysPortalHeader.getPath())) {
                    resultList.add(sysPortalHeader);
                }
            } else {
                //未找到，不进行处理
            }
        });
        return resultList;
    }

    /**
     * 导出页面背景图片
     *
     * @param rootPath
     * @param sysPortalPageDetailList
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/2 4:32 下午
     */
    private void exportPageBgImg(String rootPath, List<SysPortalPageDetail> sysPortalPageDetailList, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {

        String imageTargetPath = XmlReaderContext.UIEXT + File.separator + "portal" + File.separator + "page" + File.separator + "backgroundImage" + File.separator;
        String packagePath = rootPath + File.separator + "bg" + File.separator;
        Set<String> backgroundImageSet = getTagRef("backgroundimagepath", sysPortalPageDetailList, "template:include");
        FileUtil.createDirs(packagePath, true);
        backgroundImageSet.stream().forEach(item -> {
            try {
                String imagePath = ResourceUtil.KMSS_RESOURCE_PATH + item;
                if (item.indexOf("/") > -1 && FileUtil.getFile(imagePath).exists()) {
                    String[] split = item.split("\\/");
                    String imageName = split[split.length - 1];
                    if (StringUtil.isNotNull(imageName)) {
                        FileUtil.copy(ResourceUtil.KMSS_RESOURCE_PATH + item, packagePath + imageName);
                        SysFileStoreDTO sysFileStoreDTO = new SysFileStoreDTO();
                        sysFileStoreDTO.setFdId(imageName);
                        sysFileStoreDTO.setPackagePath("bg");
                        sysFileStoreDTO.setTargetPath(imageTargetPath);
                        fileStoreDTOList.add(sysFileStoreDTO);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * 从headerVars中分离attMain的ID
     *
     * @param headerVarsList
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/7/19 5:38 下午
     */
    private List<String> splitAttMainHeaderVars(List<String> headerVarsList) {

        List<String> attMainIdList = new ArrayList<>();
        if (CollectionUtils.isEmpty(headerVarsList)) {
            return attMainIdList;
        }
        for (String headerVars : headerVarsList) {
            if (StringUtil.isNull(headerVars)) {
                continue;
            }
            JSONObject jsonObject = JSONObject.parseObject(headerVars);
            if (jsonObject.get("headBg") == null) {
                continue;
            }
            String headBg = (String) jsonObject.get("headBg");
            if (StringUtil.isNull(headBg) || headBg.indexOf("fdId") < 0) {
                continue;
            }
            String[] split = headBg.split("&");
            for (int i = 0; i < split.length; i++) {
                if (split[i].indexOf("fdId") > -1) {
                    String[] fdIdSplit = split[i].split("=");
                    if (fdIdSplit.length == 2 && StringUtil.isNotNull(fdIdSplit[1])) {
                        attMainIdList.add(fdIdSplit[1]);
                    }
                }
            }
        }
        return attMainIdList;

    }

    /**
     * 通过headerVars中分离sourceId和类型
     *
     * @param headerVarsList
     * @description:
     * @return: java.util.List<KeyValue>
     * @author: wangjf
     * @time: 2021/8/3 10:58 上午
     */
    private static Map<String, List<KeyValue>> splitSourceListHeaderVars(List<String> headerVarsList) {
        Map<String, List<KeyValue>> sourceListMap = new HashMap<>();
        if (CollectionUtils.isEmpty(headerVarsList)) {
            return sourceListMap;
        }
        List<KeyValue> sourceMapList = new ArrayList<>();
        for (String headerVars : headerVarsList) {
            if (StringUtil.isNotNull(headerVars)) {
                JSONObject headerVarsJson = JSONObject.parseObject(headerVars);
                headerVarsJson.forEach((key, value) -> {
                    if (value != null && StringUtil.isNotNull(value.toString())) {
                        String valueStr = value.toString();
                        if (valueStr.indexOf("context") > -1 && valueStr.indexOf("sourceId") > -1) {
                            JSONObject jsonObject = JSONObject.parseObject(valueStr);
                            JSONObject context = JSONObject.parseObject(jsonObject.get("context").toString());
                            KeyValue keyValue = new KeyValue(context.get("sourceId").toString(), context.get("id").toString());
                            sourceMapList.add(keyValue);
                        }
                    }
                });
            }
        }
        if (CollectionUtils.isEmpty(sourceMapList)) {
            return sourceListMap;
        }
        Map<String, List<KeyValue>> collect = sourceMapList.stream().collect(Collectors.groupingBy(KeyValue::getKey));
        return collect;
    }

    private static class KeyValue {

        private String key;
        private String value;

        public KeyValue() {
        }

        public KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "KeyValue{" + "key='" + key + '\'' + ", value='" + value + '\'' + '}';
        }
    }

    /**
     * 从url中分离attMainId
     *
     * @param urlList
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/7/21 2:19 下午
     */
    private List<String> splitAttMainUrl(List<String> urlList) throws Exception {

        List<String> attMainIdList = new ArrayList<>();
        if (CollectionUtils.isEmpty(urlList)) {
            return attMainIdList;
        }
        for (String url : urlList) {
            if (StringUtil.isNull(url) || url.indexOf("fdId") < 0) {
                continue;
            }
            String[] split = url.split("&");
            for (int i = 0; i < split.length; i++) {
                if (split[i].indexOf("fdId") > -1) {
                    String[] fdIdSplit = split[i].split("=");
                    if (fdIdSplit.length == 2 && StringUtil.isNotNull(fdIdSplit[1])) {
                        attMainIdList.add(fdIdSplit[1]);
                    }
                }
            }
        }
        return attMainIdList;
    }


    /**
     * 导出附件信息和素材
     *
     * @param rootPath
     * @param mainId
     * @param attMainIdList
     * @param fileStoreDTOList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/19 5:35 下午
     */
    private void exportAttMainMaterial(String rootPath, String mainId, List<String> attMainIdList, List<SysFileStoreDTO> fileStoreDTOList) throws Exception {
        if (CollectionUtils.isEmpty(attMainIdList)) {
            return;
        }
        List<String> collectIds = attMainIdList.stream().distinct().collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collectIds)) {
            return;
        }
        // 导出附件信息
        List<SysAttMainDTO> sysAttMainDTOList = new ArrayList<>();
        List<String> attFileIdList = new ArrayList<>();
        List<String> portalMaterialIdList = new ArrayList<>();
        HQLInfo hqlInfo = new HQLInfo();
        String where = "sysAttMain.fdId in (:fdIds)";
        hqlInfo.setWhereBlock(where);
        hqlInfo.setParameter("fdIds", collectIds);
        List<SysAttMain> sysAttMainList = getSysAttMainService().findList(hqlInfo);
        if (CollectionUtils.isEmpty(sysAttMainList)) {
            return;
        }
        for (SysAttMain sysAttMain : sysAttMainList) {
            SysAttMainDTO sysAttMainDTO = new SysAttMainDTO();
            BeanUtils.copyProperties(sysAttMain, sysAttMainDTO);
            sysAttMainDTOList.add(sysAttMainDTO);
            if (StringUtil.isNotNull(sysAttMain.getFdFileId())) {
                attFileIdList.add(sysAttMain.getFdFileId());
            }
            if (StringUtil.isNotNull(sysAttMain.getFdModelName()) && "com.landray.kmss.sys.portal.model.SysPortalMaterialMain".equals(sysAttMain.getFdModelName()) && StringUtil.isNotNull(sysAttMain.getFdModelId())) {
                portalMaterialIdList.add(sysAttMain.getFdModelId());
            }
        }
        if (CollectionUtils.isEmpty(sysAttMainDTOList)) {
            return;
        }

        String filePath = rootPath + PortalConstant.SYS_ATT_MAIN_EXPORT_PREFIX + mainId;
        if (FileUtil.getFile(filePath).exists()) {
            List<SysAttMainDTO> sysAttMainDTOS = JSONArray.parseArray(FileUtil.getFileString(filePath, FILE_ENCODING), SysAttMainDTO.class);
            if (!CollectionUtils.isEmpty(sysAttMainDTOS)) {
                sysAttMainDTOList.addAll(sysAttMainDTOS);
                FileUtil.deleteFile(filePath);
            }
        }
        //过滤重复数据
        List<SysAttMainDTO> collect = sysAttMainDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        FileUtil.createFile(filePath, JSONArray.toJSONString(collect), FILE_ENCODING);
        //导出素材数据
        exportSysPortalMaterial(rootPath, mainId, portalMaterialIdList);
        //导出文件
        exportAttFile(rootPath, mainId, attFileIdList, fileStoreDTOList);
    }

    /**
     * 导出素材库
     *
     * @param rootPath
     * @param mainId
     * @param portalMaterialIdList
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/21 11:06 上午
     */
    private void exportSysPortalMaterial(String rootPath, String mainId, List<String> portalMaterialIdList) throws Exception {

        if (CollectionUtils.isEmpty(portalMaterialIdList)) {
            return;
        }
        List<SysPortalMaterialMainDTO> sysPortalMaterialMainDTOList = new ArrayList<>();
        HQLInfo hqlInfo = new HQLInfo();
        String where = "sysPortalMaterialMain.fdId in (:fdIds)";
        hqlInfo.setWhereBlock(where);
        hqlInfo.setParameter("fdIds", portalMaterialIdList);
        List<SysPortalMaterialMain> sysPortalMaterialMainList = getSysPortalMaterialMainService().findList(hqlInfo);
        if (CollectionUtils.isEmpty(sysPortalMaterialMainList)) {
            return;
        }
        for (SysPortalMaterialMain sysPortalMaterialMain : sysPortalMaterialMainList) {

            SysPortalMaterialMainDTO sysPortalMaterialMainDTO = new SysPortalMaterialMainDTO();
            BeanUtils.copyProperties(sysPortalMaterialMain, sysPortalMaterialMainDTO);
            sysPortalMaterialMainDTOList.add(sysPortalMaterialMainDTO);
        }
        if (CollectionUtils.isEmpty(sysPortalMaterialMainDTOList)) {
            return;
        }

        String filePath = rootPath + PortalConstant.SYS_PORTAL_MATERIAL_EXPORT_PREFIX + mainId;
        //如果文件存在读取文件内容，并删除文件
        if (FileUtil.getFile(filePath).exists()) {
            List<SysPortalMaterialMainDTO> sysPortalMaterialMainDTOS = JSONArray.parseArray(FileUtil.getFileString(filePath, FILE_ENCODING), SysPortalMaterialMainDTO.class);
            if (!CollectionUtils.isEmpty(sysPortalMaterialMainDTOS)) {
                sysPortalMaterialMainDTOList.addAll(sysPortalMaterialMainDTOS);
                FileUtil.deleteFile(filePath);
            }
        }
        //过滤重复数据
        List<SysPortalMaterialMainDTO> collect = sysPortalMaterialMainDTOList.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
        String sysPortalNavDTOListStr = JSONArray.toJSONString(collect);
        FileUtil.createFile(filePath, sysPortalNavDTOListStr, FILE_ENCODING);
    }

    /**
     * 找出部件内容和外观的
     *
     * @param sysPortalPageDetailList
     * @description:
     * @return: java.util.List<java.lang.String>
     * @author: wangjf
     * @time: 2021/8/9 5:28 下午
     */
    private List<String> getPageContentLayoutImgAndPortletImgList(List<SysPortalPageDetail> sysPortalPageDetailList) {

        List<String> urlList = new ArrayList<>();
        for (SysPortalPageDetail sysPortalPageDetail : sysPortalPageDetailList) {
            if (StringUtil.isNull(sysPortalPageDetail.getDocContent())) {
                continue;
            }
            Document document = Jsoup.parseBodyFragment(sysPortalPageDetail.getDocContent());
            Elements portletSourceTags = document.getElementsByTag("script");
            for (Element Element : portletSourceTags) {
                JSONObject object = null;
                try {
                    object = JSONObject.parseObject(Element.data());
                } catch (RuntimeException e) {
                    logger.error("找出部件内容和外观发生异常", e);
                }
                if (object == null) {
                    continue;
                }
                String layoutOpt = object.getString("layoutOpt");
                if (StringUtil.isNotNull(layoutOpt)) {
                    JSONObject layoutOptJson = JSONObject.parseObject(layoutOpt);
                    if (StringUtil.isNotNull(layoutOptJson.getString("icon")) && layoutOptJson.getString("icon").indexOf("sysAttMain.do?method") > -1) {
                        urlList.add(layoutOptJson.getString("icon"));
                    }
                    if (StringUtil.isNotNull(layoutOptJson.getString("bg")) && layoutOptJson.getString("bg").indexOf("sysAttMain.do?method") > -1) {
                        urlList.add(layoutOptJson.getString("bg"));
                    }
                }
                String portlet = object.getString("portlet");
                if (StringUtil.isNotNull(portlet)) {
                    JSONArray portletJSONArray = JSONArray.parseArray(portlet);
                    if (portletJSONArray.size() > 0) {
                        for (int i = 0; i < portletJSONArray.size(); i++) {
                            JSONObject portletJson = portletJSONArray.getJSONObject(i);
                            if (StringUtil.isNotNull(portletJson.getString("titleimg"))) {
                                urlList.add(portletJson.getString("titleimg"));
                            }
                        }
                    }
                }
            }
        }
        return urlList;
    }

    @Override

    public void getExport(String portalMainId, HttpServletRequest request, HttpServletResponse response) throws Exception {

        //根目录
        String rootPath = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + portalMainId + File.separator;
        FileUtil.createDir(rootPath, true);
        // 文件存储路径
        List<SysFileStoreDTO> fileStoreDTOList = new ArrayList<>();
        //保存headerVars信息
        List<String> headerVarsList = new ArrayList<>();

        SysPortalMain sysPortalMain = (SysPortalMain) getSysPortalMainService().findByPrimaryKey(portalMainId);

        headerVarsList.add(sysPortalMain.getFdHeaderVars());
        exportSysPortalMain(rootPath, sysPortalMain, fileStoreDTOList, new RequestContext(request));
        exportSysPortalMainPage(rootPath, sysPortalMain.getFdId(), sysPortalMain.getPages());

        List<SysPortalPage> sysPortalPageList = new ArrayList<>();
        List<SysPortalPageDetail> sysPortalPageDetailList = new ArrayList<>();
        for (SysPortalMainPage sysPortalMainPage : sysPortalMain.getPages()) {
            sysPortalPageList.add(sysPortalMainPage.getSysPortalPage());
            if (!CollectionUtils.isEmpty(sysPortalMainPage.getSysPortalPage().getPageDetails())) {
                sysPortalPageDetailList.addAll(sysPortalMainPage.getSysPortalPage().getPageDetails());
            }
        }
        //收集所有页眉自定义选项
        if (!CollectionUtils.isEmpty(sysPortalPageDetailList)) {
            List<String> detailHeaderVarsList = sysPortalPageDetailList.stream().map(item -> item.getFdHeaderVars()).collect(Collectors.toList());
            headerVarsList.addAll(detailHeaderVarsList);
        }
        //素材库list
        List<String> attMainIdList = splitAttMainHeaderVars(headerVarsList);
        // 分离页面上面引用的门户部件的类型和ID
        Map<String, List<KeyValue>> sourceListMap = splitSourceListHeaderVars(headerVarsList);

        exportSysPortalPage(rootPath, sysPortalMain.getFdId(), sysPortalPageList);
        exportSysPortalPageDetail(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, fileStoreDTOList);
        exportSysPortalHtml(rootPath, sysPortalMain.getFdId(), new RequestContext(request), sysPortalPageDetailList);
        exportSysAttFile(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, fileStoreDTOList);
        exportSysPortalSysNavTag(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, fileStoreDTOList, sourceListMap.get("sys.portal.sysnav.source"));
        exportSysPortalSysNav(rootPath, sysPortalMain.getFdId(), sysPortalPageList, fileStoreDTOList);
        exportSysPortalLinking(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, fileStoreDTOList, sourceListMap.get("sys.portal.linking.source"));
        exportSysPortalShortcut(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, fileStoreDTOList, sourceListMap.get("sys.portal.shortcut.source"));
        exportSysPortalTree2(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, sourceListMap.get("sys.portal.treeMenu2.source"));
        exportSysPortalTree3(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, sourceListMap.get("sys.portal.treeMenu3.source"));
        exportSysPortalTopic(rootPath, sysPortalMain.getFdId(), sysPortalPageDetailList, fileStoreDTOList, sourceListMap.get("sys.portal.topic.source"));
        //主题
        Set<String> themeSet = new HashSet<>();
        themeSet.add(sysPortalMain.getFdTheme());

        for (SysPortalPage sysPortalPage : sysPortalPageList) {
            themeSet.add(sysPortalPage.getFdTheme());
        }
        //导出主题
        exportSysPortalTheme(rootPath, themeSet, fileStoreDTOList);
        //拓展页眉页脚
        Set<String> headerSet = new HashSet<>();
        Set<String> footerSet = new HashSet<>();
        //主页面页眉页脚
        footerSet.add(sysPortalMain.getFdFooterId());
        headerSet.add(sysPortalMain.getFdHeaderId());
        //页面详情页眉页脚
        for (SysPortalPageDetail sysPortalPageDetail : sysPortalPageDetailList) {
            footerSet.add(sysPortalPageDetail.getFdFooter());
            headerSet.add(sysPortalPageDetail.getFdHeader());
        }
        if (!CollectionUtils.isEmpty(footerSet)) {
            exportSysExtendFooter(rootPath, footerSet, fileStoreDTOList);
        }
        if (!CollectionUtils.isEmpty(headerSet)) {
            exportSysExtendHeader(rootPath, headerSet, fileStoreDTOList);
        }
        //页面模板
        Set<String> templateSet = getTagRef("ref", sysPortalPageDetailList, "template:include");
        if (!CollectionUtils.isEmpty(templateSet)) {
            exportSysExtendTemplate(rootPath, templateSet, fileStoreDTOList);
        }
        //面板处理
        Set<String> layoutSet = getTagRef("layout", sysPortalPageDetailList, "ui:nonepanel", "ui:tabpanel", "ui:panel", "ui:accordionpanel", "ui:drawerpanel");
        if (!CollectionUtils.isEmpty(layoutSet)) {
            //pannel处理
            exportSysExtendPanel(rootPath, layoutSet, fileStoreDTOList);
        }

        // render处理
        Set<String> renderSet = getTagRef("ref", sysPortalPageDetailList, "ui:render");
        if (!CollectionUtils.isEmpty(renderSet)) {
            // render处理
            exportSysExtendRender(rootPath, renderSet, fileStoreDTOList);
        }
        //导出背景图片
        exportPageBgImg(rootPath, sysPortalPageDetailList, fileStoreDTOList);

        //找出呈现背景图，在配置门户组件中的高级选项呈现中设置
        Set<String> varBgSet = getTagRef("var-bg", sysPortalPageDetailList, "ui:render");
        if (!CollectionUtils.isEmpty(varBgSet)) {
            List<String> attMainUrlIds = splitAttMainUrl(varBgSet.stream().collect(Collectors.toList()));
            if (!CollectionUtils.isEmpty(attMainUrlIds)) {
                attMainIdList.addAll(attMainUrlIds);
            }
        }
        //素材库URL集合
        List<String> attMainMaterialList = new ArrayList<>();
        //自定义页面中引用的素材库
        List<String> htmlAttMainUrlList = getHtmlAttMainUrl(sysPortalPageDetailList);
        if (!CollectionUtils.isEmpty(htmlAttMainUrlList)) {
            attMainMaterialList.addAll(htmlAttMainUrlList);
        }
        // 找出部件的内容图片和外观素材图片
        List<String> pageContentLayoutImgAndPortletImgList = getPageContentLayoutImgAndPortletImgList(sysPortalPageDetailList);
        if (!CollectionUtils.isEmpty(pageContentLayoutImgAndPortletImgList)) {
            attMainMaterialList.addAll(pageContentLayoutImgAndPortletImgList);
        }
        // 门户自定义素材库
        if (StringUtil.isNotNull(sysPortalMain.getFdImg())) {
            attMainMaterialList.add(sysPortalMain.getFdImg());
        }
        //页面自定义素材库
        List<String> collectImg = sysPortalPageList.stream().filter(item -> StringUtil.isNotNull(item.getFdImg())).map(item -> item.getFdImg()).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(collectImg)) {
            attMainMaterialList.addAll(collectImg);
        }
        //从URL中提取id
        List<String> attMainUrlList = splitAttMainUrl(attMainMaterialList);
        if (!CollectionUtils.isEmpty(attMainUrlList)) {
            attMainIdList.addAll(attMainUrlList);
        }
        //导出自定义素材库中的图片信息
        exportAttMainMaterial(rootPath, sysPortalMain.getFdId(), attMainIdList, fileStoreDTOList);

        //存储文件位置信息
        if (!CollectionUtils.isEmpty(fileStoreDTOList)) {
            String sysAttFileDTOListStr = JSONArray.toJSONString(fileStoreDTOList);
            String attFilePath = rootPath + PortalConstant.SYS_FILE_STORE_EXPORT_PREFIX + sysPortalMain.getFdId();
            FileUtil.createFile(attFilePath, sysAttFileDTOListStr, FILE_ENCODING);
        }

        String zipFileName = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + portalMainId + ".zip";
        ZipUtil.zip(zipFileName, rootPath, true);
        downloadZip(new File(zipFileName), request, response);
    }

    /**
     * 文件下载
     *
     * @param file
     * @param request
     * @param response
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/19 2:54 下午
     */
    private void downloadZip(File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            response.reset();
            // application/zip
            String contentType = FileMimeTypeUtil.getContentType(file);
            response.setContentType(contentType);
            // 解决ie6下载附件问题,ie8在https下的下载附件问题
            response.setHeader("Pragma", "public");
            String open = request.getParameter("open");
            if (StringUtil.isNotNull(open)) {
                response.setHeader("Content-Disposition", "filename=\"" + file.getName() + "\"");
            } else {
                response.setHeader("Content-Disposition", "attachment;filename=\"" + file.getName() + "\"");
            }
            out = response.getOutputStream();
            IOUtils.copy(in, out);
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null) {
                IOUtils.closeQuietly(out, null);
            }
            if (in != null) {
                IOUtils.closeQuietly(in, null);
            }
            //下载了就删除
            file.delete();
        }
    }

    /**
     * 处理文件附件相关
     *
     * @param storeFile
     * @param rootPath
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/17 5:58 下午
     */
    private void importFileStore(File storeFile, String rootPath) throws Exception {
        String fileContext = FileUtil.getFileString(storeFile.getPath(), FILE_ENCODING);
        if (StringUtil.isNull(fileContext)) {
            return;
        }
        List<SysFileStoreDTO> fileStoreDTOList = JSONArray.parseArray(fileContext, SysFileStoreDTO.class);
        boolean updateCache = false;
        for (SysFileStoreDTO sysFileStoreDTO : fileStoreDTOList) {
            FileUtil.createDirs(ResourceUtil.KMSS_RESOURCE_PATH + File.separator + sysFileStoreDTO.getTargetPath() + File.separator, true);
            String fileTarget = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + sysFileStoreDTO.getTargetPath() + File.separator + sysFileStoreDTO.getFdId();
            String fileSource = rootPath + File.separator + sysFileStoreDTO.getPackagePath() + File.separator + sysFileStoreDTO.getFdId();
            if (!FileUtil.getFile(fileSource).exists()) {
                continue;
            }
            FileUtil.createFile(new FileInputStream(fileSource), fileTarget);
            if (sysFileStoreDTO.getZipPackage()) {
                String folderTarget = fileTarget.substring(0, fileTarget.length() - ".zip".length());
                //如果已经存在了则不解压，并进行删除zip包
                if (FileUtil.getFile(folderTarget).exists()) {
                    FileUtil.deleteFile(fileTarget);
                } else {
                    //解压 并删除zip包
                    ZipUtil.unZip(fileTarget, true);
                    if (StringUtil.isNotNull(sysFileStoreDTO.getPackagePath()) && "themeZip".equals(sysFileStoreDTO.getPackagePath())) {
                        // 在集群时上传至统一存储oss ，并且把文件zip包存放在本地资源文件夹 ui_component 目录中，为后面从集群环境中做比对提供依据
                        SysFileLocationUtil.getFileService().writeOFolder(folderTarget, "/ui-ext/", sysFileStoreDTO.getFdId(), "/ui-ext/", null);
                    }
                    //拓展部件
                    if (StringUtil.isNotNull(sysFileStoreDTO.getHandleStatus()) && "extend".equals(sysFileStoreDTO.getHandleStatus())) {
                        //先上传至oss统一存储服务器
                        // 在集群时上传至统一存储oss ，并且把文件zip包存放在本地资源文件夹 ui_component 目录中，为后面从集群环境中做比对提供依据
                        SysFileLocationUtil.getFileService().writeOFolder(folderTarget, "/ui_component/", sysFileStoreDTO.getFdId(), "/ui_component/", null);
                        copyExtendUi2WebPath(folderTarget, sysFileStoreDTO.getFdId());
                    }
                    updateCache = true;
                }
            }
        }
        //更新集群消息
        if (updateCache) {
            // 更新集群缓存信息
            ResourceCacheListener.updateResourceCache();
        }
    }

    /**
     * 拷贝方法
     *
     * @param sourceFolderPath 源文件位置
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/26 2:52 下午
     */
    private void copyExtendUi2WebPath(String sourceFolderPath, String extendId) throws Exception {

        String extendUiPath = getWebContentPath() + SYS_PORTAL_UI + extendId.replaceAll(".zip", "");
        if (FileUtil.getFile(extendUiPath).exists()) {
            return;
        }
        FileUtil.createDirs(extendUiPath, true);
        //FileUtil.copyDir(); 该方法有bug，故使用apache提供的文件夹拷贝方法
        FileUtils.copyDirectory(new File(sourceFolderPath), new File(extendUiPath));
        //拷贝至附件的ui_component文件夹中
        String uiComponentPath = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + "ui_component" + File.separator;
        FileUtils.copyDirectory(new File(sourceFolderPath), new File(uiComponentPath));

    }

    /**
     * 导入SysPortalHtml
     *
     * @param files
     * @param requestContext
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/18 11:32 上午
     */
    private void importSysPortalHtml(File[] files, RequestContext requestContext) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysPortalHtmlDTO> sysPortalHtmlDTOList = JSONArray.parseArray(fileContext, SysPortalHtmlDTO.class);
            if (CollectionUtils.isEmpty(sysPortalHtmlDTOList)) {
                return;
            }
            //替换手动上传的图片路径
            String sourceImgDownloadPath = sysPortalHtmlDTOList.get(0).getContextPath() + "/resource/fckeditor/editor/filemanager/download";
            String targetImgDownloadPath = requestContext.getContextPath() + "/resource/fckeditor/editor/filemanager/download";
            //替换素材库的图标和图片路径
            String sourceMaterialDownloadPath = sysPortalHtmlDTOList.get(0).getContextPath() + "/sys/attachment/sys_att_main/sysAttMain.do";
            String targetMaterialDownloadPath = requestContext.getContextPath() + "/sys/attachment/sys_att_main/sysAttMain.do";
            //系统自身的source/icon下面的图片，由于换了工程名字
            String sourceMaterialSourcePath = sysPortalHtmlDTOList.get(0).getContextPath() + "/sys/portal/sys_portal_material_main/source/icon";
            String targetMaterialSourcePath = requestContext.getContextPath() + "/sys/portal/sys_portal_material_main/source/icon";

            for (SysPortalHtmlDTO sysPortalHtmlDTO : sysPortalHtmlDTOList) {
                SysPortalHtml sysPortalHtml = new SysPortalHtml();
                BeanUtils.copyProperties(sysPortalHtmlDTO, sysPortalHtml);
                sysPortalHtml.setDocCreator(UserUtil.getUser());
                if (getSysPortalHtmlService().findByPrimaryKey(sysPortalHtml.getFdId(), SysPortalHtml.class, true) == null) {
                    String content = sysPortalHtml.getFdContent().replaceAll(sourceImgDownloadPath, targetImgDownloadPath);
                    content = content.replaceAll(sourceMaterialDownloadPath, targetMaterialDownloadPath);
                    content = content.replaceAll(sourceMaterialSourcePath, targetMaterialSourcePath);
                    sysPortalHtml.setFdContent(content);
                    getSysPortalHtmlService().add(sysPortalHtml);
                }

            }
        }
    }

    /**
     * 导入SysAttRtfData
     *
     * @param files
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/18 11:41 上午
     */
    private void importSysAttRtfData(File[] files) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysAttRtfDataDTO> sysAttRtfDataDTOList = JSONArray.parseArray(fileContext, SysAttRtfDataDTO.class);
            for (SysAttRtfDataDTO sysAttRtfDataDTO : sysAttRtfDataDTOList) {
                SysAttRtfData sysAttRtfData = new SysAttRtfData();
                BeanUtils.copyProperties(sysAttRtfDataDTO, sysAttRtfData);
                SysAttRtfData dbSysAttRtfData = (SysAttRtfData) getSysAttMainService().getSysAttMainDao().findByPrimaryKey(sysAttRtfData.getFdId(), SysAttRtfData.class, true);
                if (dbSysAttRtfData == null) {
                    getSysAttMainService().getSysAttMainDao().add(sysAttRtfData);
                }
            }
        }

    }

    /**
     * 导入SysAttFile
     *
     * @param files
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/18 11:56 上午
     */
    private void importSysAttFile(File[] files) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysAttFileDTO> sysAttFileDTOList = JSONArray.parseArray(fileContext, SysAttFileDTO.class);
            for (SysAttFileDTO sysAttFileDTO : sysAttFileDTOList) {
                SysAttFile sysAttFile = new SysAttFile();
                BeanUtils.copyProperties(sysAttFileDTO, sysAttFile);
                if (getSysAttUploadDao().findByPrimaryKey(sysAttFile.getFdId(), SysAttFile.class, true) == null) {
                    getSysAttUploadDao().add(sysAttFile);
                }
            }
        }
    }

    /**
     * 导入SysPortalNav
     *
     * @param files
     * @param portalPageOldNewIdMap
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/18 1:40 下午
     */
    private void importSysPortalNav(File[] files, Map<String, String> portalPageOldNewIdMap) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysPortalNavDTO> sysPortalNavDTOList = JSONArray.parseArray(fileContext, SysPortalNavDTO.class);
            for (SysPortalNavDTO sysPortalNavDTO : sysPortalNavDTOList) {
                SysPortalNav sysPortalNav = new SysPortalNav();
                BeanUtils.copyProperties(sysPortalNavDTO, sysPortalNav);
                if (StringUtil.isNotNull(sysPortalNavDTO.getFdPageId())) {
                    String newPageId = portalPageOldNewIdMap.get(sysPortalNavDTO.getFdPageId());
                    if (StringUtil.isNotNull(newPageId)) {
                        sysPortalNav.setFdPageId(newPageId);
                    }
                }
                sysPortalNav.setDocCreator(UserUtil.getUser());
                if (getSysPortalNavService().findByPrimaryKey(sysPortalNav.getFdId(), SysPortalNav.class, true) == null) {
                    getSysPortalNavService().add(sysPortalNav);
                }
            }
        }
    }

    /**
     * 导入SysPortalLink
     *
     * @param files
     * @param detailFiles
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/18 2:04 下午
     */
    private void importSysPortalLink(File[] files, File[] detailFiles) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        Map<String, SysPortalLink> linkMap = new HashMap<>();
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysPortalLinkDTO> sysPortalLinkDTOList = JSONArray.parseArray(fileContext, SysPortalLinkDTO.class);
            for (SysPortalLinkDTO sysPortalLinkDTO : sysPortalLinkDTOList) {
                SysPortalLink sysPortalLink = new SysPortalLink();
                BeanUtils.copyProperties(sysPortalLinkDTO, sysPortalLink);
                sysPortalLink.setDocCreator(UserUtil.getUser());
                linkMap.put(sysPortalLinkDTO.getFdId(), sysPortalLink);
            }
        }
        for (File file : detailFiles) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysPortalLinkDetailDTO> sysPortalLinkDetailDTOList = JSONArray.parseArray(fileContext, SysPortalLinkDetailDTO.class);
            for (SysPortalLinkDetailDTO sysPortalLinkDetailDTO : sysPortalLinkDetailDTOList) {
                SysPortalLinkDetail sysPortalLinkDetail = new SysPortalLinkDetail();
                BeanUtils.copyProperties(sysPortalLinkDetailDTO, sysPortalLinkDetail);
                sysPortalLinkDetail.setFdId(IDGenerator.generateID());
                SysPortalLink sysPortalLink = linkMap.get(sysPortalLinkDetailDTO.getSysPortalLinkId());
                sysPortalLink.getFdLinks().add(sysPortalLinkDetail);
            }
        }
        //保存数据
        linkMap.forEach((key, value) -> {
            try {
                SysPortalLink sysPortalLink = (SysPortalLink) getSysPortalLinkService().findByPrimaryKey(value.getFdId(), SysPortalLink.class, true);
                if (sysPortalLink == null) {
                    getSysPortalLinkService().add(value);
                }
            } catch (Exception e) {
                logger.error("id={}", value.getFdId(), e);
                e.printStackTrace();
            }
        });
    }

    /**
     * 导入树
     *
     * @param files
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/19 11:24 上午
     */
    private void importSysPortalTree(File[] files) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysPortalTreeDTO> sysPortalTreeDTOList = JSONArray.parseArray(fileContext, SysPortalTreeDTO.class);
            for (SysPortalTreeDTO sysPortalTreeDTO : sysPortalTreeDTOList) {
                SysPortalTree sysPortalTree = new SysPortalTree();
                BeanUtils.copyProperties(sysPortalTreeDTO, sysPortalTree);
                sysPortalTree.setDocCreator(UserUtil.getUser());
                if (getSysPortalTreeService().findByPrimaryKey(sysPortalTree.getFdId(), SysPortalTree.class, true) == null) {
                    getSysPortalTreeService().add(sysPortalTree);
                }
            }
        }
    }

    /**
     * 导入推荐
     *
     * @param files
     * @param portPageMap
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/6/19 11:27 上午
     */
    private void importSysPortalTopic(File[] files, Map<String, String> portPageMap) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysPortalTopicDTO> sysPortalTopicDTOList = JSONArray.parseArray(fileContext, SysPortalTopicDTO.class);
            for (SysPortalTopicDTO sysPortalTopicDTO : sysPortalTopicDTOList) {
                SysPortalTopic sysPortalTopic = new SysPortalTopic();
                BeanUtils.copyProperties(sysPortalTopicDTO, sysPortalTopic);
                //保持关系的更新
                sysPortalTopic.setDocCreator(UserUtil.getUser());
                if (getSysPortalTopicService().findByPrimaryKey(sysPortalTopic.getFdId(), SysPortalTopic.class, true) == null) {
                    getSysPortalTopicService().add(sysPortalTopic);
                }
            }
        }
    }

    /**
     * 导入附件主表
     *
     * @param files
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/2 8:20 下午
     */
    private void importSysAttMain(File[] files) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysAttMainDTO> sysAttMainDTOList = JSONArray.parseArray(fileContext, SysAttMainDTO.class);
            for (SysAttMainDTO sysAttMainDTO : sysAttMainDTOList) {
                SysAttMain sysAttMain = new SysAttMain();
                BeanUtils.copyProperties(sysAttMainDTO, sysAttMain);
                if (getSysAttMainService().findByPrimaryKey(sysAttMainDTO.getFdId(), SysAttMain.class, true) == null) {
                    getSysAttMainService().add(sysAttMain);
                }
            }
        }
    }

    /**
     * 导入素材
     *
     * @param files
     * @description:
     * @return: void
     * @author: wangjf
     * @time: 2021/7/21 11:11 上午
     */
    private void importSysPortalMaterial(File[] files) throws Exception {
        if (files == null || files.length == 0) {
            return;
        }
        for (File file : files) {
            String fileContext = FileUtil.getFileString(file.getPath(), FILE_ENCODING);
            if (StringUtil.isNull(fileContext)) {
                continue;
            }
            List<SysPortalMaterialMainDTO> sysPortalMaterialMainDTOList = JSONArray.parseArray(fileContext, SysPortalMaterialMainDTO.class);
            for (SysPortalMaterialMainDTO sysPortalMaterialMainDTO : sysPortalMaterialMainDTOList) {
                SysPortalMaterialMain sysPortalMaterialMain = new SysPortalMaterialMain();
                BeanUtils.copyProperties(sysPortalMaterialMainDTO, sysPortalMaterialMain);
                if (getSysPortalMaterialMainService().findByPrimaryKey(sysPortalMaterialMainDTO.getFdId(), SysPortalMaterialMain.class, true) == null) {
                    getSysPortalMaterialMainService().add(sysPortalMaterialMain);
                }
            }
        }
    }

    /**
     * 替换logo背景图
     *
     * @param sysPortalPageDetailDTO
     * @param importContextPath
     * @param localContextPath
     * @description:
     * @return: com.landray.kmss.sys.portal.dto.SysPortalPageDetailDTO
     * @author: wangjf
     * @time: 2021/7/13 1:46 下午
     */
    private SysPortalPageDetailDTO replaceLogoImg(SysPortalPageDetailDTO sysPortalPageDetailDTO, String importContextPath, String localContextPath) {

        if (StringUtil.isNull(sysPortalPageDetailDTO.getDocContent())) {
            return sysPortalPageDetailDTO;
        }
        String imagePath = "";
        Document document = Jsoup.parseBodyFragment(sysPortalPageDetailDTO.getDocContent());
        Elements imgSourceTags = document.getElementsByTag("img");
        for (Element element : imgSourceTags) {
            if ("logoImg".equals(element.attr("class"))) {
                imagePath = element.attr("src");
                break;
            }
        }

        if (StringUtil.isNull(imagePath)) {
            return sysPortalPageDetailDTO;
        }
        String newImagePath = imagePath.replace(importContextPath, localContextPath);
        String docContent = sysPortalPageDetailDTO.getDocContent().replaceAll(imagePath, newImagePath);
        sysPortalPageDetailDTO.setDocContent(docContent);
        return sysPortalPageDetailDTO;
    }

    /**
     * 获取正确的WebPath路径
     *
     * @param
     * @description:
     * @return: java.lang.String
     * @author: wangjf
     * @time: 2021/8/23 12:03 下午
     */
    private String getWebContentPath() {
        if (System.getProperties().getProperty("os.name").toLowerCase().contains("window")) {
            //判断操作系统，如果操作系统是windows则路径的第一个字母必须是字母
            if (!PluginConfigLocationsUtil.getWebContentPath().substring(0, 1).matches("[a-zA-Z]+")) {
                return PluginConfigLocationsUtil.getWebContentPath().substring(1, PluginConfigLocationsUtil.getWebContentPath().length());
            }
        }
        return PluginConfigLocationsUtil.getWebContentPath();

    }

    @Override
    public String saveImport(String filePath, RequestContext requestContext) throws Exception {

        String filePathTemp = filePath.substring(0, filePath.length() - ".zip".length());
        FileUtil.deleteFile(filePathTemp);
        ZipUtil.unZip(filePath, true);
        //删除危险文件
        File[] deleteFiles = FileUtil.searchFiles(filePathTemp, "*.exe");
        for (File file : deleteFiles) {
            file.delete();
        }
        //优先处理主题和附件信息,因为页面在插入数据库的时候需要调用这些控件
        File[] fileStores = FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_FILE_STORE_EXPORT_PREFIX + "*");
        for (File file : fileStores) {
            importFileStore(file, filePathTemp);
        }
        TransactionStatus status = null;
        String mainId = IDGenerator.generateID();
        try {
            status = TransactionUtils.beginNewTransaction();
            //导入系统的路径
            String importContextPath = "";
            //本系统的路径
            String localContextPath = requestContext.getContextPath();
            //先重建各种关系 开始
            //理论上只有一个main，多了也不处理，不支持子门户嵌套的处理
            File[] portalMainFiles = FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_MAIN_EXPORT_PREFIX + "*");
            SysPortalMainDTO sysPortalMainDTO = JSONObject.parseObject(FileUtil.getFileString(portalMainFiles[0].getPath(), FILE_ENCODING), SysPortalMainDTO.class);
            SysPortalMain sysPortalMain = new SysPortalMain();
            BeanUtils.copyProperties(sysPortalMainDTO, sysPortalMain);
            //重建ID
            sysPortalMain.setFdId(mainId);
            importContextPath = sysPortalMainDTO.getContextPath();

            //保存新的对象与旧的ID关系
            Map<String, SysPortalPage> portalPageOldIdMap = new HashMap<>();
            Map<String, String> portalPageOldNewIdMap = new HashMap<>();
            File[] portalPageFiles = FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_PAGE_EXPORT_PREFIX + "*");
            List<SysPortalPageDTO> sysPortalPageDTOList = JSONArray.parseArray(FileUtil.getFileString(portalPageFiles[0].getPath(), FILE_ENCODING), SysPortalPageDTO.class);
            for (SysPortalPageDTO sysPortalPageDTO : sysPortalPageDTOList) {
                SysPortalPage sysPortalPage = new SysPortalPage();
                BeanUtils.copyProperties(sysPortalPageDTO, sysPortalPage);
                //重建ID
                sysPortalPage.setFdId(IDGenerator.generateID());
                portalPageOldIdMap.put(sysPortalPageDTO.getFdId(), sysPortalPage);
                portalPageOldNewIdMap.put(sysPortalPageDTO.getFdId(), sysPortalPage.getFdId());
            }

            File[] portalPageDetailFiles = FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_PAGE_DETAIL_EXPORT_PREFIX + "*");
            List<SysPortalPageDetailDTO> sysPortalPageDetailDTOList = JSONArray.parseArray(FileUtil.getFileString(portalPageDetailFiles[0].getPath(), FILE_ENCODING), SysPortalPageDetailDTO.class);
            for (SysPortalPageDetailDTO sysPortalPageDetailDTO : sysPortalPageDetailDTOList) {
                //根据系统替换logo的路径
                sysPortalPageDetailDTO = replaceLogoImg(sysPortalPageDetailDTO, importContextPath, localContextPath);
                SysPortalPageDetail sysPortalPageDetail = new SysPortalPageDetail();
                BeanUtils.copyProperties(sysPortalPageDetailDTO, sysPortalPageDetail);
                sysPortalPageDetail.setFdId(IDGenerator.generateID());
                SysPortalPage sysPortalPage = portalPageOldIdMap.get(sysPortalPageDetailDTO.getFdPageId());
                sysPortalPageDetail.setSysPortalPage(sysPortalPage);
                //加入子页面
                sysPortalPage.getPageDetails().add(sysPortalPageDetail);
            }


            File[] portalMainPageFiles = FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_MAIN_PAGE_EXPORT_PREFIX + "*");
            List<SysPortalMainPageDTO> sysPortalMainPageDTOList = JSONArray.parseArray(FileUtil.getFileString(portalMainPageFiles[0].getPath(), FILE_ENCODING), SysPortalMainPageDTO.class);
            List<SysPortalMainPage> sysPortalMainPageList = new ArrayList<>();
            for (SysPortalMainPageDTO sysPortalMainPageDTO : sysPortalMainPageDTOList) {
                SysPortalMainPage sysPortalMainPage = new SysPortalMainPage();
                BeanUtils.copyProperties(sysPortalMainPageDTO, sysPortalMainPage);
                //建立关系
                sysPortalMainPage.setSysPortalMain(sysPortalMain);
                SysPortalPage sysPortalPage = portalPageOldIdMap.get(sysPortalMainPageDTO.getFdPortalPageId());
                sysPortalMainPage.setSysPortalPage(sysPortalPage);
                sysPortalMainPage.setFdId(IDGenerator.generateID());
                sysPortalMainPageList.add(sysPortalMainPage);
            }
            //保存对象
            portalPageOldIdMap.forEach((key, value) -> {
                try {
                    getSysPortalPageService().saveAdd(value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            sysPortalMain.setPages(sysPortalMainPageList);
            //保存对象
            getSysPortalMainService().add(sysPortalMain);
            //重建关系结束
            importSysPortalHtml(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_HTML_EXPORT_PREFIX + "*"), requestContext);
            importSysAttRtfData(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_ATT_RTF_DATA_EXPORT_PREFIX + "*"));
            importSysAttFile(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_ATT_FILE_EXPORT_PREFIX + "*"));
            importSysPortalNav(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_NAV_EXPORT_PREFIX + "*"), portalPageOldNewIdMap);
            importSysPortalLink(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_LINK_EXPORT_PREFIX + "*"),
                    FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_LINK_DETAIL_EXPORT_PREFIX + "*"));
            importSysPortalTree(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_TREE_EXPORT_PREFIX + "*"));
            importSysPortalTopic(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_TOPIC_EXPORT_PREFIX + "*"), portalPageOldNewIdMap);
            importSysAttMain(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_ATT_MAIN_EXPORT_PREFIX + "*"));
            importSysPortalMaterial(FileUtil.searchFiles(filePathTemp, PortalConstant.SYS_PORTAL_MATERIAL_EXPORT_PREFIX + "*"));
            TransactionUtils.commit(status);
        } catch (Exception e) {
            // 发生异常则整体事务回滚
            if (status != null) {
                TransactionUtils.rollback(status);
            }
            logger.error("导入门户失败", e);
            throw e;
        }
        FileUtil.deleteFile(filePathTemp);
        return mainId;
    }

    @Override
    public String saveUpload(RequestContext requestContext, SysPortalPackageForm sysPortalPackageForm) throws Exception {

        String tmpFilePath = ResourceUtil.KMSS_RESOURCE_PATH + File.separator + "portalTmp" + File.separator;
        FileUtil.createDir(tmpFilePath, true);
        FormFile file = sysPortalPackageForm.getFile();
        String folderPath = tmpFilePath + File.separator + IDGenerator.generateID() + ".zip";
        File zipFile = new File(folderPath);
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(zipFile);
            IOUtils.copy(file.getInputStream(), output);
            output.close();
        } catch (Exception e) {
            logger.error("上传门户发生异常", e);
            throw e;
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                logger.error("上传门户关闭输出流失败", e);
            }
        }
        return folderPath;
    }
}