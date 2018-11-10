package com.shaff.carshop.containers;

import com.shaff.carshop.utils.locale.LocaleStorageStrategy;
import com.shaff.carshop.utils.locale.strategies.LocaleCookiesStoreStrategy;
import com.shaff.carshop.utils.locale.strategies.LocaleSessionStoreStrategy;

import java.util.HashMap;
import java.util.Map;

public class LocaleStrategyStorage {
    private Map<String, LocaleStorageStrategy> localeStrategies = new HashMap<>();

    {
        localeStrategies.put("session", new LocaleSessionStoreStrategy());
        localeStrategies.put("cookies", new LocaleCookiesStoreStrategy());
    }

    public Map<String, LocaleStorageStrategy> getLocaleStrategies() {
        return localeStrategies;
    }

    public void setLocaleStrategies(Map<String, LocaleStorageStrategy> localeStrategies) {
        this.localeStrategies = localeStrategies;
    }
}