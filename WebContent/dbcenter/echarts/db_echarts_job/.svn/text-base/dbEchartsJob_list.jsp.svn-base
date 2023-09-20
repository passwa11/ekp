<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="dbEchartsJob" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  property="fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsJob.fdName') }" style="text-align:center;min-width:180px">
		</list:data-column>	
		<list:data-column  headerClass="width80" col="fdEnable" title="${ lfn:message('dbcenter-echarts:dbEchartsJob.fdEnable') }">
			<sunbor:enumsShow value="${dbEchartsJob.fdEnable}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column  headerClass="width140" property="fdExpression" title="${ lfn:message('dbcenter-echarts:dbEchartsJob.fdExpression') }">
		</list:data-column>
		<list:data-column  headerClass="width140" property="docCreateTime" title="${ lfn:message('dbcenter-echarts:dbEchartsJob.docCreateTime') }">
		</list:data-column>
		<list:data-column  headerClass="width120" property="docCreator.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsJob.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--������ť ��ʼ-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<a class="btn_txt" href="javascript:edit('${dbEchartsJob.fdId}')">${lfn:message('button.edit')}</a>
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_job/dbEchartsJob.do?method=deleteall">
						<a class="btn_txt" href="javascript:deleteAll('${dbEchartsJob.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>
				</div>
			</div>
			<!--������ť ����-->
		</list:data-column>
	</list:data-columns>
	<!-- ��ҳ -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
