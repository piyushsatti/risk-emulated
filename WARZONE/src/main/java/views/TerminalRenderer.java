package main.java.views;

import main.java.utils.TerminalColors;

public class TerminalRenderer {
    public static String renderWelcome() {
        return TerminalColors.ANSI_CYAN + """
                
                 ___       ___     ____     ______      ______      ____        __      _    _____ \s
                (  (       )  )   (    )   (   __ \\    (____  )    / __ \\      /  \\    / )  / ___/ \s
                 \\  \\  _  /  /    / /\\ \\    ) (__) )       / /    / /  \\ \\    / /\\ \\  / /  ( (__   \s
                  \\  \\/ \\/  /    ( (__) )  (    __/    ___/ /_   ( ()  () )   ) ) ) ) ) )   ) __)  \s
                   )   _   (      )    (    ) \\ \\  _  /__  ___)  ( ()  () )  ( ( ( ( ( (   ( (     \s
                   \\  ( )  /     /  /\\  \\  ( ( \\ \\_))   / /____   \\ \\__/ /   / /  \\ \\/ /    \\ \\___ \s
                    \\_/ \\_/     /__(  )__\\  )_) \\__/   (_______)   \\____/   (_/    \\__/      \\____\\\s
                \s
                Welcome to WarZone. Built by Team24.
                """ + TerminalColors.ANSI_RESET;
    }

    public static String renderMenu(String menu_type, String[] options) {
        // Display menu graphics
        StringBuilder out = new StringBuilder();
        out.append(TerminalColors.ANSI_BLUE + "============================\n" + TerminalColors.ANSI_GREEN).append(String.format("""
                |  %s Options:\s
                """, menu_type));
        for (int i = 0; i < options.length; i++) {
            out.append(String.format("""
                    |     %d. %s\s
                    """, i + 1, options[i]));
        }
        out.append(String.format("""
                |     %d. Exit\s
                """, options.length + 1));
        return out + TerminalColors.ANSI_BLUE + "============================" + TerminalColors.ANSI_RESET;
    }

    public static void main(String[] args) {
        System.out.println(renderWelcome());
        String[] poop = {"Hi", "Bye", "Opti"};
        System.out.println(renderMenu("Main Menu", poop));
    }
}
