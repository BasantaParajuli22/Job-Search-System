document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('employer-profile-form');
    const companyNameInput = document.getElementById('companyName');
    const companyDescriptionInput = document.getElementById('companyDescription');
    const addressInput = document.getElementById('address');
    const numberInput = document.getElementById('number');
    const websiteInput = document.getElementById('website');

    const companyNameError = document.getElementById('companyName-error');
    const companyDescriptionError = document.getElementById('companyDescription-error');
    const addressError = document.getElementById('address-error');
    const numberError = document.getElementById('number-error');
    const websiteError = document.getElementById('website-error');
    

    form.addEventListener('submit', function (event) {
        let isValid = true;

        // Reset error messages
        resetErrorMessages();


        //Validate company name
        if (companyNameInput.value.trim() !== '') {
            if (companyNameInput.value.length > 50) {
                companyNameError.textContent = 'Company name must be less than 50 characters';
                isValid = false;
            } else if(!isValidName(companyNameInput.value)){
                companyNameError.textContent = "Invalid company name. Use only letters, numbers, spaces, &";
                 isValid = false;
             }
        }

        // Validate Description
         if (companyDescriptionInput.value.trim() !== '') {
            if (companyDescriptionInput.value.length > 900) {
                companyDescriptionError.textContent = 'Description must be less than 900 characters';
                isValid = false;
            }
        }


         // Validate address
        if (addressInput.value.trim() !== '') {
            if (addressInput.value.length > 240) {
                addressError.textContent = 'Address must be less than 240 characters';
                isValid = false;
            }
        }


        // Validate number
        if (numberInput.value.trim() !== '') {
             if(!isValidNumber(numberInput.value)){
                numberError.textContent = "Number must contains only numbers and should be 10 digit";
                isValid = false;
            }else if (numberInput.value.length > 10) {
                numberError.textContent = 'Number must be 10 digit';
                isValid = false;
            }
        }

        // Validate Website
          if(websiteInput.value.trim() !== ''){
              if (!isValidWebsite(websiteInput.value)) {
                websiteError.textContent = 'Please enter a valid website URL';
                isValid = false;
            } else if(websiteInput.value.length > 90){
                websiteError.textContent = "Website address cannot be greater than 90 characters"
                isValid = false;
            }
          }

        if (!isValid) {
            event.preventDefault(); // Prevent form submission
        }
    });


	 function isValidName(name) {
	 	    // Match names containing letters, numbers, spaces, and specific special characters like & and '
	 	    const nameRegex = /^[a-zA-Z0-9&\s]+$/;
	 	    return nameRegex.test(name) && !/^\d+$/.test(name) && name.trim().length > 0;
	 	}

     function isValidNumber(number) {
           return /^\d{10}$/.test(number);
     }

     function isValidWebsite(website) {
        try {
            new URL(website);
            return true;
        } catch (e) {
            return false;
        }
    }

    function resetErrorMessages() {
        companyNameError.textContent = '';
        companyDescriptionError.textContent = '';
        addressError.textContent = '';
        numberError.textContent = '';
        websiteError.textContent = '';
    }
});