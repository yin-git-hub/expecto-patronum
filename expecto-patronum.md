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

   这个错误很奇怪，只需要将<mapper namespace>改个名字重启就好了，
