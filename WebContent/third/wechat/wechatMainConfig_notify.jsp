<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="config.edit" sidebar="no" >
	<template:replace name="title">
		${lfn:message('third-wechat:wechatMainConfig.notify.test')}
	</template:replace>
	<template:replace name="content">
		<html:form action="/third/wechat/wechatNotify.do?method=doSendNotify">
			<div class="lui_form_content_frame" style="padding-top:50px"> 
				<p class="txttitle">${lfn:message('third-wechat:wechatMainConfig.notify.test1')}</p>
				 <table class="tb_normal" width=75% style="margin-top: 15px;">
					<tr>
					  <td class="td_normal_title">${lfn:message('third-wechat:wechatMainConfig.notify.title')}</td>
					  <td colspan=3>
					  	<xform:text property="postSubject" style="width:97%" showStatus="edit" />
					  </td>
					</tr>
				  	<tr>
					    <td class="td_normal_title" >${lfn:message('third-wechat:wechatMainConfig.notify.receive')}</td>
						<td colspan=3>	
						  <span id="isWriterSpan" >
					         <xform:text property="receiverId"  isLoadDataDict="false"   required="true"/>
						  </span>
						  <span>
						      <xform:address  required="true"  style="width:98%" isLoadDataDict="false"  showStatus="edit"  
						         propertyId="personId" propertyName="personName" orgType='ORG_TYPE_PERSON' className="input" ></xform:address>
						  </span>
						</td>
				    </tr>
				    <tr>
				       <td class="td_normal_title" >${lfn:message('third-wechat:wechatMainConfig.notify.send')}</td>
				       <td width="85%" colspan="3">
						   <xform:textarea property="content" showStatus="edit"  style="width:97%;height:90px"/>
					   </td>
				    </tr>
				    <tr id="_retResult" style="display:none;">
				       <td class="td_normal_title" >${lfn:message('third-wechat:wechatMainConfig.notify.result')}</td>
				       <td width="85%" colspan="3">
						   <input type="text" name="result" id="_result"  readonly="readonly" disabled="disabled" style="width:98%;height:90px">
					   </td>
				    </tr>
				</table>
				<div style="width: 75%;margin: 20px auto;text-align: center;">
		         	<ui:button id="doButton" text="${lfn:message('third-wechat:wechatMainConfig.notify.dosend')}" onclick="doAjax();"  width="120" height="35"></ui:button>
		         </div>
			</div>
		</html:form>
		<script type="text/javascript">
			seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
				window.doAjax = function(){
					var postSubject = $('[name="postSubject"]').val(),
						personId = $('[name="personId"]').val(),
						content = $('[name="content"]').val();
					if(!(postSubject!='' && postSubject.length>0)){
						 dialog.alert("${lfn:message('third-wechat:wechatMainConfig.notify.title.tip')}");
						 return false; 
					}
					if(!(personId!='' && personId.length>0)){
						dialog.alert("${lfn:message('third-wechat:wechatMainConfig.notify.receive.tip')}");
						return false; 
					}
					if(!(content!='' && content.length>0)){
						dialog.alert("${lfn:message('third-wechat:wechatMainConfig.notify.send.tip')}");
						return false; 
					}
					$.ajax({
						url: Com_Parameter.ContextPath + 'third/wechat/wechatNotify.do?method=doSendNotify',
						type: 'POST',
						dataType: 'json',
						async : false,
						data:{	postSubject:postSubject,personId:personId,content:content },    
						success: function(data) {
							var code =data.code;
							var retInfor =data.retInfor;
							var message=code+":"+retInfor;
							$("#_result").val(message);
							$("#_retResult").show();
						},
						error: function(data) {
							var code =data.code;
							var retInfor =data.retInfor;
							var message=code+":"+retInfor;
							$("#_result").val(message);
							$("#_retResult").show();
						}
					});
				};
			});
		</script>
	</template:replace>
</template:include>