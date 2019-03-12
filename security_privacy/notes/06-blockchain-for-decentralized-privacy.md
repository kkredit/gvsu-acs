
# Case Study: Decentralizing Privacy: Using Blockchain to Protect Personal Data
From the IEEE CS Security and Privacy Workshops. See original
[here](https://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=7163223).
From 2015.

# I. Introduction
- Related work
  - See OpenPDS (PDS = personal data store); only answers, no raw data sent
  - See OAuth (offer third-party applications limited access to a web service)
  - Note: data is more restricted, but these are still centralized trust authorities
  - Anonymization through
    - k-anonymity
    - l-diversity
    - t-closeness
    can be de-anonymized
  - Differential privacy adds noise to data
  - Fully homeomorphic encryption allows computations on encrypted data (very cool, but currently
    inefficient
- Their proposal: use blockchain
- Original contributions:
  - combine blockchain and off-blockchain storage to construct a personal data management platform
    focused on privacy
  - illustrate how blockchains could become a primary tool for trusted computing

# II. The Privacy Problem
- Primary target: mobile apps that have access to high resolution personal data (see [Reply All
  episode](https://www.gimletmedia.com/reply-all/135-the-robocall-conundrum#episode-player)
- Privacy issues addressed
  - Data ownership - you own your data
  - Data transparency and auditability - you know how it's being used
  - Fine-grained access control - you have control over how it's being used

# III. Proposed Solution
- Three main entities: users, services, and the blockchain
- Two types of transaction: access and data
- When user creates an account with an app using the platform, a new shared identity (user, service)
  is created; the user performs an access transaction granting the application access to the given
  data
- Collected data is encrypted with the shared encryption key and sent to the blockchain in a data
  transaction; the data is stored off-chain in a DHT (distributed hash table), while the blockchain
  stores in its ledger a pointer to the data (the key is the SHA-256 of the data)
- Both the service and the user can query with a data transaction; the blockchain decides whether to
  approve the transaction based on their digital signatures and the granted access
- The user can change access at any time

# IV. The Network Protocol

# V. Discussion of Future Extensions

# VI. Conclusion

# Notes for presentation
- Worth noting that those doing research on privacy and tracking may want to refer to this papers
  references
