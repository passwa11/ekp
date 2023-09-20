define(
		["dojo/_base/declare","mui/search/SearchBar","dojo/_base/lang","mui/util","dojo/topic"],
		function(declare,SearchBar,lang,util,topic) {

			return declare(
					"mui.search.SearchDbBar",
					[SearchBar],{
						//重定向路径(均用于重定向数据库搜索)
						redirectURL : "",
						params : "",
						searchType : "",
						_onSearch : function(evt) {
							this.searchNode.blur(); 
							this._eventStop(evt);
							if (this.searchNode.value != '' || this.emptySearch) {
								var arguObj = lang.clone(this);
								arguObj.keyword = this.searchNode.value;
								if(typeof(this.searchType)!= "undefined"&&this.searchType=="db"){
									if(typeof(this.params)=='string'){
										this.searchUrl = this.redirectURL + encodeURIComponent(this.params);
									}
								}
								var _searchUrl =  this.searchUrl + "!{keyword}";
								var url =  util.formatUrl(util.urlResolver(
										_searchUrl, arguObj));
								

								topic.publish("/mui/search/submit",this, {keyword: arguObj.keyword , url:url});
							}
							return false;
						}
					});
		});
