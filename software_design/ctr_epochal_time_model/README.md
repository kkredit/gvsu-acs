# Brewers & Beer Drinkers using the Epochal Time Model

## Assignment

Update your design for the solution of the [Brewers and Beer Drinkers problem](../crossing_the_river/README.md)
to roughly fit the Epochal Time Model framework defined in
[this talk by Rich Hickey](https://www.infoq.com/presentations/Are-We-There-Yet-Rich-Hickey/).

Specifically you will:

- Update your design to fit the time model presented,
- Identify what parts of your design (likely class/objects or functions) act as each of the terms defined (value, state,
  identity, process events, observers),
- Ensure your solution returns the shortest set of moves, and
- Discuss how this impacted your original design (improvements or parts made worse).

## Updated Solution Discussion

Updating my previous solution to use the Epochal Time Model was eased by the fact that I was already treating almost all
data as immutable. Since it uses breadth-first search, the code also already produces the shortest set of moves should a
solution exist. Even so, I still made two major changes.

First, I removed extraneous features like debug logging and timing. This was initially to make allegiance to the pattern
easier to see, but eventually I realized that I don't know how well they fit in this model anyway. How impure can
functions be before they are no longer "process events"? Is one console log too much? Strictly implementing this model
raises questions about how to map model elements to real programming languages and features. One can certainly argue
things should be this way. There are benefits to instrumentation being removed from the logic of the program, and
instead taking the form of tests and strong debugging capabilities. After all, I experimented with Clojure, the language
created by the creator of this model, in [another project](https://github.com/kkredit/scramble-squares-solver) and it is
very much this way.

Second, I removed all mutability entirely in the puzzle solving classes (Move, Side, State, and Puzzle, though it only
actually required changes to Puzzle). This meant replacing the mutable `@states` variable with recursion. Although the
way it was being used still made sense within the Epochal Time Model, forcing this change allowed for greater
simplification and a more elegant solution.

See comments in the code for detailed mappings of objects and functions to Hickey's concepts of value, state, identity,
process events, and observers. The heart of the `state -> process -> state -> observe` cycle takes place in the `solve`
method: the `states` identity is observed for dead ends or solutions, then recursively processed by applying next
possible moves.

In summary, the code became simpler and more elegant, yet less debuggable. Hickey's talk was excellent, particularly in
the first half when he was explaining the nature of reality, time, and computing. Unfortunately the talk concluded with
something like "Now how to reconcile this with OO and real languages...". I still have that question after applying it
to this puzzle solving program. It works and produces elegant code, but when pushed to the limits, it's not clear how it
fits with certain practical concerns.
