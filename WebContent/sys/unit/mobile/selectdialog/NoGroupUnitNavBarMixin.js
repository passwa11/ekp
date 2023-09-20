define(["dojo/_base/declare", "mui/i18n/i18n!sys-mobile"], function(declare,  Msg) {
	
  return declare("sys.unit.mobile.selectdialog.UnitNavBarMixin", null, {
    memory:  [
		{
			moveTo: 'allCateView', 
			text: '所有单位',
			channelName : "unitNav",
			key: 'all',
			selected : true
		}
	]
  })
})
