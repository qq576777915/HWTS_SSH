<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/11/17
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html class=" JS no-touch" lang="zh" style=""><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>用户中心</title>
    <meta name="applicable-device" content="pc,mobile">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-transform">
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.css">

    <!-- Stylesheets -->
    <!-- Bootstrap is included in its original form, unaltered -->
    <link rel="stylesheet" href="./css/bootstrap.min.css">

    <!-- Related styles of various icon packs and plugins -->
    <link rel="stylesheet" href="./css/plugins.css">

    <!-- The main stylesheet of this template. All Bootstrap overwrites are defined in here -->
    <link rel="stylesheet" href="./css/main.css">

    <!-- sweealert CSS & JS in here -->
    <link href="https://cdn.bootcss.com/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/sweetalert/1.1.3/sweetalert.min.js"></script>


</head>
<body>
<!-- Page Wrapper -->
<div id="page-wrapper" class="page-loading-off">
    <!-- Preloader -->
    <div class="preloader">
        <div class="inner">
            <!-- Animation spinner for all modern browsers -->
            <div class="preloader-spinner themed-background hidden-lt-ie10"></div>

            <!-- Text for IE9 -->
            <h3 class="text-primary visible-lt-ie10"><strong>Loading..</strong></h3>
        </div>
    </div>
    <!-- END Preloader -->

    <!-- Page Container -->
    <div id="page-container" class="header-fixed-top sidebar-visible-lg-full">
        <!-- Main Sidebar -->
        <jsp:include page="head_teacher.jsp"/>
        <!-- END Main Sidebar -->

        <!-- Main Container -->
        <div id="main-container">
            <!-- Header -->
            <header class="navbar navbar-inverse navbar-fixed-top navbar-glass">
                <!-- Left Header Navigation -->
                <ul class="nav navbar-nav-custom">
                    <!-- Main Sidebar Toggle Button -->
                    <li>
                        <a href="javascript:void(0)" onclick="App.sidebar('toggle-sidebar');this.blur();">
                            <i class="fa fa-ellipsis-v fa-fw animation-fadeInRight" id="sidebar-toggle-mini"></i>
                            <i class="fa fa-bars fa-fw animation-fadeInRight" id="sidebar-toggle-full"></i>菜单
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="javascript:history.go(-1);">
                            <i class="fa fa-reply fa-fw animation-fadeInRight"></i> 返回
                        </a>
                    </li>
                    <!-- END Main Sidebar Toggle Button -->
                </ul>
                <!-- END Left Header Navigation -->

                <!-- Right Header Navigation -->
                <ul class="nav navbar-nav-custom pull-right">
                    <!-- User Dropdown -->
                    <li class="dropdown">
                        <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                            <img src="./images/Logo.png" alt="logo">
                        </a>
                    </li>
                    <!-- END User Dropdown -->
                </ul>
                <!-- END Right Header Navigation -->
            </header>
            <!-- END Header -->
            <!-- Page content -->

            <div id="page-content" style="min-height: 640px;">
                <div class="content-header">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="header-section">
                                <h2>用户中心</h2>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">

                    <div class="col-sm-12 col-md-12 col-lg-6">
                        <!-- Stats User Widget -->
                        <div class="widget">
                            <div class="widget-content themed-background text-light-op">
                                <small>欢迎您：</small>
                                ${name}
                            </div>
                            <div class="widget-content border-bottom text-center">
                                <img src="./images/Logo.png" alt="avatar"
                                     class="img-circle img-thumbnail img-thumbnail-avatar-2x">
                                <h2 class="widget-heading h3 text-dark">教师信息</h2>
                                <div class="widget-content themed-background-muted text-dark-op text-center">
                                    所属系(院)：${department}
                                    <br>
                                    所属教研室：${staffRoom}
                                    <br>
                                    绑定邮箱：${email} <br>
                                    今日日期：${time}
                                </div>
                            </div>
                            <div class="widget-content widget-content-full-top-bottom">
                                <div class="row text-center">
                                    <div class="col-xs-4 push-inner-top-bottom border-right">
                                        <a href="/taskInfo_list">
                                            <h3 class="widget-heading">已接受<br>
                                                <small>${countWork} </small>
                                            </h3>
                                        </a>
                                    </div>
                                    <div class="col-xs-4 push-inner-top-bottom border-right">
                                        <a href="/taskInfo_list">
                                            <h3 class="widget-heading">已完成<br>
                                                <small>${OKWork}</small>
                                            </h3>
                                        </a>
                                    </div>
                                    <div class="col-xs-4 push-inner-top-bottom">
                                        <a href="/taskInfo_list">
                                            <h3 class="widget-heading">待完成<br>
                                                <small>${NOWork}</small>
                                            </h3>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- END Stats User Widget -->
                    </div>
                    <div class="col-sm-12 col-md-12 col-lg-6">
                        <!-- Chart Widget -->
                        <div class="widget">
                            <div class="widget-content themed-background text-light-op">
                                <i class="fa fa-bullhorn"></i>&nbsp;网站公告
                            </div>
                            <div class="widget-content border-bottom text-dark">
                                <div class="form-group " style="margin-left: 1px;margin-right: 1px;margin-top:-10px;">
                                    <h2><textarea type="text" class="form-control" rows="17" id="notice-text"
                                                  style="overflow:auto;background-attachment:fixed;background-repeat:no-repeat;border-style:solid;border-color:#FFFFFF;">${notice}</textarea>
                                    </h2>
                                </div>
                            </div>

                            <%--<div class="widget-content border-bottom">--%>
                            <%--网站公告--%>
                            <%--</div>--%>
                            <%--<div class="widget-content border-bottom">--%>

                        </div>


                    </div>
                    <div class="col-sm-12 col-md-12 col-lg-12">
                        <!-- Chart Widget -->
                        <div class="widget">
                            <div class="widget-content themed-background text-light-op">
                                <i class="fa fa-fw fa-pencil"></i>&nbsp;我的待做任务（未完成任务）
                            </div>
                            <div class="widget-content border-bottom text-dark">
                                <div class="form-group">
                                    <table class="table table-bordered">
                                        <tbody id="tbod">
                                        <tr>
                                            <td align="center"><span style="color:silver;"><b>时间(年/月/日)</b></span></td>
                                            <td align="center"><span style="color:silver;"><b>任务名称</b></span></td>
                                            <td align="center"><span style="color:silver;"><b>发布人</b></span></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- END Chart Widget -->
                </div>
                <%--<div class="col-sm-12 col-md-12 col-lg-6">--%>
                <%--<!-- Partial Responsive Block -->--%>
                <%--<div class="widget" style="min-height: 337px;">--%>
                <%--<!-- Partial Responsive Title -->--%>
                <%--<div class="widget-content border-bottom text-dark">--%>
                <%--<span class="pull-right text-muted"></span>--%>
                <%--任务与操作列表--%>
                <%--</div>--%>
                <%--<!-- END Partial Responsive Title -->--%>

                <%--<!-- Partial Responsive Content -->--%>
                <%--<table class="table table-striped table-bordered table-vcenter">--%>
                <%--<thead>--%>
                <%--<tr>--%>
                <%--<th>任务</th>--%>
                <%--<th class="hidden-sm hidden-xs">状态</th>--%>
                <%--<th class="text-center">操作</th>--%>
                <%--</tr>--%>
                <%--</thead>--%>
                <%--<tbody>--%>
                <%--<tr>--%>
                <%--<td>查看我的工作进度</td>--%>
                <%--<td class="hidden-sm hidden-xs"><label class="label label-success">开放</label></td>--%>
                <%--<td class="text-center">--%>
                <%--<a href="/userplan" class="btn btn-xs btn-info" target="_blank">查看</a>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                <%--<td>工作进度以excel表导出</td>--%>
                <%--<td class="hidden-sm hidden-xs"><label class="label label-success">开放</label></td>--%>
                <%--<td class="text-center">--%>
                <%--<a href="./" class="btn btn-xs btn-info" target="_blank">下载</a>--%>
                <%--</td>--%>
                <%--</tr>--%>
                <%--</tbody>--%>
                <%--</table>--%>
                <%--<!-- END Partial Responsive Content -->--%>
                <%--</div>--%>
                <%--<!-- END Partial Responsive Block -->--%>
                <%--</div>--%>
            </div>
        </div>

        <div class="text-center push-bit-top-bottom visible-xs">
            <small class="help-block">Made BY：<br>RJXH - 移软分部</small>
        </div>
        <!-- END Page Content -->
    </div>
    <!-- END Main Container -->
</div>
<!-- END Page Container -->
</div>
<!-- END Page Wrapper -->

<!-- jQuery, Bootstrap, jQuery plugins and Custom JS code -->
<script src="./JS/jquery-2.2.0.min.js"></script>
<script src="./JS/app.js"></script>
<script src="//lib.baomitu.com/layer/2.3/layer.js"></script>
<script src="./JS/user.js"></script>
</body>
</html>