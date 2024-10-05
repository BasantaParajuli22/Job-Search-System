document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');

    form.addEventListener('submit', function(event) {
        let isValid = true;

        // Clear previous error messages
        clearErrors();

        // Validate Company/Organization Name
        const companyName = document.getElementById('name').value.trim();
        if (companyName === '') {
            showError('nameError', 'Company/Organization Name is required.');
            isValid = false;
        }

        // Validate Email
        const email = document.getElementById('email').value.trim();
        if (!validateEmail(email)) {
            showError('emailError', 'Invalid email address.');
            isValid = false;
        }

        // Validate Contact Number
        const contactNumber = document.getElementById('number').value.trim();
        if (contactNumber === '') {
            showError('numberError', 'Contact Number is required.');
            isValid = false;
        } else if (!validateContactNumber(contactNumber)) {
            showError('numberError', 'Contact Number must be 10 digits long.');
            isValid = false;
        }

        // Validate Address
        const address = document.getElementById('address').value.trim();
        if (address === '') {
            showError('addressError', 'Address is required.');
            isValid = false;
        }

        // Validate Website
        const website = document.getElementById('website').value.trim();
        if (website === '') {
            showError('websiteError', 'Website is required.');
            isValid = false;
        } else if (!validateWebsite(website)) {
            showError('websiteError', 'Invalid website format.');
            isValid = false;
        }

        // Validate Company Description
        const companyDescription = document.getElementById('companyDescription').value.trim();
        if (companyDescription === '') {
            showError('companyDescriptionError', 'Company Description is required.');
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

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
        }
    });

    function validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function validateContactNumber(number) {
        const numberRegex = /^\d{10}$/; // Checks for 10 digits
        return numberRegex.test(number);
    }

    function validateWebsite(website) {
        const websiteRegex = /^(https?:\/\/)?(www\.)?[a-zA-Z0-9-]+\.[a-zA-Z]{2,}(\.[a-zA-Z]{2,})?$/; // Basic website validation
        return websiteRegex.test(website);
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
            error.textContent = '';
        });
    }
});
