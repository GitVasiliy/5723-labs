function on(clicked_id, size) {
	console.log(clicked_id.replace("_"," "));
	var tmp = document.getElementById(clicked_id).textContent;
	if(tmp.includes("[-]")) {
		document.getElementById(clicked_id).textContent = "[+]";
		for (var i = 1; i <= size; i++) {
			var element = document.getElementById(clicked_id.replace("_"," ") + i);
			element.style.display = "none";
		}
	} else {
		document.getElementById(clicked_id).textContent = "[-]";
		for (var i = 1; i <= size; i++) {
			var element = document.getElementById(clicked_id.replace("_"," ") + i);
			element.style.display = "inline";
		}
	}
}
