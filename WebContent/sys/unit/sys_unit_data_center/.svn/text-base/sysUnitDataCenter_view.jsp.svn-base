<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil" %>
    
        <%
            pageContext.setAttribute("currentPerson", UserUtil.getKMSSUser().getUserId());
        pageContext.setAttribute("currentPost", UserUtil.getKMSSUser().getPostIds());
        pageContext.setAttribute("currentDept", UserUtil.getKMSSUser().getDeptId());
        if(UserUtil.getUser().getFdParentOrg() != null) {
            pageContext.setAttribute("currentOrg", UserUtil.getUser().getFdParentOrg().getFdId());
        } else {
            pageContext.setAttribute("currentOrg", "");
        } %>
    
    <template:include ref="default.view">
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
            <script type="text/javascript">
                var formInitData = {
                };
                var messageInfo = {
                };
                Com_IncludeFile("swf_attachment.js", "${KMSS_Parameter_ContextPath}sys/attachment/js/", "js", true);
            </script>
        </template:replace>
        <template:replace name="title">
            <c:out value="${sysUnitDataCenterForm.fdName} - " />
            <c:out value="${ lfn:message('sys-unit:table.sysUnitDataCenter') }" />
        </template:replace>
        <template:replace name="toolbar">
            <script>
                function deleteDoc(delUrl) {
                    seajs.use(['lui/dialog'], function(dialog) {
                        dialog.confirm('${ lfn:message("page.comfirmDelete") }', function(isOk) {
                            if(isOk) {
                                Com_OpenWindow(delUrl, '_self');
                            }
                        });
                    });
                }
                
                Com_IncludeFile("security.js");
                Com_IncludeFile("domain.js");

            </script>
            <ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3">
            	<kmss:auth requestURL="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=editPassword&fdId=${param.fdId}">
               		<ui:button text="修改密码" onclick="Com_OpenWindow('sysUnitDataCenter.do?method=editPassword&fdId=${JsParam.fdId}','_blank');" order="2" />
				</kmss:auth>
                <!--edit-->
                <kmss:auth requestURL="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=edit&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('sysUnitDataCenter.do?method=edit&fdId=${param.fdId}','_self');" order="2" />
                </kmss:auth>
                <!--delete-->
                <kmss:auth requestURL="/sys/unit/sys_unit_data_center/sysUnitDataCenter.do?method=delete&fdId=${param.fdId}">
                    <ui:button text="${lfn:message('button.delete')}" onclick="deleteDoc('sysUnitDataCenter.do?method=delete&fdId=${param.fdId}');" order="4" />
                </kmss:auth>
                <ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();" />
            </ui:toolbar>
        </template:replace>
        <template:replace name="path">
            <ui:menu layout="sys.ui.menu.nav">
                <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_self" />
                <ui:menu-item text="${ lfn:message('sys-unit:table.sysUnitDataCenter') }" href="/sys/unit/sys_unit_data_center/" target="_self" />
            </ui:menu>
        </template:replace>
        <template:replace name="content">

	        <div class='lui_form_title_frame'>
	            <div class='lui_form_subject'>
	                ${lfn:message('sys-unit:table.sysUnitDataCenter')}
	            </div>
	            <div class='lui_form_baseinfo'>
	            </div>
	        </div>
	        <table class="tb_normal" width="100%">
	        	<tr class="data_center_tr_height">
                    <td class="td_normal_title" width="15%">
                        ${lfn:message('sys-dec:sysDecDataCenter.fdType')}
                    </td>
                    <td colspan="3" width="85.0%">
                        <%-- 系统类型--%>
                        <div id="_xform_fdType" _xform_type="radio">
                            <xform:radio property="fdType" htmlElementProperties="id='fdType'" showStatus="view">
                                <xform:enumsDataSource enumsType="sys_unit_data_center_type" />
                            </xform:radio>
                        </div>
                    </td>
                </tr>
	            <tr class="data_center_tr_height">
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-unit:sysUnitDataCenter.fdName')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <%-- 系统名称--%>
	                    <div id="_xform_fdName" _xform_type="text">
	                        <xform:text property="fdName" showStatus="view" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
	            <tr class="data_center_tr_height">
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-unit:sysUnitDataCenter.fdAppkey')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <%-- 系统编号--%>
	                    <div id="_xform_fdAppkey" _xform_type="text">
	                        <xform:text property="fdAppkey" showStatus="view" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
	            <tr class="data_center_tr_height">
		            <td class="td_normal_title" width="15%">
		                 ${lfn:message('sys-unit:sysUnitDataCenter.fdDomain')}
		            </td>
	                <td colspan="3" width="85.0%">
		                 <%-- 系统域名--%>
		                 <div id="_xform_fdDomain" _xform_type="text">
		                     <xform:text property="fdDomain" showStatus="view" style="width:38.5%;" />
		                 </div>
		            </td>
	            </tr>
	            <tr class="data_center_tr_height">
		            <td class="td_normal_title" width="15%">
		                 ${lfn:message('sys-unit:sysUnitDataCenter.fdIp')}
		            </td>
	                <td colspan="3" width="85.0%">
		                 <%-- 系统域名--%>
		                 <div id="_xform_fdIp" _xform_type="text">
		                     <xform:text property="fdIp" showStatus="view" style="width:38.5%;" />
		                 </div>
		            </td>
	            </tr>
	            <tr class="data_center_tr_height">
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-unit:sysUnitDataCenter.fdAccount')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <%-- 账号--%>
	                    <div id="_xform_fdAccount" _xform_type="text">
	                        <xform:text property="fdAccount" showStatus="view" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
	            <tr class="data_center_tr_height">
		            <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-unit:sysUnitDataCenter.fdDataType')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <div id="_xform_fdDataType" _xform_type="radio">
	                        <xform:radio property="fdDataType" htmlElementProperties="id='fdDataType'" showStatus="view">
                            	<xform:simpleDataSource value="xml">XML格式</xform:simpleDataSource>
								<xform:simpleDataSource value="json">JSON格式</xform:simpleDataSource>
                            </xform:radio>
	                    </div>
	                </td>
	            </tr>
	            <tr class="data_center_tr_height">
		            <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-unit:sysUnitDataCenter.fdFileType')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <div id="_xform_fdFileType" _xform_type="radio">
	                        <xform:radio property="fdFileType" htmlElementProperties="id='fdFileType'" showStatus="view">
                                <xform:simpleDataSource value="base64">Base64</xform:simpleDataSource>
								<xform:simpleDataSource value="rest">Rest接口下载</xform:simpleDataSource>
                            </xform:radio>
	                    </div>
	                </td>
	            </tr>
				<tr class="data_center_tr_height">
					<td class="td_normal_title" width="15%">
						开通访问策略模块
					</td>
					<td colspan="3" width="85.0%">
						<div id="_xform_" _xform_type="checkbox">
							<xform:checkbox property="fdRestModels" htmlElementProperties="id='fdRestModel'"
											showStatus="view" subject="开通访问策略模块">
								<xform:customizeDataSource
										className="com.landray.kmss.sys.unit.service.spring.SysUnitDataCenterRestModelData"/>
							</xform:checkbox>
						</div>
					</td>
				</tr>
	            <tr class="data_center_tr_height">
		            <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-unit:sysUnitDataCenter.fdIsAvailable')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <%-- 是否有效--%>
	                    <div id="_xform_fdIsAvailable" _xform_type="radio">
	                        <xform:radio property="fdIsAvailable" htmlElementProperties="id='fdIsAvailable'" showStatus="view">
	                            <xform:enumsDataSource enumsType="common_yesno" />
	                        </xform:radio>
	                    </div>
	                </td>
	            </tr>
	            <tr class="data_center_tr_height">
	                <td class="td_normal_title" width="15%">
	                    ${lfn:message('sys-unit:sysUnitDataCenter.fdDesc')}
	                </td>
	                <td colspan="3" width="85.0%">
	                    <%-- 描述--%>
	                    <div id="_xform_fdDesc" _xform_type="textarea">
	                        <xform:textarea property="fdDesc" showStatus="view" style="width:95%;" />
	                    </div>
	                </td>
	            </tr>
	        </table>
        </template:replace>

    </template:include>