document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('registrationForm');

    form.addEventListener('submit', function (event) {
        let isValid = true;

        // Clear previous error messages
        clearErrors();

        // Validate Name
        const username = document.getElementById('username').value.trim();
        if (username === '') {
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
            showError('numberError', 'Contact Number is required.');
            isValid = false;
        } else if (!validateContactNumber(number)) {
            showError('numberError', 'Contact Number must be 10 digits.');
            isValid = false;
        }

        // Validate Skills
        const skills = document.getElementById('skills').value.trim();
        if (skills === '') {
            showError('skillsError', 'Skills are required.');
            isValid = false;
        }

        // Validate Password (only in registration mode)
        const password = document.getElementById('password');
        if (password && password.value.trim() === '') {
            showError('passwordError', 'Password is required.');
            isValid = false;
        } else if (password && password.value.trim().length < 6) {
            showError('passwordError', 'Password must be at least 6 characters.');
            isValid = false;
        }

        // Validate Confirm Password
        const confirmPassword = document.getElementById('confirmPassword');
        if (confirmPassword && confirmPassword.value.trim() !== password.value.trim()) {
            showError('confirmpasswordError', 'Passwords do not match.');
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
        const numberRegex = /^\d{10}$/;
        return numberRegex.test(number);
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
            error.textContent = '';  // Clear error messages
        });
    }
});
