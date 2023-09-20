/************************************
 * 标签机制移动端已实现未启用及部分注意事项如下：
 *
 * 1.使用过的标签及热点标签已实现，此版本不需要未启用
 * 2.标签格式效验也添加未启用，若要添加，实现一下效验规则即可。
 * 3.目前该机制移动端在KMS模块新增时使用，支持编辑，编辑页可直接引用，但
 * 由于KMS模块移动端只有新增页，编辑功能未经过验证，如有问题提单修复一下，感恩。
 * 4.该组件支持多层级分类，目前标签机制只有首级分类，若后续添加多级分类，可在后台后台service层getTagsData
 * 方法中添加子级分类信息获取逻辑即可，前端无需改动。
 * 5.目前此版本没有考虑同一页面多标签引用问题，如有此场景需要，需提单优化，每个组件已传入key值，
 * 可用key值区分。
 * 6.该组件默认多选，可通过isMul参数设置为单选
 * 7.该组件默认非必填，可通过required参数设置为必填
 *
 ************************************/
define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dijit/_WidgetBase",
    "mui/util",
    "mui/device/adapter",
    "mui/createUtils",
    "dojo/parser",
    "dojo/dom",
    "mui/i18n/i18n!sys-mobile",
    "mui/i18n/i18n!sys-tag",
    "dojo/_base/lang",
    "dojo/dom-style",
    "dojo/dom-class",
    "dijit/registry",
    "dojo/topic",
    "dojo/query",
    "sys/tag/mobile/import/js/select/tagUtil"
], function (declare, domConstruct, widgetBase, util, adapter, createUtils,
             parser, dom, Msg, TagMsg, lang, domStyle, domClass, registry, topic, query, tagUtil) {
    var template = createUtils.createTemplate;
    return declare('sys.tag.SysTagPda', [widgetBase], {

        modelName: "",

        modelId: "",

        queryCondition: "",

        fdKey: "",

        curIds: "",

        curNames: "",

        required: false,

        isMul: true,

        isClick: false,

        baseClass: "muiTagPdaContainer muiFormEleWrap",

        TAG_EVENT_ADD_VALUE : '/mui/tag/addValue',

        url: "",

        buildRendering: function () {
            this.inherited(arguments);
            // 渲染内容
            this.buildTagContent();
            // 渲染选择器
            this.buildSelectBtn();
            // 初始化编辑按钮
            if(this.inputHidden != "false") {
                this.buildTagEditButton();
            }
            // 绑定表单提交事件
            this.buildFormSubmitEvent();
        },

        buildFormSubmitEvent: function () {
            var events = Com_Parameter.event["submit"];
            events[events.length] = lang.hitch(this,'setQueryCondition');
        },

        setQueryCondition: function(){
            if(this.queryCondition) {
                var queryConditionKeys = this.queryCondition.split(";");
                var queryConditionValues = [];
                for(var i=0; i<queryConditionKeys.length; i++){
                    var key = queryConditionKeys[i];
                    var name = "[name='"+ key +"']";
                    var el = query(name)[0];
                    if(key == "docCategoryId" && (!el || !el.value)) {
                        el =  query("[name='cateId']")[0];
                    }
                    if(el && el.value) {
                        queryConditionValues.push(el.value);
                    }
                }
                if(queryConditionValues.length > 0) {
                    query("[name='sysTagMainForm.fdQueryCondition']")[0]
                        .value = queryConditionValues.join(";");
                }
            }
            return true;
        },

        buildTagContent: function (){
            var boxClass = "muiCateFiledShow muiFormItem muiTagEditContent";
            var boxStyle = "";
            if(this.contentAlign == "left") {
                boxClass += " muiCateFiledShowLeft";
                boxStyle = "text-align: left;"
            }
            this.tagBoxNode = domConstruct.create('div', {
                style: boxStyle,
                className: boxClass,
            }, this.domNode);

            this.tagContentNode = domConstruct.create('div', {
                className: 'muiTagInputContent',
            }, this.tagBoxNode);

            this.tagInputBox = domConstruct.create('div', {
                id: this.getNodeIdStr("muiTagInputBox"),
                className: 'muiTagInputBox',
            }, this.tagContentNode);

            this.tagBtnBox = domConstruct.create('div', {
                className: 'muiTagBtnBox',
            }, this.tagContentNode);
        },

        buildTagEditButton: function (){
            this.tagEditBtn = domConstruct.create('span', {
                className: 'fontmuis muis-org-signature muiTagBtn muiTagEditBtn',
            }, this.tagBtnBox);
            this.connect(this.tagEditBtn, "click",
                lang.hitch(this, function(){
                    if(this.isClick) return
                    this.isClick = true;
                    this.clearClick();
                    this.getTagInputElement();
                    domStyle.set(this.selectBtn, "display", "none");
                    domStyle.set(this.tagContentNode, "width", "100%");
                    domClass.add(this.tagContentNode, "muiTagInputEditing");
                    this.buildTagSubmitButton();
                    this.buildTagCancelButton();
                    this.tagEditBtn.remove()
            }))
        },

        clearClick: function(){
            this.defer(function () {
                this.isClick = false;
            }, 350);
        },

        buildTagSubmitButton: function (){
            this.tagSubmitBtn = domConstruct.create('span', {
                className: 'muiTagBtn muiTagSubmitBtn',
                innerHTML: Msg["mui.button.ok"]
            }, this.tagBtnBox);
            this.connect(this.tagSubmitBtn, "click",
                lang.hitch(this, function(){
                if(this.isClick) return
                this.isClick = true;
                this.clearClick();
                var value = this.tagInputNode.value;
                if(!value && this.tagInputNode.inputNode) {
                    value = this.tagInputNode.inputNode.value;
                }
                if(value) {
                    var selectDialog = registry.byId(this.getNodeIdStr("tag_SelectDialog"));
                    value = value.split(/;|；/);
                    var split = selectDialog.splitStr
                    var nameValue = [];
                    var idValue = [];
                    for(var i=0; i<value.length; i++) {
                        if(value[i]) {
                            nameValue.push(value[i]);
                            idValue.push(tagUtil.GenerateId());
                        }
                    }
                    topic.publish(this.TAG_EVENT_ADD_VALUE, this, {
                        curNames: nameValue.join(split),
                        curIds: idValue.join(split)
                    });
                }
                this.resetButton();
            }));
        },

        buildTagCancelButton: function () {
            this.tagCancelBtn = domConstruct.create('span', {
                className: 'fontmuis muis-delete-document muiTagCancelBtn',
            }, this.tagBtnBox);
            this.connect(this.tagCancelBtn, "click",
                lang.hitch(this, function(){
                if(this.isClick) return
                this.isClick = true;
                this.clearClick();
                this.resetButton();
            }))
        },
        // 重置按钮
        resetButton: function() {
            domStyle.set(this.selectBtn, "display", "block");
            domStyle.set(this.tagContentNode, "width", "calc(100% - 3rem)");
            domClass.remove(this.tagContentNode, "muiTagInputEditing");
            this.buildTagEditButton();
            this.tagSubmitBtn.remove();
            this.tagCancelBtn.remove();
            this.tagInputDivBox.remove();
        },
        // 设置标签输入框
        getTagInputElement: function() {
            var id = this.getNodeIdStr("tag_TagAddInput") + new Date().getTime();
            var boxId = id + "_Box";

            this.tagInputDivBox = domConstruct.create('div', {
                id: boxId,
                className: 'muiTagAddInputBox',
            }, this.tagInputBox, "first");

            var input = template('div', {
                id: id,
                dojoType: "mui/form/Input",
                className: 'muiInput muiTagAddInput',
                dojoProps: {
                    name:"sysTagMainForm.tagInpNames",
                    showStatus:"edit",
                    subject: TagMsg['mui.sysTagMain.tags'],
                    validate:"maxLength(200)",// checkTags
                    placeholder: TagMsg['mui.sysTagMain.tags.placeholder']
                }
            });
            input = domConstruct.toDom(input);
            domConstruct.place(input, this.tagInputDivBox);
            parser.parse({
                rootNode: dom.byId(boxId)
            })
            this.tagInputNode = registry.byId(id);
            // 初始化效验器 先不搞
            // this.addInputValidation();
        },
        // tag格式效验，先不搞
        addInputValidation: function() {
            var error = TagMsg['mui.sysTagMain.tags.prompt.error'];
            this.tagInputNode.validation
                .addValidator("checkTags" , error , lang.hitch(this,'doTagValidate'));
        },

        doTagValidate: function() {
            console.log("假装效验了");
            return true
        },

        buildSelectBtn: function () {
            var required = this.required == "true"? true : false;
            var isMul = this.isMul == "true"? true : false;
            var showTagInfo = this.showTagInfo == "true"? true : false;
            var selectBtn = template('div', {
                id: this.getNodeIdStr("tag_SelectDialog"),
                dojoType: 'sys/tag/mobile/import/js/select/SelectDialog',
                className: 'muiCategoryAdd fontmuis muis-new muiNewAdd tag_SelectDialog',
                dojoProps: {
                    title: TagMsg['mui.sysTagMain.tags.select'],
                    idField: "sysTagMainForm.fdTagIds",
                    nameField: "sysTagMainForm.fdTagNames",
                    isMul: isMul,
                    optClass: "mui-sys-tag-forward",
                    align: "left",
                    primaryKey: "curNames",
                    showTagInfo: showTagInfo,
                    curIds: this.curIds,
                    curNames: this.curNames,
                    modelName: this.modelName,
                    modelId: this.modelId,
                    queryCondition: this.queryCondition,
                    required: required,
                    subject: TagMsg['mui.sysTagMain.tags'],
                    showStatus: "edit",
                    listDataUrl: "/sys/tag/sys_tag_tags/sysTagTags.do?method=getTagsDialogData&kind=list&parentId=!{parentId}",
                    searchDataUrl: "/sys/tag/sys_tag_tags/sysTagTags.do?method=getTagsDialogData&kind=search&keyWord=!{keyword}",
                }
            });
            this.selectBtn = domConstruct.toDom(selectBtn);
            domConstruct.place(this.selectBtn, this.tagBoxNode, "last");
            parser.parse({
                rootNode: dom.byId("muiTagBox")
            })
        },

        getNodeIdStr: function (id) {
            return id + "_" + this.fdKey;
        },

        startup: function () {
            this.inherited(arguments);
        },

    })
});