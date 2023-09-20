<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/kmss-xform.tld" prefix="xform"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script language="JavaScript">
	Com_IncludeFile("jquery.js|dialog.js|doclist.js|optbar.js|data.js|base64.js");
</script>
<style>
.lui_tabhead{
   list-style:none;
}
.lui_tabhead > li{
   padding:5px 12px;
   float:left;
   position:relative;
   border:1px solid;
}
.lui_tabhead > li.active{
  background-color:#4285f4;
  color:#fff;
}
</style>
<html:form action="/tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting.do">
<div id="optBarDiv">
	<c:if test="${ticRestPrefixReqSettingForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="waitSubmit('update');">
	</c:if>
	<c:if test="${ticRestPrefixReqSettingForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="waitSubmit('save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<p class="txttitle">${lfn:message('tic-rest-connector:ticRestMain.preRequestSetManage')}</p>
<table class="tb_normal" width=95%>
	<!-- 服务名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestPrefixReqSetting.docSubject"/>
		</td><td  width="85%" colspan="3">
			<xform:text property="docSubject" style="width:85%" required="true"/>
		</td>
	</tr>

	<tr>
		 <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestPrefixReqSetting.fdUseCustCt"/>
		</td>
		<td  width="85%" colspan="3">
			<xform:radio property="fdUseCustCt" onValueChange="changePrefixReqSettingEnable();">
				<xform:enumsDataSource enumsType="rest_fdPrefixReqSetting_type"  />
			</xform:radio>
		</td>
	</tr>
	
	 <tr>
		<td colspan="4">
		<table class="tb_normal" id="prefixReqDef" width=100% style="display:none;">
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestPrefixReqSetting.fdPrefixReqSettingClazz"/>
					<td  width="85%" colspan="3">
					<xform:text property="fdPrefixReqSettingClazz" style="width:85%" />
					<br>
					<font color="red"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdPrefixReq.note"/></font>
				</td>
			</tr>
			
				    	<tr>	
							<td colspan="4">
							<input type="button" class="btnopt" value="${lfn:message('tic-rest-connector:ticRestSetting.debugGetBody')}"
								onclick="getPrefixReq('${ticRestPrefixReqSettingForm.fdId}','p_prefixReqStr1');" />
							   <br>
							   <p id="p_prefixReqStr1">
							   
							   </p>
							</td>
						</tr>
		</table>
		<table class="tb_normal" id="prefixReqSet" width=100%>
			<tr>
				<td colspan="4">
					<bean:message bundle="tic-rest-connector" key="ticRestPrefixReqSetting.fdMethod"/>
					<xform:select property="fdMethod">
						<xform:enumsDataSource enumsType="rest_fdReqMethod_values" />
					</xform:select>
					<bean:message bundle="tic-rest-connector" key="ticRestPrefixReqSetting.fdPrefixReqSettingURL"/>
					<xform:text property="fdPrefixReqSettingURL" style="width:65%" />
			  </td>
			</tr>
			<tr>
			   <td colspan="4">
					<div class="lui_tab_heading">
					   <ul class="lui_tabhead">
			              <li name="lui_head_tab" class="active" onclick="setUrl_param(this,'prefixReqSet_header');"><a href="javascript:void(0)" >${lfn:message('tic-rest-connector:ticRestSetting.headerParam')}</a></li>
			              <li name="lui_head_tab" onclick="setUrl_param(this,'prefixReqSet_body');"><a href="javascript:void(0)" >${lfn:message('tic-rest-connector:ticRestSetting.bodyParam')}</a></li>
			              <li name="lui_head_tab" onclick="setUrl_param(this,'prefixReqSet_token');"><a href="javascript:void(0)">${lfn:message('tic-rest-connector:ticRestSetting.getBodyTest')}</a></li>
			              
			           </ul>
					</div>
			   </td>
			  </tr>
             <tr>
			   <td colspan="4" id="prefixReqSet_table">
				    <table   class="tb_normal" id="prefixReqSet_header" width=100%>
				    	<tr>	
							<td colspan="4">
								 ${lfn:message('tic-rest-connector:ticRestSetting.fastDefineHeaderParam')}：
								<xform:checkbox property="fdPrefixReqSettingAccept" onValueChange="headerSelect(this)">
									<xform:enumsDataSource enumsType="rest_fdReqHeader"  />
								</xform:checkbox>
							</td>
						</tr>
						<tr >
			            	<td class="td_normal_title" width="40%">
									${lfn:message('tic-core-common:ticCoreTransSett.paramName')}
									</td>
			            	<td class="td_normal_title" width="50%">
									${lfn:message('tic-core-common:ticCoreCommon.fieldValue')}
									</td>
			            	<td class="td_normal_title" width="10%" align='center'>
									<img  src="${KMSS_Parameter_StylePath}icons/add.gif" title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addRow();"/>
							</td>
						</tr>		


				    </table>
				   <table   class="tb_normal" id="prefixReqSet_body" width=100%  style="display:none">
						<tr>
							<td colspan="4">
							  <xform:textarea property="bodyJson"  style="width:85%" ></xform:textarea>
							</td>
						</tr>
					</table>
					
					<table   class="tb_normal" id="prefixReqSet_token" width=100%   style="display:none">
				    	<tr>	
							<td colspan="4">
							<input type="button" class="btnopt" value="${lfn:message('tic-rest-connector:ticRestSetting.debugGetBody')}"
								onclick="getPrefixReq('${ticRestPrefixReqSettingForm.fdId}','p_prefixReqStr2');" />
							   <br>
							   <p id="p_prefixReqStr2">
							   
							   </p>
							</td>
						</tr>
					</table>
					
				</td>
			</tr>
		</table>
	  </td>
  </tr>
</table>

<input id="fdPrefixReqSettingParam" name="fdPrefixReqSettingParam" readonly="readonly"  type="hidden" ></input>
</center>
<html:hidden property="fdAppType" value="${param.fdAppType}"/>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();	
	function changePrefixReqSettingEnable(){
	   if(document.getElementsByName("fdUseCustCt")[0].checked){
				$("#prefixReqDef").hide();
				$("#prefixReqSet").show();		
	    }else{
				$("#prefixReqDef").show();
				$("#prefixReqSet").hide();			
	   }
	}
	 function setUrl_param(li,tableId){
		 $("#prefixReqSet li").each(function(){
			 $(this).removeClass("active");
		 });
		 $("#prefixReqSet_table table").each(function(index,obj){
			 $(this).hide();
		 });
		 $(li).addClass("active");
		 $("#"+tableId).show();
	 }
	 function addRow(type){
		 var new_row="<tr";
		 if(type){
			 new_row+=" id="+type;
		 }
		new_row+="><td><input  type='text' name='name' style='width:85%' validate='required' class='inputsgl'";
		 if(type){
			 new_row+="value="+type;
		 }
		 new_row+="></input></td>";
		 if(!type){
			  new_row+="<td><input  type='text' name='value' style='width:85%' validate='required' class='inputsgl'></input></td>";
		 } 
		 if(type=='Accept'){
			 new_row+='<td><xform:radio property="fdReqAccept" htmlElementProperties="on_change_fdReqAccept=\"fdReqSelect(this)\""><xform:enumsDataSource enumsType="rest_fdReqHeader_reqAccept" /></xform:radio></td>';
		 }
		 if(type=='Content-type'){
			 new_row+='<td><xform:radio property="fdReqContentType" htmlElementProperties="on_change_fdReqContentType=\"fdReqSelect(this)\""><xform:enumsDataSource enumsType="rest_fdReqHeader_reqContentType" /></xform:radio></td>';
		 }
		 if(type=='Authorization'){
			 new_row+='<td>请求认证：<a class="btn_txt" style="color:#4285f4;" href="javascript:selectHeaderInfo()">HTTP基本认证</a><span id="authorization"></span></td>';
		 }
		 new_row+="<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>";
		 $("#prefixReqSet_header").append(new_row);
	 }
	 function deleteRow(obj){
		 $(obj).parent().parent().remove();
			var name=$(obj).parent().parent().find('[name="name"]').val();
			if(name){
				if(name=="Accept"||name=="Content-type"||name=="Authorization"){
					$("input[name=_fdPrefixReqSettingAccept][value="+name+"]").attr("checked",false); 
				}
			}
	 }
	 
	 function headerSelect(source){
			var headerArray=[];
			if(source.checked){
				$('#prefixReqSet_header tr:not(:first):not(:first)').each(function(){
					headerArray.push($(this).find("input[name=name]").val());
				});
				if(containValue(headerArray,source.value)){
					$("input[name=_fdPrefixReqSettingAccept][value="+source.value+"]").attr("checked",false); 
					alert(source.value+"已存在");
				}else{
					  addRow(source.value);
				}
			}
			else
			 $("#"+source.value).remove();
		}
		function fdReqSelect(source){
			var input_other="<input  type='text' name='other' style='width:85%' validate='required' class='inputsgl'></input>";
			if(source.checked&&source.value=="other"){
				$(source).parent().parent().append($(input_other));
			}else{
				$(source).parent().parent().find("input[name=other]").remove();
			}
		}
		 function selectHeaderInfo() {
				seajs.use(['lui/jquery', 'lui/dialog'], function($, dialog) {
					dialog.iframe('/tic/rest/connector/tic_rest_main/selectHeader_diagram.jsp',"HTTP基本认证",function(json){
						$("#authorization").empty();
						var b64=Base64.encode(json["username"]+":"+json["pwd"]);
						$("#authorization").text("Basic "+b64);
					 },{height:'200',width:'450'});
				});
		 }
	 function waitSubmit(method){
		 if(document.getElementsByName("fdUseCustCt")[0].checked){
		 var fdPrefixReqSettingParam={};
		 fdPrefixReqSettingParam["method"]=$("select[name=fdMethod]").val();
		 fdPrefixReqSettingParam["url"]=$("input[name=fdPrefixReqSettingURL]").val();
		 var fdPrefixReqSettingValue=$("input[name=fdPrefixReqSettingAccept]").val();	 
		 var fdPrefixReqSettingValueArray= fdPrefixReqSettingValue.split(";");
		 var fdHeader=[];
		 $('#prefixReqSet_header tr:not(:first):not(:eq(0))').each(function(){
				var json={};
				json.name=$(this).find("input[name=name]").val();
				//debugger;
				if(containValue(fdPrefixReqSettingValueArray,json.name)){
					if(json.name=='Accept'){
						var otherInput=$(this).find("input[name=other]").val();
						if(typeof(otherInput) != "undefined"){
							json.value=otherInput;
						}else{
							json.value=$(this).find("input[name=fdReqAccept]:checked").val();console.log(json.value);
						}	
					}else if	(json.name=='Content-type'){
						var otherInput=$(this).find("input[name=other]").val();
						if(typeof(otherInput) != "undefined"){
							json.value=otherInput;
						}else{
							json.value=$(this).find("input[name=fdReqContentType]:checked").val();
						}	
					}else if	(json.name=='Authorization'){
						json.value=$("#authorization").text();
					}
				}else{
					json.value=$(this).find("input[name=value]").val();
				}
				fdHeader.push(json);
		});
		 fdPrefixReqSettingParam["header"]=fdHeader;
		 var fdBody=$("textarea[name=bodyJson]").val();
		 fdPrefixReqSettingParam["body"]=Base64.encode(fdBody);
		 $("#fdPrefixReqSettingParam").val(JSON.stringify(fdPrefixReqSettingParam));
		 }
	
		 Com_Submit(document.ticRestPrefixReqSettingForm, method);
	 }
		function containValue(array,value){
			for(var i = 0; i< array.length; i++) {
				if(array[i] == value) {
			        return true;
			    }
			}
		    return false;
		}
	 $(document).ready(function() {
		 changePrefixReqSettingEnable();
 	     if('${ticRestPrefixReqSettingForm.fdPrefixReqSettingParam}'){
			  var fdPrefixReqSettingParam=JSON.parse('${ticRestPrefixReqSettingForm.fdPrefixReqSettingParam}');
			  $("select[name=fdMethod]").val(fdPrefixReqSettingParam["method"]);
	          $("input[name=fdPrefixReqSettingURL]").val(fdPrefixReqSettingParam["url"]);
	          var fdHeader=fdPrefixReqSettingParam["header"];
	 		 $(fdHeader).each(function(){
	 			var new_row=$("<tr><td><input  type='text' name='name' style='width:85%' validate='required' value='"+this.name+"' class='inputsgl'></input></td>"+
	 					 "<td><input  type='text' name='value' style='width:85%' validate='required'  value='"+this.value+"' class='inputsgl'></input></td>"+
	 					 "<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>");
	 			$("#prefixReqSet_header").append(new_row);
			});
	 		$("textarea[name=bodyJson]").val(Base64.decode(fdPrefixReqSettingParam["body"]));
		 }

	 });

	window.getPrefixReq = function(id,p_id) {
			var fdUseCustCt = true;
			var fdPrefixReqSettingClazz = $("input[name=fdPrefixReqSettingClazz]").val();
			var fdPrefixReqSettingParam = "{}";
			//debugger;
			if(document.getElementsByName("fdUseCustCt")[0].checked){
				fdUseCustCt = false;
				 var fdPrefixReqSettingParam={};
				 fdPrefixReqSettingParam["method"]=$("select[name=fdMethod]").val();
				 fdPrefixReqSettingParam["url"]=$("input[name=fdPrefixReqSettingURL]").val();
				 
				 var fdHeader=[];
				 $('#prefixReqSet_header tr:not(:first):not(:eq(0))').each(function(){
						var json={};
						//debugger;
						json.name=$(this).find("input[name=name]").val();
						
						if (json.name == 'Accept') {
							var otherInput = $(this).find(
									"input[name=other]")
									.val();
							if (typeof (otherInput) != "undefined") {
								json.value = otherInput;
							} else {
								var valueChecked = $(this)
										.find(
												"input[name=fdReqAccept]:checked")
										.val();
								if (typeof (valueChecked) != "undefined") {
									json.value = valueChecked;
								}else{
									json.value = $(this).find("input[name=value]").val();
								}
								console.log(json.value);
							}
						} else if (json.name == 'Content-type') {
							var otherInput = $(this).find(
									"input[name=other]")
									.val();
							if (typeof (otherInput) != "undefined") {
								json.value = otherInput;
							} else {
								var valueChecked = json.value = $(this)
										.find(
												"input[name=fdReqContentType]:checked")
										.val();
								if (typeof (valueChecked) != "undefined") {
									json.value = valueChecked;
								}else{
									json.value = $(this).find("input[name=value]").val();
								}
							}
						} else if (json.name == 'Authorization') {
							if($(this).find("input[name=value]").length>0){
								json.value = $(this).find("input[name=value]").val();
							}else{
								json.value = $(this).find("span[id=authorization]").text();
							}
							
						}else{
							json.value=$(this).find("input[name=value]").val();
						}
						
						fdHeader.push(json);
				});
				 fdPrefixReqSettingParam["header"]=fdHeader;
				 var fdBody=$("textarea[name=bodyJson]").val();
				 fdPrefixReqSettingParam["body"]=Base64.encode(fdBody);
				 fdPrefixReqSettingParam = JSON.stringify(fdPrefixReqSettingParam);
				 
			}
			
			var accessTokenUrl = Com_Parameter.ContextPath+'tic/rest/connector/tic_rest_prefixReq_setting/ticRestPrefixReqSetting.do?method=getPrefixReq&v=new Date()';
			$.ajax({
				url: accessTokenUrl,
				type: 'GET',
				data:{fdPrefixReqSettingParam:fdPrefixReqSettingParam,fdUseCustCt:fdUseCustCt,fdPrefixReqSettingClazz:fdPrefixReqSettingClazz},
				dataType: 'json',
				error: function(data){
					console.log(data)
					alert(data.responseJSON);
				},
				success: function(data){
					if(data["errcode"]=="0"){
						alert("获取的body="+data["prefixReq"]);
						$("#"+p_id).text("获取到的返回信息："+data["prefixReq"]);
					}else{
						alert("请求出错："+data["errmsg"]);
					}
				}
		   });
		}

</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
