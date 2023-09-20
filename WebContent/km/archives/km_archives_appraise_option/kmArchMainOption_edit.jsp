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
			var arguments = new Object();
			arguments.title="${lfn:message('km-archives:kmArchivesBorrow.selectArc')}";
			var href = Com_GetCurDnsHost()+"${KMSS_Parameter_ContextPath}km/archives/km_archives_appraise_option/kmArchOption_add.jsp?selected="+selected+"&fdId="+fdApplicationId;
			dialog.iframe(href,"${lfn:message('button.create')}",function(rtnData){
			  if(rtnData != null){
				getContent(rtnData);
	  		  }else{
	  			_validation.validateElement(document.getElementById("archValidate"));
	  		  }
			},{width:900,height:550});
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
			var url = "${KMSS_Parameter_ContextPath}km/archives/km_archives_appraise/kmArchAppraiseOption.do?method=loadArchMain&fdId="+ rtnData;
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
					trHTML += '<input type="hidden" name="fdAppraiseDetail_Form['+i+'].fdId" value=""/>';
					trHTML += '<input type="hidden" name="fdAppraiseDetail_Form['+i+'].fdArchivesId" value="'+results[i].fdId+'"/>';
					trHTML += '<td>'+(i+1)+'</td>';
					trHTML += '<td>';
					trHTML += results[i].docSubject;
					trHTML += '</td>';
					trHTML += '<td>';
					trHTML += results[i].docNumber;
					trHTML += '</td>';
					trHTML += '<td>';
					trHTML += '<input type="hidden" name="fdAppraiseDetail_Form['+i+'].fdOriginalDate" value="'+results[i].fdValidityDate+'"/>';
					trHTML += results[i].fdValidityDate;
					trHTML += '</td>';
					
					
					trHTML += '<td>';
					trHTML += '<div class="inputselectsgl" onclick="selectDate(\'fdAppraiseDetail_Form['+i+'].fdAfterAppraiseDate\')" style="width:150px;">';
					trHTML += '<div class="input"><input type="text" name="fdAppraiseDetail_Form['+i+'].fdAfterAppraiseDate" value="" validate="required __date noEarlierthanBefore('+results[i].fdValidityDate+')"  subject="${lfn:message("km-archives:kmArchivesAppraiseDetails.fdAfterAppraiseDate")}"/></div><div class="inputdatetime"></div>';
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
	<center><b><bean:message bundle="km-archives" key="kmArchivesAppraise.details" /></b></center>
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
		<tr>
			<td align="center" class="td_normal_title" width="5%">
				${lfn:message('page.serial')}
			</td>
			<td align="center" class="td_normal_title" width="30%">
				<bean:message bundle="km-archives" key="kmArchivesAppraise.fdArchivesName" />
			</td>
			<td align="center" class="td_normal_title" width="24%">
				<bean:message bundle="km-archives" key="kmArchivesAppraise.fdArchivesNumber" />
			</td>
			<td align="center" class="td_normal_title" width="18%">
				<bean:message bundle="km-archives" key="kmArchivesAppraise.fdOriginalDate" />
			</td>
			<td align="center" class="td_normal_title" width="18%">
				<bean:message bundle="km-archives" key="kmArchivesAppraise.fdAfterAppraiseDate" />
			</td>
			<td align="center" class="td_normal_title" width="5%">
				${lfn:message('list.operation')}
			</td>
		</tr>
		
		<c:forEach items="${kmArchivesAppraiseForm.fdAppraiseDetail_Form }" var="item" varStatus="varStatus">
	 		<tr name="data-tr" id="${item.fdArchivesId }">
	 			<td align="center">
	 				<input type="hidden" name="fdAppraiseDetail_Form[${varStatus.index }].fdId" value="${item.fdId }"/>
					${varStatus.index+1 }
				</td>
				<td align="center">
					${item.fdArchivesName }
				</td>
				<td align="center">
					${item.fdArchivesNumber }
				</td>
				<td align="center">
					${item.fdOriginalDate }
				</td>
				<td align="center">
					<xform:datetime onValueChange="null" property="fdAppraiseDetail_Form[${varStatus.index }].fdAfterAppraiseDate" 
					required="true" subject="${lfn:message('km-archives:kmArchivesAppraise.fdAfterAppraiseDate') }" 
					dateTimeType="date" style="width:100px" validators="noEarlierthanBefore(${item.fdOriginalDate })"></xform:datetime>
				</td>
				<td align="center">
					<img src="${KMSS_Parameter_StylePath}icons/delete.gif" style="cursor:pointer" 
					onclick="deleteRow('${item.fdArchivesId}')">
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