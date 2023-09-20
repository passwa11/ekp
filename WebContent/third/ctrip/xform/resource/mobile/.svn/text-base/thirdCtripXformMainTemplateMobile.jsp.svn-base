<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<!-- 兼容历史数据使用 -->
<link href="<%=request.getContextPath() %>/third/ctrip/xform/resource/mobile/thirdCtripXformMobile.css" type="text/css" rel="stylesheet" />
<style>
#third_ctrip_${param.fdBookId}_view .mblScrollableViewContainer{
	height:100%;
}
.muiCtripEKP {
	width: 42px;
	height: 42px;
	line-height: 42px;
	vertical-align: middle;
	text-align: center;
	position: fixed;
	bottom: 20.5rem;
	right: 5px;
	border-radius: 50%;
	background: #02a5ee;
	background-size: 42px;
	cursor: pointer;
	display: block;
	line-height: 42px;
	z-index: 99;
	color: #fff;
	font-size: 1.2rem;
}

</style>
<input type='hidden' id='bussinessModuleFormId' value='${requestScope[param.formName].fdId}'/>
<% 
	//form处理 start
	String formBeanName = request.getParameter("formName");
	IExtendForm mainForm = (IExtendForm)request.getAttribute(formBeanName);
	//获取modelname
	String thirdCtripModelName = mainForm.getModelClass().getName();
	//获取表单数据库的数据
	Object thirdCtripFormData = PropertyUtils.getProperty(mainForm, "extendDataFormInfo.formData");
%>	
		<script>	
			//显示操作图片		
			function showOperateImg_${param.fdBookId}(bookTypeValue){
				if(bookTypeValue.indexOf('plane') > -1){
					$("#thirdCtripXformPlane_${param.fdBookId}").css('display','inline-block');	
				}
				if(bookTypeValue.indexOf('hotel') > -1){
					$("#thirdCtripXformHotel_${param.fdBookId}").css('display','inline-block');	
				}
			}
			//隐藏操作图片
			function hideOperateImg_${param.fdBookId}(bookTypeValue){
				if(bookTypeValue.indexOf('plane') > -1){
					$("#thirdCtripXformPlane_${param.fdBookId}").hide();	
				}
				if(bookTypeValue.indexOf('hotel') > -1){
					$("#thirdCtripXformHotel_${param.fdBookId}").hide();	
				}
			}
			
			function third_ctrip_bookTicket_initImg_${param.fdBookId}(){
				var formDataJson = <%=JSONArray.fromObject(thirdCtripFormData)%>;//得到的是一个json
				var formData = formDataJson[0];
				var relationIdDom = $('[name="sysXformBookTicketData_${param.fdBookId}"]')[0];
				var bookTypeId = relationIdDom.getAttribute("bookType_relationid");
				if(bookTypeId){
					var bookType = formData[bookTypeId];
					if(bookType && bookType != null && bookType != ''){
						showOperateImg_${param.fdBookId}(bookType);
					}					
					//监听控件值变化
					AttachXFormValueChangeEventById(bookTypeId, function(value, widget){
						hideOperateImg_${param.fdBookId}('plane;hotel');
						showOperateImg_${param.fdBookId}(value);						
					});
				};	
			}

			function third_ctrip_bookTicket_noAuth(type){
				var noAuthTip;
				if(type && type == 'plane'){
					noAuthTip = "抱歉，你没有预定机票的权限！";
				}else if(type && type == 'hotel'){
					noAuthTip = "抱歉，你没有预定酒店的权限！";	
				}			
				alert(noAuthTip);
			}		
			
			require(["dojo/query" ,"dojo/dom","dojo/ready"], function(query,dom,ready){
				ready(function(){
						third_ctrip_bookTicket_initImg_${param.fdBookId}(true);
					});
				});
 
			
		</script>
	<!-- 点击事件需要加个延迟，不然移动端无法及时获取到准确的数据 -->
	<xform:editShow>
		<div id="thirdCtripXformPlane_${param.fdBookId}" class="third_ctrip_mobile_btn third_ctrip_mobile_btn_plane" onclick="setTimeout(function(){third_ctrip_bookTicket_book('plane');},100);"><i></i>预定飞机</div>
		<div class="third_ctrip_mobile_btn third_ctrip_mobile_btn_hotel" id="thirdCtripXformHotel_${param.fdBookId}" onclick="setTimeout(function(){third_ctrip_bookTicket_book('hotel');},100);""><i></i>预定酒店</div>
		<div  class="thirdCtripView" data-dojo-type="mui/view/DocScrollableView" id="third_ctrip_${param.fdBookId}_view">
			<div id="third_ctrip_${param.fdBookId}_loading" style="display:none;height:100%;width:100%;filter:alpha(opacity=30); background-color:white;opacity: 0.3;-moz-opacity: 0.3; ">
				<div class="third_ctrip_loading" title="数据加载中"></div>
			</div>
			<div class="third_ctrip_iframe" style="height:100%;width:100%;">
			
			</div>
			<div class="muiCtripEKP" onclick="third_ctrip_moblie_${param.fdBookId}_hide();">流程</div>
		</div>

	</xform:editShow>
	
	<xform:viewShow>		
		<div class="third_ctrip_disabled third_ctrip_disabled_plane" id="thirdCtripXformPlane_${param.fdBookId}"><i></i>预定飞机</div>
		<div class="third_ctrip_disabled third_ctrip_disabled_hotel" id="thirdCtripXformHotel_${param.fdBookId}"><i></i>预定酒店</div>
	</xform:viewShow>

