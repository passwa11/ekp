<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<div data-dojo-type="mui/query/QueryList" data-dojo-props="topHeight:!{topHeight}">
    <div data-dojo-type="mui/query/QueryListItem" 
		data-dojo-mixins="mui/syscategory/SysCategoryDialogMixin"
		data-dojo-props="label:'<bean:message key="portlet.cate" />',icon:'mui mui-Csort',
		    getTemplate:1,
		    selType: 0|1,
			modelName:'com.landray.kmss.fssc.fee.model.FsscFeeTemplate',
			redirectURL:'/fssc/fee/mobile/index.jsp?moduleName=!{curNames}&filter=1',
			filterURL:'/fssc/fee/fssc_fee_main/fsscFeeMain.do?method=data&q.docTemplate=!{curIds}&orderby=docCreateTime&ordertype=down'">
	</div>
</div>
