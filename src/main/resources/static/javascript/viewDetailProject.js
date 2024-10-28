// // side bar
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

// move tab(task, progress, finish)
function showContent(contentType) {
  const taskContent = document.getElementById("task-content");
  const progressContent = document.getElementById("progress-content");
  const finishContent = document.getElementById("finish-content");

  const tabs = document.querySelectorAll(".tab");

  tabs.forEach((tab) => {
    tab.classList.remove("active");
  });

  if (contentType === "task") {
    taskContent.style.display = "block";
    progressContent.style.display = "none";
    finishContent.style.display = "none";
    document.querySelector(".tab:nth-child(1)").classList.add("active");
  } else if (contentType === "progress") {
    progressContent.style.display = "block";
    taskContent.style.display = "none";
    finishContent.style.display = "none";
    document.querySelector(".tab:nth-child(2)").classList.add("active");
  } else if (contentType === "finish") {
    finishContent.style.display = "block";
    taskContent.style.display = "none";
    progressContent.style.display = "none";
    document.querySelector(".tab:nth-child(3)").classList.add("active");
  }
}
// Dấu 3 chấm xem thông tin member
document.querySelectorAll(".toggle-info").forEach(function (icon) {
  icon.addEventListener("click", function () {
    const memberDetails = this.parentElement.nextElementSibling;
    if (
      memberDetails.style.display === "none" ||
      memberDetails.style.display === ""
    ) {
      memberDetails.style.display = "block";
    } else {
      memberDetails.style.display = "none";
    }
  });
});

//Dấu + add member nhận task
document.querySelector(".add-btn").addEventListener("click", function () {
  const customerTable = document.getElementById("list-customer");
  if (
    customerTable.style.display === "none" ||
    customerTable.style.display === ""
  ) {
    customerTable.style.display = "block";
  } else {
    customerTable.style.display = "none";
  }
});
