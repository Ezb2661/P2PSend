package me.ezb2661.p2psend;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class FileReceiverThread extends Thread
{
    private Socket socket;
    boolean isAuthenticated = false;

    public FileReceiverThread(String host, int port)
    {
        try
        {
            this.socket = new Socket(host, port);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void showAuthenticationCodeDialog()
    {
        String authenticationCode = JOptionPane.showInputDialog(
                null,
                "Authentication Code",
                "Enter the server's authentication code",
                JOptionPane.INFORMATION_MESSAGE
        );

        byte[] authenticationCodeArray = new byte[4];
        for(int i = 0; i < authenticationCodeArray.length; i++)
        {
            authenticationCodeArray[i] = Byte.parseByte(String.valueOf(authenticationCode.charAt(i)));
        }
        try
        {
            this.socket.getOutputStream().write(authenticationCodeArray);
            byte[] response = new byte[4];
            if(this.socket.getInputStream().read(response) != 4)
            {
                JOptionPane.showMessageDialog(
                        null,
                        "Server returned invalid response! Exiting...",
                        "Invalid Response",
                        JOptionPane.WARNING_MESSAGE
                );
                this.socket.close();
                System.exit(0);
            }
            this.isAuthenticated = true;
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public void receiveFile() throws IOException
    {
        int receivedLen = 0;
        byte[] fileBytes = new byte[1000000];
        receivedLen = this.socket.getInputStream().read(fileBytes);
        if(receivedLen > 1000000)
        {
            JOptionPane.showMessageDialog(
                    null,
                    "Server sent too large of a file! (>1GB)",
                    "File Too Large",
                    JOptionPane.WARNING_MESSAGE
            );
            this.socket.close();
            System.exit(0);
        }
        /*
        FileOutputStream fileOutputStream = new FileOutputStream("")
        TODO: Have server send file name prior to sending the file bytes so it can be saved properly once received.
         */
    }

    @Override
    public void run()
    {
        showAuthenticationCodeDialog();
        if(this.isAuthenticated)
        {
            try
            {
                receiveFile();
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(
                        null,
                        "Failed to receive file from server.",
                        "Error Receiving File",
                        JOptionPane.WARNING_MESSAGE
                );
                System.exit(0);
            }
        }
    }
}
