/**
 * 极速模式下页面配置
 */
define(function(require, exports, module) {

	module.exports =  [{
		//example : "/sys/portal/page/.jsp"
		pattern : /\/sys\/portal\/page\/.*\/.*\.jsp/,
		target : "_content"
	},{
		//example : "/sys/portal/page.jsp?mainPageId="
		pattern : /\/sys\/portal\/page\.jsp\?mainPageId=.*/,
		target : "_content"
	},{
		//example : "/sys/portal/sys_portal_page/view.jsp?fdId="
		pattern : /\/sys\/portal\/sys_portal_page\/view\.jsp\?fdId=.*/,
		target : "_content"
	},{
		//example : "/sys/portal/sys_portal_page/sysPortalPage_content.jsp?fdId="
		pattern : /\/sys\/portal\/sys_portal_page\/sysPortalPage_content\.jsp\?fdId=.*/,
		target : "_content"
	}];
	
})
