<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.profile.edit" sidebar="no">
<template:replace name="head">
	<script>
			Com_IncludeFile("dialog.js|jquery.js");
		</script>
	<style>

		.statObjectName{ 
			line-height: 25px;
			max-height: 75px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: normal;
			max-width: 400px;
		}

		.statObject-more {
			padding-right: 5px;
			background: white;
			cursor: pointer;
			color: #4285F4;
			display: none;
		}

		.statObject-moreFold{
			padding-right: 5px;
			background: white;
			cursor: pointer;
			color: #4285F4;
			display: none;
		}
		.statObject-look-more{
			display: inline-block;
		}

	</style>
</template:replace>
<template:replace name="content">
<style type="text/css">
</style>
<html:form action="/sys/attend/sys_attend_stat/sysAttendStat.do">
<html:hidden property="fdId"/>
<div style="margin-top:25px">
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sysAttendCategory.attend"  bundle="sys-attend"/>
		</td><td colspan=3>
			<input type="hidden" name="fdCategoryIds">
			<xform:textarea subject="${ lfn:message('sys-attend:sysAttendCategory.attend') }" property="fdCategoryNames" showStatus="edit" style="width:94%;border-color: #d5d5d5;"></xform:textarea>
			<a href="javascript:void(0);" onclick="selectAttendCategory();" title="">
				${ lfn:message('button.select') }
			</a>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sysAttend.tree.config.stat.targets"  bundle="sys-attend"/>
		</td><td colspan=3>
			<xform:address propertyId="fdTargetIds" propertyName="fdTargetNames"  showStatus="edit"
					subject="${ lfn:message('sys-attend:sysAttend.tree.config.stat.targets') }"
					mulSelect="true"
						   orgType="ORG_FLAG_AVAILABLEALL"
						   validators="onCheckTargets" textarea="true" style="width:95%" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message key="sysAttend.tree.config.stat.statTime"  bundle="sys-attend"/>
		</td><td colspan=3>
			<xform:datetime onValueChange="onDateChange" subject="${ lfn:message('sys-attend:sysAttend.tree.config.stat.startTime') }" placeholder="${ lfn:message('sys-attend:sysAttend.tree.config.stat.startTime') }" required="true" validators="beforeToday" property="fdStartTime" dateTimeType="date" showStatus="edit" />
			<span style="display: inline-block;position: relative;bottom: 5px;">—</span>
			<xform:datetime onValueChange="onDateChange" subject="${ lfn:message('sys-attend:sysAttend.tree.config.stat.endTime') }" placeholder="${ lfn:message('sys-attend:sysAttend.tree.config.stat.endTime') }" required="true" validators="beforeEndToday compareTime betweenTime" property="fdEndTime" dateTimeType="date" showStatus="edit" />
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			${ lfn:message('sys-attend:sysAttendStat.fdIsCalMissed.yes') }
		</td>
		<td colspan=3>
			<ui:switch property="fdIsCalMissed" enabledText="${ lfn:message('sys-attend:sysAttendStat.fdIsCalMissed.yes') }" disabledText="${ lfn:message('sys-attend:sysAttendStat.fdIsCalMissed.no') }" checked="false"></ui:switch>
		</td>
	</tr>
	<tr>
		<td colspan=4>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.notice') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content0') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content1') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content2') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content3') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content4') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content5') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content6') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content7') }<br>
				${ lfn:message('sys-attend:sysAttend.tree.config.stat.tip.content8') }<br>
		</td>
	</tr>

</table>
<div style="margin-bottom: 10px;margin-top:25px">
	<input type="hidden" id="fdOperate" name="fdOperate">
	<ui:button text="${lfn:message('sys-attend:sysAttend.tree.config.stat.btnStat')}" height="35" width="120" onclick="onSave('restat');" order="2" ></ui:button>

	<ui:button text="${lfn:message('sys-attend:sysAttend.tree.config.stat.btnStat.create')}"
			   height="35" width="200" onclick="onSave('create');" order="1"   style="margin-right: 10px;"></ui:button>

</div>
	<div style="text-align: left;margin:0px 10px;">
		<!-- 列表工具栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div class="lui_list_operation_sort_btn">
				<div class="lui_list_operation_sort_toolbar">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
						<list:sort property="sysAttendRestatLog.docCreateTime" text="${ lfn:message('sys-attend:sysAttendRestatLog.docCreateTime') }" group="sort.list" value="down"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<!-- 分页 -->
			<div class="lui_list_operation_page_top">
				<list:paging layout="sys.ui.paging.top" >
				</list:paging>
			</div>
		</div>
		<!--统计日志列表-->
		<list:listview id="listview">
			<ui:source type="AjaxJson">
				{url:'/sys/attend/sys_attend_stat/sysAttendStat.do?method=statLoglist'}
			</ui:source>
			<!-- 列表视图 -->
			<list:colTable isDefault="false"
						   name="columntable">
				<list:col-serial></list:col-serial>
				<list:col-auto props="fdOperateType;statTime;fdCategoryName;fdStatUserNames;fdCreateMiss;docCreateTime;docAlterTime;fdStatus;"></list:col-auto>

			</list:colTable>
			<ui:event topic="list.loaded">
					seajs.use(['lui/jquery'], function($){
						var infoAotuHeightNames = $("[name='infoAotuHeight']");
						if(infoAotuHeightNames && infoAotuHeightNames.length > 0){
							for(var idx in infoAotuHeightNames){
								var infoObject =infoAotuHeightNames[idx];
								var thisHeight = $(infoObject).height();
								var wordHeight= $(infoObject)[0].scrollHeight;
								if(wordHeight > thisHeight) {
									var moreBtn = $(infoObject).next().find(".statObject-more");
									moreBtn.css('display','inline-block');
								}
							}
						}
					});
			</ui:event>
		</list:listview>
	</div>
