define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "dojo/topic", "sys/mportal/mobile/OpenProxyMixin","mui/rating/Rating","mui/i18n/i18n!sys-portal:sysMportalPicDisplay.tip.num" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase, util, topic, OpenProxyMixin, Rating, msg) {
			var item = declare("sys.mportal.ComplexPicDisplayItemMixin", [ ItemBase, OpenProxyMixin ], {

				baseClass : "muiPortalComplexPicDisplayList",

				href : '',

				label : '',

				icon : '',


				buildRendering : function() {

					this.inherited(arguments);
					
					if(this.picType.indexOf("big")>-1){
						domClass.add( this.domNode, "muiPortalBigpicBox");
					}else{
						domClass.add( this.domNode, "muiPortalSmallpicBox");
					}
					
					var imgNode = domConstruct.create('div', {
						className : 'lui_oppic'
					}, this.domNode);

					
					if(this.picType.indexOf("big")>-1){
						domClass.add( imgNode, "muiPortalBigpic");
					}else{
						domClass.add( imgNode, "muiPortalSmallPic");
					}
					
                    // 绑定DOM点击事件
					this.proxyClick(this.domNode, this.href, '_blank');
					
					var a = domConstruct.create("span", {
						className : "muiPortalComplexPicImg",
						style : {
							background : 'url(' + util.formatUrl(this.icon) + ') center center no-repeat',
							backgroundSize : 'cover'
						}
					}, imgNode);

					var infoNode = domConstruct.create('div', { className : 'muiPortalInfoBox' }, this.domNode);
					
					// 标题
					var subjectNameNode  = domConstruct.create('a', {
						className : 'muiPortalSubjectName muiFontSizeM muiFontColorInfo',
						href : 'javascript:;',
						innerHTML :  this.label
					}, infoNode);
					
					// 等级（五星图标）
					var widget = new Rating({
						value :  this.docScore==null?0.0:this.docScore,
					});
					infoNode.appendChild(widget.domNode);
						
					// 右侧DOM
					var rightNode  = domConstruct.create('div', { className:'muiPortalLearnedContainer muiFontSizeS muiFontColorMuted' }, infoNode);
					
					// 显示阅读数量( “N” 观看 )
			        if (this.count) {
			            this.countNode = domConstruct.create("span", { className:"muiPortalComplexPicRead", innerHTML:"<span class='muiPortalComplexPicReadNum'>"+this.count+"</span><span class='muiPortalComplexPicReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasRead"]+"</span>" }, rightNode);
			        }
			        
					// 显示 “N”人学习 ( 学习管理《课程列表》组件显示该信息 )
					if (this.fdLearnCount) {
						this.summary = domConstruct.create("span", { className:"muiPortalComplexPicRead", innerHTML:"<span class='muiPortalComplexPicReadNum'>"+this.fdLearnCount+"</span><span class='muiPortalComplexPicReadViewText'>"+msg["sysMportalPicDisplay.tip.num.hasLearned"]+"</span>" }, rightNode);
					}

					
				},

				_setLabelAttr : function(label) {
					if (label)
						this._set("label", label);
				}

			});
			return item;
		});