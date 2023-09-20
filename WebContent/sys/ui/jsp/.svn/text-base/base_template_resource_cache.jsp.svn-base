<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    本页面为多语言缓存页面，
    推荐各模块，各类高频调用Data_GetResourceString方法的页面
    以本页面为模版创建自己的xxxx_resource_cache.jsp页面并部署自己业务需要的多语言缓存

    使用方式 ：
    在页面 <script></script>同级位置引入本页面
    可以减少dataxml.jsp?s_bean=XMLGetResourceService请求数量。

     example:
    ....
    <script>...</script>
    <c:import url="/sys/ui/jsp/base_template_resource_cache.jsp"></c:import>
    <script>...</script>

     已知:
	 1、dataxml.jsp?s_bean=XMLGetResourceService请求来源于data.js->Data_GetResourceString方法。
	 2、Data_GetResourceString方法中做了全局变量缓存window._ResourceStringCache，
	 记录已经请求的messageKey。但请求数量仍然较多。
	 本页面:
	 通过jsp页面加载时，往全局变量window._ResourceStringCache中放入资源文件信息，
	 结合上原有的前端缓存机制，达到减少前端通过调用js方法异步Data_GetResourceString请求数量
--%>
<script type="text/javascript">
    window._ResourceStringCache = window._ResourceStringCache || {};
    <%-- 如果存在全局变量，追加的方式预置calendar需要的几个常规多语言，高频  --%>
    window._ResourceStringCache['date.format.time']='<%=ResourceUtil.getString("date.format.time",request.getLocale())%>';
    window._ResourceStringCache['date.format.datetime.2y']='<%=ResourceUtil.getString("date.format.datetime.2y",request.getLocale())%>';
    window._ResourceStringCache['date.format.date.2y']='<%=ResourceUtil.getString("date.format.date.2y",request.getLocale())%>';
    window._ResourceStringCache['date.format.date7y']='<%=ResourceUtil.getString("date.format.date7y",request.getLocale())%>';
    window._ResourceStringCache['date.format.yearMonth']='<%=ResourceUtil.getString("date.format.yearMonth",request.getLocale())%>';
    window._ResourceStringCache['date.format.year']='<%=ResourceUtil.getString("date.format.year",request.getLocale())%>';
</script>
