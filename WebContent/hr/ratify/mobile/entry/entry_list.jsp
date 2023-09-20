<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<%@ page import="com.landray.kmss.hr.staff.service.spring.HrStaffEntryValidator,
				 com.landray.kmss.sys.authentication.intercept.ValidatorRequestContext,
				 com.landray.kmss.util.SpringBeanUtil"%>

<template:include ref="mobile.list">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="../resource/css/entry_list.css"></link>
		<%-- <mui:min-file name="mui-resource-view.css"/> --%>
		<style>
			.mblTabBar li:first-child .mblTabBarButtonLabel{
				color:#999FB7!important;
			}
			.mblTabBarTabBar{
				box-shadow: 0 6px 10px 0 rgba(59,68,93,0.50);
			}
		</style>
		<script src="<%=request.getContextPath()%>/hr/ratify/mobile/resource/js/rem/rem.js"></script>
	</template:replace>
	<template:replace name="title">
			<c:out value="${ lfn:message('hr-ratify:mobile.hrStaffEntry.list') }" />
	</template:replace>
	<template:replace name="content">
		<div id="scrollView"  class=""
			data-dojo-type="mui/view/DocScrollableView" data-dojo-mixins="mui/list/StoreScrollableView">
			<div class="entrySearch">
				<input text="text" placeholder="员工姓名、手机号码" /><span class="icon"></span>
			</div>
			
			<%
				HrStaffEntryValidator _val = (HrStaffEntryValidator)SpringBeanUtil.getBean("hrStaffEntryValidator");
				ValidatorRequestContext _ctx = new ValidatorRequestContext();
				boolean _flag = UserUtil.getKMSSUser().isAdmin()||UserUtil.checkRole("ROLE_HRSTAFF_READALL")||_val.validate(_ctx);
				if(_flag){ 
			%>
				<ul 
					data-dojo-type="mui/list/JsonStoreList" 
					data-dojo-mixins="hr/ratify/mobile/resource/js/list/ComplexLItemListMixin"
					data-dojo-props="url:'/hr/staff/hr_staff_entry/hrStaffEntry.do?method=list&q.fdStatus=1&q.j_path=%2FentryStatus1&orderby=docCreateTime&ordertype=down',lazy:false">
				</ul>
			<%}else{ %>
				<li class="muiListNoData" style="height: 550px; line-height: 550px;">
					<div class="muiListNoDataArea">
						<div class="muiListNoDataInnerArea">
							<div class="muiListNoDataContainer muiListNoDataIcon">
								<i class="mui mui-message"></i>
							</div>
						</div>
						<div class="muiListNoDataTxt"><bean:message bundle="sys-mobile" key="mui.list.msg.noData"/></div>
					</div>
				</li>
			<%}%>
			
			<div data-dojo-type="mui/tabbar/TabBar" fixed="bottom"  data-dojo-props='fill:"grid"'>
				<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext" onclick="scanQR();"
				 		data-dojo-props='colSize:1,icon1:"",align:"center",moveTo:"carInfoView",transition:"slide"'>
					<span class="entry_scanner"></span><bean:message bundle="hr-ratify" key="mobile.entry.scan.qr"/>
				</li>
				<kmss:authShow roles="ROLE_HRSTAFF_CREATE">
					<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnNext" onclick="addDoc();"
					 		data-dojo-props='colSize:1,icon1:"",align:"center",moveTo:"carInfoView",transition:"slide"'>
						<span class="entry_addenterh"></span>${lfn:message('hr-ratify:mobile.hrStaffEntry.create')}
					</li>
				</kmss:authShow>
			</ul>
		</div>
		
			<script>
			require(['dojo/dom-style', 'dojo/dom', 'dojo/topic',"dojo/query",'dojo/on'], function(domStyle, dom, topic,query,on){
				function handleNavItemChange(tabIndex){
					switch(tabIndex){
						case 0:
							try{
								domStyle.set(dom.byId('btnAddCertification'), 'display', 'inherit');
							}catch(err){}
							try{
								domStyle.set(dom.byId('btnAddBorrowApply'), 'display', 'none');
							}catch(err){}
							break;
						case 1:
							try{
								domStyle.set(dom.byId('btnAddCertification'), 'display', 'none');
							}catch(err){}
							try{
								domStyle.set(dom.byId('btnAddBorrowApply'), 'display', 'inherit');
							}catch(err){}
							break;
						default: break;
					}
				}
				topic.subscribe('/mui/navitem/_selected', function(tab, data){
					handleNavItemChange(tab.tabIndex);
				});
				on(query(".entrySearch"),"input",function(e){
					if(e.target.value){
						topic.publish('/mui/list/searchReload',{"keyword":e.target.value});
					}else{
						topic.publish('/mui/list/searchReload',{"keyword":""});
					}
					
				})
				
				handleNavItemChange(parseInt(localStorage.getItem("swapIndex:" + location.pathname)));
				handleNavItemChange(parseInt("${ param.filter }") - 1);
				
				window.addDoc = function(){
					window.location.href = '${LUI_ContextPath}/hr/staff/hr_staff_entry/hrStaffEntry.do?method=addEntryMobile';
				};
				
				window.scanQR = function(){
					window.location.href = '${LUI_ContextPath}/hr/ratify/mobile/entry/entry_qr.jsp';
				};
			});
			</script>	
			
			
	</template:replace>
</template:include>
