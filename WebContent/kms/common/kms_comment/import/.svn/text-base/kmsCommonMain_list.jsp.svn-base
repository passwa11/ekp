<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/list.tld" prefix="list"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column property="docDescription" title="纠错意见" style="width:50%;">
		</list:data-column>	
		
		<list:data-column escape="false" col="docStatus" title="${lfn:message('kms-common:kmsCommonDocErrorCorrection.docStatus') }">
			<sunbor:enumsShow enumsType="common_status" value="${item.docStatus}"></sunbor:enumsShow>
		</list:data-column>
		
		<list:data-column property="docCreator.fdName" title="纠错人姓名 ">
		</list:data-column>		
		 <list:data-column property="docCreateTime" title="纠错时间 ">
		</list:data-column> 
		<list:data-column property="docStatus" style="display:none">
		</list:data-column>
		<list:data-column headerClass="width140" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<div class="conf_show_more_w" >
				<div class="conf_btn_edit">
						<!-- 禁用 -->
						<a class="btn_txt" href="javascript:deleteAll('${item.fdId}')">${lfn:message('button.delete')}</a>
				</div>
			</div>
		</list:data-column>
	</list:data-columns>

	<list:data-paging page="${queryPage }" >
	</list:data-paging> 
</list:data>
