var nextBtn = document.querySelector('.next'),
    prevBtn = document.querySelector('.prev'),
    carousel = document.querySelector('.carousel'),
    list = document.querySelector('.list'),
    item = document.querySelectorAll('.item'),
    runningTime = document.querySelector('.carousel .timeRunning')

let timeRunning = 3000
let timeAutoNext = 7000

nextBtn.onclick = function(){
    showSlider('next')
}

prevBtn.onclick = function(){
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
    if(type === 'next'){
        list.appendChild(sliderItemsDom[0])
        carousel.classList.add('next')
    } else{
        list.prepend(sliderItemsDom[sliderItemsDom.length - 1])
        carousel.classList.add('prev')
    }

    clearTimeout(runTimeOut)

    runTimeOut = setTimeout( () => {
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
// JavaScript function to check login and show modal if not logged in
function checkLogin(event, url) {
    const loggedInUser = /*[[${session.loggedInUser != null}]]*/ false; // Thymeleaf will replace this with true or false

    if (!loggedInUser) {
        event.preventDefault(); // Prevent navigation
        document.getElementById('loginModal').style.display = 'block'; // Show the modal
    } else {
        window.location.href = url; // Redirect if logged in
    }
}
// Kiểm tra trạng thái đăng nhập trong sessionStorage và chuyển hướng nếu cần
function checkLoginStatus() {
    if (!sessionStorage.getItem("isLoggedIn")) {
        // Chuyển hướng về trang đăng nhập nếu không có trạng thái đăng nhập
        window.location.href = "/login";
    }
}
document.addEventListener("DOMContentLoaded", checkLoginStatus);
// Hàm xử lý đăng xuất và xóa sessionStorage
function logout() {
    sessionStorage.removeItem("isLoggedIn"); // Xóa trạng thái đăng nhập khỏi sessionStorage
    window.location.href = "/account/logout"; // Điều hướng tới trang đăng xuất
}

// Khởi tạo AOS (Animate On Scroll) nếu thư viện được sử dụng
function initializeAOS() {
    if (typeof AOS !== 'undefined') {
        AOS.init();
    }
}

// Gọi hàm kiểm tra trạng thái đăng nhập ngay khi trang được tải
document.addEventListener("DOMContentLoaded", function () {
    checkLoginStatus();
    initializeAOS();
});


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
