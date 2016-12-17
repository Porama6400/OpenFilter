package com.Porama6400.OpenFilter.Loader;

import com.Porama6400.OpenFilter.FilterAction.FilterAction;
import com.Porama6400.OpenFilter.FilterTarget;
import com.Porama6400.OpenFilter.Filters.BasicCommandFilter;
import com.Porama6400.OpenFilter.Filters.Filter;
import com.Porama6400.OpenFilter.InvalidFilterFile;
import com.Porama6400.OpenFilter.OpenFilterPlugin;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

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
            } catch (IOException | InvalidFilterFile | IndexOutOfBoundsException e) {
                OpenFilterPlugin.plugin.getLogger().warning("ERROR WHILE LOADING: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    public String readNextIgnoreEmptyLine(BufferedReader buff) throws IOException {
        buff.mark(10000);
        while (true) {
            String read = buff.readLine();
            if (read == null) return null;
            if (read.isEmpty()) continue;
            if (read.startsWith("#")) continue;
            return read;
        }
    }

    public void undoReadNextIngoreEmptyLine(BufferedReader buff) throws IOException {
        buff.reset();
    }

    public void LoadFilter(InputStream is) throws IOException, InvalidFilterFile {
        BufferedReader buff = new BufferedReader(new InputStreamReader(is));

        do {
            //CHECK is FILTER ENABLE?
            {
                String in = readNextIgnoreEmptyLine(buff);
                if (in.equalsIgnoreCase("FILTER")) {
                    //CONTINUE
                } else if (in.equalsIgnoreCase("FILTER_OFF")) {
                    continue; //NEXT LOOP
                } else throw new InvalidFilterFile("Invalid filter header ('FILTER' or 'FILTER_OFF')");

            }
            //READ TARGET (ALL,CONSOLE,PLAYER);
            FilterTarget target = FilterTarget.valueOf(readNextIgnoreEmptyLine(buff).split(" ")[1]);

            //IS BLOCK TAB COMPLETE
            Boolean blockTabComplete = false;
            {
                String btcin = readNextIgnoreEmptyLine(buff).toUpperCase();
                if (btcin.contains(" ") && (btcin.startsWith("TAB_COM") || btcin.startsWith("TAB_COMPLETE"))) {
                    String arg = btcin.split(" ")[1];
                    if (arg.equalsIgnoreCase("BLOCK")) {
                        blockTabComplete = true;
                    }
                } else
                    undoReadNextIngoreEmptyLine(buff);
            }

            //GET PERMISSION IF SPECIFIED
            List<Permission> permissions = new ArrayList<>();
            {
                String permInput = readNextIgnoreEmptyLine(buff);
                if (permInput.equalsIgnoreCase("PERMISSIONS")) {
                    while (true) {
                        String permLine = readNextIgnoreEmptyLine(buff);
                        if (permLine.startsWith("-")) {
                            String perm = removeFirstChar(permLine);
                            if (perm.contains(" ")) { //IF PERMISSION DEFAULT SPECIFIED
                                String permPart[] = perm.split(" "); //SPLIT INTO PERM AND DEFAULT
                                permissions.add(new Permission(permPart[0], PermissionDefault.valueOf(permPart[1])));
                            } else permissions.add(new Permission(perm, PermissionDefault.OP)); //IF NOT USE 'OP'
                        } else {
                            undoReadNextIngoreEmptyLine(buff);
                            break;
                        }
                    }
                } else undoReadNextIngoreEmptyLine(buff);
            }
            //  'CMDS' is needed , check if it's there
            if (!readNextIgnoreEmptyLine(buff).equalsIgnoreCase("CMDS"))
                throw new InvalidFilterFile("Header no found ('CMDS')");

            //get list of command
            List<String> targetCmds = new ArrayList<>();
            while (true) {
                String data = readNextIgnoreEmptyLine(buff);
                if (!data.startsWith("-")) break;
                targetCmds.add(removeFirstChar(data));
            }

            // get list of action
            List<FilterAction> filterActions = new ArrayList<>();
            while (true) {
                String data = readNextIgnoreEmptyLine(buff);
                if (!data.startsWith("-")) break;
                try {
                    filterActions.add(FilterAction.load_TBFP(removeFirstChar(data)));
                } catch (InvalidFilterFile e) {
                    e.printStackTrace();
                }
            }
            Filter filter = new BasicCommandFilter(targetCmds, blockTabComplete, target, filterActions, permissions);
            OpenFilterPlugin.plugin.RegisterFilter(filter);
            String str = readNextIgnoreEmptyLine(buff);
            // Should be String "END" here
            System.out.println(str);
            if (str == null) return;
            if (str.equalsIgnoreCase("NEXT")) continue;
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