</center>
</div>
<html:hidden property="method_GET"/>
</html:form>
<script type="text/javascript">
	var onsubmit = false;
	seajs.use(['lui/jquery','lui/dialog','sys/attend/resource/js/dateUtil'], function($,dialog,dateUtil){
		var validation = $KMSSValidation();
		validation.addValidator('compareTime','<bean:message bundle="sys-attend" key="sysAttend.tree.config.stat.time.tip" />',function(v, e, o){
			var result = true;
			var fdStartTime=$('[name="fdStartTime"]');
			var fdEndTime=$('[name="fdEndTime"]');
			if( fdStartTime.val() && fdEndTime.val() ){
				var start= Com_GetDate(fdStartTime.val(), 'date', Com_Parameter.Date_format);
				var end= Com_GetDate(fdEndTime.val(), 'date', Com_Parameter.Date_format);
				if(end.getTime() < start.getTime()){
					result = false;
				}
			}
			return result;
		});
		validation.addValidator('beforeToday','<bean:message bundle="sys-attend" key="sysAttend.tree.config.stat.time.tip2" />',function(v, e, o){
			if(!v){
				return true;
			}
			var valueObj=Com_GetDate(v, 'date', Com_Parameter.Date_format);
			var now = new Date();
			now.setHours(0,0,0,0)
			if(valueObj.getTime()<now.getTime()){
				return true;
			}
			return false;
		});
		validation.addValidator('beforeEndToday','<bean:message bundle="sys-attend" key="sysAttend.tree.config.stat.time.tip3" />',function(v, e, o){
			if(!v){
				return true;
			}
			var valueObj=Com_GetDate(v, 'date', Com_Parameter.Date_format);
			var now = new Date();
			now.setHours(0,0,0,0)
			if(valueObj.getTime()<now.getTime()){
				return true;
			}
			return false;
		});
		validation.addValidator('betweenTime','<bean:message bundle="sys-attend" key="sysAttend.tree.config.stat.time.tip4" />',function(v, e, o){
			var result = true;
			var fdStartTime=$('[name="fdStartTime"]');
			var fdEndTime=$('[name="fdEndTime"]');
			if( fdStartTime.val() && fdEndTime.val() ){
				var start= Com_GetDate(fdStartTime.val(),'date', Com_Parameter.Date_format);
				var end= Com_GetDate(fdEndTime.val(),'date', Com_Parameter.Date_format);
				var days = (end.getTime()- start.getTime())/(24*60*60*1000);
				if(days > 31){
					result = false;
				}
			}
			return result;
		});
		
		validation.addValidator('onCheckTargets','<bean:message bundle="sys-attend" key="sysAttend.tree.config.stat.selTargets" />',function(v, e, o){
			var result = true;
			var fdCategoryIds = $('input[name="fdCategoryIds"]').val();
			var fdTargetIds = $('input[name="fdTargetIds"]').val();
			if( !fdCategoryIds && !fdTargetIds ){
				result = false;
			}
			return result;
		});
		window.selectAttendCategory = function(){
			var fdCategoryNames = $('textarea[name="fdCategoryNames"]').val();
			if(!$.trim(fdCategoryNames)){
				$('input[name="fdCategoryIds"]').val('');
			}
			Dialog_List(true, "fdCategoryIds", "fdCategoryNames", ';',"sysAttendCategoryService",function(data){
				if(!data){
					return;
				}
				validation.validateElement($('textarea[name="fdCategoryNames"]')[0]);
			},"sysAttendCategoryService&search=!{keyword}", false, false,"${ lfn:message('sys-attend:sysAttend.tree.config.stat.selCate') }");
					
		};
		window.onDateChange = function(value,element){
			validation.validateElement(element);
			if(element.name=='fdStartTime'){
				//validation.validateElement($('[name="fdEndTime"]')[0]);
			}else{
				validation.validateElement($('[name="fdStartTime"]')[0]);
			}
		};
		
		window.onSave = function(type) {
			if(onsubmit){
				return;
			}
			if(!validation.validate()){
				return;
			}
			onsubmit = true;
			window.stat_loading = dialog.loading();
			$("#fdOperate").val(type);
			Com_Submit(document.sysAttendStatForm, 'restat');
		};
		$(function(){
			$('textarea[name="fdCategoryNames"]').attr('readonly','readonly');
		});

		window.showMoreContent =function(e){
			$(e).parent().prev().css('max-height','none');
			$(e).css('display','none');
			$(e).next().css('display','inline-block');
		}

		window.closeMoreContent =function(e){
			$(e).parent().prev().css('max-height','75px');
			$(e).prev().css('display','inline-block');
			$(e).css('display','none');
		}

	});
	
	
</script>
</template:replace>
</template:include>
<ui:top id="top"></ui:top>
<kmss:ifModuleExist path="/sys/help">
	<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
</kmss:ifModuleExist>