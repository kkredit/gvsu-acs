
Kevin Kredit  
CIS 618  
12/3/19  

# Security Testing

## What is fuzz testing?

Fuzz testing is running a program with randomized or subtly malformed data. The method uncovers
vulnerabilities by testing corner cases and programmers' implicit assumptions.

## What is penetration testing? Distinguish between black-box, gray-box, and white-box forms of penetration testing

Penetration testing is adversarial testing performed on functional products. There are three types
of penetration testing:

1. Black-box: the attackers have no special information about the system
2. Gray-box: the attackers have some architectural and implementation information about the system
3. White-box: the attackers have full access to all design documents and source code for the system

## Outline the steps in penetration testing methodology

Penetration testing consists of 7 steps. Steps 2-6 are the same as those of a real attack; steps 1
and 7 relate to business operations between tester and client.

1. Establish a contract: agree on target and scope of testing
2. Gather intelligence: undetectably scout the environment; mostly use public information
3. Scanning and enumeration: detectably scan for open ports, internet connected hosts, and running
    services
4. Penetrate the target: execute attacks; exploit vulnerabilities to achieve contracted goal
5. Maintain access: establish a reliable foothold for future attacks (e.g. backdoor, rootkit)
6. Cover your tracks: evade detection; since a clean operation, take good note of all files modified
    and restore them upon completion
7. Report findings: state lessons learned what was accomplished during the test

## What are some of the tools that are useful during the scanning phase of pen testing?

- Host identification
  - ping
  - Angry IP
  - nmap
- Port scanning
  - nmap
- Vulnerability scanning
  - Nessus
  - OpenVAS
  - Metasploit

## How does a pen tester retain and maintain access to a system once they gain access to it with a successful penetration?

Penetration testers achieve persistence using backdoors, launching viruses or spyware, inserting
trojans, or installing a rootkit.
