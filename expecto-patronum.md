## 功能设计



### 推送

被推送用户

1. 登录 直接发送

1. 未登录 使用redis list类型，user id为key，需要推送的视频为list；

### 存储到es 使用 canal

1. 弹幕
2. 用户信息
3. 视频信息

### 根据es查询

​	使用下门面模式

1. 查询弹幕
2. 查询用户
3. 查询视频



## 所遇问题

1. 这里的对象this发生了改变，@Scheduled这个是spring的一个注解，但是在websocket里面是多例模式，并不能让spring托管，所以对象会发生变化，属性值皆为null；

   ~~~java
     @Scheduled(fixedRate=1000)
       private  void noticeOnlineCount(){
           if(!currentMap.isEmpty()){
               this.sendCurrentPeopleCount();
           }
       }
   ~~~

2. 使用rabbitmq时，绑定交换机类型，如果将DirectExchange，改成topicExchange，方法类型变了，一定要改变交换机的注册名字，不然会报错。

   ~~~java
     @Bean
       TopicExchange scrollingExchange() {
     //      SCROLLING_EXCHANGE值要改变
           return new TopicExchange(SCROLLING_EXCHANGE);
       }
   ~~~

3. 时常出现mybatis的错误

   ~~~java 
   org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): com.example.dao.mapper.ScrollingMapper.saveScrolling
   ~~~

   这个错误很奇怪，只需要将**<mapper namespace>**改个名字重启就好了。

4. 在map中获取list修改可以不用concurrentMap,要加锁修改并提交，不然会出现覆盖的情况。

5. foreach（S ： list）在压测和webSocket中会出现list为空指针的异常，但list并不为空，使用fori就可以解决。、

6. 打包之后java -jar运行时，找不到自定义的springboot启动类

   - 一般有以下原因：

      - 自定义启动类spring.factories配置文件有空格
      - 在  maven 插件里面配置skip
      - 插件配置

     ~~~xml
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId><version>3.10.1</version>
     
     或者
       <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-maven-plugin</artifactId>
     
     ~~~

     我的解决办法：

      1. 在自定义启动器里面添加

         <groupId>org.apache.maven.plugins</groupId>
         <artifactId>maven-compiler-plugin</artifactId><version>3.10.1</version>

         然后

         在项目里面添加

         <groupId>org.springframework.boot</groupId>
         <artifactId>spring-boot-maven-plugin</artifactId>

7. 在腾讯云里面部署遇到了许多问题，mysql权限问题，设置密码有很多屁事，直接把这个密码长度和密码复杂度给关了。

8. canal，总是出现 should subscribe first的报错，在代码中添加 connector.subscribe();就好了，github上说的是和mysql的一个升级有关。

   大佬原话：

   ~~~sql
   Curtaingit commented on Aug 19, 2021：：：
   因为mysql同步问题 删除meta.bat 后 遇到一样的问题 com.alibaba.otter.canal.protocol.exception.CanalClientException: deserializer failed Message message = connector.getWithoutAck(batchSize);前面添加connector.subscribe() 方式可以解决。
   ~~~



## 功能扩展

1. 代码todo部分

2. 历史记录

   - 先存在redis里面，每隔一段时间刷到数据库里面。

3. 内容推送 apache manhout

4. 弹幕遮拦 javacv 1.4.3

   ~~~xml
   <dependency>
       <groupId>org.bytedeco</groupId>
       <artifactId>javacv</artifactId>
       <version>1.4.3</version>
   </dependency>
   <dependency>
       <groupId>org.bytedeco.javacpp-presets</groupId>
       <artifactId>ffmpeg-platform</artifactId>
       <version>4.0.2-1.4.3</version>
   </dependency>
   ~~~

   

