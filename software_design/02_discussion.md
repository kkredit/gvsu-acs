# CIS 622 Midterm

Kevin Kredit  
10/14/20

## Question 3

>Which decomposition paradigms, if any, could be used with Object Oriented Analysis? Justify your answer by discussing
what the analysis would identify (and why) as well as how that identification would be a part of the decomposition
paradigm.

Object oriented analysis (OOA) the process of breaking down a problem domain into a representative set of objects with
given attributes, inheritances, and relationships. It is the first step of object oriented design, even if it is
implicit. The question is which decomposition techniques assist the software designer that is performing OOA.

Top-down and bottom-up decomposition both compliment OOA in their own ways. Top-down decomposition focuses on the big
picture and recursively moves down in scope. Pairing this method with OOA gives the developer the major objects that are
specific to the problem at hand and form the logical structure of the program. Bottom-up decomposition starts with
lower-level constructs from which a solution will arise. In OOA, this results in utility classes that find use in a
variety of contexts.

For example, in the brewers and beer drinkers assignment, I applied top-down decomposition when creating the Puzzle,
State, Side, and Move classes. However, the solution also depends on the built-in Hash, Array, and Integer classes,
which provide essential bottom-up features.

Stepwise Refinement is like top-down, but its focus on modularity makes it particularly well suited for OOA, where
modules may represent objects or groups of closely related objects.

Structured Design focuses primarily on data flow. This focus doesn't help the developer immediately identify objects.
However, structured design does provide useful methods for analyzing an OOA design through use of its metrics such as
coupling and cohesion. To use the brewers and beer drinkers assignment as an example again, when initially decomposing
the problem using data flow doesn't help: a set of numbers goes in and a solution comes out--that's it. But after
performing OOA and implementing the objects, I can then analyze the data flow between them and quantify my design's
coupling and cohesion. If I have low coupling and high cohesion, then Structured Design has affirmed the quality of my
design.

Structured Analysis and Design also focuses on data flow, but emphasizes graphical tools such as activity and data
diagrams. While this assists in developing system architecture and user experience, it does not help one identify
objects.

Both Jackson Systems Development and Structured Systems Development focus on data structures and largely sequential
transformations. They are traditionally used to create imperative sequential programs. I am curious how they would fit
with purely functional programming, but neither is will paired with OOA.

In summary, OOA can be used with many forms of decomposition. Top-down and Stepwise Refinement identify the high level
objects that represent a problem domain. Bottom-up identifies utilities that solve common problems. Structured Design
provides tools to evaluate the quality of your OOA results, even if it doesn't help you arrive at them. Structured
Analysis and Design, Jackson Systems Development, and Structured Systems Development are not will suited for OOA.

## Question 5

>Describe the likely role of UML in both the Cathedral and The Bazaar. Justify your response with examples.

UML usually serves one of two purposes: communication or execution. As communicator, it exists to iron out understanding
between team members, document high level design decisions, and introduce new team members with the software. As
executor, it exists to define low level implementation details, and at times to auto-generate code itself.

The Cathedral and the Bazaar to two contrasting paradigms of software development. The Cathedral style of software
development emphasizes a small team of experts with extremely deep knowledge of the system. Whether the development is
done in the open or not, community participation is not actively encouraged; releases are infrequent, and the opinions
and contributions if this team is what matters. The Bazaar style of development emphasizes community-driven direction
and development. Releases are frequent, the product is tinkerable, and leaders must be good communicators.

The use of UML depends on the development approach a software team is using. UML as executor can only be supported by
the Cathedral, and while UML as communicator can be supported by either, it is more likely to be used by the Bazaar.
There are two reasons for this. First, using UML as executor is quite complicated. The specification itself is 796
pages. The requirements of deep knowledge of UML itself, as well as how UML is used in a particular project (which even
in an execution role can vary widely), constitutes a high barrier to entry to new developers. Therefore, UML as executor
only works with a Cathedral-style small team of experts.

Second, UML as communicator inherently suits the Bazaar style due to the role of communication. Communication is crucial
to a Bazaar project's success. Healthy Bazaar projects have a constant flux of new developers; ramping them up with
clear, basic documentation is important. UML as communicator is perfect for that need. Cathedral projects aren't as
concerned with communication; developer turnover is low, so everyone of importance already knows the system pretty well.
If UML doesn't get into the details, it doesn't provide much value.

