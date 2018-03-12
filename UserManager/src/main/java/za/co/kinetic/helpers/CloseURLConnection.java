
package za.co.kinetic.helpers;

import java.net.HttpURLConnection;

public class CloseURLConnection {
    public static void closeServerConnection(HttpURLConnection conn){
        conn.disconnect();
    }
}
