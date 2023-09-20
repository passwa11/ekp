<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.sys.organization.util.SysOrgEcoUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" showQrcode="false">
	<template:replace name="head">
		<script>
			Com_IncludeFile("doclist.js");
			seajs.use(["sys/mportal/sys_mportal_page/css/edit.css"]);
		</script>
		
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>
		
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" var-navwidth="95%">
			<c:choose>
				<c:when test="${ sysMportalCpageForm.method_GET == 'edit' }">
					<ui:button text="${ lfn:message('button.update') }" 
						onclick="Com_Submit(document.sysMportalCpageForm, 'update');">
					</ui:button>
				</c:when>
				<c:when test="${ sysMportalCpageForm.method_GET == 'add' }">	
					<ui:button text="${ lfn:message('button.save') }" 
						onclick="Com_Submit(document.sysMportalCpageForm, 'save');">
					</ui:button>
					<ui:button text="${ lfn:message('button.saveadd') }" 
						onclick="Com_Submit(document.sysMportalCpageForm, 'saveadd');"></ui:button>
				</c:when>
			</c:choose>
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav" style="height:40px;line-height:40px;">
			<ui:menu-item 
				text="${ lfn:message('home.home') }" 
				icon="lui_icon_s_home" 
				href="/index.jsp">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item1') }">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportalCard.nav.path.item2') }">
			</ui:menu-item>
			<ui:menu-item text="${lfn:message('sys-mportal:sysMportal.profile.maintain') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-mportal:sysMportal.profile.public') }">
			</ui:menu-item>
			<ui:menu-item text="${ lfn:message('sys-mportal:table.sysMportalPage') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
	<div class="lui_form_content" style="padding:10px 0">
	<html:form action="/sys/mportal/sys_mportal_cpage/sysMportalCpage.do">
		<p class="txttitle">
			<bean:message bundle="sys-mportal" key="table.sysMportalCpage"/>
		</p>
		<center>
		<table class="tb_normal" width=95%> 
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalCpage.fdName"/>
				</td>
				<td width="35%">
				    <xform:text property="fdName" style="width:85%" required="true"  validators="required maxLength(100)"
				     isLoadDataDict="false" />
				</td>
				<td class="td_normal_title" width=15%>
					${lfn:message("sys-mportal:sysMportalCpage.fdIcon") }
				</td>
				<td width="35%">
					<c:choose>
						<c:when test="${not empty sysMportalCpageForm.fdIcon}">
							<div class="mui ${sysMportalCpageForm.fdIcon}" claz="${sysMportalCpageForm.fdIcon}">
								<c:if test="${fn:indexOf(sysMportalCpageForm.fdIcon, 'mui')<0}">
									${sysMportalCpageForm.fdIcon}
								</c:if>
							</div>
							<html:hidden property="fdIcon" value="${sysMportalCpageForm.fdIcon}"/>
							<html:hidden property="fdImg" value=""/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${not empty sysMportalCpageForm.fdImg}">
									<div class="mui" style="background: url('${LUI_ContextPath}${sysMportalCpageForm.fdImg}') no-repeat center;background-size: contain">
									</div>
									<html:hidden property="fdIcon" value=""/>
									<html:hidden property="fdImg" value="${sysMportalCpageForm.fdImg}"/>
								</c:when>
								<c:otherwise>
									<div class="mui mui-approval" claz="mui-approval">
									</div>
									<html:hidden property="fdIcon" value="mui-approval"/>
									<html:hidden property="fdImg" value=""/>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalCpage.fdOrder"/>
				</td>
				<td width="35%">
					<xform:text property="fdOrder" style="width:85%" />
				</td>
				<td class="td_normal_title" width=15%>
					<bean:message bundle="sys-mportal" key="sysMportalCpage.fdEnabled"/>
				</td>
				<td width="35%">
					<xform:radio property="fdEnabled">
						<xform:enumsDataSource enumsType="common_yesno" />
					</xform:radio>
				</td>
	   	 </tr>
	   	 <!-- 类型 -->
	   	 <tr>
	   	 	<% if (StringUtil.isNull(ResourceUtil.getKmssConfigString("kmss.lang.support"))) { 
						pageContext.setAttribute("colspan", "colspan='3'");
			} %>
	   	 	<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalPage.fdType"/>
			</td>
			<td width="35%" ${colspan}>
					<xform:radio property="fdType"
								 validators="required" required="true" onValueChange="typeChange(this.value);">
						<xform:enumsDataSource enumsType="sysMportalPage_type" />
					</xform:radio>
				
			</td>
	   	 </tr>
	   	<tr id="__mportlet_link" style="${sysMportalCpageForm.fdType == '2' ? '' :'display:none;' }">
	   		<td class="td_normal_title" width=15%>
				${ lfn:message('sys-mportal:sysMportalCpage.fdType.url') }
			</td>
			<td>				
				<xform:text property="fdUrl" style="width:75%;"></xform:text>
			</td>
			<td class="td_normal_title" width=15%>
				${ lfn:message('sys-mportal:sysMportalCpage.fdUrlOpenType') }
			<td >
				<xform:radio property="fdUrlOpenType"
								 validators="required" required="true">
						<xform:enumsDataSource enumsType="sysMportalCpage_fdUrlOpenType" />
					</xform:radio>				
			</td>
					
				
	   	</tr>
		<tr id="__mportlet_cards" style="${sysMportalCpageForm.fdType == '2' || sysMportalCpageForm.fdType == '3' ? 'display:none;' :'' }">
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalPage.cards"/>
			</td>
			<td colspan="3" width="85%">
				<table id="TABLE_DocList" class="tb_normal" width=100%>
					<tr> 
						<td align="center" class="td_normal_title">
							${ lfn:message('sys-mportal:sysMportalPageCard.fdName') }
						</td> 
						<td align="center" class="td_normal_title">
							<bean:message key="sysMportal.peofile.card.border" bundle="sys-mportal"/>
						</td> 
						<td width="15%" align="center" class="td_normal_title">
							<img src="../../../resource/style/default/icons/add.gif" alt="add" 
								onclick="selectPage();" style="cursor:pointer">
						</td>
					</tr>
					<%-- 模版行 --%>
					<tr style="display:none;" KMSS_IsReferRow="1">
						<td>
							<input  type="hidden" 
									name="cards[!{index}].fdId" />
							<input  type="hidden" 
									name="cards[!{index}].sysMportalCpageId" 
									value="" />
							<input type="hidden" 
									name="cards[!{index}].sysMportalCardId" 
									value="" />
							<xform:text 
							    property="cards[!{index}].fdName" 
								style="width:65%" 
								subject="${lfn:message('sys-mportal:sysMportalPageCard.fdName') }"
								required="true" 
								validators="required maxLength(100)"/>
								<span class="lui_oname"></span>
						</td>
						
						<td align="center">
							<label>
								<input 
									type="radio" 
									name="cards[!{index}].fdMargin" 
									value="true" checked="checked"><bean:message key="sysMportal.peofile.card.border.yes" bundle="sys-mportal"/>
							</label>
							<label>
								<input 
									type="radio" 
									name="cards[!{index}].fdMargin" 
									value="false"><bean:message key="sysMportal.peofile.card.border.no" bundle="sys-mportal"/>
							</label>
						</td>
						<td align="center">
							<img src="../../../resource/style/default/icons/delete.gif" alt="del" 
								onclick="DocList_DeleteRow();" 
								style="cursor:pointer">&nbsp;&nbsp;
							<img src="../../../resource/style/default/icons/up.gif" 
								alt="up"
								onclick="DocList_MoveRow(-1);" 
								style="cursor:pointer">&nbsp;&nbsp;
							<img src="../../../resource/style/default/icons/down.gif" alt="down" 
								onclick="DocList_MoveRow(1);" style="cursor:pointer">
						</td>
					</tr>
					<%--内容行  --%>
					<c:forEach items="${sysMportalCpageForm.cards}" var="card" varStatus="vstatus">
						<tr KMSS_IsContentRow="1">
							<td width="60%">
								<input  type="hidden" 
									name="cards[${vstatus.index}].fdId" value="${card.fdId }" />
								<input  type="hidden" 
												name="cards[${vstatus.index}].sysMportalCpageId" 
												value="${ card.sysMportalCpageId }" />
								<input type="hidden" 
												name="cards[${vstatus.index}].sysMportalCardId" 
												value="${ card.sysMportalCardId }" />
								<xform:text 
								    property="cards[${vstatus.index}].fdName" 
									style="width:65%" 
									subject="${lfn:message('sys-mportal:sysMportalCpageCard.fdName')}"
									required="true" 
									validators="required maxLength(100)"/>
								<span class="lui_oname">[<c:out value="${card.sysMportalCardName}" />]</span>	
							</td>
							
							<td width="20%" align="center">
								<label>
									<input 
										type="radio"
										name="cards[${vstatus.index}].fdMargin" 
										value="true" <c:if test="${card.fdMargin != false }"> checked </c:if> />${lfn:message('message.yes')}
								</label>
								
								<label>
									<input 
										type="radio"
										name="cards[${vstatus.index}].fdMargin" 
										value="false" <c:if test="${card.fdMargin == false }"> checked </c:if> />${lfn:message('message.no')}
								</label>
							</td>
							<td width="15%" align="center">
								<img src="../../../resource/style/default/icons/delete.gif" alt="del" 
								 	onclick="DocList_DeleteRow();" 
								 	style="cursor:pointer">&nbsp;&nbsp;
							  	<img src="../../../resource/style/default/icons/up.gif" 
								    alt="up"
									onclick="DocList_MoveRow(-1);" 
									style="cursor:pointer">&nbsp;&nbsp;
								<img src="../../../resource/style/default/icons/down.gif" alt="down" 
									onclick="DocList_MoveRow(1);"
										 style="cursor:pointer">
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>		
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalPage.docCreator"/>
			</td><td width="35%">
				<xform:address propertyId="docCreatorId" propertyName="docCreatorName" 
				orgType="ORG_TYPE_PERSON" showStatus="view" />
			</td>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="sys-mportal" key="sysMportalPage.docCreateTime"/>
			</td>
			<td width="35%">
				<xform:datetime property="docCreateTime" showStatus="view" />
			</td>
		 </tr>
		</table>
	</center>
	<html:hidden property="docCreatorId"/>
	<html:hidden property="docCreateTime"/>
	<html:hidden property="fdId" />
	<html:hidden property="method_GET" />
	<script>
		$KMSSValidation();
		function selectLogo(){
			seajs.use(['lui/dialog'],function(dialog){
				var fdLogo = $('[name="fdLogo"]').val(),
					selectUrl = '/sys/mportal/sys_mportal_logo/sysMportalLogo.do?method=select&logo=' + fdLogo;
				dialog.iframe(selectUrl,'<bean:message key="sysMportal.profile.logo.select" bundle="sys-mportal"/>',function(obj){
					if(!obj) {
						return;
					}
					var path = '${LUI_ContextPath}'+obj.src,
						img = $('<img></img>').attr('src',path);
					var imgNode = $("<div class='fdLogoImgBg'></div>").append(img);
					$('.fdLogoImg').html(imgNode);
					$('[name="fdLogo"]').val(obj.src);
				},{"width":650,"height":550});
			});
		}
		function selectPage(){
			seajs.use(['lui/dialog'],function(dialog){
				dialog.iframe('/sys/mportal/sys_mportal_card/sysMportalCard_dialog.jsp',
						"${ lfn:message('sys-mportal:sysMportalCard.select') }",function(val) {
					if(val != null && val != false && val.length && val.length > 0){
						addCard(val);
					}
				},{"width":650,"height":550}); 
			});
		}
		
		//  选择图标
		seajs.use([ 'lui/jquery', 'lui/dialog' ],function($, dialog) {
                $('.mui').on('click',function(evt) {
                      var $target = $(evt.target);
                      var dialogUrl = "/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon&iconTypeRange=2,3"; //（iconTypeRange=2,3 表示弹出框中只显示字体图标和文字选择页签）
					  dialog.iframe(dialogUrl,"${lfn:message('sys-mportal:sysMportalCard.select.icons')}",function(returnData) {
							if (!returnData){
								return;
							}
							var iconType = returnData.iconType; // 1、图片图标      2、字体图标     3、文字  
	                    	var claz2 = iconType==2 ? returnData.className : "";
	                    	var text = iconType==3 ? returnData.text : "";
						  	var type = returnData.type;
						  	var url = returnData.url;
							var claz1 = $target.attr('claz');
							$target.text('');
							$target.removeClass(claz1);
							$target.removeClass("mui-approval");
							  if(type == 2){ //素材库
								  var tUrl = url;
								  if(tUrl.indexOf("/") == 0){
									  tUrl = tUrl.substring(1);
								  }
								  tUrl = Com_Parameter.ContextPath + tUrl;
								  $target.css({
									  "background": "url('"+tUrl+"') no-repeat center",
									  "background-size": "contain"
								  })
								  $target.removeClass(claz2);
								  $target.parent().find("input[type='hidden'][name='fdIcon']").val("");
								  $target.parent().find("input[type='hidden'][name='fdImg']").val(url);
							  }else {
								  if (iconType == 2) {  // 字体图标

									  $target.addClass(claz2);
									  $target.attr('claz', claz2);

								  } else if (iconType == 3) {  // 文字

									  $target.text(text);
									  $target.attr('claz', text);

								  }
								  $target.removeAttr("style");
								  // 赋值图标隐藏hidden
								  $target.parent().find("input[type='hidden'][name='fdIcon']").val((iconType == 3) ? text : claz2);
								  $target.parent().find("input[type='hidden'][name='fdImg']").val("");
							  }
					},
					{
						width : 600,
						height : 550
					});
				});

		});

		function addCard(data) {

			for (var i = 0; i < data.length; i++) {
				var row = data[i];
				var rowData = {
					'cards[!{index}].sysMportalCpageId' : '${sysMportalCpageForm.fdId}',
					'cards[!{index}].sysMportalCardId' : row.id,
					'cards[!{index}].fdName' : row.name
				};
				var newRow = DocList_AddRow('TABLE_DocList', null, rowData);
				LUI.$(newRow).find(".lui_oname").text("[" + row.name + "]");
			}
		}

		function typeChange(value) {

			seajs.use([ 'lui/jquery' ], function($) {

				if (value == "1") {
					$("#__mportlet_link").hide();
					$("#__mportlet_cards").show();
				} else if (value == "2") {
					$("#__mportlet_link").show();
					$("#__mportlet_cards").hide();
				} else if (value == "3") {
					$("#__mportlet_link").hide();
					$("#__mportlet_cards").hide();
				}
			});
		}
	</script>
	</html:form>
	</div>
	</template:replace>
</template:include>