I couldn't find a concrete example of a project using UML for execution, likely because it usually is used this way in
closed-source projects. However, the volume of [Linux kernel documentation](https://www.kernel.org/doc/html/latest/) [1]
versus [Windows kernel documentation](https://en.wikipedia.org/wiki/Architecture_of_Windows_NT) [2] (or [lack
thereof](https://docs.microsoft.com/en-us/windows/) [3]) demonstrates the importance of communication for open source
projects. Further, [this research](https://ieeexplore.ieee.org/document/7965444) [4] investigated the use of UML in open
source and found that "Collaboration seems to be the most important motivation for using UML. It benefits new
contributors and contributors who do not create models."

[1] [https://www.kernel.org/doc/html/latest/](https://www.kernel.org/doc/html/latest/)  
[2] [https://en.wikipedia.org/wiki/Architecture_of_Windows_NT](https://en.wikipedia.org/wiki/Architecture_of_Windows_NT)  
[3] [https://docs.microsoft.com/en-us/windows/](https://docs.microsoft.com/en-us/windows/)  
[4] [https://ieeexplore.ieee.org/document/7965444](https://ieeexplore.ieee.org/document/7965444)

## Question 6

>Compare the idea of proofs of completion, including partial correctness and full correctness, between the Brewers &
Beer Drinkers problem and the second problem defined in 'Proofs of Completion?' in '09-29-2020: HW Solution & Comparison
of Paradigms' (restated here).

>- Assume you operate a hand-drawn ferry attached on each side of a river by pulleys.
>- On the left side of the river, you start with `n` people, `n-1` who want to cross. You are also there, of course--and
   you don't want to cross.
>- Arguments over who gets to go first always break out, and you need some people to help you pull the ferry across.
   Therefore, if (including yourself) an even number of people are on the left bank, half may cross while half helps to
   pull.
>- If (including yourself) an odd number of people are on the left bank, you must wait for the next group to arrive
   bringing your total number of people (including yourself) to `n+((n+1)/2)`.
>- Only once you and only you remain, you may go home for the day. Can you guarantee you get to go home?

Evidence of completion or correctness is an informal proof that a program produces correct output in a finite amount of
time. Partial correctness requires only that a program produces correct output, while full correctness requires that a
program terminates (produces output in a finite period).

In the case of the Brewers & Beer Drinkers (BABD) problem, evidence of partial correctness requires a detailed analysis
of the algorithm to determine whether it can produce incorrect results. Given the small and clear definition of the
problem, this is not difficult; one essentially has to show that each rule is enforced at each state update and that the
program will only terminate when an answer has been found or shown not to exist. Evidence of full correctness requires
showing that the program terminates. Because the problem has a single finite input, one can calculate the complete set
of possible states. Even though the number may be very large, it is bounded, and if one can show that each cycle of the
program reduces the number of remaining states, and that the program terminates when remaining states have been
exhausted, he has shown that the program will produce output in a finite amount of time.

In the case of the Pulling the Ferry (PTF) variant, evidence of partial correctness is even simpler. The process is the
same: how that each rule is enforced at each state update and that the program will only terminate when an answer has
been found or shown not to exist. Because the rules are simpler, evidence of correct output is not difficult. However,
evidence of full correctness is much more difficult. Unlike BABD, PTF does not have a finite input. Because new
passengers can arrive, the potential number of states is unbounded if the program uses an empirical approach (prove a
solution by finding it or a non-solution by finding a loop). Therefore, proving full correctness requires a different
approach. Conceivably, instead of searching for a solution, problem solvers could provide a mathematical proof that
classifies values of `n` for which a solution exists. For example (certainly not correct), it may be that all even
values of `n` produce finite solutions and all odd values produce infinite loops. Then, the program can be shown to
produce correct solutions and return in finite time by implementing something like
`return n.odd? ? nil : find_solution(n)`.

As Turing's [research on the halting
problem](https://academic.oup.com/plms/article-abstract/s2-42/1/230/1491926?redirectedFrom=fulltext) [5] has shown, no
single approach can prove full correctness for any program. This means provers need to employ different strategies for
different programs. In the case of PTF, my proposed strategy is to make proof of completion tractable by pushing
additional responsibility to the proof of correct output. If a test that executes in finite time can tell whether a
solution exists for a given `n`, then we can run the test and return nil or the solution in finite time. Identifying
that test requires mathematical analysis that is beyond the scope of this analysis. Interestingly, my proposed search
for "a test that executes in finite time can tell whether a solution exists for a given `n`" sounds very much like the
halting problem itself, which is probably why Paul Erdős commented that "Mathematics may not be ready for such
problems."

[5] [https://academic.oup.com/plms/article-abstract/s2-42/1/230/1491926?redirectedFrom=fulltext](https://academic.oup.com/plms/article-abstract/s2-42/1/230/1491926?redirectedFrom=fulltext)
