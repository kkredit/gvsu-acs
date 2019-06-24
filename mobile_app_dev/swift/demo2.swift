
var probnum = 1
func printProblemHeader() {
    print("\nProblem #\(probnum):")
    probnum += 1
}

//Problem 1 Kevin
printProblemHeader()
var authorReadability = [
    "Mark Twain": 8.9,
    "Nathanial Hawthorne": 5.1,
    "John Steinbeck": 2.3,
    "C.S Lewis": 9.9,
    "Jon Krakaur": 6.1,
]
// a for loop would be much more natural here
var index = 0
var sum = 0.0;
while authorReadability.count > 0 {
    let pair = authorReadability.first!
    sum += authorReadability.removeValue(forKey:pair.key)!
    index += 1
}
print("The average readability score is \(sum / Double(index))")

//Problem 2 (dep 1) Brian
printProblemHeader()
if sum < 5 {
    print("The average readability is Low")
}else if sum >= 5 && sum < 7 {
    print("The average readability is Moderate")
}else {
    print("The average readability is High")
}

//Problem 3 Kevin
printProblemHeader()
var count = 4
var strOut :String
switch count {
case 0:
    strOut = "none"
case 1...3:
    strOut = "a few"
case 4...9:
    strOut = "several"
case 10...99:
    strOut = "tens of"
case 100...999:
    strOut = "hundreds of"
case 1000...999999:
    strOut = "thousands of"
default:
    strOut = "unknown size"
}
print("Example: \(count) is \"\(strOut)\"")

//Problem 4 (dep 3) Kevin
printProblemHeader()
func verbalizeNumber(number:Int) -> String {
    var result :String
    switch number {
    case 0:
        result = "none"
    case 1...3:
        result = "a few"
    case 4...9:
        result = "several"
    case 10...99:
        result = "tens of"
    case 100...999:
        result = "hundreds of"
    case 1000...999999:
        result = "thousands of"
    default:
        result = "unknown size"
    }
    return result
}
print("Example: verbalizeNumber(number:20) is \"\(verbalizeNumber(number:20))\"")

//Problem 5 (dep 4) Kevin
printProblemHeader()
for number in stride(from:1, to:150, by:10) {
    print("One could describe \(number) as \"\(verbalizeNumber(number:number))\"")
}

//Problem 6 (dep 3) Brian
printProblemHeader()
func verbalizeAndShoutNumber(number:Int) -> String {
    var result :String
    switch number {
    case 0:
        result = "none"
    case 1...3:
        result = "a few"
    case 4...9:
        result = "several"
    case 10...99:
        result = "tens of"
    case 100...999:
        result = "hundreds of"
    case 1000...999999:
        result = "thousands of"
    default:
        result = "unknown size"
    }
    return result.uppercased()
}
print("Example: verbalizeNumber(number:20) is \"\(verbalizeAndShoutNumber(number:20))\"")

//Problem 7 (dep 5) Brian
printProblemHeader()
func expressNumbersElegantly(max: Int, verbalizeFunction: (Int) -> String)  -> String{
  var finalElegant :String
  finalElegant = ""
  for max in stride(from:1, to:max, by:10) {
    let elegant =  "One could describe \(max) as \"\(verbalizeFunction(max))\" /"
    finalElegant.append(elegant)
  }
  return finalElegant
}

var test = expressNumbersElegantly(max:20,verbalizeFunction:verbalizeNumber)
var test1 = expressNumbersElegantly(max:20,verbalizeFunction:verbalizeAndShoutNumber)
print(test)
print(test1)


//Problem 8 (dep 5) Brian
printProblemHeader()
func expressNumbersVeryElegantly(topNumber max: Int, function verbalizeFunction: (Int) -> String)  -> String{
   var finalElegant :String
   finalElegant = ""
 for max in stride(from:1, to:max, by:10) {
    let elegant =  "One could describe \(max) as \"\(verbalizeFunction(max))\"/"
    finalElegant.append(elegant)
  }
   return finalElegant
}

var test2 = expressNumbersVeryElegantly(topNumber:20,function:verbalizeNumber)
var test3 = expressNumbersVeryElegantly(topNumber:20,function:verbalizeAndShoutNumber)
print(test2)
print(test3)

//Problem 9 Kevin
printProblemHeader()
var famousLastWords = [
    "the cow jumped over the moon.",
    "three score and four years ago",
    "lets nuc 'em Joe!",
    "ah, there is just something about Swift"
]
let uppered = famousLastWords.map { $0.prefix(1).uppercased() + $0.dropFirst() }
print(uppered)

//Problem 10 Brian
printProblemHeader()
func filterNumbers(condition: (Int) -> Bool, numbers: [Int]) -> [Int] {
 var filteredList = [Int]()
 for num in numbers {
  if condition(num){
     filteredList.append(num)
        }
    }
 return filteredList
}

let filteredata = filterNumbers(condition: { (num) -> Bool in return num < 8},numbers:[1,2,3,4,5,6,7,8,9,10])
print(filteredata)
