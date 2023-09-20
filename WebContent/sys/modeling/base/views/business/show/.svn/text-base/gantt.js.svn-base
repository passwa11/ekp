/**
 *
 */
define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var topic = require("lui/topic");
    var source = require("lui/data/source");
    var render = require("lui/view/render");
    var env = require('lui/util/env');

    var SORT_CHANGED = 'sort.changed';
    var PAGING_CHANGED = 'paging.changed';// 监听分页事件
    var Gantt = base.DataView.extend({
        /*--------------------------------组件渲染及事件---------------------------*/
        initProps: function ($super, cfg) {
            $super(cfg);
            this.criterionData = {
                sorts: [],
                query: [],
                page:[],
                criterions: [],
                lock: false,
                customCriteria: {},//自定义事件
                cacheEvt: []// 缓存事件，防止重复初始化
            };
            this.recordData = [];
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
            //订阅改变排序事件
            topic.subscribe(SORT_CHANGED, this.orderChange, self);
            //订阅刷新事件
            topic.subscribe(PAGING_CHANGED, this.pagingChanged, self);
            //订阅自定义颜色事件
            topic.subscribe("customColor.submit", this.customColorSubmit,self);
            //订阅列表刷新事件
            topic.subscribe("list.refresh",  this.listRefresh, self);

        },
        doRender: function ($super, cfg) {
            $super(cfg);
            var self = this;
            //数据-点击绑定弹窗事件
            this.element.find(".res_calendar_data_dom").on("click", function (e) {
                e.stopPropagation();
                var hasClass = $(this).find(".meeting_calendar_dialog").hasClass("dialogHidden");
                self.element.find(".meeting_calendar_dialog").addClass("dialogHidden");
                if (hasClass) {
                    $(this).find(".meeting_calendar_dialog").removeClass("dialogHidden");
                }
            });
            $("body").on("click", function () {
                $(".meeting_calendar_dialog").addClass("dialogHidden");
            });
            //刷新按钮绑定事件
            this.element.find(".lui_calendar_header_refresh").on("click", function () {
                self.load();
            });
            //周、日切换绑定事件
            this.element.find(".lui_calendar_header_month").on("click", function () {
                var $this = $(this);
                var opt = $this.attr("cal-opt");
                self.criterionData.customCriteria.headerCategory = opt;
                self.customCriteriaChange();

            });
            if(gantt_data && gantt_data.table && gantt_data.table.timeData && gantt_data.table.timeData.defaultValue){
                $("#timeDimensionSelect").val(gantt_data.table.timeData.defaultValue);
            }
            $("#timeDimensionSelect").change(function(){
                var timeDimension=($(this).val());
                if(gantt_data && gantt_data.table && gantt_data.table.timeData){
                    gantt_data.table.timeData.defaultValue = timeDimension;
                }
                window.init(gantt_data.table.timeData,gantt_data.table.missionData,gantt_data.fdEnableFlow,gantt_data.fdModelName);
            });

        },
        refresh: function (params) {
            this.erase();
            this.draw(params);
        },
        /*--------------------------------订阅处理---------------------------*/
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

        orderChange: function (evt) {
            if (!this.isDrawed) {
                this.criterionData.cacheEvt.push(evt);
                return;
            }
            if (!this.loading) {
                this.element.css('min-height', 200);
                if(this.element.is(':visible')){
                    this.loading = dialog.loading(null, this.element);
                }
            }
            this.setRefreshIcon();
            this.criterionData.cacheEvt.push(evt);
            this.resolveUrls();
            this.load();
        },

        pagingChanged: function (evt) {
            this.setRefreshIcon();
            this.criterionData.cacheEvt.push(evt);
            this.resolveUrls();
            this.load();
        },
        customColorSubmit: function (evt) {
            this.setRefreshIcon();
            this.criterionData.cacheEvt.push(gantt_data.criterionData);
            // debugger;
            this.resolveUrls();
            this.load();
        },
        listRefresh: function (evt) {
            this.resolveUrls();
            this.load();
        },
        setRefreshIcon: function (){
            var height = $('#ganttContent').height()/2+$('#criteria1').height()+$('.model-gantt-tab').height();
            this.loading = $("<img src='"+env.fn.formatUrl('/sys/ui/js/ajax.gif')+"' />");
            this.loading.css({
                "position": "absolute",
                "top":height,
                "left":"50%",
                "z-index":"999",
                "width":"40px",
                "height":"40px"
            })
        },
        // draw: function ($super, params) {
        //     $super(params);
        // },
        /*--------------------------------数据处理---------------------------*/
        setRecordData: function (data) {
            this.recordData = data || [];	//[]
        },


        /*--------------------------------工具方法---------------------------*/
        // 缓存数据拼装
        resolveUrls: function () {
            var cache = this.criterionData.cacheEvt, ps;
            for (var kk in cache) {
                if (cache[kk].query){
                    this.criterionData.query = cache[kk].query;
                    gantt_data.criterionData.query = cache[kk].query;
                }
                if (cache[kk].criterions){
                    this.criterionData.criterions = cache[kk].criterions;
                    gantt_data.criterionData.criterions = cache[kk].criterions;
                }
                if (cache[kk].page){
                    this.criterionData.page = cache[kk].page;
                    gantt_data.criterionData.page = cache[kk].page;
                }
                if (cache[kk].sorts){
                    this.criterionData.sorts = cache[kk].sorts;
                    gantt_data.criterionData.sorts = cache[kk].sorts;
                }
                if (cache[kk].others){
                    this.criterionData.others = cache[kk].others;
                    gantt_data.criterionData.others = cache[kk].others;
                }
            }
            var url = this.buildUrl();
            return url;
        },
        buildUrl: function () {
            var ps = this.criterionData.criterions.concat(this.criterionData.query);
            var page = this.criterionData.page;
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
                // if (window.console) {
                //     console.info('Gantt:source:url::\n', this.source.url);
                // }
            }
            return this.source.url;
        },
        // 构建随机数，用于无缓存刷新
        buildRag: function () {
            return {
                "key": "__seq",
                "value": [(new Date()).getTime()]
            };
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

    exports.Gantt = Gantt;
})