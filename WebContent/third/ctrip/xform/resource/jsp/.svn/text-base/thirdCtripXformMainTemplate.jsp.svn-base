<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.web.taglib.xform.TagUtils" %>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<%@page import="net.sf.json.JSONArray"%>
<!-- 兼容历史数据使用 -->
<head>
	<link rel="stylesheet" href="${KMSS_Parameter_ContextPath}third/ctrip/xform/resource/css/thirdCtripXform.css">
</head>
<% 
	// 该文件为兼容历史数据
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
						var $bookType = $("input[name*='"+bookTypeId+"'][type='checkbox']");
						if($bookType && $bookType.length > 0){
							for(var i = 0; i < $bookType.length ;i++){								
								//绑定value改变事件，以便可以控制展示图片的隐藏和展示
								$($bookType[i]).change(function() {										
									if(this.checked){
										showOperateImg_${param.fdBookId}(this.value);
									}else{
										hideOperateImg_${param.fdBookId}(this.value);
									};
								});
							}
					}
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

			Com_AddEventListener(window,"load",third_ctrip_bookTicket_initImg_${param.fdBookId});

		</script>
	<xform:editShow>
		<div class="third_ctrip_btn third_ctrip_btn_plane" id="thirdCtripXformPlane_${param.fdBookId}" onclick="third_ctrip_bookTicket_book('plane');"><i></i>预定飞机</div>
		<div class="third_ctrip_btn third_ctrip_btn_hotel" id="thirdCtripXformHotel_${param.fdBookId}" onclick="third_ctrip_bookTicket_book('hotel');"><i></i>预定酒店</div>
	</xform:editShow>
	
	<xform:viewShow>		
		<div class="third_ctrip_disabled third_ctrip_disabled_plane" id="thirdCtripXformPlane_${param.fdBookId}"><i></i>预定飞机</div>
		<div class="third_ctrip_disabled third_ctrip_disabled_hotel" id="thirdCtripXformHotel_${param.fdBookId}"><i></i>预定酒店</div>
	</xform:viewShow>

<script>Com_IncludeFile('bookTicket_js.js',Com_Parameter.ContextPath+'third/ctrip/xform/bookticket/','js',true);</script>

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

	/*start*/
	function third_ctrip_bookTicket_book(type){
		var url = Com_Parameter.ContextPath+"third/ctrip/xform/resource/jsp/thirdCtripXformMainTemplate_toCtrip.jsp?fdId=${param.fdId}&modelName=<%=thirdCtripModelName.toString()%>&fdBookId=${param.fdBookId}&type= " + type;
		window.open(url, "_blank");
	}
	/*end*/	

	//自适应高度
	function SetWinHeight(obj)
	{   
	    var win=obj;
	    if (document.getElementById)
	    {
	        if (win && !window.opera)
	        { 
	            if (win.contentDocument && win.contentDocument.body.offsetHeight) 
	                win.height = win.contentDocument.body.offsetHeight + 10; 
	            else if(win.Document && win.Document.body.scrollHeight)
	                win.height = win.Document.body.scrollHeight + 10 ;
	        }
	    } 
	    if(typeof showUrl != "undefined" && showUrl instanceof Function){
	    	showUrl();
		}
	}
</script>