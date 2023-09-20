define(
		["dojo/_base/declare","mui/search/SearchBar","dojo/_base/lang","mui/util",
			"dojo/topic","mui/search/SearchDbBar","dijit/registry","dojo/dom-construct"],
		function(declare,SearchBar,lang,util,topic,SearchDbBar,registry,domConstruct) {
			return declare("sys.lbpmperson.mobile.list.LbpmPersonSearchDbBar",[SearchDbBar],{
				_onSearch : function(evt) {
					this.searchNode.blur(); 
					this._eventStop(evt);
					if (this.searchNode.value != '' || this.emptySearch) {
						this.keyword = this.searchNode.value;
						if(typeof(this.searchType)!= "undefined"&&this.searchType=="db"){
							if(typeof(this.params)=='string'){
								this.searchUrl = this.redirectURL + this.params;
							}else{
								this.searchUrl = this.redirectURL;
							}
						}
						var url =  util.formatUrl(util.urlResolver(this.searchUrl, this));
					
						topic.publish("/mui/search/submit",this, {keyword: this.keyword , url:url});
					}
					return false;
				},
				
				_onfocus$: function(submit) {
			      this._onfocusCommon()
			      this._showPromptDb$(submit)
			    },	
			    
			    _showPromptDb$:function(submit){
			      if (!this.promptNode) {
		            this.promptNode = domConstruct.create(
		              "div",
		              {
		                className: "muiSearchPrompt fadeIn animated"
		              },
		              document.body
		            )

		            var self = this
		            require(["sys/lbpmperson/mobile/resource/js/search/_LbpmPersonSearchPrompt"], function(_LbpmPersonSearchPrompt) {
		              self._prompt = new _LbpmPersonSearchPrompt({
		                srcNodeRef: self.promptNode,
		                prefix: self.modelName,
		                modelName: self.modelName,
		                parent: self,
		                forPage: self.forPage,
		              })
		              self._prompt.startup();
		              if(self._prompt){
		            	  self._prompt.setTemplateStr();
				      }
		              if (submit) {
		                self._fillKeyword(null, self)
		              }
		            })
		          } else {
		            if (this._prompt) {
		              this._prompt.show()
		            }
		          }
			      if(this._prompt){
			    	  this._prompt.setTemplateStr();
			      }

		          var _self = this
		          var tmpEvt = this.connect(document.body, "touchend", function(evt) {
		            if (evt.target != _self.searchNode) {
		              setTimeout(function() {
		                _self.searchNode.blur()
		                _self.disconnect(tmpEvt)
		              }, 410)
		            }
		          })
			    }
			});
		});
