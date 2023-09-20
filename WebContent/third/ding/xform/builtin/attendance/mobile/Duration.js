define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"mui/form/Input",
	"dojo/query",
],function(declare, domConstruct, Input, query){
	
	var Duration = declare("third.ding.xform.builtin.attendance.mobile.Duration", [Input], {
		
		placeholder : "自动计算",
		
		buildEdit : function () {
			this.inputNode = domConstruct.create(
			"input",
			{
				name: this.name,
				className: this.inputClass,
				type: "text",
				readOnly : true
			}, 
			this.domNode);
			this.set("subject", "时长");
			this.buildXFormStyle(this.inputNode);
			// 控件统一把输入的node设置为contentNode
			this.contentNode = this.inputNode;
			this.showRight(false);
			
			// 画【查询明细】 由于时长功能，钉钉方还未提供，暂注释
			//this.drawDurationDetail();
		},
		
		// 查询明细
		drawDurationDetail : function(){
			// 增加一行空行
			var tdDom = this._createTdInNewTr();
			var descDom = domConstruct.create("span", {
				"classname" : "ding_duration_detail_desc"
			}, tdDom);
			descDom.innerHTML = "根据排班自动计算时长";
			
			this._createDetailBtnInTd(tdDom);
		},
		
		buildView: function () {
			this.inherited(arguments);
			this.inputNode = domConstruct.create('input', {
				name : this.name,
				type : 'hidden',
				value : this.value
			},this.domNode);
			// 钉钉方还未提供，暂注释
			//var tdDom = this._createTdInNewTr();
			//this._createDetailBtnInTd(tdDom);
		},
		
		// 创建新行，返回新行的td
		_createTdInNewTr : function(){
			var trDom = query(this.domNode).closest("tr");
			this.detailTrDom = domConstruct.create("tr", {
				"classname" : "ding_duration_detail_desc_wrap"
			}, trDom[0], "after");
			return domConstruct.create("td", {
				"colspan" : "2"
			}, this.detailTrDom);
		},
		
		// 创建【查看明细】的按钮
		_createDetailBtnInTd : function(tdDom){
			var viewDetailBtn = domConstruct.create("div", {
				"classname" : "ding_duration_detail_view_btn"
			}, tdDom);
			viewDetailBtn.innerHTML = "查看明细";
			return viewDetailBtn;
		}
	});
	
	return Duration;
})