define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/on"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , _ListLinkItemMixin,on) {
		return declare("mui.list.item.seachListItemMixin", [ItemBase], {
		tag:"li",
		imgUrl:'',
		fdId:'',
		key:'',
		buildRendering:function(){
			this.inherited(arguments);
			var itemNode = domConstruct.create("div",{className:'search-list-item'},this.domNode);
			var itemImgNode = domConstruct.create("img",{className:'search-list-item-img'},itemNode);
			/*var itemHrefNode = domConstruct.create("a",{className:'search-list-item-href'},this.domNode);*/
			/*itemHrefNode.href=dojoConfig.baseUrl+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+this.fdId;*/
			var _this = this;
			on(this.domNode,"click",function(){
				window.location.href=dojoConfig.baseUrl+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+_this.fdId;
			})
			itemImgNode.src = this.imgUrl;
			var itemNameNode = domConstruct.create("div",{className:'search-list-item-name'},itemNode);
			itemNameNode.innerHTML=this.personInfoName;
			if(this.key){
				var resName = this.personInfoName.replace(new RegExp(this.key,"g"),"<span>"+this.key+"</span>");
				var reg = new RegExp("[\\u4E00-\\u9FFF]+");
				var hasChinese = this.key.match(reg,"g");
				//如果没有汉字进行拼音匹配
				if(!hasChinese){
					var tempChin ="";
					for(var i=0;i<resName.length;i++){
						if(resName[i].match(reg,"g")){
							var chin = ConvertPinyin(resName[i]);
							if(chin.indexOf(this.key)>-1){
								tempChin+="<span>"+resName[i]+"</span>"
							}else{
								tempChin+=resName[i];
							}
						}else{
							tempChin+=resName[i];
						}
					}
					resName = tempChin;
				}
				itemNameNode.innerHTML=resName;
			}
		},
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);

		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}

	});
});