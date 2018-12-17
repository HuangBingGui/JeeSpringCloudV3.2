<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/modules/cms/front/include/taglib.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <title>JeeSpringCloud</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta name="author" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <link rel="stylesheet" href="${ctxStaticView}/cms/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxStaticView}/cms/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxStaticView}/cms/css/magnific-popup.css">

    <link rel="stylesheet" href="${ctxStaticView}/cms/css/owl.theme.css">
    <link rel="stylesheet" href="${ctxStaticView}/cms/css/owl.carousel.css">

    <!-- MAIN CSS -->
    <link rel="stylesheet" href="${ctxStaticView}/cms/css/tooplate-style.css">

</head>
<body>

<!-- PRE LOADER -->
<!--div class="preloader" style="display:none">
    <div class="spinner">
        <span class="sk-inner-circle"></span>
    </div>
</div-->


<!-- MENU -->
<div class="navbar custom-navbar navbar-fixed-top" role="navigation">
    <div class="container">

        <!-- NAVBAR HEADER -->
        <div class="navbar-header">
            <button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon icon-bar"></span>
                <span class="icon icon-bar"></span>
                <span class="icon icon-bar"></span>
            </button>
            <!-- lOGO -->
            <a href="${ctx}/index-1${fns:getUrlSuffix()}" class="navbar-brand">JeeSpringCloud</a>
        </div>

        <!-- MENU LINKS -->
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="${ctx}/index-1${fns:getUrlSuffix()}" class="smoothScroll">首页</a></li>
                <c:forEach items="${fnc:getMainNavList(site.id)}" var="category" varStatus="status">
                    <c:if test="${status.index lt 6}">
                        <c:set var="menuCategoryId" value=",${category.id},"/>
                        <li>
                            <a href="${category.url}" class="smoothScroll">${category.name}</a>
                        </li>
                    </c:if></c:forEach>
            </ul>
        </div>

    </div>
</div>


<!-- HOME -->
<section id="home" class="parallax-section">
    <div class="overlay"></div>
    <div class="container">
        <div class="row">

            <div class="col-md-8 col-sm-12">
                <div class="home-text">
                    <c:set var="article" value="${fnc:getArticle('2')}"/>
                    <h1>${fns:abbr(article.title,28)}</h1>
                    <p>${fns:abbr(fns:replaceHtml(article.description),260)}</p>
                    <ul class="section-btn">
                        <a href="${article.url}" class="smoothScroll"><span data-hover="Discover More">查看详情</span></a>
                    </ul>
                </div>
            </div>

        </div>
    </div>

    <!-- Video -->
    <video controls autoplay loop muted>
        <source src="${ctxStaticView}/cms/videos/video.mp4" type="video/mp4">
        Your browser does not support the video tag.
    </video>
</section>


<!-- ABOUT -->
<section id="about" class="parallax-section">
    <div class="container">
        <div class="row">
            <div class="col-md-offset-1 col-md-10 col-sm-12">
                <div class="about-info">
                    <h1>案例</h1>
                    <h4>（一款免费开源的JAVA互联网云快速开发平台）微服务分布式代码生成的敏捷开发系统架构。</h4>
                </div>
            </div>
        </div>
    </div>
</section>


