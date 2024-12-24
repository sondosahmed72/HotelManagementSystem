package com.example.hotelmanagementsystem.receptionist;

public class MassageDecorator extends ServiceDecorator {
    public MassageDecorator(Service decoratedService) {
        super(decoratedService);
    }
    @Override
    public double getCost() {
        return decoratedService.getCost() + 100.0;
    }
}