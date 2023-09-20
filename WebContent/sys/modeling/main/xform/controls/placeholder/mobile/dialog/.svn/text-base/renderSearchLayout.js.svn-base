/**
 * 筛选
 */
define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "dojo/topic",
  "mui/i18n/i18n!sys-xform-base:mui",
  "mui/header/HeaderItem",
  "mui/folder/_Folder",
  "mui/dialog/Dialog",
  "dojo/_base/lang",
  "dojo/_base/array",
  "mui/i18n/i18n!sys-mobile:mui",
  "mui/dialog/Tip",
  "mui/i18n/i18n!sys-modeling-main"
], function(
  declare,
  domConstruct,
  topic,
  Msg,
  HeaderItem,
  _Folder,
  Dialog,
  lang,
  array,
  MuiMsg,
  Tip,
  modelingLang
) {
  return declare(
    "sys.modeling.main.xform.controls.placeholder.mobile.dialog.renderSearchLayout",
    [HeaderItem, _Folder],
    {
      baseClass: "muiHeaderItem muiHeaderItemIcon",
      icon: "fontmuis muis-menu",

      searchItems: [],
      searchInputs: [],
      searchValuesList :{},
      show: function() {
        //每次进来后清空选项配置，后重新构建筛选样式组件
        this.searchInputs = [];
        this.searchContentNode = [];
        this.searchContentNode =  this.buildSearchContent();
        var self = this;
        var buttons = [
          {
            title: MuiMsg["mui.button.cancel"],
            fn: function(dialog) {
              dialog.hide();
            }
          },
          {
            title: Msg["mui.event.search.ok"],
            fn: function(dialog) {
              self.ok(dialog);
            }
          },
          {
            title: Msg["mui.event.search.reset"],
            fn: function() {
              self.reset();
            }
          }
        ];

        this.dialog = Dialog.element({
          element: self.searchContentNode,
          buttons: buttons,
          position: "bottom",
          showClass: this.showClass ? this.showClass : "muiDialogSelect",
          callback: lang.hitch(this, function() {
            topic.publish(this.SELECT_CALLBACK, this);
            this.dialog = null;
          })
        });
      },

      initFilter: function(argu, dataSource, showItems) {
        this.searchValuesList= {};
        this.argu = argu;
        this.dataSource = dataSource;
        this.searchItems = showItems;
      },

      buildSearchContent: function() {
        var contentNode = domConstruct.create("div", {
          className: "searchWrap"
        });
        for (var i = 0; i < this.searchItems.length; i++) {
          this.buildSearchItem(this.searchItems[i], contentNode);
          if (i === 0) {
            this.connect(
              this.searchInputs[0].dom,
              "change",
              "_searchValChange"
            );
          }
        }
        return contentNode;
      },

      _searchValChange: function(event) {
        var value = event.target.value;
        topic.publish("/sys/xform/event/search", this, { value: value });
      },

      buildSearchItem: function(item, container) {
        var searchItem = domConstruct.create(
          "div",
          { className: "searchItem" },
          container
        );
        domConstruct.create(
          "span",
          { innerHTML: item.sdesc + "：" },
          searchItem
        );
        if (item.stype === 'String' || item.stype === 'Double'){//字符类型和数字类型
            if(item.enumValues){ //枚举类型
                this.buildEnumGroup(item,searchItem);
            }else{//单行文本
               this.buildInputGroup(item,searchItem);
            }
        }else if(item.stype.indexOf("com.landray.kmss.sys.organization") > -1){ //地址本
           this.buildAddressGroup(item,searchItem);
        }else if(item.stype === "Date" || item.stype === "DateTime" || item.stype === "Time"){//日期时间类型
            this.buildDateGroup(item,searchItem);
        }else{//单行文本
          this.buildInputGroup(item,searchItem);
        }
      },
      buildInputGroup : function(item ,searchItem){
          //获取之前输入过的旧值，用于回显数据
          var inputValue= "";
          if(JSON.stringify(this.searchValuesList) != "{}"){
              if(this.searchValuesList[item.columnName]){
                  inputValue = this.searchValuesList[item.columnName];
              }
          }
          var inputDom = domConstruct.create("div", {
          "data-dojo-type": "mui/form/Input",
          "data-dojo-props": lang.replace('name:"{name}",subject:"{subject}",value:"{value}"',{
            name: item.columnName,
            subject : item.sdesc,
            value: inputValue
          })
        }, searchItem);
        this.searchInputs.push({ item: item, dom: inputDom });
      },
      buildAddressGroup : function(item ,searchItem){
          // 看表单地址本的设计，只要不是“person”，都是“element”
          var addressId= "";
          var addressName= "";
          if(JSON.stringify(this.searchValuesList) != "{}"){
              if(this.searchValuesList[item.columnName+".id"]){
                  addressId = this.searchValuesList[item.columnName+".id"];
              }
              if(this.searchValuesList[item.columnName+".name"]){
                  addressName = this.searchValuesList[item.columnName+".name"];
              }
          }
          var addressType = this.addressTypeTransFilter(item) || "";
          addressType = lang.isString(addressType) ? addressType : (addressType + "");
          var type = item.stype === "com.landray.kmss.sys.organization.model.SysOrgPerson" ? "ORG_TYPE_PERSON" : "ORG_TYPE_ALLORG";
          var addressDom = domConstruct.create("div",{
            "data-dojo-type": "mui/form/Address",
            "data-dojo-props" : lang.replace('type:"{type}" , idField : "{idField}" , nameField : "{nameField}",subject : "{subject}",curNames : "{curNames}",curIds : "{curIds}"',{
              type : addressType,
              idField: item.columnName+".id",
              nameField: item.columnName+".name",
              subject : item.sdesc,
              curNames:addressName,
              curIds : addressId
            })
          },searchItem);
        this.searchInputs.push({ item: item, dom: addressDom });
      },
      addressTypeTransFilter : function(item){
            var code = ORG_TYPE_ALL + "";
            if(item.customProperties && item.customProperties.orgType){
                code = item.customProperties.orgType;
            }
            return code;
      },
      buildDateGroup : function(item ,searchItem){
          var inputValue= "";
          if(JSON.stringify(this.searchValuesList) != "{}"){
              if(this.searchValuesList[item.columnName]){
                  inputValue = this.searchValuesList[item.columnName];
              }
          }
        var dateGroupDom = domConstruct.create("div", {
          "data-dojo-type": "mui/form/DateTime",
          "data-dojo-mixins": "mui/datetime/_"+item.stype+"Mixin",
          "data-dojo-props": lang.replace('valueField:"{valueField}",name:"{name}",subject:"{subject}",value:"{value}"',{
            valueField: item.columnName +"_date",
            name: item.columnName+"_date",
            subject : item.sdesc,
            value : inputValue
          })
        }, searchItem);
        this.searchInputs.push({ item: item, dom: dateGroupDom });
      },
      buildEnumGroup : function(item,searchItem){
          var array = new Array();
          var enumValues =item.enumValues;
          var enumArray = enumValues.split(';');
          var inputValue= "";
          if(JSON.stringify(this.searchValuesList) != "{}"){
              if(this.searchValuesList[item.columnName]){
                  inputValue = this.searchValuesList[item.columnName];
              }
          }
          var  inputValueArray =  inputValue.split(";");
          for (var i = 0; i < enumArray.length; i++) {
            var enumArrayinfo=enumArray[i];
            var ev = enumArrayinfo.split('|');
            var obj = {checked: false};
            obj["text"] = ev[0];
            obj["value"] = ev[1];
            //再次编辑时判断是否有值,有值设置为选中
            if (inputValueArray.length>0){
                for (var j = 0; j < inputValueArray.length; j++) {
                    if(inputValueArray[j] === obj["value"]){
                        obj["checked"] = true;
                    }
                }
            }
            array.push(obj);
          }
        if (array.length > 0) {
          var arrStr = JSON.stringify(array)
          var jsonArray = arrStr.replace(/\"/g, "'")
          if (item.businessType === 'select') { //下拉框
            var select = domConstruct.create("div" , {
              "data-dojo-type" : "mui/form/Select",
              "data-dojo-props":'"name":"'+item.columnName+'","value":"'+inputValue+'", "mul":false ,"concentrate": "false","store":'+jsonArray+''
            },searchItem);
            this.searchInputs.push({ item: item, dom: select });
          } else if(item.businessType === 'inputRadio'){ //单选框
            var inputRadio = domConstruct.create("div" , {
              "className":"muiRadioCircle__modeling",
              "data-dojo-type" : "mui/form/RadioGroup",
              "data-dojo-props":"'name':'"+item.columnName+"', 'mul':'false' ,'concentrate': 'false','store':"+jsonArray+""
            },searchItem);
            this.searchInputs.push({ item: item, dom: inputRadio });
          }else if(item.businessType === 'fSelect'){//复选下拉框
            var fSelect = domConstruct.create("div" , {
              "data-dojo-type" : "mui/form/Select",
              "data-dojo-props":'"name":"'+item.columnName+'", "value":"'+inputValue+'","mul":true ,"concentrate": "false","store":'+jsonArray+''
            }, searchItem);
            this.searchInputs.push({ item: item, dom: fSelect });
          }else if(item.businessType === 'inputCheckbox'){//多选框
            var inputCheckbox = domConstruct.create("div" , {
              "data-dojo-type" : "mui/form/CheckBoxGroup",
              "className":"muiCheckBoxCircle_modeling",
              "style" : "width:50%",
              "data-dojo-props":'"name":"'+item.columnName+'","subject":"'+item.sdesc+'", "mul":"false" ,"concentrate": "false","store":'+jsonArray+''
            }, searchItem);
            this.searchInputs.push({ item: item, dom: inputCheckbox });
          }
        }
      },
      // 仿EventSearchBarList的searchItem方法
      searchValues: function(dialog) {
        var allOuterSearchParams = this.argu.outerSearchParams;
        var outerSearchParams = [];
        //数字类型的输入框校验是否是数字类型
        var doubleValue = true ;
        for (var i = 0; i < this.searchInputs.length; i++) {
          var searchItem = this.searchInputs[i];
          if (searchItem.item.group) {
            array.forEach(allOuterSearchParams, function(outerSearch) {
              // 同组的属性值一样
              if (outerSearch.group == searchItem.item.group) {
                outerSearch.value = searchItem.dom.value;
                outerSearchParams.push(outerSearch);
              }
            });
          } else {
            var inputName= "";
            if(searchItem.item.stype.indexOf("com.landray.kmss.sys.organization") > -1){
              inputName = searchItem.item.columnName + ".id";
            }else if(searchItem.item.stype === "Date" || searchItem.item.stype === "DateTime" || searchItem.item.stype === "Time"){
                inputName = searchItem.item.columnName +"_date";
            }else{
              inputName = searchItem.item.columnName;
            }
            var value = $("input[name='" + inputName + "']").val();
            if(value){
                //判断当前是否是数字类型且是输入框控件时，判断值是否是数字类型
                if (searchItem.item.businessType === "inputText" && searchItem.item.stype === "Double") {
                    var reg = /^[0-9,.]*$/ //^[-\+]?\d+(\.\d+)?$/ ;
                    if (!reg.test(value)) {
                        Tip.fail({
                            text: searchItem.item.sdesc + modelingLang['mui.mobile.double.tips']
                        });
                        doubleValue = false;
                        break;
                    }
                }
                searchItem.item.value = value;
            }else{
                searchItem.item.value= "";
            }
            //存入缓存
          if(searchItem.item.stype.indexOf("com.landray.kmss.sys.organization") > -1){//地址本比较特殊需要存id和name
              this.searchValuesList[""+searchItem.item.columnName+".id"] = value;
              var name = $("input[name='" + searchItem.item.columnName + ".name']").val();
              this.searchValuesList[""+searchItem.item.columnName+".name"] = name;
          }else{//其他存入值就行
              this.searchValuesList[""+searchItem.item.columnName+""] = value;
          }
            outerSearchParams.push(searchItem.item);
          }
        }
        if (doubleValue){
            this.argu.paramsJSON.outerSearchs = JSON.stringify(outerSearchParams);
            topic.publish("/sys/xform/event/search", this, {
              argu: this.argu,
              dataSource: this.dataSource
            });
            dialog.hide();
        }

      },

      ok: function(dialog) {
        this.searchValues(dialog);
      },

      reset: function() {
        this.searchValuesList = {};
        $(".searchItem").find(".active").each(function(){
            $(this).removeClass('active');
        });
        //文本框
        $(".searchItem").find("[data-dojo-type='mui/form/Input']").children("input").val("");
        //单选框
        $(".searchItem").find("[data-dojo-type='mui/form/RadioGroup']").children("input").val("");
        //多选框
        $(".searchItem").find("[data-dojo-type='mui/form/CheckBoxGroup']").children("input").val("");
        //下拉框和多选下拉框
        $(".searchItem").find("[data-dojo-type='mui/form/Select']").children("input").val("");
        $(".searchItem").find("[data-dojo-type='mui/form/Select']").children(".muiSelInput").text("");
        //日期
        $(".searchItem").find("[data-dojo-type='mui/form/DateTime']").children("input").val("");
        $(".searchItem").find("[data-dojo-type='mui/form/DateTime']").children(".muiSelInput").text("");
        //地址本
        $(".searchItem").find("[data-dojo-type='mui/form/Address']").children("input").val("");
        $(".searchItem").find("[data-dojo-type='mui/form/Address']").find(".muiAddressOrg").remove();
        $(".searchItem").find("[data-dojo-type='mui/form/Address']").find(".muiCategoryAdd").css("display","inline-block");
      }
    }
  );
});
