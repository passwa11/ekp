/**
 * 移动列表视图
 */
define([
    "dojo/_base/declare",
    "mui/list/HashJsonStoreList",
    'dojo/topic',
    'dojox/mobile/viewRegistry',
    "mui/util"
], function (declare, HashJsonStoreList, topic, viewRegistry, util) {

    return declare("sys.modeling.main.xform.controls.filling.mobile.HashJsonStoreList", [HashJsonStoreList], {

        itemRenderer: null,

        customBuildDatas: function (datas) {
            var scroll = viewRegistry.getEnclosingScrollable(this.domNode);
            if (!scroll) {
                scroll = viewRegistry.getEnclosingView(this.domNode);
            }
            if (!scroll) {
                return;
            }
            var columns = datas.columns;
            var result = [];
            result = this.getDatas(columns,datas);
            datas.datas=result;
        },

        resolveItems: function (items) {

            this._loadOver = false;
            var page = {};
            this.customBuildDatas(items);
            if (items) {
                if (items['datas']) {//分页数据
                    this.listDatas = items["datas"];//this.formatDatas(items['datas']);
                    page = items['page'];
                    if (page) {
                        this.pageno = parseInt(page.pageno, 10) + 1;
                        this.rowsize = parseInt(page.rowsize, 10);
                        this.totalSize = parseInt(page.totalSize, 10);
                        if (parseInt(page.totalSize || 0, 10) <= (this.pageno - 1) * this.rowsize) {
                            this._loadOver = true;
                        }
                    }
                } else {//直接数据,不分页
                    this.listDatas = items;
                    this.totalSize = items.length;
                    this.pageno = 1;
                    this._loadOver = true;
                }
            }

            if (this._loadOver) {
                topic.publish('/mui/list/pushDomHide', this);
            } else {
                topic.publish('/mui/list/pushDomShow', this);
            }

            return this.listDatas;
        },
        getDatas: function (columns,requestDatas) {
            var datas = [];
            //主表行数
            var rowLength = 0;
            if(columns.length > 0 && columns[0].data && columns[0].data.length > 0){
                rowLength = columns[0].data.length;
            }
            //#171368 【日常缺陷】【低代码平台-修复】业务关联控件，PC选择正常，移动没有值
            var detailData = [];
            if(columns.length > 0){
                for (var i = columns.length-1; i >= 0; i--) {
                    var col = columns[i];
                    if (col.type === "detail" && col.name===requestDatas.detailField) {
                        detailData = col.data;
                        break;
                    }
                }
            }
            if(requestDatas.showDetail && detailData.length > 0){
                datas = this.getDetailDatas(rowLength,columns,requestDatas,detailData);
            }else{

                for(var j= 0;j<rowLength;j++){
                    var rowInfo = {};
                    var row ={};
                    for (var i = 0; i < columns.length; i++) {
                        var col = columns[i];
                        rowInfo[col.name] = {value: col.data[j]};
                        // 明细表
                        rowInfo[col.name].type = col.type;
                        rowInfo[col.name].hidden = col.hidden;
                        rowInfo[col.name].name = col.name;
                        rowInfo[col.name].title = col.title;
                        rowInfo[col.name].isDetail = false;
                        rowInfo[col.name].businessType = col.businessType;
                        if (col.type === "detail") {
                            // 列定义
                            rowInfo[col.name].columns = col.columns;
                        }
                    }
                    row["rowInfo"] = rowInfo;
                    row["columnIndex"] = i;
                    row["columns"] = columns;
                    row["relationId"] = requestDatas.relationId;
                    row["widgetId"] = requestDatas.widgetId;
                    row["throughList"] = requestDatas.throughList;
                    datas.push(row);
                }

            }
            return datas;
        },

        getDetailDatas:function (rowLength,columns,requestDatas,detailData){
            //明细表数据
            var datas = [];
            for (var i = 0; i < rowLength; i++) {
                for(var m=0;m < detailData.length;m++) {
                    var detail = detailData[m];
                    if (detail.length > 0 && columns[0].data[i] === detail[0].mainModelId) {
                        for (var n = 0; n < detail.length; n++) {
                            // 行信息
                            var rowInfo = {};
                            var row ={};
                            var dData = [];
                            dData.push(detail[n]);
                            //存储选中的明细表数据和对应的主表数据
                            for(var j = 0 ; j< columns.length;j++){
                                var col = columns[j];
                                rowInfo[col.name] = {value: col.data[i]};
                                // 明细表
                                rowInfo[col.name].type = col.type;
                                rowInfo[col.name].hidden = col.hidden;
                                rowInfo[col.name].name = col.name;
                                rowInfo[col.name].title = col.title;
                                rowInfo[col.name].businessType = col.businessType;

                                if (col.type === "detail") {
                                    // 列定义
                                    rowInfo[col.name].columns = col.columns;
                                    rowInfo[col.name].value = dData;
                                }
                            }
                            //用明细表id作唯一值校验
                            row["detailFdId"] = detail[n].fdId;
                            row["rowInfo"] = rowInfo;
                            row["columnIndex"] = i;
                            row["columns"] = columns;
                            row["relationId"] = requestDatas.relationId;
                            row["widgetId"] = requestDatas.widgetId;
                            row["throughList"] = requestDatas.throughList;
                            datas.push(row);
                        }
                    }
                }
            }
            return datas;
        },

    });
});


