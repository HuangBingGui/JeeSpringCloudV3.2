#JeeSpringCloud

演示版地址：http://yocity.imwork.net:10858/admin?login<br>
官网地址：https://jeespring.icoc.bz/<br>
论坛社区：<a href="https://jeespring.kf5.com/hc/community/topic/" target="_blank">https://jeespring.kf5.com/hc/community/topic/</a><br>

帮忙标星、点赞，申请码云最有价值开源项
JeeSpring开源中国：<a href="https://gitee.com/JeeHuangBingGui/jeeSpringCloud" target="_blank">https://gitee.com/JeeHuangBingGui/jeeSpringCloud</a><br>

有部分功能会提示：演示版启用为系统能正常演示，暂时不允许操作！<br>
模块包含定时任务调度、服务器监控、平台监控、异常邮件监控、服务器Down机邮件监控、平台设置、开发平台、邮件监控、图表监控、地图监控、单点登录、Redis分布式高速缓存、<br>
ActiveMQ队列、会员、营销、在线用户、日志、在线人数、访问次数、调用次数、直接集群、接口文档、生成模块、代码实例、安装视频、教程文档、dubbo（待开发）、springCloud、<br>
RedisMQ队列（待开发）、代码生成(单表、主附表、树表、列表和表单、增删改查云接口、redis高速缓存对接代码、图表统计、地图统计、vue.js)、工作流（待开发）<br>

JeeSpringCloud官方QQ群：328910546
JeeSpringCloud官方QQ群(VIP)：558699173
群内含各种工具、文档、视频教程下载

<img alt="微服务" class="md_relative_url" src="./document/001.png">
<img alt="微服务" class="md_relative_url" src="./document/RepositoryService.jpg">
<img alt="微服务" class="md_relative_url" src="./document/login6.png">
<img alt="微服务" class="md_relative_url" src="./document/login1.png">
<img alt="微服务" class="md_relative_url" src="./document/login2.png">
<img alt="微服务" class="md_relative_url" src="./document/login4.png">
<img alt="微服务" class="md_relative_url" src="./document/login5.png">
<img alt="微服务" class="md_relative_url" src="./document/07.png">
<img alt="微服务" class="md_relative_url" src="./document/01.png">
<img alt="微服务" class="md_relative_url" src="./document/02.png">
<img alt="微服务" class="md_relative_url" src="./document/06.png">
<img alt="微服务" class="md_relative_url" src="./document/05.png">
<img alt="微服务" class="md_relative_url" src="./document/03.png">
<img alt="微服务" class="md_relative_url" src="./document/04.png">

 JeeSpringCloud基于SpringBoot+SpringMVC+Mybatis+Redis+SpringCloud+Vue.js微服务分布式代码生成的敏捷开发系统架构。项目代码简洁,注释丰富,上手容易,还同时集中分布式、微服务,同时包含许多基础模块(用户管理,角色管理,部门管理,字典管理等10个模块。成为大众认同、大众参与、成就大众、大众分享的开发平台。JeeSpring官方qq群(328910546)。代码生成前端界面、底层代码（spring mvc、mybatis、Spring boot、Spring Cloud、微服务的生成）、安全框架、视图框架、服务端验证、任务调度、持久层框架、数据库连接池、缓存框架、日志管理、IM等核心技术。努力用心为大中小型企业打造全方位J2EE企业级平台ORM/Redis/Service仓库开发解决方案。一个RepositoryService仓库就直接实现dubbo、微服务、基础服务器对接接口和实现。

 努力用心为大中小型企业打造全方位J2EE企业级平台开发解决方案。


 Spring Boot/Spring cloud微服务是利用云平台开发企业应用程序的最新技术，它是小型、轻量和过程驱动的组件。微服务适合设计可扩展、易于维护的应用程序。它可以使开发更容易，还能使资源得到最佳利用。

# 分布式/集群(zookeeper)
  在大规模服务化之前，应用可能只是通过RMI或Hessian等工具，简单的暴露和引用远程服务，通过配置服务的URL地址进行调用，通过F5等硬件进行负载均衡。

(1) 当服务越来越多时，服务URL配置管理变得非常困难，F5硬件负载均衡器的单点压力也越来越大。

此时需要一个服务注册中心，动态的注册和发现服务，使服务的位置透明。

并通过在消费方获取服务提供方地址列表，实现软负载均衡和Failover，降低对F5硬件负载均衡器的依赖，也能减少部分成本。

(2) 当进一步发展，服务间依赖关系变得错踪复杂，甚至分不清哪个应用要在哪个应用之前启动，架构师都不能完整的描述应用的架构关系。

这时，需要自动画出应用间的依赖关系图，以帮助架构师理清理关系。

(3) 接着，服务的调用量越来越大，服务的容量问题就暴露出来，这个服务需要多少机器支撑？什么时候该加机器？

为了解决这些问题，第一步，要将服务现在每天的调用量，响应时间，都统计出来，作为容量规划的参考指标。

其次，要可以动态调整权重，在线上，将某台机器的权重一直加大，并在加大的过程中记录响应时间的变化，直到响应时间到达阀值，记录此时的访问量，再以此访问量乘以机器数反推总容量。


# 微服务/集群(nignx)
  支持REST风格远程调用（HTTP + JSON/XML)：基于非常成熟的Spring Boot框架，在Spring Boot Spring Cloud中实现了REST风格（HTTP + JSON/XML）的远程调用，以显著简化企业内部的跨语言交互，同时显著简化企业对外的Open API、无线API甚至AJAX服务端等等的开发。

