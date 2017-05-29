//https://www.youtube.com/watch?v=823tUKxMoOM

var tot = new Array();
var actual_pos  = 0;

$( document ).ready(function() {
    $("#main-content-section").hide();
    $("#btn-section").hide();
	
	//alert(parseURL());	

});

function parseURL(){
	
	var str = document.URL;
	var ar = new Array();
	ar = str.split("?");
	return ar[1];

}

var openFile = function(event) {
    
    var input = event.target;
    var reader = new FileReader();

    reader.onload = function(){
        var text = reader.result;
        var splitted = text.split(/\n/)
        console.log("Total size: " + splitted.length);

        var total = splitted.length;

        for ( var  i = 0 ; i < total ; i++){
            tot[i] = splitted[i];
        }

    };

    $("#prev").addClass("disabled");
    $("#attach").addClass("disabled");
    $("#btn-section").show();
    reader.readAsText(input.files[0]);
};


function previousButton(){
    if(actual_pos - 1 != -1){
        actual_pos -= 1;
        display();
        $("#next").removeClass("disabled");
    }

    if(actual_pos == 0){
        $("#prev").addClass("disabled");
    }
    

}

function nextButton(){
    if(actual_pos != tot.length - 2){
        display();
        actual_pos += 1;
        $("#prev").removeClass("disabled");
    }else{
        $("#next").addClass("disabled");

    }

    $("#main-content-section").show();

}

function display(){
    displayInCard(1,tot[actual_pos + 0]);
    displayInCard(2,tot[actual_pos + 1]);
    displayInCard(3,tot[actual_pos + 2]);
}



function displayInCard(idcard, str){
    console.log(str);
    var n = str.split('=');
    console.log(n[0]);
    var time = n[0];
    var rest = n[1].split('|');
    $('#clockv'+idcard+'').text(time);
    $('#c1v'+idcard+'').text(rest[0]);
    $('#c2v'+idcard+'').text(rest[1]);
    $('#iov'+idcard+'').text(rest[2]);
}

function refreshSite(){
    location.reload();
}
