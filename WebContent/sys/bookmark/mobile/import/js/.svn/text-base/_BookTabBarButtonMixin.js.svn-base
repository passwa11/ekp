define(
		[
				"dojo/_base/declare",
				"dojo/_base/lang",
				"dojo/request",
				"mui/util",
				"dojo/dom-class",
				"mui/dialog/Tip",
				"mui/i18n/i18n!sys-bookmark:sysBookmark.mechanism" ],
		function(declare, lang, req, util, domClass, Tip, Msg) {

			return declare(
					"mui.tabbar._BookTabBarButtonMixin",
					null,
					{
						// 判断是否已经收藏过
						isBookedUrl : '/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=isBooked',
						// 新增收藏
						addBookedUrl : '/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=save&forward=lui-source',
						// 取消收藏
						delBookedUrl : '/sys/bookmark/sys_bookmark_main/sysBookmarkMain.do?method=deleteBookmark',

						isBooked : false,

                        //源页面文档标题 #165369 切换收藏页面subject会更改页面的title为“选择收藏夹”,故此处存储源页面标题
						sourceSubject : null,

						subject : null,

						url : null,

						modelId : null,

						modelName : null,

						lock : true,

						fdId : null,

						scaleClass : 'muiBookMaskScale',

						bookedClass : 'mui-star-on',

						unBookedClass : 'mui-star-off',

						buildRendering : function() {

							this.inherited(arguments);
						},

						_setSubjectAttr : function(subject) {

							this.subject = this.getBookmarkSubject(subject);
						},

						startup : function() {
							if (this._started)
								return;

							//预先获取源页面标题
							this.sourceSubject = this.subject;

							this.inherited(arguments);

							this.url = this.getBookmarkUrl();
							// 更换是否已经收藏过图标
							req(util.formatUrl(this.isBookedUrl), {
								handleAs : 'json',
								method : 'post',
								data : {
									url : this.url,
									fdId : this.modelId,
									fdModelName : this.modelName
								}
							}).then(lang.hitch(this, function(data) {

								if (data && data.isBooked)
									this.toggleBooked(data);
								this.set("lock", false);
							}));
						},

						replaceClass : function(flag) {

							var i1 = this["iconNode" + 1], i2 = this["iconNode" + 2];
							var class1 = flag ? this.bookedClass
									: this.unBookedClass, class2 = flag ? this.unBookedClass
									: this.bookedClass;
							domClass.replace(i1,
									class1 + ' ' + this.scaleClass, class2
											+ ' ' + this.scaleClass);
							domClass.replace(i2,
									class1 + ' ' + this.scaleClass, class2
											+ ' ' + this.scaleClass);
						},

						replaceLabel : function(flag) {

							if (flag)
								this.labelNode.innerHTML = Msg['sysBookmark.mechanism.cancel'];
							else
								this.labelNode.innerHTML = Msg['sysBookmark.mechanism.bookMark'];
						},

						removeScaleClass : function() {

							var i1 = this["iconNode" + 1], i2 = this["iconNode" + 2];
							this.defer(lang.hitch(this, function() {

								domClass.remove(i1, this.scaleClass);
								domClass.remove(i2, this.scaleClass);
							}), 300);
						},

						toggleBooked : function(data) {

							if (data.isBooked)
								this.set('fdId', data.fdId);
							else
								this.set('isBooked', false);
							if (this.icon1) {
								this.replaceClass(data.isBooked);
								this.removeScaleClass();
							}
							if (this.label)
								this.replaceLabel(data.isBooked);
							this.set('isBooked', data.isBooked);
							this.set('lock', false);
						},

						rq : function(url, data, handleAs, callback) {

							req(util.formatUrl(url), {
								handleAs : handleAs,
								method : 'post',
								data : data
							}).then(lang.hitch(this, callback));
						},

						// 切换收藏
						onClick : function(evt) {
							return this.doMark(evt);
						},

						doMark:function(evt,callback){
							//点击时再获取标题
							this.subject = this.getBookmarkSubject("");
							//console.log(this.subject);
							if (this.lock)
								return false;

							this.set('lock', true);
							var curIds = null,curNames=null;
							var successTip = Msg['sysBookmark.mechanism.add'];
							if(evt){
								curIds = evt.curIds;
								curNames = evt.curNames;
								successTip = Msg['sysBookmark.mechanism.add.success'];
							}
							if (!this.isBooked)
								this.rq(this.addBookedUrl, {
									docSubject : this.subject,
									fdModelId : this.modelId,
									fdUrl : this.url,
									fdModelName : this.modelName,
									docCategoryId : curIds || "",
									docCategoryName : curNames || ""
								}, 'json', function(data) {

									this.toggleBooked({
										isBooked : true,
										fdId : data.fdId
									});
									Tip.tip({
										icon : 'mui ' + this.bookedClass,
										text : successTip,
										time : 1000,
										callback : callback || null
									});

								});
							else
								this
									.rq(
										this.delBookedUrl,
										{
											bookmarkId : this.fdId
										},
										'json',
										function(data) {

											this.toggleBooked({
												isBooked : false
											});
											Tip
												.tip({
													icon : 'mui '
														+ this.unBookedClass,
													text : Msg['sysBookmark.mechanism.cancel'],
													time : 1000,
													callback : callback || null
												});
										});
							return false;
						},

						// 获取当前文档标题
						getBookmarkSubject : function(subject) {
							if (subject.length < 1) {
								//源文档标题
								if(!!this.sourceSubject){
									return this.sourceSubject;
								}
								var title = document.getElementsByClassName("muiDocViewHeaderSubject");
								if(!title || title.length < 1){
									title = document.getElementsByTagName("title");
								}
								if (title != null && title.length > 0) {
									subject = title[0].text || title[0].textContent;
									if (subject == null) {
										subject = "";
									} else {
										subject = subject.replace(
												/(^\s*)|(\s*$)/g, "");
									}
								}
							}
							return subject;
						},

						clearParam : function(url, key) {

							// 去除移动端特有标志
							var re = new RegExp();
							re.compile("([\\?&]" + key + "=)[^&]*", "i");
							if (re.test(url))
								url = url.replace(re, "");
							return url;

						},

						// 获取当前文档url
						getBookmarkUrl : function() {

							var context = dojoConfig.baseUrl.substring(0,
									dojoConfig.baseUrl.length - 1);
							var url = window.location.href;
							url = url.substring(url.indexOf('//') + 2,
									url.length);
							url = url.substring(url.indexOf('/'), url.length);
							if (context.length > 1) {
								url = url.substring(context.length, url.length);
							}
							// #132105 由于后台匹配收藏链接是完全匹配，访问修订版本时需要去掉后缀
							if (url.indexOf("viewPattern=edition") > -1) {
								url = url.replace("&viewPattern=edition", "");
							}

							// 去除移动端特有标志

							url = this.clearParam(url, '_mobile');
							url = this.clearParam(url, '_referer');
							url = this.clearParam(url, '_time');

							return url;
						}
					});
		});