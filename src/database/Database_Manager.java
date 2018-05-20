package database;


import logic.CommonFunctions;

import java.io.IOException;

/**
 * A class to manage launching and shutting down the database.
 *
 * @author Ryan Junor
 */
public class Database_Manager {

    private Process process;

    /**
     * Launches the database
     */
    public void startup() {

        String filePath = System.getProperty("user.dir") + "/libs/mysql/bin/mysqld.exe";

        try {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec("cmd.exe /c start " + filePath);
        } catch (Exception e) {
            CommonFunctions.sqlError();
        }
    }

    /**
     * Shuts down the database
     */
    public void shutdown() {

        String shutdowncmd = "libs/mysql/bin/mysql.exe --user=root --password=ryan --database=\"graded_unit\" --execute=\"SHUTDOWN;\"";

        process.destroy();

        try {
            Runtime.getRuntime().exec(shutdowncmd);
        } catch (Exception e) {
            CommonFunctions.sqlError();
        }

    }
}
