package com.shaff.carshop.services.validators;

import java.util.List;

public interface Validator<T> {
    List<String> validate(T element);
}
