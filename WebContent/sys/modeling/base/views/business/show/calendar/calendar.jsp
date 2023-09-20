<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<template:include ref="default.simple">
    <%--日历框架JS、CSS--%>
    <template:replace name="head">
       <%-- <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/views/business/res/calendar.css?s_cache=${LUI_Cache}"/>--%>
        <link type="text/css" rel="stylesheet"
              href="${LUI_ContextPath}/sys/modeling/base/views/business/res/calendarView.css?s_cache=${LUI_Cache}"/>
        <script>
            seajs.use(['sys/modeling/base/views/business/show/calendar/dateUtil.js'], function(dateUtil) {
                window.dateUtil=dateUtil;
            });
        </script>
    </template:replace>
    <%--右侧--%>
    <template:replace name="body">
        <script type="text/javascript">
            var criterionData = {
                sorts: [],
                query: [],
                criterions: [],
                lock: false,
                customCriteria: {},//自定义事件
                cacheEvt: [],// 缓存事件，防止重复初始化
                initURL:"/sys/modeling/base/calendar.do?method=indexData&modelId=${param.modelId}&businessId=${param.businessId}&type=calendar&isFlow=${param.isFlow}&targetModelId=${param.targetModelId}&incFdId=${param.incFdId}&treeViewId=${param.treeViewId}"
            };
            seajs.use(["sys/modeling/base/views/business/show/calendar/fullcalendar.js"],function (calendar){
                window.modelingCalendar = calendar.ModelingFullCalendar;
            })
            seajs.use(['lui/jquery','lui/dialog','lui/topic',
                    'lui/toolbar', 'lui/util/env','lui/dateUtil'],
                function($,dialog , topic ,toolbar,env,dateutil) {
                    // 改变搜索条件时，动态往查询参数里增加自定义参数
                    topic.subscribe('criteria.changed', function (evt) {
                        //任务显示在什么时间位置
                        var url=LUI("calendar").source.url;
                        url = criteriaChange(evt,url)//修改请求地址
                        LUI("calendar").source.setUrl(Com_SetUrlParameter(url));//修改请求地址
                        LUI('calendar').refreshSchedules();//重刷日历
                    });
                    //系统生成筛选项
                    window.criteriaChange=function (data,url) {

                        this.criterionData.cacheEvt.push(data);
                        // 筛选器请求统一无缓存处理
                        this.criterionData.cacheEvt.push({
                            others: [buildRag()]
                        });
                        var url = resolveUrls(url);
                        this.criterionData.cacheEvt.length = 0;
                        return url;
                    };
                    // 缓存数据拼装
                    window.resolveUrls= function (url) {
                        var cache = this.criterionData.cacheEvt, ps;
                        this.page = [];
                        for (var kk in cache) {
                            if (cache[kk].query)
                                this.criterionData.query = cache[kk].query;
                            if (cache[kk].criterions)
                                this.criterionData.criterions = cache[kk].criterions;
                            if (cache[kk].page)
                                this.criterionData.page = cache[kk].page;
                            if (cache[kk].sorts)
                                this.criterionData.sorts = cache[kk].sorts;
                            if (cache[kk].others)
                                this.criterionData.others = cache[kk].others;
                        }
                        var url = buildUrl(url);
                        return url;
                    },
                    window.buildUrl=function(url){
                        var ps = this.criterionData.criterions.concat(this.criterionData.query);
                        var page = [];
                        var sorts = this.criterionData.sorts;
                        var others = this.criterionData.others;
                        var custom = this.criterionData.customCriteria;
                        var rtnData = null;
                        if (url) {
                            // 匹配替换参数，并去除重复参数
                            url =  this.criterionData.initURL.replace(/\!\{([\w\.]*)\}/gi,
                                function (_var, _key) {

                                    var value = "";
                                    $.each(ps, function (i, data) {
                                        if (_key == data.key) {
                                            value = data.value;
                                            ps.splice(i, 1);
                                            return false;
                                        }
                                    });
                                    if ($.isArray(value) && value.length > 0) {
                                        value = value[0];
                                    }
                                    return (value === null || value === undefined) ? ""
                                        : value;
                                });
                            //格式化筛选器参数
                            var urlParam = serializeParams(ps);
                            //加入自定义参数
                            if (custom) {
                                for (var key in custom) {
                                    urlParam += "&" + key + "=" + custom[key];
                                }
                            }
                            if (urlParam) {
                                if (url.indexOf('?') > 0) {
                                    url += "&" + urlParam;
                                } else {
                                    url += "?" + urlParam;
                                }
                            }
                            // 重复参数采取替换方式
                            url = replaceParams(page, url);
                            if (sorts.length > 0) {
                                url = replaceParams(sorts, url);
                            } /*else {
                                if (this.config.url != "" && this.config.url != null) {
                                    var page = "all";
                                    var param = urlParam.split("&");
                                    if (urlParam != "" && typeof (urlParam) != "undefined" && !(param[0].indexOf("q.s_raq") > -1) && !(param[0].indexOf("q.docStatus") > -1)) {
                                        if (param[0].indexOf("q.fdIsTop") > -1) {
                                            page = "top";
                                        } else {
                                            page = urlParam.split("=")[1];
                                        }

                                    }
                                    $.ajax({
                                        url: this.config.url + "&page=" + page,
                                        dataType: "text",
                                        async: false,
                                        type: "post",
                                        success: function (data) {
                                            if (data != null && data != "") {
                                                rtnData = data;
                                            }
                                        }
                                    });
                                    if (rtnData != null) {
                                        url += "&" + rtnData;
                                    }
                                }
                            }*/
                            if (others)
                                url = replaceParams(others,
                                    url);
                        }
                        return url;
                    };
                    // 构建随机数，用于无缓存刷新
                    window.buildRag= function () {
                        return {
                            "key": "__seq",
                            "value": [(new Date()).getTime()]
                        };
                    }

                function serializeParams(params) {
                var array = [];
                for (var i = 0; i < params.length; i++) {
                    var p = params[i];
                    if (p.nodeType) {
                        array.push('nodeType=' + p.nodeType);
                    }

                    // 例外对于列表数据源无用的信息
                    /*if ('j_path' == p.key){
                        continue;
                    }*/

                    for (var j = 0; j < p.value.length; j++) {
                        array.push("q." + encodeURIComponent(p.key) + '='
                            + encodeURIComponent(p.value[j]));
                    }
                    if (p.op) {
                        array.push(encodeURIComponent(p.key) + '-op='
                            + encodeURIComponent(p.op));
                    }
                    for (var k in p) {
                        if (k == 'key' || k == 'op' || k == 'value' || k == 'nodeType' || k == 'obj') {
                            continue;
                        }
                        array.push(encodeURIComponent(p.key + '-' + k) + "="
                            + encodeURIComponent(p[k] || ""));
                    }
                }
                var str = array.join('&');
                return str;
            }
                function replaceParams(params, url) {
                for (var i = 0; i < params.length; i++) {
                    var p = params[i];
                    for (var j = 0; j < p.value.length; j++) {
                        url = replaceParam(p.key, p.value[j], url);
                    }
                }
                return url;
            }

                function replaceParam(param, value, url) {
                var re = new RegExp();
                re.compile("([\\?&]" + param + "=)[^&]*", "i");
                if (value == null) {
                    if (re.test(url)) {
                        url = url.replace(re, "");
                    }
                } else {
                    value = encodeURIComponent(value);
                    if (re.test(url)) {
                        url = url.replace(re, "$1" + value);
                    } else {
                        url += (url.indexOf("?") == -1 ? "?" : "&") + param + "="
                            + value;
                    }
                }
                if (url.charAt(url.length - 1) == "?")
                    url = url.substring(0, url.length - 1);
                return url;
            }
                    //显示日程
                    topic.subscribe('calendar.thing.click',function(arg){
                        //题头:我的日程
                        $('#header_title').html(arg.schedule.title);
                        $('#header_title').css("color",arg.schedule.color.backgroundColor);
                        $('.model-preview-icon').css("background",arg.schedule.color.backgroundColor);
                      //  $('.model-preview-icon').css("border", arg.schedule.color.backgroundColor);
                        $("#calendar_add,#calendar_view,#note_view").hide();//隐藏所有弹出框
                        //初始化时间
                        if(arg.schedule.summary[0].value){
                            $(".calendar_date_dialog").html(arg.schedule.summary[0].value);//初始化日期
                            $("#calendar_date_dialog").show();
                        }else{
                            $("#calendar_date_dialog").hide();
                        }

                        //初始化内容摘要
                        if(arg.schedule.summary[1].value){
                            $(".calendar_txt_dialog").html(arg.schedule.summary[1].value);
                            $("#calendar_txt_dialog").show();
                        }else{
                            $("#calendar_txt_dialog").hide();
                        }
                        //初始化发起人
                        if(arg.schedule.summary[2].value){
                            $(".calendar_sponsor_dialog").html(arg.schedule.summary[2].value);
                            $("#calendar_sponsor_dialog").show();
                        }else{
                            $("#calendar_sponsor_dialog").hide();
                        }
                        //初始化参与人
                        if(arg.schedule.summary[3].value){
                            $(".calendar_participant_dialog").html(arg.schedule.summary[3].value);
                            $("#calendar_participant_dialog").show();
                        }else{
                            $("#calendar_participant_dialog").hide();
                        }
                        //初始化地点
                        if(arg.schedule.summary[4].value){
                            $(".calendar_location_dialog").html(arg.schedule.summary[4].value);
                            $("#calendar_location_dialog").show();
                        }else{
                            $("#calendar_location_dialog").hide();
                        }
                        //初始化链接
                        if(arg.schedule.href){
                            var relationUrl =Com_Parameter.ContextPath + arg.schedule.href.substring(1,arg.schedule.href.length);

                            $(".href").attr("value",relationUrl);
                            $("#tr_relation_url").show();
                        }else{
                            $("#tr_relation_url").hide();
                        }
                        $("#calendar_view").css(getPos(arg.evt,$("#calendar_view"))).fadeIn("fast");
                        $("#tr_relation_url").off("click").on("click",function (){
                            var itemDlg = $(this).find(".href").val();
                            //视图穿透
                            if(itemDlg){
                                //指定视图
                                Com_OpenWindow(itemDlg);
                            }
                        });
                    });
                    //获取位置
                    var getPos=function(evt,showObj){
                        var sWidth=showObj.width(),
                            sHeight=showObj.height();
                        var leftMargin = $(".lui_list_left_sidebar_innerframe").width(), //200是左边导航栏的宽度
                            topMargin = $(".lui_portal_header").height();  //50是上边导航栏的高度
                        x=evt.pageX-leftMargin;
                        y=evt.pageY-topMargin;
                        if(y + sHeight + topMargin> $(window).height() + 125 ){
                            y-=sHeight;
                        }
                        if(x + sWidth + leftMargin> $(document.body).outerWidth(true)){
                            x-=sWidth;
                        }
                        return {"top":y,"left":x};
                    };
                    // 监听新建更新等成功后刷新
                    topic.subscribe('successReloadPage', function() {
                            window.location.reload();
                    });
                    //关闭
                    window.close_view=function(id){
                        $("#"+id).fadeOut();//隐藏对话框
                    };
                    //地址本
                    window.setAddressCriteriaVal = function(item,criteriaObj){
                        //默认值
                        var defaultValue = JSON.parse(item.config.defaultValue);
                        var model = item.children[1].criterionSelectElement.selectedValues.model;
                        var values = [];
                        if(defaultValue != null){
                            if(defaultValue.length > 1){
                                //触发多选
                                if(item.config.canMulti){
                                    LUI(item.children[1].cid).setMulti(true);
                                }
                            }
                            for(var i=0; i<defaultValue.length; i++){
                                values.push(defaultValue[i].value);
                                var isExit = false;
                                for(var j=0; j<model.length; j++){
                                    if(model[j].value == defaultValue[i].value){
                                        isExit = true;
                                    }
                                }
                                if(!isExit){
                                    if(item.config.conditionType == "person")
                                        model.push(defaultValue[i]);
                                }
                            }
                        }
                        item.children[1].criterionSelectElement.selectedValues.updateModel(model);
                        item.children[1].criterionSelectElement.selectedValues.addAll(values);
                    }
                    //枚举、文档状态（有流程）
                    window.setEnumCriteriaVal = function(item,criteriaObj){
                        //默认值
                        if(item.selectBox.criterionSelectElement.config.defaultValue != ""){
                            var defaultValue = JSON.parse(item.selectBox.criterionSelectElement.config.defaultValue);
                            var values = [];
                            if(defaultValue != null){
                                if(defaultValue.length > 1){
                                    //触发多选
                                    if(item.config.canMulti){
                                        LUI(item.selectBox.cid).setMulti(true);
                                    }
                                }
                                for(var i = 0 ;i < defaultValue.length; i++){
                                    values.push(defaultValue[i].value);
                                }
                            }
                            item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                        }
                    }
                    //数字，金额
                    window.setNumberCriteriaVal = function (item,criteriaObj){
                        //默认值
                        var defaultValue = JSON.parse(item.config.defaultValue);
                        var values = [];
                        var isnull = false;
                        if(defaultValue != null){
                            for (var j = 0;j < defaultValue.length;j++){
                                var n = Number(defaultValue[j].value);
                                if (isNaN(n)){
                                    //不是数字
                                    isnull = false;
                                    break;
                                }
                                values.push(defaultValue[j].value);
                                if(defaultValue[j].value != ""){
                                    isnull = true;
                                }
                            }
                        }
                        if(isnull){
                            item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                        }
                    }
                    //时间
                    window.setTimeCriteriaVal = function(item,criteriaObj){
                        //默认值
                        var defaultValue = JSON.parse(item.config.defaultValue);
                        var isnull = false;
                        for(var i = 0;i < defaultValue.length;i++ ){
                            var regexs = /^(([0-2][0-3])|([0-1][0-9])):[0-5][0-9]$/;
                            if(defaultValue[i] != ""){
                                isnull = true;
                                if(!regexs.test(defaultValue[i])){
                                    return
                                }
                            }
                        }
                        if(isnull){
                            item.selectBox.criterionSelectElement.selectedValues.addAll(defaultValue);
                        }
                    }
                    //文本
                    window.setTextCriteriaVal = function(item,criteriaObj){
                        var values = [];
                        //默认值
                        if(item.config.defaultValue){
                            var defaultValue = item.config.defaultValue;
                            values.push(defaultValue);
                            item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                        }
                    }
                    //日期6
                    window.setDateCriteriaVal = function(item,calendarObj){
                        var allDates = item.selectBox.criterionSelectElement.allDates;
                        if(item.config.defaultValue){
                            setDateCriteriaValModify(allDates,item,item.config.defaultValue);
                        }
                    }
                    //修改日期类型组件values
                    window.setDateCriteriaValModify = function(allDates,item,type){
                        if(type.indexOf("[") == -1){
                            var values = [];
                            var allDatasArr = allDates[type];
                            if(allDatasArr){
                                for(var i = 0;i < allDatasArr.length;i++){
                                    var obj = {};
                                    obj.text = allDatasArr[i];
                                    obj.value = allDatasArr[i];
                                    values.push(obj);
                                }
                                item.selectBox.criterionSelectElement.selectedValues.addAll(values);
                            }
                        }
                    }
                    //筛选器设置默认值
                    var num = setInterval(function () {
                        var criteria = $(".criteria")[0];
                        if (!criteria) {
                            return;
                        }
                        var uid = $(criteria).attr("data-lui-cid");
                        //打开筛选
                        var criteriaObj = LUI(uid);
                        if (!criteriaObj.isDrawed || !criteriaObj.moreAction) {
                            return;
                        }
                        clearInterval(num);
                        criteriaObj.expandCriterions(criteriaObj.expand);
                        //初始化筛选器的值
                        setTimeout(function(){
                            var criteriaArr = criteriaObj.criterions;
                            for (var i = 0; i < criteriaArr.length; i++) {
                                var item = criteriaArr[i];
                                var bsnsType = item.selectBox.criterionSelectElement.config.conditionBusinessType || "";
                                if(item.config.conditionType == "DateTime" || item.config.conditionType == "Date" ){
                                    var calendar = $(".criterion-calendar")[0];
                                    if(calendar){
                                        //日期
                                        uid = $(calendar).attr("data-lui-cid");
                                        var calendarObj = LUI(uid);
                                        setDateCriteriaVal(item,calendarObj);
                                    }
                                }else if(item.config.conditionType == "Time"){
                                    //时间
                                    setTimeCriteriaVal(item,calendarObj);
                                }else if(item.config.conditionType == "person" || item.config.conditionType == "post" ||item.config.conditionType == "dept"){
                                    //地址本
                                    setAddressCriteriaVal(item,criteriaObj);
                                }else if(item.config.conditionType == "number"){
                                    //数字，金额
                                    setNumberCriteriaVal(item,criteriaObj);
                                }else if(bsnsType == "select" || bsnsType == "inputCheckbox" || bsnsType == "inputRadio" || item.config.key === "docStatus"){
                                    //（枚举）多选，单选，下拉，文档状态
                                    setEnumCriteriaVal(item,criteriaObj);
                                }else{
                                    //文本
                                    setTextCriteriaVal(item,criteriaObj);
                                }
                            }
                        },300);
                    }, 200);

                });
            var listOption = {
                fdAppModelId : '${fdAppModelId}',
                isFlow : '${isFlow}',
            }
            Com_IncludeFile("index.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
        </script>
        <!-- 筛选器 -->
        <%@ include file="/sys/modeling/base/views/business/show/criteria.jsp" %>
        <!-- 操作按钮 -->
        <div style="float:right">
            <div style="display: inline-block;vertical-align: middle;">
                <ui:toolbar id="toolbar" style="float:right; padding-right: 5px" count="3">
                    <%-- 业务操作按钮 --%>
                    <%@ include file="/sys/modeling/base/view/ui/viewopers.jsp"%>
                </ui:toolbar>
            </div>
        </div>
        <div class="calendar-title">${calendarTitle}</div>
        <ui:calendar id="calendar" showStatus="edit" mode="modelingCalendar" type="sys/modeling/base/views/business/show/calendar/modelingCalendarView!ModelingCalendarView" layout="sys.modeling.calendar" customMode="{'id':'modelingCalendar','name':'日历视图','func':modelingCalendar,'showMode':'${showMode}'}" >
            <ui:source type="AjaxJson">
                { url : '/sys/modeling/main/calendar.do?method=indexData&modelId=${param.modelId}&businessId=${param.businessId}&type=calendar&isFlow=${param.isFlow}&targetModelId=${param.targetModelId}&incFdId=${param.incFdId}&treeViewId=${param.treeViewId}'}
            </ui:source>
            <ui:render type="Template">
                var title = "${ lfn:message('sys-modeling-main:modelingCalendar.on.content') }";
                if(data['title']){
                title = env.fn.formatText(data['title'].replace(/(\n)+|(\r\n)+/g, " "));
                }
                var pclass="";
                if(data['isPrivate']){
                pclass="calendar_content_title";
                }
                {$<p title="{%title%}" class="{%pclass%}">$}
                var str="";
                str+=title;
                var color = "";
                if(data['color']){
                color = data['color']
                var backgroundColor = color.backgroundColor;
                var fontColor = color.fontColor;
                var rowBackgroundColor = color.rowBackgroundColor;
                {$<i style="background-color:{%backgroundColor%} "></i><span class="textEllipsis" style="background-color:{%rowBackgroundColor%};color:{%fontColor%}  " >{%str%}</span></p>$}
                }else{
                {$<span class="textEllipsis" style="background-color:{%backgroundColor%}" >{%str%}</span></p>$}
                }
            </ui:render>
        </ui:calendar>
        <%--查看日程DIV--%>
        <div class="lui_calendar calendar_view" id="calendar_view" style="display: none;position: absolute;">
            <div class="lui_calendar_top">
                <div class="model-preview-icon"></div>
                <div id="header_title" class="lui_calendar_title">
                </div>
                <div class="lui_calendar_close" style="background: url(${LUI_ContextPath}/sys/modeling/base/views/business/res/img/sourcePanel/close.png) no-repeat left top;background-size: contain;" onclick="close_view('calendar_view');">
                </div>
            </div>
            <div class="calendar_view_content">
                <div class="view_sched_wrapper">
                    <input type="hidden"  name="fdId" />
                    <input type="hidden" name="fdIsGroup" />
                    <table class="view_sched">
                        <tr id="calendar_date_dialog">
                                <%--时间--%>
                            <td class="title" width="50px">
                              ${ lfn:message('sys-modeling-main:modelingRes.time') }：
                            </td>
                            <td>
                                <div class="calendar_dialog calendar_date_dialog"></div>
                            </td>
                        </tr>
                        <tr id="calendar_txt_dialog">
                                <%--内容--%>
                            <td class="title" width="50px" valign="top">
                               ${ lfn:message('sys-modeling-main:modelingCalendar.abstract') }：
                            </td>
                            <td>
                                <div class="calendar_dialog calendar_txt_dialog" ></div>
                            </td>
                        </tr>
                        <tr id="calendar_sponsor_dialog">
                                <%--内容--%>
                            <td class="title" width="50px" valign="top">
                               ${ lfn:message('sys-modeling-main:modelingCalendar.sponsor') }：
                            </td>
                            <td>
                                <div class="calendar_dialog calendar_sponsor_dialog" ></div>
                            </td>
                        </tr>
                        <tr id="calendar_participant_dialog">
                                <%--内容--%>
                            <td class="title" width="50px" valign="top">
                               ${ lfn:message('sys-modeling-main:modelingCalendar.participant') }：
                            </td>
                            <td>
                                <div class="calendar_dialog calendar_participant_dialog" ></div>
                            </td>
                        </tr>
                        <tr id="calendar_location_dialog">
                                <%--内容--%>
                            <td class="title" width="50px" valign="top">
                               ${ lfn:message('sys-modeling-main:modelingCalendar.place') }：
                            </td>
                            <td>
                                <div class="calendar_dialog calendar_location_dialog"></div>
                            </td>
                        </tr>

                    </table>
                </div>
                <div id="tr_relation_url">
                        <span>${ lfn:message('sys-modeling-main:modelingCalendar.Details') }</span>
                        <input class="href"style="display: none" type="text"  >
                        <i class="right-arrow"></i>
                </div>
            </div>
        </div>

    </template:replace>

</template:include>
