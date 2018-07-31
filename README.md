# Introduction
distribute-scheduler是yl同学基于zookeeper和netty自主研发的一款分布式任务调度系统，调度目前支持命令方式和jar方式提交任务，系统提供了任务的最优调度，重试，监控，告警等一些列功能。

# Component
* distribute-scheduler-client
client端主要负责接收用户提交的任务，根据任务的所属的pool从resource manager中选择最优的机器去提交任务，任务失败后会根据用户配置的重试次数选择其它机器重新提交任务；监听改pool下面机器的变化，如果有机器掉线，关闭客户端到该机器的连接池

* distribute-scheduler-server
server端在启动的时候首先会把自己注册到zookeeper上，其次会启动一个jetty server,jetty server主要用于用户获取任务的输出信息和错误信息
* distribute-scheduler-resoucemanger
resourcemanager主要负责管理机器的资源，暴露jmx信息给外部，为client端提供最优的机器选择策略，目前支持3中策略：1.最优资源策略，提供任务pool中内存最     多的一台机器给客户端。2.最小任务策略，提供pool中机器任务数最少的一台机器给客户端。3.随机策略，从pool中随机选择一台机器给客户端。

* distribute-scheduler-common
common提供了任务调度的bean和utils等相关类

* distribute-scheduler-core
core主要提供了resourcemanager的rpc调用，rpc的序列化方式，zookeeper,jersey等一些帮助类
