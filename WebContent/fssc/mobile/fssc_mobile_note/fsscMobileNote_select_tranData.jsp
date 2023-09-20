<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/sys/ui/jsp/common.jsp" %>
<%@ include file="/resource/jsp/view_top.jsp" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/public.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/costDetail.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/home.css?s_cache=${LUI_Cache }">
    <link rel="stylesheet" href="${LUI_ContextPath}/fssc/mobile/resource/css/rememberOne.css?s_cache=${LUI_Cache }" >
    <script src="${LUI_ContextPath}/fssc/mobile/resource/js/kk-1.2.73.min.js"></script>
    <script src="//g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
    <script src="${LUI_ContextPath}/fssc/common/resource/js/Number.js"></script>
    <script>
        var formInitData={
            'LUI_ContextPath':'${LUI_ContextPath}',
        }
    </script>
    <title>交易数据</title>
</head>
<body>
<div class="ld-costDetail">
    <div class="ld-notSubmit-head" style="z-index:99;">
        <div style="position:absolute;left:0.2rem;border:none;font-size:14px;">交易数据</div>
        <div class="ld-notSubmit-select" style="position:absolute;right:0;border:none;" id="select" onclick="selectList()">${lfn:message('button.cancel')}</div>
    </div>
    <div class="ld-notSubmit-main">
        <div class="ld-notSubmit-list">
            <ul>
                <c:forEach items="${tranDataList.data}" var="data"  varStatus="status">
                    <li class="ld-notSubmit-list-item"  >
                        <label id='ld-label-${status.index}'>
                            <div class="ld-checkBox"  style="display:block;">
                                <input type="checkbox" name="tranDataDetail" value="${data.fdId}" />
                                <span class="checkbox-label"></span>
                            </div>
                            <div class="ld-notSubmit-list-item-box">
                                <div class="ld-notSubmit-list-bottom">
                                    <div class="ld-notSubmit-list-bottom-info">
                                        <div>
                                            <span>${data.fdActChiNam}</span>
                                            <span class="ld-verticalLine"></span>
                                            <span>${data.fdTrsDte}</span>
                                        </div>
                                        <span id='price-${status.index}' class="price">${data.fdOriCurAmt}</span>
                                    </div>
                                    <p>
                                        <span>${data.fdCrdNum}</span>
                                        <span class="ld-verticalLine"></span>
                                        <sunbor:enumsShow enumsType="fssc_tran_data_trsCod" value="${data.fdTrsCod}"></sunbor:enumsShow>
                                    </p>
                                </div>
                            </div>
                        </label>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="ld-create-expense">
        <div class="ld-costDetail-footer">
            <div class="ld-checkBox">
                <input type="checkbox" id="selectAll" id="ld-selectAll" value="0"/>
                <label class="checkbox-label" for="ld-selectAll">全选</label>
            </div>
            <div class="ld-costDetail-btn"  id="expenseBtn" onclick="selectList(true)">确定</div>
        </div>
    </div>
</div>
</body>
<input name="pageno" value="${queryPage.pageno}" type="hidden"/>
<input name="rowsize" value="${queryPage.rowsize}" type="hidden"/>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/zepto.js"></script>
<script src="${LUI_ContextPath}/fssc/mobile/resource/js/dyselect.js"></script>
<script  type="text/javascript">
    Com_IncludeFile("index.js", "${LUI_ContextPath}/fssc/mobile/fssc_mobile_main/", 'js', true);
</script>
<script type="text/javascript">
    $(function() {
        $(document).off('change',"input[name='tranDataDetail']").on('change',"input[name='tranDataDetail']",function(){
            if($("[name='tranDataDetail']:checked").length==$("[name='tranDataDetail']").length){  //全部选中了
                $("#selectAll").prop('checked',true);  //选中全选复选框
            }else{
                $("#selectAll").prop('checked',false);  //取消选中全选复选框
            }
        });
    });

    //全选
    $('#selectAll').on('click',function(){
        if(this.checked) {
            $("input[name='tranDataDetail']").prop('checked',true);
        } else {
            $("input[name='tranDataDetail']").prop('checked',false);
        }
    });
    function selectList(flag){
        var ids = [];
        if(flag){
            $("[name=tranDataDetail]:checked").each(function(){
                ids.push(this.value);
            })
        }
        window.parent.selectTranDataList(ids.join(';'));
    }
</script>
</html>
