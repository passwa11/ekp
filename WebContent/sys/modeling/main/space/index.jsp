<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ page import="com.landray.kmss.sys.modeling.base.space.service.IModelingAppSpaceService" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%
    IModelingAppSpaceService spaceService = (IModelingAppSpaceService) SpringBeanUtil.getBean("modelingAppSpaceService");
    JSONObject appInfo = new JSONObject();
    try {
        appInfo = spaceService.getCfgInfo(request);
    } catch (Exception e) {
        request.getRequestDispatcher("/resource/jsp/error.jsp").forward(request, response);
    }
%>
<template:include ref="config.profile.list" spa="true" rwd="true">
    <template:replace name="title"><%=appInfo.get("name") %>
    </template:replace>
    <template:replace name="head">
        <style>
            .space-content-row>div:not(:last-child){
                margin-right: 16px;
            }
            .space-content-row>div{
                border:1px solid #ddd;
                margin-bottom: 16px;
                border-radius: 4px;
                overflow:hidden;
            }
            .space-data-listview-title{
                font-size: 14px;
                color: #333333;
                height: 42px;
                line-height:42px;
                background-color:#fff;
                border-bottom: 1px solid #ddd;
                padding: 0 6px;
                border-top-right-radius: 4px;
                border-top-left-radius: 4px;
            }
            .space-data-listview-content{
                border-bottom-left-radius:4px;
                border-bottom-right-radius:4px;
                overflow: hidden;
            }
            .space-body{
                background-color: #f7f7f7;
            }
            .statistics-content-value-num {
                display: inline-block;
                font-family: DINAlternate-Bold;
                font-size: 28px;
                color: #4285F4;
            }
            .statistics-content-value-unit, .statistics-content-value-percent {
                display: inline-block;
                font-size: 14px;
                color: #4285F4;
                margin-left: 4px;
            }
            .lui_profile_list_body .com_goto {
               display: none !important;
            }
        </style>
        <script type="text/javascript">
            // 动态模块的类型信息
            var __modelType = {
                "flow": {
                    "modelName": "com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain",
                    "actionUrl": "/sys/modeling/main/modelingAppModelMain.do",
                    isFlow: true
                },
                "noflow": {
                    "modelName": "com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain",
                    "actionUrl": "/sys/modeling/main/modelingAppSimpleMain.do",
                    isFlow: false
                }
            };
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/sys/modeling/main/resources/js/index.js"></script>
    </template:replace>
    <!-- 右侧内容区 -->
    <template:replace name="content">
        <div class="space-body" style="background:<%=appInfo.get("background") %>;background-size:<%=appInfo.get("background-size") %>">
            <div class="space-content">
               <c:forEach items='<%=appInfo.getJSONArray("rows") %>' var="row" varStatus="vstatus1">
                   <div class="space-content-row" style="display: flex;display: -webkit-flex;">
                       <c:forEach items='${row.getJSONArray("ports") }' var="port" varStatus="vstatus2">
                           <c:if test="${port.get('type') eq 'text'}">
                               <!--文本类-->
                               <div class="space-text" style="display:inline-block; flex: ${port.get("fdProportion")};height:${port.get("height")};background: ${port.get("background")};background-size:${port.get("background-size")}">
                                   <div class="space-text-content" style="height:${port.get("height")};color:${port.get("color")};font-style:${port.get("font-style")};font-weight:${port.get("font-weight")};text-align:${port.get("text-align")};
                                           text-decoration:${port.get("text-decoration")};font-family:${port.get("font-family")}">
                                       <c:if test="${port.get('isOpenLink')}">
                                           <a href="${LUI_ContextPath}${port.get('link')}" target="_blank" style="display: grid;align-items: center;width:100%;height:${port.get("height")};overflow:${port.get("overflow")};color:${port.get("color")};font-size:${port.get("font-size")};font-style:${port.get("font-style")};font-weight:${port.get("font-weight")};text-align:${port.get("text-align")};
                                                   text-decoration:${port.get("text-decoration")};font-family:${port.get("font-family")}">
                                                   ${port.get("text")}
                                           </a>
                                       </c:if>
                                       <c:if test="${port.get('isOpenLink') ne true}">
                                           <div class="space-text" style="display: grid;align-items: center;height:${port.get("height")};color:${port.get("color")};font-size:${port.get("font-size")};font-style:${port.get("font-style")};font-weight:${port.get("font-weight")};text-align:${port.get("text-align")};
                                                   text-decoration:${port.get("text-decoration")};font-family:${port.get("font-family")};overflow:${port.get("overflow")};">
                                            ${port.get("text")}
                                           </div>
                                       </c:if>
                                   </div>
                               </div>
                           </c:if>
                           <c:if test="${port.get('type') eq 'picture'}">
                               <!--图片类-->
                               <div class="space-picture" style="display:inline-block; flex: ${port.get("fdProportion")};height:${port.get("height")};">
                                      <c:if test="${port.get('isOpenLink')}">
                                          <a href="${LUI_ContextPath}${port.get('link')}" target="_blank" style="width:100%;height:${port.get("height")};">
                                              <div class="space-picture-content" style="height: 100%;width:100%;background: ${port.get("background")};background-size:${port.get("background-size")}"></div>
                                          </a>
                                      </c:if>
                                      <c:if test="${port.get('isOpenLink') ne true}">
                                          <div class="space-picture-content" style="height: 100%;width:100%;background: ${port.get("background")};background-size:${port.get("background-size")} "></div>
                                      </c:if>
                               </div>
                           </c:if>
                           <c:if test="${port.get('type') eq 'number'}">
                               <!--数字类-->
                               <div class="space-number" style="display:inline-block; flex: ${port.get("fdProportion")};height:${port.get("height")};background: ${port.get("background")};">
                                   <div class="space-data-listview-title" style="color:${port.get("color")}">
                                           ${port.get("title")}
                                   </div>
                                   <div class="space-number-content" style="height: ${port.get("viewHeight")}px">
                                       <div style="display: flex;justify-content: center;align-items: center;height: ${port.get("viewHeight")}px">
                                           <div class="statistics-content-value-num">${port.get("number")}</div>
                                           <div class="statistics-content-value-unit">${port.get("unit")}</div>
                                       </div>
                                   </div>
                               </div>
                           </c:if>
                           <c:if test="${port.get('type') eq 'view'}">
                               <!--视图类-->
                               <div class="space-data-listview" style="display:inline-block; flex: ${port.get("fdProportion")};height:${port.get("height")};background: ${port.get("background")};">
                                   <div class="space-data-listview-title" style="color:${port.get("color")}">
                                           ${port.get("title")}
                                   </div>
                                   <div class="space-data-listview-content" style="height: ${port.get("viewHeight")}px">
                                       <iframe style="width:100%;height: 100%"
                                               class="lui_modeling_iframe_body"  frameborder="no" border="0"
                                               src="${LUI_ContextPath}${port.get("url")}">
                                       </iframe>
                                   </div>
                               </div>
                           </c:if>
                           <c:if test="${port.get('viewNone') eq true}">
                               <!--缺省页-->
                               <div class="space-data-listview" style="display:inline-block; flex: ${port.get("fdProportion")};height:${port.get("height")};background: ${port.get("background")};">
                                   <div class="space-data-listview-title" style="color:${port.get("color")}">
                                           ${port.get("title")}
                                   </div>
                                   <div class="space-data-listview-content" style="height: ${port.get("viewHeight")}px">
                                       <iframe style="width:100%;height: 100%"
                                               class="lui_modeling_iframe_body"  frameborder="no" border="0"
                                               src="${LUI_ContextPath}${port.get("noViewUrl")}">
                                       </iframe>
                                   </div>
                               </div>
                           </c:if>
                           <c:if test="${port.get('type') eq 'portlet' and port.get('viewNone') ne true }">
                               <!--门户部件-->
                               <!--以下出现的数字含义：1-简单列表；2-图文摘要；3-图片宫格；4-幻灯片；5-列表视图；6-时间轴列表-->
                               <div class="space-data-portlet" style="display:inline-block; flex: ${port.get("fdProportion")};height:${port.get("height")};background: ${port.get("background")};">
                                   <div class="space-data-listview-title" style="color:${port.get("color")}">
                                           ${port.get("title")}
                                   </div>
                                   <div calss="space-data-portlet-content">
                                       <ui:panel layout="sys.ui.nonepanel.default" expand="true" toggle="false" height="${port.get('portletHeight')}" scroll="true" channel="space-data-portlet-${vstatus2.index}">
                                           <ui:content title="content">
                                               <ui:dataview format='${port.get("format")}'>
                                                   <c:if test="${port.get('portletType') ne '5'}">
                                                       <div data-lui-type="lui/data/source!AjaxXml" style="display:none;">
                                                           <script type="text/config">
                                                                {"vars": {"rowsize": '${port.get("rowsize")}'}}
                                                           </script>
                                                           <script type='text/code'>
                                                                {"url":"/sys/common/dataxml.jsp?s_bean=modelingPortletService&cfgId=${port.get("fdId")}&rowsize=!{rowsize}"}
                                                           </script>
                                                       </div>
                                                   </c:if>
                                                   <c:if test="${port.get('portletType') eq '5'}">
                                                       <!--列表视图-->
                                                       <div data-lui-type="lui/data/source!AjaxJson" style="display:none;">
                                                           <script type="text/config">
                                                            {"vars": {"rowsize": '${port.get("rowsize")}'}}
                                                            </script>
                                                            <script type='text/code'>
                                                            {"url":"/sys/modeling/main/modelingPortletCfg.do?method=listPortlet&cfgId=${port.get("fdId")}&rowsize=!{rowsize}"}
                                                            </script>
                                                       </div>
                                                   </c:if>
                                                   <c:if test="${port.get('portletType') ne '3'}">
                                                       <ui:render ref='${port.get("ref")}' var-position="${port.get('varPosition')}"
                                                                  var-size="${port.get('varsize')}" var-rate="45" var-stretch="true"
                                                                  var-showCreator="true" var-showCreated="true" var-showCate="true" var-cateSize="0" var-newDay="0"></ui:render>
                                                   </c:if>
                                                   <c:if test="${port.get('portletType') eq '3'}">
                                                       <!--图片宫格-->
                                                       <ui:render ref='${port.get("ref")}' var-position="${port.get('varPosition')}"
                                                                  var-size="${port.get('varsize')}" var-columnNum="${port.get('varColumn')}" var-rate="45" var-stretch="true"
                                                                  var-showCreator="true" var-showCreated="true" var-showCate="true" var-cateSize="0" var-newDay="0"></ui:render>
                                                   </c:if>
                                                   <ui:var name="showNoDataTip" value="true"></ui:var>
                                                   <ui:var name="showErrorTip" value="true"></ui:var>
                                               </ui:dataview>
                                               <ui:operation href="/sys/modeling/base/modelingPortletCfg.do?method=goMore&amp;cfgId=${port.get('fdId')}" name="更多" type="more" align="right"></ui:operation>
                                               <ui:operation href="javascript:(function(){seajs.use(['sys/modeling/base/portlet/js/portlet'], function(portlet) {portlet.addDoc('${port.get(\"mainId\")}','${port.get(\"isFlow\")}');});})();" name="新建" target="_self" type="create" align="right"></ui:operation>
                                           </ui:content>
                                       </ui:panel>
                                   </div>
                               </div>
                           </c:if>
                       </c:forEach>
                   </div>
               </c:forEach>
            </div>
        </div>
        <script type="text/javascript">
            //解决在业务导航中点击浏览器刷新时出现图片不渲染的问题，或渲染的图片大小问题。（iframe下刷新门户）
            setTimeout(function () {
                seajs.use(['lui/jquery'], function($) {
                    $('[data-lui-type="lui/panel!Panel"]').each(function(i, n) {
                        if(LUI(n.id)){
                            LUI(n.id).resize();
                        }
                    });
                });
            }, 500);
        </script>
    </template:replace>

</template:include>