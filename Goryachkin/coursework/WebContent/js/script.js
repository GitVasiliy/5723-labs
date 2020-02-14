function test(){
	var idRef = event.target.id;
    var numberID = idRef[idRef.length - 1];
 
    var upLiRef = 'upli' + numberID;
    var downLiRef = 'downli' + numberID;
    var upCheckRef = 'upcheck' + numberID;
    var downCheckRef = 'downcheck' + numberID;
    
    var upLi = document.getElementById(upLiRef);
    var downLi = document.getElementById(downLiRef);
    var upCheck = document.getElementById(upCheckRef);
    var downCheck = document.getElementById(downCheckRef);
    
    if(upCheck.checked == true){
	    upLi.style.display = 'none';
	    downLi.style.display = 'inline';
	    downCheck.checked = true;
	    upCheck.checked = false;
    } else if(downCheck.checked == false){
    	upLi.style.display = 'inline';
    	downLi.style.display = 'none';
    	upCheck.checked = false;
    	downCheck.checked = false;
    }
    
    var size = document.getElementById('size');
    size = size.textContent;
    var checking = false;
    var numbers = '';
    for(var i = 0; i < size; i++){
    	var tempRef = 'downcheck' + i;
    	var temp = document.getElementById(tempRef);
    	if(temp.checked == true){
    		var div = document.getElementById('deleteall');
    		div.style.display = 'block';
    		checking = true;
    		numbers = numbers + i + ' ';
    	}
    }
    var hidden = document.getElementById('hidden');
    hidden.value = numbers;
    if(checking == false){
    	var div = document.getElementById('deleteall');
    	div.style.display = 'none';	
	}
}
