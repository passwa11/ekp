define(["dojo/_base/declare", 
        "dijit/_WidgetBase",
        "dijit/_Contained",
        "dijit/_Container",
        "dojo/dom-construct" ,
        "dojo/topic",
        "dojo/request"
        ], 
		function(declare, WidgetBase,_Contained, _Container, domConstruct,topic,request) {
		return  declare("hr.staff.template", [WidgetBase,_Contained,_Container], {
			url:'',
			module:'',
			template:'',
			buildRendering:function(){
				this.inherited(arguments);
			},
			getData:function(fdId){
					var hasData = "";
					var _this = this;
					topic.subscribe("hr/template/hasData",function(data){
						hasData = data;
					})
					if(hasData){
						this.renderTemplate(hasData[this.module]);
					}else{
					 	request.post(this.url,{data:{fdOrgId:fdId}}).then(function(data){
					 		if(data){
					 			try{
					 				var json = JSON.parse(data);
					 				topic.publish("hr/template/hasData",json);
					 				_this.renderTemplate(json[_this.module])
					 			}catch(e){
					 				console.log(e)
					 			}
					 		}
						})
					}

			
				
			},
			renderTemplate:function(data){
				!this.template?this.template=this.containerNode.innerHTML:'';
				
				var content =this.template;
				var template = '';
				var express = content.match(/@(.+)@/g).map((v)=>{
					return v.match(/@(.+)@/)[1];
				});
				var sum = 0;
				data.map((v)=>{
					if(v['data']){
						sum+=v['data'][0];
					}
				})
				for(var i = 0;i<data.length;i++){
					var copyContent = content;
					for(var j=0;j<express.length;j++){
						var reg = new RegExp("@"+express[j].replace(/\//g,'\/').replace(/\$/g,"\\$").replace(/\*/g,"\\*").replace(/\?/g,"\\?")+"@","g");
						var vt = express[j]
						var regVib = /\$[a-z]+/g;
						express[j].match(regVib).map((item)=>{
								if(item=='$sum'){
									vt = vt.replace(regVib,sum);
								}else{
									var tempReg = new RegExp(item.replace(/\$/g,"\\$"),"g");
									
									if(item=='$color'){
										var color = '';
										switch(data[i]['name']){
											case '试用':
												color='dsy-i-icon-jz'
												break;
											case '实习':
												color='dsy-i-icon-ss'
												break;
											case '临时':
												color='dsy-i-icon-ls'
												break;
											case '解聘':
												color='dsy-i-icon-jp'
												break;
											case '试用延期':
												color='dsy-i-icon-sy';
												break;
											case '退休':
												color='dsy-i-icon-tx';
												break;
											case '离职':
												color='dsy-i-icon-lz';
												break;
												
										}
										vt = vt.replace(tempReg,color);
									}else{
										vt = vt.replace(tempReg,data[i][item.replace(/\$/g,'')])
									}
									
								}
						})
						var res = this.evil(vt);
						
						if(!res&&res!=0){
							res =vt;
						}
						copyContent = copyContent.replace(reg,res);
						
					}
					template+=copyContent;
				}
				 this.containerNode.innerHTML = template;
			},
			evil:function (str) {
			    try{
			    	return new Function('return ' + str)()
			    }catch(e){
			    	
			    }
			    
			},
			startup:function(){
				this.inherited(arguments);
				var _this = this;
				this.getData();
				topic.subscribe("hr/statistic/address/change",function(fdId){
					_this.getData(fdId);
				})
			}
		});
});