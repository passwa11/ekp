<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="org.apache.commons.beanutils.PropertyUtils" %>
<%@page import="com.landray.kmss.util.StringUtil" %>
<%@page import="com.landray.kmss.sys.xform.service.DictLoadService" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui" %>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@ page import="com.landray.kmss.sys.xform.base.model.SysFormDefaultSwitch" %>
<%@page import="javax.servlet.jsp.PageContext" %>
<%
    boolean isEditMode = "edit".equals(request.getParameter("method")) || "add".equals(request.getParameter("method"))
            || (request.getParameter("method") == null ? "" : request.getParameter("method")).startsWith("add");
    if (isEditMode) {
        request.setAttribute("SysForm.showStatus", "readOnly");
        request.setAttribute("SysForm.base.showStatus", "readOnly");
        pageContext.setAttribute("SysForm.importType", "edit");
    } else {
        request.setAttribute("SysForm.showStatus", "view");
        request.setAttribute("SysForm.base.showStatus", "view");
        pageContext.setAttribute("SysForm.importType", "view");
    }
%>
<%
    String formBeanName = request.getParameter("formName");
    String mainFormName = null;
    String xformFormName = null;
    int indexOf = formBeanName.indexOf('.');
    if (indexOf > -1) {
        mainFormName = formBeanName.substring(0, indexOf);
        xformFormName = formBeanName.substring(indexOf + 1);
        pageContext.setAttribute("_formName", xformFormName);
    } else {
        mainFormName = formBeanName;
        pageContext.setAttribute("_formName", null);
    }

    DictLoadService dictService = (DictLoadService) SpringBeanUtil.getBean("sysFormDictLoadService");
    Object mainForm = request.getAttribute(mainFormName);
    Object xform = xformFormName == null ? mainForm : PropertyUtils.getProperty(mainForm, xformFormName);
    String path = "";
    String fdFormId = "";
    if (xform instanceof IExtendForm) {
        IExtendForm extendForm = (IExtendForm) xform;
        path = dictService.findExtendFileFullPath(extendForm);
        fdFormId = dictService.getFormId(extendForm);
    } else {
        path = (String) PropertyUtils.getProperty(xform, "extendDataFormInfo.extendFilePath");
        path = dictService.findExtendFileFullPath(path);
    }
    if (!isEditMode) {
        if (mainForm instanceof com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm
                && "view".equals(pageContext.getAttribute("SysForm.importType"))) {
            com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm wfForm = (com.landray.kmss.sys.workflow.interfaces.ISysWfMainForm) mainForm;
            if (wfForm.getDocStatus().charAt(0) < '3') {
                request.setAttribute("SysForm.base.showStatus", "readOnly");
            }
        }
    }
    if (xform instanceof IExtendForm) {
        IExtendForm extendForm = (IExtendForm) xform;
        String mainModelName = extendForm.getModelClass().getName();
        request.setAttribute("_mainModelName", mainModelName);
    }
    request.setAttribute("_xformMainForm", mainForm);
    request.setAttribute("_xformForm", xform);
    request.setAttribute("fdFormId", fdFormId);
    pageContext.setAttribute("_formFilePath", path);

    String isNewDesktopLayout = new SysFormDefaultSwitch().getIsNewDesktopLayout();
    request.setAttribute("isNewDesktopLayout",isNewDesktopLayout);
%>
<script>
    _xformMainModelClass = "${_xformMainForm.modelClass.name}";
    _xformMainModelId = "${_xformMainForm.fdId}";
    window._xform_isNewDesktopLayout = "false";
    if(dojoConfig && dojoConfig.tiny){
        window._xform_isNewDesktopLayout = "${isNewDesktopLayout}";
    }
