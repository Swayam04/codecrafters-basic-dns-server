import udp.DNSServerSocket;

public class Main {
    public static void main(String[] args) {
    // You can use print statements as follows for debugging, they'll be visible when running tests.
      System.out.println("Logs from your program will appear here!");
        String forwardingAddress = "";
        for(int i = 0; i < args.length; i++) {
          if(args[i].equals("--resolver")) {
            forwardingAddress = args[i + 1];
          }
        }
        DNSServerSocket serverSocket = new DNSServerSocket(2053, forwardingAddress);
        serverSocket.serve();
    }
}
