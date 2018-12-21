# 发布订阅、观察者模式
不管是发布订阅模型、还是观察者模式，都是一对多的情况，一人发布，
可以通知到所有订阅者后者是观察者

# 定义观察者接口
```java
public interface CustomObserver {

    void hello();
}
```

# 定义不同的观察者
```java
public class CustomObserver1 implements CustomObserver {

    public void hello() {
        System.out.println("【CustomObserve1.hello()】");
    }
}
```

```java
public class CustomObserver2 implements CustomObserver {

    public void hello() {
        System.out.println("【CustomObserve2.hello()】");
    }
}
```

```java
public class CustomObserver3 implements CustomObserver {

    public void hello() {
        System.out.println("【CustomObserve3.hello()】");
    }
}
```

# 定义发布者
```java
public class CustomEvent {

    private List<CustomObserver> customObserveList;

    public CustomEvent(List<CustomObserver> customObserveList) {
        this.customObserveList = customObserveList;
    }

    public void hello() {
        if (null != customObserveList && customObserveList.size() > 0) {
            customObserveList.forEach(CustomObserver::hello);
        }
    }
}
```

# 测试代码
```java
public class Test {

    public static void main(String[] args) {
        List<CustomObserver> customObserveList = new ArrayList<>();

        CustomObserver1 customObserve1 = new CustomObserver1();
        CustomObserver2 customObserve2 = new CustomObserver2();
        CustomObserver3 customObserve3 = new CustomObserver3();

        customObserveList.add(customObserve1);
        customObserveList.add(customObserve2);
        customObserveList.add(customObserve3);

        CustomEvent customEvent = new CustomEvent(customObserveList);
        customEvent.hello();
    }
}
```
执行代码可以看到，发布者调用hello()方法后，所有的观察者都会被通知到智执行hello()方法