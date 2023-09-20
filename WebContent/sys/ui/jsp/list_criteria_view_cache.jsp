<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/ui/lfn.tld" prefix="lfn"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%--
    本页面为列表页面、筛选项器渲染模版页面资源整合，推荐在列表页面，存在筛选项部件页面部署
    使用方式 在页面 <script></script>同级位置引入本页面
    可以减少异步前渲染的请求数量。

    example:
    ....
    <script>...</script>
    <c:import url="/sys/ui/jsp/list_criteria_view_cache.jsp"></c:import>
    <script>...</script>
--%>
    <script>
        window._loadArray= window._loadArray || {};
        <%
            List<String> _loadArray = new ArrayList<String>();
            _loadArray.add("sys/ui/js/criteria/template/criterion-cell.jsp");
            _loadArray.add("sys/ui/js/criteria/template/criterion-textinput-selected-cell.jsp");
            _loadArray.add("sys/ui/js/criteria/template/criterion-textinput-cell.jsp");
            _loadArray.add("sys/ui/js/criteria/template/criterion-selected.jsp");
            _loadArray.add("sys/ui/js/criteria/template/criterion-selected-cell.jsp");
            _loadArray.add("sys/ui/js/criteria/template/criterion-new-calendar-cell.jsp");
            _loadArray.add("sys/ui/js/criteria/template/criterion-hierarchy-cell.jsp");
            _loadArray.add("sys/ui/js/criteria/template/criteria.jsp");
            _loadArray.add("sys/ui/extend/listview/paging.jsp");
            _loadArray.add("sys/ui/extend/listview/top_paging.jsp");
            _loadArray.add("sys/ui/extend/listview/simple_paging.jsp");
            for (String url : _loadArray) {
                TargetUrlContentAcquirer acquirer = new TargetUrlContentAcquirer("/"+url, pageContext);
                String text = acquirer.acquireString();
                pageContext.setAttribute("url", url);
                pageContext.setAttribute("text", text);
        %>
        //window._loadArray的缓存格式如下：（有部分key为/ekp/..起始）
        //http://localhost:8080/ekp/sys/ui/js/criteria/template/criterion-selected-cell.jsp?s_cache=1652668503485&s_locale=zh-cn: "..."
        //http://localhost:8080/ekp/sys/ui/js/criteria/template/criterion-selected.jsp?s_cache=1652668503485&s_locale=zh-cn: "..."
            var key_url ="${KMSS_Parameter_ContextPath}${url}?s_cache=${LUI_Cache}&s_locale=" + seajs.data.env.locale;//项目路径
            var full_key_url = seajs.data.base + "${url}?s_cache=${LUI_Cache}&s_locale=" + seajs.data.env.locale;//全请求路径
            var val_txt = '${lfn:escapeJs(text)}';
            if(!window._loadArray[key_url]){
                //预先放入缓存，不再发起异步请求
                window._loadArray[key_url] = val_txt;
            }
            if(!window._loadArray[full_key_url]){
                //预先放入缓存，不再发起异步请求
                window._loadArray[full_key_url] = val_txt;
            }
        <%
            }
        %>



        
    </script>
