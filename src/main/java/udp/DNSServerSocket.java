package udp;

import dns.*;
import dns.header.DNSHeader;
import dns.header.OPCode;
import dns.header.RCode;
import utils.DNSMessageParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class DNSServerSocket {

    private final int PORT;
    private final String forwardingAddress;

    public DNSServerSocket(int port, String forwardingAddress) {
        PORT = port;
        this.forwardingAddress = forwardingAddress;
    }

    public void serve() {
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            while(true) {
                final byte[] buf = new byte[512];
                final DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                DNSMessageParser packetParser = new DNSMessageParser(buf);
                DNSMessage request = packetParser.parseMessage();
                DNSHeader responseHeader = getResponseHeader(request);

                DNSMessage response = new DNSMessage(responseHeader);
                getQuestionsAndAnswers(request, response);

                final byte[] bufResponse = response.getMessage();

                final DatagramPacket packetResponse = new DatagramPacket(bufResponse, bufResponse.length, packet.getSocketAddress());
                socket.send(packetResponse);
            }
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    private DNSHeader getResponseHeader(DNSMessage request) {
        DNSHeader requestHeader = request.getHeader();

        DNSHeader responseHeader = new DNSHeader();
        responseHeader.setID(requestHeader.getID());
        responseHeader.setQR(true);
        responseHeader.setOpCode(requestHeader.getOpCode());
        responseHeader.setRD(requestHeader.isRD());
        responseHeader.setRCode(requestHeader.getOpCode() == OPCode.STANDARD_QUERY ? RCode.NO_ERROR : RCode.NOT_IMPLEMENTED);
        responseHeader.setQuestionCount(requestHeader.getQuestionCount());
        responseHeader.setAnswerCount(requestHeader.getQuestionCount());
        return responseHeader;
    }

    private void getQuestionsAndAnswers(DNSMessage request, DNSMessage response) {
        int count = 0;
        for(DNSQuestion question : request.getQuestions()) {
            response.addQuestion(question);
            if(forwardingAddress != null && !forwardingAddress.isEmpty()) {
                response.addAnswer(forwardRequestAndGetResponse(question, (short) (request.getHeader().getID() + count)));
                count++;
            } else {
                response.addAnswer(new DNSRecord(question.getDomainName(), DNSType.A, DNSClass.IN, 256, 4, "8.8.8.8"));
            }
        }
    }

    private DNSRecord forwardRequestAndGetResponse(DNSQuestion question, short id) {
        DNSHeader header = new DNSHeader();
        header.setID(id);
        header.setQR(false);
        header.setRD(true);
        header.setQuestionCount((short) 1);

        DNSMessage singleRequest = new DNSMessage(header);
        singleRequest.addQuestion(question);

        byte[] request = singleRequest.getMessage();

        String[] addressParts = forwardingAddress.split(":");
        String host = addressParts[0];
        int port = Integer.parseInt(addressParts[1]);

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket requestPacket = new DatagramPacket(request, request.length, address, port);
            socket.send(requestPacket);

            byte[] responseBuffer = new byte[512];
            DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(responsePacket);
            DNSMessageParser parser = new DNSMessageParser(responseBuffer);
            DNSMessage response = parser.parseMessage();

            if(response.getHeader().getAnswerCount() != 1) {
                throw new Exception("Incorrect answer count");
            }

            return response.getAnswers().getFirst();
        } catch (Exception e) {
            System.out.println("Failed to send request: " + e.getMessage());
        }
        return null;
    }
}
