define(["dojo/_base/declare", "mui/tabbar/TabBarButton", "dojo/dom-construct",  "dojo/dom-class", "dijit/registry", "dojo/_base/lang", 
        "dojo/topic", "dojo/dom", "dojo/query", "mui/device/adapter", "mui/dialog/Dialog", "mui/i18n/i18n!sys-mobile", "mui/i18n/i18n!sys-iassister"], function(
       	declare, TabBarButton, domConstruct, domClass, registry, lang, topic, dom, query, adapter, Dialog, msg1, msg2) {

		return declare("sys.iassister.checkButton", [TabBarButton], {

				buildRendering:function(){
					this.inherited(arguments);
					//domClass.add(this.domNode,'muiBarSaveDraftButton');
				},
				
				rtnData : [],
				
				dialogs : [],
				
				fileList : [],
				
				paramsHere : {
					isInit: false,
					checkUrl: "/sys/iassister/sys_iassister_check/sysIassisterCheck.do",
					templateUrl: "/sys/iassister/sys_iassister_template/sysIassisterTemplate.do"
				},
				
				initSelf : function(){
					this.initParams();
					this.initTemplateInfos();
				},
				
				initParams : function() {
					this.paramsHere = $.extend(this.paramsHere, this.ias_params);
					this.paramsHere.curNode = lbpm.globals.getCurrentNodeObj();
					this.paramsHere.hasCheckItems = "true" == this.ias_params.hasCheckItems;
					this.paramsHere.hasAuth = "true" == this.ias_params.hasAuth;
					this.paramsHere.ctxPath = Com_Parameter.ContextPath;
					this.paramsHere.isInit = true;
					console.log(this.paramsHere);
				},

				onClick : function() {
					if(!this.paramsHere.isInit){
						this.initSelf();
					}
					var url = this.getCheckUrl();
					console.log(url);
					this.rtnData = this.postCheck(url);
					console.log(this.rtnData);
					
					
					var title = domConstruct.create('span',{
						style:{'font-size': 'var(--muiFontSizeMS)'},
						innerHTML:msg2["mui.button.iassister"]
					}),
					date_element = this.showDatas();
					
					this.dialog = Dialog.element({
						title : title.outerHTML,
						element : date_element,
						buttons : '',
						position: 'bottom',
						parseable : false, 
						canClose : true,
						callback : null
					});
				},
				getCheckUrl : function() {
					var checkUrl = this.paramsHere.ctxPath + this.paramsHere.checkUrl;
					checkUrl = Com_SetUrlParameter(checkUrl, "method", "execCheck");
					checkUrl = Com_SetUrlParameter(checkUrl, "fdTmplateModelId", this.paramsHere.templateId);
					checkUrl = Com_SetUrlParameter(checkUrl, "fdTemplateModelName", this.paramsHere.templateModelName);
					checkUrl = Com_SetUrlParameter(checkUrl, "fdModelId", this.paramsHere.mainModelId);
					checkUrl = Com_SetUrlParameter(checkUrl, "fdModelName", this.paramsHere.mainModelName);
					checkUrl = Com_SetUrlParameter(checkUrl, "nodeId", this.paramsHere.curNode.id);
					checkUrl = Com_SetUrlParameter(checkUrl, "fdKey", this.paramsHere.fdKey);
					if ("view" == this.paramsHere.method) {
						checkUrl = Com_SetUrlParameter(checkUrl, "fdPageMode", "view");
					}
					return checkUrl;
				},
				
				initTemplateInfos : function() {
					var self = this;
					var getTemplateInfoUrl = self.paramsHere.ctxPath + self.paramsHere.templateUrl;
					getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl, "method", "getTemplateInfo");
					getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl, "templateId", self.paramsHere.templateId);
					getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl, "templateModelName", self.paramsHere.templateModelName);
					getTemplateInfoUrl = Com_SetUrlParameter(getTemplateInfoUrl, "nodeId", self.paramsHere.curNode.id);
					$.ajax({
						async: false,
						url: getTemplateInfoUrl,
						dataType: "json",
						success: function(rtn) {
							if (rtn.success) {
								self.paramsHere.mappingFieldNames = rtn.mappingFields;
								self.paramsHere.showGroups = rtn.showGroups;
							}
						}
					})
				},
				
				postCheck : function(checkUrl, trigger) {
					var rtnData = [];
					var postData = {};
					postData.paramData = JSON.stringify(this.getParamData());
					var rtn = {
						success : false
					}
					$.ajax({
						url : checkUrl,
						type : "POST",
						data : postData,
						async:false,
						success : function(result) {
							rtn = result;
						}
					})
					if (rtn.success) {
						rtnData = rtn.result;
					} else {
						console.log(rtn.error);
					}
					if ("icheck" == trigger) {
						showCheckResult(rtnData);
					}
					return rtnData;
				},
				
				getParamData : function() {
					var rtn = [];
					var self = this;
					if (self.paramsHere.mappingFieldNames) {
						for (var i = 0, len = self.paramsHere.mappingFieldNames.length; i < len; i++) {
							var fn = self.paramsHere.mappingFieldNames[i];
							var pd = {
								name: fn,
								value: ""
							}
							var domFN = fn;
							var isCustomDetail = false;
							if (fn.indexOf("fd_") > -1) {
								domFN = "extendDataFormInfo.value(" + fn + ")";
								if (fn.indexOf(".") > -1) {
									isCustomDetail = true;
								}
							}
							var pdvType = $("[name='" + domFN + "']").prop("type");
							var pdv = $("[name='" + domFN + "']").val();
							if (pdvType == 'radio') {
								pdv = $("[name='" + domFN + "']:checked").val();
							}

							if (!pdv) {
								pdv = $("[name='" + domFN + "Id']").val();
							}
							// TABLE_DL_fd_38a32713db4cfa
							if (isCustomDetail) {
								pdv = "";
								var dtid = "TABLE_DL_" + fn.split(".")[0];
								var eachFunc = function() {
									var trT = $(this).attr("type");
									if (!trT || "templateRow" == trT) {
										var nameS = "[name^='extendDataFormInfo.value("
											+ fn.split(".")[0] + "']"
										pdv += $(this).find(nameS).val() + "|";
									}
								}
								$("#" + dtid).find("tr").each(eachFunc);
								if (pdv) {
									pdv = pdv.substring(0, pdv.length - 1);
								}
							}
							if (typeof pdv == 'undefined') {
								pdv = '';
							}
							pd.value = pdv;
							rtn.push(pd);
						}
					}
					if (window.console) {
						console.log('获取的表单数据：',rtn);
					}
					return rtn;
				},
				
				showDatas : function (){
					var jsonData = this.rtnData;
					var ias_div_data = domConstruct.create('div', {
						className : 'muiDialogIasMain'
					});
					for(var i = 0; i < this.paramsHere.showGroups.length; i++){
						var items = this.paramsHere.showGroups[i];
						var groups_dom = domConstruct.create('div', {
							className : 'muiDialogIasGroups'
						},ias_div_data);
						var title_dom = domConstruct.create('div', {
							className : 'muiDialogIasGroupTitle',
							innerHTML : items.label
						},groups_dom);
						var info_dom = domConstruct.create('ul', {
							className : 'muiDialogIasInfo'
						},groups_dom);
						
						for(var j = 0; j < jsonData.length;j++){
							if(jsonData[j].groups.indexOf(items.key)>=0){
								var data = jsonData[j];
								
								var info_label = domConstruct.create('li', {
									className : 'muiDialogIasInfoDesc',
									innerHTML : data.label
								},info_dom)
								if(data.result != 'success'){
									var info_more = domConstruct.create('a', {
										className : 'muiDialogIasInfoMore',
										innerHTML : msg2["mui.dialog.more"],
										'data-key' : data.templateId
									},info_label);
									domClass.add(info_label, data.result)
									//绑定点击事件
									this.connect(info_more,'click','_showMore');
									//
								}
								
							}
						}
					}
					
					return ias_div_data;
				},
				
				_showMore : function(evt){
					var target = evt.target;
					var key = target.dataset.key;
					var jsonData = this.getCheckInfo(key);
					var title = domConstruct.create('span',{
						style:{'font-size': 'var(--muiFontSizeMS)'},
						innerHTML:msg2["mui.button.iassister"]
					})
					var infoDia = domConstruct.create('div',{className:'muiIasInfoDialog'})
					/*****************第二层dialog dom 构建******************/
					
					var info_tab = domConstruct.create('div',{className:'muiIasInfoDialogTab'},infoDia);
					var info_tab_ul = domConstruct.create('ul',{className:'tabList'},info_tab);
					var index = 0;

					if(jsonData.showInfos.pic.fileList.length > 0){
						++index;
						var imgTab = domConstruct.create('li',{
							className : 'muiIasInfoDialog tabs' + (index == 1?' active':''),
							innerHTML:msg2['mui.dialog.more.pic'],
							'data-index':index
						},info_tab_ul);
						this.connect(imgTab, 'click', "switchTabs");
						
						/***构建显示信息 图片***/
						this.fileList = jsonData.showInfos.pic.fileList;
						var tips_box = domConstruct.create('div',{className:'muiIasInfoCont imgList'+(index==1?' show':''),'data-index':index},infoDia);
						for(var i=0;i<this.fileList.length;i++){
							var item = jsonData.showInfos.pic.fileList[i];
							var url = item.url;
							var img_box = domConstruct.create('div',{className:'imgContent','data-src':url,innerHTML:'<i class="icon"></i>'},tips_box);
							var img = domConstruct.create('img',{
								className : '',
								src : url,
								title : item.name
							},img_box);
							this.connect(img_box, 'click', 'showImg');
						}
						

						/***构建显示信息end***/
					}
					if(jsonData.showInfos.link.linkList.length > 0){
						++index;
						var linkTab = domConstruct.create('li',{
							className : 'muiIasInfoDialog tabs' + (index == 1?' active':''),
							innerHTML:msg2['mui.dialog.more.link'],
							'data-index':index
						},info_tab_ul);
						this.connect(linkTab, 'click', "switchTabs");
						
						/***构建显示信息 链接***/
						var tips_box = domConstruct.create('div',{className:'muiIasInfoCont linkList'+(index==1?' show':''),'data-index':index},infoDia);
						var link_box = domConstruct.create('ul',{className:'linkContent'},tips_box);
						for(var i=0;i<jsonData.showInfos.link.linkList.length;i++){
							var item = jsonData.showInfos.link.linkList[i];
							var li = domConstruct.create('li',{className:'linkLine'},link_box)
							var img = domConstruct.create('a',{
								className : '',
								href : item.href,
								innerHTML : item.title
							},li);
						}

						/***构建显示信息end***/
					}
					if(jsonData.showInfos.text.content !=''){
						++index;
						var textTab = domConstruct.create('li',{
							className : 'muiIasInfoDialog tabs' + (index == 1?' active':''),
							innerHTML:msg2['mui.dialog.more.text'],
							'data-index':index
						},info_tab_ul);
						this.connect(textTab, 'click', "switchTabs");
						
						/***构建显示信息 文本***/
						var tips_box = domConstruct.create('div',{className:'muiIasInfoCont textContent'+(index==1?' show':''),'data-index':index},infoDia);
						var text_box = domConstruct.create('div',{
							className:'',
							innerHTML: jsonData.showInfos.text.content
						},tips_box);

						/***构建显示信息end***/
					}
					
					/*****************第二层dialog dom 构建end******************/
					
					this.dialogs[key] = Dialog.element({
							title : msg2["mui.dialog.more.title"],
							element : infoDia,
							transform : 'right', //从右边弹出
							buttons : '',
							position: 'bottom',
							parseable : false, 
							canClose : true,
							callback : null
						});
					console.log(jsonData);
				},
				
				getCheckInfo : function (key){
					var data = this.rtnData;
					if(data.length <= 0){
						return [];
					}
					var rtn = [];
					for(var i = 0;i < data.length; i++){
						var item = data[i];
						if(item.templateId == key){
							rtn = item;
						}
					}
					return rtn;
				},
				
				switchTabs : function(evt){
					evt.target.parentNode.childNodes.forEach(function(i,e){
						if(domClass.contains(i, "active")){
							domClass.remove(i, "active");
						}
					})
					domClass.add(evt.target, "active");
					var index = evt.target.dataset.index;
					$(evt.target).parents(".muiIasInfoDialog").find(".muiIasInfoCont").removeClass("show");
					$(evt.target).parents(".muiIasInfoDialog").find(".muiIasInfoCont[data-index='"+index+"']").addClass("show");
				},
				
				showImg : function(evt){
					if(this.fileList.length<=0){
						return;
					}
					var srcList = [];
			        for (var i = 0;i < this.fileList.length; i++) {
			        	var item = this.fileList[i];
			        	if(item.url != ''){
			        		srcList[i] = item.url;
			        	}
					}
			        var url = evt.target.parentNode.dataset.src;
					adapter.imagePreview({
						curSrc : url,
						srcList : srcList,
						previewImgBgColor : ''
					});
				}

		});
});