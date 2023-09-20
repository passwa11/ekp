//关联控件
define([ "dojo/_base/declare", "mui/form/_FormBase","mui/util", "dojo/dom-construct", "dojo/_base/json",
         "sys/xform/mobile/controls/relevance/RelevanceDialog", "mui/dialog/Confirm","mui/i18n/i18n!sys-xform-base:mui",
         "sys/xform/mobile/controls/xformUtil","dojo/query","dojo/dom-style","dojo/topic","dojo/dom-class","dojo/request"], 
		function(declare, _FormBase, util, domConstruct, json, RelevanceDialog, Confirm, Msg, xUtil,query,domStyle,topic,domClass,request) {
	var claz = declare("sys.xform.mobile.controls.Relevance", [ _FormBase ], {

		viewUrl:"/sys/xform/controls/relevance.do?method=viewDoc",
		
		name: null,
		
		fdId: null,
		
		fdControlId: null,
		
		fdMainModelName: null,
		
		btnName : Msg["mui.relevance"],
		
		formFilePath : null,
		
		curIds : null,
		
		curSubjects : null,
		
		curFdModelNames : null,

		curIsCreators : null,
		
		isUseNew : "false",
		
		isMul : true,
		
		inputParams : null,

		fdKey : null,

		fdTemplateModelName : null,

		isBase : true,

		isSimpleCategory:false,

	    /* 组件布局朝向：   horizontal:横向, vertical:纵向(默认),  none: 不做布局处理仅显示控件 */
	    orient: 'none',	
		
		buildRendering : function() {
			var _extendFilePath = document.getElementsByName("extendDataFormInfo.extendFilePath")[0];
			if(_extendFilePath){
				this.formFilePath = _extendFilePath.value;
			}
//			this.formFilePath = "km/review/xform/define/170e2146a3ca8deda7fc5574aef9a596/170e21486ffe7e7f388229143018f521_v3_20200319152438";
			this.inherited(arguments);
			this._buildValue();
			//修正引入关联控件
			var curTable = query(this.domNode).closest("table.muiSimple");
			if(curTable.length > 0){
				//这个地方会导致移动端表格第一行为合并行时会出现55开的情况，此改法现在无效了，去掉
				//curTable[0].style['table-layout'] = "fixed";
			}
		},
		
		postCreate : function() {
			this.inherited(arguments);
			this.subscribe("/sys/xform/relevance/setvalue","_setvalue");
		}, 
		
		// 构建值区域
		_buildValue : function() {
			this.inherited(arguments);
			this.hiddeNode = domConstruct.create("input" ,{type:'hidden',name:this.name},this.valueNode);
			this.ListNode = domConstruct.create("div",{className:'muiFormRelevanceListBox'},this.valueNode);
			var setBuildName = 'build'
					+ util.capitalize(this.showStatus);
			this[setBuildName] ? this[setBuildName]() : '';
			var setMethdName = this.showStatus + 'ValueSet';
			this.showStatusSet = this[setMethdName] ? this[setMethdName]
					: new Function();
		},
		
		buildEdit : function(){
		},
		
		buildReadOnly: function() {
		},
		
		buildHidden: function() {
		},
		
		_setvalue : function(srcObj , evt){
			if(evt && this.name==srcObj.key){
				if(this.curIds!=evt.curIds){
					var docIds = evt.curIds.split(";");
					var subjects = evt.subjects.split(";");
					var fdModelNames = evt.fdModelNames.split(";");
					var isCreators = evt.isCreators.split(";");
					var values = [];
					for(var i = 0;i<docIds.length;i++){
						var val = {};
						val.docId = docIds[i];
						val.subject = subjects[i];
						val.fdModelName = fdModelNames[i];
						val.isCreator = isCreators[i];
						val.checked = "true";
						val.openRight = isCreators[i];
						values.push(val);
					}
					this.set("value",JSON.stringify(values));
				}
			}
		},
		
		_setValueAttr : function(value) {
			this.inherited(arguments);
			this.showStatusSet(value);
			this.hiddeNode.value = value.replace(/&quot;/g,"\"");
			topic.publish("/mui/list/resize", this);
		},
		
		viewValueSet : function(value) {
			this.showDoc(value);
		},
		
		// 编辑页面暂时和查看页面保持一致
		editValueSet : function(value) {
			this.showDoc(value);
			this.setOutParams();
		},
		
		showDoc : function(value){
			var self = this;
			//格式化值
			if(this.value && this.value != ""){
				try{
					var val = eval(this.value.replace(/&quot;/g,"\""));
					if(val.length > 0){
						var isHiddenCss = ""
						if(val.length > 5 && this.showStatus!='edit'){
							this.hiddenBoxNode = domConstruct.create("div",{
								className:'muiFormRelevanceHideBox'
							},this.ListNode)
							var hideShowBtn = domConstruct.create("div",{className:'muiFormRelevanceBtnHide fontmuis muis-drop-down',},this.ListNode)
							this.connect(hideShowBtn,"click","_ReleHideShow");
							isHiddenCss = "muiForemRelevanceHidArticle"
						}
						if(!this.valOpt){
							this.valOpt = domConstruct.create("ul",{
								className: isHiddenCss
							});
							domConstruct.place(this.valOpt, this.ListNode, 'first');
						}
						//清空选项
						this.valOpt.innerHTML = '';
						var docIds = [];
						var subjects = [];
						var fdModelNames = [];
						var isCreators = [];
						for(var i = 0;i < val.length;i++){
							var v = val[i];
							docIds.push(v.docId);
							subjects.push(v.subject);
							fdModelNames.push(v.fdModelName);
							isCreators.push(v.isCreator);
							var item = this.getItemDom(v);
							domConstruct.place(item, this.valOpt, 'last');
						}
						this.curIds = docIds.join(";");
						this.curSubjects = subjects.join(";");
						this.curFdModelNames = fdModelNames.join(";");
						this.curIsCreators = isCreators.join(";");
					}else{
						if(this.valOpt){
							this.valOpt.innerHTML = '';
							this.curIds = '';
							this.curSubjects = '';
							this.curFdModelNames = '';
							this.curIsCreators = '';
						}
					}
				}catch(e){
					
				}
			}else{
				if(this.valOpt){
					this.valOpt.innerHTML = '';
					this.curIds = '';
					this.curSubjects = '';
					this.curFdModelNames = '';
					this.curIsCreators = '';
				}
			}
			if(this.showStatus=='edit' && !this.rTitleNode){
				var topOpt = domConstruct.create("div",{className: "muiFormRelevanceBtnPlus"});
				if(this.orient=='vertical'){
					domClass.add(topOpt, "BtnPlusVertical");
				}
				var addOpt = domConstruct.create("span", {
					className : 'muiFormRelevanceBtnIcon fontmuis muis-new'
				}, topOpt);
				if(this.btnName){
					var btnText = domConstruct.create("span", {
						className : 'muiFormRelevanceBtnText',
						innerHTML : this.btnName
					}, topOpt);
				}
				this.connect(topOpt,"click",function(evt){
					//使当前元素失焦（单行等失焦时才会setValue，防止构建传入参数时取不到正确的值）
					var activeElement = document.activeElement;
					if(activeElement && activeElement.blur){
						activeElement.blur();
					}
					var inputParams = null;
					if(self.inputParams){
						var inputParamsMapping = JSON.parse(self.inputParams.replace(/quot;/g,"\""));
						var inputParamsValueMap = {};
						for(var fid in inputParamsMapping){
							var param = inputParamsMapping[fid];
							//当前表单字段
							var id = param.fieldId;
							//入参字段
							var idform = param.fieldIdForm;
							if(!id || !idform){
								continue;
							}
							//根据入参id抓取组件的值构建出入参数
							inputParamsValueMap[fid] = self.getFormFiledValue(id);
						}
						inputParams = JSON.stringify(inputParamsValueMap).replace(/"/g,"quot;")
					}
					if(self.dialog==null){
						self.dialog = new RelevanceDialog();
					}
					//关联文档点击时，请求配置的关联文档，若配置文档包含当前模板所在文档则默认选中，否则选中第一个
					var dataUrl = util.formatUrl('/sys/xform/controls/relevance.do?method=getAllList');
					dataUrl += "&fdControlId="+self.fdControlId+"&extendXmlPath="+self.formFilePath+"&fdMainModelNameMobile="+self.fdMainModelName;
					request.get(dataUrl, {handleAs:'json',sync:true}).then(function(responseText) {
						if(responseText){
							if(responseText[0].mainModelName){
								self.fdMainModelName = responseText[0].mainModelName;
							}else{
								self.fdMainModelName = responseText[0].fdMainModelName;
							}
							self.fdKey = responseText[0].fdKey;
							self.fdId = responseText[0].fdId;
							self.fdTemplateModelName = responseText[0].fdTemplateModelName;
							self.isSimpleCategory =  responseText[0].isSimpleCategory;
							self.isBase = responseText[0].isBase;
						}
					});
					self.dialog.select({
						key:self.name,fdControlId:self.fdControlId,fdKey:self.fdKey,fdId:self.fdId,
						extendXmlPath:self.formFilePath,fdMainModelName:self.fdMainModelName,
						fdTemplateModelName:self.fdTemplateModelName,isBase:self.isBase,
						isSimpleCategory:self.isSimpleCategory,
						curIds:self.curIds,curSubjects:self.curSubjects,isMul:self.isMul,
						curFdModelNames:self.curFdModelNames,curIsCreators:self.curIsCreators,
						isUseNew:self.isUseNew,inputParams:encodeURIComponent(inputParams)});
				});
				domConstruct.place(topOpt, this.valueNode, 'last');
				this.rTitleNode = topOpt;
			}
		},
		
		getFormFiledValue : function(id){
			//地址本的带有.id 或者.name，特殊处理
			var param = "";
			if(/\.(\w+)/g.test(id)){
				param = id.match(/\.(\w+)/g)[0].replace(".","").toLowerCase();
				id = id.match(/(\S+)\./g)[0].replace(".","");
			}
			var valueInfo=[];
			var wgts = xUtil.getXformWidgetsBlur(null, id);
			for(var j=0;j<wgts.length;j++){
				if(wgts[j].declaredClass=="mui.form.Address" || wgts[j].declaredClass=="sys.xform.mobile.controls.NewAddress"){
					if(param == "name"){
						valueInfo.push(wgts[j].curNames);
					}else if(param == "id"){
						valueInfo.push(wgts[j].curIds);
					}
				}else{
					//动态控件可能带有_text，即取显示值
					if(/_text/.test(id)){
						valueInfo.push(wgts[j].text);
					}else{
						valueInfo.push(wgts[j].value);
					}
				}
			}
			if(!valueInfo || valueInfo.length==0){
				valueInfo = "null";
			}
			return valueInfo;
		},
		
		// 构建查看文档的dom
		getItemDom : function(value){
			var self = this;
			var item = domConstruct.create("li",{
				className: "muiFormRelevanceDocLi"
			});
			domConstruct.create("i", {
				className : 'fontmuis muis-associated-document',
				style:{
					'float': 'left',
					'margin-right':'5px'
				}
			}, item);
			var doc = domConstruct.create("a",{
				className: "muiFormRelevanceDocLink"
			},item);
			var myValue = value.subject ? value.subject : "";
			doc.title = myValue;
//			if(myValue.length>12){
//				myValue = myValue.substring(0,12)+'...';
//			}
			doc.innerHTML = myValue;
			if(this.showStatus=='edit'){
				domClass.add(doc, "muiFormRelevanceDocDelIcon");
				var delOpt = domConstruct.create("i", {
					className : 'fontmuis muis-delete-document',
					style:{
						'float': 'right',
						'margin-right':'15px',
						'color':'red'
					}
				}, item);
				delOpt.id = value.docId;
			}
			this.connect(delOpt,"click","_deleDoc");
			
			//监控点击事件
			this.connect(doc,"click",function(evt){
				var fdControlId = self.fdControlId;
				if(/(\w+\.\d+\.\w+)/g.test(self.name)){
					fdControlId = self.name.match(/(\w+\.\d+\.\w+)/g)[0];
				}
				if(this.showStatus=='edit'){
					Confirm(Msg["mui.relevance.delete_msg"],null,function(data){
						if(data){
							var paramMap = {};
							paramMap.fdId = self.fdId;
							paramMap.fdMainModelName = self.fdMainModelName;
							paramMap.fdControlId = fdControlId;
							paramMap.fdDocId = value.docId;
							paramMap.fdModelName = value.fdModelName;
							var url = util.setUrlParameterMap(self.viewUrl, paramMap);
							url = util.formatUrl(url);
							window.open(url,'_self');
						}
					},false);
				}else{
					var paramMap = {};
					paramMap.fdId = self.fdId;
					paramMap.fdMainModelName = self.fdMainModelName;
					paramMap.fdControlId = fdControlId;
					paramMap.fdDocId = value.docId;
					paramMap.fdModelName = value.fdModelName;
					var url = util.setUrlParameterMap(self.viewUrl, paramMap);
					url = util.formatUrl(url);
					window.open(url,'_self');
				}
			});
			return item;
		},
		
		_deleDoc : function(evt){
			if(evt){
				var id = evt.target.id;
				var value = this.get("value");
				if(value){
					var val = eval(this.value.replace(/&quot;/g,"\""));
					if(val.length > 0){
						var bool = false;
						for(var i=0;i<val.length;i++){
							if(id==val[i].docId){
								val.splice(i,1);
								bool = true;
								break;
							}
						}
						if(bool){
							this.set("value",val.length > 0?JSON.stringify(val):"");
						}
					}
				}
			}
		},
		
		/**单行编辑和明细表桌面端自适应**/
		buildAutoAdaptionView : function() {
			//控件非隐藏状态
			if (this.showStatus != "hidden") {
				this.inherited(arguments);
				if (this.autoAdaptionViewNode) {
					this.autoAdaptionViewNode.innerText = "";
					this._buildItem();
				}
			}
		},
		
		buildSingleRowView : function() {
			if (this.showStatus != "hidden") {
				this.inherited(arguments);
				if (this.autoAdaptionViewNode) {
					this.autoAdaptionViewNode.innerText = "";
					this._buildItem();
				}
			}
		},
		
		_buildItem : function(){
			var val = eval(this.value.replace(/&quot;/g,"\""));
			if (val && val.length > 0) {
				for (var i = 0;i < val.length;i++) {
					var v = val[i];
					var item = this.getItemDom(v);
					domConstruct.place(item, this.autoAdaptionViewNode, 'last');
					var cancelNode = query(".mui-cancel", item)[0];
					if (cancelNode) {
						domStyle.set(cancelNode,"display","none");
					}
				}
			}
		},
		/**单行编辑和明细表桌面端自适应end**/

		/****************文档数过多时显示隐藏按钮方法****************/
		_ReleHideShow : function(evt){
			if(evt){
				var el = evt.target;
				if(this.hasClass(el,"muis-drop-down")){//显示
					this.removeClass(el,"muis-drop-down")
					this.addClass(el,"muis-drop-up")
					domStyle.set(this.hiddenBoxNode,"display","none");
				}else {//隐藏
					this.removeClass(el,"muis-drop-up")
					this.addClass(el,"muis-drop-down")
					domStyle.set(this.hiddenBoxNode,"display","block");
				}
				domClass.toggle(this.valOpt, "muiForemRelevanceHidArticle");
			}
		},
		hasClass : function (elements, cName) { //是否存在class
		    return !!elements.className.match(new RegExp("(\\s|^)" + cName + "(\\s|$)"));
		},
		addClass : function (elements, cName) { //添加class
		    if (!this.hasClass(elements, cName)) {
		        elements.className += " " + cName;
		    };
		},
		removeClass : function (elements, cName) { //删除class
		    if (this.hasClass(elements, cName)) {
		        elements.className = elements.className.replace(new RegExp("(\\s|^)" + cName + "(\\s|$)"), " ");
		    };
		},
		setOutParams : function(){
			var self = this;
			if(self.outputParams){
				var fdModelIds = [];
				var fdModelNames = [];
				var val = "";
				if(self.value){
					val = eval(this.value.replace(/&quot;/g,"\""));
					for(var i = 0; i < val.length;i++){
						var item = val[i];
						fdModelIds.push(item.docId);
						fdModelNames.push(item.fdModelName);
					}
				}
				var outputParamsMapping = JSON.parse(self.outputParams.replace(/quot;/g,"\""));
				for(var fid in outputParamsMapping){
					var param = outputParamsMapping[fid];
					//要传到的表单字段
					var fieldIdForm = param.fieldIdForm;
					//传出的模板字段
					var fieldId = param.fieldId;
					if(!fieldIdForm || !fieldId){
						continue;
					}
					var fieldDictType = param.fieldDictType;
					var fieldDataTypeForm = param.fieldDataTypeForm;
					var msg = '';
					if(self.value){
						var url = util.formatUrl('/sys/xform/controls/relevance.do?method=getOutInfo');
						url += "&fieldId="+fieldId+"&fieldDictType="+fieldDictType+"&fieldDataTypeForm="+fieldDataTypeForm+"&fdModelIds="+fdModelIds.join(";")+"&fdModelNames="+fdModelNames.join(";");
						request.get(url, {handleAs:'json',sync:true}).then(function(responseText) {
							if(responseText){
								for(var i=0;i<responseText.length-1;i++){
									msg+=responseText[i].outValue+';';
								}
								msg += responseText[responseText.length-1].outValue;
							}
						});
					}
					if(fieldIdForm.indexOf(".")>-1){
						var fields = fieldIdForm.split(".");
						var wgts = xUtil.getXformWidgetsBlur(null, fields[0]);
						if(wgts && wgts.length>0){
							if(fields[1]=="id"){
								wgts[0].set("curIds", msg);
							}else if(fields[1]=="name"){
								wgts[0].set("curNames", msg);
							}
						}
					}else{
						var wgts = xUtil.getXformWidgetsBlur(null, fieldIdForm);
				        for (var i = 0; i < wgts.length; i++) {
				        	self._filedSetValue(wgts[i], msg, fieldIdForm);
				        }
					}
				}
			}
		},
		_filedSetValue: function(wgt, val, prop) {
	      if (wgt) {
	        if (
	          wgt.declaredClass == "mui.form.Address" ||
	          wgt.declaredClass == "sys.xform.mobile.controls.NewAddress"
	        ) {
	          if (val instanceof Array) {
	            wgt.set("curIds", val[0]);
	            wgt.set("curNames", val[1] != null ? val[1] : val[0]);
	          } else {
	            wgt.set("curIds", val);
	            wgt.set("curNames", val);
	          }
	        } else if (wgt.declaredClass == "mui.form.RadioGroup") {
	          // 单选
	          var childrenArray = wgt.getChildren();
	          for (var i = 0; i < childrenArray.length; i++) {
	            if (childrenArray[i].value == val && childrenArray[i]._onClick) {
	              childrenArray[i]._onClick();
	            }
	          }
	        } else if (wgt.declaredClass == "mui.form.CheckBoxGroup") {
	          var childrenArray = wgt.getChildren();
	          // 多选
	          var valArray = val.split(";");
	          // 遍历子组件
	          for (var i = 0; i < childrenArray.length; i++) {
	            if (childrenArray[i]._onClick) {
	              if (
	                childrenArray[i].value &&
	                valArray.indexOf(childrenArray[i].value) > -1
	              ) {
	                // 此处设置相反，在_onClick的方法里面会取反
	                childrenArray[i].checked = false;
	              } else {
	                childrenArray[i].checked = true;
	              }
	              childrenArray[i]._onClick();
	            }
	          }
	        } else {
	          wgt.set("value", val);
	        }
	      } else {
	        //如果找不到控件，有可能是文本值，即ID_text
	        if (
	          this.textName &&
	          prop.indexOf("_text") > -1 &&
	          this.textName.indexOf(prop) > -1
	        ) {
	          this.set("text", val);
	        }
	      }
	    }
	});
	return claz;
});