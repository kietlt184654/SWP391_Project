let materialCount = 1; // Đếm số lượng nguyên liệu để đặt tên cho các trường

function addMaterial() {
    const container = document.getElementById('materialsContainer');

    // Lưu lại HTML của phần tử nguyên liệu đầu tiên để sao chép
    const firstMaterialGroup = document.querySelector('.material-group').cloneNode(true);

    // Cập nhật lại các thuộc tính ID và name để chúng duy nhất cho mỗi nhóm nguyên liệu
    const select = firstMaterialGroup.querySelector('.material-select');
    const quantity = firstMaterialGroup.querySelector('.material-quantity');

    select.id = `material_${materialCount}`;
    select.name = `materials[${materialCount}].materialId`;
    select.value = ""; // Đặt lại giá trị về rỗng

    quantity.name = `materials[${materialCount}].quantityNeeded`;
    quantity.value = ""; // Đặt lại số lượng về rỗng

    // Thêm phần tử đã sao chép vào container
    container.appendChild(firstMaterialGroup);
    materialCount++; // Tăng biến đếm
}

function rejectDesign() {
    if (confirm("Bạn có chắc chắn muốn từ chối thiết kế này không?")) {
        const form = document.createElement('form');
        form.method = 'POST';
        form.action = /*[[@{'/manager/designs/' + ${design.designId} + '/reject'}]]*/ '';
        document.body.appendChild(form);
        form.submit();
    }
}
let arrow = document.querySelectorAll(".arrow");
for (var i = 0; i < arrow.length; i++) {
    arrow[i].addEventListener("click", (e) => {
        let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
        arrowParent.classList.toggle("showMenu");
    });
}
let sidebar = document.querySelector(".sidebar");
let sidebarBtn = document.querySelector(".bx-menu");
console.log(sidebarBtn);
sidebarBtn.addEventListener("click", () => {
    sidebar.classList.toggle("close");
});

