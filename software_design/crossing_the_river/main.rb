#!/usr/bin/env ruby

require 'optparse'
require 'logger'
require_relative 'puzzle'

$log = Logger.new(STDOUT)
$log.level = Logger::FATAL

options = {}
OptionParser.new do |opt|
  opt.on('-b', '--brewers B', Integer, 'The number of beer brewers') { |o| options[:brewers] = o }
  opt.on('-d', '--drinkers D', Integer, 'The number of beer drinkers') { |o| options[:drinkers] = o }
  opt.on('-c', '--capacity C', Integer, 'Capacity of the boat') { |o| options[:capacity] = o }
  opt.on('-v', '--verbose', 'Run with verbose printing') { $log.level = Logger::DEBUG }
end.parse!

unless ([:brewers, :drinkers, :capacity] - options.keys).empty?
  raise 'Brewers, Drinkers, and Capacity must each be specified'
end

$log.info options

Puzzle.new(options).solve