var nextBtn = document.querySelector('.next'),
    prevBtn = document.querySelector('.prev'),
    carousel = document.querySelector('.carousel'),
    list = document.querySelector('.list'),
    item = document.querySelectorAll('.item'),
    runningTime = document.querySelector('.carousel .timeRunning')

let timeRunning = 3000
let timeAutoNext = 7000

nextBtn.onclick = function() {
    showSlider('next')
}

prevBtn.onclick = function() {
    showSlider('prev')
}

let runTimeOut

let runNextAuto = setTimeout(() => {
    nextBtn.click()
}, timeAutoNext)

function resetTimeAnimation() {
    runningTime.style.animation = 'none'
    runningTime.offsetHeight /* trigger reflow */
    runningTime.style.animation = null
    runningTime.style.animation = 'runningTime 7s linear 1 forwards'
}

function showSlider(type) {
    let sliderItemsDom = list.querySelectorAll('.carousel .list .item')
    if (type === 'next') {
        list.appendChild(sliderItemsDom[0])
        carousel.classList.add('next')
    } else {
        list.prepend(sliderItemsDom[sliderItemsDom.length - 1])
        carousel.classList.add('prev')
    }

    clearTimeout(runTimeOut)

    runTimeOut = setTimeout(() => {
        carousel.classList.remove('next')
        carousel.classList.remove('prev')
    }, timeRunning)

    clearTimeout(runNextAuto)
    runNextAuto = setTimeout(() => {
        nextBtn.click()
    }, timeAutoNext)

    resetTimeAnimation() // Reset the running time animation
}

// Start the initial animation
resetTimeAnimation()

// Check login status in sessionStorage and redirect if not logged in
function checkLoginStatus() {
    if (!sessionStorage.getItem("isLoggedIn")) {
        // Redirect to the login page if the user is not logged in
        window.location.href = "/login";
    }
}

// Function to handle logout and clear sessionStorage
function logout() {
    sessionStorage.removeItem("isLoggedIn"); // Clear login status from sessionStorage
    window.location.href = "/account/logout"; // Redirect to logout page
}

// Function to close the modal
function closeModal() {
    document.getElementById('loginModal').style.display = 'none';
}

// Close modal when clicking outside of it
window.onclick = function(event) {
    const modal = document.getElementById('loginModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};

// Listen for changes in localStorage
window.addEventListener('storage', function(event) {
    if (event.key === 'loggedInUser') {
        updateLoginStatus();
    }
});

// Update login status
function updateLoginStatus() {
    const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser'));
    const loginButton = document.getElementById('loginBtn');
    const registerButton = document.getElementById('registerBtn');
    const logoutForm = document.getElementById('logoutForm');
    const profileLink = document.getElementById('profileLink');
    const avatarImg = document.getElementById('avatarImg');
    const accountName = document.getElementById('accountName');
    const navLinks = document.querySelectorAll('.logged-in');

    if (loggedInUser) {
        // Show items for logged-in user
        loginButton.style.display = 'none';
        registerButton.style.display = 'none';
        logoutForm.style.display = 'block';
        profileLink.style.display = 'block';
        avatarImg.src = loggedInUser.avatar || '/img/defaultAva.jpg';
        accountName.textContent = loggedInUser.username;

        // Show links for logged-in user
        navLinks.forEach(link => link.style.display = 'block');
    } else {
        // Hide items for logged-in user
        loginButton.style.display = 'inline-block';
        registerButton.style.display = 'inline-block';
        logoutForm.style.display = 'none';
        profileLink.style.display = 'none';

        // Hide links for logged-in user
        navLinks.forEach(link => link.style.display = 'none');
    }
}

// Check login status and initialize animations when the page loads
document.addEventListener("DOMContentLoaded", function() {
    checkLoginStatus();
    updateLoginStatus();
});
