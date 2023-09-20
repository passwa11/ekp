define([ 
	"dojo/_base/declare",
	"dojo/topic",
	"dojo/dom-style",
	"dojo/dom-attr",
	"dijit/registry",	
	"dojox/mobile/TransitionEvent",
	"mui/search/SearchBar",
	"mui/util",
	"mui/i18n/i18n!sys-mobile:mui",
	"mui/address/AddressSearchBar",
	"mui/i18n/i18n!sys-organization:*"
	],function(declare, topic, domStyle, domAttr, registry, TransitionEvent, SearchBar, util, Msg, addressSearchBar, orgMsg) {
	
	return declare("mui.eco.address.AddressSearchBar",[ SearchBar, addressSearchBar ],{
		
		getPlaceHolder: function(){
			// 根据orgType来决定placeHolder提示语
			var placeHolder = Msg['mui.form.please.input'];
			if((this.orgType & window.ORG_TYPE_PERSON) ==  window.ORG_TYPE_PERSON){
				// 姓名
				placeHolder +=  Msg['mui.mobile.address.search.type.person'] + '/';
			}
			if((this.orgType & window.ORG_TYPE_POST) ==  window.ORG_TYPE_POST){
				// 岗位名称
				placeHolder +=  Msg['mui.mobile.address.search.type.post'] + '/';
			}
			placeHolder += orgMsg['sysOrgElementExternal.fdName'] + '/';
			return placeHolder.replace(/\/$/,'');
		}
		
			
	});
});
