<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>

<template:include ref="default.simple4list">
    <template:replace name="title">
        <c:out value="${ lfn:message('fssc-ctrip:module.fssc.ctrip') }-${ lfn:message('fssc-ctrip:table.fsscCtripCountry') }" />
    </template:replace>
    <template:replace name="path">
        <ui:menu layout="sys.ui.menu.nav">
            <ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home" href="/" target="_top" />
            <ui:menu-item text="${ lfn:message('fssc-ctrip:table.fsscCtripCountry') }" />
        </ui:menu>
    </template:replace>
    <template:replace name="head">
    	<script>
    	seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function($, dialog,topic) {
    		//初始化国家数据 
        	window.initCountryData=function() {
        		 var del_load = dialog.loading();
        		 var param = {};
                 $.ajax({
                     url: '${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do?method=initCountryData',
                     data: $.param(param, true),
                     dataType: 'json',
                     type: 'POST',
                     success: function(data) {
                         if(del_load != null) {
                             del_load.hide();
                             topic.publish("list.refresh");
                         }
                         dialog.result(data);
                     },
                     error: function(req) {
                         if(req.responseJSON) {
                             var data = req.responseJSON;
                             dialog.failure(data.title);
                         } else {
                             dialog.failure('${lfn:message("return.optFailure")}');
                         }
                         del_load.hide();
                     }
                 });
            }
    		//根据勾选的国家初始化国家的城市信息
        	window.initCityData=function() {
                var selected = [];
                $("input[name='List_Selected']:checked").each(function() {
                    selected.push($(this).val());
                });
                if(selected.length == 0) {
                    dialog.alert('${lfn:message("fssc-ctrip:fssc.ctrip.city.before.country.tips")}');
                    return;
                }
                var del_load = dialog.loading();
                var param = {
                    "List_Selected": selected
                };
                $.ajax({
                    url: '${LUI_ContextPath}/fssc/ctrip/fssc_ctrip_city/fsscCtripCity.do?method=initCityData',
                    data: $.param(param, true),
                    dataType: 'json',
                    type: 'POST',
                    success: function(data) {
                        if(del_load != null) {
                            del_load.hide();
                            topic.publish("list.refresh");
                        }
                        dialog.result(data);
                    },
                    error: function(req) {
                        if(req.responseJSON) {
                            var data = req.responseJSON;
                            dialog.failure(data.title);
                        } else {
                            dialog.failure('${lfn:message("return.optFailure")}');
                        }
                        del_load.hide();
                    }
                });
            }
    	});
    	</script>
    </template:replace>
    <template:replace name="nav">
        <ui:combin ref="menu.nav.create">
            <ui:varParam name="title" value="${ lfn:message('fssc-ctrip:table.fsscCtripCountry') }" />
            <ui:varParam name="button">
                [ {"text": "","href": "javascript:void(0);","icon": "/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do"} ]
            </ui:varParam>
        </ui:combin>
        <div class="lui_list_nav_frame">
            <ui:accordionpanel>
                <c:if test="${param.j_iframe=='true'}">
                    <c:set var="j_iframe" value="?j_iframe=true" />
                </c:if>

                <ui:content title="${ lfn:message('list.search') }">
                    <ul class='lui_list_nav_list'>

                        <li><a href="javascript:void(0)" onclick="openQuery();LUI('criteria1').clearValue();">${ lfn:message('list.alldoc') }</a>
                        </li>
                    </ul>
                </ui:content>

                <ui:content title="${ lfn:message('list.otherOpt') }">
                    
                </ui:content>
            </ui:accordionpanel>
        </div>
    </template:replace>
    <template:replace name="content">
        <div style="margin:5px 10px;">
			 <!-- 筛选 -->
            <list:criteria id="criteria1">
                <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('fssc-ctrip:fsscCtripCountry.fdName')}" />
                 <list:cri-auto expand="true" modelName="com.landray.kmss.fssc.ctrip.model.FsscCtripCountry" property="fdNameEn" />

            </list:criteria>
            <!-- 操作 -->
            <div class="lui_list_operation">

                <div style="float:left;">
                    <list:paging layout="sys.ui.paging.top" />
                </div>
                <div style="float:right">
                    <div style="display: inline-block;vertical-align: middle;">
                        <ui:toolbar count="3">
                            <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do?method=add">
                                <ui:button text="${lfn:message('fssc-ctrip:button.init.country')}" onclick="initCountryData()" order="1" id="btnDelete" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do?method=add">
                                <ui:button text="${lfn:message('fssc-ctrip:button.init.city')}" onclick="initCityData()" order="2" id="btnDelete" />
                            </kmss:auth>
                            <kmss:auth requestURL="/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do?method=deleteall">
                                <c:set var="canDelete" value="true" />
                            </kmss:auth>
                            <!---->
                            <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="3" id="btnDelete" />
                        </ui:toolbar>
                    </div>
                </div>
            </div>
            <ui:fixed elem=".lui_list_operation" />
            <!-- 列表 -->
            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {url:appendQueryParameter('/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do?method=data')}
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" rowHref="/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do?method=view&fdId=!{fdId}" name="columntable">
                    <list:col-checkbox />
                    <list:col-serial/>
                    <list:col-auto props="fdCountryId;fdName;fdNameEn" url="" /></list:colTable>
            </list:listview>
            <!-- 翻页 -->
            <list:paging />
        </div>
        <script>
            var listOption = {
                contextPath: '${LUI_ContextPath}',
                jPath: 'country',
                modelName: 'com.landray.kmss.fssc.ctrip.model.FsscCtripCountry',
                templateName: '',
                basePath: '/fssc/ctrip/fssc_ctrip_country/fsscCtripCountry.do',
                canDelete: '${canDelete}',
                mode: '',
                templateService: '',
                templateAlert: '${lfn:message("fssc-ctrip:treeModel.alert.templateAlert")}',
                customOpts: {

                    ____fork__: 0
                },
                lang: {
                    noSelect: '${lfn:message("page.noSelect")}',
                    comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                }

            };
            Com_IncludeFile("list.js", "${LUI_ContextPath}/fssc/ctrip/resource/js/", 'js', true);
        </script>
    </template:replace>
</template:include>
