var url = "http://localhost:8080/api/games"
var urlLeader = "http://localhost:8080/api/leaderboard"
var app = new Vue({
    el:'#app',
    data:{
        login: "false",
        user:"",
        games:[],
        scores:[],
    }
})
fetch(urlLeader)
.then(function(myData){
    return myData.json();
})
.then(function(myData){
    data = myData;
    app.scores = data;
})

fetch(url)
.then(function(myData){
    return myData.json();
})
.then(function(myData){
    data = myData;
    app.games = data.games;
    done();
})

//Crear tabla de datos
function done(){
    var table = document.getElementById('salvo-table');
    table.innerHTML="";
    var tableContent = createTableContent(app.games);
    table.innerHTML = tableContent;
}
//crear el contenido de la tabla
function createTableContent(games){
    var table = '<thead class="thead"><tr><th>DateGame</th><th>Player1</th><th>Player2</th><th>Winner</th></tr></thead>';
    table +='<tbody>';
    games.forEach(function(game){
        table += '<tr>';
        table += '<td>'+new Date(game.created).toLocaleString()+'</td>';
        if(game.gamePlayers[0] === null){
            table += '<td>'+"N/A"+'</td>';
        }else{
            table += '<td>'+game.gamePlayers[0].player.email+'</td>';
        }
        if(game.gamePlayers[1] === null){
            table += '<td>'+"N/A"+'</td>';
        }else{
            table += '<td>'+game.gamePlayers[1].player.email+'</td>';
        }
        table += '<td></td>';
        table += '</tr>';
    });
    table += '</tbody>';
    return table;
}

$("#loginButton").click(
    function(){
        data ={username: document.forms['loginForm'].elements['username'].value, 
                password: document.forms['loginForm'].elements['password'].value};
        login(data, 'true');
        $("#loginForm").hide();
    }
)

$("#logoutButton").click(
    function(){
        $.post("/api/logout")
        .done(function(){
            location.reload();
            $("#logoutForm").hide();
        })
    }
)
$("#signinButton").click(
    function(){
        data = {username: document.forms['loginForm'].elements['username'].value,
                password: document.forms['loginForm'].elements['password'].value};
        signin();
    }
)

function login(data, newStatus){
    $.post("/api/login", data)
    .done(function(){
        app.login = newStatus;
        
    })
    .fail(function(){
        alert("User does not exist");
        location.reload();
    })
}

function signin(data){
    $.post("/api/players", data)
    .done(function(){
        location.reload();
    })
    .fail(function(){
        alert("Username already exists");
    })
}

function doSomething(){
    alert("Haciendo algo");
}

function createNewGame(){
    $.post("/api/games")
    .done(function(){
        alert("Game created");
    })
    .fail(function(){
        alert("error creating game");
    })
}