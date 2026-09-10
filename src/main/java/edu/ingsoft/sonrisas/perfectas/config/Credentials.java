
package main.java.edu.ingsoft.sonrisas.perfectas.config;


public class Credentials {
    public static final String URL_MYSQL_DB =System.getenv("DB_URL") + "sonrisas_perfectas_bd_in4bm";

    public static final String USER_DB =System.getenv("DB_USER");

    public static final String PASS_DB =System.getenv("DB_PASSWORD");
}


