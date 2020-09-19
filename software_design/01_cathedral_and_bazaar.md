# Reading Response to _The Cathedral and the Bazaar_

You can find _The Cathedral and the Bazaar_
[here](http://www.catb.org/~esr/writings/cathedral-bazaar/cathedral-bazaar/).

## Notes and Quotes

Eric S. Raymond's programming aphorisms:

1. Every good work of software starts by scratching a developer's personal itch.
2. Good programmers know what to write. Great ones know what to rewrite (and reuse).
3. ``Plan to throw one away; you will, anyhow.'' (Fred Brooks, The Mythical Man-Month, Chapter 11)
4. If you have the right attitude, interesting problems will find you.
5. When you lose interest in a program, your last duty to it is to hand it off to a competent successor.
6. Treating your users as co-developers is your least-hassle route to rapid code improvement and effective debugging.
7. Release early. Release often. And listen to your customers.
8. Given a large enough beta-tester and co-developer base, almost every problem will be characterized quickly and the
   fix obvious to someone.
9. Smart data structures and dumb code works a lot better than the other way around.
10. If you treat your beta-testers as if they're your most valuable resource, they will respond by becoming your most
    valuable resource.
11. The next best thing to having good ideas is recognizing good ideas from your users. Sometimes the latter is better.
12. Often, the most striking and innovative solutions come from realizing that your concept of the problem was wrong.
13. ``Perfection (in design) is achieved not when there is nothing more to add, but rather when there is nothing more to
    take away.''
14. Any tool should be useful in the expected way, but a truly great tool lends itself to uses you never expected.
15. When writing gateway software of any kind, take pains to disturb the data stream as little as possible—and never
    throw away information unless the recipient forces you to!
16. When your language is nowhere near Turing-complete, syntactic sugar can be your friend.
17. A security system is only as secure as its secret. Beware of pseudo-secrets.
18. To solve an interesting problem, start by finding a problem that is interesting to you.
19. Provided the development coordinator has a communications medium at least as good as the Internet, and knows how to
    lead without coercion, many heads are inevitably better than one.

>Human beings generally take pleasure in a task when it falls in a sort of optimal-challenge zone; not so easy as to be
boring, not too hard to achieve. A happy programmer is one who is neither underutilized nor weighed down with
ill-formulated goals and stressful process friction. Enjoyment predicts efficiency. ... It may well turn out that one of
the most important effects of open source's success will be to teach us that play is the most economically efficient
mode of creative work.

Benefits of the bazaar:

- Having a large community
  - A large tester base exposes problems quickly
  - A large developer base is able to solve problems quickly
  - A large developer base leverages the principle that many minds are better than a few
  - Communication overhead applies to the core team that is in frequent communication, not to everyone
- Being volunteer oriented
  - Users are inherently interested in the product
  - Developers self select according to interest and skill
  - Developers are motivated not by command, but by interest and ego
  - The traditional management tasks of motivating and marshalling resources are reduced
- Having an open development model
  - Everything is peer reviewed, and only the best work gets in
  - The traditional management tasks of monitoring and organization are reduced

Necessary conditions for the bazaar method to operate:

- Plausible vision: offer a believable promise that the project can become something really neat
- Tinkerable product: begin with something that which the community can test and play with
- Frequent shipments: rapidly iterate to keep attention and reward participation
- Appreciation of design: leadership needs to _recognize and incorporate_ more than _originate_ good design
- Active community: a pool of contributors and beta testers is what drives the project
- People skills: to build and maintain the community, communication and personality matter

## Questions

### 1. What is the Cathedral, what is the Bazaar?

The cathedral is a style of software development consisting of careful planning and design by a select group of
"revered" developers. It fears a large developer base due to the linear increase in productivity but exponential
increase in communication costs. It also has a small beta-user base, infrequent releases, and bugs that are considered
deep and difficult.

A bazaar is a style consisting of rapid releases and community-driven direction and development. It requires an open and
tinkerable development process. The bazaar style leverages a large developer and beta-user base to root out bugs quickly
and effortlessly. It leverages the "Deplhi Effect", which observes that the average of many opinions is typically much
more accurate than the opinion of a few opinions, even from experts. Bazaar-style projects produce frequent releases
designed to keep to developer community engaged.

The bazaar method benefits from several principles. Its large community servers to "crowdsource" beta testing and
parallelize bug fixing. The communication structure is more hub- than web-like, so it escapes the exponential
communication costs. Its openness leads to contributions from developers who self-select according to both interest and
skill. It evades traditional management overhead though use of the highly effective joy and ego as primary motivators
and through decentralized peer review. All these principles lead to healthy and (at the time of the writing) shockingly
successful software projects.

### 2. Is the difference important? Why or why not? Does the author think it is important?

Whether the difference is important depends on what your goals are for a project. If the goal is a self-sustaining
software project that productively solves users' problems over a long period of time, then the difference is very
important, as the author argues throughout the essay. The bazaar style of development has proven to be an extremely
successful model in the current legal and technical environment.

The difference may not matter for all projects. You may not care about community and longevity if the project is just
for you or a one-off program. You may not be able to use the bazaar style of development if the project contains secret
technology or you have external licensing requirements. The differences between the methods only matters to the extent
that you can or would actually implement them.
