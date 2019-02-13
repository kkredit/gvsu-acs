
# SPINS: Security Protocols for Sensor Networks
See original article
[here](https://netsec.ethz.ch/publications/papers/mc2001.pdf).
From 2001.

## Overview
- Introduce two Protocols
  1. SNEP:
    - Secure Network Encryption Protocol
    - provides data confidentiality, two-party data authentication, and data freshness
  2. uTESLA:
    - (u)Micro Time, Efficient, Streaming, Loss-tolerant Authentication protocol
    - provides authenticated broadcast
- Designed for resource-constrained environments
- Root problem being solved: memory, CPU, and comms bandwidth
  - Working on 8-bit MCUs
  - The MCUs don't implement complex instructions
  - 8k of memory--RSA keys can be 2-4k!
  - Frequent batter replacement zeroes-out memory and keys (by design)
  - Traditional, workstation-oriented protocols require large packet overhead
- Applications under consideration:
  - Node -> Basestation
  - Basestation -> Node
  - Basestation -> All noes (broadcast)
- Together, SNEP and uTESLA provide:
  - Data confidentiality
  - Data authentication
  - Data integrity (inhereted from auth, which is stronger)
  - Data freshness (ordering; prevent replays)
- Accomplished with about 25% power and memory overhead
- Heavy code reuse
- Uses only symmetric primitives
- The project was a success

## Takeaways
- Security is achievable even in low resource environments
