define([
  "dojo/_base/declare",
  "mui/form/Address",
  "sys/xform/mobile/controls/xformUtil",
  "dojo/query",
  "mui/i18n/i18n!sys-mobile",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/topic",
  "dojo/ready",
  "mui/util"
], function(
  declare,
  Address,
  xUtil,
  query,
  Msg,
  domConstruct,
  domStyle,
  topic,
  ready,
  util
) {
  var NewAddress = declare("sys.xform.mobile.controls.NewAddress", [Address], {
    // 在明细表删除行操作中，需要更新索引的属性
    needToUpdateAttInDetail: ["idField", "nameField", "key"],

    scope: "", //"11"全部 "22"本机构"33"本部门"44"自定义

    scope_id: "", //公式定义的表达式中所含控件id

    scopeType: "", //"org"组织架构选取 "formula"公式定义选取

    controlId: "",

    outputParams: "",

    autoload: "false",

    _inDetailTable: false,
    
    deptLimit:"",

    tipMsg: Msg["mui.form.please.select"],

    dataUrl: "/sys/organization/mobile/address.do?method=addCustomList",
    
    searchUrl:
      "/sys/organization/mobile/address.do?method=searchList&keyword=!{keyword}&orgType=!{orgType}&deptLimit=!{deptLimit}",
      
    // 生态组织才是true
    isEco: false,

    buildRendering: function() {
      if (/\.(\d+)\./g.test(this.get("name"))) {
        this._inDetailTable = true;
      } else {
        this._inDetailTable = false;
      }
      if (this.scope == "22" || this.scope == "33" || this.scope == "44" || this.scope == "55") {
        this.dataUrl +=
          "&scope=" +
          this.scope +
          "&scopeType=" +
          this.scopeType +
          "&orgType=" +
          encodeURIComponent(this.type);
        if(this.scope == "55"){
        	 this.dataUrl += "&deptLimit=!{deptLimit}";
        }
        this.searchUrl = this.dataUrl + "&keyword=!{keyword}";
        if (this.isMul) {
          this.jsURL = "/sys/xform/mobile/controls/newAddress/address_mul.js!";
        } else {
          this.jsURL = "/sys/xform/mobile/controls/newAddress/address_sgl.js!";
        }
      } else {
        this.isNew = true;
        this.dataUrl =
          "/sys/organization/mobile/address.do?method=addressList&parentId=!{parentId}&orgType=!{selType}&deptLimit=!{deptLimit}&maxPageSize=!{maxPageSize}";
      }

      this.inherited(arguments);
    },

    _setIsMulAttr:function(mul){
    	if (this.scope == "22" || this.scope == "33" || this.scope == "44" || this.scope == "55") {
			 this._set('isMul' , mul);
			 
			 if (this.isMul) {
		          this.jsURL = "/sys/xform/mobile/controls/newAddress/address_mul.js!";
		        } else {
		          this.jsURL = "/sys/xform/mobile/controls/newAddress/address_sgl.js!";
		        }
		 }else{
			 this.inherited(arguments);
		 }
	},
    
    startup: function() {
      this.inherited(arguments);
      
      //新建时有初始值和传参时自动加载
       //#157884 权限区段控制的明细表，明细表内的高级地址本，在有编辑权限的流程节点，设置了初始值，无法传出参数
        if ((this.right=="edit") &&
        this.autoload &&
        this.autoload == "true"
      ) {
        var self = this;
        ready(function() {
          self.defer(function() {
            self.setOutputParams();
          }, 30);
        });
      }
      if (this.right=="edit") {
        //有传参时，监听值改变事件
        var s = this.type;
        var n = s.split("_").length - 1;
        if (this.outputParams != null && this.outputParams != "" && n == 2) {
          this.subscribe(this.EVENT_VALUE_CHANGE, "_onValueChange");
        }
      } else {
        this.disconnect(this.domNodeOnClickEvent);
      }
      //清空值
      if (this.isFormulaLoad) {
        this.subscribe(this.EVENT_VALUE_CHANGE, "_clearValue");
      }
    },

    buildValue: function(domContainer) {
      this.inherited(arguments);
      if (this.muiCategoryAddNode) {
        if (this.edit && (!this.curIds || this.isMul)) {
          domStyle.set(this.muiCategoryAddNode, "display", "inline-block");
        } else {
          domStyle.set(this.muiCategoryAddNode, "display", "none");
        }
      }
      //在以桌面端显示的时候需要更新高度
      topic.publish("/mui/list/resize", this);
    },

    _selectCate: function() {
      this._buildNewAddressValue();
      this.inherited(arguments);
    },

    _buildNewAddressValue: function() {
      if (this.scope == "44") {
        var params = this._setAddressParam();
        this.dataUrl =
          "/sys/organization/mobile/address.do?method=addCustomList&scope=" +
          this.scope +
          "&scopeType=" +
          this.scopeType +
          "&orgType=" +
          encodeURIComponent(this.type) +
          "&" +
          params;
        this.searchUrl = this.dataUrl + "&keyword=!{keyword}";
      }
    },

    _setAddressParam: function() {
      if (
        this.scopeType == "formula" &&
        this.scope_id != "" &&
        this.scope_id != null
      ) {
        var autoFiledIds = this.scope_id.split(";");
        var filedsJSON = {};
        //用来存储 公式绑定域的属性和值如：{fd_134343:aaaa,fd_dfdafafd:bbbb}
        var rowIndex = this.get("name").match(/\.(\d+)\./g);
        if (rowIndex != null) {
          var detailFromId = this.get("name")
            .match(/\((\w+)\./g)[0]
            .replace("(", "")
            .replace(".", "");
        }
        for (var i = 0; i < autoFiledIds.length; i++) {
          var valueInfo = [];
          if (autoFiledIds[i].indexOf(".") > -1 && this._inDetailTable) {
            //地址本和所需赋值的控件都在明细表
            var wgt = "";
            var fieldId = autoFiledIds[i];
            if(/_text/.test(fieldId) && (fieldId.length - "_text".length == fieldId.indexOf("_text"))){
              // 如果是_text的则拿input结构的值，因为没有dojo结构所以拿不到
              fieldId = fieldId.replace(".",rowIndex);
              valueInfo.push(query("[name*='"+fieldId+"']").val());
            }else{
              wgt = xUtil.getXformWidget(
                  "TABLE_DL_" + detailFromId,
                  autoFiledIds[i].replace(".", rowIndex)
              );
              if (
                  wgt.declaredClass == "mui.form.Address" ||
                  wgt.declaredClass == "sys.xform.mobile.controls.NewAddress"
              ) {
                valueInfo.push(wgt.curIds);
                valueInfo.push(wgt.curNames);
              } else {
                valueInfo.push(wgt.value);
              }
            }

          } else if (autoFiledIds[i].indexOf(".") < 0 && !this._inDetailTable) {
            //地址本和所需赋值的控件都不在明细表
            var wgts = "";
            var fieldId = autoFiledIds[i];
            if(/_text/.test(fieldId) && (fieldId.length - "_text".length == fieldId.indexOf("_text"))){
              // 如果是_text的则拿input结构的值，因为没有dojo结构所以拿不到
              valueInfo.push(query("[name*='"+fieldId+"']").val());
            }else{
              wgts = xUtil.getXformWidgetsBlur(null, autoFiledIds[i]);
              for (var j = 0; j < wgts.length; j++) {
                if (
                    wgts[j].declaredClass == "mui.form.Address" ||
                    wgts[j].declaredClass == "sys.xform.mobile.controls.NewAddress"
                ) {
                  valueInfo.push(wgts[j].curIds);
                  valueInfo.push(wgts[j].curNames);
                } else {
                  valueInfo.push(wgts[j].value);
                }
              }
            }

          }
          if (!valueInfo || valueInfo.length == 0) {
            valueInfo = "null";
          }
          filedsJSON[autoFiledIds[i]] = valueInfo;
        }
      }
      //请求参数数组
      var paramArray = new Array();
      //url格式的参数
      for (filedId in filedsJSON) {
        if (filedsJSON[filedId] != "null") {
          paramArray.push(
            encodeURIComponent(filedId) +
              "=" +
              encodeURIComponent(filedsJSON[filedId])
          );
        }
      }
      paramArray.push("modelId=" + encodeURIComponent(_xformMainModelId));
      paramArray.push("modelName=" + encodeURIComponent(_xformMainModelClass));

      var _extendFilePath = document.getElementsByName(
        "extendDataFormInfo.extendFilePath"
      )[0];
      if (_extendFilePath) {
        var extendFilePath = _extendFilePath.value;
        paramArray.push("extendFilePath=" + encodeURIComponent(extendFilePath));
      }
      var fdControlId = "";
      if (this._inDetailTable) {
        var rowIndex = this.get("name").match(/\.(\d+)\./g);
        var detailFromId = this.get("name")
          .match(/\((\w+)\./g)[0]
          .replace("(", "")
          .replace(".", "");
        fdControlId = detailFromId + rowIndex + this.controlId;
      } else {
        fdControlId = this.controlId;
      }
      paramArray.push("fdControlId=" + encodeURIComponent(fdControlId));
      return paramArray.join("&");
    },

    _onValueChange: function(srcObj, arguContext) {
      if (srcObj && srcObj == this) {
        this.setOutputParams();
      }
    },

    setOutputParams: function() {
      var self = this;
      $.ajax({
        url: Com_Parameter.ContextPath + "sys/xform/controls/address.do?method=getInfos&orgType="+encodeURIComponent(self.type)+
            "&id="+self.curIds+"&outputParams="+encodeURIComponent(self.outputParams)+"&fdControlId="+self.controlId,
        type:'GET',
        async:true,//异步请求
        success: function(json){
          if(json && json.results){
            var fdControlId = json.fdControlId;
            var results = json.results;
            for(var fieldIdForm in results){
              var msg = '';
              var info = results[fieldIdForm];
              if(info.length>0){
                for(var i=0;i<info.length-1;i++){
                  msg+=info[i].fd+';';
                }
                msg+=info[info.length-1].fd;
              }
              var rowIndex = self.get("name").match(/\.(\d+)\./g);
              if (rowIndex != null) {
                var detailFromId = self.get("name")
                    .match(/\((\w+)\./g)[0]
                    .replace("(", "")
                    .replace(".", "");
              }
              if (fieldIdForm.indexOf(".") > -1 && self._inDetailTable) {
                //地址本和所需赋值的控件都在明细表
                fieldIdForm = fieldIdForm.replace(".", rowIndex);
                var wgt = xUtil.getXformWidget("TABLE_DL_" + detailFromId, fieldIdForm);
                self._filedSetValue(wgt, msg, fieldIdForm);
              } else if (fieldIdForm.indexOf(".") < 0 && !self._inDetailTable) {
                //地址本和所需赋值的控件都不在明细表
                var wgts = xUtil.getXformWidgetsBlur(null, fieldIdForm);
                for (var i = 0; i < wgts.length; i++) {
                  self._filedSetValue(wgts[i], msg, fieldIdForm);
                }
              } else if (fieldIdForm.indexOf(".") > -1 && !self._inDetailTable) {
                //地址本不在，所需赋值的控件在
                continue;
              } else if (fieldIdForm.indexOf(".") < 0 && self._inDetailTable) {
                //地址本在，所需赋值的控件不在
              }
            }
          }
        },
        dataType: 'json'
      });
    },

    _filedSetValue: function(wgt, val, prop) {
      if (wgt) {
        if (
          wgt.declaredClass == "mui.form.Address" ||
          wgt.declaredClass == "sys.xform.mobile.controls.NewAddress"
        ) {
          if (val instanceof Array) {
            wgt.set("curIds", val[0]);
            wgt.set("curNames", val[1] != null ? val[1] : val[0]);
          } else {
            wgt.set("curIds", val);
            wgt.set("curNames", val);
          }
        } else if (wgt.declaredClass == "mui.form.RadioGroup") {
          // 单选
          var childrenArray = wgt.getChildren();
          for (var i = 0; i < childrenArray.length; i++) {
            if (childrenArray[i].value == val && childrenArray[i]._onClick) {
              childrenArray[i]._onClick();
            }
          }
        } else if (wgt.declaredClass == "mui.form.CheckBoxGroup") {
          var childrenArray = wgt.getChildren();
          // 多选
          var valArray = val.split(";");
          // 遍历子组件
          for (var i = 0; i < childrenArray.length; i++) {
            if (childrenArray[i]._onClick) {
              if (
                childrenArray[i].value &&
                valArray.indexOf(childrenArray[i].value) > -1
              ) {
                // 此处设置相反，在_onClick的方法里面会取反
                childrenArray[i].checked = false;
              } else {
                childrenArray[i].checked = true;
              }
              childrenArray[i]._onClick();
            }
          }
        } else {
          wgt.set("value", val);
        }
      } else {
        //如果找不到控件，有可能是文本值，即ID_text
        if (
          this.textName &&
          prop.indexOf("_text") > -1 &&
          this.textName.indexOf(prop) > -1
        ) {
          this.set("text", val);
        }
      }
    },
    _clearValue: function(srcObj, argus) {
      if (srcObj != null && this != srcObj && this.isFormulaLoad) {
        // 判断源控件是否是表达式里面中的变量，只有相关控件才能出发计算
        var tmpName = xUtil.parseName(srcObj);
        if (
          tmpName != null &&
          tmpName != "" &&
          this._isRelationControl(tmpName)
        ) {
          this.set("curNames", "");
          this.set("curIds", "");
          query(".muiAddressOrg", this.domNode).remove();
        }
      }
    },
    _isRelationControl: function(sourceName) {
      // 是否是相关控件触发了计算
      var isInDetail = xUtil.isInDetail(this);
      var expression = this.scope_id;
      var name = xUtil.parseName(this);
      var flag = false;
      //在明细表
      if (/\.(\d+)\./g.test(name)) {
        // sourceName在明细表
        if (/\.(\d+)\./g.test(sourceName)) {
          // 判断索引是否一致
          var calIndex = name.match(/\.(\d+)\./g)[0];
          var sourceIndex = sourceName.match(/\.(\d+)\./g)[0];
          if (calIndex == sourceIndex) {
            if (expression.indexOf(sourceName.replace(sourceIndex, ".")) > -1) {
              flag = true;
            }
          }
        } else {
          if (expression.indexOf(sourceName) > -1) {
            flag = true;
          }
        }
      } else if (isInDetail) {
        // 在明细表的统计航里面时，name不含明细表ID
        // 清掉索引
        var indexs = sourceName.match(/\.(\d+)\./g);
        indexs = indexs ? indexs : [];
        if (indexs.length > 0) {
          sourceName = sourceName.replace(indexs[0], ".");
        }
        if (expression.indexOf(sourceName) > -1) {
          flag = true;
        }
      } else {
        sourceName = sourceName
          ? sourceName.replace(/\.(\d+)\./g, ".")
          : sourceName;
        if (expression.indexOf(sourceName) > -1) {
          flag = true;
        }
      }
      return flag;
    },
    getText: function() {
      var text = [];
      if (this.curNames != null && this.curNames != "") {
        var curNameArr = this.curNames.split(";");
        for (var i = 0; i < curNameArr.length; i++) {
          text.push(curNameArr[i]);
        }
      }
      return text.join(";");
    }
  });
  return NewAddress;
});
