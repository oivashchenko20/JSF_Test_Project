function getChange() {
    let type = PF('selectType').getSelectedValue();
    let box = document.getElementById('form:option');

    if (type === 'COMBOBOX' || type === 'RADIOBUTTON') {
        box.style.display = "block";
    } else {
        box.style.display = "none";
    }
}