<div align="center">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">P2PSend</h3>

  <p align="center">
    Send/receive files to/from peers through Java sockets.
    <br />
    <br />
    <a href="https://github.com/ezb2661/P2PSend/issues">Report Bug</a>
  </p>
</div>

## About The Project

This project is designed to allow you to send a file directly to a peer instead of having to use a file uploading service. I started this project with the intention of learning more about Java sockets and more specifically, sending files.


## Building It Yourself

In order to build this project on your own from the source, you will need to have a Java IDE and replace the src folder with the one in this repository. If you do not already have a Java IDE I recommend either [eclipse](https://www.eclipse.org/) or [IntelliJ](https://www.jetbrains.com/idea/)

## Usage

Double click p2psend.jar and select either receiver or sender.

Receiver:
* Type in the host and port of a sender
* Wait for the sender to receive a dialog showing an authorization code
* Enter the authorization code
* You will be alerted once the file has been transferred

Sender:
* Type in a port for the server to be hosted on
* Once a receiver connects, send the authorization code shown
* Type in the path of the file you would like to send (less than 1GB)


## License

Distributed under the MIT License. See `LICENSE` for more information.