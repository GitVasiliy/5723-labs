function showList(obj) 
{
	var list = obj.parentNode;
	if (list === null) {
		return;
	}
	var showDropList = list.querySelector("ul.showDropList");
	if(showDropList == null) {
		return;
	}
	if(showDropList.style.display == "none") {
		obj.innerHTML = "[-]";
		showDropList.style.display = "block";
	}
	else {
		obj.innerHTML = "[+]";
		showDropList.style.display = "none";
	}
}


var showLists = document.querySelectorAll("li.dropdown > ul.showDropList"); 
var showManagers = document.querySelectorAll("li.dropdown > a.show");

for(var i = 0; i < showLists.length; i++) 
{
	showLists[i].style.display = "none";
}

for(var i = 0; i < showManagers.length; i++) 
{
	showManagers[i].innerHTML = "[+]";
}

function check(obj) {
	//var list = obj.parentNode;
	var xhttp = new XMLHttpRequest();
	var body = "lvl1="+encodeURIComponent(document.getElementById("first").value)+"&lvl2="+encodeURIComponent(document.getElementById("second").value);
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var f = Number(document.getElementById("first").value);
			var s = Number(document.getElementById("second").value);
			console.log(document.getElementById("first").value + " " + document.getElementById("second").value);
			document.getElementById("first").value = '';
			document.getElementById("second").value = '';
			var firstNode = document.querySelectorAll("li.dropdown");
			console.log(firstNode);
			if (f != 0 && s == 0) // ready
			{
				var firstNode = document.querySelectorAll("li.dropdown")[f - 1];
				firstNode.parentNode.removeChild(firstNode);
			}
			if (f == 0 && s != 0) // ready
			{
				console.log("errJS");
				document.write("error");

			}
			if (f != 0 && s != 0) // ready
			{
				var secondNode = document.querySelectorAll("li.dropdown > ul.showDropList")[f - 1];
				var l1 = document.querySelectorAll("li.dropdown")[f - 1];
				console.log(l1);
				var l2 = l1.querySelectorAll("ul.showDropList > li")[s - 1];
				console.log(l2);
				l2.parentNode.removeChild(l2);


				//console.log(secondNode);
			}
			//var thirdNode = document.querySelectorAll("li");
			//console.log(thirdNode);




		}
	};
	xhttp.open("POST", "http://localhost:8080/lab16/DropdownList", true);
	xhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	console.log(body);
	xhttp.send(body);
}


//послед.предложение = удаляется