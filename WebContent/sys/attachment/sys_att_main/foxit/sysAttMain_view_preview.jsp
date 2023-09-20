<%@ page import="com.landray.kmss.sys.attachment.model.SysAttMain" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<%@ page import="com.landray.kmss.sys.attachment.util.JgWebOffice" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.foxit.ISysAttachmentFoxitProvider" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
    //版式文件不显示切换阅读
    String isPdfOrOfd = "false";
    //是否是附件
    String isAtt = "true";
    //打印
    boolean canPrint = false;
    //下载
    String showDownload = "true";
    //切换阅读模式
    String showChangeView = "false";
    String url = null;
  //  Object previewUrl = request.getAttribute("previewUrl");
    Object attMain = null;

    SysAttMain sysAttMain = null;
    request.setAttribute("isMobile", false);
    Boolean mobile = (Boolean) request.getAttribute("isMobile");

    if(mobile) {
        request.setAttribute("isMobile", mobile);
     }
    List attMainList = (List)pageContext.getAttribute("sysAttMains");
    if (CollectionUtils.isNotEmpty(attMainList)) {
        attMain = attMainList.get(0);
    } else {
        attMain = request.getAttribute("attMain");
    }
    if (attMain != null) {
        sysAttMain = (SysAttMain) attMain;
        if (JgWebOffice.isPDF(sysAttMain.getFdFileName()) || JgWebOffice.isOFD(sysAttMain.getFdFileName())) {
            isPdfOrOfd = "true";
        }
    }

    if(attMain != null) {
        ISysAttachmentFoxitProvider sysAttachmentFoxitProvider = (ISysAttachmentFoxitProvider)
                SpringBeanUtil.getBean("foxitProvider");
        String result = sysAttachmentFoxitProvider.isConverted(sysAttMain);
        if(!"Success".equalsIgnoreCase(result)) {
            request.setAttribute("preview", "");
            request.setAttribute("converted", false);
            request.setAttribute("resultInfo", result);
            pageContext.setAttribute("isPdfOrOfd", isPdfOrOfd);
            pageContext.setAttribute("onlineToolType",SysAttConfigUtil.getOnlineToolType());
            sysAttMain = (SysAttMain) attMain;
            pageContext.setAttribute("fdId", sysAttMain.getFdId());
        } else {
            request.setAttribute("converted", true);
            sysAttMain = (SysAttMain) attMain;
            if (JgWebOffice.isPDF(sysAttMain.getFdFileName()) || JgWebOffice.isOFD(sysAttMain.getFdFileName())) {
                isPdfOrOfd = "true";
            }

            pageContext.setAttribute("isPdfOrOfd", isPdfOrOfd);
            url = sysAttachmentFoxitProvider.extendsPreviewUrl(sysAttMain);
            pageContext.setAttribute("fdId", sysAttMain.getFdId());

            if ("editonline".equalsIgnoreCase(sysAttMain.getFdKey()) || "mainOnline".equalsIgnoreCase(sysAttMain.getFdKey())) {
                isAtt = "false";
            }

            pageContext.setAttribute("previewUrl", url);
            pageContext.setAttribute("onlineToolType",SysAttConfigUtil.getOnlineToolType());
            pageContext.setAttribute("isAtt", isAtt);
            pageContext.setAttribute("showDownload",showDownload);
            pageContext.setAttribute("showChangeView",showChangeView);
            showDownload = request.getParameter("showDownload");
            if (StringUtils.isNotEmpty(showDownload)) {
                pageContext.setAttribute("showDownload",showDownload);
            }
            showChangeView = request.getParameter("showChangeView");
            if (StringUtils.isNotEmpty(showChangeView)) {
                pageContext.setAttribute("showChangeView",showChangeView);
            }
            isAtt = request.getParameter("isAtt");
            if (StringUtils.isNotEmpty(isAtt)) {
                pageContext.setAttribute("isAtt", isAtt);
            }
            //内嵌加载项
            pageContext.setAttribute("wpsoAassistEmbed",SysAttWpsoaassistUtil.isWPSOAassistEmbed());
        }


    } else {
        request.setAttribute("preview", "");
        request.setAttribute("converted", false);
        request.setAttribute("resultInfo", "附件不存在");
    }

