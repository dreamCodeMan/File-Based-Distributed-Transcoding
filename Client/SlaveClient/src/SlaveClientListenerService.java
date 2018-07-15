import java.io.IOException;
import java.io.ObjectInputStream;

public class SlaveClientListenerService implements Runnable {

    private boolean isConnected = true;
    private ObjectInputStream fromServer;
    private FFmpegJobRequestListener fFmpegJobRequestListener;

    public SlaveClientListenerService(ObjectInputStream fromServer, FFmpegJobRequestListener fFmpegJobRequestListener) {
        this.fromServer = fromServer;
        this.fFmpegJobRequestListener = fFmpegJobRequestListener;
    }

    @Override
    public void run() {
        System.out.println("Updater service is running.");
        while (isConnected) {
            //Update Listeners
            try {
                byte dataType = fromServer.readByte();
                switch (dataType) {
                    case 1:
                        String command = fromServer.readUTF();
                        fFmpegJobRequestListener.onJobRequest(command);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
}