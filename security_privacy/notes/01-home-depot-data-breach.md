
# Case Study: The Home Depot Data Breach
From the _SANS Institute InfoSec Reading Room_. See original
[here](https://www.sans.org/reading-room/whitepapers/casestudies/case-study-home-depot-data-breach-36367).
From January 2015.

## Overview
- The attack was after the Target breach, but used largely the same methods (RAM scraping)
- Cash/data flow
  - Sold to information bulk brokers
  - Brokers sell to "carders" who specialize in credit card info
  - Criminals purchase info from carders, use the info to purchase prepaid credit cards
  - The prepaid card is then used to buy gift cards
  - Gift cards are redeemed
  - Products are shipped to re-shippers instead of to the original criminal
  - Products are shipped to people who buy them on Amazon or Craigslist
  - Common, proven way of making profit
- Part of the problem: Magstripe
  - Includes card number, expiration date, owner name
  - Easy to rip off the information
- Magstripe alternatives:
  - Chip-and-pin
    - What seems to have won
    - Chip makes each transaction unique
    - More expensive to produce
    - Mandated? Not sure, but if don't have then liability of breach is on merchant instead of bank
  - Mobile
    - Apple Pay, Google Pay, etc
    - Gaining more adoption
    - More secure in that credit card number is not actually passed (not sure how that's possible)
  - P2P encryption
    - Credit card data is encrypted by the time it resides in system memory
    - Must use some embedded device or coprocessor then?
    - Still vulnerable to skimmers
- Home Depot attack
  - Attackers got into vendor environment using a third-part vendor's credentials
  - Used an 0-day in Windows to pivot to the Home Depot corporate environment
  - From there they installed RAM scraping malware on 7500 POS terminals
  - The exfiltrated 56M credit cards and 53M email addresses
  - They were in the network for 5 months
- Failures
  - Insufficient security, scanning of POS environment
  - Didn't segregate POS and corporate network
  - Lack of exfiltration detection, intrusion detection
  - Basic magstripe readers
  - POS terminals were running Windows XP Embedded SP3
- Steps to take
  - Device level
    - Upgrade the OS version on POS systems
    - Run antivirus, protection systems on POS
    - Enable automatic updates, roll in patches
    - Use P2P if possible!
    - Disable all unused ports and features on POS
  - Network level
    - Segregate corporate, POS networks
    - Have detection capabilities
      - Logs
      - Automatic detection systems
    - Limit what third-part vendors can access

## Reflections
- All the solutions are expensive
  - Significant hardware upgrades
  - Complex SW systems that must be configured, maintained, and monitored
  - Security isn't cheap! The cost is clear, but the benefit is fuzzy
- Best strategy: make the information useless (by encrypting it)
  - Also don't use Windows Embedded if possible
