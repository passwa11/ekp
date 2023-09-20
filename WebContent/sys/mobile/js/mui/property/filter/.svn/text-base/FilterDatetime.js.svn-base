define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "mui/property/filter/FilterBase",
  "dojo/dom-construct",
  "dojo/_base/array",
  "dojo/topic",
  "dojo/text!./datetime/tmpl.tmpl",
  "dojo/html",
  "mui/util",
  "./FilterItem",
  "mui/i18n/i18n!sys-mobile:mui.data.volume",
  "mui/i18n/i18n!sys-mobile:mui.datetime.validate.compare.fail",
  "mui/dialog/Tip"
], function(
  declare,
  lang,
  FilterBase,
  domConstruct,
  array,
  topic,
  tmpl,
  html,
  util,
  FilterItem,
  msg,
  msgFail,
  Tip
) {
  var claz = declare("mui.property.FilterDatetime", [FilterBase], {
    types: ["Date", "Time", "DateTime"],

    buildRendering: function() {
      this.inherited(arguments)
      this.subscribe("/mui/form/datetime/change", "onChange")
      this.subscribe(this.SET_EVENT, "onValueSet")
      this.renderItems()

      this.pickerNode = domConstruct.create(
        "li",
        {
          className: "filterItemPicker"
        },
        this.contentNode,
        "last"
      )

      this.pickerWrapperNode = domConstruct.create(
        "div",
        {
          className: "filterItemPicker_wrapper"
        },
        this.pickerNode
      )

      domConstruct.create(
        "span",
        {
          className: "filterItemPicker_split"
        },
        this.pickerNode
      )
    },

    getDate: function() {
      var d = new Date()
      return new Date(
        d.getFullYear(),
        d.getMonth(),
        d.getDate(),
        23,
        59,
        59,
        999
      )
    },

    buildOptions: function() {
      var options = []

      var format = ""
      switch (this.type) {
        case "date":
          format = dojoConfig.Date_format
          break
        case "time":
          format = dojoConfig.Time_format
          break
        case "datetime":
          format = dojoConfig.DateTime_format
          break
        default:
          break
      }

      if (!format) {
        return []
      }

      var d1 = this.getDate()
      var d2 = this.getDate()
      d2.setDate(d1.getDate() - 7)
      options.push({
        name: msg['mui.data.volume.week'],
        value: [util.formatDate(d2, format), util.formatDate(d1, format)]
      })

      d1 = this.getDate()
      d2 = this.getDate()
      d2.setMonth(d1.getMonth() - 1)
      options.push({
        name: msg['mui.data.volume.month'],
        value: [util.formatDate(d2, format), util.formatDate(d1, format)]
      })

      d1 = this.getDate()
      d2 = this.getDate()
      d2.setMonth(d1.getMonth() - 3)
      options.push({
        name: msg['mui.data.volume.three.month'],
        value: [util.formatDate(d2, format), util.formatDate(d1, format)]
      })

      d1 = this.getDate()
      d2 = this.getDate()
      d2.setMonth(d1.getMonth() - 6)
      options.push({
        name: msg['mui.data.volume.harf.a.year'],
        value: [util.formatDate(d2, format), util.formatDate(d1, format)]
      })

      d1 = this.getDate()
      d2 = this.getDate()
      d2.setYear(d1.getFullYear() - 1)
      options.push({
        name: msg['mui.data.volume.year'],
        value: [util.formatDate(d2, format), util.formatDate(d1, format)]
      })

      return options
    },

    // 点击后同时反映到下面的输入框，体验优化
    onValueSet: function(obj, evt) {
      if (!evt) {
        return
      }
      if (evt.name == this.name) {
        var vals = evt.value;
        if(this.startWidget) {
          this.startWidget.set("value", vals[0] || "");
        }
        if(this.endWidget) {
          this.endWidget.set("value", vals[1] || "");
        }
      }
    },

    renderItems: function() {
      var options = this.buildOptions()
      array.forEach(
        options,
        function(option) {
          var item = new FilterItem(
            lang.mixin(
              {
                key: this.key,
                compareValue: function(v1, v2) {
                  if (v1.length !== v2.length) {
                    return false
                  }

                  var i,
                    l = v1.length
                  for (i = 0; i < l; i++) {
                    if (v1[i] != v2[i]) {
                      return false
                    }
                  }
                  return true
                },
                resolveValue: function(isSelected, value) {
                  return isSelected ? [] : value
                }
              },
              option
            )
          )
          domConstruct.place(item.domNode, this.contentNode)
          // 初始化已选条件
          if (this.value == option) {
            domClass.add(item.domNode, this.selectedClass)
          }
        },
        this
      )
    },

    startup: function() {
      this.inherited(arguments)

      var self = this
      this.getValue(function(_value) {
        var _types = array.filter(
          self.types,
          function(item) {
            return item.toLowerCase() == self.type
          },
          self
        )
        if (_types.length == 0) return

        var value = _value || []

        var dhs = new html._ContentSetter({
          parseContent: true,
          cleanContent: true,
          node: self.pickerWrapperNode,
          onBegin: function() {
            this.content = lang.replace(this.content, {
              type: _types[0],
              name: self.name,
              value1: value.length > 0 ? value[0] || "" : "",
              value2: value.length > 1 ? value[1] || "" : ""
            })
            this.inherited("onBegin", arguments)
          }
        })
        dhs.set(tmpl)
        dhs.parseDeferred.then(
          lang.hitch(self, function(parseResults) {
            // 开始时间和结束时间
            self.startWidget = parseResults[0]
            self.endWidget = parseResults[1]
          })
        )
        dhs.tearDown()
      })
    },
    
    validateDate : function (obj){
    	if(obj.value != ''){
    		if(obj.dateType == 'start'){
    			this.startTime = new Date(obj.value);
    		} else if(obj.dateType == 'end'){
    			this.endTime = new Date(obj.value);
    		}
    	}
    	if(this.startTime && this.endTime){
    		if(this.startTime - this.endTime > 0) {
    			obj.contentNode.innerText = '';
    			Tip.fail({
    				'text' : msgFail['mui.datetime.validate.compare.fail']
    			});
    			return false;
    		}
    	}
    	return true;
    },

    onChange: function(obj, isReset) {
      if (obj && obj.valueField != this.name) {
        return;
      }
      var value = this.value
      if(!isReset){
    	  if (obj == this.startWidget) {
    		  value[0] = obj.value
    	  }
    	  
    	  if (obj == this.endWidget) {
    		  value[1] = obj.value
    	  }
    	  
    	  if (value[0] == undefined) {
    		  value[0] = "";
    	  }
    	  if (value[1] == undefined) {
    		  value[1] = "";
    	  }
      } else {
    	  value = new Array();
      }

      var children = this.getChildren()

      array.forEach(
        children,
        function(child, index) {
          if (child.compareValue) {
            if (child.compareValue(child.value, this.value)) {
              child.selected()
            } else {
              child.unSelected()
            }
          }
        },
        this
      )

      //这里导致的问题，判断由原来的与改成或的关系才正常  169964
      if(value[0]!="" || value[1]!=""){
    	  topic.publish(this.SET_EVENT, this, {
    	        name: this.name,
    	        value: value
    	      });
      }
    }
  })
  return claz
})
