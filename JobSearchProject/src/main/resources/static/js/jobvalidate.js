document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');

    form.addEventListener('submit', function(event) {
        let isValid = true;

        // Clear previous error messages
        clearErrors();

        // Validate Name (id is now 'username')
        const name = document.getElementById('username').value.trim();
        if (name === '') {
            showError('nameError', 'Name is required.');
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
        const confirmPassword = document.getElementById('confirmPassword').value.trim();
        if (password === '') {
            showError('passwordError', 'Password is required.');
            isValid = false;
        } else if (password.length < 6) {
            showError('passwordError', 'Password must be at least 6 characters long.');
            isValid = false;
        }

        if (confirmPassword === '') {
            showError('confirmpasswordError', 'Please confirm your password.');
            isValid = false;
        } else if (password !== confirmPassword) {
            showError('confirmpasswordError', 'Passwords do not match.');
            isValid = false;
        }

        // Validate Gender (unique IDs)
        const genderChecked = document.querySelector('input[name="genderGroup"]:checked');
        if (!genderChecked) {
            showError('genderError', 'Please select your gender.');
            isValid = false;
        }

        // Validate Terms
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
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
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
