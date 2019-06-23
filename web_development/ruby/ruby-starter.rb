#!/usr/bin/env ruby

# Homework 2. See
# https://cis.gvsu.edu/~kurmasz/Teaching/Courses/W19/CIS658/Homework/RubyHomework.pdf

$probnum = 0
def print_start()
  $probnum += 1
  puts "Problem #{$probnum}:"
end

def print_end()
  puts ""
end

# 1. Print out the ubiquitous "Hello, World" message.
print_start
puts "Hello, World"
print_end

# 2. For the string "Hello, World," find and print the index of the word "World".
print_start
hello_str = "Hello, World"
puts "Index of 'World' is #{ hello_str.index("World") }"
print_end

# 3. Write a loop that prints the string "This is funny monkey #1!" 10 times where the number 1
#    changes from 1 to 10. Implement this in 3 different ways using Ruby (including both "normal"
#    and functional styles).
print_start

# "normal" style
def monkey1()
  for i in 1..10 do
    puts "This is funny monkey ##{i}!"
  end
end
monkey1

# with mapped function
def monkey2(i)
  puts "This is funny monkey ##{i}!"
end
(1..10).map{ |i| monkey2(i) }

# with inline map
(1..10).map{ |i| puts "This is funny monkey ##{i}!" }

print_end

# 4. Write a simple game that generates a random number 1–1000. Let a player guess the number. If
#    the guess is wrong print out whether the guess is low or high and let the player guess again.
#    Repeat this until the user guesses the number. Award the lucky user who finally gets it right,
#    a surprise of their choice. Hint: rand(1000) will generate a random number random number 0 ..
#    999. The function gets will read a string from the keyboard that can be translated into an
#    integer.
print_start

target = rand(1000) + 1
puts "Guess at a number between 1 and 1000:"

begin
  guess = gets.to_i
  if guess < target
    puts "Too low."
  elsif guess > target
    puts "Too high."
  end
end until guess == target

puts "You get a surprise of your choice! Do you want a smiley face or an emoji?"
gets
puts "Good choice!"
puts ":)"

print_end

# 5. Given the following array definition in Ruby, generate a method named convert_to_type_strings
#    that takes the array as input and returns a second array where each element in the array
#    corresponds to a string representation of the type (e.g. class) of each element in the array.
#
#    data = ['hello', 0, :sym, 3.4, "world", true, [0..3]]
#
#    e.g, the method should return the following array:
#
#    ["String", "Integer", "Symbol", "Float", "String", "TrueClass", "Array"]
print_start

data = ['hello', 0, :sym, 3.4, "world", true, [0..3]]
p data.map{ |item| item.class }

print_end

# 6. Augment the existing Array class in Ruby so that it has a method named convert_to_type_strings
#    that does exactly what the method in question #5 above does but using the array’s internal data
#    (e.g. you cannot pass the array as a parameter). Hint: investigate the map method defined by
#    Array. When you are finished, test with the following code:
#
#    data = ['hello', 0, :sym, 3.4, "world", true, [0..3]]
#    data.convert_to_type_strings
#
#    e.g. the call to convert_to_type_strings on the array should produce:
#
#    ["String", "Integer", "Symbol", "Float", "String", "TrueClass", "Array"]
print_start

Array.class_eval {
  def convert_to_type_strings
    map{ | item | item.class }
  end
}

data = ['hello', 0, :sym, 3.4, "world", true, [0..3]]
p data.convert_to_type_strings

print_end

# 7. Given an array of symbol values representing an ensemble, write a method called
#    tabulate_sections that produces a hash that maps a string representation of the section (e.g.
#    type) the instrument belongs to, to the number of instruments in that section of the ensemble.
#    For example, the input array:
#
#    ensemble = [:piano, :clarinet, :oboe, :trumpet, :frenchhorn, :violin, :piano, :oboe, :cello]
#
#    produces the output hash:
#
#    {"percussion"=>2, "woodwind"=>3, "brass"=>2, "strings"=>2}
#
#    You may assume that you only need to deal with the instrument and instrument types referenced
#    in this example.
print_start

def tabulate_sections(list)
  instr_types = { :piano => "percussion",
                  :clarinet => "woodwind", :oboe => "woodwind",
                  :trumpet => "brass", :frenchhorn => "brass",
                  :violin => "strings", :cello => "strings"
                }
  sections = { "percussion" => 0, "woodwind" => 0, "brass" => 0, "strings" => 0 }

  list.map{ |i|
    type = instr_types.fetch(i)
    sections[type] = sections.fetch(type) + 1
  }

  sections
end

ensemble = [:piano, :clarinet, :oboe, :trumpet, :frenchhorn, :violin, :piano, :oboe, :cello]
p tabulate_sections(ensemble)

print_end