<!-- PROJECT -->
<section id="project" class="parallax-section">
    <div class="container">
        <div class="row">
            <div class="col-md-6 col-sm-6">
                <!-- PROJECT ITEM -->
                <div class="project-item">
                    <a href="${ctxStaticView}/cms/images/project-image1.jpg" class="image-popup">
                        <img src="${ctxStaticView}/cms/images/project-image1.jpg" class="img-responsive" alt="">

                        <div class="project-overlay">
                            <div class="project-info">
                                <h1>Beautiful Women</h1>
                                <h3>Click to pop up!</h3>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="tlinks">Collect from <a href="http://www.cssmoban.com/">网页模板</a></div>

            <div class="col-md-6 col-sm-6">
                <!-- PROJECT ITEM -->
                <div class="project-item">
                    <a href="${ctxStaticView}/cms/images/project-image2.jpg" class="image-popup">
                        <img src="${ctxStaticView}/cms/images/project-image2.jpg" class="img-responsive" alt="">

                        <div class="project-overlay">
                            <div class="project-info">
                                <h1>Nulla efficitur quam</h1>
                                <h3>Sed ligula accumsan</h3>
                            </div>
                        </div>
                    </a>
                </div>
            </div>

            <div class="col-md-6 col-sm-6">
                <!-- PROJECT ITEM -->
                <div class="project-item">
                    <a href="${ctxStaticView}/cms/images/project-image3.jpg" class="image-popup">
                        <img src="${ctxStaticView}/cms/images/project-image3.jpg" class="img-responsive" alt="">

                        <div class="project-overlay">
                            <div class="project-info">
                                <h1>Large Sea Wave</h1>
                                <h3>Nam feugiat dui in nisi</h3>
                            </div>
                        </div>
                    </a>
                </div>
            </div>
            <div class="col-md-6 col-sm-6">
                <!-- PROJECT ITEM -->
                <div class="project-item">
                    <a href="${ctxStaticView}/cms/images/project-image4.jpg" class="image-popup">
                        <img src="${ctxStaticView}/cms/images/project-image4.jpg" class="img-responsive" alt="">
                        <div class="project-overlay">
                            <div class="project-info">
                                <h1>Lorem ipsum dolor</h1>
                                <h3>Mollis aliquam faucibus urna</h3>
                            </div>
                        </div>
                    </a>
                </div>
            </div>

        </div>
    </div>
</section>


<!-- TEAM -->
<section id="team" class="parallax-section">
    <div class="container">
        <div class="row">

            <div class="col-md-offset-2 col-md-8 col-sm-12">
                <!-- SECTION TITLE -->
                <div class="section-title">
                    <h1>产品</h1>
                </div>
            </div>

            <div class="clearfix"></div>

            <div id="owl-team" class="owl-carousel">
                <div class="col-md-4 col-sm-4 item">
                    <div class="team-item">
                        <img src="${ctxStaticView}/cms/images/team-image1.jpg" class="img-responsive" alt="">
                        <div class="team-overlay">
                            <ul class="social-icon">
                                <li><a href="#" class="fa fa-twitter"></a></li>
                                <li><a href="#" class="fa fa-linkedin"></a></li>
                            </ul>
                        </div>
                    </div>
                    <p>Catherine Jann</p>
                    <h3>Head Designer</h3>
                </div>

                <div class="col-md-4 col-sm-4 item">
                    <div class="team-item">
                        <img src="${ctxStaticView}/cms/images/team-image2.jpg" class="img-responsive" alt="">
                        <div class="team-overlay">
                            <ul class="social-icon">
                                <li><a href="#" class="fa fa-instagram"></a></li>
                                <li><a href="#" class="fa fa-github"></a></li>
                                <li><a href="#" class="fa fa-facebook"></a></li>
                            </ul>
                        </div>
                    </div>
                    <p>Luke Wara</p>
                    <h3>Speciality Focus</h3>
                </div>

                <div class="col-md-4 col-sm-4 item">
                    <div class="team-item">
                        <img src="${ctxStaticView}/cms/images/team-image3.jpg" class="img-responsive" alt="">
                        <div class="team-overlay">
                            <ul class="social-icon">
                                <li><a href="#" class="fa fa-instagram"></a></li>
                                <li><a href="#" class="fa fa-dribbble"></a></li>
                                <li><a href="#" class="fa fa-behance"></a></li>
                            </ul>
                        </div>
                    </div>
                    <p>Mono Mana</p>
                    <h3>Art director</h3>
                </div>

                <div class="col-md-4 col-sm-4 item">
                    <div class="team-item">
                        <img src="${ctxStaticView}/cms/images/team-image4.jpg" class="img-responsive" alt="">
                        <div class="team-overlay">
                            <ul class="social-icon">
                                <li><a href="#" class="fa fa-twitter"></a></li>
                                <li><a href="#" class="fa fa-facebook"></a></li>
                                <li><a href="#" class="fa fa-envelope-o"></a></li>
                            </ul>
                        </div>
                    </div>
                    <p>Phway Phyu</p>
                    <h3>Designer in Chief</h3>
                </div>

                <div class="col-md-4 col-sm-4 item">
                    <div class="team-item">
                        <img src="${ctxStaticView}/cms/images/team-image1.jpg" class="img-responsive" alt="">
                        <div class="team-overlay">
                            <ul class="social-icon">
                                <li><a href="#" class="fa fa-twitter"></a></li>
                                <li><a href="#" class="fa fa-linkedin"></a></li>
                            </ul>
                        </div>
                    </div>
                    <p>Cherry Lynn</p>
                    <h3>Marketing Manager</h3>
                </div>
            </div>

        </div>
    </div>
