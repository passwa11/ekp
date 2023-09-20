<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

    <% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

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
                contextPath: '${LUI_ContextPath}',
            };
            Com_IncludeFile("security.js");
            Com_IncludeFile("domain.js");
            Com_IncludeFile("form.js");
            Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/fee/fssc_fee_main/", 'js', true);
            Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/fee/resource/js/", 'js', true);
            Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
        </script>
        <link rel="stylesheet" href="${LUI_ContextPath }/fssc/common/resource/layui/css/layui.css"  media="all">
        <script src="${LUI_ContextPath }/fssc/common/resource/layui/layui.js" charset="utf-8"></script>
        <kmss:ifModuleExist path="/fssc/budget">
        	<script>
        	seajs.use(['lui/jquery','lui/dialog','lang!fssc-fee'],function($,dialog,lang){
        		Com_Parameter.event["submit"].push(function(){ 
        			//暂存或查看不作校验
					if($("[name=docStatus]").val()=='10'||Com_GetUrlParameter(window.location.href,'method')=='view'){
						return true;
					}
					var method=Com_GetUrlParameter(window.location.href,'method');
					var props = window.Designer_Control_Budget_Rule_Props;
					//如果没有预算规则控件，不作校验
					if(!props){
						return true;
					}
        			var pass=true;
        			var params = [],len = $("#TABLE_DL_"+props.fdMatchTable+">tbody>tr:not(.tr_normal_title)").length,tb = props.fdMatchTable||'';
        			var fdCompanyId=Designer_Control_Get_Field_Value(tb,props.fdCompanyId,0);
        			if(tb){
        				for(var i=0;i<len;i++){
        					if($("input[name$='."+i+".fd_374254d9aae440_budget_info)']").val()){
        						param = {
            							'fdBudgetInfo':$("input[name$='."+i+".fd_374254d9aae440_budget_info)']").val()
            					}
            					params.push(param);
        					}
        				}
        			}else{
        				if(Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',i)){
    						param = {
        							'fdBudgetInfo':Designer_Control_Get_Field_Value(tb,props.id+'_budget_info',0)
        					}
        					params.push(param);
    					}
        			}
        			$.post(
        					Com_Parameter.ContextPath+'fssc/budget/fssc_budget_data/fsscBudgetData.do?method=checkBudgetExchangeRate',
        					{params:JSON.stringify(params),fdCompanyId:fdCompanyId},
        					function(rtn){
        						rtn = JSON.parse(rtn);
        						if(rtn.msg){
        							dialog.alert(rtn.msg);
        							pass=false;
        						}
        					}
        				);
        			return pass;
        		});
        	});
        	</script>
        </kmss:ifModuleExist>
    </template:replace>
    <c:if test="${fsscFeeMainForm.method_GET == 'edit' || (param['i.docTemplate']!=null && param['i.docTemplate']!='')}">
        <template:replace name="title">
            <c:choose>
                <c:when test="${fsscFeeMainForm.method_GET == 'add' }">
                    <c:out value="${ lfn:message('operation.create') } - ${ lfn:message('fssc-fee:table.fsscFeeMain') }" />
                </c:when>
                <c:otherwise>
                    <c:out value="${fsscFeeMainForm.docSubject} - " />
                    <c:out value="${ lfn:message('fssc-fee:table.fsscFeeMain') }" />
                </c:otherwise>
            </c:choose>
        </template:replace>
        <template:replace name="toolbar">
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
                <c:choose>
                    <c:when test="${ fsscFeeMainForm.method_GET == 'edit' }">
                        <c:if test="${ fsscFeeMainForm.docStatus=='10' || fsscFeeMainForm.docStatus=='11' }">
                            <ui:button text="${ lfn:message('button.savedraft') }" onclick="submitForm('10','update',true);" />
                        </c:if>
                        <c:if test="${ fsscFeeMainForm.docStatus=='10' || fsscFeeMainForm.docStatus=='11' || fsscFeeMainForm.docStatus=='20' }">
                            <ui:button text="${ lfn:message('button.submit') }" onclick="submitForm('20','update');" />
                        </c:if>
                    </c:when>
                    <c:when test="${ fsscFeeMainForm.method_GET == 'add' }">
                        <ui:button text="${ lfn:message('button.savedraft') }" order="2" onclick="submitForm('10','save',true);" />
                        <ui:button text="${ lfn:message('button.submit') }" order="2" onclick="submitForm('20','save');" />
                    </c:when>
                </c:choose>

                <ui:button text="${ lfn:message('button.close') }" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" />
                <ui:menu-item text="${ lfn:message('fssc-fee:table.fsscFeeMain') }" />
                 <ui:menu-item text="${docTemplateName }"  />
            </ui:menu>
        </template:replace>
        <template:replace name="content">
        <c:if test="${approveType ne 'right'}">
            <form action="${LUI_ContextPath }/fssc/fee/fssc_fee_main/fsscFeeMain.do" name="fsscFeeMainForm"  method="post">
		</c:if>
                <ui:tabpage expand="false" var-navwidth="90%" id="reviewTabPage" collapsed="true">
                	<c:if test="${param.approveType eq 'right'}">
						<script>
								LUI.ready(function(){
									setTimeout(function(){
										var reviewTabPage = LUI("reviewTabPage");
										if(reviewTabPage!=null){
											reviewTabPage.element.find(".lui_tabpage_float_collapse").hide();
											reviewTabPage.element.find(".lui_tabpage_float_navs_mark").hide();
										}
									},100)
								})
						</script>
					</c:if>
                	<div class='lui_form_subject'>
                       	<c:if test="${empty fsscFeeMainForm.docSubject }">
                            		${ docTemplate.fdName}
                            	</c:if>
                            	<c:if test="${not empty fsscFeeMainForm.docSubject }">
                            		${ fsscFeeMainForm.docSubject}
                            	</c:if>
                    </div>
                    <table class="tb_normal" width="100%">
                        <tr>
                        	<td class="td_normal_title" width="16.6%">
                                ${lfn:message('fssc-fee:fsscFeeMain.docSubject')}
                            </td>
                            <td colspan="5">
                                <div id="_xform_docSubject" _xform_type="address">
                                	<c:if test="${docTemplate.fdSubjectType=='1' }">
                                    <xform:text property="docSubject" style="width:95%" showStatus="edit"></xform:text>
                                    </c:if>
                                    <c:if test="${docTemplate.fdSubjectType=='2' && fsscFeeMainForm.docSubject==null}">
                                    	<span style="color:#888;">${lfn:message('fssc-fee:py.BianHaoShengCheng') }</span>
                                    </c:if>
                                    <c:if test="${docTemplate.fdSubjectType=='2' && fsscFeeMainForm.docSubject!=null}">
                                    	${fsscFeeMainForm.docSubject }
                                    </c:if>
                                </div>
                            </td>
                        </tr>
                    </table>
                    <c:if test="${param.approveType ne 'right'}">
                    <ui:content title="${lfn:message('fssc-fee:py.BiaoDanNeiRong')}" expand="true">
                        <c:if test="${fsscFeeMainForm.docUseXform == 'false'}">
                            <table class="tb_normal" width=100%>
                                <tr>
                                    <td colspan="2">
                                        <kmss:editor property="docXform" width="95%" />
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                        <c:if test="${fsscFeeMainForm.docUseXform == 'true' || empty fsscFeeMainForm.docUseXform}">
                            <c:import url="/sys/xform/include/sysForm_edit.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="fsscFeeMainForm" />
                                <c:param name="fdKey" value="fsscFeeMain" />
                                <c:param name="useTab" value="false" />
                            </c:import>
                        </c:if>
                    </ui:content>
                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscFeeMainForm" />
                        <c:param name="fdKey" value="fsscFeeMain" />
                        <c:param name="isExpand" value="true" />
                    </c:import>
                    <%--权限 --%>
                    <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                      <c:param name="formName" value="fsscFeeMainForm" />
	                      <c:param name="moduleModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
	                </c:import>
                    </c:if>
                </ui:tabpage>
                <c:if test="${param.approveType eq 'right'}">
                <ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="5" var-average='false' var-useMaxWidth='true'>
                    <ui:content title="${lfn:message('fssc-fee:py.BiaoDanNeiRong')}" expand="true">
                        <c:if test="${fsscFeeMainForm.docUseXform == 'false'}">
                            <table class="tb_normal" width=100%>
                                <tr>
                                    <td colspan="2">
                                        <kmss:editor property="docXform" width="95%" />
                                    </td>
                                </tr>
                            </table>
                        </c:if>
                        <c:if test="${fsscFeeMainForm.docUseXform == 'true' || empty fsscFeeMainForm.docUseXform}">
                            <c:import url="/sys/xform/include/sysForm_edit.jsp" charEncoding="UTF-8">
                                <c:param name="formName" value="fsscFeeMainForm" />
                                <c:param name="fdKey" value="fsscFeeMain" />
                                <c:param name="useTab" value="false" />
                            </c:import>
                        </c:if>
                    </ui:content>
                    <c:import url="/sys/lbpmservice/import/sysLbpmProcess_edit.jsp" charEncoding="UTF-8">
                        <c:param name="formName" value="fsscFeeMainForm" />
                        <c:param name="fdKey" value="fsscFeeMain" />
                        <c:param name="isExpand" value="true" />
                        <c:param name="approveType" value="right" />
                    </c:import>
                    <%--权限 --%>
                    <c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
	                      <c:param name="formName" value="fsscFeeMainForm" />
	                      <c:param name="moduleModelName" value="com.landray.kmss.fssc.fee.model.FsscFeeMain" />
	                </c:import>
                    </ui:tabpanel>
                    </c:if>
                <html:hidden property="fdId" />
                <html:hidden property="docStatus" />
                <html:hidden property="docTemplateId" />
                <html:hidden property="method_GET" />
                <script>
                	Com_IncludeFile("quickSelect.js", Com_Parameter.ContextPath+"eop/basedata/resource/js/", 'js', true);
                </script>
            <c:if test="${param.approveType ne 'right' }">
            </form>
            </c:if>
        </template:replace>
    </c:if>
    <c:if test="${param.approveType eq 'right' }">
		<template:replace name="barRight">
			<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel">
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscFeeMainForm" />
					<c:param name="fdKey" value="fsscFeeMain" />
					<c:param name="isExpand" value="true" />
					<c:param name="approveType" value="right" />
					<c:param name="approvePosition" value="right" />
					<c:param name="needInitLbpm" value="true" />
				</c:import>
				<c:import url="/fssc/fee/fssc_fee_main/fsscFeeMain_baseInfo_right.jsp"></c:import>
				<!-- 关联机制 -->
				<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="fsscFeeMainForm" />
					<c:param name="approveType" value="right" />
					<c:param name="needTitle" value="true" />
				</c:import>
			</ui:tabpanel>
		</template:replace>
	</c:if>
	<c:if test="${param.approveType ne 'right'}">
		<template:replace name="nav">
			<%--关联机制--%>
			<c:import url="/sys/relation/import/sysRelationMain_edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="fsscFeeMainForm" />
			</c:import>
		</template:replace>
	</c:if>
