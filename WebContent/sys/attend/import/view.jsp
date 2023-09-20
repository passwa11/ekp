<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.attend.service.ISysAttendCategoryService,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.StringUtil" %>
<%@page import="com.landray.kmss.sys.attend.model.SysAttendCategory,java.util.List,com.landray.kmss.util.DateUtil,java.util.Date,java.net.URLEncoder" %>
<style>
	.lui_listview_columntable_table tr input[type=checkbox] {
	  margin-left: 7px;
	}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/sys/attend/resource/css/import_view.css?s_cache=${LUI_Cache}"></link>
<script type="text/javascript">
	Com_IncludeFile('calendar.js')
	seajs.use(['lui/topic','lui/dialog','lang!sys-attend'],function(topic,dialog,flnM){
		topic.subscribe('sys.attend.category.load.complete',function(categories){
			var button = LUI('sysAttendButton_${JsParam.fdModelId}');
			if(categories && categories.length>0){
				$('#sysAttendView_${JsParam.fdModelId}').show();
				$('#sysAttendView_${JsParam.fdModelId}_nodata').hide();
				if(button){
					button.setVisible(true);
				}
			}else{
				$('#sysAttendView_${JsParam.fdModelId}').hide();
				$('#sysAttendView_${JsParam.fdModelId}_nodata').show();
				if(button)
					button.setVisible(true);
			}
		});
		window.onBtnClick = function(fdCategoryId,method,evt){
			if(method=='del'){
				delCategory(fdCategoryId,evt);
			}else if(method=='finish'){
				finishCategory(fdCategoryId);
			}else if(method=='edit'){
				editCategory(fdCategoryId);
			}
		};
		window.delCategory = function(fdCategoryId,evt){
			var statusNode = $(evt).parents('.lui_attendCategoryTitle').find('.lui_left .lui_attendCategoryStatus');
			if(statusNode && statusNode.hasClass('doing')){
				dialog.failure(flnM['sysAttend.alert.topic.1']);
				return ;
			}
			dialog.confirm(flnM['sysAttend.alert.topic.2'],function(value){
				if(value==true){
					var del_load = dialog.loading();
					$.get('${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=delete&${JsParam.fdModelName}&fdModelId=${JsParam.fdId}',{fdId:fdCategoryId},function(data){
						if(del_load!=null){
							del_load.hide();
							location.reload();
						}
						dialog.result(data);
					},'json');
				}
			});
		};
		window.finishCategory = function(fdCategoryId){
			dialog.confirm(flnM['sysAttend.alert.topic.3'],function(value){
				if(value==true){
					var del_load = dialog.loading();
					$.get('${LUI_ContextPath}/sys/attend/sys_attend_category/sysAttendCategory.do?method=updateStatus&${JsParam.fdModelName}&fdModelId=${JsParam.fdId}',{fdId:fdCategoryId},function(data){
						if(del_load!=null){
							del_load.hide();
							location.reload();
						}
						dialog.result(data);
					},'json');
				}
			});
		};
		window.editCategory = function(fdCategoryId){
			var url = Com_Parameter.ContextPath + 'sys/attend/sys_attend_category/sysAttendCategory.do?method=edit';
			url += '&fdModelName=${JsParam.fdModelName}';
			url += '&fdModelId=${JsParam.fdId}';
			url += '&fdId=' + fdCategoryId;
			window.open(url);

		};
		// 导出签到记录
		window.exportExcel = function(fdCategoryId, fdCategoryName){
			_exportExcel('${LUI_ContextPath}/sys/attend/sys_attend_main/sysAttendMain.do?method=exportExtendExcel', {
				categoryId : fdCategoryId,
				categoryName : fdCategoryName,
			})
		};
		var _exportExcel = function(url, params) {
			var form = document.createElement("form");
			form.style.display = 'none';
			form.action = url;
			form.method = "post";
			document.body.appendChild(form);
			for ( var key in params) {
				var input = document.createElement("input");
				input.type = "hidden";
				input.name = key;
				input.value = params[key];
				form.appendChild(input);
			}
			form.submit();
			form.remove();
		}
		// 补签
		window.addPatch = function(mainId, cateId){
			window.open('${LUI_ContextPath}/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=add&mainId=' + mainId + '&cateId=' + cateId);
		};
		// 批量补签
		window.addPatchAll = function(cateId){
			var values = [];
			$("#sysAttendNotSign_${JsParam.fdModelId}_"+cateId+" input[name='List_Selected']:checked").each(function(){
					values.push($(this).val());
				});
			if(values.length==0){
				dialog.alert('<bean:message key="page.noSelect"/>');
				return;
			}
			window.open('${LUI_ContextPath}/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=add&' + $.param({'mainId':values, 'cateId':cateId},true));
		};
	});
	window.__fdModelId4sysAttend__ = '${JsParam.fdModelId}';
	// 会议发起签到事项
	window.openAttendRule = function(fdModelName,modelId){
		var url = Com_Parameter.ContextPath + 'sys/attend/sys_attend_category/sysAttendCategory.do?method=add';
		url += '&fdModelName=' + fdModelName;
		url += '&fdModelId=' + modelId;
		window.open(url);
	};
	// 切换签到列表
	window.switchAttendListview = function(fdStatus,dom,categoryId){
		var notSignList = $('#sysAttendNotSign_${JsParam.fdModelId}'+"_"+categoryId);
		var signedList = $('#sysAttendSigned_${JsParam.fdModelId}'+"_"+categoryId);
		var notStatus = $('#sysAttendSignedStatus_${JsParam.fdModelId}'+"_"+categoryId);
		if(fdStatus == '0'){
			notSignList.show();
			signedList.hide();
			notStatus.show();
			LUI('sysAttendNotSignListview_${JsParam.fdModelId}'+"_"+categoryId).source.get();
		} else if(fdStatus == '1'){
			notSignList.hide();
			signedList.show();
			notStatus.hide();
			LUI('sysAttendSignedListview_${JsParam.fdModelId}'+"_"+categoryId).source.get();
		}
		$('.lui_attendListview_selectItem').removeClass('selected');
		$(dom).addClass('selected');
	};
	// 展开收起签到
	window.onToggleClick = function(value,evt){
		if($(evt).hasClass("up")){
			$(evt).removeClass('up').addClass('down');
		}else{
			$(evt).removeClass('down').addClass('up');
		}
		$("#"+value).toggle();
	};
	// 打开签到投影
	window.onOpenProjectionPage = function(fdModelId,fdCategoryId,fdQRCodeUrl){
		var url = Com_Parameter.ContextPath + "sys/attend/import/projection.jsp?";
		url += 'fdCategoryId=' + fdCategoryId;
		url += '&fdModelId=' + fdModelId;
		url += '&fdQRCodeUrl=' + encodeURIComponent(fdQRCodeUrl);
		window.open(url);
	};
	
	
