function getEditChange() {
    var boxEdit = document.getElementById('form:optionEdit');
    var editType = PF('typeEdit').getSelectedValue();

    if (editType === 'COMBOBOX' || editType === 'RADIOBUTTON') {
        boxEdit.style.display = "block";
    } else {
        boxEdit.style.display = "none";
    }
}