<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <title>黑晶芯小程序后台管理</title>



    <!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html"/><![endif]-->

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/css/bootstrap-table.min.css" rel="stylesheet">
    <link href="${contextPath}/css/font-awesome.css" rel="stylesheet">
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/css/style.min.css" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element" style="text-align: center;">
                        <span><img style="width: 80px;height: 80px;" alt="image" class="img-circle" src="${contextPath}/picture/hjx.jpg"/></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold"> </strong></span>
                                <span class="text-muted text-xs block" style="margin-top: 10px;">黑晶芯小程序 </span>
                                </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">

                        </ul>
                    </div>
                    <div class="logo-element">黑晶芯
                    </div>
                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-home"></i> <span class="nav-label">用户管理</span> <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" href="${contextPath}/manage/user_info" data-index="0">用户信息</a>
                        </li>
                    </ul>


                </li>
                <li>
                    <a href="#">
                        <i class="fa fa-home"></i> <span class="nav-label">意见反馈</span> <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" href="${contextPath}/manage/admin_fk" data-index="0">反馈管理</a>
                        </li>
                        <li>
                            <a class="J_menuItem" href="${contextPath}/manage/admin_user">反馈设置</a>
                        </li>
                    </ul>


                </li>

               <!-- <li>
                    <a href="#"><i class="fa fa-cutlery"></i>
                        <span class="nav-label">反馈设置 </span><span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">

                        <li>
                            <a class="J_menuItem" href="form_builder.html">问题列表</a>
                        </li>
                    </ul>
                </li>-->

                <li>
                    <a href="#">
                        <i class="fa fa fa-bar-chart-o"></i> <span class="nav-label">充值管理</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a class="J_menuItem" href="${contextPath}/manage/recharge">充值记录</a>
                        </li>

                    </ul>
                </li>




            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i>
                    </a>

                </div>
                <ul class="nav navbar-top-links navbar-right">


                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="index_v1.html">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabFresh"><a>刷新</a>
                    </li>
                    <li class="J_tabShowActive">
                        <a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll">
                        <a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther">
                        <a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="${contextPath}/manage/exit" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="" frameborder="0" data-id="index_v1.html" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2017-2018
                <a href="http://www.jingkingsmart.com/" target="_blank">四川精工伟达</a>
            </div>
        </div>
    </div>
</div>
<script src="${contextPath}/js/jquery.min.js"></script>
<script src="${contextPath}/js/bootstrap.min.js"></script>
<script src="${contextPath}/js/jquery.metismenu.js"></script>
<script src="${contextPath}/js/jquery.slimscroll.min.js"></script>
<script src="${contextPath}/js/layer/layer.min.js"></script>
<script src="${contextPath}/js/hplus.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/contabs.min.js"></script>
<script src="${contextPath}/js/pace.min.js"></script>
</body>

</html>
