package com.shaff.carshop.utils.parsers;

import com.shaff.carshop.constants.LoggerMessages;
import com.shaff.carshop.exceptions.ApplicationException;
import com.google.common.base.Splitter;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class RequestTextParser {
    private static final Logger LOG = Logger.getLogger(RequestTextParser.class);

    public Map<String, String> getParameterMap(HttpServletRequest request) throws ApplicationException {
        Map<String, String> dataMap = null;
        try (InputStreamReader reader = new InputStreamReader(request.getInputStream());
             BufferedReader br = new BufferedReader(reader)) {

            String data = br.readLine();
            dataMap = Splitter.on('&').trimResults().withKeyValueSeparator(Splitter.on('=').limit(2).trimResults()).split(data);
        } catch (IOException e) {
            LOG.error(LoggerMessages.CANNOT_PARSE_REQUEST, e);
            throw new ApplicationException(LoggerMessages.CANNOT_PARSE_REQUEST, e);
        }
        return dataMap;
    }
}