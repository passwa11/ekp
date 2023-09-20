define( [ "dojo/_base/declare", "mui/address/AddressHeader",
          "dojo/dom-geometry", "dojo/dom-style", "sys/zone/mobile/js/AddressSessionStorage"], 
			function(declare, AddressHeader, domGeometry,domStyle, AddressSessionStorage) {
		var header = declare("sys.zone.mobile.js.address.AddressZoneHeader", [AddressHeader], {
			buildRendering : function() {
				this.inherited(arguments);
				this.cancelNode.innerHTML = "";
			},
			
			
			_gotoParent : function() {
				AddressSessionStorage.removeStorage("zoneListData");
				this.inherited(arguments);
			},
			
			startup: function() {
				var listData = AddressSessionStorage.getStorage("zoneListData");
				if(listData) {
					//listData.curDepId为空时是最顶级部门
					this.curId = listData.curDepId ? listData.curDepId : "";
				} 
				this.inherited(arguments);
				var h = domGeometry.position(this.titleDom).h;
				domStyle.set(this.contentNode , "height" , h + "px");
			}
			
		});
		return header;
});