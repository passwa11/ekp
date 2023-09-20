// 高级明细表
define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct",'dojo/dom','dojo/_base/lang',
        "mui/util", "sys/xform/mobile/controls/advancedDt/_RequestDataMixin","dojo/dom-class",
        "sys/xform/mobile/controls/advancedDt/Paging","dojo/dom-attr",
        "dojo/dom-form","dojox/mobile/viewRegistry","dojo/_base/array",
        "sys/attachment/mobile/js/AttachmentList", "dijit/registry", "mui/form/_FormBase",
        "sys/xform/mobile/controls/detailsTable/mobileDetailsTableScript",
        "mui/base64", "dojo/topic", "dojo/query", "dojo/parser", "dojo/dom-style"],
    function (declare, WidgetBase, domConstruct, dom, lang, util, _RequestDataMixin, domClass, Paging, domAttr, domForm,
              viewRegistry, array, AttachmentList, registry, FormBase, mobileDetailsTableScript, base64, topic, query, parser, domStyle) {
        var claz = declare("sys.xform.mobile.controls.advancedDt.DetailsTable", [WidgetBase, _RequestDataMixin], {

            pageno: "1",

            pageSize: "5",

            totalSize: "0",

            adDetail : true,

            controlId: null,

            mainModelName: null,

            fdKey: null,

            fdOriginControlId: "",

            formFilePath: "",

            docStatus: null,

            xformRight: "",

            right: "",

            //默认展开
            expand: true,

            buildRendering: function () {
                this.mainModelName = _xformMainModelClass;
                this.modelId = _xformMainModelId;
                this.formFilePath = Xform_ObjectInfo.formFilePath;
                this.docStatus = Xform_ObjectInfo.mainDocStatus;
                this.fdOriginControlId = this.originId;
                this.controlId = this.objId;
                this.formId = Xform_ObjectInfo.fdFormId;
                this.optType = Xform_ObjectInfo.optType;
                this.inherited(arguments);
                this.init();
                domClass.add(this.domNode, "muiDetailTableWrap");
            },

            init: function () {
                DocList_Info.push(this.tableId);
                window._mobileDetailsTableScript.init(this.objId, this.tableId, this.showRow, this.required);
                //提交时保存当前页数据
                var self = this;
                Com_Parameter["event"]["confirm"].push(function () {
                    self.savePageData();
                    return true;
                });
            },

            /**
             * 切页或者提交文档时保存明细表当前页数据
             */
            savePageData: function (callback) {
                var onePageValue = this.getOnePageValueNew();
                var data = {};
                lang.mixin(data, onePageValue);
                data["fdControlId"] = this.controlId;
                data["fdKey"] = this.fdKey;
                data["fdModelName"] = this.mainModelName;
                data["fdModelId"] = this.modelId;
                data["fdFormId"] = this.formId;
                data["fdOriginControlId"] = this.originId;
                this.submit(callback, data);
            },

            getOnePageValueNew: function () {
                return this.formSubmit();
            },

            /**
             * 分页
             * @param evt
             */
            doPaging: function (evt) {
                /*var callback = function () {
                    this.requestData(this.appendNextPageRows);
                }
                //编辑状态,切页先校验, 校验通过再保存当前页数据
                var validateResult = true;
                if (this.right === "edit") {
                    validateResult = this.validate();
                    if (validateResult) {
                        var confirm = this.confirm();
                        if (confirm) {
                            this.savePageData(callback);
                        }
                    }
                } else {
                    callback.call(this);
                }*/
                this.requestData(this.appendNextPageRows);
            },

            /**
             * 明细表字段校验
             * @returns {boolean}
             */
            validate: function () {
                var viewWgt = viewRegistry.getEnclosingView(this.domNode);
                var result = true;
                var elements = this.getValidateElements();
                array.forEach(elements, function (ele){
                    if (!viewWgt._validation.validateElement(ele)) {
                        result = false;
                    }
                });
                return result;
            },

            expandTable: function() {
                var tableHead = query(".muiDetailTableHead", this.domNode)[0];
                var expandIcon = query(".muiDetailDisplayOpt", tableHead)[0];
                this.connect(expandIcon, "click", this.doExpandTable);
            },

            doExpandTable: function(evt) {
                var opt = evt.srcElement;
                var content = query(".muiDetailTableContent", this.domNode)[0];
                if (this.expand) {
                    domClass.add(content, "folder");
                    domClass.add(opt, 'muis-spread');
                    domClass.remove(opt, 'muis-put-away');
                } else {
                    domClass.remove(content, "folder");
                    domClass.add(opt, 'muis-put-away');
                    domClass.remove(opt, 'muis-spread');
                }
                this.expand = !this.expand;
                topic.publish("/mui/list/resize");
            },

            getValidateElements: function () {
                var elems = [];
                if (this.domNode) {
                    array.forEach(query("[widgetid]", this.domNode), function (node) {
                        var w = registry.byNode(node);
                        if (w instanceof FormBase && w.edit == true) {
                            elems.push(w);
                        }
                    });
                    array.forEach(query("[validate]", this.domNode), function (node) {
                        elems.push(node);
                    });
                }
                return elems;
            },

            formSubmit: function () {
                var detailsTable = dom.byId(this.tableId);
                var detailTableForm = domConstruct.toDom("<form name='" + this.tableId + "'></form>");
                domConstruct.place(detailTableForm, this.domNode, "first");
                domConstruct.place(detailsTable, detailTableForm, "first");
                var formSerial = domForm.toObject(detailTableForm);
                return formSerial;
            },

            getAttWgts: function() {
                var attWgts = [];
                array.forEach(query("[widgetid]", this.domNode), function (node) {
                    var w = registry.byNode(node);
                    if (w instanceof AttachmentList && w.edit) {
                        attWgts.push(w);
                    }
                });
                return attWgts;
            },

            confirm: function () {
                //处理附件保存前更新隐藏域值
                var attWgts = this.getAttWgts();
                for (var i = 0; i < attWgts.length; i++) {
                    var attWgt = attWgts[i];
                    if (attWgt.edit) {
                        var checked = attWgt.checkAttRules();
                        if (!checked) {
                            return false;
                        }
                    }
                }
                return true;
            },

            postCreate: function () {
                this.inherited(arguments);
            },

            startup: function () {
                this.inherited(arguments);
                // 翻页功能 evt : {pageno:xx}
                this.subscribe("/sys/xform/detailsTable/page", lang.hitch(this, function (evt) {
                    if (typeof this.controlId != "undefined" && evt.controlId == this.controlId) {
                        this.pageno = evt.pageno;
                        this.doPaging();
                    }
                }));

            },

            destroyRow : function () {
                var detailsTable = dom.byId(this.tableId);
                var self = this;
                query(".detail_wrap_td", detailsTable).forEach(function(obj) {
                    window["detail_" + self.objId + "_delRow"](obj.parentNode);
                });
            },

            /**
             * 设置请求返回的html
             * @param html
             */
            setHtml: function (html) {
                var self = this;
                util.setInnerHTML(this.domNode, html).then(
                    lang.hitch(self, function () {
                        parser.parse({rootNode: self.domNode}).then(function () {
                            topic.publish("/detailsTable/parser/done");
                            self.parseDone();
                            self.expandTable();
                            self.refreshTableInfo();
                        });
                    })
                );
            },

            parseDone: function () {
                var detailTable = dom.byId(this.tableId);
                // var pageInfo =  domAttr.get(detailTable,'pageInfo');
                var pageInfo = window["detail_" + this.objId + "_pageInfo"];
                if (pageInfo) {
                    pageInfo = JSON.parse(pageInfo);
                    this.reRender({
                        pageno: pageInfo.currentPage,
                        rowsize: pageInfo.pageSize,
                        totalSize: pageInfo.totalSize
                    });
                } else {
                    this.reRender({});
                }
            },

            // 请求完数据回调
            onDataLoad: function (data) {
                if (data) {
                    //this.destroyRow();
                    this.setHtml(data);
                } else {
                    if (this.right == "edit") {
                        var showRow = parseInt(this.showRow);
                        for (var i = 0; i < showRow; i++) {
                            window["detail_" + this.objId + "_addRow"]();
                        }
                    }
                }
            },

            refreshTableInfo: function () {
                var tbObj = dom.byId(this.tableId);
                this.tableInfo = DocList_TableInfo[tbObj.id];
                if (this.tableInfo) {
                    this.tableInfo.DOMElement = tbObj;
                    for (var i = 0; i < tbObj.rows.length; i++) {
                        var trObj = tbObj.rows[i];
                        att = trObj.getAttribute("KMSS_IsReferRow");
                        if (att == "1" || att == "true") {
                            this.tableInfo.firstIndex = i;
                            this.tableInfo.lastIndex = i;
                            //tbObj.deleteRow(i);
                            Com_SetOuterHTML(trObj, "");
                            break;
                        }
                    }
                    for (; i < tbObj.rows.length; i++) {
                        var att = tbObj.rows[i].getAttribute("KMSS_IsContentRow");
                        if (att != "1" && att != "true") {
                            break;
                        }
                        this.tableInfo.lastIndex++;
                    }
                    DocList_ResetOptCol(this.tableInfo);
                }
            },

            doRender: function () {
                this.renderPaging();
            },

            renderPaging: function () {
                if (!this.pagingWgt) {
                    this.pagingWgt = new Paging();
                    this.pagingWgt.startup();
                    this.pagingWgt.render(this);
                    domConstruct.place(this.pagingWgt.domNode, this.domNode, "last");
                } else {
                    this.pagingWgt.render(this);
                }
            },

            // 重新渲染 evt:{pageno:xxx,rowsiez:xxx,totalSize:xxx}
            reRender: function (evt) {
                this.pageno = (evt.pageno && evt.pageno > 0) ? evt.pageno : "1";
                this.pageSize = (evt.rowsize && evt.rowsize > 0) ? evt.rowsize : "5";
                this.totalSize = evt.totalSize || 0;
                this.currentPageDatas = [];
                this.doRender();
            },

            delRow: function(row) {
                var rowIdDom = query("input[ele-type='rowId']")[0];
                if (rowIdDom) {
                    var rowId = domAttr.get(rowIdDom, "value");
                    if (rowId) {
                        this.delRowById(rowId);
                    }
                }
            },

            appendNextPageRows: function(html) {
                this.handler(html);
            },

            handler: function(html){
                var self = this;
                if (!this.formatDom) {
                    this.formatDom = domConstruct.toDom("<div name='detail_" + this.objId + "_format'></div>");
                    domConstruct.place(this.formatDom, this.domNode, "last");
                }
                util.setInnerHTML(this.formatDom, html).then(
                    lang.hitch(self, function () {
                        parser.parse({rootNode: self.formatDom}).then(function () {
                            var contentRows = query("tr[kmss_iscontentrow='1']", self.formatDom);
                            self.appendRows(contentRows);
                            var pageInfo = window["detail_" + self.objId + "_pageInfo"];
                            if (pageInfo) {
                                pageInfo = JSON.parse(pageInfo);
                                self.reRender({
                                    pageno: pageInfo.currentPage,
                                    rowsize: pageInfo.pageSize,
                                    totalSize: pageInfo.totalSize
                                });
                            } else {
                                self.reRender({});
                            }
                        });
                    })
                );
            },

            appendRows: function(contentRows) {
                /*var contentTb = query("[name='detail_" + this.objId + "_content']")[0];
                var tbody = query("tbody", contentTb)[0];*/
                var table = dom.byId(this.tableId);
                var currentContentRows = query("tr[kmss_iscontentrow='1']", table);
                var lastCurrentContentRow = currentContentRows[currentContentRows.length - 1];
                array.forEach(contentRows, function(row){
                    domConstruct.place(row, lastCurrentContentRow, "after");
                });
                domConstruct.empty(this.formatDom);
                this.tableInfo = DocList_TableInfo[this.tableId];
                if (this.tableInfo) {
                    this.tableInfo.lastIndex = this.tableInfo.lastIndex + contentRows.length;
                }
            }

        });
        return claz;
    });