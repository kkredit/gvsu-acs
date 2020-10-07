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
#   solve(states) is a process event
#   solution(states) is an observation
#   next_states(states) is a process event
#   possible_moves() is a process event
class Puzzle
  def initialize(options)

    # @possible_moves is a value that comprises part of a Puzzle's state
    @possible_moves = possible_moves(options[:capacity])

    # @init_state is a value that comprises part of a Puzzle's state
    @init_state = State.new(
      Side.new(options[:brewers], options[:drinkers]),
      Side.new(0, 0),
      true,
      []
    )
  end

  def solve(states = [@init_state])
    # This is the heart of the Epochal Time Model cycle:
    #   1. Identity Puzzle begins with state `[@init_state]`
    #   2. `states` progresses to its next value via the pure next_states() processing event
    #   3. Cycle is repeated until a dead end or a solution is observed in `states`
    states.none? ? nil : solution(states) || solve(next_states(states))
  end

  private

  def solution(states)
    states.select {|s| s.solved?}.first
  end

  def next_states(states)
    states.flat_map {|s| @possible_moves.map {|m| s.apply_move(m)}}
      .compact
      .uniq {|s| [s.start, s.dest]}
  end

  def possible_moves(cap)
    (1..cap).flat_map {|c| (0..c).map {|b| Move.new(b, c - b)}}
      .select {|m| m.legal?}
  end
end