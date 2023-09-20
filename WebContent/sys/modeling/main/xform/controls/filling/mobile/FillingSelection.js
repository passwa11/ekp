define([ "dojo/_base/declare", "mui/iconUtils",
				"mui/category/CategorySelection" , "mui/util", "dojo/dom-construct", "dojo/dom-class","dojo/_base/array","dojo/topic"],
		function(declare, iconUtils, CategorySelection, util, domConstruct, domClass,array,topic) {
			var selection = declare("sys.modeling.main.xform.controls.filling.mobile.FillingSelection",[ CategorySelection ],{
				_addSelItme: function(srcObj, evt) {
					if (srcObj.fsKey == this.key) {
						if (evt) {
							if (typeof evt.labelLevel == "undefined") {
								evt.labelLevel =
									typeof srcObj.labelLevel != "undefined"
										? srcObj.labelLevel
										: srcObj.label
							}
							if (!this._checkInSelArr(evt.fdId)) {
								this.cateSelArr.push(evt)
							}
						}
						this._resizeSelection()
					}
				},
				_delSelItem: function(srcObj, evt) {
					if (srcObj.key == this.key || srcObj.fsKey == this.key) {
						if (evt && evt.fdId) {
							for (var i = 0; i < this.cateSelArr.length; i++) {
								if (this.cateSelArr[i].fdId == evt.fdId) {
									this.cateSelArr.splice(i, 1)
									break
								}
							}
							this._resizeSelection()
						}
					}
				},
				_calcCurSel: function() {
					var eCxt = {
						curIds: "",
						key: this.key,
						rows:{}
					}
					var self = this;
					if (this.cateSelArr.length > 0) {
						var ids = "";
						var rows = {};
						array.forEach(this.cateSelArr, function(selItem) {
							ids += ";" + selItem.fdId;
							for(var key in selItem.currentData){
								var datas = selItem.currentData[key];
								for(var dk in datas){
									if(rows.hasOwnProperty(key)){
										if(!rows[key].hasOwnProperty(dk)){
											rows[key][dk] = [];
										}
										rows[key][dk].push(datas[dk]);
									}else{
										rows[key] = {};
										rows[key][dk] = [];
										rows[key][dk].push(datas[dk]);
									}
								}
							}
						})
						if (ids != "") {
							ids = ids.substring(1)
							eCxt.curIds = ids
						}
						if('{}' != JSON.stringify(rows)){
							eCxt.rows = rows;
						}
					}
					return eCxt
				},
			});
			return selection;
		});