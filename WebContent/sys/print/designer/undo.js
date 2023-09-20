
(function(window,undefined){
	
	/*操作历史对象*/
	var Historys=function(){
		this.settings={
			limit:30
		};
		this.redos=[];//重做区
		this.undos=[];//撤销区
	};
	
	//增加历史操作
	Historys.prototype.add=function(operation){
		this.redos=[];//清空重做区
		if(!operation instanceof Historys.operation){
			return ;
		}
		var _prevOpt;
		if(!operation.prev && this.undos.length>0){
			_prevOpt=this.undos[0];
			operation.prev=_prevOpt.next;
		}

		this.undos.unshift(operation);
		if(this.settings.limit){
			this.undos.splice(this.settings.limit);
		}
		return this;
	};
	
	//撤销
	Historys.prototype.undo=function(){
		var operation;
		if(operation=this.undos.shift() ){
			if(operation.prev){
				this.redos.unshift(operation);
				operation.prev();
				PrintDesignerUndoSupport.fire("undo",operation.control?operation.control:null);
			}else{
				this.undos.unshift(operation);
			}
		}
		return this;
	};

	//重做
	Historys.prototype.redo=function(){
		var operation;
		if(operation=this.redos.shift()){
			if(operation.next){
				this.undos.unshift(operation);
				operation.next();
				PrintDesignerUndoSupport.fire("redo",operation.control?operation.control:null);
			}
		}
		return this;
	};
	
	//操作对象
	Historys.operation=function(args){
		this.prev=args.prev || null; //即将要执行的方法
		this.next=args.next || null; //上一个执行方法
	};
	
	/*JS AOP*/
	function AopImp() {
		this.doAspect;
	}
	
	AopImp.doAspect = function(object) {
		object.yield = null;
		object.rtnValue = {};//原返回值
		object.before = function(method, f) {
			var original = eval("this." + method);
			this[method] = function() {
				f.apply(this, arguments);
				return original.apply(this, arguments);
			};
		};
		object.after = function(method, f) {
			var original = eval("this." + method);
			this[method] = function() {
				this.rtnValue[method] = original.apply(this, arguments);
				return f.apply(this, arguments);
			};
		};
		object.around = function(method, f) {
			var original = eval("this." + method);
			this[method] = function() {
				this.yield = original;
				return f.apply(this, arguments);
			};
		};
	};
	
	var hasChanged=false;
	var isMoving=false;
	var currentActionType="default";
	//绘制区事件切入
	function UndoAdapter_fn_aspectBuilder (designer) {
//		debugger;
		var builder=designer.builder;
		if(!builder.before)
			AopImp.doAspect(builder);

		//鼠标移动事件开始时执行
		builder.before("_mouseMove", function(){
			switch (this._actionType) {
				case 'drag':
				case 'resize':
					isMoving = true;
					break;
			}
		});

		//鼠标弹出事件开始时执行
		builder.before('onDrawMouseUp',function(e){
//			debugger;
			if (sysPrintUtil.eventButton(e) != 1)
				return;
			currentActionType=this._actionType;
			switch(this._actionType){
				case "resize":
				case "createControl":
				case "drag":
					hasChanged=true;
					break;
			}
			//如果存在选中控件，添加控件事件切入
			var designer=sysPrintDesigner.instance;
			if(designer.builder.selectControl){
				UndoAdapter_fn_aspectControl(designer,designer.builder.selectControl);
			}
		});

		//鼠标弹出事件结束时执行
		builder.after("onDrawMouseUp", function(e){
//			debugger;
			if (sysPrintUtil.eventButton(e) != 1)
				return;
			switch(currentActionType){
				case "drag":
					hasChanged=true;
					isMoving=false;
					var designer = sysPrintDesigner.instance;
					setTimeout(function(){
						UndoAdapter_fn_addOperation();
					},500)
					
			}
		});

		//创建控件事件结束时执行
		builder.after("createControl", function(){
//			debugger;
			hasChanged=true;
			UndoAdapter_fn_addOperation();
			//控件事件切入
			//UndoAdapter_fn_aspectControl(Designer.instance,Designer.instance.control);
		});
	}
	
	//控件事件切入
	var undoDestoryTimer=null;//销毁定时器
	function UndoAdapter_fn_aspectControl(designer,control){
		function _callback(){
			hasChanged=true;
			isMoving=false;
			UndoAdapter_fn_addOperation();
		}
		
		function _destoryCallback(){
			var _designer=sysPrintDesigner.instance;
			if(undoDestoryTimer==null){
				undoDestoryTimer=setInterval(function(){
					var _controls=_designer.controls;
					//执行插入操作历史的条件：  1、_controls.length==0 :多个非容器类控件都已删除     2、_controls.length==1 &&……：容器类控件已被删除
					if(_controls.length==0 || (_controls.length==1 && _controls[0].container && _designer.control==null)){
						_callback();
						clearInterval(undoDestoryTimer);
						undoDestoryTimer=null;
					}
				},100);
			}
		}
		if(!control.after){
			AopImp.doAspect(control);
			//删除控件
			control.after('remove',_callback);
			//表格特有事件
			if(control.type == "table" || control.type=='detailsTable'){
				control.after("insertRow", _callback);
				control.after("appendRow", _callback);
				control.after("insertCol", _callback);
				control.after("appendCol", _callback);
				control.after("deleteRow", _callback);
				control.after("deleteCol", _callback);
				control.after("uniteCell", _callback);
				control.after("splitCell", _callback);
			}
			
			//文本特有事件
			if(control.type == 'textLabel'){
			}
		}
	}
	
	//工具栏按钮对象切入(主要针对font类操作)
	function UndoAdapter_fn_aspectToolbar(designer){
		var currentButtons=designer.toolBar.buttons;//当前表单所拥有的按钮
		var buttons=[].concat(sysPrintDesignerConfig.buttons.font);
		buttons.push('table');
//		debugger;
		for(var i=0;i<currentButtons.length;i++){
			if(!currentButtons[i].config || !contains(currentButtons[i].name,buttons)){
				continue;
			}
			var config=currentButtons[i].config;
			if(!config.before){
				AopImp.doAspect(config);
				if(config.run){
					
					//执行按钮操作开始时执行
					config.before('run',function(designer,button){
						isMoving=false;
					});

					//执行按钮操作结束时执行
					config.after('run',function(designer,button){
						if (designer.builder.selectControl) {
							hasChanged = true;
							UndoAdapter_fn_addOperation();
						}
					});
				}
			}
		}
	}
	
	//增加历史记录
	function UndoAdapter_fn_addOperation () {
//		debugger;
		var designer=sysPrintDesigner.instance;
		if(!hasChanged || isMoving){
			return ;
		}
		var html=getHtml();
		var operation=new Historys.operation({
			next:function(){
				setHtml(html);
				resetFieldPanel();
			}
		});
		//保存当前控件
		if(designer.builder.selectControl){
			operation.control=designer.builder.selectControl;
		}
		historys.add(operation);
		hasChanged=false;
		isMoving=false;
	}
	
	//指定item是否存在list中
	function contains (item,list) {
		if(!list || list.length==0){
			return false;
		}
		for ( var i = 0; i < list.length; i++) {
		if (item == list[i])
			return true;
		}
		return false;
	}
	//数据面板更新
	function resetFieldPanel(){
		if(window.sysPrintFieldPanelInstance){
			window.sysPrintFieldPanelInstance.destroy();
			var toolBarDom = $('#sys_print_designer_toolbar')[0];
			var ps = sysPrintUtil.absPosition(toolBarDom);
			var fp = new sysPrintFieldPanel();
			var designer = sysPrintDesigner.instance;
			fp.open(designer,ps.x,ps.y + toolBarDom.offsetHeight);
			window.sysPrintFieldPanelInstance = designer.builder.sysPrintFieldPanelInstance;
		}
	}
	
	function setHtml(html){
		var designer=sysPrintDesigner.instance;

		designer.builder.setHTML(html);
		//清空选中控件信息
		designer.builder.clearSeclectedCtrl();
	}
	function getHtml(){
		var designer=sysPrintDesigner.instance;
		//清空选中控件信息
		//designer.builder.clearSeclectedCtrl();
		
		var html=designer.builder.getHTML();
		return html;
	}
	
	/*初始化*/	
	var historys;
	function init () {
//		debugger;
		var designer=sysPrintDesigner.instance;
		AopImp.doAspect(designer);
		if(!designer.hasInitialized){
			setTimeout(init,500);
		}else{
			historys=new Historys();
			var html=getHtml();
			var operation=new Historys.operation({
				next:function(){
					setHtml(html);
					resetFieldPanel();
				}
			});
			historys.add(operation);
			UndoAdapter_fn_aspectBuilder(designer);//绘制区切入
			UndoAdapter_fn_aspectToolbar(designer);//按钮栏切入
			if(designer.builder.selectControl){
				UndoAdapter_fn_aspectControl(designer,designer.builder.selectControl);
			}
			//增加监听器
			designer.addListener('undoSaveHTML',function(){
//				debugger;
				var designer=Designer.instance;
				var html=getHtml();
				var operation=new Historys.operation({
					next:function(){
						setHtml(html);
						resetFieldPanel();
					}
				});
				historys.add(operation);
			});
		}
	}
	Com_AddEventListener(window,'load',init);
	
	function UndoAdapter_fn_undo () {
//		debugger;
		historys.undo();
	}

	function UndoAdapter_fn_redo () {
		historys.redo();
	}
	
	//控件配置
	sysPrintDesignerConfig.operations['undo'] = {
		imgIndex : 0,
		title : DesignerPrint_Lang.controlOperation_undo,
		icon_s:true,
		run : UndoAdapter_fn_undo,
//		hotkey : 'delete',
		type:'cmd'
	};
	sysPrintDesignerConfig.operations['redo'] = {
		imgIndex : 2,
		title : DesignerPrint_Lang.controlOperation_redo,
		icon_s:true,
		run : UndoAdapter_fn_redo,
//		hotkey : 'delete',
		type:'cmd'
	};
	
	//添加控件入口到工具栏
	sysPrintDesignerConfig.buttons.common.push('undo');
	sysPrintDesignerConfig.buttons.common.push('redo');
	
	//添加按钮到右键菜单
	sysPrintDesignerConfig.menus.undo = sysPrintDesignerConfig.operations['undo'];
	sysPrintDesignerConfig.menus.redo = sysPrintDesignerConfig.operations['redo'];
	
	var evtList={};
	var PrintDesignerUndoSupport={
		//事件接口	
		on:function(type,fn){
//			debugger;
			var evt = evtList[type];
			if (evt == null) {
				evt = [];
				evtList[type] = evt;
			}
			evt.push({fn: fn});
		},
		off:function(type,fn){
			if (arguments.length == 0) {
				evtList = {};
				return;
			}
			if (arguments.length == 1) {
				evtList[type] = [];
				return;
			}
			var evt =evtList[type] ;
			if(evt==null)
				return;
			for (var i = 0; i < evt.length; i ++) {
				if (evt[i].fn == fn) {
					evt.splice(i, 1);
					return this;
				}
			}
		},
		fire:function(type){
//			debugger;
			var evt = evtList[type];
			if (evt) {
				var args = [];
				for (i = 1, len = arguments.length; i < len; i++) {
					args[i - 1] = arguments[i];
				}
				for (var i = 0; i < evt.length; i ++) {
					try {
						var _evt = evt[i];
						_evt.fn.apply(this, args);
					} catch (e) {
						
					}
				}
			}
		}
	};
	
	PrintDesignerUndoSupport.saveOperation=function(prev,next){
//		debugger;
		var designer=sysPrintDesigner.instance;
		var arg={};//new Historys.opertion的参数
		if(prev!=null){
			arg['prev']=prev;
		}
		if(next!=null){
			arg['next']=next;
		}else{
			var html=getHtml();
			arg['next']=function(){
				setHtml(html);
				resetFieldPanel();
			};
		}
		var operation=new Historys.operation(arg);
		historys.add(operation);
	};
	//对外开放接口
//	PrintDesignerUndoSupport.getHTML=getCurrentHTML;
//	PrintDesignerUndoSupport.setHTML=setNewHTML;
	window.sysPrintUndoSupport = PrintDesignerUndoSupport;
})(window);