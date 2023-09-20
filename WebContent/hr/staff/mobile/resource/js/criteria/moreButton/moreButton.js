define(["dojo/_base/declare", 
        "dojo/dom-construct",
        "dijit/_WidgetBase",
        "dijit/_Contained",
        "dijit/_Container",
        "dojo/on",
        "dojo/touch",
        "dojo/dom-class",
        "dojo/query",
        "dojo/topic","dijit/registry","mui/i18n/i18n!hr-staff:mobile.hr.staff.view"], 
		function(declare, domConstruct, WidgetBase,_Contained, _Container,on,touch,domClass,query,topic,registry,msg) {
		return declare("hr.criteria.moreButton", [WidgetBase,_Contained,_Container], {
			subject:'',
			//是否多选
			multi:false,
			//列表数据
			data:[],
			//选中样式
			value:[],
			emptyValue:msg['mobile.hr.staff.view.8'],
			checkBoxValue:[],
			isclose:false,
			buildRendering:function(){
				this.inherited(arguments);
				this.selectNode = domConstruct.create("div",{className:'hr-select'},this.domNode);
				this.content = domConstruct.create("div",{className:'hr-select-content'},this.selectNode);
				this.contentValueNode = domConstruct.create("span",{className:'hr-select-subject',innerHTML:this.subject},this.content);
				this.contentIconNode = domConstruct.create("span",{className:'hr-select-content-down'},this.content);
				this.dropdownContent = domConstruct.create("div",{className:'hr-select-dropdown trogger-height-hidden'},this.domNode)
				var _this = this;
				this.troggleDropwDown();
			},
			startup:function(){
				this.inherited(arguments);
				var _this = this;
				var box = this.containerNode.querySelector(".moreBtnBox");
				domConstruct.place(box,this.dropdownContent);
				var ensureBox = domConstruct.create("div",{className:'more-button-ensure'},this.dropdownContent);
				var resetBtn = domConstruct.create("div",{className:'more-button-ensure-button',innerHTML:msg['mobile.hr.staff.view.14']},ensureBox);
				var submitBtn = domConstruct.create("div",{className:'more-button-ensure-button',innerHTML:msg['mobile.hr.staff.view.15']},ensureBox);
				on(resetBtn,touch.press,function(){
					_this.resetAddress();
					topic.publish("hr/criteria/more/reset");
				})
				on(submitBtn,"click",function(){
					var addressValue=_this.getAddress();
					var checkBoxValue=_this.checkBoxValue.join("&");
					var res=[];
					res.push(addressValue);
					if(checkBoxValue){
						var dataArr = _this.checkBoxValue[0].split('&');
						if(dataArr.length ==2){
							var startTime =  dataArr[0].split('=');
							var endTime =  dataArr[1].split('=');
							if(startTime.length == 2 && endTime.length == 2 ){
								res.push('fdEnterPriseStartTime='+ startTime[1]);
								res.push('fdEnterPriseEndTime='+ endTime[1]);
							}
						}
					}
					topic.publish("hr/criteria/more/submit",res.join("&"));
					_this.closeIcon();
					topic.publish('criteria/mask',false);
				})
				on(resetBtn,touch.press,function(){
					topic.publish("hr/criteria/more/reset");
					_this.checkBoxValue
					_this.checkBoxValue=[];
				})
				topic.subscribe("hr/criteria/checkbox",function(data){
					if(data.index == -1){
                        _this.checkBoxValue=[];
					}else{
                        _this.checkBoxValue[data.index]=data.value;
                    }
				})
				topic.subscribe("hr/criteria/closed",function(id){
					if(id!=_this.id&&_this.isclose==false){
						_this.closeIcon();
					}
				});
			},
			troggleDropwDown:function(){
				on(this.content,touch.press,this.changeIcon.bind(this))
			},
			resetAddress:function(){
				var post = registry.byId("criteriaOrgPost");
				var dept = registry.byId("criteriaOrgDept");
				post._setCurIdsAttr("");
				post._setCurNamesAttr("");
				dept._setCurIdsAttr("");
				dept._setCurNamesAttr("");
			},
			getAddress:function(){
				var post = registry.byId("criteriaOrgPost");
				var dept = registry.byId("criteriaOrgDept");
				var param="";
				var postIds=post.curIds;
				var deptIds=dept.curIds;
				if(postIds){
					param+="q._fdPosts="+postIds;
				}
				if(deptIds){
					if(postIds){
						param+="&";
					}
					param+="q._fdDept="+deptIds;
				}
				return param;
			},
			containsNode:function(root,node,res){
				if(root==node){
					this.isclose = true;
				}
				for(var i = 0;i<root.children.length;i++){
					var res = this.containsNode(root.children[i],node);
				}					
			},
			changeIcon:function(){
				if(domClass.contains(this.dropdownContent,'trogger-height-hidden')){
					domClass.remove(this.dropdownContent,'trogger-height-hidden');
					domClass.remove(this.contentIconNode,'hr-select-content-down');
					domClass.add(this.contentIconNode,'hr-select-content-up');
					domClass.add(this.dropdownContent,'more-button-dropdown');
					this.contentValueNode.innerHTML=msg["mobile.hr.staff.view.16"];
					this.contentValueNode.style.color="#318FED";
					topic.publish("hr/criteria/closed",this.id);
					topic.publish('criteria/mask',true);
					this.isclose=false;
				}else{
					this.isclose=true;
					this.closeIcon();
					topic.publish('criteria/mask',false);
				}
			},
			closeIcon:function(){
				domClass.add(this.dropdownContent,'trogger-height-hidden');
				domClass.remove(this.contentIconNode,'hr-select-content-up');
				domClass.add(this.contentIconNode,'hr-select-content-down');
				domClass.remove(this.dropdownContent,'more-button-dropdown');
				this.contentValueNode.innerHTML=msg["mobile.hr.staff.view.17"];
				this.contentValueNode.style.color="#50617A";
			}

		});

});