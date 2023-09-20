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
		window.third_ctrip_bookTicket_searchData_mobile = function(url){};
		require(["dojo/request"],function(request){
	
			window.third_ctrip_bookTicket_searchData_mobile = function(url){
				request.post(url,{sync:true,handleAs:'json'}).then(
					function(resultJson){
						if(resultJson){
							//error为0即表明数据没问题
							if(resultJson.errcode == '0'){
								if(resultJson.data && resultJson.data.length > 0){
									third_ctrip_loadSearchData_mobile(resultJson.data);
								}
							}else{
								alert(resultJson.errmsg);
							}	
						}
					},
					function(error){
						alert(error);
					}
				);
			}
		});
		
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
					//pc端和移动端展示的样式不同,还有pc端的ajax的同步不适合移动端				
					third_ctrip_bookTicket_searchData_mobile(url);
					return ;
				}
				
				if(displayData != null && displayData.length && displayData.length > 0){
					third_ctrip_loadSearchData_mobile(displayData);
				}	
			}

			window.third_ctrip_loadSearchData_mobile = function(data){};
			//构造移动端订单数据列表
			third_ctrip_loadSearchData_mobile = function(displayData){
				var $div = $("#third_ctrip_${param.fdControlId}_wrap");
				$div.show();
				var html = [];
				html.push('<table cellspacing="0" cellpadding="0" class="detailTableSimple"><tr><td class="detailTableSimpleTd"><table width="100%" border="0" cellspacing="0" cellpadding="0" id="TABLE_DL_bookTicketInfo" >'
							+'<tr style="display:none;"><td></td></tr>');
				//行模板
				var tableTrTemp = '<tr data-celltr="true"><td class="muiTitle"><LABEL class=xform_label style="WIDTH: auto; DISPLAY: inline" isHiddenInMobile="false">'; 
				for(var i = 0; i < displayData.length; i++){
					html.push('<tr KMSS_IsContentRow="1"><td class="detail_wrap_td"><table class="muiSimple">');
					html.push('<tr><td colspan="2" align="left" valign="middle" class="muiDetailTableNo"><span>第'+ (i+1) + '行</span></td></tr>');
					html.push(tableTrTemp + '出行人</LABEL>' +'</td><td>'+displayData[i].clients+'</td></tr>');
					html.push(tableTrTemp + '行程/有效日期</LABEL>' +'</td><td>'+displayData[i].effectDate+'</td></tr>');
					html.push(tableTrTemp + '订单内容</LABEL>' +'</td><td>'+displayData[i].orderDescription+'</td></tr>');
					html.push(tableTrTemp + '订单金额</LABEL>' +'</td><td>'+displayData[i].amount+'</td></tr>');
					html.push(tableTrTemp + '订单状态</LABEL>' +'</td><td>'+displayData[i].orderStatus+'</td></tr>');
					html.push(tableTrTemp + '订票人</LABEL>' +'</td><td>'+displayData[i].loginname+'</td></tr>');
					html.push(tableTrTemp + '预订日期</LABEL>' +'</td><td>'+displayData[i].reserveDate+'</td></tr>');
					html.push(tableTrTemp + '预订类型</LABEL>' +'</td><td>'+displayData[i].orderType+'</td></tr>');
					html.push(tableTrTemp + '订单号</LABEL>' +'</td><td>'+displayData[i].orderId+'</td></tr>');
					html.push("<tr><td colspan='2'><a onclick='third_ctrip_ssoAuth(\""+displayData[i].orderType+"\")'; style='text-decoration:underline;float:right;padding-right:1em;color:#328fb1;'>跳转到携程</a>"
							+ " </td> </tr>");
					html.push("</table></td></tr>");
				}
				html.push('</table></td></tr></table>');
				$div.html(html.join(''));
			}
			
			require(["dojo/ready"], function(ready) {
				ready(function(){
					third_ctrip_bookTicket_updateInfo();
				});
			});
			//Com_AddEventListener(window,"load",third_ctrip_bookTicket_updateInfo);
		</script>
		
		<div id="third_ctrip_${param.fdControlId}_wrap" style="display:none">		
		</div>

	</c:if>

