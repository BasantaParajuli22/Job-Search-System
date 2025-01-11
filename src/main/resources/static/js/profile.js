// Get the modal
const profilePictureModal = document.getElementById("profilePictureModal");
const resumeModal = document.getElementById("resumeModal");
// Get the buttons that opens the modal
const changePhotoButtons = document.querySelectorAll(".change-photo-btn");
const changeResumeButtons = document.querySelectorAll(".change-resume-btn");

// Get the button that closes the modal
const closeButtons = document.querySelectorAll(".close-btn");
//Function to open modal
function openModal(modal) {
  modal.style.display = "block";
}
 //Function to close modal
function closeModal(modal) {
  modal.style.display = "none";
}

// When the user clicks the button, open the modal
 changePhotoButtons.forEach(button => {
    button.addEventListener("click", () => openModal(profilePictureModal))
});
 changeResumeButtons.forEach(button => {
    button.addEventListener("click", () => openModal(resumeModal));
});

// When the user clicks on the close button, close the modal
closeButtons.forEach(button => {
     button.addEventListener("click", () => {
     closeModal(profilePictureModal);
    closeModal(resumeModal);
    });
});

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == profilePictureModal) {
    closeModal(profilePictureModal);
  }
  if (event.target == resumeModal) {
    closeModal(resumeModal);
  }
}