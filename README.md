__demo__ 
# src/main/java
## com.example.demo
###  --> config
    DateConverConfig.java  //自定义传入日期转换器，用以解决前端提交时间数据，后台date字段接收转换不准确的问题
    JobRunner.java         //配置 spring boot启动后执行程序，这里执行的是动态定时任务
    MyConfigMvc.java       //配置 spring boot访问页面不用经过controller跳转
    WebSocketConfig.java   //开启webSocket支持
###  --> controller
    BizQuartzController.java //动态定时任务控制器
    TestController.java      //测试用控制器
    UserController.java      //用户控制器
###  --> dao
    BizQuartzMapper.java   //动态定时任务dao
    BizUserMapper.java     //用户dao
###  --> entity
    none                   //实体类包，暂时无用
###  --> JSch
    JschMain.java          //Jsch访问服务器日志用例，判断日志是否存在异常，true则推送到钉钉机器人
      --> utils
        HttpUtil.java      //采用胡图工具编写的http请求
###  --> model
    BizQuartz.java         //动态定时任务数据模型
    BizQuartzExample.java  //动态定时任务查询范例，具体使用可以看service中的代码
    BizUser.java           //用户数据模型
    BizUserExample.java    //用户查询范例，具体使用可以看service中的代码
###  --> quartz
    MyStartupRunner.java   //任务监听器，项目启动后即刻执行
      --> job
        BusinessJob.java   //定时任务工作类
        ScheduledJob.java
      --> schedulerJob
        CronSchedulerJob.java  //定时器工作类
###  --> scheduledTask
    ScheduleTask.java      //spring实现的定时任务
###  --> service
        --> impl
          BizQuartzServiceImpl.java //动态定时任务服务实现
          UserServiceImpl.java      //用户服务实现
    BizQuartzService.java  //动态定时任务服务接口
    UserService.java       //用户服务接口
    WebSocketServer.java   //websocket接口
    WSMessageService.java  //测试websocket发送接口用例
###  --> utils
    SpringUtil.java        //spring boot工具类，作用于获取bean
### DemoApplication.java   //demo启动类

## src/main/resources
### mapper
  BizQuartzMapper.xml      //动态定时任务映射文件
  BizUserMapper.xml        //用户映射文件
### mybatis
  mybatis-config.xml       //mybatis映射文件返回类型别名
### static
  none                     //静态资源夹，暂无
### templates
  index.html               //websocket聊天室页面
  quartz.html              //动态定时任务配置页面
### application-dev.yml    //spring boot配置文件
### application.yml        //配置文件读取分支
### generator.properties   //mybatis 自动生成代码工具配置文件
### generatorConfig.xml    //mybatis 自动生成代码规范文件
### quartz.yml             //定时器配置文件
