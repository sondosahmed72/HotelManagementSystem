package com.example.hotelmanagementsystem.receptionist.Decorator;

public abstract class ServiceDecorator extends Service {
    protected Service decoratedService;
    public ServiceDecorator(Service decoratedService) {
        this.decoratedService = decoratedService;
    }
    public abstract double getCost();
}

