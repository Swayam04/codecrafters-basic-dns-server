package udp;

import dns.*;
import dns.header.DNSHeader;
import dns.header.OPCode;
import dns.header.RCode;
import utils.DNSMessageParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DNSServerSocket {

    private final int PORT;

    public DNSServerSocket(int port) {
        PORT = port;
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

    private static DNSHeader getResponseHeader(DNSMessage request) {
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

    private static void getQuestionsAndAnswers(DNSMessage request, DNSMessage response) {
        for(DNSQuestion question : request.getQuestions()) {
            response.addQuestion(question);
            response.addAnswer(new DNSRecord(question.getDomainName(), DNSType.A, DNSClass.IN, 256, 4, "8.8.8.8"));
        }
    }
}
