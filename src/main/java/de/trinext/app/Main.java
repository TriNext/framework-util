package de.trinext.app;

import static spark.Spark.get;

import spark.*;

/**
 * @author Dennis Woithe
 */
public class Main {

    private static final String STATUS = "/status";

    public static void main(String... args) {
        get(STATUS, Main::handleStatus);
    }

    @SuppressWarnings("SameReturnValue") private static Object handleStatus(@SuppressWarnings("unused") Request request, Response response) {
        //noinspection MagicNumber
        response.status(200);
        return "OK";
    }

}