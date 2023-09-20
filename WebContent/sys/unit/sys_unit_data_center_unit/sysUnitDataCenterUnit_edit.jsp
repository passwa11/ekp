<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser());
        pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.edit">
        <template:replace name="head">
            <style type="text/css">
           		.lui_paragraph_title{
           			font-size: 15px;
           			color: #15a4fa;
           	    	padding: 15px 0px 5px 0px;
           		}
           		.lui_paragraph_title span{
           			display: inline-block;
           			margin: -2px 5px 0px 0px;
           		}
           		.inputsgl[readonly], .tb_normal .inputsgl[readonly] {
           		    border: 0px;
           		    color: #868686
           		}
           		.tips{
           			font-size: 9px;
           			color: #666;
           			margin: 10px 5px;
           		}
           		.data_center_tr_height{
           			height:35px;
           		}
            </style>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${sysUnitDataCenterUnitForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-unit:table.sysUnitDataCenterUnit') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${ lfn:message('sys-unit:table.sysUnitDataCenterUnit') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ sysUnitDataCenterUnitForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="submitMethod('update');" />
                    </c:when>
                    <c:when test="${ sysUnitDataCenterUnitForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="submitMethod('save');" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('sys-unit:table.sysUnitDataCenterUnit') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do">
                <div class='lui_form_title_frame'>
                    <div class='lui_form_subject'>
                        ${lfn:message('sys-unit:table.sysUnitDataCenterUnit')}
                    </div>
                </div>
                <table class="tb_normal" width="100%">
                    <tr>
                        <td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdUnitCode')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 单位编码--%>
                            <div id="_xform_fdAppkey" _xform_type="text">
                                <xform:text property="fdUnitCode" showStatus="edit" style="width:95%;" required="true" validators="checkOnly checkChar" />
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdSecretary')}
                        </td>
                        <td colspan="3" width="85.0%">
                                <%-- 单位文书--%>
                            <div id="_xform_fdSecretary" _xform_type="text">
                                <xform:address required="true" subject="${ lfn:message('sys-unit:sysUnitDataCenterUnit.fdSecretary')}"
                                               propertyName="fdSecretaryNames" propertyId="fdSecretaryIds"
                                               orgType="ORG_TYPE_PERSON" textarea="true"
                                               style="width:95%" mulSelect="true">
                                </xform:address>
                                <c:if test="${ sysUnitDataCenterUnitForm.method_GET == 'edit' }">
                                    <div style="width:95%;color: red;" class="tips">${lfn:message('sys-unit:sysUnitDataCenterUnit.fdSecretary.tips')}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>
                    <%--<tr>
                        <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdSupervisePerson')}
                        </td>
                        <td colspan="3" width="85.0%">
                                &lt;%&ndash; 单位联络员&ndash;%&gt;
                            <div id="_xform_fdSecretary" _xform_type="text">
                                <xform:address required="true" subject="${ lfn:message('sys-unit:sysUnitDataCenterUnit.fdSupervisePerson')}"
                                               propertyName="fdSupervisePersonNames" propertyId="fdSupervisePersonIds"
                                               orgType="ORG_TYPE_PERSON" textarea="true"
                                               style="width:95%" mulSelect="true">
                                </xform:address>
                                <c:if test="${ sysUnitDataCenterUnitForm.method_GET == 'edit' }">
                                    <div style="width:95%;color: red;" class="tips">${lfn:message('sys-unit:sysUnitDataCenterUnit.fdSupervisePerson.tips')}</div>
                                </c:if>
                            </div>
                        </td>
                    </tr>--%>

                    <tr>
                        <td class="td_normal_title" width="15%">
                                ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdCenters')}
                        </td>
                        <td colspan="3" width="85.0%">
                                <%-- 对接交换中心--%>
                            <div id="_xform_fdCenters" _xform_type="text">
                                <xform:dialog dialogJs="selectCenter();" required="true" subject="${ lfn:message('sys-unit:sysUnitDataCenterUnit.fdCenters')}"
                                              propertyId="fdCenterIds" propertyName="fdCenterNames" style="width:95%" textarea="true"  useNewStyle="false">
                                </xform:dialog>
                            </div>
                        </td>
                    </tr>

	                <tr>
                    	<td class="td_normal_title" width="15%">
                            ${lfn:message('sys-unit:sysUnitDataCenterUnit.fdIsAvailable')}
                        </td>
                        <td colspan="3" width="85.0%">
                            <%-- 是否有效--%>
                            <div id="_xform_fdIsAvailable" _xform_type="radio">
                                <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="edit">
                                    <xform:enumsDataSource enumsType="common_yesno" />
                                </xform:radio>
                            </div>
                        </td>
                    </tr>
                </table>
                <html:hidden property="fdId" />
                <html:hidden property="method_GET" />
            </html:form>
            <script language="JavaScript">
      			var validation = $KMSSValidation(document.forms['sysUnitDataCenterUnitForm']);
      			
      			validation.addValidator("checkOnly","此编码已存在，请重新输入",function(value,object,text){
      	   					return checkOnly(value,object,text);
      	   				}
      	    	);
      			
      			validation.addValidator("checkChar","此编码存在非法字符'/'，请重新输入",function(value,object,text){
      					var flag = true;
      					if(value.indexOf("/") > 0){
      						flag = false;
      					}
	   					return flag;
	   				}
	    		);

                function  selectCenter(){
                    Dialog_List(true, "fdCenterIds", "fdCenterNames", ";", "sysUnitDataCenterService", null, null, false, true, "选择对接交换中心");
                }
      			
   	    		function checkOnly(value,object,text) {
   	    			var flag = false;
   	    			var fdUnitId = document.getElementsByName('fdId')[0];
   	    			var url =  Com_Parameter.ContextPath + "sys/unit/sys_unit_data_center_unit/sysUnitDataCenterUnit.do?method=checkOnly";
   	    			$.ajax({
   	    				url:url,
   	    				type:'POST',
   	    				async:false,
   	    				dataType:'json',
   	    				data:$.param({"fdUnitCode":value,"fdUnitId":fdUnitId.value},true),
   	    				success:function(result) {
   	    					if(result) {
   	    						if(result.isExistence == "false") {
   	    							flag = true;
   	    						}
   	    					}
   	    				}
   	    			});
   	    			return flag;
   	    		}
      			
      			function submitMethod(method){ 
      	    		var formObj = document.sysUnitDataCenterUnitForm;
      	    		if(validation.validate()){
      	            	Com_Submit(formObj, method);
      	    		}
      	    	}
      		</script>
        </template:replace>
    </template:include>