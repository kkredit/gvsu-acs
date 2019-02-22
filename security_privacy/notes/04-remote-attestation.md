
# SPINS: Security Protocols for Sensor Networks
See original article
[here](http://static.usenix.org/events/vm04/tech/haldar/haldar_html/).
From 2004.

## Overview
### Intro
- Def'n: "Using language-based virtual machines enables the remote attestation of complex, dynamic,
	and high-level program properties -- in a platform-independent way. We call this semantic remote
	attestation."
- Designed to enable trust in an environment with
	- Heterogeneity
	- Mobility
	- Open systems
- Their solution based on idea of a "trusted virtual machine"

### Background
- They describe the TPM and secure-boot
- Notably they talk about secure-boot (a continuous chain of measurements and verifications) as
	opposed to measured-boot (an after-the-fact or incomplete chain of measurement and verification)
- Remote attestation: sending the entire certificate chain from boot through application to the
	server
	- Problems:
		- It says nothing about program behavior
		- It is static, inflexible and inexpressive
		- Upgrades and patches to programs are hard to deal with
		- It is fundamentally incompatible with a widely varying, heterogeneous computing environment
		- Revocation is a problem

### Semantic Remote Attestation
- Root problem with traditional attestation: what you care about is the behavior or the remote
	software; what you're actually checking is a binary version
- "Cryptography is good at certifying entities -- it is not suitable for certifying behavior"
- Semantic solution:
	- Language-based security
	- Virtual machines
- Using at _runtime_:
	- Code rewriting
	- Code analysis
- My thoughts: performance! I like the ideas but projects and businesses usually are unwilling to
  make the sacrifice
#### Using the TrustedVM
- Still use traditional remote attestation through VM layer
- Beyond, use semantic attestation
- Requires platform independent bytecode running in a language-based VM, e.g. JVM
- Using such code makes property-determination simpler
- Examples of what can be enforced:
  - properties of classes
  - runtime state and program input properties
  - arbitrary code analysis
  - system properties, like "passes a set of floating-point impl tests"
#### Advantages of SRA
- They somehow call requiring bytecode an "advantage". It being bytecode is an enabler for the
  requirements of SRA, but it is not an advantage from the designer's POV to be limited to this
- It allows flexible and scalable security policies
  - If something doesn't meet all desired properties, instead of a hard stop you can continue, just
    with a more conservative interaction

### Implementation Results
- Traditional attestation required for
  - Host OS
  - VM
  - Standard libraries
- Use separate connections for application comms and attestation comms
- They implemented
  - P2P messaging applicatoin
    - Did some properties verification
    - Found that binary rewriting was too hard (or else would have been very costly) in the JVM
  - Distributed computing application
    - Used a test suite to determine client capabilities
    - Ran this in the TVM attestation service

### Related and Future Work
- Trusted computing was really picking up at the time, and the TPM spec was under development
- Distributed firewalls seem like an application


## My Thoughts
- A neat idea
- Limiting that it requires a language-specific VM--rules out many embedded, IoT applications
- Limiting the scope that needs to be traditionally attested is good, as flexibility is needed
