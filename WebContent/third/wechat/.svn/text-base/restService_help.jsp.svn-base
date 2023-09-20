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
			{TBID:"List_ViewTable1_1"},{TBID:"List_ViewTable1_2"},{TBID:"List_ViewTable1_3"},
			{TBID:"List_ViewTable2_1"},{TBID:"List_ViewTable2_2"},{TBID:"List_ViewTable2_3"},
			{TBID:"List_ViewTable3_1"},{TBID:"List_ViewTable3_2"},{TBID:"List_ViewTable3_3"}			
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
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.1&nbsp;&nbsp;添加附件
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">addForum(WeChatParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">添加附件</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">附件的数据库主键值（fd_id）</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
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
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>docSubject</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档标题，不允许为空</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>docContent</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档的富文本内容</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>fdTopicId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>主贴id，允许为空</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>fdPostId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>回复id，允许为空</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>modelId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>附件关联id，允许为空</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>modelName</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>附件模块，允许为空</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>attachmentForms</td>
							<td>链表（List&lt;AttachmentForm&gt;）</td>
							<td>无</td>
							<td>附件列表，允许为空</td>
						</tr>			
						<tr>
							<td align="center">8</td>
							<td>utype</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>查询类型 - 1部门  2个人，允许为空</td>
						</tr>
						<tr>
							<td align="center">9</td>
							<td>uparam</td>
							<td>字符串（Sring）</td>
							<td>无</td>
							<td>参数，允许为空</td>
						</tr>
						<tr>
							<td align="center">10</td>
							<td>unum</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>返回的控制条目</td>
						</tr>														
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>附件AttachmentForm的定义</td>
					<td width="85%"><img id="viewSrc1_3"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc1_3','paramDiv1_3')" style="cursor: pointer;"><br>
					<div id="paramDiv1_3" style="display:none">
					<table id="List_ViewTable1_3" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="30%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="20%">缺省值</td>
							    <td width="30%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdKey</td>
							<td>字符串（String）</td>
							<td>"fdAttachment"</td>
							<td>附件的关键字，富文本模式下为"fdAttachment"，表单模式下为附件控件的id</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>fdFileName</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>附件文件名</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>fdAttachment</td>
							<td>字节数组（byte[]）</td>
							<td>无</td>
							<td>附件内容，格式为字节编码</td>
						</tr>									
					</table>
					</div>
					</td>
				</tr>	
			</table>
		</td>
	</tr>
	<!-- 接口02 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.2&nbsp;&nbsp;查找电话号码
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">findPhone(WeChatParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">查找电话号码</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">返回json字符串，包括姓名/电话和部门名称</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc2_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc2_1','paramDiv2_1')" style="cursor: pointer"><br>
					<div id="paramDiv2_1" style="display:none">
					<table id="List_ViewTable2_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>docSubject</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档标题，不允许为空</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>docContent</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档的富文本内容</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>fdTopicId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>主贴id，允许为空</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>fdPostId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>回复id，允许为空</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>modelId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>附件关联id，允许为空</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>modelName</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>附件模块，允许为空</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>attachmentForms</td>
							<td>链表（List&lt;AttachmentForm&gt;）</td>
							<td>无</td>
							<td>附件列表，允许为空</td>
						</tr>			
						<tr>
							<td align="center">8</td>
							<td>utype</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>查询类型 - 1部门  2个人，允许为空</td>
						</tr>
						<tr>
							<td align="center">9</td>
							<td>uparam</td>
							<td>字符串（Sring）</td>
							<td>无</td>
							<td>参数，允许为空</td>
						</tr>
						<tr>
							<td align="center">10</td>
							<td>unum</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>返回的控制条目</td>
						</tr>													
					</table></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口03 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.3&nbsp;&nbsp;查找待办
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">findSysNotifyTodo(WeChatNotifyParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">查待审/查待阅/查已审</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">json格式的待办信息</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc3_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc3_1','paramDiv3_1')" style="cursor: pointer"><br>
					<div id="paramDiv3_1" style="display:none">
					<table id="List_ViewTable3_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>pageno</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>页码</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>rowsize</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>每页多少条数据</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>oprType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待审/已审，如：doing/done</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>ordertype</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>顺序/逆序，如：up/down</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>orderBy</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>排序字段</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>userId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办所属人员</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>fdType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办类型</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>status</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档状态</td>
						</tr>
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm的说明</td>
					<td width="35%">未列出来的字段不需关注</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口04 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.4&nbsp;&nbsp;查询新闻数据
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">findSysNewsTodo(WeChatNotifyParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">查询最新的新闻数据</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">json格式的新闻信息</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc4_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc4_1','paramDiv4_1')" style="cursor: pointer"><br>
					<div id="paramDiv4_1" style="display:none">
					<table id="List_ViewTable4_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>pageno</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>页码</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>rowsize</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>每页多少条数据</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>oprType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待审/已审，如：doing/done</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>ordertype</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>顺序/逆序，如：up/down</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>orderBy</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>排序字段</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>userId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办所属人员</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>fdType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办类型</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>status</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档状态</td>
						</tr>
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm的说明</td>
					<td width="35%">未列出来的字段不需关注</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口05 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.5&nbsp;&nbsp;查询知识文档
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">findSysKnowledgeTodo(WeChatNotifyParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">查询查询知识文档</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">json格式的新闻信息</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc5_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc5_1','paramDiv5_1')" style="cursor: pointer"><br>
					<div id="paramDiv5_1" style="display:none">
					<table id="List_ViewTable5_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>pageno</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>页码</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>rowsize</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>每页多少条数据</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>oprType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待审/已审，如：doing/done</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>ordertype</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>顺序/逆序，如：up/down</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>orderBy</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>排序字段</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>userId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办所属人员</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>fdType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办类型</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>status</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档状态</td>
						</tr>
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm的说明</td>
					<td width="35%">未列出来的字段不需关注</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口06 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.6&nbsp;&nbsp;我的流程
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">myReviewList(WeChatNotifyParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">查询我的流程</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">json格式的流程信息</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc6_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc6_1','paramDiv6_1')" style="cursor: pointer"><br>
					<div id="paramDiv6_1" style="display:none">
					<table id="List_ViewTable6_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>pageno</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>页码</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>rowsize</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>每页多少条数据</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>oprType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待审/已审，如：doing/done</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>ordertype</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>顺序/逆序，如：up/down</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>orderBy</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>排序字段</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>userId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办所属人员</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>mydoc</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>是否查询自己的待办，为true时，必须跟userId字段配合使用，否则无效。字段值如：true/false,</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>fdType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>待办类型</td>
						</tr>
						<tr>
							<td align="center">9</td>
							<td>status</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>文档状态</td>
						</tr>
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm的说明</td>
					<td width="35%">未列出来的字段不需关注</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口07 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.7&nbsp;&nbsp;搜索
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">search4Wechat(SearchParamForm searchParamForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">全文搜索</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">json格式的流程信息</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc7_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc7_1','paramDiv7_1')" style="cursor: pointer"><br>
					<div id="paramDiv7_1" style="display:none">
					<table id="List_ViewTable7_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>pageno</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>页码</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>rowsize</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>每页多少条数据</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>queryString</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>查询关键字</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>modelName</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>查询的模块名</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>searchFields</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>查询字段</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>filterString</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>过滤搜索</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>facetFields</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>分页参数名</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>facetString</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>分页参数值</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>sortType</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>排序字段</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>isSearchByButton</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>搜索按钮,如：true/false</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>creator</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>创建者</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>status</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>状态</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>timeRange</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>时间段，如：day/week/month/year</td>
						</tr>
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm的说明</td>
					<td width="35%">未列出来的字段不需关注</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口08 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.8&nbsp;&nbsp;发送短信和邮件
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">receiveMessage(WeChatNotifyForm weChatNotifyForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">根据从lwe接收到的信息 判读是否需要发送短信和邮件</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">null</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc8_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc8_1','paramDiv8_1')" style="cursor: pointer"><br>
					<div id="paramDiv8_1" style="display:none">
					<table id="List_ViewTable8_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>link</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>链接地址</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>linkSubject</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>链接标题</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>subject</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>主题</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>content</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>内容</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>fdBundle</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>所属资源文件</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>replaceText</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>需替换的文字</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>personList</td>
							<td>链表（String）</td>
							<td>无</td>
							<td>接收者id,email,mobile,name 信息</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>resultList</td>
							<td>链表（String）</td>
							<td>无</td>
							<td>记录发送给每个微信客户端成功标志</td>
						</tr>
					</table></div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm的说明</td>
					<td width="35%">未列出来的字段不需关注</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口09 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.9&nbsp;&nbsp;配置维护
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">addWechatConfig(String param)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">提供对wechatConfig表操作的的webservice接口</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">null</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc9_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc9_1','paramDiv9_1')" style="cursor: pointer"><br>
					<div id="paramDiv9_1" style="display:none">
					<table id="List_ViewTable9_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>param</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>加密信息,解密后可以 获取对应的配置信息</td>
						</tr>
					</table></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口10 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.10&nbsp;&nbsp;对照表维护（企业号和EKP）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">insertWeChatConfig(WeChatConfigParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">对照表维护，没有新建，存在则更新</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">字符串，1/0</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc10_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc10_1','paramDiv10_1')" style="cursor: pointer"><br>
					<div id="paramDiv10_1" style="display:none">
					<table id="List_ViewTable10_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdLicense</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>License信息</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>fdEkpid</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>EKP的业务主键</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>fdOpenid</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>微信号</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>fdPushProcess</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>是否推送待办</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>fdPushRead</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>是否推送待阅</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>fdUrlAccess</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>是否链接授权微信</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>fdNickname</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>微信昵称</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>fdImage</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>微信图片</td>
						</tr>
						<tr>
							<td align="center">9</td>
							<td>fdRandom</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>用户随机串</td>
						</tr>
						<tr>
							<td align="center">10</td>
							<td>fdQyRandom</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>用户随机串--企业微信</td>
						</tr>
						<tr>
							<td align="center">11</td>
							<td>fdScene</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>用户二维码</td>
						</tr>
						<tr>
							<td align="center">12</td>
							<td>fdBindFlag</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>绑定标识</td>
						</tr>
						<tr>
							<td align="center">13</td>
							<td>fdAlterTime</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>修改时间</td>
						</tr>
					</table></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口11 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.11&nbsp;&nbsp;对照表维护（服务号和EKP）
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">insertWeChatConfigByWeiYun(WeChatConfigParamterForm webForm)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">对照表维护，没有新建，存在则更新</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">字符串，1/0</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc11_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc11_1','paramDiv11_1')" style="cursor: pointer"><br>
					<div id="paramDiv11_1" style="display:none">
					<table id="List_ViewTable11_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdLicense</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>License信息</td>
						</tr>
						<tr>
							<td align="center">2</td>
							<td>fdEkpid</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>EKP的业务主键</td>
						</tr>
						<tr>
							<td align="center">3</td>
							<td>fdOpenid</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>微信号</td>
						</tr>
						<tr>
							<td align="center">4</td>
							<td>fdPushProcess</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>是否推送待办</td>
						</tr>
						<tr>
							<td align="center">5</td>
							<td>fdPushRead</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>是否推送待阅</td>
						</tr>
						<tr>
							<td align="center">6</td>
							<td>fdUrlAccess</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>是否链接授权微信</td>
						</tr>
						<tr>
							<td align="center">7</td>
							<td>fdNickname</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>微信昵称</td>
						</tr>
						<tr>
							<td align="center">8</td>
							<td>fdImage</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>微信图片</td>
						</tr>
						<tr>
							<td align="center">9</td>
							<td>fdRandom</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>用户随机串</td>
						</tr>
						<tr>
							<td align="center">10</td>
							<td>fdQyRandom</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>用户随机串--企业微信</td>
						</tr>
						<tr>
							<td align="center">11</td>
							<td>fdScene</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>用户二维码</td>
						</tr>
						<tr>
							<td align="center">12</td>
							<td>fdBindFlag</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>绑定标识</td>
						</tr>
						<tr>
							<td align="center">13</td>
							<td>fdAlterTime</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>修改时间</td>
						</tr>
					</table></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 接口12 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod_tr(this);">&nbsp;&nbsp;1.12&nbsp;&nbsp;获取附件
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style="display: none">
		<td>
			<table class="tb_normal" width=95%>
				<tr>
					<td class="td_normal_title" width=15%>服务接口</td>
					<td width="85%">getAttachement(String fdId)</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>功能描述</td>
					<td width="85%">获取附件Base64的字节码</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>返回值</td>
					<td width="85%">字符串</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>接口参数webForm</td>
					<td width="85%"><img id="viewSrc12_1"
						src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0"
						onclick="expandMethod('viewSrc12_1','paramDiv12_1')" style="cursor: pointer"><br>
					<div id="paramDiv12_1" style="display:none">
					<table id="List_ViewTable12_1" class="tb_noborder">
						<tr>
							<sunbor:columnHead htmlTag="td">
								<td width="40pt">序号</td>
							    <td width="20%">属性名</td>
							    <td width="20%">类 型</td>
							    <td width="10%">缺省值</td>
							    <td width="50%">描 述</td>
							</sunbor:columnHead>
						</tr>
						<tr>
							<td align="center">1</td>
							<td>fdId</td>
							<td>字符串（String）</td>
							<td>无</td>
							<td>附件主键</td>
						</tr>
					</table></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>
<br><br><br><br>
<%@ include file="/resource/jsp/view_down.jsp"%>