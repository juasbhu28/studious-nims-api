package com.nips.api.user.common;

public final class RouteMapping {
    private RouteMapping() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ROOT_VERSION = "/v1";
    public static final String PUBLIC_API = ROOT_VERSION + "/public";
    public static final String PRIVATE_API = ROOT_VERSION + "/private";

    //AUTH

    public static final String AUTH_API_ROOT = PUBLIC_API + "/auth";
    public static final String LOGIN_API = "/login";
    public static final String REGISTER_API = "/register";

    //USER

    public static final String USER_API_ROOT = PRIVATE_API + "/user";
    public static final String GET_USER = "/";

}