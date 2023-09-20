/**
 * 高级明细表视图组件
 * 功能：1. 获取当前页数据，切页或者提交文档进行保存
 *      2. 分页查询, 返回明细表的html进行渲染
 */
define(function(require, exports, module) {
    require("sys/xform/designer/seniorDetailsTable/css/detailsTable.css");
    var $ = require("lui/jquery");
    var base = require("lui/base");
    var dialog = require("lui/dialog");
    var render = require("lui/view/render")
    var paging = require("sys/xform/designer/seniorDetailsTable/seniorDetailsTablePaging");
    var RequestBeanLoader = require("sys/xform/designer/seniorDetailsTable/seniorDetailsTableData");

    var SeniorDetailsTableView = base.DataView.extend({

        "addOrUpdateUrl": Com_Parameter.ContextPath + "sys/xform/detailsTable/sysFormDetailsTableData.do?method=addOrUpdate",

        "deleteRowUrl": Com_Parameter.ContextPath + "sys/xform/detailsTable/sysFormDetailsTableData.do?method=delete",

        "batchDeleteRowUrl": Com_Parameter.ContextPath + "sys/xform/detailsTable/sysFormDetailsTableData.do?method=deleteByIds",

        initProps: function ($super, cfg) {
            $super(cfg);
            this.modelId = Xform_ObjectInfo.mainFormId;
            this.formId = Xform_ObjectInfo.fdFormId;
            this.controlId = this.id;
            this.mainModelName = Xform_ObjectInfo.mainModelName;
            this.fdKey = Xform_ObjectInfo.fdKey;
            this.fdOriginControlId = this.config.originId;
            this.formFilePath = Xform_ObjectInfo.formPath;
            this.dataLoader = RequestBeanLoader;
            this.docStatus = Xform_ObjectInfo.mainDocStatus;
            this.tableId = "TABLE_DL_" + this.controlId;
            this.tableSelector = "#" + this.tableId;
            this.showStatus = Xform_ObjectInfo.showStatus;
            this.init();
        },

        /**
         * tabPanel组件会调用此方法, 会使用到render, 而这个组件没有render, 故覆盖此方法
         */
        predraw : function() {

        },

        init: function () {
            //放在此处初始化明细表, 解决偶发新建页面数据行不显示
            //明细表初始化跟组件实例化都是监听了页面加载完成事件, 若明细表初始化先执行
            //后面才执行initEvent, 就会导致数据行不显示
            if(!this.isNewPrint()) {
                this.initEvent();
                DocList_Info.push(this.tableId);
                DocListFunc_Init();
            }
        },

        isNewPrint : function() {
            return $(this.tableSelector).attr("printcontrol") === "true";
        },

        initEvent: function () {
            var self = this;
            //明细表初始化完成
            $(document).on("detaillist-init", function (e, tbObj) {
                if ($(tbObj).attr("id") === self.tableId) {
                    self.seniorTable = $(tbObj);
                    self.initTableInfo();
                    //self.bindEvent();
                }
            });
            //复制行
            $(document).on("table-copy-new", function (event, source) {
                var newRow = $(source.row);
                var detailTable = $(newRow).closest("[fd_type='seniorDetailsTable']");
                if (detailTable.attr("id") === self.tableId) {
                    self.bindRowOpt(newRow);
                }
            });
            //新增行
            $(document).on("table-add-new", function (event, source) {
                self.dynamicBindOptEvent(source);
            });
            //提交时保存当前页数据
            Com_Parameter["event"]["confirm"].push(function () {
                self.savePageData();
                return true;
            });
        },

        /**
         * 动态新增行或者复制行的时候, 绑定事件
         * @param source
         */
        dynamicBindOptEvent: function (source) {
            var newRow = $(source.row);
            var detailTable = $(source.table);
            if (detailTable.attr("id") === this.tableId) {
                this.bindRowOpt(newRow);
            }
        },

        /**
         * 初始化明细表行的表单字段, 保存数据时, 基于这些字段获取值
         */
        /**
         * 初始化明细表行的表单字段, 保存数据时, 基于这些字段获取值
         */
        initDetailsTableProp: function () {
            var fieldNames = this.tableInfo.fieldNames;
            var childProperties = [];
            var properties = Xform_ObjectInfo.properties
            for (var i = 0; i < properties.length; i++) {
                var prop = properties[i];
                if (prop && prop.name && prop.name.indexOf(this.controlId) > -1) {
                    fieldNames.push(prop.name);
                }
            }
            if (fieldNames && fieldNames.length > 0) {
                for (var i = 0; i < fieldNames.length; i++) {
                    var name = fieldNames[i];
                    if (/^extendDataFormInfo.value/.test(name)) {
                        if (childProperties.indexOf(name) < 0) {
                            childProperties.push(name);
                        }
                    } else {
                        var regResult = /(\S+)\.(\S+)/.exec(name);
                        if (regResult && regResult.length == 3) {
                            if (regResult[1] === this.controlId) {
                                var prop = "extendDataFormInfo.value(" + this.controlId + ".!{index}." + regResult[2] + ")";
                                if (childProperties.indexOf(prop) < 0) {
                                    childProperties.push(prop);
                                }
                            }
                        }
                    }
                }
            }
            this.childProperties = childProperties;
        },

        initTableInfo: function () {
            //这里需要发布init-begin事件是因为, 类似前端计算控件还没执行load事件监听添加行事件, 就先执行了initRows事件
            //导致前端计算控件没有执行计算, 没有显示初始值
            $(this.seniorTable).trigger($.Event("detailsTable-init-begin"), this.seniorTable);
            this.tableInfo = DocList_TableInfo[this.tableId];
            this.initDetailsTableProp();
            //this.initRows();
        },

        refreshTableInfo: function () {
            var tbObj = document.getElementById(this.tableId);
            this.tableInfo = DocList_TableInfo[tbObj.id];
            if (this.tableInfo) {
                this.tableInfo.DOMElement = tbObj;
                for (var i = 0; i < tbObj.rows.length; i++) {
                    var trObj = tbObj.rows[i];
                    att = trObj.getAttribute("KMSS_IsReferRow");
                    if (att == "1" || att == "true") {
                        this.tableInfo.firstIndex = i;
                        this.tableInfo.lastIndex = i;
                        tbObj.deleteRow(i);
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
            this.processInPrintMode(tbObj);
        },

        /**
         * 打印模式下，隐藏操作栏, 重新调整节点宽度
         * @param tbObj
         */
        processInPrintMode: function (tbObj) {
            if (tbObj) {
                if (typeof window.isPrintModel != "undefined" && window.isPrintModel) {
                    $(tbObj).find("tr[type='optRow']").hide();
                    if (typeof sysPreviewDesign != "undefined") {
                        sysPreviewDesign.resetBoxWidth(this.element[0]);
                    }
                    var t = $(tbObj);
                    var tRow = t.find("tr[type='titleRow']");
                    if(tRow && tRow[0]){
                        $thead = $('<thead style="display:table-header-group;"></thead>');
                        for (var i = 0; i < tRow.length; i++) {
                            $thead.append(tRow[i].cloneNode(true).outerHTML)
                            $(tRow[i]).remove();
                        }
                        t.prepend($thead);
                    }
                }
            }
        },

        /**
         * 冻结行和冻结列
         * 前提: 非多表头明细表外层必须套div, 且设定了固定px的宽度,
         */
        freeze: function () {
            if (!this.isNewPrint()) {
                tableFreezeArray[this.tableId] = null;
                tableDivInfoArray[this.tableId] = null;
                tableFreezeStarter(this.tableId, true, 2, this.config.multihead, this.config.showIndex, this.config.right);
            }
        },

        // 对sourceUrl里面的变量进行替换，load在draw阶段执行
        load: function ($super) {
            var formFilePath = this.formFilePath;
            this.source.params = {"formFilePath": formFilePath, "xformRight": this.config.xformRight, "showStatus": this.showStatus};
            if (this.isNewPrint()) {
                this.source.params.printFormFilePath = Xform_ObjectInfo.printFormFilePath;
            }
            this.source.resolveUrl(this);
            if (window.isPrintModel && location.href.indexOf("file:") > -1) {
                return;
            }
            $super();
        },

        /**
         * 明细表字段校验
         * @returns {boolean}
         */
        validate: function () {
            var elements = null;
            var validation = $KMSSValidation();
            var result = true;
            if (validation.form) {
                elements = new Elements(validation.form).getElements();
                for (var i = 0, length = elements.length; i < length; i++) {
                    var name = $(elements[i]).attr("name");
                    if (/^extendDataFormInfo.value/.test(name) && name.indexOf(this.controlId) > -1) {
                        if (!validation.validateElement(elements[i])) {
                            if (result) {
                                result = false;
                            }
                        }
                    }
                }
            }
            return result;
        },

        getOnePageValueNew: function () {
            return this.formSubmit();
        },

        //获取一页的明细表行数据
        getOnePageValue: function () {
            var contentRows = this.getContentRows();
            var fdRowForms = {};
            for (var i = 0; i < contentRows.length; i++) {
                for (var j = 0; j < this.childProperties.length; j++) {
                    var name = this.childProperties[j];
                    var elementName = name.replace("!{index}", i);
                    var val = $form(elementName).val();
                    if (!val) {
                        val = $("[name='" + elementName + "']").val();
                    }
                    var submitName = name.replace("!{index}", 0);
                    var key = "fdRowForms[" + i + "]." + submitName;
                    fdRowForms[key] = val;
                }
            }
            var customPageValue = this.getCustomPageValue(contentRows);
            $.extend(fdRowForms, customPageValue);
            return fdRowForms;
        },

        getCustomPageValue: function (contentRows) {
            var attValue = this.getAttValue(contentRows);
            var customValues = {};
            $.extend(customValues, attValue);
            return customValues;
        },

        /**
         * 获取当前页附件对象
         * @param contentRows
         * @returns {[]}
         */
        getAttObj: function (contentRows) {
            var attObjs = [];
            if (contentRows && contentRows.length > 0) {
                for (var i = 0; i < contentRows.length; i++) {
                    var contentRow = $(contentRows[i]);
                    contentRow.find("[name*=attachmentForms]").closest("xformflag").each(function (index, obj) {
                        var name = $(obj).attr("property");
                        var attObjKey = $form(name).val();
                        attObjs.push(Attachment_ObjectInfo[attObjKey]);
                    });
                }
            }
            return attObjs;
        },

        /**
         * 获取关联文对象
         * @param contentRows
         * @returns {[]}
         */
        getRelevanceWgt: function (contentRows) {
            var relevanceWgts = [];
            if (contentRows && contentRows.length > 0) {
                for (var i = 0; i < contentRows.length; i++) {
                    var contentRow = $(contentRows[i]);
                    contentRow.find("xformflag[flagtype='relevance']").each(function (index, obj) {
                        var controlId = $(obj).attr("flagid");
                        for (var j = 0; j < Xform_ObjectInfo.Xform_Controls.relevanceObj.length; j++) {
                            var relevanceObj = Xform_ObjectInfo.Xform_Controls.relevanceObj[i];
                            if (relevanceObj.controlId === controlId) {
                                relevanceWgts.push(relevanceObj);
                                break;
                            }
                        }
                    });
                }
            }
            return relevanceWgts;
        },

        getCurrentPageRowIds: function () {
            var rowIdMap = {};
            var contentRows = this.getContentRows();
            for (var i = 0; i < contentRows.length; i++) {
                var rowId = this.getRowId(i);
                rowIdMap[i] = rowId;
            }
            return rowIdMap;
        },

        /**
         * 获取内容行
         * @returns {[]}
         */
        getContentRows: function () {
            var rows = $("#" + this.tableId)[0].rows;
            var contentRows = [];
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                if (this.isContentRow(row)) {
                    contentRows.push(row);
                }
            }
            return contentRows;
        },

        /**
         * 获取内容行索引
         * @param obj
         * @returns {null|number}
         */
        getContentRowIndex: function (obj) {
            var contentRows = this.getContentRows();
            var optTR = DocListFunc_GetParentByTagName("TR", obj);
            for (var i = 0; i < contentRows.length; i++) {
                if (contentRows[i] === optTR) {
                    return i;
                }
            }
            return null;
        },

        /**
         * 判断指定row是否为内容行
         * @param row
         * @returns {boolean} 如果是则返回true, 否则返回false
         */
        isContentRow: function (row) {
            var type = $(row).attr("type");
            var isReferRow = $(row).attr("kmss_isreferrow") === "1";
            var isNotContentRow = (type === "titleRow" || type === "optRow" || type === "statisticRow") || (isReferRow);
            return !isNotContentRow;
        },

        /**
         * 设置请求返回的html
         * @param html
         */
        setHtml: function (html) {
            this.element.html(html);
            this.seniorTable = $(this.tableSelector);
            $(this.seniorTable).trigger($.Event("detailsTable-init"), this.seniorTable);
            this.freeze();
            // this.publicAddRowEvent();
        },

        /**
         * 设置html前, 先删除当前页的行
         */
        destroy : function () {
            var $detailsTable = $(this.tableSelector);
            this.deleteCurrentPageRow();
        },

        deleteCurrentPageRow : function () {
            var contentRows = this.getContentRows();
            if (contentRows) {
                for (var i = 0; i < contentRows.length; i++) {
                    var optRow = contentRows[i];
                    this.doDelete(optRow);
                }
            }
        },

        publishAddRowEvent: function () {
            var contentRows = this.getContentRows();
            if (contentRows) {
                for (var i = 0; i < contentRows.length; i++) {
                    var optRow = contentRows[i];
                    $(this.seniorTable).trigger($.Event("table-add"), optRow);
                    $(this.seniorTable).trigger($.Event("table-add-new"), {
                        'row': optRow,
                        'vals': null,
                        'table': this.seniorTable[0]
                    });
                }
            }
        },

        // 请求完数据回调
        onDataLoad: function (data) {
            if (data) {
                if (data.undefined) {
                    this.destroy();
                    this.setHtml(data.undefined);
                    this.bindEvent();
                } else {
                    this.initRows();
                }
                var pageInfo = $(this.tableSelector).attr("pageInfo");
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
                this.refreshTableInfo();
                //渲染完成, 隐藏加载图标
                this.hideLoading();
            }
        },

        //无数据, 编辑状态新增行
        initRows: function () {
            if (this.config.right === "edit" && !this.config.totalSize) {
                var showRow = parseInt(this.config.showRow);
                this.seniorTable = $(this.tableSelector);
                for (var i = 0; i < showRow; i++) {
                    DocList_AddRow(this.seniorTable[0]);
                }
            }
            this.bindEvent();
            this.freeze();
        },

        bindEvent: function () {
            this.bindOptRowEvent();
            this.bindRowOpt();
        },

        /**
         * 绑定操作行按钮事件
         */
        bindOptRowEvent: function () {
            var self = this;
            //新增
            this.seniorTable.find("[opt='add']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).click(function () {
                    self.addRow(obj);
                });
            });
            //批量删除
            this.seniorTable.find("[opt='batchDel']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).click(function () {
                    self.batchDeleteRow();
                });
            });
            //全选
            this.seniorTable.find("[opt='selectAll']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).click(function () {
                    self.selectRows(obj);
                });
            });
            // excel导入
            this.seniorTable.find("[opt='seniorImportExcel']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).click(function () {
                    self.excelImport(self.tableId, self.controlId, true, null, self.originId, self.currentPage, obj, self.config.importType);
                });
            });
            //excel导出
            this.seniorTable.find("[opt='seniorExportExcel']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).click(function () {
                    self.excelExport(obj);
                });
            });
        },

        /**
         * 绑定内容行按钮事件
         * @param context
         */
        bindRowOpt: function (context) {
            context = context || this.seniorTable
            var self = this;
            //删除
            context.find("[opt='del']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).mousedown(function () {
                    self.deleteRow(obj);
                });
            });

            //选中一行
            context.find("[opt='select']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).click(function () {
                    self.selectRow(obj);
                });
            });

            //复制一行
            context.find("[opt='copy']").each(function (index, obj) {
                $(obj).unbind();
                $(obj).click(function () {
                    self.copyRow(obj);
                });
            });
        },

        /**
         * 新增行
         */
        addRow: function (src) {
            DocList_AddRow();
            XFom_RestValidationElements();
        },

        /**
         * 复制行
         */
        copyRow: function (src) {
            var optTr = this.getOptRow(src);
            DocList_CopyRow(optTr);
        },

        getOptRow: function (src) {
            var optTr = DocListFunc_GetParentByTagName("TR", src);
            return optTr;
        },

        /**
         * 删除行
         * @param obj
         */
        deleteRow: function (src) {
            var optTr = this.getOptRow(src);
            var contentRowIndex = this.getContentRowIndex(src);
            var rowId = this.getRowId(contentRowIndex);
            var data = {"fdId": rowId, "fdFormId": this.formId, "fdControlId": this.controlId};
            if (rowId) {
                var self = this;
                dialog.confirm('一旦选择了删除，所选记录的相关数据都会被删除，无法恢复！您确认要执行此删除操作吗？', function (isOk) {
                    if (isOk) {
                        $.get(self.deleteRowUrl, data, function (rtnData) {
                            self.doDelete(optTr);
                            //删除后重新刷新记录
                            self.refresh();
                        });
                    }
                });
            } else {
                this.doDelete(optTr);
            }
        },

        doDelete: function (optTr) {
            DocList_DeleteRow_ClearLast(optTr);
            XFom_RestValidationElements();
        },

        /**
         * 批量删除行
         */
        batchDeleteRow: function () {
            var $optTb = $(this.tableSelector);
            var selectedRow = $("input[name='DocList_Selected']:checked", $optTb[0]);
            var fdIds = [];
            var idToTr = {};
            for (var i = selectedRow.size() - 1; i >= 0; i--) {
                var optTr = selectedRow.eq(i).closest('tr');
                if (optTr && optTr.size() > 0) {
                    var contentRowIndex = this.getContentRowIndex(optTr[0]);
                    var rowId = this.getRowId(contentRowIndex);
                    if (rowId) {
                        fdIds.push(rowId);
                        idToTr[rowId] = optTr[0];
                    } else {
                        DocList_DeleteRow_ClearLast(optTr[0]);
                    }
                }
            }
            if (fdIds.length > 0) {
                var fdIdsStr = fdIds.join(";");
                var self = this;
                var data = {"fdIds": fdIdsStr, "fdFormId": this.formId, "fdControlId": this.controlId};
                $.post(this.batchDeleteRowUrl, data, function (rtnData) {
                    for (var key in idToTr) {
                        self.doDelete(idToTr[key]);
                    }
                    //删除后重新刷新记录
                    self.refresh();
                });
            }
        },

        selectRow: function (src) {
            DocList_SelectRow(src);
        },

        selectRows: function (src) {
            DocList_SelectAllRow(src);
        },

        getRowId: function (rowIndex) {
            var fdIdName = "extendDataFormInfo.value(" + this.controlId + "." + rowIndex + ".fdId)";
            var fdId = $("[name='" + fdIdName + "']").val();
            return fdId;
        },

        // 渲染完毕之后添加事件
        doRender: function ($super, cfg) {
            $super(cfg);
            this.renderPaging();
        },

        // 重新渲染 evt:{pageno:xxx,rowsiez:xxx,totalSize:xxx}
        reRender: function (evt) {
            this.config.currentPage = (evt.pageno && evt.pageno > 0) ? evt.pageno : "1";
            this.config.pageSize = (evt.rowsize && evt.rowsize > 0) ? evt.rowsize : "15";
            ;
            this.config.totalSize = evt.totalSize || 0;
            this.currentPageDatas = [];
            this.doRender(this.cfg);
        },

        /**
         * 分页
         * @param evt
         */
        doPaging: function (evt) {
            var callback = function () {
                //查询下一页
                $.extend(this, evt);
                this.fetchData();
            }
            //编辑状态,切页先校验, 校验通过再保存当前页数据
            var validateResult = true;
            if (this.config.right === "edit") {
                validateResult = this.validate();
                if (validateResult) {
                    //保存数据前, 显示加载图标
                    var submit = this.submit();
                    if (submit) {
                        var confirm = this.confirm();
                        if (confirm) {
                            this.showLoading();
                            this.savePageData(callback);
                        }
                    }
                }
            } else {
                this.showLoading();
                callback.call(this);
            }
        },

        /**
         * 切页或者提交文档时保存明细表当前页数据
         */
        savePageData: function (callback) {
            var onePageValue = this.getOnePageValueNew();
            var data = {};
            $.extend(data, onePageValue);
            data["fdControlId"] = this.controlId;
            data["fdKey"] = this.fdKey;
            data["fdModelName"] = this.mainModelName;
            data["fdModelId"] = this.modelId;
            data["fdFormId"] = this.formId;
            data["fdExtendFilePath"] = this.formFilePath;
            data["fdOriginControlId"] = this.config.originId;
            var self = this;
            $.post(this.addOrUpdateUrl, data, function (rtnData) {
                if (typeof callback != "undefined") {
                    callback.call(self);
                }
            });
        },

        submit: function () {
            //处理附件提交前校验
            var contentRows = this.getContentRows();
            var attObjs = this.getAttObj(contentRows);
            for (var i = 0; i < attObjs.length; i++) {
                var attObj = attObjs[i];
                var validate = attObj.submitValidate();
                if (!validate) {
                    return false;
                }
            }
            return true;
        },

        confirm: function () {
            //处理附件保存前更新隐藏域值
            var contentRows = this.getContentRows();
            var attObjs = this.getAttObj(contentRows);
            for (var i = 0; i < attObjs.length; i++) {
                var attObj = attObjs[i];
                if (attObj.editMode == "edit" || attObj.editMode == "add") {
                    var updateInput = attObj.updateInput();
                    if (!updateInput) {
                        return false;
                    }
                }
            }
            var relevanceWgts = this.getRelevanceWgt(contentRows);
            var relevanceMainConfirm = true;
            for (var i = 0; i < relevanceWgts.length; i++) {
                var relevanceWgt = relevanceWgts[i];
                var relevanceObjConfirm = relevance_main_confirm(relevanceWgt);
                if (!relevanceObjConfirm) {
                    relevanceMainConfirm = false;
                }
            }
            if (!relevanceMainConfirm) {
                return false;
            }
            return true;
        },

        renderPaging: function () {
            /**************** 分页插件 start *****************/
            // 由于分页块放置外render外面不合适，只能放置在模板HTML里面，故渲染完毕需要重新new分页组件
            if (this.paging) {
                this.paging.destroy();
            }
            this.pagingWrap = $("<div class='seniorDetailsTable-foot'></div>");
            this.element.append(this.pagingWrap);
            this.pagingFoot = $("<div class='seniorDetailsTable-foot-paging'></div>");
            this.pagingWrap.append(this.pagingFoot);
            this.paging = new paging.SeniorDetailsTablePaging({
                parent: this,
                element: this.pagingFoot,
                currentPage: this.config.currentPage,
                pageSize: this.config.pageSize,
                totalSize: this.config.totalSize,
                viewSize: "2"
            });
            this.paging.startup();
            this.paging.draw();
            /**************** 分页插件 end *****************/
        },

        formSubmit: function () {
            $(this.tableSelector).wrap("<form name='" + this.tableId + "'></form>");
            var $form = $("form[name='" + this.tableId + "']");
            var formSerial = {};
            $($form.serializeArray()).each(function () {
                formSerial[this.name] = this.value;
            });
            $(this.tableSelector).unwrap();
            return formSerial;
        },

        //刷新记录
        refresh: function () {
            this.fetchData();
        },

        showLoading: function () {
            if (!this.loadingDialog) {
                this.element.css('min-height', 200);
                this.loadingDialog = dialog.loading(null, this.element);
            } else {
                this.loadingDialog.show();
            }
        },

        hideLoading: function () {
            if (this.loadingDialog) {
                this.loadingDialog.hide();
                this.element.css('min-height', 'inherit');
                this.loadingDialog = null;
            }
        },

        fetchData: function () {
            this.source.resolveUrl(this);
            this.source.get();
        },

        /***********************************************
         功能：高级明细表导入excel
         参数：
         optTB：
         必选，参考对象 例如："TABLE_DocList"
         itemName：
         必选，form里面的明细表字段名 例如："fdItems"
         modelName：
         必选，完整的模块名 例如："com.landray.kmss.km.asset.model.KmAssetApplyStock"，表单的是fileName
         isXform:
         可选：是否是表单的明细表 例如："1"或者"true"
         fieldNameArray:
         可选：业务模块，数组类型，元素是列的字段名，可以自行设置那些列需要导出，例如：['fdTotalMoney','fdStockMatter',...]
         originDetailId：
         必选，form里面的明细表唯一标识Id 例如："fdOriginControlId"
         pageNum:
         必选，当前页码 例如：1
         obj:
         可选
         返回：null
         ***********************************************/
        // excel导入,导入时即保存数据，之后再请求数据渲染
        excelImport: function (optTB, itemName, isXform, fieldNameArray, originDetailId, pageNum, obj, importType) {
            if (optTB == null)
                optTB = DocListFunc_GetParentByTagName("TABLE");
            else if (typeof (optTB) == "string")
                optTB = document.getElementById(optTB);
            if (isXform && (isXform == '1' || isXform == 'true')) {
                isXform = 'true';
            } else {
                isXform = 'false';
            }
            var propertyName = [];
            //新增一个基准行dom，以免导入的时候没有一条数据，后面获取的数据不好校验，这个temp相当于一个基准行的dom对象
            var temp = DocList_AddRow(optTB.id);
            //新增的这一行，有可能包含附件，如果有附件的话，必须先等附件初始化完再删除，因为附件有个setTimeout，故删除之前需要隐藏这个虚拟行 by 朱国荣 2016-09-03
            if (temp) {
                temp.style.display = 'none';
            }
            var dataJson = [];
            //如果是自定义表单
            if (isXform == 'true') {
                dataJson = DocList_GetDetailsTableXformflag(optTB, temp, true);
                //模板下载不支持导出的控件
                var unsuportedControl = ['xform_chinavalue', 'xform_calculate', 'xform_relation_radio', 'xform_relation_checkbox', 'xform_relation_select', 'xform_relation_choose'];
                for (var i = 0; i < dataJson.length; i++) {
                    var data = dataJson[i];
                    // IE8不支持数组的indexof by zhugr 2017-08-26
                    if ($.inArray(data.fieldType, unsuportedControl) > -1) {
                        continue;
                    }
                    propertyName.push(data.fieldId);
                }
            } else {
                if (fieldNameArray != null) {
                    propertyName = fieldNameArray;
                } else {
                    //以下是普通业务模块的Excel导出
                    //得到明细表字段名，用于数据字典的查找
                    var tbObj = DocList_TableInfo[optTB.id];
                    var fieldNames = tbObj.fieldNames;

                    for (var i = 0; i < fieldNames.length; i++) {
                        var parseField = DocList_parseProName(itemName, fieldNames[i], isXform);
                        if (parseField && parseField != undefined && parseField != '') {
                            var $fdFiled = $(temp).find("[name*='" + parseField + "']");
                            //特殊控件处理
                            if ($fdFiled && $fdFiled.length == 1) {
                                //大小写控件不需要匹配
                                if ($fdFiled.attr('chinavalue') && $fdFiled.attr('chinavalue') == 'true') {
                                    continue;
                                }
                                //计算控件不需要匹配
                                if ($fdFiled.attr('calculation') && $fdFiled.attr('calculation') == 'true') {
                                    continue;
                                }
                            }
                            propertyName.push(parseField);
                        }
                    }
                }
                //去除相同的项
                propertyName = DocList_promiseUnique(propertyName);
                //增加type属性
                for (var i = 0; i < propertyName.length; i++) {
                    var $dom = $(temp).find("[name*='" + propertyName[i] + "'][type!='hidden']");
                    if ($dom[0] && $dom[0].type) {
                        dataJson.push({'fieldType': $dom[0].type, 'fieldId': propertyName[i]});
                    }
                }
            }

            //删除自己增加的dom行
            //新增的这一行，有可能包含附件，如果有附件的话，必须先等附件初始化完再删除，因为附件有个setTimeout，附件的延时是300，故这里稍微设得长一点就可以了 by 朱国荣 2016-09-03
            window.setTimeout(function () {
                DocList_DeleteRow(temp);
            }, 310);
            // 拿到当前页的行fdId
            var currentPageRowIds = this.getCurrentPageRowIds();
            //给弹出框调用
            window._paramJsonExcelUpload = {
                'modelName': this.formFilePath,
                'modelId': this.modelId,
                'mainModelName': this.mainModelName,
                'formId': this.formId,
                'fdKey': this.fdKey,
                'itemName': itemName,
                'field': JSON.stringify(dataJson),
                'propertyName': propertyName,
                'optTBId': optTB.id,
                'maxLimitedNum': '1000',//限制最大导入数量
                'maxRenderNum': '200',// 限制前端最大渲染数量
                //'validateType' : 'false',//用于是否需要导入的时候校验属性的必填、数据类型等等
                'isXform': isXform,
                'detailId': this.controlId,
                'originDetailId': this.fdOriginControlId, // 此为高级明细表的唯一标识id
                'currentPageRowIds': currentPageRowIds,
                'pageNum': pageNum,         // 当前导入页面的页码
                'excelImportType': importType
            };

            var url = "/sys/transport/sys_transport_xform/SysTransportImportSeniorDetailTable_upload.jsp";
            var height = document.documentElement.clientHeight * 0.5;
            var width = document.documentElement.clientWidth * 0.6;
            seajs.use(['lui/dialog'], function (dialog) {
                dialog.iframe(url, Data_GetResourceString('明细表导入'), null, {width: width, height: height, close: false});
            });
        },
        // excel导出，可全部导出，支持指定行数，指定某一列的导出。导出原则：非所见即所导，只是后台跟前台一样的获取列数据逻辑，注意当table内容展示逻辑调整，后台excel导出也需要调整
        excelExport: function (obj) {
            var totalSize = this.config.totalSize;
            var exportDialogObj = {
                exportUrl: Com_Parameter.ContextPath + "sys/transport/detailTableSeniorExport.do?method=exportDataResult",
                exportNum: totalSize,
            };
            var fieldArray = [];
            var modelId = this.modelId;
            var filePath = this.formFilePath;
            var formId = this.formId;
            var detailId = this.controlId;
            var modelName = this.mainModelName;
            seajs.use(['lui/jquery', 'lui/dialog', 'lui/topic'], function ($, dialog, topic) {
                //viewType为空默认为列表视图
                exportResult = function (obj) {
                    showExportDialog(obj);
                };

                showExportDialog = function (obj) {
                    var selectedData = getSelectIndexs();
                    var selectedNum = selectedData.length;
                    var hasSelected = selectedData.length > 0;

                    if (exportDialogObj.exportNum > 0) {
                        var url = '/sys/transport/sys_transport_xform/exportDialog.jsp?' +
                            '&totalNum=' + exportDialogObj.exportNum + '&hasSelected=' + hasSelected + '&selectedNum=' + selectedNum + '&detailId=' + detailId;
                        dialog.iframe(url, '导出设置',
                            function (value) {
                                exportCallbak(value);
                            },
                            {
                                width: 500,
                                height: 510
                            }
                        );
                    } else {
                        dialog.alert('无法导出！当前列表数据为空');
                    }
                };

                exportCallbak = function (returnValue) {
                    if (returnValue == null)
                        return;
                    var selectedData = getSelectIndexs();
                    var fdNum = exportDialogObj.exportNum;
                    var fdNumStart = returnValue["fdNumStart"];
                    var fdNumEnd = returnValue["fdNumEnd"];
                    var fdKeepRtfStyle = returnValue["fdKeepRtfStyle"];
                    var fdColumns = returnValue["fdColumns"];
                    var fdExportType = returnValue["fdExportType"];
                    var checkIdValues = selectedData.join("|");
                    let url = exportDialogObj.exportUrl;
                    var fieldArray = getFieldArray(detailId, fdColumns);
                    var params = {
                        'modelId': modelId,
                        'formId': formId,
                        'detailId': detailId,
                        'modelName': modelName,
                        'filePath': filePath,
                        'isXform': "true",
                        'field': JSON.stringify(fieldArray),
                        'fdNum': fdNum,
                        'fdNumStart': fdNumStart,
                        'fdNumEnd': fdNumEnd,
                        'fdColumns': fdColumns,
                        'fdExportType': fdExportType,
                        'checkIdValues': checkIdValues,

                    };
                    dialog.confirm('导出数据需要稍等片刻，期间请不要关闭窗口，是否确认导出？', function (value) {
                        if (value === true) {
                            console.log(url);
                            var temp_form = document.createElement("form");
                            temp_form.action = url;
                            temp_form.target = "_parent";
                            temp_form.method = "post";
                            temp_form.style.display = "none";
                            //添加参数
                            for (var item in params) {
                                var opt = document.createElement("textarea");
                                opt.name = item;
                                opt.value = params[item];
                                temp_form.appendChild(opt);
                            }
                            document.body.appendChild(temp_form);

                            temp_form.submit();
                            $(temp_form).remove();

                        }
                    });
                };

                function getSelectIndexs() {
                    var selectedData = [];
                    $("[name='DocList_Selected']:checkbox").each(function () {
                        if (this.checked) {
                            // 查找行fdId
                            if ($(this).closest("tr") && $(this).closest("tr").find("input[name$='.fdId)']")) {
                                selectedData.push($(this).closest("tr").find("input[name$='.fdId)']").val());
                            }
                        }
                    });

                    return selectedData;
                };

                function buildUrl(url, params) {
                    //添加参数
                    for (var item in params) {
                        var opt = document.createElement("textarea");
                        opt.name = item;
                        opt.value = params[item];
                        return opt;
                    }
                };

                //通过xformflag获取明细表里面的控件ID,并且过滤未选择的列
                function getFieldArray(controlId, fdColumns) {
                    var fieldArray = [];
                    if (controlId == undefined || controlId == null) {
                        fieldArray = DocList_GetSeniorDetailsTableXformflag(null, null, null, false);
                    }
                    fieldArray = DocList_GetSeniorDetailsTableXformflag("TABLE_DL_" + controlId, null, fdColumns, false);
                    return fieldArray;
                }


                window.exportResult = exportResult;
            });
            exportResult("", "", "", obj);

        }

    });

    exports.SeniorDetailsTable = SeniorDetailsTableView;
})