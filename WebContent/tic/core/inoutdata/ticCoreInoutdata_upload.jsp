<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<style type="text/css">
.input_text{width:250px;}
.input_file{width:300px; margin-left:-300px; filter:alpha(opacity=0); opacity:0;}
</style>
<script type="text/javascript">Com_IncludeFile("treeview.js|jquery.js");</script>
<script language="JavaScript">

function submitForm(){
	
	if(document.getElementsByName("initfile")[0].value.length>0){
		if((document.getElementsByName("initfile")[0].value).indexOf(".zip")>=0){
			document.forms[0].submit();
		}else{
			alert("file is not zip");
			var obj = document.getElementsByName('initfile')[0] ;  
			obj.outerHTML=obj.outerHTML;  
		}
		return;
	}
	alert("<bean:message key="page.noSelect"/>");
	
}
</script>




<form
	action="<c:url value="/tic/core/inoutdata/ticCoreInoutdata.do?method=upload&fdAppType=${JsParam.fdAppType}" />" name="ticCoreInoutdataForm"  method="POST" enctype="multipart/form-data">
    <div id="optBarDiv">
		<input type=button value="${lfn:message('home.help')}"
			onclick="Com_OpenWindow(Com_Parameter.ContextPath+'tic/core/inoutdata/help/ExportAndImportHelp.html','_blank');"/>
	</div> 

	<p class="txttitle">
		<bean:message bundle="tic-core-inoutdata"
			key="imExport.dataImport" />
	</p>
	
<center>
<table class="tb_normal" width=95%>				
	<tr>
		<td class="td_normal_title" width=20%>
			<bean:message bundle="tic-core-inoutdata" key="imExport.select.upload"/>
		</td><td width=60%>
			<span id="_formFilesSpan">
				
				 <input class="input_text" type="text" id="txt" name="initfile" />
                 <input type="button" value="浏览"  />
                 <input class="input_file" size="30" type="file"  name="initfile" onchange="txt.value=this.value"
                 id="inputFile" value="<bean:message bundle="tic-core-inoutdata" key="imExport.data.upload.analysis"/>" />
				<input class="btnopt" type=button value="<bean:message bundle="tic-core-inoutdata" key="imExport.data.upload.analysis"/>" 
				onclick="submitForm();">
			</span>
		</td>
		
		
	</tr>
	
</table>
</center>

</form>
<%@ include file="/resource/jsp/view_down.jsp"%>
