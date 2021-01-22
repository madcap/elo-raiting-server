$(document).ready(function () {

    var backend = 'http://eloratingserver-env.eba-mbnj37nm.us-east-1.elasticbeanstalk.com/';
    //var backend = 'http://localhost:8080/';
    var getMatchupEndpoint = 'rating/v1/match/domain/hades-boons';
    var reportMatchupEndpoint = '/rating/v1/match/domain/hades-boons/matchId/$MATCHUP_ID$/winningPlayerId/$WINNER_ID$';
    var rankingEndpoint = 'rating/v1/list/domain/hades-boons';
    var leftPlayerId = '';
    var rightPlayerId = '';

    var currentMatchup = {
        matchId:"",
        domain:"hades-boons",
        player1:{
            id:"",
            domain:"hades-boons",
            name:"",
            description:"",
            rating:1500,
            wins:0,
            losses:0
        },
        player2:{
            id:"",
            domain:"hades-boons",
            name:"",
            description:"",
            rating:1500,
            wins:0,
            losses:0
        }
    };

    function fetchNewMatchup() {
        $.get((backend + getMatchupEndpoint), function (data, status) {
            //console.log("status = " + status);
            //console.log(JSON.stringify(data));
            currentMatchup = data;
            populateChoiceTable();
        });
    }

    function populateChoiceTable() {
        console.log(JSON.stringify(currentMatchup));
        leftPlayerId = currentMatchup.player1.id;
        rightPlayerId = currentMatchup.player2.id;
        $("#tableLeftName").text(currentMatchup.player1.name);
        $("#tableRightName").text(currentMatchup.player2.name);
        $("#tableLeftDescription").text(currentMatchup.player1.description);
        $("#tableRightDescription").text(currentMatchup.player2.description);
    }

    // on page load populate the first matchup
    fetchNewMatchup();

    $("#tableLeftChoiceButton").click(function() {
        //alert( "you chose left side: " + leftPlayerId );
        console.log("you chose: " + currentMatchup.player1.name)
        reportMatchupResult(leftPlayerId);
    });

    $("#tableRightChoiceButton").click(function() {
        //alert( "you chose right side: " + rightPlayerId );
        console.log("you chose: " + currentMatchup.player2.name)
        reportMatchupResult(rightPlayerId);
    });

    function reportMatchupResult(winningPlayerId) {
        let url = backend + (reportMatchupEndpoint.replace('$MATCHUP_ID$', currentMatchup.matchId).replace('$WINNER_ID$', winningPlayerId));
        $.post( url, function( data ) {
            //console.log("post: " + JSON.stringify(data));
            fetchNewMatchup();
        });
    }

    $("#loadRankings").click(function() {
        $.get((backend + rankingEndpoint), function (data, status) {
            //console.log("status = " + status);
            //console.log(JSON.stringify(data));
            let players = data.players;
            players.sort((a, b) => (a.rating > b.rating) ? 1 : -1);
            let html = "";
            players.reverse().forEach(function(player) {
                html = html + "<tr><th>" + player.name + "</th><th>" + player.rating + "</th><th>" + player.wins + "</th><th>" + player.losses + "</th></tr>"
                    //html + player.name + "  " + player.rating + "<br/>";
            });
            $("#rankingTableBody").html(html);
        });
    });


});