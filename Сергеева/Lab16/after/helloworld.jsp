<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
         "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*,com.example.*" %>
<script language="JavaScript">

function on(clicked_id)
{   
    var elements = document.getElementsByClassName(clicked_id.replace("_", " "));
    var tmp = document.getElementById(clicked_id+"0").textContent;

    if(tmp.includes("[-]")) {
        document.getElementById(clicked_id+"0").textContent= tmp.replace('[-]',"[+]");
        n = elements.length;
        for (var i = 0; i < elements.length; i++) {
            elements[i].style.display = "none";
        }
    } else {
        document.getElementById(clicked_id+"0").textContent= tmp.replace("+","-");
        n = elements.length;
        for (var i = 0; i < elements.length; i++) {
            elements[i].style.display = "block";
        }

    }

}
function off(num,str)
{
    var x = parseInt(num);
    console.log(str);
    var elements = document.getElementsByClassName(str);
    if(num == -1){
        var tmp = str.replace(" ","_");
        console.log(tmp);
        for(var i = 0; i < elements.length; i++)
            elements[i].style.display = 'none';   
        console.log()
        document.getElementById(tmp).style.display = 'none';
    
    }
    //var elements = document.getElementsByClassName(str);
    if(x >= 0) {
        elements[num].style.display = 'none';   
    }
}
</script>
</script>

<jsp:useBean id="hello" class="MainPage" scope="page" />

<%! 
String getFormattedDate() 
{ 
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss"); 
    return sdf.format(new Date()); 
} 
%>
<html>
  <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>Ферма</title>
  </head>
  <body>
        ${hello.getFromFile("last.txt")}
        ${hello.getList()}
  </body>
</html>