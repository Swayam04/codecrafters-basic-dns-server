# Basic Forwarding DNS Server
This project implements a basic forwarding DNS server, built as part of a step-by-step, unguided and TDD based [Codecrafters](https://app.codecrafters.io/catalog) challenge.

## Core Features
* **DNS Packet Processing**: Receives and responds to DNS query packets
* **Header Parsing**: Processes DNS headers with complete flag support
* **Question Section**: Handles DNS questions with proper domain name parsing
* **Answer Processing**: Processes response records from upstream resolvers
* **Name Compression**: Supports DNS message compression according to RFC 1035
* **Forwarding Resolution**: Acts as a forwarding resolver by querying upstream DNS servers
* **IPv4 Support**: Complete support for A record resolution

## Understanding DNS Resolution
DNS (Domain Name System) acts as the internet's phone book, translating human-readable domain names into IP addresses. This implementation follows the standard DNS protocol:

### Packet Structure
* **Header**: Contains flags and section counts
* **Question**: The domain name being queried
* **Answer**: Resource records responding to the query
* **Authority**: Details about authoritative nameservers
* **Additional**: Extra information like related records

### Name Compression
DNS uses a special compression scheme where repeated domain names are replaced with pointers to reduce packet size. The server implements this compression scheme according to RFC 1035 specifications.

### Resolution Process
1. Client sends query to this forwarding server
2. Server parses and validates the request
3. Query is forwarded to a recursive resolver
4. Response is received, parsed, and returned to client

## Future Enhancements
Planned improvements include:
* **Recursive Resolution**: Converting to a full recursive resolver
* **Extended Record Support**: Adding support for IPv6 (AAAA) and other common record types (MX, CNAME, etc.)
* **Caching Layer**: Implementing response caching with proper TTL handling
* **Security Features**:
   * DNS over HTTPS support
   * DDOS protection mechanisms
   * Rate limiting
* **DNSSec**: Adding DNSSEC validation and signing capabilities
* **Performance Optimizations**: Connection pooling and request batching

## Acknowledgments
This project was built as part of the Codecrafters challenge, with all implementation done independently.
