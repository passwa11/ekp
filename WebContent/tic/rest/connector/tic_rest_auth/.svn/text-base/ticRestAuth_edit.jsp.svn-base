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
<html:form action="/tic/rest/connector/tic_rest_auth/ticRestAuth.do">
<div id="optBarDiv">
	<c:if test="${ticRestAuthForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="waitSubmit('update');">
	</c:if>
	<c:if test="${ticRestAuthForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="waitSubmit('save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<p class="txttitle">REST${lfn:message('tic-rest-connector:table.ticRestAuth')}</p>
<table class="tb_normal" width=95%>
	<!-- 服务名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestAuth.docSubject"/>
		</td><td  width="85%" colspan="3">
			<xform:text property="docSubject" style="width:85%" required="true"/>
		</td>
	</tr>

	<tr>
		 <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestAuth.fdUseCustAt"/>
		</td>
		<td  width="85%" colspan="3">
			<xform:radio property="fdUseCustAt" onValueChange="changeOauthEnable();">
				<xform:enumsDataSource enumsType="rest_fdOauth_type"  />
			</xform:radio>
		</td>
	</tr>
	<tr>
		 <td class="td_normal_title" width=15%>
			AgentID
		</td>
		<td  width="85%" colspan="3">
			<xform:text property="fdAgentId" style="width:85%" value="${ticRestAuthForm.fdAgentId}"></xform:text>
		</td>
	</tr>
	 <tr>
		<td colspan="4">
		<table class="tb_normal" id="oauthDef" width=100% style="display:none;"
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestAuth.fdAccessTokenClazz"/>
					<td  width="85%" colspan="3">
					<xform:text property="fdAccessTokenClazz" style="width:85%" />
					<br>
					<font color="red"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdUseCustAt.note"/></font>
				</td>
			</tr>
		</table>
		<table class="tb_normal" id="oauthSet" width=100%>
			<tr>
				<td colspan="4">
					<bean:message bundle="tic-rest-connector" key="ticRestAuth.fdMethod"/>
					<xform:select property="fdMethod">
						<xform:enumsDataSource enumsType="rest_fdReqMethod_values" />
					</xform:select>
					<bean:message bundle="tic-rest-connector" key="ticRestAuth.fdAccessTokenURL"/>
					<xform:text property="fdAccessTokenURL" style="width:65%" />
			  </td>
			</tr>
			<tr>
			   <td colspan="4">
					<div class="lui_tab_heading">
					   <ul class="lui_tabhead">
			              <li name="lui_head_tab" class="active" onclick="setUrl_param(this,'oauthSet_header');"><a href="javascript:void(0)" >${lfn:message('tic-rest-connector:ticRestSetting.headerParam')}</a></li>
			              <li name="lui_head_tab" onclick="setUrl_param(this,'oauthSet_body');"><a href="javascript:void(0)" >${lfn:message('tic-rest-connector:ticRestSetting.bodyParam')}</a></li>
			              <li name="lui_head_tab" onclick="setUrl_param(this,'oauthSet_token');"><a href="javascript:void(0)">${lfn:message('tic-rest-connector:ticRestSetting.getAccessTokenTest')}</a></li>
			           </ul>
					</div>
			   </td>
			  </tr>
             <tr>
			   <td colspan="4" id="oauthSet_table">
				    <table   class="tb_normal" id="oauthSet_header" width=100%>
				    	<tr>	
							<td colspan="4">
								 ${lfn:message('tic-rest-connector:ticRestSetting.fastDefineHeaderParam')}：
								<xform:checkbox property="fdAuthAccept" onValueChange="headerSelect(this)">
									<xform:enumsDataSource enumsType="rest_fdReqHeader"  />
								</xform:checkbox>
							</td>
						</tr>
						<tr >
			            	<td class="td_normal_title" width="40%">
									${lfn:message('tic-rest-connector:ticRestMain.fdReqBizParam.set.name')}
									</td>
			            	<td class="td_normal_title" width="50%">
									${lfn:message('tic-core-common:ticCoreCommon.fieldValue')}
									</td>
			            	<td class="td_normal_title" width="10%" align='center'>
									<img  src="${KMSS_Parameter_StylePath}icons/add.gif" title="${lfn:message('tic-core-common:ticCoreCommon.add')}" onClick="addRow();"/>
							</td>
						</tr>		


				    </table>
				   <table   class="tb_normal" id="oauthSet_body" width=100%  style="display:none">
						<tr>
							<td colspan="4">
							  <xform:textarea property="bodyJson"  style="width:85%" ></xform:textarea>
							</td>
						</tr>
					</table>
					<table   class="tb_normal" id="oauthSet_token" width=100%  style="display:none">
				    	<tr>	
							<td colspan="4">
							<input type="button" class="btnopt" value="${lfn:message('tic-rest-connector:ticRestSetting.debugGetAccessToken')}"
								onclick="getAccessToken('${ticRestAuthForm.fdId}');" />
							   <br>
							   <span id="accessToken"></span>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	  </td>
  </tr>
</table>
<input id="fdAccessTokenParam" name="fdAccessTokenParam" readonly="readonly"  type="hidden" ></input>
</center>
<html:hidden property="fdAppType" value="${param.fdAppType}"/>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();	
	function changeOauthEnable(){
	   if(document.getElementsByName("fdUseCustAt")[0].checked){
				$("#oauthDef").hide();
				$("#oauthSet").show();		
	    }else{
				$("#oauthDef").show();
				$("#oauthSet").hide();			
	   }
	}
	 function setUrl_param(li,tableId){
		 $("#oauthSet li").each(function(){
			 $(this).removeClass("active");
		 });
		 $("#oauthSet_table table").each(function(index,obj){
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
		 $("#oauthSet_header").append(new_row);
	 }
	 function deleteRow(obj){
		 $(obj).parent().parent().remove();
			var name=$(obj).parent().parent().find('[name="name"]').val();
			if(name){
				if(name=="Accept"||name=="Content-type"||name=="Authorization"){
					$("input[name=_fdAuthAccept][value="+name+"]").attr("checked",false); 
				}
			}
	 }
	 
	 function headerSelect(source){
			var headerArray=[];
			if(source.checked){
				$('#oauthSet_header tr:not(:first):not(:first)').each(function(){
					headerArray.push($(this).find("input[name=name]").val());
				});
				if(containValue(headerArray,source.value)){
					$("input[name=_fdAuthAccept][value="+source.value+"]").attr("checked",false); 
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
		 getTokenParamJson();
		 Com_Submit(document.ticRestAuthForm, method);
	 }
	 function getTokenParamJson(){
		 if(document.getElementsByName("fdUseCustAt")[0].checked){
			 var fdAccessTokenParam={};
			 fdAccessTokenParam["method"]=$("select[name=fdMethod]").val();
			 fdAccessTokenParam["url"]=$("input[name=fdAccessTokenURL]").val();
			 var fdAuthHeaderValue=$("input[name=fdAuthAccept]").val();	 
			 var fdAuthHeaderValueArray= fdAuthHeaderValue.split(";");
			 var fdHeader=[];
			 $('#oauthSet_header tr:not(:first):not(:eq(0))').each(function(){
					var json={};
					json.name=$(this).find("input[name=name]").val();
					if(containValue(fdAuthHeaderValueArray,json.name)){
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
			 fdAccessTokenParam["header"]=fdHeader;
			 var fdBody=$("textarea[name=bodyJson]").val();
			 if(fdBody && fdBody!=''){
				 fdAccessTokenParam["body"]=$.parseJSON(fdBody);
			 }
			 $("#fdAccessTokenParam").val(JSON.stringify(fdAccessTokenParam));
			 }
		 return JSON.stringify(fdAccessTokenParam);
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
		 changeOauthEnable();
 	     if('${ticRestAuthForm.fdAccessTokenParam}'){
			  var fdAccessTokenParam=JSON.parse('${ticRestAuthForm.fdAccessTokenParam}');
			  $("select[name=fdMethod]").val(fdAccessTokenParam["method"]);
	          $("input[name=fdAccessTokenURL]").val(fdAccessTokenParam["url"]);
	          var fdHeader=fdAccessTokenParam["header"];
	 		 $(fdHeader).each(function(){
	 			var new_row=$("<tr><td><input  type='text' name='name' style='width:85%' validate='required' value='"+this.name+"' class='inputsgl'></input></td>"+
	 					 "<td><input  type='text' name='value' style='width:85%' validate='required'  value='"+this.value+"' class='inputsgl'></input></td>"+
	 					 "<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>");
	 			$("#oauthSet_header").append(new_row);
			});
	 		$("textarea[name=bodyJson]").val( JSON.stringify(fdAccessTokenParam["body"]));
		 }

	 });

	window.getAccessToken = function(id) {
			var accessTokenUrl = Com_Parameter.ContextPath+'tic/rest/connector/tic_rest_auth/ticRestAuth.do?method=getAccessToken';
			var fdAccessTokenParam=getTokenParamJson();
			var fdUseCustAt=$("input[name=fdUseCustAt]").prop('checked');
			var fdAgentId=$("input[name=fdAgentId]").val();
			var fdAccessTokenClazz=$("input[name=fdAccessTokenClazz]").val();
			$.ajax({
				url: accessTokenUrl,
				type: 'GET',
				data:{fdAccessTokenParam:fdAccessTokenParam,fdUseCustAt:fdUseCustAt,fdAgentId:fdAgentId,fdAccessTokenClazz:fdAccessTokenClazz},
				dataType: 'json',
				error: function(data){
					console.log(data)
					alert(data.responseJSON);
				},
				success: function(data){
					if(data["errcode"]=="0"){
						//alert("获取的access_token="+data["access_token"]);
						$("#accessToken").text("获取的access_token："+data["access_token"]);
					}else{
						alert("获取access_token出错："+data["errmsg"]);
					}
				}
		   }); 
		}

</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
