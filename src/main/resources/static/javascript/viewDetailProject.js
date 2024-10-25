// Sidebar Toggle
const menuToggle = document.getElementById("menu-toggle");
const sidebar = document.getElementById("sidebar");

menuToggle.addEventListener("click", () => {
    sidebar.classList.toggle("show");
});

// Dropdown Menu Toggle
const accountIcon = document.getElementById("accountIcon");
const dropdownMenu = document.querySelector(".dropdown-menu");

accountIcon.addEventListener("click", (event) => {
    event.preventDefault();
    dropdownMenu.style.display =
        dropdownMenu.style.display === "block" ? "none" : "block";
});

// Toggle Project Content
document.querySelectorAll(".toggle-btn").forEach((btn) => {
    btn.addEventListener("click", () => {
        const projectContent = btn.nextElementSibling;
        projectContent.style.display =
            projectContent.style.display === "block" ? "none" : "block";
        btn.innerHTML = btn.innerHTML.includes("▼") ? "▲" : "▼";
    });
});

// Move Tab (Task, Progress, Finish)
function showContent(contentType) {
    const taskContent = document.getElementById("task-content");
    const progressContent = document.getElementById("progress-content");
    const finishContent = document.getElementById("finish-content");
    const tabs = document.querySelectorAll(".tab");

    tabs.forEach((tab) => tab.classList.remove("active"));

    switch (contentType) {
        case "task":
            taskContent.style.display = "block";
            progressContent.style.display = "none";
            finishContent.style.display = "none";
            document.querySelector(".tab:nth-child(1)").classList.add("active");
            break;
        case "progress":
            progressContent.style.display = "block";
            taskContent.style.display = "none";
            finishContent.style.display = "none";
            document.querySelector(".tab:nth-child(2)").classList.add("active");
            break;
        case "finish":
            finishContent.style.display = "block";
            taskContent.style.display = "none";
            progressContent.style.display = "none";
            document.querySelector(".tab:nth-child(3)").classList.add("active");
            break;
    }
}
