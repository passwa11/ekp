<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
<list:data-columns var="notifyLog" list="${queryPage.list}">
		<list:data-column property="fdId" title="fdId">
		</list:data-column>
		<list:data-column property="docSubject" title="${ lfn:message('third-ding:thirdDingNotifylog.docSubject') }" style="text-align:left;padding-left:20px;">
		</list:data-column>
		<list:data-column property="fdSendTime" title="${ lfn:message('third-ding:thirdDingNotifylog.fdSendTime') }">
		</list:data-column>
		<%-- <list:data-column property="fdRtnMsg" title="${ lfn:message('third-ding:thirdDingNotifylog.fdRtnMsg') }">
		</list:data-column> --%>
		<list:data-column property="fdRtnTime" title="${ lfn:message('third-ding:thirdDingNotifylog.fdRtnTime') }">
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/third/ding/third_ding_notifylog/thirdDingNotifylog.do?method=delete&fdId=${notifyLog.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${notifyLog.fdId}')">删除</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>		
	</list:data-columns>	
	
	<list:data-paging page="${ queryPage }"></list:data-paging>
</list:data>