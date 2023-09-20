<%@page import="com.landray.kmss.sys.person.interfaces.LinkInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
    <link type="text/css" rel="stylesheet" href="${KMSS_Parameter_ContextPath}sys/ui/extend/theme/default/style/dialog.css"/>
<template:include ref="default.simple">
	<template:replace name="title"><bean:message bundle="sys-person" key="sysPersonLink.selectLink"/></template:replace>
	<template:replace name="body">
	<script>
		seajs.use(['theme!form']);
	</script>

	<div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="width: 95%;margin: 10px auto;">
		<script type="lui/event" data-lui-event="changed" data-lui-args="evt">
 			$('#selectedBean').hide();
		</script>
	</div>
	
	<script>
	
		function refreshCheckbox() {
			var vals = LUI('selectedBean').getValues();
			LUI.$('[name="List_Selected"]').each(function() {
				for (var i = 0; i < vals.length; i ++) {
					if (vals[i].id == this.value) {
						if (!this.checked)
							this.checked = true;
						return;
					}
				}
				if (this.checked)
					this.checked = false;
			});
		}
		function submitSelected(data) {
			var categoryNames = $("[name='categoryNames']").val();
			var categoryIds = $("[name='categoryIds']").val();
			if(categoryNames && categoryIds && null != categoryNames && null != categoryIds){
				var categoryName = categoryNames.split(";");
				var categoryId = categoryIds.split(";");
				for(var i = 0;i<categoryName.length;i++){
					var url = $("select[name='categories']").find("option:selected").attr ("addUrl");
					url = url.replace('{0}', categoryId[i]);
					selectLink(categoryId[i], categoryName[i], url, '', '');
				}
				
			}
			
			window.$dialog.hide(data || LUI('selectedBean').getValues());
		}
		function selectLink(id, name, url, icon, server, langNames) {
			var data = {
					"id": id,
					"name":name,
					"url":url.replace(/&amp;/g, "&"),
					"icon":icon,
					"server":server,
					"langNames":langNames
			}; 
			/* if (multi == false) {
				submitSelected([data]);
				return;
			}
			if (LUI('selectedBean').hasVal(data)) {
				LUI('selectedBean').removeVal(data);
				return;
			} */
			LUI('selectedBean').addVal(data);
		}
	</script>

	<table id="categoryTable" class="tb_normal " width="100%">
			<tr class="tr_normal_title">
				<td width=30%>
					${lfn:message('sys-person:sysPerson.config.module')}
				</td>
				<td width=50%>
					${lfn:message('sys-person:sysPerson.config.myCategory')}
				</td>
			</tr>
			<tr KMSS_IsContentRow="1" >
				<td>
					<select name="categories" onchange="FixCategoryModelSelector();">
						<option value="">=== <bean:message bundle="sys-person" key="sysPersonLink.selectCategory"/> ===</option>
						<c:forEach items="${rtnCategoryMap }" var="category">
							<option value="${category.value.fdModelName }" addUrl="${category.value.addUrl }"><c:out value="${category.value.messageKey }" /></option>
						</c:forEach>
					</select>
				</td>
				<td>
					<xform:dialog 
							propertyId="categoryIds" 
							propertyName="categoryNames" 
							style="width:100%"
							textarea="true"
							showStatus="edit"
							idValue="${category.value.fdFollowId}"
							nameValue="${category.value.fdFollowName}">
						selectCategorys(this);
					</xform:dialog>
				</td>
			</tr>
		</table>

	<c:if test="${param.multi != 'false'}">
		<div data-lui-mark="dialog.content.buttons" style="position: fixed;bottom:0;left: 15px;width:95%;background: #fff;padding-top:2px;text-align:center;" class="lui_dialog_common_buttons clearfloat">
			<ui:button onclick="submitSelected();" text="${lfn:message('button.ok') }" />
		</div>
	</c:if>
	</template:replace>
</template:include>
<style>
	.lui_listview_body .lui_listview_columntable_table{
		table-layout: fixed;
	}
</style>
<script type="text/javascript">

//让已选的失效
function FixCategoryModelSelector() {
	$("[name='categoryNames']").val('');
	$("[name='categoryIds']").val('');
	$("#addUrl").val('');
}


function selectCategorys(sel) {
	var ms = mArray;
	var select = $("select[name='categories']");
	console.log("select--" + select);
	var modelName = select.val();
	console.log("modelName--" + modelName);
	var idfield = $(sel).find('[name^=categoryIds]').attr('name');
	var namefield = $(sel).find('[name^=categoryNames]').attr('name');
	var categoryModelName;
	var categoryType;
	for(var i = 0; i < ms.length; i++) {
		var m = ms[i];
		if(m.modelName == modelName) {
			categoryModelName = m.categoryModelName;
			categoryType = m.categoryType;
		}
	}
	if(categoryModelName && categoryModelName != "") {
		if("sysSimpleCategory" == categoryType) {
			seajs.use(['lui/dialog'],function (dialog) {
				dialog.simpleCategory(categoryModelName, idfield, 
						namefield, true,null, "${lfn:message('sys-person:sysPerson.config.dialog.simpleCategory')}", true);
			});
		} else if("sysCategory" == categoryType) {
			seajs.use(['lui/dialog'],function(dialog){
				dialog.category(categoryModelName, idfield,namefield, true,
					null, "${lfn:message('sys-person:sysPerson.config.dialog.category')}", true);
			});
		}
	}
}
</script>

<script>
	var data = <% out.print(request.getAttribute("data").toString()); %>;
	var mArray = data.array;
	//已经订阅的分类的modelname数组
	var followedArray = [];
	
</script>