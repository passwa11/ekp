// 属性变更控件
define([ "dojo/_base/declare", "dijit/_WidgetBase","dojo/dom","dojo/query", "sys/xform/mobile/controls/xformUtil", "dojo/json","sys/xform/mobile/controls/relationRule/RelationRuleAttrChange", "mui/dialog/Tip","dojo/ready","dojo/topic"], 
         function(declare, WidgetBase, dom, query, xUtil, json, RelationRuleAttrChange, Tip, ready, topic) {
	var claz = declare("sys.xform.mobile.controls.RelationRule", [WidgetBase], {
		
		// 触发控件ID
		bindDomId : null,
		
		// 触发事件
		bindEvent : null,
		
		// 目标控件集
		destDomIds : null,
		
		// 操作
		opsStr : null,
		
		postCreate : function(){
			this.inherited(arguments);
			if("change" == this.bindEvent){//监听表单事件变更
				this.subscribe("/mui/form/valueChanged","controlsChange");
			}else if('relation_rule_event' == this.bindEvent){//自定义事件
				var self = this; 
				$(document).bind('relation_rule_event',function(event, controlId){
					self.controlsChange(controlId);
				});
			}
		},
		
		startup : function(){
			this.inherited(arguments);
			var self = this;
			ready(function(){
				//必须等到_ValidateMixin初始化完
				self.defer(function(){
					if(window.__validateInit == true){
						self.init();
					}	
				},30);
			});
		},
		
		init : function(){
			var bindDomId = this.bindDomId;
			if(/(\w+)\.(\w+)/g.test(bindDomId)){
				var row = query(this.domNode).parents("tr[kmss_iscontentrow='1']");
				if(row.length>0){
					this.execAttrChange("01",{row:row[0]});	
				}else{
					this.execAttrChange("00");	
				}
			}else{
				this.execAttrChange("00");	
			}
		},
		
		// 控件改变触发
		controlsChange : function(srcObj, arguContext){
			var self = this;
			if(srcObj){
				var evtObjName;
				var cIndex = "-1";
				if(typeof(srcObj) == 'string'){
					//如果时明细表，要进行处理
					if(/(\w+.\d.\w+)/g.test(evtObjName)){
						var ids = srcObj.split(".");
						if(ids.length >= 3){
							cIndex = ids[1];
							evtObjName = ids[0]+ids[2];
						}
					}else{
						evtObjName = srcObj;
					}
				}else{
					evtObjName =  xUtil.parseXformName(srcObj);	
				}
				// 判断触发控件是否是属性变更控件配置的触发控件
				if(evtObjName!=null && evtObjName!=''){
					var bindDomId = this.bindDomId;
					if(bindDomId){
						var domArray = bindDomId.split(";");
						for(var i = 0;i < domArray.length;i++){
							var bindDomId = domArray[i];
							if(/-fd(\w+)/g.test(bindDomId)){
								bindDomId = bindDomId.replace(/-fd(\w+)/g,"");
							}
							if(evtObjName.indexOf(bindDomId) > -1){
								//判断触发的控件是否是明细表中的控件
								if((typeof(srcObj) == 'object' && xUtil.isInDetail(srcObj || {})) || (evtObjName && /(\w+.\w+)/g.test(evtObjName) && evtObjName.indexOf(".") >= 0 )){
									/*if(srcObj.srcNodeRef){
										srcNode = $(srcObj.srcNodeRef).parents("tr")[0];
									}else{
										srcNode = $(srcObj.valueNode).parents("tr")[0];
									}
									var rows = array.filter(,function(trObj){
										return dom.isDescendant(srcNode,trObj);
									});*/
									var detailTableId = "TABLE_DL_" + evtObjName.split(".")[0];
									if(typeof(srcObj) == "string" && cIndex == "-1"){
										//没有带角标，所有行进行触发
										query(">tbody>tr",dom.byId(detailTableId)).forEach(function(row){
											arguContext.row = row;
											self.execAttrChange("00",arguContext);	
										})
									}else{
										var rowIndex = null;
										if(typeof(srcObj) == "string"){
											rowIndex = cIndex;
										}else{
											rowIndex = xUtil.getCalculationRowNo(srcObj.name || srcObj.valueField 
													|| srcObj.idField  || (srcObj.params && srcObj.params.valueField));
										}
										if(rowIndex || rowIndex == 0){
											var rows = query(">tbody>tr",dom.byId(detailTableId)).at(parseInt(rowIndex)+1);
											if(rows && rows.length > 0){
												arguContext.row = rows[0];
												this.execAttrChange("00",arguContext);	
											}else{
												this.execAttrChange("00");	
											}
										}else{
											this.execAttrChange("00");	
										}
									}
								}else{
									this.execAttrChange("00");	
								}
								break;
							}
						}
					}
				}
			}else if(srcObj == null){
				// 明细表新增行触发
				if(arguContext && arguContext.row){
					this.execAttrChange("01", arguContext);
				}
			}
		},
		
		// trrigleType: 00--控件触发；01--明细表新增行; by zhugr 2017-09-05
		execAttrChange : function(trrigleType, _arguContext){
			setTimeout(function() {
			//处理目标控件的ID集，把ID集转换为组件集 start
			var destDomIds = this.destDomIds;
			var destIdArray = destDomIds.split(";");
			var assemblyArray = [];//目标组件集
			// 默认触发类型为控件触发
			if(!trrigleType){
				trrigleType = "00";
			}
            var _self = this;
			for(var i = 0;i < destIdArray.length;i++){
				var destId = destIdArray[i];
				var area = '';
				var arguContext =  _arguContext;
				// 锁定范围和目标控件
				if(/(\w+)\.(\w+)/g.test(destId)){
					//获取明细表ID
					var destArray = destId.split(".");
					var detailTableId = destArray[0];
					destId = destArray[1];
					if(trrigleType == "00"){
						// 限定当前table
						area = "TABLE_DL_" + detailTableId;
					}else if(trrigleType == "01"){
						// 只处理和当前明细表的控件
						if(arguContext && arguContext.tableId && arguContext.tableId != detailTableId){
							continue;
						}
						// 新增行，范围局限新增的行
						if(arguContext){
							area = arguContext.row;	
						}
					}
				}else{
					arguContext = null;
				}
				var wgts = xUtil.getXformWidgetsBlur(area , destId);
				if(wgts){
					var wgtIndex = null;
					if(arguContext && arguContext.row){
						wgtIndex = arguContext.row.rowIndex-1;
					}
					for(var j = 0;j < wgts.length;j++ ){
						var name = wgts[j].get("name");
						if(name == null || name == '' || (wgtIndex != null && name.indexOf("."+wgtIndex+".") < 0)){
							continue;
						}
						var baseField = $form(name);
						// 如果这些元素为空，再处理下name（可能是查看页面的地址本）
						if(!baseField.target.widget && !baseField.target.root){
							if(name.indexOf('.id') > -1){
								name = name.replace(/\.id/g,'');
								baseField = $form(name);
							}
						}
						assemblyArray.push(baseField);	
					}
				}
			}

			// end
			//遍历条件
			if(this.opsStr && this.opsStr != ''){
				var ops = json.parse(this.opsStr.replace(/quot;/g,"\""));
				for(var j = 0;j < ops.length;j++){
					var op = ops[j];
					var condition = op.cndId;
					//将条件中的参数值替换为实际值
					condition = condition.replace(/\$(\w+)\$/g,function($1,$2){
						//不用考虑明细表的情况，不支持明细表里面的控件作为公式的参数计算
						var val = xUtil.getXformWidgetValues(null,$2,true);
						if(_self.isNumber(val)){
							return  Number(val);
						}
						return "'" + val + "'";
					});
					var isContinue = false;
					//处理明细表值替换
					condition=condition.replace(/\$(\w+.\w+)\$/g,function($1,$2){
						if($2.indexOf(".")>=0 && _arguContext && _arguContext.row){
							var rowIndex = _arguContext.row.rowIndex-1;
							$2 = $2.split(".")[0]+'.'+rowIndex+'.'+$2.split(".")[1];
							var val = xUtil._getXformWidget(null, $2, true, true, true, false);
							if(_self.isNumber(val)){
								return  Number(val);
							}
							return "'"+val+"'";
						}else{
							isContinue = true;
						}
						return $2;
					});
					if(isContinue){
						continue;
					}
					var condition = this.htmlEscape(condition);
					//如果不条件成立，跳过
					try{
						if(!eval(condition)){
							continue;
						}	
					}catch(e){
						// 无法解析，跳过
						console.log("公式条件无法通过：" + condition);
						continue;
					}
					
					//遍历目标组件
					for(var i = 0;i < assemblyArray.length;i++){
						this.__fixMaxLevel(assemblyArray[i]);
						var relationRuleAttrChange = new RelationRuleAttrChange(assemblyArray[i]);
						// 新UI需要先处理必填再处理只读
						relationRuleAttrChange.execRequired(op.required);
						if(relationRuleAttrChange.isContainedByRightControl() == false){
							relationRuleAttrChange.execReadonly(op.readonly);	
						}
						relationRuleAttrChange.execDisplay(op.display);
					}
				}
				topic.publish("/mui/list/resize", this);
				topic.publish("/mui/relationRule/attrChange", this);
			}
			}.bind(this),0);
		},
		//判断是否为number
		isNumber : function (value){
			var number =  Number.isNaN || function(value) {
				return  (typeof value) === 'number' && window. isNaN(value);
			}
			return !number(Number(value));
		},
		__fixMaxLevel: function(baseField) {
			if(baseField && baseField.target){
				if(baseField.target.cache.type == "detailSummary") {
					if(!baseField.target.widget.calcNode){
						baseField.target.widget = baseField.target.widget.getParent();
					}
					if(baseField.target.cache.maxLevel == 0 && baseField.target.widget.edit){
						baseField.target.cache.maxLevel = 2;
					}
					
				}
			}
		},
		
		htmlEscape : function(str){
			if(!str) return str;
			str= str.replace(/&amp;/g, "&");
			str= str.replace(/&#34;/g, "\"");
			str= str.replace(/&#39;/g, "\'");
			return str;
		}
	});
	return claz;
});