</script>
<%-- 显示发起签到按钮 --%>
<c:if test="${JsParam.isShowBtn=='true'}">
	<ui:button id="sysAttendButton_${JsParam.fdModelId}" order="1" parentId="toolbar" text="${ lfn:message('sys-attend:sysAttendCategory.importView.openAttendRule') }" 
		onclick="openAttendRule('${JsParam.fdModelName}','${JsParam.fdModelId}');">
	</ui:button>
</c:if>
<%-- 展开签到 --%>
<c:set var="expand" value="false"></c:set>
<c:if test="${JsParam.isExpandTab=='true'}">
	<c:set var="expand" value="true"></c:set>
</c:if>
<%-- 展开哪个签到 --%>
<c:set var="expandCateId" value="${JsParam.expandCateId}"></c:set>
<c:set var="order" value="${empty param.order ? '10' : param.order}"/>
<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
<ui:content title="${ lfn:message('sys-attend:sysAttendCategory.importView.signDetail') }" expand="${expand}" id="${JsParam.fdModelId}" cfg-order="${order}" cfg-disable="${disable}">
	<c:if test="${expand=='true'}">
		<ui:event event="show">
			 var id = '${JsParam.fdModelId}';
			 location.href = "#" + id;
	     </ui:event>
     </c:if>
	<div id='sysAttendView_${JsParam.fdModelId}'>
		<%
			// 获取签到组
			String fdAppId = request.getParameter("fdModelId");
			ISysAttendCategoryService categoryService = ((ISysAttendCategoryService)SpringBeanUtil.getBean("sysAttendCategoryService"));
			List<SysAttendCategory> cateList = categoryService.findCategorysByAppId(fdAppId);
			request.setAttribute("_cateList", cateList);
		%>
		
		<c:forEach var="category" items="${_cateList }" varStatus="status">
			<div class="lui_category_item">
				<%-- 标题 --%>
				<div class="lui_attendCategoryTitle" id="sysAttendView_title_${JsParam.fdModelId}_${category.fdId }">
					<div class="lui_left">
						<span class="lui_attendCategoryName">${category.fdName }</span>
						<%
						SysAttendCategory _cate = (SysAttendCategory)pageContext.getAttribute("category");
						pageContext.setAttribute("__fdStartTime", DateUtil.convertDateToString(_cate.getFdStartTime(),DateUtil.TYPE_TIME,null));
						pageContext.setAttribute("__fdEndTime", DateUtil.convertDateToString(_cate.getFdEndTime(),DateUtil.TYPE_TIME,null));
						pageContext.setAttribute("__fdQRCodeUrl", StringUtil.formatUrl("/resource/sys/attend/sysAttendAnym.do?method=scanToSign&categoryId=" + _cate.getFdId() + "&qrCodeTime=" + _cate.getFdQRCodeTime()));
						pageContext.setAttribute("isStatSignReader", categoryService.isStatSignReader(_cate));
						%>
						<span class="lui_attendCategoryTime">${ lfn:message('sys-attend:sysAttendMain.docCreateTime') }:${__fdStartTime }-${__fdEndTime }</span>
						<span class="lui_attendCategoryStatus"></span>
					</div>
					<div class="lui_right">
						<c:if test="${category.fdStatus!=2 }">
							<kmss:auth requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=edit&fdId=${category.fdId}" requestMethod="GET">
								<span class="lui_attendBtnEdit lui_icon_s_icon_pencil" onclick="onBtnClick('${category.fdId }','edit',this,'${JsParam.fdId}')">${ lfn:message('sys-attend:sysAttendCategory.edit') }</span>
							</kmss:auth>
							<kmss:auth requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=updateStatus&fdId=${category.fdId }" requestMethod="GET">
								<span class="lui_attendBtnFinish lui_icon_s_icon_off" onclick="onBtnClick('${category.fdId }','finish',this)">${ lfn:message('sys-attend:sysAttendCategory.close') }</span>
							</kmss:auth>
						</c:if>
						<kmss:auth requestURL="/sys/attend/sys_attend_category/sysAttendCategory.do?method=delete&fdId=${category.fdId }" requestMethod="GET">
							<span class="lui_attendBtnDel lui_icon_s_icon_trash" onclick="onBtnClick('${category.fdId }','del',this)">${lfn:message('button.delete')}</span>
						</kmss:auth>	
						<span class="lui_attendCategoryIcon <c:if test="${status.index !=0 && empty expandCateId || category.fdId!=expandCateId && not empty expandCateId}">down</c:if><c:if test="${status.index ==0 && empty expandCateId || category.fdId==expandCateId && not empty expandCateId}">up</c:if>" onclick="onToggleClick('sysAttendView_${JsParam.fdModelId}_${category.fdId }',this)">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
					</div>
				</div>
				<%-- 内容 --%>
				<div id="sysAttendView_${JsParam.fdModelId}_${category.fdId }" style="<c:if test="${status.index !=0 && empty expandCateId || category.fdId!=expandCateId && not empty expandCateId}">display:none;</c:if>">
					<%-- 签到投影 --%>
					<div class="lui_sysAttend_projection" onclick="onOpenProjectionPage('${JsParam.fdModelId}','${category.fdId }','${__fdQRCodeUrl }')">
						<span>${ lfn:message('sys-attend:sysAttendMain.projection.title') }</span>
					</div>
					<c:if test="${isStatSignReader}">
					<%-- 签到记录列表选择 --%>
					<div class='lui_attendListview_select'>
						<span class='lui_attendListview_selectItem selected' 
							onclick="switchAttendListview('0',this,'${category.fdId }')">${ lfn:message('sys-attend:sysAttendCategory.importView.missed') }</span>
						<span class='lui_attendListview_selectSep'></span>
						<span class='lui_attendListview_selectItem'
							onclick="switchAttendListview('1',this,'${category.fdId }')">${ lfn:message('sys-attend:sysAttendCategory.importView.signed') }</span>
						<ui:button onclick="exportExcel('${category.fdId }','${category.fdName }')" text="${ lfn:message('button.export') }" style="float: right;margin-right:15px;"></ui:button>
						<kmss:auth requestURL="/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=add&cateId=${category.fdId}">
							<ui:button id="sysAttendSignedStatus_${JsParam.fdModelId}_${category.fdId }" onclick="addPatchAll('${category.fdId }')" text="${ lfn:message('sys-attend:sysAttendSignPatch.addPatchAll') }" style="float: right;margin-right:15px;"></ui:button>
						</kmss:auth>
					</div>
					<%-- 未签到列表 --%>
					<div id="sysAttendNotSign_${JsParam.fdModelId}_${category.fdId }">
					<list:listview id="sysAttendNotSignListview_${JsParam.fdModelId}_${category.fdId }" channel="sysAttendNotSignListview_${JsParam.fdModelId}_${category.fdId }">
					 	<ui:source type="AjaxJson">
							{"url":"/sys/attend/sys_attend_main/sysAttendMain.do?method=list&categoryType=custom&rowsize=15&operType=0&appId=${JsParam.fdModelId}&fdCategoryId=${category.fdId }"}
						</ui:source>
						<list:colTable isDefault="true" layout="sys.ui.listview.columntable" cfg-norecodeLayout="simple">
							<kmss:auth requestURL="/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=add&cateId=${category.fdId}">
							<list:col-checkbox></list:col-checkbox>
							</kmss:auth>
							<list:col-serial style="width15"></list:col-serial>
							<list:col-html title="${ lfn:message('sys-attend:sysAttendCategory.importView.docCreator') }" headerStyle="min-width: 100px;">
								{$ {%row['docCreator.fdName'] %} $}  
							</list:col-html>
							<kmss:auth requestURL="/sys/attend/sys_attend_sign_patch/sysAttendSignPatch.do?method=add&cateId=${category.fdId}">
							<list:col-auto props="patchOper;"></list:col-auto>
							</kmss:auth>
						</list:colTable>
					 </list:listview>
					 <list:paging channel="sysAttendNotSignListview_${JsParam.fdModelId}_${category.fdId }"></list:paging>
					 </div>
					 <%-- 已签到列表 --%>
					 <div id="sysAttendSigned_${JsParam.fdModelId}_${category.fdId }" style="display: none;">
					 <list:listview id="sysAttendSignedListview_${JsParam.fdModelId}_${category.fdId }" channel="sysAttendSignedListview_${JsParam.fdModelId}_${category.fdId }">
						<ui:source type="AjaxJson">
							{"url":"/sys/attend/sys_attend_main/sysAttendMain.do?method=list&categoryType=custom&rowsize=15&operType=1&appId=${JsParam.fdModelId}&fdCategoryId=${category.fdId }"}
						</ui:source>
						<list:colTable isDefault="true" layout="sys.ui.listview.columntable" cfg-norecodeLayout="simple">
							<list:col-serial style="width15"></list:col-serial>
							<list:col-html title="${ lfn:message('sys-attend:sysAttendCategory.importView.docCreator') }">
								{$ 
									{% row['fdOutTarget'] ==true ? (row['docCreator.fdName'] + ' (${ lfn:message("sys-attend:sysAttendCategory.importView.outOfZone") })'):row['docCreator.fdName'] %}
								$}  
							</list:col-html>
							<list:col-html title="${ lfn:message('sys-attend:sysAttendCategory.importView.signTime') }">
								{$ 
									{% row['_fdStatus']==0 ? '' : row['docCreateTime'] %}
								$}
							</list:col-html>
							<list:col-html title="${ lfn:message('sys-attend:sysAttendMain.fdLocation') }" headerStyle="min-width: 200px">
								{$ 
									{% row['fdLocation'] %}
								$}
							</list:col-html>
							<list:col-html title="${ lfn:message('sys-attend:sysAttendCategory.importView.signStatus') }">
								{$ 
									{% row['_fdStatus']==1 ? '${ lfn:message("sys-attend:sysAttendMain.fdStatus.ok") }':(row['_fdStatus']==2)?'${ lfn:message("sys-attend:sysAttendMain.fdStatus.late") }':'' %}
								$}
							</list:col-html>
							<list:col-html title="${ lfn:message('sys-attend:sysAttendMain.fdPersonType') }">
								{$ 
									{% row['_fdStatus']==0 ? '' : row['fdPersonType'] %}
								$}
							</list:col-html>
							<list:col-auto props="fdIsPatch;fdPatchPerson;fdSignPatch.fdPatchTime;"></list:col-auto>
						</list:colTable>	
					</list:listview>
					<list:paging channel="sysAttendSignedListview_${JsParam.fdModelId}_${category.fdId }"></list:paging>
					</div>
					</c:if>
				</div>
			</div>
		</c:forEach>
		<%-- 二维码 --%>
		<ui:layout type="Javascript">
			<c:import url="/sys/attend/import/view_layout.js" charEncoding="UTF-8">
			</c:import>
		</ui:layout>
		
		
		
	</div>
	<div id="sysAttendView_${JsParam.fdModelId}_nodata">
		${ lfn:message('sys-attend:sysAttendCategory.importView.noData') }
	</div>
</ui:content>