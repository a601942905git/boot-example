# 基于JDK的方式实现发布订阅

# 定义观察者
```java
public class SMSObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("【发送短信】");
    }
}
```

```java
public class PointObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("【赠送积分】");
    }
}
```

# 定义发布者
```java
public class RegisterEvent extends Observable {

    public void register() {
        System.out.println("【用户注册成功】");
        setChanged();
        notifyObservers();
    }
}
```

# 测试代码
```java
public class Test {

    public static void main(String[] args) {
        SMSObserver smsObserver = new SMSObserver();
        PointObserver pointObserver = new PointObserver();

        RegisterEvent registerEvent = new RegisterEvent();
        registerEvent.addObserver(smsObserver);
        registerEvent.addObserver(pointObserver);

        registerEvent.register();
    }
}
```