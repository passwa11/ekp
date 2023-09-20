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
<html:form action="/tic/rest/connector/tic_rest_cookie_setting/ticRestCookieSetting.do">
<div id="optBarDiv">
	<c:if test="${ticRestCookieSettingForm.method_GET=='edit'}">
		<input type=button value="<bean:message key="button.update"/>"
			onclick="waitSubmit('update');">
	</c:if>
	<c:if test="${ticRestCookieSettingForm.method_GET=='add'}">
		<input type=button value="<bean:message key="button.save"/>"
			onclick="waitSubmit('save');">
	</c:if>
	<input type="button" value="<bean:message key="button.close"/>" onclick="Com_CloseWindow();">
</div>
<center>
<p class="txttitle">${lfn:message('tic-rest-connector:ticRestSetting.cookieSetManage')}</p>
<table class="tb_normal" width=95%>
	<!-- 服务名称 -->
	<tr>
		<td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestCookieSetting.docSubject"/>
		</td><td  width="85%" colspan="3">
			<xform:text property="docSubject" style="width:85%" required="true"/>
		</td>
	</tr>

	<tr>
		 <td class="td_normal_title" width=15%>
			<bean:message bundle="tic-rest-connector" key="ticRestCookieSetting.fdUseCustCt"/>
		</td>
		<td  width="85%" colspan="3">
			<xform:radio property="fdUseCustCt" onValueChange="changeCookieSettingEnable();">
				<xform:enumsDataSource enumsType="rest_fdCookieSetting_type"  />
			</xform:radio>
		</td>
	</tr>
	
	 <tr>
		<td colspan="4">
		<table class="tb_normal" id="cookieDef" width=100% style="display:none;"
			<tr>	
				<td class="td_normal_title" width=15%>
					<bean:message bundle="tic-rest-connector" key="ticRestCookieSetting.fdCookieSettingClazz"/>
					<td  width="85%" colspan="3">
					<xform:text property="fdCookieSettingClazz" style="width:85%" />
					<br>
					<font color="red"><bean:message bundle="tic-rest-connector" key="ticRestMain.fdUseCustCt.note"/></font>
				</td>
			</tr>
			
				    	<tr>	
							<td colspan="4">
							<input type="button" class="btnopt" value="调试获取Cookie"
								onclick="getCookie('${ticRestCookieSettingForm.fdId}','p_cookieStr1');" />
							   <br>
							   <p id="p_cookieStr1">
							   
							   </p>
							</td>
						</tr>
		</table>
		<table class="tb_normal" id="cookieSet" width=100%>
			<tr>
				<td colspan="4">
					<bean:message bundle="tic-rest-connector" key="ticRestCookieSetting.fdMethod"/>
					<xform:select property="fdMethod">
						<xform:enumsDataSource enumsType="rest_fdReqMethod_values" />
					</xform:select>
					<bean:message bundle="tic-rest-connector" key="ticRestCookieSetting.fdCookieSettingURL"/>
					<xform:text property="fdCookieSettingURL" style="width:65%" />
			  </td>
			</tr>
			<tr>
			   <td colspan="4">
					<div class="lui_tab_heading">
					   <ul class="lui_tabhead">
			              <li name="lui_head_tab" class="active" onclick="setUrl_param(this,'cookieSet_header');"><a href="javascript:void(0)" >${lfn:message('tic-rest-connector:ticRestSetting.headerParam')}</a></li>
			              <li name="lui_head_tab" onclick="setUrl_param(this,'cookieSet_body');"><a href="javascript:void(0)" >${lfn:message('tic-rest-connector:ticRestSetting.bodyParam')}</a></li>
			              <li name="lui_head_tab" onclick="setUrl_param(this,'cookieSet_token');"><a href="javascript:void(0)">${lfn:message('tic-rest-connector:ticRestSetting.getCookieTest')}</a></li>
			              
			           </ul>
					</div>
			   </td>
			  </tr>
             <tr>
			   <td colspan="4" id="cookieSet_table">
				    <table   class="tb_normal" id="cookieSet_header" width=100%>
				    	<tr>	
							<td colspan="4">
								 ${lfn:message('tic-rest-connector:ticRestSetting.fastDefineHeaderParam')}：
								<xform:checkbox property="fdCookieSettingAccept" onValueChange="headerSelect(this)">
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
				   <table   class="tb_normal" id="cookieSet_body" width=100%  style="display:none">
						<tr>
							<td colspan="4">
							  <xform:textarea property="bodyJson"  style="width:85%" ></xform:textarea>
							</td>
						</tr>
					</table>
					
					<table   class="tb_normal" id="cookieSet_token" width=100%   style="display:none">
				    	<tr>	
							<td colspan="4">
							<input type="button" class="btnopt" value="调试获取Cookie"
								onclick="getCookie('${ticRestCookieSettingForm.fdId}','p_cookieStr2');" />
							   <br>
							   <p id="p_cookieStr2">
							   
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

