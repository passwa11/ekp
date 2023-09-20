<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|optbar.js");
</script>
<script type="text/javascript">
 document.title = "主数据查询";
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


<table border="0" width="96%">
	<tr>
		<td><b>1、接口说明</b></td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);">&nbsp;&nbsp;1.1获取主数据信息（getData）
			<img src="${KMSS_Parameter_StylePath}icons/collapse.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr>
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 96%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="10%" style="white-space:nowrap;">参数信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;入参格式（JSON对象），详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="12%"><b>程序名</b></td>
								<td align="center" width="12%"><b>描述</b></td>
								<td align="center" width="13%"><b>类型</b></td>
								<td align="center" width="11%"><b>可否为空</b></td>
								<td align="center" width="52%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>pageSize</td>
								<td>返回记录数</td>
								<td>数字</td>
								<td>允许为空</td>
								<td>为空则使用默认值10。</td>
							</tr>
							<tr>
								<td>pageNo</td>
								<td>页码</td>
								<td>数字</td>
								<td>允许为空</td>
								<td>为空则使用默认值1。</td>
							</tr>
							<tr>
								<td>loginName</td>
								<td>用户登录名</td>
								<td>字符串</td>
								<td>不允许为空</td>
								<td>接口返回该用户的可见数据
								</td>
							</tr>
							<tr>
								<td>maindataKey</td>
								<td>系统内数据配置的ID</td>
								<td>字符串</td>
								<td>不允许为空</td>
								<td></td>
							</tr>
					</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="10%" style="white-space:nowrap;">返回信息</td>
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;返回信息，详细说明如下:</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="12%"><b>程序名</b></td>
								<td align="center" width="12%"><b>描述</b></td>
								<td align="center" width="13%"><b>类型</b></td>
								<td align="center" width="11%"><b>可否为空</b></td>
								<td align="center" width="52%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>state</td>
								<td>返回状态</td>
								<td>数字（int）</td>
								<td>不允许为空</td>
								<td>
									1:表示操作失败。<br/>
									2:表示操作成功。
								</td>
							</tr>
							<tr>
								<td>data</td>
								<td>返回信息</td>
								<td>JSON数组</td>
								<td>可为空</td>
								<td>
									返回状态值为1时，该值主数据信息。<br/>
									返回状态值为2时， 该值返回空。
								</td>
							</tr>
							<tr>
								<td>errMsg</td>
								<td>错误信息</td>
								<td>字符串</td>
								<td>可为空</td>
								<td>
									返回状态值为2时，该值错误信息。<br/>
									返回状态值为1时， 该值返回空。
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