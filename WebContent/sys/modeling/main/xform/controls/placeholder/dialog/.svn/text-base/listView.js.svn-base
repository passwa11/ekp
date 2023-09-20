define(function (require, exports, module) {

    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require("lui/dialog");
    var topic = require('lui/topic');
    var dateUtil = require('lui/dateUtil');
    var env = require('lui/util/env');
    var paging = require("sys/modeling/main/xform/controls/placeholder/dialog/paging");
    var modelingLang = require("lang!sys-modeling-main");
    var listView = base.DataView.extend({

        formData: null,	// 表单页面的表单数据

        search: {},

        sort:{},

        fetchUrl: "/sys/modeling/main/modelingAppXFormMain.do?method=executeQuery&pageno=!{currentPage}&rowsize=!{rowsize}",

        pageCfg: {},

        requestData: {},

        initProps: function ($super, cfg) {
            $super(cfg);
            this.pageCfg.currentPage = '1';
            this.pageCfg.rowsize = '10';
        },

        startup: function ($super, cfg) {
            $super(cfg);
            topic.channel("placeholder").subscribe("search", this.doSearch, this);
            topic.channel("placeholder").subscribe("sort", this.doSort, this);
        },

        init: function (params) {
            this.formData = params.formDatas;
            this.widgetId = params.widgetId;
            this.fdAppModelId = params.fdAppModelId;
            this.cfgInfo = params.cfgInfo;
            this.initSouce();
        },

        initSouce: function () {
            this.source.url = this.source._url = this.fetchUrl + "&fdAppModelId=" + this.fdAppModelId + "&widgetId=" + this.widgetId;
        },

        // 拉取数据
        fetch: function (evt) {
            if (!evt) {
                evt = this.pageCfg;
            }
            this.source.resolveUrl(evt);
            // 设置设置
            this.source.params = {ins: JSON.stringify(this.formData), search: JSON.stringify(this.search),sort: JSON.stringify(this.sort)};
            this.source.get();
        },

        doSearch: function (data) {
            this.search = data.searchData;
            this.fetch();
        },

        doSort: function (data) {
            this.sort = data.sortData;
            this.fetch();
        },

        // 请求完数据之后更新数据
        onDataLoad: function ($super, data) {
            if (data && data.page) {
                this.pageCfg.totalSize = data.page.totalSize;
            }
            if (data.page.totalSize === 0) {
                var noDataTipHtml = this._buildNoDataTipContent();
                this.doNoRecordRender();
            } else {
                this.render.get(data);
            }
        },

        doNoRecordRender: function () {
            var self = this;
            var __url = '/resource/jsp/listview_norecord.jsp?_=' + new Date().getTime();
            $.ajax({
                url: env.fn.formatUrl(__url),
                dataType: 'text',
                success: function (data, textStatus) {
                    self.doRender(data);
                    // 对无记录页面的样式进行调整
                    self.element.find(".prompt_container").css("padding-top", "1px");
                }
            });
        },

        // 缓存请求数据
        storeTemp: function (data) {
            //this.formatData(data);
            this.requestData = data;
        },

        // 渲染完毕之后添加事件
        doRender: function ($super, cfg) {
            $super(cfg);
            var self = this;
            /**************** 分页插件 start *****************/
            // 由于分页块放置外render外面不合适（分页参数不是通过page获得），只能放置在模板HTML里面，故渲染完毕需要重新new分页组件
            self.paging = new paging.Paging({
                parent: self,
                element: self.element.find(".lui-listview-foot-paging"),
                currentPage: self.pageCfg.currentPage,
                pageSize: self.pageCfg.rowsize,
                totalSize: self.pageCfg.totalSize,
                viewSize: "2"
            });
            self.paging.startup();
            self.paging.draw();
            /**************** 分页插件 end *****************/
            /**************** 添加事件 start *****************/
            var multi = self.config.multi === "true" ? true : false;
            if (!multi) {
                self.element.find(".lui-listview-table-tr").on("click", function (evt) {
                    var rowIndex = $(this).attr("data-rowindex");
                    self.hide(self.getRowInfo(rowIndex));
                });
            } else {
                // 全选
                self.element.find("[name='List_Select_All']").on("change", function () {
                    if ($(this).prop("checked")) {
                        self.element.find("[name='List_Selected']").prop("checked", true);
                        self.pushAllSelected(true);
                    } else {
                        self.element.find("[name='List_Selected']").prop("checked", false);
                        self.pushAllSelected(false);
                    }
                });
                self.element.find(".lui-listview-table-tr").find('td:gt(0)').on("click", function (evt) {
                    var $checkbox = $(this).closest('tr').find("[name='List_Selected']");
                    $checkbox.prop('checked', !$checkbox.prop('checked'));
                });
            }
            // 穿透跳转
            self.element.find(".drilling-link").on("click", function (event) {
                event.stopPropagation();
                var rowIndex = $(this).closest("tr.lui-listview-table-tr").attr("data-rowindex");
                var rowInfo = self.getRowInfo(rowIndex);
                var targetUrl = self.getDrillingUrl(rowInfo);
                if (targetUrl) {
                    Com_OpenWindow(targetUrl, "_blank");
                } else {
                    dialog.alert(modelingLang['modeling.error.obtaining.penetration.link']);
                }
            });

            /**************** 添加事件 end *****************/
        },

        hide: function (value) {
            $dialog.hide(value);
        },

        // 多选
        // TODO 没法处理选择分页的多行记录
        selectedRowsMap: {},
        changeSelected: function (rowIdx) {
            var row = this.getRowInfo(rowIdx);
            var showDetail = this.requestData.showDetail;
            var rowKey = "";
            if (row) {
                if(showDetail && row["detailFdId"] ){
                    rowKey = row["detailFdId"];
                    //用明细表id作唯一值校验,记录之后需要删除明细id,选择回填时不需要,会报错
                    delete row["detailFdId"];
                }else if(row["fdId"] && row.fdId["value"]){
                    rowKey = row.fdId.value;
                }
                if(rowKey != ""){
                    if (this.selectedRowsMap[rowKey]) {
                        delete this.selectedRowsMap[rowKey];
                    } else {
                        this.selectedRowsMap[rowKey] = row;
                    }
                }
            }

            return this.selectedRowsMap;
        },
        pushAllSelected: function (checked) {
            var self = this;
            var showDetail = this.requestData.showDetail;
            this.element.find("[name='List_Selected']").each(function(){
                var row = self.getRowInfo($(this).closest("tr").attr("data-rowindex"))
                if (row) {
                    var rowKey = "";
                    if(showDetail && row["detailFdId"] ){
                        rowKey = row["detailFdId"];
                        //用明细表id作唯一值校验,记录之后需要删除明细id,选择回填时不需要,会报错
                        delete row["detailFdId"];
                    }else if(row["fdId"] && row.fdId["value"]){
                        rowKey = row.fdId.value;
                    }
                    if(rowKey != ""){
                        if (!checked) {
                            if (self.selectedRowsMap[rowKey]){
                                delete self.selectedRowsMap[rowKey];
                            }
                        } else {
                            self.selectedRowsMap[rowKey] = row;
                        }
                    }
                }
            });

            return this.selectedRowsMap;
        },
        getRowsInfo: function () {
            // var self = this;
            var rowInfos = [];
            for (var k in this.selectedRowsMap) {
                rowInfos.push(this.selectedRowsMap[k]);
            }
            return rowInfos;
        },


        getRowInfo: function (rowIndex) {
            var columns = this.requestData.columns;
            var showDetail = this.requestData.showDetail;
            // 行信息
            var rowInfo = {};
            //明细表数据
            var detailData = [];
            if(columns.length > 0){
                var detailField = this.requestData.detailField;
                for (var i = columns.length-1; i >= 0; i--) {
                    var col = columns[i];
                    if (col.type === "detail" && col.name===detailField) {
                        detailData = col.data;
                        break;
                    }
                }
            }
            if(showDetail && detailData.length>0){
                rowInfo = this.getDetailRow(rowIndex,columns,detailData);
            }else{
                for (var i = 0; i < columns.length; i++) {
                    var col = columns[i];
                    rowInfo[col.name] = {value: col.data[rowIndex]};
                    // 明细表
                    rowInfo[col.name].type = col.type;
                    if (col.type === "detail") {
                        // 列定义
                        rowInfo[col.name].columns = col.columns;
                    }
                }
            }
            return rowInfo;
        },

        getDetailRow:function (rowIndex,columns,detailData){
            // 行信息
            var rowInfo = {};
            //主表行数
            var rowLength = 0;
            if(columns.length > 0 && columns[0].data && columns[0].data.length > 0){
                rowLength = columns[0].data.length;
            }
            var index = 0;
            for (var i = 0; i < rowLength; i++) {
                for(var m=0;m < detailData.length;m++) {
                    var detail = detailData[m];
                    if (detail.length > 0 && columns[0].data[i] === detail[0].mainModelId) {
                        for (var n = 0; n < detail.length; n++) {
                            if(index == rowIndex){
                                var dData = [];
                                dData.push(detail[n]);
                                //存储选中的明细表数据和对应的主表数据
                                for(var j = 0 ; j< columns.length;j++){
                                    var col = columns[j];
                                    rowInfo[col.name] = {value: col.data[i]};
                                    // 明细表
                                    rowInfo[col.name].type = col.type;
                                    if (col.type === "detail") {
                                        // 列定义
                                        rowInfo[col.name].columns = col.columns;
                                        rowInfo[col.name].value = dData;
                                    }
                                }
                                //用明细表id作唯一值校验
                                rowInfo["detailFdId"] = detail[n].fdId;
                                return rowInfo;
                            }
                            index++;
                        }
                    }
                }
            }
            return rowInfo;
        },

        // 翻页更新页数
        pageChange: function (evt) {
            this.pageCfg.currentPage = (evt.pageno && evt.pageno > 0) ? evt.pageno : "1";
            this.pageCfg.rowsize = (evt.rowsize && evt.rowsize > 0) ? evt.rowsize : "10";
            this.pageCfg.totalSize = evt.totalSize;
            this.fetch();
        },

        // 是否列表穿透
        isDrilling: function () {
            var throughList = this.cfgInfo.throughList || {};
            return throughList.isThrough === true ? true : false;
        },

        getDrillingUrl: function (info) {
            info = info || {};
            var url = "";
            var throughList = this.cfgInfo.throughList || {};
            if (throughList.isThrough) {
                var tmpUrl = throughList.url;
                var idInfo = info.fdId || {};
                if (idInfo.value && tmpUrl) {
                    url = env.fn.formatUrl(tmpUrl.replace(/\:fdId/g, idInfo.value));
                } else {
                    console.error("【业务关联控件】获取穿透跳转链接失败!跳转的模板链接:" + tmpUrl + ";fdId:" + idInfo.value);
                }
            }
            return url;
        },
        //获取多语言资源
        getModelingLang :function (){
            return modelingLang;
        }

    });

    exports.listView = listView;
})