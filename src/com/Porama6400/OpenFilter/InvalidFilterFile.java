package com.Porama6400.OpenFilter;

/**
 * Created by Porama6400
 */
public class InvalidFilterFile extends Exception {
    private String thisdata = "";
    public InvalidFilterFile(String data) {
        thisdata = data;
    }

    @Override
    public void printStackTrace() {
        OpenFilterPlugin.plugin.getLogger().warning("ERROR: " + thisdata);
    }
}
