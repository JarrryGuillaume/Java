import java.io.*;

class ServerResourceAccessible {
    private final static String serverStorageDir = "data/";

    public ServerResourceAccessible() {}

    protected final String getServerStorageDir() {
        return serverStorageDir;
    }

}
