let arrow = document.querySelectorAll(".arrow");
for (var i = 0; i < arrow.length; i++) {
    arrow[i].addEventListener("click", (e) => {
        let arrowParent = e.target.parentElement.parentElement; //selecting main parent of arrow
        arrowParent.classList.toggle("showMenu");
    });
}
let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".bx-menu");
console.log(sidebarBtn);
sidebarBtn.addEventListener("click", () => {
    sidebar.classList.toggle("close");
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
//show modal when delete
document.querySelectorAll(".btn-action.delete").forEach((button) => {
    button.addEventListener("click", (event) => {
        event.preventDefault();
        const deleteUrl = button.closest("form").action;
        const deleteModal = document.getElementById("deleteModal");
        deleteModal.style.display = "flex";

        // delete
        document.getElementById("confirmDelete").onclick = () => {
            window.location.href = deleteUrl;
        };

        // cancel to delete
        document.getElementById("cancelDelete").onclick = () => {
            deleteModal.style.display = "none";
        };
    });
});