</section>


<!-- CONTACT -->
<section id="contact" class="parallax-section">
    <div class="container">
        <div class="row">

            <div class="col-md-offset-3 col-md-6 col-sm-12">
                <div class="section-title">
                    <h1>联系我们</h1>
                </div>
            </div>

            <div class="clearfix"></div>

            <div class="col-md-offset-2 col-md-8 col-sm-12">
                <!-- CONTACT FORM HERE -->
                <form id="contact-form" action="#" method="get" role="form">

                    <!-- IF MAIL SENT SUCCESSFULLY -->
                    <h6 class="text-success">Your message has been sent successfully. </h6>

                    <!-- IF MAIL SENDING UNSUCCESSFULL -->
                    <h6 class="text-danger">E-mail must be valid and message must be longer than 1 character.</h6>

                    <div class="col-md-6 col-sm-6">
                        <input type="text" class="form-control" id="cf-name" name="cf-name" placeholder="Name">
                    </div>

                    <div class="col-md-6 col-sm-6">
                        <input type="email" class="form-control" id="cf-email" name="cf-email"
                               placeholder="Email Address">
                    </div>

                    <div class="col-md-12 col-sm-12">
                        <input type="text" class="form-control" id="cf-subject" name="subject" placeholder="Subject">
                        <textarea class="form-control" rows="5" id="cf-message" name="cf-message"
                                  placeholder="Message"></textarea>
                    </div>

                    <div class="col-md-offset-4 col-md-4 col-sm-offset-4 col-sm-4">
                        <div class="section-btn">
                            <button type="submit" class="form-control" id="cf-submit" name="submit"><span
                                    data-hover="Send Message">Send Message</span></button>
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</section>

<!-- FOOTER -->
<footer>
    <div class="container">
        <div class="row">

            <div class="col-md-5 col-sm-6">
                <h2>Digital Studio</h2>
                <p>321 Donec et justo id risus, Malesuada pharetra,<br> Tristique vestibulum,<br> Lorem ipsum dolor</p>


            </div>

            <div class="col-md-4 col-sm-6">
                <div class="footer-info">
                    <h2>Keep in touch</h2>
                    <p><a href="tel:010-090-0780">010-090-0780</a></p>
                    <p><a href="mailto:info@company.com">info@company.com</a></p>
                    <p><a href="#">Our Location</a></p>
                </div>
            </div>

            <div class="col-md-3 col-sm-12">

                <h2>About Us</h2>
                <p>Sed vestibulum posuere ante, eget blandit metus. Morbi sodales feugiat erat, et placerat sapien
                    suscipit ut.</p>
                <ul class="social-icon">
                    <li><a href="#" class="fa fa-twitter"></a></li>
                    <li><a href="#" class="fa fa-facebook"></a></li>
                    <li><a href="#" class="fa fa-instagram"></a></li>
                    <li><a href="#" class="fa fa-linkedin"></a></li>
                </ul>

            </div>
            <div class="clearfix"></div>
            <div class="col-md-12 col-sm-12">
                <div class="copyright-text">
                    <p>Copyright © 2018 JeeSpringCloud</p>
                </div>
            </div>

        </div>
    </div>
</footer>

<!-- SCRIPTS -->
<script src="${ctxStaticView}/cms/js/jquery.js"></script>
<script src="${ctxStaticView}/cms/js/bootstrap.min.js"></script>
<script src="${ctxStaticView}/cms/js/jquery.parallax.js"></script>
<script src="${ctxStaticView}/cms/js/owl.carousel.min.js"></script>
<script src="${ctxStaticView}/cms/js/jquery.magnific-popup.min.js"></script>
<script src="${ctxStaticView}/cms/js/magnific-popup-options.js"></script>
<script src="${ctxStaticView}/cms/js/modernizr.custom.js"></script>
<script src="${ctxStaticView}/cms/js/smoothscroll.js"></script>
<script src="${ctxStaticView}/cms/js/custom.js"></script>

</body>
</html>