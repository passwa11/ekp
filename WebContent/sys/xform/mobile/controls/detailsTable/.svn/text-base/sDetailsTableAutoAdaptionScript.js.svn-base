define(["dojo/parser", "dojo/dom", "dojo/dom-construct", "dijit/registry", "dojo/topic",
    "mui/device/adapter", "dojo/ready", "mui/form/validate/Validation", "dojo/query", "dojo/dom-class", "dojo/dom-style",
    "sys/xform/mobile/controls/xformUtil", "dojo/_base/array", "mui/form/_FormBase", "mui/dialog/Confirm", "dojo/_base/lang", "mui/history/listener"],
    function (parser, dom, domConstruct, registry, topic, adapter, ready, Validation, query, domClass, domStyle, xUtil, array, FormBase, Confirm, lang, listener) {
        window._sDetailsTableAutoAdaptionScript = {
            init: function (objId, tableId, showRow, required, backTo) {
                if (window["_isSingleRowMode_" + objId]) {
                    topic.subscribe('mui/switch/statChanged', function (wgt) {
                        window['autoAdpation_' + objId + '_action'](wgt);
                    });
                }
                window['autoAdpation_' + objId + '_action'] = function (wgt) {
                    var switchId = 'detailsTableAutoAdaption_' + tableId;
                    if (wgt.id == switchId) {
                        var val = wgt.value;
                        var tableDomEle = dom.byId('_' + tableId);
                        var domArr = query('[widgetid]', tableDomEle);
                        var scrollableViewObj = $(tableDomEle).closest(".mblScrollableView");
                        var scrollableViewWgt;
                        if (scrollableViewObj && scrollableViewObj[0]) {
                            scrollableViewWgt = registry.byNode(scrollableViewObj[0]);
                        }
                        //调整label样式
                        var labelDomArr = query('label', tableDomEle);
                        //调整td样式
                        var tdDomArr = query("td", tableDomEle);

                        var autoAdaptionClass = 'muiDetailTableAutoAdaption';
                        for (var i = 0; i < labelDomArr.length; i++) {
                            if (val === "on") {
                                domClass.add(labelDomArr[i], autoAdaptionClass);
                            }
                            if (val === "off") {
                                domClass.remove(labelDomArr[i], autoAdaptionClass);
                            }
                        }

                        for (var i = 0; i < tdDomArr.length; i++) {
                            if (val === "on") {
                                domClass.add(tdDomArr[i], autoAdaptionClass);
                            }
                            if (val === "off") {
                                domClass.remove(tdDomArr[i], autoAdaptionClass);
                            }
                        }
                        var delOprDomArr = query(".muiDetailTableDel", tableDomEle);
                        for (var i = 0; i < delOprDomArr.length; i++) {
                            if (val === "on") {
                                domClass.add(delOprDomArr[i], autoAdaptionClass);
                            }
                            if (val === "off") {
                                domClass.remove(delOprDomArr[i], autoAdaptionClass);
                            }
                        }
                        var delIconDomArr = query(".muiDetailTableDel i", tableDomEle);
                        for (var i = 0; i < delIconDomArr.length; i++) {
                            if (val === "on") {
                                domClass.add(delIconDomArr[i], autoAdaptionClass);
                            }
                            if (val === "off") {
                                domClass.remove(delIconDomArr[i], autoAdaptionClass);
                            }
                        }

                        if (val === "on") {
                            scrollableViewWgt.disableTouchScroll = true;
                            var arguH = scrollableViewWgt.containerNode.offsetHeight;
                            var parentW = scrollableViewWgt.getParent();
                            if (parentW && parentW.resizeH) {
                                //parentW.resizeH(arguH);
                            }
                        }
                        if (val === "off") {
                            scrollableViewWgt.disableTouchScroll = false;
                            scrollableViewWgt.scrollDir = "h";
                            scrollableViewWgt._v = (scrollableViewWgt.scrollDir.indexOf("v") != -1); // vertical scrolling
                            scrollableViewWgt._h = (scrollableViewWgt.scrollDir.indexOf("h") != -1); // horizontal scrolling
                            var arguH = scrollableViewWgt.containerNode.offsetHeight;
                            var parentW = scrollableViewWgt.getParent();
                            if (parentW && parentW.resizeH) {
                                //parentW.resizeH(arguH);
                            }
                        }
                        //发布自适应事件
                        topic.publish('/mui/form/detailTableAutoAdaption', wgt);
                        topic.publish('/mui/list/resize', scrollableViewWgt);
                    }
                }

                var _curview = null;
                window['detail_' + objId + '_show'] = function (addNewRow, callbackFun, srcElement) {
                    var backCallback = lang.hitch(this, function() {
                  	  // 浏览器后退，清掉beforeSelectCateHistoryId
    				    var callback = function(bool,dialog){
    				    	if(bool){
    				    		//确定
    				    		window["detail_" + objId + "_hide"]();
    				    	}else{
    				    		//取消
    				    		window["detail_" + objId + "_cancel"]();
    				    	}
    				    }
                    	Confirm("","是否保存此次编辑？",callback);
                    });
                    var listenerResult = listener.push({
                  		backCallback: backCallback
                    });
                    window.beforeSelectCateHistoryId = listenerResult.previousId
                    var tmpl = dom.byId("dt_wrap_" + objId + "_view");
                    var singleRowEditView = registry.byNode(tmpl);
                    if (_curview == null)
                        _curview = registry.byId(backTo);//scrollView
                    if (_curview.isdbClick && _curview.isdbClick()) {
                        return;
                    } else {
                        _curview.lastTime = new Date().getTime();
                    }
                    _curview.set('validateNext', false);
                    var editDetailTable = dom.byId(tableId);
                    var viewDetailTable = dom.byId("_" + tableId);
                    var showIndex = window["detail_" + objId + "_getRowIndex"](srcElement, viewDetailTable);
                    query("tr[class='contentRow']", editDetailTable).forEach(function (obj, index) {
                        if (showIndex === index) {
                            domStyle.set(obj, "display", "");
                        } else {
                            domStyle.set(obj, "display", "none");
                        }
                    });
                    if (_curview.domNode.parentNode == tmpl.parentNode) {
                        var validated = _curview.performTransition('dt_wrap_' + objId + '_view', 1, "slide");
                        if (validated) {
                            tmpl.style.display = 'block';
                        }
                        var hViewWgt = registry.byId('dt_wrap_' + objId + '_hview');
                        domStyle.set(hViewWgt.containerNode, "width", "100%");
                        setTimeout(function () {
                            hViewWgt.resize();
                            singleRowEditView.resize();
                            var bottomTip = query(".addBottomTip", tmpl);
                            if(bottomTip.length>0){
                            	bottomTip[0].style.display = 'none';
                            }
                        }, 500);
                    }

                }

                // <#-- 获取内容行索引 ,单行编辑或者删除行时，都要获取对应的行索引-->
                window["detail_" + objId + "_getRowIndex"] = function (srcElement, context) {
                    if (!srcElement) {
                        return null;
                    }
                    var showIndex = 0;
                    var trDom = query(srcElement).closest("tr[class='contentRow']");
                    //var viewDetailTable = dom.byId("_${tableId}");
                    var allContentRow = query("tr[class='contentRow']", context);
                    //获取点击行索引
                    for (var i = 0; i < allContentRow.length; i++) {
                        if (trDom[0] && (trDom[0] === allContentRow[i])) {
                            showIndex = i;
                            break;
                        }
                    }
                    return showIndex;
                };

                // <#-- 解析编辑视图table -->
                window["detail_" + objId + "_parseTable"] = function () {
                    var tmpl = dom.byId("dt_wrap_" + objId + "_view");
                    if (_curview == null) {
                        _curview = registry.byId(backTo);
                    }
                    if (!_curview) {
                        setTimeout(function () {
                            window["detail_" + objId + "_parseTable"]();
                        }, 100);
                        return;
                    }
                    // <#-- 未初始化则初始化 -->
                    if (_curview.domNode.parentNode != tmpl.parentNode) {
                        domConstruct.place(tmpl, _curview.domNode.parentNode, 'last');
                        parser.parse(tmpl).then(function () {
                        	if (DocList_TableInfo[objId] == null) {
                                DocListFunc_Init();
                            }
                            if (window["_editShow_" + objId]) {
                                if (window["_emptyFormData_" + objId]) {
                                    try {
                                        for (var i = 0; i < showRow; i++) {
                                            window["detail_" + objId + "_addEditRow"]();
                                        }
                                    } catch (e) {

                                    }
                                }
                            }
                            topic.publish("/mui/form/rowValueChanged", null, { tableId: objId });
                            topic.publish("/mui/datailTable/init", null, { tableId: objId });
                        });
                    }
                };

                // <#-- 返回不做校验 -->
                window["detail_" + objId + "_back"] = function () {
                    var view = registry.byId('dt_wrap_' + objId + '_view');
                    view.set('validateNext', false);
                    var result = view.performTransition(_curview.id, -1, "slide");
                    _curview.set('validateNext', true);
                    if (_curview.validate) {
                        _curview.validate();
                    }
                    if (view.oldGetValidateElements) {
                        view.getValidateElements = view.oldGetValidateElements;
                    }
                    return result;
                };

                // <#-- 取消按钮处理函数 -->
                window["detail_" + objId + "_cancel"] = function () {
                	
                    //adapter.goBack(true);
                    domStyle.set(_curview.domNode, "display", "block");
                    var view = dom.byId('dt_wrap_' + objId + '_view');
                    domStyle.set(view, "display", "none");
                    if (view.oldGetValidateElements) {
                        view.getValidateElements = view.oldGetValidateElements;
                    }
                    if (window["_isSingleRowMode_" + objId]) {
                        //判断是否需要自适应
                        var switchWgt = registry.byId('detailsTableAutoAdaption_' + tableId);
                        if (switchWgt) {
                            //topic.publish('mui/standDetailTable/singleRowView', objId);
                            if (switchWgt.value === "on") {
                                //window['autoAdpation_' + objId + '_action'](switchWgt);
                            }
                        }
                    }
                    window["detail_" + objId + "_resize"](null, dom.byId("_TABLE_DL_" + objId));
                    if (_curview) {
                        _curview.set('validateNext', true);
                    }

                	if(window.beforeSelectCateHistoryId){
                		listener.goNoop({
                			historyId: window.beforeSelectCateHistoryId
                		});
                		window.beforeSelectCateHistoryId = null;
                	}
                };


                // <#-- 确定按钮处理函数,返回做校验 -->
                window["detail_" + objId + "_hide"] = function () {
                    if (_curview.isdbClick && _curview.isdbClick()) {
                        return;
                    } else {
                        _curview.lastTime = new Date().getTime();
                    }
                    var view = registry.byId('dt_wrap_' + objId + '_view');

                    view.oldGetValidateElements = view.getValidateElements;

                    // <#-- 检验框架此函数获取到的是所有的行，因此需要覆盖，只获取当前行,执行完校验才还原 -->
                    view.getValidateElements = function () {
                        var elems = [];
                        if (view.domNode) {
                            var showRow;
                            query("tr[class='contentRow']", view.domNode).forEach(function (obj, i) {
                                if (domStyle.get(obj, "display") !== "none") {
                                    showRow = obj;
                                    return false;
                                }
                            });
                            array.forEach(query("[widgetid]", showRow), function (node) {
                                var w = registry.byNode(node);
                                if ((w instanceof FormBase) && w.edit == true) {
                                    elems.push(w);
                                }
                            });
                            array.forEach(query("[validate]", showRow), function (node) {
                                elems.push(node);
                            });
                        }
                        return elems;
                    };

                    if (view.validate) {
                        if (view.validate()) {
                            //adapter.goBack(true);
                            domStyle.set(_curview.domNode, "display", "block");
                            domStyle.set(view.domNode, "display", "none");
                        }
                    } else {
                        adapter.goBack(true);
                    }

                    view.getValidateElements = view.oldGetValidateElements;
                    if (window["_isSingleRowMode_" + objId]) {
                        //判断是否需要自适应
                        var switchWgt = registry.byId('detailsTableAutoAdaption_' + tableId);
                        if (switchWgt) {
                            topic.publish('mui/standDetailTable/singleRowView', objId);
                            if (switchWgt.value === "on") {
                                window['autoAdpation_' + objId + '_action'](switchWgt);
                            }
                        }
                    }
                    window["detail_" + objId + "_resize"](null, dom.byId("_TABLE_DL_" + objId));
                    if (_curview) {
                        _curview.set('validateNext', true);
                    }

                	if(window.beforeSelectCateHistoryId){
                		listener.goNoop({
                			historyId: window.beforeSelectCateHistoryId
                		});
                		window.beforeSelectCateHistoryId = null;
                	}
                };

                // <#-- 调整高度 -->
                window["detail_" + objId + "_resize"] = function (newRow, optTB) {
                    var scrollHView;
                    var context = newRow || optTB;
                    if (context) {
                        for (var parent = context.parentNode; parent != null; parent = parent.parentNode) {
                            if (domClass.contains(parent, "detailTableContent")) {
                                break;
                            }
                            if (domClass.contains(parent, "mblScrollableView")) {
                                scrollHView = parent;
                                break;
                            }
                        }
                        if (scrollHView) {
                            var wgt = registry.byNode(scrollHView);
                            var arguH = wgt.containerNode.offsetHeight;
                            var parentW = wgt.getParent();
                            if (parentW && parentW.resizeH) {
                                //parentW.resizeH(arguH);
                            }
                        }
                    }
                    // <#-- 在以桌面端显示的时候新增行需要更新高度 -->
                    topic.publish("/mui/list/resize", newRow);
                };

                if (window["_editShow_" + objId]) {
                    // <#-- 添加按钮处理函数,先添加编辑行,再添加查看行 -->
                    window["detail_" + objId + "_add"] = function (event) {
                        event = event || window.event;
                        if (event.stopPropagation) {
                            event.stopPropagation();
                        } else {
                            event.cancelBubble = true;
                        }
                        window["detail_" + objId + "_addRow"]();
                    };


                    // <#-- 添加行 -->
                    window["detail_" + objId + "_addRow"] = function (callbackFun) {
                        window["detail_" + objId + "_addEditRow"](callbackFun);
                        window["detail_" + objId + "_addViewRow"]();
                        if (window["_isSingleRowMode_" + objId]) {
                            //判断是否需要自适应
                            var switchWgt = registry.byId('detailsTableAutoAdaption_' + tableId);
                            if (switchWgt) {
                                topic.publish('mui/standDetailTable/singleRowView', objId);
                                if (switchWgt.value === "on") {
                                    window['autoAdpation_' + objId + '_action'](switchWgt);
                                }
                            }
                        }
                    };

                    // <#-- 添加编辑行 -->
                    window["detail_" + objId + "_addEditRow"] = function (callbackFun) {
                        //添加编辑视图行
                        var newRow = DocList_AddRow(tableId);
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
                            window["detail_" + objId + "_fixNo"]();
                            var view = registry.byId('dt_wrap_' + objId + '_view');
                            if (view.resize)
                                view.resize();
                            topic.publish('/mui/form/rowValueChanged', null, { row: newRow, tableId: objId, eventType: 'detailsTable-addRow' });
                            if (callbackFun) callbackFun(newRow);
                        });
                    };

                    // <#-- 添加查看行 -->
                    window["detail_" + objId + "_addViewRow"] = function (callbackFun) {
                        //添加查看视图行
                        var viewRow = DocList_AddRow("_" + tableId);
                        // <#-- 在以桌面端显示的时候新增行需要更新高度 -->
                        window["detail_" + objId + "_resize"](viewRow);
                        //绑定行点击事件
                        viewRow.onclick = function (event) {
                            var srcElement = event.srcElement;
                            window['detail_' + objId + '_show'](null, null, srcElement);
                        };
                    };


                    // <#-- 删除按钮处理函数,先获取删除的行索引，再进行删除操作  -->
                    window["detail_" + objId + "_del"] = function (domNode) {
                    	event = arguments.callee.caller.arguments[0];
                        if (event.stopPropagation)
                            event.stopPropagation();
                        if (event.cancelBubble)
                            event.cancelBubble = true;
                        if (event.preventDefault)
                            event.preventDefault();
                        var viewDetailTable = dom.byId("_" + tableId);
                        var rowIndex = window["detail_" + objId + "_getRowIndex"](domNode, viewDetailTable);
                        window["detail_" + objId + "_delViewRow"](domNode);
                        window["detail_" + objId + "_delEditRow"](rowIndex);
                        topic.publish('/mui/form/rowValueChanged', null, { tableId: objId, eventType: 'detailsTable-delRow' });
                    };


                    // <#-- 删除查看行 -->
                    window["detail_" + objId + "_delViewRow"] = function (domNode) {
                        var tr = $(domNode).closest('tr')[0];
                        var optTB = DocListFunc_GetParentByTagName("TABLE", tr);
                        window["_detail_" + objId + "_delRow"](tr);
                        window["detail_" + objId + "_resize"](tr, optTB);
                    };

                    // <#-- 删除编辑行 -->
                    window["detail_" + objId + "_delEditRow"] = function (rowIndex) {
                        var editDetailTable = dom.byId(tableId);
                        var editTrDom = window["_detail_" + objId + "_getContentRowByIndex"](rowIndex, editDetailTable);
                        if (editTrDom) {
                            window["_detail_" + objId + "_delRow"](editTrDom);
                        }
                    };

                    // <#-- 根据指定的的索引获取内容行 -->
                    window["_detail_" + objId + "_getContentRowByIndex"] = function (index, context) {
                        var contentRow;
                        query("tr[class='contentRow']", context).forEach(function (obj, i) {
                            if (index === i) {
                                contentRow = obj;
                                return false;
                            }
                        });
                        return contentRow;
                    };

                    // <#-- 数据填充控件调用,domNode指的是编辑行 -->
                    window["detail_" + objId + "_delRow"] = function (domNode) {
                        var editDetailTable = dom.byId(tableId);
                        var viewDetailTable = dom.byId("_" + tableId);
                        var rowIndex = window["detail_" + objId + "_getRowIndex"](domNode, editDetailTable);
                        var viewTrDom = window["_detail_" + objId + "_getContentRowByIndex"](rowIndex, viewDetailTable);
                        window["detail_" + objId + "_delViewRow"](viewTrDom);
                        window["detail_" + objId + "_delEditRow"](rowIndex);
                    };



                    // <#-- 销毁行内控件,更新其它行控件的索引 -->
                    window["_detail_" + objId + "_delRow"] = function (trDom) {
                        $(trDom).find("*[widgetid]").each(function (idx, widgetDom) {
                            var widget = registry.byNode(widgetDom);
                            if (widget && widget.destroy) {
                                widget.destroy();
                            }
                        });
                        var optTB = DocListFunc_GetParentByTagName("TABLE", trDom);
                        if (!optTB) {
                            return;
                        }
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
                                        tmpFileds[0].name = window["detail_" + objId + "_repalceIndexInfo"](tmpFileds[0].name, i - tbInfo.firstIndex);
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
                        var view = registry.byId('dt_wrap_' + objId + '_view');
                        if (view && view.resize)
                            view.resize();
                        
                    };


                    window["detail_" + objId + "_repalceIndexInfo"] = function (fieldName, index) {
                        fieldName = fieldName.replace(/\[\d+\]/g, "[!{index}]");
                        fieldName = fieldName.replace(/\.\d+\./g, ".!{index}.");
                        fieldName = fieldName.replace(/!\{index\}/g, index);
                        return fieldName;
                    }
                    window["detail_" + objId + "_fixNo"] = function () {
                        $('#' + tableId).find('.muiDetailTableNo').each(function (i) {
                            $(this).children('span').text("<bean:message  bundle='sys-xform-base' key='Designer_Lang.controlDetailsTable_the'/> " + (i + 1) + " <bean:message  bundle='sys-xform-base' key='Designer_Lang.controlDetailsTable_item'/>");
                        });
                    }
                    // <#--明细表删除最后一行校验-->
                    window["_sys_xfrom_detailsTable_requiredInit_" + objId] = function (tableId) {
                        var validateName = 'required';
                        var listendDomId = tableId + "_position";
                        var tipMessage = Data_GetResourceString('sys-xform:sysForm.detailsTable.tipMessage');
                        var inputTemplate = "<input id='" + listendDomId + "' type='text' style='display:table-cell;width:0px;height:1px;border:0px;' subject='" + tipMessage + "' validate='" + validateName + "'/>";
                        var tbDom = document.getElementById(tableId);
                        $(tbDom).after(inputTemplate);
                        DocList_Xform_DetailsTable_SetInputValue($('#' + listendDomId), tableId);
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
                        // <#--添加行，删除行触发-->
                        $(document).on('table-delete-new table-add-new table-add"', listened, function (e, obj) {
                            var table = obj.table || document.getElementById('TABLE_DL_' + objId);
                            if (obj && table) {
                                DocList_Xform_DetailsTable_SetInputValue($listen, table.id);
                                // <#--执行校验-->
                                validation.validateElement($listen[0]);
                            }
                        });
                    }
                }

                topic.subscribe('parser/done',function(){
                	if (DocList_TableInfo['_' + objId] == null) {
                        DocListFunc_Init();
                    }
                    if (window["_editShow_" + objId]) {
                    	if (window["_emptyFormData_" + objId]) {
                            try {
                                for (var i = 0; i < showRow; i++) {
                                    window["detail_" + objId + "_addViewRow"]();
                                }
                            } catch (e) {
                            }
                        }
                        // <#--#48057明细表删除最后一行校验 by liwc-->
                        if (required === "true") {
                            window["_sys_xfrom_detailsTable_requiredInit_" + objId]('_TABLE_DL_' + objId);
                        }
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
                    // <#--73279移动端前端计算控件计算明细表异常-->
                    window["detail_" + objId + "_parseTable"]();
                    if (window["_isSingleRowMode_" + objId]) {
                        topic.publish('mui/standDetailTable/singleRowView', objId);
                        var switchWgt = registry.byId('detailsTableAutoAdaption_' + tableId);
                        if (switchWgt) {
                            var switchTitle = query(".muiFormEleTitle", switchWgt.containerNode);
                            if (switchTitle.length > 0) {
                                domClass.add(switchTitle[0], "autoAdaptionSwitchTitle");
                            }
                            topic.publish('mui/switch/statChanged', switchWgt, switchWgt._value);
                        }
                        //监听控件值改变事件
                        topic.subscribe('/mui/form/rowValueChanged', function (wgt, val) {
                            if (wgt) {
                                var controlDetailTableId;
                                var name = xUtil.parseName(wgt);
                                if (name && /\.(\d+)\./g.test(name)) {
                                    var index = name.indexOf(".");
                                    controlDetailTableId = name.substring(0, index);
                                }
                                if (controlDetailTableId === objId) {
                                    if (switchWgt) {
                                        topic.publish('mui/standDetailTable/singleRowView', objId);
                                        if (switchWgt.value === "on") {
                                            window['autoAdpation_' + objId + '_action'](switchWgt);
                                        }
                                    }
                                }
                            }
                        });
                    }
                })
            }
        }
    });