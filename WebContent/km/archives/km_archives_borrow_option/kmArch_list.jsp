<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<script type="text/javascript">
Com_IncludeFile("document.css", "style/"+Com_Parameter.Style+"/doc/");
seajs.use(['theme!list']);
</script>

<div class="input_search" style="border:0">
 <input type="text"  name="keywords"  placeholder="${lfn:message('km-archives:select.archives.tip')}"  size="25" onkeydown="searchEnter();" />
   <input type="button" class="btnopt" value="<bean:message key="button.search"/>" onclick="dosearch();">
</div>
<c:if test="${queryPage.totalrows==0}">
	<%@ include file="/resource/jsp/list_norecord.jsp"%>
</c:if>
<c:if test="${queryPage.totalrows>0}">
	<%@ include file="/resource/jsp/list_pagenav_top.jsp"%>
	<table id="List_ViewTable">
		<tr align="center">
			<sunbor:columnHead htmlTag="td">
				<td width="10pt"></td>
				<td width="40pt"><bean:message key="page.serial"/></td>
				<sunbor:column property="kmArchivesMain.docSubject">
					${lfn:message('km-archives:kmArchivesMain.docSubject')}
				</sunbor:column>
				<sunbor:column property="kmArchivesMain.fdDense.fdName">
					${lfn:message('km-archives:kmArchivesMain.fdDenseLevel')}
				</sunbor:column>
				<sunbor:column property="kmArchivesMain.docNumber">
					${lfn:message('km-archives:kmArchivesMain.docNumber')}
				</sunbor:column>
				<sunbor:column property="kmArchivesMain.docCreator.fdName">
					${lfn:message('km-archives:kmArchivesMain.docCreator')}
				</sunbor:column>
				<sunbor:column property="kmArchivesMain.fdFileDate">
					${lfn:message('km-archives:kmArchivesMain.fdFileDate')}
				</sunbor:column>
			</sunbor:columnHead>
		</tr>
		<c:forEach items="${queryPage.list}" var="kmArchivesMain" varStatus="vstatus">
			<tr onclick="showHistory('${vstatus.index+1}','${kmArchivesMain.fdId}','');" align="center">
				<td>
					<input type="checkbox" name="List_Selected" id="checkBox_${kmArchivesMain.fdId}"  value="${kmArchivesMain.fdId}" onclick="chooseOne(this,'${kmArchivesMain.fdId}');">
					
				</td>
				<td>${vstatus.index+1}</td>
				<td kmss_wordlength="20" title="${kmArchivesMain.docSubject}">
					<c:out value="${kmArchivesMain.docSubject}" />
				</td>
				<td kmss_wordlength="20" title="${kmArchivesMain.fdDense.fdName}">
					<c:out value="${kmArchivesMain.fdDense.fdName}" />
				</td>
				<td title="${lfn:message('km-archives:kmArchivesMain.docNumber')}">
					<c:out value="${kmArchivesMain.docNumber}" />
				</td>
				<td title="${lfn:message('km-archives:kmArchivesMain.docCreator')}">
					<c:out value="${kmArchivesMain.docCreator.fdName}" />
				</td>
				<td title="${lfn:message('km-archives:kmArchivesMain.fdFileDate')}">
					<kmss:showDate value="${kmArchivesMain.fdFileDate}" type="date"/>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="/resource/jsp/list_pagenav_down.jsp" %>
</c:if>
<%@ include file="/resource/jsp/list_down.jsp"%>
<script>
Com_IncludeFile("data.js");
Com_IncludeFile("jquery.js");
</script>

<script type="text/javascript">
window.onload=function(){
	var oldUrl = location.href;
	//搜索关键字
	var keywords = Com_GetUrlParameter(oldUrl,'keywords'); 
	var selected = self.frameElement.getAttribute('selectId');
	if(keywords != null &&  keywords != ''){		
		document.getElementsByName("keywords")[0].value = keywords;
	} 
	if(selected){ 
		 var fdIdArray = selected.split(",");
		 for(var i=0;i<fdIdArray.length;i++){
			var id =fdIdArray[i]; 
			if(id && id.length > 0){
				$("#checkBox_"+id).attr('checked', 'checked');
			}
		 }
	} 
	
};
function dosearch(){
	var keywords = document.getElementsByName("keywords")[0].value;
	//去除首尾空格
	keywords = keywords.replace(/(^\s*)|(\s*$)/g,"");
	keywords = encodeURI(keywords); //中文两次转码
	window.location.href ="${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchBorrowOption.do?method=checkArchList&categoryId=${fdCategoryId}&fdTemplatId=${fdTemplatId}&keywords="+keywords;
}
function showHistory(index,fdId,selectInit){
	  
	/*  var fdId_old = self.frameElement.getAttribute('selectId');
	 var text=selectInit;
	 if(null != fdId_old && fdId_old != ''){
		 if(fdId == '' || fdId_old.indexOf(fdId)){
		 	 var fdIdArray = fdId_old.split(",");
			 for(var i=0;i<fdIdArray.length;i++){
				 if(fdIdArray[i] != fdId){
					 text += fdIdArray[i] + ",";
				 }
			 }
		 }
	 } 
	 $("input[name=List_Selected]").each(function() {  
         if ($(this).is(':checked')) {  
             text += $(this).val()+",";  
         }  
     });  
	 self.frameElement.setAttribute('selectId',text.substring(0, text.length-1)); 
	 window.parent.frames[2].location.href = "${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchBorrowOption.do?method=redirectCheckList&r="+Math.random();
	  */
}

function chooseOne(even,id){ 
	var fdId_old = self.frameElement.getAttribute('selectId');
	var fdIdArray = fdId_old.split(",");
	if(!fdIdArray){
		fdIdArray=[];
	}
	if($(even).is(':checked')){ 
		window.parent.frames[2].showInfo(id,"ADD");
		fdIdArray.push(id);
	}else{
		window.parent.frames[2].showInfo(id,"DEL"); 
		var delIndex = fdIdArray.indexOf(id); 
		fdIdArray.splice(delIndex,1);
	} 
	self.frameElement.setAttribute('selectId',fdIdArray.join(",")); 
}

function searchEnter(event){
	event = event || window.event;
	if (event.keyCode == 13){
		dosearch();
	}
}
</script>