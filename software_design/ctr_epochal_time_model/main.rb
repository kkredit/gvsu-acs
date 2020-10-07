#!/usr/bin/env ruby

require 'optparse'
require_relative 'puzzle'

# `options` is an identity whose state is updated by OptionParser's process events
# An observation on `options` conditionally alters the entire process's state by aborting if options are missing
options = {}
OptionParser.new do |opt|
  opt.on('-b', '--brewers B', Integer, 'The number of beer brewers') { |o| options[:brewers] = o }
  opt.on('-d', '--drinkers D', Integer, 'The number of beer drinkers') { |o| options[:drinkers] = o }
  opt.on('-c', '--capacity C', Integer, 'Capacity of the boat') { |o| options[:capacity] = o }
end.parse!

unless ([:brewers, :drinkers, :capacity] - options.keys).empty?
  raise 'Brewers, Drinkers, and Capacity must each be specified'
end

# This line combines three steps:
#   1. Initialze a Puzzle identity
#   2. Advance the Puzzle's state using the solve() process event
#   3. Observe the Puzzle's state using the print_solution() observation
Puzzle.new(options).solve.print_solution