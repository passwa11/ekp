define(	["dojo/_base/declare",  "dojo/topic", "mui/util", "mui/category/CategoryItemMixin", "mui/address/AddressItemMixin"],
		function(declare, topic, util, CategoryItemMixin, AddressItemMixin) {
			var item = declare("sys.org.eco.AddressItemMixin", [ CategoryItemMixin, AddressItemMixin ], {
				
				personViewUrl : "/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=view&fdId=",
				postViewUrl : "/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=view&fdId=",

				startup: function () {
					this.inherited(arguments);
					this.dataUrl += "&sys_page=true";
				},
				_openCate : function() {
					
					var data = new Object();
					data.isShow = true; 
					data.fdId = this.fdId;
					data.label = this.label;
					data.fdNo = this.fdNo;
					data.fdAdmin = this.admin; 
					topic.publish("/sys/org/eco/card/reload", data);
					var changeData = new Object();
		            changeData.fdId = this.fdId;
		            changeData.fdOrgType = this.type;
		            changeData.fdInviteUrl = this.fdInviteUrl;
					topic.publish("/sys/org/eco/btn/change", changeData);
					topic.publish("/sys/org/eco/btn/setId", this.fdId);
					this.inherited(arguments);
				},
				
				_selectCate : function(evt) {

					this.superSelectCate(evt);
					
					var fdId = this.fdId;
					if(fdId) {
						if(this.isSearch) { // 搜索
							if(this.type == 2){ // 部门
								topic.publish("/sys/org/eco/btn/setId", fdId);
								this._openCate();
								topic.publish("/mui/address/searchBar/cancel");
							} else if (this.type == 4) { // 岗位
								var url = this.postViewUrl;
								window.location.href = util.formatUrl(url + fdId);
							} else if (this.type == 8) { // 人员
								var url = this.personViewUrl;
								window.location.href = util.formatUrl(url + fdId);
							}
						} else { // 列表
							var parent = this.getParent();
							if (this.showMore() && parent.showMore) {
								topic.publish("/sys/org/eco/btn/setId", fdId);
								this._openCate();
							} else {
								if (this.type == 4) { // 岗位
									var url = this.postViewUrl;
									window.location.href = util.formatUrl(url + fdId);
								} else if (this.type == 8) { // 人员
									var url = this.personViewUrl;
									window.location.href = util.formatUrl(url + fdId);
								}
							}
						}
					}
				},
				
				showSelect : function () {
					return false;
				},
				
				superSelectCate : function (evt) {
					evt && evt.stopPropagation();
				      if (this.startTime != 0) {
				        var endTime = new Date().getTime()
				        if (endTime - this.startTime < 500) {
				          return
				        }
				        this.startTime = 0
				      } else {
				        this.startTime = new Date().getTime()
				        this.defer(function() {
				          this.startTime = 0
				        }, 500)
				      }
				      if (evt) {
				        if (evt.stopPropagation) evt.stopPropagation()
				        if (evt.cancelBubble) evt.cancelBubble = true
				        if (evt.preventDefault) evt.preventDefault()
				        if (evt.returnValue) evt.returnValue = false
				      }
				      
				      if (this.selectNode) {
				        //存在选择区域时设置是否选中
				        if (this.checkedIcon != null) {
				          this._toggleSelect(false)
				        } else {
				          this._toggleSelect(true)
				        }
				        return
				      }

				},
				dataUrl: function () {

					return this.dataUrl;
				}
				
			});
			return item;
		});