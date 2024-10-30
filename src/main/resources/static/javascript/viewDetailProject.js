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

// Elements
const modal = document.getElementById("staffModal");
const closeModalBtn = document.getElementsByClassName("close")[0];
const selectedStaffDisplay = document.getElementById("selectedStaff");
const staffIdInput = document.getElementById("staffId");
const openModalBtn = document.getElementById("openStaffModal");
const selectStaffBtn = document.getElementById("selectStaffBtn");

// Open modal when "Select Staff" button is clicked
if (openModalBtn) {
    openModalBtn.onclick = function () {
        modal.style.display = "block";
    };
}

// Close modal when "x" button is clicked
if (closeModalBtn) {
    closeModalBtn.onclick = function () {
        modal.style.display = "none";
    };
}

// Close modal when clicking outside of it
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};

// Function to select a staff member and close modal
function selectStaff(id, name, role, email, phone) {
    if (staffIdInput) {
        staffIdInput.value = id;
    }
    if (selectedStaffDisplay) {
        selectedStaffDisplay.innerText = `${name} - ${role}`;
    }
    modal.style.display = "none";
}
// function showContent(status) {
//     fetch(`/projects?status=${status}`)
//         .then(response => response.json())
//         .then(data => {
//             const taskContainer = document.querySelector('.assigned-tasks ul');
//             taskContainer.innerHTML = ""; // Clear existing projects
//
//             data.forEach(project => {
//                 const taskItem = document.createElement('li');
//                 taskItem.classList.add('task-item');
//
//                 taskItem.innerHTML = `
//                     <div class="task-details">
//                         <span class="task-name"><strong>Project Name:</strong> ${project.name}</span>
//                         <span class="task-description"><strong>Description:</strong> ${project.description}</span>
//                         <span class="task-progress"><strong>Status:</strong> ${project.status}</span>
//                         <span class="task-deadline"><strong>End Date:</strong> ${project.endDate}</span>
//                     </div>
//                 `;
//
//                 taskContainer.appendChild(taskItem);
//             });
//         })
//         .catch(error => console.error("Error fetching projects:", error));
// }
//
// // Event listeners for tabs
// document.querySelector(".tab[onclick='showContent(\"in-progress\")']").onclick = () => showContent('in-progress');
// document.querySelector(".tab[onclick='showContent(\"done\")']").onclick = () => showContent('done');
// // Function to filter tasks by status
// Function to filter tasks by status
function filterByStatus(status) {
    const url = status === 'all' ? '/tasks' : `/tasks?status=${status}`;
    console.log(`Fetching tasks with URL: ${url}`); // Debugging log
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const taskContainer = document.querySelector('.assigned-tasks ul');
            taskContainer.innerHTML = ""; // Clear existing tasks

            if (data.length === 0) {
                taskContainer.innerHTML = "<li>No tasks available.</li>"; // Handle no tasks
            }

            data.forEach(task => {
                const taskItem = document.createElement('li');
                taskItem.classList.add('task-item');
                taskItem.innerHTML = `
                    <div class="task-details">
                        <span class="task-name"><strong>Mô tả nhiệm vụ:</strong> ${task.task}</span>
                        <span class="task-staff"><strong>Tên nhân viên:</strong> ${task.staff.account.accountName}</span>
                        <span class="task-progress"><strong>Progress:</strong> ${task.status}</span>
                        <span class="task-deadline"><strong>Thời hạn:</strong> ${task.assignmentDate}</span>
                    </div>
                `;
                taskContainer.appendChild(taskItem);
            });
        })
        .catch(error => console.error("Error fetching tasks:", error));
}

// Event listeners for tabs
document.querySelectorAll('.tab-bar .tab').forEach(tab => {
    tab.addEventListener('click', () => {
        const status = tab.getAttribute('data-status') === 'task' ? 'all' : tab.getAttribute('data-status');
        filterByStatus(status);

        // Update active tab styling
        document.querySelectorAll('.tab-bar .tab').forEach(t => t.classList.remove('active'));
        tab.classList.add('active');
    });
});

