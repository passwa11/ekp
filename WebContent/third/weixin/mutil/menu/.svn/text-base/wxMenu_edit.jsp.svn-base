<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	String urlpre = ResourceUtil.getKmssConfigString("kmss.urlPrefix");
	request.setAttribute("urlpre", urlpre);
%>
<template:include ref="default.edit" width="95%" sidebar="no">
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
				<c:choose>
					<c:when test="${ wxworkMenuForm.method_GET == 'add' }">
						<ui:button text="${lfn:message('button.save') }" order="2" onclick=" Com_Submit(document.wxworkMenuForm, 'save');">
						</ui:button>
					</c:when>
					<c:when test="${ wxworkMenuForm.method_GET == 'edit' }">					
						<ui:button text="${lfn:message('button.update') }" order="2" onclick=" Com_Submit(document.wxworkMenuForm, 'update');">
						</ui:button>	
						
					<ui:button text="${lfn:message('third-weixin-work:third.wx.menu.btn.savepublish') }" order="3" onclick="Com_Submit(document.wxworkMenuForm, 'publish');">
					</ui:button>

					</c:when>
				</c:choose>
		
				<ui:button text="${lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();">
				</ui:button>
		</ui:toolbar>
	</template:replace>	
	<template:replace name="content">
		<script>
		Com_IncludeFile("dialog.js|doclist.js|json2.js");
		</script>
			 
		<script>
		$(function(){
			var checkedAppId = '${checkedAppId}';
			if(checkedAppId!=""){
				$("#allAgents").val(checkedAppId);
				$("#allAgents").change();
				$("#allAgents").attr("disabled","disabled");  
			}
		});
		
		Com_AddEventListener(window, "load", function(){
			doSwitchType('1');
			doSwitchType('2');
			doSwitchType('3');
		});

			function doSwitchType(no){
				var submTypes = document.getElementsByName("subm"+no+"Type");
				var submlink = document.getElementById("subm"+no+"linkTr");
				var linksTable = document.getElementById("linksTable"+no);
				var _mHrefLink = document.getElementById("_mHrefLink"+no);
				
				if(submTypes[0].value=="view"){
					submlink.style.display="";
					linksTable.style.display="none";
					_mHrefLink.style.display="";
				}else{
					submlink.style.display="none";
					linksTable.style.display="";
					_mHrefLink.style.display="none";
				}

			}

		Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = submitFormEvent;
		function submitFormEvent(){
			var allAgentIds = document.getElementsByName("allAgentIds")[0].value;
			var currAgentId = document.getElementsByName("currAgentId")[0].value;
			var fdAgentId = document.getElementsByName("fdAgentId")[0].value;
			if(currAgentId!=""){
					if(fdAgentId!=currAgentId){
						var ids = allAgentIds.split(",");
						for(var i=0;i<ids.length;i++){
							if(fdAgentId==ids[i]){
								alert("应用'"+fdAgentId+"'已配置，不能新增，请修改");
								return false;
							}
						}
					}
			}else{
				var ids = allAgentIds.split(",");
				for(var i=0;i<ids.length;i++){
					if(fdAgentId==ids[i]){
						alert("应用'"+fdAgentId+"'已配置，不能新增，请修改");
						return false;
					}
				}
			}

			if(validateParam()){
				setParamProp();
			}else{
				return false;
			}
			return true;
		}

		function validateParam() {
			for(var i=1;i<=3;i++){
				var submName =  document.getElementsByName("subm"+i+"Name");
				var submType =  document.getElementsByName("subm"+i+"Type");
				var submLink =  document.getElementsByName("subm"+i+"Link");
				if(submName[0].value!=""){
					var len = getByteLen(submName[0].value);
					if(len>16){
						alert("子菜单标题，不超过16个字节");
						submName[0].focus();
						return false;
					}

					if(submType[0].value=="view"){
						if(submLink[0].value==""){
							alert("子菜单类型选择链接时对应的URL不能空");
							submLink[0].focus();
							return false;
						}else{
							var len = getByteLen(submLink[0].value);
							if(len>256){
								alert("菜单可打开链接，不超过256字节");
								submLink[0].focus();
								return false;
							}
						}
					}else{
						var lenm = DocList_TableInfo["linksTable"+i].lastIndex-1;
						for(var j=0; j<lenm; j++){
							var subFdName = document.getElementsByName("fdLinks"+i+"["+j+"].fdName");
							var subFdUrl = document.getElementsByName("fdLinks"+i+"["+j+"].fdUrl");
							if(subFdName.length>0){
								if(subFdName[0].value!=""){
									var len = getByteLen(subFdName[0].value);
									if(len>60){
										alert("子菜单项标题，不超过60个字节");
										submName[0].focus();
										return false;
									}

									if(subFdUrl[0].value==""){
										alert("子菜单项链接不能空");
										subFdUrl[0].focus();
										return false;
									}else{
										var len = getByteLen(subFdUrl[0].value);
										if(len>256){
											alert("菜单可打开链接，不超过256字节");
											subFdUrl[0].focus();
											return false;
										}
									}
								}
							}
						}
						if(lenm>5){
							alert("每个一级菜单下可创建最多 5 个二级菜单！");
							return false;
						}
					}

				}

			}

			return true;
		}

		function setParamProp(){
			var menu = {};
			menu["button"]=[];
			var n=0;
			for(var i=1;i<=3;i++){
				var submName =  document.getElementsByName("subm"+i+"Name");
				var submType =  document.getElementsByName("subm"+i+"Type");
				var submLink =  document.getElementsByName("subm"+i+"Link");
				if(submName[0].value!=""){
					var mb = {};
					mb["name"]=submName[0].value;

					if(submType[0].value=="view"){
						mb["type"]="view";
						mb["url"]=submLink[0].value;
					}else{
						var subms = [];
						var len = DocList_TableInfo["linksTable"+i].lastIndex-1;
						for(var j=0; j<len; j++){
							var subFdName = document.getElementsByName("fdLinks"+i+"["+j+"].fdName");
							if(subFdName[0].value!=""){
								var subFdUrl = document.getElementsByName("fdLinks"+i+"["+j+"].fdUrl");
								var subm = {};
								subm["type"]="view";
								subm["name"]=subFdName[0].value;
								subm["url"]=subFdUrl[0].value;
								subms[subms.length]=subm;
							}
						}
						mb["sub_button"]=subms;
					}
					menu["button"][n++]=mb;
				}
			}
			//alert(JSON.stringify(menu));
			document.getElementsByName("fdMenuJson")[0].value=JSON.stringify(menu);
		}

		function agentOnchange(el){
			document.getElementsByName("fdAgentId")[0].value=el.value;
			if(document.getElementsByName("fdAgentId")[0].value==""){
				document.getElementsByName("fdAgentName")[0].value="";
			}else{
				document.getElementsByName("fdAgentName")[0].value=el.options[el.selectedIndex].text;
			}
		}

		//选择模块
		function selectModule(idn,nn){
			var ide = document.getElementsByName(idn)[0];
			var nne = document.getElementsByName(nn)[0];
			var fdWxKey = $('input[name=fdWxKey]').val();
			console.log(fdWxKey);
			Dialog_List(false, ide, nne, null, "wxworkPdaModelSelectDialog&fdWxKey="+fdWxKey,afterModuleSelect,null,null,null,
					"<bean:message bundle='third-pda' key='pdaModuleConfigMain.moduleSelectDilog'/>");
		}

		function afterModuleSelect(dataObj){
			if(dataObj==null)
				return ;
			var rtnData = dataObj.GetHashMapArray();
			if(rtnData[0]==null)
				return;
		}

		function selectListModule(optTR,n){
				if(optTR==null)
					optTR = DocListFunc_GetParentByTagName("TR");
				var optTB = DocListFunc_GetParentByTagName("TABLE", optTR);
				var rowIndex = Com_ArrayGetIndex(optTB.rows, optTR);
				rowIndex = rowIndex-1;
				var ide = document.getElementsByName('fdLinks'+n+'['+rowIndex+'].fdUrl')[0];
				var nne = document.getElementsByName('fdLinks'+n+'['+rowIndex+'].fdName')[0];
				var fdWxKey = $('input[name=fdWxKey]').val();
				console.log(fdWxKey);

				Dialog_List(false, ide, nne, null, "wxworkPdaModelSelectDialog&fdWxKey="+fdWxKey,afterModuleSelect,null,null,null,
						"<bean:message bundle='third-pda' key='pdaModuleConfigMain.moduleSelectDilog'/>");
		}
		
		function getByteLen(str) {
			var url = '<c:url value="/third/wxwork/mutil/menu/wxworkMenuDefine.do?method=checkLen" />';
			var len = 0;
			$.ajax({
			   type: "POST",
			   url: url,
			   async:false,
			   dataType: "json",
			   data: "name="+str,
			   success: function(data){
					len = data.len;
			   }
			});
			return len;
		}  

		</script>
		<html:form action="/third/wxwork/mutil/menu/wxworkMenuDefine.do">
		<p class="txttitle">
			<bean:message bundle="third-weixin-mutil" key="third.wx.menu.title1"/>
		</p> 
		<center>
		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="third-weixin-mutil" key="third.wx.menu.agentId"/>
					<input type="hidden" name="allAgentIds" value="${allAgentIds}">
					<input type="hidden" name="currAgentId" value="${wxworkMenuForm.fdAgentId}">
				</td>
				<td width="35%">
					<c:if test="${wxworkMenuForm.method_GET == 'add' }">
						<xform:text property="fdAgentId" showStatus="readOnly" subject="${lfn:message('third-weixin-mutil:third.wx.menu.agentId') }" required="true" style="width:85%" />
					</c:if>
					<c:if test="${wxworkMenuForm.method_GET != 'add' }">
						<html:hidden property="fdAgentId"/>
						${wxworkMenuForm.fdAgentId}
					</c:if>
					
				</td> 
				<td class="td_normal_title" width=15%>
					<bean:message bundle="third-weixin-mutil" key="third.wx.menu.agentName"/>
				</td>
				<td width="35%">
					
					<c:if test="${wxworkMenuForm.method_GET == 'add' }">
						<html:hidden property="fdAgentName"/>
						<select id="allAgents" name="allAgent" onchange="agentOnchange(this);" subject="${lfn:message('third-weixin-mutil:third.wx.menu.agentName') }"  validate="required" >
						<option value=""><bean:message key="page.firstOption"/></option>
						<c:forEach var="agent" items="${agents}">    
							<option value="${agent.id}"  <c:if test="${wxworkMenuForm.fdAgentId eq agent.id}"> selected </c:if> >${agent.name}</option>
						</c:forEach> 
					</select>
					</c:if>
					<c:if test="${wxworkMenuForm.method_GET != 'add' }">
						${wxworkMenuForm.fdAgentName}
					</c:if>
					<!--xform:text property="fdAgentName" subject="${lfn:message('third-weixin-mutil:third.wx.menu.agentName') }"  required="true" style="width:85%" /-->
				</td> 
			</tr>

				<tr>
					<td colspan="4">
						<table class="tb_normal" width="100%">
						<tr>
							<td colspan="4">
							<span class="txtstrong">说明：微信菜单可创建最多 3 个一级菜单，每个一级菜单下可创建最多 5 个二级菜单</span>
							</td>
						</tr>

							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm1.name"/>
								</td>
								<td width="35%">
									<input name="subm1Name" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm1.name') }" class="inputsgl" value="${subm1Name}" type="text"  style="width:85%" />
									<span id="_mHrefLink1">
										<a onclick="selectModule('subm1Link','subm1Name');return false;" href=""><bean:message key="dialog.selectOther" /></a>
									</span>
								</td> 
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm1.type"/>
								</td>
								<td width="35%">
								<%
								String subm1Type = (String)request.getAttribute("subm1Type");
								%>
									<select name="subm1Type" onchange="doSwitchType('1')" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm1.type') }" >
										<option value="text" <%=("text".equals(subm1Type)?"selected":"")%>><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.text"/></option>
										<option value="view" <%=("view".equals(subm1Type)?"selected":"")%>><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.link"/></option>
									</select>
									<span class="txtstrong"><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.note"/></span>
								</td> 
							</tr>
							<tr id="subm1linkTr" style="display:none">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm1.link"/>
								</td>
								<td width="85%" colspan="3">
									<input name="subm1Link" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm1.link') }" class="inputsgl" value="${subm1Link}" type="text"  style="width:85%" />
								</td> 
							</tr>
						</table>
						<table id="linksTable1" class="tb_normal" width="100%">
							<col width="10px" align="center">
							<col width="260px" align="center">
							<col width="" align="center">
							<col width="130px" align="center">
							<tr class="tr_normal_title">
								<td>
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td><bean:message bundle="third-weixin-mutil" key="third.wx.menu.submenu.name"/></td>
								<td><bean:message bundle="third-weixin-mutil" key="third.wx.menu.submenu.url"/></td>
								<td>
									<a href="javascript:;" class="com_btn_link" onclick="DocList_AddRow('linksTable1');"><bean:message key="button.insert"/></a>
								</td>
							</tr>
							<%-- 模版行 --%>
							<tr style="display:none;" KMSS_IsReferRow="1">
								<td KMSS_IsRowIndex="1">
									!{index}
								</td>
								<td>
									<input name="fdLinks1[!{index}].fdName" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.name') }" class="inputsgl" value="" type="text"  style="width:85%" />
									<a onclick="selectListModule(this.parentNode.parentNode,1);return false;" href=""><bean:message key="dialog.selectOther" /></a>
								</td>
								<td>
									<input name="fdLinks1[!{index}].fdUrl" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.url') }" flag='fdUrl' class="inputsgl" value="" type="text"  style="width:95%" />
								</td>
								<td>
									<div style="text-align:center">
									<img src="/resource/style/default/icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							<%-- 内容行 --%>
							<c:forEach items="${fdLinks1}" var="link" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td>
									${vstatus.index + 1}
								</td>
								<td>
									<input name="fdLinks1[${vstatus.index}].fdName" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.name') }" class="inputsgl" value="${link['name']}" type="text"  style="width:85%" />
									<a onclick="selectListModule(this.parentNode.parentNode,1);return false;" href=""><bean:message key="dialog.selectOther" /></a>
								</td>
								<td>
									<input name="fdLinks1[${vstatus.index}].fdUrl" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.url') }" flag='fdUrl' class="inputsgl" value="${link['url']}" type="text"  style="width:95%" />
								</td>

								<td>
									<div style="text-align:center">
									<img src="/resource/style/default/icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							</c:forEach>						
						</table>
					</td>
				</tr>
	
					<tr>
					<td colspan="4">
						<table class="tb_normal" width="100%">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm2.name"/>
								</td>
								<td width="35%">
									<input name="subm2Name" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm2.name') }" class="inputsgl" value="${subm2Name}" type="text"  style="width:85%" />
									<span id="_mHrefLink2">
										<a onclick="selectModule('subm2Link','subm2Name');return false;" href=""><bean:message key="dialog.selectOther" /></a>
									</span>

								</td> 
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm2.type"/>
								</td>
								<td width="35%">
								<%
								String subm2Type = (String)request.getAttribute("subm2Type");
								%>
									<select name="subm2Type" onchange="doSwitchType('2')" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm2.type') }" >
										<option value="text" <%=("text".equals(subm2Type)?"selected":"")%>><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.text"/></option>
										<option value="view" <%=("view".equals(subm2Type)?"selected":"")%>><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.link"/></option>
									</select>
									<span class="txtstrong"><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.note"/></span>
								</td> 
							</tr>
							<tr id="subm2linkTr" style="display:none">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm2.link"/>
								</td>
								<td width="85%" colspan="3">
									<input name="subm2Link" subject="${lfn:message('third-weixin-work:third.wx.menu.subm2.link') }" class="inputsgl" value="${subm2Link}" type="text"  style="width:85%" />
								</td> 
							</tr>
						</table>
						<table id="linksTable2" class="tb_normal" width="100%">
							<col width="10px" align="center">
							<col width="260px" align="center">
							<col width="" align="center">
							<col width="130px" align="center">
							<tr class="tr_normal_title">
								<td>
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td><bean:message bundle="third-weixin-mutil" key="third.wx.menu.submenu.name"/></td>
								<td><bean:message bundle="third-weixin-mutil" key="third.wx.menu.submenu.url"/></td>
								<td>
									<a href="javascript:;" class="com_btn_link" onclick="DocList_AddRow('linksTable2');"><bean:message key="button.insert"/></a>
								</td>
							</tr>
							<%-- 模版行 --%>
							<tr style="display:none;" KMSS_IsReferRow="1">
								<td KMSS_IsRowIndex="1">
									!{index}
								</td>
								<td>
									<input name="fdLinks2[!{index}].fdName" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.name') }" class="inputsgl" value="" type="text"  style="width:85%" />
									<a onclick="selectListModule(this.parentNode.parentNode,2);return false;" href=""><bean:message key="dialog.selectOther" /></a>

								</td>
								<td>
									<input name="fdLinks2[!{index}].fdUrl" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.url') }" flag='fdUrl' class="inputsgl" value="" type="text"  style="width:95%" />
								</td>
								<td>
									<div style="text-align:center">
									<img src="/resource/style/default/icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							<%-- 内容行 --%>
							<c:forEach items="${fdLinks2}" var="link" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td>
									${vstatus.index + 1}
								</td>
								<td>
									<input name="fdLinks2[${vstatus.index}].fdName" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.name') }" class="inputsgl" value="${link['name']}" type="text"  style="width:85%" />
									<a onclick="selectListModule(this.parentNode.parentNode,2);return false;" href=""><bean:message key="dialog.selectOther" /></a>

								</td>
								<td>
									<input name="fdLinks2[${vstatus.index}].fdUrl" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.url') }" flag='fdUrl' class="inputsgl" value="${link['url']}" type="text"  style="width:95%" />
								</td>

								<td>
									<div style="text-align:center">
									<img src="/resource/style/default/icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							</c:forEach>	
					</table>
			<tr>
		
					<tr>
					<td colspan="4">
						<table class="tb_normal" width="100%">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm3.name"/>
								</td>
								<td width="35%">
									<input name="subm3Name" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm3.name') }" class="inputsgl" value="${subm3Name}" type="text"  style="width:85%" />
									<span id="_mHrefLink3">
										<a onclick="selectModule('subm3Link','subm3Name');return false;" href=""><bean:message key="dialog.selectOther" /></a>
									</span>

								</td> 
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm3.type"/>
								</td>
								<td width="35%">
								<%
								String subm3Type = (String)request.getAttribute("subm3Type");
								%>
									<select name="subm3Type" onchange="doSwitchType('3')" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm3.type') }" >
										<option value="text" <%=("text".equals(subm3Type)?"selected":"")%>><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.text"/></option>
										<option value="view" <%=("view".equals(subm3Type)?"selected":"")%>><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.link"/></option>
									</select>
									<span class="txtstrong"><bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm.type.note"/></span>
								</td> 
							</tr>
							<tr id="subm3linkTr" style="display:none">
								<td class="td_normal_title" width=15%>
									<bean:message bundle="third-weixin-mutil" key="third.wx.menu.subm3.link"/>
								</td>
								<td width="85%" colspan="3">
									<input name="subm3Link" subject="${lfn:message('third-weixin-mutil:third.wx.menu.subm3.link') }" class="inputsgl" value="${subm3Link}" type="text"  style="width:85%" />
								</td> 
							</tr>
						</table>
						<table id="linksTable3" class="tb_normal" width="100%">
							<col width="10px" align="center">
							<col width="260px" align="center">
							<col width="" align="center">
							<col width="130px" align="center">
							<tr class="tr_normal_title">
								<td>
									<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
								</td>
								<td><bean:message bundle="third-weixin-mutil" key="third.wx.menu.submenu.name"/></td>
								<td><bean:message bundle="third-weixin-mutil" key="third.wx.menu.submenu.url"/></td>
								<td>
									<a href="javascript:;" class="com_btn_link" onclick="DocList_AddRow('linksTable3');"><bean:message key="button.insert"/></a>
								</td>
							</tr>
							<%-- 模版行 --%>
							<tr style="display:none;" KMSS_IsReferRow="1">
								<td KMSS_IsRowIndex="1">
									!{index}
								</td>
								<td>
									<input name="fdLinks3[!{index}].fdName" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.name') }" class="inputsgl" value="" type="text"  style="width:85%" />
									<a onclick="selectListModule(this.parentNode.parentNode,3);return false;" href=""><bean:message key="dialog.selectOther" /></a>
								</td>
								<td>
									<input name="fdLinks3[!{index}].fdUrl" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.url') }" flag='fdUrl' class="inputsgl" value="" type="text"  style="width:95%" />
								</td>
								<td>
									<div style="text-align:center">
									<img src="/resource/style/default/icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							<%-- 内容行 --%>
							<c:forEach items="${fdLinks3}" var="link" varStatus="vstatus">
							<tr KMSS_IsContentRow="1">
								<td>
									${vstatus.index + 1}
								</td>
								<td>
									<input name="fdLinks3[${vstatus.index}].fdName" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.name') }" class="inputsgl" value="${link['name']}" type="text"  style="width:85%" />
									<a onclick="selectListModule(this.parentNode.parentNode,3);return false;" href=""><bean:message key="dialog.selectOther" /></a>

								</td>
								<td>
									<input name="fdLinks3[${vstatus.index}].fdUrl" subject="${lfn:message('third-weixin-mutil:third.wx.menu.submenu.url') }" flag='fdUrl' class="inputsgl" value="${link['url']}" type="text"  style="width:95%" />
								</td>

								<td>
									<div style="text-align:center">
									<img src="/resource/style/default/icons/delete.gif" alt="del" onclick="DocList_DeleteRow(this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/up.gif" alt="up" onclick="DocList_MoveRow(-1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">&nbsp;&nbsp;
									<img src="/resource/style/default/icons/down.gif" alt="down" onclick="DocList_MoveRow(1, this.parentNode.parentNode.parentNode);" style="cursor:pointer">
									</div>
								</td>
							</tr>
							</c:forEach>	
					</table>
			<tr>

		</table>
		<html:hidden property="fdWxKey" />
		<html:hidden property="fdMenuJson" />
		<script>Com_IncludeFile("doclist.js");</script>
		<script>DocList_Info.push('linksTable1');</script>
		<script>DocList_Info.push('linksTable2');</script>
		<script>DocList_Info.push('linksTable3');</script>

		</center>
		<html:hidden property="fdId"/>
		<html:hidden property="method_GET" />
		<script type="text/javascript">	
		Com_IncludeFile("validator.jsp|validation.js|plugin.js|validation.jsp|xform.js", null, "js");
		</script>		
		<script>
		$KMSSValidation(document.forms['wxworkMenuForm']);
		</script>
		</html:form>
		<br>
		<br>
	</template:replace>
</template:include>
