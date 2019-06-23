  "use strict";

var currentDate = new Date();

/**
 * Updates calendar.html to display the desired month.
 *
 * @param date a JavaScript Date object set to a day in the month to be displayed.
 */
var update = function (date) {
  currentDate = date;
  console.log(date);
    var months = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var daysInCurrentMonth = new Date(date.getFullYear(), date.getMonth() + 1, 0).getDate();

    document.getElementById("monthHeader").innerHTML= months[date.getMonth()];
    document.getElementById("yearHeader").innerHTML = date.getFullYear();

    var days = document.getElementsByTagName("td");
    var dayFirstIndex = 2;
    var daysTotal = 6 * 7;

    var firstDayOfMonth = date.getUTCDay();
    var dayOfMonth = 1;

    for(var i = 0; i < daysTotal; i++) {
      if (i < firstDayOfMonth || dayOfMonth > daysInCurrentMonth) {
        days[dayFirstIndex + i].innerHTML = "&nbsp;";
      }
      else {
        days[dayFirstIndex + i].innerHTML = dayOfMonth;
        dayOfMonth++;
      }
    }
}

var goNextMonth = function() {
  console.log(currentDate);
  // Calculate the previous and next month
  // (You may use this for adding links to the left arrow)
  var nextMonth = currentDate.getMonth() + 1;
  var nextYear = currentDate.getFullYear();
  if (nextMonth >= 12) {
      // Remember:  Months are numbered beginning with 0.
      nextMonth = 0;
      nextYear++;
  }
  update(new Date(nextYear, nextMonth));
}

var goLastMonth = function() {
  // Calculate the previous and next month
  // (You may use this for adding links to the left arrow)
  var lastMonth = currentDate.getMonth() - 1;
  var lastYear = currentDate.getFullYear();
  if (lastMonth < 0) {
      // Remember:  Months are numbered beginning with 0.
      lastMonth = 11;
      lastYear--;
  }
  update(new Date(lastYear, lastMonth));
}
