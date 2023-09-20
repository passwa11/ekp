<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
    本页面为流程关联查看页多语言缓存页面，
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
    window._ResourceStringCache['sys-lbpmservice:lbpm.nodeType.draftNode']='<%=ResourceUtil.getString("sys-lbpmservice:lbpm.nodeType.draftNode",request.getLocale())%>';
    window._ResourceStringCache['sys-xform-base:Designer_Lang.controlAttrMaxPersonNumValidate']='<%=ResourceUtil.getString("sys-xform-base:Designer_Lang.controlAttrMaxPersonNumValidate",request.getLocale())%>';
    window._ResourceStringCache['sys-xform-base:Designer_Lang.controlCalculation_func_timeWarning']='<%=ResourceUtil.getString("sys-xform-base:Designer_Lang.controlCalculation_func_timeWarning",request.getLocale())%>';
</script>
