package com.landray.kmss.sys.portal.cloud.controller;

import com.landray.kmss.sys.config.design.SysCfgModuleInfo;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.portal.cloud.*;
import com.landray.kmss.sys.portal.cloud.dto.PortletConfigVO;
import com.landray.kmss.sys.portal.cloud.dto.PortletRequestVO;
import com.landray.kmss.sys.portal.cloud.dto.SysPortalDataModuleVO;
import com.landray.kmss.sys.portal.cloud.dto.SysPortalDataSourceVO;
import com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil;
import com.landray.kmss.sys.portal.cloud.util.PortletConstants;
import com.landray.kmss.sys.ui.util.ForSystemType;
import com.landray.kmss.sys.ui.xml.model.*;
import com.landray.kmss.util.MD5Util;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.KmssMediaTypes;
import com.landray.kmss.web.annotation.RestApi;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

import static com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil.dataSourceCache;
import static com.landray.kmss.sys.portal.cloud.util.CloudPortalUtil.moduleCache;

/**
 * cloud请求此接口，获取模块和数据源信息
 *
 * @author chao
 */
@Controller
@RequestMapping(value = "/api/sys-portal/portalDataSource", produces = {
        KmssMediaTypes.APPLICATION_JSON_VALUE,
        KmssMediaTypes.APPLICATION_XML_VALUE}, consumes = {
        KmssMediaTypes.APPLICATION_JSON_VALUE})
