//常用查询
define( [ "dojo/_base/declare", "mui/panel/SlidePanel", "mui/util", "dojo/query",
          "dojo/_base/lang"],
		function(declare, SlidePanel, util, query, lang) {
			var query = declare("mui.query.CommonQueryDialogMixin", null, {
				filterURL:null,
				
				redirectURL: null,
				
				SLIDE_PANEL_CLICK : '/mui/panel/slide/click',
				
				SLIDE_PANEL_CHANGEPROPS: '/mui/panel/slide/changeprops',
				
				//数组，格式对象包含信息，text，dataURL，icon
				store:[],
				
				postCreate : function() {
					this.inherited(arguments);
					this.subscribe(this.SLIDE_PANEL_CLICK,"doValueChange");
					this.subscribe(this.SLIDE_PANEL_CHANGEPROPS, "doPropsChange");
				},
				
				doPropsChange: function(newProps){
					for(var key in newProps){
					  this[key] = newProps[key];
					}
				},
			
				doValueChange:function(srcObj, evt){
					if(!this.dealed){
						if(evt && evt.index){
							var selected = this.store[evt.index];
							this.defer(function(){
								window.open(this._formatParam(selected) , "_self");
								this.dealed = false;
							},200);
						}else{
							this.dealed = false;
						}
					}
					this.dealed = true;
				},
				
				show:function(evt){
					var slides = query("#_common_query_panel");
					if(slides.length>0) 
						return;
					var slidePanel = new SlidePanel({id:'_common_query_panel',store:this.store,dir:'right',
						width:'45%',icon:'mui-file-text'});
					document.body.appendChild(slidePanel.domNode);
					slidePanel.startup();
				},
				
				_formatParam:function(item){
					var url = location.href;
					if(this.redirectURL){
						//苹果浏览器下点击返回的时候有缓存，item里面的text在跳转之前已经被转码，返回后在常用查询里面会显示为转码之后的text
						var cloneItem = lang.clone(item);
						cloneItem.text = encodeURIComponent(cloneItem.text);
						url =  util.formatUrl(util.urlResolver(this.redirectURL, cloneItem));
					}
					return util.setUrlParameter(url,"queryStr",item['dataURL']);
				}
			});
			return query;
	});