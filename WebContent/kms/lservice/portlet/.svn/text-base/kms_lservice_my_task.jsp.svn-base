<%@ page import="com.landray.kmss.util.DateUtil" %>
<%@ page import="java.util.Date" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/sys/ui/jsp/common.jsp" %>
<%
    String type = request.getParameter("type");
%>
<style>
    .kmsLserviceMyTaskContainer {
        overflow: auto;
    }
    .kmsLserviceMyTaskContainer .item {
        border: 1px solid #F2F2F2;
        margin: 5px 10px 5px 0;
        background: #FFFFFF;
        border-radius: 6px;
        width: auto;
        height: 128px;
        float: none;
    }
    .kmsLserviceMyTaskContainer .item .top {
        width: 100%;
        height: 80px;
        margin: 0 auto;
        box-sizing: border-box;
    }
    .kmsLserviceMyTaskContainer .item .top .box {
        height: 40px;
        width: fit-content;
        margin: 0 auto;
        padding-top: 15px;
    }
    .kmsLserviceMyTaskContainer .item .top .box .number {
        font-size: 36px;
        color: #333333;
        margin-right: 6px;
        float: left;
        height: 50px;
        line-height: 50px;
    }
    .kmsLserviceMyTaskContainer .item .top .box .title {
        font-size: 14px;
        color: #999999;
        float: left;
        padding-top: 21px;
    }
    .kmsLserviceMyTaskContainer .item .bottom {
        width: 150px;
        height: 20px;
        margin: 0 auto;
        padding-top: 10px;
        border-top: 1px solid #F2F2F2;
        cursor: pointer;
    }
    .kmsLserviceMyTaskContainer .item .bottom .box {
        margin: 0 auto;
        width: fit-content;
    }
    .kmsLserviceMyTaskContainer .item .bottom .box:after {
        content: '';
        height: 0;
        line-height: 0;
        display: block;
        visibility: hidden;
        clear: both;
    }
    .kmsLserviceMyTaskContainer .item .bottom .icon {
        margin: 0px 3px 0 0;
        float: left;
        width: 20px;
        height: 20px;
    }
     .kmsLserviceMyTaskContainer .item .bottom .icon.learned {
         background: url("../../../kms/lservice/portlet/images/myTask/learn.svg") no-repeat 100% 100%;
     }
    .kmsLserviceMyTaskContainer .item .bottom .icon.acti {
        background: url("../../../kms/lservice/portlet/images/myTask/acti.svg") no-repeat 100% 100%;
    }
    .kmsLserviceMyTaskContainer .item .bottom .icon.exam {
        background: url("../../../kms/lservice/portlet/images/myTask/exam.svg") no-repeat 100% 100%;
    }
    .kmsLserviceMyTaskContainer .item .bottom .icon.lmap {
        background: url("../../../kms/lservice/portlet/images/myTask/lmap.svg") no-repeat 100% 100%;
    }
    .kmsLserviceMyTaskContainer .item .bottom .icon.train {
        background: url("../../../kms/lservice/portlet/images/myTask/tcourse.svg") no-repeat 100% 100%;
    }
    .kmsLserviceMyTaskContainer .item .bottom .icon.reminder {
        background: url("../../../kms/lservice/portlet/images/myTask/reminder.svg") no-repeat 100% 100%;
    }
    .kmsLserviceMyTaskContainer .item .bottom .message {
        font-size: 14px;
        float: left;
    }
    .kmsLserviceMyTaskContainer .item .bottom .learnedFontSize,.actiFontSize {
        color: #4285F4;
    }
    .kmsLserviceMyTaskContainer .item .bottom .examFontSize {
        color: #0DC47B;
    }
    .kmsLserviceMyTaskContainer .item .bottom .lmapFontSize {
        color: #F96262;
    }
    .kmsLserviceMyTaskContainer .item .bottom .trainFontSize {
        color: #FE8D51;
    }
    .kmsLserviceMyTaskContainer .item .bottom .reminderFontSize {
        color: #4285F4;
    }
    .kmsLserviceMyTaskItemParent{
        display: inline-block;
        min-width: 203px;
    }
