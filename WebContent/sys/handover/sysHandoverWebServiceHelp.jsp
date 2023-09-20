<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
	Com_IncludeFile("optbar.js|list.js");
</script>
<script>
 function expandMethod_tr(domObj) {
	 var thisObj = $(domObj);
		var isExpand = thisObj.attr("isExpanded");
		if(isExpand == null)
			isExpand = "0";
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
 
 function expandMethod(imgSrc,divSrc) {
		var imgSrcObj = document.getElementById(imgSrc);
		var divSrcObj = document.getElementById(divSrc);
		if(divSrcObj.style.display!=null && divSrcObj.style.display!="") {
			divSrcObj.style.display = "";
			imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/collapse.gif";
		}else{
			divSrcObj.style.display = "none";
			imgSrcObj.src = "${KMSS_Parameter_StylePath}icons/expand.gif";		
		}
	 }
 List_TBInfo = new Array(
			{TBID:"List_ViewTable1_1"},{TBID:"List_ViewTable1_2"}
		);
</script>


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">${HtmlParam.name}服务接口说明</p>

<center>
<br/>
<table border="0" width="95%">
	<tr>
		<td><b>1.接口说明</td>
	</tr>
	<!-- 接口01 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;工作交接
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">addHandover(SysHandoverReq req)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">工作交接（根据参数不同，提供四种不同类型的或者全部的工作交接）</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数req</td>
					<td width="85%"><img id="viewSrc1_1"
										 src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
										 onclick="expandMethod('viewSrc1_1','paramDiv1_1')" style="cursor: pointer"><br>
						<div id="paramDiv1_1" style="display:none">
							<table id="List_ViewTable1_1" class="tb_noborder">
								<tr>
									<sunbor:columnHead htmlTag="td">
										<td width="40pt">序号</td>
										<td width="20%">属性名</td>
										<td width="20%">类 型</td>
										<td width="20%">缺省值</td>
										<td width="10%">描 述</td>
									</sunbor:columnHead>
								</tr>
								<tr>
									<td align="center">1</td>
									<td>fdFromElement</td>
									<td>字符串（String）</td>
									<td>不能为空</td>
									<td>工作交接的交接人，标准组织架构数据格式</td>
								</tr>
								<tr>
									<td align="center">2</td>
									<td>fdToElement</td>
									<td>字符串（String）</td>
									<td>不能为空</td>
									<td>工作交接的接收人，标准组织架构数据格式</td>
								</tr>
								<tr>
									<td align="center">2</td>
									<td>fdCreator</td>
									<td>字符串（String）</td>
									<td>不能为空</td>
									<td>工作交接的创建人，标准组织架构数据格式</td>
								</tr>
								<tr>
									<td align="center">3</td>
									<td>fdType</td>
									<td>字符串（String）</td>
									<td>不能为空</td>
									<td>交接类型 doc-在途的流程 config-后台配置类 auth-文档中权限 item-事项交接 all-交接所有工作</td>
								</tr>
								<tr>
									<td align="center">4</td>
									<td>execMode</td>
									<td>字符串（String）</td>
									<td>0</td>
									<td>当交接类型为事项交接时，可选填此事项交接模式：替换交接人-0,追加交接人-1</td>
								</tr>
							</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">执行结果SysHandoverResp resp</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回参数</td>
					<td width="85%"><img id="viewSrc1_2"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc1_2','paramDiv1_2')" style="cursor: pointer"><br>
					<div id="paramDiv1_2" style="display:none">
					<table id="List_ViewTable1_2" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
								<td width="20%">缺省值</td>
							    <td width="10%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdTitle</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>定时任务标题（交接类型为在途流程或事项交接时，返回此参数）,如果入参数的fdType为all，则此参数返回多个标题</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>fdUrl</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>定时任务url（交接类型为在途流程或事项交接时，返回此参数）,如果入参数的fdType为all，则此参数返回多个url</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>fdMsg</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>返回消息,如果入参数的fdType为all，则此参数返回多个消息</td>
						</tr>
					</table></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>