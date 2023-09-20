define(["dojo/_base/declare", 
        "dijit/_WidgetBase",
        "dijit/_Contained",
        "dijit/_Container",
        "dojo/dom-construct" ,
        "dojo/topic",
        "dijit/registry",
        "dojo/on",
        "dojo/touch",
        'dojo/query',
        "dojo/dom-class",
        ], 
		function(declare, WidgetBase,_Contained, _Container, domConstruct,topic,registry,on,touch,query,domClass) {
			return declare("hr.criteria.criteria", [WidgetBase,_Contained,_Container], {
			queryDept:'',
			querySelect:new Map(),
			queryMore:'',
			changeSelect:'changeSelect',
			has_line:true,
			buildRendering:function(){
				this.inherited(arguments);
			},
			startup:function(){
				this.inherited(arguments);
				var op = domConstruct.create("p",{});
				this.domNode.className="criteriaNode"
				var _this = this;
				/*var postWidget = registry.byId("criteriaOrgDept");
				postWidget.onValueChange="window.onChangeAddress"
				window.onChangeAddress = this.onChangeAddressDept;*/
				topic.subscribe(this.changeSelect,this.onChangeSelect)
				topic.subscribe('hr/criteria/repload',function(queryDept){
					_this.queryDept = queryDept;
					_this.reloadQuery();
				});
				topic.subscribe('hr/criteria/more/submit',function(data){
					_this.queryMore = data;
					_this.reloadQuery();
				})
				topic.subscribe('criteria/mask',function(status){
					if(status){
						_this.createMask();
					}else{
						_this.cancelMask();
					}
				})
				topic.subscribe('criteria/select/changeValue',function(data){
					if(data.value){
						_this.querySelect.set(data.id,{value:data.value,name:data.name});
					}else{
						_this.querySelect.set(data.id,"");
					}
					_this.reloadQuery();
				});
			},
			onChangeAddressDept:function(deptId){
				var queryDept = "";
				if(deptId){
					queryDept = "q._fdDept="+deptId;
				}else{
					queryDept = "";
				}
				topic.publish('hr/criteria/repload',queryDept);
			},
			onChangeSelect:function(value){
				this.querySelect = value;
			},
			reloadQuery:function(){
				var querySelctArr=[]
				var prefix = this.has_line?"q._":"q.";
				for (var [key, value] of this.querySelect) {
					if(value){
						querySelctArr.push(prefix+value.name+'='+value.value);
					}
				}
				var query = this.coninQuery(this.queryDept)+this.coninQuery(querySelctArr.join("&"))+this.coninQuery(this.queryMore);
				topic.publish("hr/criteria/value",query);
			},
			coninQuery:function(data){
				if(data){
					return "&"+data;
				}else{
					return "";
				}
			},
			//增加遮罩
			createMask:function(){
				if(query("#hr-mask").length==0){
					var maskNode = domConstruct.create("div",{className:'hr-criteria-mask',id:'hr-mask'},query(".mblScrollableViewContainer")[0]);
					var _this = this;
					on(maskNode,touch.press,function(){
						_this.changeIcon();
					})
				}else{
					if(document.getElementsByClassName("mblScrollableViewContainer") != ''
						&& document.getElementsByClassName("mblScrollableViewContainer") != null
						&& document.getElementsByClassName("mblScrollableViewContainer")[0] != null
						&& document.getElementsByClassName("mblScrollableViewContainer")[0] != '')
						{
						   document.getElementsByClassName("mblScrollableViewContainer")[0].style="";
						}
					
					domClass.add(query("#hr-mask")[0],'hr-criteria-mask');
				}
				this.forbidPageScroll();
			},
			cancelMask:function(){
				var that = this;
				setTimeout(function(){
					document.querySelector("#hr-mask").className="";
					that.allowPageScroll();
				},500)
				//this.allowPageScroll();
			},
			//禁止滑屏
			forbidPageScroll:function(){
				document.body.addEventListener('touchmove', function (e) {
		              e.preventDefault();
		        }, { passive: false });
			},
			//允许滑屏
			allowPageScroll:function(){
				document.body.addEventListener('touchmove', function (e) {
	                  e.returnValue = true;
	            }, {passive: false});
			},			
			
		});
});