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
					<td style="padding: 0px;"><div style="margin: 8px;">&nbsp;&nbsp;入参格式，详细说明如下:</div>
						<table class="tb_normal" cellpadding="0" cellspacing="0" width=100%>
							<tr class="tr_normal_title">
								<td align="center" width="12%"><b>程序名</b></td>
								<td align="center" width="12%"><b>描述</b></td>
								<td align="center" width="13%"><b>类型</b></td>
								<td align="center" width="11%"><b>可否为空</b></td>
								<td align="center" width="52%"><b>备注说明</b></td>
							</tr>
							<tr>
								<td>in</td>
								<td>入参信息</td>
								<td>字符串</td>
								<td>不允许为空</td>
								<td>格式为JSON结构的字符串。例如：{"pageSize":5,"pageNo":1,"loginName":"admin1","maindataKey":"16eeda4e5a2db38db75d48f4e7d8828e"}
								</br>
								其中，pageSize表示返回记录数，pageNo表示页码，loginName表示用户登录名（接口会返回该用户的可见数据），maindataKey表示系统内数据配置的ID。</td>
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
								<td>return</td>
								<td>返回信息</td>
								<td>字符串（JSON格式）</td>
								<td>不可为空</td>
								<td>
									例如：
									</br>
									{"state":1,"data":[{"fdId":"16eed69a92d890aa3bf7d304cc2bef31","docSubject":"123","fdAuthor|fdName":"管理员","docPublishTime":"2019-12-10 09:26","picUrl":"http://localhost:8080/ekp/sys/xform/maindata/main_data_insystem/sysFormMainDataInsystem.do?method=downloadFile&amp;fdId=16eed6a7224fe494bd4788541c9a9d3a&amp;Signature=xc2+c0bmKSypexI+X1O/vno6iaY=&amp;Expires=1578307715771"}]}
									</br>
									其中，state表示状态，1为成功，2为失败；data为JSON格式的字符串，内容为主数据的信息；errMsg表示错误信息，但state返回2时，该字段返回对应的错误内容。
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