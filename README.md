#JeeRTD简介
  JeeRTD是官方分布式微服务集群开源框架，使用spring mvc、mybatis、alibaba dubbo 分布式、微服务、集群、代码生成（前端界面、底层代码、dubbo、微服务的生成）等核心技术。

 DUBBO是一个分布式服务框架，致力于提供高性能和透明化的RPC远程服务调用方案，分布式微服务集群核心框架，每天为2,000+个服务提供3,000,000,000+次访问量支持。

 微服务是利用云平台开发企业应用程序的最新技术，它是小型、轻量和过程驱动的组件。微服务适合设计可扩展、易于维护的应用程序。它可以使开发更容易，还能使资源得到最佳利用。

#分布式集群
  在大规模服务化之前，应用可能只是通过RMI或Hessian等工具，简单的暴露和引用远程服务，通过配置服务的URL地址进行调用，通过F5等硬件进行负载均衡。

(1) 当服务越来越多时，服务URL配置管理变得非常困难，F5硬件负载均衡器的单点压力也越来越大。

此时需要一个服务注册中心，动态的注册和发现服务，使服务的位置透明。

并通过在消费方获取服务提供方地址列表，实现软负载均衡和Failover，降低对F5硬件负载均衡器的依赖，也能减少部分成本。

(2) 当进一步发展，服务间依赖关系变得错踪复杂，甚至分不清哪个应用要在哪个应用之前启动，架构师都不能完整的描述应用的架构关系。

这时，需要自动画出应用间的依赖关系图，以帮助架构师理清理关系。

(3) 接着，服务的调用量越来越大，服务的容量问题就暴露出来，这个服务需要多少机器支撑？什么时候该加机器？

为了解决这些问题，第一步，要将服务现在每天的调用量，响应时间，都统计出来，作为容量规划的参考指标。

其次，要可以动态调整权重，在线上，将某台机器的权重一直加大，并在加大的过程中记录响应时间的变化，直到响应时间到达阀值，记录此时的访问量，再以此访问量乘以机器数反推总容量。


#微服务功能包括：
  支持REST风格远程调用（HTTP + JSON/XML)：基于非常成熟的JBoss RestEasy框架，在dubbo中实现了REST风格（HTTP + JSON/XML）的远程调用，以显著简化企业内部的跨语言交互，同时显著简化企业对外的Open API、无线API甚至AJAX服务端等等的开发。

事实上，这个REST调用也使得Dubbo可以对当今特别流行的“微服务”架构提供基础性支持。 另外，REST调用也达到了比较高的性能，在基准测试下，HTTP + JSON与Dubbo 2.x默认的RPC协议（即TCP + Hessian2二进制序列化）之间只有1.5倍左右的差距，详见下文的基准测试报告。

支持基于Kryo和FST的Java高效序列化实现：基于当今比较知名的Kryo和FST高性能序列化库，为Dubbo 默认的RPC协议添加新的序列化实现，并优化调整了其序列化体系，比较显著的提高了Dubbo RPC的性能，详见下图和文档中的基准测试报告。

支持基于嵌入式Tomcat的HTTP remoting体系：基于嵌入式tomcat实现dubbo的 HTTP remoting体系（即dubbo-remoting-http），用以逐步取代Dubbo中旧版本的嵌入式Jetty，可以显著的提高REST等的远 程调用性能，并将Servlet API的支持从2.5升级到3.1。（注：除了REST，dubbo中的WebServices、Hessian、HTTP Invoker等协议都基于这个HTTP remoting体系）。

RepositoryORM仓库,提供ORM接口和多种实现,可进行配置实现。RepositoryRedis仓库,提供Redis接口和多种实现,可进行配置实现。RepositoryService仓库,提供Service接口和多种实现,可进行配置实现。

#技术选型
1.	使用目前流行的多种web技术，包括spring mvc、mybatis。

2.	alibaba dubbo 分布式、微服务、集群、zookeper

3.	代码生成（前端界面、底层代码、dubbo、微服务的生成）

4.	RepositoryORM仓库,提供ORM接口和多种实现,可进行配置实现。

5.	RepositoryRedis仓库,提供Redis接口和多种实现,可进行配置实现。

6.	RepositoryService仓库,提供Service接口和多种实现,可进行配置实现。

#代码生成器
1.	spring mvc
2.	mybatis
3.	alibaba dubbo
4.	微服务
5.	集群
6.	前端界面

#如何交流、反馈、参与贡献？

JeeRTD官方QQ群：328910546

GitHub：https://github.com/HuangBingGui/JeeRTD.git

开源中国：http://git.oschina.net/guanshijiehnan/JeeRTD



