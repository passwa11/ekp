define([
	"dojo/_base/declare",
	"mui/util",
	"mui/hash"
], function(declare,util,hash) {
  return declare("sys.lbpmperson.LbpmPersonCfgNavBarMixin", null, {
	  _createItemProperties : function(item) {
		  if (this.isTiny) {
			 item['text'] = item[dojoConfig.locale];
		  }
		  //若从门户点入，则匹配key，使默认选中对应Tab
		  var key = util.getUrlParameter(window.location.href,"key");
		  if(key && item.key == key){
			  //若开启hash，则替换hash path，否则设置item.selected
			  if(hash.canHash()){
				  // 替换hash path
				  hash.replacePath([item.tabIndex]);
			  }else{
				  item.selected = true;
			  }
		  }
		  return item;
	  }
  })
})
