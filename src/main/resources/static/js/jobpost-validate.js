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
		// Validate Job Title
		const requirements = document.getElementById("requirements").value.trim();
		if (!requirements) {
		    isValid = false;
		    document.querySelector("#requirements + .error-message").textContent = "Requirements is required.";
		}

        // Validate Job Description
        const jobDescription = document.getElementById("jobDescription").value.trim();
        if (!jobDescription) {
            isValid = false;
            document.querySelector("#jobDescription + .error-message").textContent = "Job Description is required.";
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
