<%@page import="java.util.Iterator"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
    Object _varParams = request.getAttribute("varParams");
    if (_varParams != null) {
        Map<String, Object> varParams = (Map) _varParams;
        // 扩展查询字段
        Object extProps = varParams.get("extProps");
        if (extProps != null) {
            JSONObject ___obj = JSONObject.fromObject(extProps);
            Iterator it = ___obj.keys();
            JSONArray array = new JSONArray();
            while (it.hasNext()) {
                Object key = it.next();
                array.add(("qq." + key + "="
                        + ___obj.getString(key.toString())).toString());
            }
            String ___extProps = StringUtil.join(array, "&");
            pageContext.setAttribute("extProps", "&" + ___extProps);
        }

        Object criProps = varParams.get("criProps");
        if (criProps == null) {
            pageContext.setAttribute("criProps", "");
        } else {
            pageContext.setAttribute("criProps", criProps);
        }
        Object isHasSearch = varParams.get("isHasSearch");
        Object isKeepCriParameter = varParams.get("isKeepCriParameter");
        if (isHasSearch == null) {
            pageContext.setAttribute("isHasSearch", false);
        } else if("true".equals(isHasSearch))
            pageContext.setAttribute("isHasSearch",true);
        else
            pageContext.setAttribute("isHasSearch", false);
        if (isKeepCriParameter == null) {
            pageContext.setAttribute("isKeepCriParameter", false);
        } else if("true".equals(isKeepCriParameter))
            pageContext.setAttribute("isKeepCriParameter",true);
        else
            pageContext.setAttribute("isKeepCriParameter", false);


    }
%>

<c:if test="${isHasSearch}">
    <div class="lui_dataview_search_wrap">
        <!-- 搜索条 Starts -->
        <div class="lui_search_bar">
            <div class="search_bar">
                <div class="search_input">
                    <input type="text" placeholder="${ lfn:message('kms-knowledge:kmsKnowledge.index.search.cat')}" id="nav_search" data-lui-mark="search.input"/>
                    <!-- 删除取消 -->
                    <i class="lui_icon_cancel" onclick="javascript:navCleanSearch();"></i>
                </div>
                <div class="search_btn">
                    <i class="lui_icon_search" onclick="javascript:navSearch();"></i>
                </div>
            </div>
        </div>

    </div>

    <script>
        function navCleanSearch(){
            $("#nav_search").val("");
        }
        function navSearch(){
            var navSearchName=$("#nav_search").val();
            if(navSearchName==""){
                return;
            }
            seajs.use('lui/topic',function(topic) {
                topic.publish("navSearchCategory");
            });
        }
    </script>
</c:if>

<ui:dataview>

    <ui:source type="AjaxJson">
        {"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=index&modelName=${varParams.modelName }&parentId=!{value}&level=4&expand=true&authType=2${extProps}"}
    </ui:source>

    <ui:render ref="sys.ui.cate.all"
               cfg-modelName="${varParams.modelName }" cfg-criProps="${criProps }" cfg-isHasSearch="${isHasSearch }" cfg-isKeepCriParameter="${isKeepCriParameter }">
    </ui:render>

</ui:dataview>
