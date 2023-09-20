define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/dom-style",
	"dojo/_base/lang",
	"dijit/_WidgetBase",
	"dojo/_base/array",
	"dojo/request",
	"dojo/on",
	"mui/util",
	"mui/device/adapter",
	"./MyIntegralAndCreditAndLearnTimeMixin",
	"./item/LayoutListNumberItem",
	"mui/i18n/i18n!kms-lservice:mobile"
	], function(declare, domConstruct, domClass, domStyle, lang, _WidgetBase, array, request, on, util, adapter,
			kmsLserviceIntegralCreditTimeMixin, LayoutListNumberItem, msg) {
	
	return  declare("kms.lservice.myIntegralAndCreditAndLearnTimeNumberNode",[_WidgetBase, kmsLserviceIntegralCreditTimeMixin], {
		
		//展示积分
		showIntegral: true,
		
		//展示学分
		showCredit: true,
		
		//展示学时
		showLearnTime: true,	
		
		//当前序号
		currentOrder: 0,
		
		//需要获取的数量
		needGetCount: 0,
		
		//完成的数量
		completeCount: 0,
		
		//ajax获取结果
		ajaxResult: {},
		
		typeUrl: {
			"credit": util.formatUrl("/kms/credit/mobile",true),
			"learnTime": util.formatUrl("/kms/loperation/mobile/myLearnTime/index.jsp",true),
			"integral": util.formatUrl("/kms/integral/mobile",true),
		},
		
		baseClass: "kmsLserviceNumberNode",
		
		langInfo: {
			credit: msg["mobile.msg.credit"],
			credit_single: msg["mobile.msg.credit"] + ":",
			integral: msg["mobile.msg.integral"],
			integral_single: msg["mobile.msg.integral"] + ":",
			learnTime: msg["mobile.msg.learnTime"],
			learnTime_single :msg["mobile.msg.learnTime"]+ ":",
			unitScore: msg["mobile.msg.unit.score"],
			unitHour: msg["mobile.msg.unit.hour"],
			exp: msg["mobile.msg.exp"],
			wealthValue: msg["mobile.msg.wealthValue"],
		},
			
		buildRendering : function() {
			this.inherited(arguments);	
			this.initCountAndGetInfo();
		},		
		
		//构建内容
		build: function(){			
			if(this.needGetObj["credit"]) this.buildCredit();
			if(this.needGetObj["learnTime"]) this.buildTime();
			if(this.needGetObj["integral"]) this.buildIntegral();
			
			//构建占位图
			if(this.needGetCount < 3){
				this.buildSeizeImg();
			}			
		},

		
		//构建学分
		buildCredit: function(){	
			this.currentOrder++;			
			var info = this.ajaxResult["credit"];
			var creditSum = "";
			if(info && info.data){
				creditSum = info.data.fdCreditSum || 0;
			}
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/credit.png",true);	
			if(this.needGetCount == 1){
				this.buildSingleNode("credit", imgUrl, this.langInfo["credit_single"], creditSum);
			}else{
				this.buildMultiNode("credit", imgUrl, this.langInfo["credit"], creditSum);
			}
			
		},
		
		//构建学时
		buildTime: function(){
			this.currentOrder++;
			var info = this.ajaxResult["learnTime"];
			var time = 0;
			if(info && info.data){
				time = info.data.time || 0;
			}
			time = Number(time);
			//分钟转换为小时
			time = time / 60;
			time = time.toFixed(1);
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/learnTime.png",true);
			if(this.needGetCount == 1){
				this.buildSingleNode("learnTime", imgUrl, this.langInfo["learnTime_single"], time);
			}else{
				this.buildMultiNode("learnTime", imgUrl, this.langInfo["learnTime"], time);
			}
		},
		
		
		//构建积分
		buildIntegral: function(){
			this.currentOrder++;
			var info = this.ajaxResult["integral"];
			var riches = 0;
			if(info && info.data){
				if(this.integralShowProp == "fdTotalScore"){
					riches = info.data.fdTotalScore || 0;
				}else{
					riches = info.data.usableRiches || 0;
				}
			}
			var text = this.langInfo["wealthValue"];
			if(this.integralShowProp == "fdTotalScore"){
				text = this.langInfo["exp"];
			}
			if(this.needGetCount == 1){
				text += ":";
			}
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/integral.png",true);	
			if(this.needGetCount == 1){
				this.buildSingleNode("integral", imgUrl, text, riches);
			}else{
				this.buildMultiNode("integral", imgUrl, text, riches);
			}
			
		},
		
		
		//构建多个结点（至少展示两项：比如学分、学时）
		buildMultiNode: function(type, imgUrl, text, count ){
			var firstNode = false;
			if(this.currentOrder == 1){
				firstNode = true;
			}
			
			var url = this.typeUrl[type];
			if(type == "integral"){
				if(this.integralShowProp == "fdTotalScore"){
					url += "#path=0";
				}else{
					url += "#path=1";
				}
			}
			
			var layoutListNumberItem = new LayoutListNumberItem({
				type: type,
				imgUrl: imgUrl,
				url: url,
				text: text,
				count: count,
				firstNode: firstNode,
				integralShowProp: this.integralShowProp,
			});
			layoutListNumberItem.placeAt(this.domNode);
		},
		
		//构建单个结点（单独展示学分、学时或者积分）
		buildSingleNode: function(type, imgUrl, text, count){			
			
			var containerNode = domConstruct.create("div",{
				className: "kmsLserviceSingleNode",
			},this.domNode);
			
			var self = this;
			var url = this.typeUrl[type];
			if(type == "integral"){
				if(this.integralShowProp == "fdTotalScore"){
					url += "#path=0";
				}else{
					url += "#path=1";
				}
			}
			on(containerNode,"click",function(e){
				var url = typeUrl[type];
				if(url){
					adapter.open(url);
				}
			});
			
			domConstruct.create("img",{
				className: "kmsLserviceSingleNodeImg",
				src: imgUrl,
			},containerNode);
			domConstruct.create("div",{
				className: "kmsLserviceSingleNodeText",
				innerHTML: text,
			},containerNode);
			domConstruct.create("div",{
				className: "kmsLserviceSingleNodeCount",
				innerHTML: count,
			},containerNode);
		},
		
		//构建占位图
		buildSeizeImg: function(){
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/rightSeizeImg.png",true);	
			domConstruct.create("div",{
				className: "kmsLserviceSeizeNode",
			},this.domNode);
			var containerNode = domConstruct.create("div",{
				className: "kmsLserviceSeizeNodeRight",
			},this.domNode);
			domConstruct.create("div",{
				className: "kmsLserviceSeizeNodeRightImg",
				style: "background:url(" + imgUrl + ") 100% 100% no-repeat;background-size: cover;",
			},containerNode);			
		},
		
	});
});