%>
<style>
    .foxitBtn{
        float: right;
        margin-right: 15px;
        margin-top: 5px;
        margin-bottom: 5px;
    }
    .foxitBtnDiv{
        display: flex;
        align-items: center;
    }
    .foxitDownloadIcon{
        background: url(${KMSS_Parameter_ContextPath}/sys/attachment/sys_att_main/dianju/img/download.png) no-repeat center;
        background-size: contain;
        display: inline-block;
        width: 14px;
        height: 14px;
    }
    .foxitChangeViewIcon{
        background: url(${KMSS_Parameter_ContextPath}/sys/attachment/sys_att_main/dianju/img/changeView.png) no-repeat center;
        background-size: contain;
        display: inline-block;
        width: 14px;
        height: 14px;
        margin-right: 2px;
    }
    .foxitBtnFont {
        color: #0a8cd2;
    }
</style>
<%--<script>Com_IncludeFile("jquery.js");</script>--%>
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/js/common.js"></script>
<script>
    function setIframeHeight(iframe) {
        var h = document.documentElement.scrollHeight ||
            document.documentElement.offsetHeight || window.outerHeight;
        if (!h || h < 300) {
            h = 360;
        } else {
            h -= 40;
        }
        iframe.height = h;

    }

</script>
<%--<c:if test="${isAtt=='true'}">--%>
<%--    <%@ include file="/sys/ui/jsp/jshead.jsp"%>--%>
<%--</c:if>--%>

<c:if test="${isPdfOrOfd=='false' and isMobile == 'false'}">
    <c:if test="${isAtt=='true' or showChangeView=='true' or converted=='false'}">
        <%--		切换阅读模式 0 金格, 3 wps加载项--%>
        <div class="foxitBtn">
            <div class="foxitBtnDiv">
                <span class="foxitChangeViewIcon"></span>
                <c:if test="${onlineToolType == '0'}">
                    <a class="foxitBtnFont" href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${fdId }&viewType=jg">${lfn:message('sys-attachment:viewer_hint_5')}</a>
                </c:if>
                <c:if test="${onlineToolType == '3'}">
                    <%@ include file="/sys/ui/jsp/jshead.jsp"%>
                    <script type="text/javascript" src="${LUI_ContextPath }/sys/attachment/sys_att_main/wps/oaassist/js/wps_utils.js?s_cache=${LUI_Cache }"></script>
                    <c:choose>
                        <c:when test="${wpsoAassistEmbed == 'true'}">
                            <a class="foxitBtnFont" href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=viewByWpsoAassistEmbed&fdId=${fdId}">${lfn:message('sys-attachment:viewer_hint_5')}</a>
                        </c:when>
                        <c:otherwise>
                            <a class="foxitBtnFont" href="javascript:void(0);" onclick="openByWpsOaassist('${fdId}');">${lfn:message('sys-attachment:viewer_hint_5')}</a>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </c:if>
</c:if>
<c:if test="${isAtt=='true' and showDownload=='true' and isMobile == 'false'}">
    <div class="foxitBtn">
        <kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${fdId}" requestMethod="GET">
            <div class="foxitBtnDiv">
                <span class="foxitDownloadIcon"></span>
                <a href="javascript:void(0);" class="foxitBtnFont" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${fdId}&downloadType=manual');">
                    <bean:message bundle="sys-attachment" key="sysAttMain.button.download" />
                </a>
            </div>
        </kmss:auth>
            <%--			<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${fdId}" requestMethod="GET">--%>
            <%--				<a href="javascript:void(0);" class="" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${fdId }','_blank')">--%>
            <%--					<kmss:message key="lbpmNode.subform.print_form" bundle="sys-lbpmservice" />--%>
            <%--				</a>--%>
            <%--			</kmss:auth>--%>
    </div>
</c:if>
<c:if test="${converted=='false'}">
    <iframe id="foxit_iframe"  width="100%"  height="300" frameborder="0" scrolling="no"
            src="${LUI_ContextPath }/sys/attachment/sys_att_main/jg/sysAttMain_prompt.jsp?promptInfo=${resultInfo}"></iframe>

</c:if>
<c:if test="${converted=='true'}">
<iframe id="foxit_iframe" src="${previewUrl}" width="100%" frameborder="0" scrolling="no" onload="setIframeHeight(this)"></iframe>
</c:if>
