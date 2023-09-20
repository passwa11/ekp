define(		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dijit/_WidgetBase",
				"mui/util", "mui/dialog/Tip", "dojo/html",
				"dojo/text!./medal.jsp", "dojo/query", "dojo/_base/lang",
				"dojo/dom", "dojo/_base/array", "dojo/request", "dojo/topic",
				"dijit/registry", "dojo/window", "mui/i18n/i18n!kms-medal:kmsMedalMobile","dojox/mobile/_ItemBase" ],
		function(declare, domConstruct, domClass, domStyle, domAttr,
				widgetBase, util, tip, html, tmpl, query, lang, dom, array,
				request, topic, registry, win, msg,ItemBase) {

	return declare("kms.medal.list.item", [ ItemBase ], {


		// 标题
		fdName : '',

		// id
		fdId : '',

		//图片路径
		detailUrl : "/kms/medal/kms_medal_main/kmsMedalMain.do?method=getPicPath&fdKey=bigMedalPic&fdId=!{fdId}",

		//简介
		fdIntroduction :'',
		
		//分类名称
		cateName : '',
		
		//创建时间
		docCreateTime : '',
		
		//勋章获取时间
		docHonoursTime : '',
		
		//是否有效
		isValide : '',

		buildRendering : function() {

			this.inherited(arguments);

			domClass.add(this.domNode, 'mui_medal_list_content');

			// 构建勋章列表
			this.buildContentNode();
			

		},
		
		buildContentNode : function() {
			var imgNode = domConstruct.create('div', {
				className : 'muiMedalImg'
			}, this.domNode);
			
			if(this.isValide.indexOf("false")>-1){
				domClass.add(imgNode, 'status_abate');				
			}
			this.detailUrl = util.urlResolver(util.formatUrl(this.detailUrl), {
				"fdId" : this.fdId,
			});
			var a = domConstruct.create("span", {
				className : "muiMedalImgItem",
				style : {
					background : 'url(' + this.detailUrl
							+ ') center center no-repeat',
					backgroundSize : 'cover',
					height : '80px',
					width : '80px',
					display : 'inline-block'
				}
			}, imgNode);
			
			var infoNode = domConstruct.create('div', {
				className : 'muiPortalInfoBox'
			}, this.domNode);
			
			var subjectNameNode  = domConstruct.create('p', {
				className : 'muiPortalSubjectName',
				href : 'javascript:;',
				innerHTML : (this.isValide.indexOf("false")>-1?"<em class='status_abate_span'>"+msg['kmsMedalMobile.medal.disable']+"</em>":"")+this.fdName
			}, infoNode);
			
			this.connect(this.domNode, "onclick", 'medalClick');

		},
		
	
		// 勋章点击事件
		medalClick : function(evt) {
			this.defer(this.showMask, 350);
		},
		

		bindHideMask : function() {
			this.hideMaskNode.on('click', lang.hitch(this, this.hideMask));
		},

		buildMask : function() {
			if (this.container) {
				this._showMask();
				return;
			}
			var self = this;
			var dhs = new html._ContentSetter({
				parseContent : true,
				onBegin : function() {

					this.content = this.content
							.replace('!{fdId}',
									self.fdId)
							.replace('!{fdName}',
									self.fdName)
							.replace('!{fdIntroduction}',
									self.fdIntroduction)
							.replace('!{cateName}',
									self.cateName)
							.replace('!{docCreateTime}',
											self.docCreateTime.substring(0,10))
							.replace('!{docHonoursTime}',
									self.docHonoursTime.substring(0,10))
							.replace('!{isValide}',
											self.isValide.indexOf("false")>-1?"status_abate":"")
							.replace('!{imgUrl}',
									self.detailUrl)
							.replace('!{valideName}',
									self.isValide.indexOf("false")>-1?"<em class='status_abate_span'>"+msg['kmsMedalMobile.medal.disable']+"</em>":"");
					this.inherited("onBegin", arguments);
				}
			});
			this.container = domConstruct.create('div', {
				'className' : 'muiMedalMask'
			}, document.body, 'last');

			dhs.node = this.container;
			dhs.set(tmpl);
			dhs.tearDown();
			var self = this;
			setTimeout(function() {
				self.ulContainer = query('.mui_medal_detail_pop', self.container);
				self._showMask();
			}, 1);
		
			// 绑定收起按钮（收起按钮）
			this.hideMaskNode = query('.mui_medal_close', this.container);
			this.connect(this.container, 'onclick', this.hideNode);
			
			this.bindHideMask();
		},
		
		hideNode : function(evt) {
			// 点击下方黑色空白区域时关闭窗口
			if("muiMedalMask" == evt.target.className) {
				this.hideMask();
			}
		},

		_showMask : function() {
			domStyle.set(this.container, {
				'display' : 'block',
				'opacity' : 1
			});
			this
					.defer(
							function() {
								domStyle.set(this.domNode, {
									
								});
								this.ulContainer
										.style({
											'-webkit-transform' : 'translate3d(0px, 0px, 0px)',
											'opacity' : 1
										});
							}, 200);
		},

		_hideMask : function(fire) {
			this.ulContainer
					.style({
						'-webkit-transform' : ' perspective(1200px) rotateY(180deg) scale(0.01)',
						'opacity' : 0
					});
			this.defer(function() {
				domStyle.set(this.container, {
					'opacity' : 0
				});
				domStyle.set(this.domNode, {
					'display' : 'block'
				});
				if (!fire) {
					topic.publish(this.SELECTED_EVENT, {
						value : this.value
					});
					registry.byId('eval_star_opt').set('value',
							this.value);
				}
			}, 500);
			this.defer(function() {
				domStyle.set(this.container, {
					'display' : 'none'
				});
			}, 700);

		},

		destroyMask : function(fire) {
			if (!this.ulContainer)
				return;
			this._hideMask(fire);
		},

		// 显示遮罩
		showMask : function() {
			if (this.lock == true)
				return;
			this.set('lock', true);
			this.defer(function() {
				this.buildMask();
			}, 350);

		},

		// 隐藏遮罩
		hideMask : function(fire) {
			this.defer(function() {
				this.destroyMask(fire);
				this.set('lock', false);
			}, 350);
		},

		_setLabelAttr : function(label) {

			if (label)
				this._set("label", label);
		}

	});


});