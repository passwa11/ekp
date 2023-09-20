<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.ftsearch.util.MultSystemlicense" %>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page import="com.landray.kmss.util.StringUtil" %>
<%@ page import="java.net.InetAddress" %>
<%@ page import="java.net.NetworkInterface" %>
<%@page import="java.util.*"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<script type="text/javascript" src="${KMSS_Parameter_ResPath}js/jquery.js"></script>
<%@ include file="/resource/jsp/tree_top.jsp" %>
<%
    String locale = ResourceUtil.getLocaleStringByUser();
	boolean multSystemFlag=MultSystemlicense.getLicene();
	String searchEngineType= ResourceUtil.getKmssConfigString("sys.ftsearch.config.engineType");
	StringBuilder ipBuilder = new StringBuilder();
	 try {  
		 boolean flag = false;
	       for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {  
	           NetworkInterface intf = en.nextElement();  
	           for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {  
	               InetAddress inetAddress = enumIpAddr.nextElement();  
	               if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {  
	            	   ipBuilder.append(inetAddress.getHostAddress().toString());
	            	   flag = true;
	            	   break;
	               }  
	  
	           }
	           if(flag){
	        	   break;
	           }
	       }  
	   } catch (Exception ex) {  
	   }  
	String relIp = ipBuilder.toString();
%>
var ip = null;
var port = null;
var flag = true;
var urlHeader = null;
$(function(){
	if(<%="elasticsearch".equals(searchEngineType) || StringUtil.isNull(searchEngineType)%>){
			flag = false;
			var linkUrl = "<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=loadOnesearchConfig"/>";
			$.ajax({url:linkUrl,dataType:"json",success:function(data){
				ip = data['kmss.onesearch.config.host'];
				if(ip == "127.0.0.1" || ip =="localhost"){
					ip = "<%=relIp%>";
				}
				port = data['kmss.onesearch.config.service.port'];
				var outUrl = data['kmss.onesearch.config.service.outURL.header'];
				if(typeof(outUrl) != 'undefined' &&　$.trim(outUrl) != "null"){
					var outLinkUrl = outUrl+"/loginAction.action?passWord=landray@onesearch";
					$.ajax({url:outLinkUrl,dataType:"jsonp", beforeSend: function(request) {
					                        request.setRequestHeader("P3P:CP", "NOI ADM DEV COM NAV OUR STP");
					                    },async:false,timeout:2000,    
					     		 success:function(data){
									urlHeader = outUrl;
									generateTree();
									loginOnesearchServer();
									flag = true;
								}});
					setTimeout(sendIpUrl,4000);
				}else{
					sendIpUrl();
				}
			}});
	}
});
function sendIpUrl(){
		var ipUrl = "http://"+ip+":"+port+"/loginAction.action?passWord=landray@onesearch";
		$.ajax({url:ipUrl,dataType:"jsonp", beforeSend: function(request) {
                   	request.setRequestHeader("P3P:CP", "NOI ADM DEV COM NAV OUR STP");
                   },
            async:false,timeout:2000,success:function(data){
				urlHeader = "http://"+ip+":"+port;
				generateTree();
				loginOnesearchServer();
				flag = true;
		}});
}

