function initDialog(name,country) {
 $("#idOfDialogPlaceHolder").dialog({
     modal: true,
     buttons: {
            Close: function () {
                $(this).dialog("close");
            }
     },
 });
 
 document.getElementById('name').innerHTML  = name;
 document.getElementById('country').innerHTML  = country;
 
 document.getElementById('nameHid').value  = name;
 document.getElementById('countryHid').value  = country;
 
}