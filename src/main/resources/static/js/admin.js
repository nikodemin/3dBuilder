'use strict'
$(document).ready(function () {
    $('#catalog').slideUp()

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    var vueData = {
        tools: [],
        categories: [],
        brands: [],
        currCategory: {},
        toolId: -1,
        newCategoryName: "",
        isToolEditing: false
    }

    $('#tree').jstree({
        "core" : {
            "check_callback" : true,
            "themes" : { "stripes" : true },
            'data' : {
                'url': baseUrl+'/admin/categories',
                'data': function (node) {
                    return {
                        'id':node.id
                    }
                }
            }
        }
    }).bind("select_node.jstree",function(event, data){
        vueData.currCategory = data.node.original
        getTools()
    }).bind("refresh.jstree",function (event,data) {
        getCategories()
    })

    function getCategories() {
        $.ajax({
            url: baseUrl + '/admin/categories/leafs',
            type: 'GET',
            success: function (data) {
                vueData.categories = data
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }
    getCategories()

    function getBrands() {
        $.ajax({
            url: baseUrl + '/admin/brands',
            type: 'GET',
            success: function (data) {
                vueData.brands = data
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }
    getBrands()

    function getTools() {
        $.ajax({
            url: baseUrl + '/admin/tools/category/'+vueData.currCategory.id,
            type: 'GET',
            success: function (data) {
                vueData.tools = data
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    }

    $('#addCatModal form').on('submit',function(e) {
        e.preventDefault()
        $('#addCatModal').hide()
        $('#shadow').hide()
        var data = new FormData($('#addCatModal form')[0])

        $.ajax({
            url: baseUrl+'/admin/category/parent/'+vueData.currCategory.id,
            type: 'POST',
            enctype: 'multipart/form-data',
            data: data,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                raisePopup(data,'success')
                $('#tree').jstree(true).refresh();
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    })

    $('#addToolModal form').on('submit',function(e) {
        e.preventDefault()
        $('#addToolModal').hide()
        $('#shadow').hide()
        $('div.modal input[type=file]').attr('required','required')
        var data = new FormData($('#addToolModal form')[0])
        if (vueData.isToolEditing){
            var url = '/admin/tool/'+vueData.toolId
        } else {
            url = '/admin/tools/category/'+vueData.currCategory.id
        }
        $('#addToolModal input[type=file]').val(null)

        $.ajax({
            url: baseUrl+url,
            type: vueData.isToolEditing? 'PUT':'POST',
            enctype: 'multipart/form-data',
            data: data,
            cache: false,
            processData: false,
            contentType: false,
            success: function (data) {
                raisePopup(data,'success')
                getTools()
            },
            error: function (jqXHR, status, errorThrown) {
                raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                console.log('ERROR: ' + jqXHR.responseText)
            }
        })
    })

    $('div.modal > i').on('click', function (e) {
        $('div.modal').hide()
        $('#shadow').hide()
        $('div.modal input[type=file]').attr('required','required')
    })

    var vue = new Vue({
        el: '#main-container',
        data: vueData,
        methods: {
            deleteCategory: function () {
                $.ajax({
                    url: baseUrl + '/admin/category/'+vueData.currCategory.id,
                    type: 'DELETE',
                    success: function (data) {
                        raisePopup(data,'success')
                        $('#tree').jstree(true).refresh();
                    },
                    error: function (jqXHR, status, errorThrown) {
                        raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            renameCategory: function () {
                $.ajax({
                    url: baseUrl + '/admin/category/'+vueData.currCategory.id+
                        '/newName/'+vueData.newCategoryName,
                    type: 'PUT',
                    success: function (data) {
                        raisePopup(data,'success')
                        $('#tree').jstree(true).refresh();
                    },
                    error: function (jqXHR, status, errorThrown) {
                        raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            addSubCategory: function () {
                $('#addCatModal').show()
                $('#shadow').show()
            },
            addTool: function () {
                vueData.isToolEditing = false
                $('#addToolModal').show()
                $('#shadow').show()
                $('#addToolModal select[name=categoryID] option').remove()
                vueData.categories.forEach((category)=>{
                    if (category.name===vueData.currCategory.text){
                        $('#addToolModal select[name=categoryID]')
                            .append(`<option selected value="${category.id}">${category.name}</option>`)
                    } else {
                        $('#addToolModal select[name=categoryID]')
                            .append(`<option value="${category.id}">${category.name}</option>`)
                    }
                })
                vueData.brands.forEach((brand)=>{
                    $('#addToolModal select[name=brandId]')
                        .append(`<option value="${brand.id}">${brand.name}</option>`)
                })
            },
            editTool: function (id) {
                this.addTool()
                vueData.toolId = id
                vueData.isToolEditing = true
                let tool = vueData.tools.filter((tool)=>tool.id===id)[0]
                $('#addToolModal input[name=name]').val(tool.name)
                $('#addToolModal input[name=power]').val(tool.power)
                $('#addToolModal input[name=weight]').val(tool.weight)
                $('#addToolModal input[name=price]').val(tool.price)
                $('#addToolModal input[name=pledge]').val(tool.pledge)
                $('#addToolModal textarea').val(tool.description)
                $('#addToolModal select[name=brandId] option')
                    .removeAttr('selected')
                $('#addToolModal input[type=file]').removeAttr('required')
                $(`#addToolModal select[name=brandId] option[value=${tool.brand.id}]`)
                    .attr('selected','selected')
            },
            detachTool: function (id) {
                $.ajax({
                    url: baseUrl + '/admin/tool/detach/'+id,
                    type: 'PUT',
                    success: function (data) {
                        raisePopup(data,'success')
                        getTools()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            },
            deleteTool: function (id) {
                $.ajax({
                    url: baseUrl + '/admin/tool/'+id,
                    type: 'DELETE',
                    success: function (data) {
                        raisePopup(data,'success')
                        getTools()
                    },
                    error: function (jqXHR, status, errorThrown) {
                        raisePopup('ERROR: ' + jqXHR.responseText,'danger')
                        console.log('ERROR: ' + jqXHR.responseText)
                    }
                })
            }
        }
    })
})