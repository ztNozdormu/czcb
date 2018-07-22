<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%><!DOCTYPE html>
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

    <link href="${contextPath}/css/font-awesome.css" rel="stylesheet">
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/css/style.min.css" rel="stylesheet">
</head>
<body>

<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>反馈配置</h5>


        </div>

        <div class="ibox-content">

            <!--搜索框开始-->
            <div class="row">
                <div class="col-md-12">
                    <a href="javascript:;"  class="btn btn-feedback-add btn-success" style="margin-bottom: 10px;">添加</a>
                </div>
            </div>
            <!--搜索框结束-->

            <div class="example-wrap">
                <div class="example">
                    <table id="cusTable">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>反馈类型</th>
                            <th>创建时间</th>
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
            url: "${contextPath}/BG/feedbackType/list", //获取数据的地址
            striped: true,  //表格显示条纹
            pagination: true, //启动分页
            pageSize: 10,  //每页显示的记录数
            pageNumber:1, //当前第几页
            pageList: [5, 10, 15, 20, 25],  //记录数可选列表
            sidePagination: "server", //表示服务端请求
            paginationFirstText: "首页",
            paginationPreText: "上一页",
            paginationNextText: "下一页",
            paginationLastText: "尾页",
            queryParamsType : "undefined",
            queryParams: function queryParams(params) {   //设置查询参数
                var param = {
                    pageNumber: params.pageNumber,
                    pageSize: params.pageSize,
                    searchText:$('#title').val(),
                    model: $('#model').val(),
                    startdate:$('#startdate').val(),
                    enddate:$('#enddate').val()
                };
                return param;
            },
            onLoadSuccess: function(res){  //加载成功时执行
                if(111 == res.code){
                    window.location.reload();
                }
                layer.msg("加载成功", {time : 1000});
                //执行出货监听
                // shipmentListent();
                feedbackEdit();
            },
            onLoadError: function(){  //加载失败时执行
                layer.msg("加载数据失败");
            },
            columns: [{
                field: 'Number',
                formatter: function (value, row, index) {
                    return index+1;
                }
            }, {
                field: 'text'
            }, {
                field: 'createTime',
                formatter:function (row) {
                    return timetrans(row);
                }
            }, {
                field:'id',
                formatter:function (row) {
                    return "<a  class='btn btn-info feedback-edit'  data-id='"+ row +"'>修改</a> <a class='btn btn-success' href='${contextPath}/manage/fk_config?id="+row+"' >配置</a>  <a class='btn btn-warning' onclick='articleDel(\""+row+"\")'>删除</a>"
                }
            }


            ]
        });
    }

    $(document).ready(function () {
        //调用函数，初始化表格
        initTable();
        feedbackadd();

        //当点击查询按钮的时候执行
        $("#search").bind("click", initTable);

    });
    function feedbackEdit(){
        $('.feedback-edit').on('click', function () {
            var id = $(this).attr('data-id');
            var text = $(this).parent().parent().find('td:eq(1)').html();
            layer.prompt({
                formType: 0,
                value: text,
                title: '修改反馈类型'
            }, function(value, index, elem){
                $.ajax({
                    url:"${contextPath}/BG/feedbackType/edit?text="+value+"&feedbackTypeId="+id,
                    dataType:"text",
                    success:function (data) {
                        if(data=="success"){
                            initTable();
                        }else {
                            layer.alert("修改失败")
                        }
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                        layer.alert("网络错误！"+textStatus);
                    }
                })

                layer.close(index);
            });
        })
    }

    function feedbackadd() {
        $('.btn-feedback-add').on('click', function () {
            layer.prompt({
                formType: 0,
                value: '',
                title: '新增反馈类型'
            }, function(value, index, elem){
                $.ajax({
                    url:"${contextPath}/BG/feedbackType/edit?text="+value,
                    dataType:"text",
                    success:function (data) {
                        if(data=="success"){
                            initTable();
                        }else {
                            layer.alert("添加失败")
                        }
                    },
                    error:function (XMLHttpRequest, textStatus, errorThrown) {
                        layer.alert("网络错误！"+textStatus);
                    }
                })
                layer.close(index);
            });
        })
    }


    function articleDel(id){
        layer.confirm('确认删除此记录?', {icon: 3, title: '提示'}, function (index) {
            //do something
            $.ajax({
                url:"${contextPath}/BG/feedbackType/delete",
                dataType:"text",
                data:{
                    "feedbackTypeId":id
                },
                success:function (data) {
                    if(data=="success"){
                        initTable();
                    }else{
                        layer.alert("删除失败!")
                    }
                },
                error:function () {
                    layer.alert("网络错误！")
                }
            })

            layer.close(index);
        })

    }
</script>
</body>
</html>
