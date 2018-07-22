<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
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
    <link rel="stylesheet" href="${contextPath}/js/layer/skin/layer.css">
    <link href="${contextPath}/css/font-awesome.css" rel="stylesheet">
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/css/style.min.css" rel="stylesheet">
</head>
<body>

<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>用户信息</h5>
        </div>
        <div class="ibox-content">

            <!--搜索框开始-->
            <form id='commentForm' role="form" method="post" class="form-inline pull-right">
                <div class="content clearfix m-b">
                    <div class="form-group">
                        <label>用户名称：</label>
                        <input type="text" class="form-control" id="nickName" name="title">

                        <%--<input type="text" class="form-control" id="model" name="model">--%>

                    </div>
                    <div class="form-group">
                        <label>注册时间：</label>
                        <input name="startdate" class="form-control" type="date" id="startTime">
                    </div>
                    <div class="form-group">

                        <label>结束日期：</label>
                        <input name="enddate" class="form-control" type="date" id="endTime">


                    </div> &nbsp;&nbsp;

                    <div class="form-group">
                        <button class="btn btn-primary " type="button"
                                style="margin-top:5px" id="search"><strong>搜 索</strong>
                        </button>
                    </div>
                </div>
            </form>
            <!--搜索框结束-->
            <button class="btn btn-primary" style="margin-bottom: 10px;" id="excel">导出结果为Excel
            </button>
            <div class="example-wrap">
                <div class="example">
                    <table id="cusTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>用户名称</th>
                            <th>设备数量</th>
                            <th>注册时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
            <!-- End Example Pagination -->
        </div>
    </div>
</div>

<!-- 编辑节点 -->
<div class="ibox-content" id="edit_box" style="display: none;">
    <div class="row">
        <%--<div class="col-md-12">--%>
            <%--<h4>用户详情</h4>--%>
        <%--</div>--%>
    </div>

    <div class="row" id="content">
        <%--<div class="col-md-6">
            <p>用户名称：<span id="wj_userName"></span></p>
        </div>
        <div class="col-md-6">
            <p>设备名称：<span id="wj_deviceName"></span></p>
        </div>
        <div class="col-md-6">
            <p>设备名称：<span id="wj_cardNo"></span></p>
        </div>
        <div class="col-md-6">
            <p>设备名称：<span id="wj_matchTime"></span></p>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <p>卡号：<span id="wj_type">呵呵</span></p>
        </div>
        <div class="col-md-6">
            <p>配对时间：<span id="wj_question">2018-01-29 10:21</span></p>
        </div>
    </div>--%>
</div>
<!-- 添加节点 -->


<script src="${contextPath}/js/jquery.min.js"></script>
<script src="${contextPath}/js/bootstrap.min.js"></script>
<script src="${contextPath}/js/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${contextPath}/js/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${contextPath}/js/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<script src="${contextPath}/js/jquery.metismenu.js"></script>
<script src="${contextPath}/js/jquery.slimscroll.min.js"></script>
<script src="${contextPath}/js/layer/layer.min.js"></script>
<script src="${contextPath}/js/hplus.min.js"></script>
<script type="text/javascript" src="${contextPath}/js/contabs.min.js"></script>
<script src="${contextPath}/js/pace.min.js"></script>
<script>
  function timestampToTime(timestamp) {
    var date = new Date(timestamp/1000*1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '-';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
    D = date.getDate() + ' ';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D+h+m+s;
  }

  function initTable() {
    //先销毁表格
    $('#cusTable').bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $("#cusTable").bootstrapTable({
      method: "get",  //使用get请求到服务器获取数据
      url: "${contextPath}/BG/userInfo", //获取数据的地址
      striped: true,  //表格显示条纹
      pagination: true, //启动分页
      pageSize: 10,  //每页显示的记录数
      pageNumber: 1, //当前第几页
      pageList: [5, 10, 15, 20, 25],  //记录数可选列表
      sidePagination: "server", //表示服务端请求
      paginationFirstText: "首页",
      paginationPreText: "上一页",
      paginationNextText: "下一页",
      paginationLastText: "尾页",
      queryParamsType: "undefined",
      queryParams: function queryParams(params) {   //设置查询参数
        var param = {
          pageNumber: params.pageNumber,
          pageSize: params.pageSize,
          nickName: $('#nickName').val(),
          startTime: $('#startTime').val(),
          endTime: $('#endTime').val()
        };
        return param;
      },
      onLoadSuccess: function (res) {  //加载成功时执行
        if (111 == res.code) {
          window.location.reload();
        }
        layer.msg("加载成功", {time: 1000});
        //执行出货监听
        // shipmentListent();
        info();
      },
      onLoadError: function () {  //加载失败时执行
        layer.msg("加载数据失败");
      },
      columns: [{
        field: 'Number',
        formatter: function (value, row, index) {
          return index + 1;
        }
      }, {
        field: 'nickName',
      }, {
        field: 'deviceCount'
      }, {
        field: 'createTime',
        formatter: function (row) {
          return row ? timestampToTime(row) : "";
        }
      }, {
        field: 'openId',
        formatter: function (row) {
          return "<a class='btn-fk-search btn btn-info' data-id='" + row + "'>详情</a>"
        }
      }

      ]
    });
  }

  $(document).ready(function () {
    //调用函数，初始化表格
    initTable();

    //当点击查询按钮的时候执行
    $("#search").bind("click", function () {
      var startTime = $('#startTime').val();
      var endTime = $('#endTime').val();
      if(startTime!=null&&startTime!=""){
        if(endTime==null||endTime==""){
          layer.alert("请选择结束时间");
          return false;
        }

      }
      if(endTime!=null&&endTime!=""){
        if(startTime==null||startTime==""){
          layer.alert("请选择开始时间");
          return false;
        }
      }

      initTable();
    });

  });

  function info() {
    $('.btn-fk-search').on('click', function () {
      var id = $(this).attr("data-id");
      $.ajax({
        url: "${contextPath}/BG/userInfo/info",
        data: {
          "openId": id
        },
        dataType: "json",
        success: function (data) {
          console.log(data);
          var str = '';
          for(var i=0;i<data.device.length;i++){
            str+='<div class="col-md-6">\n'
                + '            <p><strong>用户名称：</strong><span id="wj_userName"></span>'+data.user.nickName+'</p>\n'
                + '        </div>\n'
                + '        <div class="col-md-6">\n'
                + '            <p><strong>设备名称：</strong><span id="wj_deviceName"></span>'+data.device[i].deviceName+'</p>\n'
                + '        </div>\n'
                + '        <div class="col-md-6">\n'
                + '            <p><strong>卡号：</strong><span id="wj_cardNo"></span>'+data.device[i].cardNo+'</p>\n'
                + '        </div>\n'
                + '        <div class="col-md-6">\n'
                + '            <p><strong>配对时间：</strong><span id="wj_matchTime"></span>'+timestampToTime(data.device[i].matchTime)+'</p>\n'
                + '        </div>'
          }
          $("#content").html(str);
          console.log(str);
        }
      })
      box = layer.open({
        type: 1,
        title: '用户详情',
        anim: 2,
        skin: 'layui-layer-molv', //加上边框
        area: ['920px', '400px'], //宽高
        content: $('#edit_box')
      });
    });
  }

  $("#excel").click(function () {
    var openId = $('#openId').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    location.href = "${contextPath}/BG/export_user?openId=" + openId + "&startTime=" + startTime + "&endTime=" + endTime;
  })


</script>
</body>
</html>
