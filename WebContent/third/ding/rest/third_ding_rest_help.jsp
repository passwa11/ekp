<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|optbar.js");
</script> 
<script type="text/javascript">
 function expandMethod(domObj) {
	var thisObj = $(domObj);
	var isExpand = thisObj.attr("isExpanded");
	if(isExpand == null)
		isExpand = "1";
	var trObj=thisObj.parents("tr");
	trObj = trObj.next("tr");
	var imgObj = thisObj.find("img");
	if(trObj.length > 0){
		if(isExpand=="0"){
			trObj.show();
			thisObj.attr("isExpanded","1");
			imgObj.attr("src","${KMSS_Parameter_StylePath}icons/collapse.gif");
		}else{
			trObj.hide();
			thisObj.attr("isExpanded","0");
			imgObj.attr("src","${KMSS_Parameter_StylePath}icons/expand.gif");
		}
	}
 }
</script>


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}接口说明</p>

<center>
<div style="width: 95%;text-align: left;">
	<p>
			&nbsp;&nbsp;提供了一个接口，根据钉钉userId获取用户信息（getUserInfo）。</br>
	</p>
	</br>
	
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1获取getUserInfo（<font color="red">请求方式：POST</font>）（/api/third-ding/thirdDingUserInfo/getUserInfo）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
		<br/>	
		
	</tr>
	<tr>
	
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;">
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>userId</td>
								<td>钉钉用户ID</td>
								<td>字符串（String）</td>
								<td>不许为空</td>
								<td>URL参数</td>
							</tr>
					</table>
					</td>
				</tr>
				
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回body数据（JSON对象），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>state</td>
								<td>结果</td>
								<td>数字</td>
								<td>不为空</td>
								<td>1:表示成功。<br/>
									0:表示失败。
								</td>
							</tr>
							<tr>
								<td>errorMsg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									state为1时，该值返回空。<br/>
									state为0时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>用户信息</td>
								<td>JSON对象</td>
								<td>可为空</td>
								<td>
									result为false时，该值返回空。<br/>
									result为true时，该值令牌信息。
								</td>
							</tr>
							
							<tr>
								<td style="padding-left:25px;">name</td>
								<td>用户名称</td>
								<td>字符串</td>
								<td>不为空</td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td style="padding-left:25px;">loginName</td>
								<td>用户登录名</td>
								<td>字符串</td>
								<td>不为空</td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td style="padding-left:25px;">mobile</td>
								<td>用户手机号</td>
								<td>字符串</td>
								<td>不为空</td>
								<td>
									
								</td>
							</tr>
							<tr>
								<td style="padding-left:25px;">email</td>
								<td>用户邮箱</td>
								<td>字符串</td>
								<td>可为空</td>
								<td>
									
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>