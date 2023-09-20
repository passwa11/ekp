<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<meta name="format-detection" content="telephone=no"/>
	<link rel="stylesheet" href="css/keydata_new.css" /> 
	<link rel="stylesheet" type="text/css" href="${ LUI_ContextPath}/sys/ui/extend/theme/default/style/zone.css" />
<title>
	<c:out value="主数据"></c:out>
</title>
<script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${ LUI_Cache }"></script>
<script>
seajs.use(['theme!module']);
function clickIframe(target) {
	//var $target = $(e.target);
	//alert(1);
	$target = $(target);
	var info = $($target).attr("data-info");
	//alert(info);
	if(!info) {
		return;
	}
	var target = Com_GetUrlParameter(info, "target");
	if("_blank" == target) {
		return;
	}
	var href = Com_GetUrlParameter(info, "href");
	if(href.indexOf("http") < 0){
		href = Com_Parameter.ContextPath+href;
		href=href+"&mainDataId="+Com_GetUrlParameter(window.location, "fdId");
	}
	
	//alert(href);
	//	serverPath = Com_GetUrlParameter(info,"serverPath"),
		key = Com_GetUrlParameter(info,"key"),
		text = Com_GetUrlParameter(info, "text");	
	$("[data-info]").removeClass("com_btn_link ");
	var indexIn = $target.attr("data-nav-in");
	if(indexIn) {
		var $target0 = $("[data-nav-out][data-info]:eq(0)");
		exchangeInfo($target0,$target);
		$target0.addClass("com_btn_link ");
	} else {
		 $target.addClass("com_btn_link ");
	}

	$("#iframe_body").attr("src", href);
}

function showDetail(prop){
	var href = window.location.href;
	var modelName = Com_GetUrlParameter(href, "modelName");
	var fdId = Com_GetUrlParameter(href, "fdId");
	var url = "/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=showContent&fdId="+fdId+"&modelName="+modelName+"&prop="+prop;
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		dialog.iframe(url,"内容",function(value){
			
		},{
			"width" : 800,
			"height" : 500
		});
	}
	);
}

function showDetail2(obj){
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
		var obj_div = $(obj).next();
		if (obj_div != null) {
				dialog.build( {
					config : {
						width:800,
						height:600,
						lock : true,
						cache : false,
						close: true,
						content : {
							//type : "element",
							//elem : obj_div,
							type : "Html",
							html : obj_div.text(),
							scroll : true
							
						},
						title : "查看更多1"
					},

					callback : function(value, dialog) {
						
					},
					actor : {
						type : "default"
					},
					trigger : {
						type : "default"
					}
				}).show();
				
		}
	});
}
</script>
		<div class="lui_keydata_frame">
			<!-- 页眉导航 Starts -->
			<div class="lui_keydata_sidebar">
				<div class="lui_keydata_infoBar">
					<div class="lui_keydata_info_avatar">
						<img src="<c:url value="${icon}" />" alt="" />
					</div>
				</div>
				<div class="lui_keydata_info_list">
					<div class="lui_keydata_content">
						<h2 class="lui_keydata_info_name">${title }<span class="lui_keydata_category" style="background:${color }">${mainDataType}</span></h2>
						<ul class="lui_keydata_info_ul">
							<c:forEach items="${showFieldsList }" var="field" varStatus="varStatus">
							<li>
								<div class="lui_keydata_info_innner"><em class="keydata_info_label" title="${field[0] }">${field[0] }</em><span title="${field[1] }" class="keydata_info_txt">${field[1] }
								<c:if test="${field[2]=='RTF' }">
									<i class="lui_keydata_moreBtn" onClick="showDetail('${field[4]}');">更多</i>
								</c:if>
								
								</span>
								</div>
							</li>
							
							</c:forEach>
							
						</ul>
					</div>
					<div class="lui_keydata_opBar">
						<a class="lui_keydata_link" href="${detailUrl }" target="_blank"><i class="icon_more"></i><span>查看详情</span></a>
						<a class="lui_keydata_code" href="javascript:;"><i class="icon_code"></i><span>扫码查看</span>
							<span class="lui_keydata_qcode">
							<%-- 
								<img src="images/code.png" alt="" />
								--%>
							</span>
						</a>
					</div>
				</div>
				
				</div>
			<!-- 页眉导航 Ends -->
			<!-- 隐藏滚动条 -->
			<div class="lui_keydata_scroll_mask"></div>
			<!-- 内容列表 Starts -->
			<div class="lui_keydata_body_frame">
				<!-- 这里用系统的列表 所以用图片代替 -->
				<div class="lui_zone_nav_box_new com_bgcolor_d">
							<div class="lui_zone_nav_bar_new " id="showNavBarUl">
								
							    
			<div class="nav_link">
				<div class="left_row" onclick="left_drag_nav();" style="display: block;">&lt;</div>
					<div class="nav_link_frame">
						<div class="nav_link_body" style="width: 2100px;">
							<div data-lui-type="lui/base!DataView" style="" id="lui-id-16" class="lui-component" data-lui-cid="lui-id-16" data-lui-parse-init="15">	
							<c:forEach items="${relates }" var="relate" varStatus="varStatus">
						 		<li style="display: inline-block;"><a class="com_bordercolor_d" title="${relate.docSubject }" href="javascript:;" onclick="clickIframe(this);" data-info="&amp;id=${relate.fdId }&amp;text=个人首页&amp;href=${relate.url}" data-nav-out="0">${relate.docSubject }</a></li>
								<li class="line"></li>
							</c:forEach>
							<%-- 
							<li style="display: inline-block;"><a class="com_bordercolor_d com_btn_link" title="个人首页" href="javascript:;" onclick="clickIframe(this);" data-info="&amp;id=1625192cad53afcaee216af41f4867aa&amp;text=个人首页&amp;href=/sys/zone/sys_zone_personInfo/template_view/personalPage/personalPage.jsp%3FfdLoginName=huangwq%26fdUserId=12efa0bdd91ec2cc7854d1b46da98f44&amp;serverPath=http://java.landray.com.cn&amp;target=_self&amp;key=" data-nav-out="0">个人首页</a></li>
							<li class="line"></li>
							<li style="display: inline-block;"><a class="com_bordercolor_d" title="个人资料" href="javascript:;" onclick="clickIframe(this);" data-info="&amp;id=15acae731b6f54173e8bf044ce785914&amp;text=个人资料&amp;href=/sys/zone/sys_zone_personInfo/sysZonePersonInfo_personData_edit.jsp?user=&amp;serverPath=http://java.landray.com.cn&amp;target=_self&amp;key=java" data-nav-out="1">个人资料</a></li>
							<li class="line"></li>
							<li style="display: inline-block;"><a class="com_bordercolor_d" title="问答" href="javascript:;" onclick="clickIframe(this);" data-info="&amp;id=14b480e8ac3a7438c0ef8324a8ba3ae1&amp;text=问答&amp;href=/kms/ask/kms_ask_personal/kmsAsk_zone.jsp&amp;serverPath=&amp;target=_self&amp;key=undefined" data-nav-out="2">问答</a></li>
							--%>
							</div>
						</div>
					</div>
				<div class="right_row" onclick="right_drag_nav();" style="display: block;">&gt;</div>
			</div>
		
							   
							</div>
						</div>
						<div class="lui_zone_mbody">
							<!--左侧区域 Starts-->
							<div class="lui_zone_mbody_l">
								<div class="lui_zone_mbody_l_inner">
