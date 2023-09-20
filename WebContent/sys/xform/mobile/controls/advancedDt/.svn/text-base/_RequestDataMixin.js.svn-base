/**
 *
 */
define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/query", "mui/util", "dojo/request", "dojo/dom-construct", "dojo/dom-style", 'dojo/_base/lang',],
    function (declare, WidgetBase, query, util, request, domConstruct, domStyle, lang) {
        var claz = declare("sys.xform.mobile.controls.advancedDt._RequestDataMixin", [WidgetBase], {

            defaultUrl: "/sys/xform/detailsTable/sysFormDetailsTableData.do?method=data&mobile=true&formId=!{formId}&fdOriginControlId=!{fdOriginControlId}&modelId=!{modelId}&controlId=!{controlId}&pageno=!{pageno}&rowsize=!{pageSize}&docStatus=!{docStatus}&modelName=!{mainModelName}&xformRight=!{xformRight}&optType=!{optType}&formFilePath=!{formFilePath}",

            addOrUpdateUrl: "/sys/xform/detailsTable/sysFormDetailsTableData.do?method=addOrUpdate",

            deleteRowUrl: "/sys/xform/detailsTable/sysFormDetailsTableData.do?method=delete&fdId=!{fdId}&fdFormId=!{fdFormId}&fdControlId=!{fdControlId}",

            batchDeleteRowUrl: "/sys/xform/detailsTable/sysFormDetailsTableData.do?method=deleteByIds",

            startup: function () {
                this.inherited(arguments);
                this.requestData(this.onDataLoad);
            },

            requestData: function (callback) {
                var self = this;
                var url = util.formatUrl(util.urlResolver(this.defaultUrl, this));
                this.buildLoading();
                request.post(url, {handleAs: 'text'}).then(function (json) {
                    if (typeof callback != "undefined") {
                        callback.call(self, json);
                        domStyle.set(self.tmpLoading, "display", "none");
                    }
                });
            },

            buildLoading: function () {
                if (this.tmpLoading == null) {
                    this.tmpLoading = domConstruct.create("div", {
                        className: 'muiCateLoading', style: {
                            display: "inline-block"
                        }, innerHTML: '<i class="mui mui-loading mui-spin"></i>'
                    }, this.domNode);
                } else {
                    domStyle.set(this.tmpLoading, "display", "inline-block");
                }
            },

            submit: function (callback, params) {
                var self = this;
                var url = util.formatUrl(util.urlResolver(this.addOrUpdateUrl, this));
                request.post(url, {data: params, handleAs: 'json'}).then(function (json) {
                    if (typeof callback != "undefined") {
                        callback.call(self, json);
                    }
                });
            },

            delRowById: function (rowId) {
                var context = {fdId: rowId, fdFormId: this.formId, fdControlId: this.controlId};
                var url = util.formatUrl(util.urlResolver(this.deleteRowUrl, context));
                request.get(url, {handleAs:'json',sync:true}).then(function(json) {
                    if(json && json.code == 1){

                    }
                });
            }
        });
        return claz;
    });