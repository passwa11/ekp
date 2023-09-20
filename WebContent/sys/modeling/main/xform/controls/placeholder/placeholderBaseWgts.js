/**
 * 
 */
define(function(require, exports, module) {

	require("sys/modeling/main/xform/controls/placeholder/css/placeholder.css");
	require("resource/js/treeview.js");
	var $ = require("lui/jquery");
	var base = require("lui/base");
	var dialog = require('lui/dialog');
	var source = require("lui/data/source");
	
	var PlaceholderBaseWgt = base.Container.extend({
		
		CONST : {"RECORDID" : "fdId"},
		
		fetchUrl : "/sys/modeling/main/modelingAppXFormMain.do?method=executeQuery",
		
		startup : function($super,cfg) {
			$super(cfg);
			this.doRender();
		},
		
		getCfgInfo : function(){
			return this.config.envInfo;
		},

		doRender : function(){

		},
		
		updateTextView : function(){
			
		},
		
		// 根据控件ID返回控件值
		findFormValueByCfg : function(cfg){
			var inputCfg = cfg.inputs;
			var formDatas = {};
			if(inputCfg.fields.length > 0){
				for(var i = 0;i < inputCfg.fields.length;i++){
					var field = inputCfg.fields[i];
					formDatas[field] = this.getFormValueById(field);
				}
			}
			return formDatas;
		},
		
		getFormValueById : function(controlId){
			var vals = GetXFormFieldValueById_ext(controlId,true);
			return vals.join(";");
		},
		
		transValByExp : function(info, expression){
			var rs = "";
			// 把两个$里面括起来的内容替换为对应的值
			var controls = expression.match(/\$[^\$][\w.]*\$/g);
			if(controls) {
				for (var i = 0; i < controls.length; i++) {
					var control = controls[i].replace(/\$/g, '');
					if (info.hasOwnProperty(control)) {
						var val = info[control]["value"];
						// 枚举值，默认取显示值
						if (typeof val === "object" && val["text"]) {
							val = val["text"];
						}
						// 地址本，默认取名字
						else if (typeof val === "object" && val["name"]) {
							val = val["name"];
						}
						// RegExp 里面的表达式需要两个反斜杠
						expression = expression.replace(new RegExp("\\$" + control + "\\$", "g"), val);
					}
					//#127238 明细表中数据因为有.导致映射不上数据
					if(control.indexOf(".")!=-1){
						var arrays = control.split(".");
						var tolval = info[arrays[0]]["value"];
						var val = tolval[0][arrays[1]];
						// 枚举值，默认取显示值
						if (typeof val === "object" && val["text"]) {
							val = val["text"];
						}
						// 地址本，默认取名字
						else if (typeof val === "object" && val["name"]) {
							val = val["name"];
						}
						expression = expression.replace(new RegExp("\\$" + control + "\\$", "g"), val);
					}
				}
				rs = expression;
			}else if(expression){
				//#143031  纯文本的情况
				rs = expression;
			}
			return rs;
		},
	});
	
	// 表单元素渲染组件
	var FormPlaceholder = PlaceholderBaseWgt.extend({
		
		recordDatas : {"showInfo":{},"valueInfo":{}},
		
		initialize : function($super,cfg) {
			$super(cfg);
			this.randomId = parseInt(((new Date().getTime()+Math.random())*10000)).toString(16);
		},
		
		doRender : function(){
			this.fetchSourceData();
		},
		
		fetchSourceData : function(){
			// 根据配置信息获取表单数据
			var formDatas = this.findFormValueByCfg(this.getCfgInfo());
			var url = this.fetchUrl + "&widgetId=" + this.parent.config.controlId + "&fdAppModelId=" + this.parent.config.fdModelId;
			this.source = new source.UrlSource({
				url : url,
				params : {ins:JSON.stringify(this.formData)},
				parent : this
			});
			this.source.on("data",this.onDataLoad,this);
			this.addChild(this.source);
			this.source.startup();
			this.source.get();
		},
		
		onDataLoad : function(initData){
			var data = this.formatData(initData);
			var html = this.html(data);
			if(this.required === "true"){
				html.append("<span class=txtstrong>*</span>");
			}
			this.emit("html" , html);
		},
		
		html : function(){
			
		},
		
		// 格式化数据，补全recordDatas信息
		formatData : function(sourceData){
			var columns = sourceData.columns;
			var valueIndex = 0;
			// 设置实际列
			for(var i = 0;i < columns.length;i++){
				var column = columns[i];
				// 约定name为fdId的为实际值列，而且 必须存在
				if(column.name === this.CONST.RECORDID){
					valueIndex = i;
					this.recordDatas.valueInfo = column;
					break;
				}
			}
			// 设置显示列
			this.setShowInfo(columns);
			return this.recordDatas;
		},
		
		// 设置显示值
		setShowInfo : function(columns){
			var cfg = this.getCfgInfo();
			var transedDatas = this.transSourceData(columns);
			var expression = cfg["showTxt"]["expression"];
			var values = [];
			if(transedDatas && transedDatas.length){
				for(var i = 0;i < transedDatas.length;i++){
					var info = transedDatas[i];
					values.push(this.transValByExp(info, expression));
				}
			}
			this.recordDatas.showInfo = {data : values};
		},
		
		// 把列呈现的数据，转换成横向的数组
		transSourceData : function(columns){
			var rs = [];
			if(columns.length > 0){
				var len = columns[0]["data"].length;
				for(var i = 0;i < len;i++){
					var ele = {};
					for(var j = 0;j < columns.length;j++){
						ele[columns[j].name] = {};
						ele[columns[j].name]["value"] = columns[j]["data"][i];
					}
					rs.push(ele);
				}
			}
			return rs;
		},

        setValueAndText: function(value, text, isTrigger) {
            this.inputNode.val(value);
            if (isTrigger) {
                this.inputNode.trigger("change"); // fire the change event
            }
            this.textNode.val(text);
            if (isTrigger) {
                this.textNode.trigger("change"); // fire the change event
            }
        }
	});
	
	// 自定义渲染组件
	var CustomPlaceholder = PlaceholderBaseWgt.extend({
		
	});
	
	var RadioPlaceholder = FormPlaceholder.extend({
		
		// data = recordDatas : {"showInfo":{},"valueInfo":{}},
		html : function(data){
			var showInfoDatas = data.showInfo["data"];
			var html = $("<div class=\"placeholderWrap placeholderRadio\"  />");
			for(var i = 0;i < showInfoDatas.length;i++){
				var d = showInfoDatas[i];
				html.append(this.buildRadio(d,data.valueInfo["data"][i]));
			}
			return html;
		},
		
		buildRadio : function(text,value){
			var label = this.parent.config.label;
			var html = "<label><input type='radio' name='__"+ this.randomId +"' value='"+ value +"' ";
			if(value === this.value){
				html += " checked ";
			}
			html += "/>"+ text +"</label>";
			var node = $(html);
			var self = this;
			node.on("click" , function(event){
				self.setValueAndText(value, text, true);
			});
			return node;
		}
	});
	
	var CheckboxPlaceholder = FormPlaceholder.extend({
		html : function(data){
			var showInfoDatas = data.showInfo["data"];
			var html = $("<div class=\"placeholderWrap placeholderCheckbox\" />");
			var values = this.value.split(";");
			for(var i = 0;i < showInfoDatas.length;i++){
				var d = showInfoDatas[i];
				html.append(this.buildCheckbox(d,data.valueInfo["data"][i], values));
			}
			return html;
		},
		
		buildCheckbox : function(text, value, values){
			// 为避免需要再找文本，这里直接把文本放置在元素上面
			var label = this.parent.config.label;
			var html = "<label><input type='checkbox' name='__"+ this.randomId +"' value='"+ value +"' data-text='"+ text +"' ";
			if($.inArray(value,values) > -1){
				html += " checked ";
			}
			html += "/>"+ text +"</label>";
			var node = $(html);
			var self = this;
			var $element = self.parent.element;
			node.on("click" , function(event){
				var checkboxVal = '';
				var checkboxTxt = '';
				$element.find("input[name='__"+ self.randomId +"']").each(function(index,dom){
					if(dom.checked){
						//#150643 兼容明细表内外的关联类型为多选按钮，选定数据之后，提交只保存最后一项
						if(checkboxVal.length>0){
							checkboxVal = checkboxVal + ';';
						}
						checkboxVal = checkboxVal + dom.value;
						if(checkboxTxt.length>0){
							checkboxTxt = checkboxTxt + ',';
						}
						checkboxTxt = checkboxTxt + $(dom).attr("data-text");
					}
				});
                self.setValueAndText(checkboxVal, checkboxTxt, true);
			});
			return node;
		}
	});
	
	var SelectPlaceholder = FormPlaceholder.extend({
		html : function(data){
			var label = this.parent.config.label;
			var valueInfoDatas = data.valueInfo["data"];
			var html = $("<div class=\"placeholderWrap placeholderSelect\"/>");
			var $select = $("<select name='__"+ this.randomId +"' style=\"width:95%\" />").appendTo(html);
			$select.append("<option value=''>==请选择==</option>");
			for(var i = 0;i < valueInfoDatas.length;i++){
				var value = valueInfoDatas[i];
				var optionHtml = "<option value='"+ value +"' ";
				if(value === this.value){
					optionHtml += " selected ";
				}
				optionHtml += ">" + data.showInfo["data"][i] +"</option>";
				$select.append(optionHtml);
			}
			var self = this;
			$select.on("change",function(event, params){
				var txt = "";
				// 实际值为空，则显示值也为空
				if(this.value){
					txt = $(this).find("option:selected").text();
				}
				var isTrigger = true;
				if (params && params.isInit) {
                    isTrigger = false;
                }
                self.setValueAndText(this.value, txt, isTrigger);
			});
			// 首次选中第一项，需手动同步
			$select.trigger($.Event("change"),{isInit: true});
			return html;
		}
	});
	
	var TreePlaceholder = FormPlaceholder.extend({

		html: function(data) {
			//return;
			var html = $("<div class=\"placeholderWrap placeholderTree\"/>");
			//树域
			var $tree = $('<div class="tree-wrap"></div>').appendTo(html);
            $tree.append('<div class="tree-label-wrap"><div class="tree-label">==请选择==</div><span class="tree-arrow"></span></div>');
            $tree.append('<div class="tree-dropdown hidden"><div class="tree-options"></div></div>');
            this.$tree = $tree;
            this.reload();
            this.onEvent();

            return html;
        },

        onEvent : function() {
        	var self = this;
        	this.on("render_finish",function(){
        		self.initSelectNode();
        	});
	    	//控制显示
        	$(document).on('click',function(e){
        		var $el = $(e.target);
		        var $wrap = $el.closest('.tree-wrap');

		        if ($wrap.length == 0) {
		        	 $('.tree-dropdown').addClass('hidden');
		        }
        	})
	    	$(self.$tree).find('.tree-label-wrap').on('click', function(e) {
		        var $el = $(e.target);
		        var $wrap = $el.closest('.tree-wrap');

		        if ($wrap.length > 0) {
		            if ($el.hasClass('tree-label') || $el.hasClass('tree-arrow')) {
		                var is_hidden = $wrap.find('.tree-dropdown').hasClass('hidden');
		                //发布校验
		                //$wrap.trigger("doValidate");
		                //$('.tree-dropdown').addClass('hidden');

		                if (is_hidden) {
		                	var _readonly = $wrap.attr("_readonly");
		                	if (typeof _readonly === "undefined"){
		                		$wrap.find('.tree-dropdown').removeClass('hidden');
		                	}
		                }
		                else {
		                    $wrap.find('.tree-dropdown').addClass('hidden');
		                }
		            }
		        }else {
		        	//发布必填校验
		        	$el.trigger("doValidate");
		            $('.tree-dropdown').addClass('hidden');
		        }
		        return false;
		    });
	    	//搜索
	    	$(self.$tree).on('keyup', '.tree-search input', function(e) {
		        if (40 == e.which) {
		            $(this).blur();
		            return;
		        }
		        if(13 != e.which){
		        	return;//回车的时候才进行搜索
		        }

		        var $wrap = $(this).closest('.tree-wrap');
		        var keyword = $(this).val();

		        var $iconDel =  $wrap.find(".tree-search-icon-del");
		        if ('' != keyword) {
		        	$iconDel.show();
		        	//刷新树图
		        	self.generateTree(null,keyword,true);
		        } else {
		        	$iconDel.hide();
		        	//刷新树图
		        	self.generateTree(null,keyword,false);
		        	//self.initSelectNode();
		        }
		    });
	    	$(self.$tree).on('click', 'i.tree-search-icon-del',function(){
	    		var $searchInput = $(this).prev('input');
	    		var keyword = $searchInput.val();
	    		if(keyword){
	    			$searchInput.val("");
	    			//刷新树图
		        	self.generateTree(null,keyword,false);
		        	//self.initSelectNode();
		        	$(this).hide();
	    		}
	    	});
        },

        reload: function() {
            var search = '<div class="tree-search"><input type="text" autocomplete="off" placeholder="搜索" /><i class="tree-search-icon-del" style="display:none"></i></div>';
            this.$tree.find('.tree-dropdown').prepend(search);
            this.buildTreeView();
        },

        destroy: function() {
            this.$wrap.find('.fs-label-wrap').remove();
            this.$wrap.find('.fs-dropdown').remove();
            this.$select.unwrap().removeClass('hidden');
        },

        buildTreeView: function() {
            var treeViewDom = $('<div class="treediv"></div>');
			var controlId = this.parent.config.controlId;
			if(controlId.indexOf(".") > -1){
				controlId = controlId.replace(".","_");
			}
            $(treeViewDom).attr("id","treeDiv_"+controlId);
            this.$tree.find('.tree-options').append(treeViewDom);
            this.generateTree(treeViewDom);
        },

        generateTree: function(treeViewDom,keyword,isSearch){
        	var self = this;
        	if(isSearch){
        		this.isSearch = true;
        	}else{
        		this.isSearch = false;
        	}
        	//清空树
        	this.treeView ? this.treeView.treeRoot = null : this.treeView = null;
        	this.treeView = null;
        	var controlId = this.parent.config.controlId;
        	if(controlId.indexOf(".") > -1){
				controlId = controlId.replace(".","_");
			}
        	window["LKSTree_"+controlId] = null;
        	treeViewDom = treeViewDom || $("#treeDiv_"+controlId);
        	treeViewDom.empty();
        	//重新构建树
        	this.treeView = new TreeView("LKSTree_"+controlId, "请选择");
        	window["LKSTree_"+controlId] = this.treeView;
        	this.treeView.DOMElement = treeViewDom[0];
        	this.treeView.isShowCheckBox = true;
        	var treeRoot = this.treeView.treeRoot;
        	treeRoot.value = '';
        	this.treeView.OnNodePostClick = function(node){
        	}
        	if(this.config.isMultiTree){
        		this.treeView.isMultSel = true;
        		treeRoot.isShowCheckBox = false;
        		this.treeView.OnNodeCheckedPostChange = function(node){
        			if(self.isSearch){
        				return;
        			}
            		var value = node.value;
            		var text = value ? node.text : "";
            		self.valJsonArr = self.valJsonArr || [];//多选
    				var fdHierarchyId = self.getFdHierarchyId(node);
            		if(node.isChecked){//选中
            			if(self.currentVal){
                			self.currentVal += value ? ';'+value : null;
                    		self.currentText += text ? ','+text : null;
                		}else{
                			self.currentVal = value ? value : null;
                    		self.currentText = text ? text : null;
                		}
    					//构造json
        				var valJson = {};
        				valJson[fdHierarchyId] = value;
        				self.valJsonArr.push(valJson);
            		}else{//取消选中
    					//构造json
            			var index;
        				for(var i=0; i<self.valJsonArr.length; i++){
        					var valJson = self.valJsonArr[i];
        					if(valJson[fdHierarchyId]){
        						self.valJsonArr.splice(i,1);
        						index = i;
        						break;
        					}
        				}
        				var vals = self.currentVal.split(';');
        				var texts = self.currentText.split(',');
        				if(vals.length == 1){
        					//剩下一个，直接删除
        					self.currentVal = "";
        					self.currentText = "";
        				}else{
        					vals.splice(index,1);
        					texts.splice(index,1);
        					self.currentVal = vals.join(';');
            				self.currentText = texts.join(',');
        				}
            		}
            		//设置到dom
            		if(self.currentVal){
                		self.$tree.find('.tree-label').text(self.currentText);
            		}else{
                		self.$tree.find('.tree-label').text('==请选择==');
            		}
                    self.setValueAndText(self.currentVal, self.currentText, true);
            	}
        	}else{
        		this.treeView.isMultSel = false;
        		treeRoot.isShowCheckBox = true;
        		this.treeView.OnNodeCheckedPostChange = function(node){
        			if(self.isSearch){
        				return;
        			}
            		var value = node.value;
            		var text = value ? node.text : "";
            		self.currentVal = value ? value : null;
            		self.currentText = text ? text : null;
            		//设置到dom
            		if(value){
                		self.$tree.find('.tree-label').text(text);
            		}else{
                		self.$tree.find('.tree-label').text('==请选择==');
            		}
                    self.setValueAndText(value, text, true);
    				if(value){
    					//构造json
        				self.valJsonArr = [];//单选
        				var fdHierarchyId = self.getFdHierarchyId(node);
        				var valJson = {};
        				valJson[fdHierarchyId] = value;
        				self.valJsonArr.push(valJson);
    				}else{
    					self.valJsonArr = [];
    				}
            	}
        	}
        	this.treeView.OnNodeQueryExpand = function(node){//节点展开前
        		//构建层级
        		var fdHierarchyId = self.getFdHierarchyId(node);
        		//修改树的url
        		var fdId = $("input[name='fdId']").val();
        		var url = "modelingAppXFormMainService&fdId="+fdId+"&parentId=!{value}&fdHierarchyId="+fdHierarchyId+"&widgetId=" + self.parent.config.controlId + "&fdAppModelId=" + self.parent.config.fdModelId;
        		//self.treeView.treeRoot.AppendBeanData(url, null, null, null, null);
        		node.XMLDataInfo.beanURL = TREENXMLBEANURL + url;
        	}
        	this.treeView.OnNodePostExpand = function(node){//节点展开后
        		//恢复url
        		var fdId = $("input[name='fdId']").val();
        		var url = "modelingAppXFormMainService&fdId="+fdId+"&parentId=!{value}&widgetId=" + self.parent.config.controlId + "&fdAppModelId=" + self.parent.config.fdModelId;
        		//self.treeView.treeRoot.AppendBeanData(url, null, null, null, null);
        		node.XMLDataInfo.beanURL = TREENXMLBEANURL + url;
        	}
        	if(isSearch){
        		//从后台请求所有匹配的节点
        		var fdId = $("input[name='fdId']").val();
        		var url = "/sys/modeling/main/modelingAppXFormMain.do?method=searchNodes&fdId="+fdId+"&keyword="+keyword+"&widgetId=" + self.parent.config.controlId + "&fdAppModelId=" + self.parent.config.fdModelId;
        		$.ajax({
                	url: this.source.getEnv().fn.formatUrl(url),
                	dataType: "json",
                	type:"GET",
                	success: function(data) {
                		//循环添加所有匹配的节点
                		self.needClickNodes = [];
                		var nodeDatas = data.nodes;
                		if(nodeDatas && nodeDatas.length > 0){
                			for(var i=0; i<nodeDatas.length; i++){
                    			self.addTreeChildNode(nodeDatas[i],treeRoot);
                    		}
                		}
                		self.treeView.Show();
            			self.clickNodes();
                		self.isSearch = false;
                    }
                });
        	}else{
        		var fdId = $("input[name='fdId']").val();
        		var url = "modelingAppXFormMainService&fdId="+fdId+"&parentId=!{value}&widgetId=" + this.parent.config.controlId + "&fdAppModelId=" + this.parent.config.fdModelId;
            	treeRoot.AppendBeanData(url, null, null, null, null);
            	this.treeView.Show();
        	}

        	Com_Parameter.event["submit"].push(function(){
        		var controlId = self.parent.config.controlId;
        		var dataJsonArr = self.generateDataJsonArr() || [];
        		//获取内容
        		var fdTreeNodeData = $("input[name='fdTreeNodeData']").val();
        		var fdTreeNodeDataJson = {};
        		if(fdTreeNodeData){
        			try{
        				fdTreeNodeDataJson = eval('('+fdTreeNodeData+')');
        			}catch(e){
        			}
        		}
        		fdTreeNodeDataJson[controlId] = dataJsonArr;
        		$("input[name='fdTreeNodeData']").val(JSON.stringify(fdTreeNodeDataJson));
    			return true;
    		});
        },

        initSelectNode : function(){
        	var self = this;
        	//将树的name改变，避免一样
        	$(this.treeView.DOMElement).find("input[name='List_Selected']").attr("name","List_Selected_"+this.parent.config.controlId);
        	if(!self.isSearch){//初始化
        		var currentVal = this.currentVal;
        		var currentText = this.currentText;
        		this.currentVal = this.currentText = "";
        		if(!currentVal){
        			currentVal = this.inputNode.val();
        			currentText = this.textNode.val();
            	}
        		if(!currentVal){
        			return;//空值就不需要进行初始化了
        		}
        		var fdId = $("input[name='fdId']").val();
        		var url = "/sys/modeling/main/modelingAppXFormMain.do?method=getModelParentIds&modelId="+fdId+"&widgetId=" + this.parent.config.controlId + "&fdAppModelId=" + this.parent.config.fdModelId;
        		var ifGet = false;
        		if(this.config.isMultiTree){
        			var vals = currentVal.split(';');
        			var reqVals = '';
        			for(var i=0; i<vals.length; i++){
        				var selectNode = Tree_GetNodeByValue(this.treeView.treeRoot,vals[i]);
        				if(selectNode && !selectNode.isChecked){
        					self.treeView.ClickNode(selectNode);
        				}else{
        					reqVals += vals[i] + ';';
        				}
        			}
            		if(reqVals){
            			ifGet = true;
            		}
        		}else{
        			var selectNode = Tree_GetNodeByValue(this.treeView.treeRoot,currentVal);
        			if(selectNode && !selectNode.isChecked){
        				self.treeView.ClickNode(selectNode);
        			}else{
        				ifGet = true;
        			}
        		}
        		//不是父节点，要从后台请求得到对应的父节点id
        		if(ifGet){
        			$.ajax({
                    	url: this.source.getEnv().fn.formatUrl(url),
                    	dataType: "json",
                    	type:"GET",
                    	success: function(data) {
                    		if(data){
                    			for(var key in data){
                    				var parentIdsArr = data[key];
                    				//循环打开节点
                    				for(var j=0; j<parentIdsArr.length; j++){//多选
                    					var parentIds = parentIdsArr[j];
                    					var root = self.treeView.treeRoot;
                                		for(var i=parentIds.length - 1; i>=1; i--){
                                			var parentId = parentIds[i];
                                			var parentNode = Tree_GetNodeByValue(root,parentId);
                                			if(!parentNode.isExpanded){
                                				self.treeView.ExpandNode(parentNode);
                                			}
                                			root = parentNode;
                                		}
                                		var selectNode = Tree_GetNodeByValue(root,parentIds[0]);
                                		if(selectNode && !selectNode.isChecked){
                                			self.treeView.ClickNode(selectNode);
                                		}
                    				}
                    			}
                    		}
                        }
                    });
        		}
        	}
        },

        generateDataJsonArr : function(){
        	var dataJsonArr = [];
        	var valStr = this.inputNode.val();
        	var valJsonArr = this.valJsonArr;
        	if(valStr){
        		var vals = valStr.split(';');
        		for(var i=0; i<valJsonArr.length; i++){
        			var valJson = valJsonArr[i];
        			for(var key in valJson){
            			if(vals.indexOf(valJson[key]) > -1){
            				var dataJson = {};
            				dataJson['hierarchyId'] = key;
            				dataJson['value'] = valJson[key];
            				dataJsonArr.push(dataJson);
            			}
            		}
        		}
        	}
        	return dataJsonArr;
        },

        addTreeChildNode : function(nodeData,parentNode){
        	var node = parentNode.AppendChild(nodeData.text, null, null, nodeData.value, nodeData.text, null);

        	if(this.currentVal && this.isToClickNode(node)){
        		//this.treeView.ClickNode(node);
        		//记录查询时需要点击的节点
        		this.needClickNodes.push(node);
        	}
        	var childDatas = nodeData.childs;
        	if(childDatas && childDatas.length > 0){
        		node.isExpanded = true;
        		for(var i=0; i<childDatas.length; i++){
            		this.addTreeChildNode(childDatas[i],node);
            	}
        	}
        },

        isToClickNode : function(node){
        	var click = false;
        	var fdHierarchyId = this.getFdHierarchyId(node);
        	var valJsonArr = this.valJsonArr;
        	for(var i=0; i<valJsonArr.length; i++){
        		var dataJson = valJsonArr[i];
        		if(dataJson[fdHierarchyId]){
        			click = true;
        			break;
        		}
        	}
        	return click;
        },

        clickNodes : function(){
        	for(var i=0; i<this.needClickNodes.length; i++){
        		this.treeView.ClickNode(this.needClickNodes[i]);
        	}
        },

        getFdHierarchyId : function(node){
        	var fdHierarchyId = node.value;
        	var pNode = node.parent;
        	while(pNode){
        		if(pNode.value){
        			fdHierarchyId = pNode.value+';'+fdHierarchyId;
        		}
        		pNode = pNode.parent;
        	}
        	return fdHierarchyId;
        }
	});
	
	exports.PlaceholderBaseWgt = PlaceholderBaseWgt;
	exports.FormPlaceholder = FormPlaceholder;
	exports.CustomPlaceholder = CustomPlaceholder;
	
	exports.RadioPlaceholder = RadioPlaceholder;
	exports.CheckboxPlaceholder = CheckboxPlaceholder;
	exports.SelectPlaceholder = SelectPlaceholder;
	exports.TreePlaceholder = TreePlaceholder;
})