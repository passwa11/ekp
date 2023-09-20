<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<% pageContext.setAttribute("currentUser", UserUtil.getKMSSUser()); %>

<template:include ref="maxhub.list">

	<template:replace name="head">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/km/imeeting/maxhub/resource/css/map.css?s_cache=${MUI_Cache}">
	</template:replace>

	<template:replace name="content">
	
	  	<!-- 主体内容区 Starts -->
		<section class="mhui-main-content">
			<div class="mhui-row">
			    <div class="mhui-col mhui-col-xs-12 meetingMapWrapper">
			    	
			    	<div class="meetingMapSidebar" id="meetingMapLeftSideBar" data-pos="left">
			    		<div class="meetingMapSidebarToggler" onclick="toggleSidebar('left')"><div>筛</div><div>选</div><div><span class="triangle"></span></div></div>
			    		
			    		<div class="meetingMapSidebarTable" id="meetingLeftFilter" data-dojo-type="mhui/form/Form">
							<div class="meetingMapSearchBar" 
								data-dojo-type="mhui/form/SearchBar"
								data-dojo-props="name:'fdName',
									placeholder:'搜索会议名称',
									onSearch:'searchMeeting'">
							</div>
							<div class="meetingMapResetBar">
								<span>筛选：</span>
								<span onclick="resetMeetingFilter('left')">清空筛选</span>
							</div>
				    		<table>
				    			<tr>
				    				<td class="label">会议状态：</td>
				    				<td>
				    					<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-props="
								    			name:'meetingStatus',
								    			value:'',
								    			defaultValue: '',
								    			options:[
								    				{text:'不限',value:''},
								    				{text:'未召开',value:'unHold'},
								    				{text:'进行中',value:'holding'},
								    				{text:'已召开',value:'hold'},
								    				{text:'已取消',value:'41'}]">
								    	</div>
				    				</td>
				    			</tr>
				    			<tr>
				    				<td class="label">所属角色：</td>
				    				<td>
				    					<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-props="
								    			name:'role',
								    			value:'0',
								    			defaultValue: '0',
								    			options:[
								    				{text:'不限',value:'0'},
								    				{text:'我发起的',value:'5'},
								    				{text:'我组织的',value:'2'},
								    				{text:'我主持的',value:'1'},
								    				{text:'我纪要的',value:'3'},
								    				{text:'我参与的',value:'4'}]">
								    	</div>
				    				</td>
				    			</tr>
								<tr>
				    				<td class="label">会议时间：</td>
				    				<td>
										<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-props="
								    			name:'fdHoldDate',
								    			value:'0',
								    			defaultValue: '0',
								    			options:[
								    				{text:'不限',value:'0'},
								    				{text:'今天',value:'1'},
								    				{text:'本周',value:'2'},
								    				{text:'本月',value:'3'},
								    				{text:'本季',value:'4'},
								    				{text:'本年',value:'5'},
								    				{text:'上一月',value:'6'},
								    				{text:'上一年',value:'7'}]">
								    	</div>
				    				</td>
				    			</tr>
								<tr>
				    				<td class="label">会议类别：</td>
				    				<td>
							    		<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-mixins="km/imeeting/maxhub/resource/js/TemplateRadioMixin"
							    			data-dojo-props="
								    			name:'fdTemplate',
								    			value:'',
								    			defaultValue: ''"></div>
				    				</td>
				    			</tr>
								<tr>
				    				<td class="label">会议室分类：</td>
				    				<td>
							    		<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-mixins="km/imeeting/maxhub/resource/js/PlaceRadioMixin"
							    			data-dojo-props="
								    			name:'fdPlace',
								    			value:'',
								    			defaultValue: ''"></div>
				    				</td>
				    			</tr>
				    		</table>

						</div>
			    	</div>
			    	
			    	<div class="meetingMapSidebar active" id="meetingMapRightSideBar" data-pos="right">
			    		<div class="meetingMapSidebarToggler" data-pos="right" onclick="toggleSidebar('right')"><div>筛</div><div>选</div><div><span class="triangle"></span></div></div>
			    		
			    		<div class="meetingMapSidebarTable" id="meetingRightFilter" data-dojo-type="mhui/form/Form">
							<div class="meetingMapSearchBar" 
								data-dojo-type="mhui/form/SearchBar"
								data-dojo-props="name:'fdName',
									placeholder:'搜索会议名称',
									onSearch:'searchMeeting'">
							</div>
							<div class="meetingMapResetBar">
								<span>筛选：</span>
								<span onclick="resetMeetingFilter('right')">清空筛选</span>
							</div>
				    		<table>
				    			<tr>
				    				<td class="label">会议状态：</td>
				    				<td>
				    					<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-props="
								    			name:'meetingStatus',
								    			value:'',
								    			defaultValue: '',
								    			options:[
								    				{text:'不限',value:''},
								    				{text:'未召开',value:'unHold'},
								    				{text:'进行中',value:'holding'},
								    				{text:'已召开',value:'hold'},
								    				{text:'已取消',value:'41'}]">
								    	</div>
				    				</td>
				    			</tr>
				    			<tr>
				    				<td class="label">所属角色：</td>
				    				<td>
				    					<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-props="
								    			name:'role',
								    			value:'0',
								    			defaultValue: '0',
								    			options:[
								    				{text:'不限',value:'0'},
								    				{text:'我发起的',value:'5'},
								    				{text:'我组织的',value:'2'},
								    				{text:'我主持的',value:'1'},
								    				{text:'我纪要的',value:'3'},
								    				{text:'我参与的',value:'4'}]">
								    	</div>
				    				</td>
				    			</tr>
								<tr>
				    				<td class="label">会议时间：</td>
				    				<td>
										<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-props="
								    			name:'fdHoldDate',
								    			value:'0',
								    			defaultValue: '0',
								    			options:[
								    				{text:'不限',value:'0'},
								    				{text:'今天',value:'1'},
								    				{text:'本周',value:'2'},
								    				{text:'本月',value:'3'},
								    				{text:'本季',value:'4'},
								    				{text:'本年',value:'5'},
								    				{text:'上一月',value:'6'},
								    				{text:'上一年',value:'7'}]">
								    	</div>
				    				</td>
				    			</tr>
								<tr>
				    				<td class="label">会议类别：</td>
				    				<td>
							    		<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-mixins="km/imeeting/maxhub/resource/js/TemplateRadioMixin"
							    			data-dojo-props="
								    			name:'fdTemplate',
								    			value:'',
								    			defaultValue: ''"></div>
				    				</td>
				    			</tr>
								<tr>
				    				<td class="label">会议室分类：</td>
				    				<td>
							    		<div data-dojo-type="mhui/form/Radio"
							    			data-dojo-mixins="km/imeeting/maxhub/resource/js/PlaceRadioMixin"
							    			data-dojo-props="
								    			name:'fdPlace',
								    			value:'',
								    			defaultValue: ''"></div>
				    				</td>
				    			</tr>
				    		</table>

						</div>
			    	</div>
			    
					<div id="meetingMapList" 
						data-sidebar="right"
						data-dojo-type="mhui/list/ItemListBase"
						data-dojo-mixins="km/imeeting/maxhub/resource/js/list/MeetingMapItemListMixin"
						data-dojo-props="lazy:false"></div>
			 	</div>
			</div>
		</section>
		<!-- 主体内容区 Ends -->
		<div data-dojo-type="mhui/toolbar/Toolbar">
			<div data-dojo-type="mhui/toolbar/ToolbarItem"
				data-dojo-props="align:'left'">
				
			</div>
			<div data-dojo-type="mhui/toolbar/ToolbarItem">
				<div data-dojo-type="mhui/toolbar/ToolbarButton"
					data-dojo-props="text:'返回首页',type:'primary',size:'lg',onClick:'goBack'"></div>
			</div>
			<div data-dojo-type="mhui/toolbar/ToolbarItem"
				data-dojo-props="align:'right'">
			</div>
		</div>
		
		<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/maxhub/resource/js/map.js"></script>
	</template:replace>	
</template:include>