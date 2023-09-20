<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<ui:ajaxtext>
	<div data-dojo-block="title">
		<c:out value="${kmForumPostForm.docSubject}"></c:out>
	</div>
	<div data-dojo-block="content">
		<html:form action="/km/forum/km_forum/kmForumPost.do">
			<html:hidden property="fdId" />
			 <html:hidden property="fdTopicId"/>
			 <html:hidden property="fdParentId"/>
			 <html:hidden property="fdQuoteMsg"/>
			 <html:hidden property="quoteMsg"/>
			 <html:hidden property="fdForumId"/>
			 <html:hidden property="fdForumName"/>
	         <html:hidden property="fdPosterId"/>
			 <html:hidden property="docSubject"/>
			 <html:hidden property="fdPdaType"/>
			<div data-dojo-type="mui/view/DocScrollableView" id="scrollView" data-dojo-mixins="mui/form/_ValidateMixin,mui/form/_AlignMixin">
				<div data-dojo-type="mui/panel/Content">
					<table class="muiSimple" cellpadding="0" cellspacing="0">
						<tr>
							<td colspan="2">
								<xform:rtf property="docContent" mobile="true">
								</xform:rtf>
							</td>
						</tr>
						<tr>
							<td class="muiTitle">
								${lfn:message('sys-attachment:mui.sysAttMain.button.upload') }
							</td>
							<td>
								<c:import
									url="/sys/attachment/mobile/import/edit.jsp"
									charEncoding="UTF-8">
									<c:param name="formName" value="kmForumPostForm"></c:param>
									<c:param name="fdKey" value="attachment" />
									<c:param name="align" value="right" />
								</c:import>
							</td>
						</tr>
						<c:if test="${globalIsAnonymous && fdForumAnonymous}">
						<tr>
							<td class="muiTitle">
								${lfn:message('km-forum:KmForumPost.notify.title.anonReply')}
							</td>
							<td>
								<div data-dojo-type="mui/form/switch/NewSwitch"
									 data-dojo-mixins="km/forum/mobile/resource/js/view/SwitchMixin"
									 data-dojo-props="orient:'vertical',leftLabel:'',rightLabel:'',value:'${kmForumPostForm.fdIsAnonymous}',property:'fdIsAnonymous'"
									 class="kmForumSwitch">
								</div>
							</td>
						</tr>
						</c:if>
					</table>
				</div>
				
				<ul data-dojo-type="mui/tabbar/TabBar" fixed="bottom" data-dojo-props='fill:"grid"'>
				  	<li data-dojo-type="mui/tabbar/TabBarButton" class="muiBtnSubmit " 
				  		data-dojo-props='colSize:2,href:"javascript:commitMethod()",transition:"slide"'>
				  		${lfn:message('button.submit')}	
				  	</li>
				</ul>
			</div>
		</html:form>
		<script type="text/javascript">
			require(["mui/form/ajax-form!kmForumPostForm"]);
			require(['dojo/ready', "mui/device/device", "dojo/_base/query", "mui/dialog/Tip" ,"mui/i18n/i18n!km-forum:kmForumPost.notEmpty","mui/i18n/i18n!km-forum:kmForumConfig.mobile.word.warn","km/forum/mobile/resource/js/view/wordCheck"], function (ready,
					device, query, Tip, msg,warn,wordCheck) {
					ready(function () {
						query('[name="fdPdaType"]')[0].value = device
							.getClientType();
					});
					//提交
					window.commitMethod = function () {
						docContent = CKEDITOR.instances['docContent'].getData()
						if (!docContent) {
							Tip.warn({
								text: msg['kmForumPost.notEmpty']
							})
							return;
						}
						
						var flag = true;
						flag = wordCheck.forum_wordCheck(
								docContent,
								warn["kmForumConfig.mobile.word.warn"]
							    );
						
					    if (!flag) {
					      return;
					    }
					    
						Com_Submit(document.kmForumPostForm, 'saveQuick');
					}
				});
		</script>
	</div>
</ui:ajaxtext>