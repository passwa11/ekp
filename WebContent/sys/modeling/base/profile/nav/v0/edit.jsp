<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/viewList.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/profile/nav/v0/css/nav.css" rel="stylesheet">
<link href="${LUI_ContextPath}/sys/modeling/base/resources/css/override.css" rel="stylesheet">
<template:include ref="config.edit" sidebar="no">
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<div class="lui_custom_list_boxs">
              <center>
                  <div class="lui_custom_list_box_content_col_btn" style="text-align: right;">
                  	<ui:button isForcedAddClass="true" styleClass="lui_custom_list_box_content_whith_btn" text="${lfn:message('sys-modeling-base:modeling.app.baseinfo')}" height="30" width="90"
                          onclick="editNav(this);" order="1">
		             </ui:button>
		             <ui:button isForcedAddClass="true" styleClass="lui_custom_list_box_content_whith_btn" text="${lfn:message('sys-modeling-base:modeling.access.path')}" height="30" width="90"
		                          onclick="redirectIndex(this);" order="1">
		             </ui:button>
					<c:if test="${modelingAppNavForm.method_GET=='edit'}">
						 <ui:button isForcedAddClass="true" styleClass="lui_custom_list_box_content_blue_btn" text="${lfn:message('button.save')}" height="30" width="90"
		                          onclick="Modeling_Submit('update');" order="2">
		               	</ui:button>
					</c:if>
					 <c:if test="${modelingAppNavForm.method_GET=='add'}">
						 <ui:button isForcedAddClass="true" styleClass="lui_custom_list_box_content_blue_btn" text="${lfn:message('button.save')}"  height="30" width="90"
		                          onclick="Modeling_Submit('save');" order="2">
		               	</ui:button>
					</c:if> 
                  </div>
              </center>
          </div>
	</template:replace>
	
    <template:replace name="content">
		
		<style>
            /*样式覆盖*/
            .tb_simple tbody tr td:last-child .inputContainer {
                width: 100%;
            }
            
             .tb_simple tbody tr td.td_normal_title{
                text-align: left;
            }

            .inputContainer input,
            .inputContainer textarea {
                width: 100%;
            }

            .inputselectsgl, .weui_switch {
                margin-left: 20px;
            }

            .inputselectsgl .input input {
                margin-left: 0;

            }
            .model-mask-panel-table .comp,
            .model-mask-panel-table input[type=text],
            .model-mask-panel-table textarea {
                font-size: 12px;
                color: #333333;
                line-height: 12px;
            }
            .model-mask-panel-table textarea{
                padding: 10px;
            }
            .inputContainer .textarea textarea {
                border: none;
                font-size: 12px;
                color: #333333;
                line-height: 12px;
            }

            .tb_simple .onlyRead input {
                border: none;
                font-size: 14px;
                color: #666666;
                line-height: 12px;
            }

            .description_txt {
                font-size: 12px;
                color: #999999;
                margin-left: 20px;
            }
            .model-mask-panel{
                background: #FFFFFF;
                border: 1px solid #DDDDDD;
                border-top: none;
                box-shadow: 0 0 6px 0 rgba(0,0,0,0.06);
                border-radius: 4px;
                border-radius: 4px;
            }
            .td_label0 > nobr {
                 margin: 0px;
                cursor: pointer;
                position:relative
            }
            .td_label0 > nobr > input {
                margin: 0;
                padding: 0 20px;
                width: auto;
                height: 40px;
                line-height: 40px;
                color: #666666;
                font-size: 14px;
                border: none !important;
                background-image: none !important;
                background-color: #F6F7FA !important;


            }
            /*tab选项卡样式覆盖*/
          #Label_Tabel  .td_label0{
                background-color: #F6F7FA;
                height: 40px;
                border: 1px solid #dddddd !important;
                border-bottom: 1px solid #dddddd !important;
            }
            .td_label0 .btnlabel1,
            .td_label0 > nobr > input.btnlabel2:hover{
                background: #fff !important;
                color: #4285f4;
                border-bottom:1px solid #FFFFFF !important;
                border-right:1px solid #DDDDDD !important;
                border-left:1px solid #DDDDDD !important;
                border-top:1px solid #DDDDDD !important;
                z-index: 6;
            }
            .td_label {
                 padding-top: 0px;
            }
            .pcIndexUrlPath,.mobileIndexUrlPath{
				margin-left:16px;
				margin-bottom:20px;            
            }
            
            .lui_toolbar_frame_float .lui_toolbar_content {
            	width: 100% !important;
            	max-width: 100% !important;
            }
            .lui_widget_iframe,.lui_modeling_nav,#navTabPanel{
            	height: 100%;
            }
            .lui_tabpanel_list_content_l{
            	padding-bottom: 50px;
    			box-sizing: border-box;
    			height: 100%;
            }
            #DIV_Tree{
            	height:92%;
            	padding-bottom: 90px!important;
    			box-sizing: border-box;
    		}
    		.nav_content_wrap{
    			background:none;
    		}
    		.lui_toolbar_frame_float_mark{
    			background-color: #f9fbfe
    		}
        </style>
		
        <html:form action="/sys/modeling/base/modelingAppNav.do" style="background-color:#fff;">
			<html:hidden property="fdApplicationId"/>
			<html:hidden property="fdId"/>
            <script>
                Com_IncludeFile("doclist.js|domain.js|jquery.js|treeview.js");
                Com_IncludeFile("custom_treeview.js", Com_Parameter.ContextPath + "sys/modeling/base/profile/nav/v0/container/", "js", true);
                Com_IncludeFile("nav.css", Com_Parameter.ContextPath + "sys/modeling/base/profile/nav/v0/", "css", true);
                Com_IncludeFile("dialog.css", Com_Parameter.ContextPath + "sys/modeling/base/resources/css/", "css", true);
            </script>
            <div class="nav_content_wrap">
            	<div class="nav_left">
		           	 <div class="nav_left_wrap">
		               <!--  <center style="background-color: #fff;padding-bottom: 102px;min-width:1025px;"> -->
		                <div style="clear: both;"></div>
		                <html:hidden property="fdAppId" value="${param.fdAppId }"/>
		                <html:hidden property="s_path" value="${param.s_path }"/>
		                <input type="hidden" name="fdAppName" value="${applicationName}"/>
		                <div class="lui_modeling_nav" style="width:380px;">
		                    <ui:tabpanel scroll="true" layout="sys.ui.tabpanel.list" id="navTabPanel">
		                        <!-- 业务表单 -->
		                        <ui:content title= '${lfn:message("sys-modeling-base:modeling.business.form")}'>
		                            <ui:iframe id="modeling_listview"
		                                       src="${LUI_ContextPath }/sys/modeling/base/profile/nav/v0/container/appListView.jsp?fdAppId=${JsParam.fdAppId}"></ui:iframe>
		                        </ui:content>
		                        <!-- 表单报表 -->
		                        <ui:content title='${lfn:message("sys-modeling-base:modeling.business.chart")}'>
		                            <ui:iframe id="modeling_rptview"
		                                       src="${LUI_ContextPath }/sys/modeling/base/profile/nav/v0/container/appListView.jsp?fdAppId=${JsParam.fdAppId}&type=rpt"></ui:iframe>
		                        </ui:content>
		                        <ui:content title='${lfn:message("sys-modeling-base:modeling.other.form")}'>
		                            <ui:iframe id="modeling_otherListview"
		                                       src="${LUI_ContextPath }/sys/modeling/base/profile/nav/v0/container/appListView.jsp?fdAppId=${JsParam.fdAppId}&type=otherListview"></ui:iframe>
		                        </ui:content>
		                        <ui:content title='${lfn:message("sys-modeling-base:modeling.other.charts")}'>
		                            <ui:iframe id="modeling_otherRptview"
		                                       src="${LUI_ContextPath }/sys/modeling/base/profile/nav/v0/container/appListView.jsp?fdAppId=${JsParam.fdAppId}&type=rpt&rptType=otherRptview"></ui:iframe>
		                        </ui:content>
		                  		   
		                    </ui:tabpanel>
		                </div>
					
					 <!-- 左移右移按钮 -->
	                <div class="lui_modeling_nav_opt_wrap">
	                    <div class="lui_modeling_nav_opt_content">
	                        <div class='lui_modeling_nav_option_add' title='${lfn:message("sys-modeling-base:modelingTransport.button.add")}' onClick='Modeling_OptionAdd();'>
	                            <div>${lfn:message("sys-modeling-base:modeling.add.right")}</div>
	                            <i></i>
	                        </div>
	                        <div class='lui_modeling_nav_option_delete' title='${lfn:message("sys-modeling-base:modeling.page.delete")}' onClick='Modeling_OptionDelete();'>
	                            <i></i>
	                            <div>${lfn:message("sys-modeling-base:modeling.add.left")}</div>
	                        </div>
	                    </div>
	                </div>
                
	                <!-- 菜单设置 -->
	                <div class="lui_modeling_nav">
	                    <div class="lui_modeling_nav_head">
	                        <div class="lui_modeling_nav_head_title">${applicationName}</div>
	                        <div class='lui_modeling_nav_head_add' title='${lfn:message("sys-modeling-base:modelingTransport.button.add")}' onClick='Modeling_AddNavContainer();'>${lfn:message("sys-modeling-base:button.add")}</div>
	                        <div class='lui_modeling_nav_head_reset' title='${lfn:message("sys-modeling-base:button.reset")}' onClick='Modeling_ResetNavContainer();'>${lfn:message("sys-modeling-base:button.reset")}</div>
	                    </div>
	                    <div id=DIV_Portal class="portaldiv lui_modeling_nav_portal" style="display: none">
	                    	<div class='title lui_modeling_nav_portal_link'>
	                    		<i title="${lfn:message("sys-modeling-base:modeling.suppory.linktoNav")}"></i>
	                    		<span class="">${lfn:message("sys-modeling-base:modeling.portal.link")}</span>
	                    	</div>
	                    	<div class='btn lui_modeling_nav_portal_title' onclick="generateUrl()">
	                    		<span id='portalText'>${lfn:message("sys-modeling-base:modelingTransport.button.add")}</span>
	                    		<i id='portalIcon' style="display: none"></i>
	                    	</div>
	                    	<input type='hidden' name='protalTmpURL'>
	                    	<input type='hidden' name='protalTmpNodeName'>
	                    </div>
	                    <div id=DIV_Tree class="treediv lui_modeling_nav_tree_right" style="margin-top: 5px"></div>
	                    <div id=Empty_Page style="display: none" class="empty_page">
	                    	<img style="margin-top:50px" src='${LUI_ContextPath }/sys/modeling/base/resources/images/nav/empty.png'>
	                    	<div style="margin-top:20px">${lfn:message("sys-modeling-base:modeling.join.left")}</div>
	                    </div>
	                </div>


	                <html:hidden property="fdId"/>
	                <html:hidden property="fdNavContent"/>
	            </center>
	            </div>
	            </div>
		             <!-- 可视区 -->
			        <div class="modeling_app_nav_right">
			        	<html:hidden property="docSubject"/>
			        	<html:hidden property="fdOrder"/>
			        	<html:hidden property="authReaderIds"/>
			        	<html:hidden property="authReaderNames"/>
			        	<%-- <div class="modeling_app_nav_right_head">
			        		<div class="modeling_app_right_title">配置</div>
			        		<div class="modeling_app_right_navs_item_l modeling_app_right_expand_item" title="收起">						
        					</div>
			        	</div>
			        	 <!-- 基本信息 -->
							<div class="model-mask-panel-table" <c:if test="${modelingAppNavForm.method_GET=='edit'}">style="display:none;"</c:if>>
								<table class="tb_simple modeling_form_table operationMainForm" width="100%" mdlng-prtn-mrk="regionTable">
									<tbody>
										<tr>
											<td class="td_normal_title" width=100%>
												<span class="title_wrap">
												${lfn:message('sys-modeling-base:modelingAppNav.docSubject')}:
												</span>
											</td>
										</tr>
										<tr>
											<td width=100%>
												<div class="inputContainer">
													<xform:text property="docSubject" required="true"/>
												</div>
											</td>
										</tr>
										<tr>
											<td class="td_normal_title" width=100%>
												<span class="title_wrap">
												${lfn:message('sys-modeling-base:modelingAppNav.fdOrder')}:
												</span>
											</td>
										</tr>
										<tr>
											<td width=100%>
												<div class="inputContainer">
													<xform:text property="fdOrder"/>
												</div>
											</td>
										</tr>
										<tr>
											<td class="td_normal_title">
												<span class="title_wrap">
												${lfn:message('sys-modeling-base:modelingAppNav.fdUrl')}:
												</span>
											</td>
											<td width=35%>
												<div class="inputContainer" style="width:100%;" >
													<a name="__fdUrl" href="javascript:void(0);" onclick="redirectIndex(this)"></a>
													<xform:text property="fdUrl"  showStatus="view"/>
												</div>
											</td>
										</tr>
										<tr>
											<!-- 可访问者 -->
											<td class="td_normal_title" width=100%>
												${lfn:message('sys-modeling-base:modelingAppNav.fdAuthReaders')}:
											</td>
										</tr>
										<tr>
											<td width=100%>
												<xform:address textarea="true" mulSelect="true" propertyId="authReaderIds" propertyName="authReaderNames" orgType="ORG_TYPE_ALL" style="width:100%;height:90px;" >
												</xform:address>
												<div style="color: #999999;">（为空所有人可以访问）</div>
											</td>
										</tr>
									</tbody>
								</table>
							</div> --%>
					    <div class="modeling_app_nav_preview model-sidebar" id="nav_preview">
							<div class="modeling_app_nav_preview_head" id="nav_preview_head">
				        		<div class="modeling_app_nav_title">${lfn:message("sys-modeling-base:modeling.preview.image")}</div>
				        	</div>
				        	<div class="modeling_app_nav_preview_content" id="nav_preview_content">
				        		<div class="nav-left_head">${lfn:message("sys-modeling-base:modeling.left.nav")}</div>
				        		<ul class="model-sidebar-wrap" id="nav_preview_item_wrap">
				        		</ul>
				        	</div>
			        	</div>
			        	<div id="nav_preview_empty" style="display: none">
			        		<img style="margin-top:50px" src='${LUI_ContextPath }/sys/modeling/base/resources/images/nav/empty.png'>
	                    	<div style="margin-top:20px">${lfn:message("sys-modeling-base:modeling.no.set")}</div>
			        	</div>
			        </div>
		        </div>
