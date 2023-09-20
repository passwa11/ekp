<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.common.forms.IExtendForm" %>
<%@page import="net.sf.json.JSONArray"%>
<style>
	.tr_listrowcur{
		cursor: pointer;
		color: rgb(255, 255, 255);
		background: rgb(170, 170, 170);
	}
</style>
<%
	//获取表单的数据
	//form处理 start
	String formBeanName = request.getParameter("formName");
	IExtendForm mainForm = (IExtendForm)request.getAttribute(formBeanName);
	//获取modelname
	request.setAttribute("modelName",mainForm.getModelClass().getName());
	//获取表单数据库的数据
	Object thirdCtripFormData = PropertyUtils.getProperty(mainForm, "extendDataFormInfo.formData");
	
%>
	<c:if test="${param.fdId ne null}">
		<script>
			function third_ctrip_bookTicket_updateInfo(){
				//根据控件id从表单数据里面查找存储的数据
				var formDataJson = <%=JSONArray.fromObject(thirdCtripFormData)%>;//得到的是一个json
				var formData = formDataJson[0];
				// 控件id
				var fdControlId = '${param.fdControlId}';
				var resultJson = eval('(' + formData[fdControlId] + ')');
				var displayData = resultJson;
				// 是否需要更新预订信息标志
				var isUpdata = true;
				// 只要能够根据控件id找到控件存储的信息就继续进行判断
				/*
				if(resultJson){
					//如果存在存储数据，则判断所有数据的订单状态，只要是不可更改状态，即不再查询
					if(resultJson.length > 0){
						for(var i = 0; i < resultJson.length; i++){
							var controlData = resultJson[i];
							if(controlData && controlData['orderStatus'] && (controlData['orderStatus'] != '已付款')){
								isUpdata = true;
								break;	
							}
						}
					}else{
						isUpdata = true;
					}
				}else{
					isUpdata = true;
				}
				*/

				if(isUpdata){
					var url = Com_Parameter.ContextPath + 'third/ctrip/ctripCommon.do?method=searchCtripOrder&approvalNumber=${param.fdId }&isMobile=${param.isMobile }&fdControlId=${param.fdControlId }&modelName=${modelName}';
					
					$.ajax({
						type:"post",
						url:url,
						asyncx:false,
						success:function(result){
							if(result && result != ''){
								var resultJson = $.parseJSON(result);
								//error为0即表明数据没问题
								if(resultJson.errcode == '0'){
									displayData = resultJson.data;	
								}else{
									alert(resultJson.errmsg);
								}	
							}
						}
					});
				}
				
				
				if(displayData != null && displayData.length && displayData.length > 0){
					third_ctrip_loadSearchData(displayData);
				}	
			}

			//构造订单数据列表
			function third_ctrip_loadSearchData(displayData){
				var $div = $("#third_ctrip_${param.fdControlId}_wrap");
				$div.show();
				var html = [];
				html.push('<table class="tb_normal" width="100%" style="text-align:center;">');
				html.push('<tr class="tr_normal_title">');
				html.push('<td width="40pt" class="td_normal_title">' + '序号' + '</td>');
				html.push('<td class="td_normal_title">' + '出行人' + '</td>');
				html.push('<td class="td_normal_title">' + '行程/有效日期' + '</td>');
				html.push('<td class="td_normal_title">' + '订单内容' + '</td>');		
				html.push('<td class="td_normal_title">' + '订单金额' + '</td>');
				html.push('<td class="td_normal_title">' + '订单状态' + '</td>');
				html.push('<td class="td_normal_title">' + '订票人' + '</td>');
				html.push('<td class="td_normal_title">' + '预订日期' + '</td>');
				html.push('<td class="td_normal_title">' + '预订类型' + '</td>');
				html.push('<td class="td_normal_title">' + '订单号' + '</td>');
				html.push('</tr>');
				for(var i = 0; i < displayData.length; i++){
					html.push('<tr id = "' + displayData[i].orderId + '" >');
					html.push('<td>' + (i + 1) + '</td>');
					html.push('<td>' + displayData[i].clients + '</td>');			
					html.push('<td>' + displayData[i].effectDate + '</td>');
					html.push('<td>' + displayData[i].orderDescription + '</td>');
					html.push('<td>' + displayData[i].amount + '</td>');
					html.push('<td>' + displayData[i].orderStatus + '</td>');
					html.push('<td>' + displayData[i].loginname + '</td>');
					html.push('<td>' + displayData[i].reserveDate + '</td>');
					html.push('<td name="bk">' + displayData[i].orderType + '</td>');
					html.push('<td>' + displayData[i].orderId + '</td>');
					html.push('</tr>');
				}
				html.push('</table>');
				$div.html(html.join(''));
				$div.find('tr:gt(0)').mouseover(function(){
					$(this).addClass("tr_listrowcur");
				}).mouseout(function(){
					$(this).removeClass("tr_listrowcur");
				}).click(function(){
					var status = "${JsParam.status}";
					if(status!="edit"){
						return;
					}
					var fdId = $(this).attr("id");
					var bookType = $(this).find("td[name='bk']").html();
					if(bookType=="飞机"){
						bookType = "plane";
					}else{
						bookType = "hotel";
					}
					var ssoUrl = Com_Parameter.ContextPath+'third/ctrip/ctripCommon.do?method=ctripSsoAuth&initPage=1&type=pc&orderId=${param.fdId }&bookType='+bookType;
					window.open(ssoUrl,"_blank");
					//third_ctrip_info_openCtripBySsoUrl(ssoUrl);
				});
			}

			function third_ctrip_info_openCtripBySsoUrl(url){
				$.ajax({
					type:"post",
					url:url,
					asyncx:true,
					success:function(result){
						if(result && result != ''){
							var resultJson = $.parseJSON(result);
							if(resultJson && resultJson != null){
								//error为0即表明数据没问题
								if(resultJson.errcode == '0'){
									var ssoUrl = resultJson.url;
									window.open(ssoUrl, "_blank");
								}else{
									alert(resultJson.errmsg);
								}
							}	
						}
					}
				});
			}
			
			Com_AddEventListener(window,"load",third_ctrip_bookTicket_updateInfo);
		</script>
		
		<div id="third_ctrip_${param.fdControlId}_wrap" style="display:none">		
		</div>

	</c:if>

