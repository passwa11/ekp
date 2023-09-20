define([ "dojo/_base/declare",
		"dijit/_WidgetBase",
		"dojo/dom-class",
		"dojo/request",
		"mui/util",
		"mui/i18n/i18n!km-review:kmReviewDocumentLableName.feedbackInfo"
	],
	function(declare, WidgetBase, domClass, request, util, Msg){
		var button = declare("sys.circulation.CirculationHeader", [ WidgetBase ], {

			postCreate: function() {
				this.inherited(arguments);
			},
			startup: function() {
				this.inherited(arguments);
			},
			buildRendering : function() {
				this.inherited(arguments);
				domClass.add(this.domNode,'muiCirculationHeader');
				this.domNode.innerText=Msg['sysCirculationMain.mobile.lastCirculation'];
				var self = this;
				var url = util.formatUrl('/km/review/km_review_feedback_info/kmReviewFeedbackInfo.do?method=listdata&s_ajax=true');
				url = util.setUrlParameter(url, "fdModelId", this.modelId);
				url = util.setUrlParameter(url, "fdModelName", this.modelName);
				request.get(url, {handleAs:'json',sync:true}).then(function(json) {
					if(json && json.page && json.page.totalSize){
						self.domNode.innerText=Msg['kmReviewDocumentLableName.feedbackInfo']+"("+json.page.totalSize+")";
					}
				});
			}
		});
		return button;
	});
