按照官网的[Get Started](http://www.rabbitmq.com/getstarted.html)写的Demo  
基于springboot 2.2.0.BUILD-SNAPSHOT版本开发  

- ["Hello World!"](http://www.rabbitmq.com/tutorials/tutorial-one-java.html)   
   基于指定队列的消息生产、发送       
   一个生产者,一个消费者,生产者基于指定队列发送消息,消费者基于指定队列监听、消费消息    
   ```rmq.learn.helloworld.producer```: 通过提供的rest服务(/send)来触发发送消息的动作   
   ```rmq.learn.helloworld.consumer```:通过配置类```RmqMessageListener```创建```SimpleMessageListenerContainer```来添加一个指定的监听器```MessageReceive```
            
- [Work Queues](http://www.rabbitmq.com/tutorials/tutorial-two-java.html)  
   基于工作队列的消息生产、发送  
   一个生产者,多个消费者    
   
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
   
   ```rmq.learn.helloworld.consumer```: 默认auto模式  
   ```rmq.learn.helloworld.consumermanual```: manual模式.手动模式必须要注意,必须用接收消息的channel来发送ack.    
   
   > It's a common mistake to miss the basicAck. It's an easy error, but the consequences are serious. Messages will be redelivered when your client quits (which may look like random redelivery), but RabbitMQ will eat more and more memory as it won't be able to release any unacked messages.
   
   
   再考虑一个场景,如果RabbitMQ Server挂了,那么消息会丢失吗?  
   RabbitMQ支持**消息持久化**.通过设置持久化queue以及持久化的message delivery mode,尽可能地保证消息不丢失.当前版本这两个都是默认持久化的.    
   
   > Marking messages as persistent doesn't fully guarantee that a message won't be lost. Although it tells RabbitMQ to save the message to disk, there is still a short time window when RabbitMQ has accepted a message and hasn't saved it yet. Also, RabbitMQ doesn't do fsync(2) for every message -- it may be just saved to cache and not really written to the disk. The persistence guarantees aren't strong, but it's more than enough for our simple task queue. If you need a stronger guarantee then you can use publisher confirms.  
   
   
   
   
- [Publish/Subscribe](http://www.rabbitmq.com/tutorials/tutorial-three-java.html)
- [Routing](http://www.rabbitmq.com/tutorials/tutorial-four-java.html)
- [Topics](http://www.rabbitmq.com/tutorials/tutorial-five-java.html)
- [Remote procedure call (RPC)](http://www.rabbitmq.com/tutorials/tutorial-six-java.html)