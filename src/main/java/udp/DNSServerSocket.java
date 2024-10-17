package udp;

import dns.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DNSServerSocket {

    final int PORT;
    final int ID;

    public DNSServerSocket(int port, int ID) {
        PORT = port;
        this.ID = ID;
    }

    public void serve() {
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            while(true) {
                final byte[] buf = new byte[512];
                final DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                DNSMessage response = new DNSMessage.Builder()
                        .setID((short) ID)
                        .setQueryResponse(true)
                        .setQuestionCount((short) 1)
                        .addQuestion(new DNSQuestion("codecrafters.io", DNSType.A, DNSClass.IN))
                        .setAnswerCount((short) 1)
                        .addAnswerRecord(new DNSRecord("codecrafters.io", DNSType.A, DNSClass.IN, 60, (short) 4, "8.8.8.8"))
                        .build();
                final byte[] bufResponse = response.getMessage();
                final DatagramPacket packetResponse = new DatagramPacket(bufResponse, bufResponse.length, packet.getSocketAddress());
                socket.send(packetResponse);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
