define(["dojo/_base/declare", "mui/i18n/i18n!sys-mobile"], function(
  declare,
  Msg
) {
  return declare("mui.address.AddressNavBarMixin", null, {
    memory:  [ 
		{ 
			moveTo: 'usualCateView', 
			text: Msg['mui.mobile.address.usual'],
			key: 'usual',
			channelName : "addressNav",
			selected : true
		},
		{
			moveTo: 'allCateView', 
			text: Msg['mui.mobile.address.allCate'],
			channelName : "addressNav",
			key: 'all'
		},
		{
			moveTo: 'groupView',
			text: Msg['mui.mobile.address.group'],
			channelName : "addressNav",
			key: 'group'
		}
	],
	  startup: function() {
		  this.inherited(arguments);
		  if(this.deptLimit) {
			  // 有限制时，只出现“组织架构”
			  this.memory = [
				  {
					  moveTo: 'allCateView',
					  text: Msg['mui.mobile.address.allCate'],
					  channelName : "addressNav",
					  key: 'all',
					  selected : true
				  }
			  ];
		  }
	  }
  
  })
})
