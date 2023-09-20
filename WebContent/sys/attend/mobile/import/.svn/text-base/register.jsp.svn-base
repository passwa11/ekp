<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.profile.model.PasswordSecurityConfig" %>
<%
	PasswordSecurityConfig pswConfig = PasswordSecurityConfig.newInstance();
	if(pswConfig != null){
		request.setAttribute("reSentIntervalTime", pswConfig.getReSentIntervalTime());
		request.setAttribute("smsReceiveEnable", pswConfig.getSmsReceiveEnable());
	}
%>
<template:include ref="mobile.edit" compatibleMode="true">
	<template:replace name="head">
		<link rel="stylesheet" type="text/css" href="${LUI_ContextPath }/sys/attend/mobile/import/css/view.css?s_cache=${MUI_Cache}"></link>
		<style>
		[data-dojo-type="mui/form/Input"] .muiSelInputRight{
			display: none;
		}
		</style>
	</template:replace>
	<template:replace name="content">
			<html:form action="/resource/sys/attend/sysAttendOutPerson.do" styleClass="mui_meetingSign_external_login">
				<html:hidden property="categoryId" value="${JsParam.categoryId }"/>
				
				<div data-dojo-type="mui/view/DocScrollableView"
					data-dojo-mixins="mui/form/_ValidateMixin"
					class="mui_meetingSign_external_view" id="docScrollView">
				<h2 class="login_title">填写个人信息</h2>
				<ul class="mui_meetingSign_external_login_list">
					<li>
						<%-- 姓名 --%>
						<xform:text property="fdName" htmlElementProperties="class='mui_meetingSign_form_control' id='fdName' placeholder='请输入 姓名'" mobile="true" required="true" showStatus="edit" subject="姓名" title="姓名">
						</xform:text>
					</li>
					<li>
						<div class="mui_meetingSign_input_group">
							<%-- 手机号 --%>
							<div class="mui_meetingSign_left">
								<xform:text property="fdPhoneNum" htmlElementProperties="class='mui_meetingSign_form_control' style='margin: 0;' id='fdPhoneNum' placeholder='请输入 手机号码'" mobile="true" required="true" validators="number phone" showStatus="edit" subject="手机号码" title="手机号码">
								</xform:text>
							</div>
							<%-- 发送验证码 --%>
							<c:if test="${smsReceiveEnable eq 'true' }">
							<div class="mui_meetingSign_right" id="vcodeBlock">
								<span class="mui_meetingSign_input_group_addon" id="vcodeBtn" onclick="sendVcode();">
									<span class="mui_meetingSign_text_primary" id='vcodeBtnText'>获取验证码
									</span>
								</span>
							</div>
							</c:if>
						</div>
					</li>
					<c:if test="${smsReceiveEnable eq 'true' }">
					<li>
						<%-- 验证码 --%>
						<xform:text property="fdVcode" htmlElementProperties="class='mui_meetingSign_form_control' placeholder='请输入 验证码'" mobile="true" required="true" validators="number" showStatus="edit" subject="验证码" title="验证码">
						</xform:text>
					</li>
					</c:if>
				</ul>
				<p class="login_title_warning" style='<c:if test="${empty errMsg }">display: none</c:if>' id='errMsg'>
					${errMsg }
				</p>
				<button type="button" class="mui_meetingSign_btn" onclick="doSubmit();">提交</button>
				</div>
			</html:form>
		<script type="text/javascript">
		//require(["mui/form/ajax-form!sysAttendOutPersonForm"]);
		require(['dojo/topic','mui/dialog/Tip',"dojo/query","dojo/ready","dijit/registry","dojo/request",'dojo/io-query',
		         'mui/util','mui/dialog/Confirm','dojo/dom-attr','dojo/dom-construct','dojo/dom-class','dojo/dom-style'],
				function(topic,Tip,query,ready,registry,request,ioq,util,Confirm,domAttr,domConstruct,domClass,domStyle){
			
			ready(function(){
				var docScrollView = registry.byId('docScrollView');
				// 手机号校验器
				docScrollView._validation.addValidator('phone','请输入正确的手机号码', function(v){
					if (!v) {
						return true;
					}
					if(startsWith(v, "+")) {
						if(startsWith(v, "+86")) {
							return /^(\+86)(\d{11})$/.test(v);
						} else {
							return /^(\+\d{1,5})(\d{6,11})$/.test(v);
						}
					} else {
						return /^[1][0-9][0-9]{9}$/.test(v);
					}
				});
			});
			
			//提交
			window.doSubmit = function() {
				var docScrollView = registry.byId('docScrollView');
				if(!docScrollView.validate()){
					return false;
				}
				var fdName = registry.byId('fdName').get('value');
				var fdPhoneNum = registry.byId('fdPhoneNum').get('value');
				setCookie('outerName', fdName);
				setCookie('outerPhone', fdPhoneNum);
				_getUserId({
					name : fdName,
					phoneNum : fdPhoneNum
				}).then(function(data){
					// 外部人员已存在，跳到签到页面，不发送验证码
					if(data && data.userId){
						var categoryId = '${JsParam.categoryId}';
						Confirm('该用户已注册，可直接签到', null, function(status, dialog){
							if(status){
								if(categoryId) {
									location.href = util.formatUrl('/resource/sys/attend/sysAttendAnym.do?method=signOuter')
										+ '&categoryId=' + categoryId + '&userId=' + data.userId;
								} else {
									window.close();
								}
							}
						});
					} else {
						Com_Submit(document.forms['sysAttendOutPersonForm'], 'validateVCode');
					}
				});
			};
			
			//获取验证码
			window.sendVcode = function() {
				var docScrollView = registry.byId('docScrollView'),
					validation = docScrollView._validation;
				var fdName = registry.byId('fdName');
				var fdPhoneNum = registry.byId('fdPhoneNum');
				if(validation.validateElement(fdName) && validation.validateElement(fdPhoneNum)){
					_getUserId({
						name : fdName.get('value'),
						phoneNum : fdPhoneNum.get('value')
					}).then(function(data){
						// 外部人员已存在，跳到签到页面，不发送验证码
						if(data && data.userId){
							var categoryId = '${JsParam.categoryId}';
							Confirm('该用户已注册，可直接签到', null, function(status, dialog){
								if(status){
									if(categoryId) {
										location.href = util.formatUrl('/resource/sys/attend/sysAttendAnym.do?method=signOuter')
											+ '&categoryId=' + categoryId + '&userId=' + data.userId;
									} else {
										window.close();
									}
								}
							});
						} else {
							disbleSendBtn();
							countdown();
							var errMsgBlock = query('#errMsg')[0];
							// 发送验证码
							_sendVcode({
								name : fdName.get('value'),
								phoneNum : fdPhoneNum.get('value')
							}).then(function(result){
								if(result && result.status == '1'){
									Tip.success({
										text : '发送成功'
									})
									domStyle.set(errMsgBlock, 'display', 'none');
									errMsgBlock.innerHTML = '';
								} else {
									domStyle.set(errMsgBlock, 'display', 'block');
									errMsgBlock.innerHTML = result.errMsg;
								}
							},function(e){
								console.error(e);
								Tip.fail({
									text : '发送验证码失败'
								});
								enableSendBtn();
								domStyle.set(errMsgBlock, 'display', 'block');
								errMsgBlock.innerHTML = '发送验证码失败';
							});
						}
					},function(e){
						console.error(e);
					});
				} else {
				}
			}
			
			// 发送验证码
			var _sendVcode = function(options) {
				var url = util.formatUrl('/resource/sys/attend/sysAttendOutPerson.do?method=sendVcode');
				var params = {
					handleAs : 'json',
					method : 'POST',
					data : ioq.objectToQuery(options)
				}
				var promise = request(url, params);
				return promise;
			}
			
			// 倒计时
			var s = parseInt('${reSentIntervalTime}' || 30), t,vcodeBtnText = query('#vcodeBtnText')[0];
			var countdown = function(){
				s--;
				vcodeBtnText.innerHTML = '重新获取('+ s +'s)'
			 	t = setTimeout(function(){
			 		countdown();
			 	}, 1000);
				if (s <= 0){
					 s = parseInt('${reSentIntervalTime}' || 30);
					 clearTimeout(t);
					 vcodeBtnText.innerHTML = '获取验证码';
					 enableSendBtn();
				}
			}
			
			// disble发送按钮
			var disbleSendBtn = function(){
				var vcodeBtn = query('#vcodeBtn')[0];
				domAttr.remove(vcodeBtn,'onclick');
				domClass.add(vcodeBtn, 'readonly');
			}
			// enable发送按钮
			var enableSendBtn = function(){
				var vcodeBtn = query('#vcodeBtn')[0];
				domAttr.set(vcodeBtn,'onclick', 'sendVcode();');
				domClass.remove(vcodeBtn, 'readonly');
			}
			
			// 判定是否已注册
			var _getUserId = function(options){
				var url = util.formatUrl('/resource/sys/attend/sysAttendOutPerson.do?method=getUserId');
				var params = {
						handleAs : 'json',
						method : 'POST',
						data : ioq.objectToQuery(options)
					};
				var promise = request(url, params);
				return promise;
			}
			
			var setCookie = function(name, value) {
				var expdate = new Date(); 
			    expdate.setTime(expdate.getTime() + (86400000 * 7));//7天过期
			    var path = '${LUI_ContextPath }/resource/sys/attend/';
				document.cookie= name + "=" + encodeURIComponent(value)+";expires="+expdate.toGMTString() + ';path=' + path;
			}
			
			var startsWith = function(value, prefix) {
				return value.slice(0, prefix.length) === prefix;
			}
		});
		</script>
	</template:replace>
</template:include>
