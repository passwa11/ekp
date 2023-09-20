<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
	<%
		String mainModelName = "com.landray.kmss.hr.ratify.model.HrRatifyEntry";
		SysDictModel dict = SysDataDict.getInstance().getModel(mainModelName);
		String url = dict.getUrl();
		String addUrl = url.substring(0, url.indexOf(".do"))
				+ ".do";
		addUrl += "?method=add&i.docTemplate=!{id}";
		if (addUrl.startsWith("/")) {
			addUrl = addUrl.substring(1);
		}
		request.setAttribute("addUrl", addUrl);
	%>
<kmss:authShow roles="ROLE_HRRATIFY_CREATE">	
<div id="usualCateDiv">	
	<ui:dataview>
		<ui:event event="load">
			var data = this.data;
			if(data.list && data.list.length == 0){
				this.erase();
			}  
		</ui:event>
		<ui:source type="AjaxJson">
		    {"url":"/hr/ratify/hr_ratify_template/hrRatifyTemplate.do?method=listUsual&mainModelName=com.landray.kmss.hr.ratify.model.HrRatifyEntry&fdTempKey=HrRatifyEntryDoc"}
		</ui:source>
		<ui:render type="Template">
				{$
					<div class="lui-cate-panel-heading usual-cate-title">		     
						 <h2 class="lui-cate-panel-heading-title">员工入职模板</h2> 
					</div>
					<ul class='lui-cate-panel-list'>
				$} 
				var _data = data.list;
				for(var i=0;i < _data.length;i++){
					{$
					 <kmss:auth requestURL="{%_data[i].addUrl%}" requestMethod="GET">
						<li class="lui-cate-panel-list-item">
							 <div class="link-box">
						        <div class="link-box-heading">
						          <p><span>{%_data[i].templateDesc%}</span></p>
						        </div>
						        <div class="link-box-body">
						          <a onclick="Com_OpenNewWindow(this)" data-href="{%Com_Parameter.ContextPath%}{%_data[i].addUrl%}" target="_blank"  title="{%_data[i].templateDesc %}">新建</a>
						        </div>
								<div class="link-box-footer">
						          <h6 class="link-box-title">
						$}
						          if(_data[i].cateName){
						           {$
						         	  <i class="icon"></i><span>{%env.fn.formatText(_data[i].cateName)%}</span>
						         	$} 
						           }
						{$
						          </h6>
						        </div>
						     </div>
						</li>
						</kmss:auth>
					$}
				}
			 {$
				</ul>
			$}
		</ui:render>
	</ui:dataview>
</div>	
</kmss:authShow>