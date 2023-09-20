define(["dojo/_base/array",	"dojo/_base/declare","dojo/_base/lang", "dojo/dom-construct",
        "dojo/dom-attr", "dijit/_Contained","dijit/_Container","dijit/_WidgetBase", 
        "dojo/topic", "sys/xform/mobile/controls/fSelect/FSelectListItem",
        "sys/xform/mobile/controls/xformUtil","dojo/request","mui/util",
        "dojo/dom-style","mui/list/item/_TemplateItemMixin",
        "dojo/query","dojo/dom-class","dojox/mobile/_css3","mui/i18n/i18n!sys-xform-base:mui","mui/dialog/Tip"], 
	function(array, declare, lang, domConstruct, domAttr, Contained, 
			Container, WidgetBase, topic, FSelectListItem, xUtil, 
			request, util, domStyle,TemplateItem,query,domClass,css3,Msg,Tip){
	return declare("sys.xform.mobile.controls.fSelect.FSelectList", [WidgetBase, Container, Contained], {
		
		isMul:true,
		
		pageAble:false,
		
		key:null,
		
		headers:null,
		
		argu:null,
		
		baseClass:"muiCateLists",
		
		_pageNum:1,
		
		items : [],
		
		SUBMIT_EVENT : "/sys/xform/fSelect/submit",
		
		ONPUSH_EVENT : "/mui/list/onPush",
		
		SEARCH_EVENT : "/sys/xform/fSelect/search",
		
		SELECTED_EVENT : "/sys/xform/fSelect/selected",
		
		SHOWNOMORE_EVENT : "/sys/xform/event/showNomore",
		
		NEXTPAGE_EVENT : "/sys/xform/event/nextpage",
		
		CANCEL_SEARCH_EVENT : "/mui/search/cancel",
		
		CHECKED_EVENT : "/sys/xform/fSelect/checked",
		
		UNSELECTALL_EVENT : "/sys/xform/fSelect/unSelectAll",
		
		postCreate : function() {
			this.inherited(arguments);
			//监听提交按钮事件
			this.subscribe(this.SUBMIT_EVENT,"_returnData");
			//监听搜索事件
			this.subscribe(this.SEARCH_EVENT,"_searchDataInfo");
			this.subscribe(this.ONPUSH_EVENT,"_appendNext");
			
		},
		
		_returnData:function(srcObj){
			if(this.key==srcObj.key){
				var rtnData={};
				rtnData.headers = this.headers;
				rtnData.argu = this.argu;
				rtnData.rows=[];
				array.forEach(this.getChildren(),function(item){
					if(item.selected){
						rtnData.rows.push(item.data);
					}
				},this);
				//必填且至少选中n项
				if (this.required){
					if (this.leastNItem > rtnData.rows.length){
						var tip = Tip.tip({text:Msg['mui.fSelect.validateMsg'].replace("{title}",this.subject).replace("{n}",this.leastNItem)
						});
						domStyle.set(tip.containerNode, {
							"z-index":9999999
						});
						return false;
					}
				}
				topic.publish(this.SELECTED_EVENT, this, rtnData);
			}
		},
		
		setArgu:function(argu){
			this.argu = argu;
		},
		
		setData:function(data,dom,curIds){
			if(!this.headers)
				this.headers = data.headers;
			if(!dom){
				dom = this.containerNode;
			}
			if(data.length>0){
				this.items = [];
				var index = 0;
				array.forEach(data,function(tmpData){
					tmpData.itemVals = [tmpData.text];
					var item = this.createItem(data.headers, tmpData);
					this.append(item,dom);
					var idArr = ( curIds || '').split(";");
					for (var i = 0; i < idArr.length; i++){
						if (idArr[i] === tmpData.value){
							index++;
							item._selectData();
							break;
						}
					}
				},this); 
				if (index != data.length){
					topic.publish(this.UNSELECTALL_EVENT,this);
				}
			}
		},
		
		_searchDataInfo:function(srcObj,evt){
			if(evt){
				if(srcObj.key == this.key){
					var self = this; 
					var argu = evt.argu;
					argu = JSON.parse(argu);
					var regex = new RegExp(argu.value, 'gi');
					var hasData = false;
					array.forEach(this.items,function(item){
						var result = item.data.text.match(regex);
						if (result == null){
							 domStyle.set(item.domNode, "display", "none");
						}else{
							domStyle.set(item.domNode, "display", "block");
							hasData = true;
						}
					},this);
					if (!hasData){
						this.noDataDom = domConstruct.toDom(this.buildNoDataMessage());
						domConstruct.place(this.noDataDom,this.domNode,"last");
					}else{
						if (this.noDataDom){
							 domConstruct.destroy(this.noDataDom);
							 this.noDataDom = null;
						}
						topic.publish("/sys/xform/fSelect/unSelectAll",this); //取消全选
					}
				}
			}
		},

		_appendNext:function(srcObj,handle){
			if(srcObj.key==this.key){
				if(this.pageAble){
					var _self=this;
					topic.publish(_self.NEXTPAGE_EVENT,this,{argu:this.argu,pageNum:(this._pageNum+1)},{done:function(data){
							if(data!=null && data.rows.length>0){
								_self.argu.dataLength = data.rows.length;
								_self._pageNum = _self._pageNum + 1;
								_self.setData(data);
							}else{
								topic.publish(_self.SHOWNOMORE_EVENT,_self);
							}
							handle.done();
						}
					});
				}else{
					handle.done();
				}
			}
		},
		
		append:function(item,dom){
			domConstruct.place(item.domNode,dom,"last");
		},
		
		createItem:function(headers,row){
			var item = new FSelectListItem({key:this.key, isMul:this.isMul, headers:headers, data:row});
			item.startup();
			this.items.push(item);
			return item;
		},
		buildNoDataMessage : function(){
			var arr = [];
			arr.push("<li class='muiListNoData' style='height: 599px; line-height: 599px;'>");
			arr.push("<div class='muiListNoDataArea'>");
			arr.push("<div class='muiListNoDataInnerArea'>");
			arr.push("<div class='muiListNoDataContainer muiListNoDataIcon'><i class='mui mui-message'></i></div>");
			arr.push("</div>")
			arr.push("<div class='muiListNoDataTxt'>")
			arr.push("暂无内容");
			arr.push("</div>");
			arr.push("</div>");
			return arr.join("");
		}
		
		
		
		
	});
});
