define([ "dojo/_base/declare", "mui/form/Select", "dojo/topic","sys/xform/mobile/controls/RelationCommonBase","dojo/dom-construct","sys/xform/mobile/controls/xformUtil","dojo/_base/lang",
         "dojo/dom-style", "dojo/parser", "dojo/dom-class","mui/dialog/Dialog","mui/i18n/i18n!sys-mobile","mui/i18n/i18n!sys-xform-base:mui", "mui/form/_CompositeContainedMixin","mui/util"], 
         function(declare, Select, topic, relationCommonBase, domConstruct, xUtil, lang, domStyle, parser, domClass, Dialog, msg, Msg, _CompositeContainedMixin, util) {

	var claz = declare("sys.xform.mobile.controls.RelationSelect", [Select,relationCommonBase,_CompositeContainedMixin], {
		
		bindDom : null,
		
		isSearch : true,
		
		searchOpt : null,
		
		dialogContentClass : 'muiDialogRelationSelectContent',
		
		showPleaseSelect : true,
		
		needToUpdateAttInDetail : ['name','textName','valueField'],
		
		postCreate: function() {
			this.inherited(arguments);
			if(this.edit){
				this.searchOpt = "jumpToSearchUrl:false,needPrompt:false,searchUrl:'',key:'" + xUtil.parseXformName(this) + "',emptySearch:true";
				this.subscribe(this.SELECT_CALLBACK, "_changeStatus");
				// 当bindDom不为空的时候，即表明该控件需要监控输入控件，当输入控件值改变的时候，当前控件值清空
				if(this.bindDom && this.bindDom != ''){
					this.subscribe("/mui/form/valueChanged", "resetValue");
				}
			}
		},
		
		startup: function(){
			this.inherited(arguments);
			if(this.edit){
				//启动需要先查询数据，否则数据填充时取不到对应的文本
				//加上第二个参数canReload，主要是避免部分有入参的情况，初始化是没有值的，只有入参了才可以加载
				this.queryData(true,true);
				// 设置默认值
				if(this.value && this.value != ''){
					topic.publish(this.SELECT_CALLBACK, this);
				}
				// 搜索触发
				this.subscribe("/mui/search/submit",lang.hitch(this,'searchItem'));
				// 取消搜索触发
				this.subscribe("/mui/search/cancel",lang.hitch(this,'cancelSearchItem'));
			}else if(this.showStatus == "view"){
				// 设置默认值
				if(this.value && this.value != ''){
					if(!(this.text && this.text != '')){
						// 下拉框只读模式，有入参时也初始化
						this.queryData(true,true);
						topic.publish(this.SELECT_CALLBACK, this);
					}
				}
			}
		},
		
		searchItem : function(srcObj , ctx){
			
			if(srcObj && srcObj.containerNode && srcObj.containerNode == this.searchNode){
				var searchVal = ctx.keyword;
				// this.contentNode 为所有选项集合
				if(searchVal != ''){
					var vals = [];
					// 查找符合查询条件的选项
					for(var i = 0;i < this.values.length;i++){
						var value = this.values[i];
						if(value.text.toUpperCase().indexOf(searchVal.toUpperCase()) > -1){
							vals.push(value);
						}
					}
					this.set("values",vals);
					// 重新构建选项
					if(this.dialog.searchItemContent){
						// 清空原本的搜索选项
						domConstruct.empty(this.dialog.searchItemContent);
					}else{
						this.dialog.searchItemContent = domConstruct.create('ul', {
							className : 'muiRadioGroupPopList muiFormSelectSearchElement'
						},this.contentNodeWrap);	
					}
					
					this.renderListItem(this.dialog.searchItemContent);
					parser.parse(this.dialog.searchItemContent);
					// 还原数组
					this.set("values",this.valuesBackup);
					if (this.dialogScroll) {
						this.dialogScroll.scrollTo({
							y : 0
						});
					}
					domStyle.set(this.contentNode,{display:'none'});
					domStyle.set(this.dialog.searchItemContent,{display:'block'});
				}else{
					domStyle.set(this.contentNode,{display:'block'});
					if(this.dialog.searchItemContent){
						domStyle.set(this.dialog.searchItemContent,{display:'none'});
					}
				}
			}
		},
		
		buildDialog : function(evt) {
			if (this.dialog)
				return;
			
			this.contentContainNode = domConstruct.create('div', {
				className : 'muiFormSelectContainElement'
			});
			
			// 搜索框设置
			if(this.isSearch){
				this.searchInputNode = domConstruct.create('div', {
					className : 'muiDialogElementSearchDiv'
				});
				if(!this.searchOpt){
					this.searchOpt = "height:'3.8rem'";
				}
				this.searchNode = domConstruct.create('div', {
					'data-dojo-type' : 'mui/search/SearchBar',
					'data-dojo-props' : this.searchOpt
					}, this.searchInputNode);
			}
			// 选项外层
			this.contentNodeWrap = domConstruct.create('div', {
				//style : {'height':'16.8rem'}
			},this.contentContainNode);
			
			this.contentNode = domConstruct.create('ul', {
				className : 'muiRadioGroupPopList'
			},this.contentNodeWrap);
			this.renderListItem(this.contentNode);
			
			var buttons = [];
			if (this.mul) {
				buttons = [ {
					title : msg['mui.button.ok'],
					fn : lang.hitch(this, this._closeDialog)
				} ];
			}
			
			this.dialog = Dialog.element({
				title : this.subject || msg['mui.form.select'],
				element : this.contentContainNode,
				buttons : buttons,
				fixElement : this.searchInputNode,
				position: 'bottom',
				showClass : this.showClass?this.showClass:'muiDialogSelect',
				callback : lang.hitch(this, function() {
					topic.publish(this.SELECT_CALLBACK, this);
					this.dialog = null;
				}),
				onDrawed:lang.hitch(this, function(evt) {
					if(this.dialogContentClass){
						domClass.add(evt.contentNode,this.dialogContentClass);	
					}
					this.doReduce(evt);
				})
			});
			
			if(this.dialogContentClass){
				domClass.add(this.dialog.contentNode,this.dialogContentClass);	
			}
			
		},
		
		  // 有值则复位
	      doReduce: function(obj) {
	        // 参考父类resizeTop方法
	        var wdgts = obj.htmlWdgts
	        var value = this.get("value")
	        this.dialogScroll = wdgts[0]
	        for (var i = 0; i < wdgts.length; i++) {
	          var item = wdgts[i]
	          if (value != item.value) {
	            continue
	          }
	
	          // 选中项相关信息
	          var itemT = item.checkboxNode.offsetTop
	          var itemH = item.checkboxNode.clientHeight
	          // 容器相关信息
	          var containerH = obj.contentNode.clientHeight
	          // 滚动容器相关信息
	          var contentH = this.contentNode.clientHeight
	
	          if (itemT + itemH < containerH) {
	            break
	          }
	
	          if (contentH - itemT < containerH) {
	            y = contentH - containerH
	          } else {
	            y = itemT - (containerH - itemH) / 2
	          }
	
	          wdgts[0].scrollTo(
	            {
	              y: -y
	            },
	            true
	          )
	        }
	      },
		
		cancelSearchItem : function(srcObj){
			if(srcObj.key && srcObj.key == xUtil.parseXformName(this)){
				this.set("values",this.valuesBackup); 
				domStyle.set(this.contentNode,{display:'block'});
				if(this.dialog.searchItemContent){
					// 清空原本的搜索选项
					domStyle.set(this.dialog.searchItemContent,{display:'none'});
				}
			}
		},

		resetValue : function(srcObj, arguContext){
			if(srcObj){
				var evtObjName = srcObj.get("name");
				if(evtObjName == null || evtObjName == ''){
					return;
				}
				var self = this;
				this.execFunByDom({'evtObjName':evtObjName,'bindDoms':this.bindDom},function(){
					self.set('value', '');
				});	
			}
		},
		
		buildEdit : function() {
			this.inherited(arguments);
			this.hiddenTextNode = domConstruct.create('input', {
				type:"hidden",
				name:this.textName
			}, this.domNode);
		},
		
		buildReadOnly: function() {
			this.inherited(arguments);
			this.hiddenTextNode = domConstruct.create('input', {
				type:"hidden",
				name:this.textName
			}, this.domNode);
		},
		
		buildHidden: function() {
			this.inherited(arguments);
			this.hiddenTextNode = domConstruct.create('input', {
				type:"hidden",
				name:this.textName
			}, this.domNode);
		},
		
		editValueSet : function(value) {
			if(value||!this.showPleaseSelect){
				domClass.add(this.domNode,"showTitle");
				if(this.getTextByValue(value) != ""){
					this.set('text', this.getTextByValue(value));							
				}else{
					this.set('text', '');
					return;
				}
			}else{
				this.set('text', '');
			}
			this.selectNode.value = value;
			this.hiddenTextNode.value =  this._get("text");
		},
		
		_setTextAttr:function(value) {
			if(value){
				var text = this.filtText(value);
				if(text){
					this.inputContent.innerHTML = text;
					this.text = text;
				}
			}else{
				this.inputContent.innerHTML = "";
				this.text = "";
			}
		},
		
		filtText : function(value){
			var text = '';
			if (value == undefined)
				return true;
			value = util.decodeHTML(value);//字符分割的时候需要用解码后的字符串，避免&amp;中的;被分割了
			var values = value.split(';');
			for (var k = 0; k < values.length; k++) {
				for (var j = 0; j < this.values.length; j++) {
					//临时参数，用于和分割后的字符判断（分割后的字符是解码后的）
					var tempText = this.values[j].text;
					tempText = util.decodeHTML(tempText);
					if (values[k] == tempText) {//判断的时候需要用解码后的字符串
						//实际拼接的是未解码的，保证数据正常
						text += (k == 0 ? this.values[j].text
								: ';' + this.values[j].text);
						break;
					}
				}
			}
			return text;
		},
		
		_changeStatus:function(srcObj){
			if(srcObj==this)
				this._working = false;
		},
		
		addItems:function(values,texts,isInit){
			var backList = [];
			if (values!=null && texts!=null) {
				if(!this.required) {
					backList.push({text:Msg['mui.ControlPleaseSelect'],value:''});
				}
				var val = '';
				isInit = isInit ? isInit:false;
				for ( var i = 0; i < values.length; i++) {
					//设置默认值 start
					if(isInit == true && (this.value && this.value == values[i])){
						val = values[i];
					}
					//end
					backList.push({text:texts[i],value:values[i]});
				}
				this.values = backList;
				//增加一个备用values，用于搜索之后还原
				this.valuesBackup = backList;
				//设置默认值 start
				//之所以把设置放到循环外面，是为了设置value的时候，调用父组件的getTextByValue方法赋值 by zhugr 2017-07-04
				if(isInit){
					this.set('value', val);
				}
				//end
			}			
		},
		
		_onClick:function(){
			this.queryData();
			this.buildDialog();
		},
		
	});
	return claz;
});
