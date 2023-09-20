/**
 * 标签筛选器
 */
define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dojo/topic",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-attr",
  "dijit/_WidgetBase",
  "mui/property/HashMixin",
  "dojo/_base/lang"
], function(
  declare,
  array,
  topic,
  domConstruct,
  domClass,
  domAttr,
  _WidgetBase,
  HashMixin,
  lang
) {
  return declare("mui.property.TagFilterItem", [_WidgetBase, HashMixin], {
    // 为了与mui/property/Filter把持一致，也构建一个filters数组
    filters: [],

    // 为了与mui/property/Filter把持一致，也使用values:{}保存选中值
    values: {},

    // 是否获取标签下的统计数字
    isTagCount: false,

    postMixInProperties: function() {
      this.filters = [
        {
          name: this.name,
          subject: this.subject,
          options: this.options,
          prefix: this.prefix
        }
      ]
      this.inherited(arguments)
    },

    buildRendering: function() {
      this.inherited(arguments)

      this.items = []
      if (this.options && this.options.length > 0) {
        array.forEach(
          this.options,
          function(option) {
            var element = domConstruct.create(
              "span",
              {
                "data-value": option.value,
                innerHTML: option.name,
                className: "muiTagFilterItem"
              },
              this.domNode
            )

            this.connect(element, "click", "submit")
            this.items.push(element)
          },
          this
        )
      }
      this._handleClassName()
    },

    startup: function() {
      this.inherited(arguments)
      this.tagCount()
    },

    tagCount: function() {
      if (!this.isTagCount) {
        return
      }

      this.renderTagCount()
      // 分类发生变化
      this.subscribe("/mui/catefilter/confirm", "renderTagCount")
      // 主筛选器发生变化
      this.subscribe("/mui/property/filter", "renderTagCount")
    },

    renderTagCount: function(obj) {
      this.defer(function() {
        if (obj && (obj == this || !this.isSameChannel(obj.key))) {
          return
        }

        array.forEach(
          this.options,
          function(option, index) {
            var val = {}
            val[this.name] = option.value
            var evt = {values: this.ensureValues(val)}
            evt.callback = lang.hitch(this, function(count) {
              var countMsg = ""
              var option = this.options[index]
              if (count > 0) {
                countMsg = "(!{count})".replace(/!{count}/, count)
              }
              this.items[index].innerHTML = option.name + countMsg

              if (option.prompt) {
                this.prompt(count > 0)
              }
            })
            topic.publish("/mui/list/count", this, evt)
          },
          this
        )
      }, 1)
    },

    prompt: function(show) {
      topic.publish("/mui/navitem/prompt", this, {show: show})
    },

    submit: function(evt) {
      if (!evt || !evt.target) {
        return
      }
      var element = evt.target
      var value = domAttr.get(element, "data-value")
      this.values[this.name] = value
      this._handleClassName()
      topic.publish(
        "/mui/property/filter",
        this,
        this.ensureValues(this.values)
      )
      this.inherited(arguments)
    },

    ensureValues: function(values) {
      var publishValues = {}
      for (var key in values) {
        var prefix = typeof this.prefix === "undefined" ? "q" : this.prefix
        publishValues[key] =
          prefix === "q"
            ? values[key]
            : {
                value: values[key],
                prefix: prefix
              }
      }
      return publishValues
    },

    _handleClassName: function() {
      var selectdValue = this.values[this.name]
      array.forEach(
        this.domNode.childNodes,
        function(element) {
          var value = domAttr.get(element, "data-value")
          domClass.toggle(element, "selected", selectdValue === value)
        },
        this
      )
    }
  })
})
