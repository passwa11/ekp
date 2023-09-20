<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<body>
	<div class="relationChoose_content" style="${param.style} display:table">
		<div  class="relationChoose_textShow ${param.parentClass}" parentClass ="${param.parentClass}"></div>
		
		<xform:xtext property="extendDataFormInfo.value(${param.fdControlId }_text)" showStatus="edit" style="display:none;" subject="${param.subject }" htmlElementProperties="validator='true'"></xform:xtext>
		<xform:xtext property="extendDataFormInfo.value(${param.fdControlId }_dataFdId)" showStatus="noShow" ></xform:xtext>
		<xform:xtext property="extendDataFormInfo.value(${param.fdControlId }_dataSourceId)" showStatus="noShow" ></xform:xtext>
		<xform:xtext property="extendDataFormInfo.value(${param.fdControlId }_dataModelName)" showStatus="noShow" ></xform:xtext>
		<xform:xtext property="extendDataFormInfo.value(${param.fdControlId }_selectedDatas)" showStatus="noShow" ></xform:xtext>
		<xform:editShow>
			<xform:xtext property="extendDataFormInfo.value(${param.fdControlId })" style="display:none;" subject="${param.subject }" required="${param.required }" htmlElementProperties="validator='true'"></xform:xtext>
		</xform:editShow>
		<xform:viewShow>
			<xform:xtext property="extendDataFormInfo.value(${param.fdControlId })" showStatus="noShow"></xform:xtext>
		</xform:viewShow>
	
		<xform:editShow>
			<div class="relationChoose_operation">
				<div style='display:inline-block;cursor:pointer;' textId="${param.fdControlId }_text" bindDom='${param.bindDom}' bindEvent='${param.bindEvent}' mytype='relation_choose' 
						myid='extendDataFormInfo.value(${param.fdControlId})' inputParams='${param.inputParams}' <c:if test="${param.inputDomIds ne null }">inputDomIds='${param.inputDomIds }'</c:if>  
						outputParams='${param.outputParams}'  outerSearchParams='${param.outerSearchParams}' params='${param.params}' dialogWidth='${param.dialogWidth}' appendSearchResult='${param.appendSearchResult}'
						dialogHeight = '${param.dialogHeight}' oneRowSearchNum= '${param.oneRowSearchNum}' onclick='Relation_Choose_Run(this);'>
					<bean:message bundle="sys-xform-base" key="Designer_Lang.relation_choose_button" />
				</div>
				<a href='javascript:void(0);' class='com_btn_link' onclick='Relation_Choose_clearValue(this);'><bean:message bundle="sys-xform-base" key="Designer_Lang.relation_choose_clear" /></a>
			</div>
			<script>Com_IncludeFile('relation_run.js','../sys/xform/designer/relation/');</script>
			<script>Com_IncludeFile('relation_event_run.js','../sys/xform/designer/relation_event/');</script>
		</xform:editShow>
	</div>
	<script>
		Com_IncludeFile('relation_choose_run.js','../sys/xform/designer/relation_choose/');
		//使用Com_IncludeCSSFiles追加到head中，不知道为什么谷歌好想也有link样式表个数的限制，具体原因未知
		var urlArr = [];
		urlArr.push('${LUI_ContextPath}/sys/xform/designer/relation_choose/style/css/relation_choose.css');
		Com_IncludeCSSFiles(urlArr);
	</script>
</body>