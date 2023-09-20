define(["dojo/_base/declare","dijit/_WidgetBase","dojo/request","dojo/query","dojo/topic"], function(declare,WidgetBase,request,query,topic){
    return declare("sys.xform.mobile.controls.AuditShowContent",[WidgetBase],{

        buildRendering :function(){
            this.inherited(arguments);
            if(typeof __auditshows == "undefined"){
                __auditshows = {};
                __auditshowCount = 2;
            }
            __auditshows[this.controlId] = this;
        },

        startup: function () {
            this.inherited(arguments);
            topic.subscribe('parser/done',this.getNoteDataShow());
            topic.subscribe('parser/dialog/done',this.getNoteDataShow());
        },
        getNoteDataShow: function(){
            if(typeof __auditshows_init == "undefined"){
                var url = Com_Parameter.ContextPath+"sys/lbpmservice/support/auditNoteDataShowAction.do?method=getNoteDataShow";
                request(url,{data:{fdModelId:_xformMainModelId,fdModelName:_xformMainModelClass,type:'bulidCache'}, method:"post",handleAs:"json"}).then(function(data){
                    var controlIds = [];
                    var opValues = [];
                    var sortNotes = [];
                    var processIds = [];
                    var exhibitionDataClasses = [];
                    var exhibitionStyleClasses = [];
                    var groupNotes = [];
                    var filterNotes = [];
                    var widths = [];
                    var infos = [];
                    for(var key in __auditshows){
                        controlIds.push(__auditshows[key].controlId);
                        opValues.push(__auditshows[key].opValue);
                        sortNotes.push(__auditshows[key].sortNote);
                        processIds.push(__auditshows[key].processId);
                        exhibitionDataClasses.push(__auditshows[key].exhibitionDataClass);
                        exhibitionStyleClasses.push(__auditshows[key].exhibitionStyleClass);
                        groupNotes.push(__auditshows[key].groupNote);
                        filterNotes.push(__auditshows[key].filterNote);
                        widths.push(__auditshows[key].width);
                        infos.push(__auditshows[key].info);
                    }
                    var _index = 0;
                    var _count = Math.ceil(controlIds.length/__auditshowCount);
                    while(_index<controlIds.length){
                        var endIndex = (_index+__auditshowCount>controlIds.length)?controlIds.length:(_index+__auditshowCount);
                        var options = {
                            data:{
                                controlIds : controlIds.slice(_index,endIndex),
                                opValues : opValues.slice(_index,endIndex),
                                sortNotes:sortNotes.slice(_index,endIndex),
                                processIds:processIds.slice(_index,endIndex),
                                exhibitionDataClasses:exhibitionDataClasses.slice(_index,endIndex),
                                exhibitionStyleClasses:exhibitionStyleClasses.slice(_index,endIndex),
                                groupNotes:groupNotes.slice(_index,endIndex),
                                filterNotes:filterNotes.slice(_index,endIndex),
                                widths:widths.slice(_index,endIndex),
                                infos:infos.slice(_index,endIndex),
                                isMobile:true,
                                fdModelName:_xformMainModelClass,
                                fdModelId:_xformMainModelId
                            },
                            method:"post",
                            handleAs:"json"
                        };
                        _index += __auditshowCount;
                        request(url,options).then(function(data){
                            for(var controlId in data){
                                query(__auditshows[controlId].domNode).html(data[controlId],{
                                    parseContent: true,
                                    onEnd: function() {
                                        this.inherited("onEnd", arguments);
                                        //#172505 再计算一次高度
                                        topic.publish("/mui/list/resize","resize");
                                    }
                                })
                            }
                            _count--;
                            if(_count==0){
                                request(url,{data:{fdModelId:_xformMainModelId,fdModelName:_xformMainModelClass,type:'cleanCache'}, method:"post",handleAs:"json"}).then(function(data){

                                },function(err){
                                    console.error(err);
                                })
                            }
                        },function(err){
                            console.error(err);
                            _count--;
                            if(_count==0){
                                request(url,{data:{fdModelId:_xformMainModelId,fdModelName:_xformMainModelClass,type:'cleanCache'}, method:"post",handleAs:"json"}).then(function(data){

                                },function(err){
                                    console.error(err);
                                })
                            }
                        })
                    }
                },function(err){
                    console.error(err);
                })
                __auditshows_init = true;
            }
        }
    })
})