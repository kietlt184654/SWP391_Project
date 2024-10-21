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
