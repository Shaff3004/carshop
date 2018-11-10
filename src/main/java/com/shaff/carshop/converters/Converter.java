package com.shaff.carshop.converters;

import com.shaff.carshop.converters.populators.Populator;

public interface Converter<S, T> {
    T convert(S source);
    void addPopulator(Populator<S, T> populator);
}
