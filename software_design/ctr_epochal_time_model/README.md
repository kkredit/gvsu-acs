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

<!--
- Positive and negative impacts to design
  - Fortunately didn't take much work since I was already treating data structures as immutable (though I didn't go as
    far as moving to immutable data structures [like this](https://github.com/immutable-ruby/immutable-ruby))
  - Split up `solve` and `print_solution` to increase separation of concerns
  - Removed debugging info to make the pattern clearer
  - Raises the question--how do we instrument code in this model? It makes it less clear whether we're following the
    model (I had a global variable, more instance variables, before). But maybe you could argue this is the way it
    should be? Instrumentation should be removed from the logic of the program. In the form of tests and strong
    debugging capabilities (Clojure is very much this way, after all).
- Application of terms
  - value
  - state
  - identity
  - process events
  - observers
-->
