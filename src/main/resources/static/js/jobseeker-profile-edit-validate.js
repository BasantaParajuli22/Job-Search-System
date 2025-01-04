document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("jobseeker-profile-form");

    form.addEventListener("submit", function (event) {
        let isValid = true;

        // Clear previous error messages
        document.querySelectorAll(".error-message").forEach(el => el.textContent = "");

        // Validate Full Name
        const fullName = document.getElementById("fullName").value.trim();
        if (!fullName) {
            isValid = false;
            addErrorMessage("fullName", "Full Name is required.");
        }

        // Validate Skills
        const skills = document.getElementById("skills").value.trim();
        if (!skills) {
            isValid = false;
            addErrorMessage("skills", "Skills are required.");
        }

        // Validate Phone Number
        const number = document.getElementById("number").value.trim();
        const phonePattern = /^\+?[0-9]{10,15}$/; // Optional '+' followed by 10 to 15 digits
        if (!number) {
            isValid = false;
            addErrorMessage("number", "Phone Number is required.");
        } else if (!phonePattern.test(number)) {
            isValid = false;
            addErrorMessage("number", "Please enter a valid phone number (10-15 digits).");
        }

        // Prevent form submission if validation fails
        if (!isValid) {
            event.preventDefault();
        }
    });

    // Function to add error message
    function addErrorMessage(inputId, message) {
        const inputElement = document.getElementById(inputId);
        let errorElement = inputElement.nextElementSibling;
        if (!errorElement || !errorElement.classList.contains("error-message")) {
            errorElement = document.createElement("span");
            errorElement.classList.add("error-message");
            inputElement.parentNode.insertBefore(errorElement, inputElement.nextSibling);
        }
        errorElement.textContent = message;
    }
});
