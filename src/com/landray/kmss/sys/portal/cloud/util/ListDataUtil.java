package com.landray.kmss.sys.portal.cloud.util;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.language.utils.SysLangUtil;
import com.landray.kmss.sys.lbpm.engine.builder.ProcessInstance;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessCurrentInfoService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import java.util.Map;

/**
 * 转换工具类
 *
 * @author chao
 */
public class ListDataUtil {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(ListDataUtil.class);

    private static ILbpmProcessService lbpmProcessService;

    private static ILbpmProcessService getLbpmProcessService() {
        if (lbpmProcessService == null) {
            lbpmProcessService = (ILbpmProcessService) SpringBeanUtil
                    .getBean("lbpmProcessService");
        }
        return lbpmProcessService;
    }

    private static ILbpmProcessCurrentInfoService
    getProcessCurrentInfoService() {
        return (ILbpmProcessCurrentInfoService) SpringBeanUtil
                .getBean("lbpmProcessCurrentInfoService");
    }

    public static String getDocStatusString(String status) {
        if (SysDocConstant.DOC_STATUS_DISCARD.equals(status)) {
            return ResourceUtil.getString("status.discard");
        } else if (SysDocConstant.DOC_STATUS_DRAFT.equals(status)) {
            return ResourceUtil.getString("status.draft");
        } else if (SysDocConstant.DOC_STATUS_REFUSE.equals(status)) {
            return ResourceUtil.getString("status.refuse");
        } else if (SysDocConstant.DOC_STATUS_EXAMINE.equals(status)) {
            return ResourceUtil.getString("status.examine");
        } else if (SysDocConstant.DOC_STATUS_PUBLISH.equals(status)) {
            return ResourceUtil.getString("status.publish");
        } else if (ProcessInstance.ERROR.equals(status)) {
            return ResourceUtil.getString("sys-lbpmmonitor:status.error");
        } else if ("31".equals(status)) {
            return ResourceUtil.getString("km-review:status.feedback");
        } else {
            return status;
        }
    }

    /**
     * 状态对应的颜色
     *
     * @param status
     * @return
     */
    public static String getDocStatusColor(String status) {
        if (SysDocConstant.DOC_STATUS_DISCARD.equals(status)) {
            return "info";
        } else if (SysDocConstant.DOC_STATUS_DRAFT.equals(status)) {
            return "weaken";
        } else if (SysDocConstant.DOC_STATUS_REFUSE.equals(status)) {
            return "warning";
        } else if (SysDocConstant.DOC_STATUS_EXAMINE.equals(status)) {
            return "primary";
        } else if (ProcessInstance.ERROR.equals(status)) {
            return "error";
        } else if (SysDocConstant.DOC_STATUS_PUBLISH.equals(status)) {
            return "success";
        } else {
            return null;
        }
    }

    public static String getLbpmName(String idValue,
                                     String propertyName, boolean mobile, String extend) {
        String content = null;
        if (StringUtil.isNotNull(propertyName)
                && StringUtil.isNotNull(idValue)) {
            try {
                IBaseModel model = getLbpmProcessService()
                        .findByPrimaryKey(idValue, null, true);
                if (model != null && model instanceof LbpmProcess) {
                    LbpmProcess process = (LbpmProcess) model;
                    ILbpmProcessCurrentInfoService processCurrentInfoService = getProcessCurrentInfoService();
                    if ("nodeName".equals(propertyName)) {
                        // 当前节点
                        content = processCurrentInfoService
                                .getCurNodesName(process);
                    } else if ("handlerName".equals(propertyName)) {
                        // 当前处理人
                        content = processCurrentInfoService
                                .getCurHanderNames(process);
                        if (StringUtil.isNull(content)) {
                            content = ResourceUtil.getString(
                                    "lbpmProcess.HandlersName.Empty",
                                    "sys-lbpmservice");
                        }
                    } else if ("handerNameDetail".equals(propertyName)) {
                        // 当前处理人（详） 移动端和特权人、起草人操作不显示个人空间链接
                        content = processCurrentInfoService
                                .getCurHanderNamesDetail(process, mobile,
                                        !"hidePersonal".equals(extend)
                                                && !mobile);
                    } else if ("historyHanderName".equals(propertyName)) {
                        // 历史处理人
                        content = processCurrentInfoService
                                .getHistoryHanderNames(process, mobile);
                    } else if ("summary".equals(propertyName)) {
                        content = processCurrentInfoService
                                .getCurHanderNamesByNode(process);
                    } else if ("arrivalTime".equals(propertyName)) {
                        content = DateUtil.convertDateToString(
                                processCurrentInfoService
                                        .getArrivalTime(process),
                                DateUtil.PATTERN_DATETIME);
                    }
                }
            } catch (Exception e) {
                logger.warn("流程列表视图出错propertyName=" + propertyName + ", id="
                        + idValue, e);
            }
        }
        return content;
    }