</script>
<c:if test="${_formFilePath!=null}">
    <c:if test="${empty JsParam._data }"><mui:cache-file name="mui-xform.js" cacheType="md5"/></c:if>
    <mui:min-file name="sys-xform.css"/>
    <script>
        require(['sys/xform/mobile/controls/xform'])
        require(["dojo/topic", "dojo/query"], function (topic, query) {
            topic.subscribe('parser/done', function () {
                __fixLabelStyle();
            });

            function __fixLabelStyle(argus) {
                var context = document;
                if (argus && argus.row) {
                    context = argus.context;
                }
                var xformLables = query('.xform_label', context);
                for (var i = 0; i < xformLables.length; i++) {
                    var html = xformLables[i].innerHTML;
                    var reg = new RegExp("&nbsp;", "g");
                    html = html.replace(reg, " ");
                    html = html.replace(/\s*$/g, "");
                    html = html.replace(/^\s*/g, "");
                    xformLables[i].innerHTML = html;
                }
            }

            //复制行事件
            $(document).on('table-copy-new', 'table[showStatisticRow]', function (e, argus) {
                __fixLabelStyle(argus);
            });

            //新增行事件
            $(document).on('table-add-new', 'table[showStatisticRow]', function (e, argus) {
                __fixLabelStyle(argus);
            });
        });
        // 自定义表单的全局变量
        var Xform_ObjectInfo = {};
        Xform_ObjectInfo.mainModelName = "${_mainModelName}";
        Xform_ObjectInfo.fdKey = "${param.fdKey}";
        Xform_ObjectInfo.fdFormId = "${fdFormId}";
        Xform_ObjectInfo.formFilePath = "${_formFilePath}";
        Xform_ObjectInfo.mainDocStatus = "<%=TagUtils.getDocStatus(request)%>";
        Xform_ObjectInfo.mainFormId = "${_xformMainForm.fdId}";
        Xform_ObjectInfo.optType = "${pageScope['SysForm.importType']}";
        // 设置日期格式
        Xform_ObjectInfo.calendarLang = {
            format: {
                date: "${lfn:message('date.format.date')}",
                time: "${lfn:message('date.format.time')}",
                datetime: "${lfn:message('date.format.datetime')}"
            }
        };
        //提供一个方法给业务模块，该方法的能力是获取配置的基本信息表格样式
        //只所以这么做，是考虑有iframe的情况
        function getBaseInfoTableStyle() {
            var baseInfoTableStyle = null;
            if (window.baseInfoTbClassname
                && window.baseInfoTbStylePath
                && window.baseInfoTbStyleFileName) {
                baseInfoTableStyle = {};
                baseInfoTableStyle.isDefault = false;
                baseInfoTableStyle.className = window.baseInfoTbClassname;
                baseInfoTableStyle.path = window.baseInfoTbStylePath;
                baseInfoTableStyle.fileName = window.baseInfoTbStyleFileName;
            }
            //屏蔽此默认功能，后续有需要可以开启
            /* else{//不存在时默认取第一个表格样式
                var fdKey = '



            ${param.fdKey}';
				//获取表单域div
				var xformArea = $("#__xform_"+fdKey)[0] || $(".muiXformArea")[0];
				//获取带有baseInfoTableStyle属性的表格
				var $table = $("table",xformArea).eq(0);
				if($table.length > 0){
					baseInfoTableStyle.isDefault = true;
					baseInfoTableStyle.className = $table.attr("class");
				}
			} */
            return baseInfoTableStyle;
        }
        if(window._xform_isNewDesktopLayout == "true"){
            //解决压缩后底部留白的问题(桌面端的情况下才生效）
            window.changeHeight = function(target){
                if(!target){
                    return;
                }
                var tableHeight = $(target).find("[layout2col='desktop']").height();
                if(tableHeight){
                    $(target).css({
                        "height":tableHeight+"px"
                    })
                }
            }
            //初始化绑定dom变化的事件监听(桌面端的情况下才生效）
            $(document).ready(function() {
                $("[isXform='true'][isDesktopLayoutForm='true']").bind("DOMNodeInserted",function(event) {
                    event.stopPropagation();
                    event.preventDefault();
                    var _self = this;
                    window.setTimeout(function(){
                        window.changeHeight(_self);
                    },0)
                })

                $("[isXform='true'][isDesktopLayoutForm='true']").bind("DOMNodeRemoved",function(event) {
                    event.stopPropagation();
                    event.preventDefault();
                    var _self = this;
                    window.setTimeout(function(){
                         window.changeHeight(_self);
                    },0)
                })

                window.setTimeout(function(){
                    window.changeHeight($("[isXform='true'][isDesktopLayoutForm='true']")[0]);
                },0)
            });
        }
        require(["dojo/query","dojo/ready","dojo/dom-attr"],function(query,ready,domAttr){
            //处理桌面端优化的不同情况兼容
            ready(function(){
                //保证是在表单桌面端
                var formContainer = query("[isXform='true'][isDesktopLayoutForm='true']")[0];
                if(formContainer && window._xform_isNewDesktopLayout == "true"){
                    Com_IncludeFile('desktop.css','${LUI_ContextPath}/sys/xform/mobile/resource/css/','css',true);
                }else{
                    //如果不满足新桌面端+开关的情况，都恢复一切，不支持放大缩小，也不影响样式
                    var metaInfo = query("meta[name='viewport']");
                    if(metaInfo.length > 0){
                        var content = domAttr.get(metaInfo[0],"content");
                        if(content && content.indexOf("initial-scale") > -1 && content.indexOf("user-scalable") == -1){
                            if(content.lastIndexOf(",") != content.length - 1){
                                content += ",";
                            }
                            domAttr.set(metaInfo[0],"content",content+"maximum-scale=1,minimum-scale=1,user-scalable=no");
                        }
                    }
                }
            });
        })
    </script>
    <xform:config formName="${_formName}" orient="none">
        <div id='__xform_${param.fdKey}' class="muiXformArea">
            <input type="hidden" name="extendDataFormInfo.extendFilePath" value="${_formFilePath}"/>
                <%-- 兼容add时候的公式解析,无mainModel需要手动构建mainModel --%>
            <input type="hidden" name="extendDataFormInfo.mainModelName" value="${_mainModelName}"/>
            <c:import url="/${_formFilePath}.4m.jsp" charEncoding="UTF-8">
                <c:param name="method" value="${param.method}"/>
                <c:param name="fdKey" value="${param.fdKey}"/>
                <c:param name="optType" value="${pageScope['SysForm.importType']}"/>
                <c:param name="mainModelName" value="${_mainModelName}"/>
            </c:import>
        </div>
    </xform:config>
</c:if>
<%
    pageContext.removeAttribute("_formName",PageContext.REQUEST_SCOPE);
    pageContext.removeAttribute("_formFilePath",PageContext.REQUEST_SCOPE);
    request.removeAttribute("SysForm.base.showStatus");
    request.removeAttribute("SysForm.isPrint");
    request.removeAttribute("SysForm.showStatus");
    request.removeAttribute("_xformMainForm");
    request.removeAttribute("_xformForm");
    request.removeAttribute("fdFormId");
    if (xform instanceof IExtendForm) {
        request.removeAttribute("_mainModelName");
    }
%>