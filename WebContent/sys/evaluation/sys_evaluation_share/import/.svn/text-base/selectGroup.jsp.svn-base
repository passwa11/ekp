<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple">
    <template:replace name="body">
        <script>
            seajs.use(["theme!list", "sys/profile/resource/css/operations.css#"]);
        </script>
        <div style="padding:8px;">
            <input name="refreshNum" value="0" type="hidden"/>
            <input name="toggle" value="0" type="hidden"/>
            <!-- 查询条件  -->
            <list:criteria id="criteria1" cfg-isSetToHash="false">
                <list:cri-ref key="docSubject" ref="criterion.sys.docSubject" title="${ lfn:message('sns-group:snsGroupMain.fdSubject') }"/>
                <list:cri-ref key="fdModerator" ref="criterion.sys.person" title="${ lfn:message('sns-group:snsGroupMain.fdModerator') }"/>
                <list:cri-auto
                        modelName="com.landray.kmss.sns.group.model.SnsGroupMain"
                        property="docCategory" />
                <list:cri-auto
                        modelName="com.landray.kmss.sns.group.model.SnsGroupMain"
                        property="fdPublishTime" />
            </list:criteria>
                <%-- 多选选中组件 --%>
            <div id="selectedBean" data-lui-type="lui/selected/multi_selected!Selected" style="width: 95%;margin: 10px auto;">
                <script type="lui/event" data-lui-event="changed" data-lui-args="evt">
			        refreshCheckbox();
                </script>
            </div>

            <!-- 列表工具栏 -->
            <div class="lui_list_operation">
                <div class="lui_list_operation_order_btn">
                    <list:selectall></list:selectall>
                </div>
                <!-- 分割线 -->
                <div class="lui_list_operation_line"></div>

                <div class="lui_list_operation_sort_btn">
                    <div class="lui_list_operation_order_text">${ lfn:message('list.orderType') }：</div>
                    <div class="lui_list_operation_sort_toolbar">
                        <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                            <list:sort property="fdCreateTime"
                                       text="${lfn:message('sns-group:snsGroupMain.fdCreateTime') }"
                                       group="sort.list" value="down"></list:sort>
                            <list:sort property="fdPublishTime"
                                       text="${lfn:message('sns-group:snsGroupMain.fdPublishTime') }"
                                       group="sort.list" value="down"></list:sort>
                        </ui:toolbar>
                    </div>
                </div>

                <div class="lui_list_operation_page_top">
                    <list:paging layout="sys.ui.paging.top">
                    </list:paging>
                </div>
            </div>

            <ui:fixed elem=".lui_list_operation"></ui:fixed>

            <list:listview id="listview">
                <ui:source type="AjaxJson">
                    {
                    url:'/sns/group/sns_group_main/snsGroupMain.do?method=data&q.fdStatus=1'
                    }
                </ui:source>
                <!-- 列表视图 -->
                <list:colTable isDefault="false" name="columntable"
                               rowHref="/sns/group/sns_group_main/snsGroupMain.do?method=view&fdId=!{fdId}">
                    <list:col-checkbox></list:col-checkbox>
                    <list:col-serial></list:col-serial>
                    <list:col-html
                            title="${ lfn:message('sns-group:snsGroupMain.fdSubject')}"
                            headerStyle="width:35%">
                        {$
                        {%row['icon']%}
                        <span class="com_subject">{%row['docSubject']%}</span>
                        $}
                    </list:col-html>
                    <list:col-auto props="fdModerator.fdName"></list:col-auto>
                    <list:col-auto props="fdCreateTime"></list:col-auto>
                    <list:col-auto props="fdPublishTime"></list:col-auto>
                </list:colTable>
                <ui:event topic="list.loaded" args="vt">
                    $("[name='List_Tongle']").css("display","none");

                    refreshCheckbox();

                    //每一行的数据
                    var datas = vt.table.kvData;
                    function getVal(id) {
                    for (var i = 0; i < datas.length; i ++) {
                    if (datas[i].fdId == id) {
                    return datas[i];
                    }
                    }
                    return null;
                    }

                    //全选
                    LUI.$('.lui_listview_selectall input').bind('click', function() {
                    $("[name='toggle']").val("1"); //设置flag值是因为removeValAll()会触发refreshCheckbox(),这样会导致全选框按钮被提前设置false，数据紊乱
                    LUI('selectedBean').removeValAll();
                    if (this.checked) {
                    //LUI('selectedBean').addValAll(datas);
                    //不能直接用该方法原因为name和fdName的名字不同
                    for (var i = 0; i < datas.length; i ++) {
                    selectLink(datas[i].fdId, datas[i].docSubject);

                    }

                    }
                    $("[name='toggle']").val("0");
                    });
                    LUI.$('#listview .lui_listview_columntable_table tr').bind('click', function() { //点击列表某一项

                    $(this).find("input[name='List_Selected']").each(function(){
                    if ($(this).is(':checked')) {
                    this.checked = false;
                    LUI('selectedBean').removeVal(this.value);
                    } else {
                    this.checked = true;
                    var values = LUI('selectedBean').getValues();
                    if(values.length>0&&!${param.mulSelect}){
                    LUI('selectedBean').removeValAll();
                    }
                    var val = getVal($(this).val());
                    if (val != null)
                    selectLink(val.fdId, val.docSubject)
                    }
                    });

                    });

                    LUI.$('#listview [name="List_Selected"]').bind('click', function() { //点击列表某一项的checkbox
                    if (!this.checked) {
                    LUI('selectedBean').removeVal(this.value);
                    } else {
                    var values = LUI('selectedBean').getValues();
                    if(values.length>0&&!${param.mulSelect}){
                    LUI('selectedBean').removeValAll();
                    }
                    var val = getVal(this.value);
                    if (val != null)
                    selectLink(val.fdId, val.docSubject);
                    }
                    });

                    $('#listview [name="List_Selected"]').each(function(){
                    var val = getVal(this.value);
                    var selectedIds = "${JsParam.fdSelectedIds}";
                    if(selectedIds.indexOf(this.value) > -1){
                    selectLink(val.fdId, val.docSubject);
                    }
                    });

                    //首次刷新列表加载链接带入数据
                    var refreshNum = $("[name='refreshNum']").val(); //此处放在列表刷新而不放在 $(function(){})中是因为selectedBean在$(function(){})运行时还没有加载出来
                    if(refreshNum.indexOf("0")>-1){
                    var fdId = top.window.selectLearnMainId;
                    var docSubject = top.window.selectLearnMainSubject;
                    if(fdId){
                    var id = fdId.split(";");
                    var name = docSubject.split(";");
                    for(var i=0;i<id.length;i++){
                        selectLink(id[i],name[i]);
                    }
                    }
                    $("[name='refreshNum']").val("1");
                    }

                </ui:event>
            </list:listview>

            <list:paging></list:paging>
        </div>
        <script>
            seajs.use(['lui/jquery', 'lui/dialog'], function ($, dialog) {
                window.refreshCheckbox = function () {
                    var vals = LUI('selectedBean').getValues();
                    LUI.$('[name="List_Selected"]').each(function () {
                        for (var i = 0; i < vals.length; i++) {
                            if (vals[i].id == this.value) {
                                if (!this.checked)
                                    this.checked = true;
                                return;
                            }
                        }
                        if (this.checked)
                            this.checked = false;
                    });
                    var toggle = $("[name='toggle']").val();
                    if ($('.lui_listview_selectall input').is(':checked') && (toggle.indexOf("0") > -1)) {
                        $('.lui_listview_selectall input').attr('checked', false);
                    }
                };

                var contains = function (arr, item) {
                    for (var i = 0; i < arr.length; i++) {
                        if (item.value == arr[i].fdId) {
                            return arr[i];
                        }
                    }
                };

                window.onSubmit = function () {

                    var values = LUI('selectedBean').getValues();
                    if (values.length > 1 && !${param.mulSelect}) {
                        dialog.alert("${ lfn:message('sys-evaluation:sysEvaluationShareProducer.selectExcessMain')}");
                        return false;
                    } else if (values.length < 1) {
                        dialog.alert("${ lfn:message('sys-evaluation:sysEvaluationShareProducer.selectNoMain')}");
                        return false;
                    }
                    var rtn = [];

                    //拼装docSubject和fdId
                    for (var i = 0; i < values.length; i++) {
                        var data = {fdId: "", docSubject: ""};
                        data.fdId = values[i].id;
                        data.docSubject = values[i].name;
                        rtn.push(data);
                    }

                    if (rtn.length > 0) {
                        return rtn;
                    }

                };

                <%-- 设置选中 --%>
                window.selectLink = function (id, name) {
                    var data = {
                        "id": id,
                        "name": name
                    };
                    if (LUI('selectedBean').hasVal(data)) {
                        LUI('selectedBean').removeVal(data);
                        return;
                    }
                    var values = LUI('selectedBean').getValues();
                    if (values.length > 1 && !${param.mulSelect}) {
                        dialog.alert("${ lfn:message('sys-evaluation:sysEvaluationShareProducer.selectExcessMain')}");
                        refreshCheckbox();
                        return;
                    }

                    LUI('selectedBean').addVal(data);
                    refreshCheckbox();
                }
            });

        </script>
    </template:replace>
</template:include>