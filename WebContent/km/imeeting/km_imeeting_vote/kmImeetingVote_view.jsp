<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	
	<template:replace name="head">
		<style>
        	.vote_word_td {
			    position: relative;
			}
			.vote_tr {
			    line-height: 40px;
			    width:100%;
			}
        </style>
    </template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	
	<%--督办撤销表单--%>
	<template:replace name="content"> 
		<html:form action="/km/imeeting/km_imeeting_vote/kmImeetingVote.do">
		    <p class="lui_form_subject">${ lfn:message('km-imeeting:table.kmImeetingVote') }</p>
	        <div class="lui_form_content_frame" >
	            <table class="tb_normal" width="95%">
	            	<!-- 标题 -->
	            	<tr>
	            		<td class="td_normal_title" width="15%">
	            			<bean:message bundle="km-imeeting" key="kmImeetingVote.docSubject" />
	            		</td>
	            		<td colspan="3">
	            			<xform:text property="docSubject" required="true"/>
	            		</td>
	            	</tr>
	            	<!-- 投票对象 -->
	                <tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="km-imeeting" key="kmImeetingVote.fdVoteObj" />
						</td>
						<td colspan="3">
							<table id="TABLE_DocList_Obj" width="83%" tbdraggable="true">
								<tr KMSS_IsReferRow="1" class="vote_tr" style="display: none;">
									<td class="vote_word_td">
										<xform:text property="fdVoteObjs[!{index}]" validators="maxLength(300)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:97%" className="inputsgl" required="true"  />									
									</td>	
								</tr>
								<c:forEach items="${kmImeetingVoteForm.fdVoteObjs}" var="fdVoteObj" varStatus="vstatus">
									<tr KMSS_IsContentRow="1" class="vote_tr">
										<td class="vote_word_td">
											<xform:text property="fdVoteObjs[${vstatus.index}]" value="${fdVoteObj}" validators="maxLength(300)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:97%;"  className="inputsgl" required="true" />
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
					<!-- 投票选项 -->
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="km-imeeting" key="kmImeetingVote.fdVoteOption" />
						</td>
						<td colspan="3">
							<table id="TABLE_DocList_Option" width="83%" tbdraggable="true">
								<tr KMSS_IsReferRow="1" class="vote_tr" style="display: none;">
									<td class="vote_word_td">
										<xform:text property="fdVoteOptions[!{index}]" validators="maxLength(300)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:97%" className="inputsgl" required="true"  />									
									</td>	
								</tr>
								<c:forEach items="${kmImeetingVoteForm.fdVoteOptions}" var="fdVoteOption" varStatus="vstatus">
									<tr KMSS_IsContentRow="1" class="vote_tr">
										<td class="vote_word_td">
											<xform:text property="fdVoteOptions[${vstatus.index}]" value="${fdVoteOption}" validators="maxLength(300)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:97%;"  className="inputsgl" required="true" />
										</td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
	            </table>
	        </div>
		    <html:hidden property="fdId" />
		    <html:hidden property="method_GET" />
		</html:form>
	</template:replace>
	
</template:include>