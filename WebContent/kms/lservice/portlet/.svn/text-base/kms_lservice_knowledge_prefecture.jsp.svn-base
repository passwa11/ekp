<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/sys/ui/jsp/common.jsp" %>
<%
    String rowsize = request.getParameter("rowsize");
%>
<style>
    .floatLeft {
        float: left;
    }
    .textHidden {
        overflow: hidden;
        text-overflow:ellipsis;
        white-space: nowrap;
    }
    .kmsLserviceKnowledgePrefectureContainer {
        margin: 10px;
    }
    .kmsLserviceKnowledgePrefectureContainer .header {
        width: 100%;
        height: 50px;
        line-height: 50px;
        text-align: center;
        font-size: 18px;
        color: #333333;
        font-weight: bold;
    }
    .kmsLserviceKnowledgePrefectureContainer .content {
        border: 1px solid #E9E9E9;
        border-radius: 1px;
        width: 100%;
        box-sizing: border-box;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .tab {
        width: 100%;
        display: flex;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .tab .item {
        flex: 1;
        height: 40px;
        font-size: 14px;
        line-height: 40px;
        text-align: center;
        background: #F6F6F6;
        border: 1px solid transparent;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .tab .item.selected {
        font-weight: bold;
        color: #4285F4;
        background: #fff;
        position: relative;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .tab .item.selected::after {
        position: absolute;
        content: '';
        width: 100%;
        height: 3px;
        background: #4285F4;
        left: 0;
        top: -1px;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .tab .item.unselected:hover {
        color: #4285F4;
        background: #fff;
        border-color: #E9E9E9;
        cursor: pointer;
    }
     .kmsLserviceKnowledgePrefectureContainer .content .tab .item.unselected {
        border-color: #EEEEEE;
        border-top: 0;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .list {
        width: 100%;
        padding: 10px;
        box-sizing: border-box;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .list .item {
        width: 100%;
        height: 24px;
        font-size: 14px;
        color: #333333;
        display: flex;
        padding: 5px 0;
        cursor: pointer;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .list .item:hover {
        color: #4285F4;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .list .item .icon {
        text-align: center;
        width: 4px;
        height: 4px;
        background: #999999;
        border-radius: 2px;
        margin: 8px 10px 0 0;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .list .item:hover .icon {
        background: #4285F4;
    }
    .kmsLserviceKnowledgePrefectureContainer .content .list .item .value {
    }
    .kmsLserviceKnowledgePrefectureContainer .content .more {
        border-top: 1px solid #F4F5F7;
        margin: 0 20px;
        height: 40px;
        line-height: 40px;
        text-align: center;
        color: #999999;
        cursor: pointer;
    }
    .kmsLserviceKnowledgePrefectureContainer .bottom {
        margin-top: 10px;
        display: flex;
    }
    .kmsLserviceKnowledgePrefectureContainer .bottom .box {
        width: 90px;
        margin: 0 auto;
        height: 20px;
        margin-top: 20px;
        color: #ffffff;
        position: absolute;
	    top: 0px;
	    left: 50%;
	    margin-left: -45px;
	    z-index: 1;
    }
    .kmsLserviceKnowledgePrefectureContainer .bottom .box .message {
        float: left;
    }
    .kmsLserviceKnowledgePrefectureContainer .bottom .learn {
        height: 60px;
        border-radius: 2px;
        margin-right: 10px;
        flex: 1;
        cursor: pointer;
        position: relative;
    }
    .kmsLserviceKnowledgePrefectureContainer .bottom .learn .icon {
        width: 30px;
        height: 100%;
        float: left;
    	background: url("../../../kms/lservice/portlet/images/prefecture/learn.svg") no-repeat 50% 50%;
    }
    .kmsLserviceKnowledgePrefectureContainer .bottom .exam {
        height: 60px;
        border-radius: 2px;
        flex: 1;
        cursor: pointer;
        position: relative;
    }
    .kmsLserviceKnowledgePrefectureContainer .bottom .exam .icon {
        width: 30px;
        height: 100%;
        float: left;
    	background: url("../../../kms/lservice/portlet/images/prefecture/exam.svg") no-repeat 50% 50%;
    }
    .kmsLserviceKnowledgePrefectureContainer img{
        width: 100%;
	    height: 100%;
	    position: absolute;
	    top: 0px;
	    left: 0px;
	    z-index: 0;
    }
</style>
<script>
    // 组件id，防止乱窜
    var dataId;
    function initKnowledgePrefecture(data) {
        // 初始化组件id
        dataId = data.source.cid;

        // 数据库数据
        var dataDB = data.source.data;

        // 初始化列表页面
        var list = $('#' + dataId + ' .kmsLserviceKnowledgePrefectureContainer .content .list .box');

        for (var i = 0; i < list.length; i++) {
            var dom = list[i];
            drawList(dom, dataDB, i);
        }

        // 初始化查看更多按钮
        var $content = $('#' + dataId + ' .kmsLserviceKnowledgePrefectureContainer .content');
        $content.append('<div class="more" onclick="more()">查看更多 >></div>');

    }

    function more() {
        if(tabNumber == 0) {
        	window.open('${LUI_ContextPath}/kms/learn/#cri.learn_self.q=fdIsEnd%3Afalse');
        } else if(tabNumber == 1) {
            window.open('${LUI_ContextPath}/kms/learn/main/student/index.jsp#j_path=%2Frequire');
        } else if(tabNumber == 2) {
            window.open('${LUI_ContextPath}/kms/exam');
        }
    }

    function drawList(dom, dataDB, num) {
        var db, url;
        if(num == 0) { // 正在学习
            db = dataDB.learning;
            url = '${LUI_ContextPath}/kms/learn/kms_learn_main/kmsLearnMain.do?method=view&fdId=';
        } else if(num == 1) { // 指派给我的学习任务
            db = dataDB.actiList;
            url = '${LUI_ContextPath}/kms/learn/kms_learn_activity/kmsLearnActivity.do?method=todo&fdId=';
        } else if(num == 2) { // 指派给我的考试任务
            db = dataDB.examList;
            url = '${LUI_ContextPath}/kms/exam/kms_exam_unified_activity/kmsExamUnifiedActivity.do?method=entryExam&fdId=';
        }
        for (var i = 0; i < db.length; i++) {
            var params = db[i];
            var $item = $('<div class="item" title="' + params.fdName + '" onclick="view(\''+ (url+params.fdId) +'\')"/>');
            var $icon = $('<div class="icon"></div>');
            var $value = $('<div class="value textHidden">' + params.fdName + '</div>');
            $(dom).append($item.append($icon).append($value));
        }
    }

    function view(url) {
        window.open(url);
    }

    // 切换页签
    var tabNumber = 0;
    function selectTab(num) {
        if(tabNumber == num)
            return;
        var tabs = $('#' + dataId + ' .kmsLserviceKnowledgePrefectureContainer .content .tab .item');
        for (var i = 0; i < tabs.length; i++) {
            tabs[tabNumber].classList.add('unselected');
            var item = tabs[i];
            // tab处理
            item.classList.remove('selected');
            if(num == item.getAttribute("value")) {
                item.classList.add('selected');
                item.classList.remove('unselected');
            }
        }
        // 内容处理
        var list = $('#' + dataId + ' .kmsLserviceKnowledgePrefectureContainer .content .list .box');
        for (var i = 0; i < list.length; i++) {
            var item = list[i];
            item.style.display = 'none';
            if(num == item.getAttribute("value"))
                item.style.display = 'block';
        }
        tabNumber = num;
    }
    
    //设置每日一学，每日一考按钮背景图
    function initKnowledgePrefectureImg(data,learnImg,examImg) {
    	var src=data.source.env.config.contextPath+"/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=";
        if(learnImg){
        	$('#' + dataId + ' .kmsLserviceKnowledgePrefectureContainer .learn img').attr("src",src+learnImg);
        }
        if(examImg){
        	$('#' + dataId + ' .kmsLserviceKnowledgePrefectureContainer .exam img').attr("src",src+examImg);
        }
    }

</script>
<ui:ajaxtext>
    <ui:dataview>
        <ui:render type="Template">
            {$
                <div class="kmsLserviceKnowledgePrefectureContainer">
                    <div class="header">
                        ${lfn:message('kms-lservice:kmsLservice.knowledge.prefecture.name') }
                    </div>
                    <div class="content">
                        <div class="tab">
                            <div class="item selected" value="0" onclick="selectTab(0)"> ${lfn:message('kms-lservice:kmsLservice.portlet.my.learn.ing') }</div>
                            <div class="item unselected" value="1" onclick="selectTab(1)"> ${lfn:message('kms-lservice:kmsLservice.portlet.my.learn.task') }</div>
                            <div class="item unselected" value="2" onclick="selectTab(2)"> ${lfn:message('kms-lservice:kmsLservice.portlet.my.learn.exam') }</div>
                        </div>
                        <div class="list">
                            <div class="box" value="0"></div>
                            <div class="box" value="1" style="display:none"></div>
                            <div class="box" value="2" style="display:none"></div>
                        </div>
                    </div>
                    <div class="bottom">
                        <div class="learn" onclick="window.open('${LUI_ContextPath}/kms/reminder/#j_path=%2Flearn')">
                            <div class="box">
                                <div class="icon"></div>
                                <div class="message"> ${lfn:message('kms-lservice:kmsLservice.portlet.my.learn.one') }</div>
                            </div>
                            <img class="" src="${LUI_ContextPath}/kms/lservice/portlet/images/prefecture/learnImg.png">
                        </div>
                        <div class="exam" onclick="window.open('${LUI_ContextPath}/kms/reminder/#j_path=%2Fexam')">
                            <div class="box">
                                <div class="icon"></div>
                                <div class="message"> ${lfn:message('kms-lservice:kmsLservice.portlet.my.learn.one.exam') }</div>
                            </div>
                            <img class="" src="${LUI_ContextPath}/kms/lservice/portlet/images/prefecture/examImg.png">
                        </div>
                    </div>
                </div>
            $}
        </ui:render>
        <ui:source type="AjaxJson">
            {url:'/kms/lservice/kmsLservicePortletAction.do?method=getPrefectureData&rowsize=<%=rowsize%>'}
        </ui:source>
        <ui:event event="load" args="data">
            initKnowledgePrefecture(data);
            initKnowledgePrefectureImg(data,"${JsParam.learnImg}","${JsParam.examImg}")
        </ui:event>
    </ui:dataview>
</ui:ajaxtext>
