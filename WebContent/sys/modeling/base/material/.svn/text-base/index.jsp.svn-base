<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ include file="/sys/ui/jsp/common.jsp"%>

        <template:include ref="default.print" sidebar="no">
            <template:replace name="title">
                <c:out value="${ lfn:message('sys-modeling-base:module.sys.modeling') }-${ lfn:message('sys-modeling-base:modeling.material.library') }" />
            </template:replace>
            <template:replace name="content">
                <link rel="stylesheet" type="text/css" href="${LUI_ContextPath}/sys/modeling/base/material/source/material_main.css" />
                <script>
                    Com_IncludeFile('doclist.js|jquery.js|plugin.js');
                    Com_IncludeFile("material_main.js", "${LUI_ContextPath}/sys/modeling/base/material/source/", 'js', true);
                    var listOption = {
                        contextPath: '${LUI_ContextPath}',
                        jPath: 'material_main',
                        modelName: 'com.landray.kmss.sys.modeling.base.material.model.ModelingMaterialMain',
                        templateName: '',
                        basePath: '/sys/modeling/base/modelingMaterialMain.do',
                        canDelete: '${canDelete}',
                        mode: '',
                        templateService: '',
                        customOpts: {
                            ____fork__: 0
                        },
                        lang: {
                            noSelect: '${lfn:message("page.noSelect")}',
                            comfirmDelete: '${lfn:message("page.comfirmDelete")}'
                        }
                    };
                 
                </script>
                <style>
                </style>
                <!-- 筛选 -->
                <%--                        <list:criteria id="criteria_pic">--%>
                <%--                            <list:cri-ref key="fdTagsName" ref="criterion.sys.docSubject" title="${ lfn:message('sys-portal:sysPortalMaterialMain.search.help')}" />--%>
                <%--                            <list:cri-criterion title="类型" key="fdPcType">--%>
                <%--                                <list:box-select>--%>
                <%--                                    <list:item-select>--%>
                <%--                                        <ui:source type="Static">--%>
                <%--                                            [ {text:'内置', value:'01'}--%>
                <%--                                            ,{text:'自定义', value:'02'}--%>
                <%--                                            ]--%>
                <%--                                        </ui:source>--%>
                <%--                                    </list:item-select>--%>
                <%--                                </list:box-select>--%>
                <%--                            </list:cri-criterion>--%>
                <%--                        </list:criteria>--%>
                    <!-- 筛选器 -->
                    <list:criteria id="criteria_pic">
                        <list:cri-ref key="fdName" ref="criterion.sys.docSubject" title="${lfn:message('sys-modeling-base:modelingAppListview.fdName') }"></list:cri-ref>
                    </list:criteria>
                    <!-- 操作 -->
                    <div class="lui_list_operation lui_list_operation_pic">
                         <div style='float: left; padding-top: 1px;'>
                             <label style='color:#333333'><input type="checkbox"  name="selectAll" onclick="selectAll()">
                             ${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.selectAll')} &emsp;</label>
                         </div>
                        <div style='color: #979797; float: left; padding-top: 1px;'>
                            ${ lfn:message('list.orderType') }：</div>
                        <div style="float: left">
                            <div style="display: inline-block; vertical-align: middle;">
                                <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                                    <list:sort property="modelingMaterialMain.docCreateTime" text="${lfn:message('sys-modeling-base:modelingMaterialMain.docCreateTime')}" group="sort.list" value="down" />
                                </ui:toolbar>
                            </div>
                        </div>
                        <div style="float: left;">
                            <list:paging layout="sys.ui.paging.top" />
                        </div>
                        <div style="float: right">
                            <div style="display: inline-block; vertical-align: middle;">
                                <ui:toolbar count="3">
                                    <!-- 上传 -->
                                    <kmss:auth requestURL="/sys/modeling/base/modelingMaterialMain.do?method=add">
                                        <ui:button text="${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.upload')}" onclick="uploadDoc()" order="2" />
                                    </kmss:auth>
                                    <!-- 删除  批量  -->
                                    <kmss:auth requestURL="/sys/modeling/base/modelingMaterialMain.do?method=deleteall">
                                        <c:set var="canDelete" value="true" />
                                    </kmss:auth>
                                    <ui:button text="${lfn:message('button.deleteall')}" onclick="deleteAll()" order="4" id="btnDelete" />
                                </ui:toolbar>
                            </div>
                        </div>
                    </div>
                    <ui:fixed elem=".lui_list_operation_pic" />
                    <list:listview id="listview_pic">
                        <ui:source type="AjaxJson">
                            {url:'/sys/modeling/base/modelingMaterialMain.do?method=gridData'}
                        </ui:source>
                        <list:gridTable name="gridtable_pic" columnNum="3" gridHref="">
                            <list:row-template>
                                var XSSPattern = new RegExp("[$&(){}':,\\[\\].<>]","g");
                                grid['fdName']= grid['fdName'].replace(XSSPattern, '');
                                {$
                                <div class="lui_material_view_box lui_material_vbox_pic">
                                    <div class="lui_material_img">
                                        <img src="${LUI_ContextPath }{% grid['imageUrl']%}" alt="">
                                        <div class="lui_material_cover">
                                            <div class="lui_material_cover_preview">
                                                <span onclick="onPreview('{% grid['imageUrl']%}','{%grid['fdName']%}')"><em>${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.preview')}</em></span>
                                            </div>
                                            <div class="lui_material_cover_option">
                                                <span onclick="onEdit('{%grid['fdId']%}')">${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.edit')}</span>
                                                <span onclick="onDeleteItem('{% grid['fdId']%}')">${ lfn:message('sys-modeling-base:modelingMaterialMain.btn.del')}</span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="lui_material_text">
                                        <p class="lui_material_text_title">
                                            <input type="checkbox" value="{% grid['fdId']%}" name="List_Selected">
                                                <b> {%grid['fdName']%}</b>
                                        </p>
                                        <p class="lui_material_text_tags">{%grid['tags']%}</p>
                                    </div>
                                </div>
                                $}
                            </list:row-template>
                        </list:gridTable>
                    </list:listview>
                    <!-- 翻页 -->
                    <list:paging />
            </template:replace>
        </template:include>
        <script>
        var isAll = false;
        window.selectAll=function(){
			isAll = !isAll;
			if(isAll){
				//全选
				$("input[name='List_Selected']").each(function(idx,ele){
					$(ele).prop("checked",true)
				});
			}else{
				//清空
				$("input[name='List_Selected']").each(function(idx,ele){
					$(ele).prop("checked",false)
				});
			}
		}
        //素材库宽度未匹配页面宽度
        $(document).ready(function() {
            $(".lui_print_main_content").removeClass("lui_print_main_content");
        });
        </script>