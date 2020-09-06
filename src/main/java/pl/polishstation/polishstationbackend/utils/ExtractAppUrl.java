package pl.polishstation.polishstationbackend.utils;

import javax.servlet.http.HttpServletRequest;

public class ExtractAppUrl {
    public static String extractAppUrl(HttpServletRequest request) {
        return String.format("%s://%s:%d/",request.getScheme(),  request.getServerName(), request.getServerPort());
    }
}
