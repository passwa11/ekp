<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>

<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js");
</script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/mustache.js" type="text/javascript"></script>
<link href="${KMSS_Parameter_ContextPath}tic/core/resource/css/jquery.treeTable.css" rel="stylesheet" type="text/css" />
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/jquery.treeTable.js" type="text/javascript"></script>
<script src="${KMSS_Parameter_ContextPath}tic/core/resource/js/erp.parser.js" type="text/javascript"></script>
<script type="text/javascript" src="${KMSS_Parameter_ContextPath}tic/core/resource/plugins/XMLParseUtil.js"></script>

<script>
XMLParseUtil.initDomByMozilla();
</script>
<div id="optBarDiv">
	<input type="button"
		value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<script type="text/javascript"
	src="${KMSS_Parameter_ContextPath}tic/core/resource/js/tools.js"></script>
<p class="txttitle">SOAP ${lfn:message('tic-soap-sync:ticSoapSyncJob.syncJobMappingSetting')}  <img src="${KMSS_Parameter_ContextPath}resource/style/default/tag/help.gif" title='帮助' style="cursor:pointer" onclick='Com_OpenWindow("ticSoapSyncSettingHelp.jsp","_blank")' ></img> </p>
<table id="rfcTabel" class="tb_normal" width=95% >

</table>

<table class="tb_normal" width=95%>
  <tr>
     <td style="padding:0px;">
       <div id="tic_soap_head">
       	 <table class="tb_normal" width="100%">
       	   <tr>
       	   	 <td width="15%">${lfn:message('tic-core-common:ticCoreCommon.funcName')}</td>
       	   	 <td width="35%">
       	   	 	<input type="hidden" readOnly="readonly" id="fdSoapMainId" name="fdSoapMainId"/>
       	   	 	<input type="text" readOnly="readonly" id="fdSoapMainName" name="fdSoapMainName" class="inputread"/>
       	   	 </td>
       	   	 <td width="15%">${lfn:message('tic-core-common:ticCoreCommon.dataSource')}</td>
       	   	 <td width="35%">
       	   	 	<select id="dbselect" name="dbselect" style="disabled:true;">
       	   	 	</select>
       	   	 </td>
       	   </tr>
       	   <tr>
       	   	 <td width="15%">${lfn:message('tic-core-common:ticCoreCommon.lastExecuteTime')}</td>
       	   	 <td width="85%" colspan="3">
       	   	 	<span id="fdLastDate"></span>
       	   	 </td>
       	   </tr>
       	 </table>
       </div>
     </td>
  </tr>
  <tr>
     <td>
       <div id="tic_soap_input"></div>
     </td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr>
     <td>
       <div id="tic_soap_output"></div>
     </td>
  </tr>
  <tr>
  	<td>&nbsp;</td>
  </tr>
  <tr>
     <td>
       <div id="tic_soap_falut"></div>
     </td>
  </tr>
</table>
</center>

