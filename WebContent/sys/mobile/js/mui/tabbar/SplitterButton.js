define([ "dojo/_base/declare", "mui/tabbar/TabBarButton", "dojo/dom-class"],
		function(declare, TabBarButton, domClass) {

			return declare("mui.tabbar.SplitterButton", [ TabBarButton ], {

				flag : "left",	  //标示方向: left or right
				
				groupIndex : -1,  //按钮组序号
				
				tarbarGruop:null,

				buildRendering : function() {
					if (this.flag == 'left') {
						this.icon1 = "mui mui-splitterLeftShape";
					} else {
						this.icon1 = "mui mui-splitterRightShape";
					}
					this.inherited(arguments);
					domClass.add(this.domNode,'muiSplitterButton');
				},

				startup : function() {
					if (this._started)
						return;
					this.inherited(arguments);
				},

				onClick : function() {
					this.defer(function(){
						if(this.groupIndex!=-1){
							if(this.tarbarGruop==null){
								this.tarbarGruop = this.getParent().getParent();
							}
							if(this.tarbarGruop && this.tarbarGruop.declaredClass=='mui.tabbar.TabBarGroup'){
								if(this.flag=='left'){
									this.tarbarGruop.switchTabBar(this.groupIndex-1);
								}else{
									this.tarbarGruop.switchTabBar(this.groupIndex+1);
								}
							}
						}
					},300)
					
				}

			});
		});