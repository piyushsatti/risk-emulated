package main.java.views;

public class TerminalRenderer {
    public static String renderWelcome() {
        return """
                 _    _          ______                \s
                | |  | |        |___  /                \s
                | |  | | __ _ _ __ / /  ___  _ __   ___\s
                | |/\\| |/ _` | '__/ /  / _ \\| '_ \\ / _ \\\s
                \\  /\\  / (_| | |./ /__| (_) | | | |  __/\s
                 \\/  \\/ \\__,_|_|\\_____/\\___/|_| |_|\\___|\s
                \s
                Welcome to WarZone. Built by Team24.
                """;
    }

    public static String renderMenu(String menu_type, String[] options) {
        // Display menu graphics
        StringBuilder out = new StringBuilder();
        out.append(String.format("""
                ============================
                |  %s Options:\s
                """, menu_type)
        );
        for (int i = 1; i < options.length; i++) {
            out.append(String.format("""
                    |     %d. %s\s
                    """, i, options[i-1]));
        }
        out.append(String.format("""
                    |     %d. Exit\s
                    """, options.length));
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(renderWelcome());
        String[] test = {"Hi", "Bye", "Opti"};
        System.out.println(renderMenu("Main Menu", test));
    }
}
