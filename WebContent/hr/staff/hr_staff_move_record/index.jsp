<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" spa="true">
<style type='text/css'>
table td {
white-space: nowrap;
}
</style>
    <template:replace name="title">
        <c:out value="${ lfn:message('hr-staff:module.hr.staff') }-${ lfn:message('hr-staff:table.hrStaffMoveRecord') }" />
    </template:replace>
    <template:replace name="body">
        <script type="text/javascript">
            seajs.use(['theme!list']);
        </script>
        <script type="text/javascript" src="${LUI_ContextPath}/resource/js/jquery.js?s_cache=${LUI_Cache}"></script>
        <script type="text/javascript" src="${LUI_ContextPath}/resource/js/data.js?s_cache=${LUI_Cache}"></script>
        <!-- 筛选器 -->
        <list:criteria id="criteria">
            <list:cri-ref key="_fdKey" ref="criterion.sys.docSubject" title="${ lfn:message('hr-staff:hrStaffPersonInfo.criteria.fdKey1') }" style="width:300px;">
            </list:cri-ref>
            <list:cri-criterion title="${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus') }" key="_fdStatus" multi="true">
                <list:box-select>
                    <list:item-select cfg-defaultValue="official">
                        <ui:source type="Static">
                          	[{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.onpost') }',value:'onpost'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.official') }',value:'official'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trial') }', value:'trial'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.rehireAfterRetirement') }',value:'rehireAfterRetirement'},
<%-- 								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.trialDelay') }',value:'trialDelay'}, --%>
<%-- 								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'quit'}, --%>
<%-- 								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.dismissal') }',value:'dismissal'}, --%>
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.leave') }',value:'leave'},
								{text:'${ lfn:message('hr-staff:hrStaffPersonInfo.fdStatus.blacklist') }', value:'blacklist'}
								]
                        </ui:source>
                    </list:item-select>
                </list:box-select>
            </list:cri-criterion>
        </list:criteria>
        <!-- 排序 -->
        <div class="lui_list_operation">
            <!-- 全选 -->
            <div class="lui_list_operation_order_btn">
                <list:selectall></list:selectall>
            </div>
            <!-- 分割线 -->
            <div class="lui_list_operation_line"></div>
            <!-- 排序 -->
            <div class="lui_list_operation_sort_btn">
                <div class="lui_list_operation_order_text">
                        ${ lfn:message('list.orderType') }：
                </div>
                <div class="lui_list_operation_sort_toolbar">
                    <ui:toolbar layout="sys.ui.toolbar.sort" style="float:left">
                        <list:sortgroup>
                            <list:sort property="fdMoveDate" text="${lfn:message('hrStaffMoveRecord.fdMoveDate') }" group="sort.list" value="down"></list:sort>
                        </list:sortgroup>
                    </ui:toolbar>
                </div>
            </div>
            <!-- 分页 -->
            <div class="lui_list_operation_page_top">
                <list:paging layout="sys.ui.paging.top" >
                </list:paging>
            </div>
            <div style="float:right">
                <div style="display: inline-block;vertical-align: middle;">
                    <ui:toolbar count="5">
                    <kmss:authShow roles="ROLE_KMREVIEW_TRANSPORT_EXPORT">
									<ui:button
											text="${lfn:message('button.export')}" id="export"
											onclick="listExport('${LUI_ContextPath}/sys/transport/sys_transport_export/SysTransportExport.do?method=listExport&fdModelName=com.landray.kmss.hr.staff.model.HrStaffMoveRecord')"
											order="2" >
									</ui:button>
								</kmss:authShow> 
                        <ui:button text="${ lfn:message('hr-staff:hrStaffTrackRecord.button.batchImport')}" onclick="_modify();" order="2"></ui:button>
                        <ui:button text="${lfn:message('button.deleteall') }" onclick="_delete();" order="4"></ui:button>
                    </ui:toolbar>
                </div>
            </div>
        </div>
        <ui:fixed elem=".lui_list_operation"></ui:fixed>

        <!-- 列表 -->
        <list:listview id="listview">
            <ui:source type="AjaxJson">
                {url:'/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=data'}
            </ui:source>
            <!-- 列表视图 -->
            <!-- 列表视图 -->
            <list:colTable isDefault="false" rowHref="/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=view&fdId=!{fdId}" name="columntable">
                <list:col-checkbox />
                <list:col-serial/>
                <list:col-auto props="fdStaffNumber;fdStaffName;fdIsExplore;fdInternStartDate;fdInternEndDate;fdTransDept;fdMoveType.name;fdBeforeFirstDeptName;fdBeforeSecondDeptName;fdBeforeThirdDeptName;fdBeforeRank;fdBeforePosts;fdBeforeLeader.name;fdAfterFirstDeptName;fdAfterSecondDeptName;fdAfterThirdDeptName;fdAfterRank;fdAfterPosts;fdAfterLeader.name;fdMoveDate" url="" />
            </list:colTable>
        </list:listview>
        <list:paging></list:paging>
        <script type="text/javascript">
            seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
                // 监听新建更新等成功后刷新
                topic.subscribe('successReloadPage', function() {
                    setTimeout(function() {
                        seajs.use(['lui/topic'], function(topic) {
                            topic.publish('list.refresh');
                        });
                    }, 100);
                });

                // 模板下载
                window.download = function() {
                    $("#downloadTempletForm").submit();
                };

                // 导入
                window._modify = function() {
                    var actionUrl = "${LUI_ContextPath}/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=fileUpload";
                    var downLoadUrl = "${LUI_ContextPath}/hr/staff/hr_staff_move_record/hrStaffMoveRecord.do?method=downloadTemplet";
                    dialog.iframe('/hr/staff/upload_files/common_upload_download.jsp?uploadActionUrl=' + actionUrl + '&isRollBack=false'+'&downLoadUrl='+downLoadUrl,
                        "${ lfn:message('hr-staff:table.hrStaffMoveRecord')}", function(data) {
                            topic.publish('list.refresh');
                        }, {
                            width : 680,
                            height : 380
                        });
                };

                // 删除
                window._delete = function(id) {
                    var values = [];
                    if(id) {
                        values.push(id);
                    } else {
                        $("input[name='List_Selected']:checked").each(function() {
                            values.push($(this).val());
                        });
                    }
                    if(values.length==0){
                        dialog.alert('<bean:message key="page.noSelect"/>');
                        return;
                    }
                    dialog.confirm('<bean:message key="page.comfirmDelete"/>', function(value) {
                        if(value == true) {
                            window.del_load = dialog.loading();
                            $.ajax({
                                url : "${LUI_ContextPath}/hr/staff/hr_staff_person_track_record/hrStaffTrackRecord.do?method=deleteall",
                                type : 'POST',
                                data : $.param({"List_Selected" : values}, true),
                                dataType : 'json',
                                error : function(data) {
                                    if(window.del_load != null) {
                                        window.del_load.hide();
                                    }
                                    dialog.result(data.responseJSON);
                                },
                                success: function(data) {
                                    if(window.del_load != null){
                                        window.del_load.hide();
                                        topic.publish("list.refresh");
                                    }
                                    dialog.result(data);
                                }
                            });
                        }
                    });
                };
            });
        </script>
    </template:replace>
</template:include>
