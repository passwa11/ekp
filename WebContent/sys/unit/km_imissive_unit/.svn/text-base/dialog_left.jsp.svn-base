<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.landray.kmss.sys.unit.util.SysUnitUtil" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/sys/ui/jsp/jshead.jsp" %>
<style>
    .navbar_page > div {
        height: 420px !important;
    }

    .navbar_page {
        vertical-align: top;
        background-color: #fff !important;
    }
</style>
<%@ include file="/resource/jsp/nav_top.jsp" %>
Com_AddEventListener(window,'load',function(){
        var unitTree = top.dialogObject.tree;
        var dataUrlInfo = unitTree.treeRoot.parameter;
        var fdUnitId = Com_GetUrlParameter(dataUrlInfo, "fdUnitId");
        var showDec = Com_GetUrlParameter(dataUrlInfo, "showDec");
        var allUnitByDec = Com_GetUrlParameter(dataUrlInfo, "allUnitByDec");
        var navBarInfo = [];

        var narInfoArray = new Array(
            "<bean:message bundle="sys-unit" key="sysUnitDialog.cate"/>|dialog_cate_tree.jsp"
        );
        if (dataUrlInfo.indexOf("kmImissiveUnitUseTreeService") < 0) {
            narInfoArray.push("<bean:message bundle="sys-unit" key="sysUnitDialog.group"/>|dialog_group_tree.jsp");
        }

        <% if (SysUnitUtil.getExchangeEnable()) {%>
        if (showDec == 'true') {
            if (dataUrlInfo.indexOf("kmImissiveUnitUseTreeService") < 0) {
                narInfoArray.push("<bean:message bundle="sys-unit" key="sysUnitDialog.dec"/>|dialog_dec_tree.jsp");
            }
        } else if (allUnitByDec == 'true') {
            if (dataUrlInfo.indexOf("kmImissiveUnitUseTreeService") < 0) {
                narInfoArray.push("<bean:message bundle="sys-unit" key="sysUnitDialog.dec"/>|dialog_dec_tree.jsp?allUnitByDec=true");
            }
        }
        <% } %>

        if (fdUnitId) {
            seajs.use(['lui/jquery'], function ($) {
                var decFlag = false;
                var url = "${KMSS_Parameter_ContextPath}sys/unit/km_imissive_unit/kmImissiveUnit.do?method=checkDecUnit&fdUnitId=" + fdUnitId;
                $.ajax({
                    type: "post",
                    url: url,
                    async: true,
                    success: function (data) {
                        var results = eval("(" + data + ")");
                        if (results['decFlag'] == "true") {
                            decFlag = true;
                        }

                        <% if (SysUnitUtil.getExchangeEnable()) {%>
                        if (decFlag) {
                            if (dataUrlInfo.indexOf("kmImissiveUnitUseTreeService") < 0) {
                                narInfoArray.push("<bean:message bundle="sys-unit" key="sysUnitDialog.dec"/>|dialog_dec_tree.jsp");
                            }
                        }
                        <%} %>
                        navBarInfo = narInfoArray;
                        Nav_Draw(document.body, navBarInfo);
                    }
                });
            });
        } else {
            navBarInfo = narInfoArray;
            Nav_Draw(document.body, navBarInfo);
        }
})


</script>
</head>
<body>
</body>
</html>