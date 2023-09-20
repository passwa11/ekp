define( [ "dojo/_base/declare", "mui/nav/NavItem","mui/i18n/i18n!sys-mobile","dojo/topic", "dijit/registry"], 
		function(declare,NavItem,Msg,topic,registry) {
		var header = declare("mui.selectdialog.UnitCateNavItem", [ NavItem], {
				//加载
				startup : function() {
					this.inherited(arguments);
				},
				_onClick: function(e) {
				      if (e) {
				        var target = e.target;
				        this.beingSelected(target);
				      }
				      // 默认click事件，触发列表数据刷新
				      this.defaultClickAction(e);
			    }
			});
			return header;
});