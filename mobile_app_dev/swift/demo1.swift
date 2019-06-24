
var probnum = 1
func printProblemHeader() {
    print("\nProblem #\(probnum):")
    probnum += 1
}

// Problem 1 - Brian
printProblemHeader()

let myString: String
myString = "Hello!"
print(myString)

var cost: Double
cost = 3.14

let cnt: Int
cnt = 2

var shouldWe: Bool
shouldWe = true

let hexadecimalInteger = 0xB

let binaryInteger = 0b1010

// Problem 2 - Brian
printProblemHeader()

let multStr = "3.14 * 5.1234 = \(3.14 * 5.1234)"
print(multStr)

// Problem 3 - Brian
printProblemHeader()

var example = ["Queen", "Worker","Drone"]

print("The first item is \(example[0])")

example.append("Honey")

example += ["are","us"]

// Problem 4 - Kevin
printProblemHeader()

for item in example {
    print("\(item) is an item.")
}

for (index, item) in example.enumerated() {
    print("Item #\(index) is \(item)")
}

// Problem 5 - Brian
printProblemHeader()

var authorReadability = [String: Double]()

authorReadability["Mark Twain"] = 8.9
authorReadability["Nathanial Hawthorne"] = 5.1
authorReadability["John Steinbeck"] = 2.3
authorReadability["C.S Lewis"] = 9.9
authorReadability["Jon Krakaur"] = 6.1

print(authorReadability)

// Problem 6 - Kevin
printProblemHeader()

print("John Steinbeck has a score of \(authorReadability["John Steinbeck"]!)")

authorReadability["Eric Larson"] = 9.2

if authorReadability["Jon Krakaur"]! > authorReadability["Mark Twain"]! {
    print("John Krakaur is more readable than Mark Twain")
}
else {
    print("Mark Twain is more readable than John Krakaur")
}

// Problem 7 - Kevin
printProblemHeader()

for (author, score) in authorReadability {
    print("\(author) has a readability score of \(score)")
}

// Problem 8 - Kevin
printProblemHeader()

for index in 1...10 {
    print("\(index)")
}

// Problem 9 - Brian
printProblemHeader()
for index in (1...10).reversed() {
 print("\(index)")
}

// Problem 10 - Kevin
printProblemHeader()

func MultWithAddition(m1:Int, m2:Int) -> Int {
  var ans = 0;
  for _ in 0..<m1 {
    ans += m2
  }
  return ans
}
print("5 * 7 = \(MultWithAddition(m1:5, m2:7))")
