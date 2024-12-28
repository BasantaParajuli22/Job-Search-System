document.addEventListener('DOMContentLoaded', function () {
    const form = document.querySelector('form');

    form.addEventListener('submit', function (event) {
        let isValid = true;

        // Clear previous error messages
        clearErrors();

        // Validate Company Name
        const companyName = document.getElementById('companyName').value.trim();
        if (companyName === '') {
            showError('companyName', 'Company Name is required.');
            isValid = false;
        }

        // Validate Company Description
        const companyDescription = document.getElementById('companyDescription').value.trim();
        if (companyDescription === '') {
            showError('companyDescription', 'Company Description is required.');
            isValid = false;
        }

        // Validate Address
        const address = document.getElementById('address').value.trim();
        if (address === '') {
            showError('address', 'Address is required.');
            isValid = false;
        }

        // Validate Contact Number
        const contactNumber = document.getElementById('number').value.trim();
        if (contactNumber === '') {
            showError('number', 'Contact Number is required.');
            isValid = false;
        } else if (!validateContactNumber(contactNumber)) {
            showError('number', 'Contact Number must be 10 digits long.');
            isValid = false;
        }

        // Prevent form submission if invalid
        if (!isValid) {
            event.preventDefault();
        }
    });

    function validateContactNumber(number) {
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
