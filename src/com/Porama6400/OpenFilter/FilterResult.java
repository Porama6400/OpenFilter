package com.Porama6400.OpenFilter;

/**
 * Created by Porama6400
 */
public class FilterResult {
    private boolean Cancelled = false;
    private boolean Filtered = false;

    public boolean isCancelled() {
        return Cancelled;
    }

    public boolean isFiltered() {
        return Filtered;
    }

    public FilterResult(boolean cancelled,boolean filtered){
        this.Cancelled = cancelled;
        this.Filtered = filtered;
    }
}
