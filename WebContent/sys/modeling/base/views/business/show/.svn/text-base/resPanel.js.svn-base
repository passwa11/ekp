/**
 *
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var env = require("lui/util/env");
    var listview = require('lui/listview/listview');
    var topic = require("lui/topic");
    var source = require("lui/data/source");
    var render = require("lui/view/render");
    var calendar = require("lui/calendar");
    var rescalendar = require("sys/modeling/base/views/business/show/rescalendar");
    var modelingLang = require("lang!sys-modeling-main");
    var ResPanel = base.DataView.extend({
        /*--------------------------------组件渲染及事件---------------------------*/
        initProps: function ($super, cfg) {
            $super(cfg);
            this.criterionData = {
                sorts: [],
                query: [],
                criterions: [],
                lock: false,
                customCriteria: {},//自定义事件
                cacheEvt: []// 缓存事件，防止重复初始化
            };
            this.recordData = [];
            this.date =[];
            this.setSource(new source.Static({
                parent: this,
                url: cfg.sourceUrl
            }));
        },
        startup: function ($super, cfg) {
            $super(cfg);
            this.source.startup();
            this.render.startup();
            var self = this;
            //改变搜索条件时，动态往查询参数里增加自定义参数
            topic.subscribe('criteria.changed', this.criteriaChange, self);
        },
        doRender: function ($super, cfg) {
            $super(cfg);
            var self = this;
            //数据-点击绑定弹窗事件
            this.element.find(".res_calendar_data_dom").on("click", function (e) {
                e.stopPropagation();
                var hasClass = $(this).find(".meeting_calendar_dialog").hasClass("dialogHidden");
                self.element.find(".meeting_calendar_dialog").addClass("dialogHidden");
                self.element.find(".meeting_more_calendar_dialog").addClass("dialogHidden");
                if (hasClass) {
                    $(this).find(".meeting_calendar_dialog").removeClass("dialogHidden");
                }
                self.getDialogPosition(this);
            });
            $(".to-model-view").on("click",function (){
                var itemDlg = $(this).find(".href").val();
                //视图穿透
                if(itemDlg){
                    //指定视图
                    Com_OpenWindow(Com_Parameter.ContextPath + itemDlg.substring(1,itemDlg.length));
                }
            });
            //更多弹窗里面的弹窗事件
            this.element.find(".res_calendar_data_collection").on("click", function (e) {
                e.stopPropagation();
                var hasClass = $(this).find(".meeting_calendar_dialog").hasClass("dialogHidden");
                self.element.find(".meeting_calendar_dialog").addClass("dialogHidden");
                if (hasClass) {
                    $(this).find(".meeting_calendar_dialog").removeClass("dialogHidden");
                }
                var  y = $(this).offset().top;//元素在当前视窗距离顶部的位置
                var  x = $(this).offset().left;
                var hight = $(window).height();
                var width = $(window).width();
                var right = width-x;
                var button = hight-y;
                $(this).find(".meeting_calendar_dialog").css("position","fixed");
                if (button<230){
                    $(this).find(".meeting_calendar_dialog").css("top",y-120);
                }else {
                    $(this).find(".meeting_calendar_dialog").css("top",y+10);
                }
                if (right<400){
                 //   $(this).find(".meeting_calendar_dialog").css("top",y+20);
                    $(this).find(".meeting_calendar_dialog").css("left",x-120);
                }else {
                    $(this).find(".meeting_calendar_dialog").css("left",x+70);
                }

            });
            //更多数据
            this.element.find(".more_calendar_data_dom").on("click", function (e) {
                e.stopPropagation();
                var hasClass = $(this).find(".meeting_more_calendar_dialog").hasClass("dialogHidden");
                self.element.find(".meeting_more_calendar_dialog").addClass("dialogHidden");
                self.element.find(".meeting_calendar_dialog").addClass("dialogHidden");
                if (hasClass) {
                    $(this).find(".meeting_more_calendar_dialog").removeClass("dialogHidden");
                }
                var  y = $(this).offset().top;//元素在当前视窗距离顶部的位置
                var  x = $(this).offset().left;
                var hight = $(window).height()
                var width = $(window).width();
                var button = hight-y;
                var right = width-x;
                if (right<400){
                    $(this).find(".meeting_more_calendar_dialog").css("top",20);
                    $(this).find(".meeting_more_calendar_dialog").css("left",right-380);
                }
                if (button<230){
                    $(this).find(".meeting_more_calendar_dialog").css("top",-button);
                }
            });

            $("body").on("click", function () {
                $(".meeting_calendar_dialog").addClass("dialogHidden");
                $(".meeting_more_calendar_dialog").addClass("dialogHidden");
            });
            //刷新按钮绑定事件
            this.element.find(".lui_calendar_header_refresh").on("click", function () {
                self.load();
            });
            //周、日切换绑定事件
            this.element.find(".lui_calendar_header_month").on("click", function () {
                var $this = $(this);
                var opt = $this.attr("cal-opt");
                self.criterionData.customCriteria.today = "false";
                self.criterionData.customCriteria.headerCategory = opt;
                self.customCriteriaChange();

            });
            //向前翻页
            this.element.find(".lui_calendar_btn_arrow_prev").on("click", function () {
                self.prePage();

            });
            //向后翻页
            this.element.find(".lui_calendar_btn_arrow_next").on("click", function () {
                self.nextPage();
            });
            //回到今天
            this.element.find(".lui_calendar_header_today").on("click", function () {
                var $this = $(this);
                self.criterionData.customCriteria.today = "rebackToday";
                self.customCriteriaChange();
                var rebacktoday =  $.find("rebacktoday");

            });
        },
        refresh: function (params) {
            debugger
            this.erase();
            this.draw(params);
        },
        /*--------------------------------订阅处理---------------------------*/

        prePage:function (){
            var startTime = this.date.startTime;
            this.removeRepat();
            var url = this.buildUrl();
            this.source.url = this.source.url+"&startTime="+startTime+"&endTime=";
            this.load();
        },
        nextPage:function (){
            var endTime = this.date.endTime;
            this.removeRepat();
            var url = this.buildUrl();
            this.source.url = this.source.url+"&endTime="+endTime+"&startTime=";
            this.load();
        },
        rebackToday:function (){

        },
        getNowDate:function (){
            var date = new Date();
            var seperator1 = "-";
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var strDate = date.getDate();
            if (month >= 1 && month <= 9) {
                month = "0" + month;
            }
            if (strDate >= 0 && strDate <= 9) {
                strDate = "0" + strDate;
            }
            var currentdate = year + seperator1 + month + seperator1 + strDate;
            return currentdate;
        },
        getDialogPosition:function (self){
            var  y = $(self).offset().top;//元素在当前视窗距离顶部的位置
            var  x = $(self).offset().left;
            var hight = $(window).height()
            var width = $(window).width();
            var button = hight-y;
            var right = width-x;
            if (right<400){
                $(self).find(".meeting_calendar_dialog").css("top",20);
                $(self).find(".meeting_calendar_dialog").css("left",right-380);
            }
            if (button<230){
                $(self).find(".meeting_calendar_dialog").css("top",-button);
            }
        },

        //自定义筛选项
        customCriteriaChange: function (callBack) {
            if (!this.loading) {
                this.element.css('min-height', 200);
                if (this.element.is(':visible')) {
                    this.loading = dialog.loading(null, this.element);
                }
            }
            var url = this.buildUrl();
            this.load();
            if (callBack) {
                callBack();
            }

        },
        //系统生成筛选项
        criteriaChange: function (data) {
            if (!this.loading) {
                this.element.css('min-height', 200);
                if (this.element.is(':visible')) {
                    this.loading = dialog.loading(null, this.element);
                }
            }
            this.criterionData.cacheEvt.push(data);
            // 筛选器请求统一无缓存处理
            this.criterionData.cacheEvt.push({
                others: [this.buildRag()]
            });
            var url = this.resolveUrls();
            console.debug("criteriaChange：：", url);
            this.load();
            this.criterionData.cacheEvt.length = 0;


        },
        // draw: function ($super, params) {
        //     $super(params);
        // },
        /*--------------------------------数据处理---------------------------*/
        setRecordData: function (data) {
            this.recordData = data || [];	//[]
        },
        //处理数据
        buildDrawPanelParam: function (data) {

            var param = {
                _tdWidth: 264,
                _dataItemHeight: 22,
                _lineHeight: 57,
            };

            var table = data.table;
            var source = data.source;
            this.date = table.date;
            //底表基础数据
            param.title = table.title;
            param.dataTitle = table.date.timeTitle;
            param.dataTitleWidth =  $("body").width();
            if (source.nav || source.nav.color) {
                param.navColors = source.nav.color;
            } else {
                param.navColors = [];
            }
            param.headerCategory = data.headerCategory
            param.isToday = table.date.isToday && table.date.isToday == true;
            param.rebackToday = data.today =="rebackToday";
            param.today  = this.getNowDate();
            param.colTitle  = table.colTitle
            //表头-标题行-额外放置方便渲染
            param.headX = [];
            var trow = table.row;
            for (var i = 0; i < trow.length; i++) {
                trow[i].dataLeft = i * param._tdWidth;
                param.headX.push(trow[i])
            }
            // //数据表总宽度
            param.fullWidth = trow.length * param._tdWidth;
            param.fullHeight = 0;
            //数据的二维数组
            var sourceData = this.getSourceData(source.data);
            param.table = [];
            for (var y = 0; y < table.col.length; y++) {
                var yItem = {
                    idx: y,
                    maxSize: 0,
                    xArray: [],
                    head: table.col[y]
                };
                for (var x = 0; x < param.headX.length; x++) {
                    var dataArray = [];
                    if (sourceData[y] && sourceData[y][x]) {
                        dataArray = sourceData[y][x];
                        if (yItem.maxSize < dataArray.length) {
                            yItem.maxSize = dataArray.length;
                        }
                    }
                    var xItem = {
                        idx: x,
                        head: param.headX[x],
                        dataArray: dataArray
                    };
                    yItem.xArray.push(xItem)
                }
                //每行的最大高度  //170203  每条数据26px加2px的间距
                var yHeight = yItem.maxSize * 26;
                if (yHeight < param._lineHeight) {
                    yHeight = param._lineHeight;
                }else if (yHeight >120){
                    yHeight = 120;
                } else{
                    //加点边距
                    yHeight+=yItem.maxSize*2+1;
                }
                param.fullHeight+=yHeight;
                yItem.yHeight = yHeight;


                param.table.push(yItem)
            }
            //加上表头
            param.fullHeight +=254;
            return param;
        },
        removeRepat:function (){
            // 匹配替换参数，并去除重复参数
            this.source.url = this.source._url.replace(/\!\{([\w\.]*)\}/gi,
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
        },
        getSourceData: function (source) {
            var data = {};
            for (var i = 0; i < source.length; i++) {
                var item = source[i];
                var c = item.matchCol;
                var r = item.matchRow;

                for (var ci = 0; ci < c.length; ci++) {
                    var cidx = c[ci];
                    for (var ri = 0; ri < r.length; ri++) {
                        var ridx = r[ri];

                        if (!data[cidx]) {
                            data[cidx] = {};
                        }
                        if (!data[cidx][ridx]) {
                            data[cidx][ridx] = [];
                        }
                        if (!item.title) {
                            item.title = "设定显示值为空！"
                        }
                        var cssStr = "";
                        var cssStrA = "";
                        if (item.css) {
                            for (var k in item.css) {
                                cssStr = "background-color:"+ item.css.backgroundColor;
                                cssStrA ="background-color:"+ item.css.backgroundColora;
                                break;
                            }
                        }
                        item.cssStr = cssStr;
                        if (item.cssStr==""){
                            item.cssStr ="background-color:rgba(1,201,173,1)";
                        }
                        item.cssStrA = cssStrA;
                        if (item.cssStrA==""){
                            item.cssStrA ="background-color:rgba(1,201,173,0.2)";
                        }
                        // if (item.dialog) {
                        //     if (item.dialog.__link) {
                        //         item.dialog.__link = Com_Parameter.ContextPath +  item.dialog.__link.substring(1);
                        //     }
                        // }
                        data[cidx][ridx].push(item)
                    }
                }
            }
            this.sourceData = data;
            return data;
        },
        /*--------------------------------工具方法---------------------------*/
        // 缓存数据拼装
        resolveUrls: function () {
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
            var url = this.buildUrl();
            return url;
        },
        buildUrl: function () {
            var ps = this.criterionData.criterions.concat(this.criterionData.query);
            var page = [];
            var sorts = this.criterionData.sorts;
            var others = this.criterionData.others;
            var custom = this.criterionData.customCriteria;
            var rtnData = null;
            if (this.source.url) {
                // 匹配替换参数，并去除重复参数
                this.source.url = this.source._url.replace(/\!\{([\w\.]*)\}/gi,
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
                    if (this.source.url.indexOf('?') > 0) {
                        this.source.url += "&" + urlParam;
                    } else {
                        this.source.url += "?" + urlParam;
                    }
                }
                // 重复参数采取替换方式
                this.source.url = replaceParams(page, this.source.url);
                if (sorts.length > 0) {
                    this.source.url = replaceParams(sorts, this.source.url);
                } else {
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
                            this.source.url += "&" + rtnData;
                        }
                    }
                }
                if (others)
                    this.source.url = replaceParams(others,
                        this.source.url);
                if (window.console) {
                    console.info('resPanel:source:url::\n', this.source.url);
                }
            }
            return this.source.url;
        },
        // 构建随机数，用于无缓存刷新
        buildRag: function () {
            return {
                "key": "__seq",
                "value": [(new Date()).getTime()]
            };
        },
        getModelingLang :function (){
            return modelingLang;
        }

    });

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


    exports.ResPanel = ResPanel;
})