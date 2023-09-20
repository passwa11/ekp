define(["dojo/_base/declare", "mui/i18n/i18n!sys-mobile"], function(
  declare,
  msg
) {
  return declare("mui.selectdialog.DialogNavBarMixin", null, {
    memory: [
      {
        moveTo: "allUnitView",
        text: "所有单位",
        channelName : "dialogNav",
        key:"all",
        selected: true
      },
      {
	  	moveTo: "unitGroupView", 
	  	text: "机构组",
	  	channelName : "dialogNav",
	  	key:"group",
      },
      {
  	  	moveTo: "unitDecView", 
  	  	text: "交换单位",
  	  	channelName : "dialogNav",
  	  	key:"dec",
      }
    ]
  })
})
