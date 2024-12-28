document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector(".form-register");

    form.addEventListener("submit", function (event) {
        let isValid = true;

        // Clear previous error messages
        document.querySelectorAll(".error-message").forEach(el => el.textContent = "");

        // Validate Job Title
        const title = document.getElementById("title").value.trim();
        if (!title) {
            isValid = false;
            document.querySelector("#title + .error-message").textContent = "Job Title is required.";
        }

        // Validate Job Description
        const jobDescription = document.getElementById("jobDescription").value.trim();
        if (!jobDescription) {
            isValid = false;
            document.querySelector("#jobDescription + .error-message").textContent = "Job Description is required.";
        }

		// Validate Contact Email
		       const contactEmail = document.getElementById("contactEmail").value.trim();
		       if (!contactEmail) {
		           isValid = false;
		           document.querySelector("#contactEmail + .error-message").textContent = "Contact Email is required.";
		       } else if (!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/.test(contactEmail)) {
		           isValid = false;
		           document.querySelector("#contactEmail + .error-message").textContent = "Please enter a valid email address.";
		       }
			   
        // Validate Application Deadline
        const applicationDeadline = document.getElementById("applicationDeadline").value;
        if (!applicationDeadline) {
            isValid = false;
            document.querySelector("#applicationDeadline + .error-message").textContent = "Application Deadline is required.";
        } else if (new Date(applicationDeadline) < new Date()) {
            isValid = false;
            document.querySelector("#applicationDeadline + .error-message").textContent = "Application Deadline must be in the future.";
        }

        // Prevent form submission if validation fails
        if (!isValid) {
            event.preventDefault();
        }
    });
});
