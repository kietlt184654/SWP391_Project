// Hiển thị thông báo thành công nếu tồn tại
const successAlert = document.getElementById('success-alert');
const modal = document.getElementById('success-modal');

if (successAlert && successAlert.textContent) {
    document.getElementById('modal-message').innerText = successAlert.textContent;
    modal.style.display = 'block'; // Hiện modal
}

// Đóng modal
function closeModal() {
    modal.style.display = 'none';
}

// Chuyển hướng về trang chi tiết dự án
function redirectToProjectDetail() {
    window.location.href = '/viewDetailProject'; // Thay đổi đường dẫn nếu cần
}

// Lắng nghe sự kiện để đóng modal khi nhấn ngoài modal
window.onclick = function(event) {
    if (event.target === modal) {
        closeModal();
    }
}
// Get the current date in the format required by the date input
const today = new Date().toISOString().split('T')[0];
// Set the min attribute to today's date
document.getElementById("deadline-input").setAttribute("min", today);
document.querySelectorAll('.staff-container tr').forEach((row) => {
    const taskInput = row.querySelector('.task-input');
    const deadlineInput = row.querySelector('.deadline-input');
    const hiddenTaskInput = row.querySelector('.task-input-hidden');
    const hiddenDeadlineInput = row.querySelector('.deadline-input-hidden');

    if (taskInput && hiddenTaskInput) {
        taskInput.addEventListener('input', () => hiddenTaskInput.value = taskInput.value);
    }

    if (deadlineInput && hiddenDeadlineInput) {
        deadlineInput.addEventListener('input', () => hiddenDeadlineInput.value = deadlineInput.value);
    }
});
