package com.nhnacademy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestURL {
    String scheme;
    String host;
    int port;
    String uri;
    String method;

    public void parser(String url) {
        int start = 0;
        Pattern schemePattern = Pattern.compile("(http|ftp)://");
        Pattern hostPattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*(\\.[a-zA-Z][a-zA-Z0-9]*)*");
        Pattern uriPattern = Pattern.compile("(\\/[a-zA-Z][a-zA-Z0-9]*)+");

        Matcher m = schemePattern.matcher(url.substring(start, url.length()));
        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        scheme = url.substring(m.start(), m.end() - 3);
        start = m.end();

        m = hostPattern.matcher(url.substring(start, url.length()));
        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        host = url.substring(start + m.start(), start + m.end());
        start = start + m.end();

        m = uriPattern.matcher(url.substring(start, url.length()));
        if (!m.find()) {
            throw new IllegalArgumentException();
        }

        uri = url.substring(start + m.start(), start + m.end());
        start = start + m.end();
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("Scheme: %s%n", scheme));
        stringBuilder.append(String.format("Host: %s%n", host));
        stringBuilder.append(String.format("URI: %s%n", uri));

        return stringBuilder.toString();
    }
}