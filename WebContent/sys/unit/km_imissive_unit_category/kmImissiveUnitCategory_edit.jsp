<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit"  sidebar="auto">
<template:replace name="toolbar">
	<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
		<c:if test="${kmImissiveUnitCategoryForm.method_GET=='edit'}">
		 <ui:button text="${ lfn:message('button.update') }" order="2" onclick="Com_Submit(document.kmImissiveUnitCategoryForm, 'update');">
		 </ui:button>
		</c:if>
		<c:if test="${kmImissiveUnitCategoryForm.method_GET=='add'}">
			<ui:button text="${ lfn:message('button.submit') }" order="1" onclick="Com_Submit(document.kmImissiveUnitCategoryForm, 'save');">
		    </ui:button>
		    <ui:button text="${ lfn:message('button.saveadd') }" order="2" onclick="Com_Submit(document.kmImissiveUnitCategoryForm, 'saveadd');">
		    </ui:button>
		</c:if>
		<ui:button text="${ lfn:message('button.close') }" order="5"  onclick="Com_CloseWindow()">
		 </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<html:form action="/sys/unit/km_imissive_unit_category/kmImissiveUnitCategory.do">

<p class="txttitle"><bean:message bundle="sys-unit" key="table.kmImissiveUnitCategory"/></p>

<center>
<table class="tb_normal" width=100%>
		<html:hidden property="fdId"/>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.fdParentName"/>
		</td><td width=85% colspan="3">
		    <xform:dialog propertyId="fdParentId" propertyName="fdParentName" className="inputsgl" style="width:85%" >
		       Dialog_Tree(false, 'fdParentId', 'fdParentName', ',', 'kmImissiveUnitCategoryInnerTreeService&parentId=!{value}', '<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.fdParentName"/>', null, null, '${kmImissiveUnitCategoryForm.fdId}', null, null, '<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.fdParentName"/>');
		    </xform:dialog>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.fdName"/>
		</td><td width=35% colspan="3">
			<xform:text property="fdName" required="true" style="width:85%"/>
		</td>
	</tr>
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.fdOrder"/>
		</td><td width=85% colspan="3">
			<xform:text property="fdOrder" style="width:85%" validators="digits"/>
		</td>
		<!-- 
		<td class="td_normal_title" width=15%>
			<bean:message bundle="sys-unit" key="kmImissiveUnitCategory.fdIsAvailable"/>
		</td><td width=35%>
			<sunbor:enums property="fdIsAvailable" enumsType="common_yesno" elementType="radio" />
		</td>
		 -->
	</tr>
</table>
</center>
<html:hidden property="method_GET"/>
<script language="JavaScript">
      $KMSSValidation(document.forms['kmImissiveUnitCategoryForm']);
</script>
</html:form>
</template:replace>
</template:include>