<input id="fdCookieSettingParam" name="fdCookieSettingParam" readonly="readonly"  type="hidden" ></input>
</center>
<html:hidden property="fdAppType" value="${param.fdAppType}"/>
<html:hidden property="fdId" />
<html:hidden property="method_GET" />
<script>
	$KMSSValidation();	
	function changeCookieSettingEnable(){
	   if(document.getElementsByName("fdUseCustCt")[0].checked){
				$("#cookieDef").hide();
				$("#cookieSet").show();		
	    }else{
				$("#cookieDef").show();
				$("#cookieSet").hide();			
	   }
	}
	 function setUrl_param(li,tableId){
		 $("#cookieSet li").each(function(){
			 $(this).removeClass("active");
		 });
		 $("#cookieSet_table table").each(function(index,obj){
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
		 $("#cookieSet_header").append(new_row);
	 }
	 function deleteRow(obj){
		 $(obj).parent().parent().remove();
			var name=$(obj).parent().parent().find('[name="name"]').val();
			if(name){
				if(name=="Accept"||name=="Content-type"||name=="Authorization"){
					$("input[name=_fdCookieSettingAccept][value="+name+"]").attr("checked",false); 
				}
			}
	 }
	 
	 function headerSelect(source){
			var headerArray=[];
			if(source.checked){
				$('#cookieSet_header tr:not(:first):not(:first)').each(function(){
					headerArray.push($(this).find("input[name=name]").val());
				});
				if(containValue(headerArray,source.value)){
					$("input[name=_fdCookieSettingAccept][value="+source.value+"]").attr("checked",false); 
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
		 var fdCookieSettingParam={};
		 fdCookieSettingParam["method"]=$("select[name=fdMethod]").val();
		 fdCookieSettingParam["url"]=$("input[name=fdCookieSettingURL]").val();
		 var fdCookieSettingValue=$("input[name=fdCookieSettingAccept]").val();	 
		 var fdCookieSettingValueArray= fdCookieSettingValue.split(";");
		 var fdHeader=[];
		 $('#cookieSet_header tr:not(:first):not(:eq(0))').each(function(){
				var json={};
				json.name=$(this).find("input[name=name]").val();
				if(containValue(fdCookieSettingValueArray,json.name)){
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
		 fdCookieSettingParam["header"]=fdHeader;
		 var fdBody=$("textarea[name=bodyJson]").val();
		 fdCookieSettingParam["body"]=Base64.encode(fdBody);
		 $("#fdCookieSettingParam").val(JSON.stringify(fdCookieSettingParam));
		 }
	
		 Com_Submit(document.ticRestCookieSettingForm, method);
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
		 changeCookieSettingEnable();
 	     if('${ticRestCookieSettingForm.fdCookieSettingParam}'){
			  var fdCookieSettingParam=JSON.parse('${ticRestCookieSettingForm.fdCookieSettingParam}');
			  $("select[name=fdMethod]").val(fdCookieSettingParam["method"]);
	          $("input[name=fdCookieSettingURL]").val(fdCookieSettingParam["url"]);
	          var fdHeader=fdCookieSettingParam["header"];
	 		 $(fdHeader).each(function(){
	 			var new_row=$("<tr><td><input  type='text' name='name' style='width:85%' validate='required' value='"+this.name+"' class='inputsgl'></input></td>"+
	 					 "<td><input  type='text' name='value' style='width:85%' validate='required'  value='"+this.value+"' class='inputsgl'></input></td>"+
	 					 "<td align='center'><img  src=\"${KMSS_Parameter_StylePath}icons/delete.gif\" title=\"删除\" onClick=\"deleteRow(this);\"/></td></tr>");
	 			$("#cookieSet_header").append(new_row);
			});
	 		$("textarea[name=bodyJson]").val(Base64.decode(fdCookieSettingParam["body"]));
		 }

	 });

	window.getCookie = function(id,p_id) {
			var fdUseCustCt = true;
			var fdCookieSettingClazz = $("input[name=fdCookieSettingClazz]").val();
			var fdCookieSettingParam = "{}";
			
			if(document.getElementsByName("fdUseCustCt")[0].checked){
				fdUseCustCt = false;
				 var fdCookieSettingParam={};
				 fdCookieSettingParam["method"]=$("select[name=fdMethod]").val();
				 fdCookieSettingParam["url"]=$("input[name=fdCookieSettingURL]").val();
				 
				 var fdCookieSettingValue=$("input[name=fdCookieSettingAccept]").val();	 
				 var fdCookieSettingValueArray= fdCookieSettingValue.split(";");
				 var fdHeader=[];
				 $('#cookieSet_header tr:not(:first):not(:eq(0))').each(function(){
					 var json={};
						json.name=$(this).find("input[name=name]").val();
						if(containValue(fdCookieSettingValueArray,json.name)){
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
				 fdCookieSettingParam["header"]=fdHeader;
				 var fdBody=$("textarea[name=bodyJson]").val();
				 fdCookieSettingParam["body"]=Base64.encode(fdBody);
				 fdCookieSettingParam = JSON.stringify(fdCookieSettingParam);
				 
			}
			
			var accessTokenUrl = Com_Parameter.ContextPath+'tic/rest/connector/tic_rest_cookie_setting/ticRestCookieSetting.do?method=getCookie';
			$.ajax({
				url: accessTokenUrl,
				type: 'GET',
				data:{fdCookieSettingParam:fdCookieSettingParam,fdUseCustCt:fdUseCustCt,fdCookieSettingClazz:fdCookieSettingClazz},
				dataType: 'json',
				error: function(data){
					console.log(data)
					alert(data.responseJSON);
				},
				success: function(data){
					if(data["errcode"]=="0"){
						alert("获取的cookie="+data["cookies"]);
						$("#"+p_id).text("获取到的cookie信息："+data["cookies"]);
					}else{
						alert("获取cookie出错："+data["errmsg"]);
					}
				}
		   });
		}

</script>
</html:form>
<%@ include file="/resource/jsp/edit_down.jsp"%>
