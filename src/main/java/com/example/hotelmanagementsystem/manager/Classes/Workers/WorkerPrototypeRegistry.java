package com.example.hotelmanagementsystem.manager.Classes.Workers;

import java.util.HashMap;
import java.util.Map;

public class WorkerPrototypeRegistry {
    private final Map<String, Worker>workerPrototype=new HashMap<>();

    public WorkerPrototypeRegistry() {
        Worker workerPrototypeInstance = new workerMember("Default Name", "1234567890");
        registerWorker("worker", workerPrototypeInstance);
    }

    public void registerWorker(String type, Worker worker){
        workerPrototype.put(type,worker);
    }
       public Worker getClone(String role ){
        Worker Prototype = workerPrototype.get(role);
        if (Prototype !=null){
            return (Worker)Prototype.clone();
        }
        else return null;

        }

}
