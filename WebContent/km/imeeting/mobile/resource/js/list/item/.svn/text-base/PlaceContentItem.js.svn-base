define(
		'km/imeeting/mobile/resource/js/list/item/PlaceContentItem',
		[ "dojo/_base/declare", "dojox/mobile/_ItemBase", 
				"dojo/dom-construct", "dojo/dom-style", "dojo/dom-class", "dojo/dom-attr", 
				"dojo/query", "dojo/request", "dojo/topic", "mui/util" ],
		function(declare, ItemBase, domConstruct, domStyle, domClass, domAttr, query,
				request, topic,util) {
			var NavItem = declare(
					'km.imeeting.PlaceContentItem',
					ItemBase,
					{

						// 选中时class
						_selClass : 'place_selected',
						
						// 选中事件
						topicType : '/km/imeeting/placeitem/_selected',
						
						tag : 'ul',

						buildRendering : function() {
							this.domNode = this.containerNode = this.srcNodeRef
									|| domConstruct.create(this.tag, {
										className : 'placeContentitem'
							});
							this.inherited(arguments);
							/*
							if(this.fdOccupiedlist.length > 0){
								for(var i = 9;i < 24;i++){ 
									for(var j = 0;j<this.fdOccupiedlist.length;j++){
										var timeArea = this.fdOccupiedlist[j].timeArea;
										if(timeArea == i){
											this.textNode = domConstruct.create('li', {
												name:this.fdId+"_"+i+":00",
												detail : j,
												style:"width:40px;height:30px;background-color: #6ad449",
												className:''
											}, this.domNode);
											this.connect(this.textNode, "onclick", '_onClick');
										}else{
											//if(query('[name="'+this.fdId+"_"+i+":00"+'"]')){
												this.textNode = domConstruct.create('li', {
													name:this.fdId+"_"+i+":00",
													style:"width:40px;height:30px;background-color: #b3b5b2",
													className:''
												}, this.domNode);
												this.connect(this.textNode, "onclick", '_onClick');
											//}
										}
									}
								}
							}else{
								for(var i = 9;i<24;i++){ 
									this.textNode = domConstruct.create('li', {
										name:this.fdId+"_"+i+":00",
										style:"width:40px;height:30px;background-color: #b3b5b2",
										className:''
									}, this.domNode);
									this.connect(this.textNode, "onclick", '_onClick');
								}
							}
							*/
							for(var i = 8;i<24;i++){ 
								if(this.today){
									if(i< this.todayHour){
										this.textNode = domConstruct.create('li', {
											name:this.fdId+"_"+i+":00",
											value: i,
											className:'place_occupied'
										}, this.domNode);
										domAttr.set(this.textNode, 'data-flag', 'late');
									}else{
										this.textNode = domConstruct.create('li', {
											name:this.fdId+"_"+i+":00",
											value: i,
											className:'place_free'
										}, this.domNode);
										this.connect(this.textNode, "onclick", '_onClick');
									}
								}else{
									if(this.late) {
										this.textNode = domConstruct.create('li', {
											name:this.fdId+"_"+i+":00",
											value: i,
											className:'place_occupied'
										}, this.domNode);
										domAttr.set(this.textNode, 'data-flag', 'late');
									} else {
										this.textNode = domConstruct.create('li', {
											name:this.fdId+"_"+i+":00",
											value: i,
											className:'place_free'
										}, this.domNode);
										this.connect(this.textNode, "onclick", '_onClick');
									}
								}
							}
							for(var j = 0;j<this.fdOccupiedlist.length;j++){
								var timeArea = this.fdOccupiedlist[j].timeArea;
								//console.log('[name="'+this.fdId+'_'+timeArea+':00'+'"]');
								var ele = query('[name="'+this.fdId+'_'+timeArea+':00'+'"]',this.domNode)[0];
								ele.setAttribute("detail",j);
								domClass.replace(ele,"place_occupied");
								//domStyle.set(ele,"background-color","#b3b5b2");

								var fdOccupiedItem = this.fdOccupiedlist[j];
								switch(fdOccupiedItem.fdHasExam) {
									case 'wait': 
										domAttr.set(ele, 'data-has-exam', 'wait');
										break;
									default: break;
								}
								
							}
							
						},
						beingSelected : function(target) {
							//domStyle.set(target,"background-color","#b555b2");
							topic.publish(this.topicType, this,{detail:target.getAttribute("detail"),value:target.value});
						},
						_onClick : function(e) {
							var target = e.target;
							this.beingSelected(target);
						},
						startup : function() {
							if (this._started)
								return;
							this.inherited(arguments);
						}
					});

			return NavItem;
		});