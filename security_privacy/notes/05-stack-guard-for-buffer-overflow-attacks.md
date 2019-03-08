
# StackGuard: Automatic Adaptive Detection and Prevention of Buffer-Overflow Attacks
See original article
[here](https://www.usenix.org/legacy/publications/library/proceedings/sec98/full_papers/cowan/cowan.pdf)
From 1998.

## Overview
- Addresses buffer overflows as the _destination_ (the stack) instead of the _source_ (the source)
  since the sources are so profligate
- ^^ you could argue which is really the source. You can also blame bad languages, like C...
- Target is traditional "stack smashing" to overwrite return addresses
- Method 1: place a canary word right next to the return address, and check it before returning
  - Assumption: RA changed IFF canary changed
  - Weaknesses:
    - Can violate the assumption by skipping over the canary
    - Can simulate the canary
  - Mitigations:
    - Skipping over the canary via overflows requires a very specific kind of bug; possible, but
      will be rare
    - Randomize canary values (at program-launch-time)
- Method 2: make the return address unwritable completely with MemGuard
  - MemGuard can mark specific pages as read-only
  - Builds on concept of "quasi-invariants", invariants that are _usually_ invariant
  - Relies on system calls, so much less performant
  - Because of implementation details, still cannot provide cold-hard guarantees
- Adaptability
  - The researchers propose running a canary version of a process for performance, and
    if the canary is tripped restarting it as the MemGuard version for security
  - This balances security and performance by falling back to the safer only when under attack
- Notes on implementation
  - Libraries, even standard libraries, can be vulnerable
  - Function pointers are an unaddressed problem
- They had trouble getting their hands on exploits and source code. They wouldn't have that problem
  today!
- Use:
  - Doesn't eliminate the need for patches, but reduces the urgency
  - You can deliver and install on ordinary patch cycles
- Future work
  - Heap protection
  - Function pointers--can also be protected by canaries, but might require source changes (or just
    compiler annotations)
- There is a GCC patch that does full array bounds checking for C!
  - See [here](https://www.doc.ic.ac.uk/~phjk/BoundsChecking.html)
- Done by researches at the Oregon Graduate Institute of Science and Technology
