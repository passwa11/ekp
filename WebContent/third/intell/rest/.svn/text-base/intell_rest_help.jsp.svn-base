<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|list.js");
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
	<p>&nbsp;&nbsp;智能组件接口提供了给aip智能平台调用的接口，以下是对上述接口参数的具体说明。
	</p>
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;单点测试（/api/third-intell/rest/testAPI）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;测试接口能否调用参数信息如下：</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="10%"><b>程序名</b></td>
								<td align="center" width="10%"><b>描述</b></td>
								<td align="center" width="10%"><b>类型</b></td>
								<td align="center" width="15%"><b>可否为空</b></td>
								<td align="center" width="55%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>任意一个id</td>
								<td>String字符串</td>
								<td>不允许为空</td>
								<td>不能为空</td>
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
								<td>data</td>
								<td>返回数据</td>
								<td>String字符串</td>
								<td>不允许为空</td>
								<td>一个空字符串</td>
							</tr>
							<tr>
								<td>errcode</td>
								<td>出错码</td>
								<td>数字（int）</td>
								<td>不为空</td>
								<td>
									返回结果状态码，0为正常，其他为失败
								</td>
							</tr>
							<tr>
								<td>errmsg</td>
								<td>出错信息</td>
								<td>字符串（String）</td>
								<td>不为空</td>
								<td>
									返回出错结果描述
								</td>
							</tr>

						</table>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
    		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.2&nbsp;&nbsp;单点测试（/api/third-intell/rest/testSSO）
    			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
    			</td>
    	</tr>
    	<tr>
    		<td>
    			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
    				<tr>
    					<td class="td_normal_title" width="20%">参数信息</td>
    					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;测试单点是否成功调用参数信息如下：</div>
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
    								<td>单点的token</td>
    								<td>String字符串</td>
    								<td>不允许为空</td>
    								<td>不能为空</td>
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
    								<td>data</td>
    								<td>返回数据</td>
    								<td>String字符串</td>
    								<td>不允许为空</td>
    								<td>一个空字符串</td>
    							</tr>
    							<tr>
    								<td>errcode</td>
    								<td>出错码</td>
    								<td>数字（int）</td>
    								<td>不为空</td>
    								<td>
    									返回结果状态码，0为正常，其他为失败
    								</td>
    							</tr>
    							<tr>
    								<td>errmsg</td>
    								<td>出错信息</td>
    								<td>字符串（String）</td>
    								<td>不为空</td>
    								<td>
    									返回出错结果描述
    								</td>
    							</tr>

    						</table>
    					</td>
    				</tr>
    			</table>
    		</td>
    	</tr>
</table>

</center>

<%@ include file="/resource/jsp/view_down.jsp"%>