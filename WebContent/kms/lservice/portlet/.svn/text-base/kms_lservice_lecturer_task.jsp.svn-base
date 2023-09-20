<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/sys/ui/jsp/common.jsp" %>
<%
    String type = request.getParameter("type");
%>
<style>
    .kmsLserviceLecturerTaskConstainer {
        background: #FFFFFF;
        border-radius: 6px;
        width: 290px;
        height: 86px;
        margin: 5px 10px 5px 0;
        border: 1px solid #F2F2F2;
        cursor: pointer;
    }
    .kmsLserviceLecturerTaskConstainer .left {
        float: left;
        width: 86px;
        height: 100%;
        text-align: center;
        line-height: 86px;
    }
    .kmsLserviceLecturerTaskConstainer .left.exam {
        background: url("../../../kms/lservice/portlet/images/lecturerTask/exam.svg") no-repeat 50% 50%;
    }
    .kmsLserviceLecturerTaskConstainer .left.homework {
        background: url("../../../kms/lservice/portlet/images/lecturerTask/homework.svg") no-repeat 50% 50%;
    }
    .kmsLserviceLecturerTaskConstainer .left.lmap {
        background: url("../../../kms/lservice/portlet/images/lecturerTask/lmap.svg") no-repeat 50% 50%;
    }
    .kmsLserviceLecturerTaskConstainer .left.train {
        background: url("../../../kms/lservice/portlet/images/lecturerTask/train.svg") no-repeat 50% 50%;
    }
    .kmsLserviceLecturerTaskConstainer .right {
        float: right;
        width: 106px;
        height: 100%;
        margin-right: 24px;
    }
    .kmsLserviceLecturerTaskConstainer .right .top {
        width: 100%;
        font-size: 16px;
        color: #4285F4;
        text-align: right;
        padding-top: 16px;
    }
    .kmsLserviceLecturerTaskConstainer .right .bottom {
        margin: 0 auto;
        margin-top: 10px;
    }
    .kmsLserviceLecturerTaskConstainer .right .bottom:after {
        content: '';
        height: 0;
        line-height: 0;
        display: block;
        visibility: hidden;
        clear: both;
    }
    .kmsLserviceLecturerTaskConstainer .right .bottom .label {
        float: right;
        font-size: 14px;
        color: #666666;
        padding-top: 6px;
    }
    .kmsLserviceLecturerTaskConstainer .right .bottom .number {
        float: right;
        font-size: 20px;
        color: #333333;
        margin-left: 10px;
    }
    .kmsLserviceLecturerTaskConstainerParent{
        display: inline-block;
        min-width: 302px;
    }
</style>
<script>
    function getLecturerTitle(type) {
        let title = '';
        if('exam' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.lecturer.exam") }';
        } else if('homework' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.lecturer.homework") }';
        } else if('lmap' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.lecturer.lmap") }';
        } else if('train' == type) {
            title = '${lfn:message("kms-lservice:kmsLservice.portlet.my.lecturer.train") }';
        }
        return title;
    }
    function getLecturerLabel(type) {
        let label = "${lfn:message('kms-lservice:kmsLservice.portlet.my.learn.task.num') }";
        if('train' == type) {
            label = "${lfn:message('kms-lservice:kmsLservice.portlet.my.learn.speed' ) }";
        }
        return label;
    }
    function getLecturerUrl(type) {
        let url = '';
        if('exam' == type) {
            url = '/kms/exam/teacher/index.jsp#cri.exam_acti_my_check.q=actiStatus%3A0';
        } else if('homework' == type) {
            url = '/kms/homework/teacher/#cri.q=myMarkHwork:marking';
        } else if('lmap' == type) {
            url = '/kms/lmap/main/teacher/index.jsp#j_path=%2Fcheck&cri.kmsLmapCheck.q=fdStatus%3A0';
        } else if('train' == type) {
            url = '/kms/train/teacher/index.jsp#j_path=%2Fspeak';
        }
        return url;
    }
    function lecturerDetail(url) {
        const contextPath = '${LUI_ContextPath}';
        window.open(contextPath + url);
    }
  //元素宽度计算
	function kms_lservice_lecturer_task_refresh(domId,allContent){
		// 总宽度
		var allWidth = $("#"+domId).width()-1;
		// 个数
		var content = allContent.split(";");
		var itemNum = content.length;
		
		//每个元素宽度
		$("#"+domId).find(".kmsLserviceLecturerTaskConstainerParent").css("width",100/itemNum+"%");
		//$("#"+domId).find(".kmsLserviceLecturerTaskConstainer").css("margin","5px auto");
	}
</script>
<ui:ajaxtext>
    <ui:dataview>
        <ui:render type="Template">
            var arr = Object.keys(data);
            for(var i = 0; i < arr.length; i++) {
                var type = arr[i];
                const title = getLecturerTitle(type);
                const label = getLecturerLabel(type);
                const url = getLecturerUrl(type);
                const number = data[type] > 999 ? '999+' : data[type];
            {$  <div class="kmsLserviceLecturerTaskConstainerParent">
                <div class="kmsLserviceLecturerTaskConstainer" onclick="lecturerDetail('{%url%}')">
                    <div class="left {%type%}"></div>
                    <div class="right">
                        <div class="top">{%title%}</div>
                        <div class="bottom">
                            <div class="number">{%number%}</div>
                            <div class="label">{%label%}</div>
                        </div>
                    </div>
                </div>
                </div>
            $}
            }
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/kms/lservice/kmsLservicePortletAction.do?method=getLecturerTaskData&type=<%=type%>'}
        </ui:source>
        <ui:event event="load" args="data">
			//元素宽度计算
		    kms_lservice_lecturer_task_refresh(data.source.cid,"<%=type%>")
			//设置容器滚动条
			$("#"+data.source.cid).css({"overflow-x":"auto","font-size":"0"});
		</ui:event>
    </ui:dataview>
</ui:ajaxtext>
