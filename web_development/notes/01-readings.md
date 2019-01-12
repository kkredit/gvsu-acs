
# Week 1 Readings
Original
[link](https://cis.gvsu.edu/~kurmasz/Teaching/Courses/W19/CIS658/Homework/week1_reading.html).

Please read the following articles and answer the questions below.
Your answers are not due for credit; but, be prepared to discuss the articles in class.

## Articles

- [Original WWW Proposal](https://www.w3.org/History/1989/proposal.html)
- [Formal WWW article in the ACM magazine](https://dl.acm.org/citation.cfm?doid=179606.179671)
(You will need to be logged into the GVSU library in order to access this article's full text.)
- [HTTP: The protocol Ever Web Developer Must Know](https://code.tutsplus.com/tutorials/http-the-protocol-every-web-developer-must-know-part-1--net-31177)
- [Progressive Web App Video](https://developers.google.com/web/progressive-web-apps/)
(focus on first 8 minutes)
- [Responsive Web Design](https://alistapart.com/article/responsive-web-design)
- Chapters 1 and 4 in this [Free IBM ebook](http://www.redbooks.ibm.com/pubs/pdfs/redbooks/gg243376.pdf)
(This should be review from CIS 654.)
- [Stack Overflow article about URIs, URLs and
  URNs](https://stackoverflow.com/questions/176264/what-is-the-difference-between-a-uri-a-url-and-a-urn)

## Questions

#### 1. For whom was Tim Berners-Lee working when he came up with the idea for the WWW?

CERN, the European Organization for Nuclear Research.

#### 2. Describe the main problem he was trying to solve.

Information availability. Information was stuck in people's heads and notebooks, not available in
any organized manner. This was resulting in duplicated efforts, lost efficiency, and great loss of
information when individuals left the organization.

#### 3. What are the two key ideas upon which the WWW is built?

HTTP, the data transfer protocol, and HTML, the data format.

#### 4. Which of these two key ideas does Berners Lee focus more on in his original paper?

HTML (though it wasn't named yet, so he just refers to hypertext and hypermedia).

#### 5. What are two competitors to the WWW described in the 1994 paper?

WAIS from Thinking Machines Corporation and Gopher from the University of Minnesota.

#### 6. List some advantages and disadvantages of these competing systems.

WAIS had powerful search capabilities, but would "parachute" you in without context (hyperlinks).
Gopher had search and a menu system for navigation, but the menu system was not always intuitive,
and certainly less flexible than hyperlinks. All allowed for access to multiple types of media, but
WWW was the most transparent and flexible.

#### 7. Which points from "the future" have come true? Which have not?

Have come true:
- Name service so that we can reference independent of location
- Hypertext editors that make making websites easy
- More sophisticated document types (stylesheets, javascript, HTML5, etc)
- Integration with "live" features like live doc edit and teleconferencing
- Easy setup servers
- Machine readable formats (in some cases)
- Conventions for charging and commercial use

Have not:
- Links from 2- and 3-dimensional images for creative UI (kind of?)

#### 8. How much did the WWW grow from 1993 to 1994?

From 62 to 829 servers.

#### 9. What does it mean for HTTP to be a "stateless" protocol?

Every request-response pair is independent. The server does not track or make assumptions about the
state of the client. This does not mean that the server itself does not have state. It also is
referring to its level of abstraction only--TCP connections have state, but that is unrelated to
HTTP state.

#### 10. What is responsive web design?

Responsive web design means making a web page that automatically suits itself to whatever
environment that renders it. If it is a small screen with a touch interface, reduce the number of
columns and increase the click hit-box, for example. It makes it so that you don't need separate
mobile and desktop webistes.

#### 11. What is a Progressive Web App?

TODO TODO TODO

#### 12. What is the difference between a URI and a URL?

A URI is a Uniform Resource Identifier, while a URL is a .. Locator. The historical difference is
that a URL includes the means for acquiring the object identified by the URI--that is, it includes
the protocol. URLs are then a subset of URIs. Due to the mass confusion on the subject, recent RFCs
have declared URI and URL to be synonymous.
