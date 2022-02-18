package me.ezb2661.p2psend;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class FileSenderThread extends Thread
{
    public ServerSocket serverSocket;
    public Socket socket;
    private boolean isAvailable = true;
    private int authenticationCode;

    public FileSenderThread(int port)
    {
        try
        {
            this.serverSocket = new ServerSocket(port);
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    private void showConfirmConnectionDialog()
    {
        Random random = new Random();
        this.authenticationCode = random.nextInt(1000, 9999);
        JOptionPane.showMessageDialog(
                null,
                "Connection requested from: " + socket.getInetAddress().getHostName() + ".\nThe authentication code is: " + this.authenticationCode,
                "Authentication Code",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void run()
    {
        while(this.isAvailable)
        {
            try
            {
                this.socket = this.serverSocket.accept();
                this.isAvailable = false;
                showConfirmConnectionDialog();
                byte[] receivedAuthCodeArray = new byte[4];
                if(this.socket.getInputStream().read(receivedAuthCodeArray) != 4)
                {
                    this.isAvailable = true;
                    this.socket.close();
                    this.socket = null;
                    continue;
                }
                int receivedAuthCode = receivedAuthCodeArray[0] * 1000 + receivedAuthCodeArray[1] * 100 + receivedAuthCodeArray[2] * 10 + receivedAuthCodeArray[3];
                if(receivedAuthCode != this.authenticationCode)
                {
                    this.isAvailable = true;
                    this.socket.close();
                    this.socket = null;
                    continue;
                }
                String filePath = JOptionPane.showInputDialog(
                        null,
                        "Enter the path to the file you want to send",
                        "File Path",
                        JOptionPane.INFORMATION_MESSAGE
                );
                FileInputStream fileInputStream = new FileInputStream(filePath);
                byte[] fileBytes = new byte[1000000]; //1GB
                if(fileInputStream.read(fileBytes) > 1000000)
                {
                    JOptionPane.showMessageDialog(
                            null,
                            "File is too large and will not be sent! (>1GB)",
                            "File Too Large",
                            JOptionPane.WARNING_MESSAGE
                    );
                    this.isAvailable = true;
                    this.socket.close();
                    continue;
                }
                this.socket.getOutputStream().write(receivedAuthCodeArray);
                this.socket.getOutputStream().write(fileBytes);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }

}
