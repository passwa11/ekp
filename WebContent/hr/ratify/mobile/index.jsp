<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/mobile/jsp/ajax-accept.jsp" %>

<template:include ref="mobile.list" compatibleMode="true">
	<template:replace name="title">
		<bean:message bundle="hr-ratify" key="module.hr.ratify" />
	</template:replace>
	<template:replace name="head">	   
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/css/index.css?s_cache=${MUI_Cache}">
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/css/reset.css">
	   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/hr/ratify/mobile/resource/font/dabFont/dabFont.css">
		<script src="resource/js/rem.js"></script>
	</template:replace>
	<template:replace name="content">
	 
	<div id="scrollView"
			data-dojo-type="mui/view/DocScrollableView">
		<div class="mui_personnel_process_box">
		    <div class="mui_personnel_process_content">
		      <div class="mui_personnel_process_head">
		        <div class="mui_pph_user">
		          <span>Hi，<%=UserUtil.getUser().getFdName() %></span>
		        </div>
		        <div class="mui_pph_toast" onclick="javascript:window.location.href='./list.jsp?modulekey=main'">
		          <div class="mui_pph_toast_content">
		            <i class="mui_pph_toast_icon"></i>
		            <div class="mui_pph_tc_text">
		             	 <bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.1"/><span id="approval">7<bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.2"/></span><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.3"/>
		            </div>
		            <i class="mui_pph_new_icon"></i>
		          </div>
		          <i class="mui_pph_close_icon"></i>
		        </div>
		        <div class="mui_pph_main_message">
		          <div class="mui_pph_mm_item" onclick="javascript:window.location.href='./entry/entry_list.jsp'">
		            <div class="mui_pph_mm_data_statistics">
		              	<span id="waitEntry">10</span>
		            </div>
		            <span class="mui_pph_mm_item_introduce"><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.4"/></span>
		          </div>
		          <div class="mui_pph_mm_item" onclick="javascript:window.location.href='./list.jsp?modulekey=entry'">
		            <div class="mui_pph_mm_data_statistics">
		              <span id="entry">37</span>
		            </div>
		            <span class="mui_pph_mm_item_introduce"><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.5"/></span>
		          </div>
		          <div class="mui_pph_mm_item" onclick="javascript:window.location.href='./list.jsp?modulekey=positive'" >
		            <div class="mui_pph_mm_data_statistics mui_pp_mm_ds_undefined">
		              <span id="positive">0</span>
		            </div>
		            <span class="mui_pph_mm_item_introduce"><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.6"/></span>
		          </div>
		        </div>
		        <div class="mui_pph_handleList">
		          <div class="mui_pph_handleList_item" onclick="javascript:window.location.href='./list.jsp?modulekey=transfer'">
		            <div class="mui_pph_hli_title">
		              <i class="mui_pph_hli_title_icon mui_icon-ddlc"></i>
		              <span><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.7"/></span>
		            </div>
		            <div class="mui_pph_hli_more">
		              <span class="mui_pph_hli_more_number" id="transfer">3</span>
		              <i class="mui_pph_hli_more_icon"></i>
		            </div>
		          </div>
		          <div class="mui_pph_handleList_item" onclick="javascript:window.location.href='./list.jsp?modulekey=leave'">
		            <div class="mui_pph_hli_title">
		              <i class="mui_pph_hli_title_icon mui_icon-lzlc"></i>
		              <span><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.8"/></span>
		            </div>
		            <div class="mui_pph_hli_more">
		              <span class="mui_pph_hli_more_number" id="leave">1</span>
		              <i class="mui_pph_hli_more_icon"></i>
		            </div>
		          </div>
		          <div class="mui_pph_handleList_item" onclick="javascript:window.location.href='./list.jsp?modulekey=main&mobile=contract'">
		            <div class="mui_pph_hli_title">
		              <i class="mui_pph_hli_title_icon mui_icon-rsht"></i>
		              <span><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.9"/></span>
		            </div>
		            <div class="mui_pph_hli_more">
		              <span class="mui_pph_hli_more_number" id="contract">99+</span>
		              <i class="mui_pph_hli_more_icon"></i>
		            </div>
		          </div>
		          <div class="mui_pph_handleList_item" onclick="javascript:window.location.href='./list.jsp?modulekey=salary'">
		            <div class="mui_pph_hli_title">
		              <i class="mui_pph_hli_title_icon mui_icon-txlc"></i>
		              <span><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.10"/></span>
		            </div>
		            <div class="mui_pph_hli_more">
		              <!-- <span class="mui_pph_hli_more_number"></span> -->
		              <i class="mui_pph_hli_more_number" id="salary">0</i>
		              <i class="mui_pph_hli_more_icon"></i>
		            </div>
		          </div>
		          <div class="mui_pph_handleList_item" onclick="javascript:window.location.href='./list.jsp?modulekey=main&mobile=other'">
		            <div class="mui_pph_hli_title">
		              <i class="mui_pph_hli_title_icon mui_icon-qtlc"></i>
		              <span><bean:message bundle="hr-ratify" key="mobile.hr.ratify.index.11"/></span>
		            </div>
		            <div class="mui_pph_hli_more">
		              <span class="mui_pph_hli_more_number" id="other">13</span>
		              <i class="mui_pph_hli_more_icon"></i>
		            </div>
		          </div>
		        </div>
		      </div>
		    </div>
		  </div>
	</div>
	</template:replace>
</template:include>
<script>
	require(["dojo/ready","dojo/dom-class","dojo/request","dojo/dom","dojo/query","dojo/on","dojo/dom-attr","dojo/dom-style","dojox/mobile/TransitionEvent"], 
		function(ready,domClass,request,dom,query,on,domAttr,domStyle,TransitionEvent) {
		request("<%=request.getContextPath()%>/hr/ratify/hr_ratify_main/hrRatifyMain.do?method=getRatifyMobileIndex").then(function(data){
			var json = eval("("+data+")");
			for(var p in json){
				var name = json[p].name;
				var count = json[p].count;
				var subject = dom.byId(name);
				if(count===0){
					domClass.add(subject,"zero_status_color")
				}else{
					domClass.remove(subject,"zero_status_color")
				}
				subject.innerText = json[p].count;
				if(name == 'approval'){
					subject.innerText = count + "${lfn:message('hr-ratify:mobile.hr.ratify.index.2')}";
					if(count == 0){
						var newIcon = query('.mui_pph_new_icon')[0];
						domStyle.set(newIcon,'display','none');
					}
				}
				if(name == 'transfer' || name == 'leave' || name == 'contract' || name == 'salary' || name == 'other'){
					if(count == 0){
						domStyle.set(subject,'display','none');
					}
				}
			}
		},function(error){
		    
		});
	});
</script>