<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.third.ding.scenegroup.util.ThirdDingUtil" %>
    
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
                		
            </style>
            <script type="text/javascript">
                var formInitData = {

                };
                var messageInfo = {

                };

                var initData = {
                    contextPath: '${LUI_ContextPath}'
                };
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");
                Com_IncludeFile("form.js");
                Com_IncludeFile("form_option.js", "${LUI_ContextPath}/third/ding/scenegroup/third_ding_scenegroup_module/", 'js', true);
                Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/third/ding/resource/js/", 'js', true);
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
                Com_IncludeFile("docutil.js|data.js");
                
                var _validation = $KMSSValidation(document.forms['thirdDingScenegroupModuleForm']);
                _validation.addValidator('fdKeyUnique','<bean:message key="error.thirdDingScenegroupModule.fdKey.repeat" bundle="third-ding-scenegroup" />',function(v, e, o) {
            		if(v == "") {
            			return true;
            		}
            		var result = checkFdKeyUnique(v);
            		alert("result:"+result);
            		if(result == "") {
            			return true;
            		}
            		debugger;
            		return false;
            	});
                
                function checkFdKeyUnique(value) {
            		var kmssData = new KMSSData();
            		kmssData.AddBeanData("thirdDingScenegroupModuleService&fdId=${thirdDingScenegroupModuleForm.fdId}&value=" + value);
            		var data = kmssData.GetHashMapArray();
            		return data[0]["result"];
            	}
                
                </script>
        </template:replace>

        <template:replace name="title">
            <c:choose>
                <c:when test="${thirdDingScenegroupModuleForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('third-ding-scenegroup:table.thirdDingScenegroupModule') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${thirdDingScenegroupModuleForm.fdName} - " />
                    <c:out value="${ lfn:message('third-ding-scenegroup:table.thirdDingScenegroupModule') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ thirdDingScenegroupModuleForm.method_GET == 'edit' }">
                        <ui:button text="${ lfn:message('button.update') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingScenegroupModuleForm, 'update');}" />
                    </c:when>
                    <c:when test="${ thirdDingScenegroupModuleForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.save') }" onclick="if(validateDetail()){Com_Submit(document.thirdDingScenegroupModuleForm, 'save');}" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('third-ding-scenegroup:table.thirdDingScenegroupModule') }" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
            <html:form action="/third/ding/scenegroup/third_ding_scenegroup_module/thirdDingScenegroupModule.do">

                <ui:tabpage expand="false" var-navwidth="90%">
                    <ui:content title="${ lfn:message('third-ding-scenegroup:py.JiBenXinXi') }" expand="true">
                        <div class='lui_form_title_frame'>
                            <div class='lui_form_subject'>
                                ${lfn:message('third-ding-scenegroup:table.thirdDingScenegroupModule')}
                            </div>
                            <div class='lui_form_baseinfo'>

                            </div>
                        </div>
                        <table class="tb_normal" width="100%">
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupModule.fdName')}
                                </td>
                                <td width="35%">
                                    <%-- 群模板--%>
                                    <div id="_xform_fdName" _xform_type="text">
                                        <xform:text property="fdName" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupModule.fdKey')}
                                </td>
                                <td width="35%">
                                    <%-- 模板标识--%>
                                    <div id="_xform_fdKey" _xform_type="text">
                                        <xform:text property="fdKey" showStatus="edit" style="width:95%;" validators="fdKeyUnique"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="td_normal_title" width="15%">
                                    ${lfn:message('third-ding-scenegroup:thirdDingScenegroupModule.fdModuleId')}
                                </td>
                                <td width="35%">
                                    <%-- 模板ID--%>
                                    <div id="_xform_fdModuleId" _xform_type="text">
                                        <xform:text property="fdModuleId" showStatus="edit" style="width:95%;" />
                                    </div>
                                </td>
                                <td colspan="2">
                                </td>
                            </tr>
                        </table>
                        <br>
                        <br>
                        <div>
                        <span style="font-size:20px;">
                        		帮助:
                        </span>
                        <br>
                         <span style="font-size:18px;">
                        		场景群接入流程请参考钉钉开发文档：https://ding-doc.dingtalk.com/document#/org-dev-guide/scenario-group-access-process
                        </span>
                        </div>
                    </ui:content>
                </ui:tabpage>
                <html:hidden property="fdId" />


                <html:hidden property="method_GET" />
            </html:form>
            
            <script type="text/javascript">
            var _validation = $KMSSValidation(document.forms['thirdDingScenegroupModuleForm']);
            _validation.addValidator('fdKeyUnique','<bean:message key="error.thirdDingScenegroupModule.fdKey.repeat" bundle="third-ding-scenegroup" />',function(v, e, o) {
        		if(v == "") {
        			return true;
        		}
        		var result = checkFdKeyUnique(v);
        		if(result == "") {
        			return true;
        		}
        		return false;
        	});
            
            function checkFdKeyUnique(value) {
        		var kmssData = new KMSSData();
        		kmssData.AddBeanData("thirdDingScenegroupModuleService&fdId=${thirdDingScenegroupModuleForm.fdId}&value=" + value);
        		var data = kmssData.GetHashMapArray();
        		return data[0]["result"];
        	}
            </script>
        </template:replace>


    </template:include>