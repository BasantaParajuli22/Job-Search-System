document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('jobseeker-profile-form');
    const fullNameInput = document.getElementById('fullName');
    const skillsInput = document.getElementById('skills');
    const numberInput = document.getElementById('number');
    const descriptionInput = document.getElementById('description');


    const fullNameError = document.getElementById('fullName-error');
    const skillsError = document.getElementById('skills-error');
    const numberError = document.getElementById('number-error');
    const descriptionError = document.getElementById('description-error');

    form.addEventListener('submit', function (event) {
        let isValid = true;

        // Reset error messages
        resetErrorMessages();

        // Validate full name
		// Validate Full Name
		   if (fullNameInput.value.trim() === '') {
		        fullNameError.textContent = 'Full name cannot be empty';
		       isValid = false;
		   }  else if (fullNameInput.value.length > 50) {
		       fullNameError.textContent = 'Full name must be less than 50 characters';
		       isValid = false;
		   }
		   else if(!isValidName(fullNameInput.value)){
		     fullNameError.textContent = 'Full name must not contain numbers and special characters'
		     isValid = false;
		   }
		   else {
			 fullNameError.textContent = '';
		}


        // Validate skills
        if (skillsInput.value.trim() !== '') {
            if (skillsInput.value.length > 240) {
                skillsError.textContent = 'Skills must be less than 240 characters';
                isValid = false;
            }
        }

        // Validate number
       if (numberInput.value.trim() !== '') {
            if (!isValidNumber(numberInput.value)) {
               numberError.textContent = "Number must contain digits and less than 10 numbers only";
                isValid = false;
           } else if (numberInput.value.length > 11) {
               numberError.textContent = "Number must contain 10 digits only";
               isValid = false;
           }
       }

        // Validate description
         if (descriptionInput.value.trim() !== '') {
            if (descriptionInput.value.length > 900) {
                descriptionError.textContent = 'Description must be less than 900 characters';
                isValid = false;
            }
        }

        if (!isValid) {
            event.preventDefault(); // Prevent form submission
        }
    });

	function isValidName(name) {
		//firstname and space between last name
			const nameRegex = /^[A-Za-z]+ [A-Za-z]+$/;
	        return nameRegex.test(name);
	    }
    function isValidNumber(number) {
       return /^\d{10}$/.test(number);
    }

    function resetErrorMessages() {
        fullNameError.textContent = '';
        skillsError.textContent = '';
        numberError.textContent = '';
        descriptionError.textContent = '';
    }
});