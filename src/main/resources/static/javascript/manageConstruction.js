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

const toggleBtns = document.querySelectorAll(".toggle-btn");

toggleBtns.forEach((btn) => {
  btn.addEventListener("click", () => {
    const projectContent = btn.nextElementSibling;

    // Toggle display of the project content
    if (
      projectContent.style.display === "none" ||
      projectContent.style.display === ""
    ) {
      projectContent.style.display = "block";
      btn.innerHTML = btn.innerHTML.replace("▼", "▲");
    } else {
      projectContent.style.display = "none";
      btn.innerHTML = btn.innerHTML.replace("▲", "▼");
    }
  });
});
