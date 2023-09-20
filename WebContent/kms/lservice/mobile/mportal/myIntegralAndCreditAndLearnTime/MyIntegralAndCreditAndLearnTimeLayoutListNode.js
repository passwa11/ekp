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
	"./MyIntegralAndCreditAndLearnTimeMixin",
	"./item/LayoutListNodeItem",
	"mui/i18n/i18n!kms-lservice:mobile"
	], function(declare, domConstruct, domClass, domStyle, lang, _WidgetBase, _Container, _Contained,  array, request, on, util, 
			kmsLserviceIntegralCreditTimeMixin, LayoutListNodeItem, msg) {
	
	return  declare("kms.lservice.myIntegralAndCreditAndLearnTimeListNode",[_WidgetBase, _Container, _Contained, kmsLserviceIntegralCreditTimeMixin], {
		
		//展示积分
		showIntegral: true,
		
		//积分展示的字段
		integralShowProp: "",
		
		//展示学分
		showCredit: true,
		
		//展示学时
		showLearnTime: true,	
		
		//需要获取的对象,用来判断是否要进行渲染
		needGetObj: { 
			credit: true,
			integral: true,
			learnTime: true,
		},
		
		//需要获取的数量
		needGetCount: 0,
		
		//完成的数量
		completeCount: 0,
		
		//ajax获取结果
		ajaxResult: {},
		
		baseClass: "kmsLserviceListNode",
		
		typeUrl: {
			"credit": util.formatUrl("/kms/credit/mobile",true),
			"learnTime": util.formatUrl("/kms/loperation/mobile/myLearnTime/index.jsp",true),
			"integral": util.formatUrl("/kms/integral/mobile",true),
		},
		
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
			this.currentOrder = 0;
			this.initCountAndGetInfo();
		},		
		
		//构建内容
		build: function(){					
			if(this.needGetObj["credit"]) this.buildCredit();
			if(this.needGetObj["learnTime"]) this.buildTime();
			if(this.needGetObj["integral"]) this.buildIntegral();
		},

		
		//构建学分
		buildCredit: function(){	
			this.currentOrder++;			
			var info = this.ajaxResult["credit"];
			var creditSum = "";
			if(info && info.data){
				creditSum = info.data.fdCreditSum || 0;
			}
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/credit-small.png",true);	
			this.buildNode("credit", imgUrl, this.langInfo["credit"], creditSum, this.langInfo["unitScore"]);
			
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
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/learnTime-small.png",true);				
			this.buildNode("learnTime", imgUrl, this.langInfo["learnTime"], time, this.langInfo["unitHour"]);
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
			var imgUrl = util.formatUrl("/kms/lservice/mobile/style/img/integral-small.png",true);	
			this.buildNode("integral", imgUrl, text , riches, this.langInfo["unitScore"]);
		},
		
		buildNode: function(type, imgUrl, text, count, unit){
			var showHr = false;
			//分割线
			if(this.currentOrder > 1){
				domConstruct.create("div",{
					className: "kmsLserviceListNodeHr"
				},this.domNode);
			}
				
			var url = this.typeUrl[type];
			if(type == "integral"){
				if(this.integralShowProp == "fdTotalScore"){
					url += "#path=0";
				}else{
					url += "#path=1";
				}
			}
			
			var layoutListNodeItem = new LayoutListNodeItem({
				type: type,
				imgUrl: imgUrl,
				url: url,
				text: text,
				count: count,
				unit: unit,
				showHr: showHr,			
			});
			layoutListNodeItem.placeAt(this.domNode);
		}
		
	});
});