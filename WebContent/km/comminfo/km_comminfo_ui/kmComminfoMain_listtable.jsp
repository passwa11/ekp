<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
		<list:listview id="listview">
			<ui:source type="AjaxJson">
					{url:'/km/comminfo/km_comminfo_main/kmComminfoMainIndex.do?method=list&forward=list'}
			</ui:source>
			  <!-- 列表视图 -->	
			<list:colTable isDefault="true" layout="sys.ui.listview.columntable" 
				rowHref="/km/comminfo/km_comminfo_main/kmComminfoMain.do?method=view&fdId=!{fdId}"  name="columntable">
				<list:col-checkbox></list:col-checkbox>
				<list:col-serial></list:col-serial> 
				
				<list:col-auto props="docSubject;fdOrder;docCategory;docCreator"></list:col-auto>
				<list:col-html headerClass="width100" title="${ lfn:message('km-comminfo:kmComminfoMain.docCreateTime') }">
					var dataTime = row['docCreateTime'];
					dataTime = dataTime.substring(0,10);
					{$ {%dataTime%} $}									
				</list:col-html>
			</list:colTable>
		</list:listview> 
		
		<list:paging></list:paging>	 