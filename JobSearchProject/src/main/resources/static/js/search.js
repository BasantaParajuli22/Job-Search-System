document.addEventListener('DOMContentLoaded', function () {
    // Example of adding an event listener to the search form
    const searchForm = document.querySelector('form');
    const keywordInput = document.getElementById('keyword');

    searchForm.addEventListener('submit', function (event) {
        if (keywordInput.value.trim() === '') {
            event.preventDefault(); // Prevent form submission
            alert('Please enter a keyword to search.');
        }
    });

    // You can add more JavaScript functionality as needed
});