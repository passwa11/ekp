<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.landray.kmss.util.*"%>
<%@ page import="com.landray.kmss.sys.admin.commontools.actions.SysAdminCommontoolsClearLogAction"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>

<script>
seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog, topic){
	window.BackstageLogoSetting = function (){
		dialog.iframe("/sys/admin/commontools/change_back_logo_dialog.jsp", 
				'<bean:message bundle="sys-admin" key="sys.admin.commontools.backenLogo"/>', null, {
				width:500,
				height:400,

			});
	}
});
</script>