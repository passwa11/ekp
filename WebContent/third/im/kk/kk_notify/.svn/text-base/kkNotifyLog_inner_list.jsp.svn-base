<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
<list:data-columns var="kkNotifyLog" list="${queryPage.list}">
		<list:data-column property="fdId" title="fdId">
		</list:data-column>
		<list:data-column property="fdSubject" title="${ lfn:message('third-im-kk:kkNotifyLog.fdSubject') }" style="text-align:left;padding-left:20px;">
		</list:data-column>
		<list:data-column property="sendTime" title="${ lfn:message('third-im-kk:kkNotifyLog.sendTime') }">
		</list:data-column>
		<list:data-column property="rtnTime" title="${ lfn:message('third-im-kk:kkNotifyLog.rtnTime') }">
		</list:data-column>
		<list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/third/im/kk/kkNotifyLog.do?method=delete&fdId=${kkNotifyLog.fdId}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteAll('${kkNotifyLog.fdId}')">删除</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>		
	</list:data-columns>	
	
	<list:data-paging page="${ queryPage }"></list:data-paging>
</list:data>