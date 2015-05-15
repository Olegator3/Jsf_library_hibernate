

function showProgress(data){
    
    if(data.status === "begin"){
        document.getElementById('loading').style.display = "block";
        document.getElementById('booklist').style.opacity = 0.3;
    }
    if(data.status === "success"){
        document.getElementById('loading').style.display = "none";
        document.getElementById('booklist').style.opacity = 1.0;
    }
    
}