<script>Com_IncludeFile('thirdXformMobilCtrip_script.js',Com_Parameter.ContextPath+'third/ctrip/xform/resource/mobile/','js',true);</script>
<script>

	//提交的时候校验必填数据
	Com_Parameter.event["confirm"][Com_Parameter.event["confirm"].length] = third_ctrip_xform_validate;
	function third_ctrip_xform_validate(){
		var method = '${param.method}';
		if( method.indexOf('view') < 0){
			var bookTicketData = $("[name='sysXformBookTicketData_${param.fdBookId}']")[0];
			if(third_ctrip_bookTicket_bookTicket(bookTicketData)){
				return true;
			}
			return false;
		}
		return true;
	}

	//获取默认值 主要用于view页面获取数据库的值
	function third_ctrip_bookTicket_getDefalut(name,index,collection){
		var formDataJson = <%=request.getAttribute("formData")%>;//得到的是一个json
		var formData = formDataJson[0];
		//如果index是XXX.id，则转换
		if(index.indexOf('.') > -1){
			var leftVar = index.substring(0,index.indexOf('.'));
			var rightVar = index.substring(index.indexOf('.')+1);	
			collection[name] = formData[leftVar][rightVar];
			return
		}
		collection[name] = formData[index];
	}

	window.third_ctrip_bookTicket_book = function(type){
		var url = Com_Parameter.ContextPath + "third/ctrip/ctripCommon.do?method=appovalCtripOrder&modelName=<%=thirdCtripModelName.toString()%>&fdId=${param.fdId}"
		 + "&fdControlId=" + "${param.fdBookId}&bookType=" + type;
		third_xform_mobile_appravolCtrip(url,type);
	};

	window.third_xform_mobile_appravolCtrip = function(url){};

	window.third_ctrip_moblie_${param.fdBookId}_hide = function(){};

	window.third_ctrip_moblie_ssoCtrip = function(iframe,url){};
	
	require(["dojo/parser","dojo/request","dojo/dom", "dijit/registry", "dojo/dom-construct","dojo/query"],
			function(parser,request,dom,registry,domConstruct, query){
		var _curview = null;

		//隐藏
		window.third_ctrip_moblie_${param.fdBookId}_hide = function(){
			var view = registry.byId('third_ctrip_${param.fdBookId}_view');
			// 清空iframe
			var iframe = query('iframe',view.domNode);
			domConstruct.destroy(iframe[0]);
			return view.performTransition(_curview.id, -1, "slide");
		};
		
		window.third_xform_mobile_appravolCtrip = function(url,type){
			//先切换视图
			var tmpl = dom.byId("third_ctrip_${param.fdBookId}_view");
			if(_curview==null)
				_curview = registry.byId('${param.backTo}');
			var validated = _curview.performTransition("third_ctrip_${param.fdBookId}_view", 1, "slide");
			if(_curview.domNode.parentNode == tmpl.parentNode){
				if(validated){
					tmpl.style.display = 'block';
				}
			}else{
				domConstruct.place(tmpl,_curview.domNode.parentNode,'last');
				tmpl.style.display = 'block';
			}	
			var divIframe = query('.third_ctrip_iframe',tmpl)[0];
			var iframeDom = query('iframe',divIframe);
			if(iframeDom.length == 0){
				iframeDom = domConstruct.create("iframe",{
								frameborder : '0',
								scrolling : 'auto', 
								style : {'height':'100%',
										 'width':'100%',
										 'border':'0px'
										 }
								},divIframe); 
			}
				
			//加载图标 
			var loadingDom = dom.byId("third_ctrip_${param.fdBookId}_loading");
			if(loadingDom){
				loadingDom.style.display = '';
			}
			//切换视图之后，让它慢慢加载
			request.post(url,{handleAs:'json'}).then(
					function(data){
						//成功后返回
						if(data && data != null){
							//error为0即表明数据没问题
							if(data.errcode == '0'){							
								var ssoMethodUrl = Com_Parameter.ContextPath+"third/ctrip/ctripCommon.do?method=ctripSsoAuth&type=mobile&orderId=${param.fdId}&bookType="+type+"&fdControlId=${param.fdBookId}&fdId=${param.fdId}&modelName=<%=thirdCtripModelName.toString()%>";
								//third_ctrip_moblie_ssoCtrip(iframeDom,ssoMethodUrl);
								iframeDom.src = ssoMethodUrl;
								iframeDom.onload = function(){
									dom.byId("third_ctrip_${param.fdBookId}_loading").style.display = 'none';
								}
							}else{
								alert(data.errmsg);
							}
						}
					},
					function(error){
						//失败后返回
						alert(error);
					}
			);	
		};

		//返回sso的url，并跳转
		window.third_ctrip_moblie_ssoCtrip = function(iframe,url){
			request.post(url,{handleAs:'json'}).then(
				function(data){
					if(data.errcode == '0'){
						iframe.src = data.url;
						iframe.onload = function(){
							dom.byId("third_ctrip_${param.fdBookId}_loading").style.display = 'none';
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
		}; 
	})
</script>