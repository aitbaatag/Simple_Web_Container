document.getElementById("signupForm").addEventListener("submit", function(event) {
    event.preventDefault();

    const firstName = document.getElementById("firstName").value.trim();
    const lastName = document.getElementById("lastName").value.trim();
    const errorMessage = document.getElementById("error");

    // Validate inputs
    if (firstName === "" || lastName === "") {
        errorMessage.textContent = "Please fill in both your first and last names.";
    } else {
        errorMessage.textContent = "";
        alert(`Signup Successful! Welcome, ${firstName} ${lastName}`);
        // Here you can add code to send data to a server, etc.
    }
});
