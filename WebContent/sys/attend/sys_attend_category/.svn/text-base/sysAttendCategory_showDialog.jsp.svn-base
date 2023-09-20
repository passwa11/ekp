<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.dialog">
	<template:replace name="head">
		<style type="text/css">
		</style>
	</template:replace>
	<template:replace name="content"> 
			<p class="txttitle" style="margin: 10px 0;">
				${ lfn:message('sys-attend:sysAttendCategory.select.attend')}
			</p>
			<table class="tb_normal" width="98%">
				<tr>
					<td class="td_normal_title" width=15%>
					  	${lfn:message('sys-attend:sysAttendCategory.attend.fdName') }
					</td>
					<td width=35%>
						<xform:text property="categoryName" showStatus="edit" style="width:90%"></xform:text>
					</td>
					<td class="td_normal_title" width=15%>
					  	${lfn:message('sys-attend:sysAttendCategory.fdATemplate') }
					</td>
					<td width=35%>
						<xform:dialog propertyId="categoryTmplIds" propertyName="categoryTmplNames" showStatus="edit" style="width:90%">
					  	 	selectCategoryTmpl();
						</xform:dialog>
					</td>
				</tr>
				<tr>
					<%-- 查询 --%>
					<td colspan="4" style="text-align: center;">
						<ui:button text="${lfn:message('button.list') }"  onclick="searchCategory()"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<div style="max-height: 270px;overflow-y: auto;">
						<%--列表--%>
						<list:listview id="listview" style="" cfg-needMinHeight="false">
							<ui:source type="AjaxJson" >
								{url:'/sys/attend/sys_attend_category/sysAttendCategory.do?method=list&type=attend&rowsize=10'}
							</ui:source>
							<list:colTable isDefault="false" layout="sys.ui.listview.listtable" name="columntable" onRowClick="selectCategory('!{fdId}');">
								<list:col-checkbox></list:col-checkbox>
								<list:col-serial headerStyle="width:12%"></list:col-serial>
								<list:col-html title="${lfn:message('sys-attend:sysAttendCategory.attend.fdName') }">
									{$ <span data-id="{%row['fdId']%}">{%row['fdName']%}</span> $}
								</list:col-html>
								<list:col-auto props="fdStatus;fdManager.fdName;fdATemplate.fdName;"></list:col-auto>
							</list:colTable>
							<ui:event topic="list.loaded">
								var categoryIds="${JsParam.categoryIds}".split(";");
								for(var i in categoryIds){
									if(categoryIds[i]){
										$('[name="List_Selected"][value="'+ categoryIds[i] +'"]').prop('checked','checked');
									}
								}
							</ui:event>
						</list:listview>
						<list:paging></list:paging>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="4" style="text-align: center;">
						<%--全选--%>
						<ui:button text="${lfn:message('sys-attend:sysAttendCategory.selectAll')}"   onclick="selectAll();"/>
					  	<%--选择--%>
						<ui:button text="${lfn:message('button.select')}"   onclick="selectSubmit();"/>
						<%--取消--%>
						<ui:button text="${lfn:message('button.cancel') }"  onclick="selectCancel();"/>
						<%--取消选定--%>
						<ui:button text="${lfn:message('button.cancelSelect') }"  onclick="selectCancelSubmit();"/>
					</td>
				</tr>
			</table>
	</template:replace>
</template:include>
<script>
	seajs.use([
 	      'lui/jquery',
 	      'lui/dialog',
 	      'lui/topic',
 	      'lui/util/str'
 	        ],function($,dialog,topic,str){

		//选择时触发
		window.selectCategory = function(categoryId){
			//可选中才触发此事件
			if($('[name="List_Selected"][value="'+ categoryId +'"]').is(':checked')){
				$('[name="List_Selected"][value="'+ categoryId +'"]').prop('checked', false);
			}else{
				$('[name="List_Selected"][value="'+ categoryId +'"]').prop('checked',true);
			}
		};
		//搜索
		window.searchCategory = function(){
			var url=LUI('listview').source.url;
			LUI('listview').source.setUrl(setUrlParameter(url,{
				"fdName" : $('[name="categoryName"]').val(),
				"fdATemplateIds": $('[name="categoryTmplIds"]').val()
			}));
			LUI('listview').source.get();
		};
		//提交
		window.selectSubmit = function(){
			var cateIds="",cateNames="";
			var cateEles =$('[name="List_Selected"]:checked').each(function(){
				var prefix = !!cateIds ? ';' : '';
				cateIds += prefix + $(this).val();
				cateNames += prefix + $('span[data-id="'+ $(this).val() +'"]').html();
			});
			if(cateNames) {
				cateNames = str.decodeHTML(cateNames);
			}
			$dialog.hide({cateIds:cateIds,cateNames:cateNames});
		};
		//取消
		window.selectCancel = function(){
			$dialog.hide(null);
		};
		
		//取消选定
		window.selectCancelSubmit = function(){
			$dialog.hide({cateIds:'',cateNames:''});
		};
		
		//全选
		window.selectAll = function() {
			$('[name="List_Tongle"]:checkbox').click();	
		};
		
		// 考勤组分类
		window.selectCategoryTmpl = function() {
			dialog.simpleCategory('com.landray.kmss.sys.attend.model.SysAttendCategoryATemplate','categoryTmplIds','categoryTmplNames',true,null,null,true,null,false);
		}
		
		var setUrlParameter = function(url, prams){
			for(var key in prams){
				url = Com_SetUrlParameter(url, key, prams[key]);
			}
			return url;
		}
		
	});
</script>