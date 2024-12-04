//for login functions

/**
 * 
document.getElementById("loginButton").addEventListener("click", function() {
	
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (username == "basanta1" && password == "password") {
        document.getElementById("message").textContent = "Login successful!";
        document.getElementById("loginButton").style.display = "none";
        document.getElementById("logoutButton").style.display = "block";
    } else {
        document.getElementById("message").textContent = "Invalid username or password.";
    }
});

*/
//when clicked logout button//
document.getElementById("logoutButton").addEventListener("click", function() {
    document.getElementById("message").textContent = "You have logged out.";
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
    document.getElementById("loginButton").style.display = "block";
    document.getElementById("logoutButton").style.display = "none";
});

//toggle password 
document.getElementById("togglePassword").addEventListener("click",function(){
    //if(passwordType.type === "password" ) console.log("itss password"); 
    const passwordType = document.getElementById("password");
    if(passwordType.type === "password" ){
        passwordType.type = "text"
        document.getElementById("togglePassword").textContent = "Hide";
        document.getElementById("togglePassword").style.display = "inline";
    }else{
        passwordType.type = "password";
        document.getElementById("togglePassword").textContent = "Show";
        document.getElementById("togglePassword").style.display = "inline";
    }

});