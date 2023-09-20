<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/eop/basedata/resource/jsp/jshead.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<!DOCTYPE html>
<html>

<head>
    <title>个人借款统计</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta http-equiv="x-dns-prefetch-control" content="on">
    <meta name="format-detection" content="telephone=no">
    <link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/eop/basedata/portlet/fssc_work_portlet/css/css.css">
    <link charset="utf-8" rel="stylesheet" href="${LUI_ContextPath}/eop/basedata/portlet/fssc_work_portlet/css/listview_.css"> <!-- 这里请使用变量引入当前主题包的css -->
    <script>
        Com_IncludeFile("main_edit.js", "${LUI_ContextPath}/fssc/loan/resource/js/", 'js', true);
        Com_IncludeFile("form_option.js", "${LUI_ContextPath}/fssc/loan/fssc_loan_main/", 'js', true);
        Com_IncludeFile("doclist.js|dialog.js|calendar.js|jquery.js|validation.js");
        Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
        Com_IncludeFile("plugin.js");
    </script>
    <script>
        var formInitData={
            "LUI_ContextPath":"${LUI_ContextPath}",
        }
        function searchList(){
            Com_SubmitNoEnabled(document.fsscLoanMainForm, 'loanSearchList');
        }

        $(document).ready(function(){
            //加载台账数据
            var fdLoanPersonId= "<%=com.landray.kmss.util.UserUtil.getUser().getFdId()%>";
            var isAdmin= "<%=com.landray.kmss.util.UserUtil.getKMSSUser().isAdmin()%>";
            if(isAdmin=='false'){
                $("[name='fdLoanPersonId']").val(fdLoanPersonId);
            }
            Com_SubmitNoEnabled(document.fsscLoanMainForm, 'loanSearchList');
        });
    </script>
</head>

<body>
<div class="demo_fssc_work">

    <!-- 统计数据 -->
    <div class="demo_fssc_work_content">
        <div class="demo_fssc_work_content_wrap" id="demoFsscWorkContent01" style="display: block;">
            <div class="demo_fssc_work_task">
                <div class="demo_fssc_work_panel_nav">
                    <div class="demo_fssc_work_panel_title">
                        <span>个人借款统计</span>
                    </div>
                </div>

                <div class="demo_fssc_work_panel" id="demoFsscWorkTask01">
                    <div class="demo_fssc_work_panel_content">
                        <div class="demo_fssc_work_task_total">
                            <div class="demo_fssc_work_task_total_num">
                                <span>0</span>
                            </div>
                            <div class="demo_fssc_work_task_total_sub">
                                <span>借款</span>
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
                        <span>已还</span>
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
                            <span>未还</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="demo_fssc_work_datalist">
        <div class="demo_fssc_work_panel_nav">
            <div class="demo_fssc_work_panel_title">
                <span>借款台账</span>
            </div>
        </div>
    </div>

    <html:form action="/fssc/loan/fssc_loan_main/fsscLoanMain.do" method="get"  target="searchIframe">
        <input type="hidden" name="method" value="personLoanSearchList"/>
    </html:form>
    <!-- 列表数据 -->
    <iframe src="" name="searchIframe" id="searchIframe" align="top"  style="margin-left:1%" onload="this.height=searchIframe.document.body.scrollHeight" width="98%"  Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No>
    </iframe>
</div>
<script src="${LUI_ContextPath}/resource/js/jquery.js"></script>
<script src="${LUI_ContextPath}/sys/ui/js/chart/echarts/echarts4.2.1.js"></script>

<!-- 本页面的js -->
<script src="js/index.js"></script>
</body>

</html>