<!-- js input脚本模板 -->
<script id="erp_query_template" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 40%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 15%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 5%" />
	<caption>{{info.caption}}</caption>
	<thead>
		<tr>
		{{#info.thead}}
			<td style='font-size:13px; text-align:center;' nowrap >{{{th}}}</td>
		{{/info.thead}}
		</tr>
	</thead>
	<tbody>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >
			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span></td>
			<td>{{dataType}}</td>
			<td>{{#comment.minOccurs}}  min:{{comment.minOccurs}} {{/comment.minOccurs}}  {{#comment.maxOccurs}} max:{{comment.maxOccurs}}{{/comment.maxOccurs}} </td>
			<td>
				{{^isHead}}
				{{^hasNext}}
				<select disabled="true" name="{{nodeKey}}_inputSelect" id="{{nodeKey}}_inputSelect" commentValue="{{comment.inputSelect}}"
						onchange="inputSelectChange(this.value, '{{nodeKey}}');" nodeKey="{{nodeKey}}" commentName="inputSelect">
					<option value="1">固定值</option>
					<option value="2">最后更新时间</option>
					<option value="3">当前执行时间</option>
				</select>
				<input type="text" class="inputread" name="{{nodeKey}}_inputText" id="{{nodeKey}}_inputText" {{#comment.inputText}} value="{{comment.inputText}}" {{/comment.inputText}} nodeKey="{{nodeKey}}" commentName="inputText"/>
				{{/hasNext}}
				{{/isHead}}
			</td>
 			<td>
				{{^isHead}}
				{{^hasNext}}
            	<input type="text" id="{{nodeKey}}_mark" class="inputread" commentName="mark"  name="{{nodeKey}}_mark"  nodeKey="{{nodeKey}}" {{#comment.mark}} value="{{comment.mark}}" {{/comment.mark}} /> 
				{{/hasNext}}
				{{/isHead}}	
			</td>
            <td>
			{{^hasNext}}
 			 <input type="text" id="{{nodeKey}}_matchName" readOnly class="inputread" commentName="ekpname" name="{{nodeKey}}_fault_name"   nodeKey="{{nodeKey}}" {{#comment.ekpname}} value="{{comment.ekpname}}" {{/comment.ekpname}} /> 
			{{/hasNext}}
				</td>
 			<td>
			{{^hasNext}}
            <input type="text" id="{{nodeKey}}_matchID" readOnly class="inputread" commentName="ekpid"  name="{{nodeKey}}_fault_id"  nodeKey="{{nodeKey}}" {{#comment.ekpid}} value="{{comment.ekpid}}" {{/comment.ekpid}} /> 
			{{/hasNext}}	
			</td>
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>

<script id="erp_query_outtemplate" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 40%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 15%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 5%" />
	<caption>{{info.caption}}</caption>
	<thead>
		<tr>
		{{#info.thead}}
			<td style='font-size:13px; text-align:center;' nowrap >{{{th}}}</td>
		{{/info.thead}}
		</tr>
	</thead>
	<tbody>
		{{#info.tbody}}
			{{#isMore}}
			<tr>
				<td align="right">
				同步方式:
				<select style="width:110px;" disabled="true" id="{{nodeKey}}_syncType" name="{{nodeKey}}_syncType" onchange="syncTypeChange(this.value, '{{nodeKey}}');" 
						nodeKey="{{nodeKey}}" commentName="syncType" commentValue="{{comment.syncType}}">
       	   	 		<option value="2">全量</option>
       	   	 		<option value="1">增量</option>
       	   	 		<option value="3">增量(时间戳)</option>
       	   	 		<option value="4">增量(插入前删除)</option>
       	   	 		<option value="5">增量(条件删除)</option>
       	   	 	</select>
       	   	 	<select id="{{nodeKey}}_syncType_date" name="{{nodeKey}}_syncType_date" nodeKey="{{nodeKey}}" 
						commentName="syncType_date" commentValue="{{comment.syncType_date}}" style="display: none; disabled:true;">
       	   	 		<option value="">==请选择==</option>
       	   	 	</select>
       	   	 	<span id="{{nodeKey}}_delConditionSpan" style="display: none;">
       	   	 	<input type="text" id="{{nodeKey}}_fdDelConditionName" name="{{nodeKey}}_fdDelConditionName" value="" 
					nodeKey="{{nodeKey}}" commentName="fdDelConditionName" commentValue="{{comment.fdDelConditionName}}" readonly="readonly" size="10" class="inputread"/>
       	   	 	<img style="cursor:pointer" alt="公式定义" onclick="conditionImgClick('{{nodeKey}}_fdDelConditionName', '{{nodeKey}}_fdDelConditionName', '{{nodeKey}}');" src="${KMSS_Parameter_ContextPath}resource/style/default/icons/edit.gif"/>
       	   	 	<img style="cursor:pointer" alt="帮助" src="${KMSS_Parameter_ContextPath}resource/style/default/tag/help.gif" onclick="Com_OpenWindow('ticSoapSyncHelp.jsp','_blank')" />
       	   		</span>
				
				&nbsp;<font color="blur" align="right">同步表</font>
				</td>
				<td colspan="5">
					<input type="text" id="{{nodeKey}}_fdSyncTable" name="{{nodeKey}}_fdSyncTable" 
						nodeKey="{{nodeKey}}" commentName="fdSyncTable" commentValue="{{comment.fdSyncTable}}" readonly="readonly" class="inputread"/>
					<img style="cursor:pointer" alt="选择同步表" onclick="syncTableChange('{{nodeKey}}_fdSyncTable', '{{nodeKey}}_fdSyncTable', '{{nodeKey}}');"
       	   	 			src="${KMSS_Parameter_ContextPath}resource/style/common/images/small_search.png"/>
					KEY:
					<select style="width:120px;" disabled="true" name="{{nodeKey}}_key_mappingValue" id="{{nodeKey}}_key_mappingValue" commentValue="{{comment.key}}"
						nodeName="{{nodeName}}" nodeKey="{{nodeKey}}" commentName="key" isMore="true">
						<option value="">==请选择==</option>
					</select>
					&nbsp;	
					生成FD_ID:
					<select style="width:140px;" disabled="true" name="{{nodeKey}}_generateFdId_mappingValue" id="{{nodeKey}}_generateFdId_mappingValue" 
						commentValue="{{comment.generateFdId}}" nodeName="{{nodeName}}" nodeKey="{{nodeKey}}" commentName="generateFdId" isMore="true">
						<option value="">==请选择==</option>
					</select>	
				</td>
			</tr>
			{{/isMore}}
		<tr id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >
			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span></td>
			<td>{{dataType}}</td>
			<td>{{#comment.minOccurs}}  min:{{comment.minOccurs}} {{/comment.minOccurs}}  {{#comment.maxOccurs}} max:{{comment.maxOccurs}}{{/comment.maxOccurs}} </td>
			<td>
				{{^isHead}}
				{{^hasNext}}
				{{#pMore}}
				<select style="width:90%;" disabled="true" name="{{nodeKey}}_mappingValue" id="{{nodeKey}}_mappingValue" commentValue="{{comment.mappingValue}}"
						nodeName="{{nodeName}}" nodeKey="{{nodeKey}}" commentName="mappingValue" {{#isMore}}isMore="true"{{/isMore}} {{^isMore}}isMore="false"{{/isMore}}>
					<option value="">==请选择==</option>
					<option value="1">当前执行时间</option>
				</select>
				{{/pMore}}	
				{{/hasNext}}
				{{/isHead}}	
			</td>
 			<td>
				{{^isHead}}
				{{^hasNext}}
				{{#pMore}}
            	<input type="text" id="{{nodeKey}}_mark" class="inputread" commentName="mark"  
					name="{{nodeKey}}_mark"  nodeKey="{{nodeKey}}"
					{{#comment.mark}} value="{{comment.mark}}" {{/comment.mark}} /> 
				{{/pMore}}				
				{{/hasNext}}
				{{/isHead}}	
			</td>
            <td>
			{{^hasNext}}
 			 <input type="text" id="{{nodeKey}}_matchName" readOnly class="inputread" commentName="ekpname" name="{{nodeKey}}_output_name"   nodeKey="{{nodeKey}}" {{#comment.ekpname}} value="{{comment.ekpname}}" {{/comment.ekpname}} /> 
			{{/hasNext}}
				</td>
 			<td>
			{{^hasNext}}
            <input type="text" id="{{nodeKey}}_matchID" readOnly class="inputread" commentName="ekpid"  name="{{nodeKey}}_output_id"  nodeKey="{{nodeKey}}" {{#comment.ekpid}} value="{{comment.ekpid}}" {{/comment.ekpid}} /> 
			{{/hasNext}}	
			</td>
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>
<script id="erp_query_faulttemplate" type="text/mustache">
<table class="erp_template" style="table-layout:fixed;width:100%" >
<col style="width: 40%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 15%" />
<col style="width: 10%" />
<col style="width: 10%" />
<col style="width: 5%" />
	<caption>{{info.caption}}</caption>
	<thead>
		<tr>
		{{#info.thead}}
			<td style='font-size:13px; text-align:center;' nowrap >{{{th}}}</td>
		{{/info.thead}}
		</tr>
	</thead>
	<tbody>
		{{#info.tbody}}
		<tr  id="{{nodeKey}}"  {{^root}} class="child-of-{{parentKey}}" {{/root}} >
			<td><span {{^hasNext}} class='file' {{/hasNext}} {{#hasNext}} class='folder' {{/hasNext}}  >{{nodeName}}</span></td>
			<td>{{dataType}}</td>
			<td>{{#comment.minOccurs}}  min:{{comment.minOccurs}} {{/comment.minOccurs}}  {{#comment.maxOccurs}} max:{{comment.maxOccurs}}{{/comment.maxOccurs}} </td>
			<td>
			</td>
 			<td>
				{{^isHead}}
				{{^hasNext}}
            	<input type="text" id="{{nodeKey}}_mark" class="inputread" commentName="mark"  name="{{nodeKey}}_mark"  nodeKey="{{nodeKey}}" {{#comment.mark}} value="{{comment.mark}}" {{/comment.mark}} /> 
				{{/hasNext}}
				{{/isHead}}	
			</td>
            <td>
			{{^hasNext}}
 			 <input type="text" id="{{nodeKey}}_matchName" readOnly class="inputread" commentName="ekpname" name="{{nodeKey}}_fault_name"   nodeKey="{{nodeKey}}" {{#comment.ekpname}} value="{{comment.ekpname}}" {{/comment.ekpname}} /> 
			{{/hasNext}}
				</td>
 			<td>
			{{^hasNext}}
            <input type="text" id="{{nodeKey}}_matchID" readOnly class="inputread" commentName="ekpid"  name="{{nodeKey}}_fault_id"  nodeKey="{{nodeKey}}" {{#comment.ekpid}} value="{{comment.ekpid}}" {{/comment.ekpid}} /> 
			{{/hasNext}}	
			</td>
           <td nowrap>
			</td>
		</tr>
		{{/info.tbody}}
	</tbody>
</table>
</script>

<script type="text/javascript" src="soapQuartzCfg.js"></script>

<%@ include file="/resource/jsp/view_down.jsp"%>