define(["dojo/_base/declare", "dijit/_WidgetBase","dojo/query","dojo/dom-construct","dojo/dom-style","dojo/date/locale","mui/util",
        "dojo/dom-class","mui/form/Address","dojo/touch","dojo/topic","mui/i18n/i18n!sys-mobile","dijit/registry","dojo/_base/lang",
        "dojo/io-query","dojo/request","dojo/dom-prop","mui/i18n/i18n!sys-attend",'dojo/on','mui/picslide/ImagePreview'],
		function(declare,WidgetBase,query,domConstruct,domStyle,locale,util,domClass,Address,touch,topic,muiMsg,registry,lang,
				ioq,request,domProp,Msg,on,ImagePreview){
	
		return declare("sys.attend.SignTrailPersonView",[WidgetBase],{
			baseClass :'',

			postCreate : function() {
				this.inherited(arguments);
				this.subscribe("/signTrail/stat/person/datas",'renderSignTrailList');
				topic.subscribe("/mui/category/submit",lang.hitch(this,"returnDialog"));
				this.subscribe("/mui/category/tmploaded",lang.hitch(this,"showCategoryDialog"));
				topic.subscribe("/signTrail/stat/list/scroll", this.scrollToListItem);
				
			},
			
			buildRendering:function(){
				this.inherited(arguments);
				this.domNode = domConstruct.create('div',{className:'muiSignTrailContent'},this.domNode);
			},
			
			renderSignTrailList :function(evt,datas){
				domConstruct.empty(query('.muiLocationListDom .muiSignTrailContent')[0]);
				var toolNodeType = 'up';
				query('.muiLocationListDom .muiSignTrailHeadBtn').forEach(function(node){
					toolNodeType = domClass.contains(node, 'collapse') ? 'up' : 'down';
					domConstruct.destroy(node);
				});
				if(datas){
					datas.toolNodeType = toolNodeType;
				}
				var headH = domStyle.get(query('.muiSignTrailHead')[0],'height');
				var botttomH = domStyle.get(query('.muiSignTrailTabBar')[0],'height');
				var winH = util.getScreenSize().h;
				domStyle.set(query('.muiSignTrailTabBar')[0],'top',(winH-headH)+'px');
				if(datas){
					domStyle.set(query('.muiSignTrailScrollableView')[0].parentNode,'display','');
					this.buildTitle(datas);
					this.buildList(datas);
				}else{
					domStyle.set(query('.muiSignTrailScrollableView')[0].parentNode,'display','none');
				}
				
			},
			
			buildTitle : function(datas){
				domClass.remove(query('.muiSignTrailScrollableView')[0],'mblScrollableView');
				var toolNode = domConstruct.create('span',{className:'muiSignTrailHeadBtn'+(datas.toolNodeType=='up'?' collapse':'')},query('.muiSignTrailScrollableView')[0].parentNode,'first');
				domConstruct.create('span',{className:'mui mui-down-n'},toolNode);
				
				var record = datas[0];
				var headNode = domConstruct.create('div',{className:'muiSignTrailHead'},this.domNode);
				var leftNode = domConstruct.create('div',{className:'muiSignTrailLeftArea',innerHTML:''},headNode);
				var personHead = domConstruct.create('span',{className:'muipersonHead'},leftNode);
				domConstruct.create('img',{className:'muiImg',src:record.docCreatorImg},personHead);
				
				domConstruct.create('input',{type:'hidden',name:'docCreatorId',value:record.docCreatorId},personHead);
				
				var infoNode = domConstruct.create('span',{className:'muiPersonInfo'},leftNode);
				domConstruct.create('span',{className:'',innerHTML:record.docCreatorName},infoNode);
				domConstruct.create('span',{className:'muiDept',innerHTML:record.dept || ''},infoNode);
				
				this.connect(toolNode,'click', 'onPanelToggle');
				
				if(record.mydoc && record.mydoc != 'true') {
					var rightNode = domConstruct.create('div',{className:'muiSignTrailRightArea'},headNode);
					domConstruct.create('span',{className:'muiSignTrailCount'},rightNode);
					
					var fdSignCount = record.fdSignCount;
					domConstruct.create('span',{className:'muiSignTrailCountTxt',innerHTML:Msg['mui.total.people'].replace('%count%', fdSignCount)},rightNode);
					this.connect(rightNode,'click', 'onPersonsClick');
				}
			},
			buildList:function(datas){
				var self = this;
				var listNode = domConstruct.create('ul',{className:'muiSignTrailRecordList'},this.domNode);
				for(var i = 0; i< datas.length;i++){
					var record = datas[i];
					var liNode = domConstruct.create('li',{className:'muiSignTrailItem'},listNode);
					domConstruct.create("span",{className:"muiItemPoint",innerHTML:''},liNode);
					var titleNode = domConstruct.create("div",{className:"muiItemTitle",innerHTML:''},liNode);
					domConstruct.create("span",{className:"time",innerHTML:record.fdSignTime},titleNode);
					domConstruct.create("span",{className:"info",innerHTML:Msg['mui.sign.cust']},titleNode);
					
					var contentNode = domConstruct.create("div",{className:"muiItemContent",innerHTML:''},liNode);
					var item = domConstruct.create("div",{className:"item",innerHTML:''},contentNode);
					if(record.fdAddress){
						var p = domConstruct.toDom("<p class='muiInfo'><i class='mui mui-location'></i>" + record.fdAddress + "</p>");
						domConstruct.place(p, item);
					}
					if(record.fdDesc){
						var p = domConstruct.toDom("<p class='muiInfo'><i class='mui mui-file-txt'></i>" + record.fdDesc + "</p>");
						domConstruct.place(p, item);
					}
					var images = eval(record.fdAttrs);
					if(images && images.length>0){
						var imgBoxNode = domConstruct.create('div',{className:'muiImgBox'},contentNode);
						for(var idx in images){
							var imgNode = domConstruct.create("span",{className:""},imgBoxNode);
							var picUrl = images[idx];
							domConstruct.create("img",{className:"",src:picUrl},imgNode);
							on(imgNode, touch.press, (function(curSrc, srcList) {
								return function(){
									self.viewPic(curSrc, srcList);
								};
							})(picUrl, images));
						}
					}
				}
				
			},
			
			viewPic : function(curSrc, srcList) {
				var options = {
						curSrc : curSrc, 
						srcList : srcList,
						previewImgBgColor : ''
					};
				if(!this.preivew)
					this.preivew = new ImagePreview();
				this.preivew.play(options);
			},
			
			onPersonsClick : function(){
				var fdCategoryId = query('#signTrailView input[name="fdCategoryId"]')[0].value;
				var fdDate = query('#signTrailView input[name="nowDate"]')[0].value;
				fdDate = locale.parse(fdDate+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
				var options = {
						type:'ORG_TYPE_PERSON',
						idField:'docCreatorId',nameField:'__docCreatorName',
						dataUrl:'/sys/attend/sys_attend_sign_stat/sysAttendSignStat.do?method=addressList&fdCategoryId=' + fdCategoryId + "&fdDate=" + fdDate.getTime()
				};
				var address = new Address(options);
				address.startup();
				domConstruct.place(address.domNode,document.body,'last');
				this.defer(function(){
					address._selectCate();
				},350);
			},
			
			onPanelToggle : function(){
				var btnNode = query('.muiSignTrailHeadBtn')[0];
				if(domClass.contains(btnNode,'collapse')){
					domClass.remove(btnNode,'collapse');
					//展开
					var winH = util.getScreenSize().h;
					var headH = domStyle.get(query('.muiSignTrailHead')[0],'height');
					contentH = winH - headH - 120;
					var scrollView = query('.muiLocationListDom .muiSignTrailScrollableView')[0];
					domStyle.set(scrollView,'maxHeight',contentH+'px');
					domStyle.set(scrollView,'height',contentH+'px');
				}else{
					domClass.add(btnNode,'collapse');
					//收缩
					var scrollView = query('.muiLocationListDom .muiSignTrailScrollableView')[0];
					domStyle.set(scrollView,'maxHeight','120px');
					domStyle.set(scrollView,'height','120px');
				}
				
			},
			
			returnDialog : function(widget,evt){
				if(widget.key!='docCreatorId'){
					return;
				}
				var docCreatorId = evt.curIds;
				//临时优化处理
				var tmpInput = query('input[name="__docCreatorName"]');
				if(tmpInput && tmpInput.length>0){
					tmpInput.parents('.muiField').remove();
				}
				var fdDate = query('#signTrailView input[name="nowDate"]')[0].value;
				fdDate = locale.parse(fdDate+' 00:00',{selector : 'date',datePattern : dojoConfig.DateTime_format});
				var fdCategoryId = query('#signTrailView input[name="fdCategoryId"]')[0].value;
				var options = {
						handleAs : 'json',
						method : 'POST',
						data : ioq.objectToQuery({
							fdDate : fdDate.getTime(),
							fdCategoryId:fdCategoryId,
							docCreatorId:docCreatorId,
							mydoc : false
						})	
					};
				var url = util.formatUrl('/sys/attend/sys_attend_main/sysAttendMain.do?method=statTrail');
				request(url,options).then(lang.hitch(this,function(result){
					if(result && result.datas.length>0){
						var datas = this.formatData(result.datas);
						var coords = [];
						for(var idx in datas){
							var point = {};
							point.fdLng = datas[idx].fdLng;
							point.fdLat = datas[idx].fdLat;
							coords.push(point);
						}

						var evt = {
								showStatus : 'view',
								datas:coords
							};
						window['muiSignTrailLocationDialog'].show(evt);
						topic.publish('/signTrail/stat/person/datas',this,datas);
					}else{
						
					}
				}),lang.hitch(this,function(err){
					
				}));
			},
			showCategoryDialog : function(evt){
				if(evt.dom.id=='__cate_dialog_docCreatorId'){
					domStyle.set(query('#__cate_dialog_docCreatorId .muiCateHeader')[0],'display','none');
				}
			},
			formatData:function(datas){
				var data = [];
				for(var i = 0 ; i < datas.length;i++){
					var records = datas[i];
					var record = {};
					for(var idx in records){
						record[records[idx].col] = records[idx].value;
					}
					data.push(record);
				}
				return data;
			},
			
			scrollToListItem : function(evt) {
				if(evt){
					var headH = domProp.get(query('.muiSignTrailScrollableView .mblScrollableViewContainer .muiSignTrailHead')[0],'scrollHeight');
					var itemH = domProp.get(query('.muiSignTrailScrollableView .mblScrollableViewContainer .muiSignTrailItem')[0],'scrollHeight');
					var offsetY = - ( headH + itemH * parseInt(evt.index) );
					domStyle.set(query('.muiSignTrailScrollableView .mblScrollableViewContainer')[0],'transform','translate3d(0px, '+ offsetY +'px, 0px)');
				}
			},
			
			startup:function(){
				if(this._started){ return; }
				this.inherited(arguments);
			}
		});
});