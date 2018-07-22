<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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
            <h5>充值记录</h5>
        </div>
        <div class="ibox-content">

            <!--搜索框开始-->
            <form id='commentForm' role="form" method="post" class="form-inline pull-right">
                <div class="content clearfix m-b">
                    <div class="form-group">
                        <label>用户名：</label>
                        <input type="text" class="form-control" id="nickName" name="title">

                    </div>
                    <div class="form-group">
                        <label>微信支付流水号：</label>
                        <input type="text" class="form-control" id="wechat_no" name="title">

                    </div>
                    <div class="form-group">
                        <label>充值时间：</label>
                        <input name="startdate" class="form-control" type="date" id="startTime">
                    </div>
                    <div class="form-group">
                        <label>结束日期：</label>
                        <input name="enddate" class="form-control" type="date" id="endTime">
                    </div>
                    <div class="form-group">
                        <label>充值卡号：</label>
                        <input name="enddate" class="form-control" type="text" id="cardNo">
                    </div>
                    <div class="form-group">
                        <label>充值金额：</label>
                        <select name="enddate" class="form-control" type="text" id="chargeNum">
                            <option></option>
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="30">30</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                            <option value="200">200</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>支付方式：</label>
                        <select name="enddate" class="form-control" type="text" id="payType">
                            <option></option>
                            <option value="1">微信支付</option>
                            <option value="2">翼支付</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>订单状态：</label>
                        <select name="enddate" class="form-control" type="text" id="status">
                            <option></option>
                            <option value="1">已支付，未圈存</option>
                            <option value="2">已支付，已圈存</option>
                        </select>
                    </div>
                    <div class="form-group">
                      <p style="opacity: 0;"> wenju</p>
                        <input  class="btn btn-primary btn-fk-search" type="button"
                                style=" " id="search" value="搜 索">

                    </div>
                </div>
            </form>
            <!--搜索框结束-->

            <div class="example-wrap">
                <div class="example">
                    <table id="cusTable">
                        <thead>
                        <tr>
                            <th> 编号</th>
                            <th>用户名称</th>
                            <th>充值时间</th>
                            <th>充值金额（/元）</th>
                            <th>充值类型</th>
                            <th>充值状态</th>
                            <th>微信支付流水号</th>
                            <th>充值卡号</th>
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
  function timetrans(date) {
    var date = new Date(date);//如果date为13位不需要乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = (date.getDate() < 10 ? '0' + (date.getDate()) : date.getDate()) + ' ';
    var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
    var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
    var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
    return Y + M + D + h + m + s;
  }

  function initTable() {
    //先销毁表格
    $('#cusTable').bootstrapTable('destroy');
    //初始化表格,动态从服务器加载数据
    $("#cusTable").bootstrapTable({
      method: "get",  //使用get请求到服务器获取数据
      url: "${contextPath}/BG/charge/list", //获取数据的地址
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
          endTime: $('#endTime').val(),
          enddate: $('#enddate').val(),
          wechatNo:$("#wechat_no").val(),
          cardNo:$("#cardNo").val(),
          status:$("#status").val(),
          payType:$("#payType").val(),
          chargeNum:$("#chargeNum").val()

        };
        return param;
      },

      onLoadSuccess: function (res) {  //加载成功时执行
        if (111 == res.code) {
          window.location.reload();
        }
        layer.msg("加载成功", {time: 1000});

        console.log(res)
        //执行出货监听
        // shipmentListent();
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
        field: 'nickName'

      }, {
        field: 'createTime',

        formatter: function (row) {
          return timetrans(row);

        }
      }, {
        field: 'chargeNum',
        formatter: function (row) {

          return row / 100

        }
      }, {
        field: 'payType',
        formatter: function (row) {
          return row == 1 ? '微信支付' : '翼支付';

        }
      },{
        field: 'status',
        formatter: function (row) {
          return row == 1 ? '已支付，未圈存' : row==2?'已支付，已圈存':"未知状态";

        }
      }, {
        field: 'transactionId',
      }, {
        field: 'cardNo',
      },

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

  function articleDel(id) {
    layer.confirm('确认删除此记录?', {icon: 3, title: '提示'}, function (index) {
      //do something
      $.getJSON("{:url('articles/articleDel')}", {'id': id}, function (res) {
        if (1 == res.code) {
          layer.alert(res.msg, {title: '友情提示', icon: 1, closeBtn: 0}, function () {
            initTable();
          });
        } else if (111 == res.code) {
          window.location.reload();
        } else {
          layer.alert(res.msg, {title: '友情提示', icon: 2});
        }
      });

      layer.close(index);
    })

  }

  $(function () {

  })

</script>
</body>
</html>