<script>
/* 导航信息的链接 */
function __navLinkUrl(fdUrl, serverPath, key){
	if(fdUrl.indexOf("http://") == 0 || fdUrl.indexOf("https://") == 0){
		return fdUrl;
	}
	var currentKey  = "java";
	if(currentKey == key || !serverPath ){
		serverPath = "";
	}
	fdUrl = serverPath + fdUrl;
	var param = "LUIID=iframe_body&userId=12efa0bdd91ec2cc7854d1b46da98f44&userSex=M&isSelf=true&zone_TA=my";
	var index = fdUrl.indexOf("?");
	if(index >= 0){
		if(index == fdUrl.length - 1){
			fdUrl += param; 
		}else{
			fdUrl += "&" + param;
		}
	}else if(index < 0){
		fdUrl += "?" + param; 
	}
	
	return fdUrl;
}

	$(function() {
		//var row_show = "";
		var navLinkSize = "${fn:length(relates) }";
		var px = (parseInt(150)*(parseInt(navLinkSize) + 1))+'px';
		//$(".nav_link_body").width(px);
		if(navLinkSize>7){
			$(".left_row").show();
			$(".right_row").show();
			
		}
		var li = $(".nav_link_body a:first");
		//alert(li);
		clickIframe(li);
	});

	


function  left_drag_nav(){
	var frame=$(".nav_link_frame");
	var xl = frame.scrollLeft();
	var scro = xl - 300;
	frame.animate({scrollLeft: scro}, 200);
} ;

function  right_drag_nav(){
	var frame=$(".nav_link_frame");
	var xl = frame.scrollLeft();
	var scro = xl + 300;
	frame.animate({scrollLeft: scro}, 200);
} 

LUI.ready(function(){
		seajs.use(['lui/qrcode'], function(qrcode) {
			var url = window.location.href;
			//alert(url);
			qrcode.Qrcode({
				text :url,
				element : $(".lui_keydata_qcode")[0],
				render :  'canvas'
			});
		});
});


</script>
			
			<div id="iframeDiv" class="lui_zone_mbody_conetnt">
				<iframe id="iframe_body" name="iframe_body" src="" value="" width="100%;" height="100%;" border="0" marginwidth="0" marginheight="0" frameborder="0" scrolling="no"> </iframe>
			</div>
		
							    </div>
							</div>
							<!--左侧区域 Ends-->
							<!--右侧区域 Starts-->
							<div class="lui_zone_mbody_r">
								<div class="lui_zone_slide_wrap">
								    
								</div>
							</div>
							<!--右侧区域 Ends-->
						</div>
	
			</div>
			<!-- 右侧列表 Ends -->
			
		</div>
		<%-- 
		<ui:top id="top"></ui:top>
		--%>
	</body>

</html>
