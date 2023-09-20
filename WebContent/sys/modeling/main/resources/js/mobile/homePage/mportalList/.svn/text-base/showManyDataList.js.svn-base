define( [ "dojo/_base/declare", "dojo/dom-construct","dojo/dom-style",
          "dijit/_WidgetBase","dojo/_base/lang","mui/util","dojo/topic",], 
	function(declare, domConstruct, domStyle,  WidgetBase, lang,util,topic) {
	var button = declare("sys.modeling.main.resources.js.mobile.homePage.mportalList.showManyDataList", [ WidgetBase], {
		
		url : "/sys/modeling/main/mobile/modelingAppMainMobileListView.do?method=listViewIndex&listViewId=!{listViewId}",
		
		listViewId:"",
		
		text:'',
		
		buildRendering:function() {
			this.inherited(arguments);
			this.contentNode = domConstruct.create("div",
					{'className':'muiButtonContainer'},this.domNode);
		},
		
		postCreate:function(){
			this.inherited(arguments);
			var that = this
			this.connect(this.contentNode,'touchend',"showManyBottonClick");
			this.subscribe("sys.modeling.listviewId", function(listViewId){
				this.listViewId = listViewId;
				if(this.text){
					this.textNode = domConstruct.create("div",{'className':'mportalList-dataList-showMany',
						'innerHTML':this.text},this.contentNode);
				}
			});
		},
		
		startup:function() {
			this.inherited(arguments);
		},
		showManyBottonClick:function(){
			if(this.listViewId){
				var url = util.urlResolver(this.url, {
					listViewId: this.listViewId,
			    });
				window.open(util.formatUrl(url),'_self');
			}
		}
	});
	return button;
});