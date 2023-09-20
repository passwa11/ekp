<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>

<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("optbar.js|list.js");
</script>
<script>
 function expandMethod(thisObj) {
	var isExpand=thisObj.getAttribute("isExpanded");
	if(isExpand==null)
		isExpand="0";
	var trObj=thisObj.parentNode;
	trObj=trObj.nextSibling;
	while(trObj!=null){
		if(trObj!=null && trObj.tagName=="TR"){
			break;
		}
		trObj = trObj.nextSibling;
	}
	var imgObj=thisObj.getElementsByTagName("IMG")[0];
	if(trObj.tagName.toLowerCase()=="tr"){
		if(isExpand=="0"){
			trObj.style.display="";
			thisObj.setAttribute("isExpanded","1");
			imgObj.setAttribute("src","${KMSS_Parameter_StylePath}icons/collapse.gif");
		}else{
			trObj.style.display="none";
			thisObj.setAttribute("isExpanded","0");
			imgObj.setAttribute("src","${KMSS_Parameter_StylePath}icons/expand.gif");
		}
	}
 }
</script>


<div id="optBarDiv"><input type="button"
	value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>

<p class="txttitle">知识仓库分类维护接口</p>

<center>
<div style="width: 95%;text-align: left;">
</div>
<br/>

<table border="0" width="95%">
	<tr>
		<td>
			<b>1、接口说明</b>
			<br>
			<br>
			<p>&nbsp;&nbsp;知识仓库分类维护接口</p>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.1&nbsp;&nbsp;新增分类接口
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style=" display:none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">接口url</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							/api/kms-knowledge/kmsKnowledgeCategoryRestService/addCategory
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							新增分类
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							POST
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							application/json
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td style="padding: 0px;"><div style="margin: 8px;">详细说明如下：</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" style="width: 30%" ><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>分类名称</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>templateType</td>
								<td>分类类型</td>
								<td>必填，取值为 1:文档知识库 2:维基知识库 3:维基加文档</td>
							</tr>
							<tr>
								<td>numberRuleId</td>
								<td>编号规则</td>
								<td>非必填，不填为默认编号规则</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>非必填, 为数字，非数值时默认为0</td>
							</tr>
							<tr>
								<td>fdParentId</td>
								<td>父类Id</td>
								<td>非必填</td>
							</tr>
							<tr>
								<td>fdDocCreator</td>
								<td>创建者</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>修改者</td>
								<td>非必填，为空默认为创建者</td>
							</tr>
							<tr>
								<td>authReaderIds</td>
								<td>可使用者</td>
								<td>非必填,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
							<tr>
								<td>authEditorIds</td>
								<td>可维护者</td>
								<td>非必填,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
							<tr>
								<td>authTmpReaderIds</td>
								<td>分类阅读者</td>
								<td>非必填,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
							<tr>
								<td>authTmpEditorIds</td>
								<td>分类编辑者</td>
								<td>非必填,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							{	<br/>
								&nbsp;&nbsp;"fdName":"知识分类", <br/>
								&nbsp;&nbsp;"fdOrder":"1",<br/>
								&nbsp;&nbsp;"fdParentId":"1728384e76aa3bb8d2f953b44619dc85",<br/>
								&nbsp;&nbsp;"authReaderIds":"1183b0b84ee4f581bba001c47a78b2d9",<br/>
								&nbsp;&nbsp;"authEditorIds":"1183b0b84ee4f581bba001c47a78b2d9;1728384e76aa3bb8d2f953b44619dc85",<br/>
								&nbsp;&nbsp;"fdDocCreator":"1183b0b84ee4f581bba001c47a78b2d9",<br/>
								&nbsp;&nbsp;"templateType":"1",<br/>
								&nbsp;&nbsp;"numberRuleId":"1183b0b84ee4f581bba001c47a78b2d9"<br/>
							}
						</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">响应说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							 响应返回json格式数据
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">成功响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{   <br/>
							    &nbsp;&nbsp;"code": "200",<br/>
							    &nbsp;&nbsp;"success": "success",<br/>
							    &nbsp;&nbsp;"data": {<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"fdId": "1729902546c84424be3b2d7449885067",<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"docCreatorId": "1183b0b84ee4f581bba001c47a78b2d9",<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"fdName": "新分类"<br/>
							    &nbsp;&nbsp;},<br/>
							    &nbsp;&nbsp;"msg": "操作成功！"<br/>
							}
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">失败响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{	<br/>
							    &nbsp;&nbsp;"code": "401",<br/>
							    &nbsp;&nbsp;"success": "false",<br/>
							    &nbsp;&nbsp;"data": [],<br/>
							    &nbsp;&nbsp;"msg": "fdParentId不存在，找不着这个分类！"<br/>
							}
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 2 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.2&nbsp;&nbsp;删除分类接口
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style=" display:none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">接口url</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							/api/kms-knowledge/kmsKnowledgeCategoryRestService/delCategory
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							删除文档
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							POST
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							application/json
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td style="padding: 0px;"><div style="margin: 8px;">详细说明如下：</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" style="width: 30%" ><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>文档Id</td>
								<td>必填,批量删除时请使用英文分号(;)隔开</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							{
								"fdId":"17298c90d5542f19f9feb22427c89a8c;1728384e76aa3bb8d2f953b44619dc81"
							}
						</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">响应说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							 响应返回json格式数据
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">成功响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{   <br/>
							    &nbsp;&nbsp;"code": "200",<br/>
							    &nbsp;&nbsp;"success": "success",<br/>
							    &nbsp;&nbsp;"data": [],<br/>
							    &nbsp;&nbsp;"msg": "操作成功！"<br/>
							}
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">失败响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{	<br/>
							    &nbsp;&nbsp;"code": "401",<br/>
							    &nbsp;&nbsp;"success": "false",<br/>
							    &nbsp;&nbsp;"data": [],<br/>
							    &nbsp;&nbsp;"msg": "请检查fdId是否正确！"<br/>
							}
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<!-- 3 -->
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.3&nbsp;&nbsp;更新分类接口
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style=" display:none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">接口url</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							/api/kms-knowledge/kmsKnowledgeCategoryRestService/updateCategory
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							更新文档
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							POST
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							application/json
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td style="padding: 0px;"><div style="margin: 8px;">详细说明如下：</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" style="width: 30%" ><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>分类Id</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>fdName</td>
								<td>分类名称</td>
								<td>非必填,为空则不更新</td>
							</tr>
							<tr>
								<td>templateType</td>
								<td>分类类型</td>
								<td>非必填,为空则不更新,取值为 1:文档知识库 2:维基知识库 3:维基加文档</td>
							</tr>
							<tr>
								<td>numberRuleId</td>
								<td>编号规则</td>
								<td>非必填，不填为默认编号规则</td>
							</tr>
							<tr>
								<td>fdOrder</td>
								<td>排序号</td>
								<td>非必填,为空则不更新, 为数字，非数值时默认为0</td>
							</tr>
							<tr>
								<td>fdParentId</td>
								<td>父类Id</td>
								<td>非必填,为空则不更新</td>
							</tr>
							<tr>
								<td>docAlterorId</td>
								<td>修改者</td>
								<td>非必填,为空则不更新</td>
							</tr>
							<tr>
								<td>authReaderIds</td>
								<td>可使用者</td>
								<td>非必填,为空则不更新,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
							<tr>
								<td>authEditorIds</td>
								<td>可维护者</td>
								<td>非必填,为空则不更新,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
							<tr>
								<td>authTmpReaderIds</td>
								<td>分类阅读者</td>
								<td>非必填,为空则不更新,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
							<tr>
								<td>authTmpEditorIds</td>
								<td>分类编辑者</td>
								<td>非必填,为空则不更新,设置多人员时使用英文逗号(;)将Id隔开</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							{	<br/>
								&nbsp;&nbsp;"fdId":"17298c90d5542f19f9feb22427c89a8c", <br/>
								&nbsp;&nbsp;"fdName":"知识分类", <br/>
								&nbsp;&nbsp;"fdOrder":"1",<br/>
								&nbsp;&nbsp;"fdParentId":"1728384e76aa3bb8d2f953b44619dc85",<br/>
								&nbsp;&nbsp;"authReaderIds":"1183b0b84ee4f581bba001c47a78b2d9",<br/>
								&nbsp;&nbsp;"authEditorIds":"1183b0b84ee4f581bba001c47a78b2d9;1728384e76aa3bb8d2f953b44619dc85",<br/>
								&nbsp;&nbsp;"fdDocCreator":"1183b0b84ee4f581bba001c47a78b2d9"<br/>
							}
						</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">响应说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							 响应返回json格式数据
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">成功响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{   <br/>
							    &nbsp;&nbsp;"code": "200",<br/>
							    &nbsp;&nbsp;"success": "success",<br/>
							    &nbsp;&nbsp;"data": {<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"fdId": "1729902546c84424be3b2d7449885067",<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"docCreatorId": "1183b0b84ee4f581bba001c47a78b2d9",<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"fdName": "新分类"<br/>
							    &nbsp;&nbsp;},<br/>
							    &nbsp;&nbsp;"msg": "操作成功！"<br/>
							}
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">失败响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{	<br/>
							    &nbsp;&nbsp;"code": "401",<br/>
							    &nbsp;&nbsp;"success": "false",<br/>
							    &nbsp;&nbsp;"data": [],<br/>
							    &nbsp;&nbsp;"msg": "fdParentId不存在，找不着这个分类！"<br/>
							}
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td style="cursor: pointer;font-size: 15px;" onclick="expandMethod(this);" id="dataInfo"><br/>&nbsp;&nbsp;1.4&nbsp;&nbsp;查询分类接口
			<img src="${KMSS_Parameter_StylePath}icons/expand.gif" border="0" align="bottom"/>
			</td>
	</tr>
	<tr style=" display:none;">
		<td>
			<table class="tb_normal" cellpadding="0" cellspacing="0"  style="width: 85%;margin-left: 40px;">
				<tr>
					<td class="td_normal_title" width="20%">接口url</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							/api/kms-knowledge/kmsKnowledgeCategoryRestService/queryCateMsgById
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">接口说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							查询分类信息
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求方式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							POST
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求格式</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							application/json
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求参数</td>
					<td style="padding: 0px;"><div style="margin: 8px;">详细说明如下：</div>
						<table class="tb_normal" width=100%>
							<tr class="tr_normal_title">
								<td align="center" style="width: 30%" ><b>字段名</b></td>
								<td align="center" style="width: 30%"><b>说明</b></td>
								<td align="center" style="width: 30%"><b>取值说明</b></td>
							</tr>
							<tr>
								<td>fdId</td>
								<td>文档Id</td>
								<td>必填</td>
							</tr>
							<tr>
								<td>showTmpl</td>
								<td>显示分类模板</td>
								<td>非必填，showTmpl=true时会显示当前分类模板结构</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">请求示例</td>
					<td style="padding: 0px;">
					<div style="margin: 8px;">
						<p>
							{	<br/>
								&nbsp;&nbsp;"fdId":"1729902546c84424be3b2d7449885067", <br/>
								&nbsp;&nbsp;"showTmpl":"true"<br/>
							}
						</p>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">响应说明</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							 响应返回json格式数据
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">成功响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{   <br/>
							    &nbsp;&nbsp;"code": "200",<br/>
							    &nbsp;&nbsp;"success": "success",<br/>
							    &nbsp;&nbsp;"data": {<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"fdId": "1729902546c84424be3b2d7449885067",<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"docCreatorId": "1183b0b84ee4f581bba001c47a78b2d9",<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"fdName": "新分类"<br/>
							    &nbsp;&nbsp;&nbsp;&nbsp;"wikiTmpl": <span style="color:#cc0000">[  //使用新增维基文档rest接口时，可直接复制此段查询结果到catelogs字段中添加相应的信息<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"content": "", // 目录内容<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"kemid": "", // 需关联的原子知识Id<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"authEditor": "1183b0b84ee4f581bba001c47a78b2d9;1729902546c84424be3b2d7449885067", // 目录可维护者，多值时英文(;)隔开<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"title": "2", // 目录标题，仅显示不可修改<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"order": "1", // 目录顺序，仅显示不可修改，若修改会导致维基内容与章节关系无法一一对应<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"children": [<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"content": "",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"kemid": "",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"title": "2.1",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"order": "2",<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"children": []<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br/>
								&nbsp;&nbsp;&nbsp;&nbsp;]<br/></span>
							    &nbsp;&nbsp;},<br/>
							    &nbsp;&nbsp;"msg": "操作成功！"<br/>
							}
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width="20%">失败响应报文</td>
					<td style="padding: 0px;">
						<div style="margin: 8px;">
							{	<br/>
							    &nbsp;&nbsp;"code": "401",<br/>
							    &nbsp;&nbsp;"success": "false",<br/>
							    &nbsp;&nbsp;"data": [],<br/>
							    &nbsp;&nbsp;"msg": "fdId不存在！"<br/>
							}
						</div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</center>
<%@ include file="/resource/jsp/view_down.jsp"%>