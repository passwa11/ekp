define(["dojo/parser", "dojo/dom", "dojo/dom-construct", "dojo/dom-style", "dojo/dom-attr", "dojo/dom-class", "dojo/query","dojo/Deferred","mui/loadingMixin",
    "dijit/registry", "dojo/topic", "dojo/on", "mui/dialog/Tip", "dojo/ready", "mui/form/validate/Validation", "mui/i18n/i18n!sys-xform-base:mui"],
    function (parser, dom, domConstruct, domStyle, domAttr, domClass, query, Deferred, loadingMixin, registry, topic, on, Tip, ready, Validation, Msg) {
        window._mobileDetailsTableScript = {
            init: function (objId, tableId, showRow, required) {
                window["detail_" + objId + "_expandRow"] = function (domNode) {
                    var domTable = $(domNode).closest('table')[0];
                    var display = domAttr.get(domTable, 'data-display'),
                        newdisplay = (display == 'none' ? '' : 'none');
                    domAttr.set(domTable, 'data-display', newdisplay);
                    var items = query('tr[data-celltr="true"],tr[statistic-celltr="true"]', domTable);
                    for (var i = 0; i < items.length; i++) {
                        if (newdisplay == 'none') {
                            domStyle.set(items[i], 'display', 'none');
                        } else {
                            domStyle.set(items[i], 'display', '');
                        }
                    }
                    var opt = query('.muiDetailDisplayOpt', domTable)[0];
                    if (newdisplay == 'none') {
                        domClass.add(opt, 'muis-spread');
                        domClass.remove(opt, 'muis-put-away');
                    } else {
                        domClass.add(opt, 'muis-put-away');
                        domClass.remove(opt, 'muis-spread');
                    }
                    topic.publish("/mui/list/resize");
                };

                window["detail_" + objId + "_add"] = function (event) {
                    event = event || window.event;
                    if (event) {
                    	if (event.stopPropagation) {
                            event.stopPropagation();
                        }
                        else {
                            event.cancelBubble = true;
                        }
                    }
                    window["detail_" + objId + "_addRow"]();
                };

                window["detail_" + objId + "_addRow"] = function (callbackFun) {
                    var newRow = DocList_AddRow(tableId);
                    newRow.dojoClick = true;
                    parser.parse(newRow).then(function () {
                        var tabInfo = DocList_TableInfo[tableId];
                        if (tabInfo['_getcols'] == null) {
                            tabInfo.fieldNames = [];
                            tabInfo.fieldFormatNames = [];
                            DocListFunc_AddReferFields(tabInfo, newRow, "INPUT");
                            DocListFunc_AddReferFields(tabInfo, newRow, "TEXTAREA");
                            DocListFunc_AddReferFields(tabInfo, newRow, "SELECT");
                            tabInfo['_getcols'] = 1;
                        }
                        newRow.setAttribute("kmss_iscontentrow","1")
                        window["detail_" + objId + "_fixNo"]();
                        topic.publish('/mui/form/valueChanged', null, { row: newRow, tableId: objId, eventType: 'detailsTable-addRow' });
                        // <#-- 在以桌面端显示的时候新增行需要更新高度 -->
                        topic.publish("/mui/list/resize", newRow);
                        if (callbackFun) { callbackFun(newRow); }
                    });
                }

                window["detail_" + objId + "_del"] = function (domNode) {
                    var td = $(domNode).closest('.detail_wrap_td')[0];
                    window["detail_" + objId + "_delRow"](td.parentNode);
                };

                window["detail_" + objId + "_delRow"] = function (trDom) {
                    var wgtObj = registry.getEnclosingWidget(trDom);
                    if (wgtObj && wgtObj.adDetail) {
                        wgtObj.delRow(trDom);
                    }
                    $(trDom).find("*[widgetid]").each(function (idx, widgetDom) {
                        var widget = registry.byNode(widgetDom);
                        if (widget && widget.destroy) {
                            widget.destroy();
                        }
                    });
                    var optTB = DocListFunc_GetParentByTagName("TABLE", trDom);
                    var rowIndex = Com_ArrayGetIndex(optTB.rows, trDom);
                    var tbInfo = DocList_TableInfo[optTB.id];
                    DocList_DeleteRow_ClearLast(trDom);
                    for (var i = rowIndex; i < tbInfo.lastIndex; i++) {
                        var row = tbInfo.DOMElement.rows[i];
                        query('*[widgetid]', row).forEach(function (widgetDom) {
                            var widget = registry.byNode(widgetDom);
                            if (widget.needToUpdateAttInDetail) {
                                var updateAttrs = widget.needToUpdateAttInDetail;
                                for (var j = 0; j < updateAttrs.length; j++) {
                                    if (widget[updateAttrs[j]]) {
                                        var updatFileds = query("[name='" + widget[updateAttrs[j]] + "']", row);
                                        if (updatFileds.length > 0) {
                                            updatFileds[0].name = window["detail_" + objId + "_repalceIndexInfo"](updatFileds[0].name, i - tbInfo.firstIndex);
                                        }
                                        widget[updateAttrs[j]] = window["detail_" + objId + "_repalceIndexInfo"](widget[updateAttrs[j]], i - tbInfo.firstIndex);
                                    }
                                }
                            } else if (widget.name) {
                                var tmpFileds = query("[name='" + widget.name + "']", row);
                                if (tmpFileds.length > 0) {
                                    if (tmpFileds[0].name) {
                                        tmpFileds[0].name = window["detail_" + objId + "_repalceIndexInfo"](tmpFileds[0].name, i - tbInfo.firstIndex);
                                    }
                                }
                                widget.name = window["detail_" + objId + "_repalceIndexInfo"](widget.name, i - tbInfo.firstIndex);
                            } else if (widget.idField) {
                                var tmpIdField = query("[name='" + widget.idField + "']", row);
                                if (tmpIdField.length > 0) {
                                    tmpIdField[0].name = window["detail_" + objId + "_repalceIndexInfo"](tmpIdField[0].name, i - tbInfo.firstIndex);
                                }
                                var tmpNameField = query("[name='" + widget.nameField + "']", row);
                                if (tmpNameField.length > 0) {
                                    tmpNameField[0].name = window["detail_" + objId + "_repalceIndexInfo"](tmpNameField[0].name, i - tbInfo.firstIndex);
                                }
                                widget.idField = window["detail_" + objId + "_repalceIndexInfo"](widget.idField, i - tbInfo.firstIndex);
                                widget.nameField = window["detail_" + objId + "_repalceIndexInfo"](widget.nameField, i - tbInfo.firstIndex);
                            }
                        });
                    }
                    window["detail_" + objId + "_fixNo"]();
                    topic.publish('/mui/form/valueChanged', null, { tableId: objId, eventType: 'detailsTable-delRow' });
                    // <#-- 在以桌面端显示的时候删除行需要更新高度 -->
                    topic.publish("/mui/list/resize", trDom);
                };

                window["detail_" + objId + "_repalceIndexInfo"] = function (fieldName, index) {
                    fieldName = fieldName.replace(/\[\d+\]/g, "[!{index}]");
                    fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
                    fieldName = fieldName.replace(/!\{index\}/g, index);
                    return fieldName;
                }

                window["detail_" + objId + "_fixNo"] = function () {
                    var muiDetailTableNo = $('#' + tableId).find('.muiDetailTableNo');
                    var controlType = $('#' + tableId).attr("control-type");
                    if (controlType === "adDetailTable") {
                        muiDetailTableNo.each(function (i) {
                            $(this).children('span').text(Msg["mui.detailTable.the"] + (i + 1) + Msg["mui.detailTable.item"]);
                        });
                    } else {
                        var tableName = muiDetailTableNo.attr("tableName") || "";
                        tableName = tableName.replace(/\n/g, "");
                        muiDetailTableNo.each(function (i) {
                            $(this).children('span').text(tableName + (i + 1));
                        });
                    }
                }

                // <#--明细表删除最后一行校验 by liwc -->
                window["_sys_xfrom_detailsTable_requiredInit_" + objId] = function (tableId) {
                    var validateName = 'required';
                    var listendDomId = tableId + "_position";
                    var tipMessage = Data_GetResourceString('sys-xform:sysForm.detailsTable.tipMessage');
                    var inputTemplate = "<input id='" + listendDomId + "' type='text' style='display:table-cell;width:0px;height:1px;border:0px;' subject='" + tipMessage + "' validate='" + validateName + "'/>";
                    var tbDom = document.getElementById(tableId);
                    $(tbDom).after(inputTemplate);
                    // <#--一开始的时候就需要设置input的值，以免在重新编辑页面，input没有值-->
                    DocList_Xform_DetailsTable_SetInputValue($('#' + listendDomId), tableId);
                    // <#--增加监听-->
                    var listened;
                    if (listendDomId == null || Validation == null) {
                        return;
                    }
                    if (tableId == null) {
                        listened = 'table[showStatisticRow]';
                    } else {
                        listened = '#' + tableId;
                    }
                    var $listen = $('#' + listendDomId);
                    var validation = new Validation();
                    // <#--添加行、删除行触发-->
                    $(document).on('table-delete-new table-add-new', listened, function (e, obj) {
                        if (obj && obj.table) {
                            DocList_Xform_DetailsTable_SetInputValue($listen, obj.table.id);
                            // <#--执行校验-->
                            validation.validateElement($listen[0]);
                        }
                    })
                }
                topic.subscribe('parser/done',function(){
                //topic.subscribe('parser/detail/done',function(){
                	if (DocList_TableInfo[tableId] == null) {
                        DocListFunc_Init();
                    }
                    // var wrapWgt = registry.byId("detail_" + objId + "_template");
                    // if (wrapWgt) {
                    //     parser.parse(wrapWgt.domNode)
                    // }

                    if (window["_editShow_" + objId]) {
                        if (window["_emptyFormData_" + objId]) {
                            try {
                               setTimeout(function () {
                                    for (var i = 0; i < showRow; i++) {
                                        window["detail_" + objId + "_addRow"]();
                                    }
                                }, 0);
                            } catch (e) {
                            }
                        }
                        // <#-- <xform:editShow> -->
                        // <#--#48057明细表删除最后一行校验 by liwc-->
                        if (required === "true") {
                            window["_sys_xfrom_detailsTable_requiredInit_" + objId]('TABLE_DL_' + objId);
                        }
                        // <#-- </xform:editShow>-->
                    }
                    var statisticRow = query('[statistic-celltr-title="true"]');
                    var row = query('[celltr-title="true"]');
                    if (statisticRow && statisticRow.length > 0) {
                        statisticRow[0].dojoClick = true;
                    }
                    if (row && row.length > 0) {
                        for (var i = 0; i < row.length; i++) {
                            row[i].dojoClick = true;
                        }
                    }
                    if(dojoConfig.dingXForm && dojoConfig.dingXForm === "true"){
                		Com_IncludeFile("dingTop_mobile.css", Com_Parameter.ContextPath + "km/review/km_review_ui/dingSuit/css/","css",true);
                	}
                });
            }
        }
    });