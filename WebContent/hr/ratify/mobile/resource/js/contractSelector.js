define([ "dojo/_base/declare", "dojox/mobile/_ItemBase", "dojo/_base/lang", 
         "dojo/_base/array", "dojo/_base/config", "dojo/window", "dojo/dom", "dojo/_base/array",
         "dojo/dom-style", "dojo/dom-attr", "dojo/dom-class", "dojo/dom-construct", "dojo/query", "dojo/topic", 
         "dojo/on", "dojo/request", "dojo/touch", "mui/dialog/Tip", "./item/contractSelectorListItem","dijit/registry"], 
	
	function(declare, ItemBase, lang, array, config, win, dom, array, 
			domStyle, domAttr, domClass, domCtr,  query, topic, on, request, touch, Tip,
			contractSelectorListItem, registry) {
	
	return declare("hr.ratify.mobile.js.contractSelector", [ ItemBase ], {

		key: '',
		
		itemRenderer: contractSelectorListItem,
		
		TYPE_CERT: 0,
		TYPE_CAT: 1,
		
		isMul: false,
		
		catList: [],
		certList: [],
		
		selectedCerts: {},
		
		certDataUrl: 'hr/staff/hr_staff_person_experience/contract/hrStaffPersonExperienceContract.do?method=list&q.fdPersonInfo={staffId}',
		
		operateType: '',
		
		postCreate: function(){
			var ctx = this;
			ctx.inherited(arguments);
			ctx.initialize();
		},
		
		initialize: function(){
			var ctx = this;
			ctx.catList = [];
			
			ctx.selectedCerts = {};
			topic.publish('km/certificate/selectedcert/get', function(selectedCerts) {
				array.forEach(selectedCerts, function(cert) {
					ctx.selectedCerts[cert.fdId] = true;
				});
			});
			
			ctx.title = dom.byId('certSelectorTitle');
			ctx.preBtn = dom.byId('certSelectorPreBtn');
			ctx.cancelBtn = dom.byId('certSelectorCalBtn');
			
			ctx.bindEvents();
			
			ctx.loadData();
			
		},
		
		bindEvents: function(){
			var ctx = this;
			on(ctx.cancelBtn, touch.press, function(e){
				topic.publish('/mui/category/cancel', {
					key: '_certSelect'
				});
			});
			
			on(ctx.preBtn, touch.press, function(e){
				ctx.catList.pop();
				if(ctx.catList.length < 1){
					domStyle.set(ctx.preBtn, 'display', 'none');	
					ctx.title.innerHTML = '请选择';
					ctx.loadData();
				}else {
					var t = ctx.catList[ctx.catList.length - 1];
					ctx.title.innerHTML = t.text;
					ctx.loadData(t.id);
				}
				
				
			});
			
			on(ctx.domNode, on.selector('.muiCateItem', 'click'), function(e){
				
				e.stopPropagation();
				e.cancelBubble = true;
				e.preventDefault();
				e.returnValue = false;
				
				var node = query(e.target).closest(".muiCateItem")[0];
				if(node) {
					var id = domAttr.get(node, 'data-id');
					var text = domAttr.get(node, 'data-text');
					var begin = domAttr.get(node, 'data-begin');
					var end = domAttr.get(node, 'data-end');
					var remark = domAttr.get(node, 'data-remark');
					var fdBeginDate = registry.byId('fdBeginDate');
					if(fdBeginDate)
						fdBeginDate._setValueAttr(begin);

					var fdRemark = registry.byId('fdRemark');
					if(fdRemark)
						fdRemark._setValueAttr(remark);
					var operateType = domAttr.get(node, 'data-operate');
					
					if(operateType == 'change'){
						if("长期有效" == end){
							var fdChangeIsLongtermContract = query('[name="fdChangeIsLongtermContract"]')[0];
							var changeIsLongtermContract = query('[id="changeIsLongtermContract"]')[0];
							domAttr.set(fdChangeIsLongtermContract, 'value', "true");
							domAttr.set(changeIsLongtermContract, 'innerHTML', end);
							var fdEndDate = registry.byId('fdEndDate');
							if(fdEndDate)
								fdEndDate._setValueAttr("");
						}else{
							var fdChangeIsLongtermContract = query('[name="fdChangeIsLongtermContract"]')[0];
							var changeIsLongtermContract = query('[id="changeIsLongtermContract"]')[0];
							domAttr.set(fdChangeIsLongtermContract, 'value', "");
							domAttr.set(changeIsLongtermContract, 'innerHTML', "");
							var fdEndDate = registry.byId('fdEndDate');
							if(fdEndDate)
								fdEndDate._setValueAttr(end);
						}
						var fdContractId = query('[name="fdContractId"]')[0];
						var fdContractName = query('[name="fdContractName"]')[0];
						domAttr.set(fdContractId, 'value', id);
						domAttr.set(fdContractName, 'value', text);
					}else if(operateType == 'remove'){
						if("长期有效" == end){
							var fdIsLongtermContract = query('[name="fdIsLongtermContract"]')[0];
							var fdContractName = query('[id="isLongtermContract"]')[0];
							domAttr.set(fdIsLongtermContract, 'value', "true");
							domAttr.set(fdContractName, 'innerHTML', end);
							var fdEndDate = registry.byId('fdEndDate');
							if(fdEndDate)
								fdEndDate._setValueAttr("");
						}else{
							var fdIsLongtermContract = query('[name="fdIsLongtermContract"]')[0];
							var fdContractName = query('[id="isLongtermContract"]')[0];
							domAttr.set(fdIsLongtermContract, 'value', "");
							domAttr.set(fdContractName, 'innerHTML', "");
							var fdEndDate = registry.byId('fdEndDate');
							if(fdEndDate)
								fdEndDate._setValueAttr(end);
						}
						var fdRemoveContractId = query('[name="fdRemoveContractId"]')[0];
						var fdRemoveContractName = query('[name="fdRemoveContractName"]')[0];
						domAttr.set(fdRemoveContractId, 'value', id);
						domAttr.set(fdRemoveContractName, 'value', text);
					}
					
					domClass.add(node,'muiCateSeled');
					topic.publish('km/certificate/certselector/result');
					topic.publish('/mui/category/cancel', {
						key: '_certSelect'
					});
				}
				
			});
			
			topic.subscribe('/mui/category/cancelSelected', function(_ctx, item) {
				
				if(ctx.key != _ctx.key) {
					return;
				}
				
				var node = query('.muiCateItem[data-id="' + item.fdId + '"]');
				ctx.selectedCerts[item.fdId] = false;
				if(node[0]) {
					domClass.remove(node[0], 'muiCateSeled');
				}
				
			});
			
			topic.subscribe('/mui/category/submit', function(_ctx, items) {
				
				if(ctx.key != _ctx.key) {
					return;
				}
				topic.publish('km/certificate/certselector/result', items);
			});
			
		},
		
		loadData: function(catId) {
			var ctx = this;
			domCtr.empty(ctx.domNode);
			var staffId = "";
			if(this.operateType == 'change'){
				staffId = query('[name="fdChangeStaffId"]')[0].value;
			}else if(this.operateType == 'remove'){
				staffId = query('[name="fdRemoveStaffId"]')[0].value;
			}
			request.post(config.baseUrl + lang.replace(ctx.certDataUrl, {
				staffId: staffId || ''
			}), {
				handleAs: 'json',
				data: {
					rowsize: 999999
				}
			}).then(function(res){
				var t = [];
				array.forEach(res.datas, function(d){
					var _t = {};
					array.forEach(d, function(_d){
						_t[_d.col] = _d.value;
					});
					t.push({
						fdId: _t.fdId,
						text: _t.fdName,
						fdBeginDate: _t.fdBeginDate,
						fdEndDate: _t.fdEndDate,
						remark: _t.remark, 
						cert: _t
					});
				});
				
				ctx.certList = t;
				ctx.renderData(t);
			});
			
		},
		
		renderData: function(data) {
			var ctx = this;
			array.forEach(data, function(item, idx){
				var certItem = new ctx.itemRenderer(lang.mixin(item, {
					isMul: ctx.isMul,
					operateType : ctx.operateType
				}));
				
				if(ctx.selectedCerts[item.fdId]) {
					domClass.add(certItem.domNode, 'muiCateSeled');
				}
				certItem.placeAt(ctx.domNode);
			});
			
		}
		
	});
		
})