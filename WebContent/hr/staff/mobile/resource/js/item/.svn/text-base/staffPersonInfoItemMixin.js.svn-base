define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/on"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin,on) {
	var item = declare("mui.list.item.staffPersonInfoItemMixin", [ItemBase], {
		tag:"li",
		personInfoName:'',
		fdId:'',
		fdSex:'',
		fdStatus:'',
		fdWorkingYears:'',
		fdDeptName:'',
		fdOrgPostNames:'',
		imgUrl : '',
		buildRendering:function(){
			this.inherited(arguments);
			var listBox = domConstruct.create("div",{className:'hr-list-box'},this.domNode);
			var imgNode = domConstruct.create("div",{className:'hr-list-header-img'},listBox);
			var headerImg = domConstruct.create("img",{className:''},imgNode);
			headerImg.src= this.imgUrl;
			var contentNode = domConstruct.create("div",{className:'hr-list-content'},listBox);
			var nameNode = domConstruct.create("div",{className:'hr-list-content-name',innerHTML:this.personInfoName},contentNode);
			var statusNode = domConstruct.create("div",{className:'hr-list-content-status'},contentNode);
			var hrefNode = domConstruct.create("a",{className:'hr-list-box-href'},this.domNode);
			this.connect(hrefNode,'onclick','_onClick');
			var fdSexClass='';
			if(this.fdSex=="M"){
				fdSexClass="hr-list-male";
			}
			if(this.fdSex=="F"){
				fdSexClass="hr-list-female";
			}
			if(fdSexClass){
				var fdSex = domConstruct.create("div",{className:'hr-list-content-status-sex'+" "+fdSexClass},statusNode);
			}
			var fdStatus = domConstruct.create("div",{className:'hr-list-content-status-status '+fdStatusClass,innerHTML:this.fdStatus},statusNode);
			var fdStatusClass="";
			if(this.fdWorkingYears){
				switch(this.fdStatus){
					case '实习':
						fdStatusClass="status-green";
						break;
					case '兼职':
					fdStatusClass="status-yellow";
						break;
				}
				var fdEntryTime = domConstruct.create("div",{className:'hr-list-content-status-status',innerHTML:this.fdWorkingYears},statusNode);
			}
			var dept = this.fdDeptName.split("_").pop();
			var deptPost = dept+(dept&&this.fdOrgPostNames?' - ':'')+this.fdOrgPostNames.split(";").join(" ");
			var postNode = domConstruct.create("div",{className:'hr-list-content-post',innerHTML:deptPost},contentNode);
		},
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);

		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		_onClick:function(evt){
			var url = dojoConfig.baseUrl+"hr/staff/hr_staff_person_info/hrStaffPersonInfo.do?method=view&fdId="+this.fdId;
			window.location.href=url;
		}

	});
	return item;
});