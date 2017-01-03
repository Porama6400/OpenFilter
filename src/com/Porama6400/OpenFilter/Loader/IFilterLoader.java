package com.Porama6400.OpenFilter.Loader;

import com.Porama6400.OpenFilter.OpenFilterPlugin;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Porama6400
 */
public interface IFilterLoader {
    String getFileExtension();

   default List<File> getFiles() {
        List<File> out = new ArrayList<>();
        File folder = new File(OpenFilterPlugin.plugin.getDataFolder() + File.separator + "filters");
        if (!folder.exists()) {
            folder.mkdirs();
            return out;
        }
        return Arrays.asList(folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(getFileExtension());
            }
        }));
    }

    void load();
}