</style>
<script>
    // 根据类型获取数字后的描述
    function getTitle(type) {
        var title = '';
        if('learned' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.learn.course") }';
        } else if('acti' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.learn.course0") }';
        } else if('exam' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.learn.course1") }';
        } else if('lmap' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.learn.course2") }';
        } else if('train' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.learn.course3") }';
        } else if('reminder' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.learn.course4") }';
        }
        return title;
    }
    // 根据类型获取描述
    function getMessage(type) {
        var message = '';
        if('learned' == type) {
            message = '${lfn:message("kms-lservice:kmsLservice.portlet.my.learned") }';
        } else if('acti' == type) {
            message = '${lfn:message("kms-lservice:kmsLservice.portlet.my.acti") }';
        } else if('exam' == type) {
            message = '${lfn:message("kms-lservice:kmsLservice.portlet.my.exam") }';
        } else if('lmap' == type) {
            message = '${lfn:message("kms-lservice:kmsLservice.portlet.my.lmap") }';
        } else if('train' == type) {
            message = '${lfn:message("kms-lservice:kmsLservice.portlet.my.train") }';
        } else if('reminder' == type) {
            message = '${lfn:message("kms-lservice:kmsLservice.portlet.my.reminder") }';
        }
        return message;
    }
    // 根据类型获取跳转的url
    function getUrl(type) {
        var url = '';
        if('learned' == type) {
            url = '/kms/learn';
            // 今天的筛选
            var today = '<%=DateUtil.convertDateToString(new Date(),"yyyy-MM-dd")%>';
            url += '/#j_path=%2Fget&cri.learn_get.q=docEndTime%3A'+today+'%3BdocEndTime%3A'+today;
        } else if('acti' == type) {
            url = '/kms/learn/#j_path=%2Frequire&cri.learn_require.q=docStatus%3A2%3BdocStatus%3A1';
        } else if('exam' == type) {
            url = '/kms/exam/student/index.jsp#cri.exam_stu_need.q=actiStatus%3AwaitToBegin%3BactiStatus%3Astarting';
        } else if('lmap' == type) {
            url = '/kms/lmap/main/student/index.jsp#cri.mine.q=fdStatus:0;fdStatus:2';
        } else if('train' == type) {
            url = '/kms/train/student/index.jsp';
        } else if('reminder' == type) {
            url = '/kms/reminder/main/student/index.jsp#cri.kmsReminderExamNotify.q=fdEnd%3A0';
        }
        return url;
    }
    // 点击跳转详情页面
    function detail(url) {
        const contextPath = '${LUI_ContextPath}';
        window.open(contextPath + url)
    }
    //元素宽度计算
	function kms_lservice_my_task_refresh(domId,allContent){
		// 总宽度
		var allWidth = $("#"+domId).width()-1;
		// 个数
		var content = allContent.split(";");
		var itemNum = content.length;
		
		//每个元素宽度
		$("#"+domId).find(".kmsLserviceMyTaskItemParent").css("width",100/itemNum+"%");
		//$("#"+domId).find(".kmsLserviceLecturerTaskConstainer").css("margin","5px auto");
	}
</script>
<ui:ajaxtext>
    <ui:dataview>
        <ui:render type="Template">
            {$
            <div class="kmsLserviceMyTaskContainer">
                $}
                var arr = Object.keys(data);
                for(var i = 0; i < arr.length; i++) {
                var type = arr[i];
                var title = getTitle(type);
                var message = getMessage(type);
                var url = getUrl(type);
                var messageStyle = type + 'FontSize';
                {$
                <div class="kmsLserviceMyTaskItemParent">
                <div class="item">
                    <div class="top">
                        <div class="box">
                            <div class="number">{%data[type]%}</div>
                            <div class="title">{%title%}</div>
                        </div>
                    </div>
                    <div class="bottom" onclick="detail('{%url%}')">
                        <div class="box {%messageStyle%}">
                            <div class="icon {%type%}"></div>
                            <div class="message">{%message%}</div>
                        </div>
                    </div>
                </div>
                </div>
                $}
                }
                {$
            </div>
            $}
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/kms/lservice/kmsLservicePortletAction.do?method=getMyTaskData&type=<%=type%>'}
        </ui:source>
        <ui:event event="load" args="data">
			//元素宽度计算
		    kms_lservice_my_task_refresh(data.source.cid,"<%=type%>")
			//设置容器滚动条
			$("#"+data.source.cid).css({"overflow-x":"auto","font-size":"0"});
			$("#"+data.source.cid).find(".kmsLserviceMyTaskContainer").css({"font-size":"0"});
		</ui:event>
    </ui:dataview>
</ui:ajaxtext>
