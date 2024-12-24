package com.example.hotelmanagementsystem.receptionist.Decorator;

public class SaunaDecorator extends ServiceDecorator {
    public SaunaDecorator(Service decoratedService) {
        super(decoratedService);
    }
    @Override
    public double getCost() {
        return decoratedService.getCost() + 50.0;
    }
}
