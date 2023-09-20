<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<style>
.businessAuthItem{
	display:flex;
}
.businessAuthItem .left{
	width:20%
}
.businessAuthItem .center{
	width:15%
}
.businessAuthItem .right{
	width:65%
}
.businessAuthTipArea:before,.businessAuthTipArea:after{
	content:"";
	display:inline-block;
	width:0;
	height:0;
	border: 6px solid transparent;
	border-bottom-color: #EAEDF3;
	position:absolute;
	top: -13px;
	z-index: 1;
	left: 50%;
	transform: translateX(-50%);
}

.businessAuthTipArea:after{
	border-bottom-color: #fff;
	margin-top: 1px;
}

.businessAuthTipArea{
	display: none;
	list-style: none;
	background: #FFFFFF;
	border: 1px solid #EAEDF3;
	box-shadow: 0 1px 3px 0 rgba(0,0,0,0.04);
	border-radius: 4px;
	width: 130px;
	text-align: center;
	position: fixed;
	z-index: 999;
}
</style>
<tr id="businessAuthTr" style="display: none;">
	<td><bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth'/></td>
	<td style="line-height: 24px;">
		<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fdAuthName'/>
		<label><input type="radio" name="businessAuthType" value="auth" onclick="switchBusinessAuthType(value);" checked><kmss:message key="FlowChartObject.Lang.Node.selectAuthList" bundle="sys-lbpmservice" /></label>
		<label><input type="radio" name="businessAuthType" value="formula" onclick="switchBusinessAuthType(value);"><kmss:message key="FlowChartObject.Lang.Node.selectFormList" bundle="sys-lbpmservice" /></label>
		<input type="hidden" name="businessAuthId">
		<input type="text" name="businessAuthName" class="inputsgl" readonly="readonly" style="width:400px"/>	
		<span id="SPAN_Business_SelectType1">
			<a href="javascript:void(0)" onclick="showFdAuth()"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
		</span>
		<span id="SPAN_Business_SelectType2" style="display:none ">
			<a href="javascript:void(0)" onclick="selectBusinessByFormula();"><kmss:message key="FlowChartObject.Lang.select" bundle="sys-lbpm-engine" /></a>
		</span><br/>
		<span id="SPAN_Business_Number">
			<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fdAuthNumber'/>
			<input type="text" name="businessAuthNumber" class="inputsgl" readonly="readonly" style="width:75%"/><br/>
		</span>
		<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fdForm'/>
		<input type="hidden" name="businessFormId">
		<input type="text" name="businessFormName" class="inputsgl" readonly="readonly" style="width:75%"/>	
		<a href="javascript:void(0)" onclick="showFormField();">
			<bean:message key="dialog.selectOrg"/>
		</a><br/>
		<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fdRule'/>
		<input type="hidden" name="businessRuleId">
		<input type="text" name="businessRuleName" class="inputsgl" readonly="readonly" style="width:70%"/>	
		<a href="javascript:void(0)" onclick="showRuleField();">
			<bean:message key="dialog.selectOrg"/>
		</a>
		<img class="businessAuthTip" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/images/icon_help.png"></img>
		<div class="businessAuthTipArea">
			<span><bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fdRuleTip'/></span>
		</div>
		<br/>
		<div class="businessAuthItem">
			<div class="left">
				<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.skipUnprivileged'/>
			</div>
			<div class="center">
				<label>
					<input type="radio" name="skipUnprivileged" value="true" />
					<kmss:message key="message.yes" />
				</label>
				<label>
					<input type="radio" name="skipUnprivileged" value="false" checked="checked"/>
					<kmss:message key="message.no" />
				</label>
			</div>
			<div class="right">
				<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.skipUnprivileged.msg'/>
			</div>
		</div>
		<div class="businessAuthItem">
			<div class="left">
				<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.finalReview'/>
			</div>
			<div class="center">
				<label>
				<input type="radio" name="finalReview" value="true" onclick="changeIsCanLevelReview(this);" checked="checked"/>
				<kmss:message key="message.yes" />
			</label>
			<label>
				<input type="radio" name="finalReview" value="false" onclick="changeIsCanLevelReview(this);"/>
				<kmss:message key="message.no" />
			</label>
			</div>
			<div class="right">
				<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.finalReview.msg'/>
			</div>
		</div>
		<div id="levelReviewLabel" class="businessAuthItem">
			<div class="left">
				<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.levelReview'/>
			</div>
			<div class="center">
				<label>
					<input type="radio" name="levelReview" value="true" />
					<kmss:message key="message.yes" />
				</label>
				<label>
					<input type="radio" name="levelReview" value="false" checked="checked"/>
					<kmss:message key="message.no" />
				</label>
			</div>
			<div class="right">
				<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.levelReview.msg'/>
			</div>
		</div>
		<input type="hidden" name="wf_businessAuthInfo">
	</td>
