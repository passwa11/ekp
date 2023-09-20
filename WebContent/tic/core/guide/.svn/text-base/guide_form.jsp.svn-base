<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/htmlhead.jsp" %>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>02-jco部署</title>
    <!-- 系统原有css -->
    <link rel="stylesheet" href="./css/lib/common.css">
    <!-- 集成中心 -->
    <link rel="stylesheet" href="./css/custom.css">
    
    <script src="<c:url value='/resource/js/jquery.js'/>"></script>
    
    <script>
    
   // Com_IncludeFile("jquery.js", null, "js");
    
        $(function () {
            $('.lui_joc_form_tab_container').on('click', '.lui_joc_form_tab_header li', function () {
                var $this = $(this)
                var index = $this.index()
                $this.addClass('lui_joc_active').siblings().removeClass('lui_joc_active')
                $this.parents('.lui_joc_form_tab_container').find('.lui_joc_form_tab_item').eq(index)
                    .addClass('lui_joc_active').siblings().removeClass('lui_joc_active')
            })
        })
    </script>
</head>

<body>
   <div class="lui_joc_guide_container">
<!-- 内容区域 -->
        <div class="lui_joc_guide_content">
            <div class="lui_joc_guide_content_item">
                <!-- 选择列表 -->
                <div class="lui_joc_form_container">
                    <div class="lui_joc_form_header" align="center">
                        		<a href="<c:url value='/sys/profile/index.jsp#app/ekp/km/review'/>" target="_blank"><font style="font-size:25px;color:red">配置表单模板</font></a>
                    </div>
                    <div class="lui_joc_form_content">
                        <!-- 应用场景介绍 切换 -->
                        <h3 class="lui_joc_form_title">应用场景介绍</h3>
                        <div class="lui_joc_form_tab_container">
                            <div class="lui_joc_form_tab_header">
                                <ul>
                                    <li class="lui_joc_active">表单控件</li>
                                    <li>审批节点事件</li>
                                    <li>机器人节点</li>
                                </ul>
                            </div>
                            <div class="lui_joc_form_tab_content">
                                <div class="lui_joc_form_tab_item lui_joc_active">
                                    <img src="images/img-01.jpg" alt="">
                                </div>
                                <div class="lui_joc_form_tab_item">
                                    <img src="images/img-02.jpg" alt="">
                                </div>
                                <div class="lui_joc_form_tab_item">
                                    <img src="images/img-03.jpg" alt="">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
       </div> 

</body>

</html>