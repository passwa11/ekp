<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
	<script type="text/javascript">
        seajs.use(['theme!list', 'theme!portal']);
    </script>
    <script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="${ LUI_ContextPath }/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/ui/extend/theme/default/style/icon.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>

    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css"/>
    <style>
    .images{
    	width:75px;
    	height:65px;
		background:url(images/noxform.png) no-repeat;
		background:url(images/noxform@2x.png) no-repeat \9;
		background-position:50% 50% ;
		margin-left: 15px;
    }
    .center{
    	position: absolute;
		left: 50%;
		top: 50%;
		margin-left:-100px;
		margin-top:-50px;
    }
    </style>
<div class="lui_modeling">
    <div class="lui_modeling_aside" id="modelingAside" style="display:none;">
        <div data-lui-type="lui/base!DataView" style="display:none;">
            <ui:source type="Static">
                [
                    {
                        "text" : "PC+移动端",
                        "iframeId":"trigger_iframe",
                        "selected":"true",
                        "src" :  "sys/modeling/base/pcAndMobile/list/index_body.jsp?fdModelId=${param.fdModelId}&method=${param.method}"
                    },
                    {
                    "text" : "PC端",
                    "iframeId":"trigger_iframe",
                    "src" :  "sys/modeling/base/listview/config/index_body.jsp?fdModelId=${param.fdModelId}&method=${param.method}"
                    },{
                    "text" : "移动端",
                    "iframeId":"trigger_iframe",
                    "src" :  "sys/modeling/base/mobile/viewDesign/listView/config/index_body.jsp?fdModelId=${param.fdModelId}&method=${param.method}&actionUrl=/sys/modeling/base/mobile/modelingAppMobileListView.do"
                }]
            </ui:source>
            <ui:render type="Javascript">
                <c:import url="/sys/modeling/base/resources/js/menu_side.js" charEncoding="UTF-8"></c:import>
            </ui:render>
        </div>
    </div>

    <div class="lui_modeling_main aside_main" >
    	<div class="center">
	    	<div class="images"></div>
	    	<div class="tips">请先完成表单设计！</div>
    	</div>
    </div>

</div>
<script>
	$(".lui_modeling").css({"background-color":"#E8ECEF","padding":"10px"});
	$("#modelingAside").css("display","block");
	var param = getUrlParam('page');
	if(param == "operation"){
		$("#modelingAside").css("display","none");
	}
    if(param == "externalQuery" || param == "externalShare"){
        $("#modelingAside").css("display","none");
        $(".lui_modeling").css("padding","0");
        $(".lui_modeling").css("overflow","hidden");
    }
	function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
</script>