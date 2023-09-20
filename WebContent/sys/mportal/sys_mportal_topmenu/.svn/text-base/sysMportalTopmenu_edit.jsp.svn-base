<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" showQrcode="false">
	<template:replace name="title">
		个人首页顶部快捷方式设置
	</template:replace>
	<template:replace name="body">
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/mobile/css/themes/default/font-mui.css"></link>
		<style>
			.icon {
				  background-color: #1d9d74;
  				  color: #fff;
  				  cursor: pointer;
			}
			.icon .mui {
			  font-size: 36px;
			}
			@font-face {
				font-family: 'FontMui';
				src:url("<%=request.getContextPath()%>/sys/mobile/css/font/fontmui.woff");
				src:url('<%=request.getContextPath()%>/sys/mobile/css/font/fontmui.eot?#iefix');
			}
		</style>
		<script>
			seajs.use(['theme!form']);
			Com_IncludeFile('doclist.js|jquery.js|plugin.js');
		</script>
		<script>
			DocList_Info.push('__sysMportalTopmenu');
			$(document).ready(function() {
				$KMSSValidation();
			});
		</script>
		<div style="padding:20px 20px;">
			<center>
			<html:form
			action="/sys/mportal/sys_mportal_topmenu/sysMportalTopmenu.do">
			
			<table id="__sysMportalTopmenu" class="tb_normal" width="100%">
				<tr class="tr_normal_title">
					<td width="20px;">
						<span style="white-space:nowrap;"><bean:message key="page.serial"/></span>
					</td>
					<td width="50px">
						图标
					</td>
					<td width="200px">名称</td>
					<td>链接</td>
					<td width="110px" >
						<a href="javascript:;" class="com_btn_link"  
							onclick="selectLinkDialog();">
							系统引入
						</a>
						&nbsp
						<a href="javascript:;" class="com_btn_link" 
							onclick="DocList_AddRow('__sysMportalTopmenu');">
							自定义
						</a>
					</td>
				</tr>
				<%-- 模版行 --%>
				<tr style="display:none;" KMSS_IsReferRow="1">
					<td KMSS_IsRowIndex="1">
						!{index}
					</td>
					<td  align="center" valign="middle" class="icon">
							<div class="mui mui-approval" claz="mui-approval">
								<input name="topmenus[!{index}].fdIcon" 
									type="hidden" value="mui-approval"/>
							</div>
					</td>
					<td>
						<input type="hidden" name="topmenus[!{index}].fdId" >
						<input type="hidden" name="topmenus[!{index}].fdOrder" value="!{index}"/>
						<xform:text property="topmenus[!{index}].fdName" style="width:95%"  
							subject="名称" required="true" />
					</td>
					<td>
						<xform:text property="topmenus[!{index}].fdUrl" style="width:95%"   
							subject="链接" required="true" />
					</td>
					<td>
						
						<div style="text-align:center">
						<img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del" onclick="DocList_DeleteRow();refreshOrder();" style="cursor:pointer">&nbsp;&nbsp;
						<img src="${KMSS_Parameter_StylePath}icons/up.gif" alt="up" onclick="DocList_MoveRow(-1);refreshOrder();" style="cursor:pointer">&nbsp;&nbsp;
						<img src="${KMSS_Parameter_StylePath}icons/down.gif" alt="down" onclick="DocList_MoveRow(1);refreshOrder();" style="cursor:pointer">
						</div>
					</td>
				</tr>
				<%-- 内容行 --%>
				<c:forEach items="${sysMportalTopmenuAllForm.topmenus}" var="link" varStatus="vstatus">
				<tr KMSS_IsContentRow="1">
					<td>
						${vstatus.index + 1}
					</td>
					<td  align="center" valign="middle" class="icon">
							<div class="mui ${link.fdIcon}" claz="${link.fdIcon}">
								<input name="topmenus[${vstatus.index}].fdIcon" 
									type="hidden" value="${link.fdIcon}"/>
							</div>
					</td>
					<td>
						<input type="hidden" name="topmenus[${vstatus.index}].fdOrder" value="${link.fdOrder}"/>
						<html:hidden property="topmenus[${vstatus.index}].fdId"/>
						<xform:text property="topmenus[${vstatus.index}].fdName"  
							 required="true" style="width:95%" value="${link.fdName }"
							   subject="名称"/>
					</td>
					<td>
						<xform:text   property="topmenus[${vstatus.index}].fdUrl"   
						 	required="true" style="width:95%" value="${link.fdUrl }" 
							 subject="链接"/>
					</td>
					<td>
						<div style="text-align:center">
						<img src="${KMSS_Parameter_StylePath}icons/delete.gif" alt="del" 
							onclick="DocList_DeleteRow();refreshOrder();" style="cursor:pointer">&nbsp;&nbsp;
						<img src="${KMSS_Parameter_StylePath}icons/up.gif" alt="up" 
							onclick="DocList_MoveRow(-1);refreshOrder();" style="cursor:pointer">&nbsp;&nbsp;
						<img src="${KMSS_Parameter_StylePath}icons/down.gif" alt="down"
							 onclick="DocList_MoveRow(1);refreshOrder();" style="cursor:pointer">
						</div>
					</td>
				</tr>
				</c:forEach>
			</table>
			</html:form>
			<div style="margin-bottom: 10px;margin-top:25px">
	    		<ui:button text="${lfn:message('button.save')}" height="35" width="120" onclick="Com_Submit(document.sysMportalTopmenuAllForm, 'update');"></ui:button>
		    </div>
		    </center>
		</div>		    
		<script>
		function refreshOrder () {
			$("[name$='fdOrder']").each(function() {
				var nameText = $(this).attr("name");
				var order = nameText.replace(/[^0-9]/ig,"");
				if(order >= 0) {
					$(this).val(order);
				}
			});
		}
		function selectLinkDialog () {
			seajs
				.use(
					[ 'lui/dialog', 'lui/jquery' ],
					function(dialog) {
						dialog.iframe("/sys/mportal/sys_mportal_menu/sysMportalMenu_dialog.jsp","选择链接",
							function(val) {
								if (!val) {
									return;
								} else {
									for (var i = 0; i < val.length; i ++) {
										var _val = val[i];
										var rowData = {
												"topmenus[!{index}].fdName" : _val.name,
												"topmenus[!{index}].fdUrl" : _val.link
										};
										DocList_AddRow('__sysMportalTopmenu', null, rowData);
									}
									refreshOrder();
								}
							}, {
								width : 500,
								height : 450
							});
					});
		}
		seajs.use(
				[ 'lui/jquery', 'lui/dialog' ],
				function(jquery, dialog) {
					$('#__sysMportalTopmenu').on('click',
							function(evt) {
								var $target = $(evt.target);
								if ($target.hasClass('mui')) {
									icon($target);
								}
							});

					function icon($target) {
						var dialogUrl = "/sys/mportal/sys_mportal_menu/sysMportalMenu.do?method=icon&iconTypeRange=2"; //（iconTypeRange=2 表示弹出框中只显示字体图标页签）
						dialog.iframe(dialogUrl,"<bean:message key='sysMportalCard.select.icons' bundle='sys-mportal'/>",function(returnData) {
								if (!returnData){
									return;
								}
								var iconType = returnData.iconType; // 1、图片图标      2、字体图标     3、文字   
								var claz2 = iconType==2 ? returnData.className : "";
								
								var claz1 = $target.attr('claz');
								$target.removeClass(claz1);
								$target.attr('claz',claz2);
								$target.addClass(claz2);
								$target.find('input').val(claz2);

						}, {
							width : 600,
							height : 550
						});
					}
				});
		</script>
	</template:replace>
</template:include>
