package controller.middleware.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapEditorCommands extends Commands{
    public MapEditorCommands(String p_command) {
        super(p_command, new String[]{
            "editcontinent",
                    "editcountry",
                    "editneighbor",
                    "showmap",
                    "savemap",
                    "editmap",
                    "validatemap"
        });
    }
    @Override
    public boolean validateCommand()
    {
        Pattern pattern = Pattern.compile("^editcontinent(?:\\s+-add\\s+\\w+\\s+\\d+)*(?:\\s+-remove\\s+\\w+)*$|"+
                "^editcountry(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*(?:\\s+-remove\\s+\\w+)*$|"+
                "^editneighbor(?:\\s+-add\\s+\\w+\\s+\\w+)*(?:\\s+-remove\\s+\\w+\\s+\\w+)*(?:\\s+showmap)?$|"+
                "^showmap$|"+
                "^validatemap$|"+
                "^savemap\\s\\w+\\.map$|"+
                "^editmap\\s\\w+\\.map$");
        Matcher matcher = pattern.matcher(d_command);
        return matcher.matches();
    }

}
