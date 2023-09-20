define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/topic",
  "mui/util",
  "mui/openProxyMixin",
  "dojox/mobile/_ItemBase",
  "dojo/dom-class",
  "dojo/_base/lang",
  "dojo/html",
  "mui/util",
  "dojo/request",
  "mui/dialog/Dialog",
  "dojo/query",
  "dojo/dom-style",
  "dijit/registry",
  "mui/form/validate/Validation",
  "mui/dialog/Tip",
  "mui/i18n/i18n!sys-lbpmservice:lbpmNode"
], function(declare, domConstruct, topic, util, openProxyMixin, ItemBase, domClass, lang, html, util, request, Dialog, query, domStyle,registry,Validation,Tip, msg1) {
	return declare("sys.lbpmservice.mobile.lbpmSummaryApproval.LbpmSummaryItemMixin", [ItemBase,openProxyMixin], {
	  tag:"li",
	  
	  templURL:"/sys/lbpmservice/mobile/lbpm_summary_approval/dialog.jsp",
	  
	  baseClass:"muiLbpmSummaryItem",
	  
	  buildRendering:function(){
		  this.inherited(arguments);
		  this.domNode = this.containerNode = domConstruct.create(this.tag);
		  domClass.add(this.domNode, this.baseClass);
		  this.buildContent();
	  },
	  
	  buildContent : function(){
		  this.contentNode = domConstruct.create("div", {className:"muiItemContent"}, this.domNode);
		  this.infosNode = domConstruct.create("div", {className:"muiItemInfos"}, this.contentNode);
		  //选择框部分
		  this.selectArea = domConstruct.create(
	    	"div",
	        {
	           className: "muiItemSelArea"
	        },
	        this.infosNode,
	      ) //用于占位
	      this.selectNode = domConstruct.create(
	    	"div",
	    	{
	    		className: "muiItemSel"
	    	},
	    	this.selectArea
	      )
		  //内容部分
		  this.infoNode = domConstruct.create("div", {className:"muiItemInfo"}, this.infosNode);
		  this.connect(this.infoNode, "click", "_viewDoc");
		  //基础内容
		  var html = "<div><div class='muiItemTitle'>"+this.docSubject+"</div><div class='muiItemCreateTime'>"+this.docCreateTime+"</div></div>" +
		  		"<div class='muiItemCreator'><div class='muiItemCreatorImg'><img src='"+Com_Parameter.ContextPath+"sys/person/image.jsp?personId="+this.docCreatorId+"&size=90'></div>" +
		  		"<div class='muiItemCreatorName'>"+this.docCreatorName+"</div></div>";
		  this.baseInfoNode = domConstruct.create("div", {className:"muiItemBaseInfo", innerHTML:html}, this.infoNode);
		  //摘要部分
		  this.summaryNode = domConstruct.create("div", {className:"muiItemSummaryInfo"}, this.infoNode);
		  //基础字段
		  var propertyNode = domConstruct.create("div", {className:"muiItemSummaryBase"}, this.summaryNode);
		  if(this.summary && this.summary.properties){
			  for(var i=0; i<this.summary.properties.length; i++){
				  var property = this.summary.properties[i];
				  var itemSummaryPropertyNode = domConstruct.create("div", {className:"muiItemSummaryProperty"}, propertyNode);
				  var propertyHtml = "<div class='label'>"+property.label+"</div><div class='hide_value' style='display:none'>"+property.value+"</div>";
				  var promptHtml = "";
				  if(property.value + ""){
					  promptHtml = "<div class='prompt' data-dojo-type='sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryPrompt' data-dojo-props='content:\""+property.value+"\"'></div>";
			  	  }	
				  propertyHtml = propertyHtml + promptHtml;
				  query(itemSummaryPropertyNode).html(propertyHtml,{parseContent:true});
			  }
		  }
		  //表格字段
		  this.buildTable();
		  this.btnsNode = domConstruct.create("div", {className:"muiItemBtns"}, this.contentNode);
		  if(this.btn.isFastApprove){
			  this.passBtnNode = domConstruct.create("div", {className:"muiItemPassBtn muiItemBtn", innerHTML:'通过'}, this.btnsNode);
			  this.connect(this.passBtnNode, "click", "_handlerPass");
		  }
		  if(this.btn.isFastReject){
			  this.rejectBtnNode = domConstruct.create("div", {className:"muiItemRejectBtn muiItemBtn", innerHTML:'<div class="muiItemSplitBtn">|</div>驳回'}, this.btnsNode);
			  this.connect(this.rejectBtnNode, "click", "_handlerReject");
		  }
		  //只有一个按钮时要铺满
		  if(this.btn.isFastApprove && !this.btn.isFastReject){
			  domClass.add(this.passBtnNode, "over");
		  }
		  
		  //绑定dom事件
	      this.connect(this.selectArea, "click", "_itemSelected");
	  },
	  
	  buildTable : function(){
		  if(!this.summary || !this.summary.tables){
			  return;
		  }
		  var tableHtml = "";
		  for(var j=0; j<this.summary.tables.length; j++){
				var headers = this.summary.tables[j].headers;
				var rows = this.summary.tables[j].rows;
				var otherRows = this.summary.tables[j].otherRows;
				tableHtml += "<table class='tb_normal'><tr class='tr_normal_title'>";
				if(headers.length > 0){
					tableHtml += "<td style='width:48px;text-align: left;' class='td_normal_title'>序号</td>";
				}
				if(headers.length > 3){
					for(var k=0; k<3; k++){
						tableHtml += '<td style="text-align: left;" class=\'td_normal_title\'>'+headers[k]+'</td>';
					}
					tableHtml += '<td style="text-align: left;" class=\'td_normal_title\'>...</td>';
				}else{
					for(var k=0; k<headers.length; k++){
						tableHtml += '<td  style="text-align: left;" class=\'td_normal_title\'>'+headers[k]+'</td>';
					}
				}
				tableHtml += '</tr>';
				var rowLen = rows.length;
				var otherRowLen = otherRows.length;
				var isOverRowNum = false;
				if(rowLen > 5){
					rowLen = 5;
					isOverRowNum = true;
				}else if(rowLen + otherRowLen > 5){
					rowLen = 4;
					otherRowLen = 1;
					isOverRowNum = true;
				}
				var isOverCellNum = false;
				for(var k=0; k<rowLen; k++){
					var len = rows.length;
					var row = rows[k];
					if((isOverRowNum && rowLen == 4 && k == rowLen-1) || (isOverRowNum && rowLen == 5 && k==rowLen-2)){
						tableHtml += '<tr><td>...</td>';
							
						if(row.length > 3){
							for(var g=0; g<4; g++){
								tableHtml += '<td class=\'td_normal_content\'><div>...</div></td>';
							}
						}else{
							for(var g=0; g<row.length; g++){
								tableHtml += '<td class=\'td_normal_content\'><div>...</div></td>';
							}
						}
						tableHtml += '</tr>';
					}else{
						if(isOverRowNum && rowLen == 5 && k==rowLen-1){
							row = rows[len-1];
							tableHtml += '<tr><td>'+len+'</td>';
								
							if(row.length > 3){
								for(var g=0; g<3; g++){
									if(row[g]+""){
										tableHtml += '<td class=\'td_normal_content\'><div class="prompt" data-dojo-type="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryPrompt" data-dojo-props="content:\''+row[g]+'\'"></div></td>';
									}
								}
								tableHtml += '<td class=\'td_normal_content\'><div>...</div></td>';
							}else{
								for(var g=0; g<row.length; g++){
									if(row[g]+""){
										tableHtml += '<td class=\'td_normal_content\'><div class="prompt" data-dojo-type="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryPrompt" data-dojo-props="content:\''+row[g]+'\'"></div></td>';
									}
								}
							}
							tableHtml += '</tr>';
						}else{
							tableHtml += '<tr><td>'+(k+1)+'</td>';
							if(row.length > 3){
								for(var g=0; g<3; g++){
									if(row[g]+""){
										tableHtml += '<td class=\'td_normal_content\'><div class="prompt" data-dojo-type="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryPrompt" data-dojo-props="content:\''+row[g]+'\'"></div></td>';
									}
								}
								tableHtml += '<td class=\'td_normal_content\'><div>...</div></td>';
							}else{
								for(var g=0; g<row.length; g++){
									if(row[g]+""){
										tableHtml += '<td class=\'td_normal_content\'><div class="prompt" data-dojo-type="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryPrompt" data-dojo-props="content:\''+row[g]+'\'"></div></td>';
									}
								}
							}
							tableHtml += '</tr>';
						}
					}
				}
				for(var k=0; k<otherRowLen; k++){
					var row;
					if(isOverRowNum){
						var len = otherRows.length;
						row = otherRows[len-1];
					}else{
						row = otherRows[k];
					}
					tableHtml += '<tr>';
					if(row.length > 0){
						tableHtml += '<td class=\'td_normal_content\'></td>';
					}
					if(row.length > 3){
						for(var g=0; g<3; g++){
							if(row[g]+""){
								tableHtml += '<td class=\'td_normal_content\'><div class="prompt" data-dojo-type="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryPrompt" data-dojo-props="content:\''+row[g]+'\'"></div></td>';
							}
						}
						tableHtml += '<td class=\'td_normal_content\'><div>...</div></td>';
					}else{
						for(var g=0; g<row.length; g++){
							if(row[g]+""){
								tableHtml += '<td class=\'td_normal_content\'><div class="prompt" data-dojo-type="sys/lbpmservice/mobile/lbpm_summary_approval/LbpmSummaryPrompt" data-dojo-props="content:\''+row[g]+'\'"></div></td>';
							}
						}
					}
					tableHtml += '</tr>';
				}
		  }
		  this.tableNode = domConstruct.create("div", {className:"muiItemSummaryTable"}, this.summaryNode);
		  query(this.tableNode).html(tableHtml,{parseContent:true});
	  },
	  
	  isShowTip:function(s,dom){
		  var contentWidth = self.getContentWidth(s);
		  var width = dom.offsetWidth;
		  if(contentWidth > parseInt(width)/7){
			  
		  }
	  },
	  
	  getContentWidth : function(s){
			var len = 0;
			for (var i=0; i<s.length; i++) { 
				var c = s.charCodeAt(i); 
				 if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
				       len++;
				     }  else {
				         len+=2;
				     } 
			}
			return len;
	  },
	  
	  postCreate : function(){
		  this.subscribe("/mui/lbpmSummary/selected", "_itemSelected");
		  //this.subscribe("mui/form/validationInitFinish","_addValidate");
		  this.subscribe("/mui/lbpmSummary/destoryDialog", "_destoryDialog");
		  this.subscribe("/mui/lbpmSummary/approve","_approveOne")
	  },
	  
	  startup : function() {					
		  this.inherited(arguments);
	  },
	  
	  _setLabelAttr: function(text){
		if(text)
			this._set("label", text);
	  },
	  
	  _viewDoc:function(){
		  location.href = util.formatUrl("/"+this.url);
	  },
	  
	  _itemSelected: function(data){
		  var selectionWgt = registry.byId("lbpmSummarySelection");
		  var isAll = false;
		  if(data && data.oprType){
			  isAll = true;
		  }
		  
		  if ((isAll && data.oprType == 'unselected') || (!isAll && this.checkedIcon)) {
              if(this.checkedIcon != null){
            	  domClass.remove(this.selectNode, "muiItemSeled");
                  domConstruct.destroy(this.checkedIcon);
                  this.checkedIcon = null;
                  var index = selectionWgt.selArr.indexOf(this.fdId);
                  selectionWgt.selArr.splice(index,1);
                  selectionWgt.selItemArr.splice(index,1);
              }
    	 }else{
    		 if(selectionWgt.selArr.indexOf(this.fdId) == -1){
	    		 this.checkedIcon = domConstruct.create(
		            "i",
		            {
		              className: "mui mui-checked muiItemSelected"
		            },
		            this.selectNode
		        )
		        domClass.add(this.selectNode, "muiItemSeled")
	        	selectionWgt.selArr.push(this.fdId);
	    		selectionWgt.selItemArr.push(this);
    		 }
    	 }
	  },
	  
	  _addValidate:function(wgt){
		  var validates = {
			  'fdUsageContentNoLbpm':{//意见必填校验（没有流程的情况下，目前只支持处理人通过和驳回两种情况）
					error:msg1["lbpmNode.mustSignYourSuggestion"],
					test:function(v){
						if((window.currentOperationType == 'handler_pass' && window.isPassContentRequired) || 
								(window.currentOperationType == 'handler_refuse' && window.isRefuseContentRequired)){
							 var fdUsageContent = query("[name=fdUsageContent]")[0];
							 if(fdUsageContent && fdUsageContent.value){
								 return true;
							 }else{
								 return false;
							 }
						}
						return true;
					}
				},
				'usageContentMaxLenNoLbpm':{//意见长度校验（没有流程的情况下）
					error:msg1["lbpmNode.createDraft.opinion.maxLength"].replace(/\{name\}/, msg1["lbpmNode.createDraft.opinion"]).replace(/\{maxLength\}/, 4000),
					test:function(v){
						var fdUsageContent=query("[name=fdUsageContent]")[0];
						if (fdUsageContent != null && fdUsageContent.value != "") {
							var contentVal = fdUsageContent.value || "";
							var newvalue = contentVal.replace(/[^\x00-\xff]/g, "***");
							if (newvalue.length > 4000) {
								return false;
							}
						}
						return true;
					}
				}
		  };
		  for (var type in validates) {
			wgt._validation.addValidator(type, validates[type].error, validates[type].test);
		  }
	  },
	  
	  //解析模板文件
	  _parser: function(){
		  var _self = this;
		  var url = util.urlResolver(this.templURL, this);
		  url = util.formatUrl(url);
		  url = "dojo/text!" + url;
		  if(registry.byId("fdUsageContent")){
			  registry.byId("fdUsageContent").destroy();
		  }
		  if(registry.byId("lbpmSummaryTabBar")){
			  registry.byId("lbpmSummaryTabBar").destroy(); 
		  }
		  
		  //创建审批弹窗的div
		  this.dialogContainerDiv = domConstruct.create(
	          "div",
	          {className: "muiApproveDiaglogContainer "}
	      )
		  
		  require([url], function(tmplStr) {
			  var dhs = new html._ContentSetter({
		          node: _self.dialogContainerDiv,
		          parseContent: true,
		          cleanContent: true,
		          onBegin: function() {
		            this.content = lang.replace(this.content, {categroy: _self})
		            this.inherited("onBegin", arguments)
		          }
		        })

		        dhs.set(tmplStr)
		        dhs.parseDeferred.then(function(results) {
		          _self.parseResults = results;
		          //构建校验对象
		          	var usageWdt = registry.byId("fdUsageContent");
		          	usageWdt._validation = new Validation();
		          	_self._addValidate(usageWdt);
		        })
		        dhs.tearDown() 
		  });
	  },
	  
	  initOperationDefaultUsage:function(){
		  if(window.initOperationDefaultUsage){//已经初始化过就不再初始化
			  return;
		  }
		  var _self = this;
		  var href = location.href;	
	      var fdNodeFactId = util.getUrlParameter(href, "fdNodeFactId");
	      var fdProcessId = this.fdId;
		  var url = "/sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=getOperationDefaultUsage&fdProcessId="+fdProcessId+"&fdNodeFactId="+fdNodeFactId;
		  url = util.formatUrl(url);
		  request.get(url,{handleAs:'json',sync:true}).then(
			function(data){
				//成功后回调
				if(data.length > 0){
					window.initOperationDefaultUsage = true;
					if(data[0].defaultUsageContent!=null){
						window.defaultUsageContent = unescape(data[0].defaultUsageContent).replace(/(\r)*\n/g,"\\n");
					}
					if(data[0].defaultUsageContent_refuse!=null){
						window.defaultUsageContent_refuse = unescape(data[0].defaultUsageContent_refuse).replace(/(\r)*\n/g,"\\n");
					}
					if(data[0].isPassContentRequired!=null){
						window.isPassContentRequired = data[0].isPassContentRequired;
					}
					if(data[0].isRefuseContentRequired!=null){
						window.isRefuseContentRequired = data[0].isRefuseContentRequired;
					}
				}
			},function(error){
		        //错误回调
				console.log(error);
			}
		  )
	  },
	  
	  showDialog:function(title){
		  this._parser();
		  this.dialog = Dialog.element({
			title:title || '',
			canClose : false,
			element : this.dialogContainerDiv,
			buttons : [],
			position:'bottom',
			'scrollable' : false,
			'parseable' : false,
			showClass : 'muiApproveDialog',
			callback : lang.hitch(this, function(win,evt) {
				domStyle.set(query("body")[0],{
					"overflow-y":""
				})
				this.dialog = null;
			}),
			onDrawed:lang.hitch(this, function(evt) {
				domStyle.set(query("body")[0],{
					"overflow-y":"hidden"
				})
				var contentHeight = document.documentElement.clientHeight*0.9;
				 if(evt.privateHeight){
					 contentHeight=evt.privateHeight
				 }
				 //减去头部高度
				 if(evt.divNode){
					contentHeight = contentHeight - evt.divNode.offsetHeight;
				 }
				 //减去按钮栏高度
				 if(evt.buttonsNode){
					contentHeight = contentHeight - evt.buttonsNode.offsetHeight;
				 }
				 domStyle.set(evt.contentNode, {
					   'max-height' : contentHeight + 'px',
					   "overflow-x":"hidden"
				 });
				 evt.scrollViewNode = evt.contentNode;
				 if(registry.byId("lbpmSummaryTabBar")){
					 registry.byId("lbpmSummaryTabBar").resize();
				 }
			})
		});  
	  },
	  
	  _destoryDialog:function(){
		  if (this.dialog && this.dialog.callback){
			 this.dialog.hide();
			 this.dialog.callback(window, this.dialog);
		  }
	  },
	  
	  //通过操作
	  _handlerPass: function(){
		  window.currentOperationType = "handler_pass";
		  this.initOperationDefaultUsage();
		  this.fdUsageContentValue = window.defaultUsageContent;
		  this.processInfoClassName = "active";
		  this.processName = this.docSubject;
		  this.processId = this.fdId;
		  this.opType = "pass";
		  
		  this.showDialog("通过");
	  },
	  //驳回操作
	  _handlerReject:function(){
		  window.currentOperationType = "handler_refuse";
		  this.initOperationDefaultUsage();
		  
		  this.fdUsageContentValue = window.defaultUsageContent_refuse;
		  this.processInfoClassName = "active";
		  this.processName = this.docSubject;
		  this.processId = this.fdId;
		  this.opType = "refuse";
		  
		  this.showDialog("驳回");
	  },
	  
	  _approveOne:function(data){
		  var processId = data.processId;
		  if(processId != this.fdId){
			  return;
		  }
		  var opType = data.opType;
		  var _self = this;
		  var url = "/sys/lbpmservice/support/actions/LbpmSummaryApproval.do?method=approveOne";
		  url = util.formatUrl(url);
		  var params = {
			"processId" : this.fdId,
			"opType" : opType,
			"usageContent":query("[name='fdUsageContent']")[0].value || ""
		  };
		  request.post(url,{data:params,handleAs:'json'}).then(
		  function(data){
		     //成功后回调
			  var code = data.code;
			  if(code == 1){//处理成功
				 Tip["success"]({text:"操作成功",time:400});
				 _self.defer(function(){
					 _self._destoryDialog();
					 topic.publish("/mui/lbpmSummary/reloadList");
					 topic.publish("/mui/lbpmSummary/selection/reset")
				 },500);
			  }
		  },function(error){
		     //错误回调
			  Tip["fail"]({text:"操作失败"});
		  });
	  }
  })
})
