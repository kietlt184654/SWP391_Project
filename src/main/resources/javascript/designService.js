// side bar
const menuToggle = document.getElementById("menu-toggle");
const sidebar = document.getElementById("sidebar");

menuToggle.addEventListener("click", () => {
  sidebar.classList.toggle("show");
});

// dropdown menu
const accountIcon = document.getElementById("accountIcon");
const dropdownMenu = document.querySelector(".dropdown-menu");

accountIcon.addEventListener("click", (event) => {
  event.preventDefault();
  dropdownMenu.style.display =
    dropdownMenu.style.display === "block" ? "none" : "block";
});

document.addEventListener("click", function (event) {
  if (
    !accountIcon.contains(event.target) &&
    !dropdownMenu.contains(event.target)
  ) {
    dropdownMenu.style.display = "none";
  }
  // icon cart
  let cartCount = 0;

  function updateCartCount(count) {
    cartCount += count;
    document.querySelector(".notification-badge").textContent = cartCount;
  }
  updateCartCount(1);
});

// Control quantity
const decreaseBtn = document.getElementById("decrease");
const increaseBtn = document.getElementById("increase");
const quantityInput = document.getElementById("quantity");

let quantity = 1;

decreaseBtn.addEventListener("click", () => {
  if (quantity > 1) {
    quantity--;
    quantityInput.value = quantity;
  }
});

increaseBtn.addEventListener("click", () => {
  if (quantity < 1000) {
    quantity++;
    quantityInput.value = quantity;
  }
});

// Lấy tất cả các liên kết dịch vụ
const links = document.querySelectorAll(".service-link");

// Lấy URL của trang hiện tại
const currentUrl = window.location.href;

// Duyệt qua các liên kết và kiểm tra URL
links.forEach((link) => {
  if (currentUrl.includes(link.getAttribute("href"))) {
    link.classList.add("highlighted"); // Thêm lớp highlighted nếu URL khớp
  }
});
