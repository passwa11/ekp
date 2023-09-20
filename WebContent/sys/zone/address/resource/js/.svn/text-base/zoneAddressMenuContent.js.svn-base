/**
 * tab选项面板,目前提供1个基类面板和3个拓展面板 
 * 1.AbstractZoneAddressContent:基类面板
 * 2.OrgZoneAddressContent:组织架构 
 * 3.InnerZoneAddressContent:有事找人
 * 4.OuterZoneAddressContent:外部伙伴
 */
define(function(require, exports, module) {

	var base = require("lui/base");
	var layout = require('lui/view/layout');
	var toolbar = require('lui/toolbar');
	var dialog = require('lui/dialog');
	var lang = require('lang!');
	var uilang = require('lang!sys-ui');
	var zonelang = require('lang!sys-zone');
	var orglang = require('lang!sys-organization');
	var topic = require('lui/topic');
	var constant = require('sys/zone/address/resource/js/zoneAddressConstant');
	var $ = require("lui/jquery");
	var qrcode = require("lui/qrcode");
	var env = require("lui/util/env");
	var str = require("lui/util/str");
	var MapObj = base.Base.extend({
		initProps : function($super, cfg) {
			this.keys = new Array();
			this.data = new Object();
		},

		put : function(key, value) {
			if (this.data[key] == null) {
				this.keys.push(key);
			}
			this.data[key] = value;
		},

		/**
		 * 获取某键对应的值
		 * 
		 * @param {String}
		 *            key
		 * @return {Object} value
		 */
		get : function(key) {
			return this.data[key];
		},

		/**
		 * 获取键值对数量
		 */
		size : function() {
			return this.keys.length;
		},

		values : function() {
			var rtnValues = new Array();
			for (var i = 0; i < this.keys.length; i++) {
				rtnValues.push(this.data[this.keys[i]]);
			}
			return rtnValues;
		}
	});

	var AbstractCommonContent = base.Container.extend({
		layoutFile : null,

		initProps : function($super, cfg) {
			this.parent = cfg.parent;
			this.commonLang = constant.commonlang
			this.startup();
		},

		startup : function() {
			if (!this.layout && this.layoutFile) {
				if (this._getExt(this.layoutFile) == 'script') {
					this.layout = new layout.Javascript({
						src : require.resolve(this.layoutFile),
						parent : this
					});
				} else {

					this.layout = new layout.Template({
						src : require.resolve(this.layoutFile),
						parent : this
					});
				}
				this.layout.startup();
				this.children.push(this.layout);
			}
		},

		doLayout : function(obj) {
			if (this.content) {
				this.content.html($(obj));
			}
			this.afterLayout();
		},

		afterLayout : function() {
			//
		},

		show : function() {
			this.content.show();
		},

		hide : function() {
			this.content.hide();
		},

		// 返回后缀名
		_getExt : function(file) {
			var ext = file.substring(file.lastIndexOf('.')).toLowerCase();
			if (ext.indexOf('js') > -1) {
				return 'srcipt';
			}
			if (ext.indexOf('html') > -1 || ext.indexOf('jsp')) {
				return 'html';
			}
			return 'html';
		},

	});

	var OrgTreeNode = base.Base.extend({
		initProps : function($super, cfg) {
			this.treeView = cfg.treeView;
			this.id = cfg.id;
			this.text = cfg.text;
			this.parent = null;
			this.isCurrent = cfg.isCurrent;
			this.nodeType = cfg.nodeType;
			this.parentsName = cfg.parentsName;
			if (this.nodeType == "8") {
				this.isLeader = cfg.isLeader;
				this.postName = cfg.postName;
				this.staffingLevelName = cfg.staffingLevel;
				this.img = cfg.img;
				this.personType = cfg.personType;
			} else {
				this.personNum = cfg.personNum;
			}
			this.children = [];
			this.rootNode = this.treeView.treeRoot;
			this.isExpanded = false;
		},

		appendChild : function(nodeCfg) {
			this.addChild(null, nodeCfg);
		},

		addChild : function(parentId, nodeCfg) {
			var parentNode = this;
			if (parentId != null) {
				parentNode = this.treeView.getNodeById(parentId);
			}
			if (parentNode != null) {
				var childNode = new OrgTreeNode(nodeCfg);
				if (childNode.isCurrent == "true") {
					this.treeView.currentNodeId = childNode.id;
				}
				childNode.parent = parentNode;
				parentNode.children.push(childNode);
				this.treeView.allTreeNodes.put(childNode.id, childNode);
				if (childNode.nodeType == "8") {
					this.treeView.allPersonNodes.put(childNode.id, childNode);
				} else {
					parentNode.isExpanded = true;
				}
			}
		}
	});

	var OrgTreeView = AbstractCommonContent.extend({
				initProps : function($super, cfg) {
					$super(cfg);
					this.eleDom = this.parent.treeviewDom;
					this.dataUrl = cfg.dataUrl;
					this.currentNode = null;
					this.currentNodeId = "";
					this.treeRoot = new OrgTreeNode({
						id : "root",
						text : "root",
						treeView : this
					});
					this.allTreeNodes = new MapObj();
					this.allPersonNodes = new MapObj();
					this.startup();
				},

				startup : function($super) {
					topic.subscribe(constant.event['CHILD_MIDDLE_CHANGED'], this.childMiddleChanged, this);
					topic.subscribe(constant.event['SEARCH_CLEARED'], this.clearCurrentNodeInfo, this);
					topic.subscribe(constant.event['SEARCH_CHANGED'], this.clearCurrentNodeInfo, this);
					topic.subscribe(constant.event['MENU_CHANGED'], this.clearCurrentNodeInfo, this);
				},

				clearCurrentNodeInfo : function(evt) {
					this.currentNode = null;
				},

				childMiddleChanged : function(evt) {
					var orgType = evt.orgType;
					if (orgType == "dept") {
						this.clickNode(this.eleDom.find("[data-lui-nodeid='" + evt.orgId + "']"), evt.orgId);
					}
				},

				show : function() {
					var initialKmssData = new KMSSData();
					initialKmssData.AddBeanData(Com_ReplaceParameter(
							this.dataUrl, {
								id : ""
							}));
					var initialUserDeptData = initialKmssData.GetHashMapArray();
					this.convertRtnDataToTreeNode(initialUserDeptData);
					this.eleDom.html(this.generateTreeHTML(this.treeRoot));
					this.eleDom.find("li").click(
							this,
							function(event) {
								var orgTreeView = event.data;
								orgTreeView.clickNode(this, $(this).attr("data-lui-nodeid"));
							});
					var currentNode = this.eleDom.find("[data-lui-nodeid='" + this.currentNodeId + "']");
					currentNode.removeClass("open");
					this.clickNode(currentNode, this.currentNodeId);
				},

				convertRtnDataToTreeNode : function(rtnData) {
					for (var i = 0; i < rtnData.length; i++) {
						this.addTreeNode(rtnData[i]);
					}
				},

				addTreeNode : function(nodeData) {
					nodeData.treeView = this;
					if (nodeData.parentId == "root") {
						this.treeRoot.appendChild(nodeData);
					} else {
						var parentNode = this.getNodeById(nodeData.parentId);
						if(parentNode!=undefined){
							parentNode.appendChild(nodeData);
						}
					}
				},

				getNodeById : function(nodeId) {
					return this.allTreeNodes.get(nodeId);
				},

				generateTreeHTML : function(parentNode) {
					var treeHTML = "<ul>";
					for (var i = 0; i < parentNode.children.length; i++) {
						treeHTML += this.generateInnerHTML(parentNode.children[i]);
					}
					if (treeHTML == "<ul>") {
						parentNode.hasChild = false;
					}
					return treeHTML + "</ul>";
				},

				generateInnerHTML : function(treeNode) {
					if (treeNode.nodeType == 8) {
						// 人员不组装树
						return "";
					}
					var innerHTML = "<li data-lui-nodeid='" + treeNode.id + "' class='folder";
					if (treeNode.isExpanded) {
						innerHTML += " open";
					}
					var hasChildren = false;
					if (treeNode.children.length > 0) {
						hasChildren = true;
					}
					innerHTML += "'>";
					innerHTML += "<a href='javascript:void(0);' haschild='" + (hasChildren ? "true" : "false") + "'>" + treeNode.text + "</a></li>";
					if (hasChildren) {
						innerHTML += "<ul"
						if ("true" == treeNode.show) {
							innerHTML += " show='true'";
						}
						innerHTML += ">";
						for (var i = 0; i < treeNode.children.length; i++) {
							innerHTML += this.generateInnerHTML(treeNode.children[i]);
						}
						innerHTML += "</ul>";
					}
					return innerHTML;
				},

				clickNode : function(obj, nodeId) {
					var nodeClick = this.getNodeById(nodeId);
					this.eleDom.find(".current").removeClass("current");
					$(obj).addClass("current");
					if ((nodeClick!=undefined) && (!nodeClick.isExpanded)) {
						// 加载下级节点
						var kmssData = new KMSSData();
						kmssData.AddBeanData(Com_ReplaceParameter(this.dataUrl,
								{
									id : nodeId
								}));
						var rtnData = kmssData.GetHashMapArray();
						this.convertRtnDataToTreeNode(rtnData);
						$(this.generateTreeHTML(nodeClick)).insertAfter($(obj));
						if (nodeClick.hasChild == false) {
							$(obj).removeClass("folder");
						} else {
							$(obj).addClass("open");
						}
						$(obj).next("ul").find("li").click(
								this,
								function(event) {
									var orgTreeView = event.data;
									orgTreeView.clickNode(this, $(this).attr("data-lui-nodeid"));
								});
						nodeClick.isExpanded = true;
					} else {
						if ($(obj).hasClass("folder")) {
							if ($(obj).hasClass("open")) {
								$(obj).removeClass("open");
								$(obj).next().hide();
							} else {
								$(obj).addClass("open");
								$(obj).next().show();
							}
						}
					}
					$(obj).parent().prev().addClass("open");
					$(obj).parent().prev().next().show();
					if (this.currentNode == null || nodeClick.id != this.currentNode.id) {
						this.currentNode = nodeClick;
						topic.publish(constant.event['ORG_MENU_CHANGED'], {
							orgNode : this.currentNode,
							treeView : this,
							menuFlag:	"orgTree"
						});
					}
				}
			});

	var OrgLeftMenuContent = AbstractCommonContent.extend({
		layoutFile : './tmpl/zoneAddressOrgLeftMenuContent.html#',

		initProps : function($super, cfg) {
			$super(cfg);
			if (this.parent.parent.leftMenuContentDom) {
				this.parentDom = this.parent.parent.leftMenuContentDom;
				this.content = this.parentDom.find("[data-lui-menucontentid='" + this.parent.menuId + "']");
			}
		},

		afterLayout : function() {
			// 构建组织架构的树
			this.content.addClass("lui_zone_address_orgContent treeview");
			this.treeviewDom = this.content.find("[data-lui-mark='lui-zone-address-treeview']");
			if (this.treeviewDom.length > 0) {
				//add #53554 URL带参可默认定位到顶级或默认定位到当前部门
				var top = $('#zoneAddress').attr('top');
				this.orgTreeview = new OrgTreeView({
					parent : this,
					dataUrl : "sysZoneAddressTree&parent=!{id}&orgType=11&top="+top
				});
				window[this.parent.menuId] = this.orgTreeview;
				this.orgTreeview.show();
			}
		}
	});

	var ChildMiddleContent = AbstractCommonContent.extend({
				layoutFile : './tmpl/zoneAddressChildMiddleContent.html#',

				initProps : function($super, cfg) {
					$super(cfg);
					if (cfg.parentDom) {
						this.parentDom = cfg.parentDom;
						this.content = this.parentDom.find(".lui-zone-address-child-middle-content");
						this.content.addClass("lui_zone_address_child_middle");
					}
				},

				startup : function($super) {
					$super();
					topic.subscribe(constant.event['ORG_MENU_CHANGED'], this.orgMenuChanged, this);
					topic.subscribe(constant.event['FINDER_MENU_CHANGED'], this.finderMenuChanged, this);
					topic.subscribe(constant.event['FINDER_MENU_DRAWED'], this.clearContent, this);
					topic.subscribe(constant.event['SEARCH_CHANGED'], this.searchWordChanged, this);
					topic.subscribe(constant.event['MENU_CHANGED'], this.clearContent, this);
				},

				clearContent : function(evt) {
					this.currentClickId = null;
					this.content.html("");
				},

				doSearch : function(evt) {
					var self = evt.data;
					if (self.searchWord != null && $.trim(self.searchWord).length > 0) {
						var kmssData = new KMSSData();
						var personParams = $.param({
							paramType : "search",
							searchWord : self.searchWord
						});
						kmssData.AddBeanData("sysZonePersonInfoProvider&" + personParams);
						var rtnData = kmssData.GetHashMapArray();
						self.content.html(self.generateSearchMiddleHTML(rtnData));
						self.content
								.find(".lui_zone_address_personItem")
								.parent()
								.click(self, function(event) {
										var child1Content = event.data;
										child1Content.clickPerson(
											$(this).children().attr("data-lui-personid"),
											$(this).children().attr("data-lui-persontype"),
											"",
											$(this).parent().parent().find(".active").removeClass("active"),
											$(this).addClass("active"));
										});
					} else {
						self.content.html("");
						topic.publish(constant.event['SEARCH_CLEARED'], {});
					}
					self.show();
				},

				searchWordChanged : function(searchWord) {
					this.currentClickId = null;
					var self = this;
					self.searchWord = searchWord;
					if (self.searchTimer) {
						clearTimeout(self.searchTimer);
					}
					self.searchTimer = setTimeout(function() {
						self.doSearch({
							data : self
						});
						self.searchTimer = null;
					}, 500);
				},

				generateSearchMiddleHTML : function(personInfos) {
					var innerPersons = new MapObj();
					var outerPersons = new MapObj();
					if (personInfos.length > 0) {
						for (var i = 0; i < personInfos.length; i++) {
							var personInfo = personInfos[i];
							if (personInfo.personType == "inner") {
								innerPersons.put(personInfo.personId, personInfo);
							} else if (personInfo.personType == "outer") {
								outerPersons.put(personInfo.personId, personInfo);
							}
						}
					}
					var searchMiddleHTML = "<dl class='lui_zone_address_org'>";
					if(personInfos.length==0){
						searchMiddleHTML="<dt><p class='noData'>" +zonelang['zoneAddress.noData'] + "</p>";
					}
					var innerPersonsArray = innerPersons.values();
					if (innerPersonsArray.length > 0) {
						searchMiddleHTML += "<dt><span>" + zonelang['zoneAddress.colleagues'] + "</span><dt>";
						for (var i = 0; i < innerPersonsArray.length; i++) {
							if(!innerPersonsArray[i].staffingLevelName){
								innerPersonsArray[i].staffingLevelName="";
							}
							if(!innerPersonsArray[i].parentsName){
								innerPersonsArray[i].parentsName="";
							}
							var deptNames=(innerPersonsArray[i].deptName).split("_");
							var lastName =deptNames[deptNames.length-1];
							searchMiddleHTML += this.generatePersonInfoHTML(
									innerPersonsArray[i].personType,
									innerPersonsArray[i].personId,
									innerPersonsArray[i].personImg,
									innerPersonsArray[i].personName,
									innerPersonsArray[i].staffingLevelName,
									lastName,
									innerPersonsArray[i].parentsName);
						}
					}
					var outerPersonsArray = outerPersons.values();
					if (outerPersonsArray.length > 0) {
						searchMiddleHTML += "<dt><span>" + zonelang['zoneAddress.outter.partner'] + "</span><dt>";
						for (var i = 0; i < outerPersonsArray.length; i++) {
							if(!outerPersonsArray[i].postName){
								outerPersonsArray[i].postName=""
							}
							if(!outerPersonsArray[i].parentsName){
								outerPersonsArray[i].parentsName="";
							}
							outerPersonsArray[i].personImg="../address/resource/images/head.png";
							//outerPersonsArray[i].postName =outerPersonsArray[i].personMemo;
							searchMiddleHTML += this.generatePersonInfoHTML(
									outerPersonsArray[i].personType,
									outerPersonsArray[i].personId,
									outerPersonsArray[i].personImg,
									outerPersonsArray[i].personName,
									outerPersonsArray[i].postName
									,"",
									outerPersonsArray[i].parentsName);
						}
					}
					return searchMiddleHTML + "</dl>";
				},

				clickDept : function(deptId) {
					if (this.currentClickId == null || this.currentClickId != deptId) {
						this.currentClickId = deptId;
						topic.publish(constant.event['CHILD_MIDDLE_CHANGED'], {
							orgType : "dept",
							orgId : deptId
						});
					}
				},

				clickPerson : function(personId, personType,itemId) {
					if (this.currentClickId == null || this.currentClickId != personId) {
						this.currentClickId = personId;
						topic.publish(constant.event['CHILD_MIDDLE_CHANGED'], {
							orgType : "person",
							orgId : personId,
							personType : personType,
							menuFlag:	this.menuFlag,
							itemId:itemId
						});
					}
				},

				generateFinderMiddleHTML : function(cateNode) {
					var catePersons = cateNode.catePersons;
					var finderMiddleHTML = "<h3 class='department_name' cateId='"+cateNode.cateId+"'>"
							+ str.decodeOutHTML(cateNode.cateName) + "<em>" + catePersons.length
							+ "</em>" + zonelang['zoneAddress.common.staff'] + "</h3>";
					if (catePersons.length > 0) {
						finderMiddleHTML += "<dl class='lui_zone_address_org'>";
						finderMiddleHTML += "<dt><span>" + zonelang['zoneAddress.member'] + "</span><dt>";
					}
					for (var i = 0; i < catePersons.length; i++) {
						if("outer"==catePersons[i].personType){
							catePersons[i].personImg="../address/resource/images/head.png";
						}
						if(!catePersons[i].personDept){
							catePersons[i].personDept="";
							}
						if(!catePersons[i].parentsName){
							catePersons[i].parentsName="catecate";
						}
						finderMiddleHTML += this.generatePersonInfoHTML(
								catePersons[i].personType,
								catePersons[i].personId,
								catePersons[i].personImg,
								catePersons[i].personName,
								catePersons[i].personDept,
								"",
								catePersons[i].parentsName);
					}
					return finderMiddleHTML + "</dl>";
				},

				finderMenuChanged : function(evt) {
					var cateNode = evt.finderNode;
					var itemId = cateNode.cateId;
					this.content.html(this.generateFinderMiddleHTML(cateNode));
					this.content
							.find(".lui_zone_address_personItem")
							.parent()
							.click(
									this,
									function(event) {
										var childMiddleContent = event.data;
										childMiddleContent.clickPerson($(this)
												.children().attr(
														"data-lui-personid"),
												$(this).children().attr(
														"data-lui-persontype"),
														itemId,
												$(this).parent().parent().find(".active").removeClass("active"),
												$(this).addClass("active"));
									});
				},

				orgMenuChanged : function(evt) {
					this.currentClickId = null;
					var selectedNode = evt.orgNode;
					this.treeView = evt.treeView;
					this.menuFlag=evt.menuFlag;
					if(selectedNode!=undefined){
						this.content.html(this.generateOrgMiddleHTML(selectedNode));
					}
					this.content
							.find(".lui_zone_address_personItem")
							.parent()
							.click(
									this,
									function(event) {
										var childMiddleContent = event.data;
										childMiddleContent.clickPerson($(this)
												.children().attr(
														"data-lui-personid"),
												$(this).children().attr(
														"data-lui-persontype"),
														"",
												$(this).parent().parent().find(".active").removeClass("active"),
												$(this).addClass("active"));
									});
					this.content.find(".lui_zone_address_depItem").parent()
							.click(
									this,
									function(event) {
										var childMiddleContent = event.data;
										childMiddleContent.clickDept($(this)
												.children().attr(
														"data-lui-deptid"));
									});
				},

				generateOrgMiddleHTML : function(orgNode) {
					var leaders = new MapObj();
					var childDepts = new MapObj();
					var childPersons = new MapObj();
					//#43954 添加count、totalNum变量用于判断组织架构全为人(如果考虑负责人则在部门哪里添加一个标识)
					var count = 0;
					var totalNum=0;
					if (orgNode!=undefined && (orgNode.children.length > 0)) {
						for (var i = 0; i < orgNode.children.length; i++) {
							var childNode = orgNode.children[i];
							if (childNode.nodeType == "8") {
								if (childNode.isLeader == "true") {
									leaders.put(childNode.id, childNode);
								} else {
									childPersons.put(childNode.id, childNode);
								}
							} else {
								childDepts.put(childNode.id, childNode);
								//count++;
							}
						}
						//totalNum=leaders.size()+childPersons.size();
					}
					if(orgNode!=undefined){
						var personNum = 0;
						$.each(orgNode.children, function(i, n) {
							if(n.nodeType == '1' || n.nodeType == '2')
								personNum += parseInt(n.personNum);
							else
								personNum++;
						});
						var orgMiddleHTML = "<h3 class='department_name'>"
								+ orgNode.text + "<em>" + personNum
								+ "</em>" + zonelang['zoneAddress.common.staff'] + "</h3>";
						orgMiddleHTML += "<dl class='lui_zone_address_org'>";
					}
					var leadersArray = leaders.values();
					if (leadersArray.length > 0) {
						orgMiddleHTML += "<dt><span>" + zonelang['zoneAddress.principal'] + "</span><dt>";
						for (var i = 0; i < leadersArray.length; i++) {
							if(!leadersArray[i].staffingLevelName){
								leadersArray[i].staffingLevelName="";
							}
							if(!leadersArray[i].parentsName){
								leadersArray[i].parentsName="";
							}
							orgMiddleHTML += this.generatePersonInfoHTML(
									leadersArray[i].personType,
									leadersArray[i].id, 
									leadersArray[i].img,
									leadersArray[i].text,
									leadersArray[i].staffingLevelName,
									"",
									leadersArray[i].parentsName);
						}
					}
					var childPersonsArray = childPersons.values();
					if (childPersonsArray.length > 0) {
						orgMiddleHTML += "<dt><span>" + zonelang['zoneAddress.member'] + "</span><dt>";
					}
					for (var i = 0; i < childPersonsArray.length; i++) {
						if(!childPersonsArray[i].staffingLevelName){
							childPersonsArray[i].staffingLevelName="";
						}
						if(!childPersonsArray[i].parentsName){
							childPersonsArray[i].parentsName="";
						}
						orgMiddleHTML += this.generatePersonInfoHTML(
								childPersonsArray[i].personType,
								childPersonsArray[i].id,
								childPersonsArray[i].img,
								childPersonsArray[i].text,
								childPersonsArray[i].staffingLevelName,
								"",
								childPersonsArray[i].parentsName);
					}
					var childDeptsArray = childDepts.values();
					if (childDeptsArray.length > 0) {
						orgMiddleHTML += "<dt><span>" + zonelang['zoneAddress.sub.architecture'] + "</span><dt>";
						for (var i = 0; i < childDeptsArray.length; i++) {
							//判断childDeptsArray[i].personNum是否未定义
							orgMiddleHTML += this.generateDeptInfoHTML(
									childDeptsArray[i].id,
									childDeptsArray[i].text,
									childDeptsArray[i].personNum,
									childDeptsArray[i].nodeType,
									childDeptsArray[i].config.fdIsExternal);
						}
					}
					return orgMiddleHTML + "</dl>";
				},

				generateDeptInfoHTML : function(deptId, deptName, deptPersonNum, nodeType, isExternal) {
					// 如果需要区分部门和机构的图标，可以根据nodeType来处理
					var deptInfoHTML = "<dd>";
					deptInfoHTML += "<div class='lui_zone_address_depItem' data-lui-deptid='" + deptId + "'>";
					deptInfoHTML += "<div class='depImg'>";
					// 部门图标
					var iconClass = "icon_depart";
					if(isExternal && isExternal == "true") {
						iconClass = "icon_depart_external";
					}
					deptInfoHTML += "<span class='" + iconClass + "'></span>";
					deptInfoHTML += "</div>";
					deptInfoHTML += "<div class='depInfo'>";
					deptInfoHTML += "<p class='dep_name'>" + deptName + "</p>";
					deptInfoHTML += "<p class='dep_num'>" + deptPersonNum + zonelang['zoneAddress.common.staff'] + "</p>";
					deptInfoHTML += "</div>";
					deptInfoHTML += "</div>";
					return deptInfoHTML + "</dd>";
				},

				generatePersonInfoHTML : function(personType, personId,
						personImg, personName, postName,lastDeptName,parentsName) {
					// 外部人员需要再次处理
					if(personType == "outer") {
						personName = env.fn.formatText(personName);
						lastName = env.fn.formatText(lastName);
						postName = env.fn.formatText(postName);
						parentsName = env.fn.formatText(parentsName);
					}
					var personInfoHTML = "<dd>";
					if(personId && personId!=""){
						personInfoHTML += "<div title='" + parentsName + "'  class='lui_zone_address_personItem' data-lui-persontype='"
								+ personType
								+ "' data-lui-personid='"
								+ personId
								+ "'>";
	
						personInfoHTML += "<div class='personImg'>";
						personInfoHTML += "<img src='" + personImg + "' alt=''>";
						personInfoHTML += "</div>";
						personInfoHTML += "<div class='personInfo'>";
						var lastName=lastDeptName;
						if(lastName || ""!=lastName){
							personInfoHTML += "<p class='person_name'>"  + personName + " -" + lastName + "</p>";
						}else{
							personInfoHTML += "<p class='person_name'>" + personName + "</p>";
						}
						
						personInfoHTML += "<p class='person_dep'>" + postName + "</p>";
						personInfoHTML += "</div>";
						personInfoHTML += "</div>";
					}
					return personInfoHTML + "</dd>";
				},
				
			});
	
	var ChildRightContent = AbstractCommonContent.extend({
				layoutFile : './tmpl/zoneAddressChildRightContent.html#',
				hideQrCode : '',

				initProps : function($super, cfg) {
					$super(cfg);
					if (cfg.parentDom) {
						this.parentDom = cfg.parentDom;
						this.content = this.parentDom.find(".lui-zone-address-child-right-content");
						this.content.addClass("lui_zone_address_child_right");
					}
					this.hideQrCode = cfg.hideQrCode;
				},

				startup : function($super) {
					$super();
					topic.subscribe(constant.event['CHILD_MIDDLE_CHANGED'], this.childMiddleChanged, this);
					topic.subscribe(constant.event['SEARCH_CLEARED'], this.reDrawDefault, this);
					topic.subscribe(constant.event['SEARCH_CHANGED'], this.reDrawDefault, this);
					topic.subscribe(constant.event['ORG_MENU_CHANGED'], this.reDrawDefault, this);
					topic.subscribe(constant.event['MENU_CHANGED'], this.reDrawDefault, this);
					topic.subscribe(constant.event['FINDER_MENU_DRAWED'], this.reDrawDefault, this);
				},

				reDrawDefault : function(evt) {
					this.isDrawed = false;
					this.draw();
				},

				childMiddleChanged : function(evt) {
					var orgType = evt.orgType;
					if (orgType == "dept") {
						this.draw();
					} else if (orgType == "person") {
						this.isDrawed = false;
						var kmssData = new KMSSData();
						var personParams = $.param({
							paramType : "fixed",
							personType : evt.personType,
							personId : evt.orgId,
							menuFlag:evt.menuFlag,
							cateId:evt.itemId
						});
						kmssData.AddBeanData("sysZonePersonInfoProvider&"
								+ personParams);
						var rtnData = kmssData.GetHashMapArray();
						if (rtnData.length == 1) {
							var personInfo = rtnData[0];
							this.content.html(this
									.generateRightHTML(personInfo));
							if(this.hideQrCode == 'false')
								this.generateQrCode(personInfo);
							else
								$(".lui_personal_code").hide();
							
							var info = this.content.find("#item_name_opera");
							info.click(function() {
								info.removeClass("item_detail_all");
							});
						}
					}
					this.show();
				},

				hideQrCode : function() {
					$("#qrcode").parent().hide();
				},

				toUTF8 : function(srcStr) {
					return srcStr;
/*//					var out, i, len, c;
//					out = "";
//					var srcStr = srcStr||'';
//					len = srcStr.length;
//					for (i = 0; i < len; i++) {
//						c = srcStr.charCodeAt(i);
//						if ((c >= 0x0001) && (c <= 0x007F)) {
//							out += srcStr.charAt(i);
//						} else if (c > 0x07FF) {
//							out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
//							out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
//							out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
//						} else {
//							out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
//							out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
//						}
//					}
//					return out;
*/				},
				
				generateQrCode : function(personInfo) {
					if ($("#qrcode").children().length == 0) {
						var qrCodeStr = "BEGIN:VCARD\nVERSION:3.0";
						//qrCodeStr += "\nFN:" + this.toUTF8(personInfo.personName);
						var str = null, personOrg = null, personDep = null; 
						if(personInfo.personName){
							qrCodeStr +="\nN:"+personInfo.personName+";;;";
							qrCodeStr +="\nFN: "+personInfo.personName;
						}
						
						
						if(null != personInfo.deptName){
							str = personInfo.deptName.split('_')
							personOrg = str[0];
							personDep = str[str.length-1];
						}
						if(personInfo.personType=='inner'){
							var postName = (this.toUTF8(personInfo.postName));
							if(postName.replaceAll){
								qrCodeStr += "\nTITLE:" + postName.replaceAll(";",",");
							}
							else {
								qrCodeStr += "\nTITLE:" + postName.replace(/;/g,",");
							}
							qrCodeStr += "\nORG:" + this.toUTF8(personInfo.deptName);
							qrCodeStr += "\nROLE:" + this.toUTF8(personInfo.staffingLevelName);
							qrCodeStr += "\nTEL;WORK,VOICE:" + this.toUTF8(personInfo.officeNo);
							qrCodeStr += "\nEMAIL;PREF,INTERNET:" + this.toUTF8(personInfo.email);
							qrCodeStr += "\nTEL;CELL,VOICE:" + this.toUTF8(personInfo.phoneNo);
							qrCodeStr += "\nEND:VCARD";
						}					
						if(personInfo.personType=='outer'){
							if(!personInfo.personMobileNo){
								personInfo.personMobileNo='';
							}
							if(!personInfo.personEmail){
								personInfo.personEmail='';
							}
							if(personDep) {
								var personDepStr = (this.toUTF8(personDep));
								if(personDepStr.replaceAll){
									qrCodeStr += "\nTITLE:" + personDepStr.replaceAll(";",",");
								}
								else {
									qrCodeStr += "\nTITLE:" + personDepStr.replace(/;/g,",");
								}
							}
							qrCodeStr += "\nORG:" + this.toUTF8(personOrg);
							qrCodeStr += "\nROLE:" + this.toUTF8(personInfo.postName);
							qrCodeStr += "\nTEL;WORK,VOICE:" + this.toUTF8(personInfo.personWorkPhone);
							qrCodeStr += "\nTEL;CELL,VOICE:" + this.toUTF8(personInfo.personMobileNo);
							qrCodeStr += "\nEMAIL;PREF,INTERNET:" + this.toUTF8(personInfo.personEmail);
							qrCodeStr += "\nEND:VCARD";
						}
						var isIE8 = navigator.userAgent.indexOf("MSIE") > -1 && document.documentMode == null || document.documentMode <= 8
						qrcode.Qrcode({
							text : qrCodeStr,
							element : $("#qrcode"),
							render : 'custom',
							className:'sys_zone_person_card_code_img_size',
							genWidth:140,
							genHeight:140
						});
					}
				},
				
				generateRightHTML : function(personInfo) {
					var childRightHTML = "<div class='lui_personal_card_frame'>";
					if (personInfo.personType && personInfo.personType == "inner") {
						var sexClass = "sex_m";
						if (personInfo.personSex == "F") {
							sexClass = "sex_f";
						}
						if(!personInfo.phoneNo){
							personInfo.phoneNo="";
						}
						if(!personInfo.officeNo){
							personInfo.officeNo="";
						}
						if(!personInfo.email){
							personInfo.email="";
						}
						if(!personInfo.postName){
							personInfo.postName="";
						}
						if(!personInfo.signatureName){
							personInfo.signatureName="";
						}
						childRightHTML += "<div class='lui_personal_card_header'>";
						childRightHTML += "<div class='lui_personal_card_img'>";
						childRightHTML += "<img src='" + personInfo.personImg + "' alt='' />";
						childRightHTML += "</div>";
						childRightHTML += "<p>";
						childRightHTML += "<span class='person_name'>" + personInfo.personName + "</span>";
						childRightHTML += "<span class='staff_sex " + sexClass
								+ "'><i class='lui_icon_s " + sexClass
								+ "'></i></span>";
						childRightHTML += "</p>";
						childRightHTML += "</div>";
						childRightHTML += "<div class='lui_personal_card_detail'>";
						childRightHTML +="<div class='lui_person_detail'>"
						childRightHTML += "<ul>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.mobilePhone']
							+ "</em><span class='item_detail'>"
							+ (personInfo.phoneNo || "") + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.shortNo']
							+ "</em><span class='item_detail'>"
							+ (personInfo.shortNo || "") + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.workPhone']
							+ "</em><span class='item_detail'>"
							+ (personInfo.officeNo || "") + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.email']
							+ "</em><span class='item_detail'>"
							+ env.fn.formatText(personInfo.email || "") + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.inner.dept']
							+ "</em><span class='item_detail'>"
							+ (personInfo.deptName || "") + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.inner.post']
							+ "</em><span class='item_detail'>"
							+ (personInfo.postName || "") + "</span></li>";
						if("finder"==personInfo.finder){
							childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.remarks']
								+ "</em><span class='item_detail item_detail_all' id='item_name_opera'>"
								+ env.fn.formatText(personInfo.signatureName || "") + "</span></li>";
						}else{
							childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.signatureName']
								+ "</em><span class='item_detail item_detail_all' id='item_name_opera'>"
								+ env.fn.formatText(personInfo.signatureName || "") + "</span></li>";
						}
						childRightHTML += "</ul>";
						childRightHTML +="</div>";
						childRightHTML += "<div class='lui_personal_code'>";
						childRightHTML += "<div class='icon_qrcode'>";
						childRightHTML += "<div id='qrcode' ></div>"
						childRightHTML += "</div>";
						childRightHTML += "<p>"+zonelang['sysZonePerson.saveto.addressBook']+"</p>";
						childRightHTML += "</div>";
						childRightHTML += "</div>";
					} else if (personInfo.personType == "outer") {
						//
						if(!personInfo.personMobileNo){
							personInfo.personMobileNo="";
						}
						if(!personInfo.personWorkPhone){
							personInfo.personWorkPhone="";
						}
						if(!personInfo.personEmail){
							personInfo.personEmail="";
						}
						if(!personInfo.postName){
							personInfo.postName="";
						}
						if(!personInfo.personMemo){
							personInfo.personMemo="";
						}
						childRightHTML += "<div class='lui_personal_card_header'>";
						childRightHTML += "<div class='lui_personal_card_img'>";
						childRightHTML += "<img src='../address/resource/images/head.png' alt='' />";
						childRightHTML += "</div>";
						childRightHTML += "<p>";
						childRightHTML += "<span class='person_name'>" + personInfo.personName + "</span>";
						childRightHTML += "</p>";
						//
						childRightHTML += "</div>";
						childRightHTML += "<div class='lui_personal_card_detail'>";
						childRightHTML +="<div class='lui_person_detail'>"
						childRightHTML += "<ul>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.out.mobilePhone']
								+ "</em><span class='item_detail'>"
								+ env.fn.formatText(personInfo.personMobileNo) + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.out.workPhone']
								+ "</em><span class='item_detail'>"
								+ env.fn.formatText(personInfo.personWorkPhone) + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.out.email']
								+ "</em><span class='item_detail'>"
								+ env.fn.formatText(personInfo.personEmail) + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.out.post']
								+ "</em><span class='item_detail'>"
								+ env.fn.formatText(personInfo.postName) + "</span></li>";
						childRightHTML += "<li><em class='item_name'>" + zonelang['zoneAddress.out.remarks']
								+ "</em><span class='item_detail'>"
								+ env.fn.formatText(personInfo.personMemo) + "</span></li>";
						childRightHTML += "</ul>";
						childRightHTML += "</div>";
						childRightHTML += "<div class='lui_personal_code'>";
						childRightHTML += "<div class='icon_qrcode'>";
						childRightHTML += "<div id='qrcode'></div>"
						childRightHTML += "</div>";
						childRightHTML += "<p>"+zonelang['sysZonePerson.saveto.addressBook']+"</p>";
						childRightHTML += "</div>";
						childRightHTML += "</div>";
					}
					childRightHTML += "</div>";
					return childRightHTML;
				}
			});

	var AbstractZoneAddressContent = base.Container.extend({

		leftMenuContent : null,
		childMiddleContent : null,
		childRightContent : null,

		initProps : function($super, cfg) {
			this.parent = cfg.parent;
			this.ancestor = cfg.ancestor;
			this.menuId = cfg.menuId;
			this.startup();
		},

		startup : function() {
			//
		},

		draw : function() {
			this.leftMenuContent.draw();
		},

		show : function() {
			this.leftMenuContent.show();
		},

		hide : function() {
			this.leftMenuContent.hide();
		}
	});

	var childMiddleContentObj = null;
	var childRightContentObj = null;

	var OrgZoneAddressContent = AbstractZoneAddressContent.extend({

		menuType : 'org',

		startup : function() {
			this.leftMenuContent = new OrgLeftMenuContent({
				parent : this
			});
			if (childMiddleContentObj == null) {
				childMiddleContentObj = new ChildMiddleContent({
					parentDom : this.parent.childContentDom
				});
			}
			if (childRightContentObj == null) {
				childRightContentObj = new ChildRightContent({
					parentDom : this.parent.childContentDom,
					hideQrCode : this.parent.config.hideQrCode
				});
			}

			this.childMiddleContent = childMiddleContentObj;

			this.childRightContent = childRightContentObj;
		}
	});

	var cLeftMenuContent = null;

	var CategoryLeftMenuContent = AbstractCommonContent
			.extend({
				layoutFile : './tmpl/zoneAddressCategoryLeftMenuContent.html#',

				initProps : function($super, cfg) {
					$super(cfg);
					if (this.parent.parent.leftMenuContentDom) {
						this.parentDom = this.parent.parent.leftMenuContentDom;
						this.content = this.parentDom
								.find("[data-lui-menucontentid='"
										+ this.parent.menuId + "']");
					}
					this.cateNodes = new MapObj();
					this.currentNode = null;
					cLeftMenuContent = this;
					topic.subscribe(constant.event['MENU_CHANGED'],
							this.menuChanged, this);
				},

				menuChanged : function(evt) {
					this.currentNode = null;
					var menuId = evt.menuId;
					if (menuId == this.parent.menuId) {
						cLeftMenuContent = this;
					}
				},

				afterLayout : function() {
					// 构建组织架构的数
					this.content.addClass("lui_zone_address_category");
					this.addBtnDom = this.content
							.find("[data-lui-mark='lui-zone-address-category-add']");
					this.addBtnDom.click(this, function(event) {
						var clm = event.data;
						clm.addCategory();
					});
					// 画分类树
					this.drawCategory();
				},

				drawCategory : function() {
					var self = this;
					//中间数据-分类id
					var midCateId = $(".lui-zone-address-child-middle-content").find(".department_name").attr("cateid");

					var dataUrl = "sysZoneAddressFinder&cateType="
							+ this.parent.menuType;
					var kmssData = new KMSSData();
					kmssData.UseCache = false;
					kmssData.AddBeanData(dataUrl);
					var rtnData = kmssData.GetHashMapArray();
					this.convertRtnDataToCateNode(rtnData);
					var cateHTML = this.generateCateHTML(this.cateNodes);
					if(this.addBtnDom!=undefined){
						this.addBtnDom.prev().remove();
					}
					$(cateHTML).insertBefore(this.addBtnDom);
					this.content.find(".group_name").click(
							this,
							function(event) {
								var clm = event.data;
								clm.clickNode(this, $(this).attr("data-lui-nodeid"));
							});
					if (this.parent.menuType == "outer") {
						if(this.config.auth==undefined){
							this.addBtnDom.remove();
						}
					}
					this.content.find(".opt_edit").click(this, function(event) {
						var clm = event.data;
						clm.editNode(this, $(this).attr("data-lui-nodeid"));
					});
					this.content.find(".opt_del").click(this, function(event) {
						var clm = event.data;
						clm.delNode(this, $(this).attr("data-lui-nodeid"));
					});
					topic.publish(constant.event['FINDER_MENU_DRAWED'], {
					});
					//midCateId - 编辑回调重新展示原节点的右侧数据
					if(!!midCateId){
						self.content.find(".group_name[data-lui-nodeid='"+midCateId+"']").click();
					}
				},

				clickNode : function(nodeObj, nodeId) {
					var nodeClick = this.cateNodes.get(nodeId);
					$(nodeObj).parent().parent().find(".active").removeClass("active");
					$(nodeObj).addClass("active");
					if (this.currentNode == null
							|| nodeClick.cateId != this.currentNode.cateId) {
						// 分类点后才去取这个分类的成员
						var dataUrl = "sysZoneAddressFinder&fdCategoryId=" + nodeId + "&cateType=" + this.parent.menuType;
						var kmssData = new KMSSData();
						kmssData.UseCache = false;
						kmssData.AddBeanData(dataUrl);
						var rtnData = kmssData.GetHashMapArray();
						var catePersons = $.parseJSON(rtnData[0].catePersons);
						nodeClick.catePersons = catePersons;
						
						this.currentNode = nodeClick;
						topic.publish(constant.event['FINDER_MENU_CHANGED'], {
							finderNode : this.currentNode
						});
					}
				},

				editNode : function(nodeObj, nodeId) {
					var self = this;
					if (self.parent.menuType == "inner") {
						dialog.iframe(
								"/sys/zone/sys_zone_address_cate/sysZoneAddressCate.do?method=edit&fdId="
										+ nodeId + "&cateType=inner",
										zonelang['zoneAddress.inner.iframe.edit'],
								function() {
									self.editNodeCB(nodeObj,nodeId);
								}, {
									width : 980,
									height : 550
								});
					} else if (self.parent.menuType == "outer") {
						dialog.iframe(
								"/sys/zone/sys_zone_address_cate/sysZoneAddressCate.do?method=edit&fdId="
										+ nodeId + "&cateType=outer",
								zonelang['zoneAddress.outer.iframe.edit'],
								function() {
									self.editNodeCB(nodeObj,nodeId);
								}, {
									width : 980,
									height : 550
								});
					}
				},

				editNodeCB : function(nodeObj,nodeId) {
					setTimeout(function() {
						//#43062拥有编辑权限的普通用户多次点击关闭按钮才能关闭
						cLeftMenuContent.drawCategory();
					}, 800);
				},

				delNode : function(nodeObj, nodeId) {
					// var confirmHintInfo='<bean:message
					// key="convertQueue.comfirmDelete.selection"
					// bundle="sys-filestore"/>';
					var confirmHintInfo = zonelang['zoneAddress.deleteTips'];
					var menuType = this.parent.menuType;
					dialog
							.confirm(
									confirmHintInfo,
									function(value) {
										if (value == true) {
											window.innerLoading = dialog
													.loading();
											$
													.post(
															Com_Parameter.ContextPath
																	+ 'sys/zone/sys_zone_address_cate/sysZoneAddressCate.do?method=delete',
															$.param({
																"fdId" : nodeId,
																"menuType":menuType
															}, true),
															cLeftMenuContent.dialogCallBack,
															'json');
										}
									});
				},

				dialogCallBack : function(data) {
					if (window.innerLoading != null) {
						window.innerLoading.hide();
					}
					if (data != null && data.status == true) {
						var tips=data.title;
						dialog
								.success(tips);
					} else {
						dialog
								.failure('<bean:message key="return.optFailure" />');
					}
					setTimeout(function() {
						cLeftMenuContent.drawCategory();
					}, 1000);
				},

				convertRtnDataToCateNode : function(rtnData) {
					this.cateNodes = new MapObj();
					this.currentNode = null;
					for (var i = 0; i < rtnData.length; i++) {
						this.cateNodes.put(rtnData[i].cateId, rtnData[i]);
					}
				},

				generateCateHTML : function(cateNodes) {
					var cateHTML = "<ul>";
					var cateNodesValues = cateNodes.values();
					for (var i = 0; i < cateNodesValues.length; i++) {
						cateHTML += "<li>";
						cateHTML += "<span class='group_name' data-lui-nodeid='"
								+ cateNodesValues[i].cateId
								+ "'>"
								+ str.decodeOutHTML(cateNodesValues[i].cateName) + "</span>";
						cateHTML += "<span class='group_opt'>" ;
						if(cateNodesValues[i].canEdit=="true"||(this.config.auth!=undefined && this.config.auth==1)){
						cateHTML +=	"<i class='opt_edit' data-lui-nodeid='"
								+ cateNodesValues[i].cateId
								+ "' title='" + zonelang['zoneAddress.editButton'] + "'></i>"
								} ;
						if(cateNodesValues[i].canDel=="true"||(this.config.auth!=undefined && this.config.auth==1)){
						cateHTML += "<i class='opt_del' data-lui-nodeid='"
								+ cateNodesValues[i].cateId + "' title='" + zonelang['zoneAddress.delButton'] + "'></i>";
						}
						cateHTML += "</span></li>";
					}
					return cateHTML + "</ul>";
				},

				addCategory : function() {
					if (this.parent.menuType == "inner") {
						dialog
								.iframe(
										"/sys/zone/sys_zone_address_cate/sysZoneAddressCate.do?method=add&cateType=inner",
										zonelang['zoneAddress.inner.iframe.add'], this.addCategoryCB, {
											width : 980,
											height : 550
										});
					} else if (this.parent.menuType == "outer") {
						dialog
								.iframe(
										"/sys/zone/sys_zone_address_cate/sysZoneAddressCate.do?method=add&cateType=outer",
										zonelang['zoneAddress.outer.iframe.add'], this.addCategoryCB, {
											width : 980,
											height : 550
										});
					}
				},

				addCategoryCB : function(rtnData) {
					setTimeout(function() {
						cLeftMenuContent.drawCategory();
					}, 1000);
				}
			});

	var InnerZoneAddressContent = AbstractZoneAddressContent.extend({

		menuType : 'inner',

		startup : function() {
			this.leftMenuContent = new CategoryLeftMenuContent({
				parent : this,
				auth:this.config.auth
			});
			if (childMiddleContentObj == null) {
				childMiddleContentObj = new ChildMiddleContent({
					parentDom : this.parent.childContentDom
				});
			}
			if (childRightContentObj == null) {
				childRightContentObj = new ChildRightContent({
					parentDom : this.parent.childContentDom
				});
			}

			this.childMiddleContent = childMiddleContentObj;

			this.childRightContent = childRightContentObj;
		}
	});

	var OuterZoneAddressContent = AbstractZoneAddressContent.extend({

		menuType : 'outer',

		startup : function() {
			this.leftMenuContent = new CategoryLeftMenuContent({
				parent : this,
				auth:this.config.auth
			});
			if (childMiddleContentObj == null) {
				childMiddleContentObj = new ChildMiddleContent({
					parentDom : this.parent.childContentDom
				});
			}
			if (childRightContentObj == null) {
				childRightContentObj = new ChildRightContent({
					parentDom : this.parent.childContentDom
				});
			}

			this.childMiddleContent = childMiddleContentObj;

			this.childRightContent = childRightContentObj;
		}
	});

	exports.AbstractZoneAddressContent = AbstractZoneAddressContent;
	exports.OrgZoneAddressContent = OrgZoneAddressContent;
	exports.InnerZoneAddressContent = InnerZoneAddressContent;
	exports.OuterZoneAddressContent = OuterZoneAddressContent;
});
