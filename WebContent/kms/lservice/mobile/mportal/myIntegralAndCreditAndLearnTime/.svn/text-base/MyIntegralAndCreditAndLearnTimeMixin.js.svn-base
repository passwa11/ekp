define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/dom-style",
	"dojo/_base/lang",
	"dijit/_WidgetBase",
	"dojo/_base/array",
	"dojo/request",
	"mui/util",
	dojoConfig.baseUrl + "kms/lservice/mobile/mportal/myIntegralAndCreditAndLearnTime/CheckMoudleExistInfo.jsp"
	], function(declare, domConstruct, domClass, domStyle, lang, _WidgetBase, array, request, util, moduleExistInfo) {
	
	return  declare("kms.lservice.myIntegralAndCreditAndLearnTimeMixin",[_WidgetBase], {		
		
		//需要获取的数量
		needGetCount: 0,
		
		//完成的数量
		completeCount: 0,
		
		//ajax获取结果
		ajaxResult: {},
		
		//初始化数量和获取详情
		initCountAndGetInfo: function(){
			//需要进行网络请求获取的对象
			var needGetObj = {
				"credit": true,
				"learnTime": true,
				"integral": true,
			};
			
			//判断模块是否存在，不存在就不必获取和渲染
			if(!moduleExistInfo.integral){
				needGetObj["integral"] = false;
			}
			if(!moduleExistInfo.loperation){
				needGetObj["learnTime"] = false;
			}
			if(!moduleExistInfo.credit){
				needGetObj["credit"] = false;
			}
			
			//判断卡片设置是否开启相应的展示，未开启则不必获取和渲染
			if(!this.showIntegral){
				needGetObj["integral"] = false;
			}
			if(!this.showLearnTime){
				needGetObj["learnTime"] = false;
			}
			if(!this.showCredit){
				needGetObj["credit"] = false;
			}
			
			
			this.needGetObj = needGetObj;
			
			//需要获取数量
			var needGetCount = 0;			
			if(needGetObj["credit"]) needGetCount++;
			if(needGetObj["learnTime"]) needGetCount++;
			if(needGetObj["integral"]) needGetCount++;			
			this.needGetCount = needGetCount;
			
			//学分
			var getCreditUrl = util.formatUrl("/kms/credit/kms_credit_sum_personal/kmsCreditSumPersonal.do?method=getUserSum",true);
			//学时
			var getLearnTimeUrl = util.formatUrl("/kms/loperation/kms_loperation_stu_total/kmsLoperationStuTotal.do?method=getUserLearnTime",true);
			//积分
			var getIntegralUrl = util.formatUrl("/kms/integral/kms_integral_portlet/kmsIntegralPortlet.do?method=getCurrentUserIntegral",true);
			
			//发送网络请求
			if(needGetObj["credit"]) this.ajaxGet(getCreditUrl, "credit");
			if(needGetObj["learnTime"]) this.ajaxGet(getLearnTimeUrl, "learnTime");
			if(needGetObj["integral"]) this.ajaxGet(getIntegralUrl, "integral");
			
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

		
	});
});