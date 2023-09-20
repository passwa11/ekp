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
			&nbsp;&nbsp;提供了两个接口，获取sessionId（getLoginSessionId）以及
			解析token(getTokenLoginName)接口。接口getLoginSessionId的作用是，第三方系统通过接口获取到sessionId后,访问/sys/authentication/sso/login_auto.jsp?sessionId=sessionId的值，可以单点到ekp系统。
			接口getTokenLoginName的作用是，第三方系统在cookie中取到token的值，通过该接口解析，可以返回登录名。</br>
	</p>
	</br>
	<p style="color:red">
		由于单点涉及到安全问题，所以该接口不要启用匿名访问。
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1获取sessionId（getLoginSessionId）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
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
								<td>loginName</td>
								<td>登录名</td>
								<td>字符串（String）</td>
								<td>不许为空</td>
								<td></td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（LoginSessionIdResult），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>result</td>
								<td>结果</td>
								<td>布尔（boolean）</td>
								<td>不允许为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>errorMsg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									result为true时，该值返回空。<br/>
									result为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>sessionId</td>
								<td>令牌信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									result为false时，该值返回空。<br/>
									result为true时，该值令牌信息。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);"><br/>&nbsp;&nbsp;1.2 解析token(getTokenLoginName)
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
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
								<td>token</td>
								<td>令牌号的值</td>
								<td>字符串（String）</td>
								<td>不许为空</td>
								<td></td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息（LoginSessionIdResult），详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>result</td>
								<td>结果</td>
								<td>布尔（boolean）</td>
								<td>不允许为空</td>
								<td>true:表示成功。<br/>
									false:表示失败。
								</td>
							</tr>
							<tr>
								<td>errorMsg</td>
								<td>错误信息</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									result为true时，该值返回空。<br/>
									result为false时，该值错误信息。
								</td>
							</tr>
							<tr>
								<td>loginName</td>
								<td>登录名</td>
								<td>字符串（String）</td>
								<td>可为空</td>
								<td>
									result为false时，该值返回空。<br/>
									result为true时，该值登录名信息。
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	
	
	<tr>
		<td><br/><b>2、单点过程说明</b></td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" style="width: 85%;margin-left: 40px;">
				<tr>
					<td style='font-size: 13px;'>
						<p style='font-size: 15px;'>2.1 从第三方系统单点到ekp:</p><br/>
						1、通过接口getLoginSessionId获取到sessionId;<br/>
						2、访问ekp的单点页面（/sys/authentication/sso/login_auto.jsp），有两个参数，sessionId设置为步骤1取到的值；target设置为实际想访问的ekp地址（不设置的话则进入ekp首页），该值必须经过URL编码。
					</td>
					<br/><br/><br/>
				</tr>
				<tr>
					<td  style='font-size: 13px;'>
						<p style='font-size: 15px;'>2.2 从ekp单点到第三方系统:</p><br/>
							业务场景：先登陆EKP系统，再进入第三方系统，无需再次输入账号密码，直接进入第三方系统实现sso。<br/>
							前提条件：<br/>
							1）EKP系统必须配置SSO单点；<br/>
							2）需要单点的双方系统的一级域名必须一致；<br/>
							3）双方系统的用户必须统一；<br/>
							4）双方系统服务器的时间必须一致；<br/>
							<br/>
							单点的关键在于获取到登录名，主要有两种方式：<br/>
							1、代码部署方式：<br/> 
							1）将jar包（<a href='bcprov-ext-jdk15to18-168.jar' style='color:blue'> bcprov-ext-jdk15to18-168.jar </a>，<a href='EKP-sso-third.jar' style='color:blue'> EKP-sso-third.jar </a>，<a href='EKP-SSO-client-java.jar' style='color:blue'> EKP-SSO-client-java.jar </a>）和 <a target="_blank" href='downloadTokenFile.jsp'  style='color:blue'>LRToken文件</a> 部署到第三方系统的lib目录下<br/>
							2）第三方系统通过API调用，进行本系统的验证以及登录。<br/>
								获取用户名的方法为：GetTokenUserName.getTokenUserName<br/>
								<br/>
							2、webService接口调用方式：<br/>
							1）从cookie中获取令牌环的值（cookie的名称一般为LtpaToken或者LRToken）；<br/>
							2）调用getTokenLoginName接口获取到登录名<br/>

						
					</td>
				</tr>
			</table>
		</td>
	</tr>
	

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>