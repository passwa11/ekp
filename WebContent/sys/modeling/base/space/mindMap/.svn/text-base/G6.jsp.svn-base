<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal" %>
<%@ include file="/sys/modeling/base/modeling_head.jsp" %>
<!doctype html>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="renderer" content="webkit"/>
    <%@ include file="/sys/ui/jsp/jshead.jsp" %>

    <script type="text/javascript">
        seajs.use(['theme!list', 'theme!portal']);
        Com_IncludeFile("g6.4.3.3.min.js", Com_Parameter.ContextPath + '/sys/modeling/base/resources/antv/', 'js', true);
            </script>
    <script src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript" src="${ LUI_ContextPath }/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/profile/resource/js/dropdown.js?s_cache=${LUI_Cache}"></script>
    <script type="text/javascript"
            src="${ LUI_ContextPath }/sys/ui/extend/template/module/list.js?s_cache=${LUI_Cache}"></script>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/profile/resource/css/operations.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${ LUI_ContextPath }/sys/ui/extend/theme/default/style/icon.css?s_cache=${LUI_Cache}"/>
    <link type="text/css" rel="stylesheet"
          href="${LUI_ContextPath}/sys/modeling/base/relation/relation/css/relation.css?s_cache=${LUI_Cache}"/>

    <link type="text/css" rel="stylesheet"
          href="${KMSS_Parameter_ContextPath}sys/modeling/base/resources/css/modeling.css?s_cache=${LUI_Cache}"/>

</head>
<body class="lui_list_body" >


<div onclick="exp()">导出图片</div>
<div id="container" style="width:100%"></div>


<script>
   // //数据

   //

   //  const width = container.scrollWidth;
   //  const height = container.scrollHeight || 500;
   //  const graph = new G6Graph(container, width, height,"roundRect");
   //  graph.render(data,"tree");
   //
   //  function exp() {
   //      graph.downloadFullImage();
   //  }
   console.log("${result}");
   Com_IncludeFile("G6Graph.js", Com_Parameter.ContextPath + '/sys/modeling/base/views/business/show/mindMap/', 'js', true);

</script>
</body>
</html>
