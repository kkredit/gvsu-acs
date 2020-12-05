# On Microservices

See [this talk](https://www.youtube.com/watch?v=-UKEPd2ipEk) on microservices and the benefits of small and disposable.

## Response

- When the speaker talks about the idea that code is a liability, and the system is the asset - what is the system?

The system is the entire technical stack including the frontend, backend, database, monitoring, and deployment systems.
You could even argue it goes beyond the technical elements of the system---the real value comes from the developers and
operators who maintain the technical system and, most importantly, the users that engage with it.

- If you changed the "system" that integrates the microservices, how many microservices could it impact?

Changing the "system" could impact a very large number of microservices. This week's [other
talk](https://www.youtube.com/watch?v=tpspO9K28PM) discussed how making individual services very small can have the
effect of shifting the complexity from the service to the messages between and integration of services---the "system". A
well-designed integration architecture (possibly using streaming, as promoted in the video) would minimize the impact of
changes in one portion of the system to another, but a wholesale change would affect everything.

- Would this change for larger or smaller systems? Is the fact he is working on a task list application an issue? Would
  a less understood system require more system changes (rather than software changes)?

That fact that they were working on a fundamentally simple application does mean that the large scale system
architecture would be less likely to change drastically over time, since they could more accurately guess their system
use cases and requirements. However, a sound integration architecture would allow for the microservices to evolve around
it without requiring significant change in the integration system itself. (Though this is more easily said than done,
especially with new technologies and fads arising all the time.)

- If microservices are not an architecture, what architecture do systems that use microservices employ?

To use "microservices" really means dividing the application into many small components and relying on a distributed
system framework to connect one component to another. A microservices-based system could employ many architectural
patterns on top of that framework. It could be layered, actor-based, producer-consumer, event-driven,
data-centered---just about anything.

## Benefits and Drawbacks of Microservices

I really liked the speaker's analogy of a software system compared to a living organism. It is a valuable insight that a
software system's value is not in its code, but in the product-level system and user base. (This has some exceptions,
such as Google's search algorithm or Facebook's data hoard, which do contain inherent value.) Since the system is the
valuable thing, it is what must be prized, not the code that composes it. In the analogy, the code is a cell, and the
system is a body; the body (the system) is the important thing, and the cells that compose it (the code) are constantly
replaced.

Microservices are a perfect fit for this approach. Since a microservice has a clear and small definition, replacing it
becomes easy. Changes are easier to implement, and the entire system is much more agile.

The drawback is that the "system" becomes untestable. The system is more than the sum of its parts, and one cannot
always predict the effect a microservice change will have on the emergent behavior of the system. This isn't necessarily
bad---the speaker talks about how they eliminated heavy-duty testing in favor of heavy-duty metrics and alpha-beta
deployments with frequent rollbacks. This isn't an option for many software systems, however. A web-based task list
application can afford live experiments on the system, but non-web-based systems practically can't achieve it, and
security- or safety-critical systems can't afford the risk.
