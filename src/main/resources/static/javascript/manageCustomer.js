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
// Function to confirm delete action and send delete request
// Sidebar Toggle
document.getElementById("menu-toggle").addEventListener("click", function () {
    const sidebar = document.getElementById("sidebar");
    sidebar.classList.toggle("show");
});

// Display Notification
function showNotification(message) {
    const notification = document.getElementById("notification");
    notification.textContent = message;
    notification.style.display = "block";
    setTimeout(() => {
        notification.style.display = "none";
    }, 3000);
}
// Hiển thị modal khi nhấn nút xóa
document.querySelectorAll(".btn-action.delete").forEach(button => {
    button.addEventListener("click", (event) => {
        event.preventDefault();
        const deleteUrl = button.closest("form").action;
        const deleteModal = document.getElementById("deleteModal");
        deleteModal.style.display = "flex";

        // Xác nhận xóa
        document.getElementById("confirmDelete").onclick = () => {
            window.location.href = deleteUrl;
        };

        // Hủy bỏ xóa
        document.getElementById("cancelDelete").onclick = () => {
            deleteModal.style.display = "none";
        };
    });
});

