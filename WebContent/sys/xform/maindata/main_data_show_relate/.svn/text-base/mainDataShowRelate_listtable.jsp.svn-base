<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<list:criteria id="criteria1">
			<list:cri-ref key="${searchField }" ref="criterion.sys.docSubject" >
			</list:cri-ref>
			
		</list:criteria>
		
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'${sourceURL}'}
			</ui:source>
			  <!-- 列表视图 -->	
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="${rowHref}"  name="columntable">
				<list:col-serial></list:col-serial> 
				<list:col-auto></list:col-auto>
				<%-- 
				<list:col-html   title="${ lfn:message('km-books:kmBooksMain.fdNumber') }" style="text-align:left">
				{$ <span class="com_subject">{%row['fdNumber']%}</span> $}
				</list:col-html>
				<list:col-auto props="docSubject;fdCount;fdBorrowNumber;fdRepealCount;fdRemainNumber"></list:col-auto>
				--%>
			</list:colTable>
		</list:listview> 
		
		
<list:paging></list:paging>	 