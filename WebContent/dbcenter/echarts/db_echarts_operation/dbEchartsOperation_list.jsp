<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="dbEchartsOperation" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsOperation.fdName') }" style="text-align:center;min-width:180px">
		</list:data-column>	
		<list:data-column  headerClass="width140" property="docCreateTime" title="${ lfn:message('dbcenter-echarts:dbEchartsOperation.docCreateTime') }">
		</list:data-column>
		<list:data-column  headerClass="width140" property="docCreator.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsOperation.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--������ť ��ʼ-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:edit('${dbEchartsOperation.fdId}')">${lfn:message('button.edit')}</a>
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_operation/dbEchartsOperation.do?method=deleteall">
						<a class="btn_txt" href="javascript:deleteAll('${dbEchartsOperation.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--������ť ����-->
		</list:data-column>
	</list:data-columns>
	<!-- ��ҳ -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
