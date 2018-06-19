function makeEditable() {
    $(".delete").click(function () {
        deleteRow($(this).attr("id"));
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () {
            updateTable();
            successNoty("Deleted");
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
            successNoty("Saved");
        }
    });
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}

function addMeal() {
    $("#detailsMealForm").find(":input").val("");
    $("#editRow1").modal();
}

function updateMealsTable() {
    $.get(ajaxMealUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}
function saveMeal() {
    var form = $("#detailsMealForm");
    $.ajax({
        type: "POST",
        url: ajaxMealUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow1").modal("hide");
            updateMealsTable();
            successNoty("Saved");
        }
    });
}
    function deleteMeal(id) {
        $.ajax({
            url: ajaxMealUrl + id,
            type: "DELETE",
            success: function () {
                updateMealsTable();
                successNoty("Deleted");
            }
        });
    }

    function makeEditableMeals() {
        $(".delete").click(function () {
            deleteMeal(k$(this).attr("id"));
        });

        $(document).ajaxError(function (event, jqXHR, options, jsExc) {
            failNoty(jqXHR);
        });

        // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
        $.ajaxSetup({cache: false});
    }
