<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
<template:replace name="body">
<link rel="Stylesheet" href="${LUI_ContextPath}/sys/circulation/resource/css/circulate.css?s_cache=${MUI_Cache}" />
<script type="text/javascript">
seajs.use(['theme!form']);
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
</script>	
	<ui:dataview id="opinionList">
		<ui:source type="AjaxJson">
			{"url":"/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=listOpinion&fdMainId=${param.fdCirMainId}&isOpinion=true"}
		</ui:source>
		<ui:render type="Template">
			{$
			  <div class="opinionContainer">
				<div class="label_title"> 
					<div class="title"><bean:message bundle="sys-circulation" key="sysCirculationMain.list" /></div>
				</div>
			$}
			if(data.length > 0){
			{$
				<ul class="opinion-list">
			$} 
				for(var i=0;i < data.length;i++){
					{$
						<li>
							<div class="opinionInfo">
								<span>{%data[i].deptName%}</span>
								<span>{%data[i].personName%}</span>
								<span>{%data[i].fdWriteTime%}</span></div>
							<div class="opinionContent">{%data[i].docContent%}</div>
						</li>
					$}
				}
		 {$
			  </ul>
		 $}
		 }else{
		 	{$
		 		<div class="prompt_container" style="text-align: left;">
					<bean:message key="return.noRecord" />
		 		</div>
		 	$}
		 
		 }
		{$	  
			</div>
		 $}
		</ui:render>
	</ui:dataview>
	</template:replace>
</template:include>
