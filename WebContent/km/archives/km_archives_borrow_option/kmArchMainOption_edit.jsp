<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page  import="java.util.Date"%>
<%@ page  import="com.landray.kmss.util.UserUtil"%>
	<script type="text/javascript">
	 	Com_IncludeFile('jquery.js');
	 	Com_IncludeFile("calendar.js");
	</script>
	<script type="text/javascript">
		seajs.use(['sys/ui/js/dialog'], function(dialog) {
			window.dialog = dialog;
		});
			
		function addOitems(){
			var fdApplicationId = document.getElementsByName("fdId")[0].value ;
			var selected = getCheckArch();
			var curMethod_GET = "${kmArchivesBorrowForm.method_GET}";
			var fdTemplateId = "";
			if ("1" == window.broTemplateSize || curMethod_GET != "add") {
				fdTemplateId = $("input[name=docTemplateId]").val(); 
			} else if ("n" == window.broTemplateSize && "add" == curMethod_GET) {
				fdTemplateId =  $("#selectTemplet option:checked").val();
			}
			
			if (!fdTemplateId) {
				dialog.alert('<bean:message bundle="km-archives" key="kmArchivesBorrow.tips.noTemplate"/>');
			} else {
				var href = Com_GetCurDnsHost()+"${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow_option/kmArchOption_add.jsp?selected="+selected + "&fdTemplateId=" + fdTemplateId;
				dialog.iframe(href,"${lfn:message('km-archives:kmArchivesBorrow.selectArc')}",function(rtnData){
				  if(rtnData != null){
					  getContent(rtnData);
		  		  }else{
		  			_validation.validateElement(document.getElementById("archValidate"));
		  		  }
				},{width:900,height:550}); 
			}
		}
		
		//获取已经选中的档案
		function getCheckArch(){
			var selected = "";
			$('#TABLE_DocList tr').each(function(){
				if(null != $(this).attr("id")){
		        	selected += $(this).attr("id") + ",";
				}
		    });
			return selected;
		}
		
		function  getContent(rtnData){
			var data = new KMSSData();
			var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_borrow/kmArchBorrowOption.do?method=loadArchMain&fdId="+ rtnData;
			data.SendToUrl(url, function(data) {
				var results = eval("(" + data.responseText + ")");
				var fdBorrowerId = $("input[name='fdBorrowerId']").val();
				buildeTable(results,fdBorrowerId);
			});
		}
		
		function buildeTable(results,fdBorrowerId){
			$("#TABLE_DocList tr:not(:first)").remove();
			if (results.length > 0) {
				for(var i=0;i<results.length;i++){
					var trHTML = '<tr id="'+results[i].fdId+'" align="center">';
					trHTML += '<input type="hidden" name="fdBorrowDetail_Form['+i+'].fdStatus" value="0"/>';
					trHTML += '<input type="hidden" name="fdBorrowDetail_Form['+i+'].fdArchId" value="'+results[i].fdId+'"/>';
					trHTML += '<input type="hidden" class="detailBorrows" name="fdBorrowDetail_Form['+i+'].fdBorrowerId" value="'+fdBorrowerId+'"/>';
					trHTML += '<td>'+(i+1)+'</td>';
					trHTML += '<td>';
					trHTML += results[i].docSubject;
					trHTML += '</td>';
					trHTML += '<td>';
					trHTML += results[i].docTemplate;
					trHTML += '</td>';
					trHTML += '<td>';
					trHTML += results[i].fdValidityDate;
					trHTML += '</td>';
					
					trHTML += '<td align="center">';
					trHTML += results[i].copyPre+i+results[i].copyLast;
					trHTML += results[i].downloadPre+i+results[i].downloadLast;
					trHTML += results[i].printPre+i+results[i].printLast;
					trHTML += results[i].divPre+i+results[i].divMid+i+results[i].divLast;
					trHTML += '</td>';
					
					trHTML += '<td>';
					trHTML += '<div class="inputselectsgl" onclick="selectDateTime(\'fdBorrowDetail_Form['+i+'].fdReturnDate\')" style="width:140px;">';
					trHTML += '<div class="input"><input type="text" name="fdBorrowDetail_Form['+i+'].fdReturnDate" value="" validate="required __datetime after returnDateValidator('+results[i].fdValidityDate+')" subject="${lfn:message("km-archives:kmArchivesDetails.fdReturnDate")}"/></div><div class="inputdatetime"></div>';
					trHTML += '</div><span class="txtstrong">*</span>';
					trHTML += '</td>';
					
					trHTML += '<td class="td_normal_title" align="center">';
					trHTML +='<img src="${KMSS_Parameter_StylePath}icons/delete.gif" style="cursor:pointer" onclick=deleteRow("'+results[i].fdId+'")>';
					trHTML += '</td>';
					trHTML += '</tr>';
					$("#TABLE_DocList").append(trHTML);
			   }
				_validation.validateElement(document.getElementById("archValidate"));
			}
		}
		
		function deleteRow(trolleyId){
			$("#"+trolleyId).remove();
		}	
	
	
	</script>
	<center><b>${lfn:message('km-archives:table.kmArchivesDetails')}</b></center>
	<table class="tb_normal" width=100% style="margin-top:15px">
	    <tr>
		    <td>
		         <input type=button class="lui_form_button" value="${lfn:message('km-archives:kmArchivesBorrow.selectArc')}" onclick="addOitems();">
				<span class="txtstrong">*</span>
				<span>
				  <input type="text" id="archValidate"  name="archValidate"  validate="archNotNull" readonly="readonly" style="border:0"/>
				</span> 
		    </td>
	    </tr>
	</table>
	
	<table class="tb_normal" width="100%" id="TABLE_DocList">
		<tr width="100%">
				<td  class="td_normal_title" style="width:5%;text-align:center"><bean:message key="page.serial"/></td>
				<td class="td_normal_title" style="width:15%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.docSubject')}
				</td>
				<td class="td_normal_title"  style="width:10%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.docTemplate')}
				</td>
				<td class="td_normal_title" style="width:10%;text-align:center">
					${lfn:message('km-archives:kmArchivesMain.fdValidityDate')}
				</td>
				<td class="td_normal_title" style="width:30%;text-align:center">
					${lfn:message('km-archives:kmArchivesDetails.fdAuthorityRange')}
				</td>
				<td class="td_normal_title" style="width:10%;text-align:center">
					${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}
				</td>
				<td class="td_normal_title" style="width:5%;text-align:center">
					${lfn:message('list.operation')}
				</td>
		</tr>
		<c:forEach items="${kmArchivesBorrowForm.fdBorrowDetail_Form}" var="fdBorrowDetail_FormItem" varStatus="vstatus">
			<tr width="100%" id="${fdBorrowDetail_FormItem.fdArchives.fdId }">
				<td class="td_normal_title" style="width:5%;text-align:center">${vstatus.index+1}</td>
				<input type="hidden" name="fdBorrowDetail_Form[${vstatus.index }].fdStatus" value="${fdBorrowDetail_FormItem.fdStatus }"/>
				<input type="hidden" name="fdBorrowDetail_Form[${vstatus.index }].fdArchId" value="${fdBorrowDetail_FormItem.fdArchId }"/>
				<input type="hidden" class="detailBorrows" name="fdBorrowDetail_Form[${vstatus.index }].fdBorrowerId" value="${fdBorrowDetail_FormItem.fdBorrowerId }"/>
				<td style="width:15%;text-align:center">
			     	<c:out value="${fdBorrowDetail_FormItem.fdArchives.docSubject }"/>
				</td>
				
				<td style="width:10%;text-align:center">
			     	<c:out value="${fdBorrowDetail_FormItem.fdArchives.docTemplate.fdName }"/>
				</td>
				
				<td style="width:10%;text-align:center">
					<c:choose>
						<c:when test="${not empty fdBorrowDetail_FormItem.fdArchives.fdValidityDate}">
							<kmss:showDate value="${fdBorrowDetail_FormItem.fdArchives.fdValidityDate }" type="date"></kmss:showDate>
						</c:when>
						<c:otherwise>
							<bean:message bundle="km-archives" key="kmArchivesMain.fdValidityDate.forever"/>
						</c:otherwise>
					</c:choose>
				</td>
				
				<td style="width:30%;text-align:center">
					<xform:checkbox property="fdBorrowDetail_Form[${vstatus.index }].fdAuthorityRange" value="${fdBorrowDetail_FormItem.fdAuthorityRange }" >
						<xform:simpleDataSource value="copy">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.copy') }</xform:simpleDataSource>
						<xform:simpleDataSource value="download">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.download') }</xform:simpleDataSource>
						<xform:simpleDataSource value="print">${lfn:message('km-archives:kmArchivesConfig.fdDefaultRange.print') }</xform:simpleDataSource>
					</xform:checkbox>
				</td>
				
				<td style="width:10%;text-align:center">
					<xform:datetime onValueChange="null" property="fdBorrowDetail_Form[${vstatus.index }].fdReturnDate" value="${fdBorrowDetail_FormItem.fdReturnDate }" dateTimeType="datetime" showStatus="edit" required="true" validators="after returnDateValidator(${fdBorrowDetail_FormItem.fdValidityDate})" subject="${lfn:message('km-archives:kmArchivesDetails.fdReturnDate')}"/>
				</td>
				
				<td class="td_normal_title" style="width:5%;text-align:center">
				    <img src="${KMSS_Parameter_StylePath}icons/delete.gif" style="cursor:pointer" onclick="deleteRow('${fdBorrowDetail_FormItem.fdArchives.fdId}')">
				</td>
			</tr>
		</c:forEach>
	</table>
	<input type="hidden" name="fdBorrowDetail_Flag" value="1">
     <script>
         Com_IncludeFile("doclist.js");
     </script>
     <script>
         DocList_Info.push('TABLE_DocList_fdBorrowDetail_Form');
     </script>