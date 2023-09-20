var __auditshows = [];
var __auditshowCount = 2;
function auditshows_show(controlId,opValue,sortNote,processId,exhibitionDataClass,exhibitionStyleClass,groupNote,filterNote,width,info){
    this.controlId=controlId;
    this.opValue = opValue;
    this.sortNote = sortNote;
    this.processId=processId;
    this.exhibitionDataClass = exhibitionDataClass;
    this.exhibitionStyleClass = exhibitionStyleClass;
    this.groupNote=groupNote;
    this.filterNote=filterNote;
    this.width=width;
    this.info=info;
    __auditshows.push(this);
}
$(document).ready(function(){
    setTimeout(function(){
        if(__auditshows.length>0){
            $.post(Com_Parameter.ContextPath+"sys/lbpmservice/support/auditNoteDataShowAction.do", $.param({method:"getNoteDataShow",fdModelId:_xformMainModelId,fdModelName:_xformMainModelClass,type:'bulidCache'},true), function(data,status){
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
                for(var i=0;i<__auditshows.length;i++){
                    controlIds.push(__auditshows[i].controlId);
                    opValues.push(__auditshows[i].opValue);
                    sortNotes.push(__auditshows[i].sortNote);
                    processIds.push(__auditshows[i].processId);
                    exhibitionDataClasses.push(__auditshows[i].exhibitionDataClass);
                    exhibitionStyleClasses.push(__auditshows[i].exhibitionStyleClass);
                    groupNotes.push(__auditshows[i].groupNote);
                    filterNotes.push(__auditshows[i].filterNote);
                    widths.push(__auditshows[i].width);
                    infos.push(__auditshows[i].info);
                }
                var _index = 0;
                var _count = Math.ceil(__auditshows.length/__auditshowCount);
                while(_index<__auditshows.length){
                    var endIndex = (_index+__auditshowCount>__auditshows.length)?__auditshows.length:(_index+__auditshowCount);
                    var param = {
                        method : "getNoteDataShow",
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
                        fdModelName:_xformMainModelClass,
                        fdModelId:_xformMainModelId
                    };
                    _index += __auditshowCount;
                    $.ajax({
                        url: Com_Parameter.ContextPath+"sys/lbpmservice/support/auditNoteDataShowAction.do",
                        type:'POST',
                        data :$.param(param,true),
                        async:true,//异步请求
                        success: function(data){
                            for(var controlId in data){
                                $("#"+controlId+"[auditAttr='xform_auditshow']").append(data[controlId]);
                            }
                        },
                        complete : function() {
                            _count--;
                            if(_count==0){
                                $.post(Com_Parameter.ContextPath+"sys/lbpmservice/support/auditNoteDataShowAction.do", $.param({method:"getNoteDataShow",fdModelId:_xformMainModelId,fdModelName:_xformMainModelClass,type:'cleanCache'},true), function(data,status){

                                },"json");
                            }
                        },
                        dataType: 'json'
                    });
                }
            },"json");
        }
    },0);
});