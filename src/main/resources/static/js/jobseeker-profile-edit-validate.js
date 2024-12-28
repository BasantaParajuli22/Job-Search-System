document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (event) {
        let isValid = true;

        // Clear previous error messages
        clearErrors();

        // Validate Full Name
        const fullName = document.getElementById('fullName').value.trim();
        if (fullName === '') {
            showError('fullName', 'Full Name is required.');
            isValid = false;
        }

        // Validate Skills
        const skills = document.getElementById('skills').value.trim();
        if (skills === '') {
            showError('skills', 'Skills are required.');
            isValid = false;
        }

        // Validate Phone Number
        const phoneNumber = document.getElementById('number').value.trim();
        if (phoneNumber === '') {
            showError('number', 'Phone Number is required.');
            isValid = false;
        } else if (!validatePhoneNumber(phoneNumber)) {
            showError('number', 'Phone Number must be 10 digits long.');
            isValid = false;
        }

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
        }
    });

    function validatePhoneNumber(number) {
        const numberRegex = /^\d{10}$/; // Checks for 10 digits
        return numberRegex.test(number);
    }

    function showError(fieldId, message) {
        const field = document.getElementById(fieldId);
        const errorElement = document.createElement('div');
        errorElement.className = 'error';
        errorElement.style.color = 'red';
        errorElement.textContent = message;
        field.parentNode.insertBefore(errorElement, field.nextSibling);
    }

    function clearErrors() {
        const errors = document.querySelectorAll('.error');
        errors.forEach(error => error.remove());
    }
});
