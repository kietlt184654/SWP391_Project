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

const links = document.querySelectorAll(".service-link");
const currentUrl = window.location.href;
links.forEach((link) => {
  if (currentUrl.includes(link.getAttribute("href"))) {
    link.classList.add("highlighted");
  }
});

//  order tracking
const circles = document.querySelectorAll(".circle"),
  progressBar = document.querySelector(".indicator"),
  buttons = document.querySelectorAll("button");

let currentStep = 1;
const updateSteps = (e) => {
  currentStep = e.target.id === "next" ? ++currentStep : --currentStep;
  circles.forEach((circle, index) => {
    circle.classList[`${index < currentStep ? "add" : "remove"}`]("active");
  });

  progressBar.style.width = `${
    ((currentStep - 1) / (circles.length - 1)) * 100
  }%`;
  if (currentStep === circles.length) {
    buttons[1].disabled = true;
  } else if (currentStep === 1) {
    buttons[0].disabled = true;
  } else {
    buttons.forEach((button) => (button.disabled = false));
  }
};
buttons.forEach((button) => {
  button.addEventListener("click", updateSteps);
});
