(function(window, undefined){
    /**
     * 绘制区
     */
    var Designer_Builder=function(designer) {
        this.owner = designer || null;//设计器对象

        this.domElement = this.owner.builderDomElement;//绘制区DOM元素
        this.$domElement= this.owner.$builderDom;//绘制区DOM元素（JQ）

        //this.controls = [];//顶层控件
        this.$selectDom=null;//选中控件的DOM
        this.$selectDomArr = [];//多选
        this.selectControl=null;//选中控件对象


        this.dashBox=$('<div style="border:1px dashed #000;position:absolute;"/>');//虚线框

        //内部属性
        this._actionType = 'default';  //鼠标操作类型
        this._chooseDomElement = null; //待选中控件对象(鼠标经过元素时)
        this._dragDomElement = null;   //拖拽对象

        //绘制区相对屏幕的偏移
        //this.offset = sysPrintUtil.absPosition(this.domElement);


        //exports
        this.getHTML=getHTML;
        this.setHTML=setHTML;
        //
        this.parse = parse;
        this.parseCtrl = parseCtrl;

        this.setSelectDom = setSelectDom;
        this.addSelDom = addSelDom;
        this.delSelDom = delSelDom;
        this.isSelectedDom = isSelectedDom;

        this.clearSeclectedCtrl = clearSeclectedCtrl;
        this.onDrawMouseMove = onDrawMouseMove;//mousemove事件
        this.onDrawMouseDown = onDrawMouseDown;//mousedown事件
        this.onDrawMouseUp = onDrawMouseUp;//mouseup事件
        this.createControl = createControl;//创建control控件
        this.attrPanelShow = attrPanelShow;//属性面板

        this._getDatas = _getDatas;
        this._getObjectByName = _getObjectByName;

    };

    //html解析成相应控件对象集  state是否绑定选中节点 (true代表无需绑定)
    //extFun参数用来执行一些扩展的内容额函数，不是必须的
    function parse(parentControl, parentNode, state, extFun) {
        try {
            var _parentControl = parentControl || null, _parent = parentNode || null, childCount, node, control;
            if (_parent == null) return;
            //遍历子节点
            childCount = _parent.childNodes.length;
            for (var i = 0; i < childCount; i++) {
                node = _parent.childNodes[i];
                if (node.nodeType != 3) {
                    if (sysPrintUtil.isDesignElement(node)) {
                        var _fdType = node.getAttribute('fd_type');
//						debugger;
                        if(!node.getAttribute('fd_type')) continue;
                        if(_fdType=='table'){
                            control = new sysPrintDesignerTableControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='detailsTable' || _fdType=='seniorDetailsTable'){
                            control = new sysPrintDetailsTableControl(this,_fdType,{renderLazy:true, fdType: _fdType});
                        }else if(_fdType=='mutiTab'){
                            control = new sysPrintDesignerMutiTabControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='label'){
                            control = new sysPrintLabelControl(this,_fdType,{renderLazy:true});
                        }else if(isFieldControl(_fdType)){
                            control = new sysPrintDesignerFieldControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='page'){
                            control = new sysPrintPageControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='process'){
                            control = new sysPrintProcControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='auditShow'){
                            control = new sysPrintAuditShowControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='linkLabel'){
                            control = new sysPrintLinkLabelControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='right'){
                            control = new sysPrintRightControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='jsp'){
                            control = new sysPrintJspControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='brcontrol'){
                            control = new sysPrintBrControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='uploadimg'){
                            control = new sysPrintUploadimgControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='divcontrol'){
                            control = new sysPrintDivControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='bookticket'){
                            control = new sysPrintBookticketControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='bookinfo'){
                            control = new sysPrintBookinfoControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='docimg'){
                            control = new sysPrintDocImgControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='ocr'){
                            control = new sysPrintOcrXFormControl(this,_fdType,{renderLazy:true});
                        }else if(_fdType=='qrCode'){
                            control = new sysPrintQRCodeControl(this,_fdType,{renderLazy:true});
                        } else if (_fdType=='voteNode') {
                            control = new sysPrintVoteNodeControl(this,_fdType,{renderLazy:true});
                        } else if (_fdType=='massData') {
                            control = new sysPrintMassDataControl(this,_fdType,{renderLazy:true});
                        } else if (_fdType == 'dateFormat') {
                            //#104389-表单片段集支持导入打印模板，能够进行打印
                            //新增”日期格式化“控件处理
                            control = new sysPrintDateFormatControl(this, _fdType, {renderLazy: true});
                        } else if (_fdType == 'composite') {
                            control = new sysPrintCompositeControl(this,_fdType,{renderLazy:true});
                        } else if (_fdType == 'uploadTemplateAttachment') {
                            control = new sysPrintUploadTemplateAttachmentControl(this,_fdType,{renderLazy:true});
                        }
                        //绑定DomElment
                        control.$domElement=$(node);
                        if(!state){
                            this.$selectDomArr=[$(_parent)];
                        }
                        control.draw();

                        //初始化控件相关信息
                        if (control.onInitialize) control.onInitialize();
                        //绑定国际化组件
                        //control.lang && control.lang();
                        //遍历后续子节点
                        this.parse(control, node, null, extFun);

                        if(extFun){
                            extFun(control);
                        }
                    } else {
                        this.parse(_parentControl, node, null, extFun);
                    }
                }
            }
        } catch (e) {
            if(window.console){
                window.console.log(e);
            }
        }
    }

    function isFieldControl(fdType){
        var FIELD_CONTROLS = ['input','inputRadio','checkbox','select','address','relationSelect','textarea',
            'map','relationRadio','relationCheckbox','relationChoose','relevance','fieldlaylout',
            'new_address','formula_calculation','attachment','rtf','relationRule','fSelect','prompt','detailSummary'];
        for(var i = 0 ; i < FIELD_CONTROLS.length;i++){
            if(FIELD_CONTROLS[i]==fdType){
                return true;
            }
        }
        return false;
    }
    //解释自定义表单模板
    function parseByXFormHTML(html){

    }
    //自定义表单控件转换为打印表单控件
    function parseCtrl(parentNode){
        try {
            var _parent = parentNode || null, childCount, node, control;
            if (!_parent) return;
            //遍历子节点
            childCount = _parent.childNodes.length;
            for (var k = 0; k < childCount; k++) {
                node = _parent.childNodes[k];
                if (node.nodeType != 3) {
                    if (sysPrintUtil.isXFormDesignElement(node)) {
                        var _fdType = node.getAttribute('fd_type');
                        if (_fdType == null) continue;
                        if(_fdType=='standardTable'){
                            node.setAttribute("printcontrol","true");
                            node.setAttribute("fd_type","table");
                            //node.setAttribute("id",sysPrintUtil.generateID());
                            node.removeAttribute("fd_values");
                            node.removeAttribute("formdesign");
                        }else if(_fdType=='detailsTable' || _fdType=='seniorDetailsTable'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            node.setAttribute("printcontrol","true");
                            node.setAttribute("fd_type",_fdType);
                            //node.setAttribute("id",values.id);
                            node.removeAttribute("fd_values");
                            node.removeAttribute("formdesign");
                            //删除多余行/列
                            var rows = $(node)[0].rows;
                            var delRows = [];

                            var delCols = function($row){
                                var selectCols= $row.find("td[coltype='selectCol']");
                                var copyCols= $row.find("td[coltype='copyCol']");
                                var emptyCell= $row.find("td[coltype='emptyCell']");
                                var blankTitleCols = $row.find("td[coltype='blankTitleCol']");
                                if(blankTitleCols && blankTitleCols.length==0){
                                    blankTitleCols = $row.find("td[coltype='addTitle']");
                                }
                                if(selectCols && selectCols.length>0){
                                    selectCols.css('display','none');
                                }
                                if(copyCols && copyCols.length>0){
                                    copyCols.css('display','none');
                                    copyCols.find('img').removeAttr('src');
                                }
                                if(blankTitleCols && blankTitleCols.length>0){
                                    blankTitleCols.css('display','none');
                                }
                                if(emptyCell && emptyCell.length>0){
                                    emptyCell.css('display','none');
                                }
                            }

                            for(var i = rows.length-1 ;i >=0;i--){
                                var $row = $(rows[i]);
                                if($row.attr('type')=='optRow'){
                                    $(node)[0].deleteRow(i);
                                }
                                delCols($row);
                            }
                        }else if(_fdType=='mutiTab'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            node.setAttribute("printcontrol","true");
                            node.setAttribute("fd_type","mutiTab");
                            node.removeAttribute("fd_values");
                            node.removeAttribute("formdesign");
                            $(node).addClass('tb_normal');
                            node.deleteRow(0);
                            $(node.rows).each(function(idx,trObj){
                                trObj.style.display='';
                                var trObj = $(trObj);
                                var tmpTitleTr = $("<tr class='tr_normal_title'><td class='td_label' align='left'>" + trObj.attr("LKS_LabelName") + "</td></tr>");
                                trObj.before(tmpTitleTr);
                            });

                        }else if(_fdType=='textLabel'){
                            node.style.width='auto';
                            $(node).attr("printcontrol","true").attr("fd_type","label").attr("id",sysPrintUtil.generateID())
                                .removeAttr("fd_values").removeAttr("formdesign").addClass("xform_label");
                        }else if(_fdType=='linkLabel'){
                            node.style.width='auto';
                            $(node).attr("printcontrol","true").attr("fd_type","linkLabel").attr("id",sysPrintUtil.generateID())
                                .removeAttr("fd_values").removeAttr("formdesign").addClass('linkLabel');
                            $(node).children('a').css('text-decoration','none').css('cursor','default');
                        }else if(_fdType=='inputText' || _fdType=='textarea' || _fdType=='select' ||
                            _fdType=='rtf' || _fdType=='datetime' || _fdType=='calculation'
                            || _fdType=='chinaValue' || _fdType=='auditNote' || _fdType=='fSelect' || _fdType=='placeholder'|| _fdType=='detailSummary'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var fdType = "input";
                            if(data.type=='Attachment'){
                                fdType = "attachment";
                            }
                            if(data.type=='RTF'){
                                fdType = "rtf";
                            }
                            if(_fdType=='select' || _fdType=='textarea' || _fdType=='fSelect'){
                                fdType = _fdType;
                            }
                            data.fdType=fdType;
                            if(_fdType=='placeholder' && data.name){
                                data.name = data.name+"_text";
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            //属性配置
                            if(_fdType=='textarea'){
                                $newDom.attr('_width',values.width).attr('_height',values.height).addClass("xform_textArea");
                            }
                            if(_fdType=='select' || _fdType=='fSelect'){
                                $newDom.attr('items',values.items).css("fontFamily",values.font).css("fontSize",values.size).addClass("xform_Select");
                            }
                            if(_fdType=='inputText' || _fdType=='calculation'|| _fdType=='detailSummary'){
                                $newDom.attr('thousandShow',values.thousandShow)
                                    .attr('data_type',values.dataType)
                                    .attr('displayFormat',values.displayFormat)
                                    .attr('scale',values.scale)
                                    .attr('canShow',values.canShow)
                                    .css("fontFamily",values.font)
                                    .css("fontSize",values.size)
                                    .addClass(_fdType=='inputText' ? "xform_inputText" : "xform_Calculation");
                            }
                            if(_fdType=='inputText'  && values.dataType=='Double'|| _fdType=='detailSummary' ){
                                //#162327 单行输入框数字类型，显示格式为补零，新版打印未没有补零 start ouyu
                                var _displayFormat="";
                                if (values.displayFormat == 'zeroFill') {
                                    _displayFormat = "{'displayFormat':'zeroFill','zeroFillFormat':'"+values.zeroFill+"'}";
                                } else if (values.displayFormat == 'percent') {
                                    _displayFormat = "{'displayFormat':'percent'}";
                                } else if (values.displayFormat == 'false') {
                                    _displayFormat = values.displayFormat;
                                }else{
                                    _displayFormat = values.displayFormat;;
                                }
                                //#162327 单行输入框数字类型，显示格式为补零，新版打印未没有补零 end ouyu
                                $newDom.attr('displayFormat',_displayFormat);
                            }
                            if(_fdType=='datetime'){
                                $newDom.addClass("xform_datetime");
                            }
                            if(_fdType=='chinaValue'){
                                $newDom.addClass("xform_chinaValue");
                            }
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='relationSelect'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="relationSelect";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var outputParams = values.outputParams;
                            var inputParams = values.inputParams;
                            var funKey = values.funKey;
                            var source = values.source;
                            var width = values.width;
                            $newDom.attr('outputParams',outputParams).attr('inputParams',inputParams).attr('funKey',funKey).attr('source',source)
                                .attr('width',width).addClass('xform_relation_select');
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='inputRadio'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="inputRadio";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var items = values.items;
                            var alignment = values.alignment;
                            $newDom.attr('items',items).attr('alignment',alignment).addClass("xform_inputRadio");
                            $newDom.attr('style',$oldDom.attr("style"));
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='inputCheckbox'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="checkbox";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var items = values.items;
                            var alignment = values.alignment;
                            $newDom.attr('items',items).attr('alignment',alignment).addClass("xform_inputCheckBox");
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='attachment'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                data = {};
                                var id = values.id;
                                var label = values.label;
                                //判断是否在明细表
                                var result = sysPrintUtil.getParentDesignElementInfo(node.parentNode);
                                if(result.isDetailTable){
                                    id = result.id+"." + id;
                                    label = result.label + "."+values.label;
                                }
                                data = {fdType:_fdType,isXFormDict:'true',name:id,type:'Attachment[]',label:label};
                            }
                            data.fdType="attachment";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $(node).before($newDom);
                            $newDom.attr("slidedown", $(node).attr("slidedown"));
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='auditShow'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                data = {};
                                data.label=values.label;
                                data.type="string";
                                data.name=values.id;
                            }
                            data.fdType="auditShow";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var exhibitionstyleclass = $oldDom.attr('exhibitionstyleclass');
                            var width = $oldDom.attr('width');
                            var filternote = $oldDom.attr('filternote');
                            var notefilter = $oldDom.attr('notefilter');
                            var attr_name = $oldDom.attr('attr_name');
                            var value = $oldDom.attr('value');
                            var mould = $oldDom.attr('mould');
                            var sortnote = $oldDom.attr('sortnote');
                            var groupnote = $oldDom.attr('groupnote');
                            $newDom.attr('filternote',filternote +"," + notefilter).attr('attr_name',attr_name).attr('exhibitionstyleclass',exhibitionstyleclass)
                                .attr('value',value).attr('mould',mould).attr('sortnote',sortnote).attr('width',width).attr('groupnote',groupnote);
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='circulationOpinionShow'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                data = {};
                                data.label=values.label;
                                data.type="string";
                                data.name=values.id;
                            }
                            data.fdType="circulationOpinionShow";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var exhibitionstyleclass = $oldDom.attr('exhibitionstyleclass');
                            var width = $oldDom.attr('width');
                            var filternote = $oldDom.attr('filternote');
                            var attr_name = $oldDom.attr('attr_name');
                            var value = $oldDom.attr('value');
                            var mould = $oldDom.attr('mould');
                            var aclass = $oldDom.attr('class');
                            var sortnote = $oldDom.attr('sortnote');
                            $newDom.attr('filternote',filternote).attr('attr_name',attr_name).attr('exhibitionstyleclass',exhibitionstyleclass)
                                .attr('value',value).attr('mould',mould).attr('sortnote',sortnote).attr('width',width).attr('class',aclass);
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='address'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
//							debugger;
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="address";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var multiSelect = values.multiSelect;
                            var orgType = values._orgType;
                            $newDom.attr('multiSelect',multiSelect).attr('orgType',orgType).addClass("xform_address");
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='new_address'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="new_address";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            var $input = $(node).find('input');
                            //配置属性
                            $newDom.attr('multiSelect',values.multiSelect).attr('orgType',values._orgType)
                                .attr('scope',$input.attr('scope')).addClass("xform_new_address");
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='right'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            node.setAttribute("printcontrol","true");
                            node.setAttribute("id",values.id);
                            node.removeAttribute("fd_values");
                            node.removeAttribute("formdesign");
                            $(node).find('button[type="button"]').removeAttr('onclick onmousedown ondblclick');
                            $(node).find('.rightIconBar').css('display','');
                            $(node).find('.righBar').css('display','none');
                        }else if(_fdType=='divcontrol'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            $(node).attr("printcontrol","true")
                                .attr("id",values.id)
                                .removeAttr('fd_values')
                                .removeAttr('formdesign');
                        }else if(_fdType=='jsp'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            node.setAttribute("printcontrol","true");
                            node.setAttribute("id",values.id);
                            node.removeAttribute("fd_values");
                            node.removeAttribute("formdesign");
                            $(node).css('display','none');
                        }else if(_fdType=='brcontrol'){
                            node.setAttribute("printcontrol","true");
                            var fdValues = node.getAttribute('fd_values'),values;
                            if(fdValues){
                                values = eval('(' + fdValues.replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            }
                            if(values && values.id)
                                node.setAttribute("id",values.id);
                            node.removeAttribute("fd_values");
                            node.removeAttribute("formdesign");
                            $(node).find('span').addClass('printBr');
                        }else if(_fdType=='uploadimg'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            $(node).attr("printcontrol","true")
                                .attr("_originalwidth",values.origWidth)
                                .attr("_originalheight",values.origHeight)
                                .attr("imgname",values.url)
                                .attr("_width",values.width)
                                .attr("_height",values.height)
                                .css('display','inline-block')
                                .removeAttr('fd_values')
                                .removeAttr('formdesign');
                            if(!values.src) {
                                var imgsrc = Com_Parameter.ContextPath +"sys/print/designer/style/img/btn_img_bg.png";
                                var html = '<img src="' + imgsrc + '" />';
                                $(node).html(html);
                                $(node).css('width','28px');
                            }
                        } else if (_fdType=='uploadTemplateAttachment') {
                            node.setAttribute("printcontrol","true");
                            $(node).removeAttr('fd_values')
                                .removeAttr('formdesign');
                        }else if(_fdType=='qrCode'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            $(node).attr("printcontrol","true")
                                .attr("_width",values.width).attr("_height",values.height)
                                .attr('mold',values.mold).attr('valType',values.valType).attr('content',values.content)
                                .removeAttr('fd_values').removeAttr('formdesign');

                        }else if(_fdType=='voteNode'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            $(node).attr("printcontrol","true")
                                .attr("_width",values.width).attr("_height",values.height)
                                .attr('mold',values.mold).attr('moldDetail',values.moldDetail).attr('mold_detail_name',values.content)
                                .attr('detail_attr_name',values.detail_attr_name).attr('detail_attr_value',values.detail_attr_value)
                                .removeAttr('fd_values').removeAttr('formdesign');

                        }else if(_fdType=='bookticket'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            $(node).attr("printcontrol","true")
                                .attr("bookType",values.bookType)
                                .attr("passengerList",values.passengerList)
                                .attr("retinueList",values.retinueList)
                                .attr("beginDate",values.beginDate)
                                .attr("endDate",values.endDate)
                                .attr("fromCity",values.fromCity)
                                .attr("toCity",values.toCity)
                                .removeAttr('fd_values')
                                .removeAttr('formdesign');
                            $(node).find('img').attr('src','').addClass('printbookticket_plane');
                        }else if(_fdType=='bookinfo'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = {};
                            data.label=DesignerPrint_Lang.bookticketInfo;
                            data.type="string";
                            data.name=values.id;
                            data.fdType="bookinfo";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='docimg'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                var id = values.id;
                                var label = values.label;
                                //判断是否在明细表
                                var result = sysPrintUtil.getParentDesignElementInfo(node.parentNode);
                                if(result.isDetailTable){
                                    id = result.id+"." + id;
                                    label = result.label + "."+values.label;
                                }
                                data = {fdType:_fdType,isXFormDict:'true',name:id,type:'',label:label};
                            }
                            data.fdType="docimg";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            //属性
                            $newDom.attr('_width',values.width).attr('_height',values.height).attr('fdMulti',values.fdMulti).attr("hidePicName", values.hidePicName);
                            node=null;
                        }else if(_fdType=='ocr'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                var id = values.id;
                                var label = values.label;
                                //判断是否在明细表
                                var result = sysPrintUtil.getParentDesignElementInfo(node.parentNode);
                                if(result.isDetailTable){
                                    id = result.id+"." + id;
                                    label = result.label + "."+values.label;
                                }
                                data = {fdType:_fdType,isXFormDict:'true',name:id,type:'',label:label};
                            }
                            var params ={};
                            params.type = values.type
                            params.remainImg = values.remainImg || "false";
                            params.outputParams = values.outputParams || {};
                            params.ocrChildrenType = values.ocrChildrenType || 1;
                            data.fdType="ocr";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            //属性
                            $newDom.attr('_width',values.width).attr('_height',values.height).attr("width",values.width).attr("height",values.height).attr('fdMulti',values.fdMulti).attr("params", JSON.stringify(params));
                            node=null;
                        }else if(_fdType=='map'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="map";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            //属性
                            $newDom.attr('_width',values.width).attr('_readonly',values.readOnly);
                            node=null;
                        }else if(_fdType=='relationRadio'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="relationRadio";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var outputParams = values.outputParams;
                            var inputParams = values.inputParams;
                            var funKey = values.funKey;
                            var source = values.source;
                            var alignment = values.alignment;
                            $newDom.attr('outputParams',outputParams).attr('inputParams',inputParams).attr('funKey',funKey).attr('source',source)
                                .attr('alignment',alignment).attr('defValue',values.defValue).addClass('xform_relation_radio');
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='relationCheckbox'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="relationCheckbox";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var outputParams = values.outputParams;
                            var inputParams = values.inputParams;
                            var funKey = values.funKey;
                            var source = values.source;
                            var alignment = values.alignment;
                            $newDom.attr('outputParams',outputParams).attr('inputParams',inputParams).attr('funKey',funKey).attr('source',source)
                                .attr('alignment',alignment).attr('defValue',values.defValue).addClass('xform_relation_checkbox');
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='relevance'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = {fdType:_fdType,name:values.id,type:'',label:values.label,isXFormDict:true};
                            var _data = this._getObjectByName(this._getDatas(),values.id);
                            if(_data){
                                data.name = _data.name;
                                data.label = _data.label;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            //属性
                            node=null;
                        }else if(_fdType=='relationChoose'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType="relationChoose";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            $newDom.attr('outputParams',values.outputParams).attr('inputParams',values.inputParams).attr('funKey',values.funKey).attr('source',values.source)
                                .attr('listrule',values.listrule).attr('fillType',values.fillType).attr('fillTypeOne',values.fillTypeOne)
                                .attr('_width',values.width).addClass('xform_relation_choose');
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        } else if (_fdType == "massData") {
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = {};
                            data.label=values.label;
                            data.type="";
                            data.name=values.id;
                            data.fdType="massData";
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            $newDom.attr('outputParams',values.outputParams).attr('inputParams',values.inputParams).attr('funKey',values.funKey)
                                .attr('excelcolumns',values.excelColumns).attr('source',values.source)
                                .addClass('xform_massData');
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        } else if(_fdType=='relationRule'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            node.setAttribute("printcontrol","true");
                            var detailTablee =  $(node).closest("table")[0];
                            var fd_typee = detailTablee.getAttribute("fd_type");
                            if( fd_typee && fd_typee == "detailsTable"){
                                var tableId = detailTablee.id;
                                var inputt = $(node).children("input")[0];
                                if(inputt){
                                    inputt.setAttribute("tableId",tableId);
                                }
                            }
                            node.setAttribute("id",values.id);
                            node.removeAttribute("fd_values");
                            node.removeAttribute("formdesign");
                            $(node).css('display','none');
                        }else if(_fdType=='fieldlaylout'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            var data = {fdType:_fdType,isXFormDict:'true',name:values.id,type:'',label:values.fieldNames};

                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            var inputNode = $(node).find('input');
                            //配置属性
                            $newDom.attr('modelname',inputNode.attr('modelname')).attr('fieldids',inputNode.attr('fieldids'))
                                .attr('fieldnames',inputNode.attr('fieldnames')).attr('jsprunprofix',inputNode.attr('jsprunprofix'))
                                .attr('fieldparams',inputNode.attr('fieldparams')).attr('formids',inputNode.attr('formids'));
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='formula_calculation'){
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            data.fdType=_fdType;
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            $newDom.attr('formula',$oldDom.attr('formula')).attr('returnType',$oldDom.attr('returntype')).attr('expression_mode',$oldDom.attr('expression_mode'))
                                .attr('loadType',$oldDom.attr('loadtype')).attr('controlsId',$oldDom.attr('controlIds')).addClass('xform_formula_load');
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node=null;
                        }else if(_fdType=='relationEvent' || _fdType=='hidden'){
                            //背景图片在新版打印的时候会被识别为模块的路径加上下面的路径导致报错，需要去除掉
                            if(_fdType=='relationEvent')
                                node.innerHTML = node.innerHTML.replace("background: url(&quot;style/img/event.png&quot;)","");
                            $(node).hide();
                            node=null;
                            continue;
                        }else if(_fdType=='prompt'){
                            $(node).hide();
                            node=null;
                        }else if(_fdType=='company'){	//费控——记账公司选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","company");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='costCenter'){	//费控——成本中心选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","costCenter");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='project'){	//费控——项目选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","project");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='postLevel'){	//费控——职级选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","postLevel");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='expenseItem'){	//费控——费用类型选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","expenseItem");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='vehicleBerth'){	//费控——交通工具舱位控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","vehicleBerth");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='budgetRule'){	//费控——预算规则控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","budgetRule");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='standardRule'){	//费控——标准规则控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","standardRule");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='currency'){	//费控——币种控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","currency");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='areaCategory'){	//费控——地域选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","areaCategory");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='accountMoney'){	//费控——本位币金额控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","accountMoney");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='dayCount'){	//费控——天数控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","dayCount");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='wbsNumber'){	//费控——WBS号选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","wbsNumber");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        }else if(_fdType=='internalOrder'){	//费控——内部订单选择控件
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig,'\\r\\n').replace(/&#;/ig,'\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(),values.id);
                            if(!data){
                                $(node).remove();
                                node=null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            $newDom.attr("fd_type","internalOrder");
                            $(node).before($newDom);
                            $(node).remove();
                            node=null;
                        } else if (_fdType == 'fragmentSet') {//片段集类型
                            //#104389-表单片段集支持导入打印模板，能够进行打印
                            //替换掉图片的url，以防找不到图片报错404
                            node.innerHTML = node.innerHTML.replace("background: url(&quot;style/img/fragmentSet.png&quot;)", "");
                            this.parseCtrl(node);
                        } else if (_fdType == 'dateFormat') {
                            //新增”日期格式化“控件处理
                            var values = eval('(' + node.getAttribute('fd_values').replace(/\r\n/ig, '\\r\\n').replace(/&#;/ig, '\\r\\n') + ')');
                            //获取数据源
                            var data = this._getObjectByName(this._getDatas(), values.id);
                            if (!data) {
                                $(node).remove();
                                node = null;
                                continue;
                            }
                            var $newDom = sysPrintButtons.genDataCtrlDom(data);
                            var $oldDom = $(node);
                            //配置属性
                            var relatedid = values.relatedid;
                            var dataType = values.dataType;
                            var dateFormatType = values.dateFormatType;
                            var canShow = values.canShow;
                            var encrypt = values.encrypt;
                            $newDom.attr('relatedid', relatedid).attr('dataType', dataType).attr('dateFormatType', dateFormatType).attr('canShow', canShow)
                                .attr('encrypt', encrypt).attr("fd_type", "dateFormat");
                            $oldDom.before($newDom);
                            $oldDom.remove();
                            node = null;
                        } else if (_fdType == 'composite') {
                            $(node).attr("printcontrol", "true");
                        } else {
                            $(node).remove();
                            node=null;
                        }
                        this.parseCtrl(node);
                    } else {
                        this.parseCtrl(node);
                    }
                }
            }
        } catch (e) {
            // do nothing
        }
    }

    function _getDatas(){
        //获取数据源
        var fdKey = this.owner.fdKey;
        var modelName = this.owner.modelName;
        var _xformCloneTemplateId = $('#_xformCloneTemplateId').val();
        var baseObjs = sysPrintCommon.getDocDict(fdKey,modelName,_xformCloneTemplateId);
        return baseObjs;
    }
    function _getObjectByName(arrays,name){
        for(var i = 0 ;i < arrays.length ; i++){
            var obj = arrays[i];
            if(obj.name==name){
                return obj;
            }else if(obj.isXFormDict && obj.name.indexOf('.'+name) != -1){
                //自定义表单明细表
                return obj;
            }
        }
        return null;
    }

    //获取绘制区HTML
    function getHTML(){
        return this.$domElement.html();
    }

    function setHTML(html){
        this.$domElement.html(html);
        //控件解释
        this.parse(null, this.domElement);

    }
    //设置选中控件dom(单选)
    function setSelectDom($dom){
        this.clearSeclectedCtrl();
        this.$selectDom = $dom;
        this.$selectDomArr=[$dom];
    }
    //添加选中对象(多选)
    function addSelDom($dom){
        this.$selectDom = $dom;
        this.$selectDomArr.push($dom);
    }
    //是否选中dom
    function isSelectedDom(){
        if(this.$selectDomArr && this.$selectDomArr.length>0)
            return true;
        return false;
    }
    function delSelDom($dom){
        var isExist = false;
        var i = 0;
        $.each(this.$selectDomArr, function(index, obj) {
            if(obj[0]===$dom[0]){
                isExist = true;
                i = index;
            }
        });
        if(isExist){
            this.$selectDomArr.splice(i,1);
        }
    }

    //清空选中dom
    function clearSeclectedCtrl(){
        this.$domElement.find('.table_select,.border_selected').removeClass("table_select border_selected");
        this.$selectDom=null;
        this.$selectDomArr = [];
        this.selectControl=null;
    }
    function onDrawMouseMove(){
        //工具栏操作
        if(this.owner.toolBarAction){
            //设置当前鼠标样式
            var path = sysPrintUtil.getSysPrintContextPath() + this.owner.toolBar.getCursorImgPath();
            this.owner.builderDomElement.style.cursor="URL('" + path +"'),auto";
            //清除单元格鼠标样式
            this.$domElement.find('.sysprint_cursor_n').removeClass('sysprint_cursor_n');
            this.$domElement.find('.sysprint_cursor_e').removeClass('sysprint_cursor_e');
            this.$domElement.find('.sysprint_cursor_d').removeClass('sysprint_cursor_d');

        }
    }
    function onDrawMouseDown(){
        //清空选中信息
        this.clearSeclectedCtrl();
        //添加控件
        sysPrintButtons.addToolBarControl(this.owner);
    }
    //目前该事件来源自table、draw面板
    function onDrawMouseUp(e){
        var button = sysPrintUtil.eventButton(event || e);
        //添加控件
        sysPrintButtons.addToolBarControl(this.owner);

        if(button==1){
            if (this.owner.rightMenu)
                this.owner.rightMenu.hide();
        }else if(button==2 && this._actionType != 'createControl'){
            if (this.owner.rightMenu)
                this.owner.rightMenu.show(event);
        }
        this._actionType = 'default';
    }

    function createControl(tAction,owner){
        sysPrintButtons.createControl(tAction,owner);
    }
    function attrPanelShow(){
        var designer = this.owner;
        if(designer.builder.selectControl){
            designer.attrPanel.panelShow();
        }
    }

    window.sysPrintDesignerBuilder=Designer_Builder;
})(window);
