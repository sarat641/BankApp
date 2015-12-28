
function editForm(id) {

    var ibanId = "iban" + id;
    var bicId = "bic" + id;
    var buttonId = "button" + id;
    document.getElementById(ibanId).disabled = false;
    document.getElementById(bicId).disabled = false;
    document.getElementById(buttonId).disabled = false;
}
function deleteRow(row, id) {
    var i = row.parentNode.parentNode.rowIndex;

    $.ajax({
        type: "DELETE",
        contentType: "application/json; charset=utf-8",
        url: "bank/rest/api/" + id,
        success: function (resp) {
            document.getElementById('accountTable').deleteRow(i);
        },
        error: function (e) {
            alert('Error occure while deleting: ' + e);
        }
    });
}


$(document).ready(function () {
    $(".modalbox").fancybox();
    $("#contact").submit(function () {
        return false;
    });


    $("#send").on("click", function () {
        var iban = $("#iban").val();
        var bic = $("#bic").val();
        var ibanlen = iban.length;
        var biclen = bic.length;


        if (ibanlen < 0) {
            $("#iban").addClass("error");
            document.getElementById("id1").innerHTML = "IBAN can not be blank";
            return;
        }
        else if (ibanlen >= 1) {
            $("#iban").removeClass("error");
            document.getElementById("id1").innerHTML = "";

        }

        if (biclen < 1) {
            $("#bic").addClass("error");
            document.getElementById("id2").innerHTML = "Bic can not be blank";
            return;
        }
        else if (biclen >= 0) {
            $("#bic").removeClass("error");
            document.getElementById("id2").innerHTML = "";
        }
        var JSONObject = {"iban": iban, "bic": bic};
        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "bank/rest/api/createAccount",
            dataType: "json",
            data: JSON.stringify(JSONObject),
            success: function (resp) {
                addRow('accountTable', resp.iban, resp.bic, resp.version, resp.id);
                resetModelForm();
                $.fancybox.close();
            },
            error: function (e) {
                var myObj = $.parseJSON(e.responseText);
                var error = "";
                for (var i = 0; i < myObj.fieldErrors.length; i++) {
                    var obj = myObj.fieldErrors[i];
                    error = error + obj.message;
                }
                alert(error);
            }
        });
    });
});
function resetModelForm() {
    document.getElementById("iban").value = "";
    document.getElementById("bic").value = "";
}

function addRow(in_tbl_name, iban, bic, version, accountId) {

    var tbody = document.getElementById(in_tbl_name).getElementsByTagName("TBODY")[0];
    // create row
    var row = document.createElement("TR");
    // create table cell 1
    var td1 = document.createElement("TD");
    var strHtml1 = "<INPUT TYPE=\"hidden\" NAME=\"" + accountId + "\"  id=\"" + accountId + "\" >";
    td1.innerHTML = strHtml1;
    // create table cell 2
    var td2 = document.createElement("TD");
    var versionId = "ver" + accountId;
    var strHtml2 = "<INPUT TYPE=\"hidden\" value=\"" + version + "\" NAME=\"" + versionId + "\"  id=\"" + versionId + "\" >";
    td2.innerHTML = strHtml2;
    // create table cell 3
    var td3 = document.createElement("TD");
    var ibanId = "iban" + accountId;
    var strHtml3 = "<INPUT disabled size=\"32\" TYPE=\"text\" value=\"" + iban + "\" NAME=\"" + ibanId + "\"   id=\"" + ibanId + "\" >";
    td3.innerHTML = strHtml3;
    // create table cell 4
    var bicId = "bic" + accountId;
    var td4 = document.createElement("TD");
    var strHtml4 = "<INPUT disabled size=\"32\" TYPE=\"text\" value=\"" + bic + "\" NAME=\"" + bicId + "\"   id=\"" + bicId + "\" >";
    td4.innerHTML = strHtml4;
    // create table cell 5
    var td5 = document.createElement("TD");
    var strHtml5 = "<a href=\"#\" onclick=\"editForm(" + accountId + ")\" >Edit</a>";
    td5.innerHTML = strHtml5;

    // create table cell 6
    var td6 = document.createElement("TD");
    var strHtml6 = "<a href=\"#\" onclick=\"deleteRow(this," + accountId + ")\" >Delete</a>";
    td6.innerHTML = strHtml6;
    // create table cell 7
    var td7 = document.createElement("TD");
    var buttonId = "button" + accountId;
    var strHtml7 = "<INPUT TYPE=\"button\"  value=\"Save\" onclick=\"saveForm(" + accountId + ")\"  id=\"" + buttonId + "\" >";
    td7.innerHTML = strHtml7;

    var td8 = document.createElement("TD");
    var errorId = "error" + accountId;
    var strHtml8 = "<span id=\"" + errorId + "\" ></span>";
    td8.innerHTML = strHtml8;
    // append data to row
    row.appendChild(td1);
    row.appendChild(td2);
    row.appendChild(td3);
    row.appendChild(td4);
    row.appendChild(td5);
    row.appendChild(td6);
    row.appendChild(td7);
    row.appendChild(td8);
    // append row to table
    tbody.appendChild(row);
}
function saveForm(accountId) {
    var ibanId = "iban" + accountId;
    var iban = document.getElementById(ibanId).value;
    var bicId = "bic" + accountId;
    var bic = document.getElementById(bicId).value;
    var versionId = "ver" + accountId;
    var version = document.getElementById(versionId).value;
    var buttonId = "button" + accountId;
    var errorId = "error" + accountId;

    var JSONObject = {"iban": iban, "bic": bic, "version": version, "id": accountId};
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url: "bank/rest/api/updateAccount",
        dataType: "json",
        data: JSON.stringify(JSONObject),
        success: function (resp) {
            document.getElementById(versionId).value = resp.version; // updating version id
            document.getElementById(bicId).disabled = true;
            document.getElementById(ibanId).disabled = true;
            document.getElementById(buttonId).disabled = true;
            document.getElementById(versionId).innerHTML = "";
        },
        error: function (e) {
            var myObj = $.parseJSON(e.responseText);
            var error = "";
            for (var i = 0; i < myObj.fieldErrors.length; i++) {
                var obj = myObj.fieldErrors[i];
                error = error + obj.message;
            }
            document.getElementById(errorId).innerHTML = error;
        }
    });
}