define(
		[ "./xhr", "dojo/_base/lang", "dojo/_base/declare",
				"dojo/store/util/QueryResults", "dojo/store/JsonRest", "mui/dialog/Tip", "mui/i18n/i18n!sys-mobile" ],
		function(xhr, lang, declare, QueryResults, JsonRest, Tip, Msg) {

			return declare(
					"mui.store.JsonRest",
					JsonRest,
					{

						defaultType : 'POST',

						query : function(query, options) {
							options = options || {};
							if (options.type)
								this.defaultType = options.type;
							var headers = lang.mixin({
								Accept : this.accepts
							}, this.headers, options.headers);
							var data = {};
							var hasQuestionMark = this.target.indexOf("?") > -1, postData = {};
							if (query && typeof query == "object") {
								if (this.defaultType.toLowerCase() == 'post') {
									data.postData = query;
									query = "";
								} else {
									query = xhr.objectToQuery(query);
									query = query ? (hasQuestionMark ? "&"
											: "?")
											+ query : "";
								}
							}
							if (options.start >= 0 || options.count >= 0) {
								headers["X-Range"] = "items="
										+ (options.start || '0')
										+ '-'
										+ (("count" in options && options.count != Infinity) ? (options.count
												+ (options.start || 0) - 1)
												: '');
								if (this.rangeParam) {
									query += (query || hasQuestionMark ? "&"
											: "?")
											+ this.rangeParam
											+ "="
											+ headers["X-Range"];
									hasQuestionMark = true;
								} else {
									headers.Range = headers["X-Range"];
								}
							}
							if (options && options.sort) {
								var sortParam = this.sortParam;
								query += (query || hasQuestionMark ? "&" : "?")
										+ (sortParam ? sortParam + '='
												: "sort(");
								for (var i = 0; i < options.sort.length; i++) {
									var sort = options.sort[i];
									query += (i > 0 ? "," : "")
											+ (sort.descending ? this.descendingPrefix
													: this.ascendingPrefix)
											+ encodeURIComponent(sort.attribute);
								}
								if (!sortParam) {
									query += ")";
								}
							}
							// #137964 URL参数超长会导致请求失败，这里将URL进行解析，参数使用请求体提交
							var __target = this.target;
							if(this.target.length > 2048 && this.target.indexOf(".jsp?") > -1) {
								var __url = this.target.split("?");
								var __params = __url[1].split("&");
								var params = [];
								for(var i=0; i<__params.length; i++) {
								    if(__params[i].length > 0) {
								        var temp = __params[i].split("=");
								        if(temp[1].length > 500) {
								            data.postData[temp[0]] = temp[1];
								        } else {
								            params.push(__params[i]);
								        }
								    }
								}
								__target = __url[0] + "?" + params.join("&");
							}
							lang.mixin(data, {
								url : __target + (query || ""),
								handleAs : "json",
								headers : headers
							})
							var results = xhr(this.defaultType, data);
							results.total = results
									.then(function() {
										var range = results.ioArgs.xhr
												.getResponseHeader("Content-Range");
										if (!range) {
											range = results.ioArgs.xhr
													.getResponseHeader("X-Content-Range");
										}
										return range
												&& (range = range
														.match(/\/(.*)/))
												&& +range[1];
									}, function () {
										var xhr = results.ioArgs.xhr;
										if(xhr.status == 401 || xhr.responseURL.indexOf("login.jsp") > -1) {
											// 出现异常（跳到登录页），刷新页面
											window.location.reload();
										} else if(xhr.status < 400) {
											// 其它异常，弹窗
											Tip.fail({text: Msg["mui.jsonRest.failure"], cover: true});
										}
									});
							return QueryResults(results);
						}
					});
		});