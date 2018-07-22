<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>黑晶芯后台管理系统 - 登录</title>

    <link rel="shortcut icon" href="favicon.ico">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.staticfile.org/font-awesome/4.4.0/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="https://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/css/style.min.css?v=4.1.0" rel="stylesheet">
    <script href="${contextPath}/js/jquery.min.js"></script>
    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/><![endif]-->
    <script>if (window.top !== window.self) {
        window.top.location = window.location;
    }</script>
</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">H+</h1>

        </div>
        <h3>欢迎使用 黑晶芯后台管理系统</h3>

        <div class="m-t" role="form" >
            <div class="form-group">
                <input id="name" type="text" class="form-control" placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input id="password" type="password" class="form-control" placeholder="密码" required="">
            </div>
            <button id="sub" type="submit" class="btn btn-primary block full-width m-b">登 录</button>


            <p class="text-muted text-center">
                <a href="login.jsp#">
                    <small>忘记密码了？</small>
                </a>
                |
                <a href="javascript:;">提示：注册新号请联系管理员</a>
            </p>

        </div>
    </div>
</div>
<script src="https://cdn.staticfile.org/jquery/2.1.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>

</body>
<script type="text/javascript">
    $("#sub").click(function () {
       var name = $("#name").val();
       var pwd = $("#password").val();
       $.ajax({
           url:"${contextPath}/manage/doBGLogin",
           type:"post",
           dataType:"text",
           data:{
               "name":name,
               "pwd":pwd
           },
           success:function (data) {
                if(data=="success"){
                    location.href="${contextPath}/manage/index"
                    // alert("success");
                }else{

                    alert("用户名或密码错误！")
                }
           },
           error:function (XMLHttpRequest, textStatus, errorThrown) {
               alert("网络错误！");
           }
       })
    });
</script>
</html>
