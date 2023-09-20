/**
 * 单选选择框
 */
define([ "dojo/_base/declare", "dojo/_base/lang", "mui/form/_FormBase",
				"dojo/request", "mui/util", "dojo/dom-construct",
				"sys/modeling/main/xform/controls/placeholder/mobile/Util",
				"mui/form/_PlaceholderMixin", "sys/xform/mobile/controls/EventDataDialog",
				"sys/xform/mobile/controls/xformUtil","dojo/_base/array", "dojo/query","dijit/registry","dojo/dom-class"],
		function(declare, lang, _FormBase, request, util, domConstruct, placeholderUtil, _PlaceholderMixin, DataDialog, xUtil, array, query,registry,domClass) {

			return declare(
					"sys.modeling.main.xform.controls.placeholder.mobile.dialog.Dialog",
					[ _FormBase, _PlaceholderMixin ],
					{

						fetchUrl : "/sys/modeling/main/modelingAppXFormMain.do?method=executeQuery&pageno=!{currentPage}&rowsize=15",

						opt : true,

						isMul : false,

						dataDialogWgt : DataDialog,

						dataDialog : null,

						defalutNull : "",	// 为null，默认显示值

						rowsize : 10,

						getCfgInfo : function(){
							return this.envInfo;
						},

						//加载
						startup : function() {
							this.inherited(arguments);
						},

						buildRendering : function() {
							this.inherited(arguments);
							// 存放隐藏字段的节点
							this.inputContent = domConstruct.create("div", {
								className : "muiSelInput",
								style : {
									"line-height" : "1.8rem",
									"padding-right" : "24px"
								}
							}, this.valueNode)
							// 显示文本的节点
							this.contentNode = domConstruct.create("div", {
								className : "relationChooseText"
							}, this.inputContent);
							this._buildValue();
						},

						postCreate : function() {
							this.inherited(arguments)
							if (this.edit) {
								this.subscribe("/sys/xform/event/modeling_selected", "_fillDataInfo_modeling")
								// 下一页事件
								this.subscribe("/sys/xform/event/nextpage", "_getNextDataInfo")
								//this.subscribe("/sys/xform/event/cancel", "_clearDataInfo")
							}
						},

						// 构建值区域
						_buildValue : function() {
							this.inherited(arguments);
							var setBuildName = "build"
									+ util.capitalize(this.showStatus)
							this[setBuildName] ? this[setBuildName]() : ""
							var setMethdName = this.showStatus + "ValueSet"
							this.showStatusSet = this[setMethdName] ? this[setMethdName]
									: new Function();
							if (this.value) {
								//#171369 【日常缺陷】【低代码平台-修复】移动端，业务操作-新建视图，携带业务关联多选的时候，空格会转义，点击链接里面的查看视图按钮
								this.setValue(this.text.replaceAll("&amp;","&"),this.value);
							}
						},

						// 编辑状态渲染
						buildEdit : function() {
							this.hiddenValueNode = this._buildHiddenInput(
									this.name, this.value, this.inputContent)
							this.hiddenTextNode = this._buildHiddenInput(
									this.textName, this.text, this.inputContent);
							this.connect(this.contentNode, "click", lang.hitch(this,this.openDialog));
						},

						// 查看状态渲染
						buildView : function() {
							this.hiddenValueNode = this._buildHiddenInput(
									this.name, this.value, this.inputContent)
							this.hiddenTextNode = this._buildHiddenInput(
									this.textName, this.text, this.inputContent)
						},

						_buildHiddenInput : function(name, val,
								parentNode) {
							var input = domConstruct.create("input", {
								type : "hidden",
								name : name
							}, parentNode)
							if (val) {
								input.value = val
							}
							return input
						},


						openDialog : function() {
							if(domClass.contains(this.domNode,"muiFormStatusReadOnly")){
								return;
							}
							this.exceQuery(null, lang.hitch(this,
									this.onDataLoad));
						},

						_getNextDataInfo : function(srcObj, evt, handle) {
							if (evt) {
								if (srcObj.key == this.name) {
									var self = this;
									// 如果查出来的数据比页数还要少，就不用再次查询了
									if (evt.dataLength && evt.dataLength < this.rowsize) {
										handle.done(null);
										return;
									}
									this.exceQuery({
										currentPage : evt.pageNum,
										paramsJSON : evt.paramsJSON
									}, function(data) {
										handle.done(data);
									}, function() {
										handle.done(null);
									});
								}
							}
						},

						// 请求查询
						exceQuery : function(evt, successFun, errFun){
							var self = this;
							if(!evt){
								// 初始化页数
								evt = {
									currentPage : 0,
									paramsJSON : {}
								};
							}
							var search = "";
							if(evt.paramsJSON && evt.paramsJSON.search){
								search = evt.paramsJSON.search;
							}
							var queryUrl = this.fetchUrl + "&fdAppModelId="+ this.appModelId +"&widgetId=" + this.controlId;
							queryUrl = util.urlResolver(queryUrl, evt);
							// 获取传入参数
							var formDatas = placeholderUtil.findFormValueByCfg(this.getCfgInfo());
							this.insFormDatas = formDatas;
							request.post(util.formatUrl(queryUrl),{data:{ins : JSON.stringify(formDatas),search:search} ,handleAs : 'json'}).then(function(data){
								if(data.page.rowsize){
									self.rowsize = data.page.rowsize;
								}
								successFun(data);
							}, errFun);
						},

						// 数据请求渲染
						onDataLoad : function(data) {
							this.switchToDialog(data);
						},

						// 打开弹出框
						switchToDialog : function(data) {
							if (this.dataDialog === null) {
								this.dataDalog = new this.dataDialogWgt({
									templURL : "sys/modeling/main/xform/controls/placeholder/mobile/dialog/dataDialogRender.tmpl",
									modelingType:"modeling"
								});
							}
							var argu = this.getArguAdapter(data);
							this.dataDalog.select({
								isMul : this.isMul,
								pageAble : true,
								key : this.name,
								dataSource : data,
								argu : argu
							});
						},

						// 获取跟RelationEventBase一样的argu
						getArguAdapter : function(data){
							var queryUrl = this.fetchUrl + "&fdAppModelId="+ this.appModelId +"&widgetId=" + this.controlId;
							queryUrl = util.urlResolver(queryUrl, {
								currentPage : 0
							});
							var argu = {
								wgtName: this.name,
			                    appendSearchResult: null,
			                    paramsJSON: {
			                    	ins : JSON.stringify(this.insFormDatas)
			                    },
			                    outputsJSON: {},
			                    outerSearchParams: [],
			                    queryDataUrl: queryUrl,
			                    dataLength: data.columns.length,
			                    control: this,
			                    hasRowsContext: false
							};
							// 搜索设置的转换
							var searchInfo = this.getCfgInfo();
							if(searchInfo.status === "00"){
								var searchArr = searchInfo.search || [];
								for(var i = 0;i < searchArr.length;i++){
									if(searchArr[i].businessType == "textarea"){
										continue;
									}
									var tempSearch = {};
									tempSearch.columnName = tempSearch.tagName = searchArr[i].name;
									tempSearch.stype = tempSearch.stypeData = searchArr[i].type;
									tempSearch.isHidden = "false";
									tempSearch.sdesc = searchArr[i].label;
									tempSearch.businessType = searchArr[i].businessType;
									tempSearch.enumValues = searchArr[i].enumValues;
									if(searchArr[i].customProperties){
										tempSearch.customProperties = searchArr[i].customProperties;
									}
									argu.outerSearchParams.push(tempSearch);
								}
							}
							return argu;
						},

						// 回填值
						// rawRtn : [{docSubject:{value:xxxx},docCreatorId:{value:xxxx}}]
						_fillDataInfo_modeling : function(srcObj, rawRtn) {
							this.inherited(arguments);
							if (rawRtn) {
								if (this.name == srcObj.key) {
									if(!rawRtn){
										return;
									}
									var cfg = this.getCfgInfo();
									var outputCfgs = cfg["outputs"];

									this.isInDetail = this.controlId.indexOf(".") > -1 ? true : false;
									this._closeDialog(srcObj);
									/*if(this.isInDetail){
										this.detailIndex = xUtil.getCalculationRowNo(this.name);
									}*/
									if (!this.isInDetail) {
										this.setValueWhenCloseForWgt(this, rawRtn);
									}else {
										//#127170多选时，若在明细表内
										for (let i = 0; i < rawRtn.length; i++) {
											var rawItem = [rawRtn[i]];
											if (i == 0) {
												this.setValueWhenCloseForWgt(this, rawItem);
												continue;
											}
											//获取新增行的this指向
											var controlArray = this.controlId.split(".");
											var newRow = window["detail_" + controlArray[0] + "_add"];
											var newDom = $(newRow).find("xformflag[_xform_type='placeholder'][id^='_xform_extendDataFormInfo.value(" + controlArray[0] + "'][id$='" + controlArray[1] + ")']");
											var newWgt_xform = newDom[newDom.length-1];
											var newWgt = $(newWgt_xform).find(".muiFormEleWrap ");
											var newWgtId = $(newWgt).attr("id");
											this.setValueWhenCloseForWgt(registry.byId(newWgtId), rawItem);
										}
									}


								}
							}

						},

						setValueWhenCloseForWgt: function (wgt, rawRtn) {
							var cfg = wgt.getCfgInfo();
							var outputCfgs = cfg["outputs"];

							wgt.outputCfgs = outputCfgs;
							wgt.outputCfgs = outputCfgs;
							wgt.isInDetail = wgt.controlId.indexOf(".") > -1;
							if (wgt.isInDetail) {
								wgt.detailIndex = xUtil.getCalculationRowNo(wgt.name);
							}
							/*************** 设置传出参数 start ******************/
								//为非明细行合并数据
							var rtn_let = wgt.margeDataForNonDetail(rawRtn);
							// 目标控件为非明细表
							wgt.fillTargetControlsVal(rtn_let, outputCfgs["fields"]);
							// 目标控件为明细表
							wgt.fillTargetControlsValInDetail(rtn_let, outputCfgs["details"], rawRtn);
							/*************** 设置传出参数 end ******************/
							// 根据配置（表达式）设置当前控件值
							wgt.setCurrentControlVal(rawRtn, wgt.getCfgInfo());

						},
						_closeDialog : function(srcObj) {
				              //topic.publish(this.SELECT_CALLBACK, this);
				              if(this.dataDalog){
				                this.dataDalog.closeDialog(srcObj);
				                this.dataDalog = null;
				              }
				        },
						//为非明细行合并数据
						margeDataForNonDetail : function(rowInfos){
							// 把所有行的数据进行合并返回
							let rs = {};
							for(var i = 0;i < rowInfos.length;i++){
								var info = rowInfos[i];
								for(var key in info){
									// 多行记录的明细表，把所有行都放置在一起
									if(info[key].type === "detail"){
										if(!rs.hasOwnProperty(key)){
											rs[key] = {value:[], type : info[key].type};
										}
										rs[key].value = rs[key].value.concat(info[key].value);
									}else if(info[key].type.indexOf("com.landray.kmss.sys.organization") > -1){
										// 地址本
										var hasPre  = true;
										var val = info[key].value || {};
										if(!rs.hasOwnProperty(key)){
											rs[key] = {value : {id : "",name : ""}, type : info[key].type};
											hasPre = false;
										}
										rs[key].value.id += ";" + (val.id || "");
										rs[key].value.name += ";" + (val.name || "");
										if(!hasPre){
											rs[key].value.id = rs[key].value.id.substring(1);
											rs[key].value.name = rs[key].value.name.substring(1);
										}
									}else{
										var hasPre  = true;
										if(!rs.hasOwnProperty(key)){
											rs[key] = {value:"", type : info[key].type};
											hasPre = false;
										}
										var val = info[key].value || this.defalutNull;
										if (val === undefined && info[key].value !== undefined){
											val = info[key].value;
										}
										// 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
										if(typeof(val) === "object" && val.hasOwnProperty("value")){
											val = val["value"];
										}
										rs[key].value += ";" + val;
										if(!hasPre){
											rs[key].value = rs[key].value.substring(1);
										}
									}
								}
							}
							return rs;
						},

						// 给非明细表的控件赋值
						// rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
						fillTargetControlsVal : function(rtn, cfgs){
							// 数据来源为非明细表数据
							for(var key in cfgs["sourceCommon"]){
								if(rtn.hasOwnProperty(key)){
									var targets = cfgs["sourceCommon"][key].target || [];
									for(var i = 0;i < targets.length;i++){
										var targetInfo = targets[i];
										var targetId = targetInfo["controlId"];
										targetId = targetId.replace(".", "." + this.detailIndex + ".");
										this._fillControlVal(targetId, rtn[key]["value"], targetInfo["type"]);
									}
								}else{
									console.log("【业务关联控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
								}
							}
							// 数据来源为明细表数据
							for(var key in cfgs["sourceDetails"]){
								if(rtn.hasOwnProperty(key)){
									var sourceDetails = cfgs["sourceDetails"][key];
									for(var sourceControlId in sourceDetails){
										var targets = sourceDetails[sourceControlId].target || [];
										for(var i = 0;i < targets.length;i++){
											var targetInfo = targets[i];
											var values = rtn[key]["value"];
											var val = "";
											// 如果填充的值是数组，则把数据合并，以;号分开
											if(values.length){
												val = this._mergeDataInDetail(values, sourceControlId, targetInfo.type);
											}
											this._fillControlVal(targetInfo["controlId"], val ,targetInfo.type);
										}
									}
								}else{
									console.log("【业务关联控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
								}
							}
						},

						// 给明细表的控件赋值
						// rtn : 弹出框选择的行信息，cfgs ：后台配置的信息
						fillTargetControlsValInDetail : function(rtn, cfgs, rawRtn){
							if(this.isInDetail){
								// 数据来源为非明细表数据
								for(var key in cfgs["sourceCommon"]){
									if(rtn.hasOwnProperty(key)){
										var targets = cfgs["sourceCommon"][key].target || [];
										for(var i = 0;i < targets.length;i++){
											var targetInfo= targets[i];
											// 只允许给同明细表的同行数据赋值
											if(this.controlId.split(".")[0] === targetInfo.controlId.split(".")[0]){
												var targetId = targetInfo["controlId"];
												targetId = targetId.replace(".", "." + this.detailIndex + ".");
												this._fillControlVal(targetId, rtn[key]["value"], targetInfo["type"]);
											}else{
												console.log("【业务关联控件】不支持明细表内的业务关联控件给其他明细表赋值");
											}
										}
									}else{
										console.log("【业务关联控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
									}
								}

								// 数据来源为明细表数据
								for(var key in cfgs["sourceDetails"]){
									if(rtn.hasOwnProperty(key)){
										var sourceDetails = cfgs["sourceDetails"][key];
										for(var sourceControlId in sourceDetails){
											var targets = sourceDetails[sourceControlId].target || [];
											for(var i = 0;i < targets.length;i++){
												var targetInfo = targets[i];
												// 只允许给同明细表的同行数据赋值
												if(this.controlId.split(".")[0] === targetInfo.controlId.split(".")[0]){
													var values = rtn[key]["value"];
													var val = "";
													// 如果填充的值是数组，则把数据合并，以;号分开
													if(values.length){
														val = this._mergeDataInDetail(values, sourceControlId, targetInfo.type);
													}
													var targetId = targetInfo["controlId"];
													targetId = targetId.replace(".", "." + this.detailIndex + ".");
													this._fillControlVal(targetId, val ,targetInfo.type);
												}else{
													console.log("【业务关联控件】不支持明细表内的业务关联控件给其他明细表赋值");
												}
											}
										}
									}else{
										console.log("【业务关联控件】对话框行返回信息里面不包含\""+ key +"\"的值!");
									}
								}

							}else{
								//明显表行填充模式
								this.changeDetailsByFillType(cfgs);

								// 数据来源为非明细表数据,添加行,赋值
								for (let i=0; i < rawRtn.length; i++) {
									let singleRtn = rawRtn[i];
									this.addDetailsTableRow(cfgs["sourceCommon"], singleRtn);
									// 数据来源为明细表数据
									for (var key in cfgs["sourceDetails"]) {
										if (singleRtn.hasOwnProperty(key)) {
											this.setDetailsTableFieldValues(cfgs["sourceDetails"][key], singleRtn[key]["value"]);
										}
									}
								}
							}

						},
						// 明显表行填充模式
						changeDetailsByFillType :function (cfgs){
							// 1|覆盖行数据（清空+追加）
							if (this.getCfgInfo().hasOwnProperty('outExtend') && this.getCfgInfo().outExtend === '1') {
								var sourceCommon  = cfgs["sourceCommon"];
								var sourceDetails = cfgs["sourceDetails"];
								//数据来源是主表
								for (var key in sourceCommon) {
									this.overwriteRow(sourceCommon);
								}
								//数据来源是明细表
								for (var key in sourceDetails) {
									var sources = cfgs["sourceDetails"][key];
									this.overwriteRow(sources);
								}
							}
						}
						,
						overwriteRow: function (sources) {
								let tableIds = [];
									for (var sourceId in sources) {
									var targetInfos = sources[sourceId]["target"];
									// 一行数据有可能传出到多个目标控件
									for (var j = 0;j < targetInfos.length;j++) {
										let targetId = targetInfos[j]["controlId"];
										let targetIdArr = targetId.split(".");
										// 仅支持“明细表id.控件id”结构
										if (targetIdArr.length === 2) {
											var detailTableId = targetIdArr[0];
											if ($.inArray(detailTableId, tableIds) === -1) {
												tableIds.push(detailTableId);
												var tdArray = query(".detail_wrap_td", "TABLE_DL_" + detailTableId);
												if (tdArray.length == 0) {
													tdArray = query("td[kmss_isrowindex]", "TABLE_DL_" + detailTableId);
												}
												if (tdArray.length > 0) {
													array.map(tdArray, function (tdObj) {
														if (window['detail_' + detailTableId + '_delRow'])
															window['detail_' + detailTableId + '_delRow'](tdObj.parentNode);
													});
												}
											}
										}
									}
									}
								}
						,

						// 合并明细表多行的数据
						_mergeDataInDetail : function(valueArr, sourceId, targetType){
							var val = null;
							for(var i = 0;i < valueArr.length;i++){
								var valueInfo = valueArr[i];
								if(valueInfo.hasOwnProperty(sourceId)){
									var sourceVal = valueInfo[sourceId];
									if(targetType.indexOf("com.landray.kmss.sys.organization") > -1){
										// 地址本
										var hasPre  = true;
										val = val || {};
										if(JSON.stringify(val) === '{}'){
											val = {id : "",name : ""};
											hasPre = false;
										}
										val.id += ";" + (sourceVal.id || "");
										val.name += ";" + (sourceVal.name || "");
										if(!hasPre){
											val.id = val.id.substring(1);
											val.name = val.name.substring(1);
										}
									}else{
										var hasPre  = true;
										val = val || "";
										if(!val){
											val = "";
											hasPre = false;
										}
										var tempVal = sourceVal || "";
										if(typeof(tempVal) === "object" && tempVal.hasOwnProperty("value")){
											tempVal = tempVal["value"];
										}
										val += ";" + tempVal;
										if(!hasPre){
											val = val.substring(1);
										}
									}
								}
							}
							return val;
						},

						_fillControlVal : function(controlId, value, type){
							if(type.indexOf("com.landray.kmss.sys.organization") > -1){
								// 目标是地址本
								var id = "";
								var name = "";
								if(typeof(value) === "object"){
									id = value.id;
									name = value.name;
								}else{
									id = name = value;
								}
								$form(controlId + ".id").val(id);
								$form(controlId + ".name").val(name);
							}else{
								var controlVal = value;
								// 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
								if(typeof(controlVal) === "object" && controlVal.hasOwnProperty("value")){
									controlVal = controlVal["value"];
								}
								//#163163 业务关联控件在工具类里面的赋值方法不生效，给业务关联控件赋值是在本JS中自己写的赋值方法，在这通过"mobile_dialog_Dialog"来判断是否是业务关联控件的dom
								var wgt = xUtil.getXformWidget(null, controlId);
								if(wgt){
								  if (wgt.id.indexOf("mobile_dialog_Dialog")!=-1){
									wgt.buildTextItem(controlVal);
									return

								  }else {
									placeholderUtil.setFormValueById(controlId,controlVal);
									// SetXFormFieldValueById_ext(controlId,controlVal);
								  }
								}else{
									console.log("【业务关联控件】填充传出数据时，找不到控件ID为\""+ controlId +"\"的控件!");
								}
							}
						},

						// 源明细表给目标控件填充值
						setDetailsTableFieldValues : function(sources, values){
							for(var i = 0;i < values.length;i++){
								this.addDetailsTableRow(sources, values[i]);
							}
						},

						addDetailsTableRow : function(sources, rowValues){
							var fieldsVal = {};
							// 遍历每一行需要输出控件配置信息
							for(var sourceId in sources){
								var targetInfos = sources[sourceId]["target"];
								// 一行数据有可能传出到多个目标控件
								for(var j = 0;j < targetInfos.length;j++){
									var targetInfo = targetInfos[j];
									var value = rowValues;
									// 明细表外的控件给明细表内的控件赋值时，没有sourceId
									if(typeof(value) === "object"){
										if(value.hasOwnProperty(sourceId)){
											value = value[sourceId];
										}else{
											value = "";
											console.log("【业务关联控件】对话框行返回信息里面不包含输出控件ID\""+ sourceId +"\"的值!");
										}
									}
									var targetId = targetInfo["controlId"];
									var targetIdArr = targetId.split(".");
									// 仅支持“明细表id.控件id”结构
									if(targetIdArr.length === 2){
										var tableId = targetIdArr[0];
										if(!fieldsVal[tableId]){
											fieldsVal[tableId] = {};
										}
										if(value){
											if(targetInfo["type"].indexOf("com.landray.kmss.sys.organization") > -1){
												// 目标是地址本
												var id = "";
												var name = "";
												if (typeof (value) === "object") {
													//#133709 地址本源数据来自主表时多一层value对象
													var mainValue = value.value;
													if ( typeof (mainValue) ==="undefined"){
														id = value.id;
														name = value.name;
													}
													if (mainValue){
														id = mainValue.id;
														name = mainValue.name;
													}
												}else{
													id = name = value;
													console.log("【业务关联控件】目标控件ID("+ targetIdArr[1] +")是地址本，但由于填充的值只有一个，故只能id和name都填充同样的值!");
												}
												fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}."+ targetIdArr[1] +".id)"] = id;
												fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}."+ targetIdArr[1] +".name)"] = name;
											}else{
												var controlVal = value;
												// 由于返回的类型没法区分是否是是枚举类型，只能通过值的类型来区分
												if(typeof(controlVal) === "object" && controlVal.hasOwnProperty("value")){
													controlVal = controlVal["value"];
													//#160111 可能嵌套两层，需要尝试进一步解析
													if(typeof(controlVal) === "object" && controlVal.hasOwnProperty("value")){
														controlVal = controlVal["value"];
													}
												}
												fieldsVal[tableId]["extendDataFormInfo.value(" + targetIdArr[0] + ".!{index}."+ targetIdArr[1] +")"] = controlVal;
											}
										}else{
											console.log("【业务关联控件】对话框行返回信息里面不包含输出控件ID\""+ targetId +"\"的值!");
										}
									}else{
										console.log("【业务关联控件】输出控件ID("+ targetId +")结构异常，不符合\"明细表ID.控件ID\"结构！");
									}
								}
							}
							if (fieldsVal) {
								for (var tableId in fieldsVal) {
									// 这里有可能是新增一行空的一行，看后续需求是否需要做为空判断
									console.log("addrow",'detail_' + tableId + '_addRow');
									if(window['detail_' + tableId + '_addRow'] && JSON.stringify(fieldsVal[tableId]) !="{}"){
										window['detail_' + tableId + '_addRow'](function(rowTR){
											for(var prop in fieldsVal[tableId]){
												var widgtId = prop.replace(/!{index}/g,rowTR.sectionRowIndex-1);
												xUtil.setXformWidgetValues(xUtil.getXformWidget(rowTR,widgtId.replace(/\.name/gi,".id")), fieldsVal[tableId][prop],widgtId);
											}
										});
									}
								}
							}
						},

						// 对选择的数据进行表达式转换
						setCurrentControlVal : function(values,cfg){
							var text = [];
							var value = [];
							var expression = cfg["showTxt"]["expression"];
							if(values && values.length){
								for(var i = 0;i < values.length;i++){
									var info = values[i];
									text.push(placeholderUtil.transValByExp(info, expression));
									value.push(info[placeholderUtil.CONST.RECORDID]["value"]);
								}
							}
							//#132247 业务关联返回的控件是多选控件，多选的值现状是分开的(修改分隔符)
							this.setValue(text.join("&&"), value.join(";"));
						},

						setValue : function(text ,value){
							this.buildTextItem(text);
							//#132247 业务关联返回的控件是多选控件，多选的值现状是分开的(还原分隔符)
							/*var reg = new RegExp("&&","g");
							text.replace(reg,";");*/
							this.hiddenTextNode.value = text;
							this.hiddenValueNode.value = value;
							this.set("value",value);
						},

						buildTextItem: function(text) {
							var self = this;
							domConstruct.empty(this.contentNode);
							var itemDom = domConstruct.create("div", {
						          className: "relationChooseTextItem relationChooseTextItemEdit",
						          style : {
						        	  display : "block"
						          }
						        }, this.contentNode);
							var ul = domConstruct.create("ul", {}, itemDom);
							var textArr = text.split("&&");
							for(var i = 0;i < textArr.length;i++){
								var li = domConstruct.create("li", {
									style : {
										"display" : "inline-block",
										"list-style-type" : "none",
										"padding" : "0.5rem 1rem 0.5rem 0",
										"color" : "#008de2"
									}
								}, ul);
								//#167406 防止html代码注入
								li.innerText = textArr[i];
							}
							//#137652 移动端新建表单入参查询条件是这个控件的text时获取不到
							self.text =text;
							var iDom = domConstruct.create("i", {
								className : "mui mui-close"
							}, itemDom);

							// 添加事件
							// 清空值
							this.connect(iDom, "click", function(evt) {
								domConstruct.empty(self.contentNode)
								self.stopPropagation(evt)
								self.hiddenTextNode.value = "";
								self.hiddenValueNode.value = "";
								self.set("value","");
							})

							//#132247 业务关联返回的控件是多选控件，多选的值现状是分开的(还原分隔符)
							/*var reg = new RegExp("&&","g");
							text.replaceAll(reg,";");*/
							this.hiddenTextNode.value = text;
						},

						stopPropagation : function(evt) {
							// 停止冒泡
							if (evt.stopPropagation)
								evt.stopPropagation()
							if (evt.cancelBubble)
								evt.cancelBubble = true
							if (evt.preventDefault)
								evt.preventDefault()
							if (evt.returnValue)
								evt.returnValue = false
						},

					});
		})