<%--			126351 规避表单隐式提交--%>
			<input type="text" style="display:none" />
			<html:hidden property="docCreatorId"/>
			<html:hidden property="docCreateTime"/>
        </html:form>
        
        <script type="text/javascript">
            var modeling_validation = $KMSSValidation();
            window.__LISTVIEWINFO = {
                "listviewIds": "${appListviewIds}",
                "listviewNames": "${appListviewNames}"
            };
            var applicationName = '${applicationName}';
            var fdAppId = '${param.fdAppId }';
            var fdNavId = '${modelingAppNavForm.fdId}';
            var __edit = "${modelingAppNavForm.method_GET}";
            var lang = {
				buttonAdd: '${lfn:message("sys-modeling-base:modelingTransport.button.add")}',
				selLeftNode: '${lfn:message("sys-modeling-base:modeling.select.left.node")}',
				fillTtile: '${lfn:message("sys-modeling-base:modeling.fill.title")}',
				maxLimit3: '${lfn:message("sys-modeling-base:modeling.max.limit.3")}',
				selSecondNode: '${lfn:message("sys-modeling-base:modeling.select.second.node")}',
				selDelNode: '${lfn:message("sys-modeling-base:modeling.select.delete.node")}',
				noDelRootNode: '${lfn:message("sys-modeling-base:modeling.no.del.rootNode")}',
				suerDel: '${lfn:message("sys-modeling-base:modeling.sure.del")}',
				nameRrequired: '${lfn:message("sys-modeling-base:modeling.name.required")}',
				unfold: '${lfn:message("sys-modeling-base:modelingApplication.unfold")}',
				putAway: '${lfn:message("sys-modeling-base:modelingApplication.putAway")}',
				baseinfo: '${lfn:message("sys-modeling-base:modeling.app.baseinfo")}',
				selectPage: '${lfn:message("sys-modeling-base:modeling.select.page")}',
				enterNodeName: '${lfn:message("sys-modeling-base:modeling.enter.node.name")}',
				edit: '${lfn:message("sys-modeling-base:modeling.page.edit")}',
				down: '${lfn:message("sys-modeling-base:modelingTransport.button.down")}',
				up: '${lfn:message("sys-modeling-base:modelingTransport.button.up")}',
				delete: '${lfn:message("sys-modeling-base:modeling.page.delete")}',
			}
            Com_IncludeFile("nav.js", Com_Parameter.ContextPath + "sys/modeling/base/profile/nav/v0/", "js", true);
        </script>
        
    </template:replace>
</template:include>
