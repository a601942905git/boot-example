# 一、定义存在的状态
订单存在的状态有：
1. 已下单未支付状态(未支付)
2. 已支付未发货状态(未发货)
3. 已发货未收货状态(未收货)
4. 已收货已完成状态(已完成)

对应代码中的状态：
1. UNPAID
2. WAITING_FOR_DELIVERY
3. WAITING_FOR_RECEIVE
4. FINISH

# 定义状态对应的事件
状态的变更往往伴随着事件的发生，是事件驱动了状态的变更，那么以上状态存在什么样的时间呢？

订单存在的事件
1. 支付事件(支付已下单未付款的订单)
2. 发货事件(对已经付款的订单进行发货操作)
3. 收货事件(用户收到订单货物)

当然其中还存在着用户发起退款、退货事件，本文暂不列举，只列举正向流程

# 初始化状态、声明与事件的关系
```java
@Configurable
@EnableStateMachine
public class OrderStateMachineConfig extends
        EnumStateMachineConfigurerAdapter<OrderStatusEnum, OrderEventEnum> {

    /**
     * 配置状态机状态
     * @param states
     * @throws Exception
     */
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatusEnum, OrderEventEnum> states) throws Exception {
        states
            .withStates()
            // 定义初始状态
            .initial(OrderStatusEnum.UNPAID)
            // 定义状态机中所有的状态
            .states(EnumSet.allOf(OrderStatusEnum.class));
    }

    /**
     * 配置状态机事件触发订单状态的扭转
     * @param transitions
     * @throws Exception
     */
    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatusEnum, OrderEventEnum> transitions) throws Exception {
        transitions
            // 付款事件触发订单状态:未付款--->待发货
            .withExternal()
            .source(OrderStatusEnum.UNPAID)
            .target(OrderStatusEnum.WAITING_FOR_DELIVERY)
            .event(OrderEventEnum.PAY)
            .and()
            // 发货事件触发订单状态:待发货--->待收货
            .withExternal()
            .source(OrderStatusEnum.WAITING_FOR_DELIVERY)
            .target(OrderStatusEnum.WAITING_FOR_RECEIVE)
            .event(OrderEventEnum.DELIVERY)
            .and()
            // 收货事件触发订单状态:待收货--->订单完成
            .withExternal()
            .source(OrderStatusEnum.WAITING_FOR_RECEIVE)
            .target(OrderStatusEnum.FINISH)
            .event(OrderEventEnum.RECEIVE);
    }
}
```

# 定义事件动作
```java
@WithStateMachine
public class OrderStateMachineEventConfig {

    /**
     * 支付事件
     */
    @OnTransition(source = "UNPAID", target = "WAITING_FOR_DELIVERY")
    public void pay() {
        System.out.println("=========================================");
        System.out.println("订单已支付,订单状态从未支付状态变成已支付状态");
        System.out.println("=========================================");
    }

    /**
     * 发货事件
     */
    @OnTransition(source = "WAITING_FOR_DELIVERY", target = "WAITING_FOR_RECEIVE")
    public void delivery() {
        System.out.println("=========================================");
        System.out.println("订单已发货,订单状态从待发货状态变成已发货状态");
        System.out.println("=========================================");
    }

    /**
     * 收货事件
     */
    @OnTransition(source = "WAITING_FOR_RECEIVE", target = "FINISH")
    public void receive() {
        System.out.println("=========================================");
        System.out.println("订单已收货,订单状态从待收货状态变成已完成状态");
        System.out.println("=========================================");
    }
}
```

# 启动状态机并触发事件发生
```java
@SpringBootApplication
public class OrderStatusMachineApplication {

    private static StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine;

    @Autowired
    public void setStateMachine(StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine) {
        OrderStatusMachineApplication.stateMachine = stateMachine;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderStatusMachineApplication.class, args);
        // 启动状态机
        stateMachine.start();
        // 支付
        stateMachine.sendEvent(OrderEventEnum.PAY);
        // 发货
        stateMachine.sendEvent(OrderEventEnum.DELIVERY);
        // 收货
        stateMachine.sendEvent(OrderEventEnum.RECEIVE);
    }
}
```