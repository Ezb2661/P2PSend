package me.ezb2661.p2psend;

public class Main
{
    public static void main(String[] args)
    {
    //  FileSenderThread fileSenderThread = new FileSenderThread(1234);
    //  fileSenderThread.start();
        FileReceiverThread fileReceiverThread = new FileReceiverThread("127.0.0.1", 1234);
        fileReceiverThread.start();
    }
}
