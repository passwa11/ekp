<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <title>财务工作台</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="x-dns-prefetch-control" content="on">
    <meta name="format-detection" content="telephone=no">
    <link charset="utf-8" rel="stylesheet" href="css/css.css">
    <link charset="utf-8" rel="stylesheet" href="css/listview_.css"> <!-- 这里请使用变量引入当前主题包的css -->
    <script>
    	var formInitData={
    			"LUI_ContextPath":"${LUI_ContextPath}",
    	}
    </script>
</head>

<body>
    <div class="demo_fssc_work">
        <!-- 工作台角色选项卡 -->
        <div class="demo_fssc_work_nav">
            <div class="demo_fssc_work_nav_title">
                <span>工作台角色选择：</span>
            </div>
            <div class="demo_fssc_work_nav_tabs">
                <div class="demo_fssc_work_nav_tabs_item select" data-id="demoFsscWork01">
                    <div class="demo_fssc_work_nav_tabs_wrap">
                        <i class="demo_fssc_work_nav_icon01"></i>
                        <span>审单岗</span>
                    </div>
                </div>
                <div class="demo_fssc_work_nav_tabs_item" data-id="demoFsscWork02">
                    <div class="demo_fssc_work_nav_tabs_wrap">
                        <i class="demo_fssc_work_nav_icon02"></i>
                        <span>出纳岗</span>
                    </div>
                </div>
                <div class="demo_fssc_work_nav_tabs_item" data-id="demoFsscWork03">
                    <div class="demo_fssc_work_nav_tabs_wrap">
                        <i class="demo_fssc_work_nav_icon03"></i>
                        <span>记账岗</span>
                    </div>
                </div>
            </div>
        </div>

        <!-- 工作台容器 -->
        <!-- 审单工作台 -->
        <div class="demo_fssc_work_content">
            <div class="demo_fssc_work_content_wrap" id="demoFsscWorkContent01" style="display: block;">
                <div class="demo_fssc_work_task">
                    <div class="demo_fssc_work_panel_nav">
                        <div class="demo_fssc_work_panel_title">
                            <span>审单任务统计</span>
                        </div>
                    </div>

                    <div class="demo_fssc_work_panel" id="demoFsscWorkTask01">
                        <div class="demo_fssc_work_panel_content">
                            <div class="demo_fssc_work_task_total">
                                <div class="demo_fssc_work_task_total_num">
                                    <span>0</span>
                                </div>
                                <div class="demo_fssc_work_task_total_sub">
                                    <span>总待审单据</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 出纳工作台 -->
            <div class="demo_fssc_work_content_wrap" id="demoFsscWorkContent02">
                <div class="demo_fssc_work_task">
                    <div class="demo_fssc_work_panel_nav">
                        <div class="demo_fssc_work_panel_title">
                            <span>审单任务统计</span>
                        </div>
                    </div>
                </div>

                <div class="demo_fssc_work_panel" id="demoFsscWorkTask02">
                    <div class="demo_fssc_work_panel_content">
                        <div class="demo_fssc_work_task_total">
                            <div class="demo_fssc_work_task_total_num">
                                <span>0</span>
                            </div>
                            <div class="demo_fssc_work_task_total_sub">
                                <span>总待审单据</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 财务会计工作台 -->
        <div class="demo_fssc_work_content_wrap" id="demoFsscWorkContent03">
            <div class="demo_fssc_work_task">
                <div class="demo_fssc_work_panel_nav">
                    <div class="demo_fssc_work_panel_title">
                        <span>审单任务统计</span>
                    </div>
                </div>

                <div class="demo_fssc_work_panel" id="demoFsscWorkTask01">
                    <div class="demo_fssc_work_panel_content">
                        <div class="demo_fssc_work_task_total">
                            <div class="demo_fssc_work_task_total_num">
                                <span>0</span>
                            </div>
                            <div class="demo_fssc_work_task_total_sub">
                                <span>总待审单据</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="demo_fssc_work_datalist">
            <div class="demo_fssc_work_panel_nav">
                <div class="demo_fssc_work_panel_title">
                    <span>待处理</span>
                </div>
                <!-- <div class="demo_fssc_work_panel_btns">
                    <div class="demo_fssc_work_panel_btns_item">
                        <a href="#" target="_blank">批量审核</a>
                    </div>
                    <div class="demo_fssc_work_panel_btns_item">
                        <a href="#" target="_blank">批量审核</a>
                    </div>
                    <div class="demo_fssc_work_panel_btns_item">
                        <a href="#" target="_blank">批量审核</a>
                    </div>
                </div> -->
            </div>
            <div class="demo_fssc_work_panel">
                <div class="demo_fssc_datalist">
                    <div class="lui_listview_body">
                        <div class="lui_listview_centerL">
                            <div class="lui_listview_centerR">
                                <div class="lui_listview_centerC">
                                    <table width="100%" class="lui_listview_columntable_table"
                                        id="demoFsscWorkDatalist">
                                        <thead id="titleDiv" data-lui-mark="column.table.header">
                                            <tr>
                                                <th style="width:23px;"><input type="checkbox" name="List_all_Selected" value="all"></th>
                                                <th style="width:48px;">序号</th>
                                                <th style="width:30%;text-align: left;padding:0 8px;">标题</th>
                                                <th style="width:160px;">所属模块</th>
                                                <th style="width:160px;">分类模板</th>
                                                <th style="width:160px;text-align: left;">所属部门</th>
                                                <th style="width:140px;">申请人</th>
                                                <th style="width:160px;text-align: left;">申请时间</th>
                                                <th style="width:160px;text-align: right;">本币金额</th>
                                                <th style="width:140px;">状态</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div data-lui-type="lui/listview/paging!Paging" style="" id="lui-id-102" class="lui-component"
                            data-lui-cid="lui-id-102" data-lui-parse-init="107">
                            <div class="lui_paging_box">
                                <div class="lui_paging_header_left">
                                    <div class="lui_paging_header_right">
                                        <div class="lui_paging_header_centre"> </div>
                                    </div>
                                </div>
                                <div class="lui_paging_content_left">
                                    <div class="lui_paging_content_right">
                                        <div class="lui_paging_content_centre">
                                            <ul class="lui_paging_contentBox clearfloat">
                                            	<div id="pageDiv">
                                                </div>
                                                <div class="lui_paging_total_left">
                                                    <div class="lui_paging_total_right">
                                                        <div class="lui_paging_total_center">
                                                            <li class="lui_paging_mr10 lui_paging_ml10">
                                                                <span id="totalRowSize"> </span></li>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_paging_jump_left">
                                                    <div class="lui_paging_jump_right">
                                                        <div class="lui_paging_jump_center">
                                                            <li class="lui_paging_mr10"><span>到第<input type="text" name="pageno" data-lui-mark="paging.pageno" value="1">页</span>
                                                            </li>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_paging_amount_left">
                                                    <div class="lui_paging_amount_right">
                                                        <div class="lui_paging_amount_center">
                                                            <li> <span> 显示 <input type="text" name="rowsize" data-lui-mark="paging.amount" value="15"> 条
                                                                </span> </li>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="lui_paging_go_left">
                                                    <div class="lui_paging_go_right">
                                                        <div class="lui_paging_go_center">
                                                            <li class="lui_paging_ml10"><a class="lui_paging_btn"
                                                                    href="javascript:;" data-lui-mark="paging.jump"
                                                                    title="Go">Go</a></li>
                                                        </div>
                                                    </div>
                                                </div>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                                <div class="lui_paging_foot_left">
                                    <div class="lui_paging_foot_right">
                                        <div class="lui_paging_foot_centre"> </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script src="${LUI_ContextPath}/resource/js/jquery.js"></script>
    <script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>


    <!-- 本页面的js -->
    <script src="js/index.js"></script>
</body>

</html>
