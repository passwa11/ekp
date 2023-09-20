define(["dojo/_base/declare", "dijit/_WidgetBase","dojo/query","dojo/dom-construct","dojo/dom-style","dojo/date/locale","mui/util",
        "dojo/dom-class","mui/form/Address","dojo/touch","dojo/topic","mui/i18n/i18n!sys-mobile","dijit/registry","dojo/_base/lang","mui/i18n/i18n!sys-attend",'dojo/on','mui/picslide/ImagePreview'],
		function(declare,WidgetBase,query,domConstruct,domStyle,locale,util,domClass,Address,touch,topic,muiMsg,registry,lang,Msg,on,ImagePreview){
	
		return declare("sys.attend.AttendLocationInfo",[WidgetBase],{
			baseClass :'',

			postCreate : function() {
				this.inherited(arguments);
				this.subscribe("/attend/location/info/datas",'renderLocationView');
			},
			
			buildRendering:function(){
				this.inherited(arguments);
				this.domNode = domConstruct.create('div',{className:'muiLocListContent'},this.domNode);
			},
			renderLocationView :function(evt,datas){
				domConstruct.empty(query('.muiLocationDialogListDom .muiLocListContent')[0]);
				query('.muiLocationDialogListDom .muiToggleHeadBtn').forEach(function(node){
					domConstruct.destroy(node);
				});
				
				var headH = domStyle.get(query('.muiAttendMainHead')[0],'height');
				var winH = util.getScreenSize().h;
				var scrollView = query('.muiLocationDialogListDom .muiLocListScrollableView')[0];
				domStyle.set(scrollView,'height', winH+'px');
				domStyle.set(scrollView,'maxHeight', '160px');
				if(datas){
					domStyle.set(query('.muiLocationDialogListDom .muiLocListScrollableView')[0].parentNode,'display','');
					this.buildTitle(datas);
					this.buildList(datas);
					//如果是多条隐藏地图显示
					if(datas.length>1){
						var scrollView = query('.muiLocationDialogListDom .muiLocListScrollableView')[0];
						domStyle.set(scrollView,'maxHeight','100%');
						domStyle.set(scrollView,'height','-webkit-fill-available');
					}
				}else{
					domStyle.set(query('.muiLocationDialogListDom .muiLocListScrollableView')[0].parentNode,'display','none');
				}
			},
			
			buildTitle : function(datas){
				domClass.remove(query('.muiLocationDialogListDom .muiLocListScrollableView')[0],'mblScrollableView');
				var toolNode = domConstruct.create('span',{className:'muiToggleHeadBtn collapse'},query('.muiLocationDialogListDom .muiLocListScrollableView')[0].parentNode,'first');
				domConstruct.create('span',{className:'mui mui-down-n'},toolNode);
				
				var record = datas[0];
				var headNode = domConstruct.create('div',{className:'muiLocListHead'},this.domNode);
				var leftNode = domConstruct.create('div',{className:'muiLocListLeftArea',innerHTML:''},headNode);
				var personHead = domConstruct.create('span',{className:'muipersonHead'},leftNode);
				domConstruct.create('img',{className:'muiImg',src:record.docCreatorImg},personHead);
				
				var infoNode = domConstruct.create('span',{className:'muiPersonInfo'},leftNode);
				domConstruct.create('span',{className:'',innerHTML:record.docCreatorName},infoNode);
				domConstruct.create('span',{className:'muiDept',innerHTML:record.dept || ''},infoNode);
				
				this.connect(toolNode,'click', 'onPanelToggle');
			},
			
			buildList:function(datas){
				var self = this;
				var listNode = domConstruct.create('ul',{className:'muiLoctionRecordList'},this.domNode);
				
				for (var i=0;i<datas.length;i++){					
					var record = datas[i];
					var liNode = domConstruct.create('li',{className:'muiLoctionItem'},listNode);
					domConstruct.create("span",{className:"muiItemPoint",innerHTML:''},liNode);
					
					var titleNode = domConstruct.create("div",{className:"muiItemTitle",innerHTML:''},liNode);
					domConstruct.create("span",{className:"time",innerHTML:record.fdSignedDate+" "+record.fdSignTime},titleNode);
					domConstruct.create("span",{className:"info",innerHTML:Msg['mui.sign.cust']},titleNode);
					if(record.fdType == '1') {
						if(record.fdStatus=='1' && record.fdOutside != 'true' || record.fdState=='2'){
							domConstruct.create("span",{className:"muiSignInLabel signInLabelPrimary",innerHTML:Msg['mui.fdStatus.ok']},titleNode);
						}
						if(record.fdOutside == 'true'&& record.fdState != '2'){
							domConstruct.create("span",{className:"muiSignInLabel signInLabelWarning",innerHTML:Msg['mui.outside']},titleNode);
						}
						if(record.fdStatus=='4' || record.fdStatus=='5' || record.fdStatus=='6'){
							domConstruct.create("span",{className:"muiSignInLabel signInLabelPrimary",innerHTML:record.fdStatusText},titleNode);
						}
						if((record.fdStatus=='0' || record.fdStatus=='2' || record.fdStatus=='3' ) && record.fdState!='2'){
							domConstruct.create("span",{className:"muiSignInLabel signInLabelDanger",innerHTML:record.fdStatusText},titleNode);
						}
					}
					if(record.fdType=='2' && record.fdAppId){
						if(record.fdStatus=='1'){
							domConstruct.create("span",{className:"muiSignInLabel signInLabelPrimary",innerHTML:Msg['mui.fdStatus.ok']},titleNode);
						} else if (record.fdStatus=='2'){
							domConstruct.create("span",{className:"muiSignInLabel signInLabelDanger",innerHTML:Msg['mui.fdStatus.late']},titleNode);
						}
					}
					
					var contentNode = domConstruct.create("div",{className:"muiItemContent",innerHTML:''},liNode);
					var item = domConstruct.create("div",{className:"item",innerHTML:''},contentNode);
					if(record.fdLocation){
						var p = domConstruct.toDom("<p class='muiInfo'><i class='mui mui-location'></i>" + record.fdLocation + "</p>");
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
							// on(imgNode, touch.press, (function(curSrc, srcList) {
							on(imgNode, "click", (function(curSrc, srcList) {
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
			
			onPanelToggle : function(){
				var btnNode = query('.muiLocationDialogListDom .muiToggleHeadBtn')[0];
				if(domClass.contains(btnNode,'collapse')){
					domClass.remove(btnNode,'collapse');
					//展开
					var winH = util.getScreenSize().h;
					var headH = domStyle.get(query('.muiAttendMainHead')[0],'height');
					var contentH = winH - headH - 160;
					var scrollView = query('.muiLocationDialogListDom .muiLocListScrollableView')[0];
					domStyle.set(scrollView,'maxHeight',contentH+'px');
					domStyle.set(scrollView,'height',contentH+'px');
				}else{
					domClass.add(btnNode,'collapse');
					//收缩
					var scrollView = query('.muiLocationDialogListDom .muiLocListScrollableView')[0];
					domStyle.set(scrollView,'maxHeight','160px');
					domStyle.set(scrollView,'height','160px');
				}
				
			},
			
			startup:function(){
				if(this._started){ return; }
				this.inherited(arguments);
			}
		});
});