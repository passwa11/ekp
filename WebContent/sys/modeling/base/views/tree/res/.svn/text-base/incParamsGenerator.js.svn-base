/**
 * 入参组件
 */
define(function(require, exports, module) {

    var $ = require('lui/jquery'),
        base = require('lui/base'),
        topic = require('lui/topic');
    var modelingLang = require("lang!sys-modeling-base");
    var IncParamsGenerator = base.Component.extend({

        initProps: function($super, cfg) {
            $super(cfg);
            this.randomName = "fd_" + parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
            this.container = cfg.container;
            this.modelId = cfg.modelId;
            this.targetModelData = cfg.targetModelData || {};
        },
        getInitDataByModelId: function(modelId){
            var self = this;
            var dictBeanValue = this.getDictBeanValue(modelId);
            return this.loadDictProperties(dictBeanValue);
        },
        //根据数据字典获取下拉框中的内容
        loadDictProperties: function (dictBeanValue) {
            var url = dictBeanValue;
            var kmssData = new KMSSData();
            var datas = kmssData.AddBeanData(url).GetHashMapArray();
            if(!datas[0])
                return "";
            var arrStr = datas[0].key0;
            if(!arrStr)
                return "";
            var dataArray = $.parseJSON(this.getValidJSONArray(arrStr));
            if(!dataArray){
                return "";
            }
            return dataArray;
        },
        //拼接URL：根据业务模块ID查：业务模块字段的数据字典
        getDictBeanValue : function (v){
            var dictBeanValue = "modelingAppListviewModelDictService&fdAppModelId=!{cateid}&modelDictOnly=true";
            dictBeanValue = decodeURIComponent(dictBeanValue);
            dictBeanValue = this.replaceCateid(dictBeanValue, v);
            return dictBeanValue;
        },
        getValidJSONArray : function (arr){
            /*if(!arr || !arr.startsWith("[") || !arr.endsWith("]")){
                return "[]";
            }*/
            var startStr = "\\[";
            var endStr = "\\]";
            var startReg =new RegExp("^"+startStr);
            var endReg = new RegExp(endStr+"$");
            if(!arr || !startReg.test(arr) || !endReg.test(arr)){
                return "[]";
            }
            return arr;
        },
        replaceCateid: function (url, cateid,isMobile,tabId) {
            var re = /!\{cateid\}/gi;
            url = url.replace(/!\{cateid\}/gi, cateid);
            if(isMobile){
                url = url.replace(/!\{tabid\}/gi, tabId);
            }
            return url;
        },
        onFdListviewChange : function(v,selectedArray,isMobile,tabId){
            var url = this.getListviewIncParamsBeanValue(v,isMobile,tabId);
            var kmssData = new KMSSData();
            var datas = kmssData.AddBeanData(url).GetHashMapArray();
            var arrStr = datas[0].key0;
            if(!arrStr)
                return "";
            var dataArray = $.parseJSON(this.getValidJSONArray(arrStr));
            if(!dataArray){
                return "";
            }
            if(dataArray.length == 0) {
                var fdTreeNoteViewType =this.container.find("[name='fdTreeNoteViewType']").val();
                switch (fdTreeNoteViewType) {
                    case "mindMap":
                        //思维导图未支持入参
                        dataArray = this.IsCollectionViewIncParams(v,isMobile,tabId);
                        break;
                    case "gantt":
                        dataArray = this.IsGanttViewIncParams(v,isMobile,tabId);
                        break;
                    case "resPanel":
                        //资源面板不能配置入参，也未支持
                        dataArray = this.IsCollectionViewIncParams(v,isMobile,tabId);
                        break;
                    case "listView":
                        dataArray = this.IsCollectionViewIncParams(v,isMobile,tabId);
                        break;
                    case "collection":
                        dataArray = this.IsCollectionViewIncParams(v,isMobile,tabId);
                        break;
                    case "calendar":
                        dataArray = this.IsCalendarViewIncParams(v,isMobile,tabId);
                        break;
                    default:
                        dataArray = this.IsCollectionViewIncParams(v,isMobile,tabId);
                        break;
                }

            }
            //根据参数在下方绘制
            var html = this.getListviewIncParamsHtml(dataArray, selectedArray);
            this.container.append(html);
        },
        getListviewIncParamsBeanValue : function(v,isMobile,tabId){
            if(isMobile){
                var dictBeanValue = "modelingAppMobileListViewService&method=getIncParams&fdId=!{cateid}&fdMobileTabId=!{tabid}";
            }else{
                var dictBeanValue = "modelingAppListviewService&method=getIncParams&fdId=!{cateid}";
            }
            dictBeanValue = decodeURIComponent(dictBeanValue);
            dictBeanValue = this.replaceCateid(dictBeanValue, v,isMobile,tabId);
            return dictBeanValue;
        },
        replaceCateid : function(url, cateid,isMobile,tabId) {
            var re = /!\{cateid\}/gi;
            url = url.replace(/!\{cateid\}/gi, cateid);
            if(isMobile){
                url = url.replace(/!\{tabid\}/gi, tabId);
            }
            return url;
        },
        IsCollectionViewIncParams : function(v,isMobile,tabId) {
            var collectionUrl = this.getCollectionListviewIncParamsBeanValue(v,isMobile,tabId);
            var kmssData = new KMSSData();
            var datasCollection = kmssData.AddBeanData(collectionUrl).GetHashMapArray();
            if(datasCollection[0]){
                var arrStr = datasCollection[0].key0;
                if(!arrStr)
                    return "";
                var collectionDataArray = $.parseJSON(this.getValidJSONArray(arrStr));
                if(!collectionDataArray){
                    return "";
                }
            }
            return collectionDataArray;
        },
        IsCalendarViewIncParams : function(v,isMobile,tabId) {
            var collectionUrl = this.getCalendarIncParamsBeanValue(v,isMobile,tabId);
            var kmssData = new KMSSData();
            var datasCollection = kmssData.AddBeanData(collectionUrl).GetHashMapArray();
            if(datasCollection[0]){
                var arrStr = datasCollection[0].key0;
                if(!arrStr)
                    return "";
                var collectionDataArray = $.parseJSON(this.getValidJSONArray(arrStr));
                if(!collectionDataArray){
                    return "";
                }
            }
            return collectionDataArray;
        },
        IsGanttViewIncParams :  function(v,isMobile,tabId) {
            var ganttUrl = this.getGanttviewIncParamsBeanValue(v,isMobile,tabId);
            var kmssData = new KMSSData();
            var datasCollection = kmssData.AddBeanData(ganttUrl).GetHashMapArray();
            if(datasCollection[0]){
                var arrStr = datasCollection[0].key0;
                if(!arrStr)
                    return "";
                var collectionDataArray = $.parseJSON(this.getValidJSONArray(arrStr));
                if(!collectionDataArray){
                    return "";
                }
            }
            return collectionDataArray;
        },
        getCollectionListviewIncParamsBeanValue : function(v,isMobile,tabId){
            var dictBeanValue = "modelingAppCollectionViewService&method=getIncParams&fdId=!{cateid}";
            dictBeanValue = decodeURIComponent(dictBeanValue);
            dictBeanValue = this.replaceCateid(dictBeanValue, v,isMobile,tabId);
            return dictBeanValue;
        },
        getGanttviewIncParamsBeanValue : function(v,isMobile,tabId){
            var dictBeanValue = "modelingGanttService&method=getIncParams&fdId=!{cateid}";
            dictBeanValue = decodeURIComponent(dictBeanValue);
            dictBeanValue = this.replaceCateid(dictBeanValue, v,isMobile,tabId);
            return dictBeanValue;
        },
        getCalendarIncParamsBeanValue : function(v,isMobile,tabId){
            var dictBeanValue = "modelingCalendarService&method=getIncParams&fdId=!{cateid}";
            dictBeanValue = decodeURIComponent(dictBeanValue);
            dictBeanValue = this.replaceCateid(dictBeanValue, v,isMobile,tabId);
            return dictBeanValue;
        },
        getListviewIncParamsHtml : function(dataArray, selectedArray){
            var html = "";
            var selectedArrayIndex = [];
            html += "<div class='incparams'>"+modelingLang['modelingTreeView.parameter'] ;
            for(var i = 0;i < dataArray.length;i++){
                var data = dataArray[i];
                //需要选中的值
                var selected = this.getSelectedValue(data, selectedArray, selectedArrayIndex);
                //地址本类型："com.landray.kmss.sys.organization.model.SysOrgPerson|String"，
                //但mainModelOptionDataArray里面是com.landray.kmss.sys.organization.model.SysOrgPerson,故分隔
                var fieldType = data.fieldType.split("|")[0];
                /*var fieldOptionHtml = "<option value=''>==请选择==</option>";
                if(!$.isEmptyObject(this.targetModelData)){
                    for(var key in this.targetModelData){
                        //过滤明细表的字段
                        if(this.targetModelData[key].name.indexOf(".") > 0){
                            continue;
                        }
                        fieldOptionHtml += "<option value='"+key+"'";
                        if(selected == key){
                            fieldOptionHtml += "selected='selected'";
                        }
                        fieldOptionHtml += ">"+this.targetModelData[key].label+"</option>";
                    }
                }*/
                var arr = this.getInitDataByModelId(this.modelId);
                var fieldOptionHtml = this.getOptionHtmlByDataArray(arr, selected,true,fieldType);
                html += "<div class='incparamsField'><div class='incparamsText'>" + data.paramText + "&nbsp;" + data.operator;
                html += "</div><select name='fdTreeNodeViewIncParams' data-param='" + data.paramName + "' class='inputsgl' style='width: 52%;height: 32px !important;'>" + fieldOptionHtml + "</select>";
                html += "</div>";
            }
            var subTableId = "";
            if(this.targetModelId) {
                for (var i = 0; i < this.targetModelId.length; i++) {
                    if (selected && selected == this.targetModelId[i].field) {
                        subTableId = this.targetModelId[i].subTableId;
                    }
                }
            }	html += "<input type='hidden'name='fdTreeNodeViewIncParamsSubTableId' value='"+subTableId+"'>";
            return html;
        },
        getSelectedValue : function(data, selectedArray, selectedArrayIndex){
            if(selectedArray){
                for(var i in selectedArray){
                    var selectedJSON = selectedArray[i];
                    if(selectedJSON.param == data.paramName && !(selectedArrayIndex.indexOf(i) != -1)){
                        selectedArrayIndex.push(i);
                        return selectedJSON.field;
                    }
                }
            }
        },
        getOptionHtmlByDataArray : function(dataArray, selected,isListviewIncParam,fieldType){
            if(!dataArray){
                return "";
            }
            var html = "";
            html += "<option value=''>"+modelingLang['relation.please.choose']+"</option>";
            for(var i = 0;i < dataArray.length;i++){
                var data = dataArray[i];
                if(!isListviewIncParam){
                    //屏蔽明细表
                    if (data.isSubTableField === "true")
                        continue
                }
                //明细表入参时下拉列表的选择进行过滤
                if(fieldType){
                    if(fieldType == data.fieldType){
                        html += "<option value='" + data.field + "'";
                        if (data.isSubTableField === "true"){
                            html += "data-subtableid='"+ data.subTableId + "'";
                        }
                        if(selected && selected == data.field){
                            html += " selected ";
                        }
                        html += ">" + data.fieldText + "</option>";
                    }
                }else{
                    html += "<option value='" + data.field + "'";
                    if(selected && selected == data.field){
                        html += " selected ";
                    }
                    html += ">" + data.fieldText + "</option>";
                }
            }
            return html;
        },
        getValidJSONArray : function(arr){
            /*if(!arr || !arr.startsWith("[") || !arr.endsWith("]")){
                return "[]";
            }*/
            var startStr = "\\[";
            var endStr = "\\]";
            var startReg =new RegExp("^"+startStr);
            var endReg = new RegExp(endStr+"$");
            if(!arr || !startReg.test(arr) || !endReg.test(arr)){
                return "[]";
            }
            return arr;
        },
        getKeyData : function () {
            var self = this;
            var incParamsArr = [];
            this.container.find("[name='fdTreeNodeViewIncParams']").each(function () {
                var incParamsObj = {
                    "param": $(this).attr("data-param"),
                    "field": $(this).find("option:selected").val()
                };
                incParamsArr.push(incParamsObj);
            })
            return incParamsArr;
        }
    })

    module.exports = IncParamsGenerator;

})