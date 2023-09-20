define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/dom-style",
	"dojo/_base/lang",
	"dijit/_WidgetBase",
	"dijit/_Container",
	"dijit/_Contained",
	"dojo/_base/array",
	"dojo/request",
	"dojo/on",
	"mui/util",
	"mui/device/adapter",
	"mui/i18n/i18n!kms-lservice:lservice",
	dojoConfig.baseUrl + "kms/lservice/mobile/mportal/myMedalAndDiploma/CheckMoudleAndGetUserInfo.jsp"
	], function(declare, domConstruct, domClass, domStyle, lang, _WidgetBase, _Container, _Contained,  array, request, on, util, adapter,
			msg, checkMoudleAndGetUserInfoObj) {
	
	return  declare("kms.lservice.myMedalAndDiplomaNode",[_WidgetBase, _Container, _Contained], {
		
		//展示勋章
		showMedal: true,
		
		//展示证书
		showDiploma: true,
		
		//需要获取的对象,用来判断是否要进行渲染
		needGetObj: { 
			medal: true,
			diploma: true,
		},
		
		//需要获取的数量
		needGetCount: 0,
		
		//完成的数量
		completeCount: 0,
		
		//ajax获取结果
		ajaxResult: {},
		
		langInfo: {
			credit: "学分",
			credit_single: "学分:",
			integral: "积分",
			integral_single: "积分:",
			learnTime: "学时",
			learnTime_single : "学时(h):",
		},
			
		buildRendering : function() {
			this.inherited(arguments);	
			domStyle.set(this.domNode,{
				"display": "flex",	
				"height": "10rem",
				"align-items": "center"
			});
			this.currentOrder = 0;
			this.initCountAndGetInfo();
		},		
		
		//初始化数量和获取详情
		initCountAndGetInfo: function(){
			//需要进行网络请求获取的对象
			var needGetObj = {
				"diploma": true,
				"medal": true,
			};
			
			//判断模块是否存在，不存在就不必获取和渲染
			if(!checkMoudleAndGetUserInfoObj.diploma){
				needGetObj["diploma"] = false;
			}
			if(!checkMoudleAndGetUserInfoObj.medal){
				needGetObj["medal"] = false;
			}
			
			//判断卡片设置是否开启相应的展示，未开启则不必获取和渲染
			if(!this.showDiploma){
				needGetObj["diploma"] = false;
			}
			if(!this.showMedal){
				needGetObj["medal"] = false;
			}
			
			this.needGetObj = needGetObj;
			
			//需要获取数量
			var needGetCount = 0;			
			if(needGetObj["diploma"]) needGetCount++;
			if(needGetObj["medal"]) needGetCount++;
			this.needGetCount = needGetCount;
			
			//勋章
			var getDiplomaUrl = util.formatUrl("/kms/diploma/kms_diploma_ui/kmsDiplomaPerson.do?method=getUserDiplomaNum",true);
			//证书
			var getMedalUrl = util.formatUrl("/kms/medal/kms_medal_owner/kmsMedalOwner.do?method=getUserMedalNum",true);
			
			//发送网络请求
			if(needGetObj["diploma"]) this.ajaxGet(getDiplomaUrl, "diploma");
			if(needGetObj["medal"]) this.ajaxGet(getMedalUrl, "medal");
			
		},
		
		//ajax请求，获取
		ajaxGet: function(url, type){
			var self = this;
			var promise = request.get(url,{
				data:{},
				handleAs:"json"
			});
			promise.response.then(function(response){
				var data =  response.data;
				self.ajaxResult[type] = {
					success: true,
					data: data,
				};
				self.checkAndWait();
			},function(error){
				self.ajaxResult[type] = {
					success: false,
					data: null,
					errorMsg: error
				};
				self.checkAndWait();
			});
		},
		
		//检测所有任务是否都完成了
		checkAndWait: function(){
			this.completeCount ++;
			if(this.completeCount == this.needGetCount){
				this.build();
			}
		},
		
		//构建内容
		build: function(){	
			
			var leftContainer = domConstruct.create("div",{
				style: "display:flex;align-items:center;height:100%;",
			},this.domNode);
			
			var userHeadimageUrl = util.formatUrl(checkMoudleAndGetUserInfoObj.userHeadimageUrl,true);
			var userHead = domConstruct.create("img",{
				style: "height:5rem;width:5rem;border-radius: 5rem;margin-right:1rem;",
				src: userHeadimageUrl
			},leftContainer);
			
			var nameContainer = domConstruct.create("div",{
				style: "display:flex;flex-direction: column;justify-content: center;",
			},leftContainer);
			
			domConstruct.create("div",{
				style: "font-size:1.6rem;color: #2A304A;font-weight:bold;line-height:1.4rem;margin-bottom:0.2rem;",
				innerHTML: checkMoudleAndGetUserInfoObj.userName
			},nameContainer);
			
			domConstruct.create("div",{
				style: "margin-top:0.5rem;font-size: 12px;color: #979DB1;max-width: 10rem;overflow: hidden;text-overflow: ellipsis;word-break: keep-all;",
				innerHTML: checkMoudleAndGetUserInfoObj.userDeptName
			},nameContainer);
			
			var rightContainer = domConstruct.create("div",{
				style: "display:flex; flex:1;height:100%;align-items: center;justify-content: flex-end;",
			},this.domNode);
			
			if(this.needGetObj["diploma"]) this.buildDiploma(rightContainer);
			if(this.needGetObj["medal"]) this.buildMedal(rightContainer);
		},

		
		//构建证书
		buildDiploma: function(rightContainer){				
			var info = this.ajaxResult["diploma"];
			if(!this.checkResult(info)){
				return;
			}
			this.currentOrder++;			
			if(this.currentOrder == 2){
				this.buildHr(rightContainer);
			}
			var diplomaSum = 0;
			if(info && info.data){
				diplomaSum = info.data.num || 0;
			}
			
			var container = domConstruct.create("div",{
				style: "height: 50%; display:flex; align-items: center;",
			},rightContainer);
			
			var self = this;
			on(container, "click", function(e){
				self.onDiplomaClick(e);
			});
			
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/diploma.png",true);	
			domConstruct.create("img",{
				style: "height:1.6rem;width:1.6rem;",
				src: imgUrl
			},container);
			domConstruct.create("div",{
				style: "margin-left: 0.3rem;font-size: 1.4rem;color: #495180; line-height:1.2rem;margin-right:1rem;",
				innerHTML: msg["lservice.index.diploma"] || "证书"
			},container);
			domConstruct.create("div",{
				style: "font-size: 1.4rem; color: #2A304A; font-weight:bold;",
				innerHTML: diplomaSum
			},container);
			
			//this.buildNode("credit", imgUrl, this.langInfo["credit"], creditSum, "分");
			
		},
		
		//构建勋章
		buildMedal: function(rightContainer){
			var info = this.ajaxResult["medal"];
			if(!this.checkResult(info)){
				return;
			}
			this.currentOrder++;
			if(this.currentOrder == 2){
				this.buildHr(rightContainer);
			}			
			var medalNum = 0;
			if(info && info.data){
				medalNum = info.data.num || 0;
			}
			
			var container = domConstruct.create("div",{
				style: "height: 50%; display:flex; align-items: center;",
			},rightContainer);
			
			var self = this;
			on(container, "click", function(e){
				self.onMedalClick(e);
			});
			
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/medal.png",true);	
			domConstruct.create("img",{
				style: "height:1.6rem;width:1.6rem;",
				src: imgUrl
			},container);
			domConstruct.create("div",{
				style: "margin-left: 0.3rem;font-size: 1.4rem;color: #495180; line-height:1.2rem; margin-right:1rem;",
				innerHTML: msg["lservice.index.medal"] || "勋章"
			},container);
			domConstruct.create("div",{
				style: "font-size: 1.4rem; color: #2A304A; font-weight:bold;",
				innerHTML: medalNum
			},container);
		},
		
		checkResult: function(result){
			if(result.errorMsg && result.errorMsg.response && result.errorMsg.response.status){
				if( result.errorMsg.response.status == "403"){
					return false;
				}
			}
			return true;
		},
	
		
		buildHr: function(rightContainer){
			domConstruct.create("div",{
				style: "width:0.1rem; height:1.2rem;margin-left:1rem;margin-right:1rem;background: #F1F1F1;",
			},rightContainer);
		},
		
		onMedalClick: function(){
		
			adapter.open(util.formatUrl("/kms/medal/kms_medal_main/kmsMedalMain.do?method=medalList&personId=" + checkMoudleAndGetUserInfoObj.userId,true));
		},
		
		onDiplomaClick: function(){
			adapter.open(util.formatUrl("/kms/diploma/mobile/index.jsp",true));
		},
		
	});
});