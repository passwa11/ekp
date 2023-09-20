<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<%@ include file="/resource/jsp/edit_top.jsp" %>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css?s_cache=${LUI_Cache}"/>
<link type="text/css" rel="stylesheet"
      href="${LUI_ContextPath}/sys/modeling/base/resources/css/preview.css?s_cache=${LUI_Cache}"/>
<script type="text/javascript">
    var formInitData = {};
    var messageInfo = {};

    var initData = {
        contextPath: '${LUI_ContextPath}',
    };
    Com_IncludeFile("jquery.js");
    Com_IncludeFile("security.js");
    Com_IncludeFile("domain.js");
    Com_IncludeFile("form.js");
    Com_IncludeFile("xml.js");
    Com_IncludeFile("dialog.js");
    Com_IncludeFile("formula.js");

    Com_IncludeFile("plugin.js");
    Com_IncludeFile("validation.js");
</script>
<html:form action="/sys/modeling/base/pcAndMobileListView.do">
    <html:hidden property="fdId"/>
    <html:hidden property="fdName"/>
    <html:hidden property="modelMainId"/>
    <html:hidden property="docCreatorId"/>
    <html:hidden property="docCreateTime"/>
    <html:hidden property="readerIds"/>
    <html:hidden property="pcViewCfg"/>
    <html:hidden property="mobileViewCfg"/>
</html:form>

<c:import url="/sys/modeling/base/pcAndMobile/res/import/edit_top.jsp" charEncoding="UTF-8">
    <c:param name="fdId" value="${modelingPcAndMobileListViewForm.fdId }"/>
    <c:param name="modelMainId" value="${modelingPcAndMobileListViewForm.modelMainId }"/>
    <c:param name="fdName" value="${modelingPcAndMobileListViewForm.fdName }"/>
    <c:param name="authReaderIds" value="${modelingPcAndMobileListViewForm.readerIds }"/>
    <c:param name="authReaderNames" value="${modelingPcAndMobileListViewForm.readerNames }"/>
    <c:param name="topType" value="list"/>
</c:import>

<div class="modeling-pam-content">
    <iframe class="modeling-pam-content-frame lui_modeling_iframe_body" onload="pamOnloadFrame('pc')"
            id="modeling-pam-content-pc"
            frameborder="no" border="0" width="100%"></iframe>
    <iframe class="modeling-pam-content-frame lui_modeling_iframe_body" onload="pamOnloadFrame('mobile')"
            id="modeling-pam-content-mobile" style="display: none"
            frameborder="no" border="0" width="100%"></iframe>
</div>
<script type="text/javascript">
    var pam = {
        pcSrc: "${LUI_ContextPath}/sys/modeling/base/modelingAppListview.do?method=edit&isPcAndMobile=true&fdModelId=${modelingPcAndMobileListViewForm.modelMainId }&fdId=${modelingPcAndMobileListViewForm.pcViewId }",
        mobileSrc: "${LUI_ContextPath}/sys/modeling/base/mobile/modelingAppMobileListView.do?method=edit&isPcAndMobile=true&fdModelId=${modelingPcAndMobileListViewForm.modelMainId }&fdId=${modelingPcAndMobileListViewForm.mobileViewId }"
    };
    //初始化iframe
    var $pcFrame = $("#modeling-pam-content-pc");
    var $mobileFrame = $("#modeling-pam-content-mobile");
    $(document).ready(function () {
        height = $(parent.parent.document).find(".model-body-wrap-iframe").eq(0).outerHeight(true) - 80;
        $("body", parent.document).find('#trigger_iframe').height(height);
        $('.modeling-pam-content').height(height - 90);
        $('.modeling-pam-content-frame').height(height - 90);
        $pcFrame.attr("src", pam.pcSrc);
        $mobileFrame.attr("src", pam.mobileSrc);
    });

    function pamOnloadFrame(type) {
        var frame = type == "pc" ? $pcFrame : $mobileFrame;
        $(window).trigger("resize");
        $(frame.context).trigger("resize");
    }

    //提交数据组装
    var pcFormPkg = false;
    var mobileFormPkg = false;

    function packageData() {
        $pcFrame[0].contentWindow.packageData(function callBack(form, method) {
            if(form.onsubmit!=null && !form.onsubmit()){
                pcFormPkg = false;
                return;
            }
            for(var i=0; i<Com_Parameter.event["submit"].length; i++){
                if(!Com_Parameter.event["submit"][i]()){
                    pcFormPkg = false;
                    return;
                }
            }
            pcFormPkg = true;
        });
        $mobileFrame[0].contentWindow.packageData(function callBack(form, method) {
            mobileFormPkg = true
        });
    }

    function doSubmit() {
        packageData();
        if (!pcFormPkg || !mobileFormPkg) {
            var tips = "";
            if (!pcFormPkg)
                tips += " 【PC端】 ";
            if (!mobileFormPkg)
                tips += " 【移动端】 ";
            tips += "有字段校验不通过，请检查";
            seajs.use(["lui/dialog"], function (dialog) {
                dialog.alert(tips);
            });
            return;
        }
        var pcForm = $pcFrame.contents().find("form");
        var mobileForm = $mobileFrame.contents().find("form");
        var pcCfg = seriFormToJson(pcForm);
        var mobileCfg = seriFormToJson(mobileForm);
        //#123360 排序转换异常
        if (pcCfg) {
            for (var pcCfgName in pcCfg) {
                if (pcCfgName.indexOf("fdOrderType") > -1 && pcCfg[pcCfgName]) {
                    pcCfg[pcCfgName] =JSON.stringify(pcCfg[pcCfgName])
                }
            }
        }
        if (pcCfg.fdWhereType) {
        	pcCfg.fdWhereType = pcCfg.fdWhereType[0];
        }
        $("[name='pcViewCfg']").val(JSON.stringify(pcCfg));
        $("[name='mobileViewCfg']").val(JSON.stringify(mobileCfg));
        seajs.use(["lui/dialog"], function (dialog) {
            dialog.success('<bean:message key="return.optSuccess"/>');
        });
       Com_Submit($("form[name='modelingPcAndMobileListViewForm']")[0], "update");
    }

</script>
<%@ include file="/resource/jsp/edit_down.jsp" %>