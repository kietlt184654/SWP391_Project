// Sidebar Toggle
const menuToggle = document.getElementById("menu-toggle");
const sidebar = document.getElementById("sidebar");

if (menuToggle && sidebar) {
    menuToggle.addEventListener("click", () => {
        sidebar.classList.toggle("show");
    });
}

// Dropdown Menu Toggle
const accountIcon = document.getElementById("accountIcon");
const dropdownMenu = document.querySelector(".dropdown-menu");

if (accountIcon && dropdownMenu) {
    accountIcon.addEventListener("click", (event) => {
        event.preventDefault();
        dropdownMenu.style.display =
            dropdownMenu.style.display === "block" ? "none" : "block";
    });
}



// Function to update project status
function fetchProjectStatus() {
    fetch(`/projects/api/project/checkStatus?projectId=${projectId}`)
        .then(response => response.text())
        .then(status => {
            const statusElement = document.getElementById("project-status");
            if (statusElement.innerText !== status) {
                statusElement.innerText = status;
            }
            if (status === "Done") {
                clearInterval(statusPolling); // Dừng polling nếu dự án đã hoàn thành
            }
        })
        .catch(error => console.error('Error fetching project status:', error));
}

// Polling function to check project status every 5 seconds
const statusPolling = setInterval(fetchProjectStatus, 5000);