事实上，这个REST调用也使得Dubbo可以对当今特别流行的“微服务”架构提供基础性支持。 另外，REST调用也达到了比较高的性能，在基准测试下，HTTP + JSON默认的RPC协议（即TCP + Hessian2二进制序列化）之间只有1.5倍左右的差距，详见下文的基准测试报告。



# ORM/Redis/Service仓库

RepositoryORM仓库,提供ORM接口和多种实现,可进行配置实现。

RepositoryRedis仓库,提供Redis接口和多种实现,可进行配置实现。可以配置调用单机、redis、云redis对接。

RepositoryService仓库,提供Service接口和多种实现,可进行配置实现。一个RepositoryService仓库就直接实现dubbo、微服务、基础服务器对接接口和实现。 


#技术选型
1.	使用目前流行的多种web技术，包括spring boot spring mvc、mybatis、Vue.js。

2.	Spring cloud 分布式、微服务、集群、zookeper、nignx。

3.	代码生成（前端界面、底层代码、微服务的生成）。

4.	RepositoryORM仓库,提供ORM接口和多种实现,可进行配置实现。

5.	RepositoryRedis仓库,提供Redis接口和多种实现,可进行配置实现。可以配置调用单机、redis、云redis对接。

6.	RepositoryService仓库,提供Service接口和多种实现,可进行配置实现。可以配置调用dubbo、微服务、基础服务器对接接口和实现。

# 代码生成器
1.	spring mvc/Vue.js
2.	控制层、服务层、数据访问层
2.	Redis
3.	mybatis
4.	alibaba dubbo
5.	微服务
6.	集群
7.	前端界面（增删改查）
8.	图表统计页面
9.	地图统计页面

版权声明
本软件使用 Apache License 2.0 协议，请严格遵照协议内容：

需要给代码的用户一份Apache Licence。
在延伸的代码中（修改和有源代码衍生的代码中）需要带有原来代码中的协议，商标，专利声明和其他原来作者规定需要包含的说明。
Apache Licence也是对商业应用友好的许可。使用者也可以在需要的时候修改代码来满足需要并作为开源或商业产品发布/销售
你可以二次包装出售，但还请保留文件中的版权和作者信息，并在你的产品说明中注明JeeSpring。
你可以以任何方式获得，你可以修改包名或类名，但还请保留文件中的版权和作者信息。

# 如何交流、反馈、参与贡献？

JeeSpringCloud官方QQ群：328910546

JeeSpringCloud官方QQ群(VIP)：558699173

官方QQ群(VIP)提供：

1、详细部署文档。

2、部署视频。

3、中级培训视频待定，包括代码生成、架构代码介绍。

4、高级培训视频待定，包括架构代码详解。

5、架构培训视频待定，包括架构详解、代码生成详解。


GitHub：

JeeSpring开源中国：https://gitee.com/JeeHuangBingGui/jeeSpringCloud


# 大众认同、大众参与、成就大众、大众分享的开发平台。

大众认同、大众参与、成就大众、大众分享的开发平台。
