document.addEventListener("DOMContentLoaded", function () {
    // Toggle password visibility
	document.getElementById("togglePassword").addEventListener("click", function () {
    const passwordType = document.getElementById("password");
    const eyeIcon = document.getElementById("eyeIcon"); // Get the i element
        if (passwordType.type === "password") {
            passwordType.type = "text";
            eyeIcon.classList.remove("fa-eye");
            eyeIcon.classList.add("fa-eye-slash");
        } else {
            passwordType.type = "password";
            eyeIcon.classList.remove("fa-eye-slash");
            eyeIcon.classList.add("fa-eye");
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