$(document).ready(function () {
    $('.category, .subCat').on('blur', function (e) {
        var $parent = $(this).parents('tr'),
            id = $parent.attr('data-id')
        $.ajax({
            type: 'POST',
            contentType: "application/json",
            url: window.location.origin + '/admin/updateCat/' + id,
            data: JSON.stringify({
                category: $parent.find('.category').val(),
                subCat: $parent.find('.subCat').val()
            }),
            error: function (e) {
                console.log("ERROR: ", e);
            }
        })
    })
})