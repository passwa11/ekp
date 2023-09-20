<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<link href="<%=request.getContextPath() %>/third/ctrip/xform/resource/mobile/thirdCtripXformMobile.css" type="text/css" rel="stylesheet" />
<style>

#third_ctrip_${param.fdControlId}_viewInCtrip .mblScrollableViewContainer{
	height:100%;
}

</style>
<script>

	require(["dojo/parser", "dojo/dom", "dojo/dom-construct", "dijit/registry","dojo/topic"], function(parser, dom, domConstruct, registry, topic){
		var _curview = null;
		window.third_ctrip_${param.fdControlId}_view_show = function(addNewRow,callbackFun) {					
			var tmpl = dom.byId("dt_wrap_${param.fdControlId}_view");
			if(_curview==null)
				_curview = registry.byId('${param.backTo}');
			_curview.set('validateNext',false);
			var validated = _curview.performTransition('dt_wrap_${param.fdControlId}_view', 1, "slide");
			if(_curview.domNode.parentNode == tmpl.parentNode){
				if(validated)
					tmpl.style.display = 'block';
			}else{
				domConstruct.place(tmpl,_curview.domNode.parentNode,'last');
				parser.parse(tmpl).then(function(){
					if(validated){
						tmpl.style.display = 'block';
						topic.publish("/mui/datailTable/init");
					    if(callbackFun)callbackFun();
					}
				});
			}
		};
		window.third_ctrip_${param.fdControlId}_hide = function(){
			var view = registry.byId('dt_wrap_${param.fdControlId}_view');
			view.performTransition(_curview.id, -1, "slide");
			_curview.set('validateNext',true);
			if(_curview.validate)
				_curview.validate();
		}
	});
</script>
<div class="mblListItem detailTableForward" onclick="third_ctrip_${param.fdControlId}_view_show();">
	<i class="mui mui-forward mblListItemRightIcon">
	</i>
	<div layout="left" class="mblListItemLayoutLeft"></div>
<%-- 	<div layout="left" class="mblListItemLayoutLeft">${param.label }</div> --%>
	<div layout="right" class="mblListItemLayoutRight">查看详情</div>
</div>
<!-- 查看预订信息界面 -->
<div class="detailTableView"  data-dojo-type="mui/table/DetailTableScrollableView"  data-dojo-mixins="mui/form/_ValidateMixin" 
	id="dt_wrap_${param.fdControlId}_view">
	<div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
	    <div class="muiHeaderDetailTitle">携程信息</div>
		  <div class="muiHeaderDetailBack" style="float:right" onclick="third_ctrip_${param.fdControlId }_hide()">
			<i class="mui mui-close"></i>
		  </div>
	</div>
	<br/><br/>
	<c:import url='/third/ctrip/xform/resource/mobile/thirdCtripBookInfoShow_mobile.jsp' charEncoding='UTF-8'>
		<c:param name='isMobile' value='true'></c:param>
		<c:param name='fdControlId' value='${param.fdControlId }'></c:param>
	</c:import>		
</div>
<!-- 跳转到携程界面 -->
<div  class="thirdCtripView" data-dojo-type="mui/view/DocScrollableView" id="third_ctrip_${param.fdControlId}_viewInCtrip">
	<div data-dojo-type="dojox/mobile/Heading" fixed="top" class="muiHeaderDetail">
	    <div class="muiHeaderDetailTitle">携程商旅</div>
		  <div class="muiHeaderDetailBack" onclick="third_ctrip_moblie_${param.fdControlId}_hideToInfoView();">
			<i class="mui mui-close"></i>
		  </div>
	</div>
	<br/><br/>
	<div id="third_ctrip_${param.fdControlId}_loading" style="display:none;height:100%;width:100%;filter:alpha(opacity=30); background-color:white;opacity: 0.3;-moz-opacity: 0.3; ">
		<div class="third_ctrip_loading" title="数据加载中"></div>
	</div>
	<iframe id="third_ctrip_${param.fdControlId}_iframeInCtrip" frameborder='0' scrolling='auto' style="height:93%;width:100%;margin-top:8px;border:0px;">
	</iframe>
</div>
<script>

	window.third_ctrip_ssoAuth = function(bookType){};

	window.third_ctrip_moblie_info_ssoCtrip = function(iframe,url){}
	
	window.third_ctrip_moblie_${param.fdControlId}_hideToInfoView = function(){};
	
	require(["dojo/parser","dojo/request","dojo/dom", "dijit/registry", "dojo/dom-construct"],function(parser,request,dom,registry,domConstruct){
		var _curview = null;

		window.third_ctrip_moblie_${param.fdControlId}_hideToInfoView = function(){
			var view = registry.byId('third_ctrip_${param.fdControlId}_viewInCtrip');
			view.performTransition(_curview.id, -1, "slide");
			dom.byId(_curview.id).style.display = 'block';
			_curview.set('validateNext',true);
			if(_curview.validate)
				_curview.validate();
		}
		
		window.third_ctrip_ssoAuth = function(bookType){
			var status = "${JsParam.status}";
			if(status!="edit"){
				return;
			}
			//切换视图
			var tmpl = dom.byId("third_ctrip_${param.fdControlId}_viewInCtrip");
			if(_curview==null)
				_curview = registry.byId('dt_wrap_${param.fdControlId}_view');
			_curview.set('validateNext',false);
			var validated = _curview.performTransition("third_ctrip_${param.fdControlId}_viewInCtrip", 1, "slide");
			if(_curview.domNode.parentNode == tmpl.parentNode){
				if(validated){
					tmpl.style.display = 'block';
				}
			}else{
				domConstruct.place(tmpl,_curview.domNode.parentNode,'last');
				tmpl.style.display = 'block';
			}	
			var iframeDom = dom.byId("third_ctrip_${param.fdControlId}_iframeInCtrip");
			//加载图标 
			var loadingDom = dom.byId("third_ctrip_${param.fdControlId}_loading");
			if(loadingDom){
				loadingDom.style.display = '';
			}
			if(bookType=="飞机"){
				bookType = "plane";
			}else{
				bookType = "hotel";
			}
			var ssoUrl = Com_Parameter.ContextPath+"third/ctrip/ctripCommon.do?method=ctripSsoAuth&initPage=1&type=mobile&orderId=${param.fdId}&bookType="+bookType;
			iframeDom.src = ssoUrl;
			iframeDom.onload = function(){
				dom.byId("third_ctrip_${param.fdControlId}_loading").style.display = 'none';
			}
		}

		//返回sso的url，并跳转
		window.third_ctrip_moblie_info_ssoCtrip = function(iframe,url){
			request.post(url,{handleAs:'json'}).then(
				function(data){
					if(data.errcode == '0'){
						iframe.src = data.url;
						iframe.onload = function(){
							dom.byId("third_ctrip_${param.fdControlId}_loading").style.display = 'none';
						}
					}else{
						alert(data.errmsg);
					}
				},
				function(error){
					//失败后返回
					alert(error);
				}
			);
		} 
	
	});

</script>

