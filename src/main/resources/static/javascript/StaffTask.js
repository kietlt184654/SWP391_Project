// Reload the page to update the status after form submission
if (window.performance && window.performance.navigation.type === window.performance.navigation.TYPE_BACK_FORWARD) {
    window.location.reload();
}
function showDetails(staffProjectID) {
    // Mở modal
    document.getElementById("imageModal").style.display = "block";
    const imageContainer = document.getElementById("imageContainer");
    imageContainer.innerHTML = ''; // Xóa nội dung cũ

    // Lấy ảnh từ server bằng AJAX
    fetch(`/getProgressImages?staffProjectID=${staffProjectID}`)
        .then(response => response.json())
        .then(data => {
            // Kiểm tra nếu không có ảnh nào (mảng rỗng hoặc `null`)
            if (data && data.length > 0) {
                data.forEach(imageUrl => {
                    const imgElement = document.createElement("img");
                    imgElement.src = imageUrl;
                    imgElement.alt = "Progress Image";
                    imgElement.style.width = "100%";
                    imgElement.style.marginBottom = "10px";
                    imageContainer.appendChild(imgElement);
                });
            } else {
                // Hiển thị thông báo khi không có ảnh
                imageContainer.innerHTML = '<p>Chưa có ảnh được upload.</p>';
            }
        })
        .catch(error => {
            console.error('Lỗi khi tải ảnh:', error);
            // Hiển thị thông báo lỗi trong modal
            imageContainer.innerHTML = '<p>Lỗi khi tải ảnh. Vui lòng thử lại sau.</p>';
        });
}

// Hàm để đóng modal
function closeModal() {
    document.getElementById("imageModal").style.display = "none";
}

function toggleDetails(button) {
    const detailsContainer = button.nextElementSibling;
    if (detailsContainer.style.display === "none") {
        detailsContainer.style.display = "block";
        button.textContent = "Ẩn Thông Tin";
    } else {
        detailsContainer.style.display = "none";
        button.textContent = "Xem Thông Tin Chi Tiết";
    }
}
