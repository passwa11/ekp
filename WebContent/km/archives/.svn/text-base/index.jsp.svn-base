<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.km.archives.service.IKmArchivesMainService"%>
<template:include ref="default.list" spa="true">
    <template:replace name="title">
        <c:out value="${ lfn:message('km-archives:module.km.archives') }-${ lfn:message('km-archives:table.kmArchivesMain') }" />
    </template:replace>
    <template:replace name="nav">
        <!-- 新建按钮 -->
        <ui:combin ref="menu.nav.title">
            <ui:varParam name="title" value="${ lfn:message('km-archives:table.kmArchivesMain') }" />
            <ui:varParam name="operation">
				<ui:source type="Static">
					[
					{
						"text": "${lfn:message('km-archives:py.WoJieYueDe') }",
						"href": "/myBorrow",
						"router" : true,
						"icon": "lui_iconfont_navleft_archives_myBorrow"
					},
					{
						"text": "${ lfn:message('list.approval') }",
						"href": "/myApproval",
						"router" : true,
						"icon": "lui_iconfont_navleft_com_my_beapproval"
					},
					{
						"text": "${ lfn:message('list.approved') }",
						"href": "/myApproved",
						"router" : true,
						"icon": "lui_iconfont_navleft_com_my_approvaled"
					},
					{
						"text": "${lfn:message('km-archives:py.SuoYouJieYue') }",
						"href": "/allBorrow",
						"router" : true,
						"icon": "lui_iconfont_navleft_com_all"
					}
				]
				</ui:source>
			</ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
		       <!-- 档案管理 -->
		        <ui:content title="${lfn:message('km-archives:py.DangAnGuanLi')}">
		        	<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[
			  				{
			  					"text" : "${lfn:message('km-archives:py.DangAnLuRu')}",
								"href" :  "/listAll/myCreate",
								"router" : true,  					
			  					"icon" : "lui_iconfont_navleft_archives_input"
			  				},{
			  					"text" : "${lfn:message('km-archives:py.DangAnShenHe')}",
								"href" :  "/examineDoc",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_archives_examine"
			  				},{
			  					"text" : "${lfn:message('km-archives:py.DangAnJieYue')}",
								"href" :  "/myBorrowDetails",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_archives_borrow"
			  				}
			  				<kmss:authShow roles="ROLE_KMARCHIVES_APPRAISE,ROLE_KMARCHIVES_DESTROY">
				  				,{
				  					"text" : "${lfn:message('km-archives:py.DangAnJDorXH')}",
									"href" :  "/operation",
									"router" : true,		  					
				  					"icon" : "lui_iconfont_navleft_archives_appraisal"
				  				}
			  				</kmss:authShow>
			  				,{
			  					"text" : "${lfn:message('km-archives:py.DangAnJiLu')}",
								"href" :  "/record",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_archives_destroy"
			  				},{
			  					"text" : "${lfn:message('km-archives:py.DangAnKu')}",
								"href" :  "/allArchives",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_archives_all"
			  				}
			  				<kmss:authShow roles="ROLE_KMARCHIVES_PREFILE_MANAGER">
			  				,{
			  					"text" : "${lfn:message('km-archives:py.YuGuiDangKu')}",
								"href" :  "/preFileArchives",
								"router" : true,		  					
			  					"icon" : "lui_iconfont_navleft_archives_all"
			  				}
			  				</kmss:authShow>
			  				]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
		        </ui:content>
		        <!-- 其他操作 -->
		        <ui:content title="${ lfn:message('km-archives:py.QiTaCaoZuo') }" expand="true">
		        	<ui:combin ref="menu.nav.simple">
		  				<ui:varParam name="source">
		  					<ui:source type="Static">
		  					[
			  				{
			  					"text" : "${ lfn:message('km-archives:py.FeiQiXiang') }",
								"href" :  "/discard",
								"router" : true,  					
			  					"icon" : "lui_iconfont_navleft_com_discard"
			  				}
		  					<% if(com.landray.kmss.sys.recycle.util.SysRecycleUtil.isEnableSoftDelete("com.landray.kmss.km.archives.model.KmArchivesMain")) { %>			  				
			  				,{
			  					"text" : "${ lfn:message('sys-recycle:module.sys.recycle') }",
								"href" :  "/recover",
								"router" : true,
			  					"icon" : "lui_iconfont_navleft_com_recycle"
			  				}
			  				<% } %>
			  				<kmss:authShow roles="ROLE_KMARCHIVES_BACKSTAGE_MANAGER">
			  				,{
								"text" : "${ lfn:message('list.manager') }",
								"icon" : "lui_iconfont_navleft_com_background",
								"router" : true,
								"href" : "/management"
							 }
			  				</kmss:authShow>
			  				]
		  					</ui:source>
		  				</ui:varParam>
		  			</ui:combin>
		        </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
    	<ui:tabpanel id="kmArchivesPanel" layout="sys.ui.tabpanel.list" cfg-router="true">
    	<ui:content cfg-route="{path:'/myBorrow'}" id="kmArchivesBorrowContent" title="${lfn:message('km-archives:py.WoJieYueDe')}">
            <ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_borrow/index.jsp"></ui:iframe>
        </ui:content>
        <!-- 档案借阅 -->
        <ui:content cfg-route="{path:'/myBorrowDetails'}" id="kmArchivesDetailsContent" title="${lfn:message('km-archives:py.WoJieYueDe')}">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_details/index.jsp"></ui:iframe>
        </ui:content>
        <!-- 待我审的档案 -->
        <ui:content cfg-route="{path:'/myApproval'}" id="myApprovalContent" title="${lfn:message('list.approval') }">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_main/index.jsp"></ui:iframe>
        </ui:content>
        <!-- 我已审的档案 -->
        <ui:content cfg-route="{path:'/myApproved'}" id="myApprovedContent" title="${lfn:message('list.approved') }">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_main/index.jsp"></ui:iframe>
        </ui:content>
        <!-- 鉴定记录 -->
        <ui:content cfg-route="{path:'/record/appraise'}" id="kmArchivesAppraiseContent" title="${lfn:message('km-archives:py.JianDingJiLu')}">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_appraise/record.jsp"></ui:iframe>
        </ui:content>
        <!-- 销毁记录 -->
        <ui:content cfg-route="{path:'/record/destroy'}" id="kmArchivesDestroyContent" title="${lfn:message('km-archives:py.XiaoHuiJiLu')}">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_destroy/record.jsp"></ui:iframe>
        </ui:content>
        <!-- 档案库 -->
        <ui:content cfg-route="{path:'/allArchives'}" id="kmArchivesMainContent" title="${lfn:message('km-archives:py.DangAnKu')}">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_main/kmArchivesMain_index.jsp"></ui:iframe>
        </ui:content>
        <!-- 档案库 -->
        <ui:content cfg-route="{path:'/preFileArchives'}" id="kmArchivesPreFileContent" title="${lfn:message('km-archives:py.YuGuiDangKu')}">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_main/kmArchivesMain_prefile_index.jsp"></ui:iframe>
        </ui:content>
        <!-- 我录入的 -->
        <ui:content cfg-route="{path:'/listAll/myCreate'}" id="kmArchivesMyCreate" title="${lfn:message('km-archives:py.WoLuRuDe')}">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_main/myExamine.jsp?mydoc=myCreate"></ui:iframe>
        </ui:content>
        <!-- 档案录入 -->
        <ui:content cfg-route="{path:'/listAll/addArchives'}" id="kmArchivesMyDoc" title="${lfn:message('km-archives:py.DangAnLuRu')}">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_main/createDoc.jsp?mainModelName=com.landray.kmss.km.archives.model.KmArchivesMain&modelName=com.landray.kmss.km.archives.model.KmArchivesCategory&isSimpleCategory=true"></ui:iframe>
        </ui:content>
        <!-- 草稿箱 -->
        <%
			String DraftCount = ((IKmArchivesMainService)SpringBeanUtil.getBean("kmArchivesMainService")).getCount("draft",true);
			pageContext.setAttribute("draftTitle",ResourceUtil.getString("py.draftBox","km-archives")+"("+DraftCount+")");
		%>
        <ui:content cfg-route="{path:'/listAll/myDrafts'}" id="kmArchivesMyDrafts" title="${lfn:message('km-archives:py.draftBox')} ">
        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_main/draftBox.jsp"></ui:iframe>
        </ui:content>
        <!-- 档案鉴定 -->
        <kmss:authShow roles="ROLE_KMARCHIVES_APPRAISE">
	        <ui:content cfg-route="{path:'/operation/myAppraiseDetails'}" id="kmAppraiseContent" title="${lfn:message('km-archives:py.DangAnJianDing')}">
	        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_appraise/index.jsp"></ui:iframe>
	        </ui:content>
        </kmss:authShow>
        <!-- 档案销毁 -->
        <kmss:authShow roles="ROLE_KMARCHIVES_DESTROY">
	        <ui:content cfg-route="{path:'/operation/myDestroyDetails'}" id="kmDestroyContent" title="${lfn:message('km-archives:py.DangAnXiaoHui')}">
	        	<ui:iframe src="${LUI_ContextPath }/km/archives/km_archives_destroy/index.jsp"></ui:iframe>
	        </ui:content>
        </kmss:authShow>
        </ui:tabpanel>
        <script>
        </script>
    </template:replace>
    <template:replace name="script">
		<!-- JSP中建议只出现·安装模块·的JS代码，其余JS代码采用引入方式 -->
		<script type="text/javascript">
			seajs.use(['lui/framework/module'],function(Module){
				Module.install('kmArchives',{
					//模块变量
					$var : {},
					//模块多语言
					$lang : {
						'DiscardArchives' : '${lfn:message("km-archives:py.DiscardArchives")}',
						'DiscardBorrow' : '${lfn:message("km-archives:py.DiscardBorrow")}',
						'myBorrow' : '${lfn:message("km-archives:py.WoJieYueDe") }',
						'myApproval' : '${lfn:message("list.approval")}',
						'myApproved' : '${lfn:message("list.approved")}',
						'allBorrow' : '${lfn:message("km-archives:py.SuoYouJieYue")}',
						'addArchives' : '${lfn:message("km-archives:py.DangAnLuRu")}',
						'examineDoc' : '${lfn:message("km-archives:py.DangAnShenHe")}',
						'myBorrowDetails' : '${lfn:message("km-archives:py.DangAnJieYue")}',
						'myAppraiseDetails' : '${lfn:message("km-archives:py.DangAnJianDing")}',
						'myDestroyDetails' : '${lfn:message("km-archives:py.DangAnXiaoHui")}',
						'appraise' : '${lfn:message("km-archives:py.JianDingJiLu")}',
						'destroy' : '${lfn:message("km-archives:py.XiaoHuiJiLu")}',
						'due' : '${lfn:message("km-archives:py.JiJiangDaoQi")}',
						'expired' : '${lfn:message("km-archives:py.DaoQiDangAn")}',
						'discard' : '${lfn:message("km-archives:py.FeiQiXiang")}',
						'recover' : '${lfn:message("sys-recycle:module.sys.recycle")}',
						'allArchives' : '${lfn:message("km-archives:py.DangAnKu")}',
						'preFileArchives' : '${lfn:message("km-archives:py.YuGuiDangKu")}',
						'myDrafts' :'${draftTitle}'
					},
					//搜索标识符
					$search : ''
				});
			});
		</script>
		<!-- 引入JS -->
		<script type="text/javascript" src="${LUI_ContextPath}/km/archives/resource/js/index.js"></script>
	</template:replace>
</template:include>