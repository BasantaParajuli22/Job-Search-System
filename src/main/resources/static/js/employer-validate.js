document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('registrationForm');

    form.addEventListener('submit', function (event) {
        let isValid = true;
        console.log("Form submission started");

        // Clear previous error messages
        clearErrors();

        // Validate Company/Organization Name
        const companyName = document.getElementById('name').value.trim();
        if (companyName === '') {
            showError('nameError', 'Company/Organization Name is required.');
            isValid = false;
			}
		else if (companyName.length > 50) {
			 showError('nameError', 'Company/Organization Name cannot exceed 50 characters.');
			 isValid = false;
        }else if(! validateName(companyName)){
			showError('nameError', 'Invalid company name. Use only letters, numbers, spaces, and &');
			isValid = false;
		}

        // Validate Email
        const email = document.getElementById('email').value.trim();
        if (!validateEmail(email)) {
            showError('emailError', 'Invalid email address.');
            isValid = false;
        }

        // Validate Password
        const password = document.getElementById('password').value.trim();
        if (password === '') {
            showError('passwordError', 'Password is required.');
            isValid = false;
        } else if (password.length < 6) {
            showError('passwordError', 'Password must be at least 6 characters long.');
            isValid = false;
        }

        // Validate Confirm Password
        const confirmPassword = document.getElementById('confirmPassword').value.trim();
        if (confirmPassword !== password) {
            showError('confirmPasswordError', 'Passwords do not match.');
            isValid = false;
        }

        // Validate Terms and Conditions
        const terms = document.getElementById('terms').checked;
        if (!terms) {
            showError('termsError', 'You must accept the terms and conditions.');
            isValid = false;
        }

        console.log("isValid after all checks: ", isValid);

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
            console.log("Form submission prevented");
        }
    });

    function validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
	function validateName(name) {
	    // Match names containing letters, numbers, spaces, and specific special characters like &
	    const nameRegex = /^[a-zA-Z0-9&\s]+$/;
	    return nameRegex.test(name) && !/^\d+$/.test(name) && name.trim().length > 0;
	}

    function showError(id, message) {
        const errorElement = document.getElementById(id);
        if (errorElement) {
            errorElement.textContent = message;
        }
    }

    function clearErrors() {
        const errors = document.querySelectorAll('.error');
        errors.forEach(error => {
            error.textContent = ''; // Clear previous error messages
        });
    }
});