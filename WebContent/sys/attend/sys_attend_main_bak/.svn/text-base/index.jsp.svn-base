<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include file="/sys/profile/resource/template/list.jsp">
	<template:replace name="head">
	</template:replace>
	<template:replace name="content">
		<!-- 查询条件  -->
		<list:criteria id="criteria1">
			<list:cri-auto modelName="com.landray.kmss.sys.attend.model.SysAttendMain" property="docCreateTime" />
			<list:cri-ref key="docCreator" ref="criterion.sys.person" multi="false" title="${lfn:message('sys-attend:sysAttendMain.docCreator') }" />
			<list:cri-ref key="docCreatorDept" ref="criterion.sys.dept" multi="false" title="${lfn:message('sys-attend:sysAttendMain.docCreatorDept') }" />
			<list:cri-criterion title="${ lfn:message('sys-attend:sysAttendMain.fdStatus')}" key="fdStatus"> 
				<list:box-select>
					<list:item-select>
						<ui:source type="Static">
							[{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.ok')}', value:'1'},
							{text:'${ lfn:message('sys-attend:sysAttendMain.fdOutside')}', value:'11'},
							{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.late')}',value:'2'},
							{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.left')}',value:'3'},
							{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.business')}',value:'4'},
							{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.askforleave')}',value:'5'},
							{text:'${ lfn:message('sys-attend:sysAttendMain.fdStatus.outgoing')}',value:'6'}]
						</ui:source>
					</list:item-select>
				</list:box-select>
			</list:cri-criterion>
			<list:cri-ref key="fdCategoryName" ref="criterion.sys.string" title="${ lfn:message('sys-attend:sysAttendCategory.attend') }"></list:cri-ref>
		</list:criteria>
	
	
		<!-- 操作栏 -->
		<div class="lui_list_operation">
			<!-- 排序 -->
			<div style='color: #979797;float: left;padding-top:1px;'>
				${ lfn:message('list.orderType') }：
			</div>
			<div style="float:left">
				<div style="display: inline-block;vertical-align: middle;">
					<ui:toolbar layout="sys.ui.toolbar.sort" style="float:left" >
						<list:sort property="sysAttendMainBak.docCreateTime" text="${lfn:message('sys-attend:sysAttendMain.docCreateTime') }" group="sort.list" value="down"></list:sort>
					</ui:toolbar>
				</div>
			</div>
			<div style="float:left;">	
				<list:paging layout="sys.ui.paging.top" > 		
				</list:paging>
			</div>
		
			<div style="float: right">
				<div style="display: inline-block;vertical-align: middle;">
				</div>
			</div>
		</div>
		
		<ui:fixed elem=".lui_list_operation"></ui:fixed>
		
		<!-- 内容列表 -->
		<list:listview>
			<ui:source type="AjaxJson">
				{url:'/sys/attend/sys_attend_main_bak/sysAttendMainBak.do?method=list&categoryType=attend&noMissed=true&year=${JsParam.year }'}
			</ui:source>
			<list:colTable isDefault="false" layout="sys.ui.listview.columntable"
				rowHref="/sys/attend/sys_attend_main_bak/sysAttendMainBak.do?method=view&fdId=!{fdId}&year=${JsParam.year }"  name="columntable">
				<list:col-serial></list:col-serial>
				<list:col-auto props="docCreator.fdName;docCreateTime;fdSignType;fdLocation;fdCategory.fdName;fdStatus;fdState"></list:col-auto>
			</list:colTable>
		</list:listview>
		<!-- 分页 -->
	 	<list:paging></list:paging>
	 	
	 	<script type="text/javascript">
			seajs.use(['lui/jquery','lui/topic','lui/dialog'],function($,topic,dialog){
				$(setInterval(function(){
					$('[data-lui-type="lui/criteria/criterion_calendar!CriterionDateDatas"] li[name=chekbox]').hide();
					$('[data-lui-type="lui/criteria/criterion_calendar!CriterionDateDatas"] .lui_criteria_date_li :text').bind('blur', function() {
						validateDate(this);
					});
				}, 200));
				
				var validateDate = function(target) {
					var value = $(target).val();
					if(value){
						var date = new Date(value);
						if(date){
							if(date.getFullYear() != '${JsParam.year }'){
								showValidate(target);
								return;
							}
						}
					}
					hideValidate(target);
				};
				
				var showValidate = function(target) {
					var container = $('.lui_criteria_date_validate_container').eq(0);
					container.show();
					var block = container.find('[data-lui-date-input-validate="'+ $(target).attr('name') +'"]');
					block.css('visibility', 'visible');
					var text = block.find('.text');
					text.css('visibility', 'visible');
					text.html('日期须在'+'${JsParam.year }'+'年内');
				};
				
				var hideValidate = function(target) {
					var container = $('.lui_criteria_date_validate_container').eq(0);
					container.hide();
					var block = container.find('[data-lui-date-input-validate="'+ $(target).attr('name') +'"]');
					block.css('visibility', 'hidden');
					var text = block.find('.text');
					text.css('visibility', 'hidden');
					text.html('');
				};
				
				topic.subscribe('criteria.changed', function(evt){
					if(evt && evt.criterions && evt.criterions.length == 0){
						$('[data-lui-type="lui/criteria/criterion_calendar!CriterionDateDatas"] .lui_criteria_date_li :text').each(function(){
							hideValidate(this);
						});
					}
				});
			});
		</script>
	</template:replace>
</template:include>