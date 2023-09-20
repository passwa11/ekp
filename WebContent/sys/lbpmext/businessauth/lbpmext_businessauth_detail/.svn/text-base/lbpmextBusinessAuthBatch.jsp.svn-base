<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<%@ include file="/sys/lbpmext/businessauth/lbpmext_businessauth_util/lbpmextBusinessAuth_util.jsp"%> 
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<script>
var dialogObject = null;
if(window.showModalDialog) {
	dialogObject = window.dialogArguments;
	if (dialogObject == null) {
		dialogObject = parent.Com_Parameter.Dialog;
	}
} else if(opener) {
	dialogObject = opener.Com_Parameter.Dialog;
} else {
	if (dialogObject == null) {
		dialogObject = parent.Com_Parameter.Dialog;
	}
}

$(function(){
	if(dialogObject){
		var isEdit = "${isEdit}";
		if(isEdit!="true"){
			for(var key in dialogObject){
				if(key == "fdAuthStartTime"){
					$("input[name='fdStartTime']").val(dialogObject[key]);
				}else if(key == "fdAuthEndTime"){
					$("input[name='fdEndTime']").val(dialogObject[key]);
				}else{
					$("input[name='"+key+"']").val(dialogObject[key]);
					if(key=="fdType"){
						var type = dialogObject[key];
						if(type==businessAuth.controlType.qualitative){
							$(".fdLimitTr").hide();
							$("input[name='_fdType']").prop("disabled",false);
						}else if(type==businessAuth.controlType.determined){
							$(".fdLimitTr").show();
							$("input[name='_fdType']").prop("disabled","false");
							$("input[name='_fdType'][value='"+businessAuth.controlType.money+"']").prop("disabled",false);
							$("input[name='_fdType'][value='"+businessAuth.controlType.determined+"']").prop("disabled",false);
						}else{
							$(".fdLimitTr").show();
							$("input[name='_fdType']").prop("disabled","false");
						}
						$("input[name='_fdType'][value='"+type+"']").prop("checked","false");
					}
				}
			}
		}
	}
	if("${lbpmExtBusinessAuthDetailForm.fdType}"==businessAuth.controlType.qualitative){
		$(".fdLimitTr").hide();
		$("input[name='_fdType']").prop("disabled",false);
	}else if("${lbpmExtBusinessAuthDetailForm.fdType}"==businessAuth.controlType.determined){
		$(".fdLimitTr").show();
		$("input[name='_fdType']").prop("disabled","false");
		$("input[name='_fdType'][value='"+businessAuth.controlType.money+"']").prop("disabled",false);
		$("input[name='_fdType'][value='"+businessAuth.controlType.determined+"']").prop("disabled",false);
	}
});
</script>
<style>
.DIV_EditButtons{position: fixed;    left: 0;    right: 0;    text-align: center;    padding-top: 10px;    padding-bottom: 10px;    bottom: 0;    background-color: #fff;    border-top: 1px solid #d5d5d5;    z-index: 99;}
</style>
<html:form action="/sys/lbpmext/businessauth/lbpmBusinessAuthDetail.do">
<center>
<table class="tb_normal" width=95%>
	<tr>
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuthorizer"/>
		</td><td width=75% colspan="3">
			<c:choose>
				<c:when test="${JsParam.isAuthorize eq 'true' || JsParam.cannotChangeAuthorizerAuth eq 'true'}">
					<html:hidden property="fdAuthorizerId" />
					<input type="text" name="fdAuthorizerName" class="inputread_normal" readonly="readonly" value="${lbpmExtBusinessAuthDetailForm.fdAuthorizerName}"/>					
				</c:when>
				<c:otherwise>
					<xform:address propertyId="fdAuthorizerId" propertyName="fdAuthorizerName" orgType="ORG_TYPE_PERSON"
							showStatus="edit" style="width:150px" required="true">
						</xform:address>
					<script type="text/javascript">
						/* （新增）批量新增时带出上个授权人开始 */
						$(document).ready(
							function(){
								if(dialogObject.fdAuthorizerId){
									Address_QuickSelection("fdAuthorizerId","fdAuthorizerName",";",ORG_TYPE_PERSON,false,[{"id":dialogObject.fdAuthorizerId,"name":dialogObject.fdAuthorizerName}],null,null,"");
								}
							}
						);
						/* （新增）批量新增时带出上个授权人结束 */
					</script>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
	<tr style="display:none">
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuthorizedPerson"/>
		</td><td width=75% colspan="3">
			<html:hidden property="fdAuthorizedPersonId" />
			<input type="text" name="fdAuthorizedPersonName" class="inputread_normal" readonly="readonly" value="${lbpmExtBusinessAuthDetailForm.fdAuthorizedPersonName}"/>					
		</td>
	</tr>
	<tr style="display:none">
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuthorizedPost"/>
		</td><td width=75% colspan="3">
			<html:hidden property="fdAuthorizedPostId" />
			<input type="text" name="fdAuthorizedPostName" class="inputread_normal" readonly="readonly" value="${lbpmExtBusinessAuthDetailForm.fdAuthorizedPostName}"/>					
		</td>
	</tr>
	<tr>
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdAuth"/>
		</td><td width=75% colspan="3">
			<html:hidden property="fdAuthId" />
			<textarea cols="35" name="fdAuthName" readonly="readonly" validate="required" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdAuth') }" value="${lbpmExtBusinessAuthDetailForm.fdAuthName}"></textarea>
	
			<span class="txtstrong">*</span>
			<a href="javascript:void(0)" onclick="showFdAuth();">
				<bean:message key="dialog.selectOrg"/>
			</a>				
		</td>
	</tr>
	<!-- 弹出框中隐藏条目分类开始 -->
	<tr style="display:none">
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthCate"/>
		</td>
		<td width=75% colspan="3">
			<textarea cols="35" name="fdCateName" readonly="readonly" validate="required" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthCate') }" value="${lbpmExtBusinessAuthDetailForm.fdCateName}"></textarea>
			<%-- <input type="text" name="fdCateName" class="inputread_normal" readonly="readonly" value="${lbpmExtBusinessAuthDetailForm.fdCateName}"/> --%>						
		</td>
	</tr>	
	<!-- 弹出框中隐藏条目分类结束 --> 
	<tr style="display:none">
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdNumber"/>
		</td><td width=75% colspan="3">
			<input type="text" name="fdNumber" class="inputread_normal" readonly="readonly" value="${lbpmExtBusinessAuthDetailForm.fdNumber}"/>					
		</td>
	</tr>
	<tr>
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdStartTime"/>
		</td><td width=75% colspan="3">
			<xform:datetime property="fdStartTime" dateTimeType="datetime" validators="checkStartTime" showStatus="edit" required="true" style="width:150px;">
				</xform:datetime>					
		</td>
	</tr>
	<tr>
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdEndTime"/>
		</td><td width=75% colspan="3">
			<xform:datetime property="fdEndTime" dateTimeType="datetime" validators="checkEndTime checkEndTime2" showStatus="edit" required="true" style="width:150px;" htmlElementProperties="data-init-hour='23' data-init-minute='59'">
				</xform:datetime>					
		</td>
	</tr>
	<tr style="display:none">
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdType"/>
		</td><td width=75% colspan="3">
			<xform:radio property="fdType" showStatus="readonly" onValueChange="fdTypeValueChange">
			   	<xform:customizeDataSource className="com.landray.kmss.sys.lbpmext.businessauth.service.spring.LbpmExtBusinessAuthTypeDataSource"></xform:customizeDataSource>
			</xform:radio>
		</td>
	</tr>
	<tr class="fdLimitTr" style="display:none">
		<td width=25% class="td_normal_title">
			<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthDetail.fdLimit"/>
		</td><td width=75% colspan="3">
			<input type="text" name="fdMinLimit" class="inputsgl" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdMinLimit') }" value="${lbpmExtBusinessAuthDetailForm.fdMinLimit}" />		
			~
			<input type="text" name="fdLimit" class="inputsgl" subject="${lfn:message('sys-lbpmext-businessauth:lbpmext.businessAuthDetail.fdMaxLimit') }" value="${lbpmExtBusinessAuthDetailForm.fdLimit}"  /><span class="txtstrong">*</span>	
			</td>
	</tr>
	<tr style="display:none">
		<!-- 创建人员 -->
		<td class="td_normal_title" width=25%>
			<bean:message key="model.fdCreator" />
		</td>
		<td width=75%>
			<html:hidden property="fdCreatorId" />
			<html:text property="fdCreatorName" readonly="true" style="width:50%;" />
		</td>
	</tr>
	<tr style="display: none">
		<!-- 创建时间 -->
		<td class="td_normal_title" width=25%>
			<bean:message key="model.fdCreateTime" />
		</td>
		<td width=75%>
			<html:text property="fdCreateTime" readonly="true" style="width:50%;" />
		</td>
	</tr>
</table>
<div class="DIV_EditButtons" style="height:46px;">
	<ui:button text="${lfn:message('button.save') }" order="1" onclick="save();"  style="width:77px;">
	</ui:button>
	<ui:button text="${lfn:message('button.cancel') }" order="2" onclick="closeDialog();" style="width:77px;padding-left:10px">
	</ui:button>
</div>
</center>
<html:hidden property="method_GET"/>
<html:hidden property="fdId"/>
</html:form>
<script>
function Dialog_AuthTreeList(mulSelect, idField, nameField, splitStr, treeBean, treeTitle, dataBean, action, searchBean, exceptValue, isMulField, notNull, winTitle){
	var dialog = new KMSSDialog(mulSelect, true);
	var node = dialog.CreateTree(treeTitle);
	dialog.winTitle = winTitle==null?treeTitle:winTitle;
	node.AppendBeanData(treeBean, dataBean, null, null, exceptValue);
	node.parameter = dataBean;
	dialog.BindingField(idField, nameField, splitStr, isMulField);
	dialog.SetAfterShow(action);
	if(searchBean!=null)
		dialog.SetSearchBeanData(searchBean);
	if(notNull!=null)
		dialog.notNull = notNull;
	dialog.Show(860);
}
function showFdAuth(){
	//获取当前已设置的id
	var fdAuthIds = $('input[name$=".fdAuthId"]',window.parent.document);
	var exceptValueArr =[];
	if(fdAuthIds && fdAuthIds.length>0){
		for(var i = 0;i<fdAuthIds.length;i++){
			exceptValueArr.push(fdAuthIds[i].value);
		}
	}
	var exceptValues = exceptValueArr.join(";");
	Dialog_AuthTreeList(true,'fdAuthId','fdAuthName',null,'lbpmExtBusinessAuthCateService&parentId=!{value}','<bean:message bundle="sys-lbpmext-businessauth" key="lbpmext.businessAuthInfo.add.title"/>','lbpmExtBusinessAuthService&filterAuth=true&parentId=!{value}&exceptValues='+exceptValues,function(rtnVal){
		if(rtnVal){
			if(rtnVal.data && rtnVal.data.length>0){
				var names = [];
				//新建条目分类数组
				var fdCateNames = [];
				for(var i =0;i < rtnVal.data.length;i++){
					names.push(rtnVal.data[i].fdName);
					//给条目分类文本装入
					fdCateNames.push(rtnVal.data[i].fdCateName);
				}
				$("textarea[name='fdAuthName']").val(names.join(";"));
				//给条目分类文本进行赋值操作。
				$("textarea[name='fdCateName']").val(fdCateNames.join(";"));
				//记录当前上下文
				window.parent.__fdAuthInfoMaps = rtnVal.data;
			}
		}
	},'lbpmExtBusinessAuthService&filterAuth=true&search=!{keyword}','171e3459a7dc93704666a474150b0f04');
}

function setValidationByScale(fdScale){
	$("input[name='fdMinLimit']").attr("validate","required number min(0) scaleLength("+fdScale+")");
	$("input[name='fdLimit']").attr("validate","required number checkLimit scaleLength("+fdScale+")");
}

function fdTypeValueChange(value,dom){
	if(value=="3"){
		$(".fdLimitTr").hide();
		$("input[name='fdLimit']").val(0);
	}else{
		$(".fdLimitTr").show();
		$("input[name='fdLimit']").val("");
	}
	$("input[name='fdType']").val(value);
	setValidationByScale(businessAuth.getScaleLengthName(value));
}

function save(){
	if(!_$validation.validate()){
		return;
	}
	returnValue = {
			fdAuthorizerId:$("input[name='fdAuthorizerId']").val(),
			fdAuthorizerName:$("input[name='fdAuthorizerName']").val(),
			fdAuthId:$("input[name='fdAuthId']").val(),
			fdAuthName:$("textarea[name='fdAuthName']").val(),
			//给条目分类文本进行赋值操作。
			fdCateName:$("textarea[name='fdCateName']").val(),
			fdNumber:$("input[name='fdNumber']").val(),
			fdStartTime:$("input[name='fdStartTime']").val(),
			fdEndTime:$("input[name='fdEndTime']").val(),
			fdType:$("input[name='fdType']").val(),
			fdLimit:$("input[name='fdLimit']").val(),
			fdMinLimit:$("input[name='fdMinLimit']").val(),
			fdCreatorId:$("input[name='fdCreatorId']").val(),
			fdCreatorName:$("input[name='fdCreatorName']").val(),
			fdCreateTime:$("input[name='fdCreateTime']").val(),
	};
	if (typeof(window.$dialog) != 'undefined') {
		$dialog.hide(returnValue);
	} else {
		window.close();
	}
}

function closeDialog(){
	if(!confirm('<bean:message key="message.closeWindow"/>')){
		return;
	}
	Com_CloseWindow();
}

</script>
<html:javascript formName="lbpmExtBusinessAuthDetailForm"  cdata="false"
      dynamicJavascript="true" staticJavascript="false"/>
<script>
_$validation.addValidator('checkStartTime',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.startTimeMsg2'/>",
	function(v,e,o){
		var bool = true;
		var startTime = dialogObject.fdStartTime;
		var endTime = $("input[name='fdEndTime']").val();
		if(v){
			if(startTime){
				if(Date.parse(v)<Date.parse(startTime)){
					bool = false;
				}
			}
			if(endTime){
				if(Date.parse(v)>Date.parse(endTime)){
					bool = false;
				}
			}
		}
		return bool;
	}
);
_$validation.addValidator('checkEndTime',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.endTimeMsg2'/>",
	function(v,e,o){
		var bool = true;
		var endTime = dialogObject.fdEndTime;
		var startTime = $("input[name='fdStartTime']").val();
		if(v){
			if(endTime){
				if(Date.parse(v)>Date.parse(endTime)){
					bool = false;
				}
			}
			if(startTime){
				if(Date.parse(v)<Date.parse(startTime)){
					bool = false;
				}
			}
		}
		return bool;
	}
);

_$validation.addValidator('checkEndTime2',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.endTimeMsg3'/>",
	function(v,e,o){
		if(v){
			if(new Date().getTime()>=Date.parse(v)){
				return false;
			}
		}
		return true;
	}
);

_$validation.addValidator('checkLimit',
	"<bean:message bundle='sys-lbpmext-businessauth' key='lbpmext.businessAuthDetail.limitMsg'/>",
	function(v,e,o){
		if(v){
			var minLimit = $("input[name='fdMinLimit']").val();
			if(minLimit){
				if(parseInt(v)<parseInt(minLimit)){
					return false;
				}
			}
		}
		return true;
	}
);
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>