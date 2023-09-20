/**
 * 树形动态列表
 */
define(function(require, exports) {
  var $ = require('lui/jquery')
  var topic = require('lui/topic')
  var base = require('lui/base')
  var toolbar = require('lui/toolbar')
  var dialog = require('lui/dialog')
  var env = require('lui/util/env')
  var lang = require('lang!sys-portal')
  // 新增下级
  var ADD_SUB = 'ADD.SUB'
  // 新增同级
  var ADD_SILB = 'ADD.SILB'
  // 上移
  var UP = 'UP'
  // 下移
  var DOWN = 'DOWN'
  // 删除
  var DEL = 'DEL'
  // 目录标题变更
  var NAMETEXT_CHANGE = 'NAMETEXT.CHANGE'

  // 展开
  var EXPAND_SHOW = 'EXPAND.SHOW'
  // 收起
  var EXPAND_HIDE = 'EXPAND.HIDE'
  // 序号变更
  var SERIAL_CHANGE = 'SERIAL.CHANGE'

  var EXPAND_SHOW_ICON_URL = env.fn.formatUrl(
    '/sys/portal/sys_portal_map_tpl/resource/images/plus.png'
  )

  var EXPAND_HIDE_ICON_URL = env.fn.formatUrl(
    '/sys/portal/sys_portal_map_tpl/resource/images/minus.png'
  )

  // 大树
  var Tree = base.Base.extend({
    // 初始化
    initProps: function($super, config) {
      $super()
      this.container = $(config.container)
      // 最大层级限制
      this.levelLimit = config.levelLimit ? config.levelLimit : 999
      this.bindElement(config)
      this.initToolbar()
      this.initTable()
      this.initContent()
      this.initEvent()
    },

    // 绑定表单
    bindElement: function(config) {
      if (config.bindElement) {
        this.bindElement = config.bindElement
        var self = this
        Com_Parameter.event['confirm'].push(function() {
          self.bindElement.val(JSON.stringify(self.getData()))
          return true
        })
      }
    },

    item2Json: function(item) {
      return {
        text: item.nameText,
        href: item.urlText,
        children: [],
        target: '_blank'
      }
    },

    // 获取数据
    getData: function() {
      var data = []

      // 上一个对象
      var json = {}
      var tmp = {}

      for (var i = 0; i < this.items.length; i++) {
        var item = this.items[i]
        // 根节点
        if (item.parent == this) {
          json = this.item2Json(item)
          data.push(json)
        } else {
          var parentObj = tmp[item.parent.cid]
          json = this.item2Json(item)
          parentObj.children.push(json)
        }
        tmp[item.cid] = json
      }
      return data
    },

    // 初始化数据
    initData: function(jsons) {
      if (!jsons) {
        return
      }

      for (var i = 0; i < jsons.length; i++) {
        this.parserJSON(this, jsons[i])
      }
    },

    parserJSON: function(parent, json) {
      var item = new Item({
        container: this,
        parent: parent,
        nameText: json.text,
        isHide: !(parent == this),
        urlText: json.href
      })

      this.setItem(item)

      var children = json.children

      for (var i = 0; i < children.length; i++) {
        this.parserJSON(item, children[i])
      }
    },

    // 初始化内容
    initContent: function() {
      this.items = []

      if (!this.bindElement) {
        return
      }

      var val = this.bindElement.val()
      if (!val) {
        return
      }

      this.initData($.parseJSON(val))
    },

    // 删除数据源
    delItem: function(item) {
      var element = item.element
      if (element == null) {
        return
      }
      var index = element.index() - 1
      this.items.splice(index, 1)
      element.off()
      element.remove()
      element = null
    },

    setItem: function(item, obj) {
      if (!item) {
        return
      }
      // 根节点
      if (!obj) {
        this.items.push(item)
        item.element.appendTo(this.table)
      } else {
        var index = this.getIndexByItem(obj)
        this.items.splice(index + 1, 0, item)
        item.element.insertAfter(obj.element)
      }
    },

    getItems: function() {
      return this.items
    },

    // 初始化工具栏
    initToolbar: function() {
      var toolbarContainer = $(' <div class="lui_tree_toolbar clearfloat" />')
      toolbarContainer.appendTo(this.container)

      var addBtn = toolbar.buildButton({
        text: lang['sysPortalPage.desgin.msg.addContents']
      })

      var self = this
      addBtn.onClick = function() {
        self.addRoot()
      }
      addBtn.draw()

      var delBtn = toolbar.buildButton({
        text: lang['sysPortalPage.desgin.opt.delete'],
        styleClass: 'lui_toolbar_btn_gray'
      })

      delBtn.onClick = function() {
        self.delAll()
      }
      delBtn.draw()

      delBtn.element.appendTo(toolbarContainer)
      addBtn.element.appendTo(toolbarContainer)
    },

    // 初始化表格
    initTable: function() {
      this.table = $('<table class="lui_tree_tb"/>')
      
      var no = lang['sysPortalPage.desgin.opt.no']
      var contentName = lang['sysPortalPage.desgin.opt.contentsName']
      var parentContent = lang['sysPortalPage.desgin.opt.parentContent']
      var link = lang['sysPortalTopic.link']
      var opts = lang['sysPortalMain.fdOpts']
      var tr = $('<tr>')
      var th1 = $('<th class="th1"></th>')
      var th2 = $('<th class="th6">'+no+'</th>')
      var th3 = $('<th class="th2">'+contentName+'</th>')
      var th4 = $('<th class="th3">'+parentContent+'</th>')
      var th5 = $('<th class="th4">'+link+'</th>')
      var th6 = $('<th class="th5">'+opts+'</th>')

      this.checkboxNode = $('<input type="checkbox"/>')

      this.checkboxNode.on('click', this.checked.bind(this))

      th1.prepend(this.checkboxNode)

      tr.append(th1)
      tr.append(th2)
      tr.append(th3)
      tr.append(th4)
      tr.append(th5)
      tr.append(th6)
      this.table.append(tr)
      this.table.appendTo(this.container)
    },

    // 初始化事件
    initEvent: function() {
      topic.subscribe(ADD_SUB, this.addSub, this)
      topic.subscribe(ADD_SILB, this.addSilb, this)
      topic.subscribe(UP, this.up, this)
      topic.subscribe(DOWN, this.down, this)
    },

    // 获取对应行索引
    getIndexByItem: function(item) {
      for (var i = 0; i < this.items.length; i++) {
        if (this.items[i] == item) {
          return i
        }
      }
    },

    // 最后一个子节点
    lastChild: function(target) {
      var items = this.getItems()
      for (var i = items.length - 1; i >= 0; i--) {
        var item = items[i]
        var flag = false
        while (item.parent) {
          if (item.parent == target) {
            flag = true
            break
          }
          item = item.parent
        }
        if (flag) {
          return items[i]
        }
      }
      return target
    },

    // 选中
    checked: function(evt) {
      this.table.find('input[type=checkbox].checkbox').each(function() {
        this.checked = evt.target.checked
      })
    },

    // 添加一级目录
    addRoot: function() {
      var item = new Item({ container: this, parent: this })
      this.setItem(item)
    },

    // 添加同级节点
    addSilb: function(evt) {
      if (this != evt.container) {
        return
      }

      var obj = evt.obj

      var config = { container: this, parent: this }

      // 同父
      if (obj.parent) {
        config.parent = obj.parent
      }

      // 添加同级节点插入到本节点所有子节点后面的位置

      obj = this.lastChild(obj)

      var item = new Item(config)
      this.setItem(item, obj)
    },

    // 获取节点层级
    getLevel: function(item) {
      var i = 0
      while (item.parent) {
        i++
        item = item.parent
      }
      return i
    },

    // 添加子节点
    addSub: function(evt) {
      if (this != evt.container) {
        return
      }
      var level = this.getLevel(evt.obj)

      if (this.levelLimit <= level) {
        dialog.alert('此目录只支持' + this.levelLimit + '级')
        return
      }

      var item = new Item({ container: this, parent: evt.obj })
      this.setItem(item, evt.obj)
    },

    // 是否同个父节点
    isSild: function(obj, target) {
      // 同为根节点
      if (!obj.parent && !target.parent) {
        return true
      }

      if (obj.parent == target.parent) {
        return true
      }
      return false
    },

    // 上移
    up: function(evt) {
      if (this != evt.container) {
        return
      }

      var currentItem = evt.obj
      var currentIndex = this.getIndexByItem(currentItem)

      if (currentIndex == 0) {
        dialog.alert('已经移至最顶，请确认')
        return
      }

      var preIndex = currentIndex - 1
      var preItem = this.items[preIndex]

      // 同级之间才可以移动
      if (this.isSild(currentItem, preItem)) {
        this.items[currentIndex] = this.items.splice(
          preIndex,
          1,
          currentItem
        )[0]
        preItem.element.insertAfter(currentItem.element)
      } else {
        dialog.alert('只能在同级之间进行移动')
      }
    },

    // 下移
    down: function(evt) {
      if (this != evt.container) {
        return
      }

      var currentItem = evt.obj
      var currentIndex = this.getIndexByItem(currentItem)
      var nextIndex = currentIndex + 1

      if (nextIndex >= this.items.length) {
        dialog.alert('已经移至底部，请确认')
        return
      }

      var nextItem = this.items[nextIndex]

      // 同级之间才可以移动
      if (this.isSild(currentItem, nextItem)) {
        this.items[currentIndex] = this.items.splice(
          nextIndex,
          1,
          currentItem
        )[0]
        currentItem.element.insertAfter(nextItem.element)
      } else {
        dialog.alert('只能在同级之间进行移动')
      }
    },

    // 删除
    del: function(obj) {
      var index = obj
        .parent()
        .parent()
        .index()

      index = index - 1
      // 删除数据
      topic.publish(DEL, { obj: this.items[index] })
    },

    // 批量删除
    delAll: function() {
      var checkedBox = this.table.find('input[type=checkbox].checkbox:checked')
      if (checkedBox.lenghth == 0) {
        dialog.alert('您没有选择需要操作的数据')
        return
      }

      var self = this
      checkedBox.each(function() {
        self.del($(this))
      })
    }
  })

  // 行对象
  var Item = base.Base.extend({
    nameText: null,
    urlText: null,
    expanded: false,

    // 初始化
    initProps: function($super, config) {
      $super()

      if (config.isHide) {
        this.isHide = true
      }

      // 🌲容器
      this.container = config.container

      // 目录名值
      if (config.nameText) {
        this.nameText = config.nameText
      }

      // 链接值
      if (config.urlText) {
        this.urlText = config.urlText
      }

      // 父节点
      this.parent = config.parent

      this.initTr()
      this.initEvent()
    },

    // 初始化事件绑定
    initEvent: function() {
      topic.subscribe(NAMETEXT_CHANGE, this.nameTextChange, this)
      topic.subscribe(DEL, this.del, this)

      topic.subscribe(EXPAND_SHOW, this.show, this)
      topic.subscribe(EXPAND_HIDE, this.hide, this)
      topic.subscribe(SERIAL_CHANGE, this.serialChange, this)
    },

    show: function(evt) {
      var parent = evt.obj
      if (this.parent == parent) {
        this.element.show(300)
      }
    },

    hide: function(evt) {
      var parent = evt.obj
      var self = this
      while (self.parent) {
        if (self.parent == parent) {
          this.element.hide()
          this.expanded = false

          this.expandNode.attr('src', EXPAND_HIDE_ICON_URL)
          break
        }

        self = self.parent
      }
    },

    // 目录标题发生变化触发的行为
    nameTextChange: function(evt) {
      var obj = evt.obj
      if (obj == this.parent) {
        this.parentNode.val(obj.nameText)
      }
    },

    // 是否为根节点
    isRoot: function() {
      return this.parent == this.container
    },

    // 初始化行对象
    initTr: function() {
      this.element = $('<tr style="display:none">')

      if (!this.isHide) {
        this.element.show()
      }

      if (this.isRoot()) {
        this.element.addClass('lui_tree_tr_root')
      }

      var tdTmp = '<td></td>'

      this.initCheckbox()
      var checkboxTd = $(tdTmp)
      checkboxTd.append(this.checkboxNode)
      this.element.append(checkboxTd)

      this.initSerial()
      var serialTd = $(tdTmp)
      serialTd.addClass('td_left')
      serialTd.append(this.serialNode)
      this.element.append(serialTd)

      this.initName()
      var nameTd = $(tdTmp)
      nameTd.append(this.nameNode)
      this.element.append(nameTd)

      this.initParentName()
      var parentTd = $(tdTmp)

      parentTd.append(this.parentNode)
      this.element.append(parentTd)

      this.initUrl()
      var urlTd = $(tdTmp)
      urlTd.append(this.urlNode)
      this.element.append(urlTd)

      this.initOperations()
      var operationsTd = $(tdTmp)
      operationsTd.append(this.operationsNode)
      this.element.append(operationsTd)
    },

    // 初始化多选框
    initCheckbox: function() {
      this.checkboxNode = $('<input type="checkbox" class="checkbox" >')
    },

    // 初始化序号
    initSerial: function() {
      this.serialNode = $('<div class="serial" />')
      this.serialTextNode = $('<span />')
      this.serialTextNode.appendTo(this.serialNode)

      this.buildSerial()

      this.expandNode = $('<img src="' + EXPAND_HIDE_ICON_URL + '" />')
      this.expandNode.prependTo(this.serialNode)
      this.serialNode.on('click', this.expandTrigger.bind(this))
    },

    // 展开收起点击操作
    expandTrigger: function(evt, expanded) {
      if (!evt) {
        this.expanded = !expanded
      }

      if (this.expanded) {
        this.expandNode.attr('src', EXPAND_HIDE_ICON_URL)
        topic.publish(EXPAND_HIDE, { obj: this })
        this.expanded = false
      } else {
        this.expandNode.attr('src', EXPAND_SHOW_ICON_URL)
        topic.publish(EXPAND_SHOW, { obj: this })
        this.expanded = true
      }
    },

    serialChange: function(evt) {
      var target = evt.obj

      if (target == this.parent) {
        this.buildSerial()
      }
    },

    buildSerial: function() {
      var items = this.container.getItems()
      var parent = this.parent
      this.index = 1

      for (var i = 0; i < items.length; i++) {
        var item = items[i]
        if (parent == item.parent) {
          if (this == item) {
            break
          }
          this.index++
        }
      }

      var self = this

      var serial = this.index
      while (self.parent) {
        if (self.parent.index) {
          serial = self.parent.index + '.' + serial
        }
        self = self.parent
      }

      this.serialTextNode.html(serial)
    },

    // 上级目录
    initParentName: function() {
      this.parentNode = $(
        '<input type="text" name="parentName" class="inputsgl" readOnly>'
      )

      if (this.parent) {
        this.parentNode.val(this.parent.nameText)
      }
    },

    // 目录
    initName: function() {
      this.nameNode = $('<input type="text" name="name" class="inputsgl" >')
      if (this.nameText) {
        this.nameNode.val(this.nameText)
      }
      this.nameNode.on('blur', this.nameBlur.bind(this))

      // 优化体验
      setTimeout(
        function() {
          this.nameNode.focus()
        }.bind(this),
        1
      )
    },

    // 目录标题焦点丢失事件
    nameBlur: function(evt) {
      var target = $(evt.target)
      this.nameText = target.val()
      topic.publish(NAMETEXT_CHANGE, { obj: this })
    },

    // 链接焦点丢失事件
    urlBlur: function(evt) {
      var target = $(evt.target)
      this.urlText = target.val()
    },

    // 链接
    initUrl: function() {
      this.urlNode = $('<input type="text" name="url" class="inputsgl" >')

      if (this.urlText) {
        this.urlNode.val(this.urlText)
      }
      this.urlNode.on('blur', this.urlBlur.bind(this))
    },

    // 同级点击
    siblClick: function() {
      topic.publish(ADD_SILB, { container: this.container, obj: this })
      topic.publish(SERIAL_CHANGE, { obj: this.parent })
    },

    // 子级点击
    subClick: function() {
      topic.publish(ADD_SUB, { container: this.container, obj: this })
      topic.publish(SERIAL_CHANGE, { obj: this })
      this.expandTrigger(null, true)
    },

    // 上移点击
    upClick: function() {
      topic.publish(UP, { container: this.container, obj: this })
      topic.publish(SERIAL_CHANGE, { obj: this.parent })
    },

    // 下移点击
    downClick: function() {
      topic.publish(DOWN, { container: this.container, obj: this })
      topic.publish(SERIAL_CHANGE, { obj: this.parent })
    },

    // 操作栏
    initOperations: function() {
    	var ch1 = lang['sysPortalMapTplNavCustom.lable.addBro']
    	var ch2 = lang['sysPortalMapTplNavCustom.lable.addChild']
    	var ch3 = lang['sysPortalPage.desgin.opt.moveup']
    	var ch4 = lang['sysPortalPage.desgin.opt.movedown']
    	
    	
      this.operationsNode = $('<div class="lui_tree_bar">')

      this.siblNode = $('<span class="sibling" title="增加同级">'+ch1+'</span>')
      this.siblNode.on('click', this.siblClick.bind(this))

      this.subNode = $('<span class="sub" title="增加子级">'+ch2+'</span>')
      this.subNode.on('click', this.subClick.bind(this))

      this.upNode = $('<span class="up" title="上移">'+ch3+'</span>')
      this.upNode.on('click', this.upClick.bind(this))

      this.downNode = $('<span class="down" title="下移">'+ch4+'</span>')
      this.downNode.on('click', this.downClick.bind(this))

      this.siblNode.appendTo(this.operationsNode)
      this.subNode.appendTo(this.operationsNode)
      this.upNode.appendTo(this.operationsNode)
      this.downNode.appendTo(this.operationsNode)
    },

    // 删除
    del: function(evt) {
      var item = evt.obj
      // 删除自己
      if (item == this) {
        this.destroy()
      }

      // 删除子节点
      var current = this
      while (current.parent) {
        if (item == current.parent) {
          this.destroy()
        }
        current = current.parent
      }

      topic.publish(SERIAL_CHANGE, { obj: this.parent })
    },

    destroy: function($super) {
      $super()
      this.container.delItem(this)
    }
  })

  exports.Tree = Tree
})
