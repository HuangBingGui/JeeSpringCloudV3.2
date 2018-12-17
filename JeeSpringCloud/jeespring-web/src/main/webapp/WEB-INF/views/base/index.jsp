<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>${fns:getConfig('productName')}-${systemMode}-${version}</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="/jeeSpringStatic/bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="/jeeSpringStatic/bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <!--link rel="stylesheet" href="/jeeSpringStatic/bower_components/Ionicons/css/ionicons.min.css"-->
  <!-- jvectormap -->
  <!--link rel="stylesheet" href="/jeeSpringStatic/bower_components/jvectormap/jquery-jvectormap.css"-->
  <!-- Theme style -->
  <link rel="stylesheet" href="/jeeSpringStatic/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="/jeeSpringStatic/dist/css/skins/_all-skins.min.css">
  <link rel="stylesheet" href="/jeeSpringStatic/dist/css/app_iframe.css">
  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <!--link rel="stylesheet"
        href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic"-->
</head>
<body class="hold-transition fixed sidebar-collapse skin-blue-light sidebar-mini">
<div class="wrapper">

  <header class="main-header">

    <!-- Logo -->
    <a class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><img id="logo" class="img-circle" src="/staticViews/index/images/flat-avatar1.png">${fns:getConfig('productName')}</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><img id="logo" class="img-circle" src="/staticViews/index/images/flat-avatar1.png">${fns:getConfig('productName')}</span>
    </a>

    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
        ${systemMode}-${version}
      </a>
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li>
            <a href="/cms" target="_blank">
              <i class="fa fa-home"></i>
              <span class="hidden-xs">网站首页</span>
            </a>
          </li>
          <!-- Messages: style can be found in dropdown.less-->
          <li class="dropdown messages-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
              <i class="fa fa-envelope-o"></i>
              <span class="label label-success">4</span>
            </a>
            <ul class="dropdown-menu">
              <li class="header">你有 4 条消息</li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                  <li><!-- start message -->
                    <a href="#">
                      <div class="pull-left">
                        <img src="${ctxJeeSpringStatic}/dist/img/user2-160x160.jpg" class="img-circle" alt="用户图像">
                      </div>
                      <h4>
                        支持团队
                        <small><i class="fa fa-clock-o"></i> 5 分钟前</small>
                      </h4>
                      <p>为什么不购买一个高级模板主题？</p>
                    </a>
                  </li>
                  <!-- end message -->
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="${ctxJeeSpringStatic}/dist/img/user3-128x128.jpg" class="img-circle" alt="用户图像">
                      </div>
                      <h4>
                        AdminLTE 设计团队
                        <small><i class="fa fa-clock-o"></i> 2 小时前</small>
                      </h4>
                      <p>为什么不购买一个高级模板主题？</p>
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="${ctxJeeSpringStatic}/dist/img/user4-128x128.jpg" class="img-circle" alt="用户图像">
                      </div>
                      <h4>
                        Developers
                        <small><i class="fa fa-clock-o"></i> 今天</small>
                      </h4>
                      <p>为什么不购买一个高级模板主题？</p>
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="${ctxJeeSpringStatic}/dist/img/user3-128x128.jpg" class="img-circle" alt="用户图像">
                      </div>
                      <h4>
                        销售部门
                        <small><i class="fa fa-clock-o"></i> 昨天</small>
                      </h4>
                      <p>为什么不购买一个高级模板主题？</p>
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <div class="pull-left">
                        <img src="${ctxJeeSpringStatic}/dist/img/user4-128x128.jpg" class="img-circle" alt="用户图像">
                      </div>
                      <h4>
                        评估人员
                        <small><i class="fa fa-clock-o"></i> 2 天前</small>
                      </h4>
                      <p>为什么不购买一个高级模板主题？</p>
                    </a>
                  </li>
                </ul>
              </li>
              <li class="footer"><a href="#">查看所有消息</a></li>
            </ul>
          </li>
          <!-- Notifications: style can be found in dropdown.less -->
          <li class="dropdown notifications-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
              <i class="fa fa-bell-o"></i>
              <span class="label label-warning">10</span>
            </a>
            <ul class="dropdown-menu">
              <li class="header">你有 10 条通知</li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                  <li>
                    <a href="#">
                      <i class="fa fa-users text-aqua"></i> 今日 5 名新进会员加入
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-warning text-yellow"></i> 很长描述可能不适用于页面，可能会导致设计问题
                      ，可能会导致设计问题
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-users text-red"></i> 5 名新成员加入
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-shopping-cart text-green"></i> 25 条销售记录
                    </a>
                  </li>
                  <li>
                    <a href="#">
                      <i class="fa fa-user text-red"></i> 你修改了用户名
                    </a>
                  </li>
                </ul>
              </li>
              <li class="footer"><a href="#">查看所有</a></li>
            </ul>
          </li>
          <!-- Tasks: style can be found in dropdown.less -->
          <li class="dropdown tasks-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
              <i class="fa fa-flag-o"></i>
              <span class="label label-danger">9</span>
            </a>
            <ul class="dropdown-menu">
              <li class="header">你有 9 项任务</li>
              <li>
                <!-- inner menu: contains the actual data -->
                <ul class="menu">
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        设计一些按钮
                        <small class="pull-right">20%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">20% 完成</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        创建一个漂亮的主题
                        <small class="pull-right">40%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-green" style="width: 40%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">40% 完成</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        我需要做的一些任务
                        <small class="pull-right">60%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-red" style="width: 60%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">60% 完成</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                  <li><!-- Task item -->
                    <a href="#">
                      <h3>
                        制作漂亮的过度效果
                        <small class="pull-right">80%</small>
                      </h3>
                      <div class="progress xs">
                        <div class="progress-bar progress-bar-yellow" style="width: 80%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                          <span class="sr-only">80% 完成</span>
                        </div>
                      </div>
                    </a>
                  </li>
                  <!-- end task item -->
                </ul>
              </li>
              <li class="footer">
                <a href="#">查看所有任务</a>
              </li>
            </ul>
          </li>
          <!-- User Account: style can be found in dropdown.less -->
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="${fns:getUser().photo }" class="user-image" alt="User Image">
              <span class="hidden-xs">${fns:getUser().name}</span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="${fns:getUser().photo }" class="img-circle" alt="User Image">

                <p>
                  ${fns:getUser().name} - ${fns:getUser().roleNames}
                  <small>Member since Nov. 2012</small>
                </p>
              </li>
              <!-- Menu Body -->
              <li class="user-body">
                <div class="row">
                  <div class="col-xs-4 text-center">
                    <a href="#">Followers</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Sales</a>
                  </div>
                  <div class="col-xs-4 text-center">
                    <a href="#">Friends</a>
                  </div>
                </div>
                <!-- /.row -->
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-left">
                  <a href="#" class="btn btn-default btn-flat" onclick="addTabs({id: '个人中心',title: '个人中心',close: true,url: 'sys/user/info',urlType: 'http'})">
                    <!--Profile|-->个人中心
                  </a>
                </div>
                <div class="pull-right">
                  <a href="/admin/logout" class="btn btn-default btn-flat"><!--Sign out|-->退出登录</a>
                </div>
              </li>
              <li><a onclick="addTabs({id: '修改头像',title: '修改头像',close: true,url: '${ctx}/sys/user/imageEdit',urlType: 'http'})">
                <i class="fa fa-user"></i> 修改头像</a>
              </li>
              <li>
                <a onclick="addTabs({id: '修改密码',title: '修改密码',close: true,url: '${ctx}/sys/user/modifyPwd',urlType: 'http'})">
                  <i class="fa fa-key"></i> 修改密码</a>
              </li>
              <li class="divider"></li>
              <li><a href="#" onclick="cancleRedis()"><i class="fa fa-qrcode"></i> <span id="cancleRedis">清除缓存 </span></a></li>
              <li><a href="#" onclick="cancleShiroRedis()"><i class="fa fa-qrcode"></i> <span id="cancleShiroRedis">清除单点登录缓存 </span></a></li>
              <li><a href="#" ><i class="fa fa-qrcode"></i> <span id="userOnlineAmount">在线人数 </span></a></li>
              <li><a href="#" ><i class="fa fa-qrcode"></i> <span id="getApiTimeLimi">访问次数 </span></a></li>
              <li><a href="#" ><i class="fa fa-qrcode"></i> <span id="getApiTime">调用次数 </span></a></li>
              <li><a href="#" ><i class="fa fa-qrcode"></i> <span id="getExpire">缓存有效时间 </span></a></li>
              <li><a href="#" ><i class="fa fa-qrcode"></i> <span id="getExpireShiro">单点登录缓存有效时间 </span></a></li>
              <li class="mt10"></li>
            </ul>
          </li>
          <!-- Control Sidebar Toggle Button -->
          <li>
            <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
          </li>
        </ul>
      </div>

    </nav>
  </header>
  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">
      <!-- Sidebar user panel -->
      <div class="user-panel">
        <div class="pull-left image">
          <img src="${fns:getUser().photo }" class="img-circle" alt="User Image">
        </div>
        <div class="pull-left info">
          <p>${fns:getUser().name}-${fns:getUser().roleNames}</p>
          <a href="#"><i class="fa fa-circle text-success"></i> 在线/Online</a>
        </div>
      </div>
      <!-- search form -->
      <div class="sidebar-form">
        <div class="input-group">
          <input type="text" name="q" class="form-control" placeholder="搜索..."><!--Search-->
          <span class="input-group-btn">
                <button type="submit" name="search" id="search-btn" class="btn btn-flat"  onclick="search_menu()">
                  <i class="fa fa-search"></i>
                </button>
              </span>
        </div>
      </div>
      <!-- /.search form -->
      <!-- sidebar menu: : style can be found in sidebar.less -->
      <ul class="sidebar-menu" data-widget="tree">
        <li class="header"><!--MAIN NAVIGATION-->主导航</li>
        <t:menu  menu="${fns:getTopMenu()}"></t:menu>
        <!--li><a href="#"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li-->
      </ul>
    </section>
    <!-- /.sidebar -->
  </aside>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
        <!--bootstrap tab风格 多标签页-->
        <div class="content-tabs">
            <button class="roll-nav roll-left tabLeft" onclick="scrollTabLeft()">
                <i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs menuTabs tab-ui-menu" id="tab-menu">
                <div class="page-tabs-content" style="margin-left: 0px;">

                </div>
            </nav>
            <button class="roll-nav roll-right tabRight" onclick="scrollTabRight()">
                <i class="fa fa-forward" style="margin-left: 3px;"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown tabClose" data-toggle="dropdown">
                    页签操作<i class="fa fa-caret-down" style="padding-left: 3px;"></i>
                </button>
                <ul class="dropdown-menu dropdown-menu-right" style="min-width: 128px;">
                    <li><a class="tabReload" href="javascript:refreshTab();">刷新当前</a></li>
                    <li><a class="tabCloseCurrent" href="javascript:closeCurrentTab();">关闭当前</a></li>
                    <li><a class="tabCloseAll" href="javascript:closeOtherTabs(true);">全部关闭</a></li>
                    <li><a class="tabCloseOther" href="javascript:closeOtherTabs();">除此之外全部关闭</a></li>
                </ul>
            </div>
            <button class="roll-nav roll-right fullscreen" onclick="App.handleFullScreen()"><i
                    class="fa fa-arrows-alt"></i></button>
        </div>
        <div class="content-iframe " style="background-color: #ffffff; ">
            <div class="tab-content " id="tab-content">

            </div>
        </div>
  </div>
  <!-- /.content-wrapper -->

  <footer class="main-footer">
    <div class="pull-right hidden-xs">
      <b>Version</b> 2.4.0
    </div>
    <strong>Copyright &copy; 2014-2018 <a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud">JeeSpring</a>.</strong> All rights
    reserved.
  </footer>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-light">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">近期活动/Recent Activity</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>
              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Langdon's 生日/Langdon's Birthday</h4>
                <p>23岁生日，在4月24日/Will be 23 on April 24th</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-user bg-yellow"></i>
              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Frodo 更新了个人资料/Frodo Updated His Profile</h4>
                <p>新电话 +1(800)555-1234/New phone +1(800)555-1234</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>
              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Nora 加入邮件列表/Nora Joined Mailing List</h4>
                <p>nora@example.com</p>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <i class="menu-icon fa fa-file-code-o bg-green"></i>
              <div class="menu-info">
                <h4 class="control-sidebar-subheading">254 计划任务执行/Cron Job 254 Executed</h4>
                <p>执行时间5秒/Execution time 5 seconds</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <h3 class="control-sidebar-heading">任务进度/Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                自定义模板设计/Custom Template Design
                <span class="label label-danger pull-right">70%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                更新简历/Update Resume
                <span class="label label-success pull-right">95%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-success" style="width: 95%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                Laravel积分/Laravel Integration
                <span class="label label-warning pull-right">50%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
              </div>
            </a>
          </li>
          <li>
            <a href="javascript:void(0)">
              <h4 class="control-sidebar-subheading">
                后端框架/Back End Framework
                <span class="label label-primary pull-right">68%</span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->

      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">常规设置/General Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              报告面板使用/Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              有关此常规设置选项信息/Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              允许邮件重定向/Allow mail redirect
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              其他选项是可用的/Other sets of options are available
            </p>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              在帖子中显示作者姓名/Expose author name in posts
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              允许用户在博客帖子中显示自己的名字/Allow the user to show his name in blog posts
            </p>
          </div>
          <!-- /.form-group -->

          <h3 class="control-sidebar-heading">聊天设置/Chat Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              显示我在线/Show me as online
              <input type="checkbox" class="pull-right" checked>
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              关闭通知/Turn off notifications
              <input type="checkbox" class="pull-right">
            </label>
          </div>
          <!-- /.form-group -->

          <div class="form-group">
            <label class="control-sidebar-subheading">
              删除聊天记录/Delete chat history
              <a href="javascript:void(0)" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
            </label>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>

</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->
<script src="/jeeSpringStatic/bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="/jeeSpringStatic/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- FastClick -->
<script src="/jeeSpringStatic/bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="/jeeSpringStatic/dist/js/adminlte.min.js"></script>
<!-- Sparkline -->
<script src="/jeeSpringStatic/bower_components/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
<!-- SlimScroll -->
<script src="/jeeSpringStatic/bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- ChartJS -->
<script src="/jeeSpringStatic/bower_components/chart.js/Chart.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<!--script src="dist/js/pages/dashboard2.js"></script-->
<!-- AdminLTE for demo purposes -->
<script src="/jeeSpringStatic/dist/js/demo.js"></script>
<script src="/jeeSpringStatic/dist/js/app_iframe.js"></script>
<script src="/jeeSpringStatic/plugs/layer-v3.1.1/layer/layer.js"></script>
<script src="/staticViews/index/index.js"></script>
<%@ include file="/WEB-INF/views/include/im.jsp"%>
</body>
</html>
