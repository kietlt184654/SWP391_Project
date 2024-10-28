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
// Elements
const modal = document.getElementById("staffModal");
const openModalBtn = document.getElementById("openStaffModal");
const closeModalBtn = document.getElementsByClassName("close")[0];
const staffIdInput = document.getElementById("staffId");
const selectedStaffText = document.getElementById("selectedStaff");

// Elements to display staff details in modal
const staffName = document.getElementById("staffName");
const staffRole = document.getElementById("staffRole");
const staffEmail = document.getElementById("staffEmail");
const staffPhone = document.getElementById("staffPhone");
const selectStaffBtn = document.getElementById("selectStaffBtn");

// Open modal when "Select Staff" button is clicked
openModalBtn.onclick = function () {
    modal.style.display = "block";

    // Populate modal with data from a list of staff
    // Here, an example array of staff for demonstration
    const staffList = [
        { id: 1, name: "John Doe", role: "Construction Staff", email: "john@example.com", phone: "123-456-7890" },
        { id: 2, name: "Jane Smith", role: "Construction Staff", email: "jane@example.com", phone: "098-765-4321" }
    ];

    // Render staff list as options (simulating dynamic population)
    let staffOptions = document.createElement("ul");
    staffOptions.innerHTML = '';
    staffList.forEach(staff => {
        let listItem = document.createElement("li");
        listItem.innerHTML = `<button type="button" class="staff-selection-btn" data-staff-id="${staff.id}" data-staff-name="${staff.name}" data-staff-role="${staff.role}" data-staff-email="${staff.email}" data-staff-phone="${staff.phone}">${staff.name}</button>`;
        staffOptions.appendChild(listItem);
    });

    // Replace staff details in modal with generated list
    const staffDetailsContainer = document.getElementById("staffDetails");
    staffDetailsContainer.innerHTML = "";
    staffDetailsContainer.appendChild(staffOptions);

    // Add event listeners to staff options
    document.querySelectorAll(".staff-selection-btn").forEach(button => {
        button.addEventListener("click", () => {
            // Populate the modal with staff details
            staffName.textContent = button.getAttribute("data-staff-name");
            staffRole.textContent = button.getAttribute("data-staff-role");
            staffEmail.textContent = button.getAttribute("data-staff-email");
            staffPhone.textContent = button.getAttribute("data-staff-phone");

            // Attach event to Select button inside modal
            selectStaffBtn.onclick = function () {
                staffIdInput.value = button.getAttribute("data-staff-id");
                selectedStaffText.textContent = `Selected Staff: ${staffName.textContent}`;
                modal.style.display = "none";
            };
        });
    });
};

// Close modal when "x" button is clicked
closeModalBtn.onclick = function () {
    modal.style.display = "none";
};

// Close modal when clicking outside of it
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
};


