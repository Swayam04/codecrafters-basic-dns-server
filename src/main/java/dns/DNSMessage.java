package dns;

import dns.header.DNSHeader;

import java.util.ArrayList;
import java.util.List;

public class DNSMessage {

    private final byte[] message = new byte[512];
    private final DNSHeader header;
    private final List<DNSQuestion> questions;
    private final List<DNSRecord> answers;

    public DNSMessage(DNSHeader header) {
        questions = new ArrayList<>();
        answers = new ArrayList<>();
        this.header = header;
    }

    public byte[] getMessage() {
        byte[] dnsHeader = header.buildDnsHeader();
        int currentPos = 0;
        System.arraycopy(dnsHeader, 0, message, currentPos, dnsHeader.length);
        currentPos += dnsHeader.length;
        for (DNSQuestion question : questions) {
            byte[] dnsQuestion = question.buildDNSQuestion();
            System.arraycopy(dnsQuestion, 0, message, currentPos, dnsQuestion.length);
            currentPos += dnsQuestion.length;
        }
        for (DNSRecord record : answers) {
            byte[] dnsRecord = record.buildDNSRecord();
            System.arraycopy(dnsRecord, 0, message, currentPos, dnsRecord.length);
            currentPos += dnsRecord.length;
        }
        return message;
    }
    
    public DNSHeader getHeader() {
        return header;
    }
    
    public List<DNSQuestion> getQuestions() {
        return questions;
    }
    
    public List<DNSRecord> getAnswers() {
        return answers;
    }
    
    public void addQuestion(DNSQuestion question) {
        questions.add(question);
    }
    
    public void addAnswer(DNSRecord answer) {
        answers.add(answer);
    }
}

