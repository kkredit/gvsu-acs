# Brewers & Beer Drinkers Puzzle

## Assignment

Create a program in the language of your preference that:

- Accepts three inputs (Brewers (B), Beer Drinkers (D), and Boat Capacity (C)), and
- Outputs a series of boat trips and passengers to allow all brewers and beer drinkers to traverse to the opposite bank,
  if such a series exists.

B brewers and D beer drinkers must cross the Grand River using a boat, in order to bring the beer to a shop where the
beer drinkers can legally purchase it. However, you must maintain the following constraints:

- The boat can only carry C people at a time.
- The boat cannot cross the river without someone to row it.
- The number of beer drinkers (D) can never outnumber the brewers (B) on either bank or in the boat. Otherwise the beer
  drinkers will steal the beer and drink it without purchasing it.

The boat and all people start on the side of the river without the store.

Output should list the number of brewers and beer drinkers in each boat trip. For example, given three boat trips:

- 1 Brewer and 1 Beer Drinker ride to the other side.
- 0 Brewers and 1 Beer Drinker ride to the other side.
- 2 Brewers and 0 Beer Drinkers ride to the other side.

The output should be as follows:

```sh
B=1, D=1
B=0, D=1
B=2, D=0
```

If there is no set of boat trips that brings all people to the store side of the river, output nothing.

## Solution Description

My solution implementation consists of three parts:

1. `puzzle.rb`, a module that defines several classes that collectively embody the nature of the puzzle
2. `main.rb`, a script that handles user input and loads and invokes the module
3. `run.sh`, a shell script that runs the application for several sample inputs

`main.rb` and `run.sh` are helpful means to isolate user input from the puzzle logic and specific test values from the
generic runner. This isolation ensures each file is serving only one purpose.

`puzzle.rb` defines several classes to break the data and functions down in an organized and understandable way:

- `Move`: object that represents a boat trip
- `Side`: object that represents a side of the river
- `State`: object that represents a possible state of the puzzle, including the status of both sides and a series of
  moves
- `Puzzle`: object that represents the puzzle as a whole, including the starting conditions as specified during
  initialization and a lengthening collection of states that narrows down to a solution

The algorithm to find a solution is a breadth-first search, going down every possible route until it reaches an illegal
state or it is performing so poorly that it will clearly not result in the optimal solution. When a solution is found,
its sequence of moves are printed out.

Breadth-first searching was chosen so that it would automatically find a solution with the least number of moves. Basic
pruning of poorly performing search paths was added to improve performance, since some search paths are legal yet
clearly suboptimal--you can legally ferry the same passengers back and forth, but that brings you no closer to a
solution. I initially limited moves across to always have close the max capacity and moves back to have close to the
minimum capacity, but the nature of the problem requires short term sacrifices to find a solution, so I had remove that
constraint.

I tried to keep the puzzle module as "pure" as possible by limiting mutation of objects. Instead of changing objects
over time, which can lead to confusing errors, I instead created copies of objects with whatever modifications were
required, and am relying on the garbage collector to handle the memory. I do use a global `$log` variable, and did not
concern myself with I/O side effects.
