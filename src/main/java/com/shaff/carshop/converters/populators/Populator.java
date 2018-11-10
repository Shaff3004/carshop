package com.shaff.carshop.converters.populators;

public interface Populator<S, T> {
    void populate(S source, T target);
}
