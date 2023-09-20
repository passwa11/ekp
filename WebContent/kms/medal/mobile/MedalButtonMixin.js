define(	["dojo/_base/declare", "dojo/dom-class",
				"dojo/dom-style", 
				"mui/util",
				"dojo/_base/lang",
				"dojo/dom-construct","mui/i18n/i18n!kms-medal:kmsMedalMobile"
				], function(declare, domClass,
				domStyle, util, lang,domConstruct,msg) {

			return declare("kms.medal.MedalButtonMixin", [ ], {

						fdModelId : "",
				
						url : "/kms/medal/kms_medal_main/kmsMedalMain.do?method=medalList&personId=!{fdModelId}",
						
						zone_TA_text : "",
						
						baseClass : "",
						
						muiIcon : "",
						
						muiLabel : "",
						
						fdMemberNum : "",
						
						startup : function() {
							if (this._started)
								return;
							this.inherited(arguments);
						},
						
						buildRendering : function() {
							this.inherited(arguments);
							this.domNode.innerHTML = "<i class='" + this.muiIcon+"'></i>"
								+ "<span>"+(this.fdMedalNum ? this.fdMedalNum : "0")+msg['kmsMedalMobile.classifier']+"</span>";
							domClass.add(this.domNode , this.baseClass);
						},
						
						onClick : function() {
							if (this.url) {
								this.detailUrl = util.urlResolver(this.url, {
									"fdModelId" : this.fdModelId,
								});
								window.open(util.formatUrl(this.detailUrl), '_self');
							}
							this.inherited(arguments);
						}

					});
		});