@RestApi(docUrl = "/sys/portal/portalDataSourceDoc.jsp", name = "sysPortalDataSourceController", resourceKey = "sys-portal:service.name.dataSourceController")
public class SysPortalDataSourceController {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysPortalDataSourceController.class);

    /**
     * 获取资源信息列表
     *
     * @return 模块、备注、md5Code
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/findModule", produces = KmssMediaTypes.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public List<SysPortalDataModuleVO> findModule(HttpServletRequest req,
                                                  HttpServletResponse resp) {
        logger.info("findModule execute...");
        if (!CollectionUtils.isEmpty(moduleCache)) {
            return moduleCache;
        }
        List<SysCfgModuleInfo> modules = SysConfigs.getInstance()
                .getModuleInfoList();
        List<SysPortalDataModuleVO> list = new ArrayList<>();
        for (SysCfgModuleInfo moduleInfo : modules) {
            if (isValidModule(moduleInfo)) {
                try {
                    list.add(createDataModuleVOByModuleInfo(moduleInfo));
                } catch (Exception e) {
                    logger.error("获取模块出错！" + moduleInfo.getUrlPrefix(), e);
                }
            }
        }
        moduleCache.addAll(list);
        return list;
    }

    /**
     * 获取模块下的portlet 列表信息
     *
     * @return
     * @RequestBody SysPortalDataModuleVO vo <BR>
     * @RequestParam("code") String code
     */
    @RequestMapping(value = "/findDataSource", produces = KmssMediaTypes.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseBody
    public List<SysPortalDataSourceVO>
    findDataSource(@RequestBody SysPortalDataModuleVO vo) {
        String code = vo.getFdCode();
        logger.info("findDataSource execute... moduleCode is " + code);
        Assert.notNull(code, "模块名不能为空!");
        List<SysPortalDataSourceVO> list = getSourceVOsByModule(code);
        return list;
    }

    /**
     * 是否是合理的模块
     *
     * @param info
     * @return
     */
    private boolean isValidModule(SysCfgModuleInfo info) {
        String fdName = ResourceUtil.getString(info.getMessageKey());
        if (StringUtil.isNull(fdName)) {
            logger.info(info.getUrlPrefix() + "模块的fdName为空，校验不通过！");
            return false;
        }
        String moduleCode = getModuleCode(info);
        if (StringUtil.isNull(moduleCode)) {
            logger.info(info.getUrlPrefix() + "模块无法获取模块code!校验不通过！");
            return false;
        }
        if (getSourcesByModule(moduleCode).size() <= 0) {
            logger.info(info.getUrlPrefix() + "模块未含有数据源!校验不通过！");
            return false;
        }
        return true;
    }

    /**
     * 获得模块的Code
     *
     * @param info
     * @return 例如：km-review
     */
    private String getModuleCode(SysCfgModuleInfo info) {
        String moduleCode = null;
        if (StringUtil.isNotNull(info.getMessageKey())) {
            moduleCode = info.getMessageKey().split(":")[0];
        } else {
            String urlPrefix = info.getUrlPrefix();
            if (urlPrefix.startsWith("/")) {
                urlPrefix = urlPrefix.substring(1);
            }
            if (urlPrefix.endsWith("/")) {
                urlPrefix = urlPrefix.substring(0, urlPrefix.length() - 1);
            }
            urlPrefix = urlPrefix.replace("/", "-");
            moduleCode = urlPrefix;
        }
        if (StringUtil.isNull(moduleCode) || moduleCode.length() > 36) {
            logger.info(
                    info.getUrlPrefix() + "模块的fdCode不符合要求：" + moduleCode);
            return null;
        }
        return moduleCode;
    }

    /**
     * 根据原来的数据源和模块信息创建新数据源VO
     *
     * @param source
     * @param moduleCode
     * @return
     */
    private SysPortalDataSourceVO
    createDataSourceVOByModuleInfo(SysUiSource source,
                                   String moduleCode) {
        MessageCache.clear();
        SysPortalDataSourceVO vo = new SysPortalDataSourceVO();
        vo.setFdCode(source.getFdId());
        // 将数据源适配的所有数据格式都获取
        vo.setFdFormatCode(source.getFdFormat());
        vo.setFdModuleCode(moduleCode);
        vo.setFdName(source.getFdName());
        vo.setFdAnonymous(source.getFdAnonymous());
        // 有则取数据源的
        if (StringUtil.isNotNull(source.getFdThumb())) {
            vo.setFdThumbnail(formatThumnailUrl(source.getFdThumb()));
        } else {
            // 无则取默认的：sourceId+".png"
            vo.setFdThumbnail(getDefaultThumbnail(source));
        }
        if (SysLangUtil.isLangEnabled()) {
            Map<String, String> langMap = getLangMap("fdName",
                    source.getFdNameKey());
            vo.setDynamicProps(langMap);
        }
        vo.setFdContent(getSourceContent(source));
        if (StringUtil.isNotNull(source.getFdFormat())) {
            // 先按数据源ID取呈现
            String render = PortalMappingHelper.getRenderCodeMapping(source.getFdId());
            if (StringUtil.isNull(render)) {
                // 如果上面没有找到呈现，再按数据格式取呈现
                render = PortalMappingHelper.getRenderCodeMapping(source.getFdFormat());
            }
            vo.setFdRenders(render);
        }
        // 将多语言保存
        if (MessageCache.get() != null) {
            vo.getResourceMap().putAll(MessageCache.get());
        }
        MessageCache.clear();
        return vo;
    }

    /**
     * 默认数据源缩略图，sourceId+".png"
     *
     * @param source
     * @return
     */
    private String getDefaultThumbnail(SysUiSource source) {
        String imageName = source.getFdId() + ".png";
        String imageUrl = PortletConstants.THUMBNAIL_PREFIX_URL + "/"
                + imageName;
        File image = new File(
                ConfigLocationsUtil.getWebContentPath() + imageUrl);
        // 默认缩略图存在才返回
        if (image.exists()) {
            return formatThumnailUrl(imageName);
        }
        return null;
    }

    private String formatThumnailUrl(String url) {
        String domain = ResourceUtil
                .getKmssConfigString("kmss.urlPrefix");
        if (domain.endsWith("/")) {
            domain = domain.substring(0, domain.length() - 1);
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        return domain + PortletConstants.THUMBNAIL_PREFIX_URL + url;
    }

    /**
     * 获得数据源的内容字段，包含url，param，var，operation等信息
     *
     * @param source
     * @return
     */
    private String getSourceContent(SysUiSource source) {
        String id = source.getFdId();
        JSONObject json = new JSONObject();
        if (StringUtil.isNotNull(source.getFdPortletId())) {
            SysUiPortlet portlet = CloudPortalCache
                    .getPortletById(source.getFdPortletId());
            if (null != portlet) {
                json.put("request", getPortletRequestVOBySource(source));
                json.put("dataFields", getDataFields(source));
                // source和portlet的vars的集合，使用set去重
                Set<SysUiVar> vars = new HashSet<>();
                vars.addAll(portlet.getFdVars());
                vars.addAll(source.getFdVars());
                json.put("configs", getConfigsByVars(vars));
                json.put("operations",
                        getOperations(portlet.getFdOperations()));
            } else {
                logger.error("cloud数据源" + id + "对应的门户部件"
                        + source.getFdPortletId() + "未定义！");
            }
        } else {
            // 定义在source.xml中的是没有.source
            json.put("request", getPortletRequestVOBySource(source));
            json.put("configs",
                    getConfigsByVars(new HashSet<>(source.getFdVars())));
        }
        return json.toString();
    }

    /**
     * 解析var中的多语言，并转为PortletConfigVO
     *
     * @param vars
     * @return
     */
    private JSONArray getConfigsByVars(Set<SysUiVar> vars) {
        if (CollectionUtils.isEmpty(vars)) {
            return null;
        }
        JSONArray array = new JSONArray();
        VarParser parser = new VarParser();
        for (SysUiVar var : vars) {
            PortletConfigVO vo = parser.parse(var);
            JSONObject json = JSONObject.fromObject(vo);
            /** 以下两步cloud那边传给前端的时候已经做了，这里就不重复做了 **/
            // 将defaultValue转为default
            // json.put("default", vo.getDefaultValue());
            // json.remove("defaultValue");
            // // content中的数据提出来
            // if (!CollectionUtils.isEmpty(vo.getContent())) {
            // json.putAll(vo.getContent());
            // json.remove("content");
            // }
            array.add(json);
        }
        return array;
    }

    /**
     * 解析operations中的多语言
     *
     * @param operations
     * @return
     */
    private List<SysUiOperation>
    getOperations(List<SysUiOperation> operations) {
        if (CollectionUtils.isEmpty(operations)) {
            return null;
        }
        List<SysUiOperation> rtnOpts = new ArrayList<>();
        OperationParser parser = new OperationParser();
        for (int i = 0; i < operations.size(); i++) {
            rtnOpts.add(parser.parse(operations.get(i)));
        }
        return rtnOpts;
    }

    @SuppressWarnings("unchecked")
    private PortletRequestVO getPortletRequestVOBySource(SysUiSource source) {
        PortletRequestVO vo = new PortletRequestVO();
        SysUiCode code = source.getFdBody();
        String url = CloudPortalUtil.getUrlByCode(code);
        // 构造参数params，根据!{fdId}
        Map<String, Object> params = code.getParam();
        params.putAll(CloudPortalUtil.getUrlParams(url));
        vo.setParams(params);
        // 去除url中的参数
        int index = url.indexOf("?");
        if (index > -1) {
            url = url.substring(0, index);
        }
        vo.setUrl(CloudPortalUtil.addAppNameInUri(url));
        // type
        vo.setDataType(source.getFdType().split("!")[1]);
        if (StringUtil.isNotNull(source.getExample())) {
            String example = source.getExample();
            try {
                example = JSONArray.fromObject(example).toString();
            } catch (Exception e) {
                example = JSONObject.fromObject(example).toString();
            }
            vo.setExample(example);
        }
        return vo;
    }

    private Map<String, String> getLangMap(String fieldName, String key) {
        Map<String, String> nameMap = new HashMap<>();
        LinkedHashMap<String, String> langs = SysLangUtil
                .getSupportedLangs();
        Iterator<String> it = langs.keySet().iterator();
        while (it.hasNext()) {
            String country = it.next();
            Locale locale = SysLangUtil
                    .getLocaleByShortName(langs.get(country));
            String nameKey = key;
            if (StringUtil.isNotNull(nameKey)) {
                if (nameKey.indexOf(":") > -1) {
                    nameKey = nameKey.replace("{", "")
                            .replace("}", "");
                }
                String nameLang = ResourceUtil.getStringValue(nameKey, null,
                        locale);
                // null, locale);
                nameMap.put(fieldName + country, nameLang);
            }
        }
        return nameMap;
    }

    /**
     * 根据原模块信息创建模块信息VO
     *
     * @param info
     * @return
     */
    private SysPortalDataModuleVO
    createDataModuleVOByModuleInfo(SysCfgModuleInfo info) {
        SysPortalDataModuleVO vo = new SysPortalDataModuleVO();
        vo.setFdName(ResourceUtil.getString(info.getMessageKey()));
        if (SysLangUtil.isLangEnabled()) {
            Map<String, String> langMap = getLangMap("fdName",
                    info.getMessageKey());
            vo.setDynamicProps(langMap);
        }
        vo.setFdCode(getModuleCode(info));
        String md5 = MD5Util.getMD5String(JSONArray
                .fromObject(getSourceVOsByModule(vo.getFdCode())).toString());
        vo.setFdMd5(md5);
        return vo;
    }

    /**
     * 根据模块名，获取模块下的所有数据源
     *
     * @param moduleName
     * @return
     */
    private List<SysUiSource> getSourcesByModule(String moduleName) {
        // moduleName = moduleName.replace("-", ".");
        Map<String, SysUiSource> sources = CloudPortalCache.getSources();
        List<SysUiSource> list = new ArrayList<>();
        Iterator<String> it = sources.keySet().iterator();
        while (it.hasNext()) {
            String sourceId = it.next();
            if (sourceId.startsWith(moduleName)) {
                SysUiSource source = sources.get(sourceId);
                // 在cloud中有映射的才会处理
                if (shouldReport(source)) {
                    list.add(source);
                }
            }
        }
        return list;
    }

    /**
     * 根据模块名，获取模块下的所有数据源VO对象
     *
     * @param moduleName
     * @return
     */
    private List<SysPortalDataSourceVO>
    getSourceVOsByModule(String moduleName) {
        List<SysPortalDataSourceVO> dataSources = dataSourceCache
                .get(moduleName);
        if (dataSources != null) {
            return dataSources;
        }
        // 设置当前处理的模块名
        CloudPortalUtil.setModule(moduleName);
        List<SysUiSource> sources = getSourcesByModule(moduleName);
        List<SysPortalDataSourceVO> list = new ArrayList<>();
        for (SysUiSource source : sources) {
            list.add(createDataSourceVOByModuleInfo(source, moduleName));
        }
        dataSourceCache.putIfAbsent(moduleName, list);
        return list;
    }

    /**
     * 数据源是否应该上报
     *
     * @param source
     * @return
     */
    private boolean shouldReport(SysUiSource source) {
        ForSystemType forSystem = source.getFdForSystem();
        return ForSystemType.all.equals(forSystem)
                || ForSystemType.cloud.equals(forSystem);
    }

    /**
     * 解析dataFields,转换为Json
     * @param source
     * @return
     */
    private String getDataFields(SysUiSource source) {
        String dataFields = null;
        if (StringUtil.isNotNull(source.getDataFields())) {
            dataFields = source.getDataFields();
            try {
                dataFields = JSONObject.fromObject(dataFields).toString();
            } catch (Exception e) {
                dataFields = JSONArray.fromObject(dataFields).toString();
            }
        }
        return dataFields;
    }

}
