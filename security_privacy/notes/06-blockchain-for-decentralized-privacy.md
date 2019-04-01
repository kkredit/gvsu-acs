
# Case Study: Decentralizing Privacy: Using Blockchain to Protect Personal Data
From the IEEE CS Security and Privacy Workshops. See original
[here](https://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=7163223).
From 2015.

See presentation slides in similarly-named PDF.

## I. Introduction
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

## II. The Privacy Problem
- Primary target: mobile apps that have access to high resolution personal data (see [Reply All
  episode](https://www.gimletmedia.com/reply-all/135-the-robocall-conundrum#episode-player)
- Privacy issues addressed
  - Data ownership - you own your data
  - Data transparency and auditability - you know how it's being used
  - Fine-grained access control - you have control over how it's being used

## III. Proposed Solution
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

## IV. The Network Protocol
- Cryptographic primitives
  - Symmetric encryption
  - Digital signatures
  - Hash function (SHA-256)

### Building Blocks
- Compound identities:
  - Traditional blockchain IDs are (basically?) public keys
  - For the purposes of shared data, create identities with the owner (RW) and another party (RO)
  - ID is
    - Public: PK-owner, PK-guest
    - Private: '', SK-owner (signing key), SK-guest, SK-encrypt
  - SK-encrypt is a shared secret symmetric key used for data encryption
- Blockchain memory:
  - ???
  - I think they're arguing that the data store will be tamper proof via the same reasoning that the
    blockchain itself is; the documents get serialized into the blockchain (though they can't be
    fully in it...)
- Policy:
  - The user defines something like POLICY={location, contacts} to grant access to such things
  - Can be flexible
- Auxiliary functions:
  - They define some protocols for interacting with the blockchain

### Blockchain Protocols
- They define access and data transaction protocols

### Privacy and Security Analysis
- Relies on the blockchain being tamper-free, which requires a sufficiently large network of
  untrusted peers
- Also relies on secure private key management
  - They call out key wallet services, but those have been compromised many times!
- Recall the original goals
  - Data ownership - you own your data
    - The owner has control; only forged digital signatures (loss of your key) or control over the
      majority of the network can violate the owner's control
    - Since data is encrypted on the DHT, no danger there
    - Because data is tied to a owner-guest pair, only a segment of the total data would be lost for
      any keypair compromise
    - The attacker actually needs both keys to see the data
      - TODO follow up on this; exactly which keys do they need, and which parties have which keys?
      - TODO create a diagram of this for the presentation
  - Data transparency and auditability - you know how it's being used
    - The policies give you fine-grain control over what they can see, though it's not the same as
      PDS
  - Fine-grained access control - you have control over how it's being used
    - Policies can be arbitrarily fine, and you can even use separate keys for blocks of data above
      some threshold

## V. Discussion of Future Extensions
- About blockchains generally

### From Storage to Processing
- Problem: blockchains can store or point to encrypted data, but this is not good for processing,
  and guests with access to the data can easily store unencrypted copies themselves
- Solution: PDS on the blockchain using Shamir's Secret Sharing and secure Multiparty Computation

### Trust and Decision Making in Blockchains
- Problem: proof-of-work is vulnerable to sybil attachs, excessive energy consumption, and high
  latency
- Solution: be smarter about how to assign trust to nodes in a system, allowing you to compute
  blocks more efficiently
  - Prevent sybil attacks, but opens up to variant where bad actors build up trust just to betray it
  - You can still take over a network if you have the majority of resources
  - We need orders of magnitude more efficiency; we need O() more efficiency

## VI. Conclusion
- They claim success on their mission for privacy
- They briefly mention coding laws into the blockchain; that feels like a whole different article

# Notes for presentation
- Worth noting that those doing research on privacy and tracking may want to refer to this papers
  references
- Will have to explain blockchains a bit first
- Reference that priv key management is still non-trivial, and wallet services have been compromised
- Problems they don't address
  - Power consumption of blockchains (ok, they mention it briefly)
    - They are extraordinarily power hungry
    - This problem needs to be fixed before blockchain can scale
  - Incentivization to process on the blockchain
    - If currencies remain so speculative, why bother processing?
    - You do need a large number of resources on the chain for it to remain secure
    - Some currencies have been popped this way, I think
    - My question: does the chain need _constant_ processing to remain secure? If all BitCoin
      miners stopped today, would the transaction history become vulnerable?
- On their success
  - I posit you still don't have say in _how your data is used_
  - Apps can say "give us all these fields or nothing", and many people would say OK
