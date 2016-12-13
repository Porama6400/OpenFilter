package com.Porama6400.OpenFilter.Loader;

import com.Porama6400.OpenFilter.FilterAction.FilterAction;
import com.Porama6400.OpenFilter.FilterTarget;
import com.Porama6400.OpenFilter.Filters.BasicCommandFilter;
import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.InvalidFilterFile;
import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextBasedFilterProfileLoader implements FilterLoader {
    @Override
    public String getFileExtension() {
        return ".tbfp";
    }

    @Override
    public void load() {
        for (File file : getFiles()) {
            try {
                FileInputStream fis = new FileInputStream(file);
                LoadFilter(fis);
                fis.close();
            } catch (IOException | InvalidFilterFile e) {
                OpenFilterPlugin.plugin.getLogger().warning("ERROR WHILE LOADING: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public String readNextIgnoreEmptyLine(BufferedReader buff) throws IOException {
        while (true) {
            String read = buff.readLine();
            if (read == null) return null;
            if(read.isEmpty()) continue;
            if(read.startsWith("#")) continue;
            return read;
        }
    }

    public void LoadFilter(InputStream is) throws IOException, InvalidFilterFile {
        BufferedReader buff = new BufferedReader(new InputStreamReader(is));

        do {
            String in = readNextIgnoreEmptyLine(buff);
            if(in.equalsIgnoreCase("FILTER")){
               //CONTINUE
            }
            else if(in.equalsIgnoreCase("FILTER_OFF")){
                continue; //NEXT LOOP
            }
            else throw new InvalidFilterFile("Invalid filter header ('FILTER' or 'FILTER_OFF')");

            FilterTarget target = FilterTarget.valueOf(readNextIgnoreEmptyLine(buff).split(" ")[1]);
            if (!readNextIgnoreEmptyLine(buff).equalsIgnoreCase("CMDS")) throw new InvalidFilterFile("Header no found ('CMDS')");
            List<String> targetCmds = new ArrayList<>();
            List<FilterAction> filterActions = new ArrayList<>();
            while (true) {
                String data = readNextIgnoreEmptyLine(buff);
                if (!data.startsWith("-")) break;
                targetCmds.add(removeFirstChar(data));
            }
            // "ACTION"
            while (true) {
                String data = readNextIgnoreEmptyLine(buff);
                if (!data.startsWith("-")) break;
                try {
                    filterActions.add(FilterAction.Load(removeFirstChar(data)));
                } catch (InvalidFilterFile e) {
                    e.printStackTrace();
                }
            }
            Filter filter = new BasicCommandFilter(targetCmds,target,filterActions);
            OpenFilterPlugin.plugin.RegisterFilter(filter);
            String str = readNextIgnoreEmptyLine(buff);
            // "END"
            System.out.println(str);
            if(str == null) return;
            if(str.equalsIgnoreCase("NEXT")) continue;
            return;
        }
        while (true);

    }

    public String removeFirstChar(String data) {
        String out = "";
        for (int i = 1; i < data.toCharArray().length; i++) {
            out += data.charAt(i);
        }
        return out;
    }
}