function loginOnesearchServer(){
	var linkUrl = urlHeader+"/loginAction.action?passWord=landray@onesearch";
	$.ajax({url:linkUrl,dataType:"jsonp", beforeSend: function(request) {
                        request.setRequestHeader("P3P:CP", "NOI ADM DEV COM NAV OUR STP");
                    },async: false,success:function(data){
	
	}});
	setTimeout(loginOnesearchServer,200000);
}
function generateTree()
{	
	LKSTree = new TreeView(
		"LKSTree",
		"<bean:message key="module.sys.ftsearch.expand" bundle="sys-ftsearch-expand"/>",
		document.getElementById("treeDiv")
	);
	var n1, n2, n3, n4, n5;
	n1 = LKSTree.treeRoot;
	
	<%-- 搜索主页 
	n2 = n1.AppendURLChild(
		"<bean:message key="sysFtsearch.ftsearch.homePage" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/searchHomePage.do?method=homePage" />"
	);--%>
	<%-- 默认搜索模块配置--%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchConfig" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/sys_ftsearch_config/sysFtsearchConfig.do?method=edit" />"
	);
	
	<%-- 字段权值配置 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.fieldBoostingConfig" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/fieldBoostingConfig.do?method=getSearchField" />"
	);
	
	<%-- 搜索优先展示区域配置 --%>
	if(<%="elasticsearch".equals(searchEngineType) || StringUtil.isNull(searchEngineType)%>){
		n2 = n1.AppendURLChild(
			"<bean:message key="reaultDisplayArea.customDisplayAreaConfig" bundle="sys-ftsearch-expand" />",
			"<c:url value="/sys/ftsearch/expand/customDisplayAreaConfig.do?method=load" />"
		);
	}
	<%-- 阅读文档 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchReadLog" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_read_log/sysFtsearchReadLog.do?method=list" />"
	);
	
	<%-- 搜索热词 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchHotword" bundle="sys-ftsearch-expand" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message key="table.sysFtsearchCurrentHotword" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=list&orderby=sysFtsearchHotword.fdWordOrder desc,sysFtsearchHotword.fdSearchFrequency&ordertype=down" />"
	);
	n3 = n2.AppendURLChild(
		"<bean:message key="table.sysFtsearchDisabledHotword" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_hotword/sysFtsearchHotword.do?method=list&fdDisabled=1&orderby=sysFtsearchHotword.fdWordOrder desc,sysFtsearchHotword.fdSearchFrequency&ordertype=down" />"
	);
	
	<%-- 用户搜索日志表 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchWord" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=list" />"
	);
	<%-- 用户搜索词排行 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchWord.ranking" bundle="sys-ftsearch-expand" />"
	);
	n3 = n2.AppendURLChild(
	   "<bean:message key="search_word_ranking_config" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sysFtsearchWordStatisticsRankingConfig.do?method=view" />"
    );
	n3 = n2.AppendURLChild(
	   "<bean:message key="table.sysFtsearchWord.ranking.day" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=wordRanking&day=-1" />"
    );
	n3 = n2.AppendURLChild(
	   "<bean:message key="table.sysFtsearchWord.ranking.week" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=wordRanking&day=-7" />"
    );
	n3 = n2.AppendURLChild(
	   "<bean:message key="table.sysFtsearchWord.ranking.month" bundle="sys-ftsearch-expand" />",
        "<c:url value="/sys/ftsearch/expand/sys_ftsearch_word/sysFtsearchWord.do?method=wordRanking&day=-30" />"
    );
	if(<%=multSystemFlag%>){
		<%-- 多系统支持配置 --%>
		n2 = n1.AppendURLChild(
			"<bean:message key="table.sysFtsearchMultisystem" bundle="sys-ftsearch-expand" />"
		);
		<%-- 多系统支持 --%>
		n3 = n2.AppendURLChild(
			"<bean:message key="table.sysFtsearchMultisystem" bundle="sys-ftsearch-expand" />",
			"<c:url value="/sys/ftsearch/expand/sys_ftsearch_multisystem/sysFtsearchMultisystem.do?method=list" />"
		);
		
		<%-- 多系统模块类别分类 --%>
		n3 = n2.AppendURLChild(
			"<bean:message key="table.sysFtsearchModelgroup" bundle="sys-ftsearch-expand" />",
			"<c:url value="/sys/ftsearch/expand/sys_ftsearch_modelgroup/sysFtsearchModelgroup.do?method=list" />"
		);
	}
	<%-- 汉字联想词表 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchChineseLegend" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_chinese_legend/sysFtsearchChineseLegend.do?method=list&orderby=sysFtsearchChineseLegend.fdSearchFrequency&ordertype=down" />"
	);
	<%-- 分面搜索配置 --%>
	 //n2 = n1.AppendURLChild(
		//"<bean:message key="table.sysFtsearchFacet" bundle="sys-ftsearch-expand" />",
		//"<c:url value="/sys/ftsearch/expand/sys_ftsearch_facet/sysFtsearchFacet.do?method=list" />"
	//);
	<%-- 同义词结果表 	--%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchSynonymsSet" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=list" />"
	);
	<%-- 统一搜索引擎服务管理
	if((<%="elasticsearch".equals(searchEngineType) || StringUtil.isNull(searchEngineType)%> )&& urlHeader != null){
		n2 = n1.AppendURLChild(
			"<bean:message key="table.sysFtsearchServiceManage" bundle="sys-ftsearch-expand" />"
		);
		n3 =  n2.AppendURLChild(
			"<bean:message key="sysFtsearch.ServiceOverView" bundle="sys-ftsearch-expand" />"
		);
		n4 = n3.AppendURLChild(
			"<bean:message key="sysFtsearch.ServiceSummary" bundle="sys-ftsearch-expand" />",
			urlHeader+"/overview/overview.jsp"
		);
		n4 = n3.AppendURLChild(
			"<bean:message key="sysFtsearch.ServicePerformanceMonitor" bundle="sys-ftsearch-expand" />",
			urlHeader+"/overview/onesearchPerformanceMonitor.jsp"
		);
		n3 =  n2.AppendURLChild(
			"<bean:message key="sysFtsearch.SearchWordStatistics" bundle="sys-ftsearch-expand" />"
		);
		n4 = n3.AppendURLChild(
			"<bean:message key="sysFtsearch.SearchWordSrcStatistics" bundle="sys-ftsearch-expand" />",
			urlHeader+"/log/statisticsSrcLog.jsp?locale=<%=locale %>"
		);
		n4 = n3.AppendURLChild(
			"<bean:message key="sysFtsearch.SearchWordAnalyzedStatistics" bundle="sys-ftsearch-expand" />",
			urlHeader+"/log/statisticsLog.jsp?locale=<%=locale %>"
		);
		n4 = n3.AppendURLChild(
			"<bean:message key="sysFtsearch.SearchWordView" bundle="sys-ftsearch-expand" />",
			urlHeader+"/log/viewLog.jsp?locale=<%=locale %>"
		);
	}--%>
	
	<%-- 同义词表 
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchSynonym" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=list" />"
	);--%>
	<%-- 
		//n3 = n2.AppendURLChild(
				//"同步到结果表",
				//"同步到结果表",
				//"<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=list"/>"
				//)
		
			n3 = n2.AppendURLChild(
				"未同步",
				"<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=list&fdSynchState=0" />"
				)
			n3 = n2.AppendURLChild(
				"已同步",
				"<c:url value="/sys/ftsearch/expand/sys_ftsearch_synonym/sysFtsearchSynonym.do?method=list&fdSynchState=1" />"
				)	
	--%>
	
	
	
	<%-- 词库管理表 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchParticiple" bundle="sys-ftsearch-expand" />"
	);
	<%-- 自定义词典 --%>
	n3 = n2.AppendURLChild(
		"<bean:message key="table.sysFtsearchUserParticiple" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_user_participle/sysFtsearchUserParticiple.do?method=list" />"
	)
	<%--歧义词分词管理 --%>
	n3 = n2.AppendURLChild(
		"<bean:message key="table.sysFtsearchAmbParticiple" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_amb_participle/sysFtsearchAmbParticiple.do?method=list" />"
	)
	
	<%-- 快照设置 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="sysFtsearch.snapshotConfig" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/snapshotConfig.do?method=getSnapshotConfig" />"
	);
	
	n2 = n1.AppendURLChild(
		"<bean:message key="sysFtsearch.defaultModeConfigInfo" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/snapshotConfig.do?method=getDefaultFtSearchModeConfig" />"
	);
	
		<%-- 模块最后索引记录 --%>
	n2 = n1.AppendURLChild(
		"<bean:message key="sysFtsearch.indexStatus.lastIndexTime" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_indexstatus/sysFtsearchIndexStatus.do?method=list" />"
	);
	
	<%--
		n3 = n2.AppendURLChild(
				//"导出分词",
				"导出分词",
				"<c:url value="/sys/ftsearch/expand/sys_ftsearch_participle/sysFtsearchParticiple.do?method=list" />"
				)
		
			n4 = n3.AppendURLChild(
				//"未导出分词",
				"未导出分词",
				"<c:url value="/sys/ftsearch/expand/sys_ftsearch_participle/sysFtsearchParticiple.do?method=list&fdExportState=0"/>"
				)
			n4 = n3.AppendURLChild(
				//"已导出分词",
				"已导出分词",
				"<c:url value="/sys/ftsearch/expand/sys_ftsearch_participle/sysFtsearchParticiple.do?method=list&fdExportState=1"/>"
				)
	 --%>
		<%-- 分词类别 
		n3 = n2.AppendURLChild(
			"<bean:message key="table.sysFtsearchParticipleCategory" bundle="sys-ftsearch-expand" />",
			"<c:url value="/sys/ftsearch/expand/sys_ftsearch_participle_category/sysFtsearchParticipleCategory_tree.jsp" />"
		);--%>
		
		
		
		
	<%--
	//按类别
	//n3 = n2.AppendURLChild(
		//"按类别"
	//);
	
	//n3.AppendSimpleCategoryData(
		//"com.landray.kmss.sys.ftsearch.expand.model.SysFtsearchParticipleCategory",
		//"<c:url value="/sys/ftsearch/expand/sys_ftsearch_participle/sysFtsearchParticiple.do?method=listChildren&categoryId=!{value}&orderby=docCreateTime&ordertype=down" />"
	//);
	//n3.isExpanded = true;
	
		//导出功能
		//<kmss:auth requestURL="/sys/ftsearch/expand/sys_ftsearch_participle/sysFtsearchParticiple.do?method=list&fdModelName=com.landray.kmss.sys.ftsearch.expand.model.SysFtsearchParticiple">
			
			//n3 = n2.AppendURLChild(
			//"导出",
			//"<c:url value="/sys/ftsearch/expand/sys_ftsearch_participle/sysFtsearchParticiple.do?method=list&fdModelName=com.landray.kmss.sys.ftsearch.expand.model.SysFtsearchParticiple"/>"	
			//);
		</kmss:auth>
	--%>	
		
	<%-- 用户搜索分析表
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchAnalysis" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_analysis/sysFtsearchAnalysis.do?method=list" />"
	); 	--%>
	
		<%-- 索引初始化
	n2 = n1.AppendURLChild(
		"<bean:message key="table.sysFtsearchAnalysis" bundle="sys-ftsearch-expand" />",
		"<c:url value="/sys/ftsearch/expand/sys_ftsearch_analysis/sysFtsearchAnalysis.do?method=list" />"
	); --%>
	
	
	LKSTree.EnableRightMenu();
	LKSTree.Show();
	
}

<%@ include file="/resource/jsp/tree_down.jsp" %>