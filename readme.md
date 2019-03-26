按照官网的[Get Started](http://www.rabbitmq.com/getstarted.html)写的Demo  
基于springboot 2.2.0.BUILD-SNAPSHOT版本开发  

- ["Hello World!"](http://www.rabbitmq.com/tutorials/tutorial-one-java.html)   
   基于指定队列的消息生产、发送       
   一个生产者,一个消费者,生产者基于指定队列发送消息,消费者基于指定队列监听、消费消息  
   > A queue is only bound by the host's memory & disk limits, it's essentially a large message buffer.   
          
            
- [Work Queues](http://www.rabbitmq.com/tutorials/tutorial-two-java.html)  
   基于工作队列的消息生产、发送  
   一个生产者,多个消费者,多个消费者之间基于某种策略消费消息.消息只被消费一次.      
   
   > 工作队列(又名:任务队列)背后的主要思想是避免立即执行资源密集型任务并等待任务完成.
     相反,我们把任务安排在以后完成.我们将任务封装为消息并将其发送到队列.在后台运行的工作进程将弹出任务并最终执行作业.
     当您运行许多worker时,任务将在它们之间共享.    
     
   这个demo,简单来说就是在前一个Demo的基础上提供多个消费者,默认情况下多个消费者之间**轮训**消费消息.    
   RabbitMQ提供```prefetch```的配置用于控制消费者允许的未回复的最大消息数.   
   ```spring.rabbitmq.listener.simple.prefetch=1```即告诉RabbitMQ Server,当前消费者最多一次只能处理一条消息,要等到消息处理完回复ack之后才能再给这个消费者派发消息.  
   
   > 使用任务队列的优点之一是能够轻松地并行化工作.如果我们积压了大量的工作,我们可以增加更多的consumers,这样就可以很容易地扩大规模.  
   
   考虑一个场景,如果消费者正在消费某个消息的时候挂了,那么这个消息会丢失吗?  
   RabbitMQ通过提供**Message acknowledgment**机制来确保消息不丢失.消费者通过回复生产者一个ack消息来通知生产者消息已经接收并消费完成.如果消费过程中发生异常,RabbitMQ会重新投递这个消息.        
   RabbitMQ提供了三种acknowledgment机制:  NONE|MANUAL|AUTO,具体可以查看枚举类```org.springframework.amqp.core.AcknowledgeMode```
   - NONE 不回复  
   - MANUAL 手动回复
   - AUTO 自动回复,当前版本默认AUTO 
   
   注意,如果关闭回复,比如配置```spring.rabbitmq.listener.simple.acknowledge-mode=none```,此时如果在程序中手动回复ack消息会报错,因为此时channel已经关闭:  
   ```aidl
    2019-03-05 19:07:56.223 ERROR 16016 --- [.16.22.114:5672] o.s.a.r.c.CachingConnectionFactory       : Channel shutdown: channel error; protocol method: #method<channel.close>(reply-code=406, reply-text=PRECONDITION_FAILED - unknown delivery tag 1, class-id=60, method-id=80)
   ```
   
   > It's a common mistake to miss the basicAck. It's an easy error, but the consequences are serious. Messages will be redelivered when your client quits (which may look like random redelivery), but RabbitMQ will eat more and more memory as it won't be able to release any unacked messages.
   
     再考虑一个场景,如果RabbitMQ Server挂了,那么消息会丢失吗?  
     RabbitMQ支持**消息持久化**.通过设置持久化queue以及持久化的message delivery mode,尽可能地保证消息不丢失.当前版本这两个都是默认持久化的.    
   
   > Marking messages as persistent doesn't fully guarantee that a message won't be lost. Although it tells RabbitMQ to save the message to disk, there is still a short time window when RabbitMQ has accepted a message and hasn't saved it yet. Also, RabbitMQ doesn't do fsync(2) for every message -- it may be just saved to cache and not really written to the disk. The persistence guarantees aren't strong, but it's more than enough for our simple task queue. If you need a stronger guarantee then you can use publisher confirms.  
   
  
- [Publish/Subscribe](http://www.rabbitmq.com/tutorials/tutorial-three-java.html)  
  发布订阅模式,广播模式.一个消息可以被多个消费者消费.  
  **messaging model**  
  > the producer can only send messages to an exchange. An exchange is a very simple thing. On one side it receives messages from producers and the other side it pushes them to queues. The exchange must know exactly what to do with a message it receives. 
  
  RabbitMQ引入Exchange,通过Exchange可以绑定多个queue.生产者将消息发送到指定的Exchange,而不需要关心发送给哪些queues.通过Exchange的类型,来决定消息推送给哪些queues.    
  RabbitMQ支持的Exchange类型:direct|topic|fanout|headers|system,具体可以查看抽象类```org.springframework.amqp.core.ExchangeTypes```
  - direct 定向的
  - topic
  - fanout 广播模式,绑定到exchange的所有queue都会接收到消息,但是同一个queue下只有一个消费者会接收到消息  
  - headers
  - system

- [Routing](http://www.rabbitmq.com/tutorials/tutorial-four-java.html)    
   Direct Exchange  
   
   > A binding is a relationship between an exchange and a queue. This can be simply read as: the queue is interested in messages from this exchange.  
     We will use a direct exchange instead. The routing algorithm behind a direct exchange is simple - a message goes to the queues whose binding key exactly matches the routing key of the message.
   
   将queue绑定到exchange时可以指定binding_key.生产者可以发送特定binding_key的消息,RabbitMQ Server通过生产者发送的binding_key来精确匹配相同binding_key的queue.将消息路由到匹配的queues.   
   前一个demo中用的fanout类型的exchange会忽略这个binding_key.这里使用direct类型的exchange  
   一个queue可以配置多个binding_key,多个queue可以配置相同的binding_key.  
   比如所有的queue都配置同一个binding_key,那么就和fanout类型的exchange实现的功能一样了,即广播消息给所有queues了.  
   
   适用场景:日志存储系统.  
   生产者即日志产生者,生产者产生不同等级的日志,比如error,warn,info,debug等等,通过设置不同的binding_key,将消息路由到不同的queues.以此对不同日志级别的日志进行处理.  
   
   
- [Topics](http://www.rabbitmq.com/tutorials/tutorial-five-java.html)  
   Topic Exchange    
   > The logic behind the topic exchange is similar to a direct one - a message sent with a particular routing key will be delivered to all the queues that are bound with a matching binding key. However there are two important special cases for binding keys:   
     star(*) can substitute for exactly one word.    
     hash(#) can substitute for zero or more words.   

     binding_key规则:由点分隔的一组单词列表,支持通配符,*匹配单词,#匹配零或多个单词.最大不能超过255个字节.  
     比如binding_key1=```*.orange.*```,binding_key2=```lazy.#```,那么```lazy.orange.rabbit```可以匹配上着两个binding_key.  
     
   
- [Remote procedure call (RPC)](http://www.rabbitmq.com/tutorials/tutorial-six-java.html)