


document.addEventListener("DOMContentLoaded", function () {
    const togglePassword = document.getElementById("togglePassword");
    const passwordField = document.getElementById("password");

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

    // Form validation on submit
    const form = document.querySelector(".form-register");
    form.addEventListener("submit", function (event) {
        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();
        let valid = true;

        // Clear previous error messages
        document.querySelectorAll(".error").forEach(function (error) {
            error.textContent = "";
        });

        // Validate empty fields
        if (username === "") {
            valid = false;
            const error = document.createElement("span");
            error.textContent = "Email is required";
            error.classList.add("error");
            document.querySelector("#username").parentElement.appendChild(error);
        }

        if (password === "") {
            valid = false;
            const error = document.createElement("span");
            error.textContent = "Password is required";
            error.classList.add("error");
            document.querySelector("#password").parentElement.appendChild(error);
        }

        if (!valid) {
            event.preventDefault(); // Prevent form submission if invalid
        }
    });
});
