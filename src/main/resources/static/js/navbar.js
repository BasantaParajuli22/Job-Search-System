function toggleDropdown(event) {
    event.preventDefault(); // Prevent default behavior of the link

    const dropdown = document.getElementById('registerDropdown');
    const isVisible = dropdown.style.display === 'block';

    // Toggle the dropdown visibility
    dropdown.style.display = isVisible ? 'none' : 'block';

    // If the dropdown is now visible, set up an event listener to detect clicks outside
    if (!isVisible) {
        document.addEventListener('click', closeDropdownOnClickOutside);
    }
}

// Function to close dropdown when clicking outside
function closeDropdownOnClickOutside(event) {
    const dropdown = document.getElementById('registerDropdown');
    const dropButton = document.querySelector('.dropbtn'); // The dropdown button

    // Check if the click happened outside the dropdown or button
    if (!dropdown.contains(event.target) && !dropButton.contains(event.target)) {
        dropdown.style.display = 'none';

        // Remove the event listener since it's no longer needed
        document.removeEventListener('click', closeDropdownOnClickOutside);
    }
}
