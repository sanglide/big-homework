//管理影院
var halls = [];
var changeIndex=0;
$(document).ready(function() {

    var canSeeDate = 0;

    getCanSeeDayNum();
    getCinemaHalls();//获得影厅

    function getCinemaHalls() {
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        var nameStr="";
        for (let hall of halls) {
            //增加选项
            nameStr += "<option>" +hall.name+ "</option>"
        }
        $('#order-halls').html(nameStr);
        $('#delete-order-halls').html(nameStr);
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = ""
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<div class='cinema-hall'>" +
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.row +'*'+ hall.column +"</span>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</div>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);

    }


    //上面部分获得所有影厅并将其显示出来
    //以下为点击新增影厅发生的方法
    $("#hall-form-btn").click(function () {
        var formData = getHallForm();
        if(!validateHallForm(formData)) {
            alert("error");
            return;
        }
        if(isNaN(formData.column)&&isNaN(formData.row)){
            alert("Not Numeric");
            return false
        }
        console.log(formData);


        getCinemaHalls();
        $("#hallModal").modal('hide');
        console.log(formData);
        //以下与后端交互真方法
        postRequest(
            '/hall/add',//这里待传
            formData,//上传表单
            function (res) {
                if (res.success) {
                    getCinemaHalls();
                    $("#hallModal").modal('hide');//表单隐藏
                }else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(error);
            });

//
    });
    function getHallForm() {
        return {
            name: $('#hall-name-input').val(),
            row: $('#row-num-input').val(),
            column: $('#column-num-input').val(),
        };
    }
    function validateHallForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#hall-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.row) {
            isValidate = false;
            $('#column-num-input').parent('.form-group').addClass('has-error');
        }
        if(!data.column) {
            isValidate = false;
            $('#row-num-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }
    //下面为修改影厅信息的实现
    // function changeHallId(index){
    //     changeIndex=index;
    // }
    $("#changeHall-form-btn").click(function () {
        var form = changeHallForm();

        if(isNaN(form.column)&&isNaN(form.row)){
            alert("Not Numeric");
            return false
        }
        console.log(form);

        //这里需以后注释掉
        // getCinemaHalls();
        // $("#changeHallModal").modal('hide');
        // console.log(form);

        //以下与后端交互真方法
        postRequest(
            '/hall/change',//这里待传
            form,//上传表单
            function (res) {
                getCinemaHalls();
                $("#changeHallModal").modal('hide');//表单隐藏
            },
            function (error) {
                alert(error);
            });


    });
    $("#deleteHall-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该影厅吗")
        var deleteForm=deleteHallForm()
        console.log(deleteForm);
        if (r) {
            deleteRequest(
                '/hall/delete',
                deleteForm,
                function (res) {
                    if(res.success){
                        getCinemaHalls();
                        $("#deleteHallModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })
    function changeHallForm() {
        return {
            name: document.getElementById("order-halls").value,
            row: $('#changeRow-num-input').val(),
            column: $('#changeColumn-num-input').val(),
        };
    }
    function deleteHallForm(){
        return{
            name: document.getElementById("delete-order-halls").value
        }
    }
    //下面为可见时间的实现
    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    })
});