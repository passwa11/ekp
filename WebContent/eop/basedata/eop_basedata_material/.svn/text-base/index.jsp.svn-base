<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@page import="com.landray.kmss.eop.basedata.util.EopBasedataUtil" %>
    <template:include ref="config.list">
    	<template:replace name="path">
			<span class="txtlistpath">
				<div class="lui_icon_s lui_icon_s_home" style="float: left;"></div>
				<div style="float: left;margin:5px 10px;">
					<bean:message key="page.curPath" /><%=java.net.URLDecoder.decode(request.getParameter("s_path"), "utf-8")%>
				</div></span>
	    </template:replace>
        <template:replace name="content">
            <div style="margin:5px 10px;">
                <!-- 筛选 -->
                <list:criteria id="criteria1">
                    <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('eop-basedata:eopBasedataMaterial.fdName')}" />
                    <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataMaterial" property="fdCode" />
                    <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataMaterial" property="fdStatus" />
                    <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataMaterial" property="docCreator" />
                    <list:cri-auto modelName="com.landray.kmss.eop.basedata.model.EopBasedataMaterial" property="docCreateTime" />

                </list:criteria>
                <!-- 操作 -->
                <div class="lui_list_operation">

                    <div style='color: #979797;float: left;padding-top:1px;'>
                        ${ lfn:message('list.orderType') }：
                    </div>
                    <div style="float:left">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                                <list:sort property="eopBasedataMaterial.docCreateTime" text="${lfn:message('eop-basedata:eopBasedataMaterial.docCreateTime')}" group="sort.list" />
                            </ui:toolbar>
                        </div>
                    </div>
                    <div style="float:left;">
                        <list:paging layout="sys.ui.paging.top" />
                    </div>
                    <div style="float:right">
                        <div style="display: inline-block;vertical-align: middle;">
                            <ui:toolbar count="3">
								<ui:button text="${lfn:message('eop-basedata:eopBasedata.button.showTree')}" onclick="switchPublicTree()" order="1" />
                                <kmss:auth requestURL="/eop/basedata/eop_basedata_material/eopBasedataMaterial.do?method=add">
                                    <ui:button text="${lfn:message('button.add')}" onclick="addDoc()" order="2" />
                                </kmss:auth>
                                <kmss:auth requestURL="/eop/basedata/eop_basedata_material/eopBasedataMaterial.do?method=deleteall">
                                    <c:set var="canDelete" value="true" />
                                </kmss:auth>
                                <!---->
                                <ui:button text="${lfn:message('button.import')}" onclick="importMaterial()" order="3" id="btnImport" />
                                <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                                <kmss:auth requestURL="/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.eop.basedata.model.EopBasedataMaterial">
                                    <ui:button text="${lfn:message('button.export')}" id="export" onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.eop.basedata.model.EopBasedataMaterial')">
                                    </ui:button>
                                </kmss:auth>

                            </ui:toolbar>
                        </div>
                    </div>
                </div>
                <ui:fixed elem=".lui_list_operation" />
                <!-- 列表 -->
                <list:listview id="listview">
                    <ui:source type="AjaxJson">
                        {url:appendQueryParameter('/eop/basedata/eop_basedata_material/eopBasedataMaterial.do?method=data')}
                    </ui:source>
                    <!-- 列表视图 -->
                    <list:colTable isDefault="false" rowHref="/eop/basedata/eop_basedata_material/eopBasedataMaterial.do?method=view&fdId=!{fdId}" name="columntable">
                        <list:col-checkbox />
                        <list:col-serial/>
                        <list:col-auto props="fdName;fdCode;fdSpecs;fdType.name;fdUnit.name;docCreateTime;fdStatus.name" url="" /></list:colTable>
                </list:listview>
                <!-- 翻页 -->
                <list:paging />
                <c:import url="/eop/basedata/resource/jsp/material_import.jsp">
                </c:import>
            </div>
            <script>
                var listOption = {
                    contextPath: '${LUI_ContextPath}',
                    modelName: 'com.landray.kmss.eop.basedata.model.EopBasedataMaterial',
                    templateName: '',
                    basePath: '/eop/basedata/eop_basedata_material/eopBasedataMaterial.do',
                    codePath: '/eop/basedata/eop_basedata_mate_code/eopBasedataMateCode.do',
                    canDelete: '${canDelete}',
                    mode: '',
                    templateService: '',
                    templateAlert: '${lfn:message("eop-basedata:treeModel.alert.templateAlert")}',
                    customOpts: {

                        ____fork__: 0
                    },
                    lang: {
                        noSelect: '${lfn:message("page.noSelect")}',
                        comfirmDelete: '${lfn:message("page.comfirmDelete")}',
                        codeIsNotExist: '${lfn:message("eop-basedata:code.is.not.exist")}',
                    }

                };
                Com_IncludeFile("list.js", "${LUI_ContextPath}/eop/basedata/resource/js/", 'js', true);

                var elecimporter = null;
                var importMaterial = function() {
                    if (elecimporter != null) {
                        elecimporter.openImporter();
                    }
                }

                top.window.showPraiseIframe = true;
                function importExcel() {
                    if (top.window.showPraiseIframe) {
                        seajs
                            .use(
                                [ 'lui/dialog' ],
                                function(dialog) {
                                    var fdUrl = "/eop/basedata/eop_basedata_material/eopBasedataMaterial_importDoc.jsp";
                                    dialog
                                        .iframe(
                                            fdUrl,
                                            "物料导入",
                                            changetInfo, {
                                                width : 800,
                                                height : 500
                                            });
                                });
                    }};

                function changetInfo() {
                    top.window.showPraiseIframe = true;
                }
                window.switchPublicTree = function(){
                	var s_path = Com_GetUrlParameter(window.location.href,"s_path");
            		if(s_path){
            			s_path=encodeURI(s_path);
            		}
                	var url = "${LUI_ContextPath}/eop/basedata/resource/jsp/publicTree.jsp?"+"&s_path="+s_path+"&modelName="+listOption.modelName
                	window.location.href = url;
                }
            </script>
        </template:replace>
    </template:include>
