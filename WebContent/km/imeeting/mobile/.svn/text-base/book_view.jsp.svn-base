<%@page import="com.landray.kmss.util.ModelUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.km.imeeting.forms.KmImeetingBookForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<template:include ref="mobile.list">
	<template:replace name="title">
		<c:if test="${not empty kmImeetingBookForm.fdName}">
			<c:out value="${ kmImeetingBookForm.fdName}"></c:out>
		</c:if>
		<c:if test="${empty kmImeetingBookForm.fdName}">
			<bean:message bundle="km-imeeting" key="module.km.imeeting"/>
		</c:if>
	</template:replace>
	<template:replace name="head">
		<link rel="Stylesheet" href="${LUI_ContextPath}/km/imeeting/mobile/resource/css/book.css?s_cache=${MUI_Cache}" />
	</template:replace>
	<template:replace name="content">
		
		<div class="meetingBookView" data-dojo-type="mui/tabbar/TabBarView">
		
			<%
				ISysAttMainCoreInnerService sysAttMainCoreInnerService = 
					(ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
			
				KmImeetingBookForm kmImeetingBook=(KmImeetingBookForm)request.getAttribute("kmImeetingBookForm");
				List list=sysAttMainCoreInnerService.findByModelKey(ModelUtil.getModelClassName("com.landray.kmss.km.imeeting.model.KmImeetingRes"),
						kmImeetingBook.getFdPlaceId(),"Attachment");
				if(list!=null && list.size()>0){
					SysAttMain att=(SysAttMain)list.get(0);
					request.setAttribute("placeImgUrl", 
							"sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="+att.getFdId());
				} else {
					request.setAttribute("placeImgUrl", "");
				}
			%>
		
			<c:choose>
				<c:when test="${placeImgUrl != '' }">
					<div class="img" style="background-image: url('${LUI_ContextPath}/${placeImgUrl}')"></div>
				</c:when>
				<c:otherwise>
					<div class="img"></div>
				</c:otherwise>			
			</c:choose>
		
		
			<div>
				<div class="title">
					<c:out value="${kmImeetingBookForm.fdName }"></c:out>
					<span class="status" data-agree="${kmImeetingBookForm.fdHasExam}">
						<c:choose>
							<c:when test="${kmImeetingBookForm.fdHasExam == 'true'}">
								<bean:message bundle="km-imeeting" key="kmImeeting.res.true"/>
							</c:when> 
							<c:when test="${kmImeetingBookForm.fdHasExam == 'false'}">
								<bean:message bundle="km-imeeting" key="kmImeeting.res.false"/>
							</c:when>
							<c:otherwise>
								<bean:message bundle="km-imeeting" key="kmImeeting.res.wait"/>
							</c:otherwise>
						</c:choose>
					
					</span>
				</div>
				<ul class="detail">
					<li><i class="mui mui-position"></i><c:out value="${kmImeetingBookForm.fdPlaceName }"></c:out></li>
					<li><i class="mui mui-people"></i><c:out value="${kmImeetingBookForm.docCreatorName }"></c:out></li>
					<c:if test="${ not empty kmImeetingBookForm.fdRecurrenceStr}">
						<li><i></i>
							<bean:message bundle="km-imeeting" key="mobile.info.repeat"/>：
							<c:out value="${kmImeetingBookForm.fdRepeatFrequency }"></c:out>
							<c:out value="${kmImeetingBookForm.fdRepeatTime }"></c:out>
							<c:out value="${kmImeetingBookForm.fdRepeatUtil }"></c:out>
						</li>
					</c:if>
				</ul>
				
				<c:if test="${kmImeetingBookForm.fdRemark != null && kmImeetingBookForm.fdRemark != ''}">
					<div class="remark"><c:out value="${kmImeetingBookForm.fdRemark }"></c:out></div>
				</c:if>
			</div>
			
			<div>
				<div class="time">
					<div class="start">
						<span class="timeline"></span>
						<span><bean:message bundle="km-imeeting" key="mobile.info.start"/></span>
						<div><c:out value="${kmImeetingBookForm.fdHoldDate}"></c:out></div>
					</div>
					<div class="end">
						<span class="timeline"></span>
						<span><bean:message bundle="km-imeeting" key="mobile.info.end"/></span>
						<div><c:out value="${kmImeetingBookForm.fdFinishDate}"></c:out></div>
					</div>
				</div>			
			</div>
		</div>
		<c:choose>
			<c:when test="${(kmImeetingBookForm.fdExamerId eq KMSS_Parameter_CurrentUserId  || isfdExamer == 'true') && kmImeetingBookForm.isNotify == 'true' &&
				kmImeetingBookForm.fdHasExam == null}">
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" 
					data-dojo-props='fill:"always"'>
				  	<li data-dojo-type="mui/tabbar/TabBarButton" 
				  		data-dojo-props="href:'javascript:window.agree()'"><bean:message bundle="km-imeeting" key="kmImeeting.res.btn.agree"/></li>
		
					<li style="color: #F95A5A;"
						data-dojo-type="mui/tabbar/TabBarButton" 
						data-dojo-props="href:'javascript:window.reject()'"><bean:message bundle="km-imeeting" key="kmImeeting.res.btn.reject"/></li>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=edit&fdId=${kmImeetingBookForm.fdId}" requestMethod="GET">
						<c:set var="showMoreBtn" value="true"></c:set>
					</kmss:auth>
					<kmss:auth requestURL="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=delete&fdId=${kmImeetingBookForm.fdId}" requestMethod="GET">
						<c:set var="showMoreBtn" value="true"></c:set>
					</kmss:auth>
					<c:if test="${showMoreBtn eq true }">
						 <li data-dojo-type="mui/tabbar/TabBarButton"  id="otherListBtn" data-dojo-props="">
						 	${lfn:message('km-imeeting:button.other')}
						 </li> 
					</c:if>	
				</ul>
			</c:when>
			<c:otherwise>
					<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" 
						data-dojo-props='fill:"always"'>
					  	<kmss:auth requestURL="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=edit&fdId=${kmImeetingBookForm.fdId}" requestMethod="GET">
			    			<li data-dojo-type="mui/tabbar/TabBarButton" 
					  			data-dojo-props="href:'javascript:window.edit()'"><bean:message key="button.edit"/></li>
					  	</kmss:auth>
					  	
					  	<kmss:authShow roles="ROLE_KMIMEETING_CREATE">
					  		<c:if test="${kmImeetingBookForm.isBegin == 'false' && kmImeetingBookForm.fdHasExam == 'true'}">
					  		<c:if test="${ empty kmImeetingBookForm.fdRecurrenceStr && kmImeetingBookForm.isCreator =='true'}">
					  		<li data-dojo-type="mui/tabbar/TabBarButton"  onclick="changeToImeeting();"
								data-dojo-props="colSize:2"><bean:message key="kmImeetingBook.changeToImeeting" bundle="km-imeeting"/></li>
							</c:if>
							</c:if>
						</kmss:authShow>
						
						<c:if test="${kmImeetingBookForm.isBegin == 'true' && kmImeetingBookForm.isEnd == 'false' && empty kmImeetingBookForm.fdRecurrenceStr && kmImeetingBookForm.isCreator =='true' && kmImeetingBookForm.fdHasExam == 'true'}">
					  		<li data-dojo-type="mui/tabbar/TabBarButton" 
					  			data-dojo-props="href:'javascript:window.earlyEndBook()'"><bean:message key="kmImeeting.btn.earlyEnd" bundle="km-imeeting"/></li>
					  	</c:if>
						
						<kmss:auth requestURL="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=delete&fdId=${kmImeetingBookForm.fdId}" requestMethod="GET">
							<li style="color: #F95A5A;"
								data-dojo-type="mui/tabbar/TabBarButton" 
								data-dojo-props="href:'javascript:window.del()'"><bean:message key="button.delete"/></li>
						</kmss:auth>
					</ul>
			</c:otherwise>
		</c:choose>
		<c:if test="${showMoreBtn eq true }">
			<div id="kmImeetingBook_otherBox" style="display:none;" >
	  			<div id="kmImeetingBook_other" class="kmImeetingBook_other">
					<ul>
						<kmss:auth requestURL="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=edit&fdId=${kmImeetingBookForm.fdId}" requestMethod="GET">
			    			<li data-dojo-type="mui/tabbar/TabBarButton" onclick="edit();"
					  			data-dojo-props="colSize:2"><bean:message key="button.edit"/></li>
					  	</kmss:auth>
					  	<kmss:authShow roles="ROLE_KMIMEETING_CREATE">
					  		<c:if test="${kmImeetingBookForm.isBegin == 'false' && kmImeetingBookForm.fdHasExam == 'true'}">
							<li data-dojo-type="mui/tabbar/TabBarButton"  onclick="changeToImeeting();"
								data-dojo-props="colSize:2"><bean:message key="kmImeetingBook.changeToImeeting" bundle="km-imeeting"/></li>
							</c:if>
						</kmss:authShow>
					  		
						<kmss:auth requestURL="/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=delete&fdId=${kmImeetingBookForm.fdId}" requestMethod="GET">
							<li style="color: #F95A5A;"
								data-dojo-type="mui/tabbar/TabBarButton"  onclick="del();"
								data-dojo-props="colSize:2"><bean:message key="button.delete"/></li>
						</kmss:auth>
			 		</ul>
			 		<div class="otherDialogCancel">
						${lfn:message('km-imeeting:button.cancel')}
					</div>
			 	</div>
			 </div>
		</c:if> 
		<script>
			
			require(['dojo/dom', 'dojo/dom-construct', 'dojo/on', 'dojo/request', 'mui/dialog/Confirm', 'mui/dialog/Tip','mui/dialog/Dialog',"dojo/_base/lang",'dojo/query','mui/syscategory/SysCategoryUtil'], 
					function(dom, domCtr, on, request, Confirm, Tip, Dialog, lang, query,SysCategoryUtil) {
				 var otherListBtn = dom.byId("otherListBtn");
				 if(otherListBtn){
					 var OtherList = dom.byId("kmImeetingBook_other");
					 dom.byId("otherListBtn").onclick= function (){
							var DialogObj = new Dialog.claz({
								element:OtherList,
								scrollable:false,
								parseable:false,
								position:"bottom",
								canClose:false
							});
							DialogObj.show();
						    var dialogCancel =  query(".muiDialogElementContainer_bottom .otherDialogCancel");
							dialogCancel[0].onclick=function(){
								DialogObj.hide();
							}; 					
					  } 
				 }
				 
				 window.changeToImeeting = function(){
					 SysCategoryUtil.create({
						  key: "normalMeeting",
						  createUrl: "/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=add&fdTemplateId=!{curIds}&bookId=${kmImeetingBookForm.fdId}&_referer=${LUI_ContextPath }/km/imeeting/mobile",
						  modelName: "com.landray.kmss.km.imeeting.model.KmImeetingTemplate",
		                  mainModelName: "com.landray.kmss.km.imeeting.model.KmImeetingMain",
		                  showFavoriteCate:'true',
		         		  authType: '02'
		            });
				 }
				 
				window.agree = function() {
					
					Confirm('<span><bean:message key="kmImeeting.btn.agree.tip" bundle="km-imeeting"/>？</span>', '${kmImeetingBookForm.fdPlaceName }', function(check, d) {
						if(!check) {
							return;
						}
						
						var processTip = Tip.processing();
						request('${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=exam', {
							method: 'post',
							data: {
								bookId: '${JsParam.fdId}', 
								fdHasExam: 'true', 
								fdExamRemark: ''
							}
						}).then(function(res){
							processTip.hide();

							Tip.success({
								text: '<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
							});
							
							setTimeout(function() {
								location.reload();
							}, 200);
						}, function(err) {
							processTip.hide();
							
							Tip.fail({
								text: '<bean:message key="mobile.fail.tip" bundle="km-imeeting"/>'
							});
							
						});
					}, false, function() {
					
					});
				}
				
				window.reject = function() {

					var confirmText = '<bean:message key="kmImeeting.reject.reason" bundle="km-imeeting"/>';
					Confirm('<textarea id="rejectReason" placeholder="'+confirmText+'"></textarea>', 
							'${kmImeetingBookForm.fdPlaceName }', function(check, d) {
						
						if(!check) {
							return;
						}
								
						var rejectReasonNode = dom.byId('rejectReason');
						var rejectReason = rejectReasonNode ? rejectReasonNode.value : '';
						if(!rejectReason) {
							Tip.fail({
								text: '<bean:message key="kmImeeting.reject.reason" bundle="km-imeeting"/>'
							});
							return;
						}
						
						var processTip = Tip.processing();
						request( '${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=exam', {
							method: 'post',
							data: {
								bookId: '${JsParam.fdId}', 
								fdHasExam: 'false', 
								fdExamRemark: rejectReason
							}
						}).then(function(res){
							processTip.hide();

							Tip.success({
								text: '<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
							});
							
							setTimeout(function() {
								location.reload();
							}, 200);
						}, function(err) {
							processTip.hide();
							
							Tip.fail({
								text: '<bean:message key="mobile.fail.tip" bundle="km-imeeting"/>'
							});
							
						});
								
					}, true, function() {
						
					}, {
						checkTitle: '<span style="color: #F95A5A;"><bean:message key="kmImeeting.res.btn.reject" bundle="km-imeeting"/></span>',
						checkValidator: function() {
							var rejectReasonNode = dom.byId('rejectReason');
							var rejectReason = rejectReasonNode ? rejectReasonNode.value : '';
							if(!rejectReason) {
								Tip.fail({
									text: '<bean:message key="kmImeeting.reject.reason" bundle="km-imeeting"/>'
								});
								return false;
							}
							
							return true;
						}
					});	
				}
				
				window.edit = function(){
					var url="${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=edit&fdId=${JsParam.fdId}";
					window.open(url, '_self');
				}
				
				window.earlyEndBook = function(){
					var url="${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=earlyEndBook&fdId=${JsParam.fdId}";
					var processTip = Tip.processing();
					request(url, {
						method: 'post',
						data: {
						}
					}).then(function(res){
						processTip.hide();

						Tip.success({
							text: '<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
						});
						
						setTimeout(function() {
							location.reload();
						}, 200);
					}, function(err) {
						processTip.hide();
						
						Tip.fail({
							text: '<bean:message key="mobile.fail.tip" bundle="km-imeeting"/>'
						});
						
					});
				}
				
				window.del = function(){
					var recurrenceStr = "${kmImeetingBookForm.fdRecurrenceStr}";
					//非周期性会议预约删除前提示是否删除
					if(!recurrenceStr) {				
						window._del();
					}else{
						window._delRecurrence();
					}
				}
				
				window._delRecurrence = function(){
					var deleteDate = "${kmImeetingBookForm.fdHoldDate}";
					var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=mobileDelete&fdId=${JsParam.fdId}";
					var delRecurrenceDialog = new Dialog.claz({
						'element' : '<br/><br/><div><bean:message key="kmImeeting.select.delte.mode" bundle="km-imeeting"/></div><br/><br/>',
						'destroyAfterClose':true,
						'closeOnClickDomNode':true,
						'scrollable' : false,
						'parseable': true,
						'position':'center',
						'buttons' : [{
							title : '<bean:message key="kmImeeting.delte.mode.current" bundle="km-imeeting"/>',
							fn : lang.hitch(this,function(dialog) {
								var processing = Tip.processing();
								request(url, {
									handleAs : 'json',
									method : 'post',
									data : {
										deleteDate:deleteDate,
										deleteType:'cur'
									}
								}).then(lang.hitch(this, function(data) {
									if (data){
									    if(data['flag'] == '1'){
									    	dialog.hide();
									    	processing.hide();
									    	Tip.success({
												text:'<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
											});
									    	location.href = "${LUI_ContextPath}/km/imeeting/mobile/index.jsp";
										}
									}
								}));
							})
						} ,{
							title : '<bean:message key="kmImeeting.delte.mode.after" bundle="km-imeeting"/>',
							fn : lang.hitch(this,function(dialog) {
								var processing = Tip.processing();
								request(url, {
									handleAs : 'json',
									method : 'post',
									data : {
										deleteDate:deleteDate,
										deleteType:'after'
									}
								}).then(lang.hitch(this, function(data) {
									if (data){
									    if(data['flag'] == '1'){
									    	dialog.hide();
									    	processing.hide();
									    	Tip.success({
												text:'<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
											});
									    	location.href = "${LUI_ContextPath}/km/imeeting/mobile/index.jsp";
										}
									}
								}));
							})
						}]
					});
					delRecurrenceDialog.show();
				}
				window._del = function(){
					var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_book/kmImeetingBook.do?method=mobileDelete&fdId=${JsParam.fdId}";
					var delDialog = new Dialog.claz({
						'element' : '<br/><br/><div><bean:message key="KmImeeting.delte.tip" bundle="km-imeeting"/>？</div><br/><br/>',
						'destroyAfterClose':true,
						'closeOnClickDomNode':true,
						'scrollable' : false,
						'parseable': true,
						'position':'center',
						'buttons' : [{
							title : '<bean:message key="button.cancel" bundle="km-imeeting"/>',
							fn : function(dialog) {
								dialog.hide();
							}
						} ,{
							title : '<bean:message key="button.submit" bundle="km-imeeting"/>',
							fn : lang.hitch(this,function(dialog) {
								var processing = Tip.processing();
								request(url, {
									handleAs : 'json',
									method : 'post',
									data : {
									}
								}).then(lang.hitch(this, function(data) {
									if (data){
									    if(data['flag'] == '1'){
									    	dialog.hide();
									    	processing.hide();
									    	Tip.success({
												text:'<bean:message key="mobile.success.tip" bundle="km-imeeting"/>'
											});
									    	location.href = "${LUI_ContextPath}/km/imeeting/mobile/index.jsp";
										}
									}
								}));
							})
						}]
					});
					delDialog.show();
				} 
			});
		
		
		</script>
		
	</template:replace>
</template:include>

