function addMaterial() {
    const container = document.getElementById('materialsContainer');
    const index = container.children.length; // Find the next index based on existing materials

    // Create a new material selection group
    const newMaterialGroup = document.createElement('div');
    newMaterialGroup.classList.add('form-group', 'material-group');

    // Create and append the <select> element
    const select = document.createElement('select');
    select.classList.add('form-control', 'material-select');
    select.name = `materials[${index}].materialId`;

    // Create the default "Choose materials" option
    const defaultOption = document.createElement('option');
    defaultOption.value = "";
    defaultOption.textContent = "Choose materials";
    defaultOption.disabled = true;
    defaultOption.selected = true;
    select.appendChild(defaultOption);

    // Clone options from the first select element
    const materialOptions = document.querySelectorAll('#material_0 option');
    materialOptions.forEach(option => {
        const newOption = document.createElement('option');
        newOption.value = option.value;
        newOption.textContent = option.textContent;
        select.appendChild(newOption);
    });

    // Create and append the <input> for quantity
    const input = document.createElement('input');
    input.type = 'number';
    input.classList.add('form-control', 'material-quantity');
    input.name = `materials[${index}].quantityNeeded`;
    input.placeholder = 'Quantity';
    input.min = 1;

    // Append the select and input to the new material group
    newMaterialGroup.appendChild(select);
    newMaterialGroup.appendChild(input);

    // Append the new material group to the container
    container.appendChild(newMaterialGroup);
}
