document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("employer-profile-form");

    form.addEventListener("submit", function (event) {
        let isValid = true;

        // Clear previous error messages
        document.querySelectorAll(".error-message").forEach(el => el.textContent = "");

        // Validate Company Name
        const companyName = document.getElementById("companyName").value.trim();
        if (!companyName) {
            isValid = false;
            addErrorMessage("companyName", "Company Name is required.");
        }

        // Validate Company Description
        const companyDescription = document.getElementById("companyDescription").value.trim();
        if (!companyDescription) {
            isValid = false;
            addErrorMessage("companyDescription", "Company Description is required.");
        }

        // Validate Address
        const address = document.getElementById("address").value.trim();
        if (!address) {
            isValid = false;
            addErrorMessage("address", "Address is required.");
        }

        // Validate Contact Number
        const number = document.getElementById("number").value.trim();
        const phonePattern = /^\+?[0-9]{10,15}$/; // Optional '+' followed by 10 to 15 digits //
        if (!number) {
            isValid = false;
            addErrorMessage("number", "Contact Number is required.");
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
