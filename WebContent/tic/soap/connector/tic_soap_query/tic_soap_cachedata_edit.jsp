<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<script type="text/javascript">
Com_IncludeFile("jquery.js|json2.js|dialog.js|formula.js|xml.js|doclist.js");
</script>
<script>

	function confirmDelete(msg) {
		var del = confirm("<bean:message key="page.comfirmDelete"/>");
		return del;
	}
	
</script>

<div id="optBarDiv">
		<input type="button"
		value="${lfn:message('tic-soap:ticSoapCachedata.test')}"
		onclick="Com_Submit(document.ticSoapQueryForm, 'transCacheData');">
	
		<input type="button"
		value="${lfn:message('tic-soap:ticSoapCachedata.updateTransImp')}"
		onclick="Com_Submit(document.ticSoapQueryForm, 'saveTransCode');">

	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>

<center>
<p class="txttitle">${ticSoapQueryForm.docSubject }</p>
<html:form action="/tic/soap/connector/tic_soap_query/ticSoapQuery.do">

		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('tic-soap:ticSoapCachedata.interfaceData')}
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td width=100%>
										<textarea name="interData" style="width:100%;height:180px" >${dataList}</textarea>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('tic-soap:ticSoapCachedata.transImp')}
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td width=100%>
										<textarea name="transImpl" style="width:100%;height:180px" >${transImpl}</textarea>
							</td>
						</tr>
					</table>
				</td>
			</tr>

		<table class="tb_normal" width=95%>
			<tr>
				<td class="td_normal_title" width=15%>
					${lfn:message('tic-soap:ticSoapCachedata.transResult')}
				</td>
				<td width=85%>
					<table class="tb_normal" width=100%>
						<tr>
							<td width=100%>
										<textarea name="transData" style="width:100%;height:180px" >${transData}</textarea>
							</td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
<input type="hidden" name="fdId" id="fdId" value="${ticSoapQueryForm.fdId}"/>
<input type="hidden" name="ticSoapMainId" value="${ticSoapQueryForm.ticSoapMainId }"/>
<input type="hidden" name="docSubject" id="docSubject" value="${ticSoapQueryForm.docSubject }"/>
<textarea name="docInputParam" id="docInputParam" style="display:none;">${ticSoapQueryForm.docInputParam }</textarea>
<textarea name="docOutputParam" id="docOutputParam" style="display:none;">${ticSoapQueryForm.docOutputParam }</textarea>
<textarea name="docFaultInfo" id="docFaultInfo" style="display:none;">${ticSoapQueryForm.docFaultInfo }</textarea>
<textarea name="resultXml" style="display:none;" id="resultXml">${resultXml }</textarea>

</html:form>
</center>

<%@ include file="/resource/jsp/view_down.jsp"%>
