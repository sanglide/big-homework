$(document).ready(function() {

    getAllAccounts();//获得所有员工的账户

    function getAllAccounts(){
        // var accounts=[
        //     {
        //         id:4,
        //         username:"/ewew",
        //         password:"wew4444"
        //     },{
        //         id:6,
        //         username:"/rr32rrr",
        //         password:"e44443235"
        //     }
        // ]
        // renderAccounts(accounts);

        getRequest(
            "/account/get",
            function (res) {
                var accounts = res.content;
                renderAccounts(accounts);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    }

    function renderAccounts(accounts) {
        $("#accounts-content").empty();
        var accountsDomStr="";

        accounts.forEach(function (account) {
            accountsDomStr +=
                "<tr class='account-container' data-account='"+JSON.stringify(account)+"'>" +
                "   <td>" +
                "       <div>"+account.id+"</div>"+
                "   </td>"+
                "   <td>" +
                "       <div>"+account.username+"</div>"+
                "   </td>"+
                "   <td>" +
                "       <div>"+account.password+"</div>"+
                "   </td>"+
                "</tr>"
        })
        $("#accounts-content").append(accountsDomStr);
    }

    $("#addButton").click(function () {
        $("#account-name-input").val("");
        $("#account-password-input").val("");
    })


    $("#account-form-btn").click(function(){
        console.log("================");
        //UserForm
        var form={
            username:$("#account-name-input").val(),
            password:$("#account-password-input").val()
        }
        console.log(form);

        if(!validateAccountForm(form)){
            return;
        }

        postRequest(
            "/account/add",
            form,
            function (res) {
                if(res.success){
                    getAllAccounts();
                    $("#accountModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    })

    $(document).on('click','.account-container',function (e) {
        var account=JSON.parse(e.currentTarget.dataset.account);
        $("#account-edit-name-input").val(account.username);
        $("#account-edit-password-input").val(account.password);
        $('#accountEditModal')[0].dataset.accountId = account.id;

        $("#accountEditModal").modal('show');
    })

    $("#account-edit-form-btn").click(function () {
        //AdminForm
        var form={
            id:Number($('#accountEditModal')[0].dataset.accountId),
            username:$("#account-edit-name-input").val(),
            password: $("#account-edit-password-input").val(),
        }

        console.log(form);
        if(!validateAccountEditForm(form)){
            return;
        }

        postRequest(
            "/account/update",
            form,
            function (res) {
                if(res.success){
                    getAllAccounts();
                    $("#accountEditModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        )
    })

    $("#account-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该员工账户信息吗")
        if (r) {
            deleteRequest(
                '/account/delete/batch',
                {accountIdList:[Number($('#accountEditModal')[0].dataset.accountId)]},
                function (res) {
                    if(res.success){
                        getAllAccounts();
                        $("#accountEditModal").modal('hide');
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



    function validateAccountForm(data) {
        var isValidate = true;
        if (!data.username || data.username.length < 4 || data.username.length > 10) {
            isValidate = false;
            $('#account-name-error').css("visibility", "visible");
            $("#account-name-error").text("员工用户名长度应在4-10位内");
        }
        if (!data.username.startsWith("/")) {
            isValidate = false;
            $('#account-name-error').css("visibility", "visible");
            $("#account-name-error").text("员工用户名必须以/开头");
        }
        if (!data.password || data.password.length < 6 || data.password.length > 12) {
            isValidate = false;
            $('#account-password-error').css("visibility", "visible");
            $("#account-password-error").text("员工密码长度应在6-12位内");
        }

        return isValidate;
    }

    function validateAccountEditForm(data) {
        var isValidate = true;
        if(!data.id){
            isValidate=false;
            alert("员工id为空!");
        }
        if (!data.username || data.username.length < 4 || data.username.length > 10) {
            isValidate = false;
            $('#account-edit-name-error').css("visibility", "visible");
            $("#account-edit-name-error").text("员工用户名长度应在4-10位内");
        }
        if (!data.username.startsWith("/")) {
            isValidate = false;
            $('#account-edit-name-error').css("visibility", "visible");
            $("#account-edit-name-error").text("员工用户名必须以/开头");
        }
        if (!data.password || data.password.length < 6 || data.password.length > 12) {
            isValidate = false;
            $('#account-edit-password-error').css("visibility", "visible");
            $("#account-edit-password-error").text("员工密码长度应在6-12位内");
        }

        return isValidate;
    }

})