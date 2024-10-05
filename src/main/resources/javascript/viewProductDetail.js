const menuToggle = document.getElementById("menu-toggle");
const sidebar = document.getElementById("sidebar");

menuToggle.addEventListener("click", () => {
  sidebar.classList.toggle("show");
});

// Toggle dropdown menu when clicking the account icon
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

function showContent(contentType) {
  const descriptionContent = document.getElementById("description-content");
  const reviewContent = document.getElementById("review-content");

  const tabs = document.querySelectorAll(".tab");

  tabs.forEach((tab) => {
    tab.classList.remove("active");
  });

  if (contentType === "description") {
    descriptionContent.style.display = "block";
    reviewContent.style.display = "none";
    document.querySelector(".tab:nth-child(1)").classList.add("active");
  } else if (contentType === "review") {
    reviewContent.style.display = "block";
    descriptionContent.style.display = "none";
    document.querySelector(".tab:nth-child(2)").classList.add("active");
  }
}

// rating star
document.addEventListener("DOMContentLoaded", function () {
  const stars = document.querySelectorAll(".star");

  stars.forEach((star) => {
    star.addEventListener("click", function () {
      const rating = parseInt(this.getAttribute("data-value"));

      stars.forEach((s, index) => {
        if (index < rating) {
          s.classList.add("selected"); // Tô màu vàng
        } else {
          s.classList.remove("selected"); // Giữ màu xám
        }
      });
    });
  });
});

// move pro-img-list
document.addEventListener("DOMContentLoaded", function () {
  const imgList = document.querySelectorAll(".pro-img-list img"); // Lấy danh sách các ảnh nhỏ
  const mainImg = document.querySelector(".pro-img-details img"); // Lấy ảnh chính hiển thị
  let currentIndex = 0; // Đặt chỉ số hiện tại của ảnh đang được hiển thị

  // Hàm cập nhật ảnh chính
  function updateMainImage(index) {
    if (imgList[index]) {
      mainImg.src = imgList[index].src;
    }
  }

  // Sự kiện khi nhấn nút "Previous"
  document.getElementById("prev-btn").addEventListener("click", function () {
    if (currentIndex > 0) {
      currentIndex--;
    } else {
      currentIndex = imgList.length - 1; // Quay lại ảnh cuối cùng nếu đã ở đầu
    }
    updateMainImage(currentIndex); // Cập nhật ảnh chính
  });

  // Sự kiện khi nhấn nút "Next"
  document.getElementById("next-btn").addEventListener("click", function () {
    if (currentIndex < imgList.length - 1) {
      currentIndex++;
    } else {
      currentIndex = 0; // Quay lại ảnh đầu tiên nếu đã đến cuối
    }
    updateMainImage(currentIndex); // Cập nhật ảnh chính
  });

  // Đặt ảnh đầu tiên khi trang được tải
  updateMainImage(currentIndex);
});
