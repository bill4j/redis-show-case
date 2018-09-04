# redis-show-case
basic and advanced redis projects for understanding how to use redis in different use case

1.vicnet's opinion - September 4th
https://www.processon.com/view/link/5b8e6d2ee4b0bd4db92bd3c7 

场景：
1.并发业务，一般采用LoanCommonRedisLock
以及自动以RedisLock类，参考Service层用法（最常用）
2.唯一订单号、唯一三方资金序列号生成
3.电销自动分单，redis缓存线下门店接单员工顺序，类似环形链表

今天只传了最外层的封装，涉及底层framework包中的内容，待我修改之后上传，公司安全部门会定期查github上的公司代码，我被点过一次名。
