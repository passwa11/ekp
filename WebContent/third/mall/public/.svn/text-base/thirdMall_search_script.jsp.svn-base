
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script>
    var keyWord="";
    var rowsize = 15; // 页面中的最大商品数量,默认15个
    var number = rowsize; // 页面中现有商品数量
    var pageno = 1; // 页数
    var industryIds = new Array(); // 行业
    var areaIds = new Array(); // 业务领域
    var cateIds = new Array(); // 分类主题
    var formatIds = new Array(); // 数据格式
    var componentTypeIds = new Array(); //部件分类
    var orderby="";
    $(document).ready(function(){
        $(".fy-nav-item").eq(1).addClass("active");
        for ( var i = 0; i < 2; i++ ) { //  、行业、领域
            if ( document.getElementsByClassName("fy-filter-select-list")[i]
                && document.getElementsByClassName("fy-filter-select-list")[i].offsetHeight < 24 ) {
                $(".collapse-btn")[i].style.display = "none"; // 隐藏更多按钮
            }
        }
        $(".collapse-btn").on('click',function(){ // 更多、收起切换
            if ( this.parentNode.parentNode.classList.contains('expand') ) {
                this.firstChild.data = "${lfn:message('third-mall:kmReuseCommon.more')} ";
                this.children[0].className = "fy-icon fy-icon-arrow-down";
                this.children[1].className = "fy-icon fy-icon-arrow-down active";
                this.parentNode.parentNode.classList.remove("expand");
            }
            else {
                this.firstChild.data = "${lfn:message('third-mall:kmReuseCommon.package')} ";
                this.children[0].className = "fy-icon fy-icon-arrow-up";
                this.children[1].className = "fy-icon fy-icon-arrow-up active";
                this.parentNode.parentNode.classList.add("expand");
            }
        });


    });
    function singleandMultiple(thisTag,varName){ // 单选、多选切换
        commonSelect($("."+varName)[0],varName); // 等同于点击不限
        if ( thisTag.classList.contains('single-btn') ) { // 单选->多选
            thisTag.classList.remove("single-btn");
            thisTag.classList.add("multi-btn");
            thisTag.innerHTML = "${lfn:message('third-mall:kmReuseCommon.oneChoose')}";
            thisTag.parentNode.nextElementSibling.classList.remove("single");
            thisTag.parentNode.nextElementSibling.classList.add("multiple");
        }else { // 多选->单选
            thisTag.classList.remove("multi-btn");
            thisTag.classList.add("single-btn");
            thisTag.innerHTML = "${lfn:message('third-mall:kmReuseCommon.manyChoose')}";
            thisTag.parentNode.nextElementSibling.classList.remove("multiple");
            thisTag.parentNode.nextElementSibling.classList.add("single");
        }
    }

    function commonSelect(thisTag,varName){ // 平台版本&行业搜索&创建人&成果类型
        if ( thisTag.id == "unlimited" ) { // 点击不限

            resetParameter(varName+"Item");
            resetFilterItem(varName+"Item");
            resetSelectedStyle(varName);
            thisTag.children[0].classList.add('selected');
            iframeSearch();
        }else { // 点击其他

            if ( thisTag.parentNode.classList.contains('single') ) { // 单选
                var name = thisTag.children[0].children[0].nextSibling.data;
                if ( varName == "industry" ) {
                    industryIds[0] = thisTag.id;
                    filterItemToJoin("${lfn:message('third-mall:kmReuseCommon.fdIndustry')}",name,"industryItem");
                }else 	 if ( varName == "area" ) {
                    areaIds[0] = thisTag.id;
                    filterItemToJoin("${lfn:message('third-mall:kmReuseCommon.fdArea')}",name,"areaItem");
                }else if ( varName == "cate") {
                    cateIds[0] = thisTag.id;
                    filterItemToJoin("${lfn:message('km-reuse:kmReuseThemeSet.fdThemeCate')}",name,"cateItem");
                }else if ( varName == "format") {
                    formatIds[0] = thisTag.id;
                    filterItemToJoin("${lfn:message('km-reuse:kmReuseThemeSet.fdThemeFormat')}",name,"formatItem");
                }else if ( varName == "componentType") {
                    componentTypeIds[0] = thisTag.id;
                    filterItemToJoin("${lfn:message('km-reuse:kmReuseThemeSet.component.type')}",name,"componentTypeItem");
                }
                resetSelectedStyle(varName);
                thisTag.children[0].classList.add('selected');
                iframeSearch();
            }
            else { // 多选
                $("."+varName+"-div")[0].style.display = "block"; // 显示确定、取消按钮
                $("."+varName)[0].children[0].classList.remove('selected');
                if ( !thisTag.parentNode.parentNode.classList.contains('expand') ) {
                    $("."+varName+"-collapse").trigger("click"); // 自动展开
                }
                if ( thisTag.children[0].classList.contains('selected') ) {
                    thisTag.children[0].className = ""; // 取消选中
                    thisTag.children[0].children[0].className = "fy-icon fy-icon-checkbox";
                    var num = 0;
                    for ( var i = 1; i < $("."+varName).length; i++ ) {
                        if ( $("."+varName)[i].children[0].children[0].classList.contains('active') ) {
                            num++;
                        }
                    }
                    if ( num == 0 ) { // 无任何选中时，默认选中不限
                        $("."+varName)[0].children[0].className = "all selected";
                    }
                }else {
                    thisTag.children[0].className = "selected"; // 选中
                    thisTag.children[0].children[0].className = "fy-icon fy-icon-checkbox active";
                }
            }
        }
    }
    function confirmButton(varName){ // 确定
        resetParameter(varName+"Item");
        var name = "";

        if ( varName == "industry" ) {
            for ( var i = 1; i < $("."+varName).length; i++ ) {
                if ( $("."+varName)[i].children[0].children[0].classList.contains('active') ) {
                    industryIds.push($("."+varName)[i].id);
                    name += $("."+varName)[i].children[0].children[0].nextSibling.data + "、";
                }
            }
            if ( name == "" ) { // 不限
                resetFilterItem(varName+"Item");
            }else {
                filterItemToJoin("${lfn:message('third-mall:kmReuseCommon.fdIndustry')}",name.substring(0,name.length-1),"industryItem");
            }
        }else  if ( varName == "area" ) {
            for ( var i = 1; i < $("."+varName).length; i++ ) {
                if ( $("."+varName)[i].children[0].children[0].classList.contains('active') ) {
                    areaIds.push($("."+varName)[i].id);
                    name += $("."+varName)[i].children[0].children[0].nextSibling.data + "、";
                }
            }
            if ( name == "" ) { // 不限
                resetFilterItem(varName+"Item");
            }else {
                filterItemToJoin("${lfn:message('third-mall:kmReuseCommon.fdArea')}",name.substring(0,name.length-1),"areaItem");
            }
        }else if ( varName == "cate" ) {
            for ( var i = 1; i < $("."+varName).length; i++ ) {
                if ( $("."+varName)[i].children[0].children[0].classList.contains('active') ) {
                    cateIds.push($("."+varName)[i].id);
                    name += $("."+varName)[i].children[0].children[0].nextSibling.data + "、";
                }
            }
            if ( name == "" ) { // 不限
                resetFilterItem(varName+"Item");
            }else {
                filterItemToJoin("${lfn:message('km-reuse:kmReuseThemeSet.fdThemeCate')}",name.substring(0,name.length-1),"cateItem");
            }
        }
        else if ( varName == "format" ) {
            for ( var i = 1; i < $("."+varName).length; i++ ) {
                if ( $("."+varName)[i].children[0].children[0].classList.contains('active') ) {
                    formatIds.push($("."+varName)[i].id);
                    name += $("."+varName)[i].children[0].children[0].nextSibling.data + "、";
                }
            }
            if ( name == "" ) { // 不限
                resetFilterItem(varName+"Item");
            }else {
                filterItemToJoin("${lfn:message('km-reuse:kmReuseThemeSet.fdThemeFormat')}",name.substring(0,name.length-1),"formatItem");
            }
        }
        else if ( varName == "componentType" ) {
            for ( var i = 1; i < $("."+varName).length; i++ ) {
                if ( $("."+varName)[i].children[0].children[0].classList.contains('active') ) {
                    componentTypeIds.push($("."+varName)[i].id);
                    name += $("."+varName)[i].children[0].children[0].nextSibling.data + "、";
                }
            }
            if ( name == "" ) { // 不限
                resetFilterItem(varName+"Item");
            }else {
                filterItemToJoin("${lfn:message('km-reuse:kmReuseThemeSet.component.type')}",name.substring(0,name.length-1),"componentTypeItem");
            }
        }
        $("."+varName+"-div")[0].style.display = "none"; // 隐藏确定、取消按钮
        iframeSearch();
    }
    function cancelButton(varName){ // 取消
        $("."+varName+"-div")[0].style.display = "none"; // 隐藏确定、取消按钮
        if ( varName == "docCreateTime" || varName == "docAlterTime" ) {
            resetParameter(varName+"Item");
            resetFilterItem(varName+"Item");
            $("."+varName)[0].value = "";
            $("."+varName)[1].value = "";
            iframeSearch();
        }else {
            commonSelect($("."+varName)[0],varName); // 等同于点击不限
        }
    }
    function resetFilterItem(varItem){ // 重置筛选项
        var deleteTag = document.getElementById(varItem);
        if ( deleteTag ) {
            deleteTag.parentNode.removeChild(deleteTag);
        }
    }

    function pageSearch(pageIndex,num){ // 根据页数进行查询
        pageno = pageIndex;
        number = num;
        iframeSearch();
    }

    function filterItemToJoin(title,value,id){ // 拼接筛选项
        pageno = 1; // 筛选项变化时,重置页数
        for ( var i = 0; i < $(".fy-breadcrumb-item").length; i++ ) {
            if ( id == $(".fy-breadcrumb-item")[i].id ) { // 有相同筛选项时进行替换操作
                $(".fy-breadcrumb-item")[i].children[0].children[1].innerHTML = value + " ";
                return;
            }
        }
        // 无相同筛选项时进行新建操作
        var str = '<li class="fy-breadcrumb-item" id="'+id+'"><a href="javascript:void(0)">'
            +'<span class="title">'+title+' : </span><span class="val">'+value
            +' </span><i class="fy-icon fy-icon-close" onclick="filterItemToDelete(this);"></i></a></li>';
        $(".fy-breadcrumb-list").append(str);
    }
    function filterItemToDelete(thisITag){ // 删除筛选项
        var deleteTag = thisITag.parentNode.parentNode;
        var deleteTagId = deleteTag.id;
        deleteTagId = deleteTagId.substring(0,deleteTagId.length-4);

        if (  deleteTagId == "industry" || deleteTagId == "area") {
            $("."+deleteTagId)[0].children[0].classList.add("selected");
            for ( var i = 1; i < $("."+deleteTagId).length; i++ ) {
                $("."+deleteTagId)[i].children[0].classList.remove("selected"); // 取消选中
                $("."+deleteTagId)[i].children[0].children[0].classList.remove("active"); // 取消勾选
            }
        }
        resetParameter(deleteTag.id);
        deleteTag.parentNode.removeChild(deleteTag);
        iframeSearch();
    }
    function resetParameter(varItem){ // 重置参数
        pageno = 1; // 筛选项变化时,重置页数

        if ( varItem == "industryItem" ) {
            industryIds = [];
        }
        else if ( varItem == "areaItem" ) {
            areaIds = [];
        }else if ( varItem == "cateItem" ) {
            cateIds=[];
        }
        else if ( varItem == "formatItem" ) {
            formatIds=[];
        }
        else if ( varItem == "componentTypeItem" ) {
            componentTypeIds=[]
        }
    }
    function resetSelectedStyle(selectedClass){ // 重置选中样式
        $("."+selectedClass)[0].children[0].classList.remove("selected");
        for ( var i = 1; i < $("."+selectedClass).length; i++ ) {
            if ( $("."+selectedClass)[i].children[0].children[0].classList.contains('active') ) { // 多选状态下
                $("."+selectedClass)[i].children[0].children[0].classList.remove("active");
            }
            $("."+selectedClass)[i].children[0].classList.remove("selected");
        }
    }


</script>
