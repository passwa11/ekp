<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" sidebar="no">
	
	<template:replace name="head">
        <script type="text/javascript">
        	Com_IncludeFile("doclist.js");
        	
			window.onload = function(){
				DocList_Info.push("TABLE_DocList_Obj");
				DocList_Info.push("TABLE_DocList_Option");
				optionTypeChange();
			}
        </script>
        <style>
        	.vote_word_td {
			    position: relative;
			}
			.vote_tr {
			    line-height: 40px;
			    width:100%;
			}
			.add_item {
			    background: #fff;
			    display: inline-block;
			    margin-top: 10px;
			    height: 26px;
			    line-height: 26px;
			    border: 1px solid #15a4fa;
			    color: #15a4fa;
			    padding: 0 8px;
			    width: auto;
			}
			.btn_del {
			    cursor: pointer;
			    width: 16px;
			    height: 16px;
			}
			.vote_tr .btn_del {
			    display: inline-block;
			    width: 16px;
			    height: 16px;
			    background: url(images/vote_del.png) no-repeat 0 0;
			    position: absolute;
			    right: 10px;
			    top: 14px;
			    vertical-align: middle;
			}
			.vote_default_option{
				color: #bcbec2;
    			background-color: #f4f4f5;
    			border-color: #e9e9eb;
    			padding: 10px;
    			margin: 0 20px 0 0;
			}
        </style>
    </template:replace>
	
	<%--操作按钮--%>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float">
			<%--add页面的按钮--%>
			<ui:button text="${lfn:message('button.update') }" order="2" onclick="commitMethod('save');">
			</ui:button>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	
	<%--督办撤销表单--%>
	<template:replace name="content"> 
		<html:form action="/km/imeeting/km_imeeting_vote/kmImeetingVote.do" >
		    <p class="lui_form_subject">${ lfn:message('km-imeeting:table.kmImeetingVote') }</p>
	        <div class="lui_form_content_frame" >
	            <table class="tb_simple" width="95%">
	            	<!-- 标题 -->
	            	<tr>
	            		<td class="td_normal_title" width="15%">
	            			<bean:message bundle="km-imeeting" key="kmImeetingVote.docSubject" />
	            		</td>
	            		<td colspan="3">
	            			<xform:text property="docSubject" required="true" validators="maxLength(300)" style="width:79%"/>
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
										<xform:text property="fdVoteObjs[!{index}]" validators="maxLength(150)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:95%" className="inputsgl" required="true"  />									
										<a href="javascript:void(0);" class="btn_del"  onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}"/>				
									</td>	
								</tr>
								<c:forEach items="${kmImeetingVoteForm.fdVoteObjs}" var="fdVoteObj" varStatus="vstatus">
									<tr KMSS_IsContentRow="1" class="vote_tr">
										<td class="vote_word_td">
											<xform:text property="fdVoteObjs[${vstatus.index}]" value="${fdVoteObj}" validators="maxLength(150)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:95%;"  className="inputsgl" required="true" />
											<a href="javascript:void(0);" class="btn_del"   onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}" style="width: 2%;">
											</a>
										</td>
									</tr>
								</c:forEach>
								<tr type="optRow">
									<td>									
										<a href="javascript:void(0);" onclick="DocList_AddRow();" title="${lfn:message('doclist.add')}" class="add_item">																							
											<bean:message bundle="km-imeeting" key="kmImeetingVote.addItem" />
										</a>																			
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- 投票选项 -->
					<tr>
						<td class="td_normal_title" width="15%">
							<bean:message bundle="km-imeeting" key="kmImeetingVote.fdVoteOption" />
						</td>
						<td colspan="3">
						
							<xform:radio property="fdVoteOptionType" value="${kmImeetingVoteForm.fdVoteOptionType}" onValueChange="optionTypeChange();">
								<xform:simpleDataSource value="default">默认</xform:simpleDataSource>
								<xform:simpleDataSource value="custom">自定义</xform:simpleDataSource>
							</xform:radio>
							
							<div id="defaultOption" style="width:83%;margin-top:20px;">
								<span class="vote_default_option">同意</span>
								<span class="vote_default_option">反对</span>
								<span class="vote_default_option">弃权</span>
							</div>
							
							<table id="TABLE_DocList_Option" width="83%" tbdraggable="true" style="display:none;margin-top:20px;">
								<tr KMSS_IsReferRow="1" class="vote_tr" style="display: none" >
									<td class="vote_word_td" >
										<xform:text property="fdVoteOptions[!{index}]" validators="maxLength(150)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:95%" className="inputsgl" required="true"  />									
										<a href="javascript:void(0);" class="btn_del"  onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}"/>				
									</td>	
								</tr>
								<c:forEach items="${kmImeetingVoteForm.fdVoteOptions}" var="fdVoteOption" varStatus="vstatus">
									<tr KMSS_IsContentRow="1" class="vote_tr">
										<td class="vote_word_td">
											<xform:text property="fdVoteOptions[${vstatus.index}]" value="${fdVoteOption}" validators="maxLength(150)" subject="${lfn:message('km-imeeting:kmImeetingVote.fdVoteObj')}" style="width:95%;"  className="inputsgl" required="true" />
											<a href="javascript:void(0);" class="btn_del"   onclick="DocList_DeleteRow();" title="${lfn:message('doclist.delete')}" style="width: 2%;">
											</a>
										</td>
									</tr>
								</c:forEach>
								<tr type="optRow">
									<td>									
										<a href="javascript:void(0);" onclick="addOption();" title="${lfn:message('doclist.add')}" class="add_item">																							
											<bean:message bundle="km-imeeting" key="kmImeetingVote.addItem" />
										</a>																			
									</td>
								</tr>
							</table>
						</td>
					</tr>
	            </table>
	        </div>
		    <html:hidden property="fdTemporaryId" />
		    <html:hidden property="fdId" />
		    <html:hidden property="method_GET" />
		    
		</html:form>
		
		 <script type="text/javascript">
		 	$KMSSValidation();
		 	
		 	window.addOption = function(){
		 		var optTB = document.getElementById("TABLE_DocList_Option");
				var tbInfo = DocList_TableInfo[optTB.id];
		 		var index = tbInfo.lastIndex;
		 		console.log(index)
		 		if(index < 5){
		 			DocList_AddRow("TABLE_DocList_Option");
		 		}else{
		 			alert("最大添加5个选项");
		 		}
		 		
		 	}
	      	
			
			window.commitMethod = function(method){
				var voteObjs = $("#TABLE_DocList_Obj .vote_tr");
				if(voteObjs.length <= 0){
					alert("投票对象不能为空");
					return;
				}
				var value = $("input[name='fdVoteOptionType']:checked").val();
				if("custom" == value){
					var voteOptions = $("#TABLE_DocList_Option .vote_tr");
					if(voteOptions.length <= 0){
						alert("投票选项不能为空");
						return;
					}
				}
				Com_Submit(document.kmImeetingVoteForm, method);
			}
			
			function optionTypeChange(){
				var value = $("input[name='fdVoteOptionType']:checked").val();
				if("default" == value){
					$("#defaultOption").css("display",'block');
					$("#TABLE_DocList_Option").css("display",'none');
				}else if("custom" == value){
					$("#defaultOption").css("display",'none');
					$("#TABLE_DocList_Option").css("display",'');
				}
			}
        </script>
	</template:replace>
	
</template:include>