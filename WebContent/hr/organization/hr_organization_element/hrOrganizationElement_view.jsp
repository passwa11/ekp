<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>

<template:include ref="default.dialog">
	<template:replace name="head">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/lib/form.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/organization.css">
		<link rel="stylesheet"
			href="${LUI_ContextPath}/hr/organization/resource/css/hrorg.css">
	</template:replace>
	<template:replace name="content">
		<div class="ld-lookList-modal">
	        <div class="ld-lookList-main">
	            <div class="ld-lookList-main-tab">
	                <div class="active" onclick="changeTab('.ld-look-org-info', '.changeRecord')">组织信息</div>
	                <div onclick="changeTab('.changeRecord', '.ld-look-org-info')">${lfn:message('hr-organization:hr.organization.info.orgChangeLog')} </div>
	            </div>
	            <div class="ld-lookList-orgInfo">
	                <div>
	                   <table class="changeRecord">
	                        <thead>
	                            <tr>
	                                <td>变动类型</td>
	                                <td>变动描述</td>
	                                <td>备注</td>
	                                <td>操作人</td>
	                                <td>操作时间</td>
	                            </tr>
	                        <tbody>
	                        	<c:forEach items="${logs }" var="log">
		                            <tr onclick="javascript:window.open('${LUI_ContextPath}/hr/organization/hr_organization_log/hrOrganizationLog.do?method=view&fdId=${log.fdId}');">
		                                <td>
											<% try{ %>
												<bean:message bundle="hr-organization" key="hr.organization.log.${log.fdParaMethod }"/>
											<%}catch(Exception ex){ %>
												${log.fdParaMethod }
											<% } %>
		                               	</td>
		                                <td width="200">
		                                    <p>
		                                    	<% try{ %>
		                                    		<bean:message bundle="hr-organization" key="hr.organization.log.${log.fdParaMethod }"/>
		                                    	<%}catch(Exception ex){ %>
													${log.fdParaMethod }
												<% } %>
		                                    	<xform:select property="fdOrgType" showStatus="view" value="${hrOrganizationElementForm.fdOrgType }">
													<xform:enumsDataSource enumsType="hr_organization_type" />
												</xform:select>
												【${hrOrganizationElementForm.fdName }】
		                                    </p>
		                                </td>
		                                <td width="240">
		                                    <p>${log.fdDetails }</p>
		                                </td>
		                                <td width="100">${log.fdOperator }</td>
		                                <td>${log.fdCreateTime }</td>
		                            </tr>
	                            </c:forEach>
	                        </tbody>
	                        </thead>
	                    </table>
	                    <div class="ld-look-org-info">
	                        <table class="ld-look-org-info active">
	                        <tbody>
	                            <tr>
	                                <td><span>组织编号：</span><span>${hrOrganizationElementForm.fdNo }</span></td>
	                                <td><span>组织名称：</span><span>${hrOrganizationElementForm.fdName }</span></td>
	                                <td><span>组织类型：</span>
		                                <span>
		                                	<xform:select property="fdOrgType" showStatus="view" value="${hrOrganizationElementForm.fdOrgType }">
												<xform:enumsDataSource enumsType="hr_organization_type" />
											</xform:select>
		                                </span>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td><span>上级组织：</span><span>${hrOrganizationElementForm.fdParentName }</span></td>
	                                <td><span>组织负责人：</span><span>${hrOrganizationElementForm.fdThisLeaderName }</span></td>
	                                <td><span>组织分管领导：</span><span>${hrOrganizationElementForm.fdBranLeaderName }</span></td>
	                            </tr>
	                            <tr>
	                                <td><span>组织简称：</span><span>${hrOrganizationElementForm.fdNameAbbr }</span></td>
	                                <td><span>启用日期：</span><span><xform:datetime property="fdCreateTime" dateTimeType="date" showStatus="view" /></span></td>
	                                <td><span>排序号：</span><span>${hrOrganizationElementForm.fdOrder }</span></td>
	                            </tr>
	                            <tr>
	                                <!-- <td><span>类型：</span><span>后台维护项</span></td> -->
	                                <td><span>虚拟组织：</span><span>${hrOrganizationElementForm.fdIsVirOrg }</span></td>
	                                <td><span>法人公司：</span><span>${hrOrganizationElementForm.fdIsCorp }</span></td>
	                            </tr>
	                            <tr>
	                                <td><span>组织描述：</span><span>${hrOrganizationElementForm.fdIsCorp }</span></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div class="ld-modal-footer">
	            <div class="ld-confirm" onclick="$dialog.hide(null);">关闭</div>
	        </div>
	    </div>
	</template:replace>
</template:include>
<script type="text/javascript">
	seajs.use(['lui/dialog','lui/jquery'], function(dialog,$) {
	    window.changeTab = function(showClassName, hideClassName){
	        $(hideClassName).removeClass('active');
	        $(hideClassName).hide();
	        $(showClassName).addClass('active')
	        $(showClassName).show();
	    }
	    $('.ld-lookList-main-tab div').click(function(){
	      $('.ld-lookList-main-tab div').removeClass('active');
	      $(this).addClass('active')
	    })
	});
</script>