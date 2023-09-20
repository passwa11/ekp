<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<html>
	<body>
		<div class="d_lui_mix_wids" style="top: 10px;display: none" id="showInfoId">
			<input type='hidden' id='personFlag' name='personFlag' value='0' />
			<div class='d_lui_mix_head'><span class='d_lui_mix_strong'>加班人员</span><span class='d_lui_mix_deletes' onclick='chosedPersonInfo();'>✕</span> </div>
			<div>
				<input type="hidden" name = "clearUserStr" value="${clearUsers}" id="clearUserStr">
				<input type="hidden" name = "selectUserStr" value="${selectUsers}" id="selectUserStr">
				<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="2" var-average='true' var-useMaxWidth='true'>			
		    		<!-- 已去除人员 -->
		    		<ui:content title="已去除(${clearCount})" expand="true" id="clearCounts">
						<div style="width: 100%;background: #F5F6F7;font-family: PingFangSC-Regular; font-size: 14px;">以下人员由于上下班时间，加班规则与其他人员不同，已从加班人员中去除，需另行提交</div>
                		<div id="clearUsers">     
                			<ul style='list-style-type:none'>
	                			<c:forEach items="${clearUsers}" var="clearuser" varStatus="vs"> 
	                				<li>
			                			<div class="muiLbpmserviceAuditNoteHandler">
			                				<span class="muiLbpmserviceNodeHander history">
		                						<person:dingHeadimage size='s' personId='${clearuser.id}' contextPath='true'></person:dingHeadimage>
			                				</span>
                							<span class="span_name_h">${clearuser.name}</span>
				                		</div>
	                				</li>
	                			</c:forEach>
                			</ul>          	
                		</div>
	                </ui:content>
	                
		    		<!-- 已选择人员 -->
		    		<ui:content title="已选择(${selectCount})" expand="true" id= "selectCounts">
	           			<div style="width: 100%;background: #F5F6F7;font-family: PingFangSC-Regular; font-size: 14px;">以下人员已选择</div>
                		<div id="selectUsers">
                			<ul style='list-style-type:none'>
	                			<c:forEach items="${selectUsers}" var="selectUser" varStatus="vs"> 
	                				<li>
			                			<div class="muiLbpmserviceAuditNoteHandler">
			                				<span class="muiLbpmserviceNodeHander history">
		                						<person:dingHeadimage size='s' personId='${selectUser.id}' contextPath='true'></person:dingHeadimage>
			                				</span>
                							<span class="span_name_h">${selectUser.name}</span>
				                		</div>
	                				</li>
	                			</c:forEach>
                			</ul> 
                		</div>
	                </ui:content> 
		    	</ui:tabpanel>
	    	</div>
		</div>
	</body>
</html>