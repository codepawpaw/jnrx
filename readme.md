# JNRX

Simple pub sub library that run on Android platform

## Goals
* Replicate rx java

## Usage
* Clone this repo
* Open your android studio
* File -> New -> Import Module
* Choose this project
* In your app build.gradle add these lines

```
compile project(":jnrx")
```

### How to create Channel Stream
* Add these lines in the root all of your activity code

```
Jnrx.jnrx.createPublicStream(1);
```

### How to subscribe
* Make sure you have create one or more channel strea,
* In this example i will guide you by create a class called Subscriber 
* Subscriber class will act as a subscriber

```
public class Message {

    private String msg;

    public Message(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
```

```
import com.labs.jonathan.jnrxs.Jnrx;
import com.labs.jonathan.jnrxs.OnMessage;


public class Subscriber {
    private String classifier;
    public Subscriber(int streamId, int subscriberId, String classifier) {
        this.classifier = classifier;
        try {
            Jnrx.jnrx.addSubscriber(streamId, this, subscriberId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnMessage
    private void onMessage(Message message) {
        System.out.println(message.getMsg());
    }
}
```

* Add the lines to register your susbcriber into a channel stream
* These lines means you create a subscriber into channel stream with id 1, and subscriber with id 10
```
Subscriber subscriber =  new Subscriber(1, 10, "Subscriber A");
```

### How to Publish
```
Message message = new Message("publish message");
Jnrx.jnrx.publish(1, message, 10);
```
* These lines means you want to publish message to channel stream with id 1 and subscriber with id 10

## Contributing

* Feel free to open PR.

## License
MIT Licensed