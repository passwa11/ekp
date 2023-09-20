define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "dojo/topic", "sys/mportal/mobile/OpenProxyMixin", "mui/i18n/i18n!sys-mportal:sysMportalPicDisplay.tip.num" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				util, topic, OpenProxyMixin, msg) {
			var item = declare("sys.mportal.ComplexLItemMixin", [ ItemBase,
					OpenProxyMixin ], {

				baseClass : "muiPortalComplexLItem",

				href : '',

				label : '',

				icon : '',

				// 扩展字段，学习人数
				fdLearnCount : '',

				// 扩展字段，章节数
				catalogNum : '',

				summary : '',

				// 创建时间
				docCreateTimeInteval : '',

				// 创建时间
				created : '',

				buildRendering : function() {

					this.inherited(arguments);
                    
					// 构建a标签容器
					var a = domConstruct.create('a', { href:'javascript:;' , className:'muiPortalComplexLItemLink' }, this.domNode);
                    // 绑定a标签点击跳转链接
					this.proxyClick(a, this.href, '_blank');

					// 左侧图标
					var icon = domConstruct.create('span', null, a);
					if (this.context)
						this.icon = util.formatUrl(this.icon);
					domStyle.set(icon, { 'background-image' : 'url(' + this.icon + ')' });

					// 右侧容器
					var right = domConstruct.create('div', {}, a);
                    
					// 右侧上方标题
					var h2Node = domConstruct.create('h2', {
						className : 'muiPortalComplexLItemTitle muiFontSizeM muiFontColorInfo',
						innerHTML : this.label
					}, right);
                    
						
                    // 组件底部右侧基本信息容器
					var footer = domConstruct.create('div', { className : 'muiPortalComplexLItemFooter muiFontSizeS muiFontColorMuted'}, right);

					// 显示 共“N”章节 ( 学习管理《课程列表》组件显示该信息 )
					if (this.catalogNum) {
						var catalogNumNode = domConstruct.create('span', { className:"muiComplexlCatalog" }, footer);
						catalogNumNode.innerText = msg["sysMportalPicDisplay.tip.num.chapter"].replace("{0}",this.catalogNum);
					}

					// 显示创建人姓名
			        if (!this.catalogNum && this.creator) {
			            this.createdNode = domConstruct.create("span", { className:"muiComplexlCreated", innerHTML:this.creator }, footer)
			        }
					
					// 显示创建时间( 注：部分组件如《课程列表》显示的创建时间 “N”分钟前、“N”小时前...格式, 另外一些部件如《图文新闻》显示 YYYY-MM-DD格式 )
					if (!this.created)
						this.created = this.docCreateTimeInteval;
					if (this.created)
						domConstruct.create('span', {innerHTML:this.created }, footer);
					
					// 显示阅读数量( “N” 观看 )
			        if (this.count) {
			            this.countNode = domConstruct.create("span", { className:"muiComplexlRead", innerHTML:"<span class='muiComplexlReadNum'>"+this.count+"</span><span class='muiComplexlReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasRead"]+"</span>" }, footer);
			        }
			        
					// 培训人数
			        if (this.fdApplyCount) {
			            this.fdApplyCount = domConstruct.create("div", { className:"muiComplexlCatalog", innerHTML:"<span class='muiFontSizeS muiFontColorMuted'>"+this.fdApplyCount+"</span><span class='muiFontSizeSS muiFontColorMuted'>"+msg["sysMportalPicDisplay.tip.num.hasApplied"]+"</span>" }, right)
			        }
			        
					// 显示 “N”人学习 ( 学习管理《课程列表》组件显示该信息 )
					if (this.fdLearnCount) {
						this.summary = domConstruct.create("span", { className:"muiComplexlRead", innerHTML:"<span class='muiComplexlReadNum'>"+this.fdLearnCount+"</span><span class='muiComplexlReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasLearned"]+"</span>" }, footer);
					}

					
				},

				_setLabelAttr : function(label) {
					if (label)
						this._set("label", label);
				}

			});
			return item;
		});