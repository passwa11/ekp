(function(window, undefined){
	/**
	 * 控件基类
	 */
	var Control=function(builder,type,config){
		 this.builder=builder;
		 this.type=type;
		 
		 //控件实例属性
		 this.options = {
			attrs:{}
		 };
		 
		 this.$domElement=null;//JQ
		 this.listeners=[];
		 this.config=config;//config
	};
	
	Control.prototype={
			//listener
			addListener:addListener,
			removeListener:removeListener,
			fireListener:fireListener,
			
			//interfaces
			render:function(){},
			bind:function(){},
			destory:function(){},
			
			//
			draw:function($attach){
				var self=this;
				this.render(self.config);//渲染节点
				//控件默认允许拖拽
				this._canDrag = true;
				//渲染至绘制区
				if(this.$domElement){
					if(self.config && self.config.renderLazy){
						//不需渲染至文档
					}else{
						//判断$selectDomArr是否为可放置容器
						var selectDom = this.builder.$selectDomArr.length > 0 ? sysPrintUtil.getTdElement(this.builder.$selectDomArr[0][0]):null;
						var $attach = $attach ? $attach:(selectDom ? $(selectDom):null);
						if($attach){
							if($attach[0].innerHTML=='&nbsp;'){
								$attach.empty();
							}
							
							if(this instanceof sysPrintPageControl){
								this.attach($attach);
							}else{
								$attach.append(this.$domElement);
							}
						}else{
							this.builder.$domElement.append(this.$domElement);
							//标识 分页控件此时不允许拖拽
							if(this instanceof sysPrintPageControl){
								this._canDrag = false;
							}
						}
					}
					
				}
				this.listeners=[];
				this.bind();//绑定事件
				//通用事件
				if(this.$domElement){
					//鼠标移动到控件上
					this.$domElement.mouseover(function(){
						//增加虚线框
						if(!self.builder.owner.toolBarAction){
							$(this).addClass('sysprint_cursor_d border_dashed');
						}
					}).mouseout(function(){
						//删除虚线框
						if(!self.builder.owner.toolBarAction){
							$(this).removeClass('sysprint_cursor_d border_dashed');	
						}
					});
					
					//鼠标按下
					this.$domElement.mousedown(function(event){
						event.stopPropagation();
						//self.builder.setSelectDom($(this));//设置当前为选中节点
						self.builder.selectControl=self;
						self.fireListener('mousedown',event);
					});
					//
					this.$domElement.mousemove(function(event){
						//event.stopPropagation();
						self.fireListener('mousemove',event);
					});
					this.$domElement.mouseup(function(event){
						self.fireListener('mouseup',event);
					});
					if(!(this instanceof sysPrintDesignerTableControl)){
						if(this._canDrag){
							//可拖拽
							this.$domElement.draggable({
								cursor: 'move',
								revert:true,
								scroll:false,
								containment:'#sys_print_designer_draw',
								start:function(event, ui){
									self.fireListener('dragStart',event,ui);
									//标识
									self.builder._actionType='drag';
								},
								drag: function(event,ui){
									var curTop = sysPrintUtil.getMousePosition(event).y;
									var winBottom = ($(window).height()==0?document.body.clientHeight:$(window).height())-20;
									var drawTop = $('#sys_print_designer_draw').offset().top+20;
									var scrTop = $('#sys_print_designer_draw').scrollTop();
									if(curTop>winBottom){
										scrTop+=20;
									}
									if(curTop<=drawTop){
										scrTop-=20;
									}
									if(scrTop<0){
										scrTop = 0;
									}
									$('#sys_print_designer_draw').scrollTop(scrTop);
								},
								//helper:'clone',
								scope:'control'
							});
						}
					}
					//可放置
					if(this instanceof sysPrintDesignerTableControl || this instanceof sysPrintDetailsTableControl){
						this.$domElement.find('td').droppable({
							drop:function(event, ui){
								self.fireListener('dropStop',event,ui);
							},
							scope:'control'
						});
					}
					if(this instanceof sysPrintRightControl){
						this.$domElement.droppable({
							drop:function(event, ui){
								self.fireListener('dropStop',event,ui);
							},
							scope:'control'
						});
					}
					
				}
				return this;
			},
			remove:function(){
				this.destory(self.config);
				this.$domElement.remove();
			}
			
	};
	
	//继承
	Control.extend=function(prop){
		var parent=this;
		var child;
		if (prop && prop.hasOwnProperty('constructor')) {
			//存在构造函数
		     child = prop.constructor;
		 } else {
			 //不存在构造函数，执行父类的
		    child = function(){ 
		    	parent.apply(this, arguments); 
		    };
		}
		//继承父类的成员
		sysPrintUtil.extend(child,parent);
		//原型继承
		function bridge(){}
		bridge.prototype=parent.prototype;
		child.prototype=new bridge();
		//继承子类特有的属性
		if(prop){
			sysPrintUtil.extend(child.prototype,prop);
		}
		//防止继承后构造函数被修改
		child.prototype.constructor = child;
		//返回对象包含extend方法
		child.extend=arguments.callee;
		return child;
	};
	
	function addListener(type, fun){
		var evt = this.listeners[type];
		if (evt == null) {
			evt = [];
			this.listeners[type] = evt;
		}
		evt.push(fun);
	}
	
	function removeListener(type, fun){
		var evt = this.listeners[type];
		if (evt != null) {
			for (var i = 0; i < evt.length; i ++) {
				if (fun === evt[i]) {
					evt.splice(i, 1);
					return;
				}
			}
		}
	}
	
	function fireListener(type){
		var slice = Array.prototype.slice,
			args = slice.call(arguments);
		var evt = this.listeners[type];
		if (evt != null) {
			for (var i = 0; i < evt.length; i ++) {
				args.splice(0, 1);
				evt[i].apply(window,args);
			}
		}
	}
	
	window.sysPrintDesignerControl=Control;
	
})(window);