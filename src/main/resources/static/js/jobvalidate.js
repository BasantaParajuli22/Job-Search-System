document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('registrationForm');

    form.addEventListener('submit', function(event) {
        let isValid = true;

        // Clear previous error messages
        clearErrors();

        // Validate Name
        const name = document.getElementById('name').value.trim();
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

        // Validate Number
        const number = document.getElementById('number').value.trim();
        if (number === '') {
            showError('numberError', 'Number is required.');
            isValid = false;
        }

        // Validate Password
        const password = document.getElementById('password').value.trim();
        if (password === '') {
            showError('passwordError', 'Password is required.');
            isValid = false;
        }

        // Validate Confirm Password
        const confirmPassword = document.getElementById('confirmpassword').value.trim();
        if (confirmPassword !== password) {
            showError('confirmpasswordError', 'Passwords do not match.');
            isValid = false;
        }

        // Validate Gender
        const genderRadios = document.getElementsByName('genderGroup');
        const genderChecked = Array.from(genderRadios).some(radio => radio.checked);
        if (!genderChecked) {
            showError('genderError', 'Please select your gender.');
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
