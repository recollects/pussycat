# pussycat






####1模拟写一个简版的RPC框架.基于redis作服务注册中心.


####2.大致三为三块:  
        a.服务提供方即为发布服务功能实现,将接口,实现,超时,版本号等参数交能beanFactory来处理,解析有哪些接口,然后信息提供给注册中心.<br> 
        b.注册中心,即接口信息存储和通知调用方的一个主要功能实现.<br> 
        c.客户端,即服务消费者,监听需要有哪些接口的信息,注册中心有变更随时通知.还有就是根据注册中心给的接口信息组装调到到服务提供方的那台机器上来.<br>

![Image text](https://github.com/recollects/pussycat/blob/rpc_study_01/img/lALPBbCc1YrglSTNAh3NAzo_826_541.png)




