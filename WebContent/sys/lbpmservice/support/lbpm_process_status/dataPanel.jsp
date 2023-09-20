<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<c:url value="/sys/lbpmservice/common/css/process_tab_main.css?s_cache=${LUI_Cache}"/>"/>
	<!-- 卡片列表 Starts -->
	<div id="lbpmHistoryWorkitemHead" style="width: 100%">
		<ul class="lui_flowstate_card">
						<li class="lui_flowstate_card_item">
				          <a class="lui_flowstate_card_pane" href="javascript:filterList(-1);">
				            <span class="icnbox bg_brown"><img src="images/icon-img-05.png"></span>
				            <div class="card_content">
				              <p class="card_num" id="personCount">0</p>
				              <p class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.personCount') }</p>
				            </div>
				          </a>
				        </li>
				        <li class="lui_flowstate_card_item">
				          <a class="lui_flowstate_card_pane" href="javascript:filterList(0);">
				            <span class="icnbox bg_blue"><img src="images/icon-img-01.png"></span>
				            <div class="card_content">
				              <p class="card_num" id="notLook">0</p>
				              <p class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.notLook') }</p>
				            </div>
				          </a>
				        </li>
				        <li class="lui_flowstate_card_item">
				          <a class="lui_flowstate_card_pane" href="javascript:filterList(1);">
				            <span class="icnbox bg_blue"><img src="images/icon-img-02.png"></span>
				            <div class="card_content">
				              <p class="card_num" id="isLook">0</p>
				              <p class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isLook') }</p>
				            </div>
				          </a>
				        </li>
				        <li class="lui_flowstate_card_item">
				          <a class="lui_flowstate_card_pane" href="javascript:filterList(2);">
				            <span class="icnbox bg_green"><img src="images/icon-img-03.png"></span>
				            <div class="card_content">
				              <p class="card_num" id="notFinish">0</p>
				              <p class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.notFinish') }</p>
				            </div>
				          </a>
				        </li>
				        <li class="lui_flowstate_card_item">
				          <a class="lui_flowstate_card_pane" href="javascript:filterList(3);">
				            <span class="icnbox bg_green"><img src="images/icon-img-04.png"></span>
				            <div class="card_content">
				              <p class="card_num" id="isFinish">0</p>
				              <p class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.isFinish') }</p>
				            </div>
				          </a>
				        </li>
				        
				        <li class="lui_flowstate_card_item">
						  <!-- #142166-流程状态中点击总催办次没有带出明细 -->
				          <a class="lui_flowstate_card_pane" id="customUrging">
				            <span class="icnbox bg_purple"><img src="images/icon-img-06.png"></span>
				            <div class="card_content">
				              <p class="card_num" id="pressCount">0</p>
				              <p class="card_txt">${ lfn:message('sys-lbpmservice-support:lbpm.process.status.pressCount') }</p>
				            </div>
				          </a>
				        </li>
				      </ul>
				      <!-- 卡片列表 Ends -->
	</div>