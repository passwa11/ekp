<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<div class="mhuiViewSec mhui-hidden" id="indexView">
	
	<div id="mhuiImeetingStatus" data-status="<c:if test="${isEnd==true}">hold</c:if><c:if test="${isBegin==false}">unHold</c:if>" class="mhuiImeetingStatus <c:if test="${isBegin==true && isEnd==false}">mhui-hidden</c:if>">
		<i class="mui mui-stamp"></i>
		<span id="mhuiImeetingStatusText">
			<c:if test="${isEnd==true}">已召开</c:if>
			<c:if test="${isBegin==false}">未召开</c:if>
		</span>
	</div>
	
	<%--会议目的--%>
	<c:if test="${not empty kmImeetingMainForm.fdMeetingAim  }">
		<div data-dojo-type="mhui/panel/ExhibtionPanel">
	      	<div data-dojo-type="mhui/panel/ExhibtionPanelHead"
	      		data-dojo-props="title:'<bean:message bundle="km-imeeting" key="kmImeetingMain.fdMeetingAim"/>'"></div>
	      	<div data-dojo-type="mhui/panel/ExhibtionPanelBody">
	      		<pre id="mhuiIMeetingAim"><c:out value="${kmImeetingMainForm.fdMeetingAim }"></c:out></pre>
	      	</div>
		</div>
	</c:if>
	
	<%--会议议程--%>
	<c:if test="${kmImeetingMainForm.fdTemplateId != null && kmImeetingMainForm.fdTemplateId != '' && fn:length(kmImeetingMainForm.kmImeetingAgendaForms) > 0}">
		<div data-dojo-type="mhui/panel/ExhibtionPanel">
	     	<div data-dojo-type="mhui/panel/ExhibtionPanelHead"
	     		data-dojo-props="title:'会议议程',buttons:[{text:'',icon:'mui mui-reflash',onClick:'refreshAgenda'}]"></div>
	     	<div data-dojo-type="mhui/panel/ExhibtionPanelBody">
	        
	           <!-- 时间轴列表 Starts -->
	           
				<script>
					window.agendaListEventPrefixs = [
						<c:forEach items="${kmImeetingMainForm.kmImeetingAgendaForms}" var="kmImeetingAgendaitem">
							'attachmentObject_ImeetingUploadAtt_${kmImeetingAgendaitem.fdId}_refresh',
						</c:forEach>						                                 
                       ];
				</script>		           
	           
				<ul id="agendaList"
					data-dojo-type="mhui/list/ItemListBase"
					data-dojo-mixins="mhui/list/TimeAxisItemListMixin"
					data-dojo-props="slot:true">
					
					<c:forEach items="${kmImeetingMainForm.kmImeetingAgendaForms}" var="kmImeetingAgendaitem" varStatus="vstatus">
						<li data-dojo-type="mhui/list/TimeAxisItem"
							data-dojo-props="slot:true,key:'agenda'">
							
							<div data-dojo-type="mhui/list/SlotBase" 
								data-dojo-props="key:'agenda',slotName:'head'">
								<span><c:out value="${kmImeetingAgendaitem.docReporterName}"></c:out></span>
								<span><c:out value="${kmImeetingAgendaitem.docSubject}"></c:out></span>
								<c:if test="${not empty  kmImeetingAgendaitem.docReporterTime}">
									<span>
										<c:out value="${kmImeetingAgendaitem.docReporterTime}"></c:out>
										<bean:message key="date.interval.minute"/>
									</span>
								</c:if>
							</div>
						
							<div data-dojo-type="mhui/list/SlotBase" 
								data-dojo-props="key:'agenda',slotName:'body'">
								<c:import url="/sys/attachment/maxhub/import/view.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="ImeetingUploadAtt_${kmImeetingAgendaitem.fdId }" />
									<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
									<c:param name="fdModelId" value="${kmImeetingMainForm.fdId }" />
								</c:import>
							</div>
						
						</li>
					</c:forEach>
					
				</ul>									           
	           <!-- 时间轴列表 Ends -->
	      	</div>
		</div>
	</c:if>
	
	<%--相关资料--%>
	<div id="attachmentPanel" 
		data-dojo-type="mhui/panel/ExhibtionPanel">
		<div data-dojo-type="mhui/panel/ExhibtionPanelHead"
			data-dojo-props="title:'相关资料',buttons:[{text:'',icon:'mui mui-reflash',onClick:'refreshRelevant'}]"></div>
			
		<div data-dojo-type="mhui/panel/ExhibtionPanelBody">
			
			<!-- 附件 Starts -->
			
			<c:import url="/sys/attachment/maxhub/import/view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="fdKey" value="attachment" />
				<c:param name="fdModelId" value="${kmImeetingMainForm.fdId }" />
				<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
			</c:import>
	        
	        <!-- 附件 Ends -->
		</div>
	</div>
	
	<div data-dojo-type="mhui/panel/ExhibtionPanel">
		<div data-dojo-type="mhui/panel/ExhibtionPanelHead"
			data-dojo-props="title:'会议记录'"></div>
			
		<div data-dojo-type="mhui/panel/ExhibtionPanelBody">
			
			<!-- 记录附件 Starts -->
			<c:import url="/sys/attachment/maxhub/import/edit.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmImeetingMainForm" />
				<c:param name="fdKey" value="tmpAttachment" />
				<c:param name="hiddenOpt" value="true" />
			</c:import>
	        <!-- 记录附件 Ends -->
	        
	        <!-- 若记录附件为空，显示白板按钮 -->
			<div id="openBoardPanel" style="text-align: center; margin-top: 4rem;" class="mhui-hidden">
				<div data-dojo-type="mhui/message/MessageBox"
					data-dojo-props="icon:'mhui-icon-nodata',message:'您可以使用白板记录会议并保存在会议记录中'"></div>
				<div id="btnOpenBoard"
					data-dojo-type="mhui/button/Button"
					data-dojo-props="text:'使用白板',type:'primary',size:'lg',onClick:'goBoard'"></div>
			</div>
			
		</div>
	</div>
	
	
      	
</div>

<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/maxhub/resource/js/view_index.js"></script>