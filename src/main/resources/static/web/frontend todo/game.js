var app = new Vue({
  el: '#app',
  data: {
    rows: ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", ],
    columns: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
  }
})
var url = "http://localhost:8080/api/game_view/nn"
fetch(url)
  .then(function (myData) {
    data = myData;
  })

function createTableShipLocations() {
  var table = document.getElementById('ship_locations_table');
  table.innerHTML = "";
  var tableContent = createTableShipContent(app.rows, app.columns);
  table.innerHTML = tableContent;
}

function createTableShipContent(rows, columns) {
  var table = '<thead><tr><th></th>';
  columns.forEach(function (column) {
    table += '<th>' + column + '</th>';
  });
  table += '</tr></thead>';
  table += '<tbody>'


  rows.forEach(function (row) {
    table += '<tr>';
    table += '<td >' + row + '</td>';
    table += '</tr>';
  })
  /*table += '<td></td>';
  table += '<td></td>';
  table += '<td></td>';
  table += '<td></td>';
  table += '<td></td>';
  table += '<td></td>';
  table += '<td></td>';
  table += '<td></td>';
  table += '<td></td>';*/

  for (var i = 1; i <= 10; i++) {
    table += '<td id="A"+i></td>'
  }
  table += '</tr>';

  table += '</tbody>';
  return table;
}