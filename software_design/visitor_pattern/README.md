# The Visitor Pattern

The advantage of the visitor pattern is that it makes modifying operations on a data structure easy, while it makes
modifying the data structure itself difficult.

One situation in which this would be valuable is in widely shared data structures or data sets. Particularly when a
large group of developers or projects use a data structure, but even if it's only an individual that uses it in several
ways, allowing new ways to access and use the structure is helpful in order to cover everyone's use case--and to enable
use cases that haven't been considered yet--without frequent modifications to this shared code. A downside (besides
confusion to the uninitiated) is that if there are many visitors and each one needs to handle every variant of a data
structure, then adding a variant becomes very costly. This was the essence of the "remainder" Expression example, but it
is also true here.

Another area this is useful is in languages which feature functions as first-class entities. In these languages, the
visitor pattern is essentially achieved through passing curried functions, closures, or callbacks. This may not
technically qualify as the Visitor pattern because it doesn't use double dispatch, so it may actually be an example of
Peter Norvig's argument that many "design patterns" are unnecessary in languages with different paradigms. However,
awareness of the pattern in its higher level form (what problem is it trying to solve and how) can still lead to better
design, even if the language-based implementation details differ.

The basic callback example may be in a frontend Javascript component. It can be used in many different ways depending on
the context; instead of modifying the component to handle each situation, it executes callbacks (accepts visitors) at
useful junctures.
