<%@ page language="java" contentType="text/json; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<list:data>
    <list:data-columns var="thirdWxWorkConfig" list="${queryPage.list}" varIndex="status">
        <list:data-column col="index">
            ${status+1}
        </list:data-column>

        <list:data-column col="fdName" title="${lfn:message('third-weixin-mutil:thirdWxWorkConfig.fdName')}" >
        	<c:out value="${thirdWxWorkConfig[1]}" />
        </list:data-column>
        <list:data-column col="fdKey" title="${lfn:message('third-weixin-mutil:thirdWxWorkConfig.fdKey')}">
        	<c:out value="${thirdWxWorkConfig[0]}" />
        </list:data-column>
        <list:data-column headerClass="width180" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!-- 操作列 -->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/third/weixin/third_wx_work_config/thirdWxWorkConfig.do?method=delete&fdKey=${thirdWxWorkConfig[0]}" requestMethod="POST">
						<!-- 删除 -->
						<a class="btn_txt" href="javascript:deleteDoc('${thirdWxWorkConfig[0]}')">删除</a>
					</kmss:auth>
					</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
    </list:data-columns>
    <%-- 分页信息生成 --%>
    <list:data-paging page="${queryPage}" />
</list:data>
