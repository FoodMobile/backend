package com.foodmobile.server.events;




import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
class Pair<T,V>{
    public T t;
    public V v;
    public Pair(T t,V v){
        this.t = t;
        this.v = v;
    }
}
public class EventHandler {
    private Hashtable<String, List<Pair<Object,Consumer<Event>>>> callbacks = new Hashtable<>();
    public static final EventHandler shared = new EventHandler();
    public void addListener(String eventType, Object o, Consumer<Event> callback){
        var callbacks = this.callbacks.getOrDefault(eventType,new LinkedList<>());
        callbacks.add(new Pair<>(o,callback));
        this.callbacks.put(eventType,callbacks);
    }

    public void removeListener(String type, Object o){
        var callbacks = this.callbacks.getOrDefault(type,new LinkedList<>());
        callbacks.removeIf(p -> p.t == o);
        this.callbacks.put(type,callbacks);
    }

    public void notify(Event event){
        this.callbacks.getOrDefault(event.key,new LinkedList<>()).forEach(p ->{
            try{p.v.accept(event);}catch (Exception e){}
        });
    }
}