</tr>
<script type="text/javascript">
	//校验事件
	AttributeObject.CheckDataFuns.push(function(){
		var businessAuthId = $("input[name='businessAuthId']").val();
		if(businessAuthId){
			var businessFormId = $("input[name='businessFormId']").val();
			if(!businessFormId){
				alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fieldTypeMsg'/>");
				return false;
			}
		}
		return true;
	});
	
	//提交事件(因写入XML也在提交事件中，故这里需要将提交事件放到最前面)
	AttributeObject.SubmitFuns.unshift(function(){
		var businessAuthId = $("input[name='businessAuthId']").val();
		var businessAuthName = $("input[name='businessAuthName']").val();
		var businessAuthType = $("input[name='businessAuthType']:checked").val();
		var businessAuthNumber = $("input[name='businessAuthNumber']").val();
		var businessFormId = $("input[name='businessFormId']").val();
		var businessFormName = $("input[name='businessFormName']").val();
		var businessRuleId = $("input[name='businessRuleId']").val();
		var businessRuleName = $("input[name='businessRuleName']").val();
		var skipUnprivileged = $("input[name='skipUnprivileged'][value='true']").is(':checked');
		var finalReview = $("input[name='finalReview'][value='true']").is(':checked');
		var levelReview = $("input[name='levelReview'][value='true']").is(':checked');
		var info = {};
		info.businessAuthId = businessAuthId;
		info.businessAuthName = businessAuthName;
		info.businessAuthType = businessAuthType;
		info.businessAuthNumber = businessAuthNumber;
		info.businessFormId = businessFormId;
		info.businessFormName = businessFormName;
		info.businessRuleId = businessRuleId;
		info.businessRuleName = businessRuleName;
		info.skipUnprivileged = skipUnprivileged;
		info.finalReview = finalReview;
		info.levelReview = levelReview;
		$("input[name='wf_businessAuthInfo']").val(JSON.stringify(info));
	});

	var businessAuthType = "";
 	AttributeObject.Init.AllModeFuns.push(function() {
 		var settingInfo = getSettingInfo();
 		if(settingInfo.businessauth=="true"){
 			$("#businessAuthTr").show();
 		}
 		
 		var businessAuthInfo = AttributeObject.NodeData["businessAuthInfo"];
 		if(businessAuthInfo){
 			var info = JSON.parse(businessAuthInfo);
 			for(var key in info){
 				if(key=="skipUnprivileged" || key=="levelReview"){
 					if(info[key]){
 						$("input[name='"+key+"'][value='true']").prop("checked","true");
 					}
 				} else if(key=="finalReview"){
 					if(!info[key]){
 						$("#levelReviewLabel").hide();
 						$("input[name='"+key+"'][value='false']").prop("checked","true");
 					}
 				} else if(key=="businessAuthType"){
 					if(info[key]){
 						businessAuthType = info[key];
 						$("input[name='"+key+"'][value='"+info[key]+"']").prop("checked","true");
 						if(info[key]=="formula"){
 							SPAN_Business_SelectType1.style.display='none';
 	 						SPAN_Business_SelectType2.style.display='';
 	 						SPAN_Business_Number.style.display='none';
 						}
 					}
 				} else{
 					$("input[name='"+key+"']").val(info[key]);
 				}
 			}
 		}
	});
 	
 	// 选择条目
	function showFdAuth(){
 		Dialog_TreeList(false,'businessAuthId','businessAuthName',null,'lbpmExtBusinessAuthCateService&parentId=!{value}',"<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fdAuthTitle'/>",'lbpmExtBusinessAuthService&parentId=!{value}',function(rtnVal){
			if(rtnVal){
				if(rtnVal.data && rtnVal.data.length>0){
					if(rtnVal.data[0].fdNumber){
						$("input[name='businessAuthNumber']").val(rtnVal.data[0].fdNumber);
					}
					$("input[name='businessAuthName']").val(rtnVal.data[0].fdName);
				}else{
					$("input[name='businessAuthNumber']").val("");
				}
				document.getElementsByName("businessFormId")[0].value = "";
		 		document.getElementsByName("businessFormName")[0].value = "";
		 		document.getElementsByName("businessRuleId")[0].value = "";
		 		document.getElementsByName("businessRuleName")[0].value = "";
			}
		},'lbpmExtBusinessAuthService&search=!{keyword}');
	}
 	
 	// 从公式定义器选择条目
 	function selectBusinessByFormula(){
 		Formula_Dialog('businessAuthId',
 				'businessAuthName',
 				FlowChartObject.FormFieldList, 
 				"String",
 				null,
 				"com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction",
 				FlowChartObject.ModelName);
 	}
 	
 	function switchBusinessAuthType(value){
 		if(businessAuthType==value)
 			return;
 		businessAuthType = value;
 		SPAN_Business_SelectType1.style.display=businessAuthType=="auth"?"":"none";
 		SPAN_Business_SelectType2.style.display=businessAuthType=="formula"?"":"none";
 		SPAN_Business_Number.style.display=businessAuthType=="auth"?"":"none";
 		document.getElementsByName("businessAuthId")[0].value = "";
 		document.getElementsByName("businessAuthName")[0].value = "";
 		document.getElementsByName("businessAuthNumber")[0].value = "";
 		document.getElementsByName("businessFormId")[0].value = "";
 		document.getElementsByName("businessFormName")[0].value = "";
 		document.getElementsByName("businessRuleId")[0].value = "";
 		document.getElementsByName("businessRuleName")[0].value = "";
 	}
 	
	// 选择表单判断字段
	function showFormField(){
		/* Formula_Dialog('businessFormId','businessFormName', FlowChartObject.FormFieldList, $("input[name='businessAuthFieldType']").val(), null, "com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction", FlowChartObject.ModelName); */
		var businessAuthId = $("input[name='businessAuthId']").val();
		if(!businessAuthId){
			alert("<bean:message bundle='sys-lbpmservice-support' key='lbpmext.businessAuth.fdAuthMsg'/>");
			return;
		}
		var dialog = new KMSSDialog();
		dialog.BindingField('businessFormId','businessFormName');
		dialog.Parameters = {
			varInfo :FlowChartObject.FormFieldList
		};
		dialog.SetAfterShow(function(rtn) {
			
		});

		dialog.URL = Com_Parameter.ContextPath
				+ "sys/lbpmservice/node/common/node_handler_businessauth_fields_tree.jsp?t="+encodeURIComponent(new Date());
		dialog.Show(window.screen.width*380/1366,window.screen.height*480/768);
	}
	
	//选择控制规则字段
	function showRuleField(){
		Formula_Dialog('businessRuleId','businessRuleName', FlowChartObject.FormFieldList, "Object", null, "com.landray.kmss.sys.lbpm.engine.formula.LbpmFunction", FlowChartObject.ModelName);
	}
	
	function changeIsCanLevelReview(dom){
		if(dom.value=="true"){
			$("#levelReviewLabel").show();
		}else{
			$("#levelReviewLabel").hide();
			$("input[name='levelReview'][value='false']").prop("checked","true");
		}
	}
	
	$(function(){
		$(".businessAuthTip").mouseover(function(){
			$(".businessAuthTipArea").show();
			var left = $(this).offset().left-$(".businessAuthTipArea").width()/2+$(this).width()/2;
			var top = $(this).offset().top+23;
			var h = document.documentElement.scrollTop || document.body.scrollTop;
			$(".businessAuthTipArea").css({
				"left" : left,
			    "top" : top-h
			});
		});
		$(".businessAuthTip").mouseout(function(){
			$(".businessAuthTipArea").hide();
		});
	})
</script>