<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ page import="
	com.landray.kmss.util.KmssMessageWriter,
	com.landray.kmss.util.KmssReturnPage" %>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<script type="text/javascript">
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("docutil.js|optbar.js|validator.jsp|validation.js|plugin.js|validation.jsp|xform.js|jquery.js", null, "js");
</script>
<body>
<br>
<% if(request.getAttribute("KMSS_RETURNPAGE")==null){ %>
<logic:messagesPresent>
	<table align=center><tr><td>
		<font class="txtstrong"><bean:message key="errors.header.vali"/></font>
		<bean:message key="errors.header.correct"/>
		<html:messages id="error">
			<br><img src='${KMSS_Parameter_StylePath}msg/dot.gif'>&nbsp;&nbsp;<bean:write name="error"/>
		</html:messages>
	</td></tr></table>
	<hr />
</logic:messagesPresent>
<% }else{
	KmssMessageWriter msgWriter = new KmssMessageWriter(request, (KmssReturnPage)request.getAttribute("KMSS_RETURNPAGE"));
%>
<script>
Com_IncludeFile("msg.js", "style/"+Com_Parameter.Style+"/msg/");
function showMoreErrInfo(index, srcImg){
	var obj = document.getElementById("moreErrInfo"+index);
	if(obj!=null){
		if(obj.style.display=="none"){
			obj.style.display="block";
			srcImg.src = Com_Parameter.StylePath + "msg/minus.gif";
		}else{
			obj.style.display="none";
			srcImg.src = Com_Parameter.StylePath + "msg/plus.gif";
		}
	}
}
</script>

<% } %>
<center>
	<table  class="tb_normal" width="100%" style="table-layout:fixed;">
		<thead>
		<tr>
				<td class="td_normal_title" style="width:6%;text-align:center"><bean:message key="page.serial"/></td>
				<td class="td_normal_title" style="width:10%;text-align:center"> 
					${lfn:message('km-archives:kmArchivesMain.docSubject')}
				</td>
				<td class="td_normal_title" style="width:10%;text-align:center"> 
					${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}
				</td>
				<td class="td_normal_title" style="width:8%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.docNumber')}
				</td>
				<td class="td_normal_title" style="width:8%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.docCreator')}
				</td>
				<td class="td_normal_title" style="width:10%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.fdFileDate')}
				</td>

		</tr>
		</thead>
		<tbody id="checkInfoTableBody"></tbody>
		<input id="TotalAmount" type="hidden" value=""/> 
	</table>
	<br/>
	<center>
	    <input type=button class="btnopt" value="<bean:message key="button.ok"/>" onclick="closeDialog();">
	</center>
</center>
<script  type="text/javascript">
	window.onload=function(){
		//获取选中的ID 
		var selectId=window.parent.frames[1].frameElement.getAttribute('selectId');
		if(selectId){		
			getChooseInfo(selectId);
		}
	}
	window.showInfo=function(id,type){
		if(type=="ADD"){
			getChooseInfo(id);
		} else {
			//删除对应的元素
			$("#"+id).remove();
		}
	}
	function getChooseInfo(id){ 
		if(id){
			var fdNumbers = $("input[name='fdNumber']");
			if(!fdNumbers){
				fdNumbers=[];
			}  
			var url = '<c:url value="/km/archives/km_archives_borrow/kmArchBorrowOption.do?method=ajaxCheckList"/>';
			$.ajax({
				url : url,
				type : 'POST',
				data : {fdMainId:id},
				dataType : 'json',
				error : function(data) {
					 
				},
				success: function(data) {
					if(data){ 
						if(data){
							$.each(data,function(i,item){
								createTr(fdNumbers.length + i +1,item);
							});
						} 
					} 
				}
		    });
		}
	} 
  function closeDialog(){ 
    var selectId=window.parent.frames[1].frameElement.getAttribute('selectId');
   	parent.$dialog.hide(selectId);
  }
   
		function createTr(i,item){ 
		  	var  html="<tr align='center' id='"+item.fdId+"'>" + 
			"<td>"+i+"</td>" + 
			"<td  style='text-overflow:ellipsis;overflow:hidden;white-space: nowrap;'  title = '${kmArchivesMain.docSubject}'>" + 
				decodeOutHTML(item.docSubject)+
			"</td>"+ 
			 "<td  style='text-overflow:ellipsis;overflow:hidden;white-space: nowrap;' title = '${kmArchivesMain.fdDense.fdName}'>" + 
				decodeOutHTML(item.fdDenseName) + 
			"</td>" + 
			"<td  style='text-overflow:ellipsis;overflow:hidden;white-space: nowrap;'  title = '${lfn:message('km-archives:kmArchivesMain.docNumber')}'>" +  
				decodeOutHTML(item.docNumber)+ 
			"</td>" + 
			"<td style='text-overflow:ellipsis;overflow:hidden;white-space: nowrap;' title = '${lfn:message('km-archives:kmArchivesMain.docCreator')}' >" + 
				decodeOutHTML(item.docCreatorName)+ 
			"</td>" + 
			"<td style='text-overflow:ellipsis;overflow:hidden;white-space: nowrap;' title = '${lfn:message('km-archives:kmArchivesMain.fdFileDate')}'>" + 
				item.fdFileDate+ 
			"</td>" + 
			"<input class='inputsgl' type='hidden' name='fdNumber'>" + 
			"</tr>"; 
			$("#checkInfoTableBody").append(html);
		}
		window.decodeOutHTML = function(html){
		    //1.首先动态创建一个容器标签元素，如DIV
	        var temp = document.createElement ("div");
	        //2.然后将要转换的字符串设置为这个元素的innerText或者textContent
	        (temp.textContent != undefined ) ? (temp.textContent = html) : (temp.innerText = html);
	        //3.最后返回这个元素的innerHTML，即得到经过HTML编码转换的字符串了
	        var output = temp.innerHTML;
	        temp = null;
	        return output;
	    } 
</script>

<%@ include file="/resource/jsp/edit_down.jsp"%>