# A Move is a value
#   print() is an observation for the console
#   legal?() is an observation for the caller
#   negate() is a process event
Move = Struct.new(:b, :d) do
  def print
    puts "B=#{b}, D=#{d}"
  end

  def legal?
    b.zero? || b >= d
  end

  def negate
    Move.new(-b, -d)
  end
end

# A Side is a value
#   legal?() is an observation for the caller
#   count() is an observation for the caller
#   apply_move(m) is a process event
Side = Struct.new(:b, :d) do
  def legal?
    !(b.negative? || d.negative?) && (b.zero? || b >= d)
  end

  def count
    b + d
  end

  def apply_move(m)
    Side.new(b + m.b, d + m.d)
  end
end

# A State is a value
#   print_moves() is an observation for the console
#   solved?() is an observation for the caller
#   apply_move(move) is a process event
State = Struct.new(:start, :dest, :at_start?, :moves) do
  def print_moves
    moves.map {|m| m.print }
  end

  def solved?
    start.count.zero?
  end

  def apply_move(move)
    return nil if move == moves.last
    new_start = start.apply_move(at_start? ? move.negate : move)
    new_dest = dest.apply_move(at_start? ? move : move.negate)
    return nil if new_dest.count * 3 < moves.count + 1
    return nil unless new_start.legal? && new_dest.legal?
    State.new(new_start, new_dest, !at_start?, moves + [move])
  end
end

# A Puzzle is an identity
#   initialize() is an special process event which creates the identity
#   solve() is a process event
#   print_solution() is an observation for the console
#   solution() is an observation for the caller
#   next_states() is a process event
#   possible_moves() is a process event
class Puzzle
  def initialize(options)

    # @possible_moves is a value that comprises part of a Puzzle's state
    @possible_moves = possible_moves(options[:capacity])

    # @states is itself an identity that comprises part of a Puzzle's state
    #   it is processed many times by next_states() in the solve() method
    @states = [State.new(
      Side.new(options[:brewers], options[:drinkers]),
      Side.new(0, 0),
      true,
      []
    )]
  end

  def solve
    # This is the heart of the Epochal Time Model cycle:
    #   1. Identity Puzzle begins with state @states
    #   2. @states progresses to its next value via the pure next_states() processing event
    #   3. Cycle is repeated until a solution or dead end is observed in the state
    @states = next_states until !solution.nil? || @states.none?
    self
  end

  def print_solution
    solution.print_moves unless solution.nil?
  end

  private

  def solution
    @states.select {|s| s.solved?}.first
  end

  def next_states
    @states.flat_map {|s| @possible_moves.map {|m| s.apply_move(m)}}
      .compact
      .uniq {|s| [s.start, s.dest]}
  end

  def possible_moves(cap)
    (1..cap).flat_map {|c| (0..c).map {|b| Move.new(b, c - b)}}
      .select {|m| m.legal?}
  end
end