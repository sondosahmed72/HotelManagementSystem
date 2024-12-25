package com.example.hotelmanagementsystem.receptionist.Decorator;

public class PrivateViewDecorator extends ServiceDecorator {
    public PrivateViewDecorator(Service decoratedService) {
        super(decoratedService);
    }
    @Override
    public double getCost() {
        return decoratedService.getCost() + 150.0;
    }
}
