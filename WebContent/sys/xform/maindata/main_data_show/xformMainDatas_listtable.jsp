<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<% response.addHeader("X-UA-Compatible", "IE=edge"); %>
<title>
	查看主数据
</title>
<center>
<list:criteria id="criteria1" style="width:95%;">
			<list:cri-ref key="${searchField}" ref="criterion.sys.docSubject" >
			</list:cri-ref>
			<%-- 
			<list:cri-auto modelName="${modelName }" 
				property="${searchFields }"/>
				--%>
		</list:criteria>
		
		<list:listview id="listview" style="width:95%;">
			<ui:source type="AjaxJson">
					{url:'/sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=queryMainDatas&fdId=${JsParam.fdId}'}
			</ui:source>
			  <!-- 列表视图 -->	
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="${showPage }"  name="columntable">
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
</center>