function filterStaff() {
    const isConsultantChecked = document.getElementById('consultantCheckbox').checked;
    const isConstructionChecked = document.getElementById('constructionCheckbox').checked;

    const rows = document.querySelectorAll('.staff-row');
    
    // Check if both checkboxes are unchecked
    if (!isConsultantChecked && !isConstructionChecked) {
        rows.forEach(row => {
            row.style.display = ''; // Show all rows
        });
        return; // Exit the function early
    }

    rows.forEach(row => {
        const roleID = row.querySelector('td:nth-child(4)').innerText; // Assuming Role ID is in the 4th column
        if ((isConsultantChecked && roleID === 'Consultant') || (isConstructionChecked && roleID === 'Construction')) {
            row.style.display = ''; // Show row
        } else {
            row.style.display = 'none'; // Hide row
        }
    });
}

function openModal() {
    document.getElementById('addStaffModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('addStaffModal').style.display = 'none';
}

function openEditModal(id) {
    // Populate the edit form with the staff data based on the ID
    // For demonstration, we will set some dummy data. Replace this with actual data retrieval logic.
    document.getElementById('editStaffId').value = id;
    document.getElementById('editAccountName').value = "Staff Name " + id; // Replace with actual data
    document.getElementById('editAccountTypeID').value = "Role ID " + id; // Replace with actual data
    document.getElementById('editPhoneNumber').value = "Phone Number " + id; // Replace with actual data
    document.getElementById('editAddress').value = "Address " + id; // Replace with actual data
    document.getElementById('editImages').value = "Image URL " + id; // Replace with actual data

    document.getElementById('editStaffModal').style.display = 'block';
}

function closeEditModal() {
    document.getElementById('editStaffModal').style.display = 'none';
}

// Close modals when clicking outside of them
window.onclick = function(event) {
    const addModal = document.getElementById('addStaffModal');
    const editModal = document.getElementById('editStaffModal');
    if (event.target === addModal) {
        closeModal();
    }
    if (event.target === editModal) {
        closeEditModal();
    }
}
