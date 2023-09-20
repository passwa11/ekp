define([ "dojo/_base/declare", "mui/dialog/_DialogBase", "dojo/dom-construct",
		"dojo/dom-style", "dojo/dom-class", "dojo/_base/array",
		"dojo/_base/lang", 'dojo/parser',"mui/i18n/i18n!sys-mobile","dojo/query" ], function(declare,
		_DialogBase, domConstruct, domStyle, domClass, array, lang, parser,Msg,query) {
		
	var claz = declare('km.forum.mobile.resource.js.ForumBreakPageDialog', [ _DialogBase ], {

		element : null,

		title : '',

		buttons : [],

		callback : null,
		
		onDrawed : null,

		showClass : 'muiDialogElementShow',

		//是否需要关闭
		canClose : true,

		//是否需要滚动，需要滚动时必须解析
		scrollable : true,
		
		//是否需要解析
		parseable : false,
		
		//当前页
		pageNo:1,
		
		//总页数
		totalPageSize:0,
		
		// 弹出框内容加载完毕后调整位置
		loaded : function() {
			domStyle.set(this.containerNode, {
				'margin-top' : -(this.containerNode.offsetHeight/2) + 'px',
				'margin-left' : -(this.containerNode.offsetWidth/2) + 'px'
			});
			//var input = query('input',this.element)[0].focus(); 自动获取焦点暂时做不到
		},

		buildRendering : function() {
			this.inherited(arguments);
			if (this.element==null){
				var html='<div class="dialog_content">';
				html=html+'<div class="jump_dialog_first"><span>'+Msg['mui.break.to.page']+'<input type="text" class="jump_dialog_input" >'+Msg['mui.page']+'</span></div>';
				html=html+'<div class="jump_input_msg" style="display:none;"><span>'+Msg['mui.current.error.page']+'</span></div>';
				html=html+'<div class="jump_dialog_page"><span>'+Msg['mui.current.page']+(this.pageNo-1)+'/'+this.totalPageSize+Msg['mui.page']+'</span></div>';
				html=html+'</div>';
				var contentElementNode = domConstruct.create('div', {
					className : 'muiBackDialogElement',
					innerHTML : html
				});
				this.element=contentElementNode;
				var input = query('input',this.element)[0];
				this.connect(input,"onblur", 'doBlur');
				this.connect(input,"input", 'onInput');				
			}
			this.domNode = domConstruct.create('div', {
				className : 'muiDialogElement' + (this.showClass!=null?(" " +this.showClass):'' )
			}, document.body, 'last');

			this.containerNode = domConstruct.create('div', {
				className : 'muiForumDialogElementContainer'
			}, this.domNode);
			
			// 内容节点
			this.contentNode = domConstruct.create('div', {
				className : 'muiDialogElementContent'
			}, this.containerNode);

			// 创建按钮节点
			if (this.buttons.length > 0) {
				this.buttonsNode = domConstruct.create('div', {
					className : 'muiDialogElementButtons'
				}, this.containerNode);

				this.buttonsDom = [];
				array.forEach(this.buttons, lang.hitch(this, function(item) {
					var btn = domConstruct.create('div', {
						className : 'muiDialogElementButton',
						innerHTML : item.title
					}, this.buttonsNode);
					this.buttonsDom.push(btn);
				}));
				this.connect(this.buttonsNode, 'click', '_onClick');
			}
			
			var _container = this.contentNode;
			var isParse = this.parseable;
			if (this.scrollable) {
				_container = domConstruct.create('div', {
					'data-dojo-type' : 'dojox/mobile/ScrollableView',
					'data-dojo-props' : 'scrollBar:true,height:\'100%\''
				}, this.contentNode);
				isParse = true;
			}
			domConstruct.place(this.element, _container);
			
			if(isParse){
				var self = this;
				parser.parse(this.contentNode).then(function(widgetList) {
					self.htmlWdgts = widgetList;
					self.loaded();
					if(self.onDrawed){
						self.onDrawed(self);
					}
				});
			}else{
				this.loaded();
				if(this.onDrawed){
					this.onDrawed(this);
				}
			}
			
		},

		_onClick : function(evt) {
			var target = evt.target;
			for (var i = 0; i < this.buttonsDom.length; i++) {
				if (target === this.buttonsDom[i]) {
					if (this.buttons[i].id && (this.buttons[i].id == "canel") ){
						this.buttons[i].fn.call(window, this);
					}
					var input = query('input',this.element)[0];
					if (!this._isNumber(input.value)){
						input.value="";	
						this.onInput();
						return ;
					}
					this.buttons[i].fn.call(window, this,input.value);
					if (this.callback)
						this.callback(window, this);
					break;
				}
			}
		},

		_onClose : function(evt) {
			if (this.callback)
				this.callback(window, this);
			this.hide();
		},

		show : function() {
			return this.inherited(arguments);
		},

		hide : function() {
			this.inherited(arguments);
			array.forEach(this.htmlWdgts,function(wdt){
				if(wdt && wdt.destroy){
					wdt.destroy();
				}
			});
			domConstruct.destroy(this.domNode);
			this.domNode = null;
			this.destroy();
		},
		doBlur : function() {
			var input = query('input',this.element)[0];			
			if (input.value=="") return ;
			if (!this._isNumber(input.value)){
				input.value="";	
				var dviMsg = query('.jump_input_msg',this.element);
				dviMsg.style("display","none");
			}
		},
		onInput :function() {
			var input = query('input',this.element)[0];
			var dviMsg = query('.jump_input_msg',this.element);
			var isNone="";
			if (input.value==null || input.value ==""){
				isNone = "none";
			}else if (!this._isNumber(input.value)){ 
				isNone="";
			}else{			   
				isNone = "none";
			}
			dviMsg.style("display",isNone );
		},
		_isNumber : function(v){
			if (v==null || v=="" ){
				return false;
			}
			var re = /^[0-9]+.?[0-9]*$/;
			if (!re.test(v)){
				return false;		
			}
			if (parseInt(v)>this.totalPageSize){
				return false;
			}
		    return true;
		}
	});
	return {
		element : function(options) {
			var obj = new claz(options);
			return obj.show();
		}
	};
})