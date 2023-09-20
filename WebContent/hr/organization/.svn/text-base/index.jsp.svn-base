<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>

<template:include ref="default.list" spa="true" rwd="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('hr-organization:module.hr.organization') }" />
    </template:replace>
    <template:replace name="head">
    	<style>
			.lui_list_body_frame{
				padding:10px 20px!important;
			}
			.lui_tabpanel_list_navs_c{
				background-color:unset!important;
				border-bottom:none!important;
				line-height:0;

			}
			.lui_tabpanel_list_navs_item_c .lui_tabpanel_navs_item_title{
				padding:11px 10px 3px!important;
			}
			body .lui_tabpanel_list_navs_item_selected{
				border:none!important;
			}					
		</style>
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('hr-organization:table.hrOrganizationElement') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/hr/organization/hr_organization_element/hrOrganizationElement.do"} ]
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('hr-organization:py.ZuZhiGuanLi') }">
                	<ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [ 
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.ZuZhiJiaGouWei') }",
                                		"href" : "/org",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_com_all"
                                	},
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.ZuZhiBianZhiGuan')}",
                                		"href" : "/compile",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_review_track"
                                	},
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.ZuZhiJiaGouTu') }",
                                		"href" : "/feedback",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_review_feedback"
                                	},{
										"text" : "${lfn:message('hr-organization:py.YiTingYongZuZhi') }",
										"href" :  "/invalidOrg",
										"router" : true,
										"icon" : "lui_iconfont_navleft_subordinate"
									 }
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                <ui:content title="${ lfn:message('hr-organization:py.GangWeiGuanLi') }" expand="false">
                	<ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [ 
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.GangWeiGuanLi') }",
                                		"href" : "/hrOrgPost",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_com_all"
                                	},
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.YiTingYongGangWei') }",
                                		"href" : "/invalidPost",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_com_all"
                                	},
                                	<kmss:authShow roles="ROLE_HRORGANIZATION_ORG_POST_SEQ;ROLE_HRORGANIZATION_ORG_ADMIN">
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.GangWeiXuLie')}",
                                		"href" : "/hrOrgPostSeq",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_review_track"
                                	},
                                	</kmss:authShow>
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.JianGangGuanLi') }",
                                		"href" : "/hrOrgConPost",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_review_feedback"
                                	}
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                <kmss:authShow roles="ROLE_HRORGANIZATION_ORG_RANK;ROLE_HRORGANIZATION_ORG_ADMIN">
                <ui:content title="${ lfn:message('hr-organization:py.ZhiDengZhiJi') }" expand="false">
                	<ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [ 
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.ZhiDeng') }",
                                		"href" : "/hrOrgGrade",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_com_all"
                                	},
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.ZhiJi')}",
                                		"href" : "/hrOrgRank",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_review_track"
                                	}
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                </kmss:authShow>
                <kmss:authShow roles="ROLE_HRORGANIZATION_ORG_STAFFING_LEVEL;ROLE_HRORGANIZATION_ORG_ADMIN">
                <ui:content title="${ lfn:message('hr-organization:py.ZhiWuGuanLi') }" expand="false">
                	<ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [ 
                                	{ 
                                		"text" : "${ lfn:message('hr-organization:py.ZhiWuGuanLi') }",
                                		"href" : "/staffingLevel",
                                		"router" : true,
                                		"icon" : "lui_iconfont_navleft_com_all"
                                	}
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                </kmss:authShow>
                <kmss:authShow roles="ROLE_HRORGANIZATION_SETTING">
                <ui:content title="${ lfn:message('hr-organization:py.QiTaCaoZuo') }" expand="false">
                    <ui:combin ref="menu.nav.simple">
                        <ui:varParam name="source">
                            <ui:source type="Static">
                                [
	                                {
	                                 "text" : "${ lfn:message('hr-organization:py.HouTaiPeiZhi') }",
	                                 "href":"/management",
	                                 "router" : true,
	                                 "icon" : "lui_iconfont_navleft_com_background" 
	                                  } 
                                ]
                            </ui:source>
                        </ui:varParam>
                    </ui:combin>
                </ui:content>
                </kmss:authShow>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
	        <ui:tabpanel id="hrOrgPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
	        	<!-- 组织架构维护 -->
	            <ui:content id="HrOrgContent" title="" cfg-route="{path:'/org'}">
	                <iframe id="hrOrgIframe" frameborder="no" border="0" scrolling="no" height="650" class="lui_widget_iframe"  src="${LUI_ContextPath }/hr/organization/hr_organization_tree/index.jsp"></iframe>
	                <script>
	                	var orgIframeHeight = $(document).height()-110
	                	$("#hrOrgIframe").height(orgIframeHeight)
	                	window.orgIframeHeight = orgIframeHeight
	                </script>
	            </ui:content>
	            <!-- 组织架构编制-->
	            <ui:content id="HrCompileContent" title="${lfn:message('hr-organization:py.ZuZhiBianZhiGuan') }" cfg-route="{path:'/compile'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_compile/index.jsp"></ui:iframe>
	            </ui:content>
	            <!-- 组织架构图 -->
	<%--             <ui:content id="HrOrgContent" title="${lfn:message('hr-organization:table.hrOrganizationDuty') }" cfg-route="{path:'/org'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_staffing_level/index.jsp"></ui:iframe>
	            </ui:content> --%>
	            <!-- 已停用组织 -->
	            <ui:content id="HrInvalidOrgContent" title="${lfn:message('hr-organization:py.YiTingYongZuZhi') }" cfg-route="{path:'/invalidOrg'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_element/disable/index.jsp"></ui:iframe>
	            </ui:content>
	            
	        	<!-- 职务管理 -->
	            <ui:content id="HrOrgStaffingLevelContent" title="${lfn:message('hr-organization:table.hrOrganizationDuty') }" cfg-route="{path:'/hrOrgStaffingLevel'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_staffing_level/index.jsp"></ui:iframe>
	            </ui:content>
	            <!-- 职等 -->
	            <ui:content id="HrOrgGradeContent" title="${lfn:message('hr-organization:table.hrOrganizationGrade') }" cfg-route="{path:'/hrOrgGrade'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_grade/index.jsp"></ui:iframe>
	            </ui:content>
	            <!-- 职级 -->
	            <ui:content id="HrOrgRankContent" title="${lfn:message('hr-organization:table.hrOrganizationRank') }" cfg-route="{path:'/hrOrgRank'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_rank/index.jsp"></ui:iframe>
	            </ui:content>
	            <!-- 兼岗管理-->
	            <ui:content id="HrOrgConPostContent" title="${lfn:message('hr-organization:table.hrOrganizationConPost') }" cfg-route="{path:'/hrOrgConPost'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_con_post/index.jsp"></ui:iframe>
	            </ui:content>
	            <!-- 岗位序列 -->
	            <ui:content id="HrOrgPostSeqContent" title="${lfn:message('hr-organization:table.hrOrganizationPostSeq') }" cfg-route="{path:'/hrOrgPostSeq'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_post_seq/index.jsp"></ui:iframe>
	            </ui:content>
	            <!-- 岗位管理 -->
	            <ui:content id="HrOrgPostContent" title="${lfn:message('hr-organization:py.GangWeiGuanLi') }" cfg-route="{path:'/hrOrgPost'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_post/index.jsp"></ui:iframe>
	            </ui:content>
	            <!-- 已停用岗位 -->
	            <ui:content id="HrInvalidPostContent" title="${lfn:message('hr-organization:py.YiTingYongGangWei') }" cfg-route="{path:'/invalidPost'}">
	                <ui:iframe src="${LUI_ContextPath }/hr/organization/hr_organization_post/disable/index.jsp"></ui:iframe>
	            </ui:content>
	            
	        </ui:tabpanel>
     </template:replace>
       <% 
        	pageContext.setAttribute( "userId", UserUtil.getKMSSUser().getUserId()); 
        	pageContext.setAttribute( "depId", UserUtil.getKMSSUser().getDeptId()); 
        	if(UserUtil.getUser().getFdParentOrg()!=null){ 
        		pageContext.setAttribute( "orgId", UserUtil.getUser().getFdParentOrg().getFdId());
			}else{ 
				pageContext.setAttribute( "orgId", null); 
			} 
        	pageContext.setAttribute( "authAreaId", UserUtil.getKMSSUser().getAuthAreaId()); 
        %>
      <template:replace name="script">
	       <script type="text/javascript">
	        seajs.use(['lui/framework/module'], function(Module) {
	            Module.install('hrOrganization', {
	                //模块变量
	                $var: {
	                	userId: '${userId}',
	                          depId: '${depId}',
	                          orgId: '${orgId}',
	                          authAreaId: '${authAreaId}'
	                },
	                //模块多语言
	                $lang: {
	                	pageNoSelect : '${lfn:message("page.noSelect")}',
						optSuccess : '${lfn:message("return.optSuccess")}',
						optFailure : '${lfn:message("return.optFailure")}',
						buttonDelete : '${lfn:message("button.deleteall")}',
	                },
	                //搜索标识符
	                $search: ''
	            });
	        });
	        </script>
      		<script type="text/javascript" src="${LUI_ContextPath}/hr/organization/resource/js/index.js"></script>
  	 </template:replace>
    
</template:include>