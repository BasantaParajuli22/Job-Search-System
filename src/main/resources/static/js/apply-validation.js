document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('applicationForm');

    form.addEventListener('submit', function (event) {
        let isValid = true;

        // Clear previous error messages
        clearErrors();

        // Validate Full Name
        const fullName = document.getElementById('name').value.trim();
        if (fullName === '') {
            showError('nameError', 'Full name is required.');
            isValid = false;
        }

        // Validate Email
        const email = document.getElementById('email').value.trim();
        if (email === '') {
            showError('emailError', 'Email is required.');
            isValid = false;
        } else if (!validateEmail(email)) {
            showError('emailError', 'Invalid email address.');
            isValid = false;
        }

        // Validate Contact Number
        const contactNumber = document.getElementById('contactNumber').value.trim();
		if(contactNumber === ''){
            showError('contactNumberError', 'Contact number is required.');
            isValid = false;
		}
		else if (!validateContactNumber(contactNumber)) {
           showError('contactNumberError', 'Contact number must be 10 digits.');
           isValid = false;
        }


        // Validate CV file
        const cvFile = document.getElementById('cv').files[0];
        if (!cvFile) {
            showError('cvError', 'Please select a CV/Resume file.');
            isValid = false;
        } else if(cvFile.type !== 'application/pdf'){
            showError('cvError', 'Please select a PDF file');
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

	 function validateContactNumber(contactNumber) {
        const contactNumberRegex = /^\d{10}$/;
        return contactNumberRegex.test(contactNumber);
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

function validateFileSize(input) {
    const file = input.files[0];
    if (file && file.size > 1 * 1024 * 1024) { // 1 MB limit
        alert("File size exceeds the maximum allowed size of 1 MB.");
        input.value = ""; // Clear the input
    }
}