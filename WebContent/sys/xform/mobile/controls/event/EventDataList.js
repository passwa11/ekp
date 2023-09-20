define([
  "dojo/_base/array",
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dijit/_Contained",
  "dijit/_Container",
  "dijit/_WidgetBase",
  "dojo/topic",
  "sys/xform/mobile/controls/event/EventDataListItem",
  "dojo/request",
  "mui/util",
  "dojo/dom-style",
  "mui/list/item/_TemplateItemMixin",
  "dojo/query",
  "dojo/dom-class",
  "dojo/topic",
  "dojo/_base/lang",
  "dijit/registry",
  "mui/i18n/i18n!sys-xform-base"
], function(
  array,
  declare,
  domConstruct,
  Contained,
  Container,
  WidgetBase,
  topic,
  EventDataListItem,
  request,
  util,
  domStyle,
  TemplateItem,
  query,
  domClass,
  topic,
  lang,
  registry,
  Msg
) {
  return declare(
    "sys.xform.mobile.controls.event.EventDataList",
    [WidgetBase, Container, Contained],
    {
      isMul: false,

      pageAble: false,

      key: null,

      headers: null,

      argu: null,

      baseClass: "muiCateLists muiXformEventLists",

      selectItems: [],
      
      selectedDatas: [],

      _pageNum: 1,

      postCreate: function() {
        this.inherited(arguments);
        this.subscribe("/sys/xform/event/submit", "_returnData");
        this.subscribe("/mui/list/onPush", "_appendNext");

        this.subscribe("/sys/xform/event/search", "_searchDataInfo");
        this.subscribe("/sys/xform/event/addSeleteItem", "_addSelectItem");
        this.subscribe("/sys/xform/event/delSeleteItem", "_delSelectItem");
        this.selectItems = [];
      },

      _returnData: function(srcObj) {
        if (this.key == srcObj.key) {
          var rtnData = {};
          var self = this;
          rtnData.headers = this.headers;
          rtnData.argu = this.argu;
          rtnData.rows = [];
          array.forEach(
            this.selectItems,
            function(item) {
              if (item != null && item.selected) {
                rtnData.rows.push(item.data);
              }
            },
            this
          );
          this._addReturnDatas(rtnData);
          topic.publish("/sys/xform/event/selected", this, rtnData);
        }
      },

      _addSelectItem: function(srcObj) {
          if(this.argu.paramsJSON.listRule == '01' || this.argu.paramsJSON.listRule =='00'){
              this.selectedDatas = [];
              this.selectItems = [];
          }
	      if(!srcObj)return;
	      var srcObj_key = srcObj.key;
          var this_key = this.key;
          if(
	         srcObj_key && this_key 
             && srcObj_key.indexOf("(")>-1
             && srcObj_key.indexOf(")")>-1
             && this_key.indexOf("(")>-1
             && this_key.indexOf(")")>-1
             && 
                (
	            srcObj_key.substring(srcObj_key.indexOf("("),srcObj_key.indexOf(")")+1)
                ==
                this_key.substring(srcObj_key.indexOf("("),this_key.indexOf(")")+1)
                )
          ){        
            var len = this.selectItems.length;
            this.selectItems[len] = srcObj;
            this._addSelectedDataInNecessary(srcObj);
          }	else if( // 如果没有获取到对应id，则走原逻辑,括号中的是控件id
	          srcObj_key.indexOf("(")==-1
             || srcObj_key.indexOf(")")==-1
             || this_key.indexOf("(")==-1
             || this_key.indexOf(")")==-1
          ){
	        var len = this.selectItems.length;
            this.selectItems[len] = srcObj;
            this._addSelectedDataInNecessary(srcObj);
           }
	   
      },
      
      setSelectedDatas: function(selectedDatas) {
    	  this.selectedDatas = selectedDatas || [];
      },
      
      //返回时,将非当前页已选数据添加到已选列表中
      _addReturnDatas: function(rtnData) {
          if(this.argu.paramsJSON.listRule == '01' || this.argu.paramsJSON.listRule =='00'){
              this.selectedDatas = [];
              this.selectItems = [];
          }
    	  if (this.argu.echo) {
	    	  var oldSelectedItemSize = this.selectedDatas.length;
	          for (var i = 0; i < oldSelectedItemSize; i++) {
	        	  var rowData = this.selectedDatas[i];
	        	  var rowId = rowData.currentRecordId;
	        	  var hasInSelectItem = false;
	        	  for (var j = 0; j < rtnData.rows.length; j++) {
	        		  var selectItem = rtnData.rows[j];
	        		  var selectItemId = selectItem.currentRecordId;
	        		  if (rowId === selectItemId) {
	        			  hasInSelectItem = true;
	        			  break;
	        		  }
	        	  }
	        	  if (!hasInSelectItem) {
	        		  rtnData.rows.push(this.selectedDatas[i]);
	        	  }
	          }
    	  }
      },
      
      //添加已选数据,去重
      _addSelectedDataInNecessary: function(srcObj) {
    	if (this.argu.echo) {
    		var selected = false;
        	for (var i = 0; i < this.selectedDatas.length; i++) {
        		var selectData = this.selectedDatas[i];
        		if (selectData.currentRecordId === srcObj.data.currentRecordId) {
        			this.selectedDatas[i] = srcObj.data;
        			selected = true; 
        			break;
        		}
        	}
        	if (!selected) {
        		this.selectedDatas[this.selectedDatas.length] = srcObj.data;
        	}
    	}
      },
      
      //移除已选数据
      _removeSelectedData : function(srcObj) {
    	  if (this.argu.echo) {
	          for (var i = 0; i < this.selectedDatas.length; i++) {
	          	var selectedData = this.selectedDatas[i];
	          	 if (srcObj && srcObj.data.currentRecordId === selectedData.currentRecordId) {
	          		this.selectedDatas.splice(i, 1);
	          		return;
	          	 }
	          }
    	  }
      },

      _delSelectItem: function(srcObj) {
        for (var i = 0; i < this.selectItems.length; i++) {
          if (this.selectItems[i] == srcObj) {
            this.selectItems[i] = null;
          }
        }
        this._removeSelectedData(srcObj);
      },

      setArgu: function(argu) {
        this.argu = argu;
      },
      
      setData: function(data, dom) {
        if (!this.headers) this.headers = data.headers;
        if (!dom) {
          dom = this.containerNode;
        }
        if (data.rows.length > 0) {
          var datas = data.rows;
          array.forEach(
            datas,
            function(tmpData) {
              this.append(this.createItem(data.headers, tmpData), dom);
            },
            this
          );
        }
        this._resizeCount();
      },

      _searchDataInfo: function(srcObj, evt) {
        if (evt) {
          if (srcObj.key == this.key) {
            var self = this;
            if (self.argu.appendSearchResult == "false" || self.argu.echo) {
              this.selectItems = [];
            }

            var paramsJSON = evt.argu.paramsJSON;
            // 只要搜索就把原有页数清了
            if (/[\d][1]/g.test(paramsJSON.listRule)) {
              paramsJSON.pageSize = 1;
            } else {
              paramsJSON.pageSize = 0;
            }
            request
              .post(util.formatUrl(evt.argu.queryDataUrl), {
                data: paramsJSON,
                handleAs: "json"
              })
              .then(
                function(json) {
                  if (!json || !json.outs) {
                    json.outs = [];
                  }
                  //对结果集进行排序
                  json.outs.sort(function(a, b) {
                    return a.rowIndex - b.rowIndex;
                  });
                  // 删除所有的子对象
                  array.forEach(
                    self.getChildren(),
                    function(item) {
                      item.destroy();
                    },
                    self
                  );

                  var data = evt.argu.control.convertData(
                    json,
                    evt.argu.outputsJSON,
                    paramsJSON
                  );
                  if (data != null && data.rows.length > 0) {
                    evt.argu.dataLength = data.rows.length;
                    self.setData(data);
                  } else {
                    //搜索时，无数据显示暂无内容
                    this.showNoRecord();
                  }
                  topic.publish("/sys/xform/event/searchData", self);
                },
                function() {}
              );
          }
        }
      },

      showNoRecord: function() {
        var self = this;
        require(["dojo/text!mui/list/item/NoDataTempl.tmpl"], function(tmpl) {
          self.tempItem = new TemplateItem({
            templateString: tmpl,
            baseClass: "muiListNoData",
            text: Msg["mui.event.list.noContent"]
          });
          var _container = query(
            ".muiListNoDataContainer",
            self.tempItem.domNode
          )[0];
          domConstruct.create(
            "i",
            { className: "mui mui-message" },
            _container
          );
          domClass.add(_container, "muiListNoDataIcon");

          if (self.addChild) self.addChild(self.tempItem);
          if (!self.tempItem.domNode.style["line-height"])
            domStyle.set(self.tempItem.domNode, {
              height: 305 + "px",
              "line-height": 155 + "px",
              "margin-top": 150 + "px"
            });
        });
      },

      _appendNext: function(srcObj, handle) {
        if (srcObj.key == this.key) {
          if (this.pageAble) {
            var _self = this;
            topic.publish(
              "/sys/xform/event/nextpage",
              this,
              { argu: this.argu, pageNum: this._pageNum + 1 },
              {
                done: function(data) {
                  if (data != null && data.rows.length > 0) {
                    _self.argu.dataLength = data.rows.length;
                    _self._pageNum = _self._pageNum + 1;
                    _self.setData(data);
                  } else {
                    topic.publish("/sys/xform/event/showNomore", _self);
                  }
                  handle.done();
                }
              }
            );
          } else {
            handle.done();
          }
        }
      },

      append: function(item, dom) {
        domConstruct.place(item.domNode, dom, "last");
      },
      
      _resizeCount: function() {
    	  if (this.argu.echo) {
	    	  if (this.selectedDatas && this.selectedDatas.length > 0) {
	          	var selectionWgt = registry.byId("_eventdata_sgl_selection_" + this.key);
	          	var self = this;
	          	setTimeout(function(){
	          		selectionWgt.count = self.selectedDatas.length;
	          		selectionWgt._resizeSelection()
	          	},100);
	          }
    	  }
      },
      
      checkedIfNecessary: function(row, item) {
    	//回显数据
    	  if (this.argu.echo) {
	        var isSelected = false;
	        if (this.selectedDatas && this.selectedDatas.length > 0) {
	        	for(var i = 0; i < this.selectedDatas.length; i++) {
	        		if (row.currentRecordId === this.selectedDatas[i].currentRecordId) {
	        			isSelected = true;
	        			item._selectData();
	        			return;
	        		}
	        	}
	        }
    	  }
      },

      createItem: function(headers, row) {
        var item = new EventDataListItem({
          key: this.key,
          isMul: this.isMul,
          headers: headers,
          data: row
        });
        item.startup();
        this.checkedIfNecessary(row, item);
        return item;
      }
    }
  );
});
