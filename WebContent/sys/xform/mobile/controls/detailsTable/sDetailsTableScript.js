define(["dojo/parser", "dojo/dom", "dojo/dom-construct", "dojo/dom-style", "dojo/dom-attr", "dojo/dom-class", "dojo/query",
    "dijit/registry", "dojo/topic", "dojo/on", "mui/dialog/Tip", "dojo/ready", "mui/form/validate/Validation"],
    function (parser, dom, domConstruct, domStyle, domAttr, domClass, query, registry, topic, on, Tip, ready, Validation) {
        window._sDetailsTableScript = {
            init: function (objId, tableId, showRow, required) {
            	window.initShow = function(){
           			if(dojoConfig.dingXForm && dojoConfig.dingXForm === "true"){
	            		$(".close_details_div").css('display','block'); 
	            		$(".details_img").attr('src',Com_Parameter.ContextPath+"sys/xform/mobile/resource/img/closed2x.png");
	            	}else{
	            		$(".close_details_div").css('display','none'); 
	            	}
            	}
            	var urlParm = window.location.search;
            	var methodCode="";
				if (urlParm.indexOf("?") != -1) {
					var strs = urlParm.substr(1).split("&");
					for(var i=0;i<strs.length;i++){
				        var kv = strs[i].split('=');
				        if(kv[0] == 'method'){
				        	methodCode = kv[1];
				        	break;
				        }
					}
				}
				if("edit"==methodCode){
					initShow();
				} 
				if("view"==methodCode){
					$(".close_details_div").css('display','none'); 
				}
            	
                if (window["_editShow_" + objId]) {
                    window["detail_" + objId + "_add"] = function (event) {
                        event = event || window.event;
                        if (event.stopPropagation) { event.stopPropagation(); }
                        else { event.cancelBubble = true; }
                        window["detail_" + objId + "_addRow"]();
                        var tabInfo = DocList_TableInfo[tableId]; 
                        if(tabInfo && tabInfo.lastIndex > 5){
                        	initShow();
                        }
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
                            window["detail_" + objId + "_fixNo"]();
                            topic.publish('/mui/form/valueChanged', null, { row: newRow, tableId: objId });
                            // <#-- 在以桌面端显示的时候新增行需要更新高度 -->
                            window["detail_" + objId + "_resize"](newRow);
                            if (callbackFun) callbackFun(newRow);
                        });
                    };

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
                        //topic.publish("/mui/list/resize", newRow);
                    };

                    window["detail_" + objId + "_del"] = function (domNode) {
                        var tr = $(domNode).closest('tr')[0];
                        window["detail_" + objId + "_delRow"](tr);
                    };
                    window["detail_" + objId + "_delRow"] = function (trDom) {
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
                        if(tbInfo && tbInfo.lastIndex <= 5){
                        	if(dojoConfig.dingXForm && dojoConfig.dingXForm === "true"){
                        		$(".close_details_div").css('display','none'); 
                        	}
                        }
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
                        topic.publish('/mui/form/valueChanged', null, { tableId: objId, eventType: 'detailsTable-delRow' });
                        window["detail_" + objId + "_resize"](trDom, optTB);
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
                }
                topic.subscribe('parser/done',function(){
                	var _index =0;
                	window.close_details_fun =function(v){
						if(dojoConfig.dingXForm && dojoConfig.dingXForm === "true"){
							var details_span = $(v).find("#details_span");
		            		var spanValue = details_span[0].innerHTML;
		            		if("收起"==spanValue){
		            			$(v).find("#details_img").attr('src',Com_Parameter.ContextPath+"sys/xform/mobile/resource/img/open2x.png");
		            			details_span[0].innerHTML="展开";
		            			open_details_fun("closed",v);
		            		}else{
		            			$(v).find("#details_img").attr('src',Com_Parameter.ContextPath+"sys/xform/mobile/resource/img/closed2x.png");
		            			details_span[0].innerHTML="收起";
		            			open_details_fun("open",v);
		            		}
		            	}
					}
                	window.open_details_fun=function(flag,v){
						var detailTrDing = $(v).parent().closest("tr").find(".detailTrDing");
	            		if(detailTrDing && detailTrDing.length>4){
	            			   for(var i=0;i<detailTrDing.length;i++){
	            				  var spanIndex =$(detailTrDing[i]).children('td').eq(_index).children('span').text().trim();
            					  if(spanIndex && spanIndex>4){
            						  if("closed"==flag){
            							  if(spanIndex==5){
        	            					  $(detailTrDing[i]).css({ opacity: .3 });
        	            				  }else{
        	            					  $(detailTrDing[i]).css('display','none'); 
        	            				  }
            						  }else{
            							  $(detailTrDing[i]).css({ opacity: 1 });
            							  $(detailTrDing[i]).css('display','table-row'); 
            						  }
            					  }
	            			  }
	            		}
					}
                	if (DocList_TableInfo[objId] == null) {
                        DocListFunc_Init();
                    }
                    if (window["_editShow_" + objId]) {
                    	_index = 1;
                        if (window["_emptyFormData_" + objId]) {
                            try {
                                setTimeout(function () {
                                    for (var i = 0; i < showRow; i++) {
                                        window["detail_" + objId + "_addRow"]();
                                    }
                                }, 100);
                            } catch (e) {
                            }
                        }
                        // <#--#48057明细表删除最后一行校验 by liwc-->
                        if (required === "true") {
                            window["_sys_xfrom_detailsTable_requiredInit_" + objId]('TABLE_DL_' + objId);
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
                    if(dojoConfig.dingXForm && dojoConfig.dingXForm === "true"){
                		Com_IncludeFile("dingTop.css", Com_Parameter.ContextPath + "km/review/km_review_ui/dingSuit/css/","css",true);
                	}
                });
            }
        }
    });