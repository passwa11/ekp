<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/fssc/mobile/resource/jsp/mobile_include.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <title>手机端弹出框</title>
    <script>
        setSize();
        addEventListener('resize',setSize);
        function setSize() {
            document.documentElement.style.fontSize = document.documentElement.clientWidth/750*100+'px';
        }
    </script>
    <style>
        .show-list{
            width:80%;
            margin: 0 auto;
        }
        .show-list li{
            height: 1rem;
            font-size: .3rem;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-bottom: 1px solid #dcdcdc;
        }
    </style>
</head>
<body>
    <ul class="show-list">
        <li id="demo1">toast</li>
        <li id="demo2">alert</li>
        <li id="demo3">confirm</li>
        <li id="demo4">prompt</li>
        <li id="demo5">点击按钮跳转</li>
        <li id="demo6">取消默认点击背景消失</li>
    </ul>
</body>
    <script>
        $(function () {
            $('#demo1').click(function () {
                jqtoast('你点击了toast')
            })
            $('#demo2').click(function () {
                jqalert({
                    title:'提示',
                    content:'自定义弹窗内容'
                })
            })
            $('#demo3').click(function () {
                jqalert({
                    title:'提示',
                    content:'自定义弹窗内容',
                    yestext:'知道了',
                    notext:'取消'
                })
            })
            $('#demo4').click(function () {
                jqalert({
                    title:'提示',
                    prompt:'请问你叫什么名字?',
                    yestext:'提交',
                    notext:'取消',
                    yesfn:function () {
                        jqtoast('提交成功');
                    },
                    nofn:function () {
                        jqtoast('你点击了取消');
                    }
                })
            })
            $('#demo5').click(function () {
                jqalert({
                    title:'提示',
                    content:'自定义弹窗内容',
                    yeslink:'https://www.baidu.com/'
                })
            })
            $('#demo6').click(function () {
                jqalert({
                    title:'提示',
                    content:'自定义弹窗内容',
                    click_bg:false
                })
            })
        })

    </script>
</html>
