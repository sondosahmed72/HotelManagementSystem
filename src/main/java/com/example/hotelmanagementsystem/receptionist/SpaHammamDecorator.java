package com.example.hotelmanagementsystem.receptionist;

public class SpaHammamDecorator extends ServiceDecorator {
    public SpaHammamDecorator(Service decoratedService) {
        super(decoratedService);
    }
    @Override
    public double getCost() {
        return decoratedService.getCost() + 70.0;
    }
}
