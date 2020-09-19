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

Side = Struct.new(:b, :d) do
  def legal?
    !(b.negative? || d.negative?) && (b.zero? || b >= d)
  end

  def count
    b + d
  end

  def apply_move(move)
    Side.new(b + move.b, d + move.d)
  end
end

State = Struct.new(:start, :dest, :cap, :at_start?, :moves) do
  def print_moves(i, ref_state=nil)
    ref_state&.debug_print
    moves[i]&.print
    print_moves(i + 1, ref_state&.apply_move(moves[i])) if moves[i]
  end

  def debug_print
    $log.debug "
      ---------------
      B: %2d |   | %2d
            |%s%1d%s|
      D: %2d |   | %2d
      ---------------" % [start.b, dest.b,
                          at_start? ? '' : '  ', cap, at_start? ? '  ' : '',
                          start.d, dest.d]
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
    State.new(new_start, new_dest, cap, !at_start?, moves + [move])
  end
end

class Puzzle
  def initialize(options)
    @capacity = options[:capacity]
    @init_state = State.new(
      Side.new(options[:brewers], options[:drinkers]),
      Side.new(0, 0),
      @capacity,
      true,
      []
    )
    @states = [@init_state]
    @possible_moves = possible_moves
    $log.debug @possible_moves
  end

  def solve
    t_start = Time.now
    @states = next_states until !solution.nil? || @states.none?
    duration = Time.now - t_start
    if solution.nil?
      $log.info "No solution found (#{duration}s)"
    else
      $log.info "Solved in #{solution.moves.count} moves (#{duration}s):"
      solution.print_moves(0, ref_state=@init_state)
    end
  end

  private

  def solution
    @states.select {|s| s.solved?}&.first
  end

  def next_states
    @states.flat_map {|s| @possible_moves.map {|m| s.apply_move(m)}}
      .compact
      .uniq {|s| [s.start, s.dest]}
  end

  def possible_moves
    (1..@capacity).flat_map {|c| (0..c).map {|b| Move.new(b, c - b)}}
      .select {|m| m.legal?}
  end
end