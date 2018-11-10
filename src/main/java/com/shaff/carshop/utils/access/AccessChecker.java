package com.shaff.carshop.utils.access;

import com.shaff.carshop.constants.RequestParameters;
import com.shaff.carshop.db.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class AccessChecker {
    private Map<String, List<String>> accessMap;

    public AccessChecker(Map<String, List<String>> accessMap) {
        this.accessMap = accessMap;
    }

    public boolean isResourceDefended(HttpServletRequest req) {
        for (List<String> currentList : accessMap.values()) {
            if (currentList.contains(req.getRequestURI())) {
                return true;
            }
        }
        return false;
    }

    public boolean isUserLogged(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute(RequestParameters.USER);
        return user != null;
    }

    public boolean checkAccessToResource(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute(RequestParameters.USER);
        return !accessMap.get(Integer.toString(user.getRoleId())).contains(req.getRequestURI());
    }
}