    /**
     * 构建返回数据的创建者对象
     *
     * @param element
     * @return
     */
    public static JSONObject buildCreator(SysOrgElement element) {
        JSONObject creator = new JSONObject();
        creator.put("fdId", element.getFdId());
        creator.put("fdName", element.getFdName());
        if (element instanceof SysOrgPerson) {
            SysOrgPerson person = (SysOrgPerson) element;
            creator.put("fdLoginName", person.getFdLoginName());
            creator.put("avatarUrl", formatImageUrl(
                    "/sys/person/image.jsp?personId=" + person.getFdId()));
        }
        return creator;
    }

    /**
     * 格式化图片地址
     *
     * @param imgUrl
     * @return
     */
    public static String formatImageUrl(String imgUrl) {
        return formatUrl(imgUrl);
    }

    /**
     * 格式化地址
     *
     * @param imgUrl
     * @return
     */
    public static String formatUrl(String url) {
        if (url.startsWith("http://") || url.startsWith("https://"))  {
            return url;
        }
        // 访问前缀这里采用admin.do中的配置
        String prefix = ResourceUtil
                .getKmssConfigString("kmss.urlPrefix");
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        return prefix + url;
    }

    /**
     * 对应{@code ExtendAction.list}方法的公共处理
     * <p>
     * note: 只处理了{@code ExtendAction.changeFindPageHQLInfo}
     * </p>
     *
     * @param request
     * @param modelName
     * @return
     */
    public static HQLInfo getListHqlInfo(RequestContext request,
                                         String modelName) {
        String s_pageno = request.getParameter("pageno");
        String s_rowsize = request.getParameter("rowsize");
        String orderby = request.getParameter("orderby");
        String ordertype = request.getParameter("ordertype");
        boolean isReserve = false;
        if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
            isReserve = true;
        }
        int pageno = 0;
        int rowsize = SysConfigParameters.getRowSize();
        if (s_pageno != null && s_pageno.length() > 0
                && Integer.parseInt(s_pageno) > 0) {
            pageno = Integer.parseInt(s_pageno);
        }
        if (s_rowsize != null && s_rowsize.length() > 0
                && Integer.parseInt(s_rowsize) > 0) {
            rowsize = Integer.parseInt(s_rowsize);
        }

        // 按多语言字段排序
        if (StringUtil.isNotNull(orderby) && StringUtil.isNotNull(modelName)) {
            String langFieldName = SysLangUtil
                    .getLangFieldName(modelName, orderby);
            if (StringUtil.isNotNull(langFieldName)) {
                orderby = langFieldName;
            }
        }
        if (isReserve) {
            orderby += " desc";
        }
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setOrderBy(orderby);
        hqlInfo.setPageNo(pageno);
        hqlInfo.setRowSize(rowsize);
        changeFindPageHQLInfo(request, hqlInfo, modelName);
        return hqlInfo;
    }

    private static void changeFindPageHQLInfo(RequestContext request,
                                              HQLInfo hqlInfo, String modelName) {
        hqlInfo.setOrderBy(
                getFindPageOrderBy(request, hqlInfo.getOrderBy(), modelName));
    }

    private static String getFindPageOrderBy(RequestContext request,
                                             String curOrderBy, String className) {
        if (curOrderBy == null) {
            if (StringUtil.isNull(className)) {
                return null;
            }
            SysDictModel model = SysDataDict.getInstance().getModel(className);
            if (model == null) {
                return null;
            }
            String modelName = ModelUtil.getModelTableName(className);
            logger.debug("modelNme=" + modelName);
            Map propertyMap = model.getPropertyMap();
            /*
             * 如果fdOrder不为空，则按照fdOrder和fdName（如果存在）排序， 否则直接按照fdId逆序排序（如果存在）
             */
            curOrderBy = "";
            if (propertyMap.get("fdOrder") != null) {
                curOrderBy += modelName + ".fdOrder";
                if (propertyMap.get("fdName") != null) {
                    curOrderBy += "," + modelName + ".fdName";
                }
            } else if (propertyMap.get("fdId") != null) {
                curOrderBy += modelName + ".fdId desc";
            }
            logger.debug("curOrderBy=" + curOrderBy);
        }
        return curOrderBy;
    }

    /**
     *  构造Iinfo对象
     * @param text
     * @return
     */
    public static JSONObject buildIinfo(String text) {
        JSONObject info = new JSONObject();
        info.put("title", null);
        info.put("text", text);
        info.put("href", null);
        info.put("color", null);
        info.put("icon", null);
        return info;
    }

    /**
     *  构造Iinfo对象
     * @param title
     * @param text
     * @param href
     * @param color
     * @param icon
     * @return
     */
    public static JSONObject buildIinfo(String title, String text, String href, String color, String icon) {
        JSONObject info = new JSONObject();
        info.put("title", title);
        info.put("text", text);
        info.put("href", href);
        info.put("color", color);
        info.put("icon", icon);
        return info;
    }



    /**
     *  构造Iinfo对象
     * @param number
     * @return
     */
    public static JSONObject buildIinfo(Number number) {
        JSONObject info = new JSONObject();
        info.put("title", null);
        info.put("text", number);
        info.put("href", null);
        info.put("color", null);
        info.put("icon", null);
        return info;